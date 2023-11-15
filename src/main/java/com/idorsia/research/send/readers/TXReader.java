package com.idorsia.research.send.readers;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.actelion.research.business.ppg.formulation.PpgTreatment;
import com.actelion.research.business.spi.formulation.SpiFormulation;
import com.idorsia.research.send.ContextServices;
import com.idorsia.research.send.SendService;
import com.idorsia.research.send.domain.EtcdElement;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.domain.TrialSets;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.send.processors.DataUtils;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.BiotypeMetadataBiosampleDto;
import com.idorsia.research.spirit.core.dto.GroupDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.model.Administration;
import com.idorsia.research.spirit.core.model.NamedTreatment;
import com.idorsia.research.spirit.core.service.AdministrationService;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.AssignmentService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.GroupService;
import com.idorsia.research.spirit.core.service.NamedTreatmentService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("txReader")
@Scope("step")
public class TXReader implements ItemReader<GenericDomain<TrialSets>> {

	private StudyDto study;
	private String ctversion;
	private StepExecution stepExecution;
	private Map<String, EtcdElement> etcdMap;
	private static final String TXDONE = "txdone";
	private Map<String, String> armMap;
	private StudyService studyService;
	private AssayResultService assayResultService;
	private BiosampleService biosampleService;
	private AssignmentService assignmentService;
	private NamedTreatmentService namedTreatmentService;
	private AdministrationService administrationService;
	private GroupService groupService;
	private static final String[] paramcds = new String[] {"ARMCD","GRPLBL","TRTDOS","TRTDOSU","SEXPOP","SPECIES","STRAIN"};//TO DEFINE

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
	}

	@BeforeStep
	@SuppressWarnings("unchecked")
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
		this.ctversion = (String) stepExecution.getJobExecution().getExecutionContext().get(Constants.CTVERSION);
		this.armMap = (Map<String, String>) stepExecution.getJobExecution().getExecutionContext().get("armcdMap");
		this.etcdMap = (Map<String, EtcdElement>) stepExecution.getJobExecution().getExecutionContext().get("etcdMap");
	}

	@Override
	public GenericDomain<TrialSets> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(TXDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(TXDONE)) {
			return null;
		}
		System.out.println("Calling TX READER");
		List<TrialSets> txList = getTrialSets();
		stepExecution.getJobExecution().getExecutionContext().put(TXDONE, true);
		return new GenericDomain<TrialSets>(txList);
	}

	private List<TrialSets> getTrialSets() {
		List<TrialSets> txList = new LinkedList<TrialSets>();
		Map<Integer, Set<TreatmentSubjectData>> admTrtDataMap = new HashMap<Integer, Set<TreatmentSubjectData>>();
		List<BiosampleDto> biosamples = getStudyService().getSubjectsSorted(study);
		Map<Integer, Set<Administration>> bioSampleAdmMap = getBiosampleAdministrationsMap();
		
		for(BiosampleDto bs : biosamples) {
			String specie = null;
			String strain = null;
			String sex = null;
			for(BiotypeMetadataBiosampleDto meta : bs.getMetadatas()) {
				if(meta.getMetadata().getName().equalsIgnoreCase("type")) {
					specie = meta.getValue().split("/")[0]; 
					strain = meta.getValue().split("/")[1];
				}
				if(meta.getMetadata().getName().equalsIgnoreCase("sex")) {
					sex = meta.getValue();
				}
			}
			if(bioSampleAdmMap.get(bs.getId()) != null) {
				GroupDto group = getBiosampleService().getGroup(Collections.singletonList(bs));
				String groupName = group != null ? group.getName() : "";
				for(Administration adm : bioSampleAdmMap.get(bs.getId())) {
					TreatmentSubjectData tsd = new TreatmentSubjectData(groupName, specie, strain, sex);
					if(admTrtDataMap.get(adm.getNamedTreatmentId()) == null) {
						Set<TreatmentSubjectData> tsdSet = new HashSet<TXReader.TreatmentSubjectData>(); 
						tsdSet.add(tsd);
						admTrtDataMap.put(adm.getNamedTreatmentId(), tsdSet);
					}else {
						admTrtDataMap.get(adm.getNamedTreatmentId()).add(tsd);
					}
				}
			}
		}
		int idx = 0;
		for(Entry<String, String> entry : armMap.entrySet()) {
			NamedTreatment trt = getNamedTreatmentService().get(etcdMap.get(entry.getKey()).getNamedTreatmentId());
			PpgTreatment ppgTrt = getPpgTreatment(trt.getPpgTreatmentInstanceId());
			SpiFormulation spiTrt = getSpiTreatment(trt.getSpiTreatmentId());
			String doseLvl = ppgTrt != null
					? ppgTrt.getCompounds().isEmpty() ? "" : String.valueOf(ppgTrt.getCompounds().get(0).getDose())
					: spiTrt != null ? spiTrt.getFormulation_compounds().isEmpty() ? ""
							: String.valueOf(spiTrt.getFormulation_compounds().get(0).getDose_amount()) : "";
			String doseUnit = ppgTrt != null
					? ppgTrt.getCompounds().isEmpty() ? "" : ppgTrt.getCompounds().get(0).getDoseUnit().getSymbol()
					: spiTrt != null ? spiTrt.getFormulation_compounds().isEmpty() ? ""
							: spiTrt.getFormulation_compounds().get(0).getDose_unit().getSymbols() : "";
			Set<TreatmentSubjectData> tsdSet = admTrtDataMap.get(etcdMap.get(entry.getKey()).getNamedTreatmentId());
			for(TreatmentSubjectData tsd : tsdSet) {
				for(String paramcd : paramcds){
					idx += 1;
					TrialSets tx = new TrialSets();
					tx.setStudyId(study.getStudyId());
					tx.setDomain(Constants.DOMAIN_TX);
					tx.setSetCd(entry.getValue());
					tx.setSet(etcdMap.get(entry.getKey()).getElement());
					tx.setTxSeq(idx);
					tx.setTxParmCd(paramcd);
					switch (paramcd) {
					case "ARMCD":
						tx.setTxParm("Arm Code");
						tx.setTxVal(entry.getValue());
						break;
					case "GRPLBL":
						tx.setTxParm("Group Label");
						tx.setTxVal(tsd.getGoupLabel());
						break;
					case "TRTDOS":
						tx.setTxParm("Dose Level");
						tx.setTxVal(doseLvl);
						break;
					case "TRTDOSU":
						tx.setTxParm("Dose Units");
						tx.setTxVal(doseUnit);
						break;
					case "SEXPOP":
						tx.setTxParm("Sex of Participants");
						tx.setTxVal(tsd.getSex());
						break;
					case "SPECIES":
						tx.setTxParm("Species");
						List<String> speciesList = SendService.getTerminologyByCode(ctversion, "SPECIES");
						String specie = DataUtils.getTerminologyCandidate(tsd.getSpecie(),
								speciesList);
						tx.setTxVal(specie);
						break;
					case "STRAIN":
						tx.setTxParm("Strain/Substrain");
						List<String> strainList = SendService.getTerminologyByCode(ctversion, "STRAIN");
						String strain = DataUtils.getTerminologyCandidate(tsd.getStrain(),
								strainList);
						tx.setTxVal(strain);
						break;
					default:
						break;
					}
					txList.add(tx);
				}
			}
			
		}

		return txList;
	}
	
	private Map<Integer, Set<Administration>> getBiosampleAdministrationsMap() {
		Map<Integer, Set<Administration>> res = new HashMap<Integer, Set<Administration>>();
		List<Administration> adms = getAdministrationService().getByStudy(study.getId());
		for(Administration adm : adms) {
			if(res.get(adm.getBiosampleId()) != null) {
				res.get(adm.getBiosampleId()).add(adm);
			}else {
				Set<Administration> admSet = new HashSet<Administration>();
				admSet.add(adm);
				res.put(adm.getBiosampleId(), admSet);
			}
		}
		
		return res;
	}


	private class TreatmentSubjectData {
		private String goupLabel;
		private String specie;
		private String strain;
		private String sex;

		public TreatmentSubjectData(String goupLabel, String specie, String strain, String sex) {
			super();
			this.goupLabel = goupLabel;
			this.specie = specie;
			this.strain = strain;
			this.sex = sex;
		}

		public String getGoupLabel() {
			return goupLabel;
		}

		public String getSpecie() {
			return specie;
		}

		public String getStrain() {
			return strain;
		}

		public String getSex() {
			return sex;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((goupLabel == null) ? 0 : goupLabel.hashCode());
			result = prime * result + ((sex == null) ? 0 : sex.hashCode());
			result = prime * result + ((specie == null) ? 0 : specie.hashCode());
			result = prime * result + ((strain == null) ? 0 : strain.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TreatmentSubjectData other = (TreatmentSubjectData) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (goupLabel == null) {
				if (other.goupLabel != null)
					return false;
			} else if (!goupLabel.equals(other.goupLabel))
				return false;
			if (sex == null) {
				if (other.sex != null)
					return false;
			} else if (!sex.equals(other.sex))
				return false;
			if (specie == null) {
				if (other.specie != null)
					return false;
			} else if (!specie.equals(other.specie))
				return false;
			if (strain == null) {
				if (other.strain != null)
					return false;
			} else if (!strain.equals(other.strain))
				return false;
			return true;
		}

		private TXReader getEnclosingInstance() {
			return TXReader.this;
		}
	}
	
	private SpiFormulation getSpiTreatment(Integer spiID) {
		if(spiID == null) return null;
		SpiFormulation spi = getNamedTreatmentService().getSpiFormulation(spiID);
		return spi;
	}

	private PpgTreatment getPpgTreatment(Integer ppgId) {
		if(ppgId == null) return null;
		PpgTreatment ppg = getNamedTreatmentService().getTreatment(ppgId);
		return ppg;
	}
	
	public StudyService getStudyService() {
		return studyService == null ? ContextServices.getStudyService() : studyService;
	}

	public AssayResultService getAssayResultService() {
		return assayResultService == null ? ContextServices.getAssayResultService() : assayResultService;
	}
	
	public BiosampleService getBiosampleService() {
		return biosampleService == null ? ContextServices.getBiosampleService() : biosampleService;
	}
	
	public AssignmentService getAssignmentService() {
		return assignmentService == null ? ContextServices.getAssignmentService() : assignmentService;
	}
	
	public NamedTreatmentService getNamedTreatmentService() {
		return namedTreatmentService == null ? ContextServices.getNamedTreatmentService() : namedTreatmentService;
	}

	public AdministrationService getAdministrationService() {
		return administrationService == null ? ContextServices.getAdministrationService() : administrationService;
	}
	
	public GroupService getGroupService() {
		return groupService == null ? ContextServices.getGroupService() : groupService;
	}
}

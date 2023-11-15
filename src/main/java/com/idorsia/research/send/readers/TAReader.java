package com.idorsia.research.send.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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

import com.idorsia.research.send.ContextServices;
import com.idorsia.research.send.domain.EtcdElement;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.domain.TrialArms;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.spirit.core.dto.AdministrationDto;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.service.AdministrationService;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.AssignmentService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.NamedTreatmentService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("taReader")
@Scope("step")
public class TAReader implements ItemReader<GenericDomain<TrialArms>> {

	private StudyDto study;
	private StepExecution stepExecution;
	private Map<String, EtcdElement> etcdMap;
	private static final String TADONE = "tadone";
	private StudyService studyService;
	private AssayResultService assayResultService;
	private BiosampleService biosampleService;
	private AssignmentService assignmentService;
	private NamedTreatmentService namedTreatmentService;
	private AdministrationService administrationService;

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
	}

	@BeforeStep
	@SuppressWarnings("unchecked")
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
		this.etcdMap = (Map<String, EtcdElement>) stepExecution.getJobExecution().getExecutionContext().get("etcdMap");
	}

	@Override
	public GenericDomain<TrialArms> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(TADONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(TADONE)) {
			return null;
		}
		System.out.println("Calling TA READER");
		List<TrialArms> taList = getTrialArms();
		stepExecution.getJobExecution().getExecutionContext().put(TADONE, true);
		return new GenericDomain<TrialArms>(taList);
	}

	private List<TrialArms> getTrialArms() {
		List<TrialArms> taList = new ArrayList<TrialArms>();
		
		List<BiosampleDto> biosamples = getStudyService().getSubjectsSorted(study);
		Map<Integer, Set<String>> treatmentsAdministratedByStage = getTreatmentsByStage(biosamples);
		int idx = 0;
		for(Entry<String, EtcdElement> entry : etcdMap.entrySet()) {
			idx += 1;
			for(String stageName : treatmentsAdministratedByStage.get(entry.getValue().getNamedTreatmentId())) {				
				TrialArms ta = new TrialArms();
				ta.setStudyId(study.getStudyId());
				ta.setDomain(Constants.DOMAIN_TA);
				ta.setArmCd(String.valueOf(entry.getValue().getSeSeq()));
				ta.setArm(entry.getValue().getNamedTreatmentName());
				ta.setTaEtOrd(String.valueOf(idx));
				ta.setEtCd(entry.getValue().getEtcd());
				ta.setElement(entry.getValue().getElement());
				ta.setTaBranch("");
				ta.setEpoch(stageName);
				
				taList.add(ta);
			}
		}
		taList.sort(Comparator.comparing(TrialArms::getArm));

		return taList;
	}

	private Map<Integer, Set<String>> getTreatmentsByStage(List<BiosampleDto> biosamples) {
		Map<Integer, Set<String>> treatmentsAdministratedByStage = new HashMap<Integer, Set<String>>();
		for(BiosampleDto biosample : biosamples) {
			List<AdministrationDto> administrations = getAdministrationService().map(getAdministrationService().getByBiosample(biosample.getId()));
			for(AdministrationDto adm : administrations) {
				if(treatmentsAdministratedByStage.get(adm.getNamedTreatment().getId())!=null) {
					treatmentsAdministratedByStage.get(adm.getNamedTreatment().getId()).add(adm.getPhase().getStage().getName());
				}else {
					Set<String> stages = new HashSet<String>();
					stages.add(adm.getPhase().getStage().getName());
					treatmentsAdministratedByStage.put(adm.getNamedTreatment().getId(), stages);
				}
			}
		}
		return treatmentsAdministratedByStage;
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
}

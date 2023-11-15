package com.idorsia.research.send.readers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.idorsia.research.send.domain.EtcdElement;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.domain.SubjectElements;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.spirit.core.dto.AdministrationDto;
import com.idorsia.research.spirit.core.dto.AssignmentDto;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.model.NamedTreatment;
import com.idorsia.research.spirit.core.service.AdministrationService;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.AssignmentService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.NamedTreatmentService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("seReader")
@Scope("step")
public class SEReader implements ItemReader<GenericDomain<SubjectElements>> {

	private StudyDto study;
	private StepExecution stepExecution;
	private SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT, Locale.ENGLISH);
	private static final String SEDONE = "sedone";
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
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
	}

	@Override
	public GenericDomain<SubjectElements> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(SEDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(SEDONE)) {
			return null;
		}
		System.out.println("Calling SE READER");
		List<SubjectElements> seList = getSubjectElements();
		stepExecution.getJobExecution().getExecutionContext().put(SEDONE, true);
		return new GenericDomain<SubjectElements>(seList);
	}

	private List<SubjectElements> getSubjectElements() {
		List<SubjectElements> seList = new ArrayList<SubjectElements>();
		try {
			List<BiosampleDto> biosamples = getStudyService().getSubjectsSorted(study);
			Map<Integer,Set<EtcdElement>> treatmentNames = getTreatmentNames(biosamples);
			for (BiosampleDto biosample : biosamples) {
				if(treatmentNames.get(biosample.getId()) != null) {					
					for(EtcdElement etcdElement : treatmentNames.get(biosample.getId())) {
						SubjectElements se = new SubjectElements();
						se.setStudyId(study.getStudyId());
						se.setDomain(Constants.DOMAIN_SE);
						se.setUsubjId(biosample.getSampleId());
						AssignmentDto asn = getBiosampleService().getFirstAssignment(biosample, study);
						Date entryDate = getAssignmentService().getFirstDate(asn, null);
						se.setSestDtc(entryDate);
						se.setSestDtcAsString(sdf.format(entryDate));
						Date lastDate = getBiosampleService().getLastDate(biosample, study);
						se.setSeenDtc(lastDate);
						se.setEtCd(etcdElement.getElement());
						se.setSeenDtcAsString(sdf.format(lastDate));
						se.setEtcdElement(etcdElement);
						seList.add(se);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		seList.sort(Comparator.comparing(SubjectElements::getUsubjId).thenComparing(SubjectElements::getSestDtc));

		return seList;
	}

	private Map<Integer,Set<EtcdElement>> getTreatmentNames(List<BiosampleDto> biosamples) {
		Map<Integer, EtcdElement> elementMap = new HashMap<Integer, EtcdElement>();
		List<NamedTreatment> treatments = getNamedTreatmentService().getNamedTreatmentsByStudy(study.getId());
		for(NamedTreatment trt : treatments) {
			String element;
			if(trt.getPpgTreatmentInstanceId() != null) {
				PpgTreatment ppgTrt = getNamedTreatmentService().getTreatment(trt.getPpgTreatmentInstanceId());
				String compoundName = ppgTrt.getCompounds().isEmpty() ? "" : ppgTrt.getCompounds().get(0).getActNumber();
				element = trt.getName() + " " + compoundName;
			}else if(trt.getSpiTreatmentId() != null) {
				SpiFormulation spiTrt = getNamedTreatmentService().getSpiFormulation(trt.getSpiTreatmentId());
				String compoundName = spiTrt.getFormulation_compounds().isEmpty() ? "" : spiTrt.getFormulation_compounds().get(0).getActive_cmpd().getName();
				element = trt.getName() + " " + compoundName;
			}else {
				element = trt.getName();
			}
			elementMap.put(trt.getId(), new EtcdElement(trt.getId(), trt.getName(), null, element));
		}
		
		Map<Integer,Set<EtcdElement>> res = new HashMap<Integer, Set<EtcdElement>>();		
		for (BiosampleDto biosample : biosamples) {
			List<AdministrationDto> administrations = getAdministrationService().map(getAdministrationService().getByBiosample(biosample.getId()));
			for(AdministrationDto adm : administrations) {
				if(res.get(biosample.getId())!=null) {
					res.get(biosample.getId()).add(elementMap.get(adm.getNamedTreatment().getId()));
				}else {
					Set<EtcdElement> treatmentNames = new HashSet<EtcdElement>();
					treatmentNames.add(elementMap.get(adm.getNamedTreatment().getId()));
					res.put(biosample.getId(), treatmentNames);
				}
			}
		}
		return res;
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

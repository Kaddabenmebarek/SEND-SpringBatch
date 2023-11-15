package com.idorsia.research.send.readers;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
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
import com.idorsia.research.send.domain.TrialElements;
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

@Component("teReader")
@Scope("step")
public class TEReader implements ItemReader<GenericDomain<TrialElements>> {

	private StudyDto study;
	private StepExecution stepExecution;
	private Map<String, EtcdElement> etcdMap;
	private static final String TEDONE = "tedone";
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
	public GenericDomain<TrialElements> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(TEDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(TEDONE)) {
			return null;
		}
		System.out.println("Calling TE READER");
		List<TrialElements> teList = getTrialElements();
		stepExecution.getJobExecution().getExecutionContext().put(TEDONE, true);
		return new GenericDomain<TrialElements>(teList);
	}

	private List<TrialElements> getTrialElements() {
		List<TrialElements> teList = new ArrayList<TrialElements>();
		List<BiosampleDto> biosamples = getStudyService().getSubjectsSorted(study);
		Map<String,Set<AdministrationDto>> allAdministrationsByTreatment = getAdministrationByTreatment(biosamples);
		Map<String,Duration> administrationsDurationMap = getAdministrationsDurationMap(allAdministrationsByTreatment);
		
		for(Entry<String, EtcdElement> entry : etcdMap.entrySet()) {
			TrialElements te = new TrialElements();
			te.setStudyId(study.getStudyId());
			te.setDomain(Constants.DOMAIN_TE);
			te.seteTcd(entry.getValue().getEtcd());
			te.setElement(entry.getValue().getElement());
			Duration administrationDuration = administrationsDurationMap.get(entry.getKey());
			te.setTeDur("P" + administrationDuration.toDays() + "D");
			teList.add(te);
		}
		teList.sort(Comparator.comparing(TrialElements::geteTcd));

		return teList;
	}

	private Map<String, Duration> getAdministrationsDurationMap(
			Map<String, Set<AdministrationDto>> allAdministrationsByTreatment) {
		Map<String, Duration> administrationsDurationMap = new HashMap<String, Duration>();
		for(Entry<String, Set<AdministrationDto>> entry : allAdministrationsByTreatment.entrySet()) {
			List<AdministrationDto> admList = sortAdministrations(entry);
			AdministrationDto firstAdm =  admList.get(0);
			AdministrationDto lastAdm =  admList.get(admList.size()-1);
			Duration duration = lastAdm.getPhase().getPhase().minus(firstAdm.getPhase().getPhase());
			administrationsDurationMap.put(entry.getKey(), duration);
		}
		return administrationsDurationMap;
	}

	private Map<String,Set<AdministrationDto>> getAdministrationByTreatment(List<BiosampleDto> biosamples) {
		Map<String,Set<AdministrationDto>> res = new HashMap<String,Set<AdministrationDto>>();
		for (BiosampleDto biosample : biosamples) {
			List<AdministrationDto> administrations = getAdministrationService().map(getAdministrationService().getByBiosample(biosample.getId()));
			for(AdministrationDto adm : administrations) {	
				if(res.get(adm.getNamedTreatment().getName())!=null) {
					res.get(adm.getNamedTreatment().getName()).add(adm);
				}else {
					Set<AdministrationDto> administrationSet = new HashSet<AdministrationDto>();
					administrationSet.add(adm);
					res.put(adm.getNamedTreatment().getName(), administrationSet);
				}
			}
		}
		return res;
	}

	private List<AdministrationDto> sortAdministrations(Entry<String, Set<AdministrationDto>> entry) {
		List<AdministrationDto> admList = new ArrayList<AdministrationDto>(entry.getValue());
		Collections.sort(admList, new Comparator<AdministrationDto>() {
			@Override
			public int compare(AdministrationDto a1, AdministrationDto a2) {
				Long durA = a1.getPhase().getPhase().toDays();
				Long durB = a2.getPhase().getPhase().toDays();
				return durA.compareTo(durB);
			}
		});
		return admList;
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

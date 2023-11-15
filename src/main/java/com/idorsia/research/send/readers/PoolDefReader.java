package com.idorsia.research.send.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.domain.PoolDefinition;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.service.BiosampleEnclosureService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.EnclosureService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("pooldefReader")
@Scope("step")
public class PoolDefReader implements ItemReader<GenericDomain<PoolDefinition>> {

	private StudyDto study;
	private StepExecution stepExecution;
	private Map<String, String> biosamplePoolMap;
	private static final String POOLDEFDONE = "pooldefdone";

	private EnclosureService enclosureService;
	private StudyService studyService;
	private BiosampleService biosampleService;
	private BiosampleEnclosureService biosampleEnclosureService;

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
	}

	@BeforeStep
	@SuppressWarnings("unchecked")
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
		this.biosamplePoolMap = (Map<String, String>) stepExecution.getJobExecution().getExecutionContext().get("biosamplePoolMap");
	}

	@Override
	public GenericDomain<PoolDefinition> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(POOLDEFDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(POOLDEFDONE)) {
			return null;
		}
		System.out.println("Calling POOLDEF READER");

		List<PoolDefinition> pooldefList = processPooldef();
		pooldefList.sort(Comparator.comparing(PoolDefinition::getUsubjId).thenComparing(PoolDefinition::getPoolId));
		
		stepExecution.getJobExecution().getExecutionContext().put(POOLDEFDONE, true);
		return new GenericDomain<PoolDefinition>(pooldefList);
	}

	private List<PoolDefinition> processPooldef() throws Exception {
		List<PoolDefinition> res = new ArrayList<PoolDefinition>();
		List<BiosampleDto> biosamples = getStudyService().getBiosamples(study);
		for (BiosampleDto b : biosamples) {
			if(biosamplePoolMap.get(b.getSampleId()) == null) continue;
			PoolDefinition pooldef = new PoolDefinition();
			pooldef.setStudyId(study.getStudyId());
			pooldef.setUsubjId(b.getSampleId());
			pooldef.setPoolId(biosamplePoolMap.get(b.getSampleId()));
			res.add(pooldef);
		}
		return res;
	}


	public EnclosureService getEnclosureService() {
		return enclosureService == null ? ContextServices.getEnclosureService() : enclosureService;
	}

	public StudyService getStudyService() {
		return studyService == null ? ContextServices.getStudyService() : studyService;
	}

	public BiosampleService getBiosampleService() {
		return biosampleService == null ? ContextServices.getBiosampleService() : biosampleService;
	}

	public BiosampleEnclosureService getBiosampleEnclosureService() {
		return biosampleEnclosureService == null ? ContextServices.getBiosampleEnclosureService()
				: biosampleEnclosureService;
	}

}

package com.idorsia.research.send.readers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
import com.idorsia.research.send.SendService;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.domain.OrganMeasurements;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.send.processors.DataUtils;
import com.idorsia.research.spirit.core.dto.AssayResultDto;
import com.idorsia.research.spirit.core.dto.AssayResultValueDto;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.PhaseDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.PhaseService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("omReader")
@Scope("step")
public class OMReader implements ItemReader<GenericDomain<OrganMeasurements>> {

	private StudyDto study;
	private StepExecution stepExecution;
	private static final String OMDONE = "omdone";
	private String ctversion;

	private StudyService studyService;
	private BiosampleService biosampleService;
	private AssayResultService assayResultService;
	private PhaseService phaseService;

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
		this.ctversion = (String) stepExecution.getJobExecution().getExecutionContext().get(Constants.CTVERSION);
	}

	@Override
	public GenericDomain<OrganMeasurements> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(OMDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(OMDONE)) {
			return null;
		}
		System.out.println("Calling OM READER");
		List<OrganMeasurements> omList = getOrganMeasurements();
		stepExecution.getJobExecution().getExecutionContext().put(OMDONE, true);
		return new GenericDomain<OrganMeasurements>(omList);
	}

	private List<OrganMeasurements> getOrganMeasurements() {
		List<OrganMeasurements> omList = new LinkedList<OrganMeasurements>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT, Locale.ENGLISH);
			List<String> organsFromCT = SendService.getTerminologyByCode(ctversion, "SPEC");
			List<String> omTestCDList = SendService.getTerminologyByCode(ctversion, "OMTESTCD");
			List<String> omTestList = SendService.getTerminologyByCode(ctversion, "OMTEST");
			Set<BiosampleDto> allSamples = new HashSet<>();

			for (BiosampleDto topAnimal : getStudyService().getSubjectsSorted(study)) {
				allSamples.addAll(getBiosampleService().getSamplesFromStudyDesign(topAnimal, study, true));
			}
			/*getAssayResultService().attachOrCreateStudyResultsToTops(study, getStudyService().getSubjectsSorted(study),
					null, null);
			getAssayResultService().attachOrCreateStudyResultsToSamplesAtDeath(study, allSamples, null, null, null);*/

			for (BiosampleDto biosample : allSamples) {

				if (!"Organ".equals(biosample.getBiotype().getName()))
					continue;
				for (AssayResultDto result : biosample.getResults()) {
					if (com.idorsia.research.spirit.core.constants.Constants.WEIGHING_TESTNAME
							.equals(result.getAssay().getName())) {
						for (AssayResultValueDto val : result.getValues()) {
							OrganMeasurements om = new OrganMeasurements();
							om.setStudyId(study.getStudyId());
							om.setDomain(Constants.DOMAIN_OM);
							om.setUsubjId(biosample.getSampleId());// context is the organ not the animal
							om.setOmTest(DataUtils.getTerminologyCandidate("weight", omTestList));
							om.setOmTestcd(DataUtils.getTerminologyCandidate("omtestcd", omTestCDList));
							BigDecimal res = val.getTextValue() != null
									? new BigDecimal(val.getTextValue()).setScale(1, RoundingMode.HALF_UP)
									: null;
							om.setOmOrres(res);
							om.setOmStresc(res);
							om.setOmStresn(res);
							om.setOmOrresu(Constants.BWUNIT);
							om.setOmStresu(Constants.BWUNIT);
							om.setOmStat(res == null ? Constants.ND : "");
							PhaseDto phase = getAssayResultService().getInheritedPhase(result);
							Date resultDate = getBiosampleService().getDate(biosample, phase, true);
							om.setOmDtc(DataUtils.toTimestamp(resultDate));
							om.setOmSDtc(sdf.format(resultDate));
							Long dy = Long.valueOf(getPhaseService().getDays(phase, true));
							om.setOmDy(dy);
							om.setOmNomdy(dy);
							om.setOmSpec(DataUtils.getTerminologyCandidate(biosample.getLocalId(), organsFromCT));
							omList.add(om);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		omList.sort(Comparator.comparing(OrganMeasurements::getUsubjId).thenComparing(OrganMeasurements::getOmSDtc));
		return omList;
	}

	public StudyService getStudyService() {
		return studyService == null ? ContextServices.getStudyService() : studyService;
	}

	public BiosampleService getBiosampleService() {
		return biosampleService == null ? ContextServices.getBiosampleService() : biosampleService;
	}

	public AssayResultService getAssayResultService() {
		return assayResultService == null ? ContextServices.getAssayResultService() : assayResultService;
	}

	public PhaseService getPhaseService() {
		return phaseService == null ? ContextServices.getPhaseService() : phaseService;
	}

}

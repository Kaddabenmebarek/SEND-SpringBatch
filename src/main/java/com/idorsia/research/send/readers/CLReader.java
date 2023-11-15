package com.idorsia.research.send.readers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import com.idorsia.research.send.SendService;
import com.idorsia.research.send.domain.ClinicalSigns;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.send.processors.DataUtils;
import com.idorsia.research.spirit.core.dto.AssayResultDto;
import com.idorsia.research.spirit.core.dto.AssayResultValueDto;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.PhaseDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("clReader")
@Scope("step")
public class CLReader implements ItemReader<GenericDomain<ClinicalSigns>> {

	private StudyDto study;
	private StudyService studyService;
	private AssayResultService assayResultService;
	private BiosampleService biosampleService;
	private StepExecution stepExecution;
	private static final String CLDONE = "cldone";
	private String ctversion;

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
	public GenericDomain<ClinicalSigns> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(CLDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(CLDONE)) {
			return null;
		}
		System.out.println("Calling CL READER");
		List<ClinicalSigns> csList = getDataFromSpiritCore();
		stepExecution.getJobExecution().getExecutionContext().put(CLDONE, true);
		return new GenericDomain<ClinicalSigns>(csList);
	}

	private List<ClinicalSigns> getDataFromSpiritCore() {
		List<ClinicalSigns> csList = new ArrayList<ClinicalSigns>();
		try {
			List<BiosampleDto> biosamples = getStudyService().getSubjectsSorted(study);
			List<String> clCats = SendService.getTerminologyByCode(ctversion, "CLCAT");
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT4, Locale.ENGLISH);
			Map<String, Integer> cltpNumMap = new HashMap<String, Integer>();
			for (BiosampleDto biosample : biosamples) {
				for (AssayResultDto result : biosample.getResults()) {
					if (com.idorsia.research.spirit.core.constants.Constants.CLINICAL_SIGN_TESTNAME
							.equals(result.getAssay().getName())) {
						ClinicalSigns cs = new ClinicalSigns();
						cs.setStudyId(study.getStudyId());
						cs.setDomain(Constants.DOMAIN_CL);
						cs.setUsubjId(biosample.getSampleId());
						cs.setClTest(Constants.CLTEST);
						cs.setClTestCd(Constants.CLTESTCD);
						cs.setClCat(DataUtils.getTerminologyCandidate("SIGNS", clCats));
						for (AssayResultValueDto val : result.getValues()) {
							String attribute = val.getAssayAttribute().getName();
							switch (attribute) {
							case "Observation":
								cs.setClOrres(val.getTextValue());
								cs.setClStresc(val.getTextValue());
								if ("Not recorded".equals(val.getTextValue())) {
									cs.setClstat(Constants.ND);
								} else {
									cs.setClstat("");
								}
								break;
							case "Localisation":
								cs.setClLoc(val.getTextValue());
								break;
							case "Timepoint":
								cs.setClTpt(val.getTextValue());
								if (cltpNumMap.get(val.getTextValue()) == null) {
									cltpNumMap.put(val.getTextValue(), cltpNumMap.size() + 1);
								}
								cs.setClTptnum(cltpNumMap.get(val.getTextValue()));
								break;
							default:
								break;
							}
						}
						PhaseDto phase = getAssayResultService().getInheritedPhase(result);
						Date resultDate = getBiosampleService().getDate(biosample, phase);
						cs.setClDtc(DataUtils.toTimestamp(resultDate));
						cs.setsClDtc(sdf.format(resultDate));
						long dy = result.getPhase().getPhase().toDays();
						cs.setClDy(dy);
						cs.setClNomdy(String.valueOf(dy));
						cs.setVisitdy(dy);
						csList.add(cs);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		csList.sort(Comparator.comparing(ClinicalSigns::getUsubjId).thenComparing(ClinicalSigns::getsClDtc));
		return csList;
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
}

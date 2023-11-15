package com.idorsia.research.send.readers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
import com.idorsia.research.send.domain.PalpableMasses;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.send.processors.DataUtils;
import com.idorsia.research.spirit.core.dto.AssayAttributeDto;
import com.idorsia.research.spirit.core.dto.AssayResultDto;
import com.idorsia.research.spirit.core.dto.AssayResultValueDto;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.PhaseDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("pmReader")
@Scope("step")
public class PMReader implements ItemReader<GenericDomain<PalpableMasses>> {

	private StudyDto study;
	private StudyService studyService;
	private AssayResultService assayResultService;
	private BiosampleService biosampleService;
	private StepExecution stepExecution;
	private static final String PMDONE = "pmdone";
	private String ctversion;
	List<String> volumeAttributeNames = Arrays.asList("Total volume", "Volume left", "Volume right", "Volume");

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
	public GenericDomain<PalpableMasses> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(PMDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(PMDONE)) {
			return null;
		}
		System.out.println("Calling PM READER");
		List<PalpableMasses> pmList = getDataFromSpiritCore();
		stepExecution.getJobExecution().getExecutionContext().put(PMDONE, true);
		return new GenericDomain<PalpableMasses>(pmList);
	}

	private List<PalpableMasses> getDataFromSpiritCore() {
		List<PalpableMasses> pmList = new LinkedList<PalpableMasses>();
		try {
			List<BiosampleDto> biosamples = getStudyService().getSubjectsSorted(study);
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT4, Locale.ENGLISH);
			List<String> phsprpcdList = SendService.getTerminologyByCode(ctversion, "PHSPRPCD");
			List<String> phsprpList = SendService.getTerminologyByCode(ctversion, "PHSPRP");

			for (BiosampleDto biosample : biosamples) {
				for (AssayResultDto result : biosample.getResults()) {
					if (com.idorsia.research.spirit.core.constants.Constants.TUMOR_SIZE_TESTNAME
							.equals(result.getAssay().getName())
							|| com.idorsia.research.spirit.core.constants.Constants.TUMOR_SIZE2_TESTNAME
									.equals(result.getAssay().getName())) {
						for (AssayResultValueDto val : result.getValues()) {
							if (volumeAttributeNames.contains(val.getAssayAttribute().getName()))
								continue;
							boolean isLenght = val.getAssayAttribute().getName().contains("Length");
							PalpableMasses pm = new PalpableMasses();
							pm.setStudyId(study.getStudyId());
							pm.setDomain(Constants.DOMAIN_PM);
							pm.setUsubjId(biosample.getSampleId());
							String pmTest = isLenght ? DataUtils.getTerminologyCandidate("length", phsprpList)
									: DataUtils.getTerminologyCandidate("width", phsprpList);
							pm.setPmTest(pmTest);
							String pmTestCd = isLenght ? DataUtils.getTerminologyCandidate("length", phsprpcdList)
									: DataUtils.getTerminologyCandidate("width", phsprpcdList);
							pm.setPmTestcd(pmTestCd);
							BigDecimal res = val.getTextValue() != null
									? new BigDecimal(val.getTextValue()).setScale(1, RoundingMode.HALF_UP)
									: null;
							pm.setPmOrres(res);
							pm.setPmStresc(res);
							pm.setPmStresn(res);
							pm.setPmOrresu(Constants.MILIMETER);
							pm.setPmStresu(Constants.MILIMETER);
							pm.setPmStat(res == null ? Constants.ND : "");
							PhaseDto phase = getAssayResultService().getInheritedPhase(result);
							Date resultDate = getBiosampleService().getDate(biosample, phase);
							pm.setPmDtc(DataUtils.toTimestamp(resultDate));
							pm.setsPmDtc(sdf.format(resultDate));
							long dy = result.getPhase().getPhase().toDays();
							pm.setPmDy(dy);
							pm.setPmNomDy(dy);
							pm.setPmSpId(Constants.MASS_PREFX + biosample.getSampleId());
							getPmsIdLoc(pm, val.getAssayAttribute(), biosample.getSampleId());
							pmList.add(pm);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pmList.sort(Comparator.comparing(PalpableMasses::getUsubjId).thenComparing(PalpableMasses::getsPmDtc));
		return pmList;
	}

	private void getPmsIdLoc(PalpableMasses pm, AssayAttributeDto attribute, String sampleId) {
		if (attribute.getName().contains("right")) {
			pm.setPmSpId(Constants.MASS_PREFX + "01 " + sampleId);
			pm.setPmLoc("Right body side");
		} else if (attribute.getName().contains("left")) {
			pm.setPmSpId(Constants.MASS_PREFX + "02 " + sampleId);
			pm.setPmLoc("Left body side");
		} else {
			pm.setPmSpId(Constants.MASS_PREFX + sampleId);
			pm.setPmLoc("");
		}
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

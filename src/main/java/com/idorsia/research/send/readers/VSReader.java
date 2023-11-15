package com.idorsia.research.send.readers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.domain.VitalSigns;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.send.processors.DataUtils;
import com.idorsia.research.spirit.core.dto.AssayResultDto;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.PhaseDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("vsReader")
@Scope("step")
public class VSReader implements ItemReader<GenericDomain<VitalSigns>> {

	private StudyDto study;
	private StepExecution stepExecution;
	private static final String VSDONE = "vsdone";
	private StudyService studyService;
	private AssayResultService assayResultService;
	private BiosampleService biosampleService;

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
	}

	@Override
	public GenericDomain<VitalSigns> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(VSDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(VSDONE)) {
			return null;
		}
		System.out.println("Calling VS READER");
		List<VitalSigns> vsList = getVitalSigns();
		stepExecution.getJobExecution().getExecutionContext().put(VSDONE, true);
		return new GenericDomain<VitalSigns>(vsList);
	}

	private List<VitalSigns> getVitalSigns() {
		List<VitalSigns> vsList = new ArrayList<VitalSigns>();
		try {
			List<BiosampleDto> biosamples = getStudyService().getSubjectsSorted(study);
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT, Locale.ENGLISH);
			for (BiosampleDto biosample : biosamples) {
				for (AssayResultDto result : biosample.getResults()) {
					if (Constants.VSTEST.equals(result.getAssay().getName())) {
						VitalSigns vs = new VitalSigns();
						vs.setStudyId(study.getStudyId());
						vs.setDomain(Constants.DOMAIN_VS);
						vs.setUsubjId(biosample.getSampleId());
						vs.setVsTest(Constants.VSTEST);
						vs.setVsTestcd(Constants.VSTESTCD);
						BigDecimal res = result.getValues().get(0) != null
								? new BigDecimal(result.getValues().get(0).getTextValue()).setScale(1,
										RoundingMode.HALF_UP)
								: null;
						vs.setVsOrres(res);
						vs.setVsStresc(res);
						vs.setVsStresn(res);
						vs.setVsOrresu(Constants.CELSIUS);
						vs.setVsStresu(Constants.CELSIUS);
						vs.setVsStat(res == null ? Constants.ND : "");
						PhaseDto phase = getAssayResultService().getInheritedPhase(result);
						Date resultDate = getBiosampleService().getDate(biosample, phase);
						vs.setVsDtc(DataUtils.toTimestamp(resultDate));
						vs.setsVsDtc(sdf.format(resultDate));
						long dy = result.getPhase().getPhase().toDays();
						vs.setVsDy(dy);
						vs.setVsNomDy(String.valueOf(dy));
						vs.setVsBlFl("");
						vsList.add(vs);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		vsList.sort(Comparator.comparing(VitalSigns::getUsubjId).thenComparing(VitalSigns::getsVsDtc));

		return vsList;
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

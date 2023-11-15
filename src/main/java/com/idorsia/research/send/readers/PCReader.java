package com.idorsia.research.send.readers;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
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
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.domain.PharmacokineticsConcentrations;
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

@Component("pcReader")
@Scope("step")
public class PCReader implements ItemReader<GenericDomain<PharmacokineticsConcentrations>> {

	private String studyId;
	private StudyDto study;
	private StepExecution stepExecution;
	private static final String PCDONE = "pcdone";
	private StudyService studyService;
	private BiosampleService biosampleService;
	private AssayResultService assayResultService;
	private PhaseService phaseService;

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
		stepExecution.getJobExecution().getExecutionContext().remove(Constants.STUDY);
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
		this.studyId = stepExecution.getJobParameters().getString(Constants.STUDYID);
	}

	@Override
	public GenericDomain<PharmacokineticsConcentrations> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		this.study = getStudyService().getStudyDtoByStudyId(studyId);
		if (stepExecution.getJobExecution().getExecutionContext().get(PCDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(PCDONE)) {
			return null;
		}
		System.out.println("Calling PC READER");
		List<PharmacokineticsConcentrations> pcList = getPharmacokineticsConcentrations();
		stepExecution.getJobExecution().getExecutionContext().put(PCDONE, true);
		return new GenericDomain<PharmacokineticsConcentrations>(pcList);
	}

	private List<PharmacokineticsConcentrations> getPharmacokineticsConcentrations() {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT4, Locale.ENGLISH);
		List<PharmacokineticsConcentrations> pcList = new ArrayList<PharmacokineticsConcentrations>();
		try {
			Set<BiosampleDto> allSamples = new HashSet<>();
			List<BiosampleDto> subjects = getStudyService().getSubjectsSorted(study);
			for (BiosampleDto topAnimal : subjects) {
				allSamples.addAll(getBiosampleService().getSamplesFromStudyDesign(topAnimal, study, true));
			}
			for (BiosampleDto biosample : allSamples) {
				if (!"Plasma".equals(biosample.getBiotype().getName()))
					continue;
				for (AssayResultDto result : biosample.getResults()) {
					if (com.idorsia.research.spirit.core.constants.Constants.PKBIOANALYSISTESTNAME.equals(result.getAssay().getName())) {
						PharmacokineticsConcentrations pc = new PharmacokineticsConcentrations();
						pc.setStudyId(study.getStudyId());
						pc.setDomain(Constants.DOMAIN_PC);
						pc.setUsubjId(biosample.getSampleId());
						for(AssayResultValueDto val :  result.getValues()) {
							String attributeName = val.getAssayAttribute().getName();
							switch (attributeName) {
							case "Compound":
								String coumpound = val.getTextValue(); 
								pc.setPcTest(coumpound);
								String coumpoundShortName = val.getTextValue().split("-")[val.getTextValue().split("-").length-1];
								pc.setPcTestcd(coumpoundShortName);
								break;
							case "Concentration":
								BigDecimal res = val.getTextValue() != null ? new BigDecimal(val.getTextValue()) : null;
								pc.setPcOrres(res);
								pc.setPcStresc(res);
								pc.setPcStresn(res);
								pc.setPcStat(res == null ? Constants.ND : "");
								break;
							case "Concentration Unit":
								String unit = val.getTextValue();
								pc.setPcOrresu(unit);
								pc.setPcStresu(unit);
								break;
							default:
								break;
							}
							
						}
						pc.setPcCat(Constants.PCCAT);
						pc.setPcNam(Constants.IDORSIA_LOC);
						pc.setPcSpec(biosample.getBiotype().getName().toUpperCase());
						pc.setPcBlFl("");
						pc.setPcLLoq("");
						PhaseDto phase = getAssayResultService().getInheritedPhase(result);
						Date resultDate = getBiosampleService().getDate(biosample, phase);
						pc.setPcDtc(DataUtils.toTimestamp(resultDate));
						pc.setsPcDtc(sdf.format(resultDate));
						Long dy = Long.valueOf(getPhaseService().getDays(phase, true));
						pc.setPcDy(dy);
						pc.setPcNomdy(String.valueOf(dy));
						pcList.add(pc);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pcList.sort(Comparator.comparing(PharmacokineticsConcentrations::getUsubjId).thenComparing(PharmacokineticsConcentrations::getsPcDtc));
		return pcList;
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

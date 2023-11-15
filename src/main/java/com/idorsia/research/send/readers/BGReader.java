package com.idorsia.research.send.readers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.idorsia.research.send.domain.BodyWeightGain;
import com.idorsia.research.send.domain.BodyWeights;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("bgReader")
@Scope("step")
public class BGReader implements ItemReader<GenericDomain<BodyWeightGain>> {

	private StudyDto study;
	private StudyService studyService;
	private AssayResultService assayResultService;
	private StepExecution stepExecution;
	private static final String BGDONE = "bgdone";
	private List<BodyWeights> bodyWeightList;

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
	}

	@BeforeStep
	@SuppressWarnings("unchecked")
	public void beforeStep(StepExecution stepExecution) {
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
		this.stepExecution = stepExecution;
		this.bodyWeightList = (List<BodyWeights>) stepExecution.getJobExecution().getExecutionContext().get("bodyWeightList");
	}

	@Override
	public GenericDomain<BodyWeightGain> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (stepExecution.getJobExecution().getExecutionContext().get(BGDONE) != null
				&& (boolean) stepExecution.getJobExecution().getExecutionContext().get(BGDONE)) {
			return null;
		}
		System.out.println("Calling BG READER");

		Map<String, List<BodyWeights>> bwMap = getBWMap();
		List<BodyWeightGain> bgList = getBodyGains(bwMap);

		stepExecution.getJobExecution().getExecutionContext().put(BGDONE, true);
		return new GenericDomain<BodyWeightGain>(bgList);
	}

	private Map<String, List<BodyWeights>> getBWMap() {
		Map<String, List<BodyWeights>> bwMap = new LinkedHashMap<String, List<BodyWeights>>();
		for (BodyWeights bw : bodyWeightList) {
			if (bwMap.get(bw.getUsubjId()) != null) {
				bwMap.get(bw.getUsubjId()).add(bw);
			} else {
				List<BodyWeights> bwList = new LinkedList<BodyWeights>();
				bwList.add(bw);
				bwMap.put(bw.getUsubjId(), bwList);
			}
		}
		return bwMap;
	}

	private List<BodyWeightGain> getBodyGains(Map<String, List<BodyWeights>> bwMap) {
		List<BodyWeightGain> bgList = new ArrayList<BodyWeightGain>();
		for (Entry<String, List<BodyWeights>> entry : bwMap.entrySet()) {
			BodyWeightGain bg = new BodyWeightGain();
			bg.setStudyId(study.getStudyId());
			bg.setDomain(Constants.DOMAIN_BG);
			bg.setUsubjId(entry.getKey());
			bg.setBgOrresu(Constants.BWUNIT);
			bg.setBgTest(Constants.BGTEST);
			bg.setBgTestCd(Constants.BGTESTCD);
			bg.setBgStresu(Constants.BWUNIT);
			BodyWeights firstBw = entry.getValue().get(0);
			BodyWeights lastBw = entry.getValue().get(entry.getValue().size() - 1);
			Double firstW = firstBw.getBwOrres() != null ? Double.valueOf(firstBw.getBwOrres()) : 0;
			Double lastW = lastBw.getBwOrres() != null ? Double.valueOf(lastBw.getBwOrres()) : 0;
			BigDecimal gain = new BigDecimal(lastW - firstW).setScale(1, RoundingMode.HALF_UP);
			bg.setBgOrres(gain);
			bg.setBgStresc(gain.toString());
			bg.setBgStresn(gain.toString());
			bg.setBgEndy(lastBw.getBwDy());
			bg.setBgDy(firstBw.getBwDy());
			bg.setSbgDtc(firstBw.getSbwDtc());
			bg.setBgDtc(firstBw.getBwDtc());
			bg.setSbgEnDtc(lastBw.getSbwDtc());
			bg.setBgEnDtc(lastBw.getBwDtc());
			bgList.add(bg);
		}
		return bgList;
	}

	public StudyService getStudyService() {
		return studyService == null ? ContextServices.getStudyService() : studyService;
	}

	public AssayResultService getAssayResultService() {
		return assayResultService == null ? ContextServices.getAssayResultService() : assayResultService;
	}

}

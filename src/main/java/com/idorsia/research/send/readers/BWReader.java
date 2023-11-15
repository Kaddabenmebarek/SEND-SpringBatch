package com.idorsia.research.send.readers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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
import com.idorsia.research.send.SendService;
import com.idorsia.research.send.domain.BodyWeights;
import com.idorsia.research.send.domain.GenericDomain;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.send.processors.DataUtils;
import com.idorsia.research.spirit.core.dto.AssayResultDto;
import com.idorsia.research.spirit.core.dto.BiosampleDto;
import com.idorsia.research.spirit.core.dto.PhaseDto;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("bwReader")
@Scope("step")
public class BWReader implements ItemReader<GenericDomain<BodyWeights>> {

	private String ctversion;
	private StudyDto study;
	private StudyService studyService;
	private AssayResultService assayResultService;
	private BiosampleService biosampleService;
	private StepExecution stepExecution; 
	private static final String BWDONE = "bwdone";
	private List<BodyWeights> bodyWeightList;


	@Override
	public GenericDomain<BodyWeights> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(stepExecution.getJobExecution().getExecutionContext().get(BWDONE)!=null && (boolean) stepExecution.getJobExecution().getExecutionContext().get(BWDONE)) {
			return null;
		}
		System.out.println("Calling BW READER");
		List<BodyWeights> bwList = markTerminalFindings(getDataFromSpiritCore());
		stepExecution.getJobExecution().getExecutionContext().put(BWDONE, true);
		return new GenericDomain<BodyWeights>(bwList);
	}
	
	@AfterStep
    public void afterStep(StepExecution stepExecution) throws IOException {
    	stepExecution.getJobExecution().getExecutionContext().put("bodyWeightList", bodyWeightList);
    }

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.study = (StudyDto) stepExecution.getJobExecution().getExecutionContext().get(Constants.STUDY);
		this.stepExecution = stepExecution;
		this.ctversion = (String) stepExecution.getJobExecution().getExecutionContext().get(Constants.CTVERSION);
	}


	private List<BodyWeights> getDataFromSpiritCore() {
		List<BodyWeights> bwList = new ArrayList<BodyWeights>();
		try {
			List<BiosampleDto> biosamples = getStudyService().getSubjectsSorted(study);
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT, Locale.ENGLISH);

			for (BiosampleDto biosample : biosamples) {
				for (AssayResultDto result : biosample.getResults()) {
					if (com.idorsia.research.spirit.core.constants.Constants.WEIGHING_TESTNAME
							.equals(result.getAssay().getName())) {
						BodyWeights bw = new BodyWeights();
						bw.setStudyId(study.getStudyId());
						bw.setDomain(Constants.DOMAIN_BW);
						bw.setUsubjId(biosample.getSampleId());
						bw.setBwTest(Constants.BWTEST);
						bw.setBwTestCD(Constants.BWTESTCD);
						String w = result.getValues().get(0) != null ? result.getValues().get(0).getTextValue() : "";
						bw.setBwOrres(w);
						bw.setBwSTresc(w);
						bw.setBwSTresn(w);
						bw.setBwOrresu(Constants.BWUNIT);
						bw.setBwSTresu(Constants.BWUNIT);
						bw.setBwStat("".equals(w) || w == null ? Constants.ND : "");
						
						PhaseDto phase = getAssayResultService().getInheritedPhase(result);
						Date resultDate = getBiosampleService().getDate(biosample, phase);
						if(resultDate == null) resultDate = result.getCreDate();
						bw.setBwDtc(DataUtils.toTimestamp(resultDate));
						bw.setSbwDtc(sdf.format(resultDate));
						long dy = result.getPhase().getPhase().toDays();
						bw.setBwDy(dy);
						bw.setBwnomdy(String.valueOf(dy));
						bw.setBwSBlfl("");
						bwList.add(bw);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		bwList.sort(Comparator.comparing(BodyWeights::getUsubjId).thenComparing(BodyWeights::getSbwDtc));

		return bwList;
	}

	private List<BodyWeights> markTerminalFindings(List<BodyWeights> data) {
		List<String> bwTestCdList = SendService.getTerminologyByCode(ctversion, "BWTESTCD");
		List<String> bwTestList = SendService.getTerminologyByCode(ctversion, "BWTEST");
		Map<String, List<BodyWeights>> tempBWMap = new LinkedHashMap<String, List<BodyWeights>>();
    	for(BodyWeights bw : data) {
    		if(tempBWMap.get(bw.getUsubjId())!=null) {
    			tempBWMap.get(bw.getUsubjId()).add(bw);
    		}else {
    			List<BodyWeights> tempBWList = new ArrayList<BodyWeights>();
    			tempBWList.add(bw);
    			tempBWMap.put(bw.getUsubjId(), tempBWList);
    		}
    	}
    	List<BodyWeights> finalBWList = new ArrayList<BodyWeights>();
    	for(Entry<String, List<BodyWeights>> entry : tempBWMap.entrySet()) {
    		entry.getValue().get(entry.getValue().size()-1).setBwTestCD(DataUtils.getTerminologyCandidate("TERM", bwTestCdList));
    		entry.getValue().get(entry.getValue().size()-1).setBwTest(DataUtils.getTerminologyCandidate("Terminal", bwTestList));
    		List<BodyWeights> bwList = entry.getValue();
    		finalBWList.addAll(bwList);
    	}
    	bodyWeightList = finalBWList;
		return finalBWList;
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

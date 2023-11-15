package com.idorsia.research.send.processors;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import com.idorsia.research.send.SendService;
import com.idorsia.research.send.domain.Demographics;

public class DMProcessor implements ItemProcessor<Demographics,Demographics> {
	
	private String ctversion;
	private StepExecution stepExecution;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.ctversion = (String) stepExecution.getJobExecution().getExecutionContext().get(Constants.CTVERSION);
		this.stepExecution = stepExecution;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Demographics process(Demographics item) throws Exception {
		Map<String, String> armMap = (Map<String, String>) stepExecution.getJobExecution().getExecutionContext().get("armcdMap");
		item.setArmCd(armMap.get(item.getArm()));
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT2, Locale.ENGLISH);
		if(item.getRfStDtc() != null) {
			if(item.getStudyStartingDate() != null) {
				Date studyStartDate = sdf.parse(item.getStudyStartingDate());
				if(item.getBrthDtc()!=null) {
					Date birthDate = sdf.parse(item.getBrthDtc());			 
				    long diffInMills = Math.abs(studyStartDate.getTime() - birthDate.getTime());
				    long weeks = TimeUnit.DAYS.convert(diffInMills, TimeUnit.MILLISECONDS)/7;
				    item.setAge(String.valueOf(weeks));
				}else {		 
					Date studyEndDate = sdf.parse(item.getRfEnDtc());
				    long diffInMills = Math.abs(studyEndDate.getTime() - studyStartDate.getTime());
				    long weeks = TimeUnit.DAYS.convert(diffInMills, TimeUnit.MILLISECONDS)/7;
				    item.setAge(String.valueOf(weeks));
				}
				item.setAgeU(Constants.AGE_UNIT_WEEKS);
				item.setRfStDtc(StringUtils.substringBefore(item.getStudyStartingDate(), " "));					
			}else {
				item.setRfStDtc(StringUtils.substringBefore(item.getRfStDtc(), " "));
			}
		}
		if(item.getRfEnDtc() != null) {
			item.setRfEnDtc(StringUtils.substringBefore(item.getRfEnDtc(), " "));
		}
		
		List<String> speciesList = SendService.getTerminologyByCode(ctversion, "SPECIES");
		item.setSpecies(DataUtils.getTerminologyCandidate(item.getSpecies(), speciesList));
		if(!item.getSex().equalsIgnoreCase("M") && !item.getSex().equalsIgnoreCase("F")) {
			item.setSex("U");
		}
		return item;
	}
	
}

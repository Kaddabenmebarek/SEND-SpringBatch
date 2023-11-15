package com.idorsia.research.send.writers;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.idorsia.research.send.ContextServices;
import com.idorsia.research.send.SendService;
import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.send.processors.DataUtils;
import com.idorsia.research.spirit.core.dto.StudyDto;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.StudyService;

@Component("initWriter")
@Scope("step")
public class InitWriter implements ItemWriter<Timestamp> {

    private String ctVersionToUse;
    private String studyId;
    private StudyDto study;
    private StudyService studyService;
    private AssayResultService assayResultService;
    private final static String H_PATERN = " 00:00";
	
    @AfterStep
    public void afterStep(StepExecution stepExecution) throws IOException {
    	stepExecution.getJobExecution().getExecutionContext().put(Constants.CTVERSION, ctVersionToUse);
    	stepExecution.getJobExecution().getExecutionContext().put("study", study);
    }
 
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
    	this.studyId = stepExecution.getJobParameters().getString(Constants.STUDYID);
    	try {
    		this.study = getStudyService().getStudyDtoByStudyId(studyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
 
	@Override
    public void write(List<? extends Timestamp> items) throws Exception {
		List<String> ctVersions = SendService.getControlledTerminologyVersions();
		LocalDateTime studyExpStartDate = DataUtils.convertDateToLocalDateTime(items.get(0));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.SD_FORMAT5); 
		for(String ct : ctVersions) {
			LocalDateTime ctVersion = LocalDateTime.parse(ct + H_PATERN, formatter);
			if(ctVersion.isAfter(studyExpStartDate)) {
				break;
			}
			ctVersionToUse = ct;
		}
	}

	public StudyService getStudyService() {
		return studyService == null ? ContextServices.getStudyService() : studyService;
	}

	public AssayResultService getAssayResultService() {
		return assayResultService == null ? ContextServices.getAssayResultService() : assayResultService;
	}
    
  

}

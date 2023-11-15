package com.idorsia.research.send.writers;

import java.util.List;
import java.io.IOException;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.idorsia.research.send.domain.SendTerminology;

@Component("cTerminologyDbWriter")
@Scope("step")
public class CTerminologyDbWriter extends DataExcelWriter implements ItemWriter<SendTerminology> {

	
    @AfterStep
    public void afterStep(StepExecution stepExecution) throws IOException {
    	
    }
 
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
    	
    }
 
    @Override
	public void write(List<? extends SendTerminology> items) throws Exception {

		for (int i = 0; i < items.size(); i++) {
			
			
		}
	}
    
}

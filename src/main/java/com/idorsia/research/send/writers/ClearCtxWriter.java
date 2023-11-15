package com.idorsia.research.send.writers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.idorsia.research.send.processors.Constants;
import com.idorsia.research.send.processors.DataUtils;

@Component("clearCtxWriter")
@Scope("step")
public class ClearCtxWriter implements ItemWriter<Timestamp> {
	private String studyId;
	Set<String> keys = new HashSet<String>();
	private Timestamp studyEndTime;

	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
		DataUtils.clearContext(stepExecution, keys);
		System.out.println("Finish Batch for study " + studyId + " that ended at " + studyEndTime);
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		studyId = stepExecution.getJobParameters().getString(Constants.STUDYID);
		System.out.println("Clear context");
	}

	@Override
	public void write(List<? extends Timestamp> items) throws Exception {
		keys.add("SPEC");
		keys.add("OMTEST");
		keys.add("OMTESTCD");
		keys.add("UNIT");
		keys.add("PHSPRP");
		keys.add("PHSPRPCD");
		keys.add("SVSTST");
		keys.add("SVSTSTCD");
		keys.add("FWTEST");
		keys.add("FWTESTCD");
		keys.add("etcdMap");
		keys.add("studyId");
		studyEndTime = items.get(0);
	}

}

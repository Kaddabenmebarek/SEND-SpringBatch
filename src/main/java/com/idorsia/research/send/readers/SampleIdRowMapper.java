package com.idorsia.research.send.readers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;

@Scope("step")
public class SampleIdRowMapper implements RowMapper<String> {
	
	@SuppressWarnings("unused")
	private StepExecution stepExecution;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	@Override
	public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		String sampleId = resultSet.getString("sampleid");
		return sampleId;
    }
}

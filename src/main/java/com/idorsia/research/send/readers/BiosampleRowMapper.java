package com.idorsia.research.send.readers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;

@Scope("step")
public class BiosampleRowMapper implements RowMapper<Integer> {
	
	@SuppressWarnings("unused")
	private StepExecution stepExecution;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	@Override
	public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int biosampleId = resultSet.getInt("biosample_id");
		return biosampleId;
    }
}

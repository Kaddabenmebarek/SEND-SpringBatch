package com.idorsia.research.send.readers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.core.StepExecution;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.idorsia.research.send.processors.Constants;

@Scope("step")
public class DatabaseStatementSetter implements PreparedStatementSetter {

	private String studyId;
	private StepExecution stepExecution;
	
	@Override
	public void setValues(PreparedStatement ps) throws SQLException {
		studyId = stepExecution.getJobParameters().getString(Constants.STUDYID);
        ps.setString(1, studyId);
        ps.getConnection().getClientInfo();
    }

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
	
	

}

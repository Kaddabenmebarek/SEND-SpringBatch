package com.idorsia.research.send.listener;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import com.idorsia.research.send.processors.Constants;

public class DsStepListener implements StepExecutionListener {

	private final static String DB_URL = "jdbc:oracle:thin:@hypnos-test:1521:testact0";
	private final static String USER = "spirit";
	private final static String PASSWORD = "vLKhUXLIGF";
	
	@Override
	public void beforeStep(StepExecution stepExecution) {

		String studyId = stepExecution.getJobParameters().getString(Constants.STUDYID); 
		try {
			List<String> phaseList = getPhaseList(studyId);
			String lastPhase = getlastPhase(phaseList);
			stepExecution.getJobExecution().getExecutionContext().put(Constants.LAST_PHASE, lastPhase);
		} catch(Exception e) {}
		
	}

	private String getlastPhase(List<String> phaseList) {
		// coeff: 
		// day = 100; minute = 10; hour = 1
		Map<Integer, String> coeffPhaseMap = new TreeMap<Integer, String>();
		for(String p : phaseList) {
			if(p.contains("d")) {
				int day = 0;
				int hour = 0;
				int minute = 0;
				if(p.contains("_")) {
					day = Integer.valueOf(StringUtils.substringBetween(p, "d", "_"));
					if(p.contains("h")) {
						hour = Integer.valueOf(StringUtils.substringBetween(p, "_", "h"));
						if(StringUtils.substringAfter(p, "h") != null && !"".equals(StringUtils.substringAfter(p, "h"))) {
							minute = Integer.valueOf(StringUtils.substringAfter(p, "h"));
						}
					}
				}else {
					day = Integer.valueOf(StringUtils.substringAfter(p, "d"));
				}
				int coeff = day*100 + hour*10 + minute; 
				coeffPhaseMap.put(coeff, p);
			}
		}
		Entry<Integer, String> entry = ((TreeMap<Integer, String>) coeffPhaseMap).lastEntry();
		return entry.getValue();
	}

	private List<String> getPhaseList(String studyId) throws SQLException {
		List<String> phaseList = new ArrayList<String>();
		Connection conn = null;
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			String phaseSql = "select p.name from study_phase p, study s where p.study_id = s.id and s.studyid = '" + studyId + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(phaseSql);
			while(rs.next()){
				phaseList.add(rs.getString("name"));
			}
		} finally {
			if(conn!=null) conn.close();
		}
		return phaseList;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}

}

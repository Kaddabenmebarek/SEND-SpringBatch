package com.idorsia.research.send.readers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.jdbc.core.RowMapper;

import com.idorsia.research.send.domain.Demographics;
import com.idorsia.research.send.processors.Constants;

public class DatabaseDMRowMapper implements RowMapper<Demographics> {

	@Override
	public Demographics mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.SD_FORMAT, Locale.ENGLISH);
		Demographics demographics = new Demographics();
		demographics.setStudyId(resultSet.getString("studyid"));
		demographics.setDomain("DM");
		demographics.setUsubjId(resultSet.getString("usubjid"));
		demographics.setSubjId(resultSet.getString("subjid"));
		demographics.setRfStDtc(resultSet.getString("rfstdtc"));
		demographics.setRfEnDtc(resultSet.getString("rfendtc"));
		demographics.setDeliveryDtc(resultSet.getString("deliverydate"));
		demographics.setBrthDtc(resultSet.getString("birthday"));
		if(resultSet.getString("rfendtc") == null) {
			demographics.setRfEnDtc(sdf.format(new Date()));
		}
		//demographics.setMetadata(resultSet.getString("metadata"));
		demographics.setSpecies(resultSet.getString("species"));
		demographics.setStrain(resultSet.getString("strain"));
		demographics.setSex(resultSet.getString("sex").toUpperCase());
		demographics.setArm(resultSet.getString("arm"));
		//demographics.setStudyStartingDate(resultSet.getString("studyStartDate"));
     	//age, ageU, sex, species, armcd, setcd set into the processor    	
        return demographics;
    }

}
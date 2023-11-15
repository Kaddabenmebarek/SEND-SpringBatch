package com.idorsia.research.send.readers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

public class DatabaseDummyRowMapper implements RowMapper<Timestamp> {

	@Override
	public Timestamp mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		return resultSet.getTimestamp("expendate");
    }

}

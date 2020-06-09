package com.jobdiva.api.dao.qualification;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.QualificationOption;

public class QualificationOptionRowMapper implements RowMapper<QualificationOption> {
	
	@Override
	public QualificationOption mapRow(ResultSet rs, int rowNum) throws SQLException {
		QualificationOption qualificationOption = new QualificationOption();
		//
		qualificationOption.setDcatId(rs.getInt("DCATID"));
		qualificationOption.setCatId(rs.getInt("CATID"));
		qualificationOption.setTeamid(rs.getInt("TEAMID"));
		qualificationOption.setDcatName(rs.getString("DCATNAME"));
		qualificationOption.setIsDefault(rs.getBoolean("IS_DEFAULT"));
		qualificationOption.setClosed(rs.getBoolean("CLOSED"));
		qualificationOption.setActive(rs.getBoolean("ACTIVE"));
		//
		return qualificationOption;
	}
}

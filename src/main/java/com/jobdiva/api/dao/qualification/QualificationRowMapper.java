package com.jobdiva.api.dao.qualification;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.Qualification;

public class QualificationRowMapper implements RowMapper<Qualification> {
	
	@Override
	public Qualification mapRow(ResultSet rs, int rowNum) throws SQLException {
		Qualification qualificationOption = new Qualification();
		//
		qualificationOption.setCateId(rs.getInt("CATID"));
		qualificationOption.setTeamId(rs.getInt("TEAMID"));
		qualificationOption.setCatName(rs.getString("CATNAME"));
		qualificationOption.setClosed(rs.getBoolean("CLOSED"));
		qualificationOption.setIncludeNotMarked(rs.getBoolean("INCLUDE_NOT_MARKED"));
		qualificationOption.setColorId(rs.getInt("COLORID"));
		qualificationOption.setAuthRequired(rs.getBoolean("AUTHREQUIRED"));
		qualificationOption.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));
		qualificationOption.setMultipleChoice(rs.getBoolean("MULTIPLE_CHOICE"));
		qualificationOption.setOrderByName(rs.getBoolean("ORDERBYNAME"));
		//
		return qualificationOption;
	}
}

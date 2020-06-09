package com.jobdiva.api.dao.team;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.Team;

public class TeamRowMapper implements RowMapper<Team> {
	
	@Override
	public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
		//
		Team team = new Team();
		team.setId(rs.getLong("ID"));
		team.setCompany(rs.getString("COMPANY"));
		team.setNotePrivacy(rs.getByte("NOTEPRIVACY"));
		team.setCandinfoChange(rs.getBoolean("CANDINFOCHANGE"));
		team.setDailyDeleteTotal(rs.getShort("DAILY_DELETE_TOTAL"));
		team.setUserCandidatePortal(rs.getBoolean("USE_CANDIDATE_PORTAL"));
		team.setUseDivaFinancials(rs.getBoolean("USE_DIVA_FINANCIALS"));
		team.setUseExchange(rs.getBoolean("USE_EXCHANGE"));
		team.setUseOwnReport(rs.getBoolean("USE_OWN_REPORT"));
		team.setUseSSL(rs.getBoolean("USE_SSL"));
		team.setRegionCode(rs.getString("REGION_CODE"));
		team.setStrTimeZone(rs.getString("STRTIMEZONE"));
		return team;
	}
}

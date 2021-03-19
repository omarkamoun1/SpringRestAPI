package com.jobdiva.api.dao.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.CompanyUDF;
import com.jobdiva.api.model.webhook.WebhookUDF;

@Component
public class SearchCompanyUDFDao extends AbstractJobDivaDao {
	
	public List<CompanyUDF> getCompanyUDF(long companyId, long teamId) {
		String sql = " Select "//
				+ " USERFIELD_ID , "//
				+ " TEAMID, " //
				+ " USERFIELD_VALUE, " //
				+ " DATECREATED  " //
				+ " FROM TCOMPANY_USERFIELDS " //
				+ " WHERE COMPANYID = ? and TEAMID = ? ";
		//
		Object[] params = new Object[] { companyId, teamId };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<CompanyUDF> list = jdbcTemplate.query(sql, params, new RowMapper<CompanyUDF>() {
			
			@Override
			public CompanyUDF mapRow(ResultSet rs, int rowNum) throws SQLException {
				CompanyUDF companyAddress = new CompanyUDF();
				companyAddress.setUserfieldId(rs.getInt("USERFIELD_ID"));
				companyAddress.setTeamid(rs.getLong("TEAMID"));
				companyAddress.setUserfieldValue(rs.getString("USERFIELD_VALUE"));
				companyAddress.setDatecreated(rs.getDate("DATECREATED"));
				return companyAddress;
			}
		});
		return list;
	}
	
	public List<WebhookUDF> getCompanyWebhookUDF(Long companyId, Long teamId) {
		String sql = " Select "//
				+ " USERFIELD_ID , "//
				+ " TEAMID, " //
				+ " USERFIELD_VALUE, " //
				+ " DATECREATED  " //
				+ " FROM TCOMPANY_USERFIELDS " //
				+ " WHERE COMPANYID = ? and TEAMID = ? ";
		//
		Object[] params = new Object[] { companyId, teamId };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<WebhookUDF> list = jdbcTemplate.query(sql, params, new RowMapper<WebhookUDF>() {
			
			@Override
			public WebhookUDF mapRow(ResultSet rs, int rowNum) throws SQLException {
				WebhookUDF webhookUDF = new WebhookUDF();
				webhookUDF.setUserFieldId(rs.getInt("USERFIELD_ID"));
				webhookUDF.setUserfieldValue(rs.getString("USERFIELD_VALUE"));
				webhookUDF.setDateCreated(rs.getDate("DATECREATED"));
				return webhookUDF;
			}
		});
		return list;
	}
}

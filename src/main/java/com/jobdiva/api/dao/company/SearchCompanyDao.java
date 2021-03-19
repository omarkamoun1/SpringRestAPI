package com.jobdiva.api.dao.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.Company;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.webhook.WebhookCompany;
import com.jobdiva.api.utils.StringUtils;

@Component
public class SearchCompanyDao extends AbstractJobDivaDao {
	
	private void handleError(StringBuffer errors, String message) throws Exception {
		errors.append(message);
	}
	
	private void assignCompanyTypes(JobDivaSession jobDivaSession, Company lcalCompany) {
		String sql = "select b.TYPENAME " //
				+ " from TCUSTOMER_COMPANY_TYPE a, TCUSTOMER_COMPANY_TYPES b " //
				+ " where a.COMPANYID = ? " //
				+ " and a.TEAMID = ? " + " and a.TYPEID = b.ID " + " and b.TEAMID = ? " + " and b.DELETED = 0";
		//
		Object[] param = new Object[] { lcalCompany.getId(), jobDivaSession.getTeamId(), jobDivaSession.getTeamId() };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<String> typenameList = jdbcTemplate.query(sql, param, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("TYPENAME");
			}
		});
		//
		StringBuffer companyType = new StringBuffer();
		for (String value : typenameList)
			if (value != null && !value.isEmpty())
				companyType.append(value + " | ");
		lcalCompany.setCompanyType(companyType.toString());
	}
	
	private void assignCompanyOwnsers(JobDivaSession jobDivaSession, Company lcalCompany) {
		String sql = "select b.FIRSTNAME||' '||b.LASTNAME as owner " //
				+ " from TCUSTOMER_COMPANY_OWNERS a, TRECRUITER b " //
				+ " where b.ACTIVE = 1 " //
				+ " and a.COMPANYID = ? " //
				+ " and b.ID = a.RECRUITERID " //
				+ " and a.TEAMID = ?  " //
				+ " order by upper(b.FIRSTNAME)";
		//
		Object[] param = new Object[] { lcalCompany.getId(), jobDivaSession.getTeamId() };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<String> ownerList = jdbcTemplate.query(sql, param, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("owner");
			}
		});
		//
		StringBuffer owners = new StringBuffer();
		for (String value : ownerList)
			if (value != null && !value.isEmpty())
				owners.append(value + " | ");
		lcalCompany.setOwners(owners.toString());
	}
	
	private void assignCompanyPrimaryContacts(JobDivaSession jobDivaSession, Company lcalCompany) {
		String sql = "select FIRSTNAME||' '||LASTNAME as Name" //
				+ " from TCUSTOMER " //
				+ " where TEAMID = ?  " //
				+ " and COMPANYID = ?  " //
				+ " and ISPRIMARYCONTACT = 1 " //
				+ " order by upper(FIRSTNAME)";
		//
		Object[] param = new Object[] { jobDivaSession.getTeamId(), lcalCompany.getId() };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<String> primaryContactList = jdbcTemplate.query(sql, param, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("Name");
			}
		});
		//
		StringBuffer primaryList = new StringBuffer();
		for (String value : primaryContactList)
			if (value != null && !value.isEmpty())
				primaryList.append(value + " | ");
		//
		lcalCompany.setPrimaryContacts(primaryList.toString());
	}
	
	public List<Long> searchForCompany(JobDivaSession jobDivaSession, String company) {
		String sql = "SELECT id FROM TCUSTOMERCOMPANY WHERE   teamid = ? AND  upper(name) = upper(?) ";
		Object[] params = new Object[] { jobDivaSession.getTeamId(), company };
		return getJdbcTemplate().query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("id");
			}
		});
	}
	
	public List<Company> searchForCompany(JobDivaSession jobDivaSession, Long companyId) {
		String sql = " SELECT * FROM TCUSTOMERCOMPANY WHERE TEAMID = ? AND id = ? ";
		Object[] param = new Object[] { jobDivaSession.getTeamId(), companyId };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Company> companyList = jdbcTemplate.query(sql, param, new RowMapper<Company>() {
			
			@Override
			public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
				Company company = new Company();
				company.setId(rs.getLong("Id"));
				company.setName(rs.getString("name"));
				return company;
			}
		});
		return companyList;
	}
	
	public List<Company> searchForCompany(JobDivaSession jobDivaSession, Long companyId, String company, String address, String city, String state, String zip, String country, //
			String phone, String fax, String url, String parentCompany, Boolean showAll, //
			String[] types, Long ownerIds, String division, String nameIndex) throws Exception {
		//
		try {
			StringBuffer errors = new StringBuffer();
			//
			if (jobDivaSession.getTeamId() != null && jobDivaSession.getTeamId() < 1) {
				throw new Exception("Invalid Client Id " + jobDivaSession.getTeamId());
			}
			//
			if (companyId != null && companyId < 1)
				handleError(errors, "Error: Company ID should be a positive number. \r\n");
			//
			if (company != null) {
				if (company.length() > 100)
					handleError(errors, "Error: Company name should be less than 100 characters. \r\n");
				else if (company.trim().isEmpty()) {
					company = null;
				}
			}
			//
			if (address != null) {
				if (address.length() > 255)
					handleError(errors, "Error: Address should be less than 255 characters. \r\n");
				else if (address.trim().isEmpty())
					address = null;
			}
			//
			if (city != null) {
				if (city.length() > 100)
					handleError(errors, "Error: City should be less than 100 characters. \r\n");
				else if (city.trim().isEmpty())
					city = null;
			}
			//
			if (state != null) {
				if (state.length() > 100)
					handleError(errors, "Error: State should be less than 100 characters. \r\n");
				else if (state.trim().isEmpty())
					state = null;
			}
			//
			if (zip != null) {
				if (zip.length() > 20)
					handleError(errors, "Error: Zipcode should be less than 20 characters. \r\n");
				else if (zip.trim().isEmpty())
					zip = null;
			}
			//
			if (country != null) {
				if (country.length() > 255)
					handleError(errors, "Error: Country should be less than 255 characters. \r\n");
				else if (country.trim().isEmpty())
					country = null;
			}
			//
			if (phone != null) {
				if (phone.length() > 20)
					handleError(errors, "Error: Phone should be less than 20 characters. \r\n");
				else if (phone.trim().isEmpty())
					phone = null;
			}
			//
			if (fax != null) {
				if (fax.length() > 20)
					handleError(errors, "Error: Fax should be less than 20 characters. \r\n");
				else if (fax.trim().isEmpty())
					fax = null;
			}
			//
			if (url != null) {
				if (url.length() > 200)
					handleError(errors, "Error: URL should be less than 200 characters. \r\n");
				else if (url.trim().isEmpty())
					url = null;
			}
			//
			if (parentCompany != null) {
				if (parentCompany.length() > 100)
					handleError(errors, "Error: Parentcompany name should be less than 100 characters. \r\n");
				else if (parentCompany.trim().isEmpty())
					parentCompany = null;
			}
			//
			if (types != null && !StringUtils.validateStrArr(types)) {
				types = null;
			}
			//
			if (division != null) {
				if (division.length() > 100)
					handleError(errors, "Error: Division should be less than 100 characters.  \r\n");
				else if (division.trim().isEmpty())
					division = null;
			}
			//
			if (!((companyId != null) || (company != null) || (address != null) || (city != null) || (state != null) || (zip != null) || (country != null) || (phone != null) || (fax != null) || (url != null) || (parentCompany != null)
					|| (showAll != null) || (division != null)))
				handleError(errors, "Error: Please specify at least one parameter to search. \r\n");
			//
			if (errors.length() > 0) {
				throw new Exception("Parameter Check Failed \r\n " + errors.toString());
			}
			//
			//
			//
			//
			Map<Short, String> pipelineIdNameMap = new HashMap<Short, String>();
			Map<String, Short> pipelineNameIdMap = new HashMap<String, Short>();
			// Pull sales pipeline values for the team
			String sql = "SELECT id, upper(name) as name " //
					+ " FROM tsalespipeline " //
					+ " WHERE teamid = ? " //
					+ " AND is_active = 1";
			//
			Object[] parameters = new Object[] { jobDivaSession.getTeamId() };
			//
			getJdbcTemplate().query(sql, parameters, new RowMapper<Boolean>() {
				
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					Short pipelineId = rs.getShort("id");
					String pipelineName = rs.getString("name");
					pipelineIdNameMap.put(pipelineId, pipelineName);
					pipelineNameIdMap.put(pipelineName, pipelineId);
					return null;
				}
			});
			if (nameIndex != null && nameIndex.length() > 0) {
				// Sales pipeline is in placeholder NameIndex; not related with
				// name index
				if (!pipelineNameIdMap.containsKey(nameIndex.toUpperCase()))
					throw new Exception("Error: invalid sales pipeline " + nameIndex);
			}
			//
			//
			//
			//
			//
			// Build the query
			String select = null;
			StringBuffer tables = new StringBuffer();
			StringBuffer constrains = new StringBuffer();
			Hashtable<String, Object> params = new Hashtable<String, Object>();
			//
			select = "select distinct a.id, a.teamid, a.name, b.address1, b.address2, b.city, b.state, b.zipcode, b.countryid, b.phone, b.fax, " //
					+ "b.email, b.url, a.PARENT_COMPANYID, a.PARENT_COMPANY_NAME , a.PIPELINE_ID from";
			//
			tables.append(" TCUSTOMERCOMPANY a, TCUSTOMERCOMPANYADDRESSES b");
			//
			constrains.append(" where a.teamid = :teamid and b.teamid = a.teamid and a.id = b.COMPANYID  and b.DEFAULT_ADDRESS = 1");
			//
			params.put("teamid", jobDivaSession.getTeamId());
			//
			//
			if (companyId != null) {
				constrains.append(" and a.id = :companyid");
				params.put("companyid", companyId);
				// can lookup detailed information from single id
			}
			//
			if (company != null) {
				constrains.append(" and upper(a.name) like upper(:companyname) ||'%'");
				params.put("companyname", company);
			}
			//
			if (parentCompany != null) {
				List<Long> parentCompanyIds = getCompanyIdsByName(jobDivaSession.getTeamId(), parentCompany);
				if (parentCompanyIds != null && parentCompanyIds.size() > 0 && (showAll != null && showAll)) {
					constrains.append(" and a.parentpath like (select parentpath from TCUSTOMERCOMPANY where id in (:parentcompanyid)) ||'%' and a.id <> :parentcompanyid");
					params.put("parentcompanyid", parentCompanyIds);// parentcompanyid);
				} else {
					constrains.append(" and upper(a.PARENT_COMPANY_NAME) like upper(:parentCompanyName) ||'%'");
					params.put("parentCompanyName", parentCompany);
				}
			}
			//
			if (types != null) {
				tables.append(", TCUSTOMER_COMPANY_TYPE c ");
				List<Long> typeIds = getIdsFromCompanyTypeName(jobDivaSession.getTeamId(), types);
				//
				if (typeIds != null && typeIds.size() > 0) {
					constrains.append(" and c.teamid = a.teamid and c.COMPANYID = a.id and c.TYPEID in (:typeids)");
					params.put("typeids", typeIds); // put a List
				} else {
					constrains.append(" and a.id < -1");
				}
			}
			//
			if (ownerIds != null) {
				tables.append(", TCUSTOMER_COMPANY_OWNERS d");
				constrains.append(" and d.teamid = a.teamid and d.COMPANYID = a.id and d.RECRUITERID in (:ownerids)");
				params.put("ownerids", ownerIds);
			}
			//
			if (division != null) {
				List<Long> companyIds = getCompanyIdsByDivision(jobDivaSession.getTeamId(), division);
				//
				if (companyIds != null && companyIds.size() > 1) {
					constrains.append(" and a.id in (:companyids)");
					params.put("companyids", companyIds);
				} else {
					constrains.append(" and a.id < -1");
				}
			}
			//
			//
			if (address != null && address.length() > 0 || city != null && city.length() > 0 || state != null && state.length() > 0 || zip != null && zip.length() > 0 || country != null && country.length() > 0 || phone != null && phone.length() > 0
					|| fax != null && fax.length() > 0 || url != null && url.length() > 0) {
				constrains.append(" and a.id in (select distinct COMPANYID from TCUSTOMERCOMPANYADDRESSES where TEAMID = :teamid");
				if (address != null && address.length() > 0) {
					constrains.append(" and (UPPER(address1) like '%'||UPPER(:address)||'%' or UPPER(address2) like '%'||UPPER(:address)||'%')");
					params.put("address", address);
				}
				if (city != null && city.length() > 0) {
					constrains.append(" and UPPER(city) like UPPER(:city)||'%'");
					params.put("city", city);
				}
				if (state != null && state.length() > 0) {
					//
					String stateId = lookupState(state, country);
					if (stateId != null) {
						constrains.append(" and UPPER(state) = UPPER(:state)");
						params.put("state", stateId);
					} else
						throw new Exception("Error: State (" + state + ") can not be identified.(with countryid(" + country + ")) \r\n");
				}
				if (zip != null && zip.length() > 0) {
					constrains.append(" and UPPER(zipcode) like UPPER(:zip)||'%'");
					params.put("zip", zip);
				}
				if (country != null && country.length() > 0) {
					constrains.append(" and UPPER(countryid) = UPPER(:country)");
					params.put("country", country);
				}
				if (phone != null && phone.length() > 0) {
					constrains.append(" and phone like :phone||'%'");
					params.put("phone", phone);
				}
				if (fax != null && fax.length() > 0) {
					constrains.append(" and fax like :fax||'%'");
					params.put("fax", fax);
				}
				if (url != null && url.length() > 0) {
					constrains.append(" and UPPER(url) like '%'||UPPER(:url)||'%'");
					params.put("url", url);
				}
				constrains.append(")");
			}
			//
			if (nameIndex != null) { // search by sales pipeline
				Short pipelineId = pipelineNameIdMap.get(nameIndex.toUpperCase());
				constrains.append(" AND a.PIPELINE_ID = :pipelineid ");
				params.put("pipelineid", pipelineId);
			}
			//
			constrains.append(" AND ROWNUM <= 201 ");
			//
			String queryString = select + tables + constrains + "  order by upper(a.name)";
			//
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
			//
			List<Company> list = namedParameterJdbcTemplate.query(queryString, params, new CompanyRowMapper());
			//
			if (list != null) {
				for (Company lcalCompany : list) {
					//
					String pipelineName = pipelineIdNameMap.get(lcalCompany.getPipelineId());
					lcalCompany.setNameIndex(pipelineName);
					//
					assignCompanyInofs(jobDivaSession, lcalCompany);
				}
			}
			//
			return list;
		} catch (Exception e) {
			//
			throw new Exception(e.getMessage());
		}
	}
	
	private void assignCompanyInofs(JobDivaSession jobDivaSession, Company lcalCompany) {
		// company types
		//
		assignCompanyTypes(jobDivaSession, lcalCompany);
		//
		assignCompanyOwnsers(jobDivaSession, lcalCompany);
		//
		assignCompanyPrimaryContacts(jobDivaSession, lcalCompany);
	}
	
	public WebhookCompany getWebhookCompany(JobDivaSession jobDivaSession, Long companyId) {
		//
		try {
			// Build the query
			String select = null;
			StringBuffer tables = new StringBuffer();
			StringBuffer constrains = new StringBuffer();
			Hashtable<String, Object> params = new Hashtable<String, Object>();
			//
			select = "select distinct a.id, a.teamid, a.name, b.address1, b.address2, b.city, b.state, b.zipcode, b.countryid, b.phone, b.fax, " //
					+ "b.email, b.url, a.PARENT_COMPANYID, a.PARENT_COMPANY_NAME , a.PIPELINE_ID from";
			//
			tables.append(" TCUSTOMERCOMPANY a, TCUSTOMERCOMPANYADDRESSES b");
			//
			constrains.append(" where a.teamid = :teamid and b.teamid = a.teamid and a.id = b.COMPANYID  and b.DEFAULT_ADDRESS = 1");
			//
			params.put("teamid", jobDivaSession.getTeamId());
			//
			//
			if (companyId != null) {
				constrains.append(" and a.id = :companyid");
				params.put("companyid", companyId);
				// can lookup detailed information from single id
			}
			String queryString = select + tables + constrains + "  order by upper(a.name)";
			//
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
			//
			List<Company> list = namedParameterJdbcTemplate.query(queryString, params, new WebhookCompanyRowMapper());
			//
			if (list != null) {
				WebhookCompany webhookCompany = (WebhookCompany) list.get(0);
				//
				assignCompanyInofs(jobDivaSession, webhookCompany);
				//
				return webhookCompany;
			}
			return null;
			//
		} catch (Exception e) {
			logger.error("Get Company [" + companyId + "] :: " + e.getMessage());
			return null;
		}
	}
}

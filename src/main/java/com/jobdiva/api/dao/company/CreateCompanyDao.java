package com.jobdiva.api.dao.company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.CompanyOwner;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.utils.StringUtils;

@Component
public class CreateCompanyDao extends AbstractJobDivaDao {
	
	class ParentCompany {
		
		Long	id;
		String	name;
		String	path;
	}
	
	private void checkExistingCompanyWithSameName(String companyName) throws Exception {
		String sql = "SELECT ID FROM TCUSTOMERCOMPANY Where   NLS_UPPER( name) =  ?  ";
		//
		Object[] params = new Object[] { companyName.toUpperCase() };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("ID");
			}
		});
		//
		if (list != null && list.size() > 0) {
			throw new Exception("Error: The company \'" + companyName + "\'(id:" + list.get(0) + ") already exists in the system." + " Please enter a different company name. ");
		}
		//
	}
	
	private ParentCompany getParentCompany(JobDivaSession jobDivaSession, String parentcompany, Long newCompanyId) throws Exception {
		if (isNotEmpty(parentcompany)) {
			try {
				String sql = "select ID, NAME, PARENTPATH " //
						+ " from TCUSTOMERCOMPANY " //
						+ " where " //
						+ " TEAMID = ? " //
						+ " and NAME_INDEX = ? ";
				//
				Object[] params = new Object[] { jobDivaSession.getTeamId(), parentcompany.toUpperCase() };
				//
				JdbcTemplate jdbcTemplate = getJdbcTemplate();
				//
				List<ParentCompany> list = jdbcTemplate.query(sql, params, new RowMapper<ParentCompany>() {
					
					@Override
					public ParentCompany mapRow(ResultSet rs, int rowNum) throws SQLException {
						ParentCompany parentCompanyObj = new ParentCompany();
						//
						parentCompanyObj.id = rs.getLong("ID");
						parentCompanyObj.name = rs.getString("NAME");
						parentCompanyObj.path = rs.getString("PARENTPATH") + newCompanyId + ".";
						//
						return parentCompanyObj;
					}
				});
				//
				if (list == null || list.isEmpty()) {
					throw new Exception("Error: Parent company(" + parentcompany + ") is not found!");
				} else {
					return list.get(0);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
			//
		} else {
			ParentCompany parentCompanyObj = new ParentCompany();
			parentCompanyObj.id = 0L;
			parentCompanyObj.path = newCompanyId + ".";
			return parentCompanyObj;
		}
	}
	
	private void updateparentCompanyInfo(JobDivaSession jobDivaSession, String parentcompany, Long companyId) throws Exception {
		Object[] params;
		ParentCompany parentCompanyObj = getParentCompany(jobDivaSession, parentcompany, companyId);
		String slUpdate = "UPDATE TCUSTOMERCOMPANY SET PARENT_COMPANYID = ? , PARENT_COMPANY_NAME = ? , PARENTPATH = ?  WHERE ID = ?  ";
		params = new Object[] { parentCompanyObj.id, parentCompanyObj.name, parentCompanyObj.path, companyId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(slUpdate, params);
	}
	
	private void insertIntoCompanyAddresses(JobDivaSession jobDivaSession, Long companyId, String address1, String address2, String city, String state, String zipcode, String country, String phone, String fax, String email, String url) {
		// save record into TCUSTOMERCOMPANYADDRESSES
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		fields.add("COMPANYID");
		paramList.add(companyId);
		//
		fields.add("TEAMID");
		paramList.add(jobDivaSession.getTeamId());
		//
		fields.add("ADDRESSID");
		paramList.add(new Short((short) 1));
		//
		fields.add("DEFAULT_ADDRESS");
		paramList.add(true);
		//
		if (isNotEmpty(address1)) {
			fields.add("ADDRESS1");
			paramList.add(address1);
		}
		//
		if (isNotEmpty(address2)) {
			fields.add("ADDRESS2");
			paramList.add(address2);
		}
		//
		if (isNotEmpty(city)) {
			fields.add("CITY");
			paramList.add(city);
		}
		//
		if (isNotEmpty(zipcode)) {
			fields.add("ZIPCODE");
			paramList.add(zipcode);
		}
		//
		if (isNotEmpty(phone)) {
			fields.add("PHONE");
			paramList.add(phone);
		}
		//
		if (isNotEmpty(fax)) {
			fields.add("FAX");
			paramList.add(fax);
		}
		//
		if (isNotEmpty(email)) {
			fields.add("EMAIL");
			paramList.add(email);
		}
		//
		if (isNotEmpty(url)) {
			fields.add("URL");
			paramList.add(url);
		}
		//
		if (isNotEmpty(country)) {
			fields.add("COUNTRYID");
			paramList.add(country);
			//
			if (isNotEmpty(state)) {
				String lookUpState = lookupState(state, country);
				if (lookUpState != null) {
					fields.add("STATE");
					paramList.add(lookUpState);
				}
			} else {
				fields.add("COUNTRYID");
				paramList.add("US");
			}
		}
		//
		String sqlInsert = "INSERT INTO TCUSTOMERCOMPANYADDRESSES (" + sqlInsertFields(fields) + ") VALUES (" + sqlInsertParams(fields) + ") ";
		//
		Object[] parameters = paramList.toArray();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, parameters);
	}
	
	private void insertIntoCompanyType(JobDivaSession jobDivaSession, String[] companytypes, Long companyId) throws Exception {
		if (companytypes != null && companytypes.length > 0) {
			//
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			List<Long> idsFromCompanyTypeName = getIdsFromCompanyTypeName(jobDivaSession.getTeamId(), companytypes);
			if (idsFromCompanyTypeName == null || idsFromCompanyTypeName.size() == 0)
				throw new Exception("Error: None of the company types could be found. ");
			//
			for (Long typeId : idsFromCompanyTypeName) {
				String sqlInsert = "INSERT INTO TCUSTOMER_COMPANY_TYPE(TEAMID, COMPANYID, TYPEID) VALUES(?, ? , ?)";
				Object[] params = new Object[] { jobDivaSession.getTeamId(), companyId, typeId };
				jdbcTemplate.update(sqlInsert, params);
			}
		}
	}
	
	private void updateCompanyOwner(JobDivaSession jobDivaSession, Long companyid, Owner[] owners) throws Exception {
		//
		if (owners != null) {
			//
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			for (Owner jdOwner : owners) {
				CompanyOwner companyOwner = new CompanyOwner();
				companyOwner.setInsertMode(true);
				companyOwner.setCompanyid(companyid);
				companyOwner.setRecruiterid(0L);
				if (jdOwner.getOwnerId() != null) {
					String sql = "SELECT ID FROM TRECRUITER WHERE ID = ?   ";
					Object[] params = new Object[] { jdOwner.getOwnerId() };
					//
					//
					List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
						
						@Override
						public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getLong("ID");
						}
					});
					//
					if (list == null || list.isEmpty())
						throw new Exception("Error: Company owner(" + jdOwner.getOwnerId() + ") does not exist. \r\n");
					//
					companyOwner.setRecruiterid(list.get(0));
				} else {
					String sql = "select ID " //
							+ " from TRECRUITER " //
							+ " where GROUPID = ? " //
							+ " and nls_upper(FIRSTNAME) = nls_upper(?)  " //
							+ " and nls_upper(LASTNAME) = nls_upper(?)";
					//
					Object[] params = new Object[] { jobDivaSession.getTeamId(), jdOwner.getFirstName(), jdOwner.getLastName() };
					//
					//
					List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
						
						@Override
						public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getLong("ID");
						}
					});
					if (list != null && list.size() > 0)
						companyOwner.setRecruiterid(list.get(0));
					else
						throw new Exception("Error: Company owner(" + jdOwner.getFirstName() + ", " + jdOwner.getLastName() + ") does not exist. \r\n");
				}
				//
				companyOwner.setTeamid(jobDivaSession.getTeamId());
				companyOwner.setIsprimaryowner(jdOwner.getPrimary());
				//
				String sqlInsert = "INSERT INTO TCUSTOMER_COMPANY_OWNERS(COMPANYID, RECRUITERID, ISPRIMARYOWNER, TEAMID) "//
						+ "VALUES " //
						+ "(?,?,?,?)";
				Object[] params = new Object[] { companyOwner.getCompanyid(), companyOwner.getRecruiterid(), companyOwner.getIsprimaryowner(), companyOwner.getTeamid() };
				//
				jdbcTemplate.update(sqlInsert, params);
			}
		} // don't set TCUSTOMER_OWNERS if not specified?
	}
	
	public Long createCompany(JobDivaSession jobDivaSession, String companyname, String address1, String address2, String city, String state, String zipcode, String country, //
			String phone, String fax, String email, String url, String parentcompany, String[] companytypes, Owner[] owners) throws Exception {
		//
		StringBuffer message = new StringBuffer();
		//
		if (companyname.length() > 100)
			message.append("Error: Company name should be less than 100 characters. ");
		else if (companyname.trim().length() == 0)
			message.append("Error: Company name should not be empty. ");
		//
		if (isNotEmpty(address1)) {
			if (address1.length() > 100)
				message.append("Error: Address1 should be less than 100 characters. ");
			else if (address1.trim().length() == 0)
				address1 = null;
		}
		//
		if (isNotEmpty(address2)) {
			if (address2.length() > 100)
				message.append("Error: Address2 should be less than 100 characters. ");
			else if (address2.trim().length() == 0)
				address2 = null;
		}
		//
		if (isNotEmpty(city)) {
			if (city.length() > 100)
				message.append("Error: City should be less than 100 characters. ");
			else if (city.trim().length() == 0)
				city = null;
		}
		//
		if (isNotEmpty(state)) {
			if (state.length() > 100)
				message.append("Error: State should be less than 100 characters. ");
			else if (state.trim().length() == 0)
				state = null;
		}
		//
		if (isNotEmpty(zipcode)) {
			if (zipcode.length() > 20)
				message.append("Error: Zipcode should be less than 20 characters. ");
			else if (zipcode.trim().length() == 0)
				zipcode = null;
		}
		// country
		if (isNotEmpty(country)) {
			if (country.length() > 100)
				message.append("Error: Country should be less than 100 characters. ");
			else if (country.trim().length() == 0)
				country = null;
			else {
				country = getCountryID(country);
				if (country.equals("not found"))
					country = null;
			}
			//
		} else if (isNotEmpty(state)) {
			throw new Exception("Error: Please specify 'country' if 'state' is specified. \r\n");
		}
		// phone
		if (isNotEmpty(phone)) {
			phone = Pattern.compile(NON_DIGITS).matcher(phone).replaceAll("");
			if (phone.length() > 20)
				message.append("Error: Phone should be less than 20 characters. ");
			else if (phone.trim().length() == 0)
				phone = null;
		}
		//
		if (isNotEmpty(fax)) {
			fax = Pattern.compile(NON_DIGITS).matcher(fax).replaceAll("");
			if (fax.length() > 20)
				message.append("Error: Fax should be less than 20 characters. ");
			else if (fax.trim().length() == 0)
				fax = null;
		}
		//
		if (isNotEmpty(email)) {
			if (email.length() > 100)
				message.append("Error: Email should be less than 100 characters. ");
			else if (email.trim().length() == 0)
				email = null;
		}
		//
		if (isNotEmpty(url)) {
			if (url.length() > 200)
				message.append("Error: Url should be less than 200 characters. ");
			else if (url.trim().length() == 0)
				url = null;
		}
		// parentcompany
		if (isNotEmpty(parentcompany)) {
			if (parentcompany.length() > 100)
				message.append("Error: Phone should be less than 100 characters. ");
			else if (parentcompany.trim().length() == 0)
				parentcompany = null;
		}
		//
		if (companytypes != null && !StringUtils.validateStrArr(companytypes))
			companytypes = null;
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed  " + message.toString());
		}
		//
		checkOwners(owners);
		//
		//
		checkExistingCompanyWithSameName(companyname);
		//
		java.sql.Date currentDt = new java.sql.Date(System.currentTimeMillis());
		//
		Long companyId = null;
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		String sql = "SELECT CUSTOMERCOMPANYID.nextval AS companyId FROM dual";
		List<Long> listLong = jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("companyId");
			}
		});
		if (listLong != null && listLong.size() > 0) {
			companyId = listLong.get(0);
		}
		// this is the key holder
		String insertSql = "INSERT INTO TCUSTOMERCOMPANY(ID,TEAMID, NAME,NAME_INDEX, DATEENTERED, RECRUITERID ) VALUES(" + companyId + ",? , ?, ? ,? ,? ) ";
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(insertSql, new String[] { "ID" });
			ps.setLong(1, jobDivaSession.getTeamId());
			ps.setString(2, companyname);
			ps.setString(3, companyname.toUpperCase());
			ps.setDate(4, currentDt);
			ps.setLong(5, jobDivaSession.getRecruiterId());
			return ps;
		});
		//
		//
		// Update parent Company Info
		updateparentCompanyInfo(jobDivaSession, parentcompany, companyId);
		//
		//
		insertIntoCompanyAddresses(jobDivaSession, companyId, address1, address2, city, state, zipcode, country, phone, fax, email, url);
		//
		//
		insertIntoCompanyType(jobDivaSession, companytypes, companyId);
		//
		updateCompanyOwner(jobDivaSession, companyId, owners);
		//
		return companyId;
	}
}

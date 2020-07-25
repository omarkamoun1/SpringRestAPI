package com.jobdiva.api.dao.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.el.ELException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.BillingUnitType;
import com.jobdiva.api.model.CompanyAddress;
import com.jobdiva.api.model.CompanyOwner;
import com.jobdiva.api.model.FinancialsType;
import com.jobdiva.api.model.FrequencyType;
import com.jobdiva.api.model.GroupInvoiceByType;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.ShowOnInvoiceType;
import com.jobdiva.api.model.TimeSheetEntryFormatType;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.WeekendingType;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.sql.JobDivaSqlLobValue;

@Component
public class UpdateCompanyDao extends AbstractJobDivaDao {
	
	//
	@Autowired
	CompanyAddressDao	companyAddressDao;
	//
	@Autowired
	SearchCompanyUDFDao	searchCompanyUDFDao;
	
	private void updateCompanyAddress(JobDivaSession jobDivaSession, Long companyid, CompanyAddress[] addresses) throws Exception {
		//
		if (addresses == null)
			return;
		//
		List<CompanyAddress> oldCompanyAddresses = companyAddressDao.getCompanyAddresses(companyid, jobDivaSession.getTeamId());
		//
		// if there's only one address with no data, delete it so that new ones
		// can be inserted
		if (oldCompanyAddresses.size() == 1) {
			CompanyAddress compAdd = oldCompanyAddresses.get(0);
			if (compAdd.getAddress1() == null && compAdd.getAddress2() == null && compAdd.getCity() == null && compAdd.getState() == null //
					&& compAdd.getZipcode() == null && compAdd.getPhone() == null && compAdd.getFax() == null && compAdd.getUrl() == null //
					&& compAdd.getEmail() == null && (compAdd.getCountryid() == null || compAdd.getCountryid().equals("US")) && compAdd.getDefaultAddress()) {
				//
				oldCompanyAddresses.remove(0);
				companyAddressDao.deleteCompanyAddress(companyid, compAdd.getAddressId(), jobDivaSession.getTeamId());
			}
		}
		//
		Hashtable<Short, Boolean> pairs = new Hashtable<Short, Boolean>();
		short maxAddressId = 0;
		for (CompanyAddress companyAddress : oldCompanyAddresses) {
			//
			if (maxAddressId < companyAddress.getAddressId())
				maxAddressId = companyAddress.getAddressId();
			//
			if (companyAddress.getDefaultAddress() == null)
				pairs.put(companyAddress.getAddressId(), false);
			else
				pairs.put(companyAddress.getAddressId(), companyAddress.getDefaultAddress());
		}
		//
		//
		//
		short maxAddId_tmp = maxAddressId;
		for (CompanyAddress companyAddress : addresses) {
			if (companyAddress.getAction() == 1) {
				if (companyAddress.getDefaultAddress() != null && !companyAddress.getDefaultAddress())
					// must be insert
					pairs.put(++maxAddId_tmp, companyAddress.getDefaultAddress());
				else if (companyAddress.getAddressId() != null)
					// may be modification
					pairs.put(companyAddress.getAddressId(), companyAddress.getDefaultAddress());
			} else if (companyAddress.getAction() == 2) // delete
				pairs.remove(companyAddress.getAddressId());
		}
		//
		//
		Enumeration<Boolean> v = pairs.elements();
		int da_cnt = 0;
		while (v.hasMoreElements() && da_cnt < 2)
			if (v.nextElement())
				da_cnt++;
		if (da_cnt != 1)
			throw new Exception("Error: There should be one and only one default address for company(" + companyid + "), please make sure the \'default\' flags are set correctly. ");
		//
		//
		for (CompanyAddress companyAddress : addresses) {
			//
			CompanyAddress dbCompanyAddress = companyAddressDao.searchForComapnyAddress(oldCompanyAddresses, companyAddress.getAddressId());
			//
			if (companyAddress.getAction() != null) {
				//
				if (companyAddress.getAction().equals(1)) {
					// insert Mode
					Boolean insertMode = dbCompanyAddress == null;
					//
					if (insertMode) {
						companyAddressDao.insertCompanyAddress(jobDivaSession, companyid, companyAddress, maxAddressId);
					} else {
						companyAddressDao.updateCompanyAddress(jobDivaSession, companyid, companyAddress);
					}
					//
				} else if (companyAddress.getAction().equals(2)) {
					// delete Mode
					if (dbCompanyAddress == null)
						throw new Exception("Error: Address(" + companyAddress.getAddressId() + ") of company(" + companyid + ") is not found. " //
								+ "Can not delete this address from the company. ");
					companyAddressDao.deleteCompanyAddress(companyid, companyAddress.getAddressId(), companyAddress.getTeamid());
				}
			}
			//
		}
	}
	
	private void deleteCompanyUDF(Long companyId, Integer userfieldId, Long teamId) {
		String sqlDelete = "DELETE FROM TCOMPANY_USERFIELDS Where COMPANYID = ? AND USERFIELD_ID = ? and TEAMID = ? ";
		Object[] params = new Object[] { companyId, userfieldId, teamId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlDelete, params);
	}
	
	private void updateCompanyUserFields(JobDivaSession jobDivaSession, Long companyId, Userfield[] userfields) throws Exception {
		//
		if (userfields != null) {
			validateUserFields(jobDivaSession, companyId, userfields, UDF_FIELDFOR_COMPANY);
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			for (Userfield userfield : userfields) {
				//
				if (isEmpty(userfield.getUserfieldValue())) {
					deleteCompanyUDF(companyId, userfield.getUserfieldId(), jobDivaSession.getTeamId());
				} else {
					String sql = "SELECT userfieldValue from TCOMPANY_USERFIELDS Where COMPANYID = ? AND USERFIELD_ID = ?  and TEAMID = ? ";
					//
					Object[] params = new Object[] { companyId, userfield.getUserfieldId(), jobDivaSession.getTeamId() };
					//
					//
					List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
						
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString("userfieldValue");
						}
					});
					//
					Timestamp currentDt = new Timestamp(System.currentTimeMillis());
					//
					if (list == null || list.isEmpty()) {
						String sqlInsert = "INSERT INTO TCOMPANY_USERFIELDS(COMPANYID, USERFIELD_ID, TEAMID, USERFIELD_VALUE, DATECREATED) " //
								+ "VALUES " //
								+ "(? , ?, ?, ?, ?) ";
						params = new Object[] { companyId, userfield.getUserfieldId(), jobDivaSession.getTeamId(), userfield.getUserfieldValue(), currentDt };
						jdbcTemplate.update(sqlInsert, params);
					} else {
						String sqlUpdate = "Update TCOMPANY_USERFIELDS SET USERFIELD_VALUE = ?, DATECREATED = ? " //
								+ "WHERE COMPANYID = ? AND USERFIELD_ID = ? and TEAMID = ?  " //
						;
						params = new Object[] { userfield.getUserfieldValue(), currentDt, companyId, userfield.getUserfieldId(), jobDivaSession.getTeamId() };
						jdbcTemplate.update(sqlUpdate, params);
					}
					//
					//
					//
				}
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
			//
			//
			String sql = "SELECT * FROM TCUSTOMER_COMPANY_OWNERS WHERE TEAMID = ? and COMPANYID = ? ";
			Object[] params = new Object[] { jobDivaSession.getTeamId(), companyid };
			//
			//
			List<CompanyOwner> allOwners = jdbcTemplate.query(sql, params, new RowMapper<CompanyOwner>() {
				
				@Override
				public CompanyOwner mapRow(ResultSet rs, int rowNum) throws SQLException {
					CompanyOwner companyOwner = new CompanyOwner();
					companyOwner.setRecruiterid(rs.getLong("RECRUITERID"));
					companyOwner.setCompanyid(rs.getLong("COMPANYID"));
					companyOwner.setIsprimaryowner(rs.getBoolean("ISPRIMARYOWNER"));
					companyOwner.setTeamid(rs.getLong("TEAMID"));
					return companyOwner;
				}
			});
			//
			Hashtable<Long, CompanyOwner> all = new Hashtable<Long, CompanyOwner>();
			int numOfprimary = 0;
			HashSet<Long> update = new HashSet<Long>();
			for (int i = 0; i < allOwners.size(); i++) { // deal with old owners
															// and get the
															// primary recruiter
															// info
				if (allOwners.get(i).isIsprimaryowner())
					numOfprimary++;
				all.put(allOwners.get(i).getRecruiterid(), allOwners.get(i));
			}
			//
			for (Owner jdOwner : owners) {
				CompanyOwner companyOwner = new CompanyOwner();
				companyOwner.setInsertMode(true);
				companyOwner.setCompanyid(companyid);
				companyOwner.setRecruiterid(0L);
				if (jdOwner.getOwnerId() != null) {
					sql = "SELECT ID FROM TRECRUITER WHERE ID = ? AND GROUPID = ?  ";
					params = new Object[] { jdOwner.getOwnerId(), jobDivaSession.getTeamId() };
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
					sql = "select ID " //
							+ " from TRECRUITER " //
							+ " where GROUPID = ?  " //
							+ " and nls_upper(FIRSTNAME) = nls_upper(?)  " //
							+ " and nls_upper(LASTNAME) = nls_upper(?)";
					//
					params = new Object[] { jobDivaSession.getTeamId(), jdOwner.getFirstName(), jdOwner.getLastName() };
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
				long recid = companyOwner.getRecruiterid();
				//
				if (jdOwner.getAction() == 2) {
					if (all.containsKey(recid)) {
						CompanyOwner owner = all.get(recid);
						String sqlDelete = "DELETE FROM TCUSTOMER_COMPANY_OWNERS WHERE COMPANYID = ? AND RECRUITERID = ? and TEAMID = ?  ";
						//
						params = new Object[] { owner.getCompanyid(), owner.getRecruiterid(), jobDivaSession.getTeamId() };
						//
						jdbcTemplate.update(sqlDelete, params);
						//
						all.remove(recid);
					}
					if (update.contains(recid))
						update.remove(recid);
					if ((Boolean) jdOwner.getPrimary())
						numOfprimary--;
				} else {
					if ((Boolean) jdOwner.getPrimary()) {
						numOfprimary++;
					}
					update.add(recid);
					if (all.containsKey(recid)) {
						all.get(recid).setIsprimaryowner((Boolean) jdOwner.getPrimary());
					} else {
						all.put(recid, companyOwner);
					}
				}
			}
			//
			if (numOfprimary > 1)
				throw new Exception("There are more than one primary owners.");
			//
			for (Long u : update) {
				CompanyOwner companyOwner = all.get(u);
				//
				if (companyOwner.getInsertMode() != null && companyOwner.getInsertMode()) {
					String sqlInsert = "INSERT INTO TCUSTOMER_COMPANY_OWNERS(COMPANYID, RECRUITERID, ISPRIMARYOWNER, TEAMID) "//
							+ "VALUES " //
							+ "(?,?,?,?)";
					params = new Object[] { companyOwner.getCompanyid(), companyOwner.getRecruiterid(), companyOwner.getIsprimaryowner(), companyOwner.getTeamid() };
					//
					jdbcTemplate.update(sqlInsert, params);
				} else {
					String sqlInsert = "UPDATE TCUSTOMER_COMPANY_OWNERS  SET ISPRIMARYOWNER = ? WHERE COMPANYID = ? AND  RECRUITERID = ? AND TEAMID = ? ";//
					//
					params = new Object[] { companyOwner.getIsprimaryowner(), companyOwner.getCompanyid(), companyOwner.getRecruiterid(), companyOwner.getTeamid() };
					//
					jdbcTemplate.update(sqlInsert, params);
				}
			}
		} // don't set TCUSTOMER_OWNERS if not specified?
	}
	
	private Byte getInvoiceContactPreferenceId(String str) {
		Byte b = null;
		if (str.equalsIgnoreCase(ShowOnInvoiceType.ADDRESS.getValue()))
			b = new Byte("0");
		else if (str.equalsIgnoreCase(ShowOnInvoiceType.EMAIL.getValue()))
			b = new Byte("1");
		else if (str.equalsIgnoreCase(ShowOnInvoiceType.DO_NOT_SEND.getValue()))
			b = new Byte("2");
		return b;
	}
	
	private Byte getEntryFormatId(String str) {
		Byte b = null;
		if (str.equalsIgnoreCase(TimeSheetEntryFormatType.MINUTE.getValue()))
			b = new Byte("1");
		else if (str.equalsIgnoreCase(TimeSheetEntryFormatType.QUARTER.getValue()))
			b = new Byte("2");
		else if (str.equalsIgnoreCase(TimeSheetEntryFormatType.TIME_IN_TIME_OUT.getValue()))
			b = new Byte("3");
		else if (str.equalsIgnoreCase(TimeSheetEntryFormatType.DEFAULT_WHEN_BILLING_UNIT_IS_DAILY.getValue()))
			b = new Byte("4");
		return b;
	}
	
	private Short getFinancialFrequencyId(String str) {
		Short b = null;
		if (str.equalsIgnoreCase(FrequencyType.BI_WEEKLY.getValue()))
			b = new Short("1");
		else if (str.equalsIgnoreCase(FrequencyType.MONTHLY.getValue()))
			b = new Short("2");
		else if (str.equalsIgnoreCase(FrequencyType.SEMI_MONTHLY.getValue()))
			b = new Short("3");
		else if (str.equalsIgnoreCase(FrequencyType.WEEKLY.getValue()))
			b = new Short("4");
		else if (str.equalsIgnoreCase(FrequencyType.WHOLE_PROJECT.getValue()))
			b = new Short("5");
		else if (str.equalsIgnoreCase(FrequencyType.MONTHLY_ENDING_WEEKEND.getValue()))
			b = new Short("6");
		return b;
	}
	
	private Short getFinancialWeekendingId(String str) {
		Short b = null;
		if (str.equalsIgnoreCase(WeekendingType.SUNDAY.getValue()))
			b = new Short("1");
		else if (str.equalsIgnoreCase(WeekendingType.SATURDAY.getValue()))
			b = new Short("2");
		else if (str.equalsIgnoreCase(WeekendingType.FRIDAY.getValue()))
			b = new Short("3");
		else if (str.equalsIgnoreCase(WeekendingType.THURSDAY.getValue()))
			b = new Short("4");
		else if (str.equalsIgnoreCase(WeekendingType.WEDNESDAY.getValue()))
			b = new Short("5");
		else if (str.equalsIgnoreCase(WeekendingType.TUESDAY.getValue()))
			b = new Short("6");
		else if (str.equalsIgnoreCase(WeekendingType.MONDAY.getValue()))
			b = new Short("7");
		return b;
	}
	
	private Short getFinancialBillingUnitId(String str) {
		Short b = null;
		if (str.equalsIgnoreCase(BillingUnitType.HOURLY.getValue()))
			b = new Short("1");
		else if (str.equalsIgnoreCase(BillingUnitType.DAILY_BASED_ON_HOURS.getValue()))
			b = new Short("2");
		else if (str.equalsIgnoreCase(BillingUnitType.DAILY_HALF_DAY.getValue()))
			b = new Short("3");
		else if (str.equalsIgnoreCase(BillingUnitType.DAILY_HALF_DAY_OT.getValue()))
			b = new Short("4");
		else if (str.equalsIgnoreCase(BillingUnitType.DAILY_BILL_WHOLE_DAY.getValue()))
			b = new Short("5");
		else if (str.equalsIgnoreCase(BillingUnitType.DAILY_BILL_WHOLE_DAY_OT.getValue()))
			b = new Short("6");
		return b;
	}
	
	private Short getFinancialInvoiceGroupIndexId(String str) {
		Short b = null;
		if (str.equalsIgnoreCase(GroupInvoiceByType.JOB.getValue()))
			b = new Short("1");
		else if (str.equalsIgnoreCase(GroupInvoiceByType.COMPANY.getValue()))
			b = new Short("2");
		else if (str.equalsIgnoreCase(GroupInvoiceByType.PO_NUMBER.getValue()))
			b = new Short("4");
		return b;
	}
	
	private void checkAddresses(CompanyAddress[] addresses) throws Exception {
		if (addresses != null) {
			for (CompanyAddress comp_add : addresses) {
				//
				if (comp_add.getAction() != 1 && comp_add.getAction() != 2)
					throw new Exception("Error: Action code should be 1(insert/modify) or 2(delete) \r\n");
				if (comp_add.getAction() == 2 && comp_add.getAddressId() == null)
					throw new Exception("Error: Please set addressid if intend to delete a address. \r\n");
				if (isNotEmpty(comp_add.getAddress1())) {
					if (comp_add.getAddress1().length() > 100)
						throw new Exception("Error: Address1 should be no more than 100 characters. \r\n");
				}
				//
				if (isNotEmpty(comp_add.getAddress2())) {
					if (comp_add.getAddress2().length() > 200)
						throw new Exception("Error: Address2 should be no more than 200 characters. \r\n");
				}
				//
				if (comp_add.getAddressId() != null) {
					if (comp_add.getAddressId() < 1)
						throw new Exception("Error: Address id should be a positive number.");
				}
				//
				if (isNotEmpty(comp_add.getCity())) {
					if (comp_add.getCity().length() > 100)
						throw new Exception("Error: City shoule be no more than 100 characters. \r\n");
				}
				if (isNotEmpty(comp_add.getCountryid())) {
					comp_add.setCountryid(getCountryID(comp_add.getCountryid()));
					if (comp_add.getCountryid().equals("not found"))
						throw new Exception("Error: Address cannot be updated due to the 'country' parameter mapping unfound. \r\n");// jd_comp_add.localCountryidTracker
																																		// =
																																		// false;
				}
				if (isNotEmpty(comp_add.getEmail())) {
					if (comp_add.getEmail().length() > 100)
						throw new Exception("Error: Email should be no more than 100 characters. \r\n");
				}
				if (isNotEmpty(comp_add.getFax())) {
					String fax = Pattern.compile(NON_DIGITS).matcher(comp_add.getFax()).replaceAll("");
					if (fax.length() > 20)
						throw new Exception("Error: 'fax' should be no more than 20 characters. \r\n");
					else if (fax.length() > 0)
						comp_add.setFax(fax);
					else
						comp_add.setFax(null);
				}
				//
				if (isNotEmpty(comp_add.getPhone())) {
					String phone = Pattern.compile(NON_DIGITS).matcher(comp_add.getPhone()).replaceAll("");
					if (phone.length() > 20)
						throw new Exception("Error: 'phone' should be no more than 20 characters. \r\n");
					else if (phone.length() > 0)
						comp_add.setPhone(phone);
					else
						comp_add.setPhone(null);
				}
				//
				if (isNotEmpty(comp_add.getUrl())) {
					if (comp_add.getUrl().length() > 200)
						throw new Exception("Error: URL should be no more than 200 characters. \r\n");
				}
				if (isNotEmpty(comp_add.getZipcode())) {
					if (comp_add.getZipcode().length() > 20)
						throw new Exception("Error: Zipcode shoule be no more than 20 characters. \r\n");
				}
			}
		}
	}
	
	private void checkCompanyOwners(Owner[] owners) throws Exception {
		if (owners != null)
			for (Owner jdOwner : owners) {
				if (jdOwner.getAction() == null) {
					throw new Exception("Have to set action code to update company owner");
				} else {
					boolean hasOwner = false;
					if (jdOwner.getAction() != 1 && jdOwner.getAction() != 2)
						throw new Exception("Error: Action code should be 1(insert/modify) or 2(delete) \r\n");
					if (jdOwner.getOwnerId() != null) {
						hasOwner = true;
					}
					if (isNotEmpty(jdOwner.getFirstName()) && isNotEmpty(jdOwner.getLastName())) {
						jdOwner.setFirstName(jdOwner.getFirstName().toUpperCase().trim());
						jdOwner.setLastName(jdOwner.getLastName().toUpperCase().trim());
						hasOwner = true;
					}
					if (!hasOwner)
						throw new Exception("Error: Please set either owner id or owner name(both first name and last name) if intend to declare the owners for contacts. \r\n");
				}
			}
	}
	
	public Boolean updateCompany(JobDivaSession jobDivaSession, Long companyid, String name, Long parentcompanyid, CompanyAddress[] addresses, String subguidelines, //
			Integer maxsubmittals, Boolean references, Boolean drugtest, Boolean backgroundcheck, Boolean securityclearance, Userfield[] userfields, //
			Double discount, String discountper, Double percentagediscount, FinancialsType financials, Owner[] owners, String nameIndex) throws Exception {
		//
		StringBuffer message = new StringBuffer();
		//
		if (isNotEmpty(name)) {
			if (name.length() > 100)
				message.append("Error: Company name should be no more than 100 characters. \r\n");
			else if (name.trim().length() == 0)
				name = null;
		}
		//
		if (parentcompanyid != null && parentcompanyid < 1) {
			throw new ELException("Invalid parentCompanyId : " + parentcompanyid);
		}
		//
		if (isNotEmpty(subguidelines)) {
			if (subguidelines.trim().length() == 0)
				subguidelines = null;
		}
		//
		if (maxsubmittals != null) {
			if (maxsubmittals < 0)
				message.append("Error: Max Allowed Submittals should not be smaller than 0. \r\n");
			if (maxsubmittals > 99)
				message.append("Error: Max Allowed Submittals should be less than 99. \r\n");
		}
		//
		if (discount != null && discount < 0)
			message.append("Error: Please specify a valid positive number in the field 'discount'. \r\n");
		//
		if (isNotEmpty(discountper)) {
			if (discountper.length() > 20)
				message.append("Error: Discountper should not be more than 20 characters. \r\n");
			else if (discountper.trim().length() == 0)
				discountper = null;
		}
		//
		if (percentagediscount != null) {
			if (percentagediscount < 0)
				message.append("Error: Please specify a valid positive number in the field 'percentagediscount'. \r\n");
			if (percentagediscount >= 100.0)
				message.append("Error: Please specify a valid number less than 100 in the field 'percentagediscount'. \r\n");
		}
		//
		if (financials != null) {
			if (financials.getPaymentterms() != null && financials.getPaymentterms() < 0)
				message.append("Error: Please enter a valid number for Payment Terms. \r\n");
			if (financials.getHours() != null && financials.getHours() < 0)
				message.append("Error: Please enter a valid number for . \r\n");
			if (isNotEmpty(financials.getInvoicecomment()) && financials.getInvoicecomment().length() > 2000)
				message.append("Error: Invoice comment should be less than 2000 characters. \r\n");
		}
		//
		//
		//
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed  " + message.toString());
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		checkAddresses(addresses);
		checkCompanyOwners(owners);
		//
		//
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		//
		if (isNotEmpty(name)) {
			fields.put("NAME", "name");
			parameterSource.addValue("name", name);
			fields.put("NAME_INDEX", "nameindex");
			parameterSource.addValue("nameindex", name.toUpperCase());
		}
		//
		if (parentcompanyid != null) {
			fields.put("PARENT_COMPANYID", "parentcompanyId");
			parameterSource.addValue("parentcompanyId", parentcompanyid);
		}
		//
		if (isNotEmpty(subguidelines)) {
			fields.put("SUBMITTAL_INSTR", "submittalInstr");
			parameterSource.addValue("submittalInstr", new JobDivaSqlLobValue(subguidelines, new DefaultLobHandler()), Types.CLOB);
		}
		//
		if (maxsubmittals != null) {
			fields.put("MAXSUBMITALS", "maxsubmittals");
			parameterSource.addValue("maxsubmittals", maxsubmittals);
		}
		//
		if (references != null) {
			fields.put("REFCHECK", "references");
			parameterSource.addValue("references", maxsubmittals);
		}
		//
		if (drugtest != null) {
			fields.put("DRUGTEST", "drugtest");
			parameterSource.addValue("drugtest", drugtest);
		}
		//
		if (backgroundcheck != null) {
			fields.put("DRUGTEST", "backgroundcheck");
			parameterSource.addValue("backgroundcheck", backgroundcheck);
		}
		//
		if (securityclearance != null) {
			fields.put("SECCLEARANCE", "securityclearance");
			parameterSource.addValue("securityclearance", securityclearance);
		}
		//
		if (discount != null) {
			fields.put("DISCOUNT", "discount");
			parameterSource.addValue("discount", discount);
		}
		//
		if (isNotEmpty(discountper)) {
			String per = discountper.toUpperCase();
			Integer intDiscountType = 0;
			if (per.equals("H"))
				intDiscountType = 0;
			else if (per.equals("D"))
				intDiscountType = 1;
			else if (per.equals("M"))
				intDiscountType = 2;
			else if (per.equals("Y"))
				intDiscountType = 3;
			else
				throw new Exception("Error: Please enter a valid unit('H', 'D', 'M' or 'Y') to the field 'discountper'. \r\n");
			//
			fields.put("DISCOUNT_TYPE", "discountper");
			parameterSource.addValue("discountper", intDiscountType);
		}
		//
		if (percentagediscount != null) {
			fields.put("DISCOUNTPCT", "percentagediscount");
			parameterSource.addValue("percentagediscount", percentagediscount);
		}
		//
		if (financials != null) {
			if (financials.getShowOnInvoiceType() != null) {
				fields.put("INVOICE_CONTACT_PREFERENCE", "INVOICE_CONTACT_PREFERENCE");
				parameterSource.addValue("INVOICE_CONTACT_PREFERENCE", getInvoiceContactPreferenceId(financials.getShowOnInvoiceType().getValue()));
			}
			//
			if (financials.getTimesheetentryformat() != null) {
				fields.put("ENTRYFORMAT", "ENTRYFORMAT");
				parameterSource.addValue("ENTRYFORMAT", getEntryFormatId(financials.getTimesheetentryformat().getValue()));
			}
			//
			if (financials.getFrequency() != null) {
				fields.put("FINANCIAL_FREQUENCY", "FINANCIAL_FREQUENCY");
				parameterSource.addValue("FINANCIAL_FREQUENCY", getFinancialFrequencyId(financials.getFrequency().getValue()));
			}
			//
			if (financials.getWeekending() != null) {
				fields.put("FINANCIAL_WEEKENDING", "FINANCIAL_WEEKENDING");
				parameterSource.addValue("FINANCIAL_WEEKENDING", getFinancialWeekendingId(financials.getWeekending().getValue()));
			}
			//
			if (financials.getHours() != null) {
				fields.put("FINANCIAL_HOURS", "FINANCIAL_HOURS");
				parameterSource.addValue("FINANCIAL_HOURS", (float) (Math.round(financials.getHours() * 100) / 100.0));
			}
			//
			if (financials.getBillingUnit() != null) {
				fields.put("FINANCIAL_BILLINGUNIT", "FINANCIAL_BILLINGUNIT");
				parameterSource.addValue("FINANCIAL_BILLINGUNIT", getFinancialBillingUnitId(financials.getBillingUnit().getValue()));
			}
			//
			if (financials.getGroupInvoiceByType() != null) {
				fields.put("FINANCIAL_INVOICE_GROUP_INDEX", "FINANCIAL_INVOICE_GROUP_INDEX");
				parameterSource.addValue("FINANCIAL_INVOICE_GROUP_INDEX", getFinancialInvoiceGroupIndexId(financials.getGroupInvoiceByType().getValue()));
			}
			//
			if (financials.getPaymentterms() != null) {
				fields.put("PAYMENTTERMS", "PAYMENTTERMS");
				parameterSource.addValue("PAYMENTTERMS", financials.getPaymentterms());
			}
			//
			if (financials.getInvoicecomment() != null) {
				fields.put("FINANCIAL_INVOICECOMMENT", "FINANCIAL_INVOICECOMMENT");
				parameterSource.addValue("FINANCIAL_INVOICECOMMENT", financials.getInvoicecomment());
			}
			//
		}
		//
		//
		Short pipelineId = checkNameIndex(jobDivaSession, nameIndex, jdbcTemplate);
		if (pipelineId != null) {
			fields.put("PIPELINE_ID", "pipelineId");
			parameterSource.addValue("pipelineId", pipelineId);
		}
		//
		updateCompanyAddress(jobDivaSession, companyid, addresses);
		//
		updateCompanyUserFields(jobDivaSession, companyid, userfields);
		//
		updateCompanyOwner(jobDivaSession, companyid, owners);
		//
		String sqlUpdate = " UPDATE TCUSTOMERCOMPANY SET " + sqlUpdateFields(fields) + " WHERE ID = :companyId and TEAMID = :teamId ";
		parameterSource.addValue("companyId", companyid);
		parameterSource.addValue("teamId", jobDivaSession.getTeamId());
		//
		//
		NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		jdbcTemplateObject.update(sqlUpdate, parameterSource);
		//
		return true;
	}
}

package com.jobdiva.api.dao.invoice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.activity.ActivityUserFieldsDao;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class UpdateBillingRecordDao extends AbstractJobDivaDao {
	
	//
	@Autowired
	ActivityUserFieldsDao activityUserFieldsDao;
	
	private void checkRequiredFields(Double assignmentID, Long candidateID) throws Exception {
		StringBuffer messageError = new StringBuffer();
		//
		//
		if (assignmentID == null || assignmentID <= 0) {
			messageError.append("assignmentID is required. \r\n ");
		}
		//
		if (candidateID == null || candidateID <= 0) {
			messageError.append("assignmentID is required. \r\n ");
		}
		//
		if (messageError.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + messageError.toString());
		}
	}
	
	public Boolean updateBillingRecord(JobDivaSession jobDivaSession, Boolean allowEnterTimeOnPortal, Boolean approved, Double assignmentID, Long billingContactID, Integer billingUnit, Double billRate, Integer billRateCurrrency, String billRatePer,
			Long candidateID, String customerRefNo, Long division, Double doubletimePer, Double doubletimeRate, String doubletimeRatePer, Boolean enableTimesheet, Date endDate, Boolean expenseEnabled, Integer expenseInvoices, Integer frequency,
			Integer biweeklySchedule, Long hiringManagerID, Double hoursPerDay, Double hoursPerHalfDay, Integer invoiceContent, String invoiceGroup, Integer invoiceGroupIndex, Double jobID, Integer overtimeByWorkingState, Boolean overtimeExempt,
			Double overtimeRate, String overtimeRatePer, String paymentTerms, Long primaryRecruiterID, Double primaryRecruiterPercentage, Double primarySalesPercentage, Long primarySalesPersonID, Integer recordID, Long secondaryRecruiterID,
			Double secondaryRecruiterPercentage, Double secondarySalesPercentage, Long secondarySalesPersonID, Date startDate, Long tertiaryRecruiterID, Double tertiaryRecruiterPercentage, Double tertiarySalesPercentage, Long tertiarySalesPersonID,
			Long timesheetEntryFormat, String timesheetInstruction, String vMSEmployeeName, String vMSWebsite, Integer weekEnding, String workAddress1, String workAddress2, String workCity, String workCountry, String workState, String workZipcode,
			Userfield[] userfields) throws Exception {
		//
		//
		//
		checkRequiredFields(assignmentID, candidateID);
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		// recid is null. Use max(recid) instead...
		if (recordID == null) {
			String sql = " SELECT NVL(MAX(recid), -1) as RECID " //
					+ " FROM Temployee_billingrecord " //
					+ " WHERE employeeid = ? " //
					+ " AND recruiter_teamid = ? " //
					+ " AND interviewid = ? " //
					+ " AND parent_recid is NULL";
			//
			Object[] params = new Object[] { candidateID, jobDivaSession.getTeamId(), assignmentID };
			//
			List<Integer> list = jdbcTemplate.query(sql, params, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getInt("RECID");
				}
			});
			//
			if (list != null && list.size() > 0) {
				recordID = list.get(0);
			}
			if (recordID == null || recordID < 0) {
				throw new Exception("Unable to find billing record under candidate ID " + candidateID + " and assignment ID " + assignmentID);
			}
		}
		//
		//
		//
		//
		// Check for approve / unapprove conditions
		if (approved != null) {
			//
			//
			String sql = " SELECT billing_contact, billing_unit, bill_rate,bill_rate_per, BILLRATEPER_CURRENCY, DIVISION, frequency, HOURS_PER_DAY, timesheet_entry_format, overtimeexempt, " //
					+ " overtime_rate1, overtime_rate1_per, primary_salesperson, start_date, week_ending, WORK_STATE, approved FROM  temployee_billingrecord " //
					+ " Where employeeid = ? and recruiter_teamid = ? and recid = ?";
			Object[] params = new Object[] { candidateID, jobDivaSession.getTeamId(), recordID };
			List<Map<String, Object>> list = jdbcTemplate.query(sql, params, new RowMapper<Map<String, Object>>() {
				
				@Override
				public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("billing_contact", rs.getLong("billing_contact"));
					map.put("billing_unit", rs.getInt("billing_unit"));
					map.put("bill_rate", rs.getDouble("bill_rate"));
					map.put("bill_rate_per", rs.getString("bill_rate_per"));
					map.put("BILLRATEPER_CURRENCY", rs.getInt("BILLRATEPER_CURRENCY"));
					map.put("DIVISION", rs.getLong("DIVISION"));
					map.put("frequency", rs.getInt("frequency"));
					map.put("HOURS_PER_DAY", rs.getDouble("HOURS_PER_DAY"));
					map.put("overtime_rate1", rs.getDouble("overtime_rate1"));
					map.put("overtime_rate1_per", rs.getString("overtime_rate1_per"));
					map.put("primary_salesperson", rs.getLong("primary_salesperson"));
					map.put("start_date", rs.getDate("start_date"));
					map.put("week_ending", rs.getInt("week_ending"));
					map.put("WORK_STATE", rs.getString("WORK_STATE"));
					map.put("approved", rs.getBoolean("approved"));
					map.put("timesheet_entry_format", rs.getLong("timesheet_entry_format"));
					map.put("overtimeexempt", rs.getBoolean("overtimeexempt"));
					return map;
				}
			});
			//
			Map<String, Object> map = list != null && list.size() > 0 ? list.get(0) : new HashMap<String, Object>();
			//
			Boolean dbApproved = (Boolean) map.get("approved");
			//
			//
			if (!approved.equals(dbApproved)) {
				//
				//
				if (approved) {
					//
					ArrayList<String> message = new ArrayList<String>();
					//
					// start date
					Date dbstart_date = (Date) map.get("start_date");
					if (startDate == null && dbstart_date == null) {
						message.add("StartDate");
					}
					// billingcontact
					Long dbbilling_contact = (Long) map.get("billing_contact");
					if (billingContactID == null && (dbbilling_contact == null || dbbilling_contact <= 0)) {
						message.add("billingContactID");
					}
					// division
					Long dbDIVISION = (Long) map.get("DIVISION");
					if (division == null && (dbDIVISION == null || dbDIVISION <= 0)) {
						message.add("division");
					}
					// billRateCurrrency
					Integer dbBILLRATEPER_CURRENCY = (Integer) map.get("BILLRATEPER_CURRENCY");
					if (billRateCurrrency == null && (dbBILLRATEPER_CURRENCY == null || dbBILLRATEPER_CURRENCY <= 0)) {
						message.add("billRateCurrrency");
					}
					// bill_rate
					Double dbbill_rate = (Double) map.get("bill_rate");
					if (billRate == null && (dbbill_rate == null || dbbill_rate <= 0)) {
						message.add("billRate");
					}
					// bill_rate_per
					String dbbill_rate_per = (String) map.get("bill_rate_per");
					if (isEmpty(billRatePer) && isEmpty(dbbill_rate_per)) {
						message.add("billRatePer");
					}
					//
					Boolean dbovertimeExempt = (Boolean) map.get("overtimeexempt");
					if (overtimeExempt == null) {
						overtimeExempt = dbovertimeExempt;
					}
					overtimeExempt = overtimeExempt != null ? overtimeExempt : false;
					//
					if (!overtimeExempt) {
						// overtime_rate1
						Double dbovertime_rate1 = (Double) map.get("overtime_rate1");
						if (overtimeRate == null && (dbovertime_rate1 == null || dbovertime_rate1 <= 0)) {
							message.add("overtimeRate");
						}
						// overtime_rate1_per
						String dbovertime_rate1_per = (String) map.get("overtime_rate1_per");
						if (isEmpty(overtimeRatePer) && isEmpty(dbovertime_rate1_per)) {
							message.add("overtimeRatePer");
						}
					}
					//
					// frequency
					Integer dbfrequency = (Integer) map.get("frequency");
					if (frequency == null && (dbfrequency == null || dbfrequency <= 0)) {
						message.add("frequency");
					}
					// billing unit
					Integer dbbilling_unit = (Integer) map.get("billing_unit");
					if (billingUnit == null && (dbbilling_unit == null || dbbilling_unit <= 0)) {
						message.add("billingUnit");
					}
					// weekending ate
					Integer dbweek_ending = (Integer) map.get("week_ending");
					if (weekEnding == null && (dbweek_ending == null || dbweek_ending <= 0)) {
						message.add("weekEnding");
					}
					// hoursPerDay
					Double dbHOURS_PER_DAY = (Double) map.get("HOURS_PER_DAY");
					if (hoursPerDay == null && (dbHOURS_PER_DAY == null || dbHOURS_PER_DAY <= 0)) {
						message.add("hoursPerDay");
					}
					// workState
					String dbWORK_STATE = (String) map.get("WORK_STATE");
					if (isEmpty(workState) && isEmpty(dbWORK_STATE)) {
						message.add("workState");
					}
					// primary sales
					Long dbprimary_salesperson = (Long) map.get("primary_salesperson");
					if (primarySalesPersonID == null && (dbprimary_salesperson == null || dbprimary_salesperson <= 0)) {
						message.add("primarySalesPersonID");
					}
					// timesheet entry format
					Long dbtimesheet_entry_format = (Long) map.get("timesheet_entry_format");
					if (timesheetEntryFormat == null && (dbtimesheet_entry_format == null || dbtimesheet_entry_format <= 0)) {
						message.add("timesheetEntryFormat");
					}
					//
					if (message.size() > 0) {
						String error = StringUtils.join(message, ",");
						throw new Exception(error + " are required to approve this billing.");
					}
					//
					//
					//
					//
				} else {
					//
					sql = "SELECT invoiceid, void FROM temployee_invoice WHERE employeeid = ? AND recruiter_teamid = ? AND billingid = ? ";
					params = new Object[] { candidateID, jobDivaSession.getTeamId(), recordID };
					List<List<Object>> invoices = jdbcTemplate.query(sql, params, new RowMapper<List<Object>>() {
						
						@Override
						public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							List<Object> list = new ArrayList<Object>();
							list.add(rs.getLong("INVOICEID"));
							list.add(rs.getBoolean("void"));
							return list;
						}
					});
					//
					if (invoices != null && invoices.size() > 0) {
						Long invoiceId = (Long) invoices.get(0).get(0);
						Boolean isVoided = (Boolean) invoices.get(0).get(1);
						if (!isVoided) {
							throw new Exception("Invoice " + invoiceId + " need to be voided before unapprove it");
							//
						}
					}
					//
				}
				//
				//
				//
				//
			}
		}
		//
		//
		//
		//
		//
		//
		//
		//
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		if (approved != null) {
			fields.add("approved");
			paramList.add(approved);
			//
			if (approved) {
				fields.add("APPROVERID");
				paramList.add(jobDivaSession.getRecruiterId());
			}
		}
		//
		if (startDate != null) {
			fields.add("start_date");
			paramList.add(new Timestamp(startDate.getTime()));
		}
		//
		if (endDate != null) {
			fields.add("end_date");
			paramList.add(new Timestamp(endDate.getTime()));
		}
		if (primarySalesPersonID != null) {
			fields.add("primary_salesperson");
			paramList.add(primarySalesPersonID);
		}
		//
		if (customerRefNo != null) {
			fields.add("CUSTOMER_REFNO");
			paramList.add(customerRefNo);
		}
		//
		if (hiringManagerID != null) {
			fields.add("hiring_manager");
			paramList.add(hiringManagerID);
		}
		//
		if (billingContactID != null) {
			fields.add("billing_contact");
			paramList.add(billingContactID);
		}
		//
		if (division != null) {
			fields.add("DIVISION");
			paramList.add(division);
		}
		//
		if (invoiceGroupIndex != null) {
			fields.add("INVOICE_GROUP_INDEX");
			paramList.add(invoiceGroupIndex);
		}
		//
		if (invoiceGroup != null) {
			fields.add("INVOICE_GROUP");
			paramList.add(invoiceGroup);
		}
		//
		if (vMSWebsite != null) {
			fields.add("vms_website");
			paramList.add(vMSWebsite);
		}
		//
		if (vMSEmployeeName != null) {
			fields.add("vmsEmployeeName");
			paramList.add(vMSEmployeeName);
		}
		//
		if (invoiceContent != null) {
			fields.add("GROUPING_EXPENSES");
			paramList.add(invoiceContent);
		}
		//
		if (expenseInvoices != null) {
			fields.add("EXPENSE_DISPLAY_CATEGORYTOTAL");
			paramList.add(expenseInvoices);
		}
		//
		if (enableTimesheet != null) {
			fields.add("ENABLE_TIMESHEET");
			paramList.add(enableTimesheet);
		}
		//
		if (allowEnterTimeOnPortal != null) {
			fields.add("ALLOW_ENTER_TIME_ON_PORTAL");
			paramList.add(allowEnterTimeOnPortal);
		}
		//
		if (timesheetInstruction != null) {
			fields.add("TIMESHEET_INSTRUCTION");
			paramList.add(timesheetInstruction);
		}
		//
		if (expenseEnabled != null) {
			fields.add("expenseenabled");
			paramList.add(expenseEnabled);
		}
		//
		if (billRate != null) {
			fields.add("bill_rate");
			paramList.add(billRate);
		}
		//
		if (billRateCurrrency != null) {
			fields.add("BILLRATEPER_CURRENCY");
			paramList.add(billRateCurrrency);
		}
		//
		if (billRatePer != null) {
			fields.add("bill_rate_per");
			paramList.add(billRatePer);
		}
		//
		if (overtimeExempt != null) {
			fields.add("overtimeexempt");
			paramList.add(overtimeExempt);
		}
		//
		if (timesheetEntryFormat != null) {
			fields.add("timesheet_entry_format");
			paramList.add(timesheetEntryFormat);
		}
		//
		if (frequency != null) {
			fields.add("frequency");
			paramList.add(frequency);
		}
		//
		if (overtimeByWorkingState != null) {
			fields.add("OT_BY_WORKING_STATE");
			paramList.add(overtimeByWorkingState);
		}
		//
		if (overtimeRate != null) {
			fields.add("overtime_rate1");
			paramList.add(overtimeRate);
		}
		//
		if (overtimeRatePer != null) {
			fields.add("overtime_rate1_per");
			paramList.add(overtimeRatePer);
		}
		//
		if (doubletimeRate != null) {
			fields.add("overtime_rate2");
			paramList.add(doubletimeRate);
		}
		//
		if (doubletimeRatePer != null) {
			fields.add("overtime_rate2_per");
			paramList.add(doubletimeRatePer);
		}
		//
		if (billingUnit != null) {
			fields.add("billing_unit");
			paramList.add(billingUnit);
		}
		//
		if (weekEnding != null) {
			fields.add("week_ending");
			paramList.add(weekEnding);
		}
		//
		if (hoursPerDay != null) {
			fields.add("HOURS_PER_DAY");
			paramList.add(hoursPerDay);
		}
		//
		if (hoursPerHalfDay != null) {
			fields.add("HOURS_PER_HALF_DAY");
			paramList.add(hoursPerHalfDay);
		}
		//
		if (workAddress1 != null) {
			fields.add("WORK_ADDRESS1");
			paramList.add(workAddress1);
		}
		//
		if (workAddress2 != null) {
			fields.add("WORK_ADDRESS2");
			paramList.add(workAddress2);
		}
		//
		if (workCity != null) {
			fields.add("WORK_CITY");
			paramList.add(workCity);
			//
			fields.add("WORKING_CITY");
			paramList.add(workCity);
			//
		}
		//
		if (workState != null) {
			fields.add("WORK_STATE");
			paramList.add(workState);
			//
			fields.add("WORKING_STATE");
			paramList.add(workState);
		}
		//
		if (workZipcode != null) {
			fields.add("WORK_ZIP");
			paramList.add(workZipcode);
		}
		//
		if (workCountry != null) {
			fields.add("WORKING_COUNTRY");
			paramList.add(workCountry);
			//
			fields.add("WORK_COUNTRY");
			paramList.add(workCountry);
		}
		//
		if (paymentTerms != null) {
			fields.add("paymentterms");
			paramList.add(paymentTerms);
		}
		//
		if (primarySalesPercentage != null) {
			fields.add("PRIMARY_SALESPERSON");
			paramList.add(primarySalesPercentage);
		}
		//
		if (primarySalesPercentage != null) {
			fields.add("PRISALE_COMM_PERCENT");
			paramList.add(primarySalesPercentage);
		}
		//
		if (secondarySalesPersonID != null) {
			fields.add("SECONDARY_SALESPERSON");
			paramList.add(secondarySalesPersonID);
		}
		//
		if (secondarySalesPercentage != null) {
			fields.add("SECSALE_COMM_PERCENT");
			paramList.add(secondarySalesPercentage);
		}
		//
		if (tertiarySalesPersonID != null) {
			fields.add("TERTIARY_SALESPERSON");
			paramList.add(tertiarySalesPersonID);
		}
		//
		if (tertiarySalesPercentage != null) {
			fields.add("TERSALE_COMM_PERCENT");
			paramList.add(tertiarySalesPercentage);
		}
		//
		if (primaryRecruiterID != null) {
			fields.add("PRIMARY_RECRUITER");
			paramList.add(primaryRecruiterID);
		}
		//
		if (primaryRecruiterPercentage != null) {
			fields.add("PRIREC_COMM_PERCENT");
			paramList.add(primaryRecruiterPercentage);
		}
		//
		if (secondaryRecruiterID != null) {
			fields.add("SECONDARY_RECRUITER");
			paramList.add(secondaryRecruiterID);
		}
		//
		if (secondaryRecruiterPercentage != null) {
			fields.add("SECREC_COMM_PERCENT");
			paramList.add(secondaryRecruiterPercentage);
		}
		//
		if (tertiaryRecruiterID != null) {
			fields.add("TERTIARY_RECRUITER");
			paramList.add(tertiaryRecruiterID);
		}
		//
		if (tertiaryRecruiterPercentage != null) {
			fields.add("TERREC_COMM_PERCENT");
			paramList.add(tertiaryRecruiterPercentage);
		}
		//
		if ((fields.size() > 0) || (userfields != null && userfields.length > 0)) {
			//
			if ((fields.size() > 0)) {
				String sqlUpdate = " UPDATE temployee_billingrecord SET datecreated = sysdate, " + sqlUpdateFields(fields)//
						+ " Where employeeid = ? and interviewid = ? and recruiter_teamid = ? and recid = ?";
				//
				paramList.add(candidateID);
				paramList.add(assignmentID);
				paramList.add(jobDivaSession.getTeamId());
				paramList.add(recordID);
				//
				Object[] parameters = paramList.toArray();
				//
				//
				jdbcTemplate.update(sqlUpdate, parameters);
				//
			}
			//
			//
			//
			//
			if (userfields != null && userfields.length > 0) {
				//
				Date currentTS = new Date();
				Long startId = assignmentID != null ? assignmentID.longValue() : 0L;
				//
				validateUserFields(jobDivaSession, jobDivaSession.getTeamId(), userfields, UDF_FIELDFOR_ACTIVITY);
				//
				//
				for (Userfield userfield : userfields) {
					//
					Boolean existActivityUDF = activityUserFieldsDao.existActivityUDF(jobDivaSession, startId, userfield.getUserfieldId());
					//
					if (isEmpty(userfield.getUserfieldValue())) {
						if (existActivityUDF)
							activityUserFieldsDao.deleteActivityUDF(jobDivaSession, startId, userfield.getUserfieldId());
					} else {
						//
						if (existActivityUDF) {
							activityUserFieldsDao.updateActivityUDF(startId, userfield.getUserfieldId(), jobDivaSession.getTeamId(), currentTS, userfield.getUserfieldValue(), jobDivaSession.getRecruiterId());
						} else {
							activityUserFieldsDao.insertActivityUDF(startId, userfield.getUserfieldId(), jobDivaSession.getTeamId(), currentTS, userfield.getUserfieldValue(), jobDivaSession.getRecruiterId());
						}
					}
				}
			}
			//
			//
		} else {
			throw new Exception("Empty Paramters!");
		}
		//
		return true;
	}
}

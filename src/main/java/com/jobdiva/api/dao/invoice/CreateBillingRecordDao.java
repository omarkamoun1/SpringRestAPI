package com.jobdiva.api.dao.invoice;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.activity.ActivityDao;
import com.jobdiva.api.dao.job.JobDao;
import com.jobdiva.api.model.Activity;
import com.jobdiva.api.model.Job;
import com.jobdiva.api.model.authenticate.JobDivaSession;

/**
 * @author Joseph Chidiac
 *
 */
@Component
public class CreateBillingRecordDao extends AbstractJobDivaDao {
	
	@Autowired
	JobDao		jobDao;
	//
	@Autowired
	ActivityDao	activityDao;
	
	public Integer createBillingRecord(JobDivaSession jobDivaSession, Long candidateID, Long assignmentID, Long jobID, Integer recordID, Long createdByID, Boolean approved, Date startDate, Date endDate, String customerRefNo, Long hiringManagerID,
			Long billingContactID, Long division, Integer invoiceGroupIndex, String invoiceGroup, String vMSWebsite, String vMSEmployeeName, Integer invoiceContent, Integer expenseInvoices, Boolean enableTimesheet, Boolean allowEnterTimeOnPortal,
			String timesheetInstruction, Boolean expenseEnabled, Double billRate, String billRatePer, Boolean overtimeExempt, Long timesheetEntryFormat, Integer frequency, Integer overtimeByWorkingState, Double overtimeRate, String overtimeRatePer,
			Double doubletimeRate, String doubletimePer, Integer billingUnit, Integer weekEnding, Double hoursPerDay, Double hoursPerHalfDay, String workAddress1, String workAddress2, String workCity, String workState, String workZipcode,
			String workCountry, Integer paymentTerms, Long primarySalesPersonID, Double primarySalesPercentage, Long secondarySalesPersonID, Double secondarySalesPercentage, Long tertiarySalesPersonID, Double tertiarySalesPercentage,
			Long primaryRecruiterID, Double primaryRecruiterPercentage, Long secondaryRecruiterID, Double secondaryRecruiterPercentage, Long tertiaryRecruiterID, Double tertiaryRecruiterPercentage) throws Exception {
		//
		int recID = -1;
		// Default RFQID, START_DATE, END_DATE, PO_END_DATE,
		// PRIMARY_RECRUITER, PRIMARY_SALESPERSON,
		// BILL_RATE, BILL_RATE_PER
		// Using Tinterviewschedule
		Activity activity = activityDao.getActivity(jobDivaSession, assignmentID);
		if (activity == null)
			throw new Exception("Error: Assignment " + assignmentID + " is not found.");
		//
		//
		if (startDate == null) {
			if (activity.getDateHired() == null)
				throw new Exception("Please assign a start date to the assignment. ");
			startDate = activity.getDateHired();
		}
		//
		if (endDate == null && activity.getDateEnded() != null)
			endDate = activity.getDateEnded();
		//
		if (endDate == null && activity.getDateEnded() != null)
			endDate = activity.getDateEnded();
		//
		if (primaryRecruiterID == null)
			primaryRecruiterID = activity.getRecruiterId();
		//
		if (primarySalesPersonID == null && activity.getPrimarySalesId() != null)
			primarySalesPersonID = activity.getPrimarySalesId();
		//
		if (billRate == null && activity.getPayHourly() != null && activity.getPayHourly().doubleValue() > 0)
			billRate = activity.getPayHourly().doubleValue();
		//
		if (isEmpty(billRatePer)) {
			if (activity.getFinalBillRateUnit() != null)
				billRatePer = activity.getFinalBillRateUnit().toUpperCase();
			else
				billRatePer = "H";
		}
		//
		String sql = " SELECT count(*) as CNT FROM Temployee_timesheet a, Temployee_billingrecord b " //
				+ " WHERE a.employeeid = b.employeeid AND a.recruiter_teamid = b.recruiter_teamid " + " AND a.billing_recid = b.recid AND a.employeeid = ? AND a.recruiter_teamid = ? " //
				+ " AND nvl(a.invoiceid, 0) > 0 AND a.tdate > ? ";
		//
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(candidateID);
		paramList.add(jobDivaSession.getTeamId());
		paramList.add(startDate);
		if (endDate != null) {
			sql += " AND a.tdate < ? ";
			paramList.add(endDate);
		}
		//
		if (assignmentID != null) {
			sql += " AND b.interviewid = ? ";
			paramList.add(assignmentID);
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		Object[] parameters = paramList.toArray();
		//
		List<Integer> cnts = jdbcTemplate.query(sql, parameters, new RowMapper<Integer>() {
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return rs.getInt("CNT");
			}
		});
		//
		//
		Integer already_invoiced = cnts != null && cnts.size() > 0 ? cnts.get(0) : 0;
		//
		if (already_invoiced > 0)
			throw new Exception("Invoiced timesheets exist.");
		// Default WORK_ADDRESS1, WORK_ADDRESS2, WORK_CITY, WORKING_CITY,
		// WORK_STATE, WORKING_STATE,
		// WORK_ZIP, WORKING_COUNTRY, WORK_COUNTRY, CUSTOMER_REFNO,
		// JOBDIVA_REFNO, DIVISION,
		// PO_START_DATE, PO_END_DATE
		// Using Trfq
		Job job = jobDao.getJob(jobDivaSession, jobID);
		//
		if (isEmpty(workAddress1) && job.getAddress1() != null)
			workAddress1 = job.getAddress1();
		//
		if (isEmpty(workAddress1) && job.getAddress2() != null)
			workAddress1 = job.getAddress2();
		//
		String jobDivaRefNo = null;
		Date poStartDate = null;
		Date poEndDate = null;
		//
		if (isEmpty(workCity) && job.getCity() != null) {
			workCity = job.getCity();
		}
		//
		if (isEmpty(workState) && job.getState() != null) {
			workState = job.getState();
		}
		//
		if (isEmpty(workZipcode) && job.getZipcode() != null)
			workZipcode = job.getZipcode();
		//
		if (isEmpty(workCountry) && job.getCountry() != null) {
			workCountry = job.getCountry();
		}
		//
		if (isEmpty(customerRefNo) && job.getRfqRefNo() != null)
			customerRefNo = job.getRfqRefNo();
		//
		if (job.getRfqNoTeam() != null)
			jobDivaRefNo = job.getRfqNoTeam();
		//
		if (division == null && job.getDivisionId() != null)
			division = job.getDivisionId();
		//
		if (job.getStartDate() != null)
			poStartDate = job.getStartDate();
		//
		if (job.getEndDate() != null)
			poEndDate = job.getEndDate();
		//
		// Default HIRING_MANAGER
		// Using Trfq_customers, Tcustomer
		if (hiringManagerID == null) {
			sql = " SELECT customerid FROM Trfq_customers JobUser LEFT JOIN Tcustomer Users " //
					+ " ON JobUser.customerid = Users.id " //
					+ " AND JobUser.teamid = Users.teamid " //
					+ " WHERE JobUser.rfqid = ? " //
					+ " AND JobUser.teamid = ? " //
					+ " AND JobUser.roleid = 1 ";
			parameters = new Object[] { jobID, jobDivaSession.getTeamId() };
			List<Long> ids = jdbcTemplate.query(sql, parameters, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getLong("customerid");
				}
			});
			//
			if (ids != null && ids.size() > 0)
				hiringManagerID = ids.get(0);
		}
		//
		// Default BILLING_CONTACT
		// Using Trfq_customers, Tcustomer
		if (billingContactID == null) {
			sql = " SELECT customerid " //
					+ " FROM Trfq_customers JobUser " //
					+ " LEFT JOIN Tcustomer Users ON JobUser.customerid = Users.id AND JobUser.teamid = Users.teamid " //
					+ " WHERE JobUser.rfqid = ? " //
					+ " AND JobUser.teamid = ? " //
					+ " AND JobUser.showonjob = 1 ";
			//
			parameters = new Object[] { jobID, jobDivaSession.getTeamId() };
			List<Long> ids = jdbcTemplate.query(sql, parameters, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getLong("customerid");
				}
			});
			//
			if (ids != null && ids.size() > 0)
				billingContactID = ids.get(0);
		}
		//
		//
		if (billingContactID == null && hiringManagerID != null)
			billingContactID = hiringManagerID;
		//
		// Default FREQUENCY, WEEK_ENDING, BILLING_UNIT, HOURS_PER_DAY,
		// PAYMENTTERMS, INVOICE_GROUP_INDEX,
		// TIMESHEET_ENTRY_FORMAT, VMS_WEBSITE, ALLOW_ENTER_TIME_ON_PORTAL,
		// GROUPING_EXPENSES,
		// EXPENSE_DISPLAY_CATEGORYTOTAL
		// Using Tcustomercompany
		if (billingContactID != null) {
			sql = " SELECT nvl(financial_frequency, 0) as financial_frequency, nvl(financial_weekending, 0) as financial_weekending, " //
					+ " nvl(financial_billingunit, 0) as financial_billingunit, nvl(financial_hours, 0) as financial_hours, nvl(paymentterms, 0) as paymentterms, " //
					+ " nvl(financial_invoice_group_index, 0) as financial_invoice_group_index , nvl(entryformat, 0) as entryformat, " //
					+ " financial_vmswebsite, nvl(financial_timesheetonportal, 1) as financial_timesheetonportal, nvl(financial_grouping_expenses, 0) as financial_grouping_expenses, " //
					+ " nvl(financial_expensecategorytotal, 0) as financial_expensecategorytotal, nvl(financial_standard_ot, 0) as financial_standard_ot, nvl(otexempt_billing, 0) as otexempt_billing " //
					+ " FROM Tcustomer Users " //
					+ " LEFT JOIN Tcustomercompany Company ON Users.companyid = Company.id AND Users.teamid = Company.teamid " //
					+ " WHERE Users.id = ?  AND Users.teamid = ?  ";
			//
			parameters = new Object[] {};
			//
			HashMap<String, Object> record = new HashMap<String, Object>();
			//
			jdbcTemplate.query(sql, parameters, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					record.put("financial_frequency", rs.getObject("financial_frequency"));
					record.put("financial_weekending", rs.getObject("financial_weekending"));
					record.put("financial_billingunit", rs.getObject("financial_billingunit"));
					record.put("financial_hours", rs.getObject("financial_hours"));
					record.put("paymentterms", rs.getObject("paymentterms"));
					record.put("financial_invoice_group_index", rs.getObject("financial_invoice_group_index"));
					record.put("entryformat", rs.getObject("entryformat"));
					record.put("financial_vmswebsite", rs.getObject("financial_vmswebsite"));
					record.put("financial_timesheetonportal", rs.getObject("financial_timesheetonportal"));
					record.put("financial_grouping_expenses", rs.getObject("financial_grouping_expenses"));
					record.put("financial_expensecategorytotal", rs.getObject("financial_expensecategorytotal"));
					record.put("financial_standard_ot", rs.getObject("financial_standard_ot"));
					record.put("otexempt_billing", rs.getObject("otexempt_billing"));
					return null;
				}
			});
			if (frequency == null)
				frequency = (Integer) record.get("financial_frequency");
			//
			if (weekEnding == null)
				weekEnding = (Integer) record.get("financial_weekending");
			//
			if (billingUnit == null)
				billingUnit = (Integer) record.get("financial_billingunit");
			//
			if (hoursPerDay == null)
				hoursPerDay = (Double) record.get("financial_hours");
			//
			if (paymentTerms == null)
				paymentTerms = (Integer) record.get("paymentterms");
			//
			if (invoiceGroupIndex == null)
				invoiceGroupIndex = (Integer) record.get("financial_invoice_group_index");
			//
			if (timesheetEntryFormat == null)
				timesheetEntryFormat = (Long) record.get("entryformat");
			//
			if (isEmpty(vMSWebsite)) {
				String localVmSite = (String) record.get("financial_vmswebsite");
				if (localVmSite != null)
					vMSWebsite = localVmSite + "~" + localVmSite;
			}
			//
			if (allowEnterTimeOnPortal == null)
				allowEnterTimeOnPortal = (Boolean) record.get("financial_timesheetonportal");
			//
			// if (!billRecord.containsKey("GROUPING_EXPENSES"))
			// billRecord.put("GROUPING_EXPENSES", query_res[9]);
			// //
			// if (!billRecord.containsKey("EXPENSE_DISPLAY_CATEGORYTOTAL"))
			// billRecord.put("EXPENSE_DISPLAY_CATEGORYTOTAL",
			// query_res[10]);
			//
			Double otRatio = (Double) record.get("financial_standard_ot");
			if (otRatio != null && otRatio > 0) {
				if (doubletimeRate == null)
					doubletimeRate = billRate * otRatio;
				//
				if (doubletimePer == null)
					doubletimePer = billRatePer;
				//
			}
			if (overtimeExempt == null) {
				BigDecimal value = (BigDecimal) record.get("otexempt_billing");
				boolean otExempt = value != null && value.intValueExact() > 0 ? true : false;
				overtimeExempt = otExempt;
			}
		}
		// Default OT_BY_WORKING_STATE, PRIREC_COMM_PERCENT,
		// PRISALE_COMM_PERCENT
		if (overtimeByWorkingState == null) {
			if (overtimeExempt)
				overtimeByWorkingState = 0;
			else
				overtimeByWorkingState = 1;
		}
		//
		if (primaryRecruiterPercentage == null)
			primaryRecruiterPercentage = 100.0;
		//
		if (primarySalesPercentage == null)
			primarySalesPercentage = 100.0;
		//
		//
		// get recID
		sql = " SELECT nvl(max(recid), 0) as RECID " //
				+ " FROM temployee_billingrecord " //
				+ " WHERE employeeid = ? " //
				+ " AND recruiter_teamid = ? ";
		parameters = new Object[] { candidateID, jobDivaSession.getTeamId() };
		List<Integer> ids = jdbcTemplate.query(sql, parameters, new RowMapper<Integer>() {
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return rs.getInt("RECID");
			}
		});
		recID = ids != null && ids.size() > 0 ? ids.get(0) : recID;
		//
		HashMap<String, Object> billingRecordMap = new HashMap<String, Object>();
		billingRecordMap.put("RECRUITER_TEAMID", jobDivaSession.getTeamId());
		// Billing Record required fields
		billingRecordMap.put("EMPLOYEEID", candidateID);
		//
		if (jobDivaRefNo != null)
			billingRecordMap.put("JOBDIVA_REFNO", jobDivaRefNo);
		if (poStartDate != null)
			billingRecordMap.put("PO_START_DATE", poStartDate);
		if (poEndDate != null)
			billingRecordMap.put("PO_END_DATE", poEndDate);
		if (frequency != null)
			billingRecordMap.put("FREQUENCY", frequency);
		if (hoursPerDay != null)
			billingRecordMap.put("HOURS_PER_DAY", hoursPerDay);
		if (billingUnit != null)
			billingRecordMap.put("BILLING_UNIT", billingUnit);
		if (primarySalesPersonID != null)
			billingRecordMap.put("PRIMARY_SALESPERSON", primarySalesPersonID);
		if (weekEnding != null)
			billingRecordMap.put("WEEK_ENDING", weekEnding);
		if (isNotEmpty(workState)) {
			billingRecordMap.put("WORKING_STATE", workState);
			billingRecordMap.put("WORK_STATE", workState);
		}
		if (startDate != null)
			billingRecordMap.put("START_DATE", new Timestamp(startDate.getTime()));
		if (endDate != null)
			billingRecordMap.put("END_DATE", new Timestamp(endDate.getTime()));
		if (billingContactID != null)
			billingRecordMap.put("BILLING_CONTACT", new Timestamp(endDate.getTime()));
		if (allowEnterTimeOnPortal != null)
			billingRecordMap.put("ALLOW_ENTER_TIME_ON_PORTAL", allowEnterTimeOnPortal);
		if (approved != null) {
			billingRecordMap.put("APPROVED", approved);
			if (approved)
				billingRecordMap.put("APPROVERID", jobDivaSession.getRecruiterId());
		}
		if (assignmentID != null)
			billingRecordMap.put("INTERVIEWID", assignmentID);
		if (billRate != null)
			billingRecordMap.put("BILL_RATE", billRate);
		if (isNotEmpty(billRatePer))
			billingRecordMap.put("BILL_RATE_PER", billRatePer);
		if (createdByID != null)
			billingRecordMap.put("CREATED_BY", createdByID);
		else
			billingRecordMap.put("CREATED_BY", jobDivaSession.getRecruiterId());
		if (isNotEmpty(customerRefNo))
			billingRecordMap.put("CUSTOMER_REFNO", customerRefNo);
		if (division != null)
			billingRecordMap.put("DIVISION", division);
		if (enableTimesheet != null)
			billingRecordMap.put("ENABLE_TIMESHEET", enableTimesheet);
		if (expenseEnabled != null)
			billingRecordMap.put("EXPENSEENABLED", expenseEnabled);
		if (hiringManagerID != null)
			billingRecordMap.put("HIRING_MANAGER", hiringManagerID);
		if (hoursPerHalfDay != null)
			billingRecordMap.put("HOURS_PER_HALF_DAY", hoursPerHalfDay);
		if (isNotEmpty(invoiceGroup))
			billingRecordMap.put("INVOICE_GROUP", invoiceGroup);
		if (invoiceGroupIndex != null)
			billingRecordMap.put("INVOICE_GROUP_INDEX", invoiceGroupIndex);
		if (jobID != null)
			billingRecordMap.put("RFQID", jobID);
		if (overtimeByWorkingState != null)
			billingRecordMap.put("OT_BY_WORKING_STATE", overtimeByWorkingState);
		if (overtimeExempt != null)
			billingRecordMap.put("OVERTIMEEXEMPT", overtimeExempt);
		if (overtimeRate != null)
			billingRecordMap.put("OVERTIME_RATE1", overtimeRate);
		if (isNotEmpty(overtimeRatePer))
			billingRecordMap.put("OVERTIME_RATE1_PER", overtimeRatePer.substring(0, 1).toUpperCase());
		if (doubletimeRate != null)
			billingRecordMap.put("OVERTIME_RATE2", doubletimeRate);
		if (isNotEmpty(doubletimePer))
			billingRecordMap.put("OVERTIME_RATE2_PER", doubletimePer.substring(0, 1).toUpperCase());
		if (paymentTerms != null)
			billingRecordMap.put("PAYMENTTERMS", paymentTerms);
		if (primaryRecruiterID != null)
			billingRecordMap.put("PRIMARY_RECRUITER", primaryRecruiterID);
		if (primaryRecruiterPercentage != null)
			billingRecordMap.put("PRIREC_COMM_PERCENT", primaryRecruiterPercentage);
		if (primarySalesPercentage != null)
			billingRecordMap.put("PRISALE_COMM_PERCENT", primarySalesPercentage);
		if (recordID != null)
			billingRecordMap.put("RECID", recordID);
		if (secondaryRecruiterID != null)
			billingRecordMap.put("SECONDARY_RECRUITER", secondaryRecruiterID);
		if (secondaryRecruiterPercentage != null)
			billingRecordMap.put("SECREC_COMM_PERCENT", secondaryRecruiterPercentage);
		if (secondarySalesPercentage != null)
			billingRecordMap.put("SECSALE_COMM_PERCENT", secondarySalesPercentage);
		if (secondarySalesPersonID != null)
			billingRecordMap.put("SECONDARY_SALESPERSON", secondarySalesPersonID);
		if (tertiaryRecruiterID != null)
			billingRecordMap.put("TERTIARY_RECRUITER", tertiaryRecruiterID);
		if (tertiaryRecruiterPercentage != null)
			billingRecordMap.put("TERREC_COMM_PERCENT", tertiaryRecruiterPercentage);
		if (tertiarySalesPercentage != null)
			billingRecordMap.put("TERSALE_COMM_PERCENT", tertiarySalesPercentage);
		if (tertiarySalesPersonID != null)
			billingRecordMap.put("TERTIARY_SALESPERSON", tertiarySalesPersonID);
		if (timesheetEntryFormat != null)
			billingRecordMap.put("TIMESHEET_ENTRY_FORMAT", timesheetEntryFormat);
		if (isNotEmpty(timesheetInstruction))
			billingRecordMap.put("TIMESHEET_INSTRUCTION", timesheetInstruction);
		if (isNotEmpty(vMSEmployeeName))
			billingRecordMap.put("VMSEMPLOYEENAME", vMSEmployeeName);
		if (isNotEmpty(vMSWebsite))
			billingRecordMap.put("VMS_WEBSITE", vMSWebsite + "~" + vMSWebsite);
		if (isNotEmpty(workAddress1))
			billingRecordMap.put("WORK_ADDRESS1", workAddress1);
		if (isNotEmpty(workAddress2))
			billingRecordMap.put("WORK_ADDRESS2", workAddress2);
		if (isNotEmpty(workCity)) {
			billingRecordMap.put("WORKING_CITY", workCity);
			billingRecordMap.put("WORK_CITY", workCity);
		}
		if (isNotEmpty(workCountry)) {
			billingRecordMap.put("WORKING_COUNTRY", workCountry);
			billingRecordMap.put("WORK_COUNTRY", workCountry);
		}
		if (isNotEmpty(workZipcode))
			billingRecordMap.put("WORK_ZIP", workZipcode);
		// insert
		sql = " INSERT INTO Temployee_billingrecord (" + " datecreated,datecreated_real,";
		String sqlValue = " VALUES (sysdate,sysdate,";
		for (String key : billingRecordMap.keySet()) {
			sql += key + ",";
			sqlValue += " ? ,";
		}
		sql = sql.substring(0, sql.length() - 1) + ") ";
		sqlValue = sqlValue.substring(0, sqlValue.length() - 1) + ") ";
		sql = sql + sqlValue;
		paramList = new ArrayList<Object>();
		for (String key : billingRecordMap.keySet()) {
			paramList.add(billingRecordMap.get(key));
		}
		//
		parameters = paramList.toArray();
		jdbcTemplate.update(sql, parameters);
		// Update BILLINGEFFDATE in temployee_timesheet
		sql = " UPDATE Temployee_timesheet " //
				+ " SET billingeffdate = ? " //
				+ " WHERE employeeid = ?  " + " AND recruiter_teamid = ?  " //
				+ " AND nvl(invoiceid, 0) = 0 AND tdate > ?  ";
		//
		if (endDate != null) {
			sql += " AND tdate < ? ";
			parameters = new Object[] { startDate, candidateID, jobDivaSession.getTeamId(), startDate, endDate };
		} else {
			parameters = new Object[] { startDate, candidateID, jobDivaSession.getTeamId(), startDate };
		}
		jdbcTemplate.update(sql, parameters);
		// copy billing contact / company discount to billing record default
		// discount
		if (billingContactID != null) {
			int discount_type = 0;
			long companyID = 0;
			double discount = 0, discount_pct = 0;
			String discountTypeStr = "%";
			sql = " SELECT nvl(companyid, 0) as companyId, nvl(discountpct, 0) as discountpct, nvl(discount, 0) as discount, nvl(discount_type, 0) as discount_type " //
					+ " FROM Tcustomer " //
					+ " WHERE id = ?  "//
					+ " AND teamid = ? ";
			//
			parameters = new Object[] { billingContactID, jobDivaSession.getTeamId() };
			List<Map<String, Object>> resultList = jdbcTemplate.query(sql, parameters, new RowMapper<Map<String, Object>>() {
				
				@Override
				public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("companyid", rs.getLong("companyid"));
					map.put("discountpct", rs.getDouble("discountpct"));
					map.put("discount", rs.getDouble("discount"));
					map.put("discount_type", rs.getInt("discount_type"));
					return map;
				}
			});
			if (resultList != null && resultList.size() > 0) {
				Map<String, Object> map = resultList.get(0);
				companyID = (Long) map.get("companyid");
				discount_pct = (Double) map.get("discountpct");
				discount = (Double) map.get("discount");
				discount_type = (Integer) map.get("discount_type");
			}
			// get discount from contact company
			if (discount_pct <= 0 && discount <= 0 && companyID > 0) {
				sql = " SELECT nvl(discountpct, 0) as discountpct , nvl(discount, 0) as discount , nvl(discount_type, 0) as discount_type " //
						+ " FROM Tcustomercompany " + " WHERE id= ? " + " AND teamid= ?";
				parameters = new Object[] { companyID, jobDivaSession.getTeamId() };
				resultList = jdbcTemplate.query(sql, parameters, new RowMapper<Map<String, Object>>() {
					
					@Override
					public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("discountpct", rs.getDouble("discountpct"));
						map.put("discount", rs.getDouble("discount"));
						map.put("discount_type", rs.getInt("discount_type"));
						return map;
					}
				});
				//
				if (resultList != null && resultList.size() > 0) {
					Map<String, Object> map = resultList.get(0);
					discount_pct = (Double) map.get("discountpct");
					discount = (Double) map.get("discount");
					discount_type = (Integer) map.get("discount_type");
				}
			}
			if (discount_pct > 0) {
				discount = discount_pct;
				discountTypeStr = "%";
			} else {
				if (discount_type == 0)
					discountTypeStr = "h";
				else if (discount_type == 1)
					discountTypeStr = "d";
				else if (discount_type == 2 || discount_type == 3)
					discountTypeStr = "y";
			}
			// get remembered value of apply_to_invoice
			sql = " SELECT candpage_layout FROM Trecruiter " //
					+ " WHERE id = ? " + " AND groupid = ? ";
			parameters = new Object[] { createdByID, jobDivaSession.getTeamId() };
			List<String> list = jdbcTemplate.query(sql, parameters, new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getString("candpage_layout");
				}
			});
			//
			String candpage_layout = "";
			//
			if (list != null && list.size() > 0) {
				candpage_layout = list.get(0);
			}
			//
			int apply_to_invoice = 0;
			if (candpage_layout.length() > 0 && candpage_layout.split("_").length >= 24)
				apply_to_invoice = Integer.parseInt(candpage_layout.split("_")[23]);
			//
			// insert discount to billing record defualt discount
			sql = " INSERT INTO Temployee_billingdiscount " //
					+ " (id, recid, employeeid, teamid, discountid, discount, discount_unit,  discount_description, apply_to_invoice) " //
					+ " VALUES " //
					+ " (ebd_seq.nextval, ?, ?, ?, 0, ?, ? , 'Default Discount', ?)";
			//
			parameters = new Object[] { recID, candidateID, jobDivaSession.getTeamId(), discount, discountTypeStr, apply_to_invoice };
			jdbcTemplate.update(sql, parameters);
		}
		//
		//
		return recID;
	}
}

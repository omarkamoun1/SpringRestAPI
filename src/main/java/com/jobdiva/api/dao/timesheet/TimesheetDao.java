package com.jobdiva.api.dao.timesheet;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.axelon.candidate.EmployeeTimesheetTimeInOut;
import com.axelon.oc4j.ServletRequestData;
import com.axelon.profit.EmployeeMultipleRate;
import com.axelon.profit.InvoiceExpense;
import com.axelon.shared.CandidateBillingRecord;
import com.axelon.shared.TimeSheetDay;
import com.axelon.shared.TimeSheetWeek;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.invoice.InvoiceDao;
import com.jobdiva.api.model.ExpenseEntry;
import com.jobdiva.api.model.Timesheet;
import com.jobdiva.api.model.TimesheetApproval;
import com.jobdiva.api.model.TimesheetEntry;
import com.jobdiva.api.model.UploadTimesheetAssignment;
import com.jobdiva.api.model.WeekEndingRecord;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.servlet.ServletTransporter;

@Component
public class TimesheetDao extends AbstractJobDivaDao {
	
	@Autowired
	InvoiceDao invoiceDao;
	
	public Long uploadTimesheet(JobDivaSession jobDivaSession, Long employeeid, Long jobid, Date weekendingdate, Boolean approved, Long timesheetId, String externalId, Timesheet[] timesheetEntry) throws Exception {
		//
		//
		StringBuffer message = new StringBuffer();
		String[] timesheetDates = new String[7];
		Double[] timesheetHours = new Double[7];
		// should be 7
		if (timesheetEntry == null || timesheetEntry.length != 7) {
			message.append("Parameter Check Failed \r\n TimeSheet required and must contains 7 elements.\r\n");
		}
		//
		if (employeeid == null || employeeid <= 0) {
			message.append("Parameter Check Failed \r\n employeeid is required.\r\n");
		}
		//
		if (approved == null) {
			message.append("Parameter Check Failed \r\n approved is required.\r\n");
		}
		//
		if (weekendingdate == null) {
			message.append("Parameter Check Failed \r\n weekendingdate is required.\r\n");
		}
		if (timesheetId != null) {
			if (timesheetId <= 0)
				message.append("Invalid timesheetId(" + timesheetId + "). \r\n");
		}
		//
		if (isNotEmpty(externalId))
			externalId = externalId.trim();
		//
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n " + message.toString());
		}
		//
		//
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		// int billing_recid = 0;
		String weekending = "";
		Date timesheetdate = null;
		Date wkDate = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(weekendingdate);
			cal.add(Calendar.DATE, -6);
			// startDate = sdf.parse(sdf.format(cal.getTime()));
			for (int i = 0; i < timesheetDates.length; i++) {
				timesheetDates[i] = sdf.format(cal.getTime());
				timesheetHours[i] = 0.0;
				cal.add(Calendar.DATE, 1);
			}
			//
			String[] params = new String[4 + timesheetEntry.length];
			params[0] = "" + jobDivaSession.getTeamId();
			params[1] = "" + employeeid;
			params[2] = jobid > 0 ? ("" + jobid) : "0";
			//
			Calendar we = Calendar.getInstance();
			we.setTime(weekendingdate);
			we.setTimeZone(TimeZone.getDefault());
			weekending = sdf.format(we.getTime());
			wkDate = simpleDateFormat.parse(weekending);
			params[3] = weekending;
			//
			for (int i = 0; i < timesheetEntry.length; i++) {
				if (timesheetEntry[i].getDate() != null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(timesheetEntry[i].getDate());
					calendar.setTimeZone(TimeZone.getDefault());
					params[4 + i] = sdf.format(calendar.getTime());
					// printLog("tsdate: " + params[4+i]);
					timesheetdate = sdf.parse(params[4 + i]);
					if (timesheetdate.getTime() > wkDate.getTime() || timesheetdate.getTime() < wkDate.getTime() - 6 * 24 * 3600 * 1000L)
						message.append("Invalid timesheet date " + params[4 + i] + " for weekending " + weekending);
				}
				//
			}
		} catch (Exception e) {
			message.append(e.getMessage());
		}
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n " + message.toString());
		}
		//
		//
		//
		Object retObj1 = null;
		String bill_recid = "";
		String pay_recid = "";
		// Prev Timesheet in server Date ->Hours
		HashMap<String, Double> tsMap = new HashMap<String, Double>();
		// timesheetId or externalId
		// boolean updateHours = true; // false if sent-in hours are the same as
		// // existing records
		// boolean updateExpenses = true; // false if sent-in expenses are the
		// same
		// as existing records
		// Object retObj = null;
		// If timesheetId or externalId provided, locate existing timesheet and
		// return billrecid~payrecid
		// if (true || timesheetId != null || externalId != null) {
		CandidateBillingRecord bill_rec = new CandidateBillingRecord();
		bill_rec.mark = 35; // option code
		bill_rec.teamID = jobDivaSession.getTeamId();
		bill_rec.candidateID = employeeid;
		// Use the following two fields as placeholder. Will be processed
		// accordingly in the backend.
		if (timesheetId != null)
			bill_rec.rfqID = timesheetId;
		bill_rec.customerRefNo = externalId;
		try {
			ServletRequestData srd = new ServletRequestData(0, null, bill_rec);
			retObj1 = ServletTransporter.callServlet(getCandidateBillingRecordsGetServlet(), srd);
		} catch (Exception e) {
			e.printStackTrace();
			message.append("Error occurs when trying to locate existing timesheet. ");
		}
		if (retObj1 instanceof Object[]) {
			Object[] retObjArr = (Object[]) retObj1;
			TimeSheetWeek tsw = retObjArr[0] instanceof TimeSheetWeek ? (TimeSheetWeek) retObjArr[0] : null;
			// Vector<Object> exp = retObjArr[1] instanceof Vector ?
			// (Vector<Object>) retObjArr[1] : null;
			// Compare hours passed in with existing hours
			if (tsw instanceof TimeSheetWeek) {
				for (int i = 0; i < tsw.dates.length; i++) {
					TimeSheetDay day = tsw.dates[i];
					tsMap.put(sdf.format(day.tDate), day.hoursWorked);
				}
				if (timesheetDates.length == tsMap.size()) {
					int i = 0;
					while (i < timesheetDates.length) {
						if (!tsMap.containsKey(timesheetDates[i]) || !tsMap.get(timesheetDates[i]).equals(timesheetHours[i])) {
							break;
						}
						i++;
					}
					// if (i == timesheetDates.length)
					// updateHours = false;
				}
			}
			//
			bill_recid = tsw instanceof TimeSheetWeek ? tsw.bill_recid + "" : "";
			pay_recid = tsw instanceof TimeSheetWeek ? tsw.createdByID + "" : "";
			// if (bill_recid.length() > 0)
			// isUpdate = true;
		} else if (retObj1 instanceof Exception) {
			Exception e = (Exception) retObj1;
			e.printStackTrace();
			message.append("Error: exception returned when trying to locate existing timesheet. " + (e.getMessage() != null ? e.getMessage() : ""));
		}
		// }
		//
		//
		//
		if (timesheetId != null && (bill_recid.length() == 0 || pay_recid.length() == 0))
			message.append("Failed to locate timesheet by ID (" + timesheetId + "). ");
		//
		//
		if (message.length() > 0) {
			saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheet", "Timesheet Upload Failed, " + message.toString());
			//
			throw new Exception("Timesheet Upload Failed : " + message.toString());
		}
		//
		// build timesheet xml
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><xml>");
		xml.append("<TEAMID>" + jobDivaSession.getTeamId() + "</TEAMID>");
		xml.append("<RECRUITERID>" + jobDivaSession.getRecruiterId() + "</RECRUITERID>");
		xml.append("<WEBSITENAME></WEBSITENAME>");
		xml.append("<CANDIDATE><CANDIDATENAME></CANDIDATENAME><CANDIDATEID>" + employeeid + "</CANDIDATEID><PONUMBER/>");
		xml.append("<TIMESHEETWEEKS><TIMESHEETWEEK>");
		if (approved != null && approved)
			xml.append("<REPLACE_PENDING>true</REPLACE_PENDING>");
		else
			xml.append("<SAVE_AS_PENDING>true</SAVE_AS_PENDING>");
		//
		xml.append("<EXTERNALID>" + externalId + "</EXTERNALID>");
		//
		xml.append("<WEEKENDINGDATE>" + weekending + "</WEEKENDINGDATE><TIMESHEETRECORDS>");
		//
		xml.append("<BILLRECID>" + bill_recid + "</BILLRECID>");
		xml.append("<SALARYRECID>" + pay_recid + "</SALARYRECID>");
		//
		for (int i = 0; i < timesheetEntry.length; i++) {
			if (timesheetEntry[i].getDate() != null)
				xml.append("<TIMESHEETRECORD>" + "<DATE>" + sdf.format(new Date(timesheetEntry[i].getDate().getTime())) + "</DATE>" //
						+ "<PROJECT/>" //
						+ "<HOURSWORKED>" //
						+ timesheetEntry[i].getHours() + "</HOURSWORKED><OTHOURSWORKED/><STATUS/></TIMESHEETRECORD>");
		}
		xml.append("</TIMESHEETRECORDS></TIMESHEETWEEK></TIMESHEETWEEKS></CANDIDATE></xml>");
		Object retObj = new Object();
		//
		String status = null;
		//
		try {
			ServletRequestData srd = new ServletRequestData(0, null, xml.toString());
			retObj = ServletTransporter.callServlet(getSaveVMSTimesheets(), srd);
		} catch (Exception e) {
			message.append(e.getMessage());
		}
		if (retObj != null) {
			if (retObj instanceof Exception) {
				message.append(((Exception) retObj).getMessage());
			} else if (message.length() == 0) {
				status = "Timesheet Uploaded Successfully.";
			}
		}
		if (message.length() > 0) {
			status = "Error! Timesheet Not Saved.";
		}
		//
		//
		Long timeSheetId = null;
		// CandidateBillingRecord
		bill_rec = new CandidateBillingRecord();
		bill_rec.mark = 34;
		bill_rec.teamID = jobDivaSession.getTeamId();
		bill_rec.candidateID = employeeid;
		bill_rec.recID = isNotEmpty(bill_recid) ? Integer.parseInt(bill_recid) : 0;
		bill_rec.startDate = sdf.parse(weekending);
		retObj = null;
		try {
			ServletRequestData srd = new ServletRequestData(0, null, bill_rec);
			retObj = ServletTransporter.callServlet(getCandidateBillingRecordsGetServlet(), srd);
			if (retObj instanceof Long) {
				timeSheetId = (Long) retObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.append("Error occurs when trying to retrieve TimeCard ID. ");
		}
		//
		saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheet", status + ", " + message.toString());
		if (message.length() > 0) {
			throw new Exception(message.toString());
		}
		//
		return timeSheetId;
	}
	
	public Boolean markTimesheetPaid(JobDivaSession jobDivaSession, Long employeeid, Integer salaryrecordid, Date datepaid, Date[] timesheetDates) throws Exception {
		//
		//
		Set<String> tdates = new HashSet<String>();
		for (int i = 0; i < timesheetDates.length; i++) {
			tdates.add(simpleDateFormat.format(timesheetDates[i]));
		}
		//
		Set<String> invalidTDates = new HashSet<String>();
		invalidTDates.addAll(tdates);
		//
		//
		String sql = "SELECT " //
				+ " DISTINCT to_char(tdate, 'MM/dd/yyyy') as TDateValue " //
				+ " FROM temployee_timesheet " //
				+ " WHERE employeeid = :employeeid " //
				+ " AND recruiter_teamid = :teamId " //
				+ " AND salary_recid = :salaryrecordid " //
				+ " AND to_char(tdate, 'MM/dd/yyyy') IN (:tdates) ";
		//
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("employeeid", employeeid);
		parameterSource.addValue("teamId", jobDivaSession.getTeamId());
		parameterSource.addValue("salaryrecordid", salaryrecordid);
		parameterSource.addValue("tdates", tdates);
		//
		NamedParameterJdbcTemplate jdbcTemplateObject = getNamedParameterJdbcTemplate();
		List<String> list = jdbcTemplateObject.query(sql, parameterSource, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("TDateValue");
			}
		});
		//
		if (list != null && list.size() > 0) {
			for (String value : list) {
				invalidTDates.remove(value);
			}
		}
		//
		if (invalidTDates.isEmpty()) {
			String sqlUpdate = " UPDATE temployee_timesheet " //
					+ " SET datepaid = :datepaid " //
					+ " WHERE employeeid = :employeeid " //
					+ " AND recruiter_teamid = :teamId " //
					+ " AND salary_recid = :salaryrecordid " //
					+ " AND to_char(tdate, 'MM/dd/yyyy') IN (:tdates)  ";
			//
			parameterSource = new MapSqlParameterSource();
			parameterSource.addValue("datepaid", datepaid);
			parameterSource.addValue("employeeid", employeeid);
			parameterSource.addValue("teamId", jobDivaSession.getTeamId());
			parameterSource.addValue("salaryrecordid", salaryrecordid);
			parameterSource.addValue("tdates", tdates);
			jdbcTemplateObject.update(sqlUpdate, parameterSource);
		} else
			throw new Exception("Unable to find timesheet for " //
					+ " employeeid(" + employeeid + ") " //
					+ " teamid(" + jobDivaSession.getTeamId() + ") " //
					+ " salaryrecordid(" + salaryrecordid + ") " //
					+ " on timesheet dates " + invalidTDates.toString());
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public UploadTimesheetAssignment uploadTimesheetAssignment(JobDivaSession jobDivaSession, Long employeeId, Long jobId, Date weekendingDate, Double payRate, Double overtimepayrate, //
			Double doubletimepayrate, Double billRate, Double overtimebillrate, Double doubletimebillrate, String location, String title, String roleNumber, //
			Long timesheetId, String externalId, String compcode, TimesheetEntry[] timesheetEntry, ExpenseEntry[] expenses, String[] emailRecipients) throws Exception {
		//
		UploadTimesheetAssignment utsa_rsp = new UploadTimesheetAssignment();
		StringBuffer message = new StringBuffer();
		StringBuffer warning = new StringBuffer();
		StringBuffer expense_message = new StringBuffer();
		StringBuffer expense_warning = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf_timeinout = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String bill_unit = "H";
		String pay_unit = "H";
		// parameters checking
		Date startDate = null;
		String weekending = "";
		String[] timesheetDates = new String[7];
		Double[] timesheetHours = new Double[7];
		EmployeeTimesheetTimeInOut[] timeInOut = new EmployeeTimesheetTimeInOut[7];
		//
		String city = "";
		String state = "";
		String bill_recid = "";
		String pay_recid = "";
		Long timecardId = null;
		Vector<Object> result = new Vector<Object>();
		Integer newInvoiceId = 0;
		boolean isUpdate = false; // true if found timesheet based on param
									// timesheetId or externalId
		boolean updateHours = true; // false if sent-in hours are the same as
									// existing records
		boolean updateExpenses = true; // false if sent-in expenses are the same
										// as existing records
		HashMap<String, Double> tsMap = new HashMap<String, Double>(); // Prev
																		// Timesheet
																		// in
																		// server
																		// Date
																		// ->
																		// Hours
		HashMap<String, InvoiceExpense> expensesMap = new HashMap<String, InvoiceExpense>(); // Prev
																								// expenses
																								// in
																								// server
																								// Category
																								// ->
																								// InvoiceExpense
		if (employeeId < 1)
			message.append("Error: invalid employeeId(" + employeeId + "). ");
		//
		if (jobId != null && jobId <= 0)
			message.append("Invalid jobId(" + jobId + "). ");
		// else
		// jobId = uploadTimesheetAssignment.getJobid();
		//
		try {
			Calendar we = Calendar.getInstance();
			we.setTime(weekendingDate);
			we.setTimeZone(TimeZone.getDefault());
			weekending = sdf.format(we.getTime());
			weekendingDate = sdf.parse(weekending);
			///
			Calendar cal = Calendar.getInstance();
			cal.setTime(weekendingDate);
			cal.add(Calendar.DATE, -6);
			startDate = sdf.parse(sdf.format(cal.getTime()));
			for (int i = 0; i < timesheetDates.length; i++) {
				timesheetDates[i] = sdf.format(cal.getTime());
				timesheetHours[i] = 0.0;
				cal.add(Calendar.DATE, 1);
			}
			//
			for (int j = 0; j < timesheetEntry.length; j++) {
				TimesheetEntry entry = timesheetEntry[j];
				Date date = null;
				if (entry.getDate() != null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(entry.getDate());
					calendar.setTimeZone(TimeZone.getDefault());
					date = sdf.parse(sdf.format(calendar.getTime()));
				}
				//
				String date_str = "";
				Double hours = entry.getHours() != null ? entry.getHours() : 0.0;
				//
				EmployeeTimesheetTimeInOut tio = new EmployeeTimesheetTimeInOut();
				//
				// if (entry.isTimeinSpecified())
				// entry.getTimeIn().setTimeZone(TimeZone.getDefault());
				// //
				// if (entry.isLunchinSpecified())
				// entry.getLunchin().setTimeZone(TimeZone.getDefault());
				// //
				// if (entry.isLunchoutSpecified())
				// entry.getLunchout().setTimeZone(TimeZone.getDefault());
				// //
				// if (entry.isTimeoutSpecified())
				// entry.getTimeout().setTimeZone(TimeZone.getDefault());
				//
				tio.setTimein(entry.getTimeIn());
				tio.setLunchin(entry.getLunchIn());
				tio.setLunchout(entry.getLunchOut());
				tio.setTimeout(entry.getTimeOut());
				//
				if (tio.getTimein() != null && tio.getTimeout() != null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(tio.getTimein());
					date_str = sdf.format(calendar.getTime());
					date = sdf.parse(date_str);
					if (tio.getLunchout() != null && tio.getLunchin() != null) {
						long diff = tio.getTimeout().getTime() - tio.getTimein().getTime() - (tio.getLunchin().getTime() - tio.getLunchout().getTime());
						hours = (double) diff / (60 * 60 * 1000);
					} else if (tio.getLunchout() == null && tio.getLunchin() == null) {
						long diff = tio.getTimeout().getTime() - tio.getTimein().getTime();
						hours = (double) diff / (60 * 60 * 1000);
					} else {
						message.append("Missing either lunch in or lunch out time. ");
					}
				} else if (tio.getTimein() == null && tio.getTimeout() == null) {
					if (date == null)
						message.append("Missing Time in, Time out, date. Please specify...");
					else
						date_str = sdf.format(date);
				} else {
					message.append("Cannot only specify Time in or Time out...");
					break;
				}
				if (hours < 0)
					message.append("Invalid timesheet hours(" + hours + ") on date " + date_str + ". ");
				if (date != null && (date.compareTo(weekendingDate) > 0 || date.compareTo(startDate) < 0))
					message.append("Invalid timesheet date " + date_str + " for weekending " + weekending + ". ");
				for (int k = 0; k < timesheetDates.length; k++) {
					if (timesheetDates[k].equals(date_str)) {
						timesheetHours[k] = hours;
						timeInOut[k] = tio;
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			message.append("Invalid weekendingDate or timesheet entry. ");
		}
		// printLog("Timesheet dates: " + Arrays.toString(timesheetDates));
		// printLog("Timesheet hours: " + Arrays.toString(timesheetHours));
		if (payRate < 0)
			message.append("Invalid payRate(" + payRate + "). ");
		//
		if (overtimepayrate != null) {
			if (overtimepayrate < 0)
				message.append("Invalid overtimePayRate(" + overtimepayrate + "). ");
		}
		//
		if (doubletimepayrate != null) {
			if (doubletimepayrate < 0)
				message.append("Invalid doubletimePayRate(" + doubletimepayrate + "). ");
		}
		//
		if (billRate != null && billRate < 0)
			message.append("Invalid billRate(" + billRate + "). ");
		//
		if (overtimebillrate != null) {
			if (overtimebillrate < 0)
				message.append("Invalid overtimeBillrate(" + overtimebillrate + "). ");
		}
		//
		if (doubletimebillrate != null) {
			if (doubletimebillrate < 0)
				message.append("Invalid doubletimeBillRate(" + doubletimebillrate + "). ");
		}
		//
		if (isNotEmpty(location)) {
			String[] city_state = location.split(",");
			if (city_state.length == 2) {
				city = city_state[0].trim();
				state = city_state[1].trim();
				if (state.length() > 2) {
					city = location;
					state = "";
				}
			} else
				city = location;
		}
		//
		if (title.trim().length() == 0)
			message.append("Role/Title is empty. ");
		//
		if (isNotEmpty(roleNumber))
			roleNumber = roleNumber.trim();
		//
		if (timesheetId != null) {
			if (timesheetId <= 0)
				message.append("Invalid timesheetId(" + timesheetId + "). ");
		}
		//
		if (isNotEmpty(externalId))
			externalId = externalId.trim();
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n " + message.toString());
		}
		//
		//
		Object retObj = null;
		// If timesheetId or externalId provided, locate existing timesheet and
		// return billrecid~payrecid
		if (timesheetId != null || externalId != null) {
			CandidateBillingRecord bill_rec = new CandidateBillingRecord();
			bill_rec.mark = 35; // option code
			bill_rec.teamID = jobDivaSession.getTeamId();
			bill_rec.candidateID = employeeId;
			// Use the following two fields as placeholder. Will be processed
			// accordingly in the backend.
			if (timesheetId != null)
				bill_rec.rfqID = timesheetId;
			bill_rec.customerRefNo = externalId;
			try {
				ServletRequestData srd = new ServletRequestData(0, null, bill_rec);
				retObj = ServletTransporter.callServlet(getCandidateBillingRecordsGetServlet(), srd);
			} catch (Exception e) {
				e.printStackTrace();
				message.append("Error occurs when trying to locate existing timesheet. ");
			}
			if (retObj instanceof Object[]) {
				Object[] retObjArr = (Object[]) retObj;
				TimeSheetWeek tsw = retObjArr[0] instanceof TimeSheetWeek ? (TimeSheetWeek) retObjArr[0] : null;
				Vector<Object> exp = retObjArr[1] instanceof Vector ? (Vector<Object>) retObjArr[1] : null;
				// Compare hours passed in with existing hours
				if (tsw instanceof TimeSheetWeek) {
					for (int i = 0; i < tsw.dates.length; i++) {
						TimeSheetDay day = tsw.dates[i];
						tsMap.put(sdf.format(day.tDate), day.hoursWorked);
					}
					if (timesheetDates.length == tsMap.size()) {
						int i = 0;
						while (i < timesheetDates.length) {
							if (!tsMap.containsKey(timesheetDates[i]) || !tsMap.get(timesheetDates[i]).equals(timesheetHours[i])) {
								break;
							}
							i++;
						}
						if (i == timesheetDates.length)
							updateHours = false;
					}
				}
				// Compare expenses passed in with existing expenses
				if (exp instanceof Vector && expenses instanceof ExpenseEntry[] && exp.size() == expenses.length) {
					for (Object o : exp) {
						if (o instanceof InvoiceExpense) {
							InvoiceExpense ie = (InvoiceExpense) o;
							expensesMap.put(ie.getItem(), ie);
						}
					}
					int i = 0;
					while (i < expenses.length) {
						ExpenseEntry expenseEntry = expenses[i];
						String date = sdf.format(expenseEntry.getDate().getTime());
						String category = expenseEntry.getCategory();
						double amount = expenseEntry.getExpenseamount();
						double quantity = expenseEntry.getQuantity();
						if (!expensesMap.containsKey(category)
								|| !(date.equals(sdf.format(expensesMap.get(category).getDateoccur())) && amount == expensesMap.get(category).getExpense().doubleValue() && quantity == expensesMap.get(category).getQuantity().doubleValue())) {
							break;
						}
						i++;
					}
					if (i == expenses.length)
						updateExpenses = false;
				}
				bill_recid = tsw instanceof TimeSheetWeek ? tsw.bill_recid + "" : "";
				pay_recid = tsw instanceof TimeSheetWeek ? tsw.createdByID + "" : "";
				if (bill_recid.length() > 0)
					isUpdate = true;
			} else if (retObj instanceof Exception) {
				Exception e = (Exception) retObj;
				e.printStackTrace();
				message.append("Error: exception returned when trying to locate existing timesheet. " + (e.getMessage() != null ? e.getMessage() : ""));
			}
		}
		//
		//
		//
		if (timesheetId != null && (bill_recid.length() == 0 || pay_recid.length() == 0))
			message.append("Failed to locate timesheet by ID (" + timesheetId + "). ");
		if (message.length() > 0) {
			saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheetAssignment", "Timesheet Upload Failed, " + message.toString());
			//
			utsa_rsp.setStatus("Timesheet Upload Failed");
			utsa_rsp.setMessage(message.toString());
			return utsa_rsp;
		}
		//
		if (updateHours) {
			//
			/* Insert or Update timesheet hours */
			// If failed to locate record id, locate parent bill/pay record id
			// and create new bill/pay record id
			if (bill_recid.length() == 0 || pay_recid.length() == 0) {
				// printLog("Locate parent bill/pay rec id...");
				String parent_bill_recid = "";
				String parent_pay_recid = "";
				CandidateBillingRecord bill_rec = new CandidateBillingRecord();
				bill_rec.mark = 33;
				bill_rec.teamID = jobDivaSession.getTeamId();
				bill_rec.candidateID = employeeId;
				bill_rec.startDate = startDate;
				if (jobId != null)
					bill_rec.rfqID = jobId;
				retObj = null;
				try {
					ServletRequestData srd = new ServletRequestData(0, null, bill_rec);
					retObj = ServletTransporter.callServlet(getCandidateBillingRecordsGetServlet(), srd);
				} catch (Exception e) {
					e.printStackTrace();
					message.append("Error occurs when trying to locate parent bill/pay rec id. " + (e.getMessage() != null ? e.getMessage() : ""));
				}
				if (retObj instanceof String) {
					String[] recids = ((String) retObj).split("~");
					if (recids.length == 2) {
						parent_bill_recid = recids[0];
						parent_pay_recid = recids[1];
					} else
						message.append("Missing parent billing record id or pay record id " + recids);
				} else if (retObj instanceof Exception) {
					Exception e = (Exception) retObj;
					e.printStackTrace();
					message.append("Error: exception returned when trying to locate parent bill/pay rec id. " + (e.getMessage() != null ? e.getMessage() : ""));
				}
				if (message.length() > 0) {
					saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheetAssignment", "Timesheet Upload Failed, " + message.toString());
					//
					utsa_rsp.setStatus("Timesheet Upload Failed");
					utsa_rsp.setMessage(message.toString());
					return utsa_rsp;
				}
				//
				Vector<Object> reqData = new Vector<Object>();
				reqData.add(jobDivaSession.getTeamId() + "");
				reqData.add(jobDivaSession.getRecruiterId() + "");
				reqData.add(employeeId + "");
				reqData.add(parent_bill_recid);
				reqData.add(parent_pay_recid);
				EmployeeMultipleRate ebra = new EmployeeMultipleRate();
				ebra.setBill_name(roleNumber != null ? (roleNumber + ":" + title) : title);
				ebra.setStartdate(startDate);
				ebra.setEnddate(weekendingDate);
				ebra.setBill_rate(new BigDecimal(billRate));
				ebra.setBill_unit(bill_unit);
				ebra.setPay_rate(new BigDecimal(payRate));
				ebra.setPay_unit(pay_unit);
				ebra.s0 = state;
				ebra.s1 = state;
				ebra.s2 = city;
				reqData.add(ebra);
				if (isNotEmpty(compcode))
					reqData.add(compcode);
				retObj = null;
				try {
					ServletRequestData srd = new ServletRequestData(0, null, reqData);
					retObj = ServletTransporter.callServlet(getCandidateAddlRecordInsertServlet(), srd);
				} catch (Exception e) {
					e.printStackTrace();
					message.append("Error occurs when trying to create add'l bill/pay record ids. ");
				}
				if (retObj instanceof String) {
					String[] recids = ((String) retObj).split("~~");
					if (recids.length == 2) {
						bill_recid = recids[0];
						pay_recid = recids[1];
					} else
						message.append("Missing billing record id or pay record id " + recids);
				} else if (retObj instanceof Exception) {
					Exception e = (Exception) retObj;
					e.printStackTrace();
					message.append("Error: exception returned when trying to create add'l bill/pay record ids. " + (e.getMessage() != null ? e.getMessage() : ""));
				}
				if (message.length() > 0) {
					saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheetAssignment", "Timesheet Upload Failed, " + message.toString());
					//
					utsa_rsp.setStatus("Timesheet Upload Failed");
					utsa_rsp.setMessage(message.toString());
					//
					return utsa_rsp;
				}
			}
			//
			// build timesheet xml; upload timesheet
			StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><xml>");
			xml.append("<TEAMID>" + jobDivaSession.getTeamId() + "</TEAMID>");
			xml.append("<RECRUITERID>" + jobDivaSession.getRecruiterId() + "</RECRUITERID>");
			xml.append("<WEBSITENAME></WEBSITENAME>");
			xml.append("<CANDIDATE>" + "		<CANDIDATENAME></CANDIDATENAME>" + "		<CANDIDATEID>" + employeeId + "</CANDIDATEID><PONUMBER/>");
			xml.append("<TIMESHEETWEEKS>" + "		<TIMESHEETWEEK>");
			xml.append("<REPLACE_PENDING>true</REPLACE_PENDING>");
			xml.append("<SAVE_AS_PENDING>false</SAVE_AS_PENDING>");
			xml.append("<REPLACE_APPROVED>true</REPLACE_APPROVED>");
			xml.append("<EXTERNALID>" + externalId + "</EXTERNALID>");
			xml.append("<WEEKENDINGDATE>" + weekending + "</WEEKENDINGDATE>");
			xml.append("<BILLRECID>" + bill_recid + "</BILLRECID>");
			xml.append("<SALARYRECID>" + pay_recid + "</SALARYRECID>");
			xml.append("<TIMESHEETRECORDS>");
			for (int i = 0; i < timesheetDates.length; i++) {
				xml.append("<TIMESHEETRECORD>" + "		<DATE>" + timesheetDates[i] + "</DATE>" + "		<PROJECT/>" + "		<HOURSWORKED>" + timesheetHours[i] + "</HOURSWORKED>");
				if (timeInOut[i] != null) {
					if (timeInOut[i].getTimein() != null)
						xml.append("<TIMEIN>" + sdf_timeinout.format(timeInOut[i].getTimein()) + "</TIMEIN>");
					if (timeInOut[i].getLunchin() != null)
						xml.append("<LUNCHIN>" + sdf_timeinout.format(timeInOut[i].getLunchin()) + "</LUNCHIN>");
					if (timeInOut[i].getLunchout() != null)
						xml.append("<LUNCHOUT>" + sdf_timeinout.format(timeInOut[i].getLunchout()) + "</LUNCHOUT>");
					if (timeInOut[i].getTimeout() != null)
						xml.append("<TIMEOUT>" + sdf_timeinout.format(timeInOut[i].getTimeout()) + "</TIMEOUT>");
				}
				xml.append("<OTHOURSWORKED/><STATUS/></TIMESHEETRECORD>");
			}
			xml.append("</TIMESHEETRECORDS></TIMESHEETWEEK></TIMESHEETWEEKS></CANDIDATE></xml>");
			retObj = null;
			try {
				ServletRequestData srd = new ServletRequestData(0, null, xml.toString());
				retObj = ServletTransporter.callServlet(getSaveVMSTimesheets(), srd);
			} catch (Exception e) {
				e.printStackTrace();
				message.append("Error occurs when saving timesheets. ");
			}
			if (retObj instanceof String) {
				// String rsp = (String) retObj;
				// printLog("Save timesheet successful. Return code: " + rsp);
			} else if (retObj instanceof Exception) {
				Exception e = (Exception) retObj;
				message.append("Error: exception returned when saving timesheets. " + (e.getMessage() != null ? e.getMessage() : ""));
			}
			if (message.length() > 0) {
				saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheetAssignment", "Timesheet Upload Failed, " + message.toString());
				//
				utsa_rsp.setStatus("Timesheet Upload Failed");
				utsa_rsp.setMessage(message.toString());
				return utsa_rsp;
			}
			// printLog("Finish Saving Timesheet...");
		} else {
			warning.append("Timesheet hours already exist.");
		}
		// Get timecard ID
		// printLog("Get TimeCard ID...");
		CandidateBillingRecord bill_rec = new CandidateBillingRecord();
		bill_rec.mark = 34;
		bill_rec.teamID = jobDivaSession.getTeamId();
		bill_rec.candidateID = employeeId;
		bill_rec.recID = Integer.parseInt(bill_recid);
		bill_rec.startDate = weekendingDate;
		retObj = null;
		try {
			ServletRequestData srd = new ServletRequestData(0, null, bill_rec);
			retObj = ServletTransporter.callServlet(getCandidateBillingRecordsGetServlet(), srd);
		} catch (Exception e) {
			e.printStackTrace();
			message.append("Error occurs when trying to retrieve TimeCard ID. ");
		}
		if (retObj instanceof Long) {
			Long rsp = (Long) retObj;
			timecardId = rsp;
			// printLog("Retrieve timecard ID successful. ID: " + rsp);
		} else if (retObj instanceof Exception) {
			Exception e = (Exception) retObj;
			e.printStackTrace();
			message.append("Error: exception returned when retrieving timecard ID. " + (e.getMessage() != null ? e.getMessage() : ""));
		}
		if (message.length() > 0) {
			saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheetAssignment", "Timesheet Upload Failed, " + message.toString());
			//
			utsa_rsp.setStatus("Timesheet Upload Failed");
			utsa_rsp.setMessage(message.toString());
			return utsa_rsp;
		}
		//
		utsa_rsp.setStatus("Timesheet Upload Successful");
		utsa_rsp.setMessage(message.toString() + (warning.length() > 0 ? ("Warning: " + warning.toString()) : ""));
		//
		if (timecardId instanceof Long && timecardId > 0)
			utsa_rsp.setTimecardid(timecardId);
		//
		// If expense passed in (such as bonus), process as in method
		// "addExpenseInvoice"
		if (expenses != null && updateExpenses) {
			// printLog("Add Expense Invoice");
			// Add Expenses
			try {
				result = invoiceDao.addExpenses(jobDivaSession.getTeamId(), jobDivaSession.getRecruiterId(), Calendar.getInstance().getTime(), weekendingDate, employeeId, expenses, "", "", isUpdate, timecardId, externalId);
				if (result.size() == 2) {
					newInvoiceId = (Integer) result.get(0);
					expense_warning.append(result.get(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
				expense_message.append(e.getMessage());
			}
			if (expense_message.length() > 0) {
				saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheetAssignment",
						"Add Expense Invoice Failed, " + expense_message.toString() + (expense_warning.length() > 0 ? (" Warning: " + expense_warning.toString()) : ""));
				//
				utsa_rsp.setAddexpensestatus("Add Expense Invoice Failed");
				utsa_rsp.setAddexpensemessage(expense_message.toString() + (expense_warning.length() > 0 ? (" Warning: " + expense_warning.toString()) : ""));
				return utsa_rsp;
				//
			}
			// Approve Expenses
			// printLog("Approve Expense Invoice...");
			try {
				invoiceDao.approveExpenseEntry(jobDivaSession, newInvoiceId, "", null);
			} catch (Exception e) {
				e.printStackTrace();
				expense_message.append(e.getMessage());
			}
			if (expense_message.length() > 0) {
				saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheetAssignment",
						"Add Expense Invoice Failed, " + expense_message.toString() + (expense_warning.length() > 0 ? (" Warning: " + expense_warning.toString()) : ""));
				//
				utsa_rsp.setAddexpensestatus("Add Expense Invoice Failed");
				utsa_rsp.setAddexpensemessage(expense_message.toString() + (expense_warning.length() > 0 ? (" Warning: " + expense_warning.toString()) : ""));
				return utsa_rsp;
				//
			}
			// Save timecard ID into expense table
			// printLog("Save timecard ID into expense table...");
			Boolean saveTimecardSuccessful = false;
			Object[] params = new Object[4];
			params[0] = new Integer(122);
			params[1] = new Long(jobDivaSession.getTeamId());
			params[2] = new Long(newInvoiceId);
			params[3] = Math.toIntExact(timecardId);
			try {
				ServletRequestData data = new ServletRequestData(0L, params);
				Object obj = ServletTransporter.callServlet(getCandidateFinancialServlet(), data);
				if (obj instanceof Exception) {
					Exception e = (Exception) obj;
					e.printStackTrace();
					expense_warning.append("Exception returned while saving timecard ID. " + (e.getMessage() != null ? e.getMessage() : ""));
				} else if (obj instanceof Boolean)
					saveTimecardSuccessful = (Boolean) obj;
			} catch (Exception e) {
				e.printStackTrace();
				expense_warning.append("Error occurs while saving timecard ID. " + (e.getMessage() != null ? e.getMessage() : ""));
			}
			if (saveTimecardSuccessful)
				utsa_rsp.setAddexpensestatus("Add Expense Invoice Successful");
			else
				utsa_rsp.setAddexpensestatus("Add Expense Invoice Failed");
			utsa_rsp.setAddexpensemessage(expense_message.toString() + (expense_warning.length() > 0 ? (" Warning: " + expense_warning.toString()) : ""));
		} else if (expenses != null && !updateExpenses) {
			utsa_rsp.setAddexpensestatus("Add Expense Invoice Successful");
			expense_warning.append("Expenses already exist. ");
			utsa_rsp.setAddexpensemessage(expense_message.toString() + (expense_warning.length() > 0 ? (" Warning: " + expense_warning.toString()) : ""));
		}
		// Send email notifications to recipients
		if (emailRecipients != null && emailRecipients.length > 0) {
			// printLog("Send email notifications to recipients...");
			Vector<Object> reqData = new Vector<Object>();
			reqData.add(8);
			reqData.add("apinotification@jobdivamail.com");
			// Recipients
			Vector<String> receivers = new Vector<String>();
			for (String email : emailRecipients) {
				if (email != null && email.trim().length() > 0)
					receivers.add(email);
			}
			reqData.add(receivers);
			// Email Subject
			String subject = "";
			if (!isUpdate)
				subject = "JobDiva API Notification - New Timesheet Assignment";
			else if (!updateHours && !updateExpenses)
				subject = "JobDiva API Notification - Duplicate Timesheet Assignment";
			else {
				subject = "JobDiva API Notification - Adjusted Timesheet Assignment";
				if (updateHours && !updateExpenses)
					subject += " - Hours Updated";
				else if (!updateHours && updateExpenses)
					subject += " - Expenses Updated";
				else
					subject += " - Hours and Expenses Updated";
			}
			reqData.add(subject);
			// Email Body
			StringBuffer msg = new StringBuffer();
			msg.append("Method: uploadTimesheetAssignment <br/>");
			msg.append("Employee ID: " + employeeId + "<br/>");
			msg.append("Weekending Date: " + sdf.format(weekendingDate) + "<br/>");
			msg.append("Pay Rate: " + payRate + "<br/>");
			msg.append("Bill Rate: " + billRate + "<br/>");
			msg.append("Title: " + title + "<br/>");
			if (externalId != null)
				msg.append("External ID: " + externalId + "<br/>");
			if (timecardId instanceof Long && timecardId > 0)
				msg.append("JobDiva Timesheet ID: " + timecardId + "<br/>");
			msg.append("<br/>Timesheet Details:<br/>");
			for (int i = 0; i < timesheetDates.length; i++) {
				if (((!isUpdate && updateHours && updateExpenses) || (isUpdate && !updateHours && !updateExpenses)) && timesheetHours[i] == 0)
					continue;
				msg.append("Hours: " + timesheetHours[i] + ", Date:" + timesheetDates[i]);
				if (isUpdate && (updateHours || updateExpenses) && tsMap.containsKey(timesheetDates[i])) {
					msg.append(" (was " + tsMap.get(timesheetDates[i]) + ")");
				}
				msg.append("<br/>");
			}
			if (expenses != null) {
				msg.append("<br/>Expenses Details:<br/>");
				for (ExpenseEntry exp : expenses) {
					msg.append("Expense Amount: " + exp.getExpenseamount() + ", Category: " + exp.getCategory() + ", Quantity: " + exp.getQuantity() + ", Date: " + sdf.format(exp.getDate().getTime()));
					if (isUpdate && (updateHours || updateExpenses) && expensesMap.containsKey(exp.getCategory())) {
						msg.append(" (was " + expensesMap.get(exp.getCategory()).getExpense() + ")");
					}
					msg.append("<br/>");
				}
			}
			reqData.add(msg.toString());
			invoiceDao.sendMail(expense_warning, reqData);
		}
		utsa_rsp.setMessage(message.toString() + (warning.length() > 0 ? (" Warning: " + warning.toString()) : ""));
		//
		saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheetAssignment",
				utsa_rsp.getStatus() + ", " + ((utsa_rsp.getAddexpensestatus() != null && utsa_rsp.getAddexpensestatus().length() > 0) ? utsa_rsp.getAddexpensestatus() + ", " : "")
						+ ((utsa_rsp.getMessage() != null && utsa_rsp.getMessage().length() > 0) ? utsa_rsp.getMessage() + ", " : "")
						+ ((utsa_rsp.getAddexpensemessage() != null && utsa_rsp.getAddexpensemessage().length() > 0) ? utsa_rsp.getAddexpensemessage() : ""));
		//
		return utsa_rsp;
	}
	
	public List<WeekEndingRecord> searchTimesheet(JobDivaSession jobDivaSession, Long userid, Integer approvedStatus, Date startDate, Date endDate, String firstname, String lastname, Long companyid, Long managerid) throws Exception {
		String sql = null;
		if (managerid == null)
			managerid = (long) -1;
		if (companyid == null)
			companyid = (long) -1;
		if (managerid <= 0) { // managerId not exist
			sql = "select /*+ ordered use_nl(t1 t4 t7 t2 t3 t5 t6 t8) index(t1 IDX_TEMPLOYEE_WED_2)  */ t1.employeeid, t1.weekendingdate, t1.billing_recid, (t2.firstname || ' ' || t2.lastname) employeename, " + " nvl(t4.approverid, 0) approverid, "
					+ " decode(nvl(t1.approved,0),0,(t3.firstname || ' ' || t3.lastname), (t6.firstname||' '||t6.lastname)) approvername, "
					+ " nvl(t1.approved, 0) approved, t1.approved_on, nvl(t1.hoursworked,0) hoursworked , nvl(t1.reg_hours,0) reg_hours, nvl(t1.ot_hours,0) ot_hours, nvl(t1.dt_hours,0) dt_hours, t1.datecreated, "
					+ " nvl(t1.ignored,0) ignored, nvl(t8.billingid, 0) myitem, t5.companyname, t4.bill_rate_per, t4.timesheet_entry_format, t10.rfqtitle, t10.rfqno_team "
					+ " from temployee_wed t1, temployee_billingrecord t4,tbilling_approver t7,tcandidate t2, trecruiter t3, tcustomer t5, trecruiter t6, " + " tbilling_approver t8,trfq t10 " + " where t1.recruiter_teamid = ?"
					+ " and ((-100 = ? and nvl(t1.approved,0)>=0) or nvl(t1.approved, 0) = ?) " + " and t1.approved is not null " + " and (t1.weekendingdate between ? and ? ) " + " and (0 = ? or upper(t2.FIRSTNAME) like ?) "
					+ " and (0 = ? or upper(t2.LASTNAME) like ?) " + " and t1.employeeid = t2.id  and t2.teamid = t1.recruiter_teamid " + " and t4.employeeid=t1.employeeid and t4.recruiter_teamid=t1.recruiter_teamid and t4.recid=t1.billing_recid "
					+ " and t4.billing_contact = t5.id and t4.recruiter_teamid = t5.teamid " + " and (-1 = ? or t5.companyid = ?) "
					+ " and t1.employeeid = t7.employeeid(+) and t1.billing_recid = t7.billingid(+) and t1.recruiter_teamid = t7.teamid(+) and t7.type(+)=1 " + " and t7.approverid = t3.id(+) and t7.teamid=t3.groupid(+) "
					+ " and t1.approved_by = t6.id(+) and t1.recruiter_teamid = t6.groupid(+) " + " and t1.employeeid = t8.employeeid(+) and t1.billing_recid = t8.billingid(+) and t1.recruiter_teamid = t8.teamid(+) and ?=t8.approverid(+) "
					+ " and t4.rfqid = t10.id(+) and t4.recruiter_teamid=t10.teamid(+) ";
			sql = " select * from ( " + sql + ") order by upper(employeename), billing_recid, weekendingdate desc ";
		} else { // managerId exist
			sql = "select /*+ ordered use_nl(t1 t4 t7 t2 t3 t5 t6 t8 t9) index(t1 IDX_TEMPLOYEE_WED_2) */ t1.employeeid, t1.weekendingdate, t1.billing_recid, (t2.firstname || ' ' || t2.lastname) employeename, " + " nvl(t4.approverid, 0) approverid, "
					+ " decode(nvl(t1.approved,0),0,(t3.firstname || ' ' || t3.lastname), (t6.firstname||' '||t6.lastname)) approvername, "
					+ " nvl(t1.approved, 0) approved, t1.approved_on, nvl(t1.hoursworked,0) hoursworked , nvl(t1.reg_hours,0) reg_hours, nvl(t1.ot_hours,0) ot_hours, nvl(t1.dt_hours,0) dt_hours, t1.datecreated, "
					+ " nvl(t1.ignored,0) ignored, nvl(t8.billingid, 0) myitem, t5.companyname, t4.bill_rate_per, t4.timesheet_entry_format, t11.rfqtitle, t11.rfqno_team  "
					+ " from temployee_wed t1, temployee_billingrecord t4,tbilling_approver t7,tcandidate t2, trecruiter t3, tcustomer t5, trecruiter t6," + " tbilling_approver t8, tbilling_approver t9, trfq t11 "
					+ " where t1.recruiter_teamid = ? and ((-100 = ? and nvl(t1.approved,0)>=0) or nvl(t1.approved, 0) = ?) " + " and t1.approved is not null " + " and (t1.weekendingdate between ? and ?) "
					+ " and (0 = ? or upper(t2.FIRSTNAME) like ?) " + " and (0 = ? or upper(t2.LASTNAME) like ?) " + " and t1.employeeid = t2.id  and t2.teamid = t1.recruiter_teamid "
					+ " and t4.employeeid=t1.employeeid and t4.recruiter_teamid=t1.recruiter_teamid and t4.recid=t1.billing_recid " + " and t4.billing_contact = t5.id and t4.recruiter_teamid = t5.teamid " + " and (-1 = ? or t5.companyid = ?) "
					+ " and t9.approverid = ? and t1.employeeid = t9.employeeid and t1.billing_recid = t9.billingid and t1.recruiter_teamid = t9.teamid "
					+ " and t1.employeeid = t7.employeeid(+) and t1.billing_recid = t7.billingid(+) and t1.recruiter_teamid = t7.teamid(+) and t7.type(+)=1 " + " and t7.approverid = t3.id(+) and t7.teamid=t3.groupid(+) "
					+ " and t1.approved_by = t6.id(+) and t1.recruiter_teamid = t6.groupid(+) " + " and t1.employeeid = t8.employeeid(+) and t1.billing_recid = t8.billingid(+) and t1.recruiter_teamid = t8.teamid(+) and ?=t8.approverid(+) "
					+ " and t4.rfqid = t11.id(+) and t4.recruiter_teamid=t11.teamid(+) ";
			sql = " select * from ( " + sql + ") order by upper(employeename), billing_recid, weekendingdate desc ";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh mm a z");
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		paramList.add(jobDivaSession.getTeamId());
		paramList.add(approvedStatus);
		paramList.add(approvedStatus);
		paramList.add(startDate);
		paramList.add(endDate);
		paramList.add(firstname == null ? 0 : firstname.trim().length());
		paramList.add(firstname == null ? "" : firstname.toUpperCase() + "%");
		paramList.add(lastname == null ? 0 : lastname.trim().length());
		paramList.add(lastname == null ? "" : lastname.toUpperCase() + "%");
		paramList.add(companyid);
		paramList.add(companyid);
		if (managerid > 0)
			paramList.add(managerid);
		paramList.add(userid);
		//
		Object[] params = paramList.toArray();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<WeekEndingRecord> records = jdbcTemplate.query(sql, params, new RowMapper<WeekEndingRecord>() {
			
			@Override
			public WeekEndingRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
				TimesheetApproval vta = new TimesheetApproval();
				vta.setEmployeeid(rs.getLong("employeeid"));
				vta.setWeekendingDate(rs.getDate("weekendingdate"));
				vta.setBillrecordid(rs.getLong("billing_recid"));
				vta.setEmployeeName(rs.getString("employeename"));
				vta.setApproved(rs.getInt("approved"));
				if (rs.getTimestamp("approved_on") != null)
					vta.setApprovedOn(rs.getTimestamp("approved_on"));
				vta.setRemark("\""+rs.getString("approverid") + "|" + rs.getString("approvername") + "|" + rs.getBigDecimal("hoursworked") + "|" + rs.getBigDecimal("reg_hours") + "|" + rs.getBigDecimal("ot_hours") + "|" + rs.getBigDecimal("dt_hours")
						+ "|" + (rs.getDate("datecreated") == null ? "" : sdf.format(rs.getTimestamp("datecreated"))) +"\"" );
				vta.setBillrateper(rs.getString("bill_rate_per"));
				vta.setEntryformat(rs.getInt("timesheet_entry_format"));
				if (rs.getInt("ignored") == 1)
					vta.setApproved(10);
				vta.setApprovedBy(rs.getLong("myitem"));
				vta.setComments(rs.getString("companyname"));
				vta.setWorkingstate("\""+deNull(rs.getString("rfqtitle")) + "|" + deNull(rs.getString("rfqno_team"))+"\"");
				WeekEndingRecord wer = ConverterWeekEndingRecord(vta);
				return wer;
			}
		});
		return records;
	}
	
	private static WeekEndingRecord ConverterWeekEndingRecord(TimesheetApproval week) {
		WeekEndingRecord w = new WeekEndingRecord();
		Calendar myCal;
		if (week.getApproved() != null)
			w.setApproved(week.getApproved());
		w.setApprovedBy(week.getApprovedBy() == null ? 0 : week.getApprovedBy());
		if (week.getApprovedOn() != null) {
			myCal = new GregorianCalendar();
			myCal.setTime(week.getApprovedOn());
			w.setApprovedOn(myCal);
		}
		if (week.getBillrecordid() != null)
			w.setBillrecordid(week.getBillrecordid());
		if (week.getBillrateper() != null)
			w.setBillrateunit(week.getBillrateper());
		if (week.getEmployeeid() != null)
			w.setCandidateid(week.getEmployeeid());
		w.setComments("\"" + week.getComments() + "|" + week.getEmployeeName() + "\"");
		if (week.getEntryformat() != null)
			w.setEntryformat(week.getEntryformat());
		w.setRemark(week.getRemark());
		if (week.getWeekendingDate() != null) {
			myCal = new GregorianCalendar();
			myCal.setTime(week.getWeekendingDate());
			w.setWeekendingDate(myCal);
		}
		w.setWorkingstate(week.getWorkingstate());
		return w;
	}
}

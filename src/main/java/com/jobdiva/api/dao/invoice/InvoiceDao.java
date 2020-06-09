package com.jobdiva.api.dao.invoice;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.axelon.candidate.BillingExpenseCategory;
import com.axelon.oc4j.ServletRequestData;
import com.axelon.profit.EmployeeInvoice;
import com.axelon.profit.InvoiceExpense;
import com.axelon.shared.CandidateBillingRecord;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.ExpenseEntry;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.servlet.ServletTransporter;

@Component
public class InvoiceDao extends AbstractJobDivaDao {
	
	@SuppressWarnings("unchecked")
	public void sendMail(StringBuffer warning, Vector<Object> reqData) throws Exception {
		Vector<Object> rspData = (Vector<Object>) ServletTransporter.callServlet(getUtilServlet(), reqData);
		//
		int returncode = ((Integer) rspData.get(0)).intValue();
		//
		if (returncode == SUCCESSFUL || returncode == ERROR) {
			Vector<Object> failedEmails = (Vector<Object>) rspData.get(1);
			if (!failedEmails.isEmpty()) {
				warning.append("Sending notifications to these emails failed: ");
				for (Object email : failedEmails) {
					warning.append((String) email + ", ");
				}
			}
		} else if (returncode == UNEXPECTED_ERROR) {
			warning.append("Error occurs while sending emails. ");
		}
	}
	
	@SuppressWarnings("unchecked")
	public Integer addExpenseInvoice(JobDivaSession jobDivaSession, Long employeeid, Date weekendingdate, Date invoicedate, String feedback, String description, ExpenseEntry[] expenseEntries, String[] emailrecipients) throws Exception {
		//
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Object[] params;
		Object obj = null;
		Date startDate = null;
		Integer billingRecId = 0;
		Integer newInvoiceId = 0;
		StringBuffer warning = new StringBuffer();
		//
		Set<String> validCategories = new HashSet<String>();
		params = new Object[2];
		params[0] = new Integer(111);
		params[1] = new Long(jobDivaSession.getTeamId());
		ServletRequestData data = new ServletRequestData(0L, params);
		obj = ServletTransporter.callServlet(getCandidateFinancialServlet(), data);
		//
		if (obj instanceof Exception) {
			throw new Exception("Exception returned while looking up expense categories. " + ((Exception) obj).getMessage(), (Exception) obj);
		} else if (obj instanceof Vector) {
			Vector<Object> responed_categories = (Vector<Object>) obj;
			for (Object cat : responed_categories) {
				BillingExpenseCategory bec = (BillingExpenseCategory) cat;
				validCategories.add(bec.getName().trim());
			}
		}
		//
		Vector<Object> expenses = new Vector<Object>();
		EmployeeInvoice invoice = new EmployeeInvoice();
		invoice.setFeedback(feedback);
		invoice.setItemdescription(description);
		invoice.setApproverid(jobDivaSession.getRecruiterId());
		invoice.setInvoicedate(invoicedate);
		invoice.setClosed(0);
		//
		for (ExpenseEntry exp : expenseEntries) {
			if (exp.getExpenseamount() < 0) {
				warning.append("Invalid expense amount(" + exp.getExpenseamount() + ") for date " + sdf.format(exp.getDate().getTime()) + ". Skip. ");
				continue;
			}
			if (!validCategories.contains(exp.getCategory().trim())) {
				warning.append("Invalid expense category(" + exp.getCategory() + ") for date " + sdf.format(exp.getDate().getTime()) + ". Skip. ");
				continue;
			}
			InvoiceExpense expense = new InvoiceExpense();
			expense.setItem(exp.getCategory().trim());
			expense.setExpense(new BigDecimal(exp.getExpenseamount()));
			expense.setQuantity(new BigDecimal(exp.getQuantity()));
			expense.setDateoccur(exp.getDate());
			if (startDate == null)
				startDate = exp.getDate();
			else if (exp.getDate().compareTo(startDate) < 0)
				startDate = exp.getDate();
			expense.setComments(exp.getComments());
			expense.setRemark(sdf.format(weekendingdate));
			expense.setPayertype(exp.getPaybycompany() ? 1 : 0);
			expense.setBillable(exp.getBillable() ? 1 : 0);
			expense.setTemporaryid(0L);
			expenses.add(expense);
		}
		if (expenses.isEmpty())
			throw new Exception("No valid expense processed. " + warning.toString());
		// printLog("Locate bill/pay rec id...");
		CandidateBillingRecord bill_rec = new CandidateBillingRecord();
		bill_rec.mark = 33;
		bill_rec.teamID = jobDivaSession.getTeamId();
		bill_rec.candidateID = employeeid;
		bill_rec.startDate = startDate;
		ServletRequestData srd = new ServletRequestData(0, null, bill_rec);
		obj = ServletTransporter.callServlet(getCandidateBillingRecordsGetServlet(), srd);
		if (obj instanceof String) {
			String[] recids = ((String) obj).split("~");
			if (recids.length == 2) {
				billingRecId = Integer.parseInt(recids[0]);
			} else
				throw new Exception("Missing billing record id or pay record id " + recids);
		} else if (obj instanceof Exception) {
			throw new Exception("Error: exception returned when trying to locate parent bill/pay rec id. " + ((Exception) obj).getMessage(), (Exception) obj);
		}
		// printLog("Bill recid(" + billingRecId + "); pay recid(" + payRecId +
		// ")");
		invoice.setBillingRecordid(billingRecId);
		params = new Object[5];
		params[0] = new Integer(121);
		params[1] = new Long(employeeid);
		params[2] = expenses;
		params[3] = invoice;
		params[4] = jobDivaSession.getTeamId();
		Vector<Object> responedExpenses = new Vector<Object>();
		data = new ServletRequestData(0L, params);
		obj = ServletTransporter.callServlet(getCandidateFinancialServlet(), data);
		if (obj instanceof Exception)
			throw new Exception("Exception returned while saving expenses. " + ((Exception) obj).getMessage(), (Exception) obj);
		else if (obj instanceof Vector) {
			responedExpenses = (Vector<Object>) obj;
			InvoiceExpense expense = (InvoiceExpense) responedExpenses.get(0);
			newInvoiceId = expense.getInvoiceid();
		}
		//
		if (emailrecipients != null && emailrecipients.length > 0) {
			// Send email notifications to recipients
			Vector<Object> reqData = new Vector<Object>();
			reqData.add(8);
			reqData.add("apinotification@jobdivamail.com");
			Vector<String> receivers = new Vector<String>();
			for (String email : emailrecipients) {
				if (email != null && email.trim().length() > 0)
					receivers.add(email);
			}
			reqData.add(receivers);
			reqData.add("JobDiva API Notification - Add Expense Invoice");
			StringBuffer msg = new StringBuffer();
			msg.append("Method: addExpenseInvoice <br/><br/>");
			msg.append("Employee ID: " + employeeid + "<br/><br/>");
			msg.append("Weekending Date: " + sdf.format(weekendingdate) + "<br/><br/>");
			reqData.add(msg.toString());
			//
			sendMail(warning, reqData);
		}
		//
		return newInvoiceId;
	}
	
	public Boolean approveExpenseEntry(JobDivaSession jobDivaSession, Integer invoiceId, String comments, String[] emailRecipients) throws Exception {
		//
		StringBuffer warning = new StringBuffer();
		Object obj;
		Object[] params;
		EmployeeInvoice invoice = new EmployeeInvoice();
		invoice.setId(Long.valueOf(invoiceId));
		invoice.setRecruiterTeamid(Math.toIntExact(jobDivaSession.getTeamId()));
		invoice.setApproved(1);
		invoice.setApproverid(jobDivaSession.getRecruiterId());
		invoice.setComments(comments);
		params = new Object[3];
		params[0] = new Integer(661);
		params[1] = invoice;
		params[2] = jobDivaSession.getTeamId();
		//
		ServletRequestData data = new ServletRequestData(0L, params);
		obj = ServletTransporter.callServlet(getCandidateFinancialServlet(), data);
		//
		if (obj instanceof Exception)
			throw new Exception("Exception returned while approving expenses. " + ((Exception) obj).getMessage(), (Exception) obj);
		//
		// Send email notifications to recipients
		if (emailRecipients != null && emailRecipients.length > 0) {
			Vector<Object> reqData = new Vector<Object>();
			reqData.add(8);
			reqData.add("apinotification@jobdivamail.com");
			Vector<String> receivers = new Vector<String>();
			for (String email : emailRecipients) {
				if (email != null && email.trim().length() > 0)
					receivers.add(email);
			}
			reqData.add(receivers);
			reqData.add("JobDiva API Notification - Approve Expense Entry");
			StringBuffer msg = new StringBuffer();
			msg.append("Method: approveExpenseEntry <br/><br/>");
			msg.append("Invoice ID: " + invoiceId + "<br/><br/>");
			if (comments != null)
				msg.append("Comments: " + comments + "<br/><br/>");
			reqData.add(msg.toString());
			//
			sendMail(warning, reqData);
			//
		}
		//
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public Vector<Object> addExpenses(long clientid, long recruiterid, Date invoiceDate, Date weekendingDate, long employeeId, ExpenseEntry[] expenseEntries, String feedback, String description, boolean isUpdate, Long timecardId, String externalId)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Object[] params;
		Object obj = null;
		Date startDate = null;
		Integer billingRecId = 0;
		Integer newInvoiceId = 0;
		StringBuffer warning = new StringBuffer();
		Vector<Object> result = new Vector<Object>();
		//
		// Get list of valid expense categories (expense.item)
		Set<String> validCategories = new HashSet<String>();
		params = new Object[2];
		params[0] = new Integer(111);
		params[1] = new Long(clientid);
		ServletRequestData data = new ServletRequestData(0L, params);
		obj = ServletTransporter.callServlet(getCandidateFinancialServlet(), data);
		if (obj instanceof Exception) {
			throw new Exception("Exception returned while looking up expense categories. " + ((Exception) obj).getMessage(), (Exception) obj);
		} else if (obj instanceof Vector) {
			Vector<Object> responed_categories = (Vector<Object>) obj;
			for (Object cat : responed_categories) {
				BillingExpenseCategory bec = (BillingExpenseCategory) cat;
				validCategories.add(bec.getName().trim());
			}
		}
		Vector<Object> expenses = new Vector<Object>();
		EmployeeInvoice invoice = new EmployeeInvoice();
		invoice.setFeedback(feedback);
		invoice.setItemdescription(description);
		invoice.setApproverid(recruiterid);
		invoice.setInvoicedate(invoiceDate);
		if (isUpdate) {
			invoice.setClosed(Math.toIntExact(timecardId));
			invoice.setRemark(externalId);
		} else
			invoice.setClosed(0);
		for (ExpenseEntry exp : expenseEntries) {
			if (exp.getExpenseamount() < 0) {
				warning.append("Invalid expense amount(" + exp.getExpenseamount() + ") for date " + sdf.format(exp.getDate().getTime()) + ". Skip. ");
				continue;
			}
			if (!validCategories.contains(exp.getCategory().trim())) {
				warning.append("Invalid expense category(" + exp.getCategory() + ") for date " + sdf.format(exp.getDate().getTime()) + ". Skip. ");
				continue;
			}
			InvoiceExpense expense = new InvoiceExpense();
			expense.setItem(exp.getCategory().trim());
			expense.setExpense(new BigDecimal(exp.getExpenseamount()));
			expense.setQuantity(new BigDecimal(exp.getQuantity()));
			expense.setDateoccur(exp.getDate());
			if (startDate == null)
				startDate = exp.getDate();
			else if (exp.getDate().compareTo(startDate) < 0)
				startDate = exp.getDate();
			expense.setComments(exp.getComments());
			expense.setRemark(sdf.format(weekendingDate));
			expense.setPayertype(exp.getPaybycompany() ? 1 : 0);
			expense.setBillable(exp.getBillable() ? 1 : 0);
			expense.setTemporaryid(0L);
			expenses.add(expense);
		}
		if (expenses.isEmpty())
			throw new Exception("No valid expense processed. " + warning.toString());
		// printLog("Locate bill/pay rec id...");
		CandidateBillingRecord bill_rec = new CandidateBillingRecord();
		bill_rec.mark = 33;
		bill_rec.teamID = clientid;
		bill_rec.candidateID = employeeId;
		bill_rec.startDate = startDate;
		ServletRequestData srd = new ServletRequestData(0, null, bill_rec);
		obj = ServletTransporter.callServlet(getCandidateBillingRecordsGetServlet(), srd);
		if (obj instanceof String) {
			String[] recids = ((String) obj).split("~");
			if (recids.length == 2) {
				billingRecId = Integer.parseInt(recids[0]);
			} else
				throw new Exception("Missing billing record id or pay record id " + recids);
		} else if (obj instanceof Exception) {
			throw new Exception("Error: exception returned when trying to locate parent bill/pay rec id. " + ((Exception) obj).getMessage(), (Exception) obj);
		}
		// printLog("Bill recid(" + billingRecId + "); pay recid(" + payRecId +
		// ")");
		invoice.setBillingRecordid(billingRecId);
		params = new Object[5];
		params[0] = new Integer(121);
		params[1] = new Long(employeeId);
		params[2] = expenses;
		params[3] = invoice;
		params[4] = clientid;
		Vector<Object> responedExpenses = new Vector<Object>();
		data = new ServletRequestData(0L, params);
		obj = ServletTransporter.callServlet(getCandidateFinancialServlet(), data);
		if (obj instanceof Exception)
			throw new Exception("Exception returned while saving expenses. " + ((Exception) obj).getMessage(), (Exception) obj);
		else if (obj instanceof Vector) {
			responedExpenses = (Vector<Object>) obj;
			InvoiceExpense expense = (InvoiceExpense) responedExpenses.get(0);
			newInvoiceId = expense.getInvoiceid();
		}
		result.add(newInvoiceId);
		result.add(warning.toString());
		return result;
	}
	
	public Integer addExpenseEntry(JobDivaSession jobDivaSession, Long employeeId, Date weekendingDate, Date invoiceDate, String feedback, String description, ExpenseEntry[] expenses, String[] emailrecipients) throws Exception {
		StringBuffer message = new StringBuffer();
		StringBuffer warning = new StringBuffer();
		Vector<Object> result = new Vector<Object>();
		Integer newInvoiceId = 0;
		//
		try {
			result = addExpenses(jobDivaSession.getTeamId(), jobDivaSession.getRecruiterId(), invoiceDate, weekendingDate, employeeId, expenses, feedback, description, false, 0L, "");
			if (result.size() == 2) {
				newInvoiceId = (Integer) result.get(0);
				warning.append(result.get(1));
			}
		} catch (Exception e) {
			message.append(e.getMessage());
		}
		// Send email notifications to recipients
		if (emailrecipients != null && emailrecipients.length > 0) {
			Vector<Object> reqData = new Vector<Object>();
			reqData.add(8);
			reqData.add("apinotification@jobdivamail.com");
			Vector<String> receivers = new Vector<String>();
			for (String email : emailrecipients) {
				if (email != null && email.trim().length() > 0)
					receivers.add(email);
			}
			reqData.add(receivers);
			reqData.add("JobDiva API Notification - Add Expense Entry");
			StringBuffer msg = new StringBuffer();
			msg.append("Method: addExpenseEntry <br/><br/>");
			msg.append("Employee ID: " + employeeId + "<br/><br/>");
			msg.append("Weekending Date: " + simpleDateFormat.format(weekendingDate) + "<br/><br/>");
			reqData.add(msg.toString());
			try {
				sendMail(warning, reqData);
			} catch (Exception e) {
				message.append(e.getMessage());
			}
		}
		if (message.toString() != null && message.toString().length() > 1) {
			throw new Exception(message.toString());
		}
		//
		return newInvoiceId;
	}
	
	public Boolean updatePayrollProfile(JobDivaSession jobDivaSession, Long employeeid, Long salaryrecid, String generationsuffix, String reasonforhire, String gender, String companycode, String country, String address1, String address2,
			String address3, String city, String state, String county, String zipcode, Long telephone, String jobtitle, String workercategory, Boolean manageposition, String businessunit, String homedepartment, String codedhomedepartment,
			String benefitseligibilityclass, String naicsworkerscomp, Long standardhours, String federalmaritalstatus, Integer federalexemptions, String workedstatetaxcode, String statemaritalstatus, Integer stateexemptions, String livedstatetaxcode,
			String suisditaxcode, String bankdepositdeductioncode, Boolean bankfulldepositflag, Double bankdepositdeductionamount, String bankdeposittransitoraba, String bankdepositaccountnumber, String prenotificationmethod,
			Date bankdepositprenotedate, String allowedtakencode, String email, Integer resetyear, Boolean donotcaltaxfederalincome, Boolean donotcaltaxmedicare, Boolean donotcaltaxsocialsecurity, Boolean donotcaltaxstate, String retirementplan,
			String federalextrataxtype, Double federalextratax, String localtaxcode, String workedlocaltaxcode, String livedlocaltaxcode, String localschooldistricttaxcode, String localtaxcode4, String localtaxcode5, String healthcoveragecode,
			String payfrequency, String paygroup, Double w4deductions, Double w4dependents, Boolean w4multiplejobs, Double w4otherincome) throws Exception {
		//
		StringBuffer message = new StringBuffer();
		HashMap<String, Object> data = new HashMap<String, Object>();
		// data checking & saving
		data.put("RECRUITER_TEAMID", jobDivaSession.getTeamId());
		if (employeeid < 1)
			message.append("Error: Invalid employeeId(" + employeeid + ") ");
		else
			data.put("EMPLOYEEID", employeeid);
		//
		if (salaryrecid != null)
			data.put("SALARY_RECID", salaryrecid);
		else
			data.put("SALARY_RECID", 1);
		//
		if (isNotEmpty(generationsuffix)) {
			if (generationsuffix.length() > 10)
				message.append("Generation Suffix should be shorter than 10 characters. ");
			else
				data.put("GENERATIONSUFFIX", generationsuffix.trim());
		}
		//
		if (isNotEmpty(reasonforhire)) {
			if (reasonforhire.length() > 2000)
				message.append("Reason for Hire should be shorter than 2000 characters. ");
			else
				data.put("HIREREASON", reasonforhire.trim());
		}
		//
		if (isNotEmpty(gender)) {
			if (gender.length() > 1)
				message.append("Gender should be one character. ");
			else
				data.put("GENDER", gender.toUpperCase());
		}
		//
		if (isNotEmpty(companycode)) {
			if (companycode.length() > 10)
				message.append("Company Code should be shorter than 10 characters. ");
			else
				data.put("COMPANYCODE", companycode.trim());
		}
		//
		if (isNotEmpty(country)) {
			if (country.length() > 10)
				message.append("Country should be shorter than 10 characters. ");
			else
				data.put("COUNTRY", country.trim());
		}
		//
		if (isNotEmpty(address1)) {
			if (address1.length() > 2000)
				message.append("Address1 should be shorter than 2000 characters. ");
			else
				data.put("ADDRESS1", address1.trim());
		}
		//
		if (isNotEmpty(address2)) {
			if (address2.length() > 2000)
				message.append("Address2 should be shorter than 2000 characters. ");
			else
				data.put("ADDRESS2", address2.trim());
		}
		//
		if (isNotEmpty(address3)) {
			if (address3.length() > 2000)
				message.append("Address3 should be shorter than 2000 characters. ");
			else
				data.put("ADDRESS3", address3.trim());
		}
		//
		if (isNotEmpty(city)) {
			if (city.length() > 200)
				message.append("City should be shorter than 200 characters. ");
			else
				data.put("CITY", city.trim());
		}
		//
		if (isNotEmpty(state)) {
			if (state.length() > 200)
				message.append("State should be shorter than 200 characters. ");
			else
				data.put("STATE", state.trim());
		}
		//
		if (isNotEmpty(county)) {
			if (county.length() > 200)
				message.append("County should be shorter than 200 characters. ");
			else
				data.put("COUNTY", county.trim());
		}
		//
		if (isNotEmpty(zipcode)) {
			if (zipcode.length() > 50)
				message.append("Zipcode should be shorter than 50 characters. ");
			else
				data.put("ZIPCODE", zipcode.trim());
		}
		//
		if (telephone != null) {
			if (telephone <= 0)
				message.append("Invalid telephone(" + telephone + ")");
			else
				data.put("HPHONELAST", telephone);
		}
		//
		if (isNotEmpty(jobtitle)) {
			if (jobtitle.length() > 200)
				message.append("Job Title should be shorter than 200 characters. ");
			else
				data.put("JOBTITLE", jobtitle.trim());
		}
		//
		if (isNotEmpty(workercategory)) {
			if (workercategory.length() > 200)
				message.append("Worker Category should be shorter than 200 characters. ");
			else
				data.put("WORKERCATEGORY", workercategory.trim());
		}
		//
		if (manageposition != null) {
			data.put("MNGPOSITION", manageposition ? 1 : 0);
			//
		}
		//
		if (isNotEmpty(businessunit)) {
			if (businessunit.length() > 200)
				message.append("Business Unit should be shorter than 200 characters. ");
			else
				data.put("BUSINESSUNIT", businessunit.trim());
		}
		//
		if (isNotEmpty(homedepartment)) {
			if (homedepartment.length() > 200)
				message.append("Home Department should be shorter than 200 characters. ");
			else
				data.put("HOMEDEPT", homedepartment.trim());
		}
		//
		if (isNotEmpty(codedhomedepartment)) {
			if (codedhomedepartment.length() > 200)
				message.append("Coded Home Department should be shorter than 200 characters. ");
			else
				data.put("CODEDHOMEDEPT", codedhomedepartment.trim());
		}
		//
		if (isNotEmpty(benefitseligibilityclass)) {
			if (benefitseligibilityclass.length() > 200)
				message.append("Benefits Eligibility Class should be shorter than 200 characters. ");
			else
				data.put("BENEFITSELIGIBILITYCLASS", benefitseligibilityclass.trim());
		}
		//
		if (isNotEmpty(naicsworkerscomp)) {
			if (naicsworkerscomp.length() > 200)
				message.append("NAICS Workers Comp should be shorter than 200 characters. ");
			else
				data.put("NAICSWORKERCOMP", naicsworkerscomp.trim());
		}
		//
		if (standardhours != null) {
			if (standardhours < 0)
				message.append("Invalid Standard Hours(" + standardhours + ")");
			else
				data.put("STANDARDHOURS", standardhours);
		}
		//
		if (isNotEmpty(federalmaritalstatus)) {
			if (federalmaritalstatus.length() > 10)
				message.append("Federal Marital Status should be shorter than 10 characters. ");
			else
				data.put("FEDERALMARRITALSTATUS", federalmaritalstatus.trim());
		}
		//
		if (federalexemptions != null) {
			if (federalexemptions < 0)
				message.append("Invalid Federal Exemptions(" + federalexemptions + ")");
			else
				data.put("FEDERALEXEMPTIONS", federalexemptions);
		}
		//
		if (isNotEmpty(workedstatetaxcode)) {
			if (workedstatetaxcode.length() > 200)
				message.append("Worked State Tax Code should be shorter than 200 characters. ");
			else
				data.put("WORKEDINSTATE", workedstatetaxcode.trim());
		}
		//
		if (isNotEmpty(statemaritalstatus)) {
			if (statemaritalstatus.length() > 10)
				message.append("State Marital Status should be shorter than 10 characters. ");
			else
				data.put("STATEMARITALSTATUS", statemaritalstatus.trim());
		}
		//
		if (stateexemptions != null) {
			if (stateexemptions < 0)
				message.append("Invalid State Exemption(" + stateexemptions + ")");
			else
				data.put("STATEEXEMPTIONS", stateexemptions);
		}
		//
		if (isNotEmpty(livedstatetaxcode)) {
			if (livedstatetaxcode.length() > 200)
				message.append("Lived State Tax Code should be shorter than 200 characters. ");
			else
				data.put("LIVEDINSTATE", livedstatetaxcode.trim());
		}
		//
		if (isNotEmpty(suisditaxcode)) {
			if (suisditaxcode.length() > 200)
				message.append("SUISDI Tax Code should be shorter than 200 characters. ");
			else
				data.put("SUISDITAXCODE", suisditaxcode.trim());
		}
		//
		if (isNotEmpty(bankdepositdeductioncode)) {
			if (bankdepositdeductioncode.length() > 10)
				message.append("Bank Deposit Deduction Code should be shorter than 10 characters. ");
			else
				data.put("DEDUCTIONCODE1", bankdepositdeductioncode.trim());
		}
		//
		if (bankfulldepositflag != null) {
			data.put("ISFULLDEPOSIT1", bankfulldepositflag ? 1 : 0);
		}
		//
		if (bankdepositdeductionamount != null) {
			if (bankdepositdeductionamount < 0)
				message.append("Invalid Bank Deposit Deduction Amount(" + bankdepositdeductionamount + ")");
			else if (countDecimalDigits(bankdepositdeductionamount) > 2)
				message.append("Bank Deposit Deduction Amount should not contain more than two decimal digits. ");
			else
				data.put("DEDUCTIONAMOUNT1", bankdepositdeductionamount);
		}
		//
		if (isNotEmpty(bankdeposittransitoraba)) {
			if (bankdeposittransitoraba.length() > 200)
				message.append("Bank Deposit Transit Or ABA should be shorter than 200 characters. ");
			else
				data.put("TRANSITABANUMBER1", bankdeposittransitoraba.trim());
		}
		//
		if (isNotEmpty(bankdepositaccountnumber)) {
			if (bankdepositaccountnumber.length() > 200)
				message.append("Bank Deposit Account Number should be shorter than 200 characters. ");
			else
				data.put("BANKDEPOSITACCOUNTNUMBER1", bankdepositaccountnumber.trim());
		}
		//
		if (isNotEmpty(prenotificationmethod)) {
			if (prenotificationmethod.length() > 100)
				message.append("Prenotification Method should be shorter than 100 characters. ");
			else
				data.put("PRENOTIFICATIONMETHOD1", prenotificationmethod.trim());
		}
		//
		if (bankdepositprenotedate != null)
			data.put("BANKDEPOSITPRENOTEDATE1", new java.sql.Date(bankdepositprenotedate.getTime()));
		//
		if (isNotEmpty(allowedtakencode)) {
			if (allowedtakencode.length() > 10)
				message.append("Allowed Taken Code should be shorter than 10 characters. ");
			else
				data.put("ALLOWEDTAKENCODE", allowedtakencode.trim());
		}
		//
		if (isNotEmpty(email)) {
			if (email.length() > 500)
				message.append("Email should be shorter than 500 characters. ");
			else
				data.put("EMAIL", email.trim());
		}
		//
		if (resetyear != null && resetyear >= 0) {
			if (resetyear > 9999)
				message.append("Invalid Reset Year(" + resetyear + ")");
			else
				data.put("RESETYEAR", resetyear);
		}
		//
		if (donotcaltaxfederalincome != null) {
			data.put("DONOTCALTAX_FEDERALINCOME", donotcaltaxfederalincome ? 1 : 0);
		}
		//
		if (donotcaltaxmedicare != null) {
			data.put("DONOTCALTAX_MEDICARE", donotcaltaxmedicare ? 1 : 0);
		}
		//
		if (donotcaltaxsocialsecurity != null) {
			data.put("DONOTCALTAX_SOCIALSECURITY", donotcaltaxsocialsecurity ? 1 : 0);
		}
		//
		if (donotcaltaxstate != null) {
			data.put("DONOTCALTAX_STATE", donotcaltaxstate ? 1 : 0);
		}
		//
		if (isNotEmpty(retirementplan)) {
			if (retirementplan.length() > 200)
				message.append("Retirement Plan should be shorter than 200 characters. ");
			else
				data.put("RETIREMENTPLAN", retirementplan.trim());
		}
		//
		if (isNotEmpty(federalextrataxtype)) {
			if (federalextrataxtype.length() > 200)
				message.append("Federal Extra Tax Type should be shorter than 200 characters. ");
			else
				data.put("EXTRATAXTYPE", federalextrataxtype.trim());
		}
		//
		if (federalextratax != null) {
			if (federalextratax <= 0)
				message.append("Invalid Federal Extra Tax(" + federalextratax + ")");
			else if (countDecimalDigits(federalextratax) > 2)
				message.append("Federal Extra Tax should not contain more than two decimal digits. ");
			else
				data.put("EXTRATAX", federalextratax);
		}
		//
		if (isNotEmpty(localtaxcode)) {
			if (localtaxcode.length() > 200)
				message.append("Local Tax Code should be shorter than 200 characters. ");
			else
				data.put("LOCALTAXCODE0", localtaxcode.trim());
		}
		//
		if (isNotEmpty(workedlocaltaxcode)) {
			if (workedlocaltaxcode.length() > 200)
				message.append("WorkedLocal Tax Code should be shorter than 200 characters. ");
			else
				data.put("LOCALTAXCODE1", workedlocaltaxcode.trim());
		}
		//
		if (isNotEmpty(livedlocaltaxcode)) {
			if (livedlocaltaxcode.length() > 200)
				message.append("LivedLocal Tax Code should be shorter than 200 characters. ");
			else
				data.put("LOCALTAXCODE2", livedlocaltaxcode.trim());
		}
		//
		if (isNotEmpty(localschooldistricttaxcode)) {
			if (localschooldistricttaxcode.length() > 200)
				message.append("Local School District Tax Code should be shorter than 200 characters. ");
			else
				data.put("LOCALTAXCODE3", localschooldistricttaxcode.trim());
		}
		//
		if (isNotEmpty(localtaxcode4)) {
			if (localtaxcode4.length() > 200)
				message.append("Local Tax Code 4 should be shorter than 200 characters. ");
			else
				data.put("LOCALTAXCODE4", localtaxcode4.trim());
		}
		//
		if (isNotEmpty(localtaxcode5)) {
			if (localtaxcode5.length() > 200)
				message.append("Local Tax Code 5 should be shorter than 200 characters. ");
			else
				data.put("LOCALTAXCODE5", localtaxcode5.trim());
		}
		//
		if (isNotEmpty(healthcoveragecode)) {
			if (healthcoveragecode.length() > 1)
				message.append("Health Coverage Code should be 1 character. ");
			else
				data.put("HEALTHCOVERAGECODE", healthcoveragecode.trim());
		}
		//
		if (isNotEmpty(payfrequency)) {
			if (payfrequency.length() > 10)
				message.append("Pay Frequency should be no longer than 10 character. ");
			else
				data.put("PAYFREQUENCY", payfrequency.trim());
		}
		//
		if (isNotEmpty(paygroup)) {
			if (paygroup.length() > 10)
				message.append("Pay Group should be no longer than 10 character. ");
			else
				data.put("PAYGROUP", paygroup.trim());
		}
		//
		if (w4deductions != null) {
			if (w4deductions < 0)
				message.append("W-4 Deductions should be positive. ");
			else if (countDecimalDigits(w4deductions) > 2)
				message.append("W-4 Deductions should not contain more than two decimal digits. ");
			else
				data.put("W4_DEDUCTIONS", w4deductions);
		}
		//
		if (w4dependents != null) {
			if (w4dependents < 0)
				message.append("W-4 Dependents should not be negative. ");
			else if (countDecimalDigits(w4dependents) > 2)
				message.append("W-4 Dependents should not contain more than two decimal digits. ");
			else
				data.put("W4_DEPENDENTS", w4dependents);
		}
		//
		if (w4multiplejobs != null) {
			data.put("W4_MULTIPLEJOBS", w4multiplejobs ? 1 : 0);
		}
		//
		if (w4otherincome != null) {
			if (w4otherincome < 0)
				message.append("W-4 Other Income should not be negative. ");
			else if (countDecimalDigits(w4otherincome) > 2)
				message.append("W-4 Other Income should not contain more than two decimal digits. ");
			else
				data.put("W4_OTHERINCOME", w4otherincome);
		}
		//
		if (data.size() == 2)
			message.append("Must specify at least one value to execute.");
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		//
		//
		String sql = "SELECT * "//
				+ " from temployee_adp "//
				+ " WHERE employeeid = ? " //
				+ " AND recruiter_teamid = ? ";
		Object[] params = new Object[] { data.get("EMPLOYEEID"), data.get("RECRUITER_TEAMID") };
		//
		//
		List<Boolean> list = jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				return true;
			}
		});
		//
		if (list != null && list.size() > 0) {
			//
			data.put("DATEUPDATED", new Date());
			data.put("UPDATEDBY", jobDivaSession.getRecruiterId());
			//
			ArrayList<Object> paramList = new ArrayList<Object>();
			String sqlUpdate = "UPDATE temployee_adp SET ";
			//
			String whereStmt = " WHERE EMPLOYEEID = ? AND RECRUITER_TEAMID = ? ";
			//
			StringBuilder valueStmt = new StringBuilder();
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				//
				if (entry.getKey().equalsIgnoreCase("EMPLOYEEID") || entry.getKey().equalsIgnoreCase("RECRUITER_TEAMID"))
					continue;
				//
				valueStmt.append(entry.getKey() + "= ? ,");
				paramList.add(entry.getValue());
			}
			//
			paramList.add(data.get("EMPLOYEEID"));
			paramList.add(data.get("RECRUITER_TEAMID"));
			//
			//
			valueStmt.setLength(valueStmt.length() - 1);
			sqlUpdate += valueStmt.toString();
			sqlUpdate += whereStmt;
			//
			//
			jdbcTemplate.update(sqlUpdate, paramList.toArray());
			//
			//
		} else {
			//
			//
			data.put("DATECREATED", new Date());
			data.put("CREATEDBY", jobDivaSession.getRecruiterId());
			//
			ArrayList<String> fieldList = new ArrayList<String>();
			ArrayList<Object> paramList = new ArrayList<Object>();
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				fieldList.add(entry.getKey());
				paramList.add(entry.getValue());
			}
			//
			String sqlInsert = "INSERT INTO temployee_adp (" + sqlInsertFields(fieldList) + ") VALUES (" + sqlInsertParams(fieldList) + ") ";
			jdbcTemplate.update(sqlInsert, paramList.toArray());
			//
		}
		//
		return true;
	}
}

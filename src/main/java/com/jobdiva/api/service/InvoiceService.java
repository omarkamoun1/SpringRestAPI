package com.jobdiva.api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jobdiva.api.dao.invoice.InvoiceDao;
import com.jobdiva.api.dao.invoice.UpdateBillingRecordDao;
import com.jobdiva.api.dao.invoice.UpdatePayRecordDao;
import com.jobdiva.api.model.ExpenseEntry;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class InvoiceService {
	
	@Autowired
	InvoiceDao				invoiceDao;
	//
	@Autowired
	UpdateBillingRecordDao	updateBillingRecordDao;
	//
	@Autowired
	UpdatePayRecordDao		updatePayRecordDao;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Integer addExpenseInvoice(JobDivaSession jobDivaSession, Long employeeid, Date weekendingdate, Date invoicedate, String feedback, String description, ExpenseEntry[] expenses, String[] emailrecipients) throws Exception {
		//
		try {
			//
			Integer id = invoiceDao.addExpenseInvoice(jobDivaSession, employeeid, weekendingdate, invoicedate, feedback, description, expenses, emailrecipients);
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addExpenseInvoice", "Add Successful");
			//
			return id;
			//
		} catch (Exception e) {
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addExpenseInvoice", "Add Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean approveExpenseEntry(JobDivaSession jobDivaSession, Integer invoiceid, String comments, String[] emailrecipients) throws Exception {
		//
		try {
			//
			Boolean success = invoiceDao.approveExpenseEntry(jobDivaSession, invoiceid, comments, emailrecipients);
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "approveExpenseEntry", "Approve Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "approveExpenseEntry", "Approve Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Integer addExpenseEntry(JobDivaSession jobDivaSession, Long employeeid, Date weekendingdate, Date invoicedate, String feedback, String description, ExpenseEntry[] expenses, String[] emailrecipients) throws Exception {
		//
		try {
			//
			Integer id = invoiceDao.addExpenseEntry(jobDivaSession, employeeid, weekendingdate, invoicedate, feedback, description, expenses, emailrecipients);
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addExpenseEntry", "Add Successful");
			//
			return id;
			//
		} catch (Exception e) {
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addExpenseEntry", "Add Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean updatePayrollProfile(JobDivaSession jobDivaSession, Long employeeid, Long salaryrecid, String generationsuffix, String reasonforhire, String gender, String companycode, String country, String address1, String address2,
			String address3, String city, String state, String county, String zipcode, Long telephone, String jobtitle, String workercategory, Boolean manageposition, String businessunit, String homedepartment, String codedhomedepartment,
			String benefitseligibilityclass, String naicsworkerscomp, Long standardhours, String federalmaritalstatus, Integer federalexemptions, String workedstatetaxcode, String statemaritalstatus, Integer stateexemptions, String livedstatetaxcode,
			String suisditaxcode, String bankdepositdeductioncode, Boolean bankfulldepositflag, Double bankdepositdeductionamount, String bankdeposittransitoraba, String bankdepositaccountnumber, String prenotificationmethod,
			Date bankdepositprenotedate, String allowedtakencode, String email, Integer resetyear, Boolean donotcaltaxfederalincome, Boolean donotcaltaxmedicare, Boolean donotcaltaxsocialsecurity, Boolean donotcaltaxstate, String retirementplan,
			String federalextrataxtype, Double federalextratax, String localtaxcode, String workedlocaltaxcode, String livedlocaltaxcode, String localschooldistricttaxcode, String localtaxcode4, String localtaxcode5, String healthcoveragecode,
			String payfrequency, String paygroup, Double w4deductions, Double w4dependents, Boolean w4multiplejobs, Double w4otherincome) throws Exception {
		//
		try {
			//
			Boolean success = invoiceDao.updatePayrollProfile(jobDivaSession, employeeid, salaryrecid, generationsuffix, reasonforhire, gender, companycode, country, address1, address2, address3, city, state, county, zipcode, telephone, jobtitle,
					workercategory, manageposition, businessunit, homedepartment, codedhomedepartment, benefitseligibilityclass, naicsworkerscomp, standardhours, federalmaritalstatus, federalexemptions, workedstatetaxcode, statemaritalstatus,
					stateexemptions, livedstatetaxcode, suisditaxcode, bankdepositdeductioncode, bankfulldepositflag, bankdepositdeductionamount, bankdeposittransitoraba, bankdepositaccountnumber, prenotificationmethod, bankdepositprenotedate,
					allowedtakencode, email, resetyear, donotcaltaxfederalincome, donotcaltaxmedicare, donotcaltaxsocialsecurity, donotcaltaxstate, retirementplan, federalextrataxtype, federalextratax, localtaxcode, workedlocaltaxcode,
					livedlocaltaxcode, localschooldistricttaxcode, localtaxcode4, localtaxcode5, healthcoveragecode, payfrequency, paygroup, w4deductions, w4dependents, w4multiplejobs, w4otherincome);
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updatePayrollProfile", "Update Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updatePayrollProfile", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean updateBillingRecord(JobDivaSession jobDivaSession, Boolean allowEnterTimeOnPortal, Integer approved, Double assignmentID, Long billingContactID, Integer billingUnit, Double billRate, String billRatePer, Long candidateID,
			String customerRefNo, Long division, Double doubletimePer, Double doubletimeRate, String doubletimeRatePer, Boolean enableTimesheet, Date endDate, Boolean expenseEnabled, Integer expenseInvoices, Integer frequency, Long hiringManagerID,
			Double hoursPerDay, Double hoursPerHalfDay, Integer invoiceContent, String invoiceGroup, Integer invoiceGroupIndex, Double jobID, Integer overtimeByWorkingState, Boolean overtimeExempt, Double overtimeRate, String overtimeRatePer,
			String paymentTerms, Long primaryRecruiterID, Double primaryRecruiterPercentage, Double primarySalesPercentage, Long primarySalesPersonID, Integer recordID, Long secondaryRecruiterID, Double secondaryRecruiterPercentage,
			Double secondarySalesPercentage, Long secondarySalesPersonID, Date startDate, Long tertiaryRecruiterID, Double tertiaryRecruiterPercentage, Double tertiarySalesPercentage, Long tertiarySalesPersonID, Long timesheetEntryFormat,
			String timesheetInstruction, String vMSEmployeeName, String vMSWebsite, Integer weekEnding, String workAddress1, String workAddress2, String workCity, String workCountry, String workState, String workZipcode) throws Exception {
		//
		try {
			//
			Boolean success = updateBillingRecordDao.updateBillingRecord(jobDivaSession, allowEnterTimeOnPortal, approved, assignmentID, billingContactID, billingUnit, billRate, billRatePer, candidateID, customerRefNo, division, doubletimePer,
					doubletimeRate, doubletimeRatePer, enableTimesheet, endDate, expenseEnabled, expenseInvoices, frequency, hiringManagerID, hoursPerDay, hoursPerHalfDay, invoiceContent, invoiceGroup, invoiceGroupIndex, jobID,
					overtimeByWorkingState, overtimeExempt, overtimeRate, overtimeRatePer, paymentTerms, primaryRecruiterID, primaryRecruiterPercentage, primarySalesPercentage, primarySalesPersonID, recordID, secondaryRecruiterID,
					secondaryRecruiterPercentage, secondarySalesPercentage, secondarySalesPersonID, startDate, tertiaryRecruiterID, tertiaryRecruiterPercentage, tertiarySalesPercentage, tertiarySalesPersonID, timesheetEntryFormat,
					timesheetInstruction, vMSEmployeeName, vMSWebsite, weekEnding, workAddress1, workAddress2, workCity, workCountry, workState, workZipcode);
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateBillingRecord", "Update Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			updateBillingRecordDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateBillingRecord", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean UpdatePayRecord(JobDivaSession jobDivaSession, String aDPCOCODE, String aDPPAYFREQUENCY, Boolean approved, Double assignmentID, Long candidateID, Double doubletimeRate, String doubletimeRatePer, Date effectiveDate, //
			Date endDate, String fileNo, Double otherExpenses, String otherExpensesPer, Double outsideCommission, String outsideCommissionPer, Boolean overtimeExempt, Double overtimeRate, //
			String overtimeRatePer, String paymentTerms, Boolean payOnRemittance, Double perDiem, String perDiemPer, Integer recordID, Double salary, String salaryPer, Integer status, //
			Long subcontractCompanyID, String taxID) throws Exception {
		//
		try {
			//
			Boolean success = updatePayRecordDao.UpdatePayRecord(jobDivaSession, aDPCOCODE, aDPPAYFREQUENCY, approved, assignmentID, candidateID, doubletimeRate, doubletimeRatePer, effectiveDate, endDate, fileNo, otherExpenses, otherExpensesPer,
					outsideCommission, outsideCommissionPer, overtimeExempt, overtimeRate, overtimeRatePer, paymentTerms, payOnRemittance, perDiem, perDiemPer, recordID, salary, salaryPer, status, subcontractCompanyID, taxID);
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "UpdatePayRecord", "Update Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			updateBillingRecordDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "UpdatePayRecord", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
}

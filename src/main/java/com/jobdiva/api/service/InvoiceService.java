package com.jobdiva.api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.jobdiva.api.dao.invoice.CreateBillingRecordDao;
import com.jobdiva.api.dao.invoice.CreatePayRecordDao;
import com.jobdiva.api.dao.invoice.InvoiceDao;
import com.jobdiva.api.dao.invoice.UpdateBillingRecordDao;
import com.jobdiva.api.dao.invoice.UpdatePayRecordDao;
import com.jobdiva.api.model.ExpenseEntry;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class InvoiceService extends AbstractService {
	
	@Autowired
	InvoiceDao				invoiceDao;
	//
	@Autowired
	CreateBillingRecordDao	createBillingRecordDao;
	//
	@Autowired
	UpdateBillingRecordDao	updateBillingRecordDao;
	//
	@Autowired
	UpdatePayRecordDao		updatePayRecordDao;
	//
	@Autowired
	CreatePayRecordDao		createPayRecordDao;
	
	public Integer addExpenseInvoice(JobDivaSession jobDivaSession, Long employeeid, Date weekendingdate, Date invoicedate, String feedback, String description, ExpenseEntry[] expenses, String[] emailrecipients) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Integer>() {
				
				@Override
				public Integer doInTransaction(TransactionStatus status) {
					try {
						//
						Integer id = invoiceDao.addExpenseInvoice(jobDivaSession, employeeid, weekendingdate, invoicedate, feedback, description, expenses, emailrecipients);
						//
						invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addExpenseInvoice", "Add Successful");
						//
						return id;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addExpenseInvoice", "Add Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean approveExpenseEntry(JobDivaSession jobDivaSession, Integer expenseId, String comments, String[] emailrecipients, Long approverId) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = invoiceDao.approveExpenseEntry(jobDivaSession, expenseId, comments, emailrecipients, approverId);
						//
						invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "approveExpenseEntry", "Approve Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "approveExpenseEntry", "Approve Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Integer addExpenseEntry(JobDivaSession jobDivaSession, String employeeFirstName, String employeeLastName, Long employeeId, String vmsemployeeId, String expenseExternalId, Date weekendingDate, Date invoiceDate, String employeeComment,
			String description, ExpenseEntry[] expenses, String[] emailRecipients, Long jobId, Long activityId) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Integer>() {
				
				@Override
				public Integer doInTransaction(TransactionStatus status) {
					try {
						//
						Integer id = invoiceDao.addExpenseEntry(jobDivaSession, employeeFirstName, employeeLastName, employeeId, vmsemployeeId, expenseExternalId, weekendingDate, invoiceDate, employeeComment, description, expenses, emailRecipients,
								jobId, activityId);
						//
						invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addExpenseEntry", "Add Successful");
						//
						return id;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addExpenseEntry", "Add Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
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
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = invoiceDao.updatePayrollProfile(jobDivaSession, employeeid, salaryrecid, generationsuffix, reasonforhire, gender, companycode, country, address1, address2, address3, city, state, county, zipcode, telephone,
								jobtitle, workercategory, manageposition, businessunit, homedepartment, codedhomedepartment, benefitseligibilityclass, naicsworkerscomp, standardhours, federalmaritalstatus, federalexemptions, workedstatetaxcode,
								statemaritalstatus, stateexemptions, livedstatetaxcode, suisditaxcode, bankdepositdeductioncode, bankfulldepositflag, bankdepositdeductionamount, bankdeposittransitoraba, bankdepositaccountnumber,
								prenotificationmethod, bankdepositprenotedate, allowedtakencode, email, resetyear, donotcaltaxfederalincome, donotcaltaxmedicare, donotcaltaxsocialsecurity, donotcaltaxstate, retirementplan, federalextrataxtype,
								federalextratax, localtaxcode, workedlocaltaxcode, livedlocaltaxcode, localschooldistricttaxcode, localtaxcode4, localtaxcode5, healthcoveragecode, payfrequency, paygroup, w4deductions, w4dependents, w4multiplejobs,
								w4otherincome);
						//
						invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updatePayrollProfile", "Update Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updatePayrollProfile", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Integer createBillingRecord(JobDivaSession jobDivaSession, Long candidateID, Long assignmentID, Long jobID, Integer recordID, Long createdByID, Boolean approved, Date startDate, Date endDate, String customerRefNo, Long hiringManagerID,
			Long billingContactID, Long division, Integer invoiceGroupIndex, String invoiceGroup, String vMSWebsite, String vMSEmployeeName, Integer invoiceContent, Integer expenseInvoices, Boolean enableTimesheet, Boolean allowEnterTimeOnPortal,
			String timesheetInstruction, Boolean expenseEnabled, Double billRate, Integer billRateCurrrency, String billRatePer, Boolean overtimeExempt, Long timesheetEntryFormat, Integer frequency, Integer overtimeByWorkingState,
			Double overtimeRate, String overtimeRatePer, Double doubletimeRate, String doubletimePer, Integer billingUnit, Integer weekEnding, Double hoursPerDay, Double hoursPerHalfDay, String workAddress1, String workAddress2, String workCity,
			String workState, String workZipcode, String workCountry, Integer paymentTerms, Long primarySalesPersonID, Double primarySalesPercentage, Long secondarySalesPersonID, Double secondarySalesPercentage, Long tertiarySalesPersonID,
			Double tertiarySalesPercentage, Long primaryRecruiterID, Double primaryRecruiterPercentage, Long secondaryRecruiterID, Double secondaryRecruiterPercentage, Long tertiaryRecruiterID, Double tertiaryRecruiterPercentage) throws Exception {
		try {
			//
			return inTransaction(new TransactionCallback<Integer>() {
				
				@Override
				public Integer doInTransaction(TransactionStatus status) {
					try {
						//
						Integer result = createBillingRecordDao.createBillingRecord(jobDivaSession, candidateID, assignmentID, jobID, recordID, createdByID, approved, startDate, endDate, customerRefNo, hiringManagerID, billingContactID, division,
								invoiceGroupIndex, invoiceGroup, vMSWebsite, vMSEmployeeName, invoiceContent, expenseInvoices, enableTimesheet, allowEnterTimeOnPortal, timesheetInstruction, expenseEnabled, billRate, billRateCurrrency, billRatePer,
								overtimeExempt, timesheetEntryFormat, frequency, overtimeByWorkingState, overtimeRate, overtimeRatePer, doubletimeRate, doubletimePer, billingUnit, weekEnding, hoursPerDay, hoursPerHalfDay, workAddress1, workAddress2,
								workCity, workState, workZipcode, workCountry, paymentTerms, primarySalesPersonID, primarySalesPercentage, secondarySalesPersonID, secondarySalesPercentage, tertiarySalesPersonID, tertiarySalesPercentage,
								primaryRecruiterID, primaryRecruiterPercentage, secondaryRecruiterID, secondaryRecruiterPercentage, tertiaryRecruiterID, tertiaryRecruiterPercentage);
						//
						invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createBillingRecord", "Update Successful");
						//
						return result;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			createBillingRecordDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createBillingRecord", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
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
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = updateBillingRecordDao.updateBillingRecord(jobDivaSession, allowEnterTimeOnPortal, approved, assignmentID, billingContactID, billingUnit, billRate, billRateCurrrency, billRatePer, candidateID, customerRefNo,
								division, doubletimePer, doubletimeRate, doubletimeRatePer, enableTimesheet, endDate, expenseEnabled, expenseInvoices, frequency, biweeklySchedule, hiringManagerID, hoursPerDay, hoursPerHalfDay, invoiceContent,
								invoiceGroup, invoiceGroupIndex, jobID, overtimeByWorkingState, overtimeExempt, overtimeRate, overtimeRatePer, paymentTerms, primaryRecruiterID, primaryRecruiterPercentage, primarySalesPercentage, primarySalesPersonID,
								recordID, secondaryRecruiterID, secondaryRecruiterPercentage, secondarySalesPercentage, secondarySalesPersonID, startDate, tertiaryRecruiterID, tertiaryRecruiterPercentage, tertiarySalesPercentage,
								tertiarySalesPersonID, timesheetEntryFormat, timesheetInstruction, vMSEmployeeName, vMSWebsite, weekEnding, workAddress1, workAddress2, workCity, workCountry, workState, workZipcode, userfields);
						//
						invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateBillingRecord", "Update Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			updateBillingRecordDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateBillingRecord", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean updatePayRecord(JobDivaSession jobDivaSession, String aDPCOCODE, String aDPPAYFREQUENCY, Boolean approved, Double assignmentID, Long candidateID, Double doubletimeRate, String doubletimeRatePer, Date startDate, //
			Date endDate, String fileNo, Double otherExpenses, String otherExpensesPer, Double outsideCommission, String outsideCommissionPer, Boolean overtimeExempt, Double overtimeRate, //
			String overtimeRatePer, String paymentTerms, Boolean payOnRemittance, Double perDiem, String perDiemPer, Integer recordID, Double salary, String salaryPer, Integer salaryPerCurrency, Integer status1, //
			Long subcontractCompanyID, String taxID, Userfield[] userfields) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = updatePayRecordDao.updatePayRecord(jobDivaSession, aDPCOCODE, aDPPAYFREQUENCY, approved, assignmentID, candidateID, doubletimeRate, doubletimeRatePer, startDate, endDate, fileNo, otherExpenses,
								otherExpensesPer, outsideCommission, outsideCommissionPer, overtimeExempt, overtimeRate, overtimeRatePer, paymentTerms, payOnRemittance, perDiem, perDiemPer, recordID, salary, salaryPer, salaryPerCurrency, status1,
								subcontractCompanyID, taxID, userfields);
						//
						invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "UpdatePayRecord", "Update Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			updateBillingRecordDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "UpdatePayRecord", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Integer createPayRecord(JobDivaSession jobDivaSession, Long candidateID, Double assignmentID, Long jobID,
			/* Integer recordID, */ Boolean approved, Long createdByID, Date effectiveDate, Date endDate, Integer status1, String taxID, String paymentTerms, Long subcontractCompanyID, Boolean payOnRemittance, Double salary, String salaryPer,
			Integer salaryPerCurrency, Double perDiem, String perDiemPer, Double otherExpenses, String otherExpensesPer, Double outsideCommission, String outsideCommissionPer, Double overtimeRate, String overtimeRatePer, Double doubletimeRate,
			String doubletimeRatePer, Boolean overtimeExempt, String fileNo, String aDPCOCODE, String aDPPAYFREQUENCY) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Integer>() {
				
				@Override
				public Integer doInTransaction(TransactionStatus status) {
					try {
						//
						Integer recordId = createPayRecordDao.createPayRecord(jobDivaSession, candidateID, assignmentID, jobID,
								/* recordID, */ approved, createdByID, effectiveDate, endDate, status1, taxID, paymentTerms, subcontractCompanyID, payOnRemittance, salary, salaryPer, salaryPerCurrency, perDiem, perDiemPer, otherExpenses,
								otherExpensesPer, outsideCommission, outsideCommissionPer, overtimeRate, overtimeRatePer, doubletimeRate, doubletimeRatePer, overtimeExempt, fileNo, aDPCOCODE, aDPPAYFREQUENCY);
						//
						invoiceDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "CreatePayRecord", "Create Successful");
						//
						return recordId;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			updateBillingRecordDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "CreatePayRecord", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
}

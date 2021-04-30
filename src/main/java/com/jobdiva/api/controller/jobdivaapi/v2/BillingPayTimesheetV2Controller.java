package com.jobdiva.api.controller.jobdivaapi.v2;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.ExpenseEntry;
import com.jobdiva.api.model.Timesheet;
import com.jobdiva.api.model.TimesheetEntry;
import com.jobdiva.api.model.UploadTimesheetAssignment;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.v2.billingtimesheet.AddExpenseEntryDef;
import com.jobdiva.api.model.v2.billingtimesheet.AddExpenseInvoiceDef;
import com.jobdiva.api.model.v2.billingtimesheet.ApproveExpenseEntryDef;
import com.jobdiva.api.model.v2.billingtimesheet.CreateBillingRecordDef;
import com.jobdiva.api.model.v2.billingtimesheet.CreatePayRecordDef;
import com.jobdiva.api.model.v2.billingtimesheet.MarkTimesheetPaidDef;
import com.jobdiva.api.model.v2.billingtimesheet.UpdateBillingRecordDef;
import com.jobdiva.api.model.v2.billingtimesheet.UpdatePayRecordDef;
import com.jobdiva.api.model.v2.billingtimesheet.UpdatePayrollProfileDef;
import com.jobdiva.api.model.v2.billingtimesheet.UploadTimesheetAssignmentDef;
import com.jobdiva.api.model.v2.billingtimesheet.UploadTimesheetDef;
import com.jobdiva.api.service.InvoiceService;
import com.jobdiva.api.service.TimesheetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/apiv2/jobdiva/")
@Api(value = "Billing, Pay, Timesheet API", description = "REST API Used For Billing, Pay and Timesheet")
// @ApiIgnore
public class BillingPayTimesheetV2Controller extends AbstractJobDivaController {
	//
	
	@Autowired
	TimesheetService	timesheetService;
	//
	@Autowired
	InvoiceService		invoiceService;
	//
	
	@RequestMapping(value = "/uploadTimesheet", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Upload Timesheet")
	public Long uploadTimesheet( //
			//
			@ApiParam(value = "employeeid : The internal JobDiva ID of the employee (required)\r\n" //
					+ "jobid : The internal JobDiva ID of the job  (not required)\r\n" //
					+ "weekendingdate : The weekending date of the timesheet Format [yyyy-MM-dd'T'HH:mm:ss] (required) \r\n" //
					+ "approved : Specify if the timesheet is approved or not (required) \r\n" //
					+ "timesheetid : If specified, will update the timesheet with its ID \r\n" //
					+ "externalId : Timesheet’s external ID \r\n" //
					+ "timesheet : Must be exactly seven (7) timesheet entries for each day of a week (required)\r\n") //
			@RequestBody UploadTimesheetDef uploadTimesheetDef
	//
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("uploadTimesheet");
		//
		Long employeeid = uploadTimesheetDef.getEmployeeid();
		Long jobid = uploadTimesheetDef.getJobid();
		Date weekendingdate = uploadTimesheetDef.getWeekendingdate();
		Boolean approved = uploadTimesheetDef.getApproved();
		Timesheet[] timesheet = uploadTimesheetDef.getTimesheet();
		Long timesheetId = uploadTimesheetDef.getTimesheetId();
		String externalId = uploadTimesheetDef.getExternalId();
		//
		return timesheetService.uploadTimesheet(jobDivaSession, employeeid, jobid, weekendingdate, approved, timesheetId, externalId, timesheet);
		//
		//
	}
	
	@RequestMapping(value = "/uploadTimesheetAssignment", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "upload Timesheet Assignment")
	public UploadTimesheetAssignment uploadTimesheetAssignment( //
			//
			@ApiParam(value = "employeeid : The internal JobDiva ID of the employee \r\n" //
					+ "jobid : The internal JobDiva ID of the job \r\n" //
					+ "weekendingdate : The weekending date of the timesheet \r\n" //
					+ "payrate : The timesheet pay rate \r\n" //
					+ "overtimepayrate : Overtime pay rate \r\n" //
					+ "doubletimepayrate : Double time pay rate \r\n" //
					+ "billrate : Bill rate \r\n" //
					+ "overtimebillrate : Overtime bill rate \r\n" //
					+ "doubletimebillrate : Double time bill rate \r\n" //
					+ "location : work location \r\n" //
					+ "title : Job title \r\n" //
					+ "rolenumber : Role number \r\n" //
					+ "timesheetid : If specified, will update the timesheet with its ID \r\n" //
					+ "externalid : Timesheet’s external ID \r\n" //
					+ "compcode : Comp Code or Insurance Code fir this assignment \r\n" //
					+ "timesheetEntry : Can specify any number of days within the week, but at least one valid entry must be provided. \r\n" //
					+ "expenses : Entries of expenses that are associated with this timesheet record. \r\n" //
					+ "emailrecipients : Email where API notification will be sent to") //
			//
			@RequestBody UploadTimesheetAssignmentDef uploadTimesheetAssignmentDef
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("uploadTimesheetAssignment");
		//
		Long employeeid = uploadTimesheetAssignmentDef.getEmployeeid();
		Long jobid = uploadTimesheetAssignmentDef.getJobid();
		Date weekendingdate = uploadTimesheetAssignmentDef.getWeekendingdate();
		Double payrate = uploadTimesheetAssignmentDef.getPayrate();
		Double overtimepayrate = uploadTimesheetAssignmentDef.getOvertimepayrate();
		Double doubletimepayrate = uploadTimesheetAssignmentDef.getDoubletimepayrate();
		Double billrate = uploadTimesheetAssignmentDef.getBillrate();
		Double overtimebillrate = uploadTimesheetAssignmentDef.getOvertimebillrate();
		Double doubletimebillrate = uploadTimesheetAssignmentDef.getDoubletimebillrate();
		String location = uploadTimesheetAssignmentDef.getLocation();
		String title = uploadTimesheetAssignmentDef.getTitle();
		String rolenumber = uploadTimesheetAssignmentDef.getRolenumber();
		Long timesheetid = uploadTimesheetAssignmentDef.getTimesheetid();
		String externalid = uploadTimesheetAssignmentDef.getExternalid();
		String compcode = uploadTimesheetAssignmentDef.getCompcode();
		TimesheetEntry[] timesheetEntry = uploadTimesheetAssignmentDef.getTimesheetEntry();
		ExpenseEntry[] expenses = uploadTimesheetAssignmentDef.getExpenses();
		String[] emailrecipients = uploadTimesheetAssignmentDef.getEmailrecipients();
		//
		return timesheetService.uploadTimesheetAssignment(jobDivaSession, employeeid, jobid, weekendingdate, payrate, overtimepayrate, doubletimepayrate, billrate, overtimebillrate, doubletimebillrate, location, title, rolenumber, timesheetid,
				externalid, compcode, timesheetEntry, expenses, emailrecipients);
		//
	}
	
	@RequestMapping(value = "/addExpenseEntry", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Add Expense Entry")
	public Integer addExpenseEntry( //
			//
			@ApiParam(value = "employeeid : The internal JobDiva ID of the invoice \r\n" //
					+ "weekendingdate : The weekending date of the expenses \r\n" //
					+ "invoicedate : The invoice date of the expenses \r\n" //
					+ "feedback : Company’s feedback \r\n" //
					+ "description : Expenses description \r\n" //
					+ "expenses : Detailed information of each expense. Must contain at least one expense entry. \r\n"//
					+ "emailrecipients : Email where API notification will be sent to") //
			@RequestBody AddExpenseEntryDef addExpenseEntryDef
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("addExpenseEntry");
		//
		Long employeeid = addExpenseEntryDef.getEmployeeid();
		Date weekendingdate = addExpenseEntryDef.getWeekendingdate();
		Date invoicedate = addExpenseEntryDef.getInvoicedate();
		String feedback = addExpenseEntryDef.getFeedback();
		String description = addExpenseEntryDef.getDescription();
		ExpenseEntry[] expenses = addExpenseEntryDef.getExpenses();
		String[] emailrecipients = addExpenseEntryDef.getEmailrecipients();
		//
		//
		return invoiceService.addExpenseEntry(jobDivaSession, employeeid, weekendingdate, invoicedate, feedback, description, expenses, emailrecipients);
		//
	}
	
	@RequestMapping(value = "/approveExpenseEntry", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Approve Expense Entry")
	public Boolean approveExpenseEntry( //
			//
			@ApiParam(value = "invoiceid : The internal JobDiva ID of the invoice \r\n" //
					+ "comments : Additional comments \r\n" //
					+ "emailrecipients : Email where API notification will be sent to")
			//
			@RequestBody ApproveExpenseEntryDef approveExpenseEntryDef
	//
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		jobDivaSession.checkForAPIPermission("approveExpenseEntry");
		//
		//
		Integer invoiceid = approveExpenseEntryDef.getInvoiceid();
		String comments = approveExpenseEntryDef.getComments();
		String[] emailrecipients = approveExpenseEntryDef.getEmailrecipients();
		//
		return invoiceService.approveExpenseEntry(jobDivaSession, invoiceid, comments, emailrecipients);
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "expenses", required = true, allowMultiple = true, dataType = "ExpenseEntry") })
	@RequestMapping(value = "/addExpenseInvoice", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Add Expense Invoice")
	public Integer addExpenseInvoice( //
			//
			@ApiParam(value = "employeeid : The internal JobDiva ID of the employee \r\n" //
					+ "weekendingdate : The weekending date of the expenses \r\n" //
					+ "invoicedate : The invoice date of the expenses \r\n" //
					+ "feedback : Company’s feedback \r\n" //
					+ "description : Expenses description \r\n" //
					+ "expenses : Detailed information of each expense. Must contain at least one expense entry. \r\n" //
					+ "emailrecipients : Email where API notification will be sent to")
			//
			@RequestBody AddExpenseInvoiceDef addExpenseInvoiceDef
	//
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("addExpenseInvoice");
		//
		Long employeeid = addExpenseInvoiceDef.getEmployeeid();
		Date weekendingdate = addExpenseInvoiceDef.getWeekendingdate();
		Date invoicedate = addExpenseInvoiceDef.getInvoicedate();
		String feedback = addExpenseInvoiceDef.getFeedback();
		String description = addExpenseInvoiceDef.getDescription();
		ExpenseEntry[] expenses = addExpenseInvoiceDef.getExpenses();
		String[] emailrecipients = addExpenseInvoiceDef.getEmailrecipients();
		//
		return invoiceService.addExpenseInvoice(jobDivaSession, employeeid, weekendingdate, invoicedate, feedback, description, expenses, emailrecipients);
		//
	}
	
	@RequestMapping(value = "/markTimesheetPaid", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Mark Timesheet Paid")
	public Boolean markTimesheetPaid( //
			//
			@ApiParam(value = "employeeid : JobDiva internal ID of the employee \r\n" //
					+ "salaryrecordid : Salary Record ID \r\n" //
					+ "datepaid : Date timesheet(s) are paid  \r\n" //
					+ "timesheetdate : Target timesheet(s) to be marked as paid")
			//
			@RequestBody MarkTimesheetPaidDef markTimesheetPaidDef
	//
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("markTimesheetPaid");
		//
		Long employeeid = markTimesheetPaidDef.getEmployeeid();
		Integer salaryrecordid = markTimesheetPaidDef.getSalaryrecordid();
		Date datepaid = markTimesheetPaidDef.getDatepaid();
		Date[] timesheetdate = markTimesheetPaidDef.getTimesheetdate();
		//
		return timesheetService.markTimesheetPaid(jobDivaSession, employeeid, salaryrecordid, datepaid, timesheetdate);
		//
	}
	
	@RequestMapping(value = "/updatePayrollProfile", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Payroll Profile")
	public Boolean updatePayrollProfile( //
			//
			@RequestBody UpdatePayrollProfileDef updatePayrollProfileDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updatePayrollProfile");
		//
		Long employeeid = updatePayrollProfileDef.getEmployeeid();
		Long salaryrecid = updatePayrollProfileDef.getSalaryrecid();
		String generationsuffix = updatePayrollProfileDef.getGenerationsuffix();
		String reasonforhire = updatePayrollProfileDef.getReasonforhire();
		String gender = updatePayrollProfileDef.getGender();
		String companycode = updatePayrollProfileDef.getCompanycode();
		String country = updatePayrollProfileDef.getCountry();
		String address1 = updatePayrollProfileDef.getAddress1();
		String address2 = updatePayrollProfileDef.getAddress2();
		String address3 = updatePayrollProfileDef.getAddress3();
		String city = updatePayrollProfileDef.getCity();
		String state = updatePayrollProfileDef.getState();
		String county = updatePayrollProfileDef.getCountry();
		String zipcode = updatePayrollProfileDef.getZipcode();
		Long telephone = updatePayrollProfileDef.getTelephone();
		String jobtitle = updatePayrollProfileDef.getJobtitle();
		String workercategory = updatePayrollProfileDef.getWorkercategory();
		Boolean manageposition = updatePayrollProfileDef.getManageposition();
		String businessunit = updatePayrollProfileDef.getBusinessunit();
		String homedepartment = updatePayrollProfileDef.getHomedepartment();
		String codedhomedepartment = updatePayrollProfileDef.getCodedhomedepartment();
		String benefitseligibilityclass = updatePayrollProfileDef.getBenefitseligibilityclass();
		String naicsworkerscomp = updatePayrollProfileDef.getNaicsworkerscomp();
		Long standardhours = updatePayrollProfileDef.getStandardhours();
		//
		String federalmaritalstatus = updatePayrollProfileDef.getFederalmaritalstatus();
		Integer federalexemptions = updatePayrollProfileDef.getFederalexemptions();
		String workedstatetaxcode = updatePayrollProfileDef.getWorkedstatetaxcode();
		String statemaritalstatus = updatePayrollProfileDef.getStatemaritalstatus();
		Integer stateexemptions = updatePayrollProfileDef.getStateexemptions();
		String livedstatetaxcode = updatePayrollProfileDef.getLivedlocaltaxcode();
		String suisditaxcode = updatePayrollProfileDef.getSuisditaxcode();
		String bankdepositdeductioncode = updatePayrollProfileDef.getBankdepositdeductioncode();
		//
		Boolean bankfulldepositflag = updatePayrollProfileDef.getBankfulldepositflag();
		Double bankdepositdeductionamount = updatePayrollProfileDef.getBankdepositdeductionamount();
		String bankdeposittransitoraba = updatePayrollProfileDef.getBankdeposittransitoraba();
		String bankdepositaccountnumber = updatePayrollProfileDef.getBankdepositaccountnumber();
		//
		String prenotificationmethod = updatePayrollProfileDef.getPrenotificationmethod();
		Date bankdepositprenotedate = updatePayrollProfileDef.getBankdepositprenotedate();
		String allowedtakencode = updatePayrollProfileDef.getAllowedtakencode();
		String email = updatePayrollProfileDef.getEmail();
		Integer resetyear = updatePayrollProfileDef.getResetyear();
		//
		Boolean donotcaltaxfederalincome = updatePayrollProfileDef.getDonotcaltaxfederalincome();
		Boolean donotcaltaxmedicare = updatePayrollProfileDef.getDonotcaltaxmedicare();
		Boolean donotcaltaxsocialsecurity = updatePayrollProfileDef.getDonotcaltaxsocialsecurity();
		Boolean donotcaltaxstate = updatePayrollProfileDef.getDonotcaltaxstate();
		//
		String retirementplan = updatePayrollProfileDef.getRetirementplan();
		String federalextrataxtype = updatePayrollProfileDef.getFederalextrataxtype();
		Double federalextratax = updatePayrollProfileDef.getFederalextratax();
		String localtaxcode = updatePayrollProfileDef.getLocaltaxcode();
		String workedlocaltaxcode = updatePayrollProfileDef.getWorkedlocaltaxcode();
		String livedlocaltaxcode = updatePayrollProfileDef.getLivedlocaltaxcode();
		String localschooldistricttaxcode = updatePayrollProfileDef.getLocalschooldistricttaxcode();
		String localtaxcode4 = updatePayrollProfileDef.getLocaltaxcode4();
		String localtaxcode5 = updatePayrollProfileDef.getLocaltaxcode5();
		String healthcoveragecode = updatePayrollProfileDef.getHealthcoveragecode();
		String payfrequency = updatePayrollProfileDef.getPayfrequency();
		String paygroup = updatePayrollProfileDef.getPaygroup();
		Double w4deductions = updatePayrollProfileDef.getW4deductions();
		Double w4dependents = updatePayrollProfileDef.getW4dependents();
		Boolean w4multiplejobs = updatePayrollProfileDef.getW4multiplejobs();
		Double w4otherincome = updatePayrollProfileDef.getW4otherincome();
		//
		//
		//
		return invoiceService.updatePayrollProfile(jobDivaSession, employeeid, salaryrecid, generationsuffix, reasonforhire, gender, companycode, country, address1, address2, address3, city, state, county, zipcode, telephone, jobtitle,
				workercategory, manageposition, businessunit, homedepartment, codedhomedepartment, benefitseligibilityclass, naicsworkerscomp, standardhours, federalmaritalstatus, federalexemptions, workedstatetaxcode, statemaritalstatus,
				stateexemptions, livedstatetaxcode, suisditaxcode, bankdepositdeductioncode, bankfulldepositflag, bankdepositdeductionamount, bankdeposittransitoraba, bankdepositaccountnumber, prenotificationmethod, bankdepositprenotedate,
				allowedtakencode, email, resetyear, donotcaltaxfederalincome, donotcaltaxmedicare, donotcaltaxsocialsecurity, donotcaltaxstate, retirementplan, federalextrataxtype, federalextratax, localtaxcode, workedlocaltaxcode, livedlocaltaxcode,
				localschooldistricttaxcode, localtaxcode4, localtaxcode5, healthcoveragecode, payfrequency, paygroup, w4deductions, w4dependents, w4multiplejobs, w4otherincome);
		//
	}
	
	@RequestMapping(value = "/createBillingRecord", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Billing Record")
	public Integer createBillingRecord( //
			//
			@RequestBody CreateBillingRecordDef createBillingRecordDef
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		jobDivaSession.checkForAPIPermission("createBillingRecord");
		//
		//
		//
		Long candidateID = createBillingRecordDef.getCandidateID();
		Long assignmentID = createBillingRecordDef.getAssignmentID();
		Long jobID = createBillingRecordDef.getJobID();
		Integer recordID = createBillingRecordDef.getRecordID();
		Long createdByID = createBillingRecordDef.getCreatedByID();
		Boolean approved = createBillingRecordDef.getApproved();
		Date startDate = createBillingRecordDef.getStartDate();
		Date endDate = createBillingRecordDef.getEndDate();
		String customerRefNo = createBillingRecordDef.getCustomerRefNo();
		Long hiringManagerID = createBillingRecordDef.getHiringManagerID();
		Long billingContactID = createBillingRecordDef.getBillingContactID();
		Long division = createBillingRecordDef.getDivision();
		Integer invoiceGroupIndex = createBillingRecordDef.getInvoiceGroupIndex();
		String invoiceGroup = createBillingRecordDef.getInvoiceGroup();
		String vMSWebsite = createBillingRecordDef.getvMSWebsite();
		String vMSEmployeeName = createBillingRecordDef.getvMSEmployeeName();
		Integer invoiceContent = createBillingRecordDef.getInvoiceContent();
		Integer expenseInvoices = createBillingRecordDef.getExpenseInvoices();
		Boolean enableTimesheet = createBillingRecordDef.getEnableTimesheet();
		Boolean allowEnterTimeOnPortal = createBillingRecordDef.getAllowEnterTimeOnPortal();
		String timesheetInstruction = createBillingRecordDef.getTimesheetInstruction();
		Boolean expenseEnabled = createBillingRecordDef.getExpenseEnabled();
		Double billRate = createBillingRecordDef.getBillRate();
		String billRatePer = createBillingRecordDef.getBillRatePer();
		Boolean overtimeExempt = createBillingRecordDef.getOvertimeExempt();
		Long timesheetEntryFormat = createBillingRecordDef.getTimesheetEntryFormat();
		Integer frequency = createBillingRecordDef.getFrequency();
		Integer overtimeByWorkingState = createBillingRecordDef.getOvertimeByWorkingState();
		Double overtimeRate = createBillingRecordDef.getOvertimeRate();
		String overtimeRatePer = createBillingRecordDef.getOvertimeRatePer();
		Double doubletimeRate = createBillingRecordDef.getDoubletimeRate();
		String doubletimePer = createBillingRecordDef.getDoubletimePer();
		Integer billingUnit = createBillingRecordDef.getBillingUnit();
		Integer weekEnding = createBillingRecordDef.getWeekEnding();
		Double hoursPerDay = createBillingRecordDef.getHoursPerDay();
		Double hoursPerHalfDay = createBillingRecordDef.getHoursPerHalfDay();
		String workAddress1 = createBillingRecordDef.getWorkAddress1();
		String workAddress2 = createBillingRecordDef.getWorkAddress2();
		String workCity = createBillingRecordDef.getWorkCity();
		String workState = createBillingRecordDef.getWorkState();
		String workZipcode = createBillingRecordDef.getWorkZipcode();
		String workCountry = createBillingRecordDef.getWorkCountry();
		Integer paymentTerms = createBillingRecordDef.getPaymentTerms();
		Long primarySalesPersonID = createBillingRecordDef.getPrimarySalesPersonID();
		Double primarySalesPercentage = createBillingRecordDef.getPrimarySalesPercentage();
		Long secondarySalesPersonID = createBillingRecordDef.getSecondarySalesPersonID();
		Double secondarySalesPercentage = createBillingRecordDef.getSecondarySalesPercentage();
		Long tertiarySalesPersonID = createBillingRecordDef.getTertiarySalesPersonID();
		Double tertiarySalesPercentage = createBillingRecordDef.getTertiarySalesPercentage();
		Long primaryRecruiterID = createBillingRecordDef.getPrimaryRecruiterID();
		Double primaryRecruiterPercentage = createBillingRecordDef.getPrimaryRecruiterPercentage();
		Long secondaryRecruiterID = createBillingRecordDef.getSecondaryRecruiterID();
		Double secondaryRecruiterPercentage = createBillingRecordDef.getSecondaryRecruiterPercentage();
		Long tertiaryRecruiterID = createBillingRecordDef.getTertiaryRecruiterID();
		Double tertiaryRecruiterPercentage = createBillingRecordDef.getTertiaryRecruiterPercentage();
		//
		//
		//
		return invoiceService.createBillingRecord(jobDivaSession, candidateID, assignmentID, jobID, recordID, createdByID, approved, startDate, endDate, customerRefNo, hiringManagerID, billingContactID, division, invoiceGroupIndex, invoiceGroup,
				vMSWebsite, vMSEmployeeName, invoiceContent, expenseInvoices, enableTimesheet, allowEnterTimeOnPortal, timesheetInstruction, expenseEnabled, billRate, billRatePer, overtimeExempt, timesheetEntryFormat, frequency,
				overtimeByWorkingState, overtimeRate, overtimeRatePer, doubletimeRate, doubletimePer, billingUnit, weekEnding, hoursPerDay, hoursPerHalfDay, workAddress1, workAddress2, workCity, workState, workZipcode, workCountry, paymentTerms,
				primarySalesPersonID, primarySalesPercentage, secondarySalesPersonID, secondarySalesPercentage, tertiarySalesPersonID, tertiarySalesPercentage, primaryRecruiterID, primaryRecruiterPercentage, secondaryRecruiterID,
				secondaryRecruiterPercentage, tertiaryRecruiterID, tertiaryRecruiterPercentage);
		//
	}
	
	@RequestMapping(value = "/updateBillingRecord", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Billing Record")
	public Boolean updateBillingRecord( //
			@RequestBody UpdateBillingRecordDef updateBillingRecordDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateBillingRecord");
		//
		//
		Boolean allowEnterTimeOnPortal = updateBillingRecordDef.getAllowEnterTimeOnPortal();
		Integer approved = updateBillingRecordDef.getApproved();
		Double assignmentID = updateBillingRecordDef.getAssignmentID();
		Long billingContactID = updateBillingRecordDef.getBillingContactID();
		Integer billingUnit = updateBillingRecordDef.getBillingUnit();
		Double billRate = updateBillingRecordDef.getBillRate();
		String billRatePer = updateBillingRecordDef.getBillRatePer();
		Long candidateID = updateBillingRecordDef.getCandidateID();
		String customerRefNo = updateBillingRecordDef.getCustomerRefNo();
		Long division = updateBillingRecordDef.getDivision();
		Double doubletimePer = updateBillingRecordDef.getDoubletimePer();
		Double doubletimeRate = updateBillingRecordDef.getDoubletimePer();
		String doubletimeRatePer = updateBillingRecordDef.getDoubletimeRatePer();
		Boolean enableTimesheet = updateBillingRecordDef.getEnableTimesheet();
		Date endDate = updateBillingRecordDef.getEndDate();
		Boolean expenseEnabled = updateBillingRecordDef.getExpenseEnabled();
		Integer expenseInvoices = updateBillingRecordDef.getExpenseInvoices();
		Integer frequency = updateBillingRecordDef.getFrequency();
		Long hiringManagerID = updateBillingRecordDef.getHiringManagerID();
		Double hoursPerDay = updateBillingRecordDef.getHoursPerDay();
		Double hoursPerHalfDay = updateBillingRecordDef.getHoursPerHalfDay();
		Integer invoiceContent = updateBillingRecordDef.getInvoiceContent();
		String invoiceGroup = updateBillingRecordDef.getInvoiceGroup();
		Integer invoiceGroupIndex = updateBillingRecordDef.getInvoiceGroupIndex();
		Double jobID = updateBillingRecordDef.getJobID();
		Integer overtimeByWorkingState = updateBillingRecordDef.getOvertimeByWorkingState();
		Boolean overtimeExempt = updateBillingRecordDef.getOvertimeExempt();
		Double overtimeRate = updateBillingRecordDef.getOvertimeRate();
		String overtimeRatePer = updateBillingRecordDef.getOvertimeRatePer();
		String paymentTerms = updateBillingRecordDef.getPaymentTerms();
		Long primaryRecruiterID = updateBillingRecordDef.getPrimaryRecruiterID();
		Double primaryRecruiterPercentage = updateBillingRecordDef.getPrimaryRecruiterPercentage();
		Double primarySalesPercentage = updateBillingRecordDef.getPrimarySalesPercentage();
		Long primarySalesPersonID = updateBillingRecordDef.getPrimarySalesPersonID();
		Integer recordID = updateBillingRecordDef.getRecordID();
		Long secondaryRecruiterID = updateBillingRecordDef.getSecondaryRecruiterID();
		Double secondaryRecruiterPercentage = updateBillingRecordDef.getSecondaryRecruiterPercentage();
		Double secondarySalesPercentage = updateBillingRecordDef.getSecondarySalesPercentage();
		Long secondarySalesPersonID = updateBillingRecordDef.getSecondarySalesPersonID();
		Date startDate = updateBillingRecordDef.getStartDate();
		Long tertiaryRecruiterID = updateBillingRecordDef.getTertiaryRecruiterID();
		Double tertiaryRecruiterPercentage = updateBillingRecordDef.getTertiaryRecruiterPercentage();
		Double tertiarySalesPercentage = updateBillingRecordDef.getTertiarySalesPercentage();
		Long tertiarySalesPersonID = updateBillingRecordDef.getTertiarySalesPersonID();
		Long timesheetEntryFormat = updateBillingRecordDef.getTimesheetEntryFormat();
		String timesheetInstruction = updateBillingRecordDef.getTimesheetInstruction();
		String vMSEmployeeName = updateBillingRecordDef.getvMSEmployeeName();
		String vMSWebsite = updateBillingRecordDef.getvMSWebsite();
		Integer weekEnding = updateBillingRecordDef.getWeekEnding();
		String workAddress1 = updateBillingRecordDef.getWorkAddress1();
		String workAddress2 = updateBillingRecordDef.getWorkAddress2();
		String workCity = updateBillingRecordDef.getWorkCity();
		String workCountry = updateBillingRecordDef.getWorkCountry();
		String workState = updateBillingRecordDef.getWorkState();
		String workZipcode = updateBillingRecordDef.getWorkZipcode();
		//
		//
		//
		return invoiceService.updateBillingRecord(jobDivaSession, allowEnterTimeOnPortal, approved, assignmentID, billingContactID, billingUnit, billRate, billRatePer, candidateID, customerRefNo, division, doubletimePer, doubletimeRate,
				doubletimeRatePer, enableTimesheet, endDate, expenseEnabled, expenseInvoices, frequency, hiringManagerID, hoursPerDay, hoursPerHalfDay, invoiceContent, invoiceGroup, invoiceGroupIndex, jobID, overtimeByWorkingState, overtimeExempt,
				overtimeRate, overtimeRatePer, paymentTerms, primaryRecruiterID, primaryRecruiterPercentage, primarySalesPercentage, primarySalesPersonID, recordID, secondaryRecruiterID, secondaryRecruiterPercentage, secondarySalesPercentage,
				secondarySalesPersonID, startDate, tertiaryRecruiterID, tertiaryRecruiterPercentage, tertiarySalesPercentage, tertiarySalesPersonID, timesheetEntryFormat, timesheetInstruction, vMSEmployeeName, vMSWebsite, weekEnding, workAddress1,
				workAddress2, workCity, workCountry, workState, workZipcode);
		//
	}
	
	@RequestMapping(value = "/UpdatePayRecord", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Pay Record")
	public Boolean UpdatePayRecord( //
			//
			@RequestBody UpdatePayRecordDef updatePayRecordDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("UpdatePayRecord");
		//
		//
		String aDPCOCODE = updatePayRecordDef.getaDPCOCODE();
		String aDPPAYFREQUENCY = updatePayRecordDef.getaDPPAYFREQUENCY();
		Boolean approved = updatePayRecordDef.getApproved();
		Double assignmentID = updatePayRecordDef.getAssignmentID();
		Long candidateID = updatePayRecordDef.getCandidateID();
		Double doubletimeRate = updatePayRecordDef.getDoubletimeRate();
		String doubletimeRatePer = updatePayRecordDef.getDoubletimeRatePer();
		Date effectiveDate = updatePayRecordDef.getEffectiveDate();
		Date endDate = updatePayRecordDef.getEndDate();
		String fileNo = updatePayRecordDef.getFileNo();
		Double otherExpenses = updatePayRecordDef.getOtherExpenses();
		String otherExpensesPer = updatePayRecordDef.getOtherExpensesPer();
		Double outsideCommission = updatePayRecordDef.getOutsideCommission();
		String outsideCommissionPer = updatePayRecordDef.getOutsideCommissionPer();
		Boolean overtimeExempt = updatePayRecordDef.getOvertimeExempt();
		Double overtimeRate = updatePayRecordDef.getOvertimeRate();
		String overtimeRatePer = updatePayRecordDef.getOvertimeRatePer();
		String paymentTerms = updatePayRecordDef.getPaymentTerms();
		Boolean payOnRemittance = updatePayRecordDef.getPayOnRemittance();
		Double perDiem = updatePayRecordDef.getPerDiem();
		String perDiemPer = updatePayRecordDef.getPerDiemPer();
		Integer recordID = updatePayRecordDef.getRecordID();
		Double salary = updatePayRecordDef.getSalary();
		String salaryPer = updatePayRecordDef.getSalaryPer();
		Integer status = updatePayRecordDef.getStatus();
		Long subcontractCompanyID = updatePayRecordDef.getSubcontractCompanyID();
		String taxID = updatePayRecordDef.getTaxID();
		//
		//
		//
		return invoiceService.UpdatePayRecord(jobDivaSession, aDPCOCODE, aDPPAYFREQUENCY, approved, assignmentID, candidateID, doubletimeRate, doubletimeRatePer, effectiveDate, endDate, fileNo, otherExpenses, otherExpensesPer, outsideCommission,
				outsideCommissionPer, overtimeExempt, overtimeRate, overtimeRatePer, paymentTerms, payOnRemittance, perDiem, perDiemPer, recordID, salary, salaryPer, status, subcontractCompanyID, taxID);
		//
	}
	
	@RequestMapping(value = "/createPayRecord", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Pay Record")
	public Integer createPayRecord( //
			//
			@RequestBody CreatePayRecordDef createPayRecordDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		jobDivaSession.checkForAPIPermission("createPayRecord");
		//
		Long candidateID = createPayRecordDef.getCandidateID();
		Double assignmentID = createPayRecordDef.getAssignmentID();
		Long jobID = createPayRecordDef.getJobID();
		Boolean approved = createPayRecordDef.getApproved();
		Long createdByID = createPayRecordDef.getCreatedByID();
		Date effectiveDate = createPayRecordDef.getEffectiveDate();
		Date endDate = createPayRecordDef.getEndDate();
		Integer status = createPayRecordDef.getStatus();
		String taxID = createPayRecordDef.getTaxID();
		String paymentTerms = createPayRecordDef.getPaymentTerms();
		Long subcontractCompanyID = createPayRecordDef.getSubcontractCompanyID();
		Boolean payOnRemittance = createPayRecordDef.getPayOnRemittance();
		Double salary = createPayRecordDef.getSalary();
		String salaryPer = createPayRecordDef.getSalaryPer();
		Double perDiem = createPayRecordDef.getPerDiem();
		String perDiemPer = createPayRecordDef.getPerDiemPer();
		Double otherExpenses = createPayRecordDef.getOtherExpenses();
		String otherExpensesPer = createPayRecordDef.getOtherExpensesPer();
		Double outsideCommission = createPayRecordDef.getOutsideCommission();
		String outsideCommissionPer = createPayRecordDef.getOutsideCommissionPer();
		Double overtimeRate = createPayRecordDef.getOvertimeRate();
		String overtimeRatePer = createPayRecordDef.getOvertimeRatePer();
		Double doubletimeRate = createPayRecordDef.getDoubletimeRate();
		String doubletimeRatePer = createPayRecordDef.getDoubletimeRatePer();
		Boolean overtimeExempt = createPayRecordDef.getOvertimeExempt();
		String fileNo = createPayRecordDef.getFileNo();
		String aDPCOCODE = createPayRecordDef.getaDPCOCODE();
		String aDPPAYFREQUENCY = createPayRecordDef.getaDPPAYFREQUENCY();
		//
		//
		return invoiceService.createPayRecord(jobDivaSession, candidateID, assignmentID, jobID,
				/* recordID, */ approved, createdByID, effectiveDate, endDate, status, taxID, paymentTerms, subcontractCompanyID, payOnRemittance, salary, salaryPer, perDiem, perDiemPer, otherExpenses, otherExpensesPer, outsideCommission,
				outsideCommissionPer, overtimeRate, overtimeRatePer, doubletimeRate, doubletimeRatePer, overtimeExempt, fileNo, aDPCOCODE, aDPPAYFREQUENCY);
		//
	}
}

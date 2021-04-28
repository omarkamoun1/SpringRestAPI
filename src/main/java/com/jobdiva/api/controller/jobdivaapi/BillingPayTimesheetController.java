package com.jobdiva.api.controller.jobdivaapi;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.ExpenseEntry;
import com.jobdiva.api.model.Timesheet;
import com.jobdiva.api.model.TimesheetEntry;
import com.jobdiva.api.model.UploadTimesheetAssignment;
import com.jobdiva.api.model.WeekEndingRecord;
import com.jobdiva.api.model.authenticate.JobDivaSession;
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
@RequestMapping("/api/jobdiva/")
@Api(value = "Billing, Pay, Timesheet API", description = "REST API Used For Billing, Pay and Timesheet")
public class BillingPayTimesheetController extends AbstractJobDivaController {
	//
	
	@Autowired
	TimesheetService	timesheetService;
	//
	@Autowired
	InvoiceService		invoiceService;
	
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
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "timesheetEntry", required = true, allowMultiple = true, dataType = "TimesheetEntry"), //
			@ApiImplicitParam(name = "expenses", required = false, allowMultiple = true, dataType = "ExpenseEntry") })
	@RequestMapping(value = "/uploadTimesheetAssignment", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "upload Timesheet Assignment")
	public UploadTimesheetAssignment uploadTimesheetAssignment( //
			//
			@ApiParam(value = "The internal JobDiva ID of the employee", required = true) //
			@RequestParam(required = true) Long employeeid, //
			//
			@ApiParam(value = "The internal JobDiva ID of the job", required = false) //
			@RequestParam(required = false) Long jobid, //
			//
			@ApiParam(value = "The weekending date of the timesheet Format [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date weekendingdate, //
			//
			@ApiParam(value = "The timesheet pay rate", required = true) //
			@RequestParam(required = true) Double payrate, //
			//
			@ApiParam(value = "Overtime pay rate", required = false) //
			@RequestParam(required = false) Double overtimepayrate, //
			//
			@ApiParam(value = "Double time pay rate", required = false) //
			@RequestParam(required = false) Double doubletimepayrate, //
			//
			@ApiParam(value = "Bill rate", required = true) //
			@RequestParam(required = true) Double billrate, //
			//
			@ApiParam(value = "Overtime bill rate", required = false) //
			@RequestParam(required = false) Double overtimebillrate, //
			//
			@ApiParam(value = "Double time bill rate", required = false) //
			@RequestParam(required = false) Double doubletimebillrate, //
			//
			@ApiParam(value = "work location", required = false) //
			@RequestParam(required = false) String location, //
			//
			@ApiParam(value = "Job title", required = true) //
			@RequestParam(required = true) String title, //
			//
			@ApiParam(value = "Role numbe", required = false) //
			@RequestParam(required = false) String rolenumber, //
			//
			@ApiParam(value = "If specified, will update the timesheet with its ID", required = false) //
			@RequestParam(required = false) Long timesheetid, //
			//
			@ApiParam(value = "Timesheet’s external ID", required = false) //
			@RequestParam(required = false) String externalid, //
			//
			@ApiParam(value = "Comp Code or Insurance Code fir this assignment", required = false) //
			@RequestParam(required = false) String compcode, //
			//
			@ApiParam(value = "Can specify any number of days within the week, but at least one valid entry must be provided.", required = true, type = "TimesheetEntry", allowMultiple = true) //
			@RequestParam(required = true) TimesheetEntry[] timesheetEntry, //
			//
			@ApiParam(value = "Entries of expenses that are associated with this timesheet record.", required = false, type = "ExpenseEntry", allowMultiple = true) //
			@RequestParam(required = false) ExpenseEntry[] expenses, //
			//
			@ApiParam(value = "Email where API notification will be sent to", required = false) //
			@RequestParam(required = false) String[] emailrecipients //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("uploadTimesheetAssignment");
		//
		return timesheetService.uploadTimesheetAssignment(jobDivaSession, employeeid, jobid, weekendingdate, payrate, overtimepayrate, doubletimepayrate, billrate, overtimebillrate, doubletimebillrate, location, title, rolenumber, timesheetid,
				externalid, compcode, timesheetEntry, expenses, emailrecipients);
		//
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "expenses", required = true, allowMultiple = true, dataType = "ExpenseEntry") })
	@RequestMapping(value = "/addExpenseEntry", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Add Expense Entry")
	public Integer addExpenseEntry( //
			//
			@ApiParam(value = "The internal JobDiva ID of the invoice", required = true) //
			@RequestParam(required = true) Long employeeid, //
			//
			@ApiParam(value = "The weekending date of the expenses Format [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date weekendingdate, //
			//
			@ApiParam(value = "The invoice date of the expenses Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date invoicedate, //
			//
			@ApiParam(value = "Company’s feedback", required = false) //
			@RequestParam(required = false) String feedback, //
			//
			@ApiParam(value = "Expenses description", required = false) //
			@RequestParam(required = false) String description, //
			//
			@ApiParam(value = "Detailed information of each expense.\r\n" + //
					"Must contain at least one expense entry.", required = true, type = "ExpenseEntry", allowMultiple = true) //
			@RequestParam(required = true) ExpenseEntry[] expenses, //
			//
			@ApiParam(value = "Email where API notification will be sent to", required = false) //
			@RequestParam(required = false) String[] emailrecipients //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("addExpenseEntry");
		//
		return invoiceService.addExpenseEntry(jobDivaSession, employeeid, weekendingdate, invoicedate, feedback, description, expenses, emailrecipients);
		//
	}
	
	@RequestMapping(value = "/approveExpenseEntry", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Approve Expense Entry")
	public Boolean approveExpenseEntry( //
			//
			@ApiParam(value = "The internal JobDiva ID of the invoice", required = true) //
			@RequestParam(required = true) Integer invoiceid, //
			//
			@ApiParam(value = "Additional comments", required = false) //
			@RequestParam(required = false) String comments, //
			//
			@ApiParam(value = "Email where API notification will be sent to", required = false) //
			@RequestParam(required = false) String[] emailrecipients //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("approveExpenseEntry");
		//
		return invoiceService.approveExpenseEntry(jobDivaSession, invoiceid, comments, emailrecipients);
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "expenses", required = true, allowMultiple = true, dataType = "ExpenseEntry") })
	@RequestMapping(value = "/addExpenseInvoice", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Add Expense Invoice")
	public Integer addExpenseInvoice( //
			//
			@ApiParam(value = "The internal JobDiva ID of the employee", required = true) //
			@RequestParam(required = true) Long employeeid, //
			//
			@ApiParam(value = "The weekending date of the expenses Format [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date weekendingdate, //
			//
			@ApiParam(value = "The invoice date of the expenses Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date invoicedate, //
			//
			@ApiParam(value = "Company’s feedback", required = false) //
			@RequestParam(required = false) String feedback, //
			//
			@ApiParam(value = "Expenses description", required = false) //
			@RequestParam(required = false) String description, //
			//
			@ApiParam(value = "Detailed information of each expense.\r\n" + //
					"Must contain at least one expense entry.", required = true, type = "ExpenseEntry", allowMultiple = true) //
			@RequestParam(required = true) ExpenseEntry[] expenses, //
			//
			@ApiParam(value = "Email where API notification will be sent to", required = false) //
			@RequestParam(required = false) String[] emailrecipients //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("addExpenseInvoice");
		//
		return invoiceService.addExpenseInvoice(jobDivaSession, employeeid, weekendingdate, invoicedate, feedback, description, expenses, emailrecipients);
		//
	}
	
	@RequestMapping(value = "/markTimesheetPaid", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Mark Timesheet Paid")
	public Boolean markTimesheetPaid( //
			//
			@ApiParam(value = "JobDiva internal ID of the employee", required = true) //
			@RequestParam(required = true) Long employeeid, //
			//
			@ApiParam(value = "Salary Record ID", required = true) //
			@RequestParam(required = true) Integer salaryrecordid, //
			//
			@ApiParam(value = "Date timesheet(s) are paid Format [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date datepaid, //
			//
			@ApiParam(value = "Target timesheet(s) to be marked as paid", required = true) //
			@RequestParam(required = true) Date[] timesheetdate //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("markTimesheetPaid");
		//
		return timesheetService.markTimesheetPaid(jobDivaSession, employeeid, salaryrecordid, datepaid, timesheetdate);
		//
	}
	
	@RequestMapping(value = "/updatePayrollProfile", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Payroll Profile")
	public Boolean updatePayrollProfile( //
			//
			@ApiParam(value = "JobDiva internal ID of the employee", required = true) //
			@RequestParam(required = true) Long employeeid, //
			//
			@ApiParam(value = "Salary Record ID", required = false) //
			@RequestParam(required = false) Long salaryrecid, //
			//
			@ApiParam(value = "Generation suffix of the employee", required = false) //
			@RequestParam(required = false) String generationsuffix, //
			//
			@ApiParam(value = "Reason the employee is hired", required = false) //
			@RequestParam(required = false) String reasonforhire, //
			//
			@ApiParam(value = "Employee’s gender. Valid options are “M”, “F”", required = false) //
			@RequestParam(required = false) String gender, //
			//
			@ApiParam(value = "Company’s code", required = false) //
			@RequestParam(required = false) String companycode, //
			//
			@ApiParam(value = "country the employee works at", required = false) //
			@RequestParam(required = false) String country, //
			//
			@ApiParam(value = "Employee’s address 1, the main address", required = false) //
			@RequestParam(required = false) String address1, //
			//
			@ApiParam(value = "Employee’s address 2, such as Apt, Suite", required = false) //
			@RequestParam(required = false) String address2, //
			//
			@ApiParam(value = "Employee’s address 3, additional address information", required = false) //
			@RequestParam(required = false) String address3, //
			//
			@ApiParam(value = "Employee’s city", required = false) //
			@RequestParam(required = false) String city, //
			//
			@ApiParam(value = "Employee’s state", required = false) //
			@RequestParam(required = false) String state, //
			//
			@ApiParam(value = "the county of the address", required = false) //
			@RequestParam(required = false) String county, //
			//
			@ApiParam(value = "the zipcode of the address", required = false) //
			@RequestParam(required = false) String zipcode, //
			//
			@ApiParam(value = "employee’s phone number", required = false) //
			@RequestParam(required = false) Long telephone, //
			//
			@ApiParam(value = "Employee’s job title", required = false) //
			@RequestParam(required = false) String jobtitle, //
			//
			@ApiParam(value = "Employee’s work category", required = false) //
			@RequestParam(required = false) String workercategory, //
			//
			@ApiParam(value = "If the position is a managing position", required = false) //
			@RequestParam(required = false) Boolean manageposition, //
			//
			@ApiParam(value = "Business Unit", required = false) //
			@RequestParam(required = false) String businessunit, //
			//
			@ApiParam(value = "employee’s home department", required = false) //
			@RequestParam(required = false) String homedepartment, //
			//
			@ApiParam(value = "employee’s coded home department", required = false) //
			@RequestParam(required = false) String codedhomedepartment, //
			//
			@ApiParam(value = "employee’s benefits eligibility class", required = false) //
			@RequestParam(required = false) String benefitseligibilityclass, //
			//
			@ApiParam(value = "NAICS Workers Compensation", required = false) //
			@RequestParam(required = false) String naicsworkerscomp, //
			//
			@ApiParam(value = "employee’s standard payroll hours", required = false) //
			@RequestParam(required = false) Long standardhours, //
			//
			@ApiParam(value = "Employee’s federal marital status", required = false) //
			@RequestParam(required = false) String federalmaritalstatus, //
			//
			@ApiParam(value = "Employee’s federal exemptions. Mapping can be provided.", required = false) //
			@RequestParam(required = false) Integer federalexemptions, //
			//
			@ApiParam(value = "the state tax code of the state the employee works", required = false) //
			@RequestParam(required = false) String workedstatetaxcode, //
			//
			@ApiParam(value = "employee’s in-state marital status", required = false) //
			@RequestParam(required = false) String statemaritalstatus, //
			//
			@ApiParam(value = "employee’s state exemptions. Mapping can be provided.", required = false) //
			@RequestParam(required = false) Integer stateexemptions, //
			//
			@ApiParam(value = "The state tax code of the state the employee lives in", required = false) //
			@RequestParam(required = false) String livedstatetaxcode, //
			//
			@ApiParam(value = "SUISDI tax code", required = false) //
			@RequestParam(required = false) String suisditaxcode, //
			//
			@ApiParam(value = "Bank deposit deduction code", required = false) //
			@RequestParam(required = false) String bankdepositdeductioncode, //
			//
			@ApiParam(value = "If full deposit goes into the bank", required = false) //
			@RequestParam(required = false) Boolean bankfulldepositflag, //
			//
			@ApiParam(value = "Bank Deposit Deduction Amount", required = false) //
			@RequestParam(required = false) Double bankdepositdeductionamount, //
			//
			@ApiParam(value = "Bank deposit transit or ABA", required = false) //
			@RequestParam(required = false) String bankdeposittransitoraba, //
			//
			@ApiParam(value = "Bank deposit account number", required = false) //
			@RequestParam(required = false) String bankdepositaccountnumber, //
			//
			@ApiParam(value = "Prenotification method", required = false) //
			@RequestParam(required = false) String prenotificationmethod, //
			//
			@ApiParam(value = "bank deposit prenote date format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date bankdepositprenotedate, //
			//
			@ApiParam(value = "Allowed taken code", required = false) //
			@RequestParam(required = false) String allowedtakencode, //
			//
			@ApiParam(value = "Email of the employee", required = false) //
			@RequestParam(required = false) String email, //
			//
			@ApiParam(value = "the reset year", required = false) //
			@RequestParam(required = false) Integer resetyear, //
			//
			@ApiParam(value = "If do not calculate federal income tax", required = false) //
			@RequestParam(required = false) Boolean donotcaltaxfederalincome, //
			//
			@ApiParam(value = "If do not calculate medicare tax", required = false) //
			@RequestParam(required = false) Boolean donotcaltaxmedicare, //
			//
			@ApiParam(value = "If do not calculate social security tax", required = false) //
			@RequestParam(required = false) Boolean donotcaltaxsocialsecurity, //
			//
			@ApiParam(value = "If do not calculate state tax", required = false) //
			@RequestParam(required = false) Boolean donotcaltaxstate, //
			//
			@ApiParam(value = "Retirement Plan of the employee", required = false) //
			@RequestParam(required = false) String retirementplan, //
			//
			@ApiParam(value = "Federal extra tax type", required = false) //
			@RequestParam(required = false) String federalextrataxtype, //
			//
			@ApiParam(value = "federal extra tax", required = false) //
			@RequestParam(required = false) Double federalextratax, //
			//
			@ApiParam(value = "local tax code", required = false) //
			@RequestParam(required = false) String localtaxcode, //
			//
			@ApiParam(value = "the local tax code of the work place", required = false) //
			@RequestParam(required = false) String workedlocaltaxcode, //
			//
			@ApiParam(value = "the local tax code of the employee’s living address", required = false) //
			@RequestParam(required = false) String livedlocaltaxcode, //
			//
			@ApiParam(value = "the local school district tax code", required = false) //
			@RequestParam(required = false) String localschooldistricttaxcode, //
			//
			@ApiParam(value = "the local tax code 4 (additional tax code)", required = false) //
			@RequestParam(required = false) String localtaxcode4, //
			//
			@ApiParam(value = "the local tax code 5 (additional tax code)", required = false) //
			@RequestParam(required = false) String localtaxcode5, //
			//
			@ApiParam(value = "health coverage code", required = false) //
			@RequestParam(required = false) String healthcoveragecode, //
			//
			@ApiParam(value = "pay frequency", required = false) //
			@RequestParam(required = false) String payfrequency, //
			//
			@ApiParam(value = "pay group", required = false) //
			@RequestParam(required = false) String paygroup, //
			//
			@ApiParam(value = "Number of W-4 Deductions", required = false) //
			@RequestParam(required = false) Double w4deductions, //
			//
			@ApiParam(value = "Number of W-4 Dependents", required = false) //
			@RequestParam(required = false) Double w4dependents, //
			//
			@ApiParam(value = "If apply to W-4 multiple jobs", required = false) //
			@RequestParam(required = false) Boolean w4multiplejobs, //
			//
			@ApiParam(value = "Number of W-4 other income", required = false) //
			@RequestParam(required = false) Double w4otherincome //
	//
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updatePayrollProfile");
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
			@ApiParam(required = true) //
			@RequestParam(required = true) Long candidateID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long assignmentID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long jobID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer recordID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long createdByID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean approved, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Date startDate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Date endDate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String customerRefNo, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long hiringManagerID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long billingContactID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long division, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer invoiceGroupIndex, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String invoiceGroup, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String vMSWebsite, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String vMSEmployeeName, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer invoiceContent, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer expenseInvoices, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean enableTimesheet, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean allowEnterTimeOnPortal, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String timesheetInstruction, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean expenseEnabled, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double billRate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String billRatePer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean overtimeExempt, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long timesheetEntryFormat, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer frequency, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer overtimeByWorkingState, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double overtimeRate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String overtimeRatePer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double doubletimeRate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String doubletimePer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer billingUnit, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer weekEnding, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double hoursPerDay, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double hoursPerHalfDay, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workAddress1, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workAddress2, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workCity, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workState, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workZipcode, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workCountry, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer paymentTerms, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long primarySalesPersonID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double primarySalesPercentage, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long secondarySalesPersonID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double secondarySalesPercentage, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long tertiarySalesPersonID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double tertiarySalesPercentage, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long primaryRecruiterID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double primaryRecruiterPercentage, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long secondaryRecruiterID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double secondaryRecruiterPercentage, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long tertiaryRecruiterID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double tertiaryRecruiterPercentage //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createBillingRecord");
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
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean allowEnterTimeOnPortal, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer approved, //
			//
			@ApiParam(required = true) //
			@RequestParam(required = true) Double assignmentID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long billingContactID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double Long, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer billingUnit, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double billRate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String billRatePer, //
			//
			@ApiParam(required = true) //
			@RequestParam(required = true) Long candidateID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String customerRefNo, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long division, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double doubletimePer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double doubletimeRate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String doubletimeRatePer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean enableTimesheet, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Date endDate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean expenseEnabled, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer expenseInvoices, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer frequency, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long hiringManagerID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double hoursPerDay, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double hoursPerHalfDay, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer invoiceContent, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String invoiceGroup, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer invoiceGroupIndex, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double jobID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer overtimeByWorkingState, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean overtimeExempt, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double overtimeRate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String overtimeRatePer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String paymentTerms, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long primaryRecruiterID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double primaryRecruiterPercentage, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double primarySalesPercentage, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long primarySalesPersonID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer recordID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long secondaryRecruiterID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double secondaryRecruiterPercentage, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double secondarySalesPercentage, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long secondarySalesPersonID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Date startDate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long tertiaryRecruiterID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double tertiaryRecruiterPercentage, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double tertiarySalesPercentage, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long tertiarySalesPersonID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long timesheetEntryFormat, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String timesheetInstruction, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String vMSEmployeeName, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String vMSWebsite, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer weekEnding, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workAddress1, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workAddress2, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workCity, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workCountry, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workState, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String workZipcode //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateBillingRecord");
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
			@ApiParam(required = false) //
			@RequestParam(required = false) String aDPCOCODE, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String aDPPAYFREQUENCY, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean approved, //
			//
			//
			@ApiParam(required = true) //
			@RequestParam(required = true) Double assignmentID, //
			//
			//
			@ApiParam(required = true) //
			@RequestParam(required = true) Long candidateID, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double doubletimeRate, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String doubletimeRatePer, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Date effectiveDate, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Date endDate, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String fileNo, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double otherExpenses, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String otherExpensesPer, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double outsideCommission, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String outsideCommissionPer, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean overtimeExempt, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double overtimeRate, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String overtimeRatePer, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String paymentTerms, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean payOnRemittance, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double perDiem, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String perDiemPer, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer recordID, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double salary, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String salaryPer, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer status, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long subcontractCompanyID, //
			//
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String taxID
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("UpdatePayRecord");
		//
		return invoiceService.UpdatePayRecord(jobDivaSession, aDPCOCODE, aDPPAYFREQUENCY, approved, assignmentID, candidateID, doubletimeRate, doubletimeRatePer, effectiveDate, endDate, fileNo, otherExpenses, otherExpensesPer, outsideCommission,
				outsideCommissionPer, overtimeExempt, overtimeRate, overtimeRatePer, paymentTerms, payOnRemittance, perDiem, perDiemPer, recordID, salary, salaryPer, status, subcontractCompanyID, taxID);
		//
	}
	
	@RequestMapping(value = "/createPayRecord", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Pay Record")
	public Integer createPayRecord( //
			//
			@ApiParam(required = true) //
			@RequestParam(required = true) Long candidateID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double assignmentID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long jobID, //
			//
			// @ApiParam(required = false) //
			// @RequestParam(required = false) Integer recordID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean approved, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long createdByID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Date effectiveDate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Date endDate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Integer status, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String taxID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String paymentTerms, //
			@ApiParam(required = false) //
			@RequestParam(required = false) Long subcontractCompanyID, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean payOnRemittance, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double salary, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String salaryPer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double perDiem, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String perDiemPer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double otherExpenses, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String otherExpensesPer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double outsideCommission, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String outsideCommissionPer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double overtimeRate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String overtimeRatePer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Double doubletimeRate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String doubletimeRatePer, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean overtimeExempt, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String fileNo, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String aDPCOCODE, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String aDPPAYFREQUENCY //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createPayRecord");
		//
		return invoiceService.createPayRecord(jobDivaSession, candidateID, assignmentID, jobID,
				/* recordID, */ approved, createdByID, effectiveDate, endDate, status, taxID, paymentTerms, subcontractCompanyID, payOnRemittance, salary, salaryPer, perDiem, perDiemPer, otherExpenses, otherExpensesPer, outsideCommission,
				outsideCommissionPer, overtimeRate, overtimeRatePer, doubletimeRate, doubletimeRatePer, overtimeExempt, fileNo, aDPCOCODE, aDPPAYFREQUENCY);
		//
	}

	@RequestMapping(value = "/searchTimesheets", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Timesheets")
	public List<WeekEndingRecord>   searchTimesheets( //
			//
			@ApiParam(required = true) //
			@RequestParam(required = true) Long userId, //
			//
			@ApiParam(required = true) //
			@RequestParam(required = true) Integer approvedStatus, //
			//
			@ApiParam(required = true) //
			@RequestParam(required = true) Calendar startDate, //
			//
			@ApiParam(required = true) //
			@RequestParam(required = true) Calendar endDate, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String firstname, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) String lastname, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long companyId, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Long managerId //
			//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return timesheetService.searchTimesheet(jobDivaSession, userId, approvedStatus, startDate, endDate, firstname, lastname, companyId, managerId);
		//
	}
	
}

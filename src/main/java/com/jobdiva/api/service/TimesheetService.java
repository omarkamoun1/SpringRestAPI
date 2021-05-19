package com.jobdiva.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.jobdiva.api.dao.timesheet.TimesheetDao;
import com.jobdiva.api.model.ExpenseCategory;
import com.jobdiva.api.model.ExpenseEntry;
import com.jobdiva.api.model.Timesheet;
import com.jobdiva.api.model.TimesheetEntry;
import com.jobdiva.api.model.UploadTimesheetAssignment;
import com.jobdiva.api.model.WeekEndingRecord;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class TimesheetService extends AbstractService {
	
	@Autowired
	TimesheetDao timesheetDao;
	
	public Long uploadTimesheet(JobDivaSession jobDivaSession, Long employeeid, Long jobid, Date weekendingdate, Boolean approved, String externalId, Timesheet[] timesheetEntry, String vmsemployeeid, Long activityid, Long approverid)
			throws Exception {
		//
		try {
			//
			Long result = timesheetDao.uploadTimesheet(jobDivaSession, employeeid, jobid, weekendingdate, approved, externalId, timesheetEntry, vmsemployeeid, activityid, approverid);
			//
			timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheet", "upload Successful");
			//
			return result;
			//
		} catch (Exception e) {
			//
			timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheet", "upload Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean markTimesheetPaid(JobDivaSession jobDivaSession, Long employeeid, Integer salaryrecordid, Date datepaid, Date[] timesheetdate) throws Exception {
		//
		try {
			//
			Boolean success = timesheetDao.markTimesheetPaid(jobDivaSession, employeeid, salaryrecordid, datepaid, timesheetdate);
			//
			timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "markTimesheetPaid", "Mark Timesheet Paid Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "markTimesheetPaid", "Mark Timesheet Paid Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public UploadTimesheetAssignment uploadTimesheetAssignment(JobDivaSession jobDivaSession, Long employeeid, Long jobid, Date weekendingdate, Double payrate, Double overtimepayrate, Double doubletimepayrate, //
			Double billrate, Double overtimebillrate, Double doubletimebillrate, String location, String title, String rolenumber, Long timesheetid, String externalid, String compcode, //
			TimesheetEntry[] timesheetEntry, ExpenseEntry[] expenses, String[] emailrecipients) throws Exception {
		//
		try {
			//
			UploadTimesheetAssignment uploadTimesheetAssignment = timesheetDao.uploadTimesheetAssignment(jobDivaSession, employeeid, jobid, weekendingdate, payrate, overtimepayrate, doubletimepayrate, billrate, overtimebillrate, doubletimebillrate,
					location, title, rolenumber, timesheetid, externalid, compcode, timesheetEntry, expenses, emailrecipients);
			//
			timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheetAssignment", "Upload Timesheet Assignment Successful");
			//
			return uploadTimesheetAssignment;
			//
		} catch (Exception e) {
			//
			timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadTimesheetAssignment", "Upload Timesheet Assignment Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public List<WeekEndingRecord> searchTimesheet(JobDivaSession jobDivaSession, Long userid, Integer approvedStatus, Date startDate, Date endDate, String firstname, String lastname, Long companyid, Long managerid) throws Exception {
		//
		try {
			//
			List<WeekEndingRecord> records = timesheetDao.searchTimesheet(jobDivaSession, userid, approvedStatus, startDate, endDate, firstname, lastname, companyid, managerid);
			//
			timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchTimesheet", "search Successful");
			//
			return records;
			//
		} catch (Exception e) {
			//
			timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchTimesheet", "search Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean deleteTimesheet(JobDivaSession jobDivaSession, Long timesheetId, String externalId) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						Boolean value = timesheetDao.deleteTimesheet(jobDivaSession, timesheetId, externalId);
						//
						timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "deleteTimesheet", "Delete Successful");
						//
						return value;
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "deleteTimesheet", "Delete Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean deleteExpense(JobDivaSession jobDivaSession, Long expenseId, String externalId) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						Boolean value = timesheetDao.deleteExpense(jobDivaSession, expenseId, externalId);
						//
						timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "deleteExpense", "Delete Successful");
						//
						return value;
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			timesheetDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "deleteExpense", "Delete Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public List<ExpenseCategory> getExpenseCategories(JobDivaSession jobDivaSession) throws Exception {
		//
		return timesheetDao.getExpenseCategories(jobDivaSession);
	}
}

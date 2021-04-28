package com.jobdiva.api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.timesheet.TimesheetDao;
import com.jobdiva.api.model.ExpenseEntry;
import com.jobdiva.api.model.Timesheet;
import com.jobdiva.api.model.TimesheetEntry;
import com.jobdiva.api.model.UploadTimesheetAssignment;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class TimesheetService {
	
	@Autowired
	TimesheetDao timesheetDao;
	
	public Long uploadTimesheet(JobDivaSession jobDivaSession, Long employeeid, Long jobid, Date weekendingdate, Boolean approved, Long timesheetId, String externalId, Timesheet[] timesheetEntry) throws Exception {
		//
		try {
			//
			Long result = timesheetDao.uploadTimesheet(jobDivaSession, employeeid, jobid, weekendingdate, approved, timesheetId, externalId, timesheetEntry);
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
}

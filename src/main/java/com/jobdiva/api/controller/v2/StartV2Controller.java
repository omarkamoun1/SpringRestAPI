package com.jobdiva.api.controller.v2;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.Activity;
import com.jobdiva.api.model.Timezone;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.v2.start.SearchStartDef;
import com.jobdiva.api.model.v2.start.SetStartDef;
import com.jobdiva.api.model.v2.start.TerminateAssignmentDef;
import com.jobdiva.api.model.v2.start.TerminateStartDef;
import com.jobdiva.api.model.v2.start.UpdateStartDef;
import com.jobdiva.api.service.ActivityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@RequestMapping("/apiv2/jobdiva/")
@Api(value = "Start API", description = "REST API Used For Start")
@ApiIgnore
public class StartV2Controller extends AbstractJobDivaController {
	//
	
	@Autowired
	ActivityService startService;
	
	@RequestMapping(value = "/searchStart", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Start", notes = "Search results.\r\n"
			+ "Returned fields: “id”, “job id”, “candidate id”, “candidate name”, “candidate email”, “candidate address”, “candidate phones”, “submittal date”, “interview date”, “start date”, “end date”, “position type”, “recruited by”, “submitted to”, “internal notes”, “final bill rate”, “bill rate unit”, “agreed pay”, “rate”, “pay rate unit”.")
	public List<Activity> searchStart( //
			//
			@ApiParam(value = "jobId : JobDiva internal ID \r\n"//
					+ "optionalref : Client reference number, be ignored if “jobid” is set. \r\n"//
					+ "jobdivaref : JobDiva reference number \r\n"//
					+ "candidateid : Candidate ID \r\n"//
					+ "candidatefirstname : Candidate first name, be ignored if “candidateid” is set. \r\n"//
					+ "candidatelastname : Candidate last name, be ignored if “candidateid” is set. \r\n" //
					+ "candidateemail : Candidate email, be ignored if “candidateid” is set.")
			//
			@RequestBody SearchStartDef searchStartDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("searchStart");
		//
		Long jobId = searchStartDef.getJobId();
		String optionalref = searchStartDef.getOptionalref();
		String jobdivaref = searchStartDef.getJobdivaref();
		Long candidateid = searchStartDef.getCandidateid();
		String candidatefirstname = searchStartDef.getCandidatefirstname();
		String candidatelastname = searchStartDef.getCandidatelastname();
		String candidateemail = searchStartDef.getCandidateemail();
		//
		return startService.searchStart(jobDivaSession, jobId, optionalref, jobdivaref, candidateid, candidatefirstname, candidatelastname, candidateemail);
		//
	}
	
	@RequestMapping(value = "/updateStart", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Start")
	public Boolean updateStart( //
			//
			@ApiParam(value = "startid : Start record ID \r\n" //
					+ "overwrite : Set true if intend to overwrite all information in the record. \r\n" //
					+ "startDate : Start date \r\n" //
					+ "endDate : End date \r\n" //
					+ "positiontype :  Position type, valid values(case insensitive):\r\n" + // //
					"• Direct Placement\r\n" + // //
					"• Contract\r\n" + // //
					"• Right to Hire\r\n" + // //
					"• Full Time/Contract \r\n \r\n" //
					+ "billrate : Bill rate \r\n" //
					+ "billratecurrency : Bill rate currency(case sensitive) , valid values can be found through “Leader Tools” → “My Team” → “Manage Rate Units” → “Currency”. \r\n" //
					+ "billrateunit : Bill rate unit(case sensitive), valid values can be found through “Leader Tools” → “My Team” → “Manage Rate Units” → “Bill Rate Units”. \r\n " //
					+ "payrate : Pay rate \r\n" //
					+ "payratecurrency : Pay rate currency(case sensitive), valid values can be found through “Leader Tools” → “My Team” → “Manage Rate Units” → “Currency”. \r\n" //
					+ "payrateunit : Pay rate unit(case sensitive), valid values can be found through “Leader Tools” → “My Team” → “Manage Rate Units” → “Pay Rate Units”. \r\n" //
					+ "Userfields : Userfields \r\n" //
			) //
			@RequestBody UpdateStartDef updateStartDef //
	// //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateStart");
		//
		Long startid = updateStartDef.getStartid();
		Boolean overwrite = updateStartDef.getOverwrite();
		Date startDate = updateStartDef.getStartDate();
		Date endDate = updateStartDef.getEndDate();
		String positiontype = updateStartDef.getPositiontype();
		Double billrate = updateStartDef.getBillrate();
		String billratecurrency = updateStartDef.getBillratecurrency();
		String billrateunit = updateStartDef.getBillrateunit();
		Double payrate = updateStartDef.getPayrate();
		String payratecurrency = updateStartDef.getPayratecurrency();
		String payrateunit = updateStartDef.getPayrateunit();
		Userfield[] Userfields = updateStartDef.getUserfields();
		//
		return startService.updateStart(jobDivaSession, startid, overwrite, startDate, endDate, positiontype, billrate, billratecurrency, billrateunit, payrate, payratecurrency, payrateunit, Userfields);
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "timezone", required = false, allowMultiple = false, dataType = "Timezone") })
	@RequestMapping(value = "/setStart", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Set Start")
	public Boolean setStart( //
			//
			@ApiParam(value = "submittalid : JobDiva internal submittal/start ID \r\n" //
					+ "recruiterid : JobDiva internal User ID. If empty, the API User ID is used \r\n" //
					+ "startDate : The date the employee will start working \r\n" //
					+ "endDate : Date the start will be terminated \r\n" //
					+ "timezone : the time zone of the start date and end date \r\n" //
					+ "internalnotes : Any internal notes and comments regarding the submittal/start." //
			//
			) @RequestBody SetStartDef setStartDef
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("setStart");
		//
		Long submittalid = setStartDef.getSubmittalid();
		Long recruiterid = setStartDef.getRecruiterid();
		Date startDate = setStartDef.getStartDate();
		Date endDate = setStartDef.getEndDate();
		Timezone timezone = setStartDef.getTimezone();
		String internalnotes = setStartDef.getInternalnotes();
		//
		return startService.setStart(jobDivaSession, submittalid, recruiterid, startDate, endDate, timezone, internalnotes);
		//
	}
	
	@RequestMapping(value = "/terminateStart", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Terminate Start")
	public Boolean terminateStart( //
			//
			@ApiParam(value = "startid : JobDiva internal start ID \r\n" //
					+ "candidateid : JobDiva internal candidate ID \r\n" //
					+ "jobId : JobDiva internal job ID \r\n" //
					+ "terminationdate : Date the start is terminated \r\n" //
					+ "reason : Reason for living. Available values provided by JobDiva \r\n" //
					+ "performancecode : Performance Code. Available values provided by JobDiva \r\n" //
					+ "notes : Notes about the termination. \r\n" //
					+ "markaspastemployee : If mark as past employee \r\n" //
					+ "markasavailable : If mark employee as available until termination date " //
			) //
			@RequestBody TerminateStartDef terminateStartDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("terminateStart");
		//
		Long startid = terminateStartDef.getStartid();
		Long candidateid = terminateStartDef.getCandidateid();
		Long jobId = terminateStartDef.getJobId();
		Date terminationdate = terminateStartDef.getTerminationdate();
		Integer reason = terminateStartDef.getReason();
		Integer performancecode = terminateStartDef.getPerformancecode();
		String notes = terminateStartDef.getNotes();
		Boolean markaspastemployee = terminateStartDef.getMarkaspastemployee();
		Boolean markasavailable = terminateStartDef.getMarkasavailable();
		//
		return startService.terminateStart(jobDivaSession, startid, candidateid, jobId, terminationdate, reason, performancecode, notes, markaspastemployee, markasavailable);
		//
	}
	
	@RequestMapping(value = "/terminateAssignment", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Terminate Assignment")
	public Boolean terminateAssignment( //
			//
			@ApiParam(value = "assignmentid : JobDiva internal assignment ID \r\n" //
					+ "candidateid : JobDiva internal candidate ID \r\n" //
					+ "jobId : JobDiva internal job ID \r\n" //
					+ "terminationdate : Date the start is terminated  \r\n" //
					+ "reason : Reason for living. Available values provided by JobDiva \r\n" //
					+ "performancecode : Performance Code. Available values provided by JobDiva \r\n" //
					+ "notes : Notes about the termination. \r\n" //
					+ "markaspastemployee : If mark as past employee \r\n" //
					+ "markasavailable : If mark employee as available until termination date" //
			) @RequestBody TerminateAssignmentDef terminateAssignmentDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("terminateAssignment");
		//
		Long assignmentid = terminateAssignmentDef.getAssignmentid();
		Long candidateid = terminateAssignmentDef.getCandidateid();
		Long jobId = terminateAssignmentDef.getJobId();
		Date terminationdate = terminateAssignmentDef.getTerminationdate();
		Integer reason = terminateAssignmentDef.getReason();
		Integer performancecode = terminateAssignmentDef.getPerformancecode();
		String notes = terminateAssignmentDef.getNotes();
		Boolean markaspastemployee = terminateAssignmentDef.getMarkaspastemployee();
		Boolean markasavailable = terminateAssignmentDef.getMarkasavailable();
		//
		return startService.terminateAssignment(jobDivaSession, assignmentid, candidateid, jobId, terminationdate, reason, performancecode, notes, markaspastemployee, markasavailable);
		//
	}
}

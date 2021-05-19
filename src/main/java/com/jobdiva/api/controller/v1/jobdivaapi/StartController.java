package com.jobdiva.api.controller.v1.jobdivaapi;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.Activity;
import com.jobdiva.api.model.Timezone;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.service.ActivityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "Start API", description = "REST API Used For Start")
public class StartController extends AbstractJobDivaController {
	//
	
	@Autowired
	ActivityService startService;
	
	@RequestMapping(value = "/searchStart", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Start", notes = "Search results.\r\n"
			+ "Returned fields: “id”, “job id”, “candidate id”, “candidate name”, “candidate email”, “candidate address”, “candidate phones”, “submittal date”, “interview date”, “start date”, “end date”, “position type”, “recruited by”, “submitted to”, “internal notes”, “final bill rate”, “bill rate unit”, “agreed pay”, “rate”, “pay rate unit”.")
	public List<Activity> searchStart( //
			//
			@ApiParam(value = "JobDiva internal ID", required = false) //
			@RequestParam(required = false) Long jobId, //
			//
			@ApiParam(value = "Client reference number, be ignored if “jobid” is set.", required = false) //
			@RequestParam(required = false) String optionalref, //
			//
			@ApiParam(value = "JobDiva reference number", required = false) //
			@RequestParam(required = false) String jobdivaref, //
			//
			@ApiParam(value = "Candidate ID", required = false) //
			@RequestParam(required = false) Long candidateid, //
			//
			@ApiParam(value = "Candidate first name, be ignored if “candidateid” is set.", required = false) //
			@RequestParam(required = false) String candidatefirstname, //
			//
			@ApiParam(value = "Candidate last name, be ignored if “candidateid” is set.", required = false) //
			@RequestParam(required = false) String candidatelastname, //
			//
			@ApiParam(value = "Candidate email, be ignored if “candidateid” is set.", required = false) //
			@RequestParam(required = false) String candidateemail //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("searchStart");
		//
		return startService.searchStart(jobDivaSession, jobId, optionalref, jobdivaref, candidateid, candidatefirstname, candidatelastname, candidateemail);
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "Userfields", required = false, allowMultiple = true, dataType = "Userfield") //
	})
	@RequestMapping(value = "/updateStart", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Start")
	public Boolean updateStart( //
			//
			@ApiParam(value = "Start record ID", required = false) //
			@RequestParam(required = false) Long startid, //
			//
			@ApiParam(value = "Set true if intend to overwrite all information in the record.", required = false) //
			@RequestParam(required = false) Boolean overwrite, //
			//
			@ApiParam(value = "Start date Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date startDate, //
			//
			//
			@ApiParam(value = "End date Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date endDate, //
			//
			@ApiParam(value = "Position type, valid values(case insensitive):\r\n" + //
					"• Direct Placement\r\n" + //
					"• Contract\r\n" + //
					"• Right to Hire\r\n" + //
					"• Full Time/Contract", required = false) //
			@RequestParam(required = false) String positiontype, //
			//
			@ApiParam(value = "Bill rate", required = false) //
			@RequestParam(required = false) Double billrate, //
			//
			@ApiParam(value = "Bill rate currency(case sensitive) , valid values can be found through “Leader Tools” → “My Team” → “Manage Rate Units” → “Currency”.", required = false) //
			@RequestParam(required = false) String billratecurrency, //
			//
			@ApiParam(value = "Bill rate unit(case sensitive), valid values can be found through “Leader Tools” → “My Team” → “Manage Rate Units” → “Bill Rate Units”.", required = false) //
			@RequestParam(required = false) String billrateunit, //
			//
			@ApiParam(value = "Pay rate", required = false) //
			@RequestParam(required = false) Double payrate, //
			//
			@ApiParam(value = "Pay rate currency(case sensitive), valid values can be found through “Leader Tools” → “My Team” → “Manage Rate Units” → “Currency”.", required = false) //
			@RequestParam(required = false) String payratecurrency, //
			//
			@ApiParam(value = "Pay rate unit(case sensitive), valid values can be found through “Leader Tools” → “My Team” → “Manage Rate Units” → “Pay Rate Units”.", required = false) //
			@RequestParam(required = false) String payrateunit, //
			//
			@ApiParam(required = false, type = "Userfield", allowMultiple = true) //
			@RequestParam(required = false) Userfield[] Userfields //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateStart");
		//
		return startService.updateStart(jobDivaSession, startid, overwrite, startDate, endDate, positiontype, billrate, billratecurrency, billrateunit, payrate, payratecurrency, payrateunit,Userfields);
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "timezone", required = false, allowMultiple = false, dataType = "Timezone") })
	@RequestMapping(value = "/setStart", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Set Start")
	public Boolean setStart( //
			//
			@ApiParam(value = "JobDiva internal submittal/start ID", required = false) //
			@RequestParam(required = false) Long submittalid, //
			//
			@ApiParam(value = "JobDiva internal User ID. If empty, the API User ID is used", required = false) //
			@RequestParam(required = false) Long recruiterid, //
			//
			//
			@ApiParam(value = "The date the employee will start working Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date startDate, //
			//
			//
			@ApiParam(value = "Date the start will be terminated, if any Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date endDate, //
			//
			@ApiParam(value = "the time zone of the start date and end date", type = "Timezone") //
			@RequestParam(required = false) Timezone timezone, //
			//
			@ApiParam(value = "Any internal notes and comments regarding the submittal/start.", required = false) //
			@RequestParam(required = false) String internalnotes //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("setStart");
		//
		return startService.setStart(jobDivaSession, submittalid, recruiterid, startDate, endDate, timezone, internalnotes);
		//
	}
	
	@RequestMapping(value = "/terminateStart", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Terminate Start")
	public Boolean terminateStart( //
			//
			@ApiParam(value = "JobDiva internal start ID", required = true) //
			@RequestParam(required = true) Long startid, //
			//
			@ApiParam(value = "JobDiva internal candidate ID", required = false) //
			@RequestParam(required = false) Long candidateid, //
			//
			@ApiParam(value = "JobDiva internal job ID", required = false) //
			@RequestParam(required = false) Long jobId, //
			//
			@ApiParam(value = "Date the start is terminated Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date terminationdate, //
			//
			@ApiParam(value = "Reason for living. Available values provided by JobDiva", required = false) //
			@RequestParam(required = false) Integer reason, //
			//
			@ApiParam(value = "Performance Code. Available values provided by JobDiva", required = false) //
			@RequestParam(required = false) Integer performancecode, //
			//
			@ApiParam(value = "Notes about the termination.", required = false) //
			@RequestParam(required = false) String notes, //
			//
			@ApiParam(value = "If mark as past employee", required = false) //
			@RequestParam(required = false) Boolean markaspastemployee, //
			//
			@ApiParam(value = "If mark employee as available until termination date", required = false) //
			@RequestParam(required = false) Boolean markasavailable //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("terminateStart");
		//
		return startService.terminateStart(jobDivaSession, startid, candidateid, jobId, terminationdate, reason, performancecode, notes, markaspastemployee, markasavailable);
		//
	}
	
	@RequestMapping(value = "/terminateAssignment", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Terminate Assignment")
	public Boolean terminateAssignment( //
			//
			@ApiParam(value = "JobDiva internal assignment ID", required = false) //
			@RequestParam(required = false) Long assignmentid, //
			//
			@ApiParam(value = "JobDiva internal candidate ID", required = false) //
			@RequestParam(required = false) Long candidateid, //
			//
			@ApiParam(value = "JobDiva internal job ID", required = false) //
			@RequestParam(required = false) Long jobId, //
			//
			@ApiParam(value = "Date the start is terminated Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date terminationdate, //
			//
			@ApiParam(value = "Reason for living. Available values provided by JobDiva", required = false) //
			@RequestParam(required = false) Integer reason, //
			//
			@ApiParam(value = "Performance Code. Available values provided by JobDiva", required = false) //
			@RequestParam(required = false) Integer performancecode, //
			//
			@ApiParam(value = "Notes about the termination.", required = false) //
			@RequestParam(required = false) String notes, //
			//
			@ApiParam(value = "If mark as past employee", required = false) //
			@RequestParam(required = false) Boolean markaspastemployee, //
			//
			@ApiParam(value = "If mark employee as available until termination date", required = false) //
			@RequestParam(required = false) Boolean markasavailable //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("terminateAssignment");
		//
		return startService.terminateAssignment(jobDivaSession, assignmentid, candidateid, jobId, terminationdate, reason, performancecode, notes, markaspastemployee, markasavailable);
		//
	}
}

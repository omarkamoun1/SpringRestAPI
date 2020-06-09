package com.jobdiva.api.controller.jobdivaapi;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.service.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "Event API", description = "REST API Used For Event")
public class EventController extends AbstractJobDivaController {
	//
	
	@Autowired
	EventService eventService;
	
	@RequestMapping(value = "/createTask", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Task")
	public Boolean createTask( //
			//
			@ApiParam(value = "The subject of the task", required = true) //
			@RequestParam(required = true) String subject, //
			//
			@ApiParam(value = "The date of the task that is due. Format [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date duedate, //
			//
			@ApiParam(value = "The user who the task assigns to", required = true) //
			@RequestParam(required = true) Long assignedtoid, //
			//
			@ApiParam(value = "The user who creates the task. If not specified, the task creator will be the same user who the task assigns to, creating a task for self.", required = false) //
			@RequestParam(required = false) Long assignedbyid, //
			//
			@ApiParam(value = "The type of the task. Available options can be provided upon request.", required = false) //
			@RequestParam(required = false) Integer tasktype, //
			//
			@ApiParam(value = "The percentage of the task that has been completed, from 0 to 100, where 100 indicates the task is completed.", required = false) //
			@RequestParam(required = false) Integer percentagecompleted, //
			//
			@ApiParam(value = "The point of contact of the task.", required = false) //
			@RequestParam(required = false) Long contactid, //
			//
			@ApiParam(value = "The candidate profile related to this task", required = false) //
			@RequestParam(required = false) Long candidateid, //
			//
			@ApiParam(value = "Additional description/remarks about the task.", required = false) //
			@RequestParam(required = false) String description, //
			//
			@ApiParam(value = "If making this task only visible for the creator and assigned user.", required = false) //
			@RequestParam(required = false) Boolean isprivate //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return eventService.createTask(jobDivaSession, subject, duedate, assignedtoid, assignedbyid, tasktype, percentagecompleted, contactid, candidateid, description, isprivate);
		//
	}
}

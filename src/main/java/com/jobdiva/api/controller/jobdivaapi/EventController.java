package com.jobdiva.api.controller.jobdivaapi;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.Event;
import com.jobdiva.api.model.EventNotification;
import com.jobdiva.api.model.Timezone;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.service.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "Event API", description = "REST API Used For Event")
public class EventController extends AbstractJobDivaController {
	//
	
	@Autowired
	EventService eventService;
	
	@RequestMapping(value = "/createTask", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Task")
	public Long createTask( //
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
		jobDivaSession.checkForAPIPermission("createTask");
		//
		return eventService.createTask(jobDivaSession, subject, duedate, assignedtoid, assignedbyid, tasktype, percentagecompleted, contactid, candidateid, description, isprivate);
		//
	}
	
	@RequestMapping(value = "/updateTask", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Task")
	public Long updateTask( //
			//
			@ApiParam(value = "Task Id", required = true) //
			@RequestParam(required = true) Long taskId, //
			//
			@ApiParam(value = "The subject of the task", required = true) //
			@RequestParam(required = true) String subject, //
			//
			@ApiParam(value = "The date of the task that is due. Format [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date duedate, //
			//
			@ApiParam(value = "Duration", required = true) //
			@RequestParam(required = true) Integer duration, //
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
		jobDivaSession.checkForAPIPermission("updateTask");
		//
		return eventService.updateTask(jobDivaSession, taskId, subject, duedate, duration, assignedtoid, assignedbyid, tasktype, percentagecompleted, contactid, candidateid, description, isprivate);
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "timezone", required = false, allowMultiple = false, dataType = "Timezone"), //
			@ApiImplicitParam(name = "eventNotification", required = false, allowMultiple = false, dataType = "EventNotification") //
	})
	@RequestMapping(value = "/createEvent", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Event")
	public Long createEvent( //
			//
			@ApiParam(value = "The subject of the task", required = true) //
			@RequestParam(required = true) String title, //
			//
			@ApiParam(value = "The type of the event. Available options can be provided upon request.", required = false) //
			@RequestParam(required = false) Integer eventType, //
			//
			@ApiParam(value = "Optional.", required = false) //
			@RequestParam(required = false) Integer optional, //
			//
			@ApiParam(value = "Event date. Format [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date eventDate, //
			//
			@ApiParam(value = "Priotity", required = true) //
			@RequestParam(required = true) Integer priority, //
			//
			@ApiParam(value = "Duration in minutes", required = false) //
			@RequestParam(required = false) Integer duration, //
			//
			@ApiParam(value = "Reminder", required = false) //
			@RequestParam(required = false) Long reminder, //
			//
			@ApiParam(value = "Status", required = false) //
			@RequestParam(required = false) Long status, //
			//
			@ApiParam(value = "Send Email Notifications Before", type = "EventNotification") //
			@RequestParam(required = false) EventNotification eventNotification, //
			//
			@ApiParam(value = "Time Zone", type = "Timezone") //
			@RequestParam(required = false) Timezone timezone, //
			//
			@ApiParam(value = "Lead Time", required = false) //
			@RequestParam(required = false) Long leadtime, //
			//
			@ApiParam(value = "Lag Time", required = false) //
			@RequestParam(required = false) Long lagTime, //
			//
			@ApiParam(value = "If making this event only visible for the creator and assigned user.", required = false) //
			@RequestParam(required = false) Boolean privateEvent, //
			//
			@ApiParam(value = "Repeat for day(s).", required = false) //
			@RequestParam(required = false) Integer repeatTimes, //
			//
			@ApiParam(value = "Participation Optional.", required = false) //
			@RequestParam(required = false) Boolean participationOptional, //
			//
			@ApiParam(value = "Location.", required = false) //
			@RequestParam(required = false) String location, //
			//
			@ApiParam(value = "Additional note/remarks about the task.", required = false) //
			@RequestParam(required = false) String notes, //
			//
			@ApiParam(value = "Customer Id", required = false) //
			@RequestParam(required = false) Long customerId, //
			//
			@ApiParam(value = "Opportunity Id(s)", required = false) //
			@RequestParam(required = false) List<Long> opportunityIds, //
			//
			@ApiParam(value = "Recruiter Id(s)", required = false) //
			@RequestParam(required = false) List<Long> recruiterids //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createEvent");
		//
		return eventService.createEvent(jobDivaSession, title, eventType, optional, eventDate, priority, duration, reminder, status, eventNotification, timezone, leadtime, lagTime, privateEvent, repeatTimes, participationOptional, location, notes,
				customerId, opportunityIds, recruiterids);
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "timezone", required = false, allowMultiple = false, dataType = "Timezone"), //
			@ApiImplicitParam(name = "eventNotification", required = false, allowMultiple = false, dataType = "EventNotification") //
	})
	@RequestMapping(value = "/updateEvent", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Event")
	public Boolean updateEvent( //
			//
			//
			@ApiParam(value = "Event Id", required = true) //
			@RequestParam(required = true) Long eventId, //
			//
			@ApiParam(value = "The subject of the task", required = true) //
			@RequestParam(required = true) String title, //
			//
			@ApiParam(value = "Event date. Format [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date eventDate, //
			//
			@ApiParam(value = "Duration in minutes", required = false) //
			@RequestParam(required = false) Integer duration, //
			//
			@ApiParam(value = "The type of the event. Available options can be provided upon request.", required = false) //
			@RequestParam(required = false) Integer eventType, //
			//
			@ApiParam(value = "Additional note/remarks about the task.", required = false) //
			@RequestParam(required = false) String notes, //
			//
			@ApiParam(value = "Priotity", required = true) //
			@RequestParam(required = true) Integer priority, //
			//
			@ApiParam(value = "Reminder", required = false) //
			@RequestParam(required = false) Long reminder, //
			//
			@ApiParam(value = "Status", required = false) //
			@RequestParam(required = false) Long status, //
			//
			@ApiParam(value = "Optional.", required = false) //
			@RequestParam(required = false) Integer optional, //
			//
			@ApiParam(value = "If making this event only visible for the creator and assigned user.", required = false) //
			@RequestParam(required = false) Boolean privateEvent, //
			//
			@ApiParam(value = "Time Zone", type = "Timezone") //
			@RequestParam(required = false) Timezone timezone, //
			//
			@ApiParam(value = "Lead Time", required = false) //
			@RequestParam(required = false) Long leadtime, //
			//
			@ApiParam(value = "Lag Time", required = false) //
			@RequestParam(required = false) Long lagTime, //
			//
			@ApiParam(value = "Location.", required = false) //
			@RequestParam(required = false) String location, //
			//
			@ApiParam(value = "Customer Id", required = false) //
			@RequestParam(required = false) Long customerId, //
			//
			@ApiParam(value = "Recruiter Id(s)", required = false) //
			@RequestParam(required = false) List<Long> recruiterids, //
			//
			@ApiParam(value = "Send Email Notifications Before", type = "EventNotification") //
			@RequestParam(required = false) EventNotification eventNotification //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateEvent");
		//
		return eventService.updateEvent(jobDivaSession, eventId, title, eventDate, duration, eventType, notes, priority, reminder, status, optional, privateEvent, timezone, leadtime, lagTime, location, customerId, recruiterids, eventNotification);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "test")
	public Boolean test() throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("test");
		//
		return eventService.test(jobDivaSession);
		//
	}
	
	@RequestMapping(value = "/searchEvents", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Contact Events")
	public List<Event> searchContactEvents( //
			//
			@ApiParam(value = "Recruiter ID", required = true) //
			@RequestParam(required = true) Long recruiterId, //
			//
			@ApiParam(value = "The event date [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date eventDate, //
			//
			@ApiParam(value = "The event end date", required = true) //
			@RequestParam(required = true) Date eventEndDate //
			//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("searchEvents");
		//
		return eventService.searchEvents(jobDivaSession, recruiterId, eventDate, eventEndDate);
		//
	}
}

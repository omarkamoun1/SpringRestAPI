package com.jobdiva.api.controller.jobdivaapi.v2;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.EventNotification;
import com.jobdiva.api.model.Timezone;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.v2.event.CreateEventDef;
import com.jobdiva.api.model.v2.event.CreateTaskDef;
import com.jobdiva.api.model.v2.event.UpdateEventDef;
import com.jobdiva.api.model.v2.event.UpdateTaskDef;
import com.jobdiva.api.service.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/apiv2/jobdiva/")
@Api(value = "Event API", description = "REST API Used For Event")
public class EventV2Controller extends AbstractJobDivaController {
	//
	
	@Autowired
	EventService eventService;
	
	@RequestMapping(value = "/createTask", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Task")
	public Long createTask( //
			//
			@ApiParam(value = "subject : The subject of the task \r\n" //
					+ "duedate : The date of the task that is due \r\n"//
					+ "assignedtoid : The user who the task assigns to \r\n"//
					+ "assignedbyid : The user who creates the task. If not specified, the task creator will be the same user who the task assigns to, creating a task for self. \r\n"//
					+ "tasktype : The type of the task. Available options can be provided upon request. \r\n"//
					+ "percentagecompleted : The percentage of the task that has been completed, from 0 to 100, where 100 indicates the task is completed. \r\n"//
					+ "contactid : The point of contact of the task. \r\n"//
					+ "candidateid : The candidate profile related to this task \r\n"//
					+ "description : Additional description/remarks about the task. \r\n"//
					+ "isprivate : If making this task only visible for the creator and assigned user.")
			//
			@RequestBody CreateTaskDef createTaskDef
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createTask");
		//
		//
		String subject = createTaskDef.getSubject();
		Date duedate = createTaskDef.getDuedate();
		Long assignedtoid = createTaskDef.getAssignedtoid();
		Long assignedbyid = createTaskDef.getAssignedbyid();
		Integer tasktype = createTaskDef.getTasktype();
		Integer percentagecompleted = createTaskDef.getPercentagecompleted();
		Long contactid = createTaskDef.getContactid();
		Long candidateid = createTaskDef.getCandidateid();
		String description = createTaskDef.getDescription();
		Boolean isprivate = createTaskDef.getIsprivate();
		//
		//
		return eventService.createTask(jobDivaSession, subject, duedate, assignedtoid, assignedbyid, tasktype, percentagecompleted, contactid, candidateid, description, isprivate);
		//
	}
	
	@RequestMapping(value = "/updateTask", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Task")
	public Long updateTask( //
			//
			@ApiParam(value = "taskId : Task Id \r\n"//
					+ "subject : The subject of the task \r\n"//
					+ "duedate : The date of the task that is due. \r\n"//
					+ "duration : Duration \r\n"//
					+ "assignedtoid : The user who the task assigns to \r\n"//
					+ "assignedbyid : The user who creates the task. If not specified, the task creator will be the same user who the task assigns to, creating a task for self. \r\n"//
					+ "tasktype : The type of the task. Available options can be provided upon request. \r\n"//
					+ "percentagecompleted : The percentage of the task that has been completed, from 0 to 100, where 100 indicates the task is completed. \r\n"//
					+ "contactid : The point of contact of the task. \r\n"//
					+ "candidateid : The candidate profile related to this task \r\n"//
					+ "description : Additional description/remarks about the task. \r\n"//
					+ "isprivate : If making this task only visible for the creator and assigned user." //
			)
			//
			@RequestBody UpdateTaskDef updateTaskDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateTask");
		//
		//
		Long taskId = updateTaskDef.getTaskId();
		String subject = updateTaskDef.getSubject();
		Date duedate = updateTaskDef.getDuedate();
		Integer duration = updateTaskDef.getDuration();
		Long assignedtoid = updateTaskDef.getAssignedtoid();
		Long assignedbyid = updateTaskDef.getAssignedbyid();
		Integer tasktype = updateTaskDef.getTasktype();
		Integer percentagecompleted = updateTaskDef.getPercentagecompleted();
		Long contactid = updateTaskDef.getContactid();
		Long candidateid = updateTaskDef.getCandidateid();
		String description = updateTaskDef.getDescription();
		Boolean isprivate = updateTaskDef.getIsprivate();
		//
		//
		return eventService.updateTask(jobDivaSession, taskId, subject, duedate, duration, assignedtoid, assignedbyid, tasktype, percentagecompleted, contactid, candidateid, description, isprivate);
		//
	}
	
	@RequestMapping(value = "/createEvent", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Event")
	public Long createEvent( //
			// //
			@ApiParam(value = "title : The subject of the task \r\n" //
					+ "eventType : The type of the event. Available options can be provided upon request. \r\n" //
					+ "optional : Optional \r\n" //
					+ "eventDate : Event date. \r\n" //
					+ "priority : Priority \r\n" //
					+ "duration : Duration in minutes \r\n" //
					+ "reminder : Reminder \r\n" //
					+ "status : Status \r\n" //
					+ "eventNotification : Send Email Notifications Before \r\n" //
					+ "timezone : Time Zone \r\n" //
					+ "leadtime : Lead Time \r\n" //
					+ "lagTime : Lag Time \r\n" //
					+ "privateEvent : If making this event only visible for the creator and assigned user. \r\n" //
					+ "repeatTimes : Repeat for day(s). \r\n" //
					+ "participationOptional : Participation Optional. \r\n" //
					+ "location : Location \r\n" //
					+ "notes : Additional note/remarks about the task. \r\n" //
					+ "customerId : Customer Id \r\n" //
					+ "opportunityIds : Opportunity Id(s) \r\n" //
					+ "recruiterids : Recruiter Id(s) " //
			) //
			@RequestBody CreateEventDef createEventDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createEvent");
		//
		//
		String title = createEventDef.getTitle();
		Integer eventType = createEventDef.getEventType();
		Integer optional = createEventDef.getOptional();
		Date eventDate = createEventDef.getEventDate();
		Integer priority = createEventDef.getPriority();
		Integer duration = createEventDef.getDuration();
		Long reminder = createEventDef.getReminder();
		Long status = createEventDef.getStatus();
		EventNotification eventNotification = createEventDef.getEventNotification();
		Timezone timezone = createEventDef.getTimezone();
		Long leadtime = createEventDef.getLeadtime();
		Long lagTime = createEventDef.getLagTime();
		Boolean privateEvent = createEventDef.getPrivateEvent();
		Integer repeatTimes = createEventDef.getRepeatTimes();
		Boolean participationOptional = createEventDef.getParticipationOptional();
		String location = createEventDef.getLocation();
		String notes = createEventDef.getNotes();
		Long customerId = createEventDef.getCustomerId();
		List<Long> opportunityIds = createEventDef.getOpportunityIds();
		List<Long> recruiterids = createEventDef.getRecruiterids();
		//
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
			@ApiParam(value = "eventId : Event Id \r\n" //
					+ "title : The subject of the task \r\n" //
					+ "eventDate : Event date. \r\n" //
					+ "duration : Duration in minutes \r\n" //
					+ "eventType : The type of the event. Available options can be provided upon request. \r\n" //
					+ "notes : Additional note/remarks about the task. \r\n" //
					+ "priority : Priority \r\n" //
					+ "reminder : Reminder \r\n" //
					+ "status : Status \r\n" //
					+ "optional : Optional \r\n" //
					+ "privateEvent : If making this event only visible for the creator and assigned user. \r\n" //
					+ "timezone : Time Zone \r\n" //
					+ "leadtime : Lead Time \r\n" //
					+ "lagTime : Lag Time \r\n" //
					+ "location : Location \r\n" //
					+ "customerId : Customer Id \r\n" //
					+ "recruiterids : Recruiter Id(s) \r\n" //
					+ "eventNotification : Send Email Notifications Before " //
			) //
			@RequestBody UpdateEventDef updateEventDef
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateEvent");
		//
		//
		//
		Long eventId = updateEventDef.getEventId();
		String title = updateEventDef.getTitle();
		Date eventDate = updateEventDef.getEventDate();
		Integer duration = updateEventDef.getDuration();
		Integer eventType = updateEventDef.getEventType();
		String notes = updateEventDef.getNotes();
		Integer priority = updateEventDef.getPriority();
		Long reminder = updateEventDef.getReminder();
		Long status = updateEventDef.getStatus();
		Integer optional = updateEventDef.getOptional();
		Boolean privateEvent = updateEventDef.getPrivateEvent();
		Timezone timezone = updateEventDef.getTimezone();
		Long leadtime = updateEventDef.getLeadtime();
		Long lagTime = updateEventDef.getLagTime();
		String location = updateEventDef.getLocation();
		Long customerId = updateEventDef.getCustomerId();
		List<Long> recruiterids = updateEventDef.getRecruiterids();
		EventNotification eventNotification = updateEventDef.getEventNotification();
		//
		//
		return eventService.updateEvent(jobDivaSession, eventId, title, eventDate, duration, eventType, notes, priority, reminder, status, optional, privateEvent, timezone, leadtime, lagTime, location, customerId, recruiterids, eventNotification);
		//
	}
}

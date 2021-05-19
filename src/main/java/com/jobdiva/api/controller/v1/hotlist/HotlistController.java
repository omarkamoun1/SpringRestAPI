package com.jobdiva.api.controller.v1.hotlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.service.HotlistService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Joseph Chidiac
 *
 */
@CrossOrigin
@RestController
@RequestMapping({ "/api/hotlist/" })
@Api(value = "Hotlist API", description = "REST API Used For Hotlist")
// @ApiIgnore
public class HotlistController extends AbstractJobDivaController {
	
	@Autowired
	HotlistService hotlistService;
	
	@ApiOperation("Create Candidate Hotlist")
	@RequestMapping(value = { "/createCandidateHoltilst" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Long createCandidateHoltilst(//
			//
			@ApiParam(value = "Hotlist Name", required = true) //
			@RequestParam(required = true) String name, //
			//
			@ApiParam(value = "Link to Job", required = false) //
			@RequestParam(required = false) Long linkToJobId, //
			//
			@ApiParam(value = "Link to Hiring Manager", required = false) //
			@RequestParam(required = false) Long linkToHiringManagerId, //
			//
			@ApiParam(value = "Description", required = true) //
			@RequestParam(required = true) String description, //
			//
			@ApiParam(value = "Users", required = false) //
			@RequestParam(required = false) List<Long> userIds, //
			//
			@ApiParam(value = "Groups", required = false) //
			@RequestParam(required = false) List<Long> groupIds, //
			//
			@ApiParam(value = "Divisions", required = false) //
			@RequestParam(required = false) List<Long> divisionIds //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createCandidateHoltilst");
		//
		return this.hotlistService.createCandidateHoltilst(jobDivaSession, name, linkToJobId, linkToHiringManagerId, description, userIds, groupIds, divisionIds);
		//
	}
	
	@ApiOperation("Add Candidate To Hotlist")
	@RequestMapping(value = { "/addCandidateToHotlist" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Boolean addCandidateToHotlist(//
			//
			@ApiParam(value = "Hotlist Id", required = true) //
			@RequestParam(required = true) Long hotListid, //
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("addCandidateToHotlist");
		//
		return this.hotlistService.addCandidateToHotlist(jobDivaSession, hotListid, candidateId);
	}
	
	// No release to production
	@ApiIgnore
	@ApiOperation("Add Candidates To Hotlist")
	@RequestMapping(value = { "/addCandidatesToHotlist" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Boolean addCandidatesToHotlist(//
			//
			@ApiParam(value = "Hotlist Id", required = true) //
			@RequestParam(required = true) Long hotListid, //
			//
			@ApiParam(value = "Candidate Id(s)", required = true) //
			@RequestParam(required = true) List<Long> candidateIds //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("addCandidatesToHotlist");
		//
		return this.hotlistService.addCandidatesToHotlist(jobDivaSession, hotListid, candidateIds);
	}
	
	@ApiOperation("Create Contact Hotlist")
	@RequestMapping(value = { "/createContactHotlist" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Long createContactHotlist(//
			//
			@ApiParam(value = "Hotlist Name", required = true) //
			@RequestParam(required = true) String name, //
			//
			@ApiParam(value = "Active", required = false) //
			@RequestParam(required = false) Boolean active, //
			//
			@ApiParam(value = "Private", required = false) //
			@RequestParam(required = false) Boolean isPrivate, //
			//
			@ApiParam(value = "Description", required = false) //
			@RequestParam(required = false) String description, //
			//
			@ApiParam(value = "Shared with", required = false) //
			@RequestParam(required = false) List<Long> sharedWithIds //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createContactHotlist");
		//
		return this.hotlistService.createContactHotlist(jobDivaSession, name, active, isPrivate, description, sharedWithIds);
		//
	}
	
	@ApiOperation("Add Contact To Hotlist")
	@RequestMapping(value = { "/addContactToHotlist" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Boolean addContactToHotlist(
			//
			@ApiParam(value = "Hotlist Id", required = true) //
			@RequestParam(required = true) Long hotListid, //
			//
			@ApiParam(value = "Contact Id", required = true) //
			@RequestParam(required = true) Long contactId //
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("addContactToHotlist");
		//
		return this.hotlistService.addContactToHotlist(jobDivaSession, hotListid, contactId);
		//
	}
}

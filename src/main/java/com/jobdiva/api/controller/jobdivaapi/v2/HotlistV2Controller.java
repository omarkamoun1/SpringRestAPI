package com.jobdiva.api.controller.jobdivaapi.v2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.v2.hotlist.AddCandidatesToHotlistDef;
import com.jobdiva.api.model.v2.hotlist.AddContactToHotlistDef;
import com.jobdiva.api.model.v2.hotlist.CreateCandidateHoltilstDef;
import com.jobdiva.api.model.v2.hotlist.CreateContactHotlistDef;
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
@RequestMapping({ "/apiv2/hotlist/" })
@Api(value = "Hotlist API", description = "REST API Used For Hotlist")
@ApiIgnore
public class HotlistV2Controller extends AbstractJobDivaController {
	
	@Autowired
	HotlistService hotlistService;
	
	@ApiOperation("Create Candidate Hotlist")
	@RequestMapping(value = { "/createCandidateHoltilst" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Long createCandidateHoltilst(//
			//
			@ApiParam(value = "name : Hotlist Name \r\n" //
					+ "linkToJobId : Link to Job \r\n" //
					+ "linkToHiringManagerId : Link to Hiring Manager \r\n" //
					+ "description : Description \r\n" //
					+ "userIds : Users \r\n" //
					+ "groupIds : Groups \r\n" //
					+ "divisionIds : Divisions " //
			)
			//
			@RequestBody CreateCandidateHoltilstDef createCandidateHoltilstDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createCandidateHoltilst");
		//
		String name = createCandidateHoltilstDef.getName();
		Long linkToJobId = createCandidateHoltilstDef.getLinkToJobId();
		Long linkToHiringManagerId = createCandidateHoltilstDef.getLinkToHiringManagerId();
		String description = createCandidateHoltilstDef.getDescription();
		List<Long> userIds = createCandidateHoltilstDef.getUserIds();
		List<Long> groupIds = createCandidateHoltilstDef.getGroupIds();
		List<Long> divisionIds = createCandidateHoltilstDef.getDivisionIds();
		return this.hotlistService.createCandidateHoltilst(jobDivaSession, name, linkToJobId, linkToHiringManagerId, description, userIds, groupIds, divisionIds);
		//
	}
	
	@ApiOperation("Add Candidates To Hotlist")
	@RequestMapping(value = { "/addCandidatesToHotlist" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Boolean addCandidatesToHotlist(//
			//
			@ApiParam(value = "hotListid : Hotlist Id \r\n" //
					+ "candidateIds : Candidate Id(s)" //
			)
			//
			@RequestBody AddCandidatesToHotlistDef addCandidatesToHotlistDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("addCandidatesToHotlist");
		//
		//
		Long hotListid = addCandidatesToHotlistDef.getHotListid();
		List<Long> candidateIds = addCandidatesToHotlistDef.getCandidateIds();
		//
		//
		return this.hotlistService.addCandidatesToHotlist(jobDivaSession, hotListid, candidateIds);
	}
	
	@ApiOperation("Create Contact Hotlist")
	@RequestMapping(value = { "/createContactHotlist" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Long createContactHotlist(//
			//
			@ApiParam(value = "name : Hotlist Name \r\n" //
					+ "active : Active \r\n" //
					+ "isPrivate : Private \r\n" //
					+ "description : Description \r\n" //
					+ "sharedWithIds : Shared with ") //
			@RequestBody CreateContactHotlistDef createContactHotlistDef) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createContactHotlist");
		//
		//
		String name = createContactHotlistDef.getName();
		Boolean active = createContactHotlistDef.getActive();
		Boolean isPrivate = createContactHotlistDef.getIsPrivate();
		String description = createContactHotlistDef.getDescription();
		List<Long> sharedWithIds = createContactHotlistDef.getSharedWithIds();
		//
		//
		return this.hotlistService.createContactHotlist(jobDivaSession, name, active, isPrivate, description, sharedWithIds);
		//
	}
	
	@ApiOperation("Add Contact To Hotlist")
	@RequestMapping(value = { "/addContactToHotlist" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Boolean addContactToHotlist(
			//
			@ApiParam(value = "hotListid : Hotlist Id \r\n" //
					+ "contactId : Contact Id ") //
			@RequestBody AddContactToHotlistDef addContactToHotlistDef //
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("addContactToHotlist");
		//
		Long hotListid = addContactToHotlistDef.getHotListid();
		Long contactId = addContactToHotlistDef.getContactId();
		//
		return this.hotlistService.addContactToHotlist(jobDivaSession, hotListid, contactId);
		//
	}
}

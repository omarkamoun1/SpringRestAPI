package com.jobdiva.api.controller.jobposting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.service.IrishJobsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/irishjobs/")
@ApiIgnore
public class IrishJobsController extends AbstractJobDivaController {
	
	@Autowired
	IrishJobsService irishJobsService;
	
	//
	@ApiOperation(value = "Request")
	@RequestMapping(value = "/request", method = RequestMethod.POST, produces = "application/json")
	public String request(
			//
			//
			@ApiParam(value = "username", required = true, type = "String") //
			@RequestParam(required = true) String username, //
			//
			@ApiParam(value = "password", required = true, type = "String") //
			@RequestParam(required = true) String password, //
			//
			@ApiParam(value = "action", required = true, type = "String") //
			@RequestParam(required = true) String action, //
			//
			@ApiParam(value = "rfqid", required = true, type = "String") //
			@RequestParam(required = true) String rfqid, //
			//
			@ApiParam(value = "recruiterid", required = true, type = "String") //
			@RequestParam(required = true) String recruiterid, //
			//
			@ApiParam(value = "teamid", required = true, type = "String") //
			@RequestParam(required = true) String teamid //
	//
	//
	) throws Exception {//
		//
		getJobDivaSession();
		return irishJobsService.request(username, password, action, rfqid, recruiterid, teamid);
	}
	
	@ApiOperation(value = "GetSlots")
	@RequestMapping(value = "/getSlots", method = RequestMethod.GET, produces = "application/json")
	public String getSlots(
			//
			//
			@ApiParam(value = "username", required = true, type = "String") //
			@RequestParam(required = true) String username, //
			//
			@ApiParam(value = "password", required = true, type = "String") //
			@RequestParam(required = true) String password, //
			//
			@ApiParam(value = "recruiterid", required = true, type = "String") //
			@RequestParam(required = true) String recruiterid, //
			//
			@ApiParam(value = "teamid", required = true, type = "String") //
			@RequestParam(required = true) String teamid //
	//
	//
	) throws Exception {//
		//
		getJobDivaSession();
		return irishJobsService.getSlots(username, password, recruiterid, teamid);
	}
	
	@ApiOperation(value = "GetAllLists")
	@RequestMapping(value = "/getAllLists", method = RequestMethod.GET, produces = "application/json")
	public String getUnitList() throws Exception {
		//
		getJobDivaSession();
		return irishJobsService.getAllLists();
	}
}
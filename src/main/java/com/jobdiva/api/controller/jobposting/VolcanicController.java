package com.jobdiva.api.controller.jobposting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.service.VolcanicService;
import com.jobdiva.api.model.authenticate.JobDivaSession;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/volcanic/")
@ApiIgnore
public class VolcanicController extends AbstractJobDivaController {
	
	@Autowired
	VolcanicService volcanicService;
	
	//
	@ApiOperation(value = "Request")
	@RequestMapping(value = "/request", method = RequestMethod.POST, produces = "application/json")
	public String request(
			//
			//
			@ApiParam(value = "req", required = true, type = "String") //
			@RequestParam(required = true) String req, //
			//
			@ApiParam(value = "rfqid", required = true, type = "String") //
			@RequestParam(required = true) String rfqid, //
			//
			@ApiParam(value = "refNo", required = true, type = "String") //
			@RequestParam(required = true) String refNo, //
			//
			@ApiParam(value = "website", required = true, type = "String") //
			@RequestParam(required = true) String website, //
			//
			@ApiParam(value = "apiKey", required = true, type = "String") //
			@RequestParam(required = true) String apiKey //
			//
	//
	) throws Exception {//
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		
		return volcanicService.request(req, jobDivaSession.getTeamId(), rfqid, refNo, website, apiKey);

	}
	
	
	@ApiOperation(value = "GetLists")
	@RequestMapping(value = "/getLists", method = RequestMethod.GET, produces = "application/json")
	public String getLists(
			//
			//
			@ApiParam(value = "website", required = true, type = "String") //
			@RequestParam(required = true) String website, //
			//
			@ApiParam(value = "apiKey", required = true, type = "String") //
			@RequestParam(required = true) String apiKey //
			//
	//
	) throws Exception {//
		//
		return volcanicService.getLists(website, apiKey);

	}
	
}
package com.jobdiva.api.controller.jobposting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.service.JobServeUKService;
import com.jobdiva.api.model.authenticate.JobDivaSession;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/jobserveuk/")
@ApiIgnore
public class JobServeUKController extends AbstractJobDivaController {
	
	@Autowired
	JobServeUKService jobServeUKService;
	
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
			@ApiParam(value = "username", required = true, type = "String") //
			@RequestParam(required = true) String username, //
			//
			@ApiParam(value = "pass", required = true, type = "String") //
			@RequestParam(required = true) String pass //
			//
	//
	) throws Exception {//
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		
		return jobServeUKService.request(req, jobDivaSession.getTeamId(), rfqid, username, pass);
	}
	
	
}


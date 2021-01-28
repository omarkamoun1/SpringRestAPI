package com.jobdiva.api.controller.jobposting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.service.NIJobsService;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.JobBoardResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/nijobs/")
@ApiIgnore
public class NIJobsController extends AbstractJobDivaController {
	
	@Autowired
	NIJobsService niJobsService;
	
	//
	@ApiOperation(value = "Request")
	@RequestMapping(value = "/request", method = RequestMethod.POST, produces = "application/json")
	public JobBoardResponse request(
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
		
		return niJobsService.request(req, jobDivaSession.getTeamId(), rfqid, username, pass);

	}
	
	
}
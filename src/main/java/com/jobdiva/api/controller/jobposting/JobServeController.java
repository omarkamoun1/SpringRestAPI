package com.jobdiva.api.controller.jobposting;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.authenticate.JobDivaSession;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Joseph Chidiac
 *
 */
@RestController
@RequestMapping("/api/jobserve/")
@Api(value = "JobServe API", description = "REST API Used For JobServe")
public class JobServeController extends AbstractJobDivaController {
	
	//
	@ApiOperation(value = "PostAdvert")
	@RequestMapping(value = "/postAdvert", method = RequestMethod.GET, produces = "application/json")
	public String postAdvert(//
	//
	//
	) throws Exception {//
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return "Success";
		//
	}
}

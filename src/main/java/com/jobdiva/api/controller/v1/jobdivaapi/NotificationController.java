package com.jobdiva.api.controller.v1.jobdivaapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.service.NotificationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "Notifications API", description = "REST API Used For Notifications")
public class NotificationController extends AbstractJobDivaController{

	
	//
	@Autowired
	NotificationService notificationService;
	
	
	@ApiOperation(value = "Register Notification")
 	@RequestMapping(value = "/registerNotification", method = RequestMethod.POST, produces = "application/json")
 	public Boolean registerNotification( //
 			//
 			@ApiParam(value = "Device Id", required = true) //
 			@RequestParam(required = true) String deviceId,  //
 			//
 			@ApiParam(value = "Token", required = true) //
 			@RequestParam(required = true) String token,  //
 			//
 			@ApiParam(value = "Device Type", required = true) //
 			@RequestParam(required = true) String deviceType,  //
 			//
 			@ApiParam(value = "App Version", required = true) //
 			@RequestParam(required = true) String appVersion  //
 			//
 	) throws Exception {
 		//
 		JobDivaSession jobDivaSession = getJobDivaSession();
 		//
 		jobDivaSession.checkForAPIPermission("registerNotification");
 		//
 		return notificationService.registerNotification(jobDivaSession, deviceId, token, deviceType,appVersion);
 		//
 	} //
	
	
}

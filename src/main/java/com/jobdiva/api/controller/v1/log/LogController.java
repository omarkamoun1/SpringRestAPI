package com.jobdiva.api.controller.v1.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.service.LogService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Joseph Chidiac
 *
 */
@RestController
@RequestMapping("/api/")
@ApiIgnore
@CrossOrigin(origins = "*")
public class LogController extends AbstractJobDivaController {
	
	@Autowired
	LogService logService;
	
	//
	@ApiIgnore
	@GetMapping(value = "/log", produces = "application/json")
	public void log(String type, String entity, Long teamId, Long userId, Long recordId, String message) {
		logService.log(type, entity, teamId, userId, recordId, message);
	}
}

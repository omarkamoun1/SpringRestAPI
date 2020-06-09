package com.jobdiva.api.controller.coddler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.controller.Coddler;
import com.jobdiva.api.model.controller.Machine;
import com.jobdiva.api.model.controller.Configuration;
import com.jobdiva.api.service.CoddlerService;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/controller/")
@ApiIgnore
@CrossOrigin(origins = "*")
public class CoddlerController extends AbstractJobDivaController {
	
	@Autowired
	CoddlerService coddlerService;
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/getSession", produces = "application/json")
	public JobDivaSession getSession( //
	//
	) throws Exception {
		//
		return getJobDivaSession();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getCoddlers", produces = "application/json")
	public List<Coddler> getCoddlers( //
			//
			@RequestParam Integer machineId, //
			//
			@RequestParam(required = false) Long teamId) //
			throws Exception {
		//
		return coddlerService.getCoddlers(machineId, teamId);
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getConfiguration", produces = "application/json")
	public Configuration getConfiguration( //
			//
			@RequestParam Integer machineId, //
			//
			@RequestParam(required = false) Long teamId) ///
			throws Exception {
		//
		return coddlerService.getConfiguration(machineId, teamId);
		//
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/saveCoddlers")
	public Boolean saveCoddlers(@RequestBody List<Coddler> coddlers //
	//
	) throws Exception {
		//
		return coddlerService.saveCoddlers(coddlers);
		//
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/saveConfiguration")
	public void saveConfiguration(@RequestBody Configuration configuration) {
		coddlerService.saveConfiguration(configuration);
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/saveMachineId")
	public Integer saveMachineId(@RequestBody Machine company) {
		//
		return coddlerService.saveMachineId(company.getMachineId());
		//
	}
}

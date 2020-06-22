package com.jobdiva.api.controller.jobdivaapi;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.Submittal;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.service.SubmittalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "Submittal API", description = "REST API Used For Submittal")
public class SubmittalController extends AbstractJobDivaController {
	//
	
	@Autowired
	SubmittalService submittalService;
	
	@RequestMapping(value = "/searchSubmittal", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Submittal")
	public List<Submittal> searchSubmittal( //
			//
			@ApiParam(value = "Submittal ID", required = false) //
			@RequestParam(required = false) Long submittalid, //
			//
			@ApiParam(value = "Job ID", required = false) //
			@RequestParam(required = false) Long jobid, //
			//
			@ApiParam(value = "Client job reference number", required = false) //
			@RequestParam(required = false) String joboptionalref, //
			// false
			@ApiParam(value = "Company Name", required = false) //
			@RequestParam(required = false) String companyname, //
			//
			@ApiParam(value = "Candidate ID", required = false) //
			@RequestParam(required = false) Long candidateid, //
			//
			@ApiParam(value = "Candidate first name, be ignored if “candidateid” is set.", required = false) //
			@RequestParam(required = false) String candidatefirstname, //
			//
			@ApiParam(value = "Candidate last name, be ignored if “candidateid” is set.", required = false) //
			@RequestParam(required = false) String candidatelastname, //
			//
			@ApiParam(value = "Candidate email, be ignored if “candidateid” is set.", required = false) //
			@RequestParam(required = false) String candidateemail, //
			//
			@ApiParam(value = "Candidate phone number, be ignored if “candidateid” is set.", required = false) //
			@RequestParam(required = false) String candidatephone, //
			//
			@ApiParam(value = "Candidate city, be ignored if “candidateid” is set.", required = false) //
			@RequestParam(required = false) String candidatecity, //
			//
			@ApiParam(value = "Candidate state, be ignored if “candidateid” is set.", required = false) //
			@RequestParam(required = false) String candidatestate //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return submittalService.searchSubmittal(jobDivaSession, submittalid, jobid, joboptionalref, companyname, candidateid, candidatefirstname, candidatelastname, candidateemail, candidatephone, candidatecity, candidatestate);
		//
	}
	
	@ApiImplicitParams({ //
			@ApiImplicitParam(name = "userfields", required = false, allowMultiple = true, dataType = "Userfield") //
	})
	@RequestMapping(value = "/createSubmittal", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Submittal")
	public Long createSubmittal( //
			//
			@ApiParam(value = "Job ID", required = true) //
			@RequestParam(required = true) Long jobid, //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Submittal Status", required = false) //
			@RequestParam(required = false) String status, //
			//
			@ApiParam(value = "Contact ID (external submittal), if empty, the job’s main contact ID is used", required = false) //
			@RequestParam(required = false) Long submit2id, //
			//
			@ApiParam(value = "Date of Submittal Format [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date submittaldate, //
			//
			@ApiParam(value = "Job type", required = false) //
			@RequestParam(required = false) String positiontype, //
			//
			@ApiParam(value = "User ID of ‘Recruited By’. If empty, the API User ID is used", required = false) //
			@RequestParam(required = false) Long recruitedbyid, //
			//
			@ApiParam(value = "User ID of Primary Sales. If empty, the job’s Primary Sales ID is used. If the job does not have a Primary Sales assigned, the API User ID is used", required = false) //
			@RequestParam(required = false) Long primarysalesid, //
			//
			@ApiParam(value = "Internal notes", required = false) //
			@RequestParam(required = false) String internalnotes, //
			//
			@ApiParam(value = "Salary amount. Only if positiontype is ‘Direct Placement’", required = false) //
			@RequestParam(required = false) Double salary, //
			//
			@ApiParam(value = "Fee type. Only if positiontype is ‘Direct Placement’", required = false) //
			@RequestParam(required = false) Integer feetype, //
			//
			@ApiParam(value = "Fee. Only if positiontype is ‘Direct Placement’", required = false) //
			@RequestParam(required = false) Double fee, //
			//
			@ApiParam(value = "Quoted bill rate. Will be ignored if ‘Direct Placement’", required = false) //
			@RequestParam(required = false) Double quotedbillrate, //
			//
			@ApiParam(value = "Agreed bill rate. Will be ignored if ‘Direct Placement’", required = false) //
			@RequestParam(required = false) Double agreedbillrate, //
			//
			@ApiParam(value = "Pay rate. Will be ignored if ‘Direct Placement’", required = false) //
			@RequestParam(required = false) Double payrate, //
			//
			@ApiParam(value = "Pay rate currency. Will be ignored if ‘Direct Placement’", required = false) //
			@RequestParam(required = false) String payratecurrency, //
			//
			@ApiParam(value = "Pay rate unit. Will be ignored if ‘Direct Placement’", required = false) //
			@RequestParam(required = false) String payrateunit, //
			//
			@ApiParam(value = "If the submittal is corp to corp? Will be ignored if ‘Direct Placement’", required = false) //
			@RequestParam(required = false) Boolean corp2corp, //
			//
			@ApiParam(value = "Agreement date Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date agreedon, //
			//
			@ApiParam(value = "User fields", required = false) //
			@RequestParam(required = false) Userfield[] userfields, //
			//
			@ApiParam(value = "Resume file name of the candidate of the submittal", required = false) //
			@RequestParam(required = false) String filename, //
			//
			@ApiParam(value = "Resume file", required = false) //
			@RequestParam(required = false) Byte[] filecontent, //
			//
			@ApiParam(value = "Text content of the resume. Will be ignored if filecontent exists.", required = false) //
			@RequestParam(required = false) String textfile //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return submittalService.createSubmittal(jobDivaSession, jobid, candidateid, status, submit2id, submittaldate, positiontype, recruitedbyid, primarysalesid, internalnotes, salary, feetype, fee, quotedbillrate, agreedbillrate, payrate,
				payratecurrency, payrateunit, corp2corp, agreedon, userfields, filename, filecontent, textfile);
	}
	
	@RequestMapping(value = "/updateSubmittal", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Submittal")
	public Boolean updateSubmittal( //
			//
			@ApiParam(value = "Submittal record ID", required = true) //
			@RequestParam(required = true) Long submittalid, //
			//
			@ApiParam(value = "Updated Submittal Status", required = false) //
			@RequestParam(required = false) String status, //
			//
			@ApiParam(value = "Updated Internal notes", required = false) //
			@RequestParam(required = false) String internalnotes, //
			//
			@ApiParam(value = "Salary amount", required = false) //
			@RequestParam(required = false) Double salary, //
			//
			@ApiParam(value = "The type of fee", required = false) //
			@RequestParam(required = false) Integer feetype, //
			//
			@ApiParam(value = "Fee amount", required = false) //
			@RequestParam(required = false) Double fee, //
			//
			@ApiParam(value = "Quoted bill rate", required = false) //
			@RequestParam(required = false) Double quotedbillrate, //
			//
			@ApiParam(value = "Agreed bill rate", required = false) //
			@RequestParam(required = false) Double agreedbillrate, //
			//
			@ApiParam(value = "Pay rate", required = false) //
			@RequestParam(required = false) Double payrate, //
			//
			@ApiParam(value = "The currency of pay rate above", required = false) //
			@RequestParam(required = false) String payratecurrency, //
			//
			@ApiParam(value = "Pay rate unit", required = false) //
			@RequestParam(required = false) String payrateunit, //
			//
			@ApiParam(value = "If the submittal is corporate-to-corporate?", required = false) //
			@RequestParam(required = false) Boolean corp2corp, //
			//
			@ApiParam(value = "Date and time the agreement is being made Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date agreedon, //
			//
			@ApiParam(value = "Date and time of the interview Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date interviewdate, //
			//
			@ApiParam(value = "Date and time the submittal is internally rejected Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date internalrejectdate, //
			//
			@ApiParam(value = "Date and time the submittal is externally rejected Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date externalrejectdate //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return submittalService.updateSubmittal(jobDivaSession, submittalid, status, internalnotes, salary, feetype, fee, quotedbillrate, agreedbillrate, payrate, payratecurrency, payrateunit, corp2corp, agreedon, interviewdate, internalrejectdate,
				externalrejectdate);
	}
}

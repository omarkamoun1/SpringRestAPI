package com.jobdiva.api.controller.v2;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.Submittal;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.v2.submittal.CreateSubmittalDef;
import com.jobdiva.api.model.v2.submittal.SearchSubmittalDef;
import com.jobdiva.api.model.v2.submittal.UpdateSubmittalDef;
import com.jobdiva.api.service.SubmittalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@RequestMapping("/apiv2/jobdiva/")
@Api(value = "Submittal API", description = "REST API Used For Submittal")
@ApiIgnore
public class SubmittalV2Controller extends AbstractJobDivaController {
	//
	
	@Autowired
	SubmittalService submittalService;
	
	@RequestMapping(value = "/searchSubmittal", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Submittal")
	public List<Submittal> searchSubmittal( //
			//
			@ApiParam(value = "submittalid : Submittal ID \r\n" //
					+ "jobid : Job ID \r\n" //
					+ "joboptionalref : Client job reference number \r\n" //
					+ "companyname : Company Name \r\n" //
					+ "candidateid : Candidate ID \r\n" //
					+ "candidatefirstname : Candidate first name, be ignored if “candidateid” is set. \r\n" //
					+ "candidatelastname : Candidate last name, be ignored if “candidateid” is set. \r\n" //
					+ "candidateemail : Candidate email, be ignored if “candidateid” is set. \r\n" //
					+ "candidatephone : Candidate phone number, be ignored if “candidateid” is set. \r\n" //
					+ "candidatecity : Candidate city, be ignored if “candidateid” is set. \r\n" //
					+ "candidatestate : Candidate state, be ignored if “candidateid” is set." //
			) //
			@RequestBody SearchSubmittalDef searchSubmittalDef //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("searchSubmittal");
		//
		Long submittalid = searchSubmittalDef.getSubmittalid();
		Long jobid = searchSubmittalDef.getJobid();
		String joboptionalref = searchSubmittalDef.getJoboptionalref();
		String companyname = searchSubmittalDef.getCompanyname();
		Long candidateid = searchSubmittalDef.getCandidateid();
		String candidatefirstname = searchSubmittalDef.getCandidatefirstname();
		String candidatelastname = searchSubmittalDef.getCandidatelastname();
		String candidateemail = searchSubmittalDef.getCandidateemail();
		String candidatephone = searchSubmittalDef.getCandidatephone();
		String candidatecity = searchSubmittalDef.getCandidatecity();
		String candidatestate = searchSubmittalDef.getCandidatestate();
		//
		return submittalService.searchSubmittal(jobDivaSession, submittalid, jobid, joboptionalref, companyname, candidateid, candidatefirstname, candidatelastname, candidateemail, candidatephone, candidatecity, candidatestate);
		//
	}
	
	@RequestMapping(value = "/createSubmittal", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Submittal")
	public Long createSubmittal( //
			//
			@ApiParam(value = "jobid : Job ID \r\n" //
					+ "candidateid : Candidate ID \r\n" //
					+ "status : Submittal Status \r\n" //
					+ "submit2id : Contact ID (external submittal), if empty, the job’s main contact ID is used \r\n" //
					+ "submittaldate : Date of Submittal \r\n" //
					+ "positiontype : Job type \r\n" //
					+ "recruitedbyid : User ID of ‘Recruited By’. If empty, the API User ID is used \r\n" //
					+ "primarysalesid : User ID of Primary Sales. If empty, the job’s Primary Sales ID is used. If the job does not have a Primary Sales assigned, the API User ID is used \r\n" //
					+ "internalnotes : Internal notes \r\n" //
					+ "salary : Salary amount. Only if positiontype is ‘Direct Placement’ \r\n" //
					+ "feetype : Fee type. Only if positiontype is ‘Direct Placement’ \r\n" //
					+ "fee : Fee. Only if positiontype is ‘Direct Placement’ \r\n" //
					+ "quotedbillrate : Quoted bill rate. Will be ignored if ‘Direct Placement’ \r\n" //
					+ "agreedbillrate : Agreed bill rate. Will be ignored if ‘Direct Placement’ \r\n" //
					+ "payrate : Pay rate. Will be ignored if ‘Direct Placement’ \r\n" //
					+ "payratecurrency : Pay rate currency. Will be ignored if ‘Direct Placement’ \r\n" //
					+ "payrateunit : Pay rate unit. Will be ignored if ‘Direct Placement’ \r\n" //
					+ "corp2corp : If the submittal is corp to corp? Will be ignored if ‘Direct Placement’ \r\n" //
					+ "agreedon : Agreement date  \r\n" //
					+ "userfields : User fields \r\n" //
					+ "filename : Resume file name of the candidate of the submittal \r\n" //
					+ "filecontent : Resume file \r\n" //
					+ "textfile : Text content of the resume. Will be ignored if filecontent exists." //
			)
			//
			@RequestBody CreateSubmittalDef createSubmittalDef //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createSubmittal");
		//
		//
		//
		Long jobid = createSubmittalDef.getJobid();
		Long candidateid = createSubmittalDef.getCandidateid();
		String status = createSubmittalDef.getStatus();
		Long submit2id = createSubmittalDef.getSubmit2id();
		Date submittaldate = createSubmittalDef.getSubmittaldate();
		String positiontype = createSubmittalDef.getPositiontype();
		Long recruitedbyid = createSubmittalDef.getRecruitedbyid();
		Long primarysalesid = createSubmittalDef.getPrimarysalesid();
		String internalnotes = createSubmittalDef.getInternalnotes();
		Double salary = createSubmittalDef.getSalary();
		Integer feetype = createSubmittalDef.getFeetype();
		Double fee = createSubmittalDef.getFee();
		Double quotedbillrate = createSubmittalDef.getQuotedbillrate();
		Double agreedbillrate = createSubmittalDef.getAgreedbillrate();
		Double payrate = createSubmittalDef.getPayrate();
		String payratecurrency = createSubmittalDef.getPayratecurrency();
		String payrateunit = createSubmittalDef.getPayrateunit();
		Boolean corp2corp = createSubmittalDef.getCorp2corp();
		Date agreedon = createSubmittalDef.getAgreedon();
		Userfield[] userfields = createSubmittalDef.getUserfields();
		String filename = createSubmittalDef.getFilename();
		Byte[] filecontent = createSubmittalDef.getFilecontent();
		String textfile = createSubmittalDef.getTextfile();
		//
		//
		return submittalService.createSubmittal(jobDivaSession, jobid, candidateid, status, submit2id, submittaldate, positiontype, recruitedbyid, primarysalesid, internalnotes, salary, feetype, fee, quotedbillrate, agreedbillrate, payrate,
				payratecurrency, payrateunit, corp2corp, agreedon, userfields, filename, filecontent, textfile);
	}
	
	@RequestMapping(value = "/updateSubmittal", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Submittal")
	public Boolean updateSubmittal( //
			//
			@ApiParam(value = "submittalid : Submittal record ID \r\n" //
					+ "status : Updated Submittal Status \r\n" //
					+ "internalnotes : Updated Internal notes \r\n" //
					+ "salary : Salary amount \r\n" //
					+ "feetype : The type of fee \r\n" //
					+ "fee : Fee amount \r\n" //
					+ "quotedbillrate : Quoted bill rate \r\n" //
					+ "agreedbillrate : Agreed bill rate \r\n" //
					+ "payrate : Pay rate \r\n" //
					+ "payratecurrency : The currency of pay rate above \r\n" //
					+ "payrateunit : Pay rate unit \r\n" //
					+ "corp2corp : If the submittal is corporate-to-corporate? \r\n" //
					+ "agreedon : Date and time the agreement is being made \r\n" //
					+ "interviewdate : Date and time of the interview \r\n" //
					+ "internalrejectdate : Date and time the submittal is internally rejected \r\n" //
					+ "externalrejectdate : Date and time the submittal is externally rejected " //
			)
			//
			@RequestBody UpdateSubmittalDef updateSubmittalDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateSubmittal");
		//
		Long submittalid = updateSubmittalDef.getSubmittalid();
		String status = updateSubmittalDef.getStatus();
		String internalnotes = updateSubmittalDef.getInternalnotes();
		Double salary = updateSubmittalDef.getSalary();
		Integer feetype = updateSubmittalDef.getFeetype();
		Double fee = updateSubmittalDef.getFee();
		Double quotedbillrate = updateSubmittalDef.getQuotedbillrate();
		Double agreedbillrate = updateSubmittalDef.getAgreedbillrate();
		Double payrate = updateSubmittalDef.getPayrate();
		String payratecurrency = updateSubmittalDef.getPayratecurrency();
		String payrateunit = updateSubmittalDef.getPayrateunit();
		Boolean corp2corp = updateSubmittalDef.getCorp2corp();
		Date agreedon = updateSubmittalDef.getAgreedon();
		Date interviewdate = updateSubmittalDef.getInterviewdate();
		Date internalrejectdate = updateSubmittalDef.getInternalrejectdate();
		Date externalrejectdate = updateSubmittalDef.getExternalrejectdate();
		//
		return submittalService.updateSubmittal(jobDivaSession, submittalid, status, internalnotes, salary, feetype, fee, quotedbillrate, agreedbillrate, payrate, payratecurrency, payrateunit, corp2corp, agreedon, interviewdate, internalrejectdate,
				externalrejectdate);
	}
}

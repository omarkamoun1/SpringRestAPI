package com.jobdiva.api.controller.jobdivaapi;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.service.JobService;
import com.jobdiva.api.service.ResumeService;
import com.jobdiva.api.service.ProxyAPIService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "JobDiva API", description = "REST API")
public class JobDivaController extends AbstractJobDivaController {
	
	//
	@Autowired
	ResumeService	resumeService;
	//
	@Autowired
	JobService		jobService;
	//
	@Autowired
	ProxyAPIService	proxyAPIService;
	
	@RequestMapping(value = "/uploadResume", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Upload Resume")
	public Long uploadResume( //
			//
			@ApiParam(value = "File name", required = true) //
			@RequestParam(required = true) String filename, //
			//
			@ApiParam(value = "Resume file content", required = false) //
			@RequestParam(required = false) byte[] filecontent, //
			//
			@ApiParam(value = "Alternate text resume", required = true) //
			@RequestParam(required = true) String textfile, //
			//
			@ApiParam(value = "Candidate ID. If provided, the resume would be added under this candidate. Otherwise, the system will try to match the resume to an existing candidate based on profile in the resume, or create a new candidate record if there is no match", required = false) //
			@RequestParam(required = false) Long candidateid, //
			//
			@ApiParam(value = "Resume Source. This allows to tag the resume with a particular source in JobDiva. The ID can be retrieved from the JobDiva interface under the ‘Manage Resume Sources’", required = false) //
			@RequestParam(required = false) Integer resumesource, //
			//
			@ApiParam(value = "Recruiter ID", required = false) //
			@RequestParam(required = false) Long recruiterid //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return resumeService.uploadResume(jobDivaSession, filename, filecontent, textfile, candidateid, resumesource, recruiterid);
		//
	}
	
	@RequestMapping(value = "/uploadCandidateAttachment", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Upload Candidate Attachment")
	public Long uploadCandidateAttachment( //
			//
			@ApiParam(value = "Candidate internal JobDiva ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Nickname of the uploaded attachment file", required = true) //
			@RequestParam(required = true) String name, //
			//
			@ApiParam(value = "Actual file name with file extension (.pdf, for example)", required = true) //
			@RequestParam(required = true) String filename, //
			//
			@ApiParam(value = "Uploaded file in base64 binary", required = true) //
			@RequestParam(required = true) byte[] filecontent, //
			//
			@ApiParam(value = "The type of the attachment", required = true) //
			@RequestParam(required = true) Integer attachmenttype, //
			//
			@ApiParam(value = "Additional description of the file", required = false) //
			@RequestParam(required = false) String description //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return resumeService.uploadCandidateAttachment(jobDivaSession, candidateid, name, filename, filecontent, attachmenttype, description);
		//
	}
	
	@RequestMapping(value = "/createJobApplication", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Job Application")
	public Integer createJobApplication( //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Job ID", required = true) //
			@RequestParam(required = true) Long jobid, //
			//
			@ApiParam(value = "Date Applied Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date dateapplied, //
			//
			@ApiParam(value = "Resume source. This allows to tag the resume with a particular source in JobDiva. The ID can be retrieved from the JobDiva interface under the ‘Manage Resume Sources’", required = false) //
			@RequestParam(required = false) Integer resumesource, //
			//
			@ApiParam(value = "Global ID", required = false) //
			@RequestParam(required = false) String globalid //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return jobService.createJobApplication(jobDivaSession, candidateid, jobid, dateapplied, resumesource, globalid);
		//
	}
	
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "headers", required = false, allowMultiple = true, dataType = "ProxyHeader"), //
			@ApiImplicitParam(name = "parameters", required = false, allowMultiple = true, dataType = "ProxyParameter") //
	})
	@RequestMapping(value = "/proxyAPI", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Proxy API")
	public Response proxyAPI( //
			//
			@ApiParam(value = "Method", required = true) //
			@RequestParam(required = true) String method, //
			//
			@ApiParam(value = "Url", required = true) //
			@RequestParam(required = true) String url, //
			//
			@ApiParam(value = "Headers", required = false, type = "ProxyHeader", allowMultiple = true) //
			@RequestParam(required = false) ProxyHeader[] headers, //
			//
			@ApiParam(value = "Parameters", required = false, type = "ProxyParameter", allowMultiple = true) //
			@RequestParam(required = false) ProxyParameter[] parameters, //
			//
			//
			@ApiParam(value = "Body", required = false) //
			@RequestParam(required = false) String body //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return proxyAPIService.proxyAPI(jobDivaSession, method, url, headers, parameters, body);
		//
	}
	
	
}

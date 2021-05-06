package com.jobdiva.api.controller.jobdivaapi;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.CandidateAttachment;
import com.jobdiva.api.model.ContactAttachment;
import com.jobdiva.api.model.UploadResume;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.proxy.ProxyHeader;
import com.jobdiva.api.model.proxy.ProxyParameter;
import com.jobdiva.api.model.proxy.Response;
import com.jobdiva.api.service.JobDivaSessionService;
import com.jobdiva.api.service.JobService;
import com.jobdiva.api.service.OktaService;
import com.jobdiva.api.service.ProxyAPIService;
import com.jobdiva.api.service.ResumeService;
import com.jobdiva.api.utils.DateUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "JobDiva API", description = "REST API")
public class JobDivaController extends AbstractJobDivaController {
	
	//
	@Autowired
	ResumeService			resumeService;
	//
	@Autowired
	JobService				jobService;
	//
	@Autowired
	ProxyAPIService			proxyAPIService;
	//
	@Autowired
	OktaService				oktaService;
	//
	@Autowired
	JobDivaSessionService	jobDivaSessionService;
	
	@ApiIgnore
	@RequestMapping(value = "/linkedInUploadResume", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Upload Resume")
	public Long linkedInUploadResume( //
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
			@ApiParam(value = "Resume Source. This allows to tag the resume with a particular source in JobDiva. The ID can be retrieved from the JobDiva interface under the ‘Manage Resume Sources’", required = true) //
			@RequestParam(required = true) Integer resumesource, //
			//
			@ApiParam(value = "Recruiter ID", required = false) //
			@RequestParam(required = false) Long recruiterid, //
			//
			@ApiParam(value = "Resume Date Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date resumeDate //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		// jobDivaSession.checkForAPIPermission("uploadResume");
		//
		return resumeService.uploadResume(jobDivaSession, filename, filecontent, textfile, candidateid, resumesource, recruiterid, resumeDate);
		//
	}
	
	//
	@RequestMapping(value = "/uploadResume", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Upload Resume")
	public Long uploadResume( //
			//
			@ApiParam(value = //
			" filename : File Name \r\n"//
					+ " filecontent : Resume file content \r\n"//
					+ " textfile : Alternate text resume \r\n"//
					+ " candidateid : If provided, the resume would be added under this candidate. Otherwise, the system will try to match the resume to an existing candidate based on profile in the resume, or create a new candidate record if there is no match \r\n" //
					+ " resumesource: This allows to tag the resume with a particular source  in JobDiva. The ID can be retrieved from the JobDiva interface under the  ‘Manage Resume Sources’ \r\n" //
					+ " recruiterid : Recruiter ID \r\n" //
					+ " resumeDate : String Format [MM/dd/yyyy HH:mm:ss] ") //
			@RequestBody UploadResume uploadResume //
	//
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("uploadResume");
		//
		String filename = uploadResume.getFilename();
		byte[] filecontent = uploadResume.getFilecontent();
		String textfile = uploadResume.getTextfile();
		Long candidateid = uploadResume.getCandidateid();
		Integer resumesource = uploadResume.getResumesource();
		Long recruiterid = uploadResume.getRecruiterid();
		//
		Date resumeDate = DateUtils.toPropertyDate(uploadResume.getResumeDate());
		//
		return resumeService.uploadResume(jobDivaSession, filename, filecontent, textfile, candidateid, resumesource, recruiterid, resumeDate);
		//
	}
	
	@RequestMapping(value = "/uploadCandidateAttachment", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Upload Candidate Attachment")
	public Long uploadCandidateAttachment( //
			//
			@ApiParam(value = //
			"candidateid     : Candidate internal JobDiva ID \r\n" //
					+ "name           : Nickname of the uploaded attachment file \r\n" //
					+ "filename       : Actual file name with file extension (.pdf, for example) \r\n" //
					+ "filecontent    : Uploaded file in base64 binary \r\n" //
					+ "attachmenttype : The type of the attachment \r\n" //
					+ "description    : Additional description of the file \r\n" //
					+ "") //
			@RequestBody CandidateAttachment candidateAttachment
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("uploadCandidateAttachment");
		//
		Long candidateid = candidateAttachment.getCandidateid(); //
		String name = candidateAttachment.getName();//
		String filename = candidateAttachment.getFilename();//
		byte[] filecontent = candidateAttachment.getFilecontent(); //
		Integer attachmenttype = candidateAttachment.getAttachmenttype();//
		String description = candidateAttachment.getDescription();//
		//
		return resumeService.uploadCandidateAttachment(jobDivaSession, candidateid, name, filename, filecontent, attachmenttype, description);
		//
	}
	
	// @ApiIgnore
	@RequestMapping(value = "/uploadContactAttachment", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Upload Contact Attachment")
	public Long uploadContactAttachment( //
			//
			@ApiParam(value = //
			"contactId 				: Contact internal JobDiva ID \r\n"//
					+ "documentname 		: Nickname of the uploaded attachment file\r\n"//
					+ "filename 			: Actual file name with file extension (.pdf, for example)\r\n"//
					+ "filecontent 			: Uploaded file in base64 binary \r\n"//
					+ "urllink 				: URL link \r\n"//
					+ "designedform 		: Designed form \r\n"//
					+ "attachmenttype 		: The type of the attachment \r\n" //
					+ "internaldescription  : Internal description of the file \r\n"//
					+ "expirationdate 		: Expiration date of the attachment Format [MM/dd/yyyy HH:mm:ss] \r\n" //
					+ "isonboardingdoc 		: If the attachment is an On-boarding document.\r\n Set to be true, the following parameters will be considered \r\n" //
					+ "ismandatory 			: If the attachment is mandatory \r\n"//
					+ "requirereturn 		: If the attachment must be completed and returned \r\n"//
					+ "isreadonly 			: If the attachment is read only \r\n"//
					+ "sendto 				: Where the attachment will be sent to. Valid values include:\r\n "//
					+ " • 0: Will be sent to Candidate\r\n "//
					+ " • 1: Will be sent to Supplier\r\n "//
					+ " • 2: For Internal Use Only \r\n"//
					+ "ismedicaldoc 		: If the attachment is medical-related document \r\n"//
					+ "portalinstruction 	: Portal instruction \r\n"//
					+ "") //
			@RequestBody ContactAttachment contactAttachment
	//
	//
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("uploadContactAttachment");
		//
		Long contactId = contactAttachment.getContactId();
		String documentname = contactAttachment.getDocumentname();
		String filename = contactAttachment.getFilename();
		byte[] filecontent = contactAttachment.getFilecontent();
		String urllink = contactAttachment.getUrllink();
		String designedform = contactAttachment.getDesignedform();
		Integer attachmenttype = contactAttachment.getAttachmenttype();
		String internaldescription = contactAttachment.getInternaldescription();
		//
		//
		Date expirationdate = DateUtils.toPropertyDate(contactAttachment.getExpirationdate());
		Boolean isonboardingdoc = contactAttachment.getIsonboardingdoc();
		Boolean ismandatory = contactAttachment.getIsmandatory();
		Boolean requirereturn = contactAttachment.getRequirereturn();
		Boolean isreadonly = contactAttachment.getIsreadonly();
		Integer sendto = contactAttachment.getSendto();
		Boolean ismedicaldoc = contactAttachment.getIsmedicaldoc();
		String portalinstruction = contactAttachment.getPortalinstruction();
		//
		//
		return resumeService.uploadContactAttachment(jobDivaSession, contactId, documentname, filename, filecontent, urllink, designedform, attachmenttype, internaldescription, expirationdate, isonboardingdoc, ismandatory, requirereturn, isreadonly,
				sendto, ismedicaldoc, portalinstruction);
		//
	}
	
	@RequestMapping(value = "/createJobApplication", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Job Application")
	public Boolean createJobApplication( //
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
		jobDivaSession.checkForAPIPermission("createJobApplication");
		//
		return jobService.createJobApplication(jobDivaSession, candidateid, jobid, dateapplied, resumesource, globalid);
		//
	}
	
	@ApiIgnore
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
		return proxyAPIService.proxyAPI(method, url, headers, parameters, body);
		//
	}
	
	@ApiIgnore
	@ApiOperation(value = "Okta AccessToken Verifier")
	@RequestMapping(value = "/oktaAccessTokenVerifier", method = RequestMethod.GET, produces = "application/json")
	public Boolean oktaAccessTokenVerifier( //
			//
			String oktaDomain, String oktaAud, String oktaToken, String email //
	//
	) throws Exception {
		//
		//
		return oktaService.oktaAccessTokenVerifier(oktaDomain, oktaAud, oktaToken, email);
		//
	}
}

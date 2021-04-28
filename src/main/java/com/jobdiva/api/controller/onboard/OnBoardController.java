package com.jobdiva.api.controller.onboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.OnboardingCandidateDocument;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.onboard.HireType;
import com.jobdiva.api.model.onboard.InterviewSchedule;
import com.jobdiva.api.model.onboard.OnBoardDocument;
import com.jobdiva.api.model.onboard.OnBoardLocationPackage;
import com.jobdiva.api.model.onboard.OnBoardPackage;
import com.jobdiva.api.service.OnBoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Joseph Chidiac
 *
 */
@CrossOrigin
@RestController
@RequestMapping({ "/api/onboard/" })
@Api(value = "Onboarding API", description = "REST API Used For Onboarding")
@ApiIgnore
public class OnBoardController extends AbstractJobDivaController {
	
	@Autowired
	OnBoardService onBoardService;
	
	@ApiOperation("Get Package List")
	@RequestMapping(value = { "/getPackageList" }, method = { RequestMethod.GET }, produces = { "application/json" })
	public List<HireType> getPackageList() throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return this.onBoardService.getPackageList(jobDivaSession);
	}
	
	@ApiOperation("Get packages detail")
	@RequestMapping(value = { "/getPackagesDetail" }, method = { RequestMethod.GET }, produces = { "application/json" })
	public List<OnBoardPackage> getPackagesDetail(
	// @ApiParam(value = "Hire Type Id", required = true) @RequestParam(required
	// = true) Long hireTypeId
	) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return this.onBoardService.getPackagesDetail(jobDivaSession);
	}
	
	@ApiOperation("Get Documents By Company")
	@RequestMapping(value = { "/getDocumentsByCompany" }, method = { RequestMethod.GET }, produces = { "application/json" })
	public List<OnBoardDocument> getDocumentsByCompany(@ApiParam(value = "Company ID", required = true) @RequestParam(required = true) Long companyId) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return this.onBoardService.getDocumentsByCompany(jobDivaSession, companyId);
	}
	
	@ApiOperation("Get Documents By Contact")
	@RequestMapping(value = { "/getDocumentsByContact" }, method = { RequestMethod.GET }, produces = { "application/json" })
	public List<OnBoardDocument> getDocumentsByContact(@ApiParam(value = "Client ID", required = true) @RequestParam(required = true) Long clientId) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return this.onBoardService.getDocumentsByContact(jobDivaSession, clientId);
	}
	
	@ApiOperation("Get packages by job location")
	@RequestMapping(value = { "/getJobLocationDocuments" }, method = { RequestMethod.GET }, produces = { "application/json" })
	public List<OnBoardLocationPackage> getJobLocationDocuments(@ApiParam(value = "Job Id", required = true) @RequestParam(required = true) Long jobId) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return this.onBoardService.getJobLocationDocuments(jobDivaSession, jobId);
	}
	
	@ApiOperation("Save Onboarding")
	@RequestMapping(value = { "/saveOnboarding" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Long saveOnboarding(@RequestBody InterviewSchedule interviewSchedule) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return this.onBoardService.saveOnboarding(jobDivaSession, interviewSchedule);
	}
	
	@RequestMapping(value = "/uploadCandidateOnboardingDocument", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Upload Candidate Onboarding Document")
	public Long uploadCandidateOnboardingDocument( //
			//
			@ApiParam(value = //
			"onboardingId     : Onboarding Id \r\n" //
					+ "filename       : Actual file name with file extension (.pdf, for example) \r\n" //
					+ "filecontent    : Uploaded file in base64 binary \r\n" //
					+ "") //
			@RequestBody OnboardingCandidateDocument OnboardingCandidateDocument
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("uploadCandidateOnboardingDocument");
		//
		return onBoardService.uploadCandidateOnboardingDocument(jobDivaSession, OnboardingCandidateDocument);
		//
	}
}

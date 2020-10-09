package com.jobdiva.api.controller.bidata;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.bidata.BiData;
import com.jobdiva.api.service.BIDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/bi/")
@Api(value = "BIDATA API", description = "REST API Used For BIDATA")
public class BIDataController extends AbstractJobDivaController {
	
	//
	@Autowired
	BIDataService biDataService;
	
	//
	@ApiOperation(value = "Bi Data Information")
	@RequestMapping(value = "/getBIData", method = RequestMethod.GET, produces = "application/json")
	public BiData getBIData(//
			//
			//
			@ApiParam(value = "MetricName", required = true, type = "String") //
			@RequestParam(required = true) String metricName, //
			//
			@ApiParam(value = "From Date Format [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format [MM/dd/yyyy HH:mm:ss]", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Parameters", required = false, type = "String") //
			@RequestParam(required = false) String[] parameters //
	//
	) throws Exception {//
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, metricName, fromDate, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/CompanyDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CompanyDetail(//
			//
			@ApiParam(value = "Company Id", required = true) //
			@RequestParam(required = true) Long companyId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		paramLits.add(companyId.toString());
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Company Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CompaniesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CompaniesDetail(//
			//
			@ApiParam(value = "Company Id (s)", required = true) //
			@RequestParam(required = true) Long[] companyIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < companyIds.length; i++) {
			paramLits.add(companyIds[i].toString());
		}
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Companies Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CompanyOwnersDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CompanyOwnersDetail(//
			//
			@ApiParam(value = "Company Id", required = true) //
			@RequestParam(required = true) Long companyId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { companyId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Company Owners Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CompaniesOwnersDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CompaniesOwnersDetail(//
			//
			@ApiParam(value = "Company Id (s)", required = true) //
			@RequestParam(required = true) Long[] companyIds //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < companyIds.length; i++) {
			paramLits.add(companyIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Companies Owners Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CompaniesTypesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CompaniesTypesDetail(//
			//
			@ApiParam(value = "Company Id (s)", required = true) //
			@RequestParam(required = true) Long[] companyIds //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < companyIds.length; i++) {
			paramLits.add(companyIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Companies Types Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CompanyAddressesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CompanyAddressesDetail(//
			//
			@ApiParam(value = "Company Id", required = true) //
			@RequestParam(required = true) Long companyId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { companyId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Company Addresses Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ContactDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactDetail(//
			//
			@ApiParam(value = "Contact Id", required = true) //
			@RequestParam(required = true) Long contacId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		paramLits.add(contacId.toString());
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Contact Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/V2ContactDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData V2ContactDetail(//
			//
			@ApiParam(value = "Contact Id", required = true) //
			@RequestParam(required = true) Long contacId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		paramLits.add(contacId.toString());
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "V2 Contact Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ContactsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactsDetail(//
			//
			@ApiParam(value = "Contact Id (s)", required = true) //
			@RequestParam(required = true) Long[] contactIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < contactIds.length; i++) {
			paramLits.add(contactIds[i].toString());
		}
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Contacts Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/V2ContactsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData V2ContactsDetail(//
			//
			@ApiParam(value = "Contact Id (s)", required = true) //
			@RequestParam(required = true) Long[] contactIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < contactIds.length; i++) {
			paramLits.add(contactIds[i].toString());
		}
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		//
		return biDataService.getBIData(jobDivaSession, "V2 Contacts Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ContactOwnersDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactOwnersDetail(//
			//
			@ApiParam(value = "Contact Id", required = true) //
			@RequestParam(required = true) Long contacId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { contacId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Contact Owners Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ContactsOwnersDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactsOwnersDetail(//
			//
			@ApiParam(value = "Contact Id (s)", required = true) //
			@RequestParam(required = true) Long[] contactIds //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < contactIds.length; i++) {
			paramLits.add(contactIds[i].toString());
		}
		//
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		//
		return biDataService.getBIData(jobDivaSession, "Contacts Owners Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ContactsTypesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactsTypesDetail(//
			//
			@ApiParam(value = "Contact Id (s)", required = true) //
			@RequestParam(required = true) Long[] contactIds //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < contactIds.length; i++) {
			paramLits.add(contactIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Contacts Types Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ContactAddressesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactAddressesDetail(//
			//
			@ApiParam(value = "Contact Id", required = true) //
			@RequestParam(required = true) Long contacId //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { contacId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Contact Addresses Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData JobDetail(//
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		paramLits.add(jobId.toString());
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		//
		return biDataService.getBIData(jobDivaSession, "Job Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData JobsDetail(//
			//
			@ApiParam(value = "Job Id (s)", required = true) //
			@RequestParam(required = true) Long[] jobIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < jobIds.length; i++) {
			paramLits.add(jobIds[i].toString());
		}
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Jobs Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobContactsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData JobContactsDetail(//
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Job Contacts Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobsContactsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData JobsContactsDetail(//
			//
			@ApiParam(value = "Job Id (s)", required = true) //
			@RequestParam(required = true) Long[] jobIds //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < jobIds.length; i++) {
			paramLits.add(jobIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Jobs Contacts Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobUsersDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData JobUsersDetail(//
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Job Users Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobsUsersDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData JobsUsersDetail(//
			//
			@ApiParam(value = "Job Id (s)", required = true) //
			@RequestParam(required = true) Long[] jobIds //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < jobIds.length; i++) {
			paramLits.add(jobIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Jobs Users Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobApplicantsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData JobApplicantsDetail(//
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Job Applicants Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateJobApplicationDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateJobApplicationDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString(), jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Job Application Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobSubmittalsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData JobSubmittalsDetail(//
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Job Submittals Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobsSubmittalsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData JobsSubmittalsDetail(//
			//
			@ApiParam(value = "Job Id (s)", required = true) //
			@RequestParam(required = true) Long[] jobIds //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < jobIds.length; i++) {
			paramLits.add(jobIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Jobs Submittals Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateSubmittalsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateSubmittalsDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateid //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateid.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Submittals Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ResumeDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ResumeDetail(//
			//
			@ApiParam(value = "Resume Id", required = true) //
			@RequestParam(required = true) Long resumeId //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { resumeId.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Resume Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/UsersTasksDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData UsersTasksDetail(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Id (s)", required = true) //
			@RequestParam(required = true) Long[] userds//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < userds.length; i++) {
			paramLits.add(userds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Users Tasks Detail", fromDate, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/EventsAttendeesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData EventsAttendeesDetail(//
			//
			@ApiParam(value = "Event Id (s)", required = true) //
			@RequestParam(required = true) Long[] eventids //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < eventids.length; i++) {
			paramLits.add(eventids[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Events Attendees Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		paramLits.add(candidateId.toString());
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidatesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidatesDetail(//
			//
			@ApiParam(value = "Candidate Id (s)", required = true) //
			@RequestParam(required = true) Long[] candidateIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < candidateIds.length; i++) {
			paramLits.add(candidateIds[i].toString());
		}
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidates Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateQualificationsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateQualificationsDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Category Names", required = false) //
			@RequestParam(required = false) String[] categoryNames //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		paramLits.add(candidateId.toString());
		//
		if (categoryNames != null) {
			for (int i = 0; i < categoryNames.length; i++) {
				paramLits.add(categoryNames[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Qualifications Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidatesQualificationsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidatesQualificationsDetail(//
			//
			@ApiParam(value = "Candidate Id (s)", required = true) //
			@RequestParam(required = true) Long[] candidateIds, //
			//
			@ApiParam(value = "Category Names", required = false) //
			@RequestParam(required = false) String[] categoryNames //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < candidateIds.length; i++) {
			paramLits.add(candidateIds[i].toString());
		}
		//
		if (categoryNames != null) {
			for (int i = 0; i < categoryNames.length; i++) {
				paramLits.add(categoryNames[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidates Qualifications Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateAvailabilityDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateAvailabilityDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Availability Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidatesAvailabilityDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidatesAvailabilityDetail(//
			//
			@ApiParam(value = "Candidate Id (s)", required = true) //
			@RequestParam(required = true) Long[] candidateIds //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < candidateIds.length; i++) {
			paramLits.add(candidateIds[i].toString());
		}
		//
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidates Availability Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateResumesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateResumesDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Resumes Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidatesResumesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidatesResumesDetail(//
			//
			@ApiParam(value = "Candidate Id (s)", required = true) //
			@RequestParam(required = true) Long[] candidateIds //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < candidateIds.length; i++) {
			paramLits.add(candidateIds[i].toString());
		}
		//
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidates Resumes Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateResumeSourceDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateResumeSourceDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Resume Source Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateOnBoardingDocumentDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateOnBoardingDocumentDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Interview Id", required = true) //
			@RequestParam(required = true) Long interviewId, //
			//
			@ApiParam(value = "File Name", required = true) //
			@RequestParam(required = true) String fileName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString(), interviewId.toString(), fileName };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate On-Boarding Document Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateAttachmentDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateAttachmentDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "File Name", required = true) //
			@RequestParam(required = true) String fileName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString(), fileName };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Attachment Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CompanyNoteDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CompanyNoteDetail(//
			//
			@ApiParam(value = "Note Id", required = true) //
			@RequestParam(required = true) Long noteId //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { noteId.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Company Note Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CompanyNotesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CompanyNotesDetail(//
			//
			@ApiParam(value = "Note Id (s)", required = true) //
			@RequestParam(required = true) Long[] noteIds //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < noteIds.length; i++) {
			paramLits.add(noteIds[i].toString());
		}
		//
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Company Notes Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ContactNoteDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactNoteDetail(//
			//
			@ApiParam(value = "Note Id", required = true) //
			@RequestParam(required = true) Long noteId //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { noteId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Contact Note Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ContactNotesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactNotesDetail(//
			//
			@ApiParam(value = "Note Id (s)", required = true) //
			@RequestParam(required = true) Long[] noteIds //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < noteIds.length; i++) {
			paramLits.add(noteIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Contact Notes Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateNoteDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateNoteDetail(//
			//
			@ApiParam(value = "Note Id", required = true) //
			@RequestParam(required = true) Long noteId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { noteId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Note Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateNotesDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateNotesDetail(//
			//
			@ApiParam(value = "Note Id (s)", required = true) //
			@RequestParam(required = true) Long[] noteIds //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < noteIds.length; i++) {
			paramLits.add(noteIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Notes Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateEEODetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateEEODetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate EEO Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/SubmittalDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData SubmittalDetail(//
			//
			@ApiParam(value = "Interview Schedule Id", required = true) //
			@RequestParam(required = true) Long interviewScheduleId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		paramLits.add(interviewScheduleId.toString());
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Submittal Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/SubmittalsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData SubmittalsDetail(//
			//
			@ApiParam(value = "Interview Schedule Id(s)", required = true) //
			@RequestParam(required = true) Long[] interviewScheduleIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < interviewScheduleIds.length; i++) {
			paramLits.add(interviewScheduleIds[i].toString());
		}
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Submittals Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/BillingRecordDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData BillingRecordDetail(//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			//
			@ApiParam(value = "Record Id", required = true) //
			@RequestParam(required = true) Long recordId //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString(), recordId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Billing Record Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/BillingRecordsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData BillingRecordsDetail(//
			//
			@ApiParam(value = "Employee Id(s)", required = true) //
			@RequestParam(required = true) Long[] employeeIds //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < employeeIds.length; i++) {
			paramLits.add(employeeIds[i].toString());
		}
		//
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Billing Records Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/EmployeeBillingRecordsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData EmployeeBillingRecordsDetail(//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Employee Billing Records Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/SalaryRecordDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData SalaryRecordDetail(//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			//
			@ApiParam(value = "Record Id", required = true) //
			@RequestParam(required = true) Long recordId //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString(), recordId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Salary Record Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/SalaryRecordsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData SalaryRecordsDetail(//
			//
			@ApiParam(value = "Employee Id(s)", required = true) //
			@RequestParam(required = true) Long[] employeeIds //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < employeeIds.length; i++) {
			paramLits.add(employeeIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Salary Records Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/EmployeeSalaryRecordsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData EmployeeSalaryRecordsDetail(//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Employee Salary Records Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/InvoiceDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData InvoiceDetail(//
			//
			@ApiParam(value = "Invoice Id", required = true) //
			@RequestParam(required = true) Long invoiceId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { invoiceId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Invoice Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/PlacementDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData PlacementDetail(//
			//
			@ApiParam(value = "Invoice Id", required = true) //
			@RequestParam(required = true) Long invoiceId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { invoiceId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Placement Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/EmployeeTimesheetImageDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData EmployeeTimesheetImageDetail(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			@ApiParam(value = "biiling Record Id", required = true) //
			@RequestParam(required = true) Long billiingRecordId //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString(), billiingRecordId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Employee Timesheet Image Detail", fromDate, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/EmployeeTimesheetImagebyTimecardIDDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData EmployeeTimesheetImagebyTimecardIDDetail(//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			@ApiParam(value = "Time Card Id", required = true) //
			@RequestParam(required = true) Long timeCardId //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString(), timeCardId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Employee Timesheet Image by Timecard ID Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/TimesheetDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData TimesheetDetail(//
			@ApiParam(value = "Recruiter TeamI Id", required = true) //
			@RequestParam(required = true) Long recruiterTeamId, //
			//
			@ApiParam(value = "Exist Employee In Billing Record", required = false) //
			@RequestParam(required = false) Boolean existEmployeeInBillingRecord //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = existEmployeeInBillingRecord != null ? new String[] { recruiterTeamId.toString(), Boolean.toString(existEmployeeInBillingRecord) } : new String[] { recruiterTeamId.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Timesheet Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ContactHotlistDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactHotlistDetail(//
			@ApiParam(value = "Hotlist Id", required = true) //
			@RequestParam(required = true) Long hotlistId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { hotlistId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Contact Hotlist Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateHotlistDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateHotlistDetail(//
			@ApiParam(value = "Hotlist Id", required = true) //
			@RequestParam(required = true) Long hotlistId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { hotlistId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Hotlist Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/UserContactHotlistDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData UserContactHotlistDetail(//
			@ApiParam(value = "Recruiter Id", required = true) //
			@RequestParam(required = true) Long recruiterId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { recruiterId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "User Contact Hotlist Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/UserCandidateHotlistDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData UserCandidateHotlistDetail(//
			@ApiParam(value = "Recruiter Id", required = true) //
			@RequestParam(required = true) Long recruiterId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { recruiterId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "User Candidate Hotlist Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ContactHotlistsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactHotlistsDetail(//
			@ApiParam(value = "Hotlist Id (s)", required = true) //
			@RequestParam(required = true) Long[] hotlistIds //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < hotlistIds.length; i++) {
			paramLits.add(hotlistIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Contact Hotlists Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateHotlistsDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateHotlistsDetail(//
			@ApiParam(value = "Hotlist Id (s)", required = true) //
			@RequestParam(required = true) Long[] hotlistIds //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < hotlistIds.length; i++) {
			paramLits.add(hotlistIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Hotlists Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateNotesListDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateNotesListDetail(//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Notes List Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateHRDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateHRDetail(//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate HR Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidatesHRDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidatesHRDetail(//
			@ApiParam(value = "Candidate Id(s)", required = true) //
			@RequestParam(required = true) Long[] candidateIds //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		for (int i = 0; i < candidateIds.length; i++) {
			paramLits.add(candidateIds[i].toString());
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidates HR Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/EmailDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData EmailDetail(//
			@ApiParam(value = "Id", required = true) //
			@RequestParam(required = true) Long id //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { id.toString() };
		// //
		return biDataService.getBIData(jobDivaSession, "Email Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ADPProfileDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData ADPProfileDetail(//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "ADP Profile Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateAttributeDetail", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateAttributeDetail(//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Attribute Detail", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobsListbyUserbyStatus", method = RequestMethod.GET, produces = "application/json")
	public BiData JobsListbyUserbyStatus(//
			//
			@ApiParam(value = "Recruiter Id", required = true) //
			@RequestParam(required = true) Long recruiterId, //
			//
			@ApiParam(value = "Job Status", required = true) //
			@RequestParam(required = true) Integer jobStatus //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { recruiterId.toString(), jobStatus.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Jobs List by User by Status", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/PortalJobsList", method = RequestMethod.GET, produces = "application/json")
	public BiData PortalJobsList(//
			@ApiParam(value = "Company Id", required = true) //
			@RequestParam(required = true) Long companyId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { companyId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Portal Jobs List", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobsDashboardbyUser", method = RequestMethod.GET, produces = "application/json")
	public BiData JobsDashboardbyUser(//
			@ApiParam(value = "Recruiter Id", required = true) //
			@RequestParam(required = true) Long recruiterId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { recruiterId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Jobs Dashboard by User", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/OpenJobsListbyCompany", method = RequestMethod.GET, produces = "application/json")
	public BiData OpenJobsListbyCompany(//
			@ApiParam(value = "Company Id", required = true) //
			@RequestParam(required = true) Long companyId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { companyId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Open Jobs List by Company", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/OpenJobsListbyContact", method = RequestMethod.GET, produces = "application/json")
	public BiData OpenJobsListbyContact(//
			//
			@ApiParam(value = "Customer Id", required = true) //
			@RequestParam(required = true) Long customerId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { customerId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Open Jobs List by Contact", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/CandidateApplicationRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateApplicationRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Application Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewApprovedBillingRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewApprovedBillingRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Approved Billing Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewApprovedSalaryRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewApprovedSalaryRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Approved Salary Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/UpdatedApprovedBillingRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData UpdatedApprovedBillingRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Updated Approved Billing Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/UpdatedApprovedSalaryRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData UpdatedApprovedSalaryRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Updated Approved Salary Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewInvoices", method = RequestMethod.GET, produces = "application/json")
	public BiData NewInvoices(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Invoices", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/VoidedInvoices", method = RequestMethod.GET, produces = "application/json")
	public BiData VoidedInvoices(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Voided Invoices", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewApprovedTimesheets", method = RequestMethod.GET, produces = "application/json")
	public BiData NewApprovedTimesheets(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Approved Timesheets", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/ApprovedTimesheetsbyWeekEndingDate", method = RequestMethod.GET, produces = "application/json")
	public BiData ApprovedTimesheetsbyWeekEndingDate(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Approved Timesheets by Week Ending Date", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewHires", method = RequestMethod.GET, produces = "application/json")
	public BiData NewHires(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Hires", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/UpdatedHires", method = RequestMethod.GET, produces = "application/json")
	public BiData UpdatedHires(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Updated Hires", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/MergedCompanies", method = RequestMethod.GET, produces = "application/json")
	public BiData MergedCompanies(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Merged Companies", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/MergedContacts", method = RequestMethod.GET, produces = "application/json")
	public BiData MergedContacts(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Merged Contacts", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/MergedJobs", method = RequestMethod.GET, produces = "application/json")
	public BiData MergedJobs(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Merged Jobs", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/MergedCandidates", method = RequestMethod.GET, produces = "application/json")
	public BiData MergedCandidates(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Merged Candidates", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/CandidateResumeSubmittedtoJob", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateResumeSubmittedtoJob(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString(), jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Resume Submitted to Job", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/SubmittalRecordsbyCandidateandJob", method = RequestMethod.GET, produces = "application/json")
	public BiData SubmittalRecordsbyCandidateandJob(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString(), jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Submittal Records by Candidate and Job", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/JobsPosted", method = RequestMethod.GET, produces = "application/json")
	public BiData JobsPosted(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Jobs Posted", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/JobsCurrentlyPosted", method = RequestMethod.GET, produces = "application/json")
	public BiData JobsCurrentlyPosted(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Jobs Currently Posted", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/UsersUpdatedJobsList", method = RequestMethod.GET, produces = "application/json")
	public BiData UsersUpdatedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Users Updated Jobs List", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCompanyRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedCompanyRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Company Records", fromDate, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCompanyNoteRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedCompanyNoteRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Company Note Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedContactRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedContactRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Contact Records", fromDate, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedContactNoteRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedContactNoteRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Contact Note Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedOpportunityRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedOpportunityRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Opportunity Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedJobRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedJobRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Job Records", fromDate, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCandidateRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedCandidateRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Candidate Records", fromDate, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCandidateNoteRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedCandidateNoteRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Candidate Note Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCandidateHRRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedCandidateHRRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Candidate HR Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedEmployeeRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedEmployeeRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Employee Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedActivityRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedActivityRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Activity Records", fromDate, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedTaskRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedTaskRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Task Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedEventRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedEventRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Event Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCandidateQualificationRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedCandidateQualificationRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Candidate Qualification Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/V2NewUpdatedContactRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData V2NewUpdatedContactRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "V2 New/Updated Contact Records", fromDate, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedSubmittalInterviewHireActivityRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedSubmittalInterviewHireActivityRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Submittal/Interview/Hire Activity Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedJobUserRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedJobUserRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Job User Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedBillingRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedBillingRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Billing Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedSalaryRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedSalaryRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Salary Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedApprovedTimesheetRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedApprovedTimesheetRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Approved Timesheet Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedApprovedTimesheetandExpenseRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedApprovedTimesheetandExpenseRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Approved Timesheet and Expense Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCandidateAvailabilityRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedCandidateAvailabilityRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Candidate Availability Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/DeletedCandidateNoteRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData DeletedCandidateNoteRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Candidate Note Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/DeletedEmployeeRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData DeletedEmployeeRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Employee Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/DeletedActivityRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData DeletedActivityRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Activity Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/DeletedCompanyRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData DeletedCompanyRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Company Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/DeletedContactRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData DeletedContactRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Contact Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/DeletedContactNoteRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData DeletedContactNoteRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Contact Note Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/DeletedJobRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData DeletedJobRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Job Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/DeletedCandidateRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData DeletedCandidateRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Candidate Records", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/DeletedTaskRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData DeletedTaskRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Task Records", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/Sage50RedberryNewEmployeeFeed", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData Sage50RedberryNewEmployeeFeed(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Sage 50 Redberry New Employee Feed", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/Sage50RedberryNewTimesheetFeed", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData Sage50RedberryNewTimesheetFeed(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Sage 50 Redberry New Timesheet Feed", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/MISINewUpdatedJobs", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData MISINewUpdatedJobs(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "MISI New/Updated Jobs", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/MISINewUpdatedStarts", method = RequestMethod.GET, produces = "application/json")
	public BiData MISINewUpdatedStarts(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "MISI New/Updated Starts", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewCompaniesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageNewCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Advantage New Companies List", fromDate, toDate, parameters);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedCompaniesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUpdatedCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Companies List", fromDate, toDate, parameters);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedCompaniesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageDeletedCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Companies List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageMergedCompaniesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageMergedCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Merged Companies List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageOwnersUpdatedCompaniesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageOwnersUpdatedCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Owners Updated Companies List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageCompanyTypesUpdatedCompaniesList",
	// method = RequestMethod.GET, produces = "application/json")
	public BiData AdvantageCompanyTypesUpdatedCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Company Types Updated Companies List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewContactsList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageNewContactsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Advantage New Contacts List", fromDate, toDate, parameters);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedContactsList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUpdatedContactsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Contacts List", fromDate, toDate, parameters);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedContactsList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageDeletedContactsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Contacts List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageMergedContactsList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageMergedContactsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Merged Contacts List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageOwnersUpdatedContactsList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageOwnersUpdatedContactsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Owners Updated Contacts List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageIssuedJobsList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageIssuedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Issued Jobs List", fromDate, toDate, parameters);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedJobsList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUpdatedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Jobs List", fromDate, toDate, parameters);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedJobsList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageDeletedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Jobs List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageMergedJobsList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageMergedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Merged Jobs List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUsersUpdatedJobsList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUsersUpdatedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Users Updated Jobs List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewCandidatesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageNewCandidatesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Advantage New Candidates List", fromDate, toDate, parameters);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedCandidatesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUpdatedCandidatesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Candidates List", fromDate, toDate, parameters);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedCandidatesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageDeletedCandidatesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Candidates List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageMergedCandidatesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageMergedCandidatesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Merged Candidates List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageSubmittalInterviewHireActivitiesList",
	// method = RequestMethod.GET, produces = "application/json")
	public BiData AdvantageSubmittalInterviewHireActivitiesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Submittal/Interview/Hire Activities List", fromDate, toDate, parameters);
		//
	}
	
	// @RequestMapping(value =
	// "/AdvantageUpdatedSubmittalInterviewHireActivitiesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUpdatedSubmittalInterviewHireActivitiesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Submittal/Interview/Hire Activities List", fromDate, toDate, parameters);
		//
	}
	
	// @RequestMapping(value =
	// "/AdvantageDeletedSubmittalInterviewHireActivitiesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageDeletedSubmittalInterviewHireActivitiesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Submittal/Interview/Hire Activities List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageCandidateActions", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageCandidateActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Candidate Actions", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageContactActions", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageContactActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Contact Actions", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedCandidateActions", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageDeletedCandidateActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Candidate Actions", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedContactActions", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUpdatedContactActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Contact Actions", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedContactActions", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageDeletedContactActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Contact Actions", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageJobActions", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageJobActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Job Actions", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedJobActions", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageDeletedJobActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Job Actions", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewEmployee", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageNewEmployee(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage New Employee", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedEmployee", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUpdatedEmployee(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Employee", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedEmployee", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageDeletedEmployee(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Employee", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageSalesPipeline", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageSalesPipeline(//
			@ApiParam(value = "Type Name", required = false) //
			@RequestParam(required = false) String typeName //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = typeName != null ? new String[] { typeName } : null;
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Sales Pipeline", null, null, parameters);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUsersList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUsersList(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Users List", null, null, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUserGroupLists", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUserGroupLists(//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage User Group Lists", null, null, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewCandidateReferenceCheck", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageNewCandidateReferenceCheck(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage New Candidate Reference Check", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedCandidateReferenceCheck",
	// method = RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUpdatedCandidateReferenceCheck(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Candidate Reference Check", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedCandidateReferenceCheck",
	// method = RequestMethod.GET, produces = "application/json")
	public BiData AdvantageDeletedCandidateReferenceCheck(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Candidate Reference Check", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewAIF", method = RequestMethod.GET,
	// produces = "application/json")
	public BiData AdvantageNewAIF(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage New AIF", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedAIF", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageUpdatedAIF(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated AIF", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedAIF", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageDeletedAIF(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted AIF", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageAccessLog", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageAccessLog(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Access Log", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AdvantageBIDataCheck", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AdvantageBIDataCheck(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage BIData Check", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/TCMLNewHires", method = RequestMethod.GET,
	// produces = "application/json")
	public BiData TCMLNewHires(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "TCML New Hires", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/TCMLUpdatedHires", method = RequestMethod.GET,
	// produces = "application/json")
	public BiData TCMLUpdatedHires(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "TCML Updated Hires", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/TCMLEEO", method = RequestMethod.GET, produces
	// = "application/json")
	public BiData TCMLEEO(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "TCML EEO", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/ACSIFeed", method = RequestMethod.GET, produces
	// = "application/json")
	public BiData ACSIFeed(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ACSI Feed", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AditiCurrentPlacementActivities", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AditiCurrentPlacementActivities(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Aditi Current Placement Activities", null, null, null);
		//
	}
	
	// @RequestMapping(value = "/AditiCurrentHeadCount", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AditiCurrentHeadCount(//
			//
			@ApiParam(value = "company Id", required = true) //
			@RequestParam(required = true) Long companyId, //
			//
			@ApiParam(value = "Sales Person Id(s)", required = false) //
			@RequestParam(required = false) Long[] salesPersonIds //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		paramLits.add(companyId.toString());
		//
		if (salesPersonIds != null) {
			for (int i = 0; i < salesPersonIds.length; i++) {
				paramLits.add(salesPersonIds[i].toString());
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Aditi Current Head Count", null, null, parameters);
		//
	}
	
	// @RequestMapping(value = "/ICSOFFICEVISITS", method = RequestMethod.GET,
	// produces = "application/json")
	public BiData ICSOFFICEVISITS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_OFFICE_VISITS", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/ICSSUBMITTALS", method = RequestMethod.GET,
	// produces = "application/json")
	public BiData ICSSUBMITTALS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_SUBMITTALS", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/ICSINTERVIEWS", method = RequestMethod.GET,
	// produces = "application/json")
	public BiData ICSINTERVIEWS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_INTERVIEWS", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/ICSHIRES", method = RequestMethod.GET, produces
	// = "application/json")
	public BiData ICSHIRES(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_HIRES", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/ICSREFERENCES", method = RequestMethod.GET,
	// produces = "application/json")
	public BiData ICSREFERENCES(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_REFERENCES", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/ICSCONTACTS", method = RequestMethod.GET,
	// produces = "application/json")
	public BiData ICSCONTACTS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_CONTACTS", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/ICSCONTACTACTIVITIES", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData ICSCONTACTACTIVITIES(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_CONTACT_ACTIVITIES", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/ICSCOVERS", method = RequestMethod.GET,
	// produces = "application/json")
	public BiData ICSCOVERS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_COVERS", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/ICSJOBREQUISITIONS", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData ICSJOBREQUISITIONS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_JOB_REQUISITIONS", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/LiniumInvoicesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData LiniumInvoicesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Linium Invoices List", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/LiniumActiveEmployeesList", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData LiniumActiveEmployeesList(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Linium Active Employees List", null, null, null);
		//
	}
	
	// @RequestMapping(value =
	// "/LiniumActiveEmployeesListNotExcludedfromWorkterra", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData LiniumActiveEmployeesListNotExcludedfromWorkterra(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Linium Active Employees List Not Excluded from Workterra", null, null, null);
		//
	}
	
	// @RequestMapping(value = "/LiniumEmployeesTerminatedinthePreviousDay",
	// method = RequestMethod.GET, produces = "application/json")
	public BiData LiniumEmployeesTerminatedinthePreviousDay(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Linium Employees Terminated in the Previous Day", null, null, null);
		//
	}
	
	// @RequestMapping(value =
	// "/RotatorSubmittalInterviewActivitiesCountbyRecruiter", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData RotatorSubmittalInterviewActivitiesCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator Submittal/Interview Activities Count by Recruiter", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/RotatorInternalSubmittalsCountbyRecruiter",
	// method = RequestMethod.GET, produces = "application/json")
	public BiData RotatorInternalSubmittalsCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator Internal Submittals Count by Recruiter", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/RotatorExternalSubmittalsCountbyRecruiter",
	// method = RequestMethod.GET, produces = "application/json")
	public BiData RotatorExternalSubmittalsCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator External Submittals Count by Recruiter", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/RotatorInterviewsCountbyRecruiter", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData RotatorInterviewsCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator Interviews Count by Recruiter", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/RotatorHiresCountbyRecruiter", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData RotatorHiresCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator Hires Count by Recruiter", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/RotatorActiveAssignments", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData RotatorActiveAssignments(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator Active Assignments", null, null, null);
		//
	}
	
	// @RequestMapping(value = "/RotatorAllAssignments", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData RotatorAllAssignments(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator All Assignments", null, null, null);
		//
	}
	
	// @RequestMapping(value = "/RotatorNewPositionsCountbyDivision", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData RotatorNewPositionsCountbyDivision(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator New Positions Count by Division", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/GenuentOpenandOnHoldJobsbyDivision", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData GenuentOpenandOnHoldJobsbyDivision(//
			//
			@ApiParam(value = "Division Id", required = true) //
			@RequestParam(required = true) Long divisionid//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { divisionid.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Genuent Open and On Hold Jobs by Division", null, null, parameters);
		//
	}
	
	// @RequestMapping(value = "/AkrayaInPersonMeetingNoteCount", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AkrayaInPersonMeetingNoteCount(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Akraya In Person Meeting Note Count", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AkrayaIssuedJobCountbyPrimarySales", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData AkrayaIssuedJobCountbyPrimarySales(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Akraya Issued Job Count by Primary Sales", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AkrayaExternalSubmittalCountbyPrimarySales",
	// method = RequestMethod.GET, produces = "application/json")
	public BiData AkrayaExternalSubmittalCountbyPrimarySales(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Akraya External Submittal Count by Primary Sales", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/AkrayaKPI", method = RequestMethod.GET,
	// produces = "application/json")
	public BiData AkrayaKPI(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Akraya KPI", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/BuildspaceClientReferences", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData BuildspaceClientReferences(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Buildspace Client References", null, null, null);
		//
	}
	
	// @RequestMapping(value = "/BuildspaceWorkerReferences", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData BuildspaceWorkerReferences(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Buildspace Worker References", null, null, null);
		//
	}
	
	// @RequestMapping(value = "/BuildspaceSuppliers", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData BuildspaceSuppliers(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Buildspace Suppliers", null, null, null);
		//
	}
	
	// @RequestMapping(value = "/BuildspaceCIS", method = RequestMethod.GET,
	// produces = "application/json")
	public BiData BuildspaceCIS(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Buildspace CIS", null, null, null);
		//
	}
	
	// @RequestMapping(value = "/NTTDataMoatFlipSubmittalCount", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData NTTDataMoatFlipSubmittalCount(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "NTT Data Moat/Flip/Submittal Count", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value =
	// "/RangTechnologiesCandidateRecordswithMissingData", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData RangTechnologiesCandidateRecordswithMissingData(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rang Technologies Candidate Records with Missing Data", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/DiversantActiveAssignments", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData DiversantActiveAssignments(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Diversant Active Assignments", null, null, null);
		//
	}
	
	// @RequestMapping(value = "/ETeamSubmittalMetricsbyLaborCategory", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData ETeamSubmittalMetricsbyLaborCategory(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Company Id", required = false) //
			@RequestParam(required = false) Long companyid//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = companyid != null ? new String[] { companyid.toString() } : new String[] {};
		//
		return biDataService.getBIData(jobDivaSession, "ETeam Submittal Metrics by Labor Category", fromDate, toDate, parameters);
		//
	}
	
	// @RequestMapping(value = "/RedBerryRecruitmentWeeksonAssignment", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData RedBerryRecruitmentWeeksonAssignment(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Red Berry Recruitment Weeks on Assignment", null, null, null);
		//
	}
	
	@RequestMapping(value = "/CandidateEmailRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateEmailRecords(//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Email Records", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/ContactEmailRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactEmailRecords(//
			@ApiParam(value = "Contact Id", required = true) //
			@RequestParam(required = true) Long contactId//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { contactId.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Contact Email Records", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/SavedEmails", method = RequestMethod.GET, produces = "application/json")
	public BiData SavedEmails(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Saved Emails", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/ResumeCVCountbyRecruiter", method = RequestMethod.GET, produces = "application/json")
	public BiData ResumeCVCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Resume/CV Count by Recruiter", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/SubmittalInterviewHireActivitiesCountbyRecruiter", method = RequestMethod.GET, produces = "application/json")
	public BiData SubmittalInterviewHireActivitiesCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Submittal/Interview/Hire Activities Count by Recruiter", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedPaychexProfile", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedPaychexProfile(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Paychex Profiles", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedADPProfiles", method = RequestMethod.GET, produces = "application/json")
	public BiData NewUpdatedADPProfiles(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated ADP Profiles", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/CompaniesCountbyUser", method = RequestMethod.GET, produces = "application/json")
	public BiData CompaniesCountbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Companies Count by User", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/JobCountbyUser", method = RequestMethod.GET, produces = "application/json")
	public BiData JobCountbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Job Count by User", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/ContactCountbyUser", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactCountbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Contact Count by User", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/CandidateNoteCountbyUser", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateNoteCountbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Note Count by User", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/ContactNoteCountbyUser", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactNoteCountbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Contact Note Count by User", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/CandidateNoteCountbyActionbyUser", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateNoteCountbyActionbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Note Count by Action by User", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/ContactNoteCountbyActionbyUser", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactNoteCountbyActionbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Contact Note Count by Action by User", fromDate, toDate, null);
		//
	}
	
	// @RequestMapping(value = "/EDIApprovedTimesheets", method =
	// RequestMethod.GET, produces = "application/json")
	public BiData EDIApprovedTimesheets(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "EDI Approved Timesheets", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewResumesDownloaded", method = RequestMethod.GET, produces = "application/json")
	public BiData NewResumesDownloaded(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Resumes Downloaded", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewExpenses", method = RequestMethod.GET, produces = "application/json")
	public BiData NewExpenses(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Expenses", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/CandidateOnBoardingDocumentList", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateOnBoardingDocumentList(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate On-Boarding Document List", null, null, parameters);
		//
	}
	
	@RequestMapping(value = "/NewPositionsCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public BiData NewPositionsCountbyDivision(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Positions Count by Division", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewPositionsCountbyPrimarySales", method = RequestMethod.GET, produces = "application/json")
	public BiData NewPositionsCountbyPrimarySales(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Positions Count by Primary Sales", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/NewPositionsCount", method = RequestMethod.GET, produces = "application/json")
	public BiData NewPositionsCount(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Positions Count", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/OpenPositionsCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public BiData OpenPositionsCountbyDivision(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Open Positions Count by Division", null, null, null);
		//
	}
	
	@RequestMapping(value = "/OpenPositionsCountbyPrimarySales", method = RequestMethod.GET, produces = "application/json")
	public BiData OpenPositionsCountbyPrimarySales(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Open Positions Count by Primary Sales", null, null, null);
		//
	}
	
	@RequestMapping(value = "/OpenPositionsCountbyJobType", method = RequestMethod.GET, produces = "application/json")
	public BiData OpenPositionsCountbyJobType(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Open Positions Count by Job Type", null, null, null);
		//
	}
	
	@RequestMapping(value = "/OpenPositionsCount", method = RequestMethod.GET, produces = "application/json")
	public BiData OpenPositionsCount(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Open Positions Count", null, null, null);
		//
	}
	
	@RequestMapping(value = "/IncomingResumesCount", method = RequestMethod.GET, produces = "application/json")
	public BiData IncomingResumesCount(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Incoming Resumes Count", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/HiresCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public BiData HiresCountbyDivision(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Hires Count by Division", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/FilledPositionsCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public BiData FilledPositionsCountbyDivision(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Filled Positions Count by Division", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/FilledPositionsCount", method = RequestMethod.GET, produces = "application/json")
	public BiData FilledPositionsCount(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Filled Positions Count", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/SubmittalInterviewActivitiesCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public BiData SubmittalInterviewActivitiesCountbyDivision( //
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Submittal/Interview Activities Count by Division", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/SubmittalInterviewActivitiesCountbyRecruiter", method = RequestMethod.GET, produces = "application/json")
	public BiData SubmittalInterviewActivitiesCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Submittal/Interview Activities Count by Recruiter", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/HiresCountbyPrimarySales", method = RequestMethod.GET, produces = "application/json")
	public BiData HiresCountbyPrimarySales(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Hires Count by Primary Sales", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/HiresCountbyRecruiter", method = RequestMethod.GET, produces = "application/json")
	public BiData HiresCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Hires Count by Recruiter", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/AgingofPositionsCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public BiData AgingofPositionsCountbyDivision(//
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Contract", required = true) //
			@RequestParam(required = true) String contract, //
			//
			@ApiParam(value = "Aging Range 1", required = true) //
			@RequestParam(required = true) Integer agingRange1, //
			//
			@ApiParam(value = "Aging Range 2", required = false) //
			@RequestParam(required = false) Integer agingRange2 //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		paramLits.add(contract);
		paramLits.add(agingRange1.toString());
		if (agingRange2 != null)
			paramLits.add(agingRange2.toString());
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Aging of Positions Count by Division", null, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/AgingofPositionsCountbyPrimarySales", method = RequestMethod.GET, produces = "application/json")
	public BiData AgingofPositionsCountbyPrimarySales(//
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Contract", required = true) //
			@RequestParam(required = true) String contract, //
			//
			@ApiParam(value = "Aging Range 1", required = true) //
			@RequestParam(required = true) Integer agingRange1, //
			//
			@ApiParam(value = "Aging Range 2", required = false) //
			@RequestParam(required = false) Integer agingRange2//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		paramLits.add(contract);
		paramLits.add(agingRange1.toString());
		if (agingRange2 != null)
			paramLits.add(agingRange2.toString());
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Aging of Positions Count by Primary Sales", null, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/EndedAssignmentsCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public BiData EndedAssignmentsCountbyDivision(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Ended Assignments Count by Division", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/IssuedJobsList", method = RequestMethod.GET, produces = "application/json")
	public BiData IssuedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Issued Jobs List", fromDate, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/UpdatedJobsList", method = RequestMethod.GET, produces = "application/json")
	public BiData UpdatedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		//
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Updated Jobs List", fromDate, toDate, parameters);
		//
	}
	
	@RequestMapping(value = "/SubmittalInterviewHireActivitiesList", method = RequestMethod.GET, produces = "application/json")
	public BiData SubmittalInterviewHireActivitiesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Submittal/Interview/Hire Activities List", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/CandidateActions", method = RequestMethod.GET, produces = "application/json")
	public BiData CandidateActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Actions", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/ContactActions", method = RequestMethod.GET, produces = "application/json")
	public BiData ContactActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Contact Actions", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/AccessLog", method = RequestMethod.GET, produces = "application/json")
	public BiData AccessLog(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Access Log", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/UsersList", method = RequestMethod.GET, produces = "application/json")
	public BiData UsersList(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Users List", null, null, null);
		//
	}
	
	@RequestMapping(value = "/VMSUsersList", method = RequestMethod.GET, produces = "application/json")
	public BiData VMSUsersList(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "VMS Users List", null, null, null);
		//
	}
	
	@RequestMapping(value = "/UserGroupLists", method = RequestMethod.GET, produces = "application/json")
	public BiData UserGroupLists(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "User Group Lists", null, null, null);
		//
	}
	
	@RequestMapping(value = "/DivisionsList", method = RequestMethod.GET, produces = "application/json")
	public BiData DivisionsList(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Divisions List", null, null, null);
		//
	}
	
	@RequestMapping(value = "/RejectionReasonsList", method = RequestMethod.GET, produces = "application/json")
	public BiData RejectionReasonsList(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rejection Reasons List", null, null, null);
		//
	}
	
	@RequestMapping(value = "/UserfieldsList", method = RequestMethod.GET, produces = "application/json")
	public BiData UserfieldsList(//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Userfields List", null, null, null);
		//
	}
	
	@RequestMapping(value = "/AttachmentTypeList", method = RequestMethod.GET, produces = "application/json")
	public BiData AttachmentTypeList(//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Attachment Type List", null, null, null);
		//
	}
	
	@RequestMapping(value = "/EmailMergeOptOutCandidateList", method = RequestMethod.GET, produces = "application/json")
	public BiData EmailMergeOptOutCandidateList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		return biDataService.getBIData(jobDivaSession, "Email Merge OptOut Candidate List", fromDate, toDate, null);
		//
	}
	
	@RequestMapping(value = "/RedactedCandidatesRecords", method = RequestMethod.GET, produces = "application/json")
	public BiData RedactedCandidatesRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate //
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Redacted Candidates Records", fromDate, toDate, null);
		//
	}
}

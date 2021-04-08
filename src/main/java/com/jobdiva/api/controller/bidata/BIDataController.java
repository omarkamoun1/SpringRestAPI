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
import com.jobdiva.api.model.bidata.IBiData;
import com.jobdiva.api.service.BIDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

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
	public IBiData getBIData(//
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
			@RequestParam(required = false) String[] parameters, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, metricName, fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CompanyDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CompanyDetail(//
			//
			@ApiParam(value = "Company Id", required = true) //
			@RequestParam(required = true) Long companyId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Company Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CompaniesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CompaniesDetail(//
			//
			@ApiParam(value = "Company Id (s)", required = true) //
			@RequestParam(required = true) Long[] companyIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Companies Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CompanyOwnersDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CompanyOwnersDetail(//
			//
			@ApiParam(value = "Company Id", required = true) //
			@RequestParam(required = true) Long companyId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { companyId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Company Owners Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CompaniesOwnersDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CompaniesOwnersDetail(//
			//
			@ApiParam(value = "Company Id (s)", required = true) //
			@RequestParam(required = true) Long[] companyIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Companies Owners Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CompaniesTypesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CompaniesTypesDetail(//
			//
			@ApiParam(value = "Company Id (s)", required = true) //
			@RequestParam(required = true) Long[] companyIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Companies Types Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CompanyAddressesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CompanyAddressesDetail(//
			//
			@ApiParam(value = "Company Id", required = true) //
			@RequestParam(required = true) Long companyId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { companyId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Company Addresses Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactDetail(//
			//
			@ApiParam(value = "Contact Id", required = true) //
			@RequestParam(required = true) Long contacId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Contact Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/V2ContactDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData V2ContactDetail(//
			//
			@ApiParam(value = "Contact Id", required = true) //
			@RequestParam(required = true) Long contacId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "V2 Contact Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactsDetail(//
			//
			@ApiParam(value = "Contact Id (s)", required = true) //
			@RequestParam(required = true) Long[] contactIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
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
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Contacts Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/V2ContactsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData V2ContactsDetail(//
			//
			@ApiParam(value = "Contact Id (s)", required = true) //
			@RequestParam(required = true) Long[] contactIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
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
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		//
		return biDataService.getBIData(jobDivaSession, "V2 Contacts Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactOwnersDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactOwnersDetail(//
			//
			@ApiParam(value = "Contact Id", required = true) //
			@RequestParam(required = true) Long contacId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { contacId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Contact Owners Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactsOwnersDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactsOwnersDetail(//
			//
			@ApiParam(value = "Contact Id (s)", required = true) //
			@RequestParam(required = true) Long[] contactIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Contacts Owners Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactsTypesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactsTypesDetail(//
			//
			@ApiParam(value = "Contact Id (s)", required = true) //
			@RequestParam(required = true) Long[] contactIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Contacts Types Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactAddressesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactAddressesDetail(//
			//
			@ApiParam(value = "Contact Id", required = true) //
			@RequestParam(required = true) Long contacId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { contacId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Contact Addresses Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobDetail(//
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Job Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobsDetail(//
			//
			@ApiParam(value = "Job Id (s)", required = true) //
			@RequestParam(required = true) Long[] jobIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
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
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Jobs Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobContactsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobContactsDetail(//
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Job Contacts Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobsContactsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobsContactsDetail(//
			//
			@ApiParam(value = "Job Id (s)", required = true) //
			@RequestParam(required = true) Long[] jobIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Jobs Contacts Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobUsersDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobUsersDetail(//
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Job Users Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobsUsersDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobsUsersDetail(//
			//
			@ApiParam(value = "Job Id (s)", required = true) //
			@RequestParam(required = true) Long[] jobIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Jobs Users Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobApplicantsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobApplicantsDetail(//
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Job Applicants Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateJobApplicationDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateJobApplicationDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString(), jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Job Application Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobSubmittalsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobSubmittalsDetail(//
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Job Submittals Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobsSubmittalsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobsSubmittalsDetail(//
			//
			@ApiParam(value = "Job Id (s)", required = true) //
			@RequestParam(required = true) Long[] jobIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Jobs Submittals Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateSubmittalsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateSubmittalsDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateid.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Submittals Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ResumeDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ResumeDetail(//
			//
			@ApiParam(value = "Resume Id", required = true) //
			@RequestParam(required = true) Long resumeId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { resumeId.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Resume Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UsersTasksDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData UsersTasksDetail(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Id (s)", required = true) //
			@RequestParam(required = true) Long[] userds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Users Tasks Detail", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/EventsAttendeesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData EventsAttendeesDetail(//
			//
			@ApiParam(value = "Event Id (s)", required = true) //
			@RequestParam(required = true) Long[] eventids, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Events Attendees Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Candidate Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidatesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidatesDetail(//
			//
			@ApiParam(value = "Candidate Id (s)", required = true) //
			@RequestParam(required = true) Long[] candidateIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
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
		if (userFieldsName != null) {
			for (int i = 0; i < userFieldsName.length; i++) {
				paramLits.add(userFieldsName[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidates Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateQualificationsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateQualificationsDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Category Names", required = false) //
			@RequestParam(required = false) String[] categoryNames, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Candidate Qualifications Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidatesQualificationsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidatesQualificationsDetail(//
			//
			@ApiParam(value = "Candidate Id (s)", required = true) //
			@RequestParam(required = true) Long[] candidateIds, //
			//
			@ApiParam(value = "Category Names", required = false) //
			@RequestParam(required = false) String[] categoryNames, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
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
		if (categoryNames != null) {
			for (int i = 0; i < categoryNames.length; i++) {
				paramLits.add(categoryNames[i]);
			}
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidates Qualifications Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateAvailabilityDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateAvailabilityDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Availability Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidatesAvailabilityDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidatesAvailabilityDetail(//
			//
			@ApiParam(value = "Candidate Id (s)", required = true) //
			@RequestParam(required = true) Long[] candidateIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
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
		return biDataService.getBIData(jobDivaSession, "Candidates Availability Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateResumesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateResumesDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Resumes Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidatesResumesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidatesResumesDetail(//
			//
			@ApiParam(value = "Candidate Id (s)", required = true) //
			@RequestParam(required = true) Long[] candidateIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Candidates Resumes Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateResumeSourceDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateResumeSourceDetail(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Resume Source Detail", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateOnBoardingDocumentDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateOnBoardingDocumentDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Interview Id", required = true) //
			@RequestParam(required = true) Long interviewId, //
			//
			@ApiParam(value = "File Name", required = true) //
			@RequestParam(required = true) String fileName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString(), interviewId.toString(), fileName };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate On-Boarding Document Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateAttachmentDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateAttachmentDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "File Name", required = true) //
			@RequestParam(required = true) String fileName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString(), fileName };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Attachment Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CompanyNoteDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CompanyNoteDetail(//
			//
			@ApiParam(value = "Note Id", required = true) //
			@RequestParam(required = true) Long noteId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { noteId.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Company Note Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CompanyNotesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CompanyNotesDetail(//
			//
			@ApiParam(value = "Note Id (s)", required = true) //
			@RequestParam(required = true) Long[] noteIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Company Notes Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactNoteDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactNoteDetail(//
			//
			@ApiParam(value = "Note Id", required = true) //
			@RequestParam(required = true) Long noteId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { noteId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Contact Note Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactNotesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactNotesDetail(//
			//
			@ApiParam(value = "Note Id (s)", required = true) //
			@RequestParam(required = true) Long[] noteIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
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
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Contact Notes Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateNoteDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateNoteDetail(//
			//
			@ApiParam(value = "Note Id", required = true) //
			@RequestParam(required = true) Long noteId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { noteId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Note Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateNotesDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateNotesDetail(//
			//
			@ApiParam(value = "Note Id (s)", required = true) //
			@RequestParam(required = true) Long[] noteIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
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
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Notes Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateEEODetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateEEODetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate EEO Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SubmittalDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData SubmittalDetail(//
			//
			@ApiParam(value = "Interview Schedule Id", required = true) //
			@RequestParam(required = true) Long interviewScheduleId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Submittal Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SubmittalsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData SubmittalsDetail(//
			//
			@ApiParam(value = "Interview Schedule Id(s)", required = true) //
			@RequestParam(required = true) Long[] interviewScheduleIds, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Submittals Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/BillingRecordDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData BillingRecordDetail(//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			//
			@ApiParam(value = "Record Id", required = true) //
			@RequestParam(required = true) Long recordId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString(), recordId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Billing Record Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/BillingRecordsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData BillingRecordsDetail(//
			//
			@ApiParam(value = "Employee Id(s)", required = true) //
			@RequestParam(required = true) Long[] employeeIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Billing Records Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/EmployeeBillingRecordsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData EmployeeBillingRecordsDetail(//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Employee Billing Records Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SalaryRecordDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData SalaryRecordDetail(//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			//
			@ApiParam(value = "Record Id", required = true) //
			@RequestParam(required = true) Long recordId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString(), recordId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Salary Record Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SalaryRecordsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData SalaryRecordsDetail(//
			//
			@ApiParam(value = "Employee Id(s)", required = true) //
			@RequestParam(required = true) Long[] employeeIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Salary Records Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/EmployeeSalaryRecordsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData EmployeeSalaryRecordsDetail(//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Employee Salary Records Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/InvoiceDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData InvoiceDetail(//
			//
			@ApiParam(value = "Invoice Id", required = true) //
			@RequestParam(required = true) Long invoiceId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { invoiceId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Invoice Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/PlacementDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData PlacementDetail(//
			//
			@ApiParam(value = "Invoice Id", required = true) //
			@RequestParam(required = true) Long invoiceId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { invoiceId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Placement Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/EmployeeTimesheetImageDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData EmployeeTimesheetImageDetail(//
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
			@RequestParam(required = true) Long billiingRecordId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString(), billiingRecordId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Employee Timesheet Image Detail", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/EmployeeTimesheetImagebyTimecardIDDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData EmployeeTimesheetImagebyTimecardIDDetail(//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			@ApiParam(value = "Time Card Id", required = true) //
			@RequestParam(required = true) Long timeCardId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString(), timeCardId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Employee Timesheet Image by Timecard ID Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/TimesheetDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData TimesheetDetail(//
			@ApiParam(value = "Recruiter TeamI Id", required = true) //
			@RequestParam(required = true) Long recruiterTeamId, //
			//
			@ApiParam(value = "Exist Employee In Billing Record", required = false) //
			@RequestParam(required = false) Boolean existEmployeeInBillingRecord, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = existEmployeeInBillingRecord != null ? new String[] { recruiterTeamId.toString(), Boolean.toString(existEmployeeInBillingRecord) } : new String[] { recruiterTeamId.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Timesheet Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactHotlistDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactHotlistDetail(//
			@ApiParam(value = "Hotlist Id", required = true) //
			@RequestParam(required = true) Long hotlistId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { hotlistId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Contact Hotlist Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateHotlistDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateHotlistDetail(//
			@ApiParam(value = "Hotlist Id", required = true) //
			@RequestParam(required = true) Long hotlistId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { hotlistId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Hotlist Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UserContactHotlistDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData UserContactHotlistDetail(//
			@ApiParam(value = "Recruiter Id", required = true) //
			@RequestParam(required = true) Long recruiterId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { recruiterId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "User Contact Hotlist Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UserCandidateHotlistDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData UserCandidateHotlistDetail(//
			@ApiParam(value = "Recruiter Id", required = true) //
			@RequestParam(required = true) Long recruiterId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { recruiterId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "User Candidate Hotlist Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactHotlistsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactHotlistsDetail(//
			@ApiParam(value = "Hotlist Id (s)", required = true) //
			@RequestParam(required = true) Long[] hotlistIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Contact Hotlists Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateHotlistsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateHotlistsDetail(//
			@ApiParam(value = "Hotlist Id (s)", required = true) //
			@RequestParam(required = true) Long[] hotlistIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Candidate Hotlists Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateNotesListDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateNotesListDetail(//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Notes List Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateHRDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateHRDetail(//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate HR Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidatesHRDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidatesHRDetail(//
			@ApiParam(value = "Candidate Id(s)", required = true) //
			@RequestParam(required = true) Long[] candidateIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
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
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return biDataService.getBIData(jobDivaSession, "Candidates HR Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/EmailDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData EmailDetail(//
			@ApiParam(value = "Id", required = true) //
			@RequestParam(required = true) Long id, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { id.toString() };
		// //
		return biDataService.getBIData(jobDivaSession, "Email Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ADPProfileDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData ADPProfileDetail(//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "ADP Profile Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateAttributeDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateAttributeDetail(//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Attribute Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobsListbyUserbyStatus", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobsListbyUserbyStatus(//
			//
			@ApiParam(value = "Recruiter Id", required = true) //
			@RequestParam(required = true) Long recruiterId, //
			//
			@ApiParam(value = "Job Status", required = true) //
			@RequestParam(required = true) Integer jobStatus, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { recruiterId.toString(), jobStatus.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Jobs List by User by Status", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/PortalJobsList", method = RequestMethod.GET, produces = "application/json")
	public IBiData PortalJobsList(//
			@ApiParam(value = "Portal ID", required = true) //
			@RequestParam(required = true) Long portalID, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { portalID.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Portal Jobs List", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobsDashboardbyUser", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobsDashboardbyUser(//
			@ApiParam(value = "Recruiter Id", required = true) //
			@RequestParam(required = true) Long recruiterId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { recruiterId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Jobs Dashboard by User", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/OpenJobsListbyCompany", method = RequestMethod.GET, produces = "application/json")
	public IBiData OpenJobsListbyCompany(//
			@ApiParam(value = "Company Id", required = true) //
			@RequestParam(required = true) Long companyId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { companyId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Open Jobs List by Company", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/OpenJobsListbyContact", method = RequestMethod.GET, produces = "application/json")
	public IBiData OpenJobsListbyContact(//
			//
			@ApiParam(value = "Customer Id", required = true) //
			@RequestParam(required = true) Long customerId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { customerId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Open Jobs List by Contact", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateApplicationRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateApplicationRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Application Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewApprovedBillingRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewApprovedBillingRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Approved Billing Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewApprovedSalaryRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewApprovedSalaryRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Approved Salary Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UpdatedApprovedBillingRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData UpdatedApprovedBillingRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Updated Approved Billing Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UpdatedApprovedSalaryRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData UpdatedApprovedSalaryRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Updated Approved Salary Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewInvoices", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewInvoices(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Invoices", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/VoidedInvoices", method = RequestMethod.GET, produces = "application/json")
	public IBiData VoidedInvoices(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Voided Invoices", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewApprovedTimesheets", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewApprovedTimesheets(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Approved Timesheets", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ApprovedTimesheetsbyWeekEndingDate", method = RequestMethod.GET, produces = "application/json")
	public IBiData ApprovedTimesheetsbyWeekEndingDate(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Approved Timesheets by Week Ending Date", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewHires", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewHires(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Hires", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UpdatedHires", method = RequestMethod.GET, produces = "application/json")
	public IBiData UpdatedHires(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Updated Hires", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/MergedCompanies", method = RequestMethod.GET, produces = "application/json")
	public IBiData MergedCompanies(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Merged Companies", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/MergedContacts", method = RequestMethod.GET, produces = "application/json")
	public IBiData MergedContacts(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Merged Contacts", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/MergedJobs", method = RequestMethod.GET, produces = "application/json")
	public IBiData MergedJobs(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Merged Jobs", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/MergedCandidates", method = RequestMethod.GET, produces = "application/json")
	public IBiData MergedCandidates(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Merged Candidates", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateResumeSubmittedtoJob", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateResumeSubmittedtoJob(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString(), jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Resume Submitted to Job", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SubmittalRecordsbyCandidateandJob", method = RequestMethod.GET, produces = "application/json")
	public IBiData SubmittalRecordsbyCandidateandJob(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString(), jobId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Submittal Records by Candidate and Job", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobsPosted", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobsPosted(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Jobs Posted", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobsCurrentlyPosted", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobsCurrentlyPosted(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Jobs Currently Posted", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UsersUpdatedJobsList", method = RequestMethod.GET, produces = "application/json")
	public IBiData UsersUpdatedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Users Updated Jobs List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCompanyRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedCompanyRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "New/Updated Company Records", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCompanyNoteRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedCompanyNoteRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Company Note Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedContactRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedContactRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "New/Updated Contact Records", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedContactNoteRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedContactNoteRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Contact Note Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedOpportunityRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedOpportunityRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Opportunity Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedJobRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedJobRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "New/Updated Job Records", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCandidateRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedCandidateRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "New/Updated Candidate Records", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCandidateNoteRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedCandidateNoteRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Candidate Note Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCandidateHRRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedCandidateHRRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Candidate HR Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedEmployeeRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedEmployeeRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Employee Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedActivityRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedActivityRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "New/Updated Activity Records", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedTaskRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedTaskRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Task Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedEventRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedEventRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Event Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCandidateQualificationRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedCandidateQualificationRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Candidate Qualification Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/V2NewUpdatedContactRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData V2NewUpdatedContactRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "V2 New/Updated Contact Records", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedSubmittalInterviewHireActivityRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedSubmittalInterviewHireActivityRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Submittal/Interview/Hire Activity Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedJobUserRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedJobUserRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Job User Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedBillingRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedBillingRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Billing Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedSalaryRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedSalaryRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Salary Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedApprovedTimesheetRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedApprovedTimesheetRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Approved Timesheet Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedApprovedTimesheetandExpenseRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedApprovedTimesheetandExpenseRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Approved Timesheet and Expense Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedCandidateAvailabilityRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedCandidateAvailabilityRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Candidate Availability Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/DeletedCandidateNoteRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData DeletedCandidateNoteRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Candidate Note Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/DeletedEmployeeRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData DeletedEmployeeRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Employee Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/DeletedActivityRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData DeletedActivityRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Activity Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/DeletedCompanyRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData DeletedCompanyRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Company Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/DeletedContactRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData DeletedContactRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Contact Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/DeletedContactNoteRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData DeletedContactNoteRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Contact Note Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/DeletedJobRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData DeletedJobRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Job Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/DeletedCandidateRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData DeletedCandidateRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Candidate Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/DeletedTaskRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData DeletedTaskRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Task Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/Sage50RedberryNewEmployeeFeed", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData Sage50RedberryNewEmployeeFeed(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Sage 50 Redberry New Employee Feed", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/Sage50RedberryNewTimesheetFeed", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData Sage50RedberryNewTimesheetFeed(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Sage 50 Redberry New Timesheet Feed", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/MISINewUpdatedJobs", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData MISINewUpdatedJobs(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "MISI New/Updated Jobs", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/MISINewUpdatedStarts", method = RequestMethod.GET, produces = "application/json")
	public IBiData MISINewUpdatedStarts(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "MISI New/Updated Starts", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewCompaniesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageNewCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Advantage New Companies List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedCompaniesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUpdatedCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Companies List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedCompaniesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageDeletedCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Companies List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageMergedCompaniesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageMergedCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Merged Companies List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageOwnersUpdatedCompaniesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageOwnersUpdatedCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Owners Updated Companies List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageCompanyTypesUpdatedCompaniesList",
	// method = RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageCompanyTypesUpdatedCompaniesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Company Types Updated Companies List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewContactsList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageNewContactsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Advantage New Contacts List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedContactsList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUpdatedContactsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Contacts List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedContactsList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageDeletedContactsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Contacts List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageMergedContactsList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageMergedContactsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Merged Contacts List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageOwnersUpdatedContactsList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageOwnersUpdatedContactsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Owners Updated Contacts List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageIssuedJobsList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageIssuedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Advantage Issued Jobs List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedJobsList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUpdatedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Jobs List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedJobsList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageDeletedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Jobs List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageMergedJobsList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageMergedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Merged Jobs List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUsersUpdatedJobsList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUsersUpdatedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Users Updated Jobs List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewCandidatesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageNewCandidatesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Advantage New Candidates List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedCandidatesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUpdatedCandidatesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Candidates List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedCandidatesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageDeletedCandidatesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Candidates List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageMergedCandidatesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageMergedCandidatesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Merged Candidates List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageSubmittalInterviewHireActivitiesList",
	// method = RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageSubmittalInterviewHireActivitiesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Advantage Submittal/Interview/Hire Activities List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value =
	// "/AdvantageUpdatedSubmittalInterviewHireActivitiesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUpdatedSubmittalInterviewHireActivitiesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Submittal/Interview/Hire Activities List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value =
	// "/AdvantageDeletedSubmittalInterviewHireActivitiesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageDeletedSubmittalInterviewHireActivitiesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Submittal/Interview/Hire Activities List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageCandidateActions", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageCandidateActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Candidate Actions", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageContactActions", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageContactActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Contact Actions", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedCandidateActions", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageDeletedCandidateActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Candidate Actions", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedContactActions", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUpdatedContactActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Contact Actions", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedContactActions", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageDeletedContactActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Contact Actions", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageJobActions", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageJobActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Job Actions", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedJobActions", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageDeletedJobActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Job Actions", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewEmployee", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageNewEmployee(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage New Employee", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedEmployee", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUpdatedEmployee(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Employee", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedEmployee", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageDeletedEmployee(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Employee", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageSalesPipeline", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageSalesPipeline(//
			@ApiParam(value = "Type Name", required = false) //
			@RequestParam(required = false) String typeName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = typeName != null ? new String[] { typeName } : null;
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Sales Pipeline", null, null, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUsersList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUsersList(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Users List", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUserGroupLists", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUserGroupLists(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage User Group Lists", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewCandidateReferenceCheck", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageNewCandidateReferenceCheck(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage New Candidate Reference Check", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedCandidateReferenceCheck",
	// method = RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUpdatedCandidateReferenceCheck(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated Candidate Reference Check", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedCandidateReferenceCheck",
	// method = RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageDeletedCandidateReferenceCheck(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted Candidate Reference Check", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageNewAIF", method = RequestMethod.GET,
	// produces = "application/json")
	public IBiData AdvantageNewAIF(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage New AIF", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageUpdatedAIF", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageUpdatedAIF(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Updated AIF", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageDeletedAIF", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageDeletedAIF(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Deleted AIF", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageAccessLog", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageAccessLog(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage Access Log", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AdvantageBIDataCheck", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AdvantageBIDataCheck(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Advantage BIData Check", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/TCMLNewHires", method = RequestMethod.GET,
	// produces = "application/json")
	public IBiData TCMLNewHires(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "TCML New Hires", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/TCMLUpdatedHires", method = RequestMethod.GET,
	// produces = "application/json")
	public IBiData TCMLUpdatedHires(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "TCML Updated Hires", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/TCMLEEO", method = RequestMethod.GET, produces
	// = "application/json")
	public IBiData TCMLEEO(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "TCML EEO", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/ACSIFeed", method = RequestMethod.GET, produces
	// = "application/json")
	public IBiData ACSIFeed(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ACSI Feed", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AditiCurrentPlacementActivities", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AditiCurrentPlacementActivities(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Aditi Current Placement Activities", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AditiCurrentHeadCount", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AditiCurrentHeadCount(//
			//
			@ApiParam(value = "company Id", required = true) //
			@RequestParam(required = true) Long companyId, //
			//
			@ApiParam(value = "Sales Person Id(s)", required = false) //
			@RequestParam(required = false) Long[] salesPersonIds, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Aditi Current Head Count", null, null, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/ICSOFFICEVISITS", method = RequestMethod.GET,
	// produces = "application/json")
	public IBiData ICSOFFICEVISITS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_OFFICE_VISITS", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/ICSSUBMITTALS", method = RequestMethod.GET,
	// produces = "application/json")
	public IBiData ICSSUBMITTALS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_SUBMITTALS", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/ICSINTERVIEWS", method = RequestMethod.GET,
	// produces = "application/json")
	public IBiData ICSINTERVIEWS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_INTERVIEWS", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/ICSHIRES", method = RequestMethod.GET, produces
	// = "application/json")
	public IBiData ICSHIRES(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_HIRES", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/ICSREFERENCES", method = RequestMethod.GET,
	// produces = "application/json")
	public IBiData ICSREFERENCES(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_REFERENCES", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/ICSCONTACTS", method = RequestMethod.GET,
	// produces = "application/json")
	public IBiData ICSCONTACTS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_CONTACTS", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/ICSCONTACTACTIVITIES", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData ICSCONTACTACTIVITIES(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_CONTACT_ACTIVITIES", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/ICSCOVERS", method = RequestMethod.GET,
	// produces = "application/json")
	public IBiData ICSCOVERS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_COVERS", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/ICSJOBREQUISITIONS", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData ICSJOBREQUISITIONS(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "ICS_JOB_REQUISITIONS", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/LiniumInvoicesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData LiniumInvoicesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Linium Invoices List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/LiniumActiveEmployeesList", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData LiniumActiveEmployeesList(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Linium Active Employees List", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value =
	// "/LiniumActiveEmployeesListNotExcludedfromWorkterra", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData LiniumActiveEmployeesListNotExcludedfromWorkterra(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Linium Active Employees List Not Excluded from Workterra", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/LiniumEmployeesTerminatedinthePreviousDay",
	// method = RequestMethod.GET, produces = "application/json")
	public IBiData LiniumEmployeesTerminatedinthePreviousDay(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Linium Employees Terminated in the Previous Day", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value =
	// "/RotatorSubmittalInterviewActivitiesCountbyRecruiter", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData RotatorSubmittalInterviewActivitiesCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator Submittal/Interview Activities Count by Recruiter", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/RotatorInternalSubmittalsCountbyRecruiter",
	// method = RequestMethod.GET, produces = "application/json")
	public IBiData RotatorInternalSubmittalsCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator Internal Submittals Count by Recruiter", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/RotatorExternalSubmittalsCountbyRecruiter",
	// method = RequestMethod.GET, produces = "application/json")
	public IBiData RotatorExternalSubmittalsCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator External Submittals Count by Recruiter", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/RotatorInterviewsCountbyRecruiter", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData RotatorInterviewsCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator Interviews Count by Recruiter", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/RotatorHiresCountbyRecruiter", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData RotatorHiresCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator Hires Count by Recruiter", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/RotatorActiveAssignments", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData RotatorActiveAssignments(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator Active Assignments", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/RotatorAllAssignments", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData RotatorAllAssignments(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator All Assignments", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/RotatorNewPositionsCountbyDivision", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData RotatorNewPositionsCountbyDivision(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rotator New Positions Count by Division", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/GenuentOpenandOnHoldJobsbyDivision", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData GenuentOpenandOnHoldJobsbyDivision(//
			//
			@ApiParam(value = "Division Id", required = true) //
			@RequestParam(required = true) Long divisionid, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { divisionid.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Genuent Open and On Hold Jobs by Division", null, null, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AkrayaInPersonMeetingNoteCount", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AkrayaInPersonMeetingNoteCount(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Akraya In Person Meeting Note Count", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AkrayaIssuedJobCountbyPrimarySales", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData AkrayaIssuedJobCountbyPrimarySales(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Akraya Issued Job Count by Primary Sales", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AkrayaExternalSubmittalCountbyPrimarySales",
	// method = RequestMethod.GET, produces = "application/json")
	public IBiData AkrayaExternalSubmittalCountbyPrimarySales(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Akraya External Submittal Count by Primary Sales", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/AkrayaKPI", method = RequestMethod.GET,
	// produces = "application/json")
	public IBiData AkrayaKPI(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Akraya KPI", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/BuildspaceClientReferences", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData BuildspaceClientReferences(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Buildspace Client References", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/BuildspaceWorkerReferences", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData BuildspaceWorkerReferences(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Buildspace Worker References", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/BuildspaceSuppliers", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData BuildspaceSuppliers(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Buildspace Suppliers", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/BuildspaceCIS", method = RequestMethod.GET,
	// produces = "application/json")
	public IBiData BuildspaceCIS(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Buildspace CIS", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/NTTDataMoatFlipSubmittalCount", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData NTTDataMoatFlipSubmittalCount(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "NTT Data Moat/Flip/Submittal Count", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value =
	// "/RangTechnologiesCandidateRecordswithMissingData", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData RangTechnologiesCandidateRecordswithMissingData(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rang Technologies Candidate Records with Missing Data", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/DiversantActiveAssignments", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData DiversantActiveAssignments(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Diversant Active Assignments", null, null, null, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/ETeamSubmittalMetricsbyLaborCategory", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData ETeamSubmittalMetricsbyLaborCategory(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Company Id", required = false) //
			@RequestParam(required = false) Long companyid, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = companyid != null ? new String[] { companyid.toString() } : new String[] {};
		//
		return biDataService.getBIData(jobDivaSession, "ETeam Submittal Metrics by Labor Category", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	// @RequestMapping(value = "/RedBerryRecruitmentWeeksonAssignment", method =
	// RequestMethod.GET, produces = "application/json")
	public IBiData RedBerryRecruitmentWeeksonAssignment(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Red Berry Recruitment Weeks on Assignment", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateEmailRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateEmailRecords(//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Email Records", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactEmailRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactEmailRecords(//
			@ApiParam(value = "Contact Id", required = true) //
			@RequestParam(required = true) Long contactId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { contactId.toString() };
		//
		//
		return biDataService.getBIData(jobDivaSession, "Contact Email Records", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SavedEmails", method = RequestMethod.GET, produces = "application/json")
	public IBiData SavedEmails(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Saved Emails", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ResumeCVCountbyRecruiter", method = RequestMethod.GET, produces = "application/json")
	public IBiData ResumeCVCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Resume/CV Count by Recruiter", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SubmittalInterviewHireActivitiesCountbyRecruiter", method = RequestMethod.GET, produces = "application/json")
	public IBiData SubmittalInterviewHireActivitiesCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Submittal/Interview/Hire Activities Count by Recruiter", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedPaychexProfile", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedPaychexProfile(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Paychex Profiles", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedADPProfiles", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedADPProfiles(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated ADP Profiles", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CompaniesCountbyUser", method = RequestMethod.GET, produces = "application/json")
	public IBiData CompaniesCountbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Companies Count by User", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobCountbyUser", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobCountbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Job Count by User", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactCountbyUser", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactCountbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Contact Count by User", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateNoteCountbyUser", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateNoteCountbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Note Count by User", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactNoteCountbyUser", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactNoteCountbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Contact Note Count by User", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateNoteCountbyActionbyUser", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateNoteCountbyActionbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Note Count by Action by User", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactNoteCountbyActionbyUser", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactNoteCountbyActionbyUser(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Contact Note Count by Action by User", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewResumesDownloaded", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewResumesDownloaded(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Resumes Downloaded", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewExpenses", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewExpenses(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Expenses", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateOnBoardingDocumentList", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateOnBoardingDocumentList(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate On-Boarding Document List", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewPositionsCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewPositionsCountbyDivision(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Positions Count by Division", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewPositionsCountbyPrimarySales", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewPositionsCountbyPrimarySales(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Positions Count by Primary Sales", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewPositionsCount", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewPositionsCount(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New Positions Count", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/OpenPositionsCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public IBiData OpenPositionsCountbyDivision(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Open Positions Count by Division", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/OpenPositionsCountbyPrimarySales", method = RequestMethod.GET, produces = "application/json")
	public IBiData OpenPositionsCountbyPrimarySales(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Open Positions Count by Primary Sales", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/OpenPositionsCountbyJobType", method = RequestMethod.GET, produces = "application/json")
	public IBiData OpenPositionsCountbyJobType(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Open Positions Count by Job Type", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/OpenPositionsCount", method = RequestMethod.GET, produces = "application/json")
	public IBiData OpenPositionsCount(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Open Positions Count", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/IncomingResumesCount", method = RequestMethod.GET, produces = "application/json")
	public IBiData IncomingResumesCount(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Incoming Resumes Count", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/HiresCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public IBiData HiresCountbyDivision(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Hires Count by Division", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/FilledPositionsCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public IBiData FilledPositionsCountbyDivision(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Filled Positions Count by Division", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/FilledPositionsCount", method = RequestMethod.GET, produces = "application/json")
	public IBiData FilledPositionsCount(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Filled Positions Count", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SubmittalInterviewActivitiesCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public IBiData SubmittalInterviewActivitiesCountbyDivision( //
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Submittal/Interview Activities Count by Division", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SubmittalInterviewActivitiesCountbyRecruiter", method = RequestMethod.GET, produces = "application/json")
	public IBiData SubmittalInterviewActivitiesCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Submittal/Interview Activities Count by Recruiter", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/HiresCountbyPrimarySales", method = RequestMethod.GET, produces = "application/json")
	public IBiData HiresCountbyPrimarySales(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Hires Count by Primary Sales", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/HiresCountbyRecruiter", method = RequestMethod.GET, produces = "application/json")
	public IBiData HiresCountbyRecruiter(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Hires Count by Recruiter", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/AgingofPositionsCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public IBiData AgingofPositionsCountbyDivision(//
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
			@RequestParam(required = false) Integer agingRange2, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Aging of Positions Count by Division", null, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/AgingofPositionsCountbyPrimarySales", method = RequestMethod.GET, produces = "application/json")
	public IBiData AgingofPositionsCountbyPrimarySales(//
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
			@RequestParam(required = false) Integer agingRange2, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Aging of Positions Count by Primary Sales", null, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/EndedAssignmentsCountbyDivision", method = RequestMethod.GET, produces = "application/json")
	public IBiData EndedAssignmentsCountbyDivision(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Ended Assignments Count by Division", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/IssuedJobsList", method = RequestMethod.GET, produces = "application/json")
	public IBiData IssuedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Issued Jobs List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UpdatedJobsList", method = RequestMethod.GET, produces = "application/json")
	public IBiData UpdatedJobsList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Updated Jobs List", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SubmittalInterviewHireActivitiesList", method = RequestMethod.GET, produces = "application/json")
	public IBiData SubmittalInterviewHireActivitiesList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Submittal/Interview/Hire Activities List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateActions", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Actions", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ContactActions", method = RequestMethod.GET, produces = "application/json")
	public IBiData ContactActions(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Contact Actions", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/AccessLog", method = RequestMethod.GET, produces = "application/json")
	public IBiData AccessLog(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Access Log", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UsersList", method = RequestMethod.GET, produces = "application/json")
	public IBiData UsersList(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Users List", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/VMSUsersList", method = RequestMethod.GET, produces = "application/json")
	public IBiData VMSUsersList(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "VMS Users List", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UserGroupLists", method = RequestMethod.GET, produces = "application/json")
	public IBiData UserGroupLists(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "User Group Lists", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/DivisionsList", method = RequestMethod.GET, produces = "application/json")
	public IBiData DivisionsList(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Divisions List", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/RejectionReasonsList", method = RequestMethod.GET, produces = "application/json")
	public IBiData RejectionReasonsList(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Rejection Reasons List", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UserfieldsList", method = RequestMethod.GET, produces = "application/json")
	public IBiData UserfieldsList(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Userfields List", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/AttachmentTypeList", method = RequestMethod.GET, produces = "application/json")
	public IBiData AttachmentTypeList(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Attachment Type List", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/EmailMergeOptOutCandidateList", method = RequestMethod.GET, produces = "application/json")
	public IBiData EmailMergeOptOutCandidateList(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		return biDataService.getBIData(jobDivaSession, "Email Merge OptOut Candidate List", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/RedactedCandidatesRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData RedactedCandidatesRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
						//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Redacted Candidates Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/NescoDinesolCandidateDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData NescoDinesolCandidateDetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Nesco Dinesol Candidate Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/NescoDinesolCandidateEEODetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData NescoDinesolCandidateEEODetail(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Nesco Dinesol Candidate EEO Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/NescoDinesolBillingRecordDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData NescoDinesolBillingRecordDetail(//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			//
			@ApiParam(value = "Record Id", required = true) //
			@RequestParam(required = true) Long recordId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString(), recordId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Nesco Dinesol Billing Record Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/NescoDinesolCandidateHRDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData NescoDinesolCandidateHRDetail(//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Nesco Dinesol Candidate HR Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/NescoDinesolNewApprovedBillingRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NescoDinesolNewApprovedBillingRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Nesco Dinesol New Approved Billing Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/NescoDinesolNewApprovedSalaryRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NescoDinesolNewApprovedSalaryRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Nesco Dinesol New Approved Salary Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/NescoDinesolSalaryRecordDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData NescoDinesolSalaryRecordDetail(//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			//
			@ApiParam(value = "Record Id", required = true) //
			@RequestParam(required = true) Long recordId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString(), recordId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Nesco Dinesol Salary Record Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/NescoDinesolUpdatedApprovedBillingRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NescoDinesolUpdatedApprovedBillingRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Nesco Dinesol Updated Approved Billing Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/NescoDinesolUpdatedApprovedSalaryRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NescoDinesolUpdatedApprovedSalaryRecords(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Nesco Dinesol Updated Approved Salary Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/NescoDinesolJobDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData NescoDinesolJobDetail(//
			//
			@ApiParam(value = "Job Id", required = true) //
			@RequestParam(required = true) Long jobId, //
			//
			@ApiParam(value = "User Fields Name", required = false) //
			@RequestParam(required = false) String[] userFieldsName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
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
		return biDataService.getBIData(jobDivaSession, "Nesco Dinesol Job Detail", null, null, parameters, alternateFormat);
		//
	}
	
	/// Joseph
	@ApiIgnore
	@RequestMapping(value = "/NescoDinesolNewUpdatedTerminations", method = RequestMethod.GET, produces = "application/json")
	public IBiData NescoDinesolNewUpdatedTerminations(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Nesco Dinesol New/Updated Terminations", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/NewUpdatedTerminations", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedTerminations(//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Terminations", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/VMSSynchronizationList", method = RequestMethod.GET, produces = "application/json")
	public IBiData VMSSynchronizationList(//
			@ApiParam(value = "Site Name", required = true) //
			@RequestParam(required = true) String siteName, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { siteName };
		//
		return biDataService.getBIData(jobDivaSession, "VMS Synchronization List", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/UserfieldsChangeManagement", method = RequestMethod.GET, produces = "application/json")
	public IBiData UserfieldsChangeManagement(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Userfields Change Management", null, null, parameters, alternateFormat);
		//
	}
	//
	
	@RequestMapping(value = "/CandidateExperienceDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateExperienceDetail(//
			//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Experience Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/CandidateEducationDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData CandidateEducationDetail(//
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Candidate Education Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SOWDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData SOWDetail(//
			//
			@ApiParam(value = "Record Id", required = true) //
			@RequestParam(required = true) Long recordId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { recordId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "SOW Detail", null, null, parameters, alternateFormat);
		//
	}
	
	//
	@RequestMapping(value = "/OverheadsDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData OverheadsDetail(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Overheads Detail", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/TimesheetBreakdownDetail", method = RequestMethod.GET, produces = "application/json")
	public IBiData TimesheetBreakdownDetail(//
			//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Employee Id", required = true) //
			@RequestParam(required = true) Long employeeId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { employeeId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Timesheet Breakdown Detail", fromDate, toDate, parameters, alternateFormat);
		//
	}
	
	//
	@RequestMapping(value = "/NewUpdatedTimesheetRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData NewUpdatedTimesheetRecords(//
			//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "New/Updated Timesheet Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	// do not remove this
	@ApiIgnore
	@RequestMapping(value = "/EDIApprovedTimesheets", method = RequestMethod.GET, produces = "application/json")
	public IBiData EDIApprovedTimesheets(//
			//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "EDI Approved Timesheets", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/OnBoardingDocumentsbyCandidate", method = RequestMethod.GET, produces = "application/json")
	public IBiData OnBoardingDocumentsbyCandidate(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "OnBoarding Documents by Candidate", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ProfitabilityReport", method = RequestMethod.GET, produces = "application/json")
	public IBiData ProfitabilityReport(//
			//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Profitability Report", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/SOWList", method = RequestMethod.GET, produces = "application/json")
	public IBiData SOWList(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "SOW List", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/JobDivaMatch", method = RequestMethod.GET, produces = "application/json")
	public IBiData JobDivaMatch(//
			//
			@ApiParam(value = "Candidate Id", required = true) //
			@RequestParam(required = true) Long candidateId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { candidateId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "JobDiva Match", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ActionTypeList", method = RequestMethod.GET, produces = "application/json")
	public IBiData ActionTypeList(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Action Type List", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/DeletedBillingRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData DeletedBillingRecords(//
			//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Billing Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	//
	@RequestMapping(value = "/DeletedSalaryRecords", method = RequestMethod.GET, produces = "application/json")
	public IBiData DeletedSalaryRecords(//
			//
			@ApiParam(value = "From Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date fromDate, //
			//
			@ApiParam(value = "To Date Format(MM/dd/yyyy HH:mm:ss)", required = true) //
			@RequestParam(required = true) Date toDate, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Deleted Salary Records", fromDate, toDate, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/ResumeSourceList", method = RequestMethod.GET, produces = "application/json")
	public IBiData ResumeSourceDetail(//
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		return biDataService.getBIData(jobDivaSession, "Resume Source List", null, null, null, alternateFormat);
		//
	}
	
	@RequestMapping(value = "/StartRecordAdditionalContacts", method = RequestMethod.GET, produces = "application/json")
	public IBiData StartRecordAdditionalContacts(//
			//
			@ApiParam(value = "Start Id", required = true) //
			@RequestParam(required = true) Long startId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = new String[] { startId.toString() };
		//
		return biDataService.getBIData(jobDivaSession, "Start Record Additional Contacts", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = { "/TaskDetail" }, method = { RequestMethod.GET }, produces = { "application/json" })
	public IBiData TaskDetail(//
			//
			@ApiParam(value = "Task Id", required = true) //
			@RequestParam(required = true) Long taskId, //
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat)//
			//
			throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = { taskId.toString() };
		//
		return this.biDataService.getBIData(jobDivaSession, "Task Detail", null, null, parameters, alternateFormat);
		//
	}
	
	@RequestMapping(value = { "/EventDetail" }, method = { RequestMethod.GET }, produces = { "application/json" })
	public IBiData EventDetail(//
			//
			@ApiParam(value = "Event Id", required = true) //
			@RequestParam(required = true) Long eventId,
			//
			@ApiParam(value = "Alternate Format", required = false) //
			@RequestParam(required = false) Boolean alternateFormat) //
			//
			throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		String[] parameters = { eventId.toString() };
		//
		return this.biDataService.getBIData(jobDivaSession, "Event Detail", null, null, parameters, alternateFormat);
		//
	}
}

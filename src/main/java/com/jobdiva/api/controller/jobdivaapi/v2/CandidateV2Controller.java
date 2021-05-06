package com.jobdiva.api.controller.jobdivaapi.v2;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.Candidate;
import com.jobdiva.api.model.CandidateQual;
import com.jobdiva.api.model.PhoneType;
import com.jobdiva.api.model.QualificationType;
import com.jobdiva.api.model.SocialNetworkType;
import com.jobdiva.api.model.TitleSkillCertification;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.v2.candidate.CandidateEmailMergeDef;
import com.jobdiva.api.model.v2.candidate.CandidateReferenceDef;
import com.jobdiva.api.model.v2.candidate.CreateCandidateNoteDef;
import com.jobdiva.api.model.v2.candidate.CreateCandidateProfileDef;
import com.jobdiva.api.model.v2.candidate.SearchCandidateProfileDef;
import com.jobdiva.api.model.v2.candidate.UpdateCandidateAvailabilityDef;
import com.jobdiva.api.model.v2.candidate.UpdateCandidateHRRecordDef;
import com.jobdiva.api.model.v2.candidate.UpdateCandidateProfileDef;
import com.jobdiva.api.model.v2.candidate.UpdateCandidateQualificationsDef;
import com.jobdiva.api.model.v2.candidate.UpdateCandidateSNLinksDef;
import com.jobdiva.api.model.v2.candidate.UpdateCandidateUserfields;
import com.jobdiva.api.service.CandidateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@RequestMapping("/apiv2/jobdiva/")
@Api(value = "Candidate API", description = "REST API Used For Candidate")
@ApiIgnore
public class CandidateV2Controller extends AbstractJobDivaController {
	
	@Autowired
	CandidateService candidateService;
	
	//
	@RequestMapping(value = "/searchCandidateProfile", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Candidate Profile")
	public List<Candidate> searchCandidateProfile( //
			//
			@ApiParam(" firstName : Candidate First Name \r\n" //
					+ "lastName   : Candidate Last Name \r\n" //
					+ "email 	  : Candidate Email \r\n" //
					+ "phone 	  : Any candidate phone number \r\n" //
					+ "city 	  : Candidate City \r\n" //
					+ "state 	  : Candidate State \r\n" //
					+ "address 	  : Address \r\n" //
					+ "zipCode 	  : Zip Code \r\n" //
					+ "country 	  : Country \r\n" //
					+ "candidateQuals : CandidateQuals \r\n" //
					+ "maxreturned 	  : Maximum number of returned results") //
			@RequestBody SearchCandidateProfileDef searchCandidateProfileDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("searchCandidateProfile");
		//
		String firstName = searchCandidateProfileDef.getFirstName();
		String lastName = searchCandidateProfileDef.getLastName();
		String email = searchCandidateProfileDef.getEmail();
		String phone = searchCandidateProfileDef.getPhone();
		String city = searchCandidateProfileDef.getCity();
		String state = searchCandidateProfileDef.getState();
		String address = searchCandidateProfileDef.getAddress();
		String zipCode = searchCandidateProfileDef.getZipCode();
		String country = searchCandidateProfileDef.getCountry();
		CandidateQual[] candidateQuals = searchCandidateProfileDef.getCandidateQuals();
		Integer maxreturned = searchCandidateProfileDef.getMaxreturned();
		//
		return candidateService.searchCandidateProfile(jobDivaSession, firstName, lastName, address, city, state, zipCode, country, email, phone, candidateQuals, maxreturned);
		//
	}
	
	@RequestMapping(value = "/createCandidate", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Candidate")
	public Long createCandidate( //
			//
			@ApiParam("firstName : Candidate First Name \r\n " //
					+ "lastName : Candidate Last Name \r\n" //
					+ "email : Candidate Email \r\n" //
					+ "alternateemail : Candidate Alternative Email \r\n" //
					+ "address1 : Address 1 \r\n" //
					+ "address2 : Address 2 \r\n" //
					+ "city : Candidate City \r\n" //
					+ "state : Candidate State \r\n" //
					+ "zipCode : Zip Code \r\n" //
					+ "countryid : CountryId \r\n" //
					+ "homephone : Home Phone \r\n" //
					+ "workphone : Work Phone \r\n" //
					+ "cellphone : Cell Phone \r\n" //
					+ "fax : Fax \r\n" //
					+ "currentsalary : Current Salary \r\n" //
					+ "currentsalaryunit : Current Salary Unit \r\n" //
					+ "preferredsalary : Preferred Salary \r\n" //
					+ "preferredsalaryunit : Preferred Salary Unit \r\n" //
					+ "narrative : Narrative \r\n" //
					+ "titleskillcertifications : • “titleskillcertification” – Titles, skills, certifications, or experiences \r\n" + "• “years” is ignored if “startdate” is set \r\n" + "• “enddate” defaults to current date if it’s not set \r\n " //
					+ "titleskillcertification : The name of the title, skill, or certification \r\n" //
					+ "startdate : Start Date format(yyyy-MM-dd'T'HH:mm:ss) \r\n" + "enddate : End Date format(yyyy-MM-dd'T'HH:mm:ss) \r\n" + "years : Years \r\n" + "resumeSource : Resume Source \r\n") //
			@RequestBody CreateCandidateProfileDef createCandidateProfileDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createCandidate");
		//
		//
		String firstName = createCandidateProfileDef.getFirstName();
		String lastName = createCandidateProfileDef.getLastName();
		String email = createCandidateProfileDef.getEmail();
		String alternateemail = createCandidateProfileDef.getAlternateemail();
		String address1 = createCandidateProfileDef.getAddress1();
		String address2 = createCandidateProfileDef.getAddress2();
		String city = createCandidateProfileDef.getCity();
		String state = createCandidateProfileDef.getState();
		String zipCode = createCandidateProfileDef.getZipCode();
		String countryid = createCandidateProfileDef.getCountryid();
		String homephone = createCandidateProfileDef.getHomephone();
		String workphone = createCandidateProfileDef.getWorkphone();
		String cellphone = createCandidateProfileDef.getCellphone();
		String fax = createCandidateProfileDef.getFax();
		Double currentsalary = createCandidateProfileDef.getCurrentsalary();
		String currentsalaryunit = createCandidateProfileDef.getCurrentsalaryunit();
		Double preferredsalary = createCandidateProfileDef.getPreferredsalary();
		String preferredsalaryunit = createCandidateProfileDef.getPreferredsalaryunit();
		String narrative = createCandidateProfileDef.getNarrative();
		TitleSkillCertification[] titleskillcertifications = createCandidateProfileDef.getTitleskillcertifications();
		String titleskillcertification = createCandidateProfileDef.getTitleskillcertification();
		Date startdate = createCandidateProfileDef.getStartdate();
		Date enddate = createCandidateProfileDef.getEnddate();
		Integer years = createCandidateProfileDef.getYears();
		Integer resumeSource = createCandidateProfileDef.getResumeSource();//
		//
		return candidateService.createCandidate(jobDivaSession, firstName, lastName, email, alternateemail, address1, address2, city, state, zipCode, countryid, homephone, workphone, cellphone, fax, currentsalary, currentsalaryunit, preferredsalary,
				preferredsalaryunit, narrative, titleskillcertifications, titleskillcertification, startdate, enddate, years, resumeSource);
		//
	}
	//
	
	@RequestMapping(value = "/updateCandidateProfile", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Profile")
	public Boolean updateCandidateProfile( //
			//
			@ApiParam(value = "candidateid : Candidate ID \r\n" //
					+ "firstName : Candidate First Name \r\n" //
					+ "lastName : Candidate Last Name \r\n" //
					+ "email : Candidate Email \r\n" //
					+ "alternateemail : Alternate Email \r\n" //
					+ "address1 : Address 1 \r\n" //
					+ "address2 : Address 2 \r\n" //
					+ "city : Candidate city \r\n" //
					+ "state : Candidate state \r\n" //
					+ "zipCode : Zip Code \r\n" //
					+ "countryid : Country Id \r\n" //
					+ "phones : Phones \r\n" //
					+ "currentsalary : Current Salary \r\n" //
					+ "currentsalaryunit : Current Salary Unit \r\n" //
					+ "preferredsalary : Preferred Salary \r\n" //
					+ "preferredsalaryunit : Preferred Salary Unit \r\n" //
			) //
			@RequestBody UpdateCandidateProfileDef updateCandidateProfileDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateProfile");
		//
		//
		//
		Long candidateid = updateCandidateProfileDef.getCandidateid();
		String firstName = updateCandidateProfileDef.getFirstName();
		String lastName = updateCandidateProfileDef.getLastName();
		String email = updateCandidateProfileDef.getEmail();
		String alternateemail = updateCandidateProfileDef.getAlternateemail();
		String address1 = updateCandidateProfileDef.getAddress1();
		String address2 = updateCandidateProfileDef.getAddress2();
		String city = updateCandidateProfileDef.getCity();
		String state = updateCandidateProfileDef.getState();
		String zipCode = updateCandidateProfileDef.getZipCode();
		String countryid = updateCandidateProfileDef.getCountryid();
		PhoneType[] phones = updateCandidateProfileDef.getPhones();
		Double currentsalary = updateCandidateProfileDef.getCurrentsalary();
		String currentsalaryunit = updateCandidateProfileDef.getCurrentsalaryunit();
		Double preferredsalary = updateCandidateProfileDef.getPreferredsalary();
		String preferredsalaryunit = updateCandidateProfileDef.getPreferredsalaryunit();
		//
		return candidateService.updateCandidateProfile(jobDivaSession, candidateid, firstName, lastName, email, alternateemail, address1, address2, city, state, zipCode, countryid, phones, currentsalary, currentsalaryunit, preferredsalary,
				preferredsalaryunit);
		//
	}
	//
	
	@RequestMapping(value = "/updateCandidateUserfields", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Userfields")
	public Boolean updateCandidateUserfields( //
			//
			@ApiParam(value = "candidateid = Candidate ID" //
					+ "overwrite : Set overwrite to true for overwriting all user-defined fields" //
					+ "userfields : User defined fields. Valid “userfieldid” can be found through “Leader Tools” → “My Team” → “User-Defined Fields” → “Contacts” → “UDF #”\r\n" + //
					"• Set “userfieldvalue” as “mm/dd/yyyy” format for Date type, “mm/dd/yyyy hh:mm” format for Date/Time type.\r\n" + //
					"• “userfieldvalue” is case sensitive.\r\n" + //
					"• For multiple selections, separate choices with commas.") //
			//
			@RequestBody UpdateCandidateUserfields updateCandidateUserfields) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateUserfields");
		//
		Long candidateid = updateCandidateUserfields.getCandidateid();
		Boolean overwrite = updateCandidateUserfields.getOverwrite();
		Userfield[] userfields = updateCandidateUserfields.getUserfields();
		//
		return candidateService.updateCandidateUserfields(jobDivaSession, candidateid, overwrite, userfields);
		//
	}
	
	//
	@RequestMapping(value = "/updateCandidateQualifications", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Qualifications")
	public Boolean updateCandidateQualifications( //
			//
			@ApiParam(value = "candidateid : Candidate ID \r\n" //
					+ "overwrite : Set overwrite to true for overwriting all qualifications \r\n"//
					+ "qualifications : Qualifications. Valid “qulificationvalue” can be found through "//
					+ " “Leader Tools” → “My Team” → “Qualifications.\r\n "//
					+ " • “qulificationvalue” is case sensitive. \r\n"//
					+ " • For multiple selections, separate choices with commas. "//
			) //
			@RequestBody UpdateCandidateQualificationsDef updateCandidateQualificationsDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateQualifications");
		//
		Long candidateid = updateCandidateQualificationsDef.getCandidateid();
		Boolean overwrite = updateCandidateQualificationsDef.getOverwrite();
		QualificationType[] qualifications = updateCandidateQualificationsDef.getQualifications();
		//
		return candidateService.updateCandidateQualifications(jobDivaSession, candidateid, overwrite, qualifications);
		//
	}
	//
	
	@RequestMapping(value = "/updateCandidateAvailability", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Availability")
	public Boolean updateCandidateAvailability( //
			//
			@ApiParam(value = "candidateid : Candidate ID" //
					+ "availablenow : Set candidate available" //
					+ "unavailableindef : Set candidate unavailable indefinitely" //
					+ "unavailableuntil : Set candidate unavailable untill “unavailableuntildate” " //
					+ "unavailableuntildate : Set a date in the future, valid only when “unavailableuntil” is set as true.date Format [yyyy-MM-dd'T'HH:mm:ss]" //
					+ "reason : Must set if either “unavailableindef” or “unavailableuntil” is set.") //
			//
			@RequestBody UpdateCandidateAvailabilityDef updateCandidateAvailabilityDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateAvailability");
		//
		//
		Long candidateid = updateCandidateAvailabilityDef.getCandidateid();
		Boolean availablenow = updateCandidateAvailabilityDef.getAvailablenow();
		Boolean unavailableindef = updateCandidateAvailabilityDef.getUnavailableindef();
		Boolean unavailableuntil = updateCandidateAvailabilityDef.getUnavailableuntil();
		Date unavailableuntildate = updateCandidateAvailabilityDef.getUnavailableuntildate();
		String reason = updateCandidateAvailabilityDef.getReason();
		//
		return candidateService.updateCandidateAvailability(jobDivaSession, candidateid, availablenow, unavailableindef, unavailableuntil, unavailableuntildate, reason);
		//
	}
	
	@RequestMapping(value = "/createCandidateNote", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Candidate Note")
	public Long createCandidateNote( //
			//
			@ApiParam(value = "candidateid : Candidate ID \r\n" //
					+ "note : Note \r\n"//
					+ "recruiterid : Recruiter Id \r\n"//
					+ "action : action \r\n"//
					+ "actionDate : Action Date format(yyyy-MM-dd'T'HH:mm:ss) \r\n"//
					+ "link2AnOpenJob : link to an open job \r\n"//
					+ "link2AContact : Link to a contact \r\n"//
					+ "setAsAuto : Set As Auto"//
			) //
			@RequestBody CreateCandidateNoteDef createCandidateNoteDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createCandidateNote");
		//
		Long candidateid = createCandidateNoteDef.getCandidateid();
		String note = createCandidateNoteDef.getNote();
		Long recruiterid = createCandidateNoteDef.getRecruiterid();
		String action = createCandidateNoteDef.getAction();
		Date actionDate = createCandidateNoteDef.getActionDate();
		Long link2AnOpenJob = createCandidateNoteDef.getLink2AnOpenJob();
		Long link2AContact = createCandidateNoteDef.getLink2AContact();
		Boolean setAsAuto = createCandidateNoteDef.getSetAsAuto();
		//
		return candidateService.createCandidateNote(jobDivaSession, candidateid, note, recruiterid, action, actionDate, link2AnOpenJob, link2AContact, setAsAuto, null);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/updateCandidateHRRecord", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate HR Record")
	public Boolean updateCandidateHRRecord( //
			//
			@ApiParam(value = "candidateid : Candidate ID \r\n" //
					+ "dateofbirth : Date Of Birth format(MM/dd/yyyy HH:mm:ss) \r\n" //
					+ "legalfirstname : Legal First Name \r\n" //
					+ "legallastname : Legal Last Name \r\n" //
					+ "legalmiddlename : Legal Middle Name \r\n" //
					+ "suffix : Suffix \r\n" //
					+ "maritalstatus : Marital Status \r\n" //
					+ "ssn : SSN \r\n" //
					+ "visastatus : Visa Status ") //
			@RequestBody UpdateCandidateHRRecordDef updateCandidateHRRecordDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateHRRecord");
		//
		Long candidateid = updateCandidateHRRecordDef.getCandidateid();
		Date dateofbirth = updateCandidateHRRecordDef.getDateofbirth();
		String legalfirstname = updateCandidateHRRecordDef.getLegalfirstname();
		String legallastname = updateCandidateHRRecordDef.getLegallastname();
		String legalmiddlename = updateCandidateHRRecordDef.getLegalmiddlename();
		String suffix = updateCandidateHRRecordDef.getSuffix();
		Integer maritalstatus = updateCandidateHRRecordDef.getMaritalstatus();
		String ssn = updateCandidateHRRecordDef.getSsn();
		String visastatus = updateCandidateHRRecordDef.getVisastatus();
		//
		return candidateService.updateCandidateHRRecord(jobDivaSession, candidateid, dateofbirth, legalfirstname, legallastname, legalmiddlename, suffix, maritalstatus, ssn, visastatus);
		//
	}
	
	@RequestMapping(value = "/createCandidateReference", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Candidate Reference")
	public Boolean createCandidateReference( //
			//
			@ApiParam(value = "candidateid : Candidate ID \r\n"//
					+ "contactid : Contact ID \r\n"//
					+ "createdByRecruiterid : Created By Recruiter Id \r\n"//
					+ "checkedByRecruiterid : Checked By Recruiter Id \r\n"//
					+ "dateChecked : Date Checked format(yyyy-MM-dd'T'HH:mm:ss) \r\n"//
					+ "notes : Notes \r\n"//
					+ "standardQuestions : Standard Questions" //
			) //
			@RequestBody CandidateReferenceDef CandidateReferenceDef
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createCandidateReference");
		//
		Long candidateid = CandidateReferenceDef.getCandidateid();
		Long contactid = CandidateReferenceDef.getContactid();
		Long createdByRecruiterid = CandidateReferenceDef.getCreatedByRecruiterid();
		Long checkedByRecruiterid = CandidateReferenceDef.getCheckedByRecruiterid();
		Date dateChecked = CandidateReferenceDef.getDateChecked();
		String notes = CandidateReferenceDef.getNotes();
		String standardQuestions = CandidateReferenceDef.getStandardQuestions();
		return candidateService.createCandidateReference(jobDivaSession, candidateid, contactid, createdByRecruiterid, checkedByRecruiterid, dateChecked, notes, standardQuestions);
		//
	}
	
	@RequestMapping(value = "/updateCandidateEmailMerge", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Email Merge")
	public Boolean updateCandidateEmailMerge( //
			//
			@ApiParam(value = "candidateid : Candidate ID \r\n "//
					+ "backonemailmerge : Backon Email Merge \r\n" //
					+ "requestoffemailindef : Request Off Email in def \r\n" //
					+ "requestoffemailuntil : Request Off Email Until \r\n" //
					+ "requestoffemailuntildate : Request Off Email Until Date format(yyyy-MM-dd'T'HH:mm:ss) \r\n " //
					+ "reason : Reason " //
			) //
			@RequestBody CandidateEmailMergeDef candidateEmailMergeDef
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateEmailMerge");
		//
		Long candidateid = candidateEmailMergeDef.getCandidateid();
		Boolean backonemailmerge = candidateEmailMergeDef.getBackonemailmerge();
		Boolean requestoffemailindef = candidateEmailMergeDef.getRequestoffemailindef();
		Boolean requestoffemailuntil = candidateEmailMergeDef.getRequestoffemailuntil();
		Date requestoffemailuntildate = candidateEmailMergeDef.getRequestoffemailuntildate();
		String reason = candidateEmailMergeDef.getReason();
		//
		return candidateService.updateCandidateEmailMerge(jobDivaSession, candidateid, backonemailmerge, requestoffemailindef, requestoffemailuntil, requestoffemailuntildate, reason);
		//
	}
	//
	
	@RequestMapping(value = "/updateCandidateSNLinks", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Social Network Links")
	public Boolean updateCandidateSNLinks( //
			//
			@ApiParam(value = "candidateid : Candidate ID \r\n" //
					+ "socialnetworks : SocialNetworkType") //
			@RequestBody UpdateCandidateSNLinksDef UpdateCandidateSNLinksDef //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateSNLinks");
		//
		//
		Long candidateid = UpdateCandidateSNLinksDef.getCandidateid();
		SocialNetworkType[] socialnetworks = UpdateCandidateSNLinksDef.getSocialnetworks();
		//
		return candidateService.updateCandidateSNLinks(jobDivaSession, candidateid, socialnetworks);
		//
	}
}
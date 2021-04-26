package com.jobdiva.api.controller.jobdivaapi;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.Candidate;
import com.jobdiva.api.model.CandidateQual;
import com.jobdiva.api.model.NoteType;
import com.jobdiva.api.model.PhoneType;
import com.jobdiva.api.model.QualificationType;
import com.jobdiva.api.model.SocialNetworkType;
import com.jobdiva.api.model.TitleSkillCertification;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.service.CandidateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "Candidate API", description = "REST API Used For Candidate")
public class CandidateController extends AbstractJobDivaController {
	
	@Autowired
	CandidateService candidateService;
	
	//
	@ApiImplicitParams({ @ApiImplicitParam(name = "candidateQuals", required = false, allowMultiple = true, dataType = "CandidateQual") })
	@RequestMapping(value = "/searchCandidateProfile", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Candidate Profile")
	public List<Candidate> searchCandidateProfile( //
			//
			@ApiParam(value = "Candidate First Name", required = false) //
			@RequestParam(required = false) String firstName, //
			//
			@ApiParam(value = "Candidate Last Name", required = false) //
			@RequestParam(required = false) String lastName, //
			//
			@ApiParam(value = "Candidate Email", required = false) //
			@RequestParam(required = false) String email, //
			//
			@ApiParam(value = "Any candidate phone number", required = false) //
			@RequestParam(required = false) String phone, //
			//
			@ApiParam(value = "Candidate City", required = false) //
			@RequestParam(required = false) String city, //
			//
			@ApiParam(value = "Candidate State", required = false) //
			@RequestParam(required = false) String state, //
			//
			@ApiParam(value = "Address", required = false) //
			@RequestParam(required = false) String address, //
			//
			@ApiParam(value = "Zip Code", required = false) //
			@RequestParam(required = false) String zipCode, //
			//
			@ApiParam(value = "Country", required = false) //
			@RequestParam(required = false) String country, //
			//
			@ApiParam(value = "CandidateQuals", required = false, type = "CandidateQual", allowMultiple = true) //
			@RequestParam(required = false) CandidateQual[] candidateQuals, //
			//
			@ApiParam(value = "Maximum number of returned results", required = false) //
			@RequestParam(required = false) Integer maxreturned //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("searchCandidateProfile");
		//
		return candidateService.searchCandidateProfile(jobDivaSession, firstName, lastName, address, city, state, zipCode, country, email, phone, candidateQuals, maxreturned);
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "titleskillcertifications", required = false, allowMultiple = true, dataType = "TitleSkillCertification") //
			//
	})
	@RequestMapping(value = "/createCandidate", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Candidate")
	public Long createCandidate( //
			//
			@ApiParam(value = "Candidate First Name", required = true) //
			@RequestParam(required = true) String firstName, //
			//
			@ApiParam(value = "Candidate Last Name", required = true) //
			@RequestParam(required = true) String lastName, //
			//
			@ApiParam(value = "Candidate Email", required = false) //
			@RequestParam(required = false) String email, //
			//
			@ApiParam(value = "Candidate Alternative Email", required = false) //
			@RequestParam(required = false) String alternateemail, //
			//
			@ApiParam(value = "Address 1", required = false) //
			@RequestParam(required = false) String address1, //
			//
			@ApiParam(value = "Address 2", required = false) //
			@RequestParam(required = false) String address2, //
			//
			@ApiParam(value = "Candidate City", required = false) //
			@RequestParam(required = false) String city, //
			//
			@ApiParam(value = "Candidate State", required = false) //
			@RequestParam(required = false) String state, //
			//
			@ApiParam(value = "Zip Code", required = false) //
			@RequestParam(required = false) String zipCode, //
			//
			@ApiParam(value = "CountryId", required = false) //
			@RequestParam(required = false) String countryid, //
			//
			@ApiParam(value = "Home Phone", required = false) //
			@RequestParam(required = false) String homephone, //
			//
			@ApiParam(value = "Work Phone", required = false) //
			@RequestParam(required = false) String workphone, //
			//
			@ApiParam(value = "Cell Phone", required = false) //
			@RequestParam(required = false) String cellphone, //
			//
			@ApiParam(value = "Fax", required = false) //
			@RequestParam(required = false) String fax, //
			//
			@ApiParam(value = "Current Salary", required = false) //
			@RequestParam(required = false) Double currentsalary, //
			//
			@ApiParam(value = "Current Salary Unit", required = false) //
			@RequestParam(required = false) String currentsalaryunit, //
			//
			@ApiParam(value = "Preferred Salary", required = false) //
			@RequestParam(required = false) Double preferredsalary, //
			//
			@ApiParam(value = "Preferred Salary Unit", required = false) //
			@RequestParam(required = false) String preferredsalaryunit, //
			//
			@ApiParam(value = "Narrative", required = false) //
			@RequestParam(required = false) String narrative, //
			//
			@ApiParam(value = "• “titleskillcertification” – Titles, skills, certifications, or experiences\r\n" + "• “years” is ignored if “startdate” is set\r\n"
					+ "• “enddate” defaults to current date if it’s not set", required = false, type = "TitleSkillCertification", allowMultiple = true) //
			@RequestParam(required = false) TitleSkillCertification[] titleskillcertifications, //
			//
			@ApiParam(value = "The name of the title, skill, or certification", required = false, type = "SocialNetwork", allowMultiple = true) //
			@RequestParam(required = false) String titleskillcertification, //
			//
			@ApiParam(value = "Start Date format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date startdate, //
			//
			@ApiParam(value = "End Date format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date enddate, //
			//
			@ApiParam(value = "Years", required = false) //
			@RequestParam(required = false) Integer years, //
			//
			@ApiParam(value = "Resume Source", required = false) //
			@RequestParam(required = false) Integer resumeSource //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createCandidate");
		//
		return candidateService.createCandidate(jobDivaSession, firstName, lastName, email, alternateemail, address1, address2, city, state, zipCode, countryid, homephone, workphone, cellphone, fax, currentsalary, currentsalaryunit, preferredsalary,
				preferredsalaryunit, narrative, titleskillcertifications, titleskillcertification, startdate, enddate, years, resumeSource);
		//
	}
	
	//
	@ApiImplicitParams({ @ApiImplicitParam(name = "phones", required = false, allowMultiple = true, dataType = "PhoneType") //
	})
	@RequestMapping(value = "/updateCandidateProfile", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Profile")
	public Boolean updateCandidateProfile( //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Candidate First Name", required = false) //
			@RequestParam(required = false) String firstName, //
			//
			@ApiParam(value = "Candidate Last Name", required = false) //
			@RequestParam(required = false) String lastName, //
			//
			@ApiParam(value = "Candidate Email", required = false) //
			@RequestParam(required = false) String email, //
			//
			@ApiParam(value = "Alternate Email", required = false) //
			@RequestParam(required = false) String alternateemail, //
			//
			@ApiParam(value = "aAddress 1", required = false) //
			@RequestParam(required = false) String address1, //
			//
			@ApiParam(value = "Address 2", required = false) //
			@RequestParam(required = false) String address2, //
			//
			@ApiParam(value = "Candidate city", required = false) //
			@RequestParam(required = false) String city, //
			//
			@ApiParam(value = "Candidate state", required = false) //
			@RequestParam(required = false) String state, //
			//
			@ApiParam(value = "Zip Code", required = false) //
			@RequestParam(required = false) String zipCode, //
			//
			@ApiParam(value = "Country Id", required = false) //
			@RequestParam(required = false) String countryid, //
			//
			@ApiParam(value = "Phones", required = false, type = "PhoneType", allowMultiple = true) //
			@RequestParam(required = false) PhoneType[] phones, //
			//
			@ApiParam(value = "Current Salary", required = false) //
			@RequestParam(required = false) Double currentsalary, //
			//
			@ApiParam(value = "Current Salary Unit", required = false) //
			@RequestParam(required = false) String currentsalaryunit, //
			//
			@ApiParam(value = "Preferred Salary", required = false) //
			@RequestParam(required = false) Double preferredsalary, //
			//
			@ApiParam(value = "Preferred Salary Unit", required = false) //
			@RequestParam(required = false) String preferredsalaryunit //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateProfile");
		//
		return candidateService.updateCandidateProfile(jobDivaSession, candidateid, firstName, lastName, email, alternateemail, address1, address2, city, state, zipCode, countryid, phones, currentsalary, currentsalaryunit, preferredsalary,
				preferredsalaryunit);
		//
	}
	
	//
	@ApiImplicitParams({ @ApiImplicitParam(name = "Userfields", required = true, allowMultiple = true, dataType = "Userfield") //
	})
	@RequestMapping(value = "/updateCandidateUserfields", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Userfields")
	public Boolean updateCandidateUserfields( //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Set overwrite to true for overwriting all user-defined fields", required = true) //
			@RequestParam(required = true) Boolean overwrite, //
			//
			@ApiParam(value = "User defined fields. Valid “userfieldid” can be found through “Leader Tools” → “My Team” → “User-Defined Fields” → “Contacts” → “UDF #”\r\n" + //
					"• Set “userfieldvalue” as “mm/dd/yyyy” format for Date type, “mm/dd/yyyy hh:mm” format for Date/Time type.\r\n" + //
					"• “userfieldvalue” is case sensitive.\r\n" + //
					"• For multiple selections, separate choices with commas.", required = true, type = "Userfield", allowMultiple = true) //
			@RequestParam(required = true) Userfield[] Userfields //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateUserfields");
		//
		return candidateService.updateCandidateUserfields(jobDivaSession, candidateid, overwrite, Userfields);
		//
	}
	
	//
	@ApiImplicitParams({ @ApiImplicitParam(name = "Qualifications", required = false, allowMultiple = true, dataType = "QualificationType") //
	})
	@RequestMapping(value = "/updateCandidateQualifications", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Qualifications")
	public Boolean updateCandidateQualifications( //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Set overwrite to true for overwriting all qualifications", required = true) //
			@RequestParam(required = true) Boolean overwrite, //
			//
			@ApiParam(value = "Qualifications. Valid “qulificationvalue” can be found through “Leader Tools” → “My Team” → “Qualifications.\r\n • “qulificationvalue” is case sensitive.\r\n" + //
					"• For multiple selections, separate choices with commas. ", required = false, type = "QualificationType", allowMultiple = true) //
			@RequestParam(required = false) QualificationType[] Qualifications //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateQualifications");
		//
		return candidateService.updateCandidateQualifications(jobDivaSession, candidateid, overwrite, Qualifications);
		//
	}
	
	//
	@RequestMapping(value = "/updateCandidateAvailability", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Availability")
	public Boolean updateCandidateAvailability( //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Set candidate available", required = false) //
			@RequestParam(required = false) Boolean availablenow, //
			//
			@ApiParam(value = "Set candidate unavailable indefinitely", required = false) //
			@RequestParam(required = false) Boolean unavailableindef, //
			//
			@ApiParam(value = "Set candidate unavailable untill “unavailableuntildate”", required = false) //
			@RequestParam(required = false) Boolean unavailableuntil, //
			//
			@ApiParam(value = "Set a date in the future, valid only when “unavailableuntil” is set as true.date Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date unavailableuntildate, //
			//
			@ApiParam(value = "Must set if either “unavailableindef” or “unavailableuntil” is set.", required = false) //
			@RequestParam(required = false) String reason //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateAvailability");
		//
		return candidateService.updateCandidateAvailability(jobDivaSession, candidateid, availablenow, unavailableindef, unavailableuntil, unavailableuntildate, reason);
		//
	}
	
	@RequestMapping(value = "/createCandidateNote", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Candidate Note")
	public Long createCandidateNote( //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Note", required = true) //
			@RequestParam(required = true) String note, //
			//
			@ApiParam(value = "Recruiter Id", required = false) //
			@RequestParam(required = false) Long recruiterid, //
			//
			@ApiParam(value = "action", required = false) //
			@RequestParam(required = false) String action, //
			//
			@ApiParam(value = "Action Date format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date actionDate, //
			//
			@ApiParam(value = "link to an open job", required = false) //
			@RequestParam(required = false) Long link2AnOpenJob, //
			//
			@ApiParam(value = "Link to a contact", required = false) //
			@RequestParam(required = false) Long link2AContact, //
			//
			@ApiParam(value = "Set As Auto", required = false) //
			@RequestParam(required = false) Boolean setAsAuto //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createCandidateNote");
		//
		return candidateService.createCandidateNote(jobDivaSession, candidateid, note, recruiterid, action, actionDate, link2AnOpenJob, link2AContact, setAsAuto, null);
		//
	}
	
	@ApiIgnore
	@RequestMapping(value = "/createCandidateNoteWithCreateDate", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Candidate Note With CreateDate")
	public Long createCandidateNoteWithCreateDate( //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Note", required = true) //
			//
			// @RequestParam(required = true)
			@RequestBody(required = true) String note, //
			//
			@ApiParam(value = "Recruiter Id", required = false) //
			@RequestParam(required = false) Long recruiterid, //
			//
			@ApiParam(value = "action", required = false) //
			@RequestParam(required = false) String action, //
			//
			@ApiParam(value = "Action Date format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date actionDate, //
			//
			@ApiParam(value = "Create Date format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date createDate, //
			//
			@ApiParam(value = "link to an open job", required = false) //
			@RequestParam(required = false) Long link2AnOpenJob, //
			//
			@ApiParam(value = "Link to a contact", required = false) //
			@RequestParam(required = false) Long link2AContact, //
			//
			@ApiParam(value = "Set As Auto", required = false) //
			@RequestParam(required = false) Boolean setAsAuto //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		// jobDivaSession.checkForAPIPermission("createCandidateNoteWithCreateDate");
		//
		return candidateService.createCandidateNote(jobDivaSession, candidateid, note, recruiterid, action, actionDate, link2AnOpenJob, link2AContact, setAsAuto, createDate);
		//
	}
	
	@RequestMapping(value = "/updateCandidateHRRecord", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate HR Record")
	public Boolean updateCandidateHRRecord( //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Date Of Birth format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date dateofbirth, //
			//
			@ApiParam(value = "Legal First Name", required = false) //
			@RequestParam(required = false) String legalfirstname, //
			//
			@ApiParam(value = "Legal Last Name", required = false) //
			@RequestParam(required = false) String legallastname, //
			//
			@ApiParam(value = "Legal Middle Name", required = false) //
			@RequestParam(required = false) String legalmiddlename, //
			//
			@ApiParam(value = "Suffix", required = false) //
			@RequestParam(required = false) String suffix, //
			//
			@ApiParam(value = "Marital Status", required = false) //
			@RequestParam(required = false) Integer maritalstatus, //
			//
			@ApiParam(value = "SSN", required = false) //
			@RequestParam(required = false) String ssn, //
			//
			@ApiParam(value = "Visa Status", required = false) //
			@RequestParam(required = false) String visastatus //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateHRRecord");
		//
		return candidateService.updateCandidateHRRecord(jobDivaSession, candidateid, dateofbirth, legalfirstname, legallastname, legalmiddlename, suffix, maritalstatus, ssn, visastatus);
		//
	}
	
	@RequestMapping(value = "/createCandidateReference", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Candidate Reference")
	public Boolean createCandidateReference( //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Contact ID", required = true) //
			@RequestParam(required = true) Long contactid, //
			//
			@ApiParam(value = "Created By Recruiter Id", required = true) //
			@RequestParam(required = true) Long createdByRecruiterid, //
			//
			@ApiParam(value = "Checked By Recruiter Id", required = false) //
			@RequestParam(required = false) Long checkedByRecruiterid, //
			@ApiParam(value = "Date Checked format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date dateChecked, //
			//
			@ApiParam(value = "Notes", required = false) //
			@RequestParam(required = false) String notes, //
			//
			@ApiParam(value = "Standard Questions", required = false) //
			@RequestParam(required = false) String standardQuestions //
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createCandidateReference");
		//
		return candidateService.createCandidateReference(jobDivaSession, candidateid, contactid, createdByRecruiterid, checkedByRecruiterid, dateChecked, notes, standardQuestions);
		//
	}
	
	@RequestMapping(value = "/updateCandidateEmailMerge", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Email Merge")
	public Boolean updateCandidateEmailMerge( //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "Backon Email Merge", required = false) //
			@RequestParam(required = false) Boolean backonemailmerge, //
			//
			@ApiParam(value = "Request Off Email in def", required = false) //
			@RequestParam(required = false) Boolean requestoffemailindef, //
			//
			@ApiParam(value = "Request Off Email Until", required = false) //
			@RequestParam(required = false) Boolean requestoffemailuntil, //
			//
			@ApiParam(value = "Request Off Email Until Date format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date requestoffemailuntildate, //
			//
			@ApiParam(value = "Reason", required = false) //
			@RequestParam(required = false) String reason//
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateEmailMerge");
		//
		return candidateService.updateCandidateEmailMerge(jobDivaSession, candidateid, backonemailmerge, requestoffemailindef, requestoffemailuntil, requestoffemailuntildate, reason);
		//
	}
	
	//
	@ApiImplicitParams({ @ApiImplicitParam(name = "socialnetworks", required = true, allowMultiple = true, dataType = "SocialNetworkType") })
	@RequestMapping(value = "/updateCandidateSNLinks", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Candidate Social Network Links")
	public Boolean updateCandidateSNLinks( //
			//
			@ApiParam(value = "Candidate ID", required = true) //
			@RequestParam(required = true) Long candidateid, //
			//
			@ApiParam(value = "socialnetworks", required = true, type = "SocialNetworkType", allowMultiple = true) //
			@RequestParam(required = false) SocialNetworkType[] socialnetworks //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCandidateSNLinks");
		//
		return candidateService.updateCandidateSNLinks(jobDivaSession, candidateid, socialnetworks);
		//
	}
	//
	@RequestMapping(value = "/GetCandidateNoteTypes", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Get Candidate Note Types")
	public List<NoteType> GetContactNoteTypes() throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("GetCandidateNoteTypes");
		//
		return  candidateService.getCandidateNoteTypes(jobDivaSession);
		//
	}
}
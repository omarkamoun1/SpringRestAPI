package com.jobdiva.api.controller.jobdivaapi;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.Attachment;
import com.jobdiva.api.model.ContactRoleType;
import com.jobdiva.api.model.Job;
import com.jobdiva.api.model.JobUserSimple;
import com.jobdiva.api.model.Skill;
import com.jobdiva.api.model.TeamRole;
import com.jobdiva.api.model.UserRole;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.service.JobService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "Job API", description = "REST API Used For Jobs")
public class JobController extends AbstractJobDivaController {
	//
	
	@Autowired
	JobService jobService;
	
	@RequestMapping(value = "/SearchJob", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Job")
	public List<Job> SearchJob( //
			//
			@ApiParam(value = "JobDiva internal ID", required = false) //
			@RequestParam(required = false) Long jobId, //
			//
			@ApiParam(value = "JobDiva reference number", required = false) //
			@RequestParam(required = false) String jobdivaref, //
			//
			@ApiParam(value = "Client reference number", required = false) //
			@RequestParam(required = false) String optionalref, //
			//
			@ApiParam(value = "Job city", required = false) //
			@RequestParam(required = false) String city, //
			//
			@ApiParam(value = "Array of state names, either spell out or 2-letter abbreviation", required = false) //
			@RequestParam(required = false) String[] state, //
			//
			@ApiParam(value = "Job title.", required = false) //
			@RequestParam(required = false) String title, //
			//
			@ApiParam(value = "Customer ID", required = false) //
			@RequestParam(required = false) Long contactid, //
			//
			@ApiParam(value = "Company ID", required = false) //
			@RequestParam(required = false) Long companyId, //
			//
			@ApiParam(value = "Company name", required = false) //
			@RequestParam(required = false) String companyname, //
			//
			@ApiParam(value = "Job status\r\n" + //
					"Value\r\n" + //
					"Description\r\n" + //
					"0\r\n" + //
					"OPEN\r\n" + //
					"1\r\n" + //
					"ON HOLD\r\n" + //
					"2\r\n" + //
					"FILLED\r\n" + //
					"3\r\n" + //
					"CANCELLED\r\n" + //
					"4\r\n" + //
					"CLOSED\r\n" + //
					"5\r\n" + //
					"EXPIRED\r\n" + //
					"6\r\n" + //
					"IGNORED", required = false) //
			@RequestParam(required = false) Integer status, //
			//
			@ApiParam(value = "Array of job types, valid values(case insensitive):\r\n" + //
					"• Direct Placement\r\n" + //
					"• Contract\r\n" + //
					"• Right to Hire\r\n" + //
					"• Full Time/Contract", required = false) //
			@RequestParam(required = false) String[] jobtype, //
			//
			@ApiParam(value = "Beginning issued date Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date issuedatefrom, //
			//
			@ApiParam(value = "Ending issued date Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date issuedateto, //
			//
			@ApiParam(value = "Beginning start date Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date startdatefrom, //
			//
			@ApiParam(value = "Ending Start date Format [MM/dd/yyyy HH:mm:ss]", required = false) //
			@RequestParam(required = false) Date startdateto, //
			//
			@ApiParam(value = "Zipcode", required = false) //
			@RequestParam(required = false) String zipcode, //
			//
			@ApiParam(value = "Zipcode Radius", required = false) //
			@RequestParam(required = false) Integer zipcodeRadius, //
			//
			@ApiParam(value = "Country ID", required = false) //
			@RequestParam(required = false) String countryId, //
			//
			@ApiParam(value = "Filter by recruiter id of the user", required = false) //
			@RequestParam(required = false) Boolean ismyjob//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("SearchJob");
		//
		return jobService.searchJobs(jobDivaSession, jobId, jobdivaref, optionalref, city, state, title, contactid, companyId, companyname, status, jobtype, issuedatefrom, issuedateto, startdatefrom, startdateto, zipcode, zipcodeRadius, countryId,
				ismyjob);
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "contacts", required = false, allowMultiple = true, dataType = "ContactRoleType"), //
			@ApiImplicitParam(name = "users", required = false, allowMultiple = true, dataType = "UserRole"), //
			@ApiImplicitParam(name = "skills", required = false, allowMultiple = true, dataType = "Skill"), //
			@ApiImplicitParam(name = "excludedskills", required = false, allowMultiple = true, dataType = "Skill"), //
			@ApiImplicitParam(name = "attachments", required = false, allowMultiple = true, dataType = "Attachment"), //
			@ApiImplicitParam(name = "Userfields", required = false, allowMultiple = true, dataType = "Userfield"), //
	})
	@RequestMapping(value = "/createJob", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Job")
	public Long createJob( //
			//
			@ApiParam(value = "Job contacts Fields in ContactRoleType:\r\n" + //
					"• Valid “roleid” values can be found through “Settings (gear icon at the top)” → “Labels” → “Contact Roles”.\r\n" + //
					"• Set “showonjob” to true for one and only one contact.\r\n" + //
					"• Set “action” to 1 for insertion.", required = false, type = "ContactRoleType", allowMultiple = true) //
			@RequestParam(required = false) ContactRoleType[] contacts, //
			//
			@ApiParam(value = "Contact first name; used for searching contact. This contact will be shown on job.\r\n" + //
					"Only used if contacts is not defined, and both contactfirstname, contactlastname and department are defined.", required = false) //
			@RequestParam(required = false) String contactfirstname, //
			//
			@ApiParam(value = "Contact last name;used for searching contact. This contact will be shown on job.\r\n" + //
					"Only used if contacts is not defined, and both contactfirstname, contactlastname and department are defined.", required = false) //
			@RequestParam(required = false) String contactlastname, //
			//
			@ApiParam(value = "Job title", required = true) //
			@RequestParam(required = true) String title, //
			//
			@ApiParam(value = "Job description. If no description passed in, there will be the default “Job Description” assigned.", required = false) //
			@RequestParam(required = false) String description, //
			//
			@ApiParam(value = "Company name", required = false) //
			@RequestParam(required = false) String department, //
			//
			@ApiParam(value = "Company internal ID", required = false) //
			@RequestParam(required = false) Long companyid, //
			//
			@ApiParam(value = "Job priority values (case insensitive):\r\n" + //
					"• A\r\n" + //
					"• B\r\n" + //
					"• C\r\n" + //
					"• D\r\n" + //
					"• User-Defined Priority. Value as string type of Integer.\r\n" + //
					"For example: “5”, “6”\r\n" + //
					"Mapping can be found through “Settings” (Wheel icon) →”Labels”→”Job Priority”", required = false) //
			@RequestParam(required = false) String priority, //
			//
			@ApiParam(value = "Valid division id can be found through “Leader Tools” → “My Team” → “Manage Divisions”.", required = false) //
			@RequestParam(required = false) Long divisionid, //
			//
			@ApiParam(value = "Fields in UserRoleType:\r\n" + //
					"• Valid “role” values (case insensitive):\r\n" + //
					"o Sales\r\n" + //
					"o Primary Sales\r\n" + //
					"o Recruiter\r\n" + //
					"o Primary Recruiter\r\n" + //
					"o For users of multiple role types, separate roles with any non-alphabetic characters.\r\n" + //
					"• Set “action” to 1 for insertion.", required = false, type = "UserRole", allowMultiple = true) //
			@RequestParam(required = false) UserRole[] users, //
			//
			@ApiParam(value = "Set “Experience Level” to “<1 Years” if “experience” equals to 0.\r\n" + //
					"Set “Experience Level” to “>10 Years” if “experience” is greater than 10.", required = false) //
			@RequestParam(required = false) Integer experience, //
			//
			@ApiParam(value = "Job status\r\n" + //
					"Value\r\n" + //
					"Description\r\n" + //
					"0\r\n" + //
					"OPEN\r\n" + //
					"1\r\n" + //
					"ON HOLD", required = false) //
			@RequestParam(required = false) Integer status, //
			//
			@ApiParam(value = "Client reference number", required = false) //
			@RequestParam(required = false) String optionalref, //
			//
			@ApiParam(value = "Address line1", required = false) //
			@RequestParam(required = false) String address1, //
			//
			@ApiParam(value = "Address line2", required = false) //
			@RequestParam(required = false) String address2, //
			//
			@ApiParam(value = "City", required = false) //
			@RequestParam(required = false) String city, //
			//
			@ApiParam(value = "State", required = false) //
			@RequestParam(required = false) String state, //
			//
			@ApiParam(value = "Postal zip code", required = false) //
			@RequestParam(required = false) String zipcode, //
			//
			@ApiParam(value = "Country, defaults to “US” if it’s not set.", required = false) //
			@RequestParam(required = false) String countryid, //
			//
			@ApiParam(value = "Job start date, defaults to issued date if it’s not set. format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date startdate, //
			//
			@ApiParam(value = "Job end date. format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date enddate, //
			//
			@ApiParam(value = "Job Type values(case insensitive):\r\n" + //
					"• Direct Placement\r\n" + //
					"• Contract\r\n" + //
					"• Right to Hire\r\n" + //
					"• Full Time/Contract", required = false) //
			@RequestParam(required = false) String jobtype, //
			//
			@ApiParam(value = "Number of job openings", required = false) //
			@RequestParam(required = false) Integer openings, //
			//
			@ApiParam(value = "Number of fills", required = false) //
			@RequestParam(required = false) Integer fills, //
			//
			@ApiParam(value = "Max Allowed Submittals", required = false) //
			@RequestParam(required = false) Integer maxsubmittals, //
			//
			@ApiParam(value = "Hide my client (default to true)", required = false) //
			@RequestParam(required = false) Boolean hidemyclient, //
			//
			@ApiParam(value = "Hide my client's address", required = false) //
			@RequestParam(required = false) Boolean hidemyclientaddress, //
			//
			@ApiParam(value = "Hide me & my company", required = false) //
			@RequestParam(required = false) Boolean hidemeandmycompany, //
			//
			@ApiParam(value = "Overtime", required = false) //
			@RequestParam(required = false) Boolean overtime, //
			//
			@ApiParam(value = "References", required = false) //
			@RequestParam(required = false) Boolean reference, //
			//
			@ApiParam(value = "Travel", required = false) //
			@RequestParam(required = false) Boolean travel, //
			//
			@ApiParam(value = "Drug Test", required = false) //
			@RequestParam(required = false) Boolean drugtest, //
			//
			@ApiParam(value = "Background Check", required = false) //
			@RequestParam(required = false) Boolean backgroundcheck, //
			//
			@ApiParam(value = "Security Clearance", required = false) //
			@RequestParam(required = false) Boolean securityclearance, //
			//
			@ApiParam(value = "Remarks", required = false) //
			@RequestParam(required = false) String remarks, //
			//
			@ApiParam(value = "Submittal Instructions", required = false) //
			@RequestParam(required = false) String submittalinstruction, //
			//
			@ApiParam(value = "Minimum bill rate", required = false) //
			@RequestParam(required = false) Double minbillrate, //
			//
			@ApiParam(value = "Maximum bill rate", required = false) //
			@RequestParam(required = false) Double maxbillrate, //
			//
			@ApiParam(value = "Valid values can be found at:\r\n" + //
					"Click “settings” (wheel icon at the top) → “Financial” on the left side → Rate Units → Click “Currency” tab\r\n" + //
					"Defaults to Default Currency on “Rate Units” page if not set.", required = false) //
			@RequestParam(required = false) String billratecurrency, //
			//
			@ApiParam(value = "Bill Rate Unit.\r\n" + //
					"Valid rate units can be found at:\r\n" + //
					"Click “settings” (wheel icon at the top) → “Financial” on the left side → Rate Units\r\n" + //
					"Defaults to “hour” if it’s not set or invalid", required = false) //
			@RequestParam(required = false) String billrateunit, //
			//
			@ApiParam(value = "Minimum pay rate", required = false) //
			@RequestParam(required = false) Double minpayrate, //
			//
			@ApiParam(value = "Maximum pay rate", required = false) //
			@RequestParam(required = false) Double maxpayrate, //
			//
			@ApiParam(value = "Same as billratecurrency\r\n" + //
					"Valid values can be found at:\r\n" + //
					"Click “settings” (wheel icon at the top) → “Financial” on the left side → Rate Units → Click “Currency” tab\r\n" + //
					"Defaults to Default Currency on “Rate Units” page if not sets", required = false) //
			@RequestParam(required = false) String payratecurrency, //
			//
			@ApiParam(value = "Pay Rate Unit.\r\n" + //
					"Valid rate units can be found at:\r\n" + //
					"Click “settings” (wheel icon at the top) → “Financial” on the left side → Rate Units\r\n" + //
					"Defaults to “hour” if it’s not set or invalid", required = false) //
			@RequestParam(required = false) String payrateunit, //
			//
			@ApiParam(value = "Please set a skill as following Patterns:\r\n" + //
					"“ECLIPSE~0~0.0~1~1”\r\n" + //
					"“ASP.NET~1~0.0~0~0”\r\n" + //
					"“.NET~1~0.0~1~0”\r\n" + //
					"“ORACLE~0~0.0~0~1”\r\n" + //
					"• Set a skill name before the 1st “~”.\r\n" + //
					"• Set “1” for an alternating skill before the 2nd “~”(“ASP.NET” and “.NET” are both alternating skill for “ECLIPSE” in the example above).\r\n" + //
					"• Set “1” for a recent skill after the 3rd “~”(“ECLIPSE” and “.NET”).\r\n" + //
					"• Set “1” for agent search w/o mapping after the 4th “~”(“ECLIPSE” and “ORACLE”).", required = false, type = "Skill", allowMultiple = true) //
			@RequestParam(required = false) Skill[] skills, //
			//
			@ApiParam(value = "Skill states", required = false) //
			@RequestParam(required = false) String[] skillstates, //
			//
			@ApiParam(value = "Skill zip code, mandatory if skillzipcodemiles is set.", required = false) //
			@RequestParam(required = false) String skillzipcode, //
			//
			@ApiParam(value = "Miles within skillzipcode", required = false) //
			@RequestParam(required = false) Integer skillzipcodemiles, //
			//
			@ApiParam(value = "Excluded skills", required = false, type = "Skill", allowMultiple = true) //
			@RequestParam(required = false) Skill[] excludedskills, //
			//
			@ApiParam(value = "Valid values(case insensitive):\r\n" + //
					"• NOW\r\n" + //
					"• On Schedule\r\n" + //
					"• Not Scheduled\r\n" + //
					"Defaults to “Not Scheduled” if set in team profile. Otherwise “On Schedule”.", required = false) //
			@RequestParam(required = false) String harvest, //
			//
			@ApiParam(value = "No. of resumes harvested, minimum 10. Valid only when “NOW” is chosen for ‘harvest’, and defaults to maximum resume number.", required = false) //
			@RequestParam(required = false) Integer resumes, //
			//
			@ApiParam(value = "Fields in Attachment type:\r\n" + //
					"• filename: string. Required field\r\n" + //
					"• filedata: base64 string. Required field.\r\n" + //
					"• description: string. Required field.\r\n" + //
					"• filetype: string", required = false, type = "Attachment", allowMultiple = true) //
			@RequestParam(required = false) Attachment[] attachments, //
			//
			@ApiParam(value = "Fields in Attachment type:\r\n" + //
					"• userfieldid: int. Required field\r\n" + //
					"• userfieldvalue: string. Required field", required = false, type = "Userfield", allowMultiple = true) //
			@RequestParam(required = false) Userfield[] Userfields //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createJob");
		//
		return jobService.createJob(jobDivaSession, contacts, contactfirstname, contactlastname, title, description, department, companyid, priority, divisionid, users, experience, status, optionalref, address1, address2, city, state, zipcode,
				countryid, startdate, enddate, jobtype, openings, fills, maxsubmittals, hidemyclient, hidemyclientaddress, hidemeandmycompany, overtime, reference, travel, drugtest, backgroundcheck, securityclearance, remarks, submittalinstruction,
				minbillrate, maxbillrate, billratecurrency, billrateunit, minpayrate, maxpayrate, payratecurrency, payrateunit, skills, skillstates, skillzipcode, skillzipcodemiles, excludedskills, harvest, resumes, attachments, Userfields);
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "contacts", required = false, allowMultiple = true, dataType = "ContactRoleType"), //
			@ApiImplicitParam(name = "users", required = false, allowMultiple = true, dataType = "UserRole"), //
			@ApiImplicitParam(name = "Userfields", required = false, allowMultiple = true, dataType = "Userfield") })
	@RequestMapping(value = "/updateJob", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Job")
	public Boolean updateJob( //
			//
			@ApiParam(value = "Job ID", required = true) //
			@RequestParam(required = true) Long jobid, //
			//
			@ApiParam(value = "Client reference number", required = false) //
			@RequestParam(required = false) String optionalref, //
			//
			//
			@ApiParam(value = "Job title", required = false) //
			@RequestParam(required = false) String title, //
			//
			@ApiParam(value = "Job description", required = false) //
			@RequestParam(required = false) String description, //
			//
			@ApiParam(value = "Job posting title", required = false) //
			@RequestParam(required = false) String postingtitle, //
			//
			@ApiParam(value = "Job posting description", required = false) //
			@RequestParam(required = false) String postingdescription, //
			//
			@ApiParam(value = "Company internal ID", required = false) //
			@RequestParam(required = false) Long companyid, //
			//
			@ApiParam(value = "Job contacts\r\n" + //
					"Fields in ContactRoleType:\r\n" + //
					"• Valid “roleid” values can be found through “Settings (wheel icon at the top)” → “Labels” → “Contact Roles”.\r\n" + //
					"• Set “showonjob” to true for one and only one contact.\r\n" + //
					"• Set “action” to 1 for insertion or update, 2 for deletion.", required = false, type = "ContactRoleType", allowMultiple = true) //
			@RequestParam(required = false) ContactRoleType[] contacts, //
			//
			@ApiParam(value = "Fields in UserRoleType:\r\n" + //
					"• Valid “role” values (case insensitive):\r\n" + //
					"o Sales\r\n" + //
					"o Primary Sales\r\n" + //
					"o Recruiter\r\n" + //
					"o Primary Recruiter\r\n" + //
					"o For users of multiple role types, separate\\r\\n\" //" + //
					"roles with any non-alphabetic characters.\r\n" + //
					"• Set “action” to 1 for insertion or update, 2 for deletion.", required = false, type = "UserRole", allowMultiple = true) //
			@RequestParam(required = false) UserRole[] users, //
			//
			@ApiParam(value = "Address 1", required = false) //
			@RequestParam(required = false) String address1, //
			//
			@ApiParam(value = "Address 2", required = false) //
			@RequestParam(required = false) String address2, //
			//
			@ApiParam(value = "City", required = false) //
			@RequestParam(required = false) String city, //
			//
			@ApiParam(value = "State", required = false) //
			@RequestParam(required = false) String state, //
			//
			@ApiParam(value = "Postal zip code", required = false) //
			@RequestParam(required = false) String zipcode, //
			//
			@ApiParam(value = "Country ID", required = false) //
			@RequestParam(required = false) String countryid, //
			//
			@ApiParam(value = "Job start date format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date startdate, //
			//
			@ApiParam(value = "Job end date format(MM/dd/yyyy HH:mm:ss)", required = false) //
			@RequestParam(required = false) Date enddate, //
			//
			@ApiParam(value = "Job status\r\n" + //
					"Value\r\n" + //
					"Description\r\n" + //
					"0\r\n" + //
					"OPEN\r\n" + //
					"1\r\n" + //
					"ON HOLD\r\n" + //
					"2\r\n" + //
					"FILLED\r\n" + //
					"3\r\n" + //
					"CANCELLED\r\n" + //
					"4\r\n" + //
					"CLOSED\r\n" + //
					"5\r\n" + //
					"EXPIRED\r\n" + //
					"6\r\n" + //
					"IGNORED", required = false) //
			@RequestParam(required = false) Integer status, //
			//
			@ApiParam(value = "Job Type values(case insensitive):\r\n" + //
					"• Direct Placement\r\n" + //
					"• Contract\r\n" + //
					"• Right to Hire\r\n" + //
					"• Full Time/Contract", required = false) //
			@RequestParam(required = false) String jobtype, //
			//
			@ApiParam(value = "Job priority values (case insensitive):\r\n" + //
					"• A\r\n" + //
					"• B\r\n" + //
					"• C\r\n" + //
					"• D\r\n" + //
					"• User-Defined Priority. Value as string type of Integer.\r\n" + //
					"For example: “5”, “6”\r\n" + //
					"Mapping can be found through “Settings” (Wheel icon) →”Labels”→”Job Priority”", required = false) //
			@RequestParam(required = false) String priority, //
			//
			@ApiParam(value = "Number of job openings", required = false) //
			@RequestParam(required = false) Integer openings, //
			//
			@ApiParam(value = "Number of fills", required = false) //
			@RequestParam(required = false) Integer fills, //
			//
			@ApiParam(value = "Max Allowed Submittals", required = false) //
			@RequestParam(required = false) Integer maxsubmittals, //
			//
			@ApiParam(value = "Hide my client", required = false) //
			@RequestParam(required = false) Boolean hidemyclient, //
			//
			@ApiParam(value = "Hide my client's address", required = false) //
			@RequestParam(required = false) Boolean hidemyclientaddress, //
			//
			@ApiParam(value = "Hide me & my company", required = false) //
			@RequestParam(required = false) Boolean hidemeandmycompany, //
			//
			@ApiParam(value = "Overtime", required = false) //
			@RequestParam(required = false) Boolean overtime, //
			//
			@ApiParam(value = "References", required = false) //
			@RequestParam(required = false) Boolean reference, //
			//
			@ApiParam(value = "Travel", required = false) //
			@RequestParam(required = false) Boolean travel, //
			//
			@ApiParam(value = "Drug Test", required = false) //
			@RequestParam(required = false) Boolean drugtest, //
			//
			@ApiParam(value = "Background Check", required = false) //
			@RequestParam(required = false) Boolean backgroundcheck, //
			//
			@ApiParam(value = "Security Clearance", required = false) //
			@RequestParam(required = false) Boolean securityclearance, //
			//
			@ApiParam(value = "Remarks", required = false) //
			@RequestParam(required = false) String remarks, //
			//
			@ApiParam(value = "Submittal Instructions", required = false) //
			@RequestParam(required = false) String submittalinstruction, //
			//
			@ApiParam(value = "Minimum bill rate", required = false) //
			@RequestParam(required = false) Double minbillrate, //
			//
			@ApiParam(value = "Maximum bill rate", required = false) //
			@RequestParam(required = false) Double maxbillrate, //
			//
			@ApiParam(value = "Minimum pay rate", required = false) //
			@RequestParam(required = false) Double minpayrate, //
			//
			@ApiParam(value = "Maximum pay rate", required = false) //
			@RequestParam(required = false) Double maxpayrate, //
			//
			@ApiParam(value = "User defined fields. Valid “userfieldid” can be found through “Leader Tools” → “My Team” → “User-Defined Fields” → “Jobs” → “UDF #”", required = false, type = "Userfield", allowMultiple = true) //
			@RequestParam(required = false) Userfield[] Userfields, //
			//
			@ApiParam(value = "Valid values(case insentitive):\r\n" + //
					"• NOW\r\n" + //
					"• On Schedule\r\n" + //
					"• Not Scheduled", required = false) //
			@RequestParam(required = false) String harvest, //
			//
			@ApiParam(value = "No. of resumes harvested, minimum 10. Valid only when “NOW” is chosen for ‘harvest’, and defaults to maximum resume number.", required = false) //
			@RequestParam(required = false) Integer resumes, //
			//
			@ApiParam(value = "Valid division id can be found through “Leader Tools” → “My Team” → “Manage Divisions”.", required = false) //
			@RequestParam(required = false) Long divisionid //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateJob");
		//
		return jobService.updateJob(jobDivaSession, jobid, optionalref, title, description, postingtitle, postingdescription, companyid, contacts, users, address1, address2, city, state, zipcode, countryid, startdate, enddate, status, jobtype,
				priority, openings, fills, maxsubmittals, hidemyclient, hidemyclientaddress, hidemeandmycompany, overtime, reference, travel, drugtest, backgroundcheck, securityclearance, remarks, submittalinstruction, minbillrate, maxbillrate,
				minpayrate, maxpayrate, Userfields, harvest, resumes, divisionid);
		//
	}
	
	@RequestMapping(value = "/AddJobNote", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Add Job Note")
	public Boolean AddJobNote( //
			//
			@ApiParam(value = "JobDiva internal ID", required = true) //
			@RequestParam(required = true) Long jobId, //
			//
			@ApiParam(value = "Recruiter ID", required = true) //
			@RequestParam(required = true) Long recruiterId, //
			//
			@ApiParam(value = "Type", required = false) //
			@RequestParam(required = false) Integer type, //
			//
			@ApiParam(value = "Shared", required = false) //
			@RequestParam(required = false) Integer shared, //
			//
			@ApiParam(value = "Note", required = false) //
			@RequestParam(required = false) String note //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("AddJobNote");
		//
		return jobService.addJobNote(jobDivaSession, jobId, type, recruiterId, shared, note);
		//
	}
	
	@RequestMapping(value = "/GetAllJobUsers", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Get All Job Users")
	public List<JobUserSimple> GetAllJobUsers( //
			//
			@ApiParam(value = "JobDiva internal ID", required = true) //
			@RequestParam(required = true) Long jobId //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("GetAllJobUsers");
		//
		return jobService.getAllJobUsers(jobDivaSession, jobId);
		//
	}
	
	@RequestMapping(value = "/GetJobPriority", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Get Job Priority")
	public List<String> GetJobPriority( //
			//
			@ApiParam(value = "Team ID", required = true) //
			@RequestParam(required = true) Long teamId //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("GetJobPriority");
		//
		return jobService.getJobPriority(jobDivaSession, teamId);
		//
	}
	
	@RequestMapping(value = "/UpdateJobPriority", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Job Priority")
	public Boolean UpdateJobPriority( //
			//
			@ApiParam(value = "Priority", required = true) //
			@RequestParam(required = true) Integer priority, //
			//
			@ApiParam(value = "Job ID", required = true) //
			@RequestParam(required = true) Long jobId , //
			//
			@ApiParam(value = "Priority Name", required = true) //
			@RequestParam(required = true) String priorityName //
			
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("UpdateJobPriority");
		//
		return jobService.updateJobPriority(jobDivaSession, priority, jobId , priorityName);
		//
	}
	
	@RequestMapping(value = "/GetUserRoles", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Get User Roles")
	public List<TeamRole> GetUserRoles() throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("GetUserRoles");
		//
		return jobService.getUserRoles(jobDivaSession);
		//
	}
}

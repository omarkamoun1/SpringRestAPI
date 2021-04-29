package com.jobdiva.api.controller.jobdivaapi.v2;

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
import com.jobdiva.api.model.Attachment;
import com.jobdiva.api.model.ContactRoleType;
import com.jobdiva.api.model.Job;
import com.jobdiva.api.model.JobUserSimple;
import com.jobdiva.api.model.Skill;
import com.jobdiva.api.model.UserRole;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.v2.job.AddJobNoteDef;
import com.jobdiva.api.model.v2.job.CreateJobDef;
import com.jobdiva.api.model.v2.job.SearchJobDef;
import com.jobdiva.api.model.v2.job.UpdateJobDef;
import com.jobdiva.api.service.JobService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/apiv2/jobdiva/")
@Api(value = "Job API", description = "REST API Used For Jobs")
// @ApiIgnore
public class JobV2Controller extends AbstractJobDivaController {
	//
	
	@Autowired
	JobService jobService;
	
	@RequestMapping(value = "/SearchJob", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Job")
	public List<Job> SearchJob( //
			//
			@ApiParam(value = "jobId : JobDiva internal ID \r\n" //
					+ "jobdivaref : obDiva reference number \r\n" //
					+ "optionalref : Client reference number \r\n" //
					+ "city : Job city \r\n" //
					+ "state : Array of state names, either spell out or 2-letter abbreviation \r\n" //
					+ "title : Job title. \r\n" //
					+ "contactid : Customer ID \r\n" //
					+ "companyId : Company ID \r\n" //
					+ "companyname : Company name \r\n" //
					+ "status :  Job status\r\n" + //
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
					"IGNORED \r\n \r\n" + "jobtype :  Array of job types, valid values(case insensitive):\r\n" + //
					"• Direct Placement\r\n" + //
					"• Contract\r\n" + //
					"• Right to Hire\r\n" + //
					"• Full Time/Contract \r\n \r\n" //
					+ "issuedatefrom : Beginning issued \r\n" //
					+ "issuedateto : Ending issued date \r\n" //
					+ "startdatefrom : Beginning start date \r\n" //
					+ "startdateto : Ending Start date \r\n" //
					+ "zipcode : Zipcode \r\n" //
					+ "zipcodeRadius : Zipcode Radius \r\n" //
					+ "countryId : Country ID \r\n" //
					+ "ismyjob : Filter by recruiter id of the user" //
			//
			) @RequestBody SearchJobDef searchJobDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("SearchJob");
		//
		//
		Long jobId = searchJobDef.getJobId();
		String jobdivaref = searchJobDef.getJobdivaref();
		String optionalref = searchJobDef.getOptionalref();
		String city = searchJobDef.getCity();
		String[] state = searchJobDef.getState();
		String title = searchJobDef.getTitle();
		Long contactid = searchJobDef.getContactid();
		Long companyId = searchJobDef.getCompanyId();
		String companyname = searchJobDef.getCompanyname();
		Integer status = searchJobDef.getStatus();
		String[] jobtype = searchJobDef.getJobtype();
		Date issuedatefrom = searchJobDef.getIssuedatefrom();
		Date issuedateto = searchJobDef.getIssuedateto();
		Date startdatefrom = searchJobDef.getStartdatefrom();
		Date startdateto = searchJobDef.getStartdateto();
		String zipcode = searchJobDef.getZipcode();
		Integer zipcodeRadius = searchJobDef.getZipcodeRadius();
		String countryId = searchJobDef.getCountryId();
		Boolean ismyjob = searchJobDef.getIsmyjob();
		//
		//
		return jobService.searchJobs(jobDivaSession, jobId, jobdivaref, optionalref, city, state, title, contactid, companyId, companyname, status, jobtype, issuedatefrom, issuedateto, startdatefrom, startdateto, zipcode, zipcodeRadius, countryId,
				ismyjob);
		//
	}
	
	@RequestMapping(value = "/createJob", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Job")
	public Long createJob( //
			//
			@ApiParam(value = "contacts : Job contacts Fields in ContactRoleType:\r\n" + //
					"• Valid “roleid” values can be found through “Settings (gear icon at the top)” → “Labels” → “Contact Roles”.\r\n" + //
					"• Set “showonjob” to true for one and only one contact.\r\n" + //
					"• Set “action” to 1 for insertion. \r\n \r\n"//
					+ "contactfirstname : Contact first name; used for searching contact. This contact will be shown on job.\r\n" + //
					"Only used if contacts is not defined, and both contactfirstname, contactlastname and department are defined. \r\n \r\n"//
					+ "contactlastname : Contact last name;used for searching contact. This contact will be shown on job.\r\n" + //
					"Only used if contacts is not defined, and both contactfirstname, contactlastname and department are defined. \r\n"//
					+ "title : Job title \r\n"//
					+ "description : Job description. If no description passed in, there will be the default “Job Description” assigned. \r\n"//
					+ "department : Company name \r\n"//
					+ "companyid : Company internal ID \r\n"//
					+ "priority : Job priority values (case insensitive):\r\n" + //
					"• A\r\n" + //
					"• B\r\n" + //
					"• C\r\n" + //
					"• D\r\n" + //
					"• User-Defined Priority. Value as string type of Integer.\r\n" + //
					"For example: “5”, “6”\r\n" + //
					"Mapping can be found through “Settings” (Wheel icon) →”Labels”→”Job Priority” \r\n \r\n"//
					+ "divisionid : Valid division id can be found through “Leader Tools” → “My Team” → “Manage Divisions”. \r\n"//
					+ "users : Fields in UserRoleType:\r\n" + //
					"• Valid “role” values (case insensitive):\r\n" + //
					"o Sales\r\n" + //
					"o Primary Sales\r\n" + //
					"o Recruiter\r\n" + //
					"o Primary Recruiter\r\n" + //
					"o For users of multiple role types, separate roles with any non-alphabetic characters.\r\n" + //
					"• Set “action” to 1 for insertion. \r\n \r\n"//
					+ "experience : Set “Experience Level” to “<1 Years” if “experience” equals to 0.\r\n" + //
					"Set “Experience Level” to “>10 Years” if “experience” is greater than 10. \r\n \r\n"//
					+ "status : Job status\r\n" + //
					"Value\r\n" + //
					"Description\r\n" + //
					"0\r\n" + //
					"OPEN\r\n" + //
					"1\r\n" + //
					"ON HOLD \r\n \r\n"//
					+ "optionalref : Client reference number \r\n"//
					+ "address1 : Address line1 \r\n"//
					+ "address2 : Address line2 \r\n"//
					+ "city : City \r\n"//
					+ "state : State \r\n"//
					+ "zipcode : Postal zip code \r\n"//
					+ "countryid : Country, defaults to “US” if it’s not set. \r\n"//
					+ "startdate : Job start date, defaults to issued date if it’s not set. \r\n"//
					+ "enddate : Job end date. \r\n"//
					+ "jobtype : Job Type values(case insensitive):\r\n" + //
					"• Direct Placement\r\n" + //
					"• Contract\r\n" + //
					"• Right to Hire\r\n" + //
					"• Full Time/Contract \r\n \r\n"//
					+ "openings : Number of job openings \r\n"//
					+ "fills : Number of fills \r\n"//
					+ "maxsubmittals : Max Allowed Submittals \r\n"//
					+ "hidemyclient : Hide my client (default to true) \r\n"//
					+ "hidemyclientaddress : Hide my client's address \r\n"//
					+ "hidemeandmycompany : Hide me & my company \r\n"//
					+ "overtime : Overtime \r\n"//
					+ "reference : References \r\n"//
					+ "travel : Travel \r\n"//
					+ "drugtest : Drug Test \r\n"//
					+ "backgroundcheck : Background Check \r\n"//
					+ "securityclearance : Security Clearance \r\n"//
					+ "remarks : Remarks \r\n"//
					+ "submittalinstruction : Submittal Instructions \r\n"//
					+ "minbillrate : Minimum bill rate \r\n"//
					+ "maxbillrate : Maximum bill rate \r\n"//
					+ "billratecurrency : Valid values can be found at:\r\n" + //
					"Click “settings” (wheel icon at the top) → “Financial” on the left side → Rate Units → Click “Currency” tab\r\n" + //
					"Defaults to Default Currency on “Rate Units” page if not set. \r\n \r\n"//
					+ "billrateunit : Bill Rate Unit.\r\n" + //
					"Valid rate units can be found at:\r\n" + //
					"Click “settings” (wheel icon at the top) → “Financial” on the left side → Rate Units\r\n" + //
					"Defaults to “hour” if it’s not set or invalid \r\n \r\n"//
					+ "minpayrate : Minimum pay rate \r\n"//
					+ "maxpayrate : Maximum pay rate \r\n"//
					+ "payratecurrency : Same as billratecurrency\r\n" + //
					"Valid values can be found at:\r\n" + //
					"Click “settings” (wheel icon at the top) → “Financial” on the left side → Rate Units → Click “Currency” tab\r\n" + //
					"Defaults to Default Currency on “Rate Units” page if not sets \r\n \r\n"//
					+ "payrateunit : Pay Rate Unit.\r\n" + //
					"Valid rate units can be found at:\r\n" + //
					"Click “settings” (wheel icon at the top) → “Financial” on the left side → Rate Units\r\n" + //
					"Defaults to “hour” if it’s not set or invalid \r\n \r\n"//
					+ "skills : Please set a skill as following Patterns:\r\n" + //
					"“ECLIPSE~0~0.0~1~1”\r\n" + //
					"“ASP.NET~1~0.0~0~0”\r\n" + //
					"“.NET~1~0.0~1~0”\r\n" + //
					"“ORACLE~0~0.0~0~1”\r\n" + //
					"• Set a skill name before the 1st “~”.\r\n" + //
					"• Set “1” for an alternating skill before the 2nd “~”(“ASP.NET” and “.NET” are both alternating skill for “ECLIPSE” in the example above).\r\n" + //
					"• Set “1” for a recent skill after the 3rd “~”(“ECLIPSE” and “.NET”).\r\n" + //
					"• Set “1” for agent search w/o mapping after the 4th “~”(“ECLIPSE” and “ORACLE”). \r\n \r\n"//
					+ "skillstates : Skill states \r\n"//
					+ "skillzipcode : Skill zip code, mandatory if skillzipcodemiles is set. \r\n"//
					+ "skillzipcodemiles : Miles within skillzipcode \r\n"//
					+ "excludedskills : Excluded skills \r\n"//
					+ "harvest : Valid values(case insensitive):\r\n" + //
					"• NOW\r\n" + //
					"• On Schedule\r\n" + //
					"• Not Scheduled\r\n" + //
					"Defaults to “Not Scheduled” if set in team profile. Otherwise “On Schedule”. \r\n \r\n"//
					+ "resumes : No. of resumes harvested, minimum 10. Valid only when “NOW” is chosen for ‘harvest’, and defaults to maximum resume number. \r\n"//
					+ "attachments : Fields in Attachment type:\r\n" + //
					"• filename: string. Required field\r\n" + //
					"• filedata: base64 string. Required field.\r\n" + //
					"• description: string. Required field.\r\n" + //
					"• filetype: string \r\n \r\n"//
					+ "Userfields : Fields in Attachment type:\r\n" + //
					"• userfieldid: int. Required field\r\n" + //
					"• userfieldvalue: string. Required field \r\n" //
			) @RequestBody CreateJobDef createJobDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createJob");
		//
		//
		ContactRoleType[] contacts = createJobDef.getContacts();
		String contactfirstname = createJobDef.getContactfirstname();
		String contactlastname = createJobDef.getContactlastname();
		String title = createJobDef.getTitle();
		String description = createJobDef.getDescription();
		String department = createJobDef.getDepartment();
		Long companyid = createJobDef.getCompanyid();
		String priority = createJobDef.getPriority();
		Long divisionid = createJobDef.getDivisionid();
		UserRole[] users = createJobDef.getUsers();
		Integer experience = createJobDef.getExperience();
		Integer status = createJobDef.getStatus();
		String optionalref = createJobDef.getOptionalref();
		String address1 = createJobDef.getAddress1();
		String address2 = createJobDef.getAddress2();
		String city = createJobDef.getCity();
		String state = createJobDef.getState();
		String zipcode = createJobDef.getZipcode();
		String countryid = createJobDef.getCountryid();
		Date startdate = createJobDef.getStartdate();
		Date enddate = createJobDef.getEnddate();
		String jobtype = createJobDef.getJobtype();
		Integer openings = createJobDef.getOpenings();
		Integer fills = createJobDef.getFills();
		Integer maxsubmittals = createJobDef.getMaxsubmittals();
		Boolean hidemyclient = createJobDef.getHidemyclient();
		Boolean hidemyclientaddress = createJobDef.getHidemyclientaddress();
		Boolean hidemeandmycompany = createJobDef.getHidemeandmycompany();
		Boolean overtime = createJobDef.getOvertime();
		Boolean reference = createJobDef.getReference();
		Boolean travel = createJobDef.getTravel();
		Boolean drugtest = createJobDef.getDrugtest();
		Boolean backgroundcheck = createJobDef.getBackgroundcheck();
		Boolean securityclearance = createJobDef.getSecurityclearance();
		String remarks = createJobDef.getRemarks();
		String submittalinstruction = createJobDef.getSubmittalinstruction();
		Double minbillrate = createJobDef.getMinbillrate();
		Double maxbillrate = createJobDef.getMaxbillrate();
		String billratecurrency = createJobDef.getBillratecurrency();
		String billrateunit = createJobDef.getBillrateunit();
		Double minpayrate = createJobDef.getMinpayrate();
		Double maxpayrate = createJobDef.getMaxpayrate();
		String payratecurrency = createJobDef.getPayratecurrency();
		String payrateunit = createJobDef.getPayrateunit();
		Skill[] skills = createJobDef.getSkills();
		String[] skillstates = createJobDef.getSkillstates();
		String skillzipcode = createJobDef.getSkillzipcode();
		Integer skillzipcodemiles = createJobDef.getSkillzipcodemiles();
		Skill[] excludedskills = createJobDef.getExcludedskills();
		String harvest = createJobDef.getHarvest();
		Integer resumes = createJobDef.getResumes();
		Attachment[] attachments = createJobDef.getAttachments();
		Userfield[] Userfields = createJobDef.getUserfields();
		//
		//
		return jobService.createJob(jobDivaSession, contacts, contactfirstname, contactlastname, title, description, department, companyid, priority, divisionid, users, experience, status, optionalref, address1, address2, city, state, zipcode,
				countryid, startdate, enddate, jobtype, openings, fills, maxsubmittals, hidemyclient, hidemyclientaddress, hidemeandmycompany, overtime, reference, travel, drugtest, backgroundcheck, securityclearance, remarks, submittalinstruction,
				minbillrate, maxbillrate, billratecurrency, billrateunit, minpayrate, maxpayrate, payratecurrency, payrateunit, skills, skillstates, skillzipcode, skillzipcodemiles, excludedskills, harvest, resumes, attachments, Userfields);
		//
	}
	
	@RequestMapping(value = "/updateJob", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Job")
	public Boolean updateJob( //
			//
			@ApiParam(value = "jobid : Job ID \r\n"//
					+ "optionalref : Client reference number \r\n"//
					+ "title : Job title \r\n"//
					+ "description : Job description \r\n"//
					+ "postingtitle : Job posting title \r\n"//
					+ "postingdescription : Job posting description \r\n"//
					+ "companyid : Company internal ID \r\n"//
					+ "contacts :  Job contacts\r\n" + //
					"Fields in ContactRoleType:\r\n" + //
					"• Valid “roleid” values can be found through “Settings (wheel icon at the top)” → “Labels” → “Contact Roles”.\r\n" + //
					"• Set “showonjob” to true for one and only one contact.\r\n" + //
					"• Set “action” to 1 for insertion or update, 2 for deletion. \r\n \r\n"//
					+ "users :  Fields in UserRoleType:\r\n" + //
					"• Valid “role” values (case insensitive):\r\n" + //
					"o Sales\r\n" + //
					"o Primary Sales\r\n" + //
					"o Recruiter\r\n" + //
					"o Primary Recruiter\r\n" + //
					"o For users of multiple role types, separate\\r\\n\" //" + //
					"roles with any non-alphabetic characters.\r\n" + //
					"• Set “action” to 1 for insertion or update, 2 for deletion. \r\n \r\n" //
					+ "address1 : Address 1 \r\n" //
					+ "address2 : Address 2 \r\n" //
					+ "city : City \r\n" //
					+ "state : State \r\n" //
					+ "zipcode : Postal zip code \r\n" //
					+ "countryid : Country ID \r\n" //
					+ "startdate : Job start date \r\n" //
					+ "enddate : Job end date \r\n" //
					+ "status : Job status\r\n" + //
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
					"IGNORED \r\n \r\n"//
					+ "jobtype : Job Type values(case insensitive):\r\n" + //
					"• Direct Placement\r\n" + //
					"• Contract\r\n" + //
					"• Right to Hire\r\n" + //
					"• Full Time/Contract \r\n \r\n" + "priority : Job priority values (case insensitive):\r\n" + //
					"• A\r\n" + //
					"• B\r\n" + //
					"• C\r\n" + //
					"• D\r\n" + //
					"• User-Defined Priority. Value as string type of Integer.\r\n" + //
					"For example: “5”, “6”\r\n" + // //
					"Mapping can be found through “Settings” (Wheel icon) →”Labels”→”Job Priority” \r\n \r\n" //
					+ "openings : Number of job openings \r\n" //
					+ "fills : Number of fills \r\n" //
					+ "maxsubmittals : Max Allowed Submittals \r\n" //
					+ "hidemyclient : Hide my client \r\n" //
					+ "hidemyclientaddress : Hide my client's address \r\n" //
					+ "hidemeandmycompany : Hide me & my company \r\n" //
					+ "overtime : Overtime \r\n" //
					+ "reference : References \r\n" //
					+ "travel : Travel \r\n" //
					+ "drugtest : Drug Test \r\n" //
					+ "backgroundcheck : Background Check \r\n" //
					+ "securityclearance : Security Clearance \r\n" //
					+ "remarks : Remarks \r\n" //
					+ "submittalinstruction : Submittal Instructions \r\n" //
					+ "minbillrate : Minimum bill rate \r\n" //
					+ "maxbillrate : Maximum bill rate \r\n" //
					+ "minpayrate : Minimum pay rate \r\n" //
					+ "maxpayrate : Maximum pay rate \r\n" //
					+ "Userfields : User defined fields. Valid “userfieldid” can be found through “Leader Tools” → “My Team” → “User-Defined Fields” → “Jobs” → “UDF #” \r\n" //
					+ "harvest : Valid values(case insentitive):\r\n" + // //
					"• NOW\r\n" + // //
					"• On Schedule\r\n" + // //
					"• Not Scheduled \r\n \r\n" //
					+ "resumes : No. of resumes harvested, minimum 10. Valid only when “NOW” is chosen for ‘harvest’, and defaults to maximum resume number. \r\n" //
					+ "divisionid : Valid division id can be found through “Leader Tools” → “My Team” → “Manage Divisions”." //
			) //
			@RequestBody UpdateJobDef updateJobDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateJob");
		//
		//
		//
		Long jobid = updateJobDef.getJobid();
		String optionalref = updateJobDef.getOptionalref();
		String title = updateJobDef.getTitle();
		String description = updateJobDef.getDescription();
		String postingtitle = updateJobDef.getPostingtitle();
		String postingdescription = updateJobDef.getPostingdescription();
		Long companyid = updateJobDef.getCompanyid();
		ContactRoleType[] contacts = updateJobDef.getContacts();
		UserRole[] users = updateJobDef.getUsers();
		String address1 = updateJobDef.getAddress1();
		String address2 = updateJobDef.getAddress2();
		String city = updateJobDef.getCity();
		String state = updateJobDef.getState();
		String zipcode = updateJobDef.getZipcode();
		String countryid = updateJobDef.getCountryid();
		Date startdate = updateJobDef.getStartdate();
		Date enddate = updateJobDef.getEnddate();
		Integer status = updateJobDef.getStatus();
		String jobtype = updateJobDef.getJobtype();
		String priority = updateJobDef.getPriority();
		Integer openings = updateJobDef.getOpenings();
		Integer fills = updateJobDef.getFills();
		Integer maxsubmittals = updateJobDef.getMaxsubmittals();
		Boolean hidemyclient = updateJobDef.getHidemyclient();
		Boolean hidemyclientaddress = updateJobDef.getHidemyclientaddress();
		Boolean hidemeandmycompany = updateJobDef.getHidemeandmycompany();
		Boolean overtime = updateJobDef.getOvertime();
		Boolean reference = updateJobDef.getReference();
		Boolean travel = updateJobDef.getTravel();
		Boolean drugtest = updateJobDef.getDrugtest();
		Boolean backgroundcheck = updateJobDef.getBackgroundcheck();
		Boolean securityclearance = updateJobDef.getSecurityclearance();
		String remarks = updateJobDef.getRemarks();
		String submittalinstruction = updateJobDef.getSubmittalinstruction();
		Double minbillrate = updateJobDef.getMinbillrate();
		Double maxbillrate = updateJobDef.getMaxbillrate();
		Double minpayrate = updateJobDef.getMinpayrate();
		Double maxpayrate = updateJobDef.getMaxpayrate();
		Userfield[] Userfields = updateJobDef.getUserfields();
		String harvest = updateJobDef.getHarvest();
		Integer resumes = updateJobDef.getResumes();
		Long divisionid = updateJobDef.getDivisionid();
		//
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
			@ApiParam(value = "jobId : JobDiva internal ID \r\n" //
					+ "recruiterId : Recruiter ID \r\n" //
					+ "type : type \r\n" //
					+ "shared : Shared \r\n" //
					+ "note : Note") //
			@RequestBody AddJobNoteDef addJobNoteDef //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("AddJobNote");
		//
		//
		Long jobId = addJobNoteDef.getJobId();
		Long recruiterId = addJobNoteDef.getRecruiterId();
		Integer type = addJobNoteDef.getType();
		Integer shared = addJobNoteDef.getShared();
		String note = addJobNoteDef.getNote();
		//
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
}

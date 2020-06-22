package com.jobdiva.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.model.Attachment;
import com.jobdiva.api.model.CandidateNote;
import com.jobdiva.api.model.CandidateQual;
import com.jobdiva.api.model.CandidateUDF;
import com.jobdiva.api.model.CompanyAddress;
import com.jobdiva.api.model.ContactAddress;
import com.jobdiva.api.model.ContactRoleType;
import com.jobdiva.api.model.ExpenseEntry;
import com.jobdiva.api.model.JobContact;
import com.jobdiva.api.model.JobNote;
import com.jobdiva.api.model.JobUDF;
import com.jobdiva.api.model.JobUser;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.PhoneType;
import com.jobdiva.api.model.QualificationType;
import com.jobdiva.api.model.Skill;
import com.jobdiva.api.model.TimesheetEntry;
import com.jobdiva.api.model.TitleSkillCertification;
import com.jobdiva.api.model.UserRole;
import com.jobdiva.api.model.Userfield;

import io.swagger.annotations.Api;

/**
 * @author Joseph Chidiac
 *
 */
@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "Job API", description = "Hidden")
public class HiddenController {
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getCompanyAddress", method = RequestMethod.GET, produces = "application/json")
	public CompanyAddress getCompanyAddress(Long companyId//
	//
	) throws Exception {
		//
		return new CompanyAddress();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getContactAddress", method = RequestMethod.GET, produces = "application/json")
	public ContactAddress getContactAddress(
	//
	) throws Exception {
		//
		return new ContactAddress();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getPhoneType", method = RequestMethod.GET, produces = "application/json")
	public PhoneType getPhoneType(
	//
	) throws Exception {
		//
		return new PhoneType();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getOwner", method = RequestMethod.GET, produces = "application/json")
	public Owner getOwner(
	//
	) throws Exception {
		//
		return new Owner();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getSkill", method = RequestMethod.GET, produces = "application/json")
	public Skill getSkill(
	//
	) throws Exception {
		//
		return new Skill();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getContactRoleType", method = RequestMethod.GET, produces = "application/json")
	public ContactRoleType getContactRoleType(
	//
	) throws Exception {
		//
		return new ContactRoleType();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getUserRole", method = RequestMethod.GET, produces = "application/json")
	public UserRole getUserRole(
	//
	) throws Exception {
		//
		return new UserRole();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getJobUDF", method = RequestMethod.GET, produces = "application/json")
	public JobUDF getJobUDF(
	//
	) throws Exception {
		//
		return new JobUDF();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getJobContact", method = RequestMethod.GET, produces = "application/json")
	public JobContact getJobContact(
	//
	) throws Exception {
		//
		return new JobContact();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getJobUser", method = RequestMethod.GET, produces = "application/json")
	public JobUser getJobUser(
	//
	) throws Exception {
		//
		return new JobUser();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getAttachment", method = RequestMethod.GET, produces = "application/json")
	public Attachment getAttachment(
	//
	) throws Exception {
		//
		return new Attachment();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getCandidateNote", method = RequestMethod.GET, produces = "application/json")
	public CandidateNote getCandidateNote(
	//
	) throws Exception {
		//
		return new CandidateNote();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getJobNote", method = RequestMethod.GET, produces = "application/json")
	public JobNote getJobNote(
	//
	) throws Exception {
		//
		return new JobNote();
		//
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getTimesheetEntry", method = RequestMethod.GET, produces = "application/json")
	public TimesheetEntry getTimesheetEntry(
	//
	) throws Exception {
		//
		return new TimesheetEntry();
		//
	}
	
	@RequestMapping(value = "/getExpenseEntry", method = RequestMethod.GET, produces = "application/json")
	public ExpenseEntry getExpenseEntry(
	//
	) throws Exception {
		//
		return new ExpenseEntry();
		//
	}
	
	@RequestMapping(value = "/getTitleSkillCertification", method = RequestMethod.GET, produces = "application/json")
	public TitleSkillCertification getTitleSkillCertification(
	//
	) throws Exception {
		//
		return new TitleSkillCertification();
		//
	}
	
	@RequestMapping(value = "/getCandidateUDF", method = RequestMethod.GET, produces = "application/json")
	public CandidateUDF getCandidateUDF(
	//
	) throws Exception {
		//
		return new CandidateUDF();
		//
	}
	
	@RequestMapping(value = "/getCandidateQual", method = RequestMethod.GET, produces = "application/json")
	public CandidateQual getCandidateQual(
	//
	) throws Exception {
		//
		return new CandidateQual();
		//
	}
	
	@RequestMapping(value = "/getQualificationType", method = RequestMethod.GET, produces = "application/json")
	public QualificationType getQualificationType(
	//
	) throws Exception {
		//
		return new QualificationType();
		//
	}
	
	@RequestMapping(value = "/getUserfield", method = RequestMethod.GET, produces = "application/json")
	public Userfield getUserfield(
	//
	) throws Exception {
		//
		return new Userfield();
		//
	}
}

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
import com.jobdiva.api.model.Contact;
import com.jobdiva.api.model.ContactAddress;
import com.jobdiva.api.model.ContactNote;
import com.jobdiva.api.model.ContactType;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.PhoneType;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.service.ContactNoteService;
import com.jobdiva.api.service.ContactService;
import com.jobdiva.api.service.ContactTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "Contact API", description = "REST API Used For Contact")
public class ContactController extends AbstractJobDivaController {
	
	@Autowired
	ContactService		contactService;
	//
	@Autowired
	ContactNoteService	contactNoteService;
	//
	@Autowired
	ContactTypeService	contactTypeService;
	
	@RequestMapping(value = "/SearchContacts", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Contacts")
	public List<Contact> SearchContacts( //
			//
			@ApiParam(value = "Contact Id", required = false) //
			@RequestParam(required = false) Long contactId, //
			//
			@ApiParam(value = "First Name", required = false) //
			@RequestParam(required = false) String firstname, //
			//
			@ApiParam(value = "Last Name", required = false) //
			@RequestParam(required = false) String lastname, //
			//
			@ApiParam(value = "Company", required = false) //
			@RequestParam(required = false) String company, //
			//
			@ApiParam(value = "Email", required = false) //
			@RequestParam(required = false) String email, //
			//
			@ApiParam(value = "Title", required = false) //
			@RequestParam(required = false) String title, //
			//
			@ApiParam(value = "Address", required = false) //
			@RequestParam(required = false) String address, //
			//
			@ApiParam(value = "City", required = false) //
			@RequestParam(required = false) String city, //
			//
			@ApiParam(value = "State", required = false) //
			@RequestParam(required = false) String state, //
			//
			@ApiParam(value = "ZipCode", required = false) //
			@RequestParam(required = false) String zipCode, //
			//
			@ApiParam(value = "Country", required = false) //
			@RequestParam(required = false) String country, //
			//
			@ApiParam(value = "Phone", required = false) //
			@RequestParam(required = false) String phone, //
			//
			@ApiParam(value = "Division", required = false) //
			@RequestParam(required = false) String division, //
			//
			@ApiParam(value = "Types", required = false) //
			@RequestParam(required = false) String[] types, //
			//
			@ApiParam(value = "Show Primary", required = false) //
			@RequestParam(required = false) Boolean showPrimary, //
			//
			@ApiParam(value = "Owner Ids", required = false) //
			@RequestParam(required = false) Long ownerIds, //
			//
			@ApiParam(value = "Show Inactive", required = false) //
			@RequestParam(required = false) Boolean showInactive //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("SearchContacts");
		//
		return contactService.searchContacts(jobDivaSession, jobDivaSession.getTeamId(), contactId, firstname, lastname, email, title, phone, //
				company, address, city, state, zipCode, country, null, types, ownerIds, division, showPrimary, showInactive);
		//
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "phones", required = false, allowMultiple = true, dataType = "PhoneType"), //
			@ApiImplicitParam(name = "addresses", required = false, allowMultiple = true, dataType = "ContactAddress"), //
			@ApiImplicitParam(name = "owners", required = false, allowMultiple = true, dataType = "Owner") //
	})
	@RequestMapping(value = "/createContact", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Contact")
	public Long createContact( //
			//
			@ApiParam(value = "Company Name", required = false) //
			@RequestParam(required = false) String company, //
			//
			@ApiParam(value = "Contact First Name", required = true) //
			@RequestParam(required = true) String firstname, //
			//
			@ApiParam(value = "Contact Last Name", required = true) //
			@RequestParam(required = true) String lastname, //
			//
			@ApiParam(value = "Title", required = false) //
			@RequestParam(required = false) String title, //
			//
			@ApiParam(value = "Department", required = false) //
			@RequestParam(required = false) String department, //
			//
			@ApiParam(value = "• Set at most 4 phone numbers.\r\n" + //
					"• Set numbers and extensions in “phone” and “ext” respectively.\r\n" + //
					"• Set “action” to 1 for insertion.\r\n" + //
					"• Do not set extensions for types other than work phone or pager.\r\n" + //
					"Valid “type” (case insensitive)\r\n" + //
					"Value\r\n" + //
					"Description\r\n" + //
					"W\r\n" + //
					"Work #\r\n" + //
					"H\r\n" + //
					"Home #\r\n" + //
					"C\r\n" + //
					"Cell #\r\n" + //
					"F\r\n" + //
					"Work Fax\r\n" + //
					"X\r\n" + //
					"Home Fax\r\n" + //
					"P\r\n" + //
					"Pager\r\n" + //
					"M\r\n" + //
					"Main #\r\n" + //
					"D\r\n" + //
					"Direct #\r\n" + //
					"O\r\n" + "Other #", required = false, type = "PhoneType", allowMultiple = true) //
			@RequestParam(required = false) PhoneType[] phones, //
			//
			@ApiParam(value = "• Set “default” to true for a default address. One and only one address can be set as default.<BR>" + //
					"• “addressid” can be left unset.<BR>" + //
					"• Set “action” to 1 for insertion.", required = false, type = "ContactAddress", allowMultiple = true) //
			@RequestParam(required = false) ContactAddress[] addresses, //
			//
			@ApiParam(value = "Email", required = false) //
			@RequestParam(required = false) String email, //
			//
			@ApiParam(value = "Alternate Email", required = false) //
			@RequestParam(required = false) String alternateemail, //
			//
			@ApiParam(value = "Array of contact types, valid values can be found through “Leader Tools” → “My Team” → “Manage Contact Types”.", required = true) //
			@RequestParam(required = true) String[] types, //
			//
			@ApiParam(value = "• Set “primary” to true for at most one owner. • Set either “ownerid” or both “firstname” and “lastname”(owner name).", required = false, type = "Owner", allowMultiple = true) //
			@RequestParam(required = false) Owner[] owners, //
			//
			@ApiParam(value = "Valid only if “company” is set.", required = false) //
			@RequestParam(required = false) Long reportsto, //
			//
			@ApiParam(value = "Primary contact", required = false) //
			@RequestParam(required = false) Boolean primary, //
			//
			@ApiParam(value = "Assistant Name", required = false) //
			@RequestParam(required = false) String assistantname, //
			//
			@ApiParam(value = "Assistant Email", required = false) //
			@RequestParam(required = false) String assistantemail, //
			//
			@ApiParam(value = "Assistant Phone", required = false) //
			@RequestParam(required = false) String assistantphone, //
			//
			@ApiParam(value = "Assistant Phone Extension", required = false) //
			@RequestParam(required = false) String assistantphoneextension, //
			//
			@ApiParam(value = "Submittal Instructions", required = false) //
			@RequestParam(required = false) String subguidelines, //
			//
			@ApiParam(value = "Max Allowed Submittals", required = false) //
			@RequestParam(required = false) Integer maxsubmittals, //
			//
			@ApiParam(value = "References", required = false) //
			@RequestParam(required = false) Boolean references, //
			//
			@ApiParam(value = "Drug Test", required = false) //
			@RequestParam(required = false) Boolean drugtest, //
			//
			@ApiParam(value = "Background Check", required = false) //
			@RequestParam(required = false) Boolean backgroundcheck, //
			//
			@ApiParam(value = "Security Clearance", required = false) //
			@RequestParam(required = false) Boolean securityclearance //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createContact");
		//
		return contactService.createContact(jobDivaSession, company, firstname, lastname, title, department, phones, addresses, email, alternateemail, types, owners, reportsto, primary, assistantname, assistantemail, assistantphone,
				assistantphoneextension, subguidelines, maxsubmittals, references, drugtest, backgroundcheck, securityclearance);
	}
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "phones", required = false, allowMultiple = true, dataType = "PhoneType"), //
			@ApiImplicitParam(name = "addresses", required = false, allowMultiple = true, dataType = "ContactAddress"), //
			@ApiImplicitParam(name = "Userfields", required = false, allowMultiple = true, dataType = "Userfield"), //
			@ApiImplicitParam(name = "owners", required = false, allowMultiple = true, dataType = "Owner")//
	})
	@RequestMapping(value = "/updateContact", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Contact")
	public Boolean updateContact( //
			//
			@ApiParam(value = "Contact ID", required = true) //
			@RequestParam(required = true) Long contactid, //
			//
			@ApiParam(value = "Contact First Name", required = false) //
			@RequestParam(required = false) String firstname, //
			//
			@ApiParam(value = "Contact Last Name", required = false) //
			@RequestParam(required = false) String lastname, //
			//
			@ApiParam(value = "Title", required = false) //
			@RequestParam(required = false) String title, //
			//
			@ApiParam(value = "Company ID", required = false) //
			@RequestParam(required = false) Long companyid, //
			//
			@ApiParam(value = "Department", required = false) //
			@RequestParam(required = false) String department, //
			//
			@ApiParam(value = "Array of ContactAddressType\r\n" + //
					"• Set “default” to true for a default address. One and only one address can be set as default.\r\n" + //
					"• Set “action” to 1 for insertion or updates, 2 for deletion.", required = false, type = "CompanyAddress", allowMultiple = true) //
			@RequestParam(required = false) ContactAddress[] addresses, //
			//
			@ApiParam(value = "• Set at most 4 phone numbers.\r\n" + //
					"• Set numbers and extensions in “phone” and “ext” respectively.\r\n" + //
					"• Set “action” to 1 for insertion.\r\n" + //
					"• Do not set extensions for types other than work phone or pager.\r\n" + //
					"Valid “type” (case insensitive)\r\n" + //
					"Value\r\n" + //
					"Description\r\n" + //
					"W\r\n" + //
					"Work #\r\n" + //
					"H\r\n" + //
					"Home #\r\n" + //
					"C\r\n" + //
					"Cell #\r\n" + //
					"F\r\n" + //
					"Work Fax\r\n" + //
					"X\r\n" + //
					"Home Fax\r\n" + //
					"P\r\n" + //
					"Pager\r\n" + //
					"M\r\n" + //
					"Main #\r\n" + //
					"D\r\n" + //
					"Direct #\r\n" + //
					"O\r\n" + "Other #", required = false, type = "PhoneType", allowMultiple = true) //
			@RequestParam(required = false) PhoneType[] phones, //
			//
			@ApiParam(value = "Contact ID of a customer being reported to", required = false) //
			@RequestParam(required = false) Long reportsto, //
			//
			@ApiParam(required = false) //
			@RequestParam(required = false) Boolean active, //
			//
			@ApiParam(value = "Primary Contact", required = false) //
			@RequestParam(required = false) Boolean primary, //
			//
			@ApiParam(value = "Email", required = false) //
			@RequestParam(required = false) String email, //
			//
			@ApiParam(value = "Alternate Email", required = false) //
			@RequestParam(required = false) String alternateemail, //
			//
			@ApiParam(value = "Assistant Name", required = false) //
			@RequestParam(required = false) String assistantname, //
			//
			@ApiParam(value = "Assistant Email", required = false) //
			@RequestParam(required = false) String assistantemail, //
			//
			@ApiParam(value = "Assistant Phone", required = false) //
			@RequestParam(required = false) String assistantphone, //
			//
			@ApiParam(value = "Assistant Phone Extension", required = false) //
			@RequestParam(required = false) String assistantphoneextension, //
			//
			@ApiParam(value = "Submittal Instructions", required = false) //
			@RequestParam(required = false) String subguidelines, //
			//
			@ApiParam(value = "Max Allowed Submittals", required = false) //
			@RequestParam(required = false) Integer maxsubmittals, //
			//
			@ApiParam(value = "References", required = false) //
			@RequestParam(required = false) Boolean references, //
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
			@ApiParam(value = "User defined fields. Valid “userfieldid” can be found through “Leader Tools” → “My Team” → “User-Defined Fields” → “Contacts” → “UDF #”", required = false, type = "Userfield", allowMultiple = true) //
			@RequestParam(required = false) Userfield[] Userfields, //
			//
			@ApiParam(value = "", required = false, type = "Owner", allowMultiple = true) //
			@RequestParam(required = false) Owner[] owners //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateContact");
		//
		return contactService.updateContact(jobDivaSession, contactid, firstname, lastname, title, companyid, department, addresses, phones, reportsto, active, primary, email, alternateemail, assistantname, assistantemail, assistantphone,
				assistantphoneextension, subguidelines, maxsubmittals, references, drugtest, backgroundcheck, securityclearance, Userfields, owners);
	}
	
	@RequestMapping(value = "/createContactNote", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Contact Note")
	public Boolean createContactNote( //
			//
			@ApiParam(value = "Contact ID", type = "Long", required = true) //
			@RequestParam(required = true) Long contactid, //
			//
			@ApiParam(value = "Note", type = "String", required = true) //
			@RequestParam(required = false) String note, //
			//
			@ApiParam(value = "Creator ID, defaults to API user ID if not set.", type = "Long", required = false) //
			@RequestParam(required = false) Long recruiterid, //
			//
			@ApiParam(required = false, type = "String", value = "Contact action. Valid action type can be found through “Leader Tools” → “My Team” → “Manage Contact Actions”. Please make sure the chosen action type is already activated.") //
			@RequestParam(required = false) String action, //
			//
			@ApiParam(required = false, type = "Date", value = "Action date,  <BR>defaults to current date and time, can only be set when contact action is set. Format [MM/dd/yyyy HH:mm:ss]") //
			@RequestParam(required = false) Date actionDate, //
			//
			@ApiParam(required = false, type = "Long", value = "Job ID") //
			@RequestParam(required = false) Long link2AnOpenJob, //
			//
			@ApiParam(required = false, type = "Long", value = "Candidate ID") //
			@RequestParam(required = false) Long link2ACandidate //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createContactNote");
		//
		return contactService.createContactNote(jobDivaSession, contactid, note, recruiterid, action, actionDate, link2AnOpenJob, link2ACandidate);
		//
	}
	
	@RequestMapping(value = "/GetContactById", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Get Contact By Id")
	public Contact GetContactById( //
			@ApiParam(value = "contactId", required = true) //
			@RequestParam(required = true) Long contactId //
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("GetContactById");
		//
		return contactService.getContactById(jobDivaSession, jobDivaSession.getTeamId(), contactId);
		//
	}
	
	@RequestMapping(value = "/GetContactNotes", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Get Contact Notes")
	public List<ContactNote> GetContactNotes( //
			@ApiParam(value = "contactId", required = true) //
			@RequestParam(required = true) Long contactId //
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("GetContactNotes");
		//
		return contactNoteService.getContactNotes(jobDivaSession, contactId);
		//
	}
	
	@RequestMapping(value = "/GetContactTypes", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Get Contact Types")
	public List<ContactType> GetContactTypes( //
			@ApiParam(value = "contactId", required = true) //
			@RequestParam(required = true) Long contactId //
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("GetContactTypes");
		//
		return contactTypeService.getContactNotes(jobDivaSession, jobDivaSession.getTeamId(), contactId);
		//
	}
}

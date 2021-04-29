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
import com.jobdiva.api.model.Contact;
import com.jobdiva.api.model.ContactAddress;
import com.jobdiva.api.model.ContactNote;
import com.jobdiva.api.model.ContactType;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.PhoneType;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.v2.contact.CreateContactDef;
import com.jobdiva.api.model.v2.contact.CreateContactNoteDef;
import com.jobdiva.api.model.v2.contact.SearchContactsDef;
import com.jobdiva.api.model.v2.contact.UpdateContactDef;
import com.jobdiva.api.service.ContactNoteService;
import com.jobdiva.api.service.ContactService;
import com.jobdiva.api.service.ContactTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/apiv2/jobdiva/")
@Api(value = "Contact API", description = "REST API Used For Contact")
// @ApiIgnore
public class ContactV2Controller extends AbstractJobDivaController {
	
	@Autowired
	ContactService		contactService;
	//
	@Autowired
	ContactNoteService	contactNoteService;
	//
	@Autowired
	ContactTypeService	contactTypeService;
	
	@RequestMapping(value = "/SearchContacts", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Search Contacts")
	public List<Contact> SearchContacts( //
			//
			@ApiParam(value = "contactId : Contact Id \r\n" //
					+ "firstname : First Name \r\n" //
					+ "lastname : Last Name \r\n" //
					+ "company : Company \r\n" //
					+ "email : Email \r\n" //
					+ "title : Title \r\n" //
					+ "address : Address \r\n" //
					+ "city : City \r\n" //
					+ "state : State \r\n" //
					+ "zipCode : ZipCode \r\n" //
					+ "country : Country \r\n" //
					+ "phone : Phone \r\n" //
					+ "division : Division \r\n" //
					+ "types : Types \r\n" //
					+ "showPrimary : Show Primary \r\n" //
					+ "ownerIds : Owner Ids \r\n" //
					+ "showInactive : Show Inactive \r\n") //
			@RequestBody SearchContactsDef searchContactsDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("SearchContacts");
		//
		Long contactId = searchContactsDef.getContactId();
		String firstname = searchContactsDef.getFirstname();
		String lastname = searchContactsDef.getLastname();
		String company = searchContactsDef.getCompany();
		String email = searchContactsDef.getEmail();
		String title = searchContactsDef.getTitle();
		String address = searchContactsDef.getAddress();
		String city = searchContactsDef.getCity();
		String state = searchContactsDef.getState();
		String zipCode = searchContactsDef.getZipCode();
		String country = searchContactsDef.getCountry();
		String phone = searchContactsDef.getPhone();
		String division = searchContactsDef.getDivision();
		String[] types = searchContactsDef.getTypes();
		Boolean showPrimary = searchContactsDef.getShowPrimary();
		Long ownerIds = searchContactsDef.getOwnerIds();
		Boolean showInactive = searchContactsDef.getShowInactive();
		//
		//
		return contactService.searchContacts(jobDivaSession, jobDivaSession.getTeamId(), contactId, firstname, lastname, email, title, phone, //
				company, address, city, state, zipCode, country, null, types, ownerIds, division, showPrimary, showInactive);
		//
	}
	
	@RequestMapping(value = "/createContact", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Contact")
	public Long createContact( //
			//
			//
			@ApiParam(value = "company : Company Name \r\n" //
					+ "firstname : Contact First Name \r\n" //
					+ "lastname : Contact Last Name \r\n" //
					+ "title : Title \r\n" //
					+ "department : Department \r\n" //
					+ "phones : • Set at most 4 phone numbers.\r\n" + // //
					"• Set numbers and extensions in “phone” and “ext” respectively.\r\n" + // //
					"• Set “action” to 1 for insertion.\r\n" + // //
					"• Do not set extensions for types other than work phone or pager.\r\n" + // //
					"Valid “type” (case insensitive)\r\n" + // //
					"Value\r\n" + // //
					"Description\r\n" + // //
					"W\r\n" + // //
					"Work #\r\n" + // //
					"H\r\n" + // //
					"Home #\r\n" + // //
					"C\r\n" + // //
					"Cell #\r\n" + // //
					"F\r\n" + // //
					"Work Fax\r\n" + // //
					"X\r\n" + // //
					"Home Fax\r\n" + // //
					"P\r\n" + // //
					"Pager\r\n" + // //
					"M\r\n" + // //
					"Main #\r\n" + // //
					"D\r\n" + // //
					"Direct #\r\n" + // //
					"O\r\n" + "Other # \r\n \r\n" //
					+ "addresses : • Set “default” to true for a default address. One and only one address can be set as default.\r\n" + // //
					"• “addressid” can be left unset.\r\n" + // //
					"• Set “action” to 1 for insertion. \r\n \r\n" //
					+ "email : Email \r\n " //
					+ "alternateemail : Alternate Email \r\n " //
					+ "types : Array of contact types, valid values can be found through “Leader Tools” → “My Team” → “Manage Contact Types”. \r\n" //
					+ "owners : • Set “primary” to true for at most one owner. • Set either “ownerid” or both “firstname” and “lastname”(owner name). \r\n" //
					+ "reportsto : Valid only if “company” is set. \r\n" //
					+ "primary : Primary contact \r\n" //
					+ "assistantname : Assistant Name \r\n" //
					+ "assistantemail : Assistant Email \r\n" //
					+ "assistantphone : Assistant Phone \r\n" //
					+ "assistantphoneextension : Assistant Phone Extension \r\n" //
					+ "subguidelines : Submittal Instructions \r\n" //
					+ "maxsubmittals : Max Allowed Submittals \r\n" //
					+ "references : References \r\n" //
					+ "drugtest : Drug Test \r\n" //
					+ "backgroundcheck : Background Check \r\n" //
					+ "securityclearance : Security Clearance" //
																//
			) @RequestBody CreateContactDef createContactDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createContact");
		//
		//
		String company = createContactDef.getCompany();
		String firstname = createContactDef.getFirstname();
		String lastname = createContactDef.getLastname();
		String title = createContactDef.getTitle();
		String department = createContactDef.getDepartment();
		PhoneType[] phones = createContactDef.getPhones();
		ContactAddress[] addresses = createContactDef.getAddresses();
		String email = createContactDef.getEmail();
		String alternateemail = createContactDef.getAlternateemail();
		String[] types = createContactDef.getTypes();
		Owner[] owners = createContactDef.getOwners();
		Long reportsto = createContactDef.getReportsto();
		Boolean primary = createContactDef.getPrimary();
		String assistantname = createContactDef.getAssistantname();
		String assistantemail = createContactDef.getAssistantemail();
		String assistantphone = createContactDef.getAssistantphone();
		String assistantphoneextension = createContactDef.getAssistantphoneextension();
		String subguidelines = createContactDef.getSubguidelines();
		Integer maxsubmittals = createContactDef.getMaxsubmittals();
		Boolean references = createContactDef.getReferences();
		Boolean drugtest = createContactDef.getDrugtest();
		Boolean backgroundcheck = createContactDef.getBackgroundcheck();
		Boolean securityclearance = createContactDef.getSecurityclearance();
		//
		//
		return contactService.createContact(jobDivaSession, company, firstname, lastname, title, department, phones, addresses, email, alternateemail, types, owners, reportsto, primary, assistantname, assistantemail, assistantphone,
				assistantphoneextension, subguidelines, maxsubmittals, references, drugtest, backgroundcheck, securityclearance);
	}
	
	@RequestMapping(value = "/updateContact", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Contact")
	public Boolean updateContact( //
			//
			@ApiParam(value = "contactid : Contact ID \r\n" //
					+ "firstname : Contact First Name \r\n" //
					+ "lastname : Contact Last Name \r\n" //
					+ "title : Title \r\n" //
					+ "companyid : Company ID \r\n" //
					+ "department : Department \r\n" //
					+ "addresses :  Array of ContactAddressType\r\n" + //
					"• Set “default” to true for a default address. One and only one address can be set as default.\r\n" + //
					"• Set “action” to 1 for insertion or updates, 2 for deletion. \r\n \r\n" + "phones : • Set at most 4 phone numbers.\r\n" + //
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
					"O\r\n" + "Other # \r\n \r\n" //
					+ "reportsto : Contact ID of a customer being reported to \r\n" //
					+ "active : Active \r\n " //
					+ "primary : Primary Contact \r\n" //
					+ "email : Email \r\n" //
					+ "alternateemail : Alternate Email \r\n" //
					+ "types : Contact types to update to. If specified, previous type assignment will be removed. \r\n" //
					+ "assistantname : Assistant Name \r\n" //
					+ "assistantemail : Assistant Email \r\n" //
					+ "assistantphone : Assistant Phone \r\n" //
					+ "assistantphoneextension : Assistant Phone Extension \r\n" //
					+ "subguidelines : Submittal Instructions \r\n" //
					+ "maxsubmittals : Max Allowed Submittals \r\n" //
					+ "references : References \r\n" //
					+ "drugtest : Drug Test \r\n" //
					+ "backgroundcheck : Background Check \r\n" //
					+ "securityclearance : Security Clearance \r\n" //
					+ "Userfields : User defined fields. Valid “userfieldid” can be found through “Leader Tools” → “My Team” → “User-Defined Fields” → “Contacts” → “UDF #” \r\n" //
					+ "owners : Owner") //
			@RequestBody UpdateContactDef updateContactDef//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateContact");
		//
		//
		//
		Long contactid = updateContactDef.getContactid();
		String firstname = updateContactDef.getFirstname();
		String lastname = updateContactDef.getLastname();
		String title = updateContactDef.getTitle();
		Long companyid = updateContactDef.getCompanyid();
		String department = updateContactDef.getDepartment();
		ContactAddress[] addresses = updateContactDef.getAddresses();
		PhoneType[] phones = updateContactDef.getPhones();
		Long reportsto = updateContactDef.getReportsto();
		Boolean active = updateContactDef.getActive();
		Boolean primary = updateContactDef.getPrimary();
		String email = updateContactDef.getEmail();
		String alternateemail = updateContactDef.getAlternateemail();
		String[] types = updateContactDef.getTypes();
		String assistantname = updateContactDef.getAssistantname();
		String assistantemail = updateContactDef.getAssistantemail();
		String assistantphone = updateContactDef.getAssistantphone();
		String assistantphoneextension = updateContactDef.getAssistantphoneextension();
		String subguidelines = updateContactDef.getSubguidelines();
		Integer maxsubmittals = updateContactDef.getMaxsubmittals();
		Boolean references = updateContactDef.getReferences();
		Boolean drugtest = updateContactDef.getDrugtest();
		Boolean backgroundcheck = updateContactDef.getBackgroundcheck();
		Boolean securityclearance = updateContactDef.getSecurityclearance();
		Userfield[] Userfields = updateContactDef.getUserfields();
		Owner[] owners = updateContactDef.getOwners();
		//
		//
		return contactService.updateContact(jobDivaSession, contactid, firstname, lastname, title, companyid, department, addresses, phones, reportsto, active, primary, email, alternateemail, assistantname, assistantemail, assistantphone,
				assistantphoneextension, subguidelines, maxsubmittals, references, drugtest, backgroundcheck, securityclearance, Userfields, owners, types);
	}
	
	@RequestMapping(value = "/createContactNote", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Contact Note")
	public Boolean createContactNote( //
			//
			@ApiParam(value = "contactid : Contact ID \r\n" //
					+ "note : Note \r\n" //
					+ "recruiterid : Creator ID, defaults to API user ID if not set. \r\n" //
					+ "action : Contact action. Valid action type can be found through “Leader Tools” → “My Team” → “Manage Contact Actions”. Please make sure the chosen action type is already activated. \r\n" //
					+ "actionDate Action date, defaults to current date and time, can only be set when contact action is set. \r\n" //
					+ "link2AnOpenJob : Job ID \r\n" //
					+ "link2ACandidate : Candidate ID " //
			) //
			@RequestBody CreateContactNoteDef createContactNoteDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createContactNote");
		//
		//
		Long contactid = createContactNoteDef.getContactid();
		String note = createContactNoteDef.getNote();
		Long recruiterid = createContactNoteDef.getRecruiterid();
		String action = createContactNoteDef.getAction();
		Date actionDate = createContactNoteDef.getActionDate();
		Long link2AnOpenJob = createContactNoteDef.getLink2AnOpenJob();
		Long link2ACandidate = createContactNoteDef.getLink2ACandidate();
		//
		//
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

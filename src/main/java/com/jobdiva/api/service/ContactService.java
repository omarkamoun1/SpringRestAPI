package com.jobdiva.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jobdiva.api.dao.contact.ContactAddressDao;
import com.jobdiva.api.dao.contact.ContactDao;
import com.jobdiva.api.dao.contact.ContactNoteDao;
import com.jobdiva.api.dao.contact.ContactUDFDao;
import com.jobdiva.api.model.Contact;
import com.jobdiva.api.model.ContactAddress;
import com.jobdiva.api.model.ContactUDF;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.PhoneType;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class ContactService {
	
	@Autowired
	ContactDao			contactDao;
	//
	@Autowired
	ContactAddressDao	contactAddressDao;
	//
	@Autowired
	ContactUDFDao		contactUDFDao;
	//
	@Autowired
	ContactNoteDao		contactNoteDao;
	
	public List<Contact> searchContacts(JobDivaSession jobDivaSession, Long teamId, Long contactId, String firstname, String lastname, String email, String title, //
			String phone, String company, String address, String city, String state, String zipCode, String country, Boolean onlyMyContacts, String[] types, Long ownerIds, //
			String division, Boolean showPrimary, Boolean showInactive) throws Exception {
		//
		try {
			//
			List<Contact> contactList = contactDao.searchContacts(jobDivaSession, teamId, contactId, firstname, lastname, email, title, phone, company, address, city, state, zipCode, country, //
					onlyMyContacts, types, ownerIds, division, showPrimary, showInactive);
			//
			if (contactList != null) {
				for (Contact contact : contactList) {
					//
					List<ContactAddress> contactAddresses = contactAddressDao.getContactAddresses(contact.getId(), teamId);
					contact.setContactAddresses(contactAddresses);
					//
					List<ContactUDF> contactUDFs = contactUDFDao.getContactUDF(contact.getId(), teamId);
					contact.setContactUDFs(contactUDFs);
				}
			}
			//
			contactDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchContacts", "Search Successful");
			//
			return contactList;
			//
		} catch (Exception e) {
			//
			contactDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchContacts", "Search Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Contact getContactById(JobDivaSession jobDivaSession, Long clientId, Long contactId) throws Exception {
		List<Contact> contactList = searchContacts(jobDivaSession, jobDivaSession.getTeamId(), contactId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		//
		try {
			//
			Contact contact = contactList != null && contactList.size() > 0 ? contactList.get(0) : null;
			//
			contactDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getContactById", "Get Contact Successful");
			//
			return contact;
			//
		} catch (Exception e) {
			//
			contactDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getContactById", "Get Contact Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean createContactNote(JobDivaSession jobDivaSession, Long contactid, String note, Long recruiterid, String action, Date actionDate, Long link2AnOpenJob, Long link2aCandidate) throws Exception {
		//
		try {
			//
			Boolean success = contactNoteDao.createContactNote(jobDivaSession, contactid, note, recruiterid, action, actionDate, link2AnOpenJob, link2aCandidate);
			//
			contactDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createContactNote", "Create Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			contactDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createContactNote", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Long createContact(JobDivaSession jobDivaSession, String company, String firstname, String lastname, String title, String department, PhoneType[] phones, ContactAddress[] addresses, String email, String alternateemail, String[] types,
			Owner[] owners, Long reportsto, Boolean primary, String assistantname, String assistantemail, String assistantphone, String assistantphoneextension, String subguidelines, Integer maxsubmittals, Boolean references, Boolean drugtest,
			Boolean backgroundcheck, Boolean securityclearance) throws Exception {
		//
		try {
			//
			Long contactId = contactDao.createContact(jobDivaSession, company, firstname, lastname, title, department, phones, addresses, email, alternateemail, types, owners, reportsto, primary, assistantname, assistantemail, assistantphone,
					assistantphoneextension, subguidelines, maxsubmittals, references, drugtest, backgroundcheck, securityclearance);
			//
			contactDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createContact", "Create Successful");
			//
			return contactId;
			//
		} catch (Exception e) {
			//
			contactDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createContact", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean updateContact(JobDivaSession jobDivaSession, Long contactid, String firstname, String lastname, String title, Long companyid, String department, ContactAddress[] addresses, PhoneType[] phones, Long reportsto, Boolean active,
			Boolean primary, String email, String alternateemail, String assistantname, String assistantemail, String assistantphone, String assistantphoneextension, String subguidelines, Integer maxsubmittals, Boolean references, Boolean drugtest,
			Boolean backgroundcheck, Boolean securityclearance, Userfield[] userfields, Owner[] owners) throws Exception {
		//
		try {
			//
			Boolean success = contactDao.updateContact(jobDivaSession, contactid, firstname, lastname, title, companyid, department, addresses, phones, reportsto, active, primary, email, alternateemail, assistantname, assistantemail, assistantphone,
					assistantphoneextension, subguidelines, maxsubmittals, references, drugtest, backgroundcheck, securityclearance, userfields, owners);
			//
			contactDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateContact", "Update Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			contactDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateContact", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
}

package com.jobdiva.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.contact.ContactTypeDao;
import com.jobdiva.api.model.ContactType;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class ContactTypeService {
	
	@Autowired
	ContactTypeDao contactTypeDao;
	//
	
	public List<ContactType> getContactNotes(JobDivaSession jobDivaSession, Long clientId, Long contactId) {
		//
		return contactTypeDao.getContactTypes(contactId,clientId);
		//
	}
}

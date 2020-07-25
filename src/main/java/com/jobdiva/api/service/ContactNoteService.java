package com.jobdiva.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.contact.ContactNoteDao;
import com.jobdiva.api.model.ContactNote;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class ContactNoteService {
	
	@Autowired
	ContactNoteDao contactNoteDao;
	//
	
	public List<ContactNote> getContactNotes(JobDivaSession jobDivaSession, Long contactId) throws Exception {
		//
		try {
			//
			List<ContactNote> contactNotes = contactNoteDao.getContactNotes(contactId, jobDivaSession.getTeamId());
			//
			contactNoteDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getContactNotes", "Get Contact Notes Successful ");
			//
			return contactNotes;
			//
		} catch (Exception e) {
			//
			contactNoteDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getContactNotes", "Get Contact Notes Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
}

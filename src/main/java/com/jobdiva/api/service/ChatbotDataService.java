package com.jobdiva.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.chatbot.ChatbotTrainingDataDao;
import com.jobdiva.api.model.SessionInfo;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.chatbot.ChatbotAnswer;
import com.jobdiva.api.model.chatbot.ChatbotCandidatePackges;
import com.jobdiva.api.model.chatbot.ChatbotHarvestAccount;
import com.jobdiva.api.model.chatbot.ChatbotHarvestMachineStatus;
import com.jobdiva.api.model.chatbot.ChatbotHarvestStatus;
import com.jobdiva.api.model.chatbot.ChatbotQuestion;
import com.jobdiva.api.model.chatbot.ChatbotSocialQuestion;
import com.jobdiva.api.model.chatbot.ChatbotTag;
import com.jobdiva.api.model.chatbot.ChatbotTagValue;
import com.jobdiva.api.model.chatbot.ChatbotUserData;
import com.jobdiva.api.model.chatbot.ChatbotVMSAccount;
import com.jobdiva.api.model.chatbot.ChatbotVMSType;
import com.jobdiva.api.model.chatbot.ChatbotVisibility;

@Service
public class ChatbotDataService {
	
	@Autowired
	ChatbotTrainingDataDao trainingDataDao;
	
	
	public SessionInfo getSession(JobDivaSession session) throws Exception {
		return trainingDataDao.getSession(session);
	}
	
	
	public List<ChatbotQuestion> getChatbotQuestions(JobDivaSession session) throws Exception {
		try {
			List<ChatbotQuestion> questions = trainingDataDao.getQuestions(session);
			return questions;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public ChatbotAnswer getChatbotAnswer(JobDivaSession session, Integer refID) throws Exception {
		try {
			ChatbotAnswer answer = trainingDataDao.getAnswer(session, refID);
			return answer;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public List<ChatbotSocialQuestion> getChatbotSocialQuestions(JobDivaSession session) throws Exception {
		try {
			List<ChatbotSocialQuestion> questions = trainingDataDao.getSocialQuestions(session);
			return questions;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public ChatbotAnswer getSocialQuestionAnswer(JobDivaSession session, Integer refID) throws Exception {
		try {
			ChatbotAnswer answer = trainingDataDao.getSocialAnswer(session, refID);
			return answer;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public ChatbotQuestion getChatbotQuestion(JobDivaSession session, Integer refID) throws Exception {
		try {
			ChatbotQuestion question = trainingDataDao.getQuestion(session, refID);
			return question;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public ChatbotUserData getUserData(JobDivaSession jobDivaSession) throws Exception {
		try {
			ChatbotUserData data = trainingDataDao.getUserData(jobDivaSession);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public ChatbotVisibility getChatbotVisibility(JobDivaSession jobDivaSession) throws Exception {
		try {
			ChatbotVisibility data = trainingDataDao.checkChatbotVisibility(jobDivaSession);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public ChatbotHarvestStatus getChatbotHarvestStatus(JobDivaSession jobDivaSession, Long webid) throws Exception {
		try {
			ChatbotHarvestStatus data = trainingDataDao.getChatbotHarvestStatus(jobDivaSession, webid);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public List<ChatbotHarvestAccount> getHarvestAccountStatus(JobDivaSession jobDivaSession, Long webid, Long machineNo) throws Exception {
		try {
			List<ChatbotHarvestAccount> data = trainingDataDao.getHarvesetAccountStatus(jobDivaSession, webid, machineNo);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public List<ChatbotVMSAccount> getVMSAccountsStatus(JobDivaSession jobDivaSession) throws Exception {
		try {
			List<ChatbotVMSAccount> data = trainingDataDao.getVMSAccountsStatus(jobDivaSession);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public List<ChatbotTag> getChatbotTagList(JobDivaSession jobDivaSession) throws Exception {
		try {
			List<ChatbotTag> data = trainingDataDao.getChatbotTagList(jobDivaSession);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public List<ChatbotHarvestMachineStatus> getHarvestMachineStatus(JobDivaSession jobDivaSession) throws Exception {
		try {
			List<ChatbotHarvestMachineStatus> data = trainingDataDao.getHarvestMachineStatus(jobDivaSession);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public ChatbotTagValue getChatbotTagValue(JobDivaSession jobDivaSession, String tagName, String references[]) throws Exception {
		try {
			ChatbotTagValue data = trainingDataDao.getChatbotTagValue(jobDivaSession, tagName, references);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public List<ChatbotVMSType> getChatbotVMSTypes(JobDivaSession jobDivaSession) throws Exception {
		try {
			List<ChatbotVMSType> data = trainingDataDao.getChatbotVMSTypes(jobDivaSession);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public boolean setChatbotVMSType(JobDivaSession jobDivaSession, String vms_name, Boolean hasJobCoddler, Boolean hasTimeSheetCoddler, Boolean hasSubmittalCoddler) throws Exception {
		try {
			Boolean data = trainingDataDao.setChatbotVMSType(jobDivaSession, vms_name, hasJobCoddler, hasTimeSheetCoddler, hasSubmittalCoddler);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public boolean deleteChatbotVMSType(JobDivaSession jobDivaSession, String vms_name) throws Exception {
		try {
			Boolean data = trainingDataDao.deleteChatbotVMSType(jobDivaSession, vms_name);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public ChatbotCandidatePackges getCandidatePackges(JobDivaSession jobDivaSession, String email) throws Exception {
		try {
			ChatbotCandidatePackges data = trainingDataDao.getCandidatePackges(jobDivaSession, email);
			return data;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}

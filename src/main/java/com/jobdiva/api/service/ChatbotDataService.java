package com.jobdiva.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.chatbot.ChatbotTrainingDataDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.chatbot.ChatbotAnswer;
import com.jobdiva.api.model.chatbot.ChatbotQuestion;
import com.jobdiva.api.model.chatbot.ChatbotSocialQuestion;
import com.jobdiva.api.model.chatbot.ChatbotUserData;
import com.jobdiva.api.model.chatbot.ChatbotVisibility;

@Service
public class ChatbotDataService {
	
	@Autowired
	ChatbotTrainingDataDao trainingDataDao;
	
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
}

package com.jobdiva.api.controller.chatbot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.chatbot.ChatbotAnswer;
import com.jobdiva.api.model.chatbot.ChatbotQuestion;
import com.jobdiva.api.model.chatbot.ChatbotSocialQuestion;
import com.jobdiva.api.model.chatbot.ChatbotTag;
import com.jobdiva.api.model.chatbot.ChatbotTagValue;
import com.jobdiva.api.model.chatbot.ChatbotUserData;
import com.jobdiva.api.model.chatbot.ChatbotVisibility;
import com.jobdiva.api.service.ChatbotDataService;
import com.jobdiva.api.service.LogService;

import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/chatbot/")
@ApiIgnore
@CrossOrigin(origins = "*")
public class SupportChatbotController extends AbstractJobDivaController {
	
	@Autowired
	ChatbotDataService	chatbotDataService;
	//
	@Autowired
	LogService			logService;
	
	@ApiIgnore
	@GetMapping(value = "/getSession", produces = "application/json")
	public JobDivaSession getSession() throws Exception {
		return getJobDivaSession();
	}
	
	@GetMapping(value = "/questions", produces = "application/json")
	public List<ChatbotQuestion> getQuestions() throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getChatbotQuestions(jobDivaSession);
	}
	
	@GetMapping(value = "/social-questions", produces = "application/json")
	public List<ChatbotSocialQuestion> getSocialQuestions() throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getChatbotSocialQuestions(jobDivaSession);
	}
	
	@GetMapping(value = "/question", produces = "application/json")
	public ChatbotQuestion getQuestion(@ApiParam(value = "questionid", required = true) //
	@RequestParam(required = false) Integer questionid //
	) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getChatbotQuestion(jobDivaSession, questionid);
	}
	
	@GetMapping(value = "/answer", produces = "application/json")
	public ChatbotAnswer getAnswer(@ApiParam(value = "questionid", required = true) //
	@RequestParam(required = false) Integer questionid //
	) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getChatbotAnswer(jobDivaSession, questionid);
	}
	
	@GetMapping(value = "/social-question", produces = "application/json")
	public ChatbotAnswer getSocialQuestion(@ApiParam(value = "questionid", required = true) //
	@RequestParam(required = false) Integer questionid //
	) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getSocialQuestionAnswer(jobDivaSession, questionid);
	}
	
	@GetMapping(value = "/user-data", produces = "application/json")
	public ChatbotUserData getRecruiterData() throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getUserData(jobDivaSession);
	}
	
	@GetMapping(value = "/visibility", produces = "application/json")
	public ChatbotVisibility getChatbotVisibility() throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getChatbotVisibility(jobDivaSession);
	}
	
	@GetMapping(value = "/tag-list", produces = "application/json")
	public List<ChatbotTag> getChatbotTagList() throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getChatbotTagList(jobDivaSession);
	}
	
	@GetMapping(value = "/tag-value", produces = "application/json")
	public ChatbotTagValue getChatbotTagVaules(@ApiParam(value = "tag", required = true) //
	@RequestParam(required = true) String tag, //
			@ApiParam(value = "references", required = false) //
			@RequestParam(required = false) String[] references //
	) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getChatbotTagValue(jobDivaSession, tag, references);
	}
	/**
	 * @CrossOrigin(origins="*") @GetMapping(value = "/user-profile", produces =
	 *                           "application/json") public ChatbotRecruiterData
	 *                           getRecruiterData() { return chatbotDataService
	 *                           }
	 ***/
}

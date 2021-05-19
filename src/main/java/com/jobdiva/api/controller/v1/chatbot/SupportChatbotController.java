package com.jobdiva.api.controller.v1.chatbot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.SessionInfo;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.chatbot.ChatbotAnswer;
import com.jobdiva.api.model.chatbot.ChatbotCandidatePackges;
import com.jobdiva.api.model.chatbot.ChatbotHarvestAccount;
import com.jobdiva.api.model.chatbot.ChatbotHarvestMachineStatus;
import com.jobdiva.api.model.chatbot.ChatbotHarvestStatus;
import com.jobdiva.api.model.chatbot.ChatbotQuestion;
import com.jobdiva.api.model.chatbot.ChatbotRecruiterInfo;
import com.jobdiva.api.model.chatbot.ChatbotSocialQuestion;
import com.jobdiva.api.model.chatbot.ChatbotTag;
import com.jobdiva.api.model.chatbot.ChatbotTagValue;
import com.jobdiva.api.model.chatbot.ChatbotUserData;
import com.jobdiva.api.model.chatbot.ChatbotVMSAccount;
import com.jobdiva.api.model.chatbot.ChatbotVMSType;
import com.jobdiva.api.model.chatbot.ChatbotVisibility;
import com.jobdiva.api.model.chatbot.ChatbotEmailAlert;
import com.jobdiva.api.service.ChatbotDataService;

import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/chatbot/")
@ApiIgnore
@CrossOrigin(origins = "*")
public class SupportChatbotController extends AbstractJobDivaController {
	
	@Autowired
	ChatbotDataService chatbotDataService;
	//
	
	@GetMapping(value = "/getSession", produces = "application/json")
	public SessionInfo getSession() throws Exception {
		return chatbotDataService.getSession(getJobDivaSession());
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
	
	@GetMapping(value = "/harvest-status", produces = "application/json")
	public ChatbotHarvestStatus getHarvestStatus(@ApiParam(value = "webid", required = true) //
	@RequestParam(required = true) Long webid //
	) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getChatbotHarvestStatus(jobDivaSession, webid);
	}
	
	@GetMapping(value = "/harvest-machine-status", produces = "application/json")
	public List<ChatbotHarvestMachineStatus> getHarvestStatus() throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getHarvestMachineStatus(jobDivaSession);
	}
	
	@GetMapping(value = "/vms-status", produces = "application/json")
	public List<ChatbotVMSAccount> getVMSAccountsStatus() throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getVMSAccountsStatus(jobDivaSession);
	}
	
	@GetMapping(value = "/harvest-account-status", produces = "application/json")
	public List<ChatbotHarvestAccount> getHarvestAccountStatus(@ApiParam(value = "webid", required = false) //
	@RequestParam(required = false) Long webid, //
			@ApiParam(value = "machineNo", required = false) //
			@RequestParam(required = false) Long machineNo //
	) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getHarvestAccountStatus(jobDivaSession, webid, machineNo);
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
	
	@GetMapping(value = "/getVMSTypes", produces = "application/json")
	public List<ChatbotVMSType> getChatbotVMSTypes() throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getChatbotVMSTypes(jobDivaSession);
	}
	
	@GetMapping(value = "/setVMSType", produces = "application/json")
	public Boolean setChatbotVMSType(@ApiParam(value = "vms_name", required = true) //
	@RequestParam(required = true) String vms_name, //
	@ApiParam(value = "hasJobCoddler", required = false) //
	@RequestParam(required = false) Boolean hasJobCoddler, //
	@ApiParam(value = "hasTimesheetCoddler", required = false) //
	@RequestParam(required = false) Boolean hasTimesheetCoddler, // 
	@ApiParam(value = "hasSubmittalCoddler", required = false) //
	@RequestParam(required = false) Boolean hasSubmittalCoddler 
	) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.setChatbotVMSType(jobDivaSession, vms_name, hasJobCoddler, hasTimesheetCoddler, hasSubmittalCoddler);
	}
	
	@GetMapping(value = "/deleteVMSType", produces = "application/json")
	public Boolean deleteChatbotVMSType(@ApiParam(value = "vms_name", required = true) //
	@RequestParam(required = true) String vms_name
	) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.deleteChatbotVMSType(jobDivaSession, vms_name);
	}

	@GetMapping(value = "/get-candidate-packges", produces = "application/json")
	public ChatbotCandidatePackges getCandidatePackges(@ApiParam(value = "email", required = true) //
	@RequestParam(required = true) String email) throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getCandidatePackges(jobDivaSession, email);
	}
	@PostMapping(value = "/email-alert", produces = "application/json")
	public Boolean emialAlert( @RequestBody(required = true) ChatbotEmailAlert email)
		throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.emailAlert(jobDivaSession,email);
	}
	@GetMapping(value = "/recruiter-data", produces = "application/json")
	public ChatbotUserData getRecruiterIdData(@ApiParam(value = "recruiterid", required = true)
	@RequestParam(required = true) long recruiterid)  throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getRecruiterData(jobDivaSession,recruiterid);
	}

	@GetMapping(value = "/recruiter-list", produces = "application/json")
	public List<ChatbotRecruiterInfo> getRecruiterList() throws Exception {
		JobDivaSession jobDivaSession = getJobDivaSession();
		return chatbotDataService.getRecruiterList(jobDivaSession);
	}
}

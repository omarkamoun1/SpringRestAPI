package com.jobdiva.api.model.chatbot;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotSocialQuestion implements java.io.Serializable {
	//
	
	@JsonProperty(value = "question", index = 0)
	private String				question;
	//
	@JsonProperty(value = "variations", index = 1)
	private ArrayList<String>	variations;
	//
	@JsonProperty(value = "referenceID", index = 2)
	private Integer				referenceID;
	//
	@JsonProperty(value = "answer", index = 3)
	private String				answer;
	//
	private Integer				tmpId;
	//
	
	public Integer getTmpId() {
		return tmpId;
	}
	
	public void setTmpId(Integer tmpId) {
		this.tmpId = tmpId;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public ArrayList<String> getVariations() {
		return variations;
	}
	
	public void setVariations(ArrayList<String> variations) {
		this.variations = variations;
	}
	
	public void addVariation(String variation) {
		if (this.variations == null)
			variations = new ArrayList<String>();
		this.variations.add(variation);
	}
	
	public Integer getReferenceID() {
		return referenceID;
	}
	
	public void setReferenceID(Integer referenceID) {
		this.referenceID = referenceID;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}

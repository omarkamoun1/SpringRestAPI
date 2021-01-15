package com.jobdiva.api.model.chatbot;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotQuestion implements java.io.Serializable {
	
	@JsonProperty(value = "question", index = 0)
	private String				question;
	//
	@JsonProperty(value = "variations", index = 1)
	private ArrayList<String>	variations;
	//
	@JsonProperty(value = "referenceID", index = 2)
	private Integer				referenceID;
	//
	@JsonProperty(value = "keywords", index = 3)
	private String				keywords;
	//
	@JsonProperty(value = "showAsSuggestion", index = 4)
	private Integer				showAsSuggestion;
	//
	@JsonProperty(value = "questionWithTag", index = 5)
	private String				questionWithTag;
	//
	@JsonProperty(value = "substituteTag", index = 6)
	private boolean				substituteTag;
	//
	@JsonProperty(value = "substituteTagValues", index = 7)
	private String				substituteTagValues;
	//
	@JsonProperty(value = "confidenceLevel", index = 8)
	private int				confidenceLevel;
	//
	private Integer				tmpId;
	
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
	
	public void addVariation(String variation) {
		if (variations == null)
			variations = new ArrayList<String>();
		variations.add(variation);
	}
	
	public ArrayList<String> getVariations() {
		return variations;
	}
	
	public void setVariations(ArrayList<String> variations) {
		this.variations = variations;
	}
	
	public Integer getReferenceID() {
		return referenceID;
	}
	
	public void setReferenceID(Integer referenceID) {
		this.referenceID = referenceID;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public Integer getShowAsSuggestion() {
		return showAsSuggestion;
	}
	
	public void setShowAsSuggestion(Integer showAsSuggestion) {
		this.showAsSuggestion = showAsSuggestion;
	}
	
	public String getQuestionWithTag() {
		return questionWithTag;
	}
	
	public void setQuestionWithTag(String questionWithTag) {
		this.questionWithTag = questionWithTag;
	}
	
	public boolean isSubstituteTag() {
		return substituteTag;
	}
	
	public void setSubstituteTag(boolean substituteTag) {
		this.substituteTag = substituteTag;
	}
	
	public String getSubstituteTagValues() {
		return substituteTagValues;
	}
	
	public void setSubstituteTagValues(String substituteTagValues) {
		this.substituteTagValues = substituteTagValues;
	}

	public int getConfidenceLevel() {
		return confidenceLevel;
	}
	
	public void setConfidenceLevel(int confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}
}

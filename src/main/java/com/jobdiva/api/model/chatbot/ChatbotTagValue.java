package com.jobdiva.api.model.chatbot;

import java.io.Serializable;
import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

public class ChatbotTagValue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tag;
	private String value;
	private String tagType;
	private Boolean forCondition;
	private Boolean forAnswer;
	
	
	public ChatbotTagValue tagType(String tagType) {
		this.tagType = tagType;
		return this;
	}
	@ApiModelProperty(value = "", required = true)
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	
	public ChatbotTagValue forCondition(Boolean forCondition) {
		this.forCondition = forCondition;
		return this;
	}
	@ApiModelProperty(value = "", required = true)	
	public Boolean getForCondition() {
		return forCondition;
	}
	public void setForCondition(Boolean forCondition) {
		this.forCondition = forCondition;
	}
	
	public ChatbotTagValue forAnswer(Boolean forAnswer) {
		this.forAnswer = forAnswer;
		return this;
	}
	@ApiModelProperty(value = "", required = true)	
	public Boolean getForAnswer() {
		return forAnswer;
	}
	public void setForAnswer(Boolean forAnswer) {
		this.forAnswer = forAnswer;
	}
	
	
	public ChatbotTagValue tag(String tag) {
		this.tag = tag;
		return this;
	}
	@ApiModelProperty(value = "", required = true)		
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public ChatbotTagValue value(String value) {
		this.value = value;
		return this;
	}
	@ApiModelProperty(value = "", required = true)		
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		ChatbotTagValue other = (ChatbotTagValue) o;
		return Objects.equals(this.tag, other.tag) &&
				Objects.equals(this.value, other.value) &&
				Objects.equals(this.tagType, other.tagType) &&
				Objects.equals(this.forAnswer, other.forAnswer) &&
				Objects.equals(this.forCondition, other.forCondition);
	}
	
	
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("class ChatbotTagValue {\n");
		
		builder.append("tag:");
		builder.append(toIndentedString(tag));
		builder.append("\n");
		
		builder.append("value:");
		builder.append(toIndentedString(value));
		builder.append("\n");
		
		builder.append("tagType:");
		builder.append(toIndentedString(tagType));
		builder.append("\n");
		
		builder.append("forAnswer:");
		builder.append(toIndentedString(forAnswer));
		builder.append("\n");
		
		builder.append("forCondition:");
		builder.append(toIndentedString(forCondition));
		builder.append("\n");

		builder.append("}");
		return builder.toString();
	}

	
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	}	
}

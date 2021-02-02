package com.jobdiva.api.model.chatbot;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class ChatbotTag implements Serializable {
	
	
	
	

	private static final long serialVersionUID = 1L;
	@JsonProperty(value = "tagTitle", index = 0)
	private String tagTitle;
	//
	@JsonProperty(value = "tag", index = 1)
	private String tag;
	//
	@JsonProperty(value = "tagid", index = 2)
	private Integer tagid;
	//
	@JsonProperty(value = "isClientDefined", index = 3)
	//
	private boolean isClientDefined;
	@JsonProperty(value = "tagType", index = 4)
	private String tagType;
	
	public ChatbotTag tagTitle(String tagTitle) {
		this.tagTitle = tagTitle;
		return this;
	}
	public String getTagTitle() {
		return tagTitle;
	}
	public void setTagTitle(String tagTitle) {
		this.tagTitle = tagTitle;
	}
	
	public ChatbotTag tag(String tag) {
		this.tag = tag;
		return this;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public ChatbotTag tagid(Integer tagid) {
		this.tagid = tagid;
		return this;
	}
	public Integer getTagid() {
		return tagid;
	}
	public void setTagid(Integer tagid) {
		this.tagid = tagid;
	}
	
	public ChatbotTag isClientDefined(boolean isClientDefined) {
		this.isClientDefined = isClientDefined;
		return this;
	}	
	public boolean isClientDefined() {
		return isClientDefined;
	}
	public void setClientDefined(boolean isClientDefined) {
		this.isClientDefined = isClientDefined;
	}
	
	public ChatbotTag tagType(String tagType) {
		this.tagType = tagType;
		return this;
	}
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		ChatbotTag other = (ChatbotTag) o;
		return Objects.equals(this.tagTitle, other.tagTitle) &&
				Objects.equals(this.tag, other.tag) &&
				Objects.equals(this.tagid, other.tagid) &&
				Objects.equals(this.isClientDefined, other.isClientDefined) &&
				Objects.equals(this.tagType, other.tagType);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("class ChatbotTag {\n");
		
		builder.append("tagTitle: ");
		builder.append(toIndentedString(tagTitle));
		builder.append("\n");
		
		builder.append("tag: ");
		builder.append(toIndentedString(tag));
		builder.append("\n");
		
		builder.append("tagid: ");
		builder.append(toIndentedString(tagid));
		builder.append("\n");
		
		builder.append("isClientDefined: ");
		builder.append(toIndentedString(isClientDefined));
		builder.append("\n");
		
		builder.append("tagType:");
		builder.append(toIndentedString(tagType));
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

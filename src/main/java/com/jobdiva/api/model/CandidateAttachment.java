package com.jobdiva.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class CandidateAttachment implements java.io.Serializable {
	
	@JsonProperty(required = true, index = 0)
	private Long	candidateid;
	//
	@JsonProperty(required = true, index = 1)
	private String	name;
	//
	@JsonProperty(required = true, index = 2)
	private String	filename;
	//
	@JsonProperty(required = true, index = 3)
	private byte[]	filecontent;
	//
	@JsonProperty(required = true, index = 4)
	private Integer	attachmenttype;
	//
	@JsonProperty(required = false, index = 5)
	private String	description;
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public byte[] getFilecontent() {
		return filecontent;
	}
	
	public void setFilecontent(byte[] filecontent) {
		this.filecontent = filecontent;
	}
	
	public Integer getAttachmenttype() {
		return attachmenttype;
	}
	
	public void setAttachmenttype(Integer attachmenttype) {
		this.attachmenttype = attachmenttype;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}

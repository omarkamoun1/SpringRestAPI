package com.jobdiva.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class UploadResume implements java.io.Serializable {
	
	@JsonProperty(value = "filename", required = true, index = 0)
	private String	filename;
	//
	@JsonProperty(value = "filecontent", required = false, index = 1)
	private byte[]	filecontent;
	//
	@JsonProperty(value = "textfile", required = true, index = 2)
	private String	textfile;
	//
	@JsonProperty(value = "candidateid", required = false, index = 3)
	private Long	candidateid;
	//
	@JsonProperty(value = "resumesource", required = true, index = 4)
	private Integer	resumesource;
	//
	@JsonProperty(value = "recruiterid", required = false, index = 5)
	private Long	recruiterid;
	//
	@JsonProperty(value = "resumeDate", required = false, index = 6)
	private String	resumeDate;
	//
	//
	
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
	
	public String getTextfile() {
		return textfile;
	}
	
	public void setTextfile(String textfile) {
		this.textfile = textfile;
	}
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public Integer getResumesource() {
		return resumesource;
	}
	
	public void setResumesource(Integer resumesource) {
		this.resumesource = resumesource;
	}
	
	public Long getRecruiterid() {
		return recruiterid;
	}
	
	public void setRecruiterid(Long recruiterid) {
		this.recruiterid = recruiterid;
	}
	
	public String getResumeDate() {
		return resumeDate;
	}
	
	public void setResumeDate(String resumeDate) {
		this.resumeDate = resumeDate;
	}
}

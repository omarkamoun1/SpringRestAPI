package com.jobdiva.api.model;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;

@ApiModel()
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "recruiterName", "date", "recruiterId", "content" })
@SuppressWarnings("serial")
public class Note implements java.io.Serializable {
	

	private Long					id;
	
	private String					recruiterName;

	private Date				    date;
	
	private Long					recruiterId;
	
	private String					content;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecruiterName() {
		return recruiterName;
	}

	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getRecruiterId() {
		return recruiterId;
	}

	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
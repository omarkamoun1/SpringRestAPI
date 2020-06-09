package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class QualificationType implements java.io.Serializable {
	
	private Integer	qualificationTypeId;
	private String	qualificationValue;
	
	public Integer getQualificationTypeId() {
		return qualificationTypeId;
	}
	
	public void setQualificationTypeId(Integer qualificationTypeId) {
		this.qualificationTypeId = qualificationTypeId;
	}
	
	public String getQualificationValue() {
		return qualificationValue;
	}
	
	public void setQualificationValue(String qualificationValue) {
		this.qualificationValue = qualificationValue;
	}
}

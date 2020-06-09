package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class CandidateQual implements java.io.Serializable {
	
	private Integer	catId;
	private String	dcatNames;
	
	public Integer getCatId() {
		return catId;
	}
	
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	
	public String getDcatNames() {
		return dcatNames;
	}
	
	public void setDcatNames(String dcatNames) {
		this.dcatNames = dcatNames;
	}
}

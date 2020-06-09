package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class QualificationOption implements java.io.Serializable {
	
	private Integer	dcatId;
	private Integer	catId;
	private Integer	teamid;
	private String	dcatName;
	private Boolean	isDefault;
	private boolean	closed;
	private Boolean	active;
	
	public Integer getDcatId() {
		return dcatId;
	}
	
	public void setDcatId(Integer dcatId) {
		this.dcatId = dcatId;
	}
	
	public Integer getCatId() {
		return catId;
	}
	
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	
	public Integer getTeamid() {
		return teamid;
	}
	
	public void setTeamid(Integer teamid) {
		this.teamid = teamid;
	}
	
	public String getDcatName() {
		return dcatName;
	}
	
	public void setDcatName(String dcatName) {
		this.dcatName = dcatName;
	}
	
	public Boolean getIsDefault() {
		return isDefault;
	}
	
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
}

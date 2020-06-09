package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class CompanyTypeNameId implements java.io.Serializable {
	
	private Long	id;
	private Long	teamid;
	
	public CompanyTypeNameId() {
	}
	
	public CompanyTypeNameId(Long id, Long teamid) {
		this.id = id;
		this.teamid = teamid;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTeamid() {
		return this.teamid;
	}
	
	public void setTeamid(Long teamid) {
		this.teamid = teamid;
	}
}

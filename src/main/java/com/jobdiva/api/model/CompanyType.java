package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class CompanyType implements java.io.Serializable {
	
	private CompanyTypeId	id;
	private Long			teamid;
	
	public CompanyType() {
	}
	
	public CompanyType(CompanyTypeId id) {
		this.id = id;
	}
	
	public CompanyType(CompanyTypeId id, Long teamid) {
		this.id = id;
		this.teamid = teamid;
	}
	
	public CompanyTypeId getId() {
		return this.id;
	}
	
	public void setId(CompanyTypeId id) {
		this.id = id;
	}
	
	public Long getTeamid() {
		return this.teamid;
	}
	
	public void setTeamid(Long teamid) {
		this.teamid = teamid;
	}
}

package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class CompanyOwner implements java.io.Serializable {
	
	private Long	companyid;
	private Long	recruiterid;
	private Boolean	isprimaryowner;
	private Long	teamid;
	//
	private Boolean	insertMode;
	
	public Long getCompanyid() {
		return companyid;
	}
	
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	
	public Long getRecruiterid() {
		return recruiterid;
	}
	
	public void setRecruiterid(Long recruiterid) {
		this.recruiterid = recruiterid;
	}
	
	public Boolean getIsprimaryowner() {
		return isprimaryowner;
	}
	
	public Boolean isIsprimaryowner() {
		return this.isprimaryowner;
	}
	
	public void setIsprimaryowner(Boolean isprimaryowner) {
		this.isprimaryowner = isprimaryowner;
	}
	
	public Long getTeamid() {
		return this.teamid;
	}
	
	public void setTeamid(Long teamid) {
		this.teamid = teamid;
	}
	
	public Boolean getInsertMode() {
		return insertMode;
	}
	
	public void setInsertMode(Boolean insertMode) {
		this.insertMode = insertMode;
	}
}

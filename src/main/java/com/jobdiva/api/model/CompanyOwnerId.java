package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class CompanyOwnerId implements java.io.Serializable {
	
	private Long	companyid;
	private Long	recruiterid;
	
	public CompanyOwnerId() {
	}
	
	public CompanyOwnerId(Long companyid, Long recruiterid) {
		this.companyid = companyid;
		this.recruiterid = recruiterid;
	}
	
	public Long getCompanyid() {
		return this.companyid;
	}
	
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	
	public Long getRecruiterid() {
		return this.recruiterid;
	}
	
	public void setRecruiterid(Long recruiterid) {
		this.recruiterid = recruiterid;
	}
}

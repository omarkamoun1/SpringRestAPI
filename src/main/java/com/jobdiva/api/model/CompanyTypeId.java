package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class CompanyTypeId implements java.io.Serializable {
	
	private Long	companyid;
	private Long	typeid;
	
	public CompanyTypeId() {
	}
	
	public CompanyTypeId(Long companyid, Long typeid) {
		this.companyid = companyid;
		this.typeid = typeid;
	}
	
	public Long getCompanyid() {
		return this.companyid;
	}
	
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	
	public Long getTypeid() {
		return this.typeid;
	}
	
	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}
}

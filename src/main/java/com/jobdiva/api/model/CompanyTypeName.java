package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class CompanyTypeName implements java.io.Serializable {
	
	private CompanyTypeNameId	id;
	private String				typename;
	private Boolean				deleted;
	
	public CompanyTypeName() {
	}
	
	public CompanyTypeName(CompanyTypeNameId id, String typename, Boolean deleted) {
		this.id = id;
		this.typename = typename;
		this.deleted = deleted;
	}
	
	public CompanyTypeNameId getId() {
		return this.id;
	}
	
	public void setId(CompanyTypeNameId id) {
		this.id = id;
	}
	
	public String getTypename() {
		return this.typename;
	}
	
	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	public Boolean isDeleted() {
		return this.deleted;
	}
	
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
}

package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class ContactType implements java.io.Serializable {
	
	private Long	id;
	private String	name;
	private Integer	deleted;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getDeleted() {
		return deleted;
	}
	
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
}

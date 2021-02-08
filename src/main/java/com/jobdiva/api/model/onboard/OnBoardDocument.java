package com.jobdiva.api.model.onboard;

import java.io.Serializable;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class OnBoardDocument implements Serializable {
	
	private Long	id;
	private Integer	documentType;
	private String	name;
	private Boolean	mandatory;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getMandatory() {
		return this.mandatory;
	}
	
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public Integer getDocumentType() {
		return this.documentType;
	}
	
	public void setDocumentType(Integer documentType) {
		this.documentType = documentType;
	}
}

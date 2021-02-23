package com.jobdiva.api.model.onboard;

import java.io.Serializable;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class OnBoardDocument implements Serializable {
	
	private Long	id;
	private String	documentType;
	private String	name;
	private Boolean	mandatory;
	private Boolean	returnRequired;
	//
	private Boolean	readonly;
	private Boolean	medical;
	private String	sendTo;
	private String	internalDescription;
	private String	portalInstruction;
	
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
	
	public String getDocumentType() {
		return this.documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public Boolean getReturnRequired() {
		return returnRequired;
	}
	
	public void setReturnRequired(Boolean returnRequired) {
		this.returnRequired = returnRequired;
	}
	
	public Boolean getReadonly() {
		return readonly;
	}
	
	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}
	
	public String getSendTo() {
		return sendTo;
	}
	
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	
	public String getInternalDescription() {
		return internalDescription;
	}
	
	public void setInternalDescription(String internalDescription) {
		this.internalDescription = internalDescription;
	}
	
	public String getPortalInstruction() {
		return portalInstruction;
	}
	
	public void setPortalInstruction(String portalInstruction) {
		this.portalInstruction = portalInstruction;
	}
	
	public Boolean getMedical() {
		return medical;
	}
	
	public void setMedical(Boolean medical) {
		this.medical = medical;
	}
}

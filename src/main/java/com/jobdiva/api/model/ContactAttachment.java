package com.jobdiva.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class ContactAttachment implements java.io.Serializable {
	
	@JsonProperty(required = true, index = 0)
	private Long	contactId;
	//
	@JsonProperty(required = false, index = 1)
	private String	documentname;
	//
	@JsonProperty(required = false, index = 2)
	private String	filename;
	//
	@JsonProperty(required = false, index = 3)
	private byte[]	filecontent;
	//
	@JsonProperty(required = false, index = 4)
	private String	urllink;
	//
	@JsonProperty(required = false, index = 5)
	private String	designedform;
	//
	@JsonProperty(required = false, index = 6)
	private Integer	attachmenttype;
	//
	@JsonProperty(required = false, index = 7)
	private String	internaldescription;
	//
	@JsonProperty(required = false, index = 8)
	private String	expirationdate;
	//
	@JsonProperty(required = false, index = 9)
	private Boolean	isonboardingdoc;
	//
	@JsonProperty(required = false, index = 10)
	private Boolean	ismandatory;
	//
	@JsonProperty(required = false, index = 11)
	private Boolean	requirereturn;
	//
	@JsonProperty(required = false, index = 12)
	private Boolean	isreadonly;
	//
	@JsonProperty(required = false, index = 13)
	private Integer	sendto;
	//
	@JsonProperty(required = false, index = 14)
	private Boolean	ismedicaldoc;
	//
	@JsonProperty(required = false, index = 15)
	private String	portalinstruction;
	
	public Long getContactId() {
		return contactId;
	}
	
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	
	public String getDocumentname() {
		return documentname;
	}
	
	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public byte[] getFilecontent() {
		return filecontent;
	}
	
	public void setFilecontent(byte[] filecontent) {
		this.filecontent = filecontent;
	}
	
	public String getUrllink() {
		return urllink;
	}
	
	public void setUrllink(String urllink) {
		this.urllink = urllink;
	}
	
	public String getDesignedform() {
		return designedform;
	}
	
	public void setDesignedform(String designedform) {
		this.designedform = designedform;
	}
	
	public Integer getAttachmenttype() {
		return attachmenttype;
	}
	
	public void setAttachmenttype(Integer attachmenttype) {
		this.attachmenttype = attachmenttype;
	}
	
	public String getInternaldescription() {
		return internaldescription;
	}
	
	public void setInternaldescription(String internaldescription) {
		this.internaldescription = internaldescription;
	}
	
	public String getExpirationdate() {
		return expirationdate;
	}
	
	public void setExpirationdate(String expirationdate) {
		this.expirationdate = expirationdate;
	}
	
	public Boolean getIsonboardingdoc() {
		return isonboardingdoc;
	}
	
	public void setIsonboardingdoc(Boolean isonboardingdoc) {
		this.isonboardingdoc = isonboardingdoc;
	}
	
	public Boolean getIsmandatory() {
		return ismandatory;
	}
	
	public void setIsmandatory(Boolean ismandatory) {
		this.ismandatory = ismandatory;
	}
	
	public Boolean getRequirereturn() {
		return requirereturn;
	}
	
	public void setRequirereturn(Boolean requirereturn) {
		this.requirereturn = requirereturn;
	}
	
	public Boolean getIsreadonly() {
		return isreadonly;
	}
	
	public void setIsreadonly(Boolean isreadonly) {
		this.isreadonly = isreadonly;
	}
	
	public Integer getSendto() {
		return sendto;
	}
	
	public void setSendto(Integer sendto) {
		this.sendto = sendto;
	}
	
	public Boolean getIsmedicaldoc() {
		return ismedicaldoc;
	}
	
	public void setIsmedicaldoc(Boolean ismedicaldoc) {
		this.ismedicaldoc = ismedicaldoc;
	}
	
	public String getPortalinstruction() {
		return portalinstruction;
	}
	
	public void setPortalinstruction(String portalinstruction) {
		this.portalinstruction = portalinstruction;
	}
}

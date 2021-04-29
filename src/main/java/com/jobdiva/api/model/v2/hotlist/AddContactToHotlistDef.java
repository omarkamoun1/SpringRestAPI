package com.jobdiva.api.model.v2.hotlist;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 28, 2021
 */
@SuppressWarnings("serial")
public class AddContactToHotlistDef implements Serializable {
	
	@JsonProperty(value = "hotListid", required = true)
	private Long	hotListid;
	//
	@JsonProperty(value = "contactId", required = true)
	private Long	contactId;
	
	public Long getHotListid() {
		return hotListid;
	}
	
	public void setHotListid(Long hotListid) {
		this.hotListid = hotListid;
	}
	
	public Long getContactId() {
		return contactId;
	}
	
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
}

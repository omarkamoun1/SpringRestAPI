package com.jobdiva.api.model.v2.company;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class AddCompanyNoteDef implements Serializable {
	
	@JsonProperty(value = "userid", required = true) //
	private Long	userid;
	//
	//
	@JsonProperty(value = "companyid", required = true) //
	private Long	companyid;
	//
	//
	@JsonProperty(value = "note", required = true) //
	private String	note;
	
	public Long getUserid() {
		return userid;
	}
	
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	public Long getCompanyid() {
		return companyid;
	}
	
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}

package com.jobdiva.api.model.v2.start;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.Userfield;

/**
 * @author Joseph Chidiac
 *
 *         Apr 27, 2021
 */
@SuppressWarnings("serial")
public class UpdateStartDef implements Serializable {
	
	//
	@JsonProperty(value = "startid", required = false)
	private Long		startid;
	//
	@JsonProperty(value = "overwrite", required = false)
	private Boolean		overwrite;
	//
	@JsonProperty(value = "startDate", required = false)
	private Date		startDate;
	//
	@JsonProperty(value = "endDate", required = false)
	private Date		endDate;
	//
	@JsonProperty(value = "positiontype", required = false)
	private String		positiontype;
	//
	@JsonProperty(value = "billrate", required = false)
	private Double		billrate;
	//
	@JsonProperty(value = "billratecurrency", required = false)
	private String		billratecurrency;
	//
	@JsonProperty(value = "billrateunit", required = false)
	private String		billrateunit;
	//
	@JsonProperty(value = "payrate", required = false)
	private Double		payrate;
	//
	@JsonProperty(value = "payratecurrency", required = false)
	private String		payratecurrency;
	//
	@JsonProperty(value = "payrateunit", required = false)
	private String		payrateunit;
	//
	@JsonProperty(value = "Userfields", required = false) //
	private Userfield[]	userfields;
	
	//
	public Long getStartid() {
		return startid;
	}
	
	public void setStartid(Long startid) {
		this.startid = startid;
	}
	
	public Boolean getOverwrite() {
		return overwrite;
	}
	
	public void setOverwrite(Boolean overwrite) {
		this.overwrite = overwrite;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getPositiontype() {
		return positiontype;
	}
	
	public void setPositiontype(String positiontype) {
		this.positiontype = positiontype;
	}
	
	public Double getBillrate() {
		return billrate;
	}
	
	public void setBillrate(Double billrate) {
		this.billrate = billrate;
	}
	
	public String getBillratecurrency() {
		return billratecurrency;
	}
	
	public void setBillratecurrency(String billratecurrency) {
		this.billratecurrency = billratecurrency;
	}
	
	public String getBillrateunit() {
		return billrateunit;
	}
	
	public void setBillrateunit(String billrateunit) {
		this.billrateunit = billrateunit;
	}
	
	public Double getPayrate() {
		return payrate;
	}
	
	public void setPayrate(Double payrate) {
		this.payrate = payrate;
	}
	
	public String getPayratecurrency() {
		return payratecurrency;
	}
	
	public void setPayratecurrency(String payratecurrency) {
		this.payratecurrency = payratecurrency;
	}
	
	public String getPayrateunit() {
		return payrateunit;
	}
	
	public void setPayrateunit(String payrateunit) {
		this.payrateunit = payrateunit;
	}
	
	public Userfield[] getUserfields() {
		return userfields;
	}
	
	public void setUserfields(Userfield[] userfields) {
		this.userfields = userfields;
	}
}

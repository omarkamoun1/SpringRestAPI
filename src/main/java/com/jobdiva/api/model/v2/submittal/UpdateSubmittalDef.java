package com.jobdiva.api.model.v2.submittal;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 28, 2021
 */
@SuppressWarnings("serial")
public class UpdateSubmittalDef implements Serializable {
	
	//
	@JsonProperty(value = "submittalid", required = true)
	private Long	submittalid;
	//
	@JsonProperty(value = "status", required = false)
	private String	status;
	//
	@JsonProperty(value = "internalnotes", required = false)
	private String	internalnotes;
	//
	@JsonProperty(value = "salary", required = false)
	private Double	salary;
	//
	@JsonProperty(value = "feetype", required = false)
	private Integer	feetype;
	//
	@JsonProperty(value = "fee", required = false)
	private Double	fee;
	//
	@JsonProperty(value = "quotedbillrate", required = false)
	private Double	quotedbillrate;
	//
	@JsonProperty(value = "agreedbillrate", required = false)
	private Double	agreedbillrate;
	//
	@JsonProperty(value = "payrate", required = false)
	private Double	payrate;
	//
	@JsonProperty(value = "payratecurrency", required = false)
	private String	payratecurrency;
	//
	@JsonProperty(value = "payrateunit", required = false)
	private String	payrateunit;
	//
	@JsonProperty(value = "corp2corp", required = false)
	private Boolean	corp2corp;
	//
	@JsonProperty(value = "agreedon", required = false)
	private Date	agreedon;
	//
	@JsonProperty(value = "interviewdate", required = false)
	private Date	interviewdate;
	//
	@JsonProperty(value = "internalrejectdate", required = false)
	private Date	internalrejectdate;
	//
	@JsonProperty(value = "externalrejectdate", required = false)
	private Date	externalrejectdate;
	
	public Long getSubmittalid() {
		return submittalid;
	}
	
	public void setSubmittalid(Long submittalid) {
		this.submittalid = submittalid;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getInternalnotes() {
		return internalnotes;
	}
	
	public void setInternalnotes(String internalnotes) {
		this.internalnotes = internalnotes;
	}
	
	public Double getSalary() {
		return salary;
	}
	
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	public Integer getFeetype() {
		return feetype;
	}
	
	public void setFeetype(Integer feetype) {
		this.feetype = feetype;
	}
	
	public Double getFee() {
		return fee;
	}
	
	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	public Double getQuotedbillrate() {
		return quotedbillrate;
	}
	
	public void setQuotedbillrate(Double quotedbillrate) {
		this.quotedbillrate = quotedbillrate;
	}
	
	public Double getAgreedbillrate() {
		return agreedbillrate;
	}
	
	public void setAgreedbillrate(Double agreedbillrate) {
		this.agreedbillrate = agreedbillrate;
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
	
	public Boolean getCorp2corp() {
		return corp2corp;
	}
	
	public void setCorp2corp(Boolean corp2corp) {
		this.corp2corp = corp2corp;
	}
	
	public Date getAgreedon() {
		return agreedon;
	}
	
	public void setAgreedon(Date agreedon) {
		this.agreedon = agreedon;
	}
	
	public Date getInterviewdate() {
		return interviewdate;
	}
	
	public void setInterviewdate(Date interviewdate) {
		this.interviewdate = interviewdate;
	}
	
	public Date getInternalrejectdate() {
		return internalrejectdate;
	}
	
	public void setInternalrejectdate(Date internalrejectdate) {
		this.internalrejectdate = internalrejectdate;
	}
	
	public Date getExternalrejectdate() {
		return externalrejectdate;
	}
	
	public void setExternalrejectdate(Date externalrejectdate) {
		this.externalrejectdate = externalrejectdate;
	}
}

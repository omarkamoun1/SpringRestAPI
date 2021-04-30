package com.jobdiva.api.model.v2.submittal;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.Userfield;

/**
 * @author Joseph Chidiac
 *
 *         Apr 28, 2021
 */
@SuppressWarnings("serial")
public class CreateSubmittalDef implements Serializable {
	
	//
	@JsonProperty(value = "jobid", required = true)
	private Long		jobid;
	//
	@JsonProperty(value = "candidateid", required = true)
	private Long		candidateid;
	//
	@JsonProperty(value = "status", required = false)
	private String		status;
	//
	@JsonProperty(value = "submit2id", required = false)
	private Long		submit2id;
	//
	@JsonProperty(value = "submittaldate", required = true)
	private Date		submittaldate;
	//
	@JsonProperty(value = "positiontype", required = false)
	private String		positiontype;
	//
	@JsonProperty(value = "recruitedbyid", required = false)
	private Long		recruitedbyid;
	//
	@JsonProperty(value = "primarysalesid", required = false)
	private Long		primarysalesid;
	//
	@JsonProperty(value = "internalnotes", required = false)
	private String		internalnotes;
	//
	@JsonProperty(value = "salary", required = false)
	private Double		salary;
	//
	@JsonProperty(value = "feetype", required = false)
	private Integer		feetype;
	//
	@JsonProperty(value = "fee", required = false)
	private Double		fee;
	//
	@JsonProperty(value = "quotedbillrate", required = false)
	private Double		quotedbillrate;
	//
	@JsonProperty(value = "agreedbillrate", required = false)
	private Double		agreedbillrate;
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
	@JsonProperty(value = "corp2corp", required = false)
	private Boolean		corp2corp;
	//
	@JsonProperty(value = "agreedon", required = false)
	private Date		agreedon;
	//
	@JsonProperty(value = "userfields", required = false)
	private Userfield[]	userfields;
	//
	@JsonProperty(value = "filename", required = false)
	private String		filename;
	//
	@JsonProperty(value = "filecontent", required = false)
	private Byte[]		filecontent;
	//
	@JsonProperty(value = "textfile", required = false)
	private String		textfile;
	
	public Long getJobid() {
		return jobid;
	}
	
	public void setJobid(Long jobid) {
		this.jobid = jobid;
	}
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getSubmit2id() {
		return submit2id;
	}
	
	public void setSubmit2id(Long submit2id) {
		this.submit2id = submit2id;
	}
	
	public Date getSubmittaldate() {
		return submittaldate;
	}
	
	public void setSubmittaldate(Date submittaldate) {
		this.submittaldate = submittaldate;
	}
	
	public String getPositiontype() {
		return positiontype;
	}
	
	public void setPositiontype(String positiontype) {
		this.positiontype = positiontype;
	}
	
	public Long getRecruitedbyid() {
		return recruitedbyid;
	}
	
	public void setRecruitedbyid(Long recruitedbyid) {
		this.recruitedbyid = recruitedbyid;
	}
	
	public Long getPrimarysalesid() {
		return primarysalesid;
	}
	
	public void setPrimarysalesid(Long primarysalesid) {
		this.primarysalesid = primarysalesid;
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
	
	public Userfield[] getUserfields() {
		return userfields;
	}
	
	public void setUserfields(Userfield[] userfields) {
		this.userfields = userfields;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public Byte[] getFilecontent() {
		return filecontent;
	}
	
	public void setFilecontent(Byte[] filecontent) {
		this.filecontent = filecontent;
	}
	
	public String getTextfile() {
		return textfile;
	}
	
	public void setTextfile(String textfile) {
		this.textfile = textfile;
	}
}

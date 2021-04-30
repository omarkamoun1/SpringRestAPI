package com.jobdiva.api.model.v2.company;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.CompanyAddress;
import com.jobdiva.api.model.FinancialsType;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.Userfield;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class UpdateCompanyDef implements Serializable {
	
	@JsonProperty(value = "companyid", required = true) //
	private Long				companyid;
	//
	@JsonProperty(value = "name", required = false) //
	private String				name;
	//
	@JsonProperty(value = "parentcompanyid", required = false) //
	private Long				parentcompanyid;
	//
	@JsonProperty(value = "companytypes", required = false) //
	private String[]			companytypes;
	//
	@JsonProperty(value = "addresses", required = false) //
	private CompanyAddress[]	addresses;
	//
	@JsonProperty(value = "subguidelines", required = false) //
	private String				subguidelines;
	//
	@JsonProperty(value = "maxsubmittals", required = false) //
	private Integer				maxsubmittals;
	//
	@JsonProperty(value = "references", required = false) //
	private Boolean				references;
	//
	@JsonProperty(value = "drugtest", required = false) //
	private Boolean				drugtest;
	//
	@JsonProperty(value = "backgroundcheck", required = false) //
	private Boolean				backgroundcheck;
	//
	@JsonProperty(value = "securityclearance", required = false) //
	private Boolean				securityclearance;
	//
	@JsonProperty(value = "Userfields", required = false) //
	private Userfield[]			Userfields;
	//
	@JsonProperty(value = "discount", required = false) //
	private Double				discount;
	//
	@JsonProperty(value = "discountper", required = false) //
	private String				discountper;
	//
	@JsonProperty(value = "percentagediscount", required = false) //
	private Double				percentagediscount;
	//
	@JsonProperty(required = false) //
	private FinancialsType		financials;
	//
	@JsonProperty(value = "owners", required = false) //
	private Owner[]				owners;
	//
	@JsonProperty(value = "salespipeline", required = false) //
	private String				salespipeline;
	
	public Long getCompanyid() {
		return companyid;
	}
	
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getParentcompanyid() {
		return parentcompanyid;
	}
	
	public void setParentcompanyid(Long parentcompanyid) {
		this.parentcompanyid = parentcompanyid;
	}
	
	public String[] getCompanytypes() {
		return companytypes;
	}
	
	public void setCompanytypes(String[] companytypes) {
		this.companytypes = companytypes;
	}
	
	public CompanyAddress[] getAddresses() {
		return addresses;
	}
	
	public void setAddresses(CompanyAddress[] addresses) {
		this.addresses = addresses;
	}
	
	public String getSubguidelines() {
		return subguidelines;
	}
	
	public void setSubguidelines(String subguidelines) {
		this.subguidelines = subguidelines;
	}
	
	public Integer getMaxsubmittals() {
		return maxsubmittals;
	}
	
	public void setMaxsubmittals(Integer maxsubmittals) {
		this.maxsubmittals = maxsubmittals;
	}
	
	public Boolean getReferences() {
		return references;
	}
	
	public void setReferences(Boolean references) {
		this.references = references;
	}
	
	public Boolean getDrugtest() {
		return drugtest;
	}
	
	public void setDrugtest(Boolean drugtest) {
		this.drugtest = drugtest;
	}
	
	public Boolean getBackgroundcheck() {
		return backgroundcheck;
	}
	
	public void setBackgroundcheck(Boolean backgroundcheck) {
		this.backgroundcheck = backgroundcheck;
	}
	
	public Boolean getSecurityclearance() {
		return securityclearance;
	}
	
	public void setSecurityclearance(Boolean securityclearance) {
		this.securityclearance = securityclearance;
	}
	
	public Userfield[] getUserfields() {
		return Userfields;
	}
	
	public void setUserfields(Userfield[] userfields) {
		Userfields = userfields;
	}
	
	public Double getDiscount() {
		return discount;
	}
	
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	public String getDiscountper() {
		return discountper;
	}
	
	public void setDiscountper(String discountper) {
		this.discountper = discountper;
	}
	
	public Double getPercentagediscount() {
		return percentagediscount;
	}
	
	public void setPercentagediscount(Double percentagediscount) {
		this.percentagediscount = percentagediscount;
	}
	
	public FinancialsType getFinancials() {
		return financials;
	}
	
	public void setFinancials(FinancialsType financials) {
		this.financials = financials;
	}
	
	public Owner[] getOwners() {
		return owners;
	}
	
	public void setOwners(Owner[] owners) {
		this.owners = owners;
	}
	
	public String getSalespipeline() {
		return salespipeline;
	}
	
	public void setSalespipeline(String salespipeline) {
		this.salespipeline = salespipeline;
	}
}

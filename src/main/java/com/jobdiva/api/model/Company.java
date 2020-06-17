package com.jobdiva.api.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class Company implements java.io.Serializable {
	
	//
	@JsonProperty(value = "id", index = 0)
	private Long					id;
	//
	@JsonIgnore
	private Long					teamid;
	//
	@JsonProperty(value = "name", index = 1)
	private String					name;
	//
	@JsonProperty(value = "address1", index = 2)
	private String					address1;
	//
	@JsonProperty(value = "address2", index = 3)
	private String					address2;
	//
	@JsonProperty(value = "city", index = 4)
	private String					city;
	//
	@JsonProperty(value = "state", index = 5)
	private String					state;
	//
	@JsonProperty(value = "zipcode", index = 6)
	private String					zipcode;
	//
	@JsonProperty(value = "country", index = 7)
	private String					countryid;
	//
	@JsonProperty(value = "phone", index = 8)
	private String					phone;
	//
	@JsonProperty(value = "fax", index = 9)
	private String					fax;
	//
	@JsonProperty(value = "email", index = 10)
	private String					email;
	//
	@JsonProperty(value = "url", index = 11)
	private String					url;
	//
	@JsonProperty(value = "parent company id", index = 12)
	private Long					parentCompanyid;
	//
	@JsonProperty(value = "parent company name", index = 13)
	private String					parentCompanyName;
	//
	@JsonProperty(value = "company types", index = 14)
	private String					companyType;
	//
	@JsonProperty(value = "company owners", index = 15)
	private String					owners;
	//
	@JsonProperty(value = "primary contacts", index = 16)
	private String					primaryContacts;
	//
	@JsonProperty(value = "sales pipeline", index = 17)
	private String					nameIndex;
	//
	@JsonIgnore
	private Short					pipelineId;
	//
	@JsonIgnore
	private transient Clob			submittalInstr;
	//
	@JsonIgnore
	private BigDecimal				discount;
	//
	@JsonIgnore
	private BigDecimal				discountpct;
	//
	@JsonIgnore
	private Byte					discountType;
	//
	@JsonIgnore
	private Date					dateentered;
	//
	@JsonIgnore
	private transient Blob			logo;
	//
	@JsonIgnore
	private Boolean					refcheck;
	//
	@JsonIgnore
	private Boolean					drugtest;
	//
	@JsonIgnore
	private Boolean					backcheck;
	//
	@JsonIgnore
	private Short					maxsubmitals;
	//
	@JsonIgnore
	private Boolean					secclearance;
	//
	@JsonIgnore
	private Boolean					supplier;
	//
	@JsonIgnore
	private Long					recruiterid;
	//
	@JsonIgnore
	private Boolean					invoiceContactPreference;
	//
	@JsonIgnore
	private Boolean					pipelineUrlExpired;
	//
	@JsonIgnore
	private Long					paymentterms;
	//
	//
	@JsonIgnore
	private Long					compPortalid;
	//
	@JsonIgnore
	private String					portalApplyemail;
	//
	@JsonIgnore
	private String					compPortalname;
	//
	@JsonIgnore
	private String					parentpath;
	//
	@JsonIgnore
	private List<CompanyAddress>	companyAddresses	= new ArrayList<CompanyAddress>();
	//
	@JsonIgnore
	private List<CompanyUDF>		companyUDFs			= new ArrayList<CompanyUDF>();
	//
	@JsonIgnore
	private List<FinancialsType>	financialsTypes;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTeamid() {
		return teamid;
	}
	
	public void setTeamid(Long teamid) {
		this.teamid = teamid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress1() {
		return address1;
	}
	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress2() {
		return address2;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getNameIndex() {
		return nameIndex;
	}
	
	public void setNameIndex(String nameIndex) {
		this.nameIndex = nameIndex;
	}
	
	public Short getPipelineId() {
		return pipelineId;
	}
	
	public void setPipelineId(Short pipelineId) {
		this.pipelineId = pipelineId;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Clob getSubmittalInstr() {
		return submittalInstr;
	}
	
	public void setSubmittalInstr(Clob submittalInstr) {
		this.submittalInstr = submittalInstr;
	}
	
	public BigDecimal getDiscount() {
		return discount;
	}
	
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	
	public BigDecimal getDiscountpct() {
		return discountpct;
	}
	
	public void setDiscountpct(BigDecimal discountpct) {
		this.discountpct = discountpct;
	}
	
	public Byte getDiscountType() {
		return discountType;
	}
	
	public void setDiscountType(Byte discountType) {
		this.discountType = discountType;
	}
	
	public Date getDateentered() {
		return dateentered;
	}
	
	public void setDateentered(Date dateentered) {
		this.dateentered = dateentered;
	}
	
	public Long getParentCompanyid() {
		return parentCompanyid;
	}
	
	public void setParentCompanyid(Long parentCompanyid) {
		this.parentCompanyid = parentCompanyid;
	}
	
	public String getParentCompanyName() {
		return parentCompanyName;
	}
	
	public void setParentCompanyName(String parentCompanyName) {
		this.parentCompanyName = parentCompanyName;
	}
	
	public Blob getLogo() {
		return logo;
	}
	
	public void setLogo(Blob logo) {
		this.logo = logo;
	}
	
	public Boolean getRefcheck() {
		return refcheck;
	}
	
	public void setRefcheck(Boolean refcheck) {
		this.refcheck = refcheck;
	}
	
	public Boolean getDrugtest() {
		return drugtest;
	}
	
	public void setDrugtest(Boolean drugtest) {
		this.drugtest = drugtest;
	}
	
	public Boolean getBackcheck() {
		return backcheck;
	}
	
	public void setBackcheck(Boolean backcheck) {
		this.backcheck = backcheck;
	}
	
	public Short getMaxsubmitals() {
		return maxsubmitals;
	}
	
	public void setMaxsubmitals(Short maxsubmitals) {
		this.maxsubmitals = maxsubmitals;
	}
	
	public Boolean getSecclearance() {
		return secclearance;
	}
	
	public void setSecclearance(Boolean secclearance) {
		this.secclearance = secclearance;
	}
	
	public Boolean getSupplier() {
		return supplier;
	}
	
	public void setSupplier(Boolean supplier) {
		this.supplier = supplier;
	}
	
	public Long getRecruiterid() {
		return recruiterid;
	}
	
	public void setRecruiterid(Long recruiterid) {
		this.recruiterid = recruiterid;
	}
	
	public String getCompanyType() {
		return companyType;
	}
	
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	
	public Boolean getInvoiceContactPreference() {
		return invoiceContactPreference;
	}
	
	public void setInvoiceContactPreference(Boolean invoiceContactPreference) {
		this.invoiceContactPreference = invoiceContactPreference;
	}
	
	public Boolean getPipelineUrlExpired() {
		return pipelineUrlExpired;
	}
	
	public void setPipelineUrlExpired(Boolean pipelineUrlExpired) {
		this.pipelineUrlExpired = pipelineUrlExpired;
	}
	
	public Long getPaymentterms() {
		return paymentterms;
	}
	
	public void setPaymentterms(Long paymentterms) {
		this.paymentterms = paymentterms;
	}
	
	public String getCountryid() {
		return countryid;
	}
	
	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}
	
	public Long getCompPortalid() {
		return compPortalid;
	}
	
	public void setCompPortalid(Long compPortalid) {
		this.compPortalid = compPortalid;
	}
	
	public String getPortalApplyemail() {
		return portalApplyemail;
	}
	
	public void setPortalApplyemail(String portalApplyemail) {
		this.portalApplyemail = portalApplyemail;
	}
	
	public String getCompPortalname() {
		return compPortalname;
	}
	
	public void setCompPortalname(String compPortalname) {
		this.compPortalname = compPortalname;
	}
	
	public String getParentpath() {
		return parentpath;
	}
	
	public void setParentpath(String parentpath) {
		this.parentpath = parentpath;
	}
	
	public String getOwners() {
		return owners;
	}
	
	public void setOwners(String owners) {
		this.owners = owners;
	}
	
	public String getPrimaryContacts() {
		return primaryContacts;
	}
	
	public void setPrimaryContacts(String primaryContacts) {
		this.primaryContacts = primaryContacts;
	}
	
	public List<CompanyAddress> getCompanyAddresses() {
		return companyAddresses;
	}
	
	public void setCompanyAddresses(List<CompanyAddress> companyAddresses) {
		this.companyAddresses = companyAddresses;
	}
	
	public List<CompanyUDF> getCompanyUDFs() {
		return companyUDFs;
	}
	
	public void setCompanyUDFs(List<CompanyUDF> companyUDFs) {
		this.companyUDFs = companyUDFs;
	}
	
	public List<FinancialsType> getFinancialsTypes() {
		return financialsTypes;
	}
	
	public void setFinancialsTypes(List<FinancialsType> financialsTypes) {
		this.financialsTypes = financialsTypes;
	}
}

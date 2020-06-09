package com.jobdiva.api.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Company implements java.io.Serializable {
	
	//
	private Long					id;
	//
	private Long					teamid;
	//
	private String					name;
	//
	private String					address1;
	//
	private String					address2;
	//
	private String					city;
	//
	private String					state;
	//
	private String					zipcode;
	//
	private String					nameIndex;
	//
	private String					phone;
	//
	private String					fax;
	//
	private String					url;
	//
	private String					email;
	//
	private transient Clob			submittalInstr;
	//
	private BigDecimal				discount;
	//
	private BigDecimal				discountpct;
	//
	private Byte					discountType;
	//
	private Date					dateentered;
	//
	private Long					parentCompanyid;
	//
	private String					parentCompanyName;
	//
	private transient Blob			logo;
	//
	private Boolean					refcheck;
	//
	private Boolean					drugtest;
	//
	private Boolean					backcheck;
	//
	private Short					maxsubmitals;
	//
	private Boolean					secclearance;
	//
	private Boolean					supplier;
	//
	private Long					recruiterid;
	//
	private String					companyType;
	//
	private Boolean					invoiceContactPreference;
	//
	private Boolean					pipelineUrlExpired;
	//
	private Long					paymentterms;
	//
	private String					countryid;
	//
	private Long					compPortalid;
	//
	private String					portalApplyemail;
	//
	private String					compPortalname;
	//
	private String					parentpath;
	//
	private String					owners;
	//
	private String					primaryContacts;
	//
	private List<CompanyAddress>	companyAddresses	= new ArrayList<CompanyAddress>();
	//
	private List<CompanyUDF>		companyUDFs			= new ArrayList<CompanyUDF>();
	//
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

package com.jobdiva.api.dao.def;

public class UserfieldsDef {
	
	private Integer	id;
	private String	fieldName;
	private Long	fieldTypeId;
	private Long	fieldSize;
	private Long	fieldFor;
	private Boolean	mandatory;
	private Long	parentUserFieldId;
	private Long	displayOrder;
	private Boolean	mask;
	private Long	tabid;
	private Long	next;
	private Boolean	enforceLink;
	private Boolean	includeInUrl;
	private Boolean	showToHm;
	private Boolean	showToSupp;
	private Boolean	showToCand;
	private Long	showLastChar;
	private Long	defaultToUdfId;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public Long getFieldTypeId() {
		return fieldTypeId;
	}
	
	public void setFieldTypeId(Long fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}
	
	public Long getFieldSize() {
		return fieldSize;
	}
	
	public void setFieldSize(Long fieldSize) {
		this.fieldSize = fieldSize;
	}
	
	public Long getFieldFor() {
		return fieldFor;
	}
	
	public void setFieldFor(Long fieldFor) {
		this.fieldFor = fieldFor;
	}
	
	public Boolean getMandatory() {
		return mandatory;
	}
	
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public Long getParentUserFieldId() {
		return parentUserFieldId;
	}
	
	public void setParentUserFieldId(Long parentUserFieldId) {
		this.parentUserFieldId = parentUserFieldId;
	}
	
	public Long getDisplayOrder() {
		return displayOrder;
	}
	
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	public Boolean getMask() {
		return mask;
	}
	
	public void setMask(Boolean mask) {
		this.mask = mask;
	}
	
	public Long getTabid() {
		return tabid;
	}
	
	public void setTabid(Long tabid) {
		this.tabid = tabid;
	}
	
	public Long getNext() {
		return next;
	}
	
	public void setNext(Long next) {
		this.next = next;
	}
	
	public Boolean getEnforceLink() {
		return enforceLink;
	}
	
	public void setEnforceLink(Boolean enforceLink) {
		this.enforceLink = enforceLink;
	}
	
	public Boolean getIncludeInUrl() {
		return includeInUrl;
	}
	
	public void setIncludeInUrl(Boolean includeInUrl) {
		this.includeInUrl = includeInUrl;
	}
	
	public Boolean getShowToHm() {
		return showToHm;
	}
	
	public void setShowToHm(Boolean showToHm) {
		this.showToHm = showToHm;
	}
	
	public Boolean getShowToSupp() {
		return showToSupp;
	}
	
	public void setShowToSupp(Boolean showToSupp) {
		this.showToSupp = showToSupp;
	}
	
	public Boolean getShowToCand() {
		return showToCand;
	}
	
	public void setShowToCand(Boolean showToCand) {
		this.showToCand = showToCand;
	}
	
	public Long getShowLastChar() {
		return showLastChar;
	}
	
	public void setShowLastChar(Long showLastChar) {
		this.showLastChar = showLastChar;
	}
	
	public Long getDefaultToUdfId() {
		return defaultToUdfId;
	}
	
	public void setDefaultToUdfId(Long defaultToUdfId) {
		this.defaultToUdfId = defaultToUdfId;
	}
}

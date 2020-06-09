package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class Qualification implements java.io.Serializable {
	
	private Integer	cateId;
	private Integer	teamId;
	private String	catName;
	private Boolean	closed;
	private Boolean	includeNotMarked;
	private Integer	colorId;
	private Boolean	authRequired;
	private Integer	displayOrder;
	private Boolean	multipleChoice;
	private Boolean	orderByName;
	
	public Integer getCateId() {
		return cateId;
	}
	
	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}
	
	public Integer getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	public String getCatName() {
		return catName;
	}
	
	public void setCatName(String catName) {
		this.catName = catName;
	}
	
	public Boolean getClosed() {
		return closed;
	}
	
	public void setClosed(Boolean closed) {
		this.closed = closed;
	}
	
	public Boolean getIncludeNotMarked() {
		return includeNotMarked;
	}
	
	public void setIncludeNotMarked(Boolean includeNotMarked) {
		this.includeNotMarked = includeNotMarked;
	}
	
	public Integer getColorId() {
		return colorId;
	}
	
	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}
	
	public Boolean getAuthRequired() {
		return authRequired;
	}
	
	public void setAuthRequired(Boolean authRequired) {
		this.authRequired = authRequired;
	}
	
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	public Boolean getMultipleChoice() {
		return multipleChoice;
	}
	
	public void setMultipleChoice(Boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}
	
	public Boolean getOrderByName() {
		return orderByName;
	}
	
	public void setOrderByName(Boolean orderByName) {
		this.orderByName = orderByName;
	}
}

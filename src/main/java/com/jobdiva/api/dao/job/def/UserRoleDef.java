package com.jobdiva.api.dao.job.def;

import com.jobdiva.api.model.UserRole;

@SuppressWarnings("serial")
public class UserRoleDef extends UserRole {
	
	private Boolean	isSale			= false;
	private Boolean	isLeadSales		= false;
	private Boolean	isRecruiter		= false;
	private Boolean	isLeadRecruiter	= false;
	
	public Boolean getIsSale() {
		return isSale;
	}
	
	public void setIsSale(Boolean isSale) {
		this.isSale = isSale;
	}
	
	public Boolean getIsLeadSales() {
		return isLeadSales;
	}
	
	public void setIsLeadSales(Boolean isLeadSales) {
		this.isLeadSales = isLeadSales;
	}
	
	public Boolean getIsRecruiter() {
		return isRecruiter;
	}
	
	public void setIsRecruiter(Boolean isRecruiter) {
		this.isRecruiter = isRecruiter;
	}
	
	public Boolean getIsLeadRecruiter() {
		return isLeadRecruiter;
	}
	
	public void setIsLeadRecruiter(Boolean isLeadRecruiter) {
		this.isLeadRecruiter = isLeadRecruiter;
	}
}

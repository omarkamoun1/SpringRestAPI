package com.jobdiva.api.dao.job.def;

import com.jobdiva.api.model.UserRole;

@SuppressWarnings("serial")
public class UserRoleDef extends UserRole {
	
	private Boolean	isSale;
	private Boolean	isLeadSales;
	private Boolean	isRecruiter;
	private Boolean	isLeadRecruiter;
	
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

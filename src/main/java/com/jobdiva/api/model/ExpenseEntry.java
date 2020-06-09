package com.jobdiva.api.model;

import java.util.Date;

@SuppressWarnings("serial")
public class ExpenseEntry implements java.io.Serializable {
	
	private Date	date;
	private String	category;
	private Double	expenseamount;
	private Double	quantity;
	private Boolean	paybycompany;
	private Boolean	billable;
	private String	comments;
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Double getExpenseamount() {
		return expenseamount;
	}
	
	public void setExpenseamount(Double expenseamount) {
		this.expenseamount = expenseamount;
	}
	
	public Double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public Boolean getPaybycompany() {
		return paybycompany;
	}
	
	public void setPaybycompany(Boolean paybycompany) {
		this.paybycompany = paybycompany;
	}
	
	public Boolean getBillable() {
		return billable;
	}
	
	public void setBillable(Boolean billable) {
		this.billable = billable;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
}

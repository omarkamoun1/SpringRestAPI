package com.jobdiva.api.model;

/**
 * @author Joseph Chidiac
 *
 *         May 17, 2021
 */
@SuppressWarnings("serial")
public class ExpenseCategory implements java.io.Serializable {
	
	private Long	id;
	private String	name;
	private Long	rate;
	private String	unit;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getRate() {
		return rate;
	}
	
	public void setRate(Long rate) {
		this.rate = rate;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
}

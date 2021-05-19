package com.jobdiva.api.model.v2.billingtimesheet;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class DeleteExpenseDef implements Serializable {
	
	@JsonProperty(required = true)
	private Long	expenseId;
	//
	@JsonProperty(required = false)
	private String	externalId;
	
	public Long getExpenseId() {
		return expenseId;
	}
	
	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}
	
	public String getExternalId() {
		return externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
}

package com.jobdiva.api.model.accessright;

/**
 * @author Joseph Chidiac
 *
 */
public class APIPermission {
	
	private String	methodName;
	private Long	divisionId;
	//
	
	public Long getDivisionId() {
		return divisionId;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}
}

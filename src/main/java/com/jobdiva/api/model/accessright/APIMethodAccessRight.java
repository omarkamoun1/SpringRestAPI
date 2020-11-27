package com.jobdiva.api.model.accessright;

/**
 * @author Joseph Chidiac
 *
 */
public enum APIMethodAccessRight {
	NONE(""); //
	
	private String methodName;
	
	APIMethodAccessRight(String methodName) {
		this.methodName = methodName;
	}
	
	public String getMethodName() {
		return this.methodName;
	}
	
	@Override
	public String toString() {
		return methodName;
	}
}

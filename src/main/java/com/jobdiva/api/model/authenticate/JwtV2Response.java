package com.jobdiva.api.model.authenticate;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JwtV2Response implements Serializable {
	
	private String	token;
	private String	refreshtoken;
	
	public JwtV2Response(String token, String refreshtoken) {
		super();
		this.token = token;
		this.refreshtoken = refreshtoken;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getRefreshtoken() {
		return refreshtoken;
	}
	
	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}
}
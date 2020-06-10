package com.jobdiva.api.model.proxy;

import static java.lang.String.format;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class Response implements java.io.Serializable {
	
	// HTTP response status code (e.g. 200)
	private Integer					statusCode;
	// HTTP response body as text.
	private String					body;
	//
	private List<ProxyParameter>	proxyParameters;
	private List<ProxyHeader>		proxyHeaders;
	
	public Response(Integer statusCode, String body) {
		this.statusCode = statusCode;
		this.body = StringUtils.trimToEmpty(body);
	}
	
	public Integer getStatusCode() {
		return statusCode;
	}
	
	public String getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		if (StringUtils.isBlank(getBody())) {
			return format("HTTP status code %d and an empty response body.", getStatusCode());
		}
		return format("HTTP status code %d and response body: %s", getStatusCode(), getBody());
	}
	
	public List<ProxyParameter> getProxyParameters() {
		return proxyParameters;
	}
	
	public void setProxyParameters(List<ProxyParameter> proxyParameters) {
		this.proxyParameters = proxyParameters;
	}
	
	public List<ProxyHeader> getProxyHeaders() {
		return proxyHeaders;
	}
	
	public void setProxyHeaders(List<ProxyHeader> proxyHeaders) {
		this.proxyHeaders = proxyHeaders;
	}
	
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
}
package com.jobdiva.api.test;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author Joseph Chidiac
 *
 */
public class JobDivaBasicAuthorizationInterceptor implements ClientHttpRequestInterceptor {
	
	private String token;
	
	public JobDivaBasicAuthorizationInterceptor(String token) {
		super();
		this.token = token;
	}
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		request.getHeaders().add("Authorization", token);
		return execution.execute(request, body);
	}
}

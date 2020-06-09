package com.jobdiva.api.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jobdiva.api.config.jwt.CustomAuthenticationToken;

public class JobdivaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final String SPRING_SECURITY_FORM_CLIENT_KEY = "client";
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		CustomAuthenticationToken authRequest = getAuthRequest(request);
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	private CustomAuthenticationToken getAuthRequest(HttpServletRequest request) {
		//
		String username = obtainUsername(request);
		//
		String password = obtainPassword(request);
		//
		Long clientId = obtainDomain(request);
		//
		if (username == null) {
			username = "";
		}
		if (password == null) {
			password = "";
		}
		if (clientId == null) {
			clientId = 0L;
		}
		return new CustomAuthenticationToken(username, password, clientId);
	}
	
	private Long obtainDomain(HttpServletRequest request) {
		return Long.parseLong(request.getParameter(SPRING_SECURITY_FORM_CLIENT_KEY));
	}
}
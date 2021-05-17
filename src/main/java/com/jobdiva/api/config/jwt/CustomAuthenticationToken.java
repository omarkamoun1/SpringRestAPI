package com.jobdiva.api.config.jwt;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.jobdiva.api.dao.setup.JobDivaConnection;

@SuppressWarnings("serial")
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
	
	private Long						clientId;
	private JobDivaConnection			jobDivaConnection;
	private JdbcTemplate				jdbcTemplate;
	private NamedParameterJdbcTemplate	namedParameterJdbcTemplate;
	private Boolean						checkApiPermission			= true;
	private Boolean						authenticateWithoutClientId	= false;
	
	public CustomAuthenticationToken(Object principal, Object credentials, Long clientId, Boolean checkApiPermission) {
		super(principal, credentials);
		this.clientId = clientId;
		this.checkApiPermission = checkApiPermission;
		super.setAuthenticated(false);
	}
	
	public CustomAuthenticationToken(Object principal, Object credentials, Boolean authenticateWithoutClientId) {
		super(principal, credentials);
		this.authenticateWithoutClientId = authenticateWithoutClientId;
		this.checkApiPermission = false;
		super.setAuthenticated(false);
	}
	
	public CustomAuthenticationToken(Object principal, Object credentials, Long clientId, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.clientId = clientId;
		super.setAuthenticated(true); // must use super, as we override
	}
	
	public CustomAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> mapAuthorities) {
		super(principal, credentials, mapAuthorities);
	}
	
	public Long getClientId() {
		return clientId;
	}
	
	public JobDivaConnection getJobDivaConnection() {
		return jobDivaConnection;
	}
	
	public void setJobDivaConnection(JobDivaConnection jobDivaConnection) {
		this.jobDivaConnection = jobDivaConnection;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}
	
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	
	public Boolean getCheckApiPermission() {
		return checkApiPermission;
	}
	
	public void setCheckApiPermission(Boolean checkApiPermission) {
		this.checkApiPermission = checkApiPermission;
	}
	
	public Boolean getAuthenticateWithoutClientId() {
		return authenticateWithoutClientId;
	}
	
	public void setAuthenticateWithoutClientId(Boolean authenticateWithoutClientId) {
		this.authenticateWithoutClientId = authenticateWithoutClientId;
	}
}
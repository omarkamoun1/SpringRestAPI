package com.jobdiva.api.model.authenticate;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@SuppressWarnings("serial")
public class JobDivaSession extends User {
	
	private Long	teamId;
	private String	userName;
	private String	password;
	private Integer	environment;
	private long	recruiterId;
	private long	leader;
	
	public JobDivaSession() {
		super(null, null, new HashSet<JobDivaGrantedAuthority>());
	}
	
	public JobDivaSession(Long teamId, String userName, String password, Integer environment, long recruiterId, long leader) {
		//
		super(userName, password, new HashSet<JobDivaGrantedAuthority>());
		//
		this.teamId = teamId;
		this.userName = userName;
		this.password = password;
		this.environment = environment;
		this.recruiterId = recruiterId;
		this.leader = leader;
	}
	
	public JobDivaSession(String username, Long teamId, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.teamId = teamId;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getEnvironment() {
		return environment;
	}
	
	public void setEnvironment(Integer environment) {
		this.environment = environment;
	}
	
	public long getRecruiterId() {
		return recruiterId;
	}
	
	public void setRecruiterId(long recruiterId) {
		this.recruiterId = recruiterId;
	}
	
	public long getLeader() {
		return leader;
	}
	
	public void setLeader(long leader) {
		this.leader = leader;
	}
}

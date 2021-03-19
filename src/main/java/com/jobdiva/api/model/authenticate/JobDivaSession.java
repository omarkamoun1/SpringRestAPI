package com.jobdiva.api.model.authenticate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jobdiva.api.model.accessright.APIPermission;

@SuppressWarnings("serial")
public class JobDivaSession extends User {
	
	private Long				teamId;
	private String				userName;
	private String				password;
	private Integer				environment;
	private long				recruiterId;
	private long				leader;
	private List<APIPermission>	apiPermissions		= new ArrayList<APIPermission>();
	private Boolean				checkApiPermission	= true;
	private String				firstName;
	private String				lastName;
	private Long				userOptions;
	private String				regionCode;
	
	//
	public JobDivaSession() {
		super("a", "a", new HashSet<JobDivaGrantedAuthority>());
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
	
	public Boolean allowAPI(String methodName) {
		//
		if (apiPermissions.size() > 0) {
			//
			for (APIPermission permission : apiPermissions) {
				if (methodName.equalsIgnoreCase(permission.getMethodName())) {
					return true;
				}
			}
			//
			return false;
		}
		//
		return true;
	}
	
	public void checkForAPIPermission(String methodName) throws Exception {
		if (!allowAPI(methodName)) {
			throw new Exception("You are not allowed to access to " + methodName + " API.");
		}
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
	
	public List<APIPermission> getApiPermissions() {
		return apiPermissions;
	}
	
	public void setApiPermissions(List<APIPermission> apiPermissions) {
		this.apiPermissions = apiPermissions;
	}
	
	public Boolean getCheckApiPermission() {
		return checkApiPermission;
	}
	
	public void setCheckApiPermission(Boolean checkApiPermission) {
		this.checkApiPermission = checkApiPermission;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Long getUserOptions() {
		return userOptions;
	}
	
	public void setUserOptions(Long userOptions) {
		this.userOptions = userOptions;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
}

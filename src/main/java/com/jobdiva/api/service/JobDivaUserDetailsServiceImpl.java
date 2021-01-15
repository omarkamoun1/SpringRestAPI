package com.jobdiva.api.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.authenticate.JobDivaAuthenticateDao;
import com.jobdiva.api.service.api.JobDivaUserDetailsService;

@Service("userDetailsService")
public class JobDivaUserDetailsServiceImpl implements JobDivaUserDetailsService {
	
	@Autowired
	JobDivaAuthenticateDao jobDivaAuthenticateDao;
	
	@Override
	public UserDetails loadUserByUsernameAndClientId(Long clientId, String username, String password, Boolean checkApiPermission) throws Exception {
		if (StringUtils.isAnyBlank(username) || clientId == null) {
			throw new UsernameNotFoundException("Username and domain must be provided");
		}
		User user = jobDivaAuthenticateDao.authenticate(clientId, username, password, null, checkApiPermission);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Username not found for Client, username=%s, domain=%s", username, clientId));
		}
		return user;
	}
}
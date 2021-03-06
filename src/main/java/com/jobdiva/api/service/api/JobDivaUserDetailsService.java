package com.jobdiva.api.service.api;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface JobDivaUserDetailsService {
	
	UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException, Exception;
	
	UserDetails loadUserByUsernameAndClientId(Long clientId, String username, String password, Boolean checkApiPermission) throws UsernameNotFoundException, Exception;
}
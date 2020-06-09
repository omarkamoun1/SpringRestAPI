package com.jobdiva.api.service.api;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface JobDivaUserDetailsService {
	
	UserDetails loadUserByUsernameAndClientId(Long clientId, String username, String password) throws UsernameNotFoundException, Exception;
}
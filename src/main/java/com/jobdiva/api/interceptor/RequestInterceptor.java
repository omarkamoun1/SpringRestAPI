package com.jobdiva.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jobdiva.api.config.ds.DataBaseContextHolder;
import com.jobdiva.api.config.jwt.CustomAuthenticationToken;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Joseph Chidiac
 *
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof CustomAuthenticationToken) {
			CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) authentication;
			String poolName = ((HikariDataSource) customAuthenticationToken.getJdbcTemplate().getDataSource()).getPoolName();
			DataBaseContextHolder.setCurrentDb(poolName);
		}
		//
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println("Intercepting request : posthandle method");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		System.out.println("Intercepting request : aftercompletion method");
	}
}
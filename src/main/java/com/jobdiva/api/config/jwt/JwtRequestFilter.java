package com.jobdiva.api.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jobdiva.api.dao.setup.JobDivaConnection;
import com.jobdiva.api.dao.setup.JobDivaConnectivity;
import com.jobdiva.api.model.authenticate.JobDivaSession;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	//
	
	//
	@Autowired
	private JwtTokenUtil	jwtTokenUtil;
	//
	private UserCache		userCache;
	//
	@Autowired
	JobDivaConnectivity		jobDivaConnectivity;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		//
		//
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null) {
			if (requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
			} else {
				jwtToken = requestTokenHeader;
			}
			//
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				logger.error("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				logger.error("JWT Token has expired");
			}
		} else {
			// logger.warn("JWT Token does not begin with Bearer String");
		}
		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			//
			JobDivaSession divaSession = (JobDivaSession) userCache.getUserFromCache(username);
			//
			// if token is valid configure Spring Security to manually set
			// authentication
			try {
				if (jwtTokenUtil.validateToken(jwtToken, divaSession)) {
					//
					CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(divaSession, divaSession.getPassword(), divaSession.getTeamId());
					customAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					//
					JdbcTemplate jdbcTemplate = jobDivaConnectivity.getJdbcTemplate(divaSession.getTeamId());
					//
					NamedParameterJdbcTemplate namedParameterJdbcTemplate = jobDivaConnectivity.getNamedParameterJdbcTemplate(divaSession.getTeamId());
					//
					JobDivaConnection divaConnection = jobDivaConnectivity.getJobDivaConnection(divaSession.getTeamId());
					//
					customAuthenticationToken.setJobDivaConnection(divaConnection);
					customAuthenticationToken.setJdbcTemplate(jdbcTemplate);
					customAuthenticationToken.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
					//
					//
					// After setting the Authentication in the context, we
					// specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
				} else {
					userCache.removeUserFromCache(username);
				}
			} catch (Exception e) {
				logger.error("Unable to get JWT Token or JWT Token has expired");
			}
		}
		chain.doFilter(request, response);
	}
	
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
}
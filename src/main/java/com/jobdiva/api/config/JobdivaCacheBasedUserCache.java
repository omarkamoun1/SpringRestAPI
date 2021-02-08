package com.jobdiva.api.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.dao.setup.JobDivaConnectivity;
import com.jobdiva.api.model.authenticate.JobDivaSession;

/**
 * @author Joseph Chidiac
 *
 */
public class JobdivaCacheBasedUserCache implements UserCache {
	// ~ Static fields/initializers
	// =====================================================================================
	
	private static final Log	logger	= LogFactory.getLog(JobdivaCacheBasedUserCache.class);
	// ~ Instance fields
	// ================================================================================================
	private final Cache			cache;
	// ~ Constructors
	// ===================================================================================================
	@Autowired
	JobDivaConnectivity			jobDivaConnectivity;
	
	public JobdivaCacheBasedUserCache(Cache cache) {
		Assert.notNull(cache, "cache mandatory");
		this.cache = cache;
	}
	// ~ Methods
	// ========================================================================================================
	
	public UserDetails getUserFromCache(String username) {
		Cache.ValueWrapper element = username != null ? cache.get(username) : null;
		if (logger.isDebugEnabled()) {
			logger.debug("Cache hit: " + (element != null) + "; username: " + username);
		}
		if (element == null) {
			UserDetails userDetails = getUserFromDbCache(username);
			if (userDetails != null) {
				cache.put(userDetails.getUsername(), userDetails);
				return userDetails;
			}
			return null;
		} else {
			return (UserDetails) element.get();
		}
	}
	
	public void putUserInCache(UserDetails user) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + user.getUsername());
		}
		cache.put(user.getUsername(), user);
		//
		putUserInDbCache(user.getUsername(), user);
		//
	}
	
	public void removeUserFromCache(UserDetails user) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + user.getUsername());
		}
		this.removeUserFromCache(user.getUsername());
		//
		deletefromDbCache(user.getUsername());
	}
	
	public void removeUserFromCache(String username) {
		cache.evict(username);
	}
	
	private void deletefromDbCache(String userName) {
		String sql = "DELETE FROM TRESTAPI_CACHE WHERE USERNAME = ? ";
		Object[] params = new Object[] { userName };
		jobDivaConnectivity.getCentralTemplate().update(sql, params);
	}
	
	private void putUserInDbCache(String userName, UserDetails userDetails) {
		//
		deletefromDbCache(userName);
		//
		String sql = "INSERT INTO TRESTAPI_CACHE (USERNAME, USER_SESSION, CREATED_DATE) values(?, ?, NOW()) ";
		//
		ObjectMapper mapper = new ObjectMapper();
		String strUserSession = null;
		try {
			strUserSession = mapper.writeValueAsString(userDetails);
		} catch (JsonProcessingException e) {
		}
		//
		Object[] params = new Object[] { userName, strUserSession };
		jobDivaConnectivity.getCentralTemplate().update(sql, params);
	}
	
	private UserDetails getUserFromDbCache(String userName) {
		//
		//
		String sql = " SELECT USER_SESSION FROM TRESTAPI_CACHE WHERE USERNAME = ? ";
		//
		//
		Object[] params = new Object[] { userName };
		List<JobDivaSession> jobDivaSessions = jobDivaConnectivity.getCentralTemplate().query(sql, params, new RowMapper<JobDivaSession>() {
			
			@Override
			public JobDivaSession mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				String userSession = rs.getString("USER_SESSION");
				ObjectMapper mapper = new ObjectMapper();
				JobDivaSession jobDivaSession;
				try {
					jobDivaSession = mapper.readValue(userSession, JobDivaSession.class);
					return jobDivaSession;
				} catch (Exception e) {
					return null;
				}
			}
		});
		return jobDivaSessions != null && jobDivaSessions.size() > 0 ? jobDivaSessions.get(0) : null;
	}
}
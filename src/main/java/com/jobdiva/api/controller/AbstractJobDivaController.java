package com.jobdiva.api.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jobdiva.api.dao.authenticate.JobDivaAuthenticateDao;
import com.jobdiva.api.dao.setup.JobDivaConnectivity;
import com.jobdiva.api.model.authenticate.JobDivaSession;

public class AbstractJobDivaController {
	
	protected final Logger							logger				= LoggerFactory.getLogger(this.getClass());
	//
	//
	@Autowired
	protected JobDivaAuthenticateDao				jobDivaAuthenticate;
	//
	@Autowired
	JobDivaConnectivity								jobDivaConnectivity;
	//
	public static HashMap<Long, Vector<Integer>>	TEAM_ACCOUNT_MAP	= new HashMap<Long, Vector<Integer>>();		// teamid
																													// ->
																													// [userCount,
																													// today(yyyyMMdd),
																													// param,
																													// threadshold]
	
	protected JobDivaSession getJobDivaSession() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			JobDivaSession jobDivaSession = (JobDivaSession) authentication.getPrincipal();
			if (jobDivaSession != null && jobDivaSession.isAccountNonExpired()) {
				//
				//
				//
				//
				// long leader = jobDivaSession.getLeader();
				// //
				// if (leader != 13328 && leader != 13584 &&
				// !checkAccountAbuse(jobDivaSession)) {
				// throw new Exception("There are too many API requests in the
				// queue. Please try again later...");
				// }
				//
				//
				//
				//
				return jobDivaSession;
			}
		}
		//
		throw new AuthenticationException("Invalid Authentication.");
	}
	
	public Integer getUserCount(long teamId) {
		String sql = "SELECT COUNT(*) as CNT FROM trecruiter WHERE groupid = ? AND active = 1 AND BITAND(leader, 1024) > 0";
		Object[] param = new Object[] { teamId };
		//
		JdbcTemplate jdbcTemplate = jobDivaConnectivity.getJdbcTemplate(teamId);
		//
		List<Integer> envIds = jdbcTemplate.query(sql, param, new RowMapper<Integer>() {
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("CNT");
			}
		});
		//
		return envIds != null && envIds.size() > 0 ? envIds.get(0) : 0;
	}
	
	public void refreshTeamAccountMap(long teamid, Integer today) {
		// Map definition:
		// teamid -> [
		// __________________________(int)userCount,
		// __________________________(Date)today,
		// __________________________(int)param,
		// __________________________(long)threadshold
		// ___________]
		//
		if (!TEAM_ACCOUNT_MAP.containsKey(teamid)) {
			Vector<Integer> data = new Vector<Integer>();
			data.add(0);
			data.add(0);
			data.add(12);
			data.add(180000);
			TEAM_ACCOUNT_MAP.put(teamid, data);
		}
		Vector<Integer> data = TEAM_ACCOUNT_MAP.get(teamid);
		if (data.get(1) < today) {
			synchronized (data) {
				Integer userCount = getUserCount(teamid);
				data.set(0, userCount);
				data.set(1, today);
				if (userCount >= 10 & userCount < 20) {
					data.set(2, 15);
					data.set(3, 200000);
				} else if (userCount >= 20 && userCount < 50) {
					data.set(2, 30);
					data.set(3, 300000);
				} else if (userCount >= 50 && userCount < 75) {
					data.set(2, 45);
					data.set(3, 450000);
				} else if (userCount >= 100) {
					data.set(2, 60);
					data.set(3, 600000);
				}
				TEAM_ACCOUNT_MAP.put(teamid, data);
			}
		}
	}
	
	protected Boolean checkAccountAbuse(JobDivaSession jobDivaSession) {
		//
		long teamid = jobDivaSession.getTeamId();
		//
		boolean passTest = false;
		//
		Integer today = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		//
		if (!TEAM_ACCOUNT_MAP.containsKey(teamid) || TEAM_ACCOUNT_MAP.get(teamid).get(1) < today) {
			refreshTeamAccountMap(teamid, today);
		}
		//
		//
		Vector<Integer> data = TEAM_ACCOUNT_MAP.get(teamid);
		Integer param = data.get(2);
		Integer threshold = data.get(3);
		//
		String sql = "SELECT lastjobdivaapicall, sysdate clock FROM tteam WHERE id = ?";
		Object[] params = new Object[] { teamid };
		//
		JdbcTemplate jdbcTemplate = jobDivaConnectivity.getJdbcTemplate(teamid);
		//
		List<List<Object>> list = jdbcTemplate.query(sql, params, new RowMapper<List<Object>>() {
			
			@Override
			public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<Object> list = new ArrayList<Object>();
				list.add(rs.getTimestamp("lastjobdivaapicall"));
				list.add(rs.getTimestamp("clock"));
				return list;
			}
		});
		//
		if (list != null && list.size() > 0) {
			Timestamp lastJobdivaApiCall = (Timestamp) list.get(0).get(0);
			Timestamp clock = (Timestamp) list.get(0).get(1);
			//
			if (lastJobdivaApiCall == null) {
				String sqlUpdate = "UPDATE tteam SET lastjobdivaapicall = sysdate WHERE id = ? ";
				jdbcTemplate.update(sqlUpdate, new Object[] { teamid });
				passTest = true;
			} else if (lastJobdivaApiCall != null && lastJobdivaApiCall.getTime() < clock.getTime()) {
				String sqlUpdate = " UPDATE tteam SET lastjobdivaapicall = greatest(sysdate, lastjobdivaapicall + 1/24/60/" + param + ") WHERE id = ? ";
				jdbcTemplate.update(sqlUpdate, new Object[] { teamid });
				passTest = true;
			} else if (lastJobdivaApiCall != null && lastJobdivaApiCall.getTime() > clock.getTime() + threshold) {
				// printLog("API call blocked due to excessive calls!!!");
				//
				//
				logger.info("checkAccountAbuse :: " + teamid + " [" + lastJobdivaApiCall + "] [" + clock + "] [" + threshold + "]");
				//
				//
			} else {
				String sqlUpdate = " UPDATE tteam SET lastjobdivaapicall = lastjobdivaapicall + 1/24/60/" + param + " WHERE id = ? ";
				jdbcTemplate.update(sqlUpdate, new Object[] { teamid });
				passTest = true;
			}
			//
		}
		//
		return passTest;
		//
	}
	
	protected Integer[] intListToArray(List<Integer> paramLits) {
		return paramLits != null ? paramLits.stream().toArray(Integer[]::new) : null;
	}
	
	protected String[] strListToArray(List<String> paramLits) {
		return paramLits != null ? paramLits.stream().toArray(String[]::new) : null;
	}
	
	protected Float[] floatListToArray(List<Float> paramLits) {
		return paramLits != null ? paramLits.stream().toArray(Float[]::new) : null;
	}
	
	protected Double[] doubleListToArray(List<Double> paramLits) {
		return paramLits != null ? paramLits.stream().toArray(Double[]::new) : null;
	}
}

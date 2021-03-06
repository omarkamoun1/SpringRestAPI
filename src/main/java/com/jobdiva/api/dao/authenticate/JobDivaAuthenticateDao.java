package com.jobdiva.api.dao.authenticate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.arizon.shared.Encryption;
import com.jobdiva.api.config.AppProperties;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.setup.JobDivaConnectivity;
import com.jobdiva.api.model.accessright.APIPermission;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class JobDivaAuthenticateDao extends AbstractJobDivaDao {
	
	//
	public static final int		SUCCESSFUL					= 0;
	public static final int		AUTHENTICATION_FAILED		= 1;
	public static final int		ERROR						= 2;
	//
	public static final Long	CONTROLLER_API_CLIENT_ID	= -2020L;
	public static final String	ATS_USERNAME				= "api.ts.user@jobdiva.com";
	public static final String	ATS_PASSWORD				= "api.ts.password";
	//
	@Autowired
	AppProperties				appProperties;
	//
	@Autowired
	JobDivaConnectivity			jobDivaConnectivity;
	//
	Map<String, Long>			usersTeamId					= Collections.synchronizedMap(new HashMap<String, Long>());
	
	//
	private Integer getEnv(Long teamId) {
		String sql = " SELECT MAINDBID FROM TMAINDB_TEAMID WHERE TEAMID = ? ";
		//
		Object[] param = new Object[] { teamId };
		//
		//
		List<Integer> envIds = _jdbcTemplate.query(sql, param, new RowMapper<Integer>() {
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("MAINDBID");
			}
		});
		//
		return envIds == null || envIds.size() == 0 ? 0 : envIds.get(0);
	}
	
	public JobDivaSession authenticate(String username, String password) throws Exception {
		//
		for (JdbcTemplate jdbcTemplate : jobDivaConnectivity.getJdbcsTemplates()) {
			//
			String sql = " select a.id, a.groupid, a.password, b.lastapicall, sysdate clock, a.s1, LEADER, a.firstname, a.lastname, a.USER_OPTIONS, a.REGION_CODE " //
					+ " from trecruiter a, tteam b " + //
					" where  a.email=? and a.active=1 " + //
					"   and b.id = a.groupid ";
			Object[] param = new Object[] { username };
			//
			//
			List<List<Object>> users = jdbcTemplate.query(sql, param, new RowMapper<List<Object>>() {
				
				@Override
				public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
					List<Object> list = new ArrayList<Object>();
					list.add(rs.getLong("id"));
					list.add(rs.getString("password"));
					list.add(rs.getTimestamp("lastapicall"));
					list.add(rs.getTimestamp("clock"));
					list.add(rs.getString("s1"));
					list.add(rs.getLong("LEADER"));
					list.add(rs.getLong("groupid"));
					//
					list.add(rs.getString("firstname"));
					list.add(rs.getString("lastname"));
					list.add(rs.getLong("USER_OPTIONS"));
					list.add(rs.getString("REGION_CODE"));
					//
					return list;
				}
			});
			//
			//
			if (users != null && users.size() > 0) {
				List<Object> list = users.get(0);
				//
				Long userId = (Long) list.get(0);
				Long leader = (Long) list.get(5);
				String db_password = (String) list.get(1);
				String salt = (String) list.get(4);
				Long clientid = (Long) list.get(6);
				//
				String firstname = (String) list.get(7);
				String lastname = (String) list.get(8);
				Long userOption = (Long) list.get(9);
				String regionCode = (String) list.get(10);
				//
				boolean correctPassword = false;
				if (salt != null) {
					if (Encryption.hashStringWithSalt(password, salt).equals(db_password))
						correctPassword = true;
				} else if (password.equals(db_password)) {
					correctPassword = true;
				}
				//
				if (correctPassword) {
					//
					Integer env = 0;
					env = getEnv(clientid);
					if (env > 0) {
						//
						JobDivaSession jobDivaSession = new JobDivaSession(clientid, username, password, env, userId, leader);
						jobDivaSession.setFirstName(firstname);
						jobDivaSession.setLastName(lastname);
						jobDivaSession.setUserOptions(userOption);
						jobDivaSession.setRegionCode(regionCode);
						return jobDivaSession;
					}
				}
			}
		}
		//
		throw new Error("Invalid username/password");
	}
	
	//
	public JobDivaSession authenticate(Long clientid, String username, String password, HashMap<Object, Object> optionsMap, Boolean checkApiPermission) throws Exception {
		//
		String sql = null;
		Object[] param = null;
		//
		JdbcTemplate jdbcTemplate = null;
		//
		if (username != null && username.startsWith(ATS_USERNAME) && password != null && password.startsWith(ATS_PASSWORD)) {
			//
			//
			Integer env = 0;
			env = getEnv(clientid);
			if (env == 0) {
				throw new Error("Invalid username/password");
			}
			//
			return new JobDivaSession(clientid, username, password, env, 0, 0);
			//
			//
		} else if (CONTROLLER_API_CLIENT_ID.equals(clientid)) {
			//
			Long teamId = usersTeamId.get(username);
			if (teamId == null) {
				//
				for (JdbcTemplate template : jobDivaConnectivity.getJdbcsTemplates()) {
					String sqlFindTeamId = "select groupid FROM trecruiter WHERE email = ? ";
					param = new Object[] { username };
					List<Long> teamIds = template.query(sqlFindTeamId, param, new RowMapper<Long>() {
						
						@Override
						public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getLong("groupid");
						}
					});
					//
					if (teamIds != null && teamIds.size() > 0) {
						jdbcTemplate = template;
						usersTeamId.put(username, teamIds.get(0));
					}
					//
					//
				}
				//
			} else {
				jdbcTemplate = jobDivaConnectivity.getJdbcTemplate(teamId);
			}
			//
			//
			if (jdbcTemplate == null) {
				throw new Error("Invalid username/password");
			}
			//
			//
			sql = " select a.id, a.groupid, a.password, b.lastapicall, sysdate clock, a.s1, LEADER , a.firstname, a.lastname, a.USER_OPTIONS, a.REGION_CODE " //
					+ " from trecruiter a, tteam b " + //
					" where  a.email=? and a.active=1 " + //
					"   and b.id = a.groupid ";
			param = new Object[] { username };
			//
		} else {
			//
			jdbcTemplate = jobDivaConnectivity.getJdbcTemplate(clientid);
			//
			if (checkApiPermission) {
				sql = " select a.id, a.groupid, a.password, b.lastapicall, sysdate clock, a.s1, LEADER , a.firstname, a.lastname, a.USER_OPTIONS, a.REGION_CODE " //
						+ " from trecruiter a, tteam b " + //
						" where a.groupid=? and a.email=? and a.active=1 and substr(a.permission2_recruiter, 31,1)='1' " + //
						"   and b.id = a.groupid ";
				param = new Object[] { clientid, username };
			} else {
				sql = " select a.id, a.groupid, a.password, b.lastapicall, sysdate clock, a.s1, LEADER , a.firstname, a.lastname, a.USER_OPTIONS, a.REGION_CODE " //
						+ " from trecruiter a, tteam b " + //
						" where a.groupid = ? and upper(a.email) = upper( ? ) and a.active=1 " + //
						"   and b.id = a.groupid ";
				param = new Object[] { clientid, username };
			}
			//
		}
		//
		List<List<Object>> users = jdbcTemplate.query(sql, param, new RowMapper<List<Object>>() {
			
			@Override
			public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<Object> list = new ArrayList<Object>();
				list.add(rs.getLong("id"));
				list.add(rs.getString("password"));
				list.add(rs.getTimestamp("lastapicall"));
				list.add(rs.getTimestamp("clock"));
				list.add(rs.getString("s1"));
				list.add(rs.getLong("LEADER"));
				list.add(rs.getLong("groupid"));
				//
				list.add(rs.getString("firstname"));
				list.add(rs.getString("lastname"));
				list.add(rs.getLong("USER_OPTIONS"));
				list.add(rs.getString("REGION_CODE"));
				return list;
			}
		});
		//
		//
		if (users == null || users.size() == 0) {
			throw new Error("Invalid username/password");
		} else {
			List<Object> list = users.get(0);
			//
			Long userId = (Long) list.get(0);
			Long leader = (Long) list.get(5);
			String db_password = (String) list.get(1);
			String salt = (String) list.get(4);
			//
			boolean correctPassword = false;
			if (salt != null) {
				if (Encryption.hashStringWithSalt(password, salt).equals(db_password))
					correctPassword = true;
			} else if (password.equals(db_password)) {
				correctPassword = true;
			}
			//
			if (!correctPassword)
				throw new Error("Invalid username/password");
			//
			Integer env = 0;
			if (CONTROLLER_API_CLIENT_ID.equals(clientid)) {
			} else {
				env = getEnv(clientid);
				if (env == 0) {
					throw new Error("Invalid username/password");
				}
			}
			//
			clientid = ((Long) list.get(6));
			//
			//
			String firstname = (String) list.get(7);
			String lastname = (String) list.get(8);
			Long userOption = (Long) list.get(9);
			String regionCode = (String) list.get(10);
			//
			JobDivaSession jobDivaSession = new JobDivaSession(clientid, username, password, env, userId, leader);
			jobDivaSession.setCheckApiPermission(checkApiPermission);
			jobDivaSession.setFirstName(firstname);
			jobDivaSession.setLastName(lastname);
			jobDivaSession.setUserOptions(userOption);
			jobDivaSession.setRegionCode(regionCode);
			//
			if (checkApiPermission) {
				sql = "select allowedoperation, divisionid from tapipermission where teamid=? and recruiterid=? ";
				param = new Object[] { clientid, userId };
				jdbcTemplate.query(sql, param, new RowMapper<Boolean>() {
					
					@Override
					public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
						String methodName = rs.getString("allowedoperation");
						Long divisionId = rs.getLong("divisionid");
						//
						APIPermission apiPermission = new APIPermission();
						apiPermission.setDivisionId(divisionId);
						apiPermission.setMethodName(methodName);
						//
						jobDivaSession.getApiPermissions().add(apiPermission);
						//
						return false;
					}
				});
			}
			//
			//
			//
			return jobDivaSession;
		}
		//
	}
}

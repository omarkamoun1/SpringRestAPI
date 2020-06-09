package com.jobdiva.api.dao.setup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobDivaConnectivity {
	
	//
	protected final Logger					logger						= LoggerFactory.getLogger(this.getClass());
	//
	@Autowired
	JdbcTemplate							jdbcTemplate;
	//
	List<JobDivaConnection>					divaConnections				= new ArrayList<JobDivaConnection>();
	//
	List<JdbcTemplate>						jdbcsTemplates				= Collections.synchronizedList(new ArrayList<JdbcTemplate>());
	Map<Long, JobDivaConnection>			jobDivaConnections			= Collections.synchronizedMap(new HashMap<Long, JobDivaConnection>());
	Map<Long, JdbcTemplate>					jdbcTemplates				= Collections.synchronizedMap(new HashMap<Long, JdbcTemplate>());
	Map<Long, NamedParameterJdbcTemplate>	namedParameterJdbcTemplates	= Collections.synchronizedMap(new HashMap<Long, NamedParameterJdbcTemplate>());
	
	@PostConstruct
	public void init() {
		//
		logger.info("Setup DataBase Connectivity");
		//
		String sql = "SELECT ID," //
				+ " CONNECT_STRING, " //
				+ " CONNECT_THIN, " //
				+ " CONNECT_USER, " //
				+ " CONNECT_PASSWORD, " //
				+ " MAIN_SERVLET ," //
				+ " WAFER_SERVLET ," //
				+ " WAFER_BACKUP_SERVLET ," //
				+ " ATTRIBUTE_SERVLET ," //
				+ " ENVIRONMENT_TYPE ," //
				+ " APACHE_LOCATION , " //
				+ " UNIVERSALSEARCH_SERVLET, " //
				+ "	FLEXSTAFFING_SERVLET " //
				+ " FROM TMAINDBS ";
		//
		divaConnections = jdbcTemplate.query(sql, new RowMapper<JobDivaConnection>() {
			
			@Override
			public JobDivaConnection mapRow(ResultSet rs, int rowNum) throws SQLException {
				JobDivaConnection jobDivaConnection = new JobDivaConnection();
				//
				jobDivaConnection.setConnectionString(rs.getString("CONNECT_STRING"));
				jobDivaConnection.setConnectionThin(rs.getString("CONNECT_THIN"));
				jobDivaConnection.setUserName(rs.getString("CONNECT_USER"));
				jobDivaConnection.setPasword(rs.getString("CONNECT_PASSWORD"));
				jobDivaConnection.setMainServlet(rs.getString("MAIN_SERVLET"));
				jobDivaConnection.setWaferServlet(rs.getString("WAFER_SERVLET"));
				jobDivaConnection.setWaferBackupServlet(rs.getString("WAFER_BACKUP_SERVLET"));
				jobDivaConnection.setAttributeServlet(rs.getString("ATTRIBUTE_SERVLET"));
				jobDivaConnection.setEnvironmentType(rs.getString("ENVIRONMENT_TYPE"));
				jobDivaConnection.setApacheLocation(rs.getString("APACHE_LOCATION"));
				jobDivaConnection.setUniversalServlet(rs.getString("UNIVERSALSEARCH_SERVLET"));
				jobDivaConnection.setFlexStaffingServlet(rs.getString("FLEXSTAFFING_SERVLET"));
				//
				Long mainDbId = rs.getLong("ID");
				//
				assignTeamIds(mainDbId, jobDivaConnection);
				//
				return jobDivaConnection;
			}
		});
		//
		initJdbcTemplates();
		//
		//
		logger.info("DataBase Connectivity Done.");
		//
	}
	
	protected void assignTeamIds(Long mainDbId, JobDivaConnection jobDivaConnection) {
		//
		//
		String sql = "SELECT TEAMID " //
				+ " FROM TMAINDB_TEAMID " //
				+ " WHERE MAINDBID = ? ";
		//
		Object[] params = new Object[] { mainDbId };
		//
		List<Long> teamIds = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("TEAMID");
			}
		});
		//
		jobDivaConnection.setTeamIds(teamIds);
	}
	
	//
	private void initJdbcTemplates() {
		//
		logger.info("Init JDBC Connectivity");
		//
		for (JobDivaConnection jobDivaConnection : divaConnections) {
			//
			JdbcTemplate jdbcTemplate = createJdbcTemplate(jobDivaConnection);
			//
			jdbcsTemplates.add(jdbcTemplate);
			//
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
			//
			for (Long teamId : jobDivaConnection.getTeamIds()) {
				jobDivaConnections.put(teamId, jobDivaConnection);
				jdbcTemplates.put(teamId, jdbcTemplate);
				namedParameterJdbcTemplates.put(teamId, namedParameterJdbcTemplate);
			}
		}
		//
		//
		logger.info("JDBC Connectivity Done.");
		//
	}
	
	private JdbcTemplate createJdbcTemplate(JobDivaConnection jobDivaConnection) {
		//
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("oracle.jdbc.OracleDriver");
		dataSourceBuilder.url("jdbc:oracle:thin:@" + jobDivaConnection.getConnectionThin());
		dataSourceBuilder.username(jobDivaConnection.getUserName());
		dataSourceBuilder.password(jobDivaConnection.getPasword());
		DataSource dataSource = dataSourceBuilder.build();
		//
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		//
		jdbcTemplate.query("SELECT 1 FROM DUAL ", new RowMapper<Integer>() {
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		});
		//
		return jdbcTemplate;
	}
	
	public JdbcTemplate getJdbcTemplate(Long teamId) {
		return jdbcTemplates.get(teamId);
	}
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(Long teamId) {
		return namedParameterJdbcTemplates.get(teamId);
	}
	
	public JobDivaConnection getJobDivaConnection(Long teamId) {
		return jobDivaConnections.get(teamId);
	}
	
	/**
	 * @return the jdbcTemplates
	 */
	public Map<Long, JdbcTemplate> getJdbcTemplates() {
		return jdbcTemplates;
	}
	
	/**
	 * @return the jdbcsTemplates
	 */
	public List<JdbcTemplate> getJdbcsTemplates() {
		return jdbcsTemplates;
	}
}

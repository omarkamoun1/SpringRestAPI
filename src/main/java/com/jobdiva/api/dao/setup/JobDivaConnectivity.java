package com.jobdiva.api.dao.setup;

import java.io.File;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import com.jobdiva.api.config.AppProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class JobDivaConnectivity {
	
	//
	protected final Logger							logger							= LoggerFactory.getLogger(this.getClass());
	//
	@Autowired
	ServletContext									context;
	//
	@Autowired
	JdbcTemplate									jdbcTemplate;
	//
	@Autowired
	AppProperties									appProperties;
	//
	List<JobDivaConnection>							divaConnections					= new ArrayList<JobDivaConnection>();
	//
	List<JdbcTemplate>								jdbcsTemplates					= Collections.synchronizedList(new ArrayList<JdbcTemplate>());
	Map<Long, JobDivaConnection>					jobDivaConnections				= Collections.synchronizedMap(new HashMap<Long, JobDivaConnection>());
	Map<Long, JdbcTemplate>							jdbcTemplates					= Collections.synchronizedMap(new HashMap<Long, JdbcTemplate>());
	Map<Long, NamedParameterJdbcTemplate>			namedParameterJdbcTemplates		= Collections.synchronizedMap(new HashMap<Long, NamedParameterJdbcTemplate>());
	//
	Map<JdbcTemplate, DataSourceTransactionManager>	dataSourceTransactionManagerMap	= Collections.synchronizedMap(new HashMap<JdbcTemplate, DataSourceTransactionManager>());
	//
	private JdbcTemplate							minerJdbcTemplate;
	private JdbcTemplate							attachmentJdbcTemplate;
	private Object									syncObj							= new Object();
	private Object									syncTransObj					= new Object();
	//
	private Map<String, String>						countries						= new HashMap<String, String>();
	// private Map<String, String> states = new HashMap<String, String>();
	// private Map<String, String> stateNames = new HashMap<String, String>();
	// private Map<String, String> statesWithoutCountry = new HashMap<String,
	// String>();
	
	//
	@PostConstruct
	public void init() {
		//
		try {
			String directory = Paths.get("").toAbsolutePath().toString() + File.separator;
			PropertyConfigurator.configure(directory + "log4j.properties");
		} catch (Exception e) {
			logger.info("Init Error : " + e.getMessage());
		}
		//
		//
		//
		System.getenv().forEach((k, v) -> this.logger.info("SYSTEM ENVIRONMENTS ==> " + k + ":" + v));
		//
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
		logger.info(((HikariDataSource) jdbcTemplate.getDataSource()).getJdbcUrl());
		//
		divaConnections = jdbcTemplate.query(sql, new RowMapper<JobDivaConnection>() {
			
			@Override
			public JobDivaConnection mapRow(ResultSet rs, int rowNum) throws SQLException {
				JobDivaConnection jobDivaConnection = new JobDivaConnection();
				//
				jobDivaConnection.setId(rs.getInt("ID"));
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
		initMinerJdbcTemplate();
		//
		logger.info("DataBase Connectivity Done.");
		//
		//
		//
		// initStates();
		//
		initCountries();
		//
	}
	// private void initStates() {
	// String sql = "SELECT * FROM tstates ";
	// jdbcTemplate.query(sql, new RowMapper<String>() {
	//
	// @Override
	// public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	// String COUNTRYID = rs.getString("COUNTRYID");
	// String STATE_CODE = rs.getString("STATE_CODE");
	// String STATE_NAME = rs.getString("STATE_NAME");
	// //
	// stateNames.put(STATE_NAME, STATE_CODE);
	// states.put(STATE_CODE + COUNTRYID, STATE_NAME);
	// statesWithoutCountry.put(STATE_CODE, STATE_NAME);
	// return null;
	// }
	// });
	// }
	
	private void initCountries() {
		String sql = "SELECT DISTINCT id,country FROM tCountries ORDER BY COUNTRY ";
		jdbcTemplate.query(sql, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String ID = rs.getString("id");
				String COUNTRY = rs.getString("country");
				COUNTRY = COUNTRY != null ? COUNTRY.toLowerCase() : null;
				//
				countries.put(COUNTRY, ID);
				return null;
			}
		});
	}
	// public Map<String, String> getStates() {
	// return states;
	// }
	//
	// public Map<String, String> getStateNames() {
	// return stateNames;
	// }
	//
	// public Map<String, String> getStatesWithoutCountry() {
	// return statesWithoutCountry;
	// }
	
	public Map<String, String> getCountries() {
		return countries;
	}
	// public String getFullStatName(String stateCode, String countryId) {
	// //
	// if (countryId != null) {
	// return states.get(stateCode + countryId);
	// } else {
	// return statesWithoutCountry.get(stateCode);
	// }
	// }
	
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
			//
			//
			DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
			dataSourceTransactionManagerMap.put(jdbcTemplate, dataSourceTransactionManager);
			//
		}
		//
		//
		logger.info("JDBC Connectivity Done.");
		//
	}
	
	@SuppressWarnings("rawtypes")
	private void initMinerJdbcTemplate() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(appProperties.getAxelonDSMinerDriver());
		dataSourceBuilder.url(appProperties.getAxelonDSMinerSUrl());
		dataSourceBuilder.username(appProperties.getAxelonDSMinerUserName());
		dataSourceBuilder.password(appProperties.getAxelonDSMinerPassword());
		DataSource dataSource = dataSourceBuilder.build();
		minerJdbcTemplate = new JdbcTemplate(dataSource);
		//
		//
		minerJdbcTemplate.query("SELECT 1 ", new RowMapper<Integer>() {
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		});
		//
		logger.info("Miner Jdbc Template Connectivity Done.");
	}
	
	protected JdbcTemplate old__createJdbcTemplate(JobDivaConnection jobDivaConnection) {
		//
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("oracle.jdbc.OracleDriver");
		dataSourceBuilder.url("jdbc:oracle:thin:@" + jobDivaConnection.getConnectionThin());
		dataSourceBuilder.username(jobDivaConnection.getUserName());
		dataSourceBuilder.password(jobDivaConnection.getPasword());
		//
		logger.info("Connecting to jdbc:oracle:thin:@" + jobDivaConnection.getConnectionThin());
		//
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
	
	private HikariConfig hikariConfig(String poolName, String driverClassName, String url, String userName, String password) {
		HikariConfig config = new HikariConfig();
		config.setPoolName(poolName);
		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(url);
		config.setUsername(userName);
		config.setPassword(password);
		config.setMaximumPoolSize(appProperties.getMaximumPoolSize());
		config.setAutoCommit(true);
		config.setConnectionTestQuery("SELECT 1 FROM DUAL");
		// config.setLeakDetectionThreshold(THREE_MINS);
		config.setValidationTimeout(appProperties.getValidationTimeout());
		config.setMaxLifetime(appProperties.getMaxLifetime());
		config.setIdleTimeout(appProperties.getIdleTimeout());
		return config;
	}
	
	private JdbcTemplate createJdbcTemplate(JobDivaConnection jobDivaConnection) {
		//
		String poolName = jobDivaConnection.getEnvironmentType() + "_" + jobDivaConnection.getId();
		String url = "jdbc:oracle:thin:@" + jobDivaConnection.getConnectionThin();
		String password = jobDivaConnection.getPasword();
		String userName = jobDivaConnection.getUserName();
		String driverClassName = "oracle.jdbc.OracleDriver";
		HikariConfig hikariConfig = hikariConfig(poolName, driverClassName, url, userName, password);
		//
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		//
		JdbcTemplate jdbcTemplate = new JdbcTemplate(hikariDataSource);
		//
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
	
	public JdbcTemplate getMinerJdbcTemplate() {
		return minerJdbcTemplate;
	}
	
	public JdbcTemplate getCentralTemplate() {
		return jdbcTemplate;
	}
	
	public DataSource createDataSource(String driverClassName, String url, String userName, String password) {
		DataSourceBuilder<? extends DataSource> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(driverClassName);
		dataSourceBuilder.url(url);
		dataSourceBuilder.username(userName);
		dataSourceBuilder.password(password);
		return dataSourceBuilder.build();
	}
	
	public JdbcTemplate getAttachmentJdbcTemplate() {
		synchronized (syncObj) {
			if (attachmentJdbcTemplate == null) {
				DataSource dataSource = createDataSource(appProperties.getAttachmentdbDriver(), appProperties.getAttachmentdbUrl(), appProperties.getAttachmentdbUserName(), appProperties.getAttachmentdbPassword());
				attachmentJdbcTemplate = new JdbcTemplate(dataSource);
			}
		}
		return attachmentJdbcTemplate;
	}
	
	public DataSourceTransactionManager getDataSourceTransactionManager(JdbcTemplate jdbcTemplate) {
		synchronized (syncTransObj) {
			//
			DataSourceTransactionManager dataSourceTransactionManager = dataSourceTransactionManagerMap.get(jdbcTemplate);
			//
			if (dataSourceTransactionManager == null) {
				dataSourceTransactionManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
				dataSourceTransactionManagerMap.put(jdbcTemplate, dataSourceTransactionManager);
			}
			//
			return dataSourceTransactionManager;
		}
	}
}

package com.jobdiva.api.dao.setup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.jobdiva.api.config.AppProperties;

//@Configuration
//@Component
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.jobdiva.api.service", entityManagerFactoryRef = "entityManager", transactionManagerRef = "multiTransactionManager")
public class JobDivaConnectivity2 {
	
	//
	protected final Logger					logger						= LoggerFactory.getLogger(this.getClass());
	//
	// @Autowired
	JdbcTemplate							jdbcTemplate;
	//
	// @Autowired
	AppProperties							appProperties;
	//
	List<JobDivaConnection>					divaConnections				= new ArrayList<JobDivaConnection>();
	//
	List<JdbcTemplate>						jdbcsTemplates				= Collections.synchronizedList(new ArrayList<JdbcTemplate>());
	Map<Long, JobDivaConnection>			jobDivaConnections			= Collections.synchronizedMap(new HashMap<Long, JobDivaConnection>());
	Map<Long, JdbcTemplate>					jdbcTemplates				= Collections.synchronizedMap(new HashMap<Long, JdbcTemplate>());
	Map<Long, NamedParameterJdbcTemplate>	namedParameterJdbcTemplates	= Collections.synchronizedMap(new HashMap<Long, NamedParameterJdbcTemplate>());
	//
	// private JdbcTemplate minerJdbcTemplate;
	// private JdbcTemplate attachmentJdbcTemplate;
	// //
	// private Map<String, DataSource> customDataSources = new HashMap<>();
	// private Object syncObj = new Object();
	//
	// @Primary
	// @Bean(name = "mainDataSource")
	// @ConfigurationProperties("main.spring.datasource")
	// public DataSource mainDataSource() {
	// DataSource dataSource =
	// DataSourceBuilder.create().type(HikariDataSource.class).build();
	// jdbcTemplate = new JdbcTemplate(dataSource);
	// return dataSource;
	// }
	//
	// @Bean(name = "dataSource")
	// public DataSource dataSource() {
	// //
	// init();
	// //
	// //
	// Map<Object, Object> dataSourceMap = new HashMap<>();
	// //
	// for (Map.Entry<String, DataSource> entry : customDataSources.entrySet())
	// {
	// //
	// dataSourceMap.put(entry.getKey(), entry.getValue());
	// //
	// }
	// //
	// MultiRoutingDataSource routingDataSource = new MultiRoutingDataSource();
	// routingDataSource.setDefaultTargetDataSource(jdbcTemplate.getDataSource());
	// routingDataSource.setTargetDataSources(dataSourceMap);
	// return routingDataSource;
	// }
	//
	// @Bean(name = "entityManager")
	// public LocalContainerEntityManagerFactoryBean entityManager() throws
	// PropertyVetoException {
	// LocalContainerEntityManagerFactoryBean entityManagerFactory = new
	// LocalContainerEntityManagerFactoryBean();
	// entityManagerFactory.setDataSource(dataSource());
	// entityManagerFactory.setJpaVendorAdapter(new
	// HibernateJpaVendorAdapter());
	// entityManagerFactory.setPackagesToScan("com.jobdiva.api");
	// entityManagerFactory.setJpaProperties(hibernateProperties());
	// return entityManagerFactory;
	// }
	//
	// @Bean(name = "multiTransactionManager")
	// public PlatformTransactionManager multiTransactionManager() throws
	// PropertyVetoException {
	// JpaTransactionManager transactionManager = new JpaTransactionManager();
	// transactionManager.setEntityManagerFactory(entityManager().getObject());
	// return transactionManager;
	// }
	//
	// private Properties hibernateProperties() {
	// Properties properties = new Properties();
	// properties.put("hibernate.show_sql", true);
	// properties.put("hibernate.format_sql", true);
	// return properties;
	// }
	//
	// // @PostConstruct
	// public void init() {
	// //
	// //
	// //
	// System.getenv().forEach((k, v) -> this.logger.info("SYSTEM ENVIRONMENTS
	// ==> " + k + ":" + v));
	// //
	// //
	// logger.info("Setup DataBase Connectivity");
	// //
	// String sql = "SELECT ID," //
	// + " CONNECT_STRING, " //
	// + " CONNECT_THIN, " //
	// + " CONNECT_USER, " //
	// + " CONNECT_PASSWORD, " //
	// + " MAIN_SERVLET ," //
	// + " WAFER_SERVLET ," //
	// + " WAFER_BACKUP_SERVLET ," //
	// + " ATTRIBUTE_SERVLET ," //
	// + " ENVIRONMENT_TYPE ," //
	// + " APACHE_LOCATION , " //
	// + " UNIVERSALSEARCH_SERVLET, " //
	// + " FLEXSTAFFING_SERVLET " //
	// + " FROM TMAINDBS ";
	// //
	// logger.info(((HikariDataSource)
	// jdbcTemplate.getDataSource()).getJdbcUrl());
	// //
	// divaConnections = jdbcTemplate.query(sql, new
	// RowMapper<JobDivaConnection>() {
	//
	// @Override
	// public JobDivaConnection mapRow(ResultSet rs, int rowNum) throws
	// SQLException {
	// JobDivaConnection jobDivaConnection = new JobDivaConnection();
	// //
	// jobDivaConnection.setId(rs.getInt("ID"));
	// jobDivaConnection.setConnectionString(rs.getString("CONNECT_STRING"));
	// jobDivaConnection.setConnectionThin(rs.getString("CONNECT_THIN"));
	// jobDivaConnection.setUserName(rs.getString("CONNECT_USER"));
	// jobDivaConnection.setPasword(rs.getString("CONNECT_PASSWORD"));
	// jobDivaConnection.setMainServlet(rs.getString("MAIN_SERVLET"));
	// jobDivaConnection.setWaferServlet(rs.getString("WAFER_SERVLET"));
	// jobDivaConnection.setWaferBackupServlet(rs.getString("WAFER_BACKUP_SERVLET"));
	// jobDivaConnection.setAttributeServlet(rs.getString("ATTRIBUTE_SERVLET"));
	// jobDivaConnection.setEnvironmentType(rs.getString("ENVIRONMENT_TYPE"));
	// jobDivaConnection.setApacheLocation(rs.getString("APACHE_LOCATION"));
	// jobDivaConnection.setUniversalServlet(rs.getString("UNIVERSALSEARCH_SERVLET"));
	// jobDivaConnection.setFlexStaffingServlet(rs.getString("FLEXSTAFFING_SERVLET"));
	// //
	// Long mainDbId = rs.getLong("ID");
	// //
	// assignTeamIds(mainDbId, jobDivaConnection);
	// //
	// return jobDivaConnection;
	// }
	// });
	// //
	// initJdbcTemplates();
	// //
	// initMinerJdbcTemplate();
	// //
	// logger.info("DataBase Connectivity Done.");
	// //
	// }
	//
	// protected void assignTeamIds(Long mainDbId, JobDivaConnection
	// jobDivaConnection) {
	// //
	// //
	// String sql = "SELECT TEAMID " //
	// + " FROM TMAINDB_TEAMID " //
	// + " WHERE MAINDBID = ? ";
	// //
	// Object[] params = new Object[] { mainDbId };
	// //
	// List<Long> teamIds = jdbcTemplate.query(sql, params, new
	// RowMapper<Long>() {
	//
	// @Override
	// public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
	// return rs.getLong("TEAMID");
	// }
	// });
	// //
	// jobDivaConnection.setTeamIds(teamIds);
	// }
	//
	// //
	// private void initJdbcTemplates() {
	// //
	// logger.info("Init JDBC Connectivity");
	// //
	// for (JobDivaConnection jobDivaConnection : divaConnections) {
	// //
	// JdbcTemplate jdbcTemplate = createJdbcTemplate(jobDivaConnection);
	// //
	// jdbcsTemplates.add(jdbcTemplate);
	// //
	// NamedParameterJdbcTemplate namedParameterJdbcTemplate = new
	// NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
	// //
	// for (Long teamId : jobDivaConnection.getTeamIds()) {
	// jobDivaConnections.put(teamId, jobDivaConnection);
	// jdbcTemplates.put(teamId, jdbcTemplate);
	// namedParameterJdbcTemplates.put(teamId, namedParameterJdbcTemplate);
	// }
	// }
	// //
	// //
	// logger.info("JDBC Connectivity Done.");
	// //
	// }
	//
	// @SuppressWarnings("rawtypes")
	// private void initMinerJdbcTemplate() {
	// DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
	// dataSourceBuilder.driverClassName(appProperties.getAxelonDSMinerDriver());
	// dataSourceBuilder.url(appProperties.getAxelonDSMinerSUrl());
	// dataSourceBuilder.username(appProperties.getAxelonDSMinerUserName());
	// dataSourceBuilder.password(appProperties.getAxelonDSMinerPassword());
	// DataSource dataSource = dataSourceBuilder.build();
	// minerJdbcTemplate = new JdbcTemplate(dataSource);
	// //
	// //
	// minerJdbcTemplate.query("SELECT 1 ", new RowMapper<Integer>() {
	//
	// @Override
	// public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
	// return rs.getInt(1);
	// }
	// });
	// //
	// logger.info("Miner Jdbc Template Connectivity Done.");
	// }
	//
	// protected JdbcTemplate __createJdbcTemplate(JobDivaConnection
	// jobDivaConnection) {
	// //
	// String poolName = jobDivaConnection.getEnvironmentType() + "_" +
	// jobDivaConnection.getId();
	// //
	// DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
	// dataSourceBuilder.driverClassName("oracle.jdbc.OracleDriver");
	// dataSourceBuilder.url("jdbc:oracle:thin:@" +
	// jobDivaConnection.getConnectionThin());
	// dataSourceBuilder.username(jobDivaConnection.getUserName());
	// dataSourceBuilder.password(jobDivaConnection.getPasword());
	// //
	// logger.info("Connecting to jdbc:oracle:thin:@" +
	// jobDivaConnection.getConnectionThin());
	// //
	// DataSource dataSource = dataSourceBuilder.build();
	// //
	// JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	// //
	// jdbcTemplate.query("SELECT 1 FROM DUAL ", new RowMapper<Integer>() {
	//
	// @Override
	// public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
	// return rs.getInt(1);
	// }
	// });
	// //
	// customDataSources.put(poolName, dataSource);
	// //
	// return jdbcTemplate;
	// }
	//
	// private HikariConfig hikariConfig(String poolName, String
	// driverClassName, String url, String userName, String password) {
	// HikariConfig config = new HikariConfig();
	// config.setPoolName(poolName);
	// config.setDriverClassName(driverClassName);
	// config.setJdbcUrl(url);
	// config.setUsername(userName);
	// config.setPassword(password);
	// config.setMaximumPoolSize(appProperties.getMaximumPoolSize());
	// // config.setAutoCommit(true);
	// config.setConnectionTestQuery("SELECT 1 FROM DUAL");
	// // config.setLeakDetectionThreshold(THREE_MINS);
	// config.setValidationTimeout(appProperties.getValidationTimeout());
	// config.setMaxLifetime(appProperties.getMaxLifetime());
	// config.setIdleTimeout(appProperties.getIdleTimeout());
	// return config;
	// }
	//
	// private JdbcTemplate createJdbcTemplate(JobDivaConnection
	// jobDivaConnection) {
	// //
	// String poolName = jobDivaConnection.getEnvironmentType() + "_" +
	// jobDivaConnection.getId();
	// String url = "jdbc:oracle:thin:@" +
	// jobDivaConnection.getConnectionThin();
	// String password = jobDivaConnection.getPasword();
	// String userName = jobDivaConnection.getUserName();
	// String driverClassName = "oracle.jdbc.OracleDriver";
	// HikariConfig hikariConfig = hikariConfig(poolName, driverClassName, url,
	// userName, password);
	// //
	// HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
	// //
	// JdbcTemplate jdbcTemplate = new JdbcTemplate(hikariDataSource);
	// //
	// customDataSources.put(poolName, hikariDataSource);
	// //
	// return jdbcTemplate;
	// }
	//
	// public JdbcTemplate getJdbcTemplate(Long teamId) {
	// return jdbcTemplates.get(teamId);
	// }
	//
	// public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(Long
	// teamId) {
	// return namedParameterJdbcTemplates.get(teamId);
	// }
	//
	// public JobDivaConnection getJobDivaConnection(Long teamId) {
	// return jobDivaConnections.get(teamId);
	// }
	//
	// /**
	// * @return the jdbcTemplates
	// */
	// public Map<Long, JdbcTemplate> getJdbcTemplates() {
	// return jdbcTemplates;
	// }
	//
	// /**
	// * @return the jdbcsTemplates
	// */
	// public List<JdbcTemplate> getJdbcsTemplates() {
	// return jdbcsTemplates;
	// }
	//
	// public JdbcTemplate getMinerJdbcTemplate() {
	// return minerJdbcTemplate;
	// }
	//
	// public JdbcTemplate getCentralTemplate() {
	// return jdbcTemplate;
	// }
	//
	// public DataSource createDataSource(String driverClassName, String url,
	// String userName, String password) {
	// DataSourceBuilder<? extends DataSource> dataSourceBuilder =
	// DataSourceBuilder.create();
	// dataSourceBuilder.driverClassName(driverClassName);
	// dataSourceBuilder.url(url);
	// dataSourceBuilder.username(userName);
	// dataSourceBuilder.password(password);
	// return dataSourceBuilder.build();
	// }
	//
	// public JdbcTemplate getAttachmentJdbcTemplate() {
	// synchronized (syncObj) {
	// if (attachmentJdbcTemplate == null) {
	// DataSource dataSource =
	// createDataSource(appProperties.getAttachmentdbDriver(),
	// appProperties.getAttachmentdbUrl(),
	// appProperties.getAttachmentdbUserName(),
	// appProperties.getAttachmentdbPassword());
	// attachmentJdbcTemplate = new JdbcTemplate(dataSource);
	// }
	// }
	// return attachmentJdbcTemplate;
	// }
}

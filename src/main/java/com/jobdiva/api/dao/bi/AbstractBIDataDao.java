package com.jobdiva.api.dao.bi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.setup.JobDivaConnectivity;

public class AbstractBIDataDao extends AbstractJobDivaDao {
	
	@Autowired
	JobDivaConnectivity				jobDivaConnectivity;
	//
	//
	private Map<String, DataSource>	dataSources	= new HashMap<String, DataSource>();
	private Object					syncObj		= new Object();
	
	@SuppressWarnings("rawtypes")
	public DataSource createDataSource(String driverClassName, String url, String userName, String password) {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(driverClassName);
		dataSourceBuilder.url(url);
		dataSourceBuilder.username(userName);
		dataSourceBuilder.password(password);
		return dataSourceBuilder.build();
	}
	
	public Connection getConnection(String driverClassName, String url, String userName, String password) throws SQLException {
		String key = driverClassName + url + userName + password;
		synchronized (syncObj) {
			//
			DataSource dataSource = dataSources.get(key);
			//
			try {
				//
				if (dataSource == null) {
					dataSource = createDataSource(driverClassName, url, userName, password);
					dataSources.put(key, dataSource);
				}
				//
				Connection connection = dataSource.getConnection();
				//
				connection.setAutoCommit(false);
				//
				return connection;
			} catch (Exception e) {
				dataSources.remove(key);
				throw new SQLException(e.getMessage());
			}
		}
	}
	
	// public Connection getCentralDbConnection() throws Exception {
	// JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
	// Connection connection = jdbcTemplate.getDataSource().getConnection();
	// connection.setAutoCommit(false);
	// return connection;
	// }
	public Connection getMainDbConnection_teamID(long teamId) throws Exception {
		JdbcTemplate jdbcTemplate = jobDivaConnectivity.getJdbcTemplate(teamId);
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		connection.setAutoCommit(false);
		return connection;
	}
	
	public Connection getClickDbConnection() throws Exception {
		return getConnection(appProperties.getMysqldbDriver(), appProperties.getMysqldbUrl(), appProperties.getMysqldbUserName(), appProperties.getMysqldbPassword());
	}
	
	public Connection getMinerDbConnection() throws Exception {
		return getConnection(appProperties.getAxelonDSMinerDriver(), appProperties.getAxelonDSMinerSUrl(), appProperties.getAxelonDSMinerUserName(), appProperties.getAxelonDSMinerPassword());
	}
	
	public Connection getDocDbConnection(int doc_db_id) throws Exception {
		if (doc_db_id == 1 || doc_db_id == 2) {
			return getConnection(appProperties.getDocDS1Driver(), appProperties.getDocDS1Url(), appProperties.getDocDS1UserName(), appProperties.getDocDS1Password());
		} else if (doc_db_id == 3) {
			return getConnection(appProperties.getDocDS3Driver(), appProperties.getDocDS3Url(), appProperties.getDocDS3UserName(), appProperties.getDocDS3Password());
		} else if (doc_db_id == 4) {
			return getConnection(appProperties.getDocDS4Driver(), appProperties.getDocDS4Url(), appProperties.getDocDS4UserName(), appProperties.getDocDS4Password());
		} else if (doc_db_id == 5) {
			return getConnection(appProperties.getDocDS5Driver(), appProperties.getDocDS5Url(), appProperties.getDocDS5UserName(), appProperties.getDocDS5Password());
		}
		throw new Exception("Cannot reach Docuemnt DataBase : " + doc_db_id);
	}
	
	public Connection getEmailSaveDbConnection() throws Exception {
		return getConnection(appProperties.getEmailsavedbDriver(), appProperties.getEmailsavedbUrl(), appProperties.getEmailsavedbUserName(), appProperties.getEmailsavedbPassword());
	}
	
	public Connection getEmailSave2DbConnection() throws Exception {
		return getConnection(appProperties.getEmailsave2dbDriver(), appProperties.getEmailsave2dbUrl(), appProperties.getEmailsave2dbUserName(), appProperties.getEmailsave2dbPassword());
	}
	
	public Connection getAttachmentDbConnection() throws Exception {
		return getConnection(appProperties.getAttachmentdbDriver(), appProperties.getAttachmentdbUrl(), appProperties.getAttachmentdbUserName(), appProperties.getAttachmentdbPassword());
	}
}

package com.jobdiva.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
	
	public final static String	UK_ENVIRONEMNT					= "uk";
	//
	@Value("${spring.datasource.maximum.pool.size}")
	private Integer				maximumPoolSize					= 20;
	//
	@Value("${spring.datasource.validation.timeout}")
	private Integer				validationTimeout				= 5000;
	//
	@Value("${spring.datasource.max.lifetime}")
	private Long				maxLifetime						= 1800000L;
	//
	@Value("${spring.datasource.idle.timeout}")
	private Long				idleTimeout						= 600000L;
	//
	@Value("${servlet.bidata.ip}")
	private String				servletBiDataIp;
	//
	@Value("${servlet.date.format}")
	private String				servletDateFormat;
	//
	//
	@Value("${servlet.jdhibernate}")
	private String				jdHibernateServlet;
	//
	@Value("${app.ws.location}")
	private String				appWsLocation;
	//
	@Value("${load.balance.servlet.location}")
	private String				loadBalanceServletLocation;
	//
	// @Value("{jobdiva.environment.production}")
	private Boolean				jobDivaEnvironmentProduction	= false;
	//
	@Value("${smtp.server.location}")
	private String				smtpServerLocation;
	//
	@Value("${apache.location}")
	private String				apacheLocation;
	//
	//
	//
	@Value("${MainDS1.datasource.driver}")
	private String				mainDS1Driver;
	//
	@Value("${MainDS1.datasource.url}")
	private String				mainDS1Url;
	//
	@Value("${MainDS1.datasource.username}")
	private String				mainDS1UserName;
	//
	@Value("${MainDS1.datasource.password}")
	private String				mainDS1Password;
	//
	//
	//
	@Value("${MainDS2.datasource.driver}")
	private String				mainDS2Driver;
	//
	@Value("${MainDS2.datasource.url}")
	private String				mainDS2Url;
	//
	@Value("${MainDS2.datasource.username}")
	private String				mainDS2UserName;
	//
	@Value("${MainDS2.datasource.password}")
	private String				mainDS2Password;
	//
	//
	//
	@Value("${mysqldb.datasource.driver}")
	private String				mysqldbDriver;
	//
	@Value("${mysqldb.datasource.url}")
	private String				mysqldbUrl;
	//
	@Value("${mysqldb.datasource.username}")
	private String				mysqldbUserName;
	//
	@Value("${mysqldb.datasource.password}")
	private String				mysqldbPassword;
	//
	//
	//
	//
	@Value("${AxelonDSMiner.datasource.driver}")
	private String				axelonDSMinerDriver;
	//
	@Value("${AxelonDSMiner.datasource.url}")
	private String				axelonDSMinerSUrl;
	//
	@Value("${AxelonDSMiner.datasource.username}")
	private String				axelonDSMinerUserName;
	//
	@Value("${AxelonDSMiner.datasource.password}")
	private String				axelonDSMinerPassword;
	//
	//
	//
	//
	@Value("${DocDS1.datasource.driver}")
	private String				docDS1Driver;
	//
	@Value("${DocDS1.datasource.url}")
	private String				docDS1Url;
	//
	@Value("${DocDS1.datasource.username}")
	private String				docDS1UserName;
	//
	@Value("${DocDS1.datasource.password}")
	private String				docDS1Password;
	//
	//
	//
	//
	@Value("${DocDS3.datasource.driver}")
	private String				docDS3Driver;
	//
	@Value("${DocDS3.datasource.url}")
	private String				docDS3Url;
	//
	@Value("${DocDS3.datasource.username}")
	private String				docDS3UserName;
	//
	@Value("${DocDS3.datasource.password}")
	private String				docDS3Password;
	//
	//
	@Value("${DocDS4.datasource.driver}")
	private String				docDS4Driver;
	//
	@Value("${DocDS4.datasource.url}")
	private String				docDS4Url;
	//
	@Value("${DocDS4.datasource.username}")
	private String				docDS4UserName;
	//
	@Value("${DocDS4.datasource.password}")
	private String				docDS4Password;
	//
	//
	@Value("${DocDS5.datasource.driver}")
	private String				docDS5Driver;
	//
	@Value("${DocDS5.datasource.url}")
	private String				docDS5Url;
	//
	@Value("${DocDS5.datasource.username}")
	private String				docDS5UserName;
	//
	@Value("${DocDS5.datasource.password}")
	private String				docDS5Password;
	//
	//
	//
	//
	@Value("${emailsavedb.datasource.driver}")
	private String				emailsavedbDriver;
	//
	@Value("${emailsavedb.datasource.url}")
	private String				emailsavedbUrl;
	//
	@Value("${emailsavedb.datasource.username}")
	private String				emailsavedbUserName;
	//
	@Value("${emailsavedb.datasource.password}")
	private String				emailsavedbPassword;
	//
	//
	//
	//
	@Value("${emailsave2db.datasource.driver}")
	private String				emailsave2dbDriver;
	//
	@Value("${emailsave2db.datasource.url}")
	private String				emailsave2dbUrl;
	//
	@Value("${emailsave2db.datasource.username}")
	private String				emailsave2dbUserName;
	//
	@Value("${emailsave2db.datasource.password}")
	private String				emailsave2dbPassword;
	//
	//
	//
	//
	@Value("${attachmentdb.datasource.driver}")
	private String				attachmentdbDriver;
	//
	@Value("${attachmentdb.datasource.url}")
	private String				attachmentdbUrl;
	//
	@Value("${attachmentdb.datasource.username}")
	private String				attachmentdbUserName;
	//
	@Value("${attachmentdb.datasource.password}")
	private String				attachmentdbPassword;
	//
	@Value("${jobserveuk.url}")
	private String				jobserveukUrl;
	//
	@Value("${api.server.environment}")
	private String				apiServerEnvironment;
	
	public Integer getMaximumPoolSize() {
		return maximumPoolSize;
	}
	
	public void setMaximumPoolSize(Integer maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}
	
	public Integer getValidationTimeout() {
		return validationTimeout;
	}
	
	public void setValidationTimeout(Integer validationTimeout) {
		this.validationTimeout = validationTimeout;
	}
	
	public Long getMaxLifetime() {
		return maxLifetime;
	}
	
	public void setMaxLifetime(Long maxLifetime) {
		this.maxLifetime = maxLifetime;
	}
	
	public Long getIdleTimeout() {
		return idleTimeout;
	}
	
	public void setIdleTimeout(Long idleTimeout) {
		this.idleTimeout = idleTimeout;
	}
	
	public String getServletBiDataIp() {
		return servletBiDataIp;
	}
	
	public void setServletBiDataIp(String servletBiDataIp) {
		this.servletBiDataIp = servletBiDataIp;
	}
	
	public String getServletDateFormat() {
		return servletDateFormat;
	}
	
	public void setServletDateFormat(String servletDateFormat) {
		this.servletDateFormat = servletDateFormat;
	}
	
	public String getJdHibernateServlet() {
		return jdHibernateServlet;
	}
	
	public void setJdHibernateServlet(String jdHibernateServlet) {
		this.jdHibernateServlet = jdHibernateServlet;
	}
	
	public String getLoadBalanceServletLocation() {
		return loadBalanceServletLocation;
	}
	
	public void setLoadBalanceServletLocation(String loadBalanceServletLocation) {
		this.loadBalanceServletLocation = loadBalanceServletLocation;
	}
	
	public Boolean getJobDivaEnvironmentProduction() {
		return jobDivaEnvironmentProduction;
	}
	
	public void setJobDivaEnvironmentProduction(Boolean jobDivaEnvironmentProduction) {
		this.jobDivaEnvironmentProduction = jobDivaEnvironmentProduction;
	}
	
	public String getAppWsLocation() {
		return appWsLocation;
	}
	
	public void setAppWsLocation(String appWsLocation) {
		this.appWsLocation = appWsLocation;
	}
	
	public String getMainDS1Driver() {
		return mainDS1Driver;
	}
	
	public void setMainDS1Driver(String mainDS1Driver) {
		this.mainDS1Driver = mainDS1Driver;
	}
	
	public String getMainDS1Url() {
		return mainDS1Url;
	}
	
	public void setMainDS1Url(String mainDS1Url) {
		this.mainDS1Url = mainDS1Url;
	}
	
	public String getMainDS1UserName() {
		return mainDS1UserName;
	}
	
	public void setMainDS1UserName(String mainDS1UserName) {
		this.mainDS1UserName = mainDS1UserName;
	}
	
	public String getMainDS1Password() {
		return mainDS1Password;
	}
	
	public void setMainDS1Password(String mainDS1Password) {
		this.mainDS1Password = mainDS1Password;
	}
	
	public String getMainDS2Driver() {
		return mainDS2Driver;
	}
	
	public void setMainDS2Driver(String mainDS2Driver) {
		this.mainDS2Driver = mainDS2Driver;
	}
	
	public String getMainDS2Url() {
		return mainDS2Url;
	}
	
	public void setMainDS2Url(String mainDS2Url) {
		this.mainDS2Url = mainDS2Url;
	}
	
	public String getMainDS2UserName() {
		return mainDS2UserName;
	}
	
	public void setMainDS2UserName(String mainDS2UserName) {
		this.mainDS2UserName = mainDS2UserName;
	}
	
	public String getMainDS2Password() {
		return mainDS2Password;
	}
	
	public void setMainDS2Password(String mainDS2Password) {
		this.mainDS2Password = mainDS2Password;
	}
	
	public String getMysqldbDriver() {
		return mysqldbDriver;
	}
	
	public void setMysqldbDriver(String mysqldbDriver) {
		this.mysqldbDriver = mysqldbDriver;
	}
	
	public String getMysqldbUrl() {
		return mysqldbUrl;
	}
	
	public void setMysqldbUrl(String mysqldbUrl) {
		this.mysqldbUrl = mysqldbUrl;
	}
	
	public String getMysqldbUserName() {
		return mysqldbUserName;
	}
	
	public void setMysqldbUserName(String mysqldbUserName) {
		this.mysqldbUserName = mysqldbUserName;
	}
	
	public String getMysqldbPassword() {
		return mysqldbPassword;
	}
	
	public void setMysqldbPassword(String mysqldbPassword) {
		this.mysqldbPassword = mysqldbPassword;
	}
	
	public String getAxelonDSMinerDriver() {
		return axelonDSMinerDriver;
	}
	
	public void setAxelonDSMinerDriver(String axelonDSMinerDriver) {
		this.axelonDSMinerDriver = axelonDSMinerDriver;
	}
	
	public String getAxelonDSMinerSUrl() {
		return axelonDSMinerSUrl;
	}
	
	public void setAxelonDSMinerSUrl(String axelonDSMinerSUrl) {
		this.axelonDSMinerSUrl = axelonDSMinerSUrl;
	}
	
	public String getAxelonDSMinerUserName() {
		return axelonDSMinerUserName;
	}
	
	public void setAxelonDSMinerUserName(String axelonDSMinerUserName) {
		this.axelonDSMinerUserName = axelonDSMinerUserName;
	}
	
	public String getAxelonDSMinerPassword() {
		return axelonDSMinerPassword;
	}
	
	public void setAxelonDSMinerPassword(String axelonDSMinerPassword) {
		this.axelonDSMinerPassword = axelonDSMinerPassword;
	}
	
	public String getDocDS1Driver() {
		return docDS1Driver;
	}
	
	public void setDocDS1Driver(String docDS1Driver) {
		this.docDS1Driver = docDS1Driver;
	}
	
	public String getDocDS1Url() {
		return docDS1Url;
	}
	
	public void setDocDS1Url(String docDS1Url) {
		this.docDS1Url = docDS1Url;
	}
	
	public String getDocDS1UserName() {
		return docDS1UserName;
	}
	
	public void setDocDS1UserName(String docDS1UserName) {
		this.docDS1UserName = docDS1UserName;
	}
	
	public String getDocDS1Password() {
		return docDS1Password;
	}
	
	public void setDocDS1Password(String docDS1Password) {
		this.docDS1Password = docDS1Password;
	}
	
	public String getDocDS3Driver() {
		return docDS3Driver;
	}
	
	public void setDocDS3Driver(String docDS3Driver) {
		this.docDS3Driver = docDS3Driver;
	}
	
	public String getDocDS3Url() {
		return docDS3Url;
	}
	
	public void setDocDS3Url(String docDS3Url) {
		this.docDS3Url = docDS3Url;
	}
	
	public String getDocDS3UserName() {
		return docDS3UserName;
	}
	
	public void setDocDS3UserName(String docDS3UserName) {
		this.docDS3UserName = docDS3UserName;
	}
	
	public String getDocDS3Password() {
		return docDS3Password;
	}
	
	public void setDocDS3Password(String docDS3Password) {
		this.docDS3Password = docDS3Password;
	}
	
	public String getDocDS4Driver() {
		return docDS4Driver;
	}
	
	public void setDocDS4Driver(String docDS4Driver) {
		this.docDS4Driver = docDS4Driver;
	}
	
	public String getDocDS4Url() {
		return docDS4Url;
	}
	
	public void setDocDS4Url(String docDS4Url) {
		this.docDS4Url = docDS4Url;
	}
	
	public String getDocDS4UserName() {
		return docDS4UserName;
	}
	
	public void setDocDS4UserName(String docDS4UserName) {
		this.docDS4UserName = docDS4UserName;
	}
	
	public String getDocDS4Password() {
		return docDS4Password;
	}
	
	public void setDocDS4Password(String docDS4Password) {
		this.docDS4Password = docDS4Password;
	}
	
	public String getDocDS5Driver() {
		return docDS5Driver;
	}
	
	public void setDocDS5Driver(String docDS5Driver) {
		this.docDS5Driver = docDS5Driver;
	}
	
	public String getDocDS5Url() {
		return docDS5Url;
	}
	
	public void setDocDS5Url(String docDS5Url) {
		this.docDS5Url = docDS5Url;
	}
	
	public String getDocDS5UserName() {
		return docDS5UserName;
	}
	
	public void setDocDS5UserName(String docDS5UserName) {
		this.docDS5UserName = docDS5UserName;
	}
	
	public String getDocDS5Password() {
		return docDS5Password;
	}
	
	public void setDocDS5Password(String docDS5Password) {
		this.docDS5Password = docDS5Password;
	}
	
	public String getEmailsavedbDriver() {
		return emailsavedbDriver;
	}
	
	public void setEmailsavedbDriver(String emailsavedbDriver) {
		this.emailsavedbDriver = emailsavedbDriver;
	}
	
	public String getEmailsavedbUrl() {
		return emailsavedbUrl;
	}
	
	public void setEmailsavedbUrl(String emailsavedbUrl) {
		this.emailsavedbUrl = emailsavedbUrl;
	}
	
	public String getEmailsavedbUserName() {
		return emailsavedbUserName;
	}
	
	public void setEmailsavedbUserName(String emailsavedbUserName) {
		this.emailsavedbUserName = emailsavedbUserName;
	}
	
	public String getEmailsavedbPassword() {
		return emailsavedbPassword;
	}
	
	public void setEmailsavedbPassword(String emailsavedbPassword) {
		this.emailsavedbPassword = emailsavedbPassword;
	}
	
	public String getEmailsave2dbDriver() {
		return emailsave2dbDriver;
	}
	
	public void setEmailsave2dbDriver(String emailsave2dbDriver) {
		this.emailsave2dbDriver = emailsave2dbDriver;
	}
	
	public String getEmailsave2dbUrl() {
		return emailsave2dbUrl;
	}
	
	public void setEmailsave2dbUrl(String emailsave2dbUrl) {
		this.emailsave2dbUrl = emailsave2dbUrl;
	}
	
	public String getEmailsave2dbUserName() {
		return emailsave2dbUserName;
	}
	
	public void setEmailsave2dbUserName(String emailsave2dbUserName) {
		this.emailsave2dbUserName = emailsave2dbUserName;
	}
	
	public String getEmailsave2dbPassword() {
		return emailsave2dbPassword;
	}
	
	public void setEmailsave2dbPassword(String emailsave2dbPassword) {
		this.emailsave2dbPassword = emailsave2dbPassword;
	}
	
	public String getAttachmentdbDriver() {
		return attachmentdbDriver;
	}
	
	public void setAttachmentdbDriver(String attachmentdbDriver) {
		this.attachmentdbDriver = attachmentdbDriver;
	}
	
	public String getAttachmentdbUrl() {
		return attachmentdbUrl;
	}
	
	public void setAttachmentdbUrl(String attachmentdbUrl) {
		this.attachmentdbUrl = attachmentdbUrl;
	}
	
	public String getAttachmentdbUserName() {
		return attachmentdbUserName;
	}
	
	public void setAttachmentdbUserName(String attachmentdbUserName) {
		this.attachmentdbUserName = attachmentdbUserName;
	}
	
	public String getAttachmentdbPassword() {
		return attachmentdbPassword;
	}
	
	public void setAttachmentdbPassword(String attachmentdbPassword) {
		this.attachmentdbPassword = attachmentdbPassword;
	}
	
	public String getSmtpServerLocation() {
		return smtpServerLocation;
	}
	
	public void setSmtpServerLocation(String smtpServerLocation) {
		this.smtpServerLocation = smtpServerLocation;
	}
	
	public String getApacheLocation() {
		return this.apacheLocation;
	}
	
	public void setApacheLocation(String apacheLocation) {
		this.apacheLocation = apacheLocation;
	}
	
	public String getJobserveukUrl() {
		return this.jobserveukUrl;
	}
	
	public String getApiServerEnvironment() {
		return apiServerEnvironment;
	}
	
	public void setApiServerEnvironment(String apiServerEnvironment) {
		this.apiServerEnvironment = apiServerEnvironment;
	}
}

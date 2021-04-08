package com.jobdiva.api.dao.webhook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.arizon.shared.Encryption;
import com.eclipsesource.json.JsonObject;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.webhook.WebhookInfo;
import com.jobdiva.api.model.webhook.WebhookRequest;
import com.jobdiva.api.model.webhook.WebhookSyncType;

@Component
public class WebhookDao extends AbstractJobDivaDao {
	
	static final String				HMAC_SHA1		= "HmacSHA1";
	static final String				JOBDIVA_SECRET	= "JobDiva2021WebHook";
	//
	@Autowired
	WebhookRequestDataDao			webhookRequestDataDao;
	//
	private Map<Long, WebhookInfo>	webHooksMap		= Collections.synchronizedMap(new HashMap<Long, WebhookInfo>());
	//
	private Object					syncObj			= new Object();
	//
	
	@PostConstruct
	private void init() {
		refresh(null);
	}
	
	private void refresh(Long teamId) {
		synchronized (this.syncObj) {
			if (teamId != null) {
				this.webHooksMap.remove(teamId);
				WebhookInfo webhookInfo = getWebhookConfiguration(teamId);
				if (webhookInfo != null)
					this.webHooksMap.put(teamId, webhookInfo);
			} else {
				this.webHooksMap.clear();
				for (JdbcTemplate jdbcTemplate : this.jobDivaConnectivity.getJdbcsTemplates()) {
					List<WebhookInfo> webhookConfigurations = getWebhookConfiguration(jdbcTemplate);
					for (WebhookInfo webhookInfo : webhookConfigurations)
						this.webHooksMap.put(webhookInfo.getTeamId(), webhookInfo);
				}
			}
		}
	}
	
	protected String calcHmacSha256(String clientSecret, String payload) throws Exception {
		//
		Mac mac = Mac.getInstance("HmacSHA1");
		//
		SecretKeySpec signingKey = new SecretKeySpec(clientSecret.getBytes(), "HmacSHA1");
		mac.init(signingKey);
		//
		char[] hash = Hex.encodeHex(mac.doFinal(payload.getBytes()));
		//
		StringBuilder builder = new StringBuilder("sha1=");
		builder.append(hash);
		String expected = builder.toString();
		//
		return expected;
	}
	
	private String encryptClientSecret(String clientSecret) {
		return Encryption.encrypt(clientSecret);
	}
	
	private String decryptClientSecret(String clientSecret) {
		return Encryption.decrypt(clientSecret);
	}
	
	public List<WebhookInfo> getWebhookConfiguration(JdbcTemplate jdbcTemplate) {
		//
		String sql = getWebHookSQL();
		//
		List<WebhookInfo> list = jdbcTemplate.query(sql, new RowMapper<WebhookInfo>() {
			
			@Override
			public WebhookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				// WebhookInfo
				WebhookInfo webhookInfo = mapWebhookResultset(rs);
				//
				return webhookInfo;
			}
		});
		return list;
	}
	
	private String getWebHookSQL() {
		String sql = "SELECT ID, TEAMID, CLIENT_SECRET, CLIENT_URL, ACTIVE, " //
				+ " ENABLE_JOB , ENABLE_CANDIDATE, ENABLE_COMPANY, ENABLE_CONTACT,  ENABLE_BILLING,  ENABLE_SALARY " //
				+ " FROM TWEBHOOK_CONFIGURATION ";//
		return sql;
	}
	
	private WebhookInfo mapWebhookResultset(ResultSet rs) throws SQLException {
		WebhookInfo webhookInfo = new WebhookInfo();
		webhookInfo.setId(rs.getLong("ID"));
		webhookInfo.setTeamId(rs.getLong("TEAMID"));
		webhookInfo.setClientUrl(rs.getString("CLIENT_URL"));
		webhookInfo.setClientSecret(decryptClientSecret(rs.getString("CLIENT_SECRET")));
		webhookInfo.setActive(rs.getBoolean("ACTIVE"));
		//
		webhookInfo.setEnableJob(rs.getBoolean("ENABLE_JOB"));
		webhookInfo.setEnableCandidate(rs.getBoolean("ENABLE_CANDIDATE"));
		webhookInfo.setEnableCompany(rs.getBoolean("ENABLE_COMPANY"));
		webhookInfo.setEnableContact(rs.getBoolean("ENABLE_CONTACT"));
		webhookInfo.setEnableBilling(rs.getBoolean("ENABLE_BILLING"));
		webhookInfo.setEnableSalary(rs.getBoolean("ENABLE_SALARY"));
		return webhookInfo;
	}
	
	public WebhookInfo getWebhookConfiguration(Long teamId) {
		JdbcTemplate jdbcTemplate = this.jobDivaConnectivity.getJdbcTemplate(teamId);
		String sql = getWebHookSQL() //
				+ " where TEAMID = ? ";
		Object[] params = { teamId };
		List<WebhookInfo> list = jdbcTemplate.query(sql, params, new RowMapper<WebhookInfo>() {
			
			@Override
			public WebhookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				// WebhookInfo
				WebhookInfo webhookInfo = mapWebhookResultset(rs);
				//
				return webhookInfo;
			}
		});
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}
	
	/**
	 * @param webhookRequest
	 */
	private void afterCompletedWebhookRequest(WebhookRequest webhookRequest) {
		//
		if (webhookRequest.getFaultId() != null) {
			String sqlDelete = "DELETE FROM TWEBHOOK_FAILEDREQUEST WHERE ID = ? AND TEAMID = ? ";
			JdbcTemplate jdbcTemplate = this.jobDivaConnectivity.getJdbcTemplate(webhookRequest.getTeamId());
			Object[] params = new Object[] { webhookRequest.getFaultId(), webhookRequest.getTeamId() };
			//
			jdbcTemplate.update(sqlDelete, params);
		}
	}
	
	private void logFailedRequest(WebhookRequest webhookRequest) {
		JdbcTemplate jdbcTemplate = this.jobDivaConnectivity.getJdbcTemplate(webhookRequest.getTeamId());
		if (webhookRequest.getFaultId() == null) {
			String sqlInsert = "INSERT INTO TWEBHOOK_FAILEDREQUEST (ID, TEAMID, SYNCTYPE, OPERATIONTYPE, DATAID, DATECREATED )" //
					+ " VALUES " //
					+ "(TWEBHOOK_FAILEDREQUEST_SEQ.nextval, ?, ?, ?, ?, sysdate) ";
			//
			Object[] params = new Object[] { webhookRequest.getTeamId(), webhookRequest.getSyncType(), webhookRequest.getOperation(), webhookRequest.getId() };
			//
			jdbcTemplate.update(sqlInsert, params);
			//
		} else {
			String sqlUpdate = "UPDATE TWEBHOOK_FAILEDREQUEST SET INPROGRESS = ? WHERE ID = ? AND  TEAMID = ? ";
			//
			Object[] params = new Object[] { false, webhookRequest.getFaultId(), webhookRequest.getTeamId() };
			//
			jdbcTemplate.update(sqlUpdate, params);
		}
	}
	
	public void send(WebhookRequest webhookRequest, String json, Integer counter) {
		WebhookInfo webhookConfiguration = getWebhookConfiguration(webhookRequest.getTeamId());
		if (webhookConfiguration != null && webhookConfiguration.getActive() != null && webhookConfiguration.getActive()) {
			String clientSecret = webhookConfiguration.getClientSecret();
			String url = webhookConfiguration.getClientUrl();
			//
			// for test
			// url = "http://localhost:88/api/webhook/welcome";
			// clientSecret = "jobdiva";
			//
			try {
				String XHubSignature = calcHmacSha256(clientSecret, json);
				//
				RestTemplate restTemplate = new RestTemplateBuilder(new RestTemplateCustomizer[] { rt -> rt.getInterceptors().add(new ClientHttpRequestInterceptor() {
					
					@Override
					public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
						request.getHeaders().set("X-Hub-Signature", XHubSignature);
						return execution.execute(request, body);
					}
				}) }).build();
				//
				//
				ResponseEntity<Boolean> postForEntity = restTemplate.postForEntity(url, json, Boolean.class, new Object[0]);
				//
				this.logger.info("WEBHOOK SENT :: [" + webhookRequest.getTeamId() + "] [" + url + "]" + json);
				//
				HttpStatus statusCode = postForEntity.getStatusCode();
				//
				if (HttpStatus.OK.equals(statusCode)) {
					//
					afterCompletedWebhookRequest(webhookRequest);
					//
				} else {
					waitAndRetry(webhookRequest, json, counter + 1);
				}
				//
			} catch (Exception e) {
				//
				this.logger.error("WEBHOOK ERROR :: [" + webhookRequest.getTeamId() + "] [" + url + "] [" + json + "] " + e.getMessage());
				//
				waitAndRetry(webhookRequest, json, counter + 1);
				//
			}
		} else {
			//
			this.logger.info("WEBHOOK CONFIG NOT EXISTS :: [" + webhookRequest.getTeamId() + "] ");
			//
		}
		//
	}
	
	private void waitAndRetry(WebhookRequest webhookRequest, String json, Integer counter) {
		//
		if (counter < 5) {
			try {
				Thread.sleep(5 * 1000);
				//
				send(webhookRequest, json, counter);
				//
			} catch (InterruptedException e) {
			}
		} else {
			logFailedRequest(webhookRequest);
		}
		//
	}
	
	public Boolean updateWebhookConfiguration(Long teamId, String clientSecret, String clientUrl, Boolean active) {
		//
		clientSecret = encryptClientSecret(clientSecret);
		//
		JdbcTemplate jdbcTemplate = this.jobDivaConnectivity.getJdbcTemplate(teamId);
		active = (active != null) ? active : false;
		//
		String sql = "update TWEBHOOK_CONFIGURATION SET CLIENT_SECRET = ? , CLIENT_URL = ? , ACTIVE = ? where TEAMID = ? ";
		Object[] params = { clientSecret, clientUrl, active, teamId };
		//
		int updated = jdbcTemplate.update(sql, params);
		//
		if (updated == 0) {
			String sqlInsert = "INSERT INTO TWEBHOOK_CONFIGURATION(ID, TEAMID, CLIENT_SECRET, CLIENT_URL, ACTIVE, ENABLE_JOB , ENABLE_CANDIDATE, ENABLE_COMPANY, ENABLE_CONTACT,  ENABLE_BILLING,  ENABLE_SALARY) " //
					+ " values( TWEBHOOK_CONFIGURATION_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			params = new Object[] { teamId, clientSecret, clientUrl, active, true, true, true, true, true, true, };
			jdbcTemplate.update(sqlInsert, params);
		}
		//
		return true;
	}
	
	public Boolean deleteWebhookConfiguration(Long teamId) {
		//
		JdbcTemplate jdbcTemplate = this.jobDivaConnectivity.getJdbcTemplate(teamId);
		String sql = "DELETE FROM TWEBHOOK_CONFIGURATION where TEAMID = ? ";
		//
		Object[] params = { teamId };
		//
		jdbcTemplate.update(sql, params);
		//
		return true;
	}
	
	private void validParameters(WebhookSyncType webhookSyncType, Integer operation, String id) throws Exception {
		//
		if (WebhookSyncType.NONE.equals(webhookSyncType)) {
			throw new Exception("Invalid SyncType");
		}
		//
		if (operation == null || operation <= 0 || operation > 3) {
			throw new Exception("Invalid operation value");
		}
		//
		if (id == null) {
			throw new Exception("Invalid Id parameter");
		}
		//
	}
	
	// operation = 1 [Insert] / 2 [Update] / 3 [Delete]
	private String getOperation(Integer operation) {
		//
		switch (operation) {
			case 1:
				return "Insert";
			case 2:
				return "Update";
			case 3:
				return "Delete";
		}
		return null;
	}
	
	private boolean enableSyncType(Long teamId, WebhookSyncType webhookSyncType) {
		WebhookInfo webhookInfo = this.webHooksMap.get(teamId);
		if (webhookInfo != null)
			switch (webhookSyncType) {
				case JOB:
					return webhookInfo.getEnableJob();
				case CANDIDATE:
					return webhookInfo.getEnableCandidate();
				case COMPANY:
					return webhookInfo.getEnableCompany();
				case CONTACT:
					return webhookInfo.getEnableContact();
				case BILLING:
					return webhookInfo.getEnableBilling();
				case SALARY:
					return webhookInfo.getEnableSalary();
				default:
					break;
			}
		return false;
	}
	
	public void syncWebhook(WebhookRequest webhookRequest) throws Exception {
		//
		WebhookSyncType webhookSyncType = WebhookSyncType.getWebhookSyncType(webhookRequest.getSyncType());
		//
		if (!enableSyncType(webhookRequest.getTeamId(), webhookSyncType))
			return;
		//
		//
		validParameters(webhookSyncType, webhookRequest.getOperation(), webhookRequest.getId());
		//
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("type", webhookSyncType.getValue());
		jsonObject.add("operation", getOperation(webhookRequest.getOperation()));
		jsonObject.add("id", webhookRequest.getId());
		//
		switch (webhookSyncType) {
			case JOB:
				jsonObject.add("data", webhookRequestDataDao.getJob(webhookRequest.getTeamId(), webhookRequest.getId()));
				break;
			case CANDIDATE:
				jsonObject.add("data", webhookRequestDataDao.getCandidate(webhookRequest.getTeamId(), webhookRequest.getId()));
				break;
			case COMPANY:
				jsonObject.add("data", webhookRequestDataDao.getCompany(webhookRequest.getTeamId(), webhookRequest.getId()));
				break;
			case CONTACT:
				jsonObject.add("data", webhookRequestDataDao.getContact(webhookRequest.getTeamId(), webhookRequest.getId()));
				break;
			case BILLING:
				jsonObject.add("data", webhookRequestDataDao.getBillingRecordDetail(webhookRequest.getTeamId(), webhookRequest.getId()));
				break;
			case SALARY:
				jsonObject.add("data", webhookRequestDataDao.getSalaryRecordDetail(webhookRequest.getTeamId(), webhookRequest.getId()));
				break;
			default:
				break;
		}
		send(webhookRequest, jsonObject.toString(), 0);
		//
	}
}

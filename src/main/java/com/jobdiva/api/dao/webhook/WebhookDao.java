package com.jobdiva.api.dao.webhook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.webhook.WebhookInfo;

@Component
public class WebhookDao extends AbstractJobDivaDao {
	
	static final String HMAC_SHA1 = "HmacSHA1";
	
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
	
	public WebhookInfo getWebhookConfiguration(Long teamId) {
		JdbcTemplate jdbcTemplate = this.jobDivaConnectivity.getJdbcTemplate(teamId);
		String sql = "SELECT ID, CLIENT_SECRET, CLIENT_URL, ACTIVE FROM TWEBHOOK_CONFIGURATION where TEAMID = ? ";
		Object[] params = { teamId };
		List<WebhookInfo> list = jdbcTemplate.query(sql, params, new RowMapper<WebhookInfo>() {
			
			@Override
			public WebhookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				// WebhookInfo
				WebhookInfo webhookInfo = new WebhookInfo();
				webhookInfo.setId(rs.getLong("ID"));
				webhookInfo.setClientUrl(rs.getString("CLIENT_URL"));
				webhookInfo.setClientSecret(rs.getString("CLIENT_SECRET"));
				webhookInfo.setActive(rs.getBoolean("ACTIVE"));
				//
				return webhookInfo;
			}
		});
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}
	
	public Boolean send(Long teamId, String json) {
		WebhookInfo webhookConfiguration = getWebhookConfiguration(teamId);
		if (webhookConfiguration != null && webhookConfiguration.getActive() != null && webhookConfiguration.getActive().booleanValue()) {
			String clientSecret = webhookConfiguration.getClientSecret();
			String url = webhookConfiguration.getClientUrl();
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
				this.logger.error("WEBHOOK SENT :: [" + teamId + "] [" + url + "]" + json);
				//
				return (Boolean) postForEntity.getBody();
				//
			} catch (Exception e) {
				this.logger.error("WEBHOOK ERROR :: [" + teamId + "/] " + e.getMessage());
				return false;
			}
		}
		//
		this.logger.info("WEBHOOK CONFIG NOT EXISTS :: [" + teamId + "] ");
		//
		return false;
		//
	}
	
	public Boolean updateWebhookConfiguration(Long teamId, String clientSecret, String clientUrl, Boolean active) {
		//
		JdbcTemplate jdbcTemplate = this.jobDivaConnectivity.getJdbcTemplate(teamId);
		active = (active != null) ? active.booleanValue() : false;
		//
		String sql = "update TWEBHOOK_CONFIGURATION SET CLIENT_SECRET = ? , CLIENT_URL = ? , ACTIVE = ? where TEAMID = ? ";
		Object[] params = { clientSecret, clientUrl, active, teamId };
		//
		int updated = jdbcTemplate.update(sql, params);
		//
		if (updated == 0) {
			String sqlInsert = "INSERT INTO TWEBHOOK_CONFIGURATION(ID, TEAMID, CLIENT_SECRET, CLIENT_URL, ACTIVE) values( TWEBHOOK_CONFIGURATION_SEQ.nextval, ?, ?, ?, ?) ";
			params = new Object[] { teamId, clientSecret, clientUrl, active };
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
}

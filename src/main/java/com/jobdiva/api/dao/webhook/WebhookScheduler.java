package com.jobdiva.api.dao.webhook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import com.jobdiva.api.config.AppProperties;
import com.jobdiva.api.dao.setup.JobDivaConnectivity;
import com.jobdiva.api.model.webhook.WebhookRequest;

/**
 * @author Joseph Chidiac
 *
 */
@Component
public class WebhookScheduler {
	
	private final Integer	RESEND_FAILED_REQUEST_IN_MINUTES	= 10;
	//
	protected final Logger	logger								= LoggerFactory.getLogger(this.getClass());
	//
	//
	@Autowired
	JobDivaConnectivity		jobDivaConnectivity;
	//
	@Autowired
	WebhookDao				webhookDao;
	//
	@Autowired
	protected AppProperties	appProperties;
	//
	protected Timer			webhookTimer;
	
	class WebhookFaultTask extends TimerTask {
		
		@Override
		public void run() {
			try {
				reSendFaultRequests();
			} catch (Exception e) {
				logger.info("Refresh WebHook : " + e.getMessage());
			}
		}
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		//
		if (appProperties.getEnableWebhook()) {
			//
			initScheduler();
			//
			logger.info(" WebhookScheduler init..");
			//
		}
	}
	
	protected void initScheduler() {
		if (webhookTimer != null) {
			webhookTimer.cancel();
		} else {
			webhookTimer = new Timer();
		}
		//
		//
		WebhookFaultTask task = new WebhookFaultTask();
		Integer period = RESEND_FAILED_REQUEST_IN_MINUTES * 60 * 1000;
		Integer start = 1 * 60 * 1000;
		//
		//
		webhookTimer.scheduleAtFixedRate(task, start, period);
	}
	
	public void reSendFaultRequests() {
		//
		try {
			List<WebhookRequest> globalList = new ArrayList<WebhookRequest>();
			for (JdbcTemplate jdbcTemplate : jobDivaConnectivity.getJdbcsTemplates()) {
				String sql = " SELECT * FROM TWEBHOOK_LOG WHERE ( INPROGRESS IS NULL OR INPROGRESS = 0) AND ( STATUSID = 2 ) ";
				List<WebhookRequest> list = jdbcTemplate.query(sql, new org.springframework.jdbc.core.RowMapper<WebhookRequest>() {
					
					@Override
					public WebhookRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						WebhookRequest webhookFaultRequest = new WebhookRequest();
						webhookFaultRequest.setFaultId(rs.getLong("ID"));
						webhookFaultRequest.setTeamId(rs.getLong("TEAMID"));
						webhookFaultRequest.setSyncType(rs.getString("SYNCTYPE"));
						webhookFaultRequest.setOperation(rs.getInt("OPERATIONTYPE"));
						webhookFaultRequest.setId(rs.getString("DATAID"));
						webhookFaultRequest.setJson(rs.getString("JSON"));
						return webhookFaultRequest;
					}
				});
				//
				//
				if (list.size() > 0) {
					//
					String sqlBatchDelete = "UPDATE TWEBHOOK_LOG SET INPROGRESS = ? WHERE ID = ? AND  TEAMID = ? ";
					//
					final int batchSize = 1000;
					//
					jdbcTemplate.execute(sqlBatchDelete, new PreparedStatementCallback<Integer>() {
						
						@Override
						public Integer doInPreparedStatement(PreparedStatement stmt) throws SQLException, DataAccessException {
							Connection cxn = stmt.getConnection();
							cxn.setAutoCommit(false);
							//
							int count = 0;
							int size = list.size();
							//
							for (WebhookRequest data : list) {
								//
								stmt.setBoolean(1, true);
								stmt.setLong(2, data.getFaultId());
								stmt.setLong(3, data.getTeamId());
								//
								stmt.addBatch();
								//
								++count;
								//
								if (count % batchSize == 0 || count == size) {
									stmt.executeBatch();
									stmt.clearBatch();
								}
							}
							cxn.setAutoCommit(true);
							return 0;
						}
					});
					//
					//
					globalList.addAll(list);
					//
				}
			}
			//
			//
			if (globalList.size() > 0) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						for (WebhookRequest webhookRequest : globalList) {
							try {
								webhookDao.syncWebhook(webhookRequest);
							} catch (Exception e) {
							}
						}
					}
				}).start();
			}
		} catch (Exception e) {
			logger.error("WebhookScheduler :: " + e.getMessage());
		}
	}
}

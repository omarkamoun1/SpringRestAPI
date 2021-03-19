package com.jobdiva.api.dao.webhook;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.jobdiva.api.config.jwt.CustomAuthenticationToken;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.bi.BIDataDaoOld;
import com.jobdiva.api.dao.bi.BiDataQuery;
import com.jobdiva.api.dao.candidate.CandidateDao;
import com.jobdiva.api.dao.candidate.CandidateUDFDao;
import com.jobdiva.api.dao.company.SearchCompanyDao;
import com.jobdiva.api.dao.company.SearchCompanyUDFDao;
import com.jobdiva.api.dao.contact.ContactDao;
import com.jobdiva.api.dao.contact.ContactUDFDao;
import com.jobdiva.api.dao.job.JobDao;
import com.jobdiva.api.dao.setup.JobDivaConnection;
import com.jobdiva.api.dao.workorder.WorkOrderDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

/**
 * @author Joseph Chidiac
 *
 */
@Component
public class WebhookRequestDataDao extends AbstractJobDivaDao {
	
	@Autowired
	JobDao				jobDao;
	//
	@Autowired
	SearchCompanyDao	searchCompanyDao;
	//
	@Autowired
	SearchCompanyUDFDao	searchCompanyUDFDao;
	//
	@Autowired
	CandidateDao		candidateDao;
	//
	@Autowired
	CandidateUDFDao		candidateUDFDao;
	//
	@Autowired
	ContactDao			contactDao;
	//
	@Autowired
	ContactUDFDao		contactUDFDao;
	//
	@Autowired
	WorkOrderDao		workOrderDao;
	//
	@Autowired
	BIDataDaoOld		biDataDaoOld;
	
	//
	private JobDivaSession assignContext(Long teamId) {
		JobDivaSession jobDivaSession = new JobDivaSession(teamId, "webhook", "webhook", null, 0, 0);
		//
		SecurityContext context = SecurityContextHolder.getContext();
		CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(jobDivaSession, "webhook", jobDivaSession.getTeamId(), false);
		//
		JdbcTemplate jdbcTemplate = jobDivaConnectivity.getJdbcTemplate(jobDivaSession.getTeamId());
		//
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = jobDivaConnectivity.getNamedParameterJdbcTemplate(jobDivaSession.getTeamId());
		//
		JobDivaConnection divaConnection = jobDivaConnectivity.getJobDivaConnection(jobDivaSession.getTeamId());
		//
		customAuthenticationToken.setJobDivaConnection(divaConnection);
		customAuthenticationToken.setJdbcTemplate(jdbcTemplate);
		customAuthenticationToken.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
		context.setAuthentication(customAuthenticationToken);
		return jobDivaSession;
	}
	
	private Vector<String[]> getBiDataInfo(JobDivaSession jobDivaSession, String metricName, String[] parameters) throws Exception {
		//
		Vector<String[]> output = null;
		//
		Map<String, String> colNameToAliasMap = new HashMap<String, String>();
		// Alias -> Original Column Name
		Map<String, String> aliasToColNameMap = new HashMap<String, String>();
		//
		// Convert long parameter (length > 30) in params to alias
		if (parameters instanceof String[]) {
			int aliasCount = 0;
			for (String parameter : parameters) {
				if (parameter.length() > 30) {
					aliasCount++;
					colNameToAliasMap.put(parameter, "colAlias_" + aliasCount);
					aliasToColNameMap.put("colAlias_" + aliasCount, parameter);
				}
			}
		}
		Vector<Object> param = new Vector<Object>();
		String[] restriction = new String[1];
		//
		String sql = BiDataQuery.constructQuery(metricName, jobDivaSession.getTeamId(), null, null, parameters, param, restriction, colNameToAliasMap);
		Connection connection = null;
		//
		try {
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			connection = jdbcTemplate.getDataSource().getConnection();
			//
			output = BiDataQuery.getQueryData(connection, jobDivaSession.getTeamId(), sql, param, aliasToColNameMap);
			//
			//
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
		//
		return output;
		//
	}
	//
	
	private JsonArray outputMetric(Vector<String[]> rspData) {
		JsonArray jsonArray = new JsonArray();
		if (rspData.size() > 1) {
			//
			String[] cols = rspData.elementAt(0);
			if (rspData.size() > 1) {
				//
				for (int i = 1; i < rspData.size(); i++) {
					JsonObject recordObj = new JsonObject();
					jsonArray.add(recordObj);
					// //
					String[] values = rspData.elementAt(i);
					// //
					for (int j = 0; j < values.length; j++) {
						String columnName = cols[j];
						String columnValue = values[j];
						recordObj.add(columnName, columnValue);
					}
				}
				//
			}
		}
		return jsonArray;
	} //
	
	public JsonArray getJob(Long teamId, String strRfqId) throws Exception {
		//
		Long rfqId = Long.parseLong(strRfqId);
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		String[] parameters = new String[] { rfqId + "" };
		//
		return outputMetric(getBiDataInfo(jobDivaSession, "Job Detail", parameters));
		// //
		// JobDivaSession jobDivaSession = assignContext(teamId);
		// //
		// Job job = jobDao.getJob(jobDivaSession, id);
		// //
		// return job;
		//
	}
	
	public JsonArray getCandidate(Long teamId, String strCandidateId) throws Exception {
		//
		Long candidateId = Long.parseLong(strCandidateId);
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		String[] parameters = new String[] { candidateId + "" };
		//
		return outputMetric(getBiDataInfo(jobDivaSession, "Candidate Detail", parameters));
		//
		//
		// WebhookCandidate webhookCandidate =
		// candidateDao.getWebhookCandidate(jobDivaSession, candidateId);
		// //
		// webhookCandidate.setUdfs(candidateUDFDao.getContactWebhookUDF(candidateId,
		// teamId));
		// //
		// return webhookCandidate;
	}
	
	public JsonArray getCompany(Long teamId, String strCompanyId) throws Exception {
		//
		Long companyId = Long.parseLong(strCompanyId);
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		String[] parameters = new String[] { companyId + "" }; // , "Comp Date
																// Test"
		return outputMetric(getBiDataInfo(jobDivaSession, "Company Detail", parameters));
		//
		// WebhookCompany company =
		// searchCompanyDao.getWebhookCompany(jobDivaSession, companyId);
		// //
		// company.setUdfs(searchCompanyUDFDao.getCompanyWebhookUDF(companyId,
		// teamId));
		// //
		// return company;
		//
	}
	
	public JsonArray getContact(Long teamId, String strContactId) throws Exception {
		//
		Long contactId = Long.parseLong(strContactId);
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		String[] parameters = new String[] { contactId + "" };
		return outputMetric(getBiDataInfo(jobDivaSession, "Contacts Detail", parameters));
		//
		// WebhookContact webhookContact =
		// contactDao.getWebhookContact(jobDivaSession, contactId);
		// //
		// webhookContact.setUdfs(contactUDFDao.getContactWebhookUDF(contactId,
		// teamId));
		// //
		// return webhookContact;
	}
	
	public JsonArray getBillingRecordDetail(Long teamId, String id) throws Exception {
		//
		//
		String metricName = "Billing Record Detail";
		//
		String[] ids = id.split(",");
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		String[] parameters = new String[] { ids[0], ids[1] };
		//
		JsonArray billingRecordDetail = outputMetric(getBiDataInfo(jobDivaSession, metricName, parameters));
		//
		return billingRecordDetail;
		//
	}
	
	public JsonArray getSalaryRecordDetail(Long teamId, String id) throws Exception {
		//
		//
		//
		String[] ids = id.split(",");
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		String[] parameters = new String[] { ids[0], ids[1] };
		//
		String metricName = "Salary Record Detail";
		//
		JsonArray salaryRecordDetail = outputMetric(getBiDataInfo(jobDivaSession, metricName, parameters));
		//
		return salaryRecordDetail;
		//
	}
}

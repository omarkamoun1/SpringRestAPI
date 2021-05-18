package com.jobdiva.api.dao.webhook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
		Connection connection = null;
		try {
			//
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			connection = jdbcTemplate.getDataSource().getConnection();
			if (metricName.equals("Billing Record Detail") || //
					metricName.equals("Deleted Salary Records") || //
					metricName.equals("Employee Billing Records Detail") || //
					metricName.equals("Employee Salary Records Detail") || //
					metricName.equals("Nesco Dinesol Salary Record Detail") || //
					metricName.equals("New/Updated Billing Records") || //
					metricName.equals("New/Updated Salary Records") || //
					metricName.equals("Rotator All Assignments - V2") || //
					metricName.equals("Salary Record Detail") || //
					metricName.equals("Salary Records Detail") || //
					metricName.equals("Timesheet Breakdown Detail")) {
				// System.out.println("restriction " + restriction.length + ", "
				// + restriction[0]);
				parameters = BiDataQuery.constructDynamicParams(connection, metricName, jobDivaSession.getTeamId(), null, null, parameters, param, restriction, colNameToAliasMap);
				param = new Vector<Object>(); // reset the param
			}
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
			String sql = BiDataQuery.constructQuery(metricName, jobDivaSession.getTeamId(), null, null, parameters, param, restriction, colNameToAliasMap);
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
	
	private List<String> getUserFields(Long teamId, Integer fieldFor) {
		//
		String sql = "SELECT fieldname FROM tuserfields WHERE teamid = ?  AND   BITAND(fieldfor, ?) = ? ";
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		Object[] params = new Object[] { teamId, fieldFor, fieldFor };
		//
		List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return rs.getString("fieldname");
				//
			}
		});
		//
		return list;
	}
	
	protected void assignBillingRecordDetailUDF(JobDivaSession jobDivaSession, Long employeeId, Long recId, ArrayList<String> paramsData) {
		String sql = "select discountid, discount_description from temployee_billingdiscount " //
				+ " where teamid = ? "//
				+ " and employeeid = ? "//
				+ " and recid = ? " //
				+ " and discount_description <> 'Default Discount'";
		//
		Object[] params = new Object[] { jobDivaSession.getTeamId(), employeeId, recId };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				paramsData.add("discount_" + rs.getString("DISCOUNTID") + "_" + rs.getString("DISCOUNT_DESCRIPTION"));
				//
				return null;
				//
			}
		});
		sql = "select u.id, u.fieldname from tuserfields u " //
				+ " where u.id in (select s.userfield_id from tstartrecord_userfields s join temployee_billingrecord b " //
				+ "	on s.teamid = b.recruiter_teamid and s.startid = b.interviewid where s.teamid = u.teamid " //
				+ "	and s.userfield_id = u.id and b.recruiter_teamid = ? and b.employeeid = ? and b.recid = ?)";
		//
		jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				paramsData.add("activityUdf_" + rs.getString("ID") + "_" + rs.getString("FIELDNAME"));
				//
				return null;
				//
			}
		});
		//
	}
	
	protected void assignSalaryRecordDetailUDF(JobDivaSession jobDivaSession, Long employeeId, Long recId, ArrayList<String> paramsData) {
		//
		String sql = "select distinct id, name from tteam_overhead_type a join temployee_overhead b " + //
				"on a.teamid = b.recruiter_teamid and a.id = b.overheadid " + //
				"where b.recruiter_teamid = ? and b.employeeid = ? and b.salary_recid = ? " + //
				"order by id";
		//
		Object[] params = new Object[] { jobDivaSession.getTeamId(), employeeId, recId };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				paramsData.add("overhead_" + rs.getString("NAME"));
				//
				return null;
				//
			}
		});
		//
		//
		sql = "select u.id, u.fieldname from tuserfields u " + //
				"where u.id in (select su.userfield_id from tstartrecord_userfields su join temployee_salaryrecord s " + //
				"	on su.teamid = s.recruiter_teamid and su.startid = s.interviewid where su.teamid = u.teamid " + //
				"	and su.userfield_id = u.id and s.recruiter_teamid = ? and s.employeeid = ? and s.recid = ?)";
		//
		jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				paramsData.add("activityUdf_" + rs.getString("ID") + "_" + rs.getString("FIELDNAME"));
				//
				return null;
				//
			}
		});
		//
		//
	}
	
	public JsonArray getJob(Long teamId, String strRfqId) throws Exception {
		//
		Long rfqId = Long.parseLong(strRfqId);
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		paramLits.add(rfqId + "");
		List<String> userFields = getUserFields(teamId, 4);
		for (int i = 0; i < userFields.size(); i++) {
			paramLits.add(userFields.get(i));
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		//
		return outputMetric(getBiDataInfo(jobDivaSession, "Job Detail", parameters));
		//
	}
	
	public JsonArray getCandidate(Long teamId, String strCandidateId) throws Exception {
		//
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		paramLits.add(strCandidateId);
		List<String> userFields = getUserFields(teamId, 2);
		for (int i = 0; i < userFields.size(); i++) {
			paramLits.add(userFields.get(i));
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		Vector<String[]> biDataInfo = getBiDataInfo(jobDivaSession, "Candidate Detail", parameters);
		//
		paramLits = new ArrayList<String>();
		paramLits.add(strCandidateId);
		parameters = paramLits.stream().toArray(String[]::new);
		Vector<String[]> qualificationBiDataInfo = getBiDataInfo(jobDivaSession, "Candidate Qualifications Detail", parameters);
		//
		JsonArray candidateJsonArray = outputMetric(biDataInfo);
		JsonArray qualificationJsonArray = outputMetric(qualificationBiDataInfo);
		//
		for (int i = 0; i < qualificationJsonArray.size(); i++) {
			JsonObject jsonObject = qualificationJsonArray.get(i).asObject();
			jsonObject.remove("CANDIDATEID");
		}
		//
		for (int i = 0; i < candidateJsonArray.size(); i++) {
			JsonObject jsonObject = candidateJsonArray.get(i).asObject();
			jsonObject.add("QUALIFICATIONS", qualificationJsonArray);
		}
		//
		return candidateJsonArray;
		//
	}
	
	public JsonArray getCompany(Long teamId, String strCompanyId) throws Exception {
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		paramLits.add(strCompanyId);
		List<String> userFields = getUserFields(teamId, 8);
		for (int i = 0; i < userFields.size(); i++) {
			paramLits.add(userFields.get(i));
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return outputMetric(getBiDataInfo(jobDivaSession, "Company Detail", parameters));
		//
	}
	
	public JsonArray getContact(Long teamId, String strContactId) throws Exception {
		//
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		paramLits.add(strContactId);
		List<String> userFields = getUserFields(teamId, 1);
		for (int i = 0; i < userFields.size(); i++) {
			paramLits.add(userFields.get(i));
		}
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		return outputMetric(getBiDataInfo(jobDivaSession, "Contacts Detail", parameters));
		//
	}
	
	public JsonArray getBillingRecordDetail(Long teamId, String id) throws Exception {
		//
		String metricName = "Billing Record Detail";
		//
		String[] ids = id.split(",");
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		paramLits.add(ids[0]);
		paramLits.add(ids[1]);
		//
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		//
		JsonArray billingRecordDetail = outputMetric(getBiDataInfo(jobDivaSession, metricName, parameters));
		//
		return billingRecordDetail;
		//
	}
	
	public JsonArray getSalaryRecordDetail(Long teamId, String id) throws Exception {
		//
		String[] ids = id.split(",");
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		//
		ArrayList<String> paramLits = new ArrayList<String>();
		paramLits.add(ids[0]);
		paramLits.add(ids[1]);
		//
		//
		String[] parameters = paramLits.stream().toArray(String[]::new);
		//
		String metricName = "Salary Record Detail";
		//
		JsonArray salaryRecordDetail = outputMetric(getBiDataInfo(jobDivaSession, metricName, parameters));
		//
		return salaryRecordDetail;
		//
	}
	
	public JsonArray getTimeSheet(Long teamId, String id) throws Exception {
		//
		//
		Long timesheetId = Long.parseLong(id);
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		String[] parameters = new String[] { timesheetId + "" };
		//
		return outputMetric(getBiDataInfo(jobDivaSession, "Timesheet Detail", parameters));
		//
	}
	
	public JsonArray getExpenses(Long teamId, String id) throws Exception {
		//
		//
		Long expenseId = Long.parseLong(id);
		//
		JobDivaSession jobDivaSession = assignContext(teamId);
		//
		String[] parameters = new String[] { expenseId + "" };
		//
		return outputMetric(getBiDataInfo(jobDivaSession, "Expense Detail", parameters));
		//
		//
	}
}

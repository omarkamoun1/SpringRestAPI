package com.jobdiva.api.dao.invoice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.activity.ActivityUserFieldsDao;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class UpdatePayRecordDao extends AbstractJobDivaDao {
	
	//
	@Autowired
	ActivityUserFieldsDao activityUserFieldsDao;
	
	private void checkRequiredFields(Double assignmentID, Long candidateID) throws Exception {
		// check Require Fields
		StringBuffer messageError = new StringBuffer();
		//
		//
		//
		if (assignmentID == null || assignmentID <= 0) {
			messageError.append("assignmentID is required. \r\n ");
		}
		//
		if (candidateID == null || candidateID <= 0) {
			messageError.append("candidateID is required. \r\n ");
		}
		//
		if (messageError.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + messageError.toString());
		}
	}
	
	public Boolean updatePayRecord(JobDivaSession jobDivaSession, String aDPCOCODE, String aDPPAYFREQUENCY, Boolean approved, Double assignmentID, Long candidateID, Double doubletimeRate, String doubletimeRatePer, Date startDate, //
			Date endDate, String fileNo, Double otherExpenses, String otherExpensesPer, Double outsideCommission, String outsideCommissionPer, Boolean overtimeExempt, Double overtimeRate, //
			String overtimeRatePer, String paymentTerms, Boolean payOnRemittance, Double perDiem, String perDiemPer, Integer recordID, Double salary, String salaryPer, Integer salaryPerCurrency, Integer status, //
			Long subcontractCompanyID, String taxID, Userfield[] userfields) throws Exception {
		//
		checkRequiredFields(assignmentID, candidateID);
		//
		//
		if (recordID == null) {
			String sql = " SELECT NVL(MAX(recid), -1) as RECID " //
					+ " FROM Temployee_salaryrecord " //
					+ " WHERE employeeid = ? " //
					+ " AND recruiter_teamid = ? " //
					+ " AND interviewid = ? " //
					+ " AND parent_recid is NULL";
			Object[] params = new Object[] { candidateID, jobDivaSession.getTeamId(), assignmentID };
			//
			List<Integer> list = getJdbcTemplate().query(sql, params, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getInt("RECID");
				}
			});
			//
			if (list != null && list.size() > 0) {
				recordID = list.get(0);
			}
			if (recordID == null || recordID < 0) {
				throw new Exception("Unable to find pay record under candidate ID " + candidateID + " and assignment ID " + assignmentID);
			}
		}
		//
		//
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		// Check for approve / unapprove conditions
		if (approved != null) {
			//
			// start date
			// employment category
			// currency
			// pay rate
			String sql = " SELECT EFFECTIVE_DATE , SALARY, salary_per,SALARYPER_CURRENCY , STATUS, approved, overtimeexempt, overtime_rate1, overtime_rate1_per "//
					+ " FROM  Temployee_salaryrecord " //
					+ " Where employeeid = ? and recruiter_teamid = ? and recid = ?";
			Object[] params = new Object[] { candidateID, jobDivaSession.getTeamId(), recordID };
			List<List<Object>> list = jdbcTemplate.query(sql, params, new RowMapper<List<Object>>() {
				
				@Override
				public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					List<Object> list = new ArrayList<Object>();
					list.add(rs.getDate("EFFECTIVE_DATE"));
					list.add(rs.getDouble("SALARY"));
					list.add(rs.getInt("SALARYPER_CURRENCY"));
					list.add(rs.getInt("STATUS"));
					list.add(rs.getBoolean("approved"));
					list.add(rs.getString("salary_per"));
					list.add(rs.getDouble("overtime_rate1"));
					list.add(rs.getString("overtime_rate1_per"));
					list.add(rs.getBoolean("overtimeexempt"));
					return list;
				}
			});
			//
			//
			Boolean existRecord = list != null && list.size() > 0;
			Date dbEffectiveDate = existRecord ? (Date) list.get(0).get(0) : null;
			Double dbSalary = existRecord ? (Double) list.get(0).get(1) : null;
			Integer dbCurrency = existRecord ? (Integer) list.get(0).get(2) : null;
			Integer dbCategory = existRecord ? (Integer) list.get(0).get(3) : null;
			Boolean dbApproved = existRecord ? (Boolean) list.get(0).get(4) : false;
			String dbsalary_per = existRecord ? (String) list.get(0).get(5) : null;
			Double dbovertime_rate1 = existRecord ? (Double) list.get(0).get(6) : null;
			String dbovertime_rate1_per = existRecord ? (String) list.get(0).get(7) : null;
			Boolean dbovertimeexempt = existRecord ? (Boolean) list.get(0).get(8) : null;
			//
			//
			if (!approved.equals(dbApproved)) {
				//
				//
				if (approved) {
					//
					ArrayList<String> message = new ArrayList<String>();
					//
					if (startDate == null && dbEffectiveDate == null) {
						message.add("startDate");
					}
					//
					if (salary == null && dbSalary == null) {
						message.add("salary");
					}
					//
					if (isEmpty(salaryPer) && isEmpty(dbsalary_per)) {
						message.add("salaryPer");
					}
					//
					if (salaryPerCurrency == null && dbCurrency == null) {
						message.add("salaryPerCurrency");
					}
					//
					if (status == null && dbCategory == null) {
						message.add("status");
					}
					//
					if (overtimeExempt == null) {
						overtimeExempt = dbovertimeexempt;
					}
					overtimeExempt = overtimeExempt != null ? overtimeExempt : false;
					//
					if (!overtimeExempt) {
						// overtime_rate1
						if (overtimeRate == null && (dbovertime_rate1 == null || dbovertime_rate1 <= 0)) {
							message.add("overtimeRate");
						}
						// overtime_rate1_per
						if (isEmpty(overtimeRatePer) && isEmpty(dbovertime_rate1_per)) {
							message.add("overtimeRatePer");
						}
					}
					if (message.size() > 0) {
						String error = StringUtils.join(message, ",");
						throw new Exception(error + " are required to approve this pay.");
					}
					//
				}
			}
		}
		//
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		if (endDate != null) {
			paramList.add(simpleDateFormat.format(endDate));
		}
		//
		if (taxID != null) {
			fields.add("taxid");
			paramList.add(taxID);
		}
		//
		if (paymentTerms != null) {
			fields.add("paymentterms");
			paramList.add(paymentTerms);
		}
		//
		if (approved != null) {
			fields.add("approved");
			paramList.add(approved);
		}
		//
		if (startDate != null) {
			fields.add("effective_date");
			paramList.add(startDate);
		}
		//
		if (status != null) {
			fields.add("status");
			paramList.add(status);
		}
		//
		if (subcontractCompanyID != null) {
			fields.add("subcontract_companyid");
			paramList.add(subcontractCompanyID);
		}
		//
		if (payOnRemittance != null) {
			fields.add("subcontract_payonremit");
			paramList.add(payOnRemittance);
		}
		//
		if (salary != null) {
			fields.add("salary");
			paramList.add(salary);
		}
		if (salaryPerCurrency != null) {
			fields.add("SALARYPER_CURRENCY");
			paramList.add(salaryPerCurrency);
		}
		//
		if (salaryPer != null) {
			fields.add("salary_per");
			paramList.add(salaryPer);
		}
		//
		if (perDiem != null) {
			fields.add("per_diem");
			paramList.add(perDiem);
		}
		//
		if (perDiemPer != null) {
			fields.add("per_diem_per");
			paramList.add(perDiemPer);
		}
		//
		if (otherExpenses != null) {
			fields.add("other_expenses");
			paramList.add(otherExpenses);
		}
		//
		if (otherExpensesPer != null) {
			fields.add("other_expenses_per");
			paramList.add(otherExpensesPer);
		}
		//
		if (outsideCommission != null) {
			fields.add("outside_commission");
			paramList.add(outsideCommission);
		}
		//
		if (outsideCommissionPer != null) {
			fields.add("outside_commission_per");
			paramList.add(outsideCommissionPer);
		}
		//
		if (overtimeRate != null) {
			fields.add("overtime_rate1");
			paramList.add(overtimeRate);
		}
		//
		if (overtimeRatePer != null) {
			fields.add("overtime_rate1_per");
			paramList.add(overtimeRatePer);
		}
		//
		if (doubletimeRate != null) {
			fields.add("overtime_rate2");
			paramList.add(doubletimeRate);
		}
		//
		if (doubletimeRatePer != null) {
			fields.add("overtime_rate2_per");
			paramList.add(doubletimeRatePer);
		}
		//
		if (overtimeExempt != null) {
			fields.add("overtimeexempt");
			paramList.add(overtimeExempt);
		}
		//
		if (fileNo != null) {
			fields.add("adp_file_no");
			paramList.add(fileNo);
		}
		//
		if (aDPCOCODE != null) {
			fields.add("ADPCOCODE");
			paramList.add(aDPCOCODE);
		}
		//
		if (aDPPAYFREQUENCY != null) {
			fields.add("ADPPAYFREQUENCY");
			paramList.add(aDPPAYFREQUENCY);
		}
		//
		//
		if (fields.size() > 0) {
			//
			String strEndDate = endDate != null ? " END_DATE = to_date(?, 'MM/dd/yyyy'), " : "";
			String sqlUpdate = " UPDATE temployee_salaryrecord SET datecreated = sysdate, " + strEndDate + sqlUpdateFields(fields)//
					+ " Where employeeid = ?  and interviewid = ?  and recruiter_teamid = ? and recid = ?";
			//
			paramList.add(candidateID);
			paramList.add(assignmentID);
			paramList.add(jobDivaSession.getTeamId());
			paramList.add(recordID);
			//
			Object[] parameters = paramList.toArray();
			//
			jdbcTemplate.update(sqlUpdate, parameters);
		} else if (endDate != null) {
			//
			String sqlUpdate = " UPDATE temployee_salaryrecord SET datecreated = sysdate, END_DATE = to_date(?, 'MM/dd/yyyy') " //
					+ " Where employeeid = ?  and interviewid = ?  and recruiter_teamid = ? and recid = ?";
			//
			paramList.add(candidateID);
			paramList.add(assignmentID);
			paramList.add(jobDivaSession.getTeamId());
			paramList.add(recordID);
			//
			Object[] parameters = paramList.toArray();
			//
			jdbcTemplate.update(sqlUpdate, parameters);
		}
		//
		//
		//
		//
		//
		if (userfields != null && userfields.length > 0) {
			//
			Date currentTS = new Date();
			Long startId = assignmentID != null ? assignmentID.longValue() : 0L;
			//
			validateUserFields(jobDivaSession, jobDivaSession.getTeamId(), userfields, UDF_FIELDFOR_ACTIVITY);
			//
			//
			for (Userfield userfield : userfields) {
				//
				Boolean existActivityUDF = activityUserFieldsDao.existActivityUDF(jobDivaSession, startId, userfield.getUserfieldId());
				//
				if (isEmpty(userfield.getUserfieldValue())) {
					if (existActivityUDF)
						activityUserFieldsDao.deleteActivityUDF(jobDivaSession, startId, userfield.getUserfieldId());
				} else {
					//
					if (existActivityUDF) {
						activityUserFieldsDao.updateActivityUDF(startId, userfield.getUserfieldId(), jobDivaSession.getTeamId(), currentTS, userfield.getUserfieldValue(), jobDivaSession.getRecruiterId());
					} else {
						activityUserFieldsDao.insertActivityUDF(startId, userfield.getUserfieldId(), jobDivaSession.getTeamId(), currentTS, userfield.getUserfieldValue(), jobDivaSession.getRecruiterId());
					}
				}
			}
		}
		//
		//
		//
		return true;
	}
}

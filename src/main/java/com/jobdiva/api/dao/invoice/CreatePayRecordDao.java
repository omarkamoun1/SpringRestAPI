package com.jobdiva.api.dao.invoice;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class CreatePayRecordDao extends AbstractJobDivaDao {
	
	public Integer createPayRecord(JobDivaSession jobDivaSession, Long candidateID, Double assignmentID, Long jobID,
			/* Integer recordID, */ Boolean approved, Long createdByID, Date effectiveDate, Date endDate, Integer status, String taxID, String paymentTerms, Long subcontractCompanyID, Boolean payOnRemittance, Double salary, String salaryPer,
			Double perDiem, String perDiemPer, Double otherExpenses, String otherExpensesPer, Double outsideCommission, String outsideCommissionPer, Double overtimeRate, String overtimeRatePer, Double doubletimeRate, String doubletimeRatePer,
			Boolean overtimeExempt, String fileNo, String aDPCOCODE, String aDPPAYFREQUENCY) {
		//
		HashMap<String, Object> payRecordMap = new HashMap<String, Object>();
		// Pay Record required fields
		payRecordMap.put("EMPLOYEEID", candidateID);
		payRecordMap.put("RECRUITER_TEAMID", jobDivaSession.getTeamId());
		// Pay Record optional fields
		//
		if (effectiveDate != null)
			payRecordMap.put("EFFECTIVE_DATE", new Timestamp(effectiveDate.getTime()));
		//
		if (status != null)
			payRecordMap.put("STATUS", status);
		//
		if (salary != null)
			payRecordMap.put("SALARY", salary);
		//
		if (jobID != null)
			payRecordMap.put("RFQID", jobID);
		//
		if (endDate != null)
			payRecordMap.put("END_DATE", new Timestamp(endDate.getTime()));
		//
		if (perDiem != null)
			payRecordMap.put("PER_DIEM", perDiem);
		//
		if (isNotEmpty(perDiemPer))
			payRecordMap.put("PER_DIEM_PER", perDiemPer);
		//
		if (otherExpenses != null)
			payRecordMap.put("OTHER_EXPENSES", otherExpenses);
		//
		if (isNotEmpty(otherExpensesPer))
			payRecordMap.put("OTHER_EXPENSES_PER", otherExpensesPer);
		//
		if (outsideCommission != null)
			payRecordMap.put("OUTSIDE_COMMISSION", outsideCommission);
		//
		if (isNotEmpty(outsideCommissionPer))
			payRecordMap.put("OUTSIDE_COMMISSION_PER", outsideCommissionPer);
		//
		if (isNotEmpty(salaryPer))
			payRecordMap.put("SALARY_PER", salaryPer.substring(0, 1).toUpperCase());
		//
		if (overtimeRate != null)
			payRecordMap.put("OVERTIME_RATE1", overtimeRate);
		//
		if (isNotEmpty(overtimeRatePer))
			payRecordMap.put("OVERTIME_RATE1_PER", overtimeRatePer.substring(0, 1).toUpperCase());
		//
		if (doubletimeRate != null)
			payRecordMap.put("OVERTIME_RATE2", doubletimeRate);
		//
		if (isNotEmpty(doubletimeRatePer))
			payRecordMap.put("OVERTIME_RATE2_PER", doubletimeRatePer.substring(0, 1).toUpperCase());
		//
		if (overtimeExempt != null)
			payRecordMap.put("OVERTIMEEXEMPT", overtimeExempt);
		//
		if (isNotEmpty(fileNo))
			payRecordMap.put("ADP_FILE_NO", fileNo);
		//
		if (subcontractCompanyID != null)
			payRecordMap.put("SUBCONTRACT_COMPANYID", subcontractCompanyID);
		//
		if (payOnRemittance != null)
			payRecordMap.put("SUBCONTRACT_PAYONREMIT", payOnRemittance);
		//
		if (isNotEmpty(taxID))
			payRecordMap.put("TAXID", taxID);
		//
		if (isNotEmpty(paymentTerms))
			payRecordMap.put("PAYMENTTERMS", paymentTerms);
		//
		if (approved != null)
			payRecordMap.put("APPROVED", approved);
		//
		if (assignmentID != null)
			payRecordMap.put("INTERVIEWID", assignmentID);
		//
		if (isNotEmpty(aDPCOCODE))
			payRecordMap.put("ADPCOCODE", aDPCOCODE);
		//
		if (isNotEmpty(aDPPAYFREQUENCY))
			payRecordMap.put("ADPPAYFREQUENCY", aDPPAYFREQUENCY);
		//
		// if (recordID != null)
		// payRecordMap.put("RECID", recordID);
		//
		if (createdByID != null)
			payRecordMap.put("CREATED_BY", createdByID);
		else
			payRecordMap.put("CREATED_BY", jobDivaSession.getRecruiterId());
		//
		//
		//
		//
		// Default END_DATE, SALARY_PER, STATUS
		// Using Tinterviewschedule
		String sql = " SELECT date_ended, payrateunits, hourly_corporate, rfqid " //
				+ " FROM Tinterviewschedule " //
				+ " WHERE id = ? " //
				+ " AND recruiter_teamid = ? ";
		//
		Object[] parameters = new Object[] { payRecordMap.get("INTERVIEWID"), jobDivaSession.getTeamId() };
		getJdbcTemplate().query(sql, parameters, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				Timestamp date_ended = rs.getTimestamp("date_ended");
				if (!payRecordMap.containsKey("END_DATE") && date_ended != null) {
					payRecordMap.put("END_DATE", date_ended);
				}
				//
				String payrateunits = rs.getString("payrateunits");
				//
				if (!payRecordMap.containsKey("SALARY_PER") && payrateunits != null) {
					String salaryPer = payrateunits.substring(0, 1).toUpperCase();
					payRecordMap.put("SALARY_PER", salaryPer);
					// default STATUS
					// Integer status = (Integer) payRecordMap.get("STATUS");
					int target_status = -1;
					if ("H".equals(salaryPer)) {
						target_status = 1; // hourly employee
						BigDecimal hourly_corporate = rs.getBigDecimal("hourly_corporate");
						if (hourly_corporate != null && hourly_corporate.intValueExact() > 0) {
							target_status = 2;
						}
					} else if ("Y".equals(salaryPer)) {
						target_status = 3; // salary employee
					}
					if (target_status > 0) {
						// if (status != target_status)
						// warning.append("Status is changed based on the
						// default. ");
						payRecordMap.put("STATUS", target_status);
					}
				}
				Long rfqid = rs.getLong("rfqid");
				payRecordMap.put("RFQID", rfqid);
				return true;
			}
		});
		//
		//
		//
		// Default ADPCOCODE
		// Using Temployee_salaryrecord
		if (!payRecordMap.containsKey("ADPCOCODE")) {
			sql = " SELECT adpcocode "//
					+ " FROM Temployee_salaryrecord "//
					+ " WHERE interviewid = ? " //
					+ " AND employeeid = ? " //
					+ " AND recruiter_teamid = ? " //
					+ " AND closed = 0 " //
					+ " ORDER BY effective_date DESC ";
			//
			parameters = new Object[] { assignmentID, candidateID, jobDivaSession.getTeamId() };
			List<String> list = getJdbcTemplate().query(sql, parameters, new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getString("adpcocode");
				}
			});
			//
			if (list != null && list.size() > 0) {
				payRecordMap.put("ADPCOCODE", list.get(0));
			}
		}
		//
		//
		//
		//
		//
		// Auto-calculate OT
		// Default ADPPAYFREQUENCY, OVERTIMEEXEMPT
		// Using Tcustomercompany
		sql = " SELECT Company.financial_standard_ot_pay, " //
				+ " NVL(Company.payroll_frequency, 0) as payroll_frequency, " //
				+ " NVL(Company.otexempt_payroll, 0) as otexempt_payroll " //
				+ " FROM TinterviewSchedule Activity, Trfq Job, Tcustomercompany Company " //
				+ " WHERE Activity.id = ? " //
				+ " AND Activity.recruiter_teamid = ? " //
				+ " AND Activity.rfqid = Job.id " //
				+ " AND Job.companyid = Company.id ";
		//
		parameters = new Object[] { assignmentID, jobDivaSession.getTeamId() };
		getJdbcTemplate().query(sql, parameters, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				//
				BigDecimal ot_ratio_pay_bd = rs.getBigDecimal("financial_standard_ot_pay");
				if (!payRecordMap.containsKey("OVERTIME_RATE1") && ot_ratio_pay_bd != null) {
					double ot_ratio_pay = ot_ratio_pay_bd.doubleValue();
					if (ot_ratio_pay > 0)
						payRecordMap.put("OVERTIME_RATE1", (Double) payRecordMap.get("SALARY") * ot_ratio_pay);
				}
				if (!payRecordMap.containsKey("ADPPAYFREQUENCY"))
					payRecordMap.put("ADPPAYFREQUENCY", rs.getString("payroll_frequency"));
				if (!payRecordMap.containsKey("OVERTIMEEXEMPT")) {
					int ot_exempt = rs.getInt("otexempt_payroll");
					payRecordMap.put("OVERTIMEEXEMPT", ot_exempt > 0 ? true : false);
				}
				//
				//
				return true;
			}
		});
		//
		//
		//
		//
		// Default ADP_FILE_NO, OVERTIMEEXEMPT, ADPPAYFREQUENCY if missing
		// Using Temployee_salaryrecord
		sql = " SELECT adp_file_no, overtimeexempt, adppayfrequency " //
				+ " FROM Temployee_salaryrecord " //
				+ " WHERE employeeid = ? " //
				+ " AND recruiter_teamid = ? " //
				+ " AND closed = 0 " //
				+ " ORDER BY recid DESC";
		//
		parameters = new Object[] { candidateID, jobDivaSession.getTeamId() };
		getJdbcTemplate().query(sql, parameters, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				String fileNo = rs.getString("adp_file_no");
				if (!payRecordMap.containsKey("ADP_FILE_NO") && fileNo != null) {
					payRecordMap.put("ADP_FILE_NO", fileNo);
				}
				//
				BigDecimal overtimeexempt = rs.getBigDecimal("overtimeexempt");
				if (!payRecordMap.containsKey("OVERTIMEEXEMPT") && overtimeexempt != null) {
					BigDecimal ot_exempt_bd = overtimeexempt;
					int ot_exempt = ot_exempt_bd.intValueExact();
					payRecordMap.put("OVERTIMEEXEMPT", ot_exempt == 0 ? false : true);
				}
				//
				String adppayfrequency = rs.getString("adppayfrequency");
				if (!payRecordMap.containsKey("ADPPAYFREQUENCY") && adppayfrequency != null) {
					payRecordMap.put("ADPPAYFREQUENCY", adppayfrequency);
				}
				//
				//
				return true;
			}
		});
		sql = " SELECT NVL(max(recid), 0) as maxRecId " //
				+ " FROM Temployee_salaryrecord " //
				+ " WHERE employeeid = ? " //
				+ " AND recruiter_teamid = ? ";
		//
		parameters = new Object[] { candidateID, jobDivaSession.getTeamId() };
		List<Integer> list = getJdbcTemplate().query(sql, parameters, new RowMapper<Integer>() {
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return rs.getInt("maxRecId");
			}
		});
		//
		int recID = -1;
		if (list != null && list.size() > 0)
			recID = list.get(0) + 1;
		//
		payRecordMap.put("RECID", recID);
		//
		//
		//
		//
		sql = "	INSERT INTO Temployee_salaryrecord (" //
				+ " datecreated, datecreated_real, closed, ";
		String sqlValue = " VALUES (sysdate,sysdate,0,";
		for (String key : payRecordMap.keySet()) {
			sql += key + ",";
			sqlValue += " ? ,";
		}
		sql = sql.substring(0, sql.length() - 1) + ") ";
		sqlValue = sqlValue.substring(0, sqlValue.length() - 1) + ") ";
		sql = sql + sqlValue;
		ArrayList<Object> paramList = new ArrayList<Object>();
		for (String key : payRecordMap.keySet()) {
			paramList.add(payRecordMap.get(key));
		}
		//
		parameters = paramList.toArray();
		getJdbcTemplate().update(sql, parameters);
		//
		//
		return recID;
		//
	}
}

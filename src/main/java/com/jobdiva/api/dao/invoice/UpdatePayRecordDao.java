package com.jobdiva.api.dao.invoice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	public Boolean UpdatePayRecord(JobDivaSession jobDivaSession, String aDPCOCODE, String aDPPAYFREQUENCY, Boolean approved, Double assignmentID, Long candidateID, Double doubletimeRate, String doubletimeRatePer, Date effectiveDate, //
			Date endDate, String fileNo, Double otherExpenses, String otherExpensesPer, Double outsideCommission, String outsideCommissionPer, Boolean overtimeExempt, Double overtimeRate, //
			String overtimeRatePer, String paymentTerms, Boolean payOnRemittance, Double perDiem, String perDiemPer, Integer recordID, Double salary, String salaryPer, Integer status, //
			Long subcontractCompanyID, String taxID, Userfield[] userfields) throws Exception {
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
			if (list == null || list.size() == 0) {
				throw new Exception("Unable to find pay record under candidate ID " + candidateID + " and assignment ID " + assignmentID);
			} else {
				recordID = list.get(0);
			}
		}
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
		if (effectiveDate != null) {
			fields.add("effective_date");
			paramList.add(effectiveDate);
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
					+ " Where employeeid = ? and recruiter_teamid = ? and recid = ?";
			//
			paramList.add(candidateID);
			paramList.add(jobDivaSession.getTeamId());
			paramList.add(recordID);
			//
			Object[] parameters = paramList.toArray();
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			jdbcTemplate.update(sqlUpdate, parameters);
		} else if (endDate != null) {
			//
			String sqlUpdate = " UPDATE temployee_salaryrecord SET datecreated = sysdate, END_DATE = to_date(?, 'MM/dd/yyyy') " //
					+ " Where employeeid = ? and recruiter_teamid = ? and recid = ?";
			//
			paramList.add(candidateID);
			paramList.add(jobDivaSession.getTeamId());
			paramList.add(recordID);
			//
			Object[] parameters = paramList.toArray();
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
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
						activityUserFieldsDao.updateActivityUDF(startId, userfield.getUserfieldId(), jobDivaSession.getTeamId(), currentTS, userfield.getUserfieldValue());
					} else {
						activityUserFieldsDao.insertActivityUDF(startId, userfield.getUserfieldId(), jobDivaSession.getTeamId(), currentTS, userfield.getUserfieldValue());
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

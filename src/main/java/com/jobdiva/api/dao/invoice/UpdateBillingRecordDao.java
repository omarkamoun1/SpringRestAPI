package com.jobdiva.api.dao.invoice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class UpdateBillingRecordDao extends AbstractJobDivaDao {
	
	//
	@Autowired
	ActivityUserFieldsDao activityUserFieldsDao;
	
	public Boolean updateBillingRecord(JobDivaSession jobDivaSession, Boolean allowEnterTimeOnPortal, Integer approved, Double assignmentID, Long billingContactID, Integer billingUnit, Double billRate, String billRatePer, Long candidateID,
			String customerRefNo, Long division, Double doubletimePer, Double doubletimeRate, String doubletimeRatePer, Boolean enableTimesheet, Date endDate, Boolean expenseEnabled, Integer expenseInvoices, Integer frequency, Long hiringManagerID,
			Double hoursPerDay, Double hoursPerHalfDay, Integer invoiceContent, String invoiceGroup, Integer invoiceGroupIndex, Double jobID, Integer overtimeByWorkingState, Boolean overtimeExempt, Double overtimeRate, String overtimeRatePer,
			String paymentTerms, Long primaryRecruiterID, Double primaryRecruiterPercentage, Double primarySalesPercentage, Long primarySalesPersonID, Integer recordID, Long secondaryRecruiterID, Double secondaryRecruiterPercentage,
			Double secondarySalesPercentage, Long secondarySalesPersonID, Date startDate, Long tertiaryRecruiterID, Double tertiaryRecruiterPercentage, Double tertiarySalesPercentage, Long tertiarySalesPersonID, Long timesheetEntryFormat,
			String timesheetInstruction, String vMSEmployeeName, String vMSWebsite, Integer weekEnding, String workAddress1, String workAddress2, String workCity, String workCountry, String workState, String workZipcode, Userfield[] userfields)
			throws Exception {
		//
		// recid is null. Use max(recid) instead...
		if (recordID == null) {
			String sql = " SELECT NVL(MAX(recid), -1) as RECID " //
					+ " FROM Temployee_billingrecord " //
					+ " WHERE employeeid = ? " //
					+ " AND recruiter_teamid = ? " //
					+ " AND interviewid = ? " //
					+ " AND parent_recid is NULL";
			//
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
				throw new Exception("Unable to find billing record under candidate ID " + candidateID + " and assignment ID " + assignmentID);
			} else {
				recordID = list.get(0);
			}
		}
		//
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		if (approved != null) {
			fields.add("approved");
			paramList.add(approved);
			//
			if (approved.intValue() == 1) {
				fields.add("APPROVERID");
				paramList.add(jobDivaSession.getRecruiterId());
			}
		}
		//
		if (startDate != null) {
			fields.add("start_date");
			paramList.add(new Timestamp(startDate.getTime()));
		}
		//
		if (endDate != null) {
			fields.add("end_date");
			paramList.add(new Timestamp(endDate.getTime()));
		}
		//
		if (customerRefNo != null) {
			fields.add("CUSTOMER_REFNO");
			paramList.add(customerRefNo);
		}
		//
		if (hiringManagerID != null) {
			fields.add("hiring_manager");
			paramList.add(hiringManagerID);
		}
		//
		if (billingContactID != null) {
			fields.add("billing_contact");
			paramList.add(billingContactID);
		}
		//
		if (division != null) {
			fields.add("DIVISION");
			paramList.add(division);
		}
		//
		if (invoiceGroupIndex != null) {
			fields.add("INVOICE_GROUP_INDEX");
			paramList.add(invoiceGroupIndex);
		}
		//
		if (invoiceGroup != null) {
			fields.add("INVOICE_GROUP");
			paramList.add(invoiceGroup);
		}
		//
		if (vMSWebsite != null) {
			fields.add("vms_website");
			paramList.add(vMSWebsite);
		}
		//
		if (vMSEmployeeName != null) {
			fields.add("vmsEmployeeName");
			paramList.add(vMSEmployeeName);
		}
		//
		if (invoiceContent != null) {
			fields.add("GROUPING_EXPENSES");
			paramList.add(invoiceContent);
		}
		//
		if (expenseInvoices != null) {
			fields.add("EXPENSE_DISPLAY_CATEGORYTOTAL");
			paramList.add(expenseInvoices);
		}
		//
		if (enableTimesheet != null) {
			fields.add("ENABLE_TIMESHEET");
			paramList.add(enableTimesheet);
		}
		//
		if (allowEnterTimeOnPortal != null) {
			fields.add("ALLOW_ENTER_TIME_ON_PORTAL");
			paramList.add(allowEnterTimeOnPortal);
		}
		//
		if (timesheetInstruction != null) {
			fields.add("TIMESHEET_INSTRUCTION");
			paramList.add(timesheetInstruction);
		}
		//
		if (expenseEnabled != null) {
			fields.add("expenseenabled");
			paramList.add(expenseEnabled);
		}
		//
		if (billRate != null) {
			fields.add("bill_rate");
			paramList.add(billRate);
		}
		//
		if (billRatePer != null) {
			fields.add("bill_rate_per");
			paramList.add(billRatePer);
		}
		//
		if (overtimeExempt != null) {
			fields.add("overtimeexempt");
			paramList.add(overtimeExempt);
		}
		//
		if (timesheetEntryFormat != null) {
			fields.add("timesheet_entry_format");
			paramList.add(timesheetEntryFormat);
		}
		//
		if (frequency != null) {
			fields.add("frequency");
			paramList.add(frequency);
		}
		//
		if (overtimeByWorkingState != null) {
			fields.add("OT_BY_WORKING_STATE");
			paramList.add(overtimeByWorkingState);
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
		if (billingUnit != null) {
			fields.add("billing_unit");
			paramList.add(billingUnit);
		}
		//
		if (weekEnding != null) {
			fields.add("week_ending");
			paramList.add(weekEnding);
		}
		//
		if (hoursPerDay != null) {
			fields.add("HOURS_PER_DAY");
			paramList.add(hoursPerDay);
		}
		//
		if (hoursPerHalfDay != null) {
			fields.add("HOURS_PER_HALF_DAY");
			paramList.add(hoursPerHalfDay);
		}
		//
		if (workAddress1 != null) {
			fields.add("WORK_ADDRESS1");
			paramList.add(workAddress1);
		}
		//
		if (workAddress2 != null) {
			fields.add("WORK_ADDRESS2");
			paramList.add(workAddress2);
		}
		//
		if (workCity != null) {
			fields.add("WORK_CITY");
			paramList.add(workCity);
			//
			fields.add("WORKING_CITY");
			paramList.add(workCity);
			//
		}
		//
		if (workState != null) {
			fields.add("WORK_STATE");
			paramList.add(workState);
			//
			fields.add("WORKING_STATE");
			paramList.add(workState);
		}
		//
		if (workZipcode != null) {
			fields.add("WORK_ZIP");
			paramList.add(workZipcode);
		}
		//
		if (workCountry != null) {
			fields.add("WORKING_COUNTRY");
			paramList.add(workCountry);
			//
			fields.add("WORK_COUNTRY");
			paramList.add(workCountry);
		}
		//
		if (paymentTerms != null) {
			fields.add("paymentterms");
			paramList.add(paymentTerms);
		}
		//
		if (primarySalesPercentage != null) {
			fields.add("PRIMARY_SALESPERSON");
			paramList.add(primarySalesPercentage);
		}
		//
		if (primarySalesPercentage != null) {
			fields.add("PRISALE_COMM_PERCENT");
			paramList.add(primarySalesPercentage);
		}
		//
		if (secondarySalesPersonID != null) {
			fields.add("SECONDARY_SALESPERSON");
			paramList.add(secondarySalesPersonID);
		}
		//
		if (secondarySalesPercentage != null) {
			fields.add("SECSALE_COMM_PERCENT");
			paramList.add(secondarySalesPercentage);
		}
		//
		if (tertiarySalesPersonID != null) {
			fields.add("TERTIARY_SALESPERSON");
			paramList.add(tertiarySalesPersonID);
		}
		//
		if (tertiarySalesPercentage != null) {
			fields.add("TERSALE_COMM_PERCENT");
			paramList.add(tertiarySalesPercentage);
		}
		//
		if (primaryRecruiterID != null) {
			fields.add("PRIMARY_RECRUITER");
			paramList.add(primaryRecruiterID);
		}
		//
		if (primaryRecruiterPercentage != null) {
			fields.add("PRIREC_COMM_PERCENT");
			paramList.add(primaryRecruiterPercentage);
		}
		//
		if (secondaryRecruiterID != null) {
			fields.add("SECONDARY_RECRUITER");
			paramList.add(secondaryRecruiterID);
		}
		//
		if (secondaryRecruiterPercentage != null) {
			fields.add("SECREC_COMM_PERCENT");
			paramList.add(secondaryRecruiterPercentage);
		}
		//
		if (tertiaryRecruiterID != null) {
			fields.add("TERTIARY_RECRUITER");
			paramList.add(tertiaryRecruiterID);
		}
		//
		if (tertiaryRecruiterPercentage != null) {
			fields.add("TERREC_COMM_PERCENT");
			paramList.add(tertiaryRecruiterPercentage);
		}
		//
		fields.add("CREATED_BY");
		paramList.add(jobDivaSession.getRecruiterId());
		//
		if (fields.size() > 0) {
			//
			String sqlUpdate = " UPDATE temployee_billingrecord SET datecreated = sysdate, " + sqlUpdateFields(fields)//
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
		} else {
			throw new Exception("Empty Paramters!");
		}
		//
		return true;
	}
}

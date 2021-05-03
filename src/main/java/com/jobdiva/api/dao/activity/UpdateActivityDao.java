package com.jobdiva.api.dao.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.model.Activity;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.utils.DateUtils;

@Component
public class UpdateActivityDao extends AbstractActivityDao {
	
	@Autowired
	ActivityDao				activityDao;
	//
	@Autowired
	ActivityUserFieldsDao	activityUserFieldsDao;
	
	public Boolean updateStart(JobDivaSession jobDivaSession, Long startid, Boolean overwrite, Date startDate, Date endDate, String positiontype, Double billrate, //
			String billratecurrency, String billrateunit, Double payrate, String payratecurrency, String payrateunit, Userfield[] userfields) throws Exception {
		//
		overwrite = overwrite != null ? overwrite : false;
		//
		StringBuffer message = new StringBuffer();
		if (overwrite && startDate == null && endDate != null)
			message.append("Error: Start Date should not be empty if End Date is set( and overwirte = true ).");
		if (isNotEmpty(positiontype)) {
			if (positiontype.length() > 20)
				message.append("Error: Position type should be less than 20 characters. \r\n");
			else if (positiontype.trim().length() == 0)
				positiontype = null;
		}
		//
		if (billrate != null && billrate <= 0)
			message.append("Error: Bill rate should be a positive number");
		//
		if (billratecurrency != null) {
			if (billratecurrency.trim().length() == 0)
				billratecurrency = null;
			if (billratecurrency.length() > 20)
				message.append("Error: Bill rate currency should be less than 20 characters. \r\n");
		}
		//
		if (billrateunit != null && billrateunit.trim().length() == 0)
			billrateunit = null;
		//
		if (payrate != null) {
			if (payrate <= 0)
				message.append("Error: Pay rate should be a positive number");
		}
		if (payratecurrency != null) {
			if (payratecurrency.trim().length() == 0)
				payratecurrency = null;
			else if (payratecurrency.length() > 20)
				message.append("Error: Pay rate currency should be less than 20 characters. \r\n");
		}
		//
		if (payrateunit != null && payrateunit.trim().length() == 0)
			payrateunit = null;
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		Activity activity = activityDao.getActivity(jobDivaSession, startid);
		//
		// checking
		if (activity == null || activity.getRecruiterTeamId() != jobDivaSession.getTeamId())
			throw new Exception("Error: Activity " + startid + " is not found.");
		//
		Date datehired = startDate;
		Date endDateDate = endDate;
		//
		if (datehired != null && DateUtils.compareDateIgnoringTime(datehired, activity.getDatePresented()) < 0)
			throw new Exception("Error: Hire Date " + datehired + " should not be set before Presented Date: " + activity.getDatePresented());
		//
		long candidateid = activity.getCandidateId();
		long rfqid = activity.getJobId();
		Date currentTS = new Date();
		// hash currency and rate units into currencyUnitHash, payUnitHash,
		// billUnitHash
		if (overwrite || isNotEmpty(payratecurrency) || isNotEmpty(payrateunit) || isNotEmpty(billratecurrency) || isNotEmpty(billrateunit))
			assignCurrencyRate(jobDivaSession);
		//
		Integer contract = getContractId(positiontype);
		//
		if (activity.getContract() == null)
			activity.setContract(0);
		//
		boolean startNew = false;
		boolean chageContract = false;
		boolean sync_calendar = false;
		//
		if (activity.getDateHired() == null && datehired != null)
			startNew = true;
		//
		if ((activity.getDateHired() != null || datehired != null) && !activity.getContract().equals(contract))
			chageContract = true;
		//
		if (overwrite != null && overwrite) {
			if (!activity.getContract().equals(contract))
				chageContract = true;
			//
			if (startNew || !activity.getDateHired().equals(datehired))
				sync_calendar = true;
			//
			activity.setDateHired(datehired);
			activity.setDateEnded(endDateDate);
			updateEnddateAction(jobDivaSession, endDateDate, activity.getCandidateTeamId(), jobDivaSession.getRecruiterId(), activity.getCandidateId());
			activity.setContract(contract);
		} else {
			//
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date null_date = new Date(sdf.parse("1980-01-01").getTime());
			if (datehired != null) {
				if (startNew || !activity.getDateHired().equals(datehired))
					sync_calendar = true;
				//
				activity.setDateHired(datehired);
				//
				if (endDateDate != null) {
					if (endDateDate.before(null_date)) {
						activity.setDateEnded(null);
					} else {
						activity.setDateEnded(endDateDate);
						//
						updateEnddateAction(jobDivaSession, endDateDate, activity.getCandidateTeamId(), jobDivaSession.getRecruiterId(), activity.getCandidateId());
					}
				} else if (activity.getDateEnded() != null && DateUtils.compareDateIgnoringTime(datehired, activity.getDateEnded()) > 0) {
					throw new Exception("Error: Hire Date " + datehired + " should not be set after End Date " + activity.getDateEnded());
				}
			} else if (endDateDate != null) {
				if (endDateDate.before(null_date)) {
					activity.setDateEnded(null);
				} else {
					if (activity.getDateHired() != null && DateUtils.compareDateIgnoringTime(endDateDate, activity.getDateHired()) < 0)
						throw new Exception("Error: End Date " + endDateDate + " should not be set before Hire Date " + activity.getDateHired());
					else if (activity.getDateHired() == null)
						throw new Exception("Error: Please also set Start Date since it is empty at the moment. \r\n");
					//
					activity.setDateEnded(endDateDate);
					//
					updateEnddateAction(jobDivaSession, endDateDate, activity.getCandidateTeamId(), jobDivaSession.getRecruiterId(), activity.getCandidateId());
					//
				}
			}
			if (contract != null) {
				activity.setContract(contract);
			}
			//
		}
		//
		activity.setDateUpdated(currentTS);
		//
		// update the last employment flag
		updateLastEmployee(jobDivaSession, activity);
		// update employee flag
		if (startNew)
			assignCurrentEmployee(getRfqServletLocation(), activity, activity.getContract());
		else if (chageContract)
			assignCurrentEmployee(getRfqServletLocation(), activity, contract);
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		// update rates, units and currencies
		if (overwrite != null && overwrite) {
			if (billUnitHash.isEmpty()) {
				assignCurrencyRate(jobDivaSession);
			}
			// update all pay rates and bill rates
			String sqlUpdate = "update TINTERVIEWSCHEDULE set " //
					+ " PAY_HOURLY = ?, " //
					+ " FINALBILLRATE_CURRENCY  = ?, " //
					+ " FINALBILLRATEUNIT = ?, " //
					+ " HOURLY = ?, " //
					+ " HOURLY_CURRENCY = ?, " //
					+ " PAYRATEUNITS = ? " //
					+ "where " //
					+ " CANDIDATEID = ? " //
					+ " and RFQID = ? " //
					+ " and RECRUITER_TEAMID = ?";
			//
			Object[] params = new Object[] { payrate, //
					getCurrencyCode(billratecurrency), //
					getRateUnitCode(billUnitHash, billrateunit), //
					billrate, //
					getCurrencyCode(payratecurrency), //
					getRateUnitCode(payUnitHash, payrateunit), //
					candidateid, rfqid, jobDivaSession.getTeamId() };
			jdbcTemplate.update(sqlUpdate, params);
		} else if ((billrate != null && billrate > 0) // payhourly
				|| isNotEmpty(billratecurrency) // FinalbillrateCurrency
				|| isNotEmpty(billrateunit) // Finalbillrateunit
				|| (payrate != null && payrate > 0) // Hourly
				|| isNotEmpty(payratecurrency) // HourlyCurrency
				|| isNotEmpty(payrateunit)) {
			//
			ArrayList<String> fields = new ArrayList<String>();
			ArrayList<Object> paramList = new ArrayList<Object>();
			//
			String sqlUpdate = "UPDATE TINTERVIEWSCHEDULE SET ";
			// set bill rate, currency or unit
			if (billrate != null && billrate > 0) {
				fields.add("PAY_HOURLY");
				paramList.add(billrate);
			}
			//
			if (isNotEmpty(billratecurrency)) {
				fields.add("FINALBILLRATE_CURRENCY");
				paramList.add(getCurrencyCode(billratecurrency));
			}
			//
			if (isNotEmpty(billrateunit)) {
				fields.add("FINALBILLRATEUNIT");
				paramList.add(getRateUnitCode(billUnitHash, billrateunit));
			}
			// set pay rate, currency or unit
			if ((payrate != null && payrate > 0)) {
				fields.add("HOURLY");
				paramList.add(payrate);// set pay rate
			}
			//
			if (isNotEmpty(payratecurrency)) {
				fields.add("HOURLY_CURRENCY");
				paramList.add(getCurrencyCode(payratecurrency));
			}
			if (isNotEmpty(payrateunit)) {
				fields.add("payrateunits");
				paramList.add(getRateUnitCode(billUnitHash, payrateunit));
			}
			sqlUpdate += sqlUpdateFields(fields)//
					+ " WHERE  " //
					+ " CANDIDATEID = ? " //
					+ " and RFQID = ? " //
					+ " and RECRUITER_TEAMID = ?";
			//
			paramList.add(candidateid);
			paramList.add(rfqid);
			paramList.add(jobDivaSession.getTeamId());
			Object[] parameters = paramList.toArray();
			jdbcTemplate.update(sqlUpdate, parameters);
		}
		//
		//
		if (userfields != null && userfields.length > 0) {
			//
			//
			validateUserFields(jobDivaSession, jobDivaSession.getTeamId(), userfields, UDF_FIELDFOR_ACTIVITY);
			//
			//
			for (Userfield userfield : userfields) {
				//
				Boolean existActivityUDF = activityUserFieldsDao.existActivityUDF(jobDivaSession, startid, userfield.getUserfieldId());
				//
				if (isEmpty(userfield.getUserfieldValue())) {
					if (existActivityUDF)
						activityUserFieldsDao.deleteActivityUDF(jobDivaSession, startid, userfield.getUserfieldId());
				} else {
					//
					if (existActivityUDF) {
						activityUserFieldsDao.updateActivityUDF(startid, userfield.getUserfieldId(), jobDivaSession.getTeamId(), currentTS, userfield.getUserfieldValue());
					} else {
						activityUserFieldsDao.insertActivityUDF(startid, userfield.getUserfieldId(), jobDivaSession.getTeamId(), currentTS, userfield.getUserfieldValue());
					}
				}
			}
		}
		//
		//
		//
		//
		if (sync_calendar) {
			//
			try {
				syncCalendar(jobDivaSession.getTeamId(), activity.getId(), 0, jobDivaSession.getEnvironment(), jobDivaSession.getUserName(), jobDivaSession.getPassword());
			} catch (Exception e) {
				logger.info("syncCalendar Error : " + e.getMessage());
			}
			//
		} //
		return true;
	}
}

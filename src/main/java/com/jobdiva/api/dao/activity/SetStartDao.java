package com.jobdiva.api.dao.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.jobdiva.api.model.Timezone;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.servlet.ServletTransporter;

@Component
public class SetStartDao extends AbstractActivityDao {
	
	public Boolean setStart(JobDivaSession jobDivaSession, Long submittalid, Long recruiterid, Date startDate, Date endDate, Timezone timezone, String internalnotes) throws Exception {
		//
		StringBuffer message = new StringBuffer();
		// StringBuffer warning = new StringBuffer();
		// Data Validation
		//
		//
		if (submittalid < 1)
			message.append("Invalid submittal id (" + submittalid + "). ");
		//
		if (recruiterid != null && recruiterid < 1) {
			message.append("Invalid recruiter id (" + recruiterid + "). ");
		}
		//
		String dateFormat = "MM/dd/yyyy";
		String startDateStr = null;
		DateFormat df = new SimpleDateFormat(dateFormat);
		//
		try {
			startDateStr = df.format(startDate.getTime());
		} catch (Exception e) {
			message.append("Invalid start date. ");
		}
		//
		String endDateStr = null;
		if (endDate != null) {
			try {
				endDateStr = df.format(endDate.getTime());
			} catch (Exception e) {
				message.append("Invalid end date. ");
			}
		}
		//
		// if (timezone == null) {
		// message.append("Invalid timezone (" + tz.getValue() + "). ");
		// }
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		recruiterid = recruiterid != null ? recruiterid : jobDivaSession.getRecruiterId();
		//
		String status = "";
		String strMessage = "";
		try {
			HashMap<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("method", new Integer(216)); // addHire
			reqMap.put("env", new Integer(jobDivaSession.getEnvironment()));
			reqMap.put("teamid", jobDivaSession.getTeamId());
			reqMap.put("submittalid", new Long(submittalid));
			reqMap.put("recruiterid", new Long(recruiterid));
			reqMap.put("dateformat", dateFormat);
			reqMap.put("startdate", startDateStr);
			//
			if (endDateStr instanceof String && endDateStr.length() > 0)
				reqMap.put("enddate", endDateStr);
			//
			reqMap.put("timezone", timezone.getValue());
			//
			if (isNotEmpty(internalnotes))
				reqMap.put("internalnotes", internalnotes);
			//
			Object retObj = ServletTransporter.callServlet(appProperties.getAppWsLocation() + "/appws/servlet/MarketplaceAPIServlet", reqMap);
			if (retObj instanceof Boolean) {
				return true;
			} else {
				status = "setStart Failed";
				//
				if (retObj instanceof Exception) {
					throw new Exception(((Exception) retObj).toString());
				} else {
					saveAccessLog(recruiterid, jobDivaSession.getLeader(), recruiterid, "setStart", status + ", " + strMessage);
				}
				return false;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		///
	}
}

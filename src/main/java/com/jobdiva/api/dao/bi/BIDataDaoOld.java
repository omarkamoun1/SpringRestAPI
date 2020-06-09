package com.jobdiva.api.dao.bi;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.bidata.BiData;
import com.jobdiva.api.servlet.ServletTransporter;
import com.jobdiva.api.utils.DateUtils;
import com.jobdiva.api.utils.StringUtils;

@Component
public class BIDataDaoOld extends AbstractJobDivaDao {
	
	private String adjustMetricName(String metricName) {
		if (metricName.startsWith("CANNED "))
			metricName = metricName.substring(7);
		return metricName;
	}
	
	private String[] adjustParameters(String[] parameters) {
		if (parameters != null) {
			String[] newParameters = new String[parameters.length];
			//
			for (int i = 0; i < parameters.length; i++) {
				newParameters[i] = StringUtils.escapeQuote(parameters[i]);
			}
			//
			return newParameters;
		}
		return null;
	}
	
	private Boolean validDate(Date fromDate, Date toDate) {
		//
		if (fromDate != null && toDate != null) {
			LocalDate fromLocalDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate toLocalDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			//
			long noOfDaysBetween = ChronoUnit.DAYS.between(fromLocalDate, toLocalDate);
			//
			return noOfDaysBetween <= 14;
		}
		//
		return true;
		//
	}
	
	@SuppressWarnings("deprecation")
	private String adjustToDate(Date toDate) {
		if (toDate != null) {
			// Adjust To Date at End of the Day
			if (toDate.getHours() == 0 && toDate.getMinutes() == 0 && toDate.getSeconds() == 0) {
				toDate.setHours(23);
				toDate.setMinutes(59);
				toDate.setSeconds(59);
			}
			//
			return DateUtils.formatDate(this.appProperties.getServletDateFormat(), toDate);
		} else {
			return "";
		}
	}
	
	private String adjustFromDate(Date fromDate) {
		if (fromDate != null) {
			// fromDate.setHours(0);
			// fromDate.setMinutes(0);
			// fromDate.setSeconds(0);
			return DateUtils.formatDate(this.appProperties.getServletDateFormat(), fromDate);
		} else {
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	private Vector<Object> requestBiDataFromServlet(Vector<Object> reqData) throws Exception {
		//
		String servletURL = "http://" + this.appProperties.getServletBiDataIp() + "/BIData/servlet/GetBIDataServlet";
		//
		com.axelon.oc4j.ServletRequestData srd = new com.axelon.oc4j.ServletRequestData(0L, reqData);
		//
		return (Vector<Object>) ServletTransporter.callServlet(servletURL, srd);
		//
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BiData getBIData(JobDivaSession jobDivaSession, String metricName, Date fromDate, Date toDate, String[] parameters) {
		//
		//
		metricName = adjustMetricName(metricName);
		parameters = adjustParameters(parameters);
		//
		//
		BiData biData = new BiData();
		//
		//
		Boolean validateDate = validDate(fromDate, toDate);
		if (!validateDate) {
			biData.setMessage("Date range more than 14 days, metric name = " + metricName);
			return biData;
		}
		//
		Vector<Object> reqData = new Vector<Object>();
		reqData.add(jobDivaSession.getTeamId() + "");
		reqData.add(jobDivaSession.getUserName());
		reqData.add(jobDivaSession.getPassword());
		reqData.add(metricName);
		reqData.add(adjustFromDate(fromDate));
		reqData.add(adjustToDate(toDate));
		reqData.add(parameters);
		//
		try {
			Vector<Object> rspData = requestBiDataFromServlet(reqData);
			//
			biData.setMessage((String) rspData.elementAt(0));
			//
			if (rspData.size() > 1) {
				Vector d = (Vector) rspData.elementAt(1);
				String[] cols = (String[]) d.elementAt(0);
				biData.assignColumnNames(Arrays.asList(cols));
				//
				biData.setData(d);
				// if (d.size() > 1) {
				// //
				// for (int i = 1; i < d.size(); i++) {
				// Record record = biData.addRecord();
				// //
				// String[] values = (String[]) d.elementAt(i);
				// //
				// for (int j = 0; j < values.length; j++) {
				// String columnName = cols[j];
				// String columnValue = values[j];
				// record.addValue(columnName, columnValue);
				// }
				// }
				// //
				// }
			}
			//
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new BiData(e.getMessage());
		}
		return biData;
	}
}

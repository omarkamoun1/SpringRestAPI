package com.jobdiva.api.dao.bi;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.bidata.BiData;

@Component
public class BIDataDao extends AbstractBIDataDao {
	
	private static Logger log = LoggerFactory.getLogger(BIDataDao.class);
	
	private String adjustMetricName(String metricName) {
		if (metricName.startsWith("CANNED "))
			metricName = metricName.substring(7);
		return metricName;
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
	
	public void refreshTeamUserHM(Connection conn, HashMap<Long, int[]> TEAMUSER_HM, long clientID, int today) {
		if (TEAMUSER_HM.get(clientID) == null) {
			TEAMUSER_HM.put(clientID, new int[] { 0, 0 });
		}
		int[] data = TEAMUSER_HM.get(clientID);
		if (data[1] < today) {
			synchronized (data) {
				data[0] = BiDataQuery.getUserCount(conn, clientID);
				data[1] = today;
				TEAMUSER_HM.put(clientID, data);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public BiData getBIData(JobDivaSession jobDivaSession, String metricName, Date from, Date to, String[] parameters) {
		//
		BiData biData = new BiData();
		//
		Boolean validateDate = validDate(from, to);
		if (!validateDate) {
			biData.setMessage("Date range more than 14 days, metric name = " + metricName);
			return biData;
		}
		//
		metricName = adjustMetricName(metricName);
		//
		// Original Column Name -> Alias; solve issue that column name is too
		// long (ORA-00972)
		Map<String, String> colNameToAliasMap = new HashMap<String, String>();
		// Alias -> Original Column Name
		Map<String, String> aliasToColNameMap = new HashMap<String, String>();
		//
		HashMap<Long, int[]> TEAMUSER_HM = new HashMap<Long, int[]>();
		//
		Vector<Object> param = new Vector<Object>();
		String fromDate = null;
		String toDate = null;
		try {
			// Adjust To Date at End of the Day
			if (to != null && to.getHours() == 0 && to.getMinutes() == 0 && to.getSeconds() == 0) {
				to.setHours(23);
				to.setMinutes(59);
				to.setSeconds(59);
			}
			//
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			fromDate = from != null ? dateFormat.format(from) : null;
			toDate = to != null ? dateFormat.format(to) : null;
			//
			//
			//
			String rspStr = BiDataQuery.validate(metricName, fromDate, toDate, parameters);
			if (rspStr != null) {
				biData.setMessage(rspStr);
				return biData;
			}
		} catch (Exception e) {
			biData.setMessage("Error: Invalid Request Parameter (" + e.getMessage() + ")");
			return biData;
		}
		//
		//
		String sql = null;
		Connection maindb_con = null;
		Connection clickdb_con = null;
		Connection minerdb_con = null;
		Connection attachmentdb_con = null;
		Connection docdb_con = null;
		Connection emailSaveDb_con = null;
		Connection emailSaveDb_con_old = null;
		//
		try {
			//
			maindb_con = getMainDbConnection_teamID(jobDivaSession.getTeamId());
			int today = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			//
			if (TEAMUSER_HM.get(jobDivaSession.getTeamId()) == null || TEAMUSER_HM.get(jobDivaSession.getTeamId())[1] < today) {
				refreshTeamUserHM(maindb_con, TEAMUSER_HM, jobDivaSession.getTeamId(), today);
			}
			//
			int usercount = TEAMUSER_HM.get(jobDivaSession.getTeamId())[0];
			if (usercount == 0)
				usercount = 10;
			//
			boolean queryEmailDB = false;
			//
			// authenticate
			String[] restriction = new String[1];
			long userid = BiDataQuery.authenticate(maindb_con, jobDivaSession.getTeamId(), jobDivaSession.getUserName(), jobDivaSession.getPassword(), metricName, restriction, usercount);
			//
			log.info("[[RECRUITERID]]" + userid + "[[/RECRUITERID]][[TEAMID]]" + jobDivaSession.getTeamId() //
					+ "[[/TEAMID]][[ACTIONCODE]]155[[/ACTIONCODE]]" //
					+ "[[CRITERIA]]BI metric - " + metricName //
					+ ", date range: " + from + ", " + to + "[[/CRITERIA]]");
			//
			//
			long teamid = jobDivaSession.getTeamId();
			//
			if ((metricName.startsWith("Advantage") && teamid != 22 && teamid != 152 && teamid != 185 && teamid != 219)//
					|| (metricName.startsWith("TCML") && teamid != 68) //
					|| (metricName.startsWith("MISI") && teamid != 96) //
					|| (metricName.startsWith("ICS") && teamid != 74) //
					|| (metricName.startsWith("ETeam") && teamid != 471)) {
				biData.setMessage("Error: Invalid username/password");
			} else {
				Connection db_con = null;
				if (metricName.indexOf("Access Log") > -1) {
					clickdb_con = getClickDbConnection();
					clickdb_con.commit();
					db_con = clickdb_con;
				} else if (metricName.contains("Jobs Posted") || metricName.equals("Jobs Currently Posted")) {
					minerdb_con = getMinerDbConnection(); // Miner DB -- Job
															// Posted
					db_con = minerdb_con;
					db_con.commit();
				} else if (metricName.indexOf("Resume Detail") > -1) {
					int dbid = BiDataQuery.getDocDBID(maindb_con, jobDivaSession.getTeamId(), parameters);
					log.info("BIData Resume Detail metric, jobDivaSession.getTeamId() = " + jobDivaSession.getTeamId() + ", params[0] = " + parameters[0] + ", document in DocDB ID = " + dbid);
					if (dbid == 0)
						throw new Exception("Invalid Resume ID");
					docdb_con = getDocDbConnection(dbid); // Oracle DocDB: dbid
															// = 1, 2; MySQL
															// DocDB: dbid = 3
					docdb_con.commit();
					db_con = docdb_con;
				} else if (metricName.contains("Email")) {
					emailSaveDb_con = getEmailSaveDbConnection();
					emailSaveDb_con.commit();
					try {
						emailSaveDb_con_old = getEmailSave2DbConnection();
						emailSaveDb_con_old.commit();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					db_con = emailSaveDb_con;
					queryEmailDB = true;
				} else {
					db_con = maindb_con;
				}
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
				// Construct Query
				try {
					sql = BiDataQuery.constructQuery(metricName, jobDivaSession.getTeamId(), fromDate, toDate, parameters, param, restriction, colNameToAliasMap);
				} catch (Exception e) {
					biData.setMessage("Error: " + e.getMessage());
					return biData;
				}
				Vector<String[]> data = null;
				if (queryEmailDB) {
					data = BiDataQuery.getQueryData(emailSaveDb_con, jobDivaSession.getTeamId(), sql, param, aliasToColNameMap);
					if (emailSaveDb_con_old != null) {
						Vector<String[]> oldData = BiDataQuery.getQueryData(emailSaveDb_con_old, jobDivaSession.getTeamId(), sql, param, aliasToColNameMap);
						if (oldData != null && oldData.size() > 0)
							oldData.remove(0);
						data.addAll(oldData);
					}
				} else {
					data = BiDataQuery.getQueryData(db_con, jobDivaSession.getTeamId(), sql, param, aliasToColNameMap);
					//
				}
				if (metricName.indexOf("ETeam Submittal Metrics by Labor Category") > -1) {
					data = BiDataQuery.processOutputETeam(data);
				}
				if (metricName.indexOf("Candidate On-Boarding Document Detail") > -1 || metricName.indexOf("Candidate Attachment Detail") > -1) {
					//
					attachmentdb_con = getAttachmentDbConnection();
					attachmentdb_con.commit();
					db_con = attachmentdb_con;
					if (data.size() > 1) {
						String[] row = (String[]) data.get(1);
						//
						// for (int i = 0; i < row.length; i++) {
						// System.out.print(row[i]);
						// }
						//
						long docID = new Long(row[0]).longValue();
						// System.out.println("Candidate On-Boarding Document
						// Detail - docID = " + docID);
						if (metricName.indexOf("Candidate On-Boarding Document Detail") > -1) {
							data = BiDataQuery.retrieveDocument(db_con, "tcandidate_onboarding_docs_blob", data, docID, jobDivaSession.getTeamId().longValue());
						} else if (metricName.indexOf("Candidate Attachment Detail") > -1) {
							data = BiDataQuery.retrieveDocument(db_con, "tcandidate_attachments_blob", data, docID, jobDivaSession.getTeamId().longValue());
						}
					}
				}
				biData.setMessage("Query \"" + metricName + "\" completed successfully");
				biData.setData(data);
			}
			//
		} catch (Exception e) {
			log.error("BIData error in 3 " + e.getMessage());
			biData.setMessage("Error: " + e.getMessage());
		}
		try {
			if (maindb_con != null)
				maindb_con.close();
		} catch (Exception e1) {
		}
		try {
			if (clickdb_con != null)
				clickdb_con.close();
		} catch (Exception e1) {
		}
		try {
			if (minerdb_con != null)
				minerdb_con.close();
		} catch (Exception e1) {
		}
		try {
			if (attachmentdb_con != null)
				attachmentdb_con.close();
		} catch (Exception e1) {
		}
		try {
			if (docdb_con != null)
				docdb_con.close();
		} catch (Exception e1) {
		}
		try {
			if (emailSaveDb_con != null)
				emailSaveDb_con.close();
		} catch (Exception e1) {
		}
		try {
			if (emailSaveDb_con_old != null)
				emailSaveDb_con_old.close();
		} catch (Exception e1) {
		}
		//
		//
		return biData;
	}
}

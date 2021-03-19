package com.jobdiva.api.dao.bi;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.Base64;

import com.arizon.shared.Encryption;
import com.axelon.shared.Crypto;
import com.axelon.shared.Zipper;

public class BiDataQuery {
	
	static Pattern	WIERD_CHARACTERS	= Pattern.compile("[^(\\p{Punct}|\\w|\\r|\\n)]", Pattern.CASE_INSENSITIVE);
	static Pattern	LINEBREAK			= Pattern.compile("\\r\\n|\\r|\\n");
	static Pattern	DOUBLE_QUOTE		= Pattern.compile("\"");
	
	@SuppressWarnings("resource")
	public static long authenticate(Connection con, long clientID, String username, String password, String metricName, String[] restriction, int usercount) throws Exception {
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		long userid = -1;
		try {
			ps = con.prepareStatement( //
					" select a.id, a.password, b.lastapicall, sysdate clock, a.s1 from trecruiter a, tteam b " + //
							" where a.groupid=? and a.email=? and a.active=1 and substr(a.permission2_recruiter, 31,1)='1' " + //
							"   and b.id = a.groupid ");
			ps.setLong(1, clientID);
			ps.setString(2, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				String db_password = rs.getString(2);
				String salt = rs.getString(5);
				boolean correctPassword = false;
				if (salt != null) {
					if (Encryption.hashStringWithSalt(password, salt).equals(db_password))
						correctPassword = true;
				} else if (password.equals(db_password))
					correctPassword = true;
				userid = rs.getLong(1);
				if (!correctPassword)
					throw new Exception("Invalid username/password");
				// check operation permission
				boolean allowed = true;
				ps1 = con.prepareStatement("select allowedoperation, divisionid from tapipermission where teamid=? and recruiterid=? ");
				ps1.setLong(1, clientID);
				ps1.setLong(2, userid);
				ResultSet rs1 = ps1.executeQuery();
				while (rs1.next()) {
					allowed = false;
					// System.out.println("found metric " + rs1.getString(1));
					if (rs1.getString(1).equals(metricName)) {
						// System.out.println("found match in tapipermission");
						allowed = true;
						if (rs1.getString(2) != null)
							restriction[0] = rs1.getString(2);
						// System.out.println(restriction[0]);
						break;
					}
				}
				rs1.close();
				ps1.close();
				if (!allowed) {
					throw new Exception("You are not allowed to query this BIData metric.");
				}
				Timestamp lastapicall = rs.getTimestamp(3);
				Timestamp clock = rs.getTimestamp(4);
				// System.out.println("BIData clock = " + clock + ", lastapicall
				// = " + lastapicall + ", clientId = " + clientID);
				int param = 12;
				long threshold = 180000L;
				if (usercount >= 10 & usercount < 20) {
					param = 15;
					threshold = 200000L;
				} else if (usercount >= 20 && usercount < 50) {
					param = 30;
					threshold = 300000L;
				} else if (usercount >= 50 && usercount < 75) {
					param = 45;
					threshold = 450000L;
				} else if (usercount >= 100) {
					param = 60;
					threshold = 600000L;
				}
				// System.out.println("param = " + param + ", threshold = " +
				// threshold);
				if (lastapicall == null) {
					ps1 = con.prepareStatement("update tteam set lastapicall = sysdate where id=?");
					ps1.setLong(1, clientID);
					ps1.executeUpdate();
					ps1.close();
					con.commit();
				} else if (lastapicall != null && lastapicall.getTime() < clock.getTime()) {
					ps1 = con.prepareStatement("update tteam set lastapicall = greatest(sysdate, lastapicall + 1/24/60/" + param + ") where id=?");
					ps1.setLong(1, clientID);
					ps1.executeUpdate();
					ps1.close();
					con.commit();
				} else if (lastapicall != null && lastapicall.getTime() > clock.getTime() + threshold) {
					// if(clientID != 68 && clientID != 94 && clientID != 288) {
					// System.out.println("BIData API call blocked due to
					// excessive calls");
					throw new Exception("There are too many API requests in the queue. Please try again in 3 minutes.");
					// }
				} else {
					ps1 = con.prepareStatement("update tteam set lastapicall = lastapicall + 1/24/60/" + param + " where id=?");
					ps1.setLong(1, clientID);
					ps1.executeUpdate();
					ps1.close();
					con.commit();
				}
			} else {
				throw new Exception("Invalid username/password");
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e1) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e1) {
				}
			if (ps1 != null)
				try {
					ps1.close();
				} catch (Exception e1) {
				}
			if (e.getMessage().indexOf("too many API requests") < 0 && e.getMessage().indexOf("not allowed to query this BIData metric") < 0)
				throw new Exception("Authentication failed. " + e.getMessage());
			else
				throw e;
		}
		return userid;
	}
	
	public static Vector<String[]> getQueryData(Connection con, long clientID, String sql, Vector<Object> params, Map<String, String> aliasToColNameMap) throws Exception {
		Vector<String[]> data = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// System.out.println("BIData getQueryData query: " + sql);
			ps = con.prepareStatement(sql);
			ps.setQueryTimeout(600);
			for (int i = 0; i < params.size(); i++) {
				Object param = params.get(i);
				if (param instanceof Long) {
					ps.setLong(i + 1, ((Long) param).longValue());
				} else if (param instanceof Integer) {
					ps.setInt(i + 1, ((Integer) param).intValue());
				} else if (param instanceof java.sql.Date) {
					ps.setDate(i + 1, (java.sql.Date) param);
				} else if (param instanceof java.sql.Timestamp) {
					ps.setTimestamp(i + 1, (java.sql.Timestamp) param);
				} else if (param instanceof String) {
					ps.setString(i + 1, (String) param);
				}
			}
			//
			rs = ps.executeQuery();
			//
			// String csvOutput = processCSVOutput(con, rs, clientID,
			// aliasToColNameMap);
			// System.out.println(csvOutput);
			//
			data = processOutput(con, rs, clientID, aliasToColNameMap);
			//
			//
			rs.close();
			ps.close();
		} catch (SQLException sqle) {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			throw new Exception(sqle.getMessage());
		}
		return data;
	}
	
	public static Vector<String[]> processOutput(Connection con, ResultSet rs, long clientID, Map<String, String> aliasToColNameMap) throws SQLException {
		String[] cols = null;
		//
		Vector<String[]> data = new Vector<String[]>();
		//
		if (clientID == -1) {
		} else { // default data
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			cols = new String[numberOfColumns];
			for (int i = 0; i < numberOfColumns; i++) {
				String colName = rsmd.getColumnName(i + 1);
				cols[i] = aliasToColNameMap.containsKey(colName) ? aliasToColNameMap.get(colName) : colName;
			}
			data.add(cols);
			while (rs.next()) {
				String[] row = new String[numberOfColumns];
				for (int i = 0; i < numberOfColumns; i++) {
					String content = "";
					if (rsmd.getColumnType(i + 1) != java.sql.Types.BLOB && rsmd.getColumnType(i + 1) != java.sql.Types.LONGVARBINARY) {
						content = rs.getString(i + 1);
					}
					if (content == null)
						row[i] = "";
					else {
						if (rsmd.getColumnType(i + 1) == java.sql.Types.DATE) {
							//
							int idx_space = content.indexOf(" ");
							row[i] = content.substring(0, idx_space) + "T" + content.substring(idx_space + 1);
							int idx_dash = row[i].indexOf("-");
							if (idx_dash < 4) {
								for (int j = 4 - idx_dash; j > 0; j--)
									row[i] = "0" + row[i];
							}
						} else if (rsmd.getColumnType(i + 1) == java.sql.Types.CLOB) {
							try {
								row[i] = rs.getString(i + 1);
								//
								if (rsmd.getColumnName(i + 1).equals("POSTINGDESCRIPTION"))
									row[i] = URLDecoder.decode(row[i], "UTF-8");
								else if (rsmd.getColumnName(i + 1).equals("JOBDESCRIPTION") || rsmd.getColumnName(i + 1).equals("SUBMITTALINSTRUCTION"))
									row[i] = row[i].replace("<", "&lt;").replace(">", "&gt;");
							} catch (IOException ioe) {
								// System.out.println("IOException in
								// processOutput");
								// ioe.printStackTrace();
							}
							row[i] = WIERD_CHARACTERS.matcher(row[i]).replaceAll(" ");
						} else if (rsmd.getColumnType(i + 1) == java.sql.Types.BLOB) {
							try {
								Blob blob = rs.getBlob(i + 1);
								//
								java.io.InputStream is = blob.getBinaryStream();
								byte[] bytes = new byte[(int) blob.length()];
								is.read(bytes, 0, (int) blob.length());
								//
								byte[] encoded = null;
								if (rsmd.getColumnName(i + 1).equalsIgnoreCase("FILECONTENT_BASE64ENCODED") || rsmd.getColumnName(i + 1).equalsIgnoreCase("RESUME_BASE64ENCODED")) {
									byte[] unzipped = Zipper.unZipIt(bytes);
									encoded = Base64.encodeBase64(unzipped);
								} else
									encoded = Base64.encodeBase64(bytes);
								//
								// System.out.println(encoded);
								row[i] = new String(encoded, "US-ASCII");
							} catch (Exception e) {
								// System.out.println("Exception in
								// processOutput");
								// e.printStackTrace();
							}
						} else if (rsmd.getColumnType(i + 1) == java.sql.Types.LONGVARBINARY) {
							try {
								//
								byte[] bytes = rs.getBytes(i + 1);
								//
								byte[] encoded = null;
								if (rsmd.getColumnName(i + 1).equalsIgnoreCase("FILECONTENT_BASE64ENCODED")) {
									byte[] unzipped = Zipper.unZipIt(bytes);
									encoded = Base64.encodeBase64(unzipped);
								} else if (rsmd.getColumnName(i + 1).equalsIgnoreCase("EMAILOBJECT")) {
									encoded = Zipper.unZipIt(bytes);
								} else
									encoded = Base64.encodeBase64(bytes);
								//
								row[i] = new String(encoded, "US-ASCII");
							} catch (Exception e) {
								// System.out.println("Exception in
								// processOutput");
								// e.printStackTrace();
							}
						} else
							row[i] = WIERD_CHARACTERS.matcher(content).replaceAll(" ");
					}
				}
				data.add(row);
			}
		}
		return data;
	}
	
	protected static String processCSVOutput(Connection con, ResultSet rs, long clientID, Map<String, String> aliasToColNameMap) throws SQLException {
		StringJoiner cols = new StringJoiner(";");
		//
		StringJoiner rows = new StringJoiner("\r\n");
		//
		if (clientID == -1) {
		} else { // default data
			//
			ResultSetMetaData rsmd = rs.getMetaData();
			//
			int numberOfColumns = rsmd.getColumnCount();
			//
			for (int i = 0; i < numberOfColumns; i++) {
				String colName = rsmd.getColumnName(i + 1);
				cols.add("\"" + (aliasToColNameMap.containsKey(colName) ? aliasToColNameMap.get(colName) : colName) + "\"");
			}
			//
			while (rs.next()) {
				StringJoiner row = new StringJoiner(";");
				for (int i = 0; i < numberOfColumns; i++) {
					String content = "";
					String rowColValue = "";
					if (rsmd.getColumnType(i + 1) != java.sql.Types.BLOB && rsmd.getColumnType(i + 1) != java.sql.Types.LONGVARBINARY) {
						content = rs.getString(i + 1);
					}
					if (content == null)
						row.add("\"\"");
					else {
						if (rsmd.getColumnType(i + 1) == java.sql.Types.DATE) {
							//
							int idx_space = content.indexOf(" ");
							rowColValue = content.substring(0, idx_space) + "T" + content.substring(idx_space + 1);
							int idx_dash = rowColValue.indexOf("-");
							if (idx_dash < 4) {
								for (int j = 4 - idx_dash; j > 0; j--)
									rowColValue = "0" + rowColValue;
							}
						} else if (rsmd.getColumnType(i + 1) == java.sql.Types.CLOB) {
							try {
								rowColValue = rs.getString(i + 1);
								//
								if (rsmd.getColumnName(i + 1).equals("POSTINGDESCRIPTION"))
									rowColValue = URLDecoder.decode(rowColValue, "UTF-8");
								else if (rsmd.getColumnName(i + 1).equals("JOBDESCRIPTION") || rsmd.getColumnName(i + 1).equals("SUBMITTALINSTRUCTION"))
									rowColValue = rowColValue.replace("<", "&lt;").replace(">", "&gt;");
							} catch (IOException ioe) {
								// System.out.println("IOException in
								// processOutput");
								// ioe.printStackTrace();
							}
							rowColValue = WIERD_CHARACTERS.matcher(rowColValue).replaceAll(" ");
						} else if (rsmd.getColumnType(i + 1) == java.sql.Types.BLOB) {
							try {
								Blob blob = rs.getBlob(i + 1);
								//
								java.io.InputStream is = blob.getBinaryStream();
								byte[] bytes = new byte[(int) blob.length()];
								is.read(bytes, 0, (int) blob.length());
								//
								byte[] encoded = null;
								if (rsmd.getColumnName(i + 1).equalsIgnoreCase("FILECONTENT_BASE64ENCODED") || rsmd.getColumnName(i + 1).equalsIgnoreCase("RESUME_BASE64ENCODED")) {
									byte[] unzipped = Zipper.unZipIt(bytes);
									encoded = Base64.encodeBase64(unzipped);
								} else
									encoded = Base64.encodeBase64(bytes);
								//
								// System.out.println(encoded);
								rowColValue = new String(encoded, "US-ASCII");
							} catch (Exception e) {
								// System.out.println("Exception in
								// processOutput");
								// e.printStackTrace();
							}
						} else if (rsmd.getColumnType(i + 1) == java.sql.Types.LONGVARBINARY) {
							try {
								//
								byte[] bytes = rs.getBytes(i + 1);
								//
								byte[] encoded = null;
								if (rsmd.getColumnName(i + 1).equalsIgnoreCase("FILECONTENT_BASE64ENCODED")) {
									byte[] unzipped = Zipper.unZipIt(bytes);
									encoded = Base64.encodeBase64(unzipped);
								} else if (rsmd.getColumnName(i + 1).equalsIgnoreCase("EMAILOBJECT")) {
									encoded = Zipper.unZipIt(bytes);
								} else
									encoded = Base64.encodeBase64(bytes);
								//
								rowColValue = new String(encoded, "US-ASCII");
							} catch (Exception e) {
								// System.out.println("Exception in
								// processOutput");
								// e.printStackTrace();
							}
						} else
							rowColValue = WIERD_CHARACTERS.matcher(content).replaceAll(" ");
					}
					//
					row.add("\"" + rowColValue + "\"");
					//
				}
				rows.add(row.toString());
			}
		}
		return cols.toString() + "\r\n" + rows.toString();
	}
	
	public static Vector<String[]> processOutputETeam(Vector<String[]> data) throws SQLException {
		if (data.size() < 2)
			return data;
		Vector<String[]> res = new Vector<String[]>();
		res.add(data.get(0));
		int pos = 1, insertPos = 1;
		int count = 0;
		int issuedJobsTotal = 0, jobRespondedTotal = 0, submittalsTotal = 0, interviewsTotal = 0, hiresTotal = 0, hiresEnteredTotal = 0;
		String prevCompany = "";
		String prevCompanyId = "";
		// System.out.println(data.size());
		while (pos < data.size()) {
			String[] curJob = data.elementAt(pos);
			String curCompany = curJob[0];
			String curCompanyId = curJob[1];
			// System.out.println(curCompany);
			// System.out.println(curCompanyId);
			int issuedJobs = Integer.parseInt(curJob[3]);
			int jobResponded = Integer.parseInt(curJob[4]);
			int submittals = Integer.parseInt(curJob[6]);
			int interviews = Integer.parseInt(curJob[7]);
			int hires = Integer.parseInt(curJob[8]);
			int hiresEntered = Integer.parseInt(curJob[9]);
			if ((curCompany.equals(prevCompany) && curCompanyId.equals(prevCompanyId)) || prevCompany.length() == 0) {
				issuedJobsTotal += issuedJobs;
				jobRespondedTotal += jobResponded;
				submittalsTotal += submittals;
				interviewsTotal += interviews;
				hiresTotal += hires;
				hiresEnteredTotal += hiresEntered;
				prevCompany = curCompany;
				prevCompanyId = curCompanyId;
				pos++;
				res.add(curJob);
			} else {
				String[] total = new String[13];
				total[0] = prevCompany;
				total[1] = prevCompanyId;
				total[2] = "All";
				total[3] = Integer.toString(issuedJobsTotal);
				total[4] = Integer.toString(jobRespondedTotal);
				total[5] = "N/A";
				if (issuedJobsTotal != 0) {
					double responseRatio = 100 * jobRespondedTotal / (double) issuedJobsTotal;
					// total[5] = Double.toString(responseRatio);
					total[5] = String.format("%.2f", responseRatio);
				}
				total[6] = Integer.toString(submittalsTotal);
				total[7] = Integer.toString(interviewsTotal);
				total[8] = Integer.toString(hiresTotal);
				total[9] = Integer.toString(hiresEnteredTotal);
				total[10] = "N/A";
				total[11] = "N/A";
				total[12] = "N/A";
				if (submittalsTotal != 0) {
					double interviewSubmittalRatio = 100 * interviewsTotal / (double) submittalsTotal;
					total[10] = String.format("%.2f", interviewSubmittalRatio);
					double hiresSubmittalRatio = 100 * hiresTotal / (double) submittalsTotal;
					// total[12] = Double.toString(hiresSubmittalRatio);
					total[12] = String.format("%.2f", hiresSubmittalRatio);
				}
				if (interviewsTotal != 0) {
					double hiresInterviewRatio = 100 * hiresTotal / (double) interviewsTotal;
					// total[11] = Double.toString(hiresInterviewRatio);
					total[11] = String.format("%.2f", hiresInterviewRatio);
				}
				issuedJobsTotal = 0;
				jobRespondedTotal = 0;
				submittalsTotal = 0;
				interviewsTotal = 0;
				hiresTotal = 0;
				hiresEnteredTotal = 0;
				res.insertElementAt(total, insertPos);
				prevCompany = curCompany;
				prevCompanyId = curCompanyId;
				count++;
				insertPos = pos + count;
			}
		}
		String[] total = new String[13];
		total[0] = prevCompany;
		total[1] = prevCompanyId;
		total[2] = "All";
		total[3] = Integer.toString(issuedJobsTotal);
		total[4] = Integer.toString(jobRespondedTotal);
		total[5] = "N/A";
		if (issuedJobsTotal != 0) {
			double responseRatio = 100 * jobRespondedTotal / (double) issuedJobsTotal;
			// total[5] = Double.toString(responseRatio);
			total[5] = String.format("%.2f", responseRatio);
		}
		total[6] = Integer.toString(submittalsTotal);
		total[7] = Integer.toString(interviewsTotal);
		total[8] = Integer.toString(hiresTotal);
		total[9] = Integer.toString(hiresEnteredTotal);
		total[10] = "N/A";
		total[11] = "N/A";
		total[12] = "N/A";
		if (submittalsTotal != 0) {
			double interviewSubmittalRatio = 100 * interviewsTotal / (double) submittalsTotal;
			total[10] = String.format("%.2f", interviewSubmittalRatio);
			double hiresSubmittalRatio = 100 * hiresTotal / (double) submittalsTotal;
			// total[12] = Double.toString(hiresSubmittalRatio);
			total[12] = String.format("%.2f", hiresSubmittalRatio);
		}
		if (interviewsTotal != 0) {
			double hiresInterviewRatio = 100 * hiresTotal / (double) interviewsTotal;
			// total[11] = Double.toString(hiresInterviewRatio);
			total[11] = String.format("%.2f", hiresInterviewRatio);
		}
		res.insertElementAt(total, insertPos);
		// System.out.println(res.size());
		return res;
	}
	
	private static String checkDetailMetricName(String metricName, String fromDate, String toDate, String[] params) {
		if (metricName.equals("Company Detail") || metricName.equals("Companies Detail") || metricName.equals("Company Addresses Detail") || //
				metricName.equals("Contact Detail") || metricName.equals("Contacts Detail") || metricName.equals("Contact Addresses Detail") || //
				metricName.equals("V2 Contact Detail") || metricName.equals("V2 Contacts Detail") || //
				metricName.equals("Candidate Detail") || metricName.equals("Candidates Detail") || metricName.equals("Resume Detail") || //
				metricName.equals("Candidate Qualifications Detail") || metricName.equals("Candidates Qualifications Detail") || //
				metricName.equals("Candidate Availability Detail") || metricName.equals("Candidates Availability Detail") || //
				metricName.equals("Candidate EEO Detail") || //
				metricName.equals("Job Detail") || metricName.equals("Jobs Detail") || //
				metricName.equals("Job Contacts Detail") || metricName.equals("Jobs Contacts Detail") || //
				metricName.equals("Job Users Detail") || metricName.equals("Jobs Users Detail") || //
				metricName.equals("Submittal Detail") || metricName.equals("Submittals Detail") || //
				metricName.equals("Job Submittals Detail") || metricName.equals("Jobs Submittals Detail") || metricName.equals("Candidate Submittals Detail") || //
				metricName.equals("Company Owners Detail") || metricName.equals("Contact Owners Detail") || //
				metricName.equals("Companies Owners Detail") || metricName.equals("Contacts Owners Detail") || //
				metricName.equals("Companies Types Detail") || metricName.equals("Contacts Types Detail") || //
				metricName.equals("Invoice Detail") || metricName.equals("Placement Detail") || //
				metricName.equals("Events Attendees Detail") || metricName.equals("Users Tasks Detail") || metricName.equals("Timesheet Detail") || //
				metricName.equals("Company Note Detail") || metricName.equals("Company Notes Detail") || //
				metricName.equals("Contact Note Detail") || metricName.equals("Contact Notes Detail") || //
				metricName.equals("Candidate Note Detail") || metricName.equals("Candidate Notes Detail") || //
				metricName.equals("Candidate On-Boarding Document Detail") || metricName.equals("Candidate Attachment Detail") || //
				metricName.equals("Candidate Resume Source Detail") || metricName.equals("Candidate Resumes Detail") || //
				metricName.equals("Candidates Resumes Detail") || metricName.equals("Billing Records Detail") || //
				metricName.equals("Salary Records Detail") || metricName.equals("Job Applicants Detail") || //
				metricName.equals("Contact Hotlist Detail") || metricName.equals("Candidate Hotlist Detail") || //
				metricName.equals("User Contact Hotlist Detail") || metricName.equals("User Candidate Hotlist Detail") || //
				metricName.equals("Contact Hotlists Detail") || metricName.equals("Candidate Hotlists Detail") || //
				metricName.equals("Candidate Notes List Detail") || metricName.equals("Candidate HR Detail") || //
				metricName.equals("Candidates HR Detail") || metricName.equals("Email Detail")) { //
			if (params == null || params.length < 1) {
				return "Error: Insufficient Parameter.  Please provide record ID.";
			} else { // check record id
				if (metricName.equals("Resume Detail") || metricName.equals("Timesheet Detail"))
					return null; // resume global id, timesheet externlid is not
									// a number
				try {
					// long recordid =
					Long.parseLong(params[0]);
				} catch (Exception e) {
					return "Error: Invalid record ID: " + params[0];
				}
				if (params.length > 100) {
					if (metricName.equals("Companies Detail") || metricName.equals("Contacts Detail") || metricName.equals("Jobs Detail") || //
							metricName.equals("Jobs Contacts Detail") || metricName.equals("Jobs Users Detail") || //
							metricName.equals("Candidates Detail") || metricName.equals("Candidates Availability Detail") || //
							metricName.equals("Submittals Detail") || metricName.equals("Candidates Qualifications Detail") || //
							metricName.equals("Events Attendees Detail") || //
							metricName.equals("Contact Hotlists Detail") || metricName.equals("Candidate Hotlists Detail") || //
							metricName.equals("Candidates HR Detail") || metricName.equals("Jobs Submittals Detail") || //
							metricName.equals("Candidates Resumes Detail")) { //
						try {
							// long recordid =
							Long.parseLong(params[100]);
							return "Error:  Please provide no more than 100 record IDs at a time.";
						} catch (Exception e) {
							return null; // the extra parameters are UDF
						}
					}
					return "Error:  Please provide no more than 100 record IDs at a time.";
				}
			}
			if (metricName.equals("Candidate On-Boarding Document Detail")) {
				try {
					// long interviewid =
					Long.parseLong(params[1]);
				} catch (Exception e) {
					return "Error: Invalid start ID: " + params[1];
				}
				if (params.length < 3 || params[2] == null) {
					return "Error: Please provide the On-Boarding document file name.";
				}
			}
			if (metricName.equals("Candidate Attachment Detail")) {
				if (params.length < 2 || params[1] == null) {
					return "Error: Please provide the attachment document file name.";
				}
			}
		} else if (metricName.equals("Billing Record Detail") || metricName.equals("Salary Record Detail") || //
				metricName.equals("Employee Timesheet Image Detail") || metricName.equals("Employee Timesheet Image by Timecard ID Detail")) {
			if (params == null || params.length < 2) {
				return "Error: Insufficient Parameter.  Please provide employee ID and record ID.";
			} else { // check employeeid and recordid
				try {
					// long employeeid =
					Long.parseLong(params[0]);
				} catch (Exception e) {
					return "Error: Invalid employee ID: " + params[0];
				}
				try {
					// int recid =
					Integer.parseInt(params[1]);
				} catch (Exception e) {
					return "Error: Invalid record ID: " + params[1];
				}
			}
		} else if (metricName.equals("Employee Billing Records Detail") || metricName.equals("Employee Salary Records Detail") || metricName.equals("ADP Profile Detail") || metricName.equals("Candidate Attribute Detail")) {
			if (params == null || params.length < 1) {
				return "Error: Insufficient Parameter.	Please provide employee ID.";
			} else { // check employeeid
				try {
					// long employeeid =
					Long.parseLong(params[0]);
				} catch (Exception e) {
					return "Error: Invalid employee ID: " + params[0];
				}
			}
		} else if (metricName.equals("Candidate Job Application Detail")) {
			if (params == null || params.length < 2) {
				return "Error: Insufficient Parameter. Please provide employee ID.";
			} else { // Check Employee ID and Job ID
				try {
					// long employeeid =
					Long.parseLong(params[0]);
				} catch (Exception e) {
					return "Error: Invalid Candidate ID: " + params[0];
				}
				try {
					// long rfqid =
					Long.parseLong(params[1]);
				} catch (Exception e) {
					return "Error: Invalid Job ID: " + params[1];
				}
			}
		} else {
			return "Error: Unknown Metric.  Please check the spelling of the metric name.";
		}
		return null;
	}
	
	private static String checkAdvantageMetricName(String metricName, String fromDate, String toDate, String[] params) {
		if (metricName.equals("Advantage New Companies List") || metricName.equals("Advantage Updated Companies List") || //
				metricName.equals("Advantage Deleted Companies List") || metricName.equals("Advantage Merged Companies List") || //
				metricName.equals("Advantage New Contacts List") || metricName.equals("Advantage Updated Contacts List") || //
				metricName.equals("Advantage Deleted Contacts List") || metricName.equals("Advantage Merged Contacts List") || //
				metricName.equals("Advantage Issued Jobs List") || metricName.equals("Advantage Updated Jobs List") || //
				metricName.equals("Advantage Deleted Jobs List") || metricName.equals("Advantage Merged Jobs List") || //
				metricName.equals("Advantage New Candidates List") || metricName.equals("Advantage Updated Candidates List") || //
				metricName.equals("Advantage Deleted Candidates List") || metricName.equals("Advantage Merged Candidates List") || //
				metricName.equals("Advantage Submittal/Interview/Hire Activities List") || //
				metricName.equals("Advantage Updated Submittal/Interview/Hire Activities List") || //
				metricName.equals("Advantage Deleted Submittal/Interview/Hire Activities List") || //
				metricName.equals("Advantage Candidate Actions") || metricName.equals("Advantage Deleted Candidate Actions") || //
				metricName.equals("Advantage Contact Actions") || metricName.equals("Advantage Updated Contact Actions") || //
				metricName.equals("Advantage Deleted Contact Actions") || //
				metricName.equals("Advantage Job Actions") || metricName.equals("Advantage Deleted Job Actions") || //
				metricName.equals("Advantage New Employee") || metricName.equals("Advantage Updated Employee") || //
				metricName.equals("Advantage Deleted Employee") || //
				metricName.equals("Advantage Access Log") || //
				metricName.equals("Advantage New Candidate Reference Check") || metricName.equals("Advantage Updated Candidate Reference Check") || //
				metricName.equals("Advantage Deleted Candidate Reference Check") || //
				metricName.equals("Advantage New AIF") || metricName.equals("Advantage Updated AIF") || metricName.equals("Advantage Deleted AIF") || //
				metricName.equals("Advantage Owners Updated Companies List") || metricName.equals("Advantage Owners Updated Contacts List") || //
				metricName.equals("Advantage Company Types Updated Companies List") || metricName.equals("Advantage Contact Types Updated Contacts List") || //
				metricName.equals("Advantage Users Updated Jobs List")) {
			if (fromDate == null || toDate == null) {
				return "Error: Insufficient Parameters.  Please provide both from date and to date.";
			}
		} else if (metricName.equals("Advantage Sales Pipeline") || metricName.equals("Advantage Open Jobs List") || //
				metricName.equals("Advantage Users List") || metricName.equals("Advantage User Group Lists")) {
		} else {
			return "Error: Unknown Metric.  Please check the spelling of the metric name.";
		}
		return null;
	}
	
	public static String validate(String metricName, String fromDate, String toDate, String[] params) {
		// System.out.println("In validate method");
		if (metricName.startsWith("Advantage"))
			return checkAdvantageMetricName(metricName, fromDate, toDate, params);
		if (metricName.endsWith("Detail")) {
			return checkDetailMetricName(metricName, fromDate, toDate, params);
		}
		//
		if (metricName.equals("New Positions Count by Division") || metricName.equals("New Positions Count by Primary Sales") || //
				metricName.equals("New Positions Count") || //
				metricName.equals("Hires Count by Division") || metricName.equals("Hires Count by Primary Sales") || //
				metricName.equals("Filled Positions Count by Division") || metricName.equals("Filled Positions Count by Primary Sales") || //
				metricName.equals("Filled Positions Count") || //
				metricName.equals("Submittal/Interview Activities Count by Division") || //
				metricName.equals("Submittal/Interview Activities Count by Primary Sales") || //
				metricName.equals("Submittal/Interview Activities Count by Recruiter") || //
				metricName.equals("Submittal/Interview/Hire Activities Count by Recruiter") || //
				metricName.equals("Aging of Positions Count by Division") || metricName.equals("Aging of Positions Count by Primary Sales") || //
				metricName.equals("Ended Assignments Count by Division") || metricName.equals("Ended Assignments Count by Primary Sales") || //
				metricName.equals("Hires Count by Primary Sales") || metricName.equals("Hires Count by Recruiter") || //
				metricName.equals("Incoming Resumes Count") || metricName.equals("Companies Count by User") || //
				metricName.equals("Job Count by User") || metricName.equals("Contact Count by User") || //
				metricName.equals("Candidate Note Count by User") || metricName.equals("Contact Note Count by User") || //
				metricName.equals("Candidate Note Count by Action by User") || //
				metricName.equals("Contact Note Count by Action by User") || //
				metricName.equals("Jobs Posted") || //
				metricName.equals("Submittal/Interview/Hire Activities List") || //
				metricName.equals("Candidate Actions") || metricName.equals("Contact Actions") || //
				metricName.equals("New Hires") || metricName.equals("Updated Hires") || //
				metricName.equals("Issued Jobs List") || metricName.equals("Updated Jobs List") || //
				metricName.equals("New/Updated Company Records") || metricName.equals("New/Updated Contact Records") || metricName.equals("New/Updated Opportunity Records") || //
				metricName.equals("New/Updated Job Records") || metricName.equals("New/Updated Candidate Records") || //
				metricName.equals("New/Updated Employee Records") || metricName.equals("New/Updated Activity Records") || //
				metricName.equals("New/Updated Candidate Note Records") || metricName.equals("New/Updated Candidate HR Records") || //
				metricName.equals("New/Updated Event Records") || metricName.equals("New/Updated Task Records") || //
				metricName.equals("New/Updated Company Note Records") || metricName.equals("New/Updated Contact Note Records") || //
				metricName.equals("New/Updated Billing Records") || metricName.equals("New/Updated Salary Records") || //
				metricName.equals("New/Updated Submittal/Interview/Hire Activity Records") || metricName.equals("New/Updated Job User Records") || //
				metricName.equals("New/Updated Approved Timesheet Records") || //
				metricName.equals("New/Updated Approved Timesheet and Expense Records") || metricName.equals("Deleted Contact Note Records") || //
				metricName.equals("New/Updated Paychex Profiles") || metricName.equals("New/Updated ADP Profiles") || //
				metricName.equals("Users Updated Jobs List") || metricName.equals("New/Updated Candidate Qualification Records") || //
				metricName.equals("Deleted Company Records") || metricName.equals("Deleted Contact Records") || //
				metricName.equals("Deleted Job Records") || metricName.equals("Deleted Candidate Records") || //
				metricName.equals("Deleted Candidate Note Records") || metricName.equals("Deleted Employee Records") || //
				metricName.equals("Deleted Activity Records") || metricName.equals("Deleted Task Records") || //
				metricName.equals("Access Log") || //
				metricName.equals("Merged Companies") || metricName.equals("Merged Contacts") || //
				metricName.equals("Merged Jobs") || metricName.equals("Merged Candidates") || //
				metricName.equals("TCML New Hires") || metricName.equals("TCML Updated Hires") || metricName.equals("TCML EEO") || //
				metricName.equals("MISI New/Updated Jobs") || metricName.equals("MISI New/Updated Starts") || //
				metricName.equals("ACSI Feed") || metricName.equals("NTT Data Moat/Flip/Submittal Count") || //
				metricName.equals("New Approved Billing Records") || metricName.equals("New Approved Salary Records") || //
				metricName.equals("Updated Approved Billing Records") || metricName.equals("Updated Approved Salary Records") || //
				metricName.equals("New Invoices") || metricName.equals("Voided Invoices") || //
				metricName.equals("New Approved Timesheets") || metricName.equals("Approved Timesheets by Week Ending Date") || //
				metricName.equals("Candidate Application Records") || //
				metricName.equals("Rang Technologies Candidate Records with Missing Data") || //
				metricName.equals("ICS_OFFICE_VISITS") || metricName.equals("ICS_SUBMITTALS") || metricName.equals("ICS_INTERVIEWS") || //
				metricName.equals("ICS_HIRES") || metricName.equals("ICS_REFERENCES") || metricName.equals("ICS_CONTACTS") || //
				metricName.equals("ICS_CONTACT_ACTIVITIES") || metricName.equals("ICS_COVERS") || metricName.equals("ICS_JOB_REQUISITIONS") || //
				metricName.equals("Linium Invoices List") || metricName.equals("Rotator Submittal/Interview Activities Count by Recruiter") || //
				metricName.equals("Rotator Internal Submittals Count by Recruiter") || metricName.equals("Rotator External Submittals Count by Recruiter") || //
				metricName.equals("Rotator Interviews Count by Recruiter") || metricName.equals("Rotator Hires Count by Recruiter") || //
				metricName.equals("Rotator New Positions Count by Division") || //
				metricName.equals("Sage 50 Redberry New Employee Feed") || metricName.equals("Sage 50 Redberry New Timesheet Feed") || //
				metricName.equals("ETeam Submittal Metrics by Labor Category") || metricName.equals("V2 New/Updated Contact Records") || //
				metricName.equals("Akraya In Person Meeting Note Count") || metricName.equals("Akraya Issued Job Count by Primary Sales") || //
				metricName.equals("Akraya External Submittal Count by Primary Sales") || metricName.equals("Akraya KPI") || metricName.equals("Saved Emails") || //
				metricName.equals("Resume/CV Count by Recruiter") || metricName.equals("EDI Approved Timesheets") || //
				metricName.equals("New Resumes Downloaded") || metricName.equals("New/Updated Candidate Availability Records") || //
				metricName.equals("New Expenses")) {
			if (fromDate == null || toDate == null) {
				return "Error: Insufficient Parameters.  Please provide both from date and to date.";
			}
			if (metricName.equals("Aging of Positions Count by Division") || metricName.equals("Aging of Positions Count by Primary Sales")) {
				if (params == null || params.length < 2) {
					return "Error: Insufficient Parameters.  Please provide position type and aging range.";
				}
			}
		} else if (metricName.equals("Jobs List by User by Status")) {
			if (params == null || params.length < 2) {
				return "Error: Insufficient Parameter.  Please provide user ID and job status.";
			}
			try {
				// long userid =
				Long.parseLong(params[0]);
			} catch (Exception e) {
				return "Error: Invalid user ID: " + params[0];
			}
			try {
				// int status =
				Integer.parseInt(params[1]);
			} catch (Exception e) {
				return "Error: Invalid job status: " + params[1];
			}
		} else if (metricName.equals("Open Jobs List by Company")) {
			if (params == null || params.length < 1) {
				return "Error: Missing Parameter.  Please provide company ID.";
			}
			try {
				// long companyid =
				Long.parseLong(params[0]);
			} catch (Exception e) {
				return "Error: Invalid company ID: " + params[0];
			}
		} else if (metricName.equals("Open Jobs List by Contact")) {
			if (params == null || params.length < 1) {
				return "Error: Missing Parameter.  Please provide contact ID.";
			}
			try {
				// long companyid =
				Long.parseLong(params[0]);
			} catch (Exception e) {
				return "Error: Invalid contact ID: " + params[0];
			}
		} else if (metricName.equals("Genuent Open and On Hold Jobs by Division")) {
			if (params == null || params.length < 1) {
				return "Error: Missing Parameter.  Please provide division ID.";
			}
		} else if (metricName.equals("Jobs Dashboard by User")) {
			if (params == null || params.length < 1) {
				return "Error: Missing Parameter.  Please provide user ID.";
			}
		} else if (metricName.equals("Portal Jobs List")) {
			if (params == null || params.length < 1) {
				return "Error: Missing Parameter.  Please provide portal ID (-1 for default portal).";
			}
			try {
				// long companyid =
				Long.parseLong(params[0]);
			} catch (Exception e) {
				return "Error: Invalid portal ID: " + params[0];
			}
		} else if (metricName.equals("Open Positions Count by Division") || //
				metricName.equals("Open Positions Count by Primary Sales") || //
				metricName.equals("Open Positions Count by Job Type") || //
				metricName.equals("Open Positions Count") || metricName.equals("Divisions List") || metricName.equals("Rejection Reasons List") || //
				metricName.equals("Incoming Resumes Count") || metricName.equals("Users List") || //
				metricName.equals("VMS Users List") || metricName.equals("User Group Lists") || //
				metricName.equals("Aditi Current Placement Activities") || metricName.equals("Aditi Current Head Count") || //
				metricName.equals("Diversant Active Assignments") || metricName.equals("Linium Active Employees List") || //
				metricName.equals("Linium Active Employees List Not Excluded from Workterra") || //
				metricName.equals("Linium Employees Terminated in the Previous Day") || //
				metricName.equals("Rotator Active Assignments") || metricName.equals("Rotator All Assignments") || //
				metricName.equals("Red Berry Recruitment Weeks on Assignment") || //
				metricName.equals("Jobs Currently Posted") || metricName.equals("Buildspace Client References") || //
				metricName.equals("Buildspace Worker References") || metricName.equals("Buildspace Suppliers") || //
				metricName.equals("Buildspace CIS") || metricName.equals("Userfields List")) { //
		} else if (metricName.equals("Submittal Records by Candidate and Job") || metricName.equals("Candidate Resume Submitted to Job")) {
			if (params == null || params.length < 2) {
				return "Error: Insufficient Parameter.  Please provide candidate ID and job ID.";
			}
			try {
				// long candidateid = //
				Long.parseLong(params[0]);
			} catch (Exception e) {
				return "Error: Invalid candidate ID: " + params[0];
			}
			try {
				// long jobid =
				Long.parseLong(params[1]);
			} catch (Exception e) {
				return "Error: Invalid job ID: " + params[1];
			}
		} else if (metricName.equals("Candidate Email Records") || metricName.equals("Candidate On-Boarding Document List")) {
			if (params == null || params.length < 1) {
				return "Error: Missing Parameter.  Please provide candidate ID.";
			}
			try {
				// long candidateid =
				Long.parseLong(params[0]);
			} catch (Exception e) {
				return "Error: Invalid Candidate ID: " + params[0];
			}
		} else if (metricName.equals("Contact Email Records")) {
			if (params == null || params.length < 1) {
				return "Error: Missing Parameter.  Please provide contact ID.";
			}
			try {
				// long contactid =
				Long.parseLong(params[0]);
			} catch (Exception e) {
				return "Error: Invalid Contact ID: " + params[0];
			}
		} else {
			return "Error: Unknown Metric.  Please check the spelling of the metric name.";
		}
		return null;
	}
	
	public static String constructQuery(String metricName, Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param, String[] restriction, Map<String, String> colNameToAliasMap) throws Exception {
		// System.out.println("Constructing the query for " + metricName);
		// construct query
		String sql = "";
		if (metricName.endsWith("Detail")) {
			if (metricName.equals("Company Detail")) {
				sql = JDData.CompanyDetail(clientID, params, param, colNameToAliasMap);
			} else if (metricName.equals("Companies Detail")) {
				sql = JDData.CompaniesDetail(clientID, params, param, colNameToAliasMap);
			} else if (metricName.equals("Company Owners Detail")) {
				sql = JDData.CompanyOwnersDetail(clientID, params, param);
			} else if (metricName.equals("Companies Owners Detail")) {
				sql = JDData.CompaniesOwnersDetail(clientID, params, param);
			} else if (metricName.equals("Companies Types Detail")) {
				sql = JDData.CompaniesTypesDetail(clientID, params, param);
			} else if (metricName.equals("Company Addresses Detail")) {
				sql = JDData.CompanyAddressesDetail(clientID, params, param);
			} else if (metricName.equals("Contact Detail")) {
				sql = JDData.ContactDetail(clientID, params, param, colNameToAliasMap);
			} else if (metricName.equals("V2 Contact Detail")) {
				sql = JDData.V2ContactDetail(clientID, params, param, colNameToAliasMap);
			} else if (metricName.equals("Contacts Detail")) {
				sql = JDData.ContactsDetail(clientID, params, param, colNameToAliasMap);
			} else if (metricName.equals("V2 Contacts Detail")) {
				sql = JDData.V2ContactsDetail(clientID, params, param, colNameToAliasMap);
			} else if (metricName.equals("Contact Owners Detail")) {
				sql = JDData.ContactOwnersDetail(clientID, params, param);
			} else if (metricName.equals("Contacts Owners Detail")) {
				sql = JDData.ContactsOwnersDetail(clientID, params, param);
			} else if (metricName.equals("Contacts Types Detail")) {
				sql = JDData.ContactsTypesDetail(clientID, params, param);
			} else if (metricName.equals("Contact Addresses Detail")) {
				sql = JDData.ContactAddressesDetail(clientID, params, param);
			} else if (metricName.equals("Job Detail")) {
				sql = JDData.JobDetail(clientID, params, param, restriction, colNameToAliasMap);
			} else if (metricName.equals("Jobs Detail")) {
				sql = JDData.JobsDetail(clientID, params, param, restriction, colNameToAliasMap);
			} else if (metricName.equals("Job Contacts Detail")) {
				sql = JDData.JobContactsDetail(clientID, params, param);
			} else if (metricName.equals("Jobs Contacts Detail")) {
				sql = JDData.JobsContactsDetail(clientID, params, param);
			} else if (metricName.equals("Job Users Detail")) {
				sql = JDData.JobUsersDetail(clientID, params, param);
			} else if (metricName.equals("Jobs Users Detail")) {
				sql = JDData.JobsUsersDetail(clientID, params, param);
			} else if (metricName.equals("Job Applicants Detail")) {
				sql = JDData.JobApplicantsDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Job Application Detail")) {
				sql = JDData.CandidateJobApplicationDetail(clientID, params, param);
			} else if (metricName.equals("Job Submittals Detail")) {
				sql = JDData.JobSubmittalsDetail(clientID, params, param);
			} else if (metricName.equals("Jobs Submittals Detail")) {
				sql = JDData.JobsSubmittalsDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Submittals Detail")) {
				sql = JDData.CandidateSubmittalsDetail(clientID, params, param);
			} else if (metricName.equals("Resume Detail")) {
				sql = JDData.resumeDetail(params, param);
			} else if (metricName.equals("Users Tasks Detail")) {
				sql = JDData.usersTasksDetail(clientID, fromDate, toDate, params, param);
			} else if (metricName.equals("Events Attendees Detail")) {
				sql = JDData.EventsAttendeesDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Detail")) {
				sql = JDData.CandidateDetail(clientID, params, param, colNameToAliasMap);
			} else if (metricName.equals("Candidates Detail")) {
				sql = JDData.CandidatesDetail(clientID, params, param, colNameToAliasMap);
			} else if (metricName.equals("Candidate Qualifications Detail")) {
				sql = JDData.CandidateQualDetail(clientID, params, param);
			} else if (metricName.equals("Candidates Qualifications Detail")) {
				sql = JDData.CandidatesQualDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Availability Detail")) {
				sql = JDData.CandidateAvailabilityDetail(clientID, params, param);
			} else if (metricName.equals("Candidates Availability Detail")) {
				sql = JDData.CandidatesAvailabilityDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Resumes Detail")) {
				sql = JDData.CandidateResumesDetail(clientID, params, param);
			} else if (metricName.equals("Candidates Resumes Detail")) {
				sql = JDData.CandidatesResumesDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Resume Source Detail")) {
				sql = JDData.CandidateResumeSourceDetail(clientID, params, param);
			} else if (metricName.equals("Candidate On-Boarding Document Detail")) {
				sql = JDData.CandidateOnboardingDocumentDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Attachment Detail")) {
				sql = JDData.CandidateAttachmentDetail(clientID, params, param);
			} else if (metricName.equals("Company Note Detail")) {
				sql = JDData.CompanyNoteDetail(clientID, params, param);
			} else if (metricName.equals("Company Notes Detail")) {
				sql = JDData.CompanyNotesDetail(clientID, params, param);
			} else if (metricName.equals("Contact Note Detail")) {
				sql = JDData.ContactNoteDetail(clientID, params, param);
			} else if (metricName.equals("Contact Notes Detail")) {
				sql = JDData.ContactNotesDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Note Detail")) {
				sql = JDData.CandidateNoteDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Notes Detail")) {
				sql = JDData.CandidateNotesDetail(clientID, params, param);
			} else if (metricName.equals("Candidate EEO Detail")) {
				sql = JDData.CandidateEEODetail(clientID, params, param);
			} else if (metricName.equals("Submittal Detail")) {
				sql = JDData.SubmittalDetail(clientID, params, param, colNameToAliasMap);
			} else if (metricName.equals("Submittals Detail")) {
				sql = JDData.SubmittalsDetail(clientID, params, param, colNameToAliasMap);
			} else if (metricName.equals("Billing Record Detail")) {
				sql = JDData.billingRecordDetail(clientID, params, param, restriction);
			} else if (metricName.equals("Billing Records Detail")) {
				sql = JDData.billingRecordsDetail(clientID, params, param, restriction);
			} else if (metricName.equals("Employee Billing Records Detail")) {
				sql = JDData.employeeBillingRecordsDetail(clientID, params, param, restriction);
			} else if (metricName.equals("Salary Record Detail")) {
				sql = JDData.salaryRecordDetail(clientID, params, param, restriction);
			} else if (metricName.equals("Salary Records Detail")) {
				sql = JDData.salaryRecordsDetail(clientID, params, param, restriction);
			} else if (metricName.equals("Employee Salary Records Detail")) {
				sql = JDData.employeeSalaryRecordsDetail(clientID, params, param, restriction);
			} else if (metricName.equals("Invoice Detail")) {
				sql = JDData.invoiceDetail(clientID, params, param);
			} else if (metricName.equals("Placement Detail")) {
				sql = JDData.placementDetail(clientID, params, param);
			} else if (metricName.equals("Employee Timesheet Image Detail")) {
				sql = JDData.employeeTimesheetImageDetail(clientID, fromDate, toDate, params, param, restriction);
			} else if (metricName.equals("Employee Timesheet Image by Timecard ID Detail")) {
				sql = JDData.employeeTimesheetImageDetailByTimecardId(clientID, fromDate, toDate, params, param, restriction);
			} else if (metricName.equals("Timesheet Detail")) {
				sql = JDData.timesheetDetail(clientID, fromDate, toDate, params, param, restriction);
			} else if (metricName.equals("Contact Hotlist Detail")) {
				sql = JDData.contactHotlistDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Hotlist Detail")) {
				sql = JDData.candidateHotlistDetail(clientID, params, param);
			} else if (metricName.equals("User Contact Hotlist Detail")) {
				sql = JDData.userContactHotlistDetail(clientID, params, param);
			} else if (metricName.equals("User Candidate Hotlist Detail")) {
				sql = JDData.userCandidateHotlistDetail(clientID, params, param);
			} else if (metricName.equals("Contact Hotlists Detail")) {
				sql = JDData.contactHotlistsDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Hotlists Detail")) {
				sql = JDData.candidateHotlistsDetail(clientID, params, param);
			} else if (metricName.equals("Candidate Notes List Detail")) {
				sql = JDData.CandidateNotesListDetail(clientID, params, param);
			} else if (metricName.equals("Candidate HR Detail")) {
				sql = JDData.CandidateHRDetail(clientID, params, param);
			} else if (metricName.equals("Candidates HR Detail")) {
				sql = JDData.CandidatesHRDetail(clientID, params, param);
			} else if (metricName.equals("Email Detail")) {
				sql = JDData.EmailDetail(clientID, params, param);
			} else if (metricName.equals("ADP Profile Detail")) {
				sql = JDData.adpProfile(clientID, params, param, restriction);
			} else if (metricName.equals("Candidate Attribute Detail")) {
				sql = JDData.candidateAttributeDetail(clientID, params, param);
			}
		} else if (metricName.equals("Jobs List by User by Status")) {
			// System.out.println("BIData Metric Name: " + metricName);
			sql = JDData.jobsByUserByStatus(clientID, fromDate, toDate, params, param);
		} else if (metricName.equals("Portal Jobs List")) {
			sql = JDData.portalJobsList(clientID, params, param);
		} else if (metricName.equals("Jobs Dashboard by User")) {
			sql = JDData.jobsDashboardByUser(clientID, fromDate, toDate, params, param);
		} else if (metricName.equals("Open Jobs List by Company")) {
			sql = JDData.openJobsListByCompany(clientID, fromDate, toDate, params, param);
		} else if (metricName.equals("Open Jobs List by Contact")) {
			sql = JDData.openJobsListByContact(clientID, fromDate, toDate, params, param);
		} else if (metricName.equals("Candidate Application Records")) {
			sql = JDData.candidateApplicationRecords(clientID, fromDate, toDate, param);
		} else if (metricName.equals("New Approved Billing Records")) {
			sql = JDData.newApprovedBillingRecords(clientID, fromDate, toDate, param, restriction);
		} else if (metricName.equals("New Approved Salary Records")) {
			sql = JDData.newApprovedSalaryRecords(clientID, fromDate, toDate, param, restriction);
		} else if (metricName.equals("Updated Approved Billing Records")) {
			sql = JDData.updatedApprovedBillingRecords(clientID, fromDate, toDate, param, restriction);
		} else if (metricName.equals("Updated Approved Salary Records")) {
			sql = JDData.updatedApprovedSalaryRecords(clientID, fromDate, toDate, param, restriction);
		} else if (metricName.equals("New Invoices")) {
			sql = JDData.newInvoices(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Voided Invoices")) {
			sql = JDData.voidedInvoices(clientID, fromDate, toDate, param);
		} else if (metricName.equals("New Approved Timesheets")) {
			sql = JDData.approvedTimesheets(clientID, fromDate, toDate, param, restriction);
		} else if (metricName.equals("Approved Timesheets by Week Ending Date")) {
			sql = JDData.weekendingOnTimesheets(clientID, fromDate, toDate, param, restriction);
		} else if (metricName.equals("New Hires")) {
			sql = JDData.newHires(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Updated Hires")) {
			sql = JDData.updatedHires(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Merged Companies")) {
			sql = JDData.mergedCompanies(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Merged Contacts")) {
			sql = JDData.mergedContacts(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Merged Jobs")) {
			sql = JDData.mergedJobs(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Merged Candidates")) {
			sql = JDData.mergedCandidates(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Candidate Resume Submitted to Job")) {
			sql = JDData.candidateResumeSubmittedtoJob(clientID, params, param);
		} else if (metricName.equals("Submittal Records by Candidate and Job")) {
			sql = JDData.submittalbyCandbyJob(clientID, params, param);
		} else if (metricName.equals("Jobs Posted")) {
			sql = JDData.jobsPosted(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Jobs Currently Posted")) {
			sql = JDData.jobsCurrentlyPosted(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Users Updated Jobs List")) {
			sql = JDData.jobUserUpdated(clientID, fromDate, toDate, param);
		} else if ((metricName.startsWith("New/Updated ") || metricName.startsWith("V2 ")) && metricName.endsWith(" Records")) {
			if (metricName.equals("New/Updated Company Records")) {
				sql = JDData.newUpdatedCompanies(clientID, fromDate, toDate, params, param, colNameToAliasMap);
			} else if (metricName.equals("New/Updated Company Note Records")) {
				sql = JDData.newUpdatedCompanyNotes(clientID, fromDate, toDate, param);
			} else if (metricName.equals("New/Updated Contact Records")) {
				sql = JDData.newUpdatedContacts(clientID, fromDate, toDate, params, param, colNameToAliasMap);
			} else if (metricName.equals("New/Updated Contact Note Records")) {
				sql = JDData.newUpdatedContactNotes(clientID, fromDate, toDate, param);
			} else if (metricName.equals("New/Updated Opportunity Records")) {
				sql = JDData.newUpdatedOpportunities(clientID, fromDate, toDate, params, param);
			} else if (metricName.equals("New/Updated Job Records")) {
				sql = JDData.newUpdatedJobs(clientID, fromDate, toDate, params, param, colNameToAliasMap);
			} else if (metricName.equals("New/Updated Candidate Records")) {
				sql = JDData.newUpdatedCandidates(clientID, fromDate, toDate, params, param, colNameToAliasMap);
			} else if (metricName.equals("New/Updated Candidate Note Records")) {
				sql = JDData.newUpdatedCandidateNotes(clientID, fromDate, toDate, param);
			} else if (metricName.equals("New/Updated Candidate HR Records")) {
				sql = JDData.newUpdatedCandidateHR(clientID, fromDate, toDate, param);
			} else if (metricName.equals("New/Updated Employee Records")) {
				sql = JDData.newUpdatedEmployees(clientID, fromDate, toDate, param);
			} else if (metricName.equals("New/Updated Activity Records")) {
				sql = JDData.newUpdatedActivities(clientID, fromDate, toDate, params, param, colNameToAliasMap);
			} else if (metricName.equals("New/Updated Task Records")) {
				sql = JDData.newUpdatedTasks(clientID, fromDate, toDate, param);
			} else if (metricName.equals("New/Updated Event Records")) {
				sql = JDData.newUpdatedEvents(clientID, fromDate, toDate, param);
			} else if (metricName.equals("New/Updated Candidate Qualification Records")) {
				sql = JDData.newUpdatedCandidateQuals(clientID, fromDate, toDate, params, param);
			} else if (metricName.equals("V2 New/Updated Contact Records")) {
				sql = JDData.V2newUpdatedContacts(clientID, fromDate, toDate, params, param, colNameToAliasMap);
			} else if (metricName.equals("New/Updated Submittal/Interview/Hire Activity Records")) {
				sql = JDData.newUpdatedSIHActivitieslist(clientID, fromDate, toDate, params, param);
			} else if (metricName.equals("New/Updated Job User Records")) {
				sql = JDData.newUpdatedJobUsers(clientID, fromDate, toDate, params, param);
			} else if (metricName.equals("New/Updated Billing Records")) {
				sql = JDData.newUpdatedBillingRecords(clientID, fromDate, toDate, param, restriction);
			} else if (metricName.equals("New/Updated Salary Records")) {
				sql = JDData.newUpdatedSalaryRecords(clientID, fromDate, toDate, param, restriction);
			} else if (metricName.equals("New/Updated Approved Timesheet Records")) {
				sql = JDData.newUpdatedApprovedTimesheets(clientID, fromDate, toDate, param, restriction);
			} else if (metricName.equals("New/Updated Approved Timesheet and Expense Records")) {
				sql = JDData.newUpdatedApprovedTimesheetsAndExpenses(clientID, fromDate, toDate, param, restriction);
			} else if (metricName.equals("New/Updated Candidate Availability Records")) {
				sql = JDData.newUpdatedCandidateAvailabilityRecords(clientID, fromDate, toDate, param);
			}
		} else if (metricName.startsWith("Deleted ") && metricName.endsWith(" Records")) {
			if (metricName.equals("Deleted Candidate Note Records")) {
				sql = JDData.deletedCandidateNotes(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Deleted Employee Records")) {
				sql = JDData.deletedEmployees(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Deleted Activity Records")) {
				sql = JDData.deletedActivities(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Deleted Company Records")) {
				sql = JDData.deletedCompanies(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Deleted Contact Records")) {
				sql = JDData.deletedContacts(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Deleted Contact Note Records")) {
				sql = JDData.deletedContactNotes(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Deleted Job Records")) {
				sql = JDData.deletedJobs(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Deleted Candidate Records")) {
				sql = JDData.deletedCandidates(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Deleted Task Records")) {
				sql = JDData.deletedTasks(clientID, fromDate, toDate, param);
			}
		} else if (metricName.startsWith("Sage 50")) {
			if (metricName.equals("Sage 50 Redberry New Employee Feed")) {
				sql = Sage50Feed.sage50RedberryNewEmployeeFeed(clientID, fromDate, toDate, params, param);
			} else if (metricName.equals("Sage 50 Redberry New Timesheet Feed")) {
				sql = Sage50Feed.sage50RedberryNewTimesheetFeed(clientID, fromDate, toDate, params, param);
			}
		} else if (metricName.startsWith("MISI")) {
			if (metricName.equals("MISI New/Updated Jobs")) {
				sql = MISIFeed.newJobs(clientID, fromDate, toDate, param);
			} else if (metricName.equals("MISI New/Updated Starts")) {
				sql = MISIFeed.newStarts(clientID, fromDate, toDate, param);
			}
		} else if (metricName.startsWith("Advantage")) {
			if (metricName.equals("Advantage New Companies List")) {
				sql = AdvantageMetric.newCompanies(params, fromDate, toDate, param);
			} else if (metricName.equals("Advantage Updated Companies List")) {
				sql = AdvantageMetric.updatedCompanies(params, fromDate, toDate, param);
			} else if (metricName.equals("Advantage Deleted Companies List")) {
				sql = AdvantageMetric.deletedCompanies(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Merged Companies List")) {
				sql = AdvantageMetric.mergedCompanies(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Owners Updated Companies List")) {
				sql = AdvantageMetric.companyOwnerUpdated(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Company Types Updated Companies List")) {
				sql = AdvantageMetric.companyTypeUpdated(fromDate, toDate, param);
			} else if (metricName.equals("Advantage New Contacts List")) {
				sql = AdvantageMetric.newContacts(params, fromDate, toDate, param);
			} else if (metricName.equals("Advantage Updated Contacts List")) {
				sql = AdvantageMetric.updatedContacts(params, fromDate, toDate, param);
			} else if (metricName.equals("Advantage Deleted Contacts List")) {
				sql = AdvantageMetric.deletedContacts(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Merged Contacts List")) {
				sql = AdvantageMetric.mergedContacts(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Owners Updated Contacts List")) {
				sql = AdvantageMetric.contactOwnerUpdated(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Issued Jobs List")) {
				sql = AdvantageMetric.issuedJobs(params, fromDate, toDate, param);
			} else if (metricName.equals("Advantage Updated Jobs List")) {
				sql = AdvantageMetric.updatedJobs(params, fromDate, toDate, param);
			} else if (metricName.equals("Advantage Deleted Jobs List")) {
				sql = AdvantageMetric.deletedJobs(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Merged Jobs List")) {
				sql = AdvantageMetric.mergedJobs(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Users Updated Jobs List")) {
				sql = AdvantageMetric.jobUserUpdated(fromDate, toDate, param);
			} else if (metricName.equals("Advantage New Candidates List")) {
				sql = AdvantageMetric.newCandidates(params, fromDate, toDate, param);
			} else if (metricName.equals("Advantage Updated Candidates List")) {
				sql = AdvantageMetric.updatedCandidates(params, fromDate, toDate, param);
			} else if (metricName.equals("Advantage Deleted Candidates List")) {
				sql = AdvantageMetric.deletedCandidates(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Merged Candidates List")) {
				sql = AdvantageMetric.mergedCandidates(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Submittal/Interview/Hire Activities List")) {
				sql = AdvantageMetric.newActivities(params, fromDate, toDate, param);
			} else if (metricName.equals("Advantage Updated Submittal/Interview/Hire Activities List")) {
				// all submit, interview, hire records of associated (rfqid,
				// candidateid) that were updated or deleted
				sql = AdvantageMetric.updatedActivities(params, fromDate, toDate, param);
			} else if (metricName.equals("Advantage Deleted Submittal/Interview/Hire Activities List")) {
				// all jobs/candidates updated or deleted
				sql = AdvantageMetric.deletedActivities(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Candidate Actions")) {
				sql = AdvantageMetric.NewCandidateActions(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Contact Actions")) {
				sql = AdvantageMetric.NewContactActions(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Deleted Candidate Actions")) {
				sql = AdvantageMetric.deletedCandidateActions(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Updated Contact Actions")) {
				sql = AdvantageMetric.updatedContactActions(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Deleted Contact Actions")) {
				sql = AdvantageMetric.deletedContactActions(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Job Actions")) {
				sql = AdvantageMetric.newJobActions(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Deleted Job Actions")) {
				sql = AdvantageMetric.deletedJobActions(fromDate, toDate, param);
			} else if (metricName.equals("Advantage New Employee")) {
				sql = AdvantageMetric.newEmployee(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Updated Employee")) {
				sql = AdvantageMetric.updatedEmployee(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Deleted Employee")) {
				sql = AdvantageMetric.deletedEmployee(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Sales Pipeline")) {
				sql = AdvantageMetric.salesPipeline(params);
			} else if (metricName.equals("Advantage Users List")) {
				sql = AdvantageMetric.usersList();
			} else if (metricName.equals("Advantage User Group Lists")) {
				sql = AdvantageMetric.userGroupLists();
			} else if (metricName.equals("Advantage New Candidate Reference Check")) {
				sql = AdvantageMetric.newCandidateReferenceCheck(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Updated Candidate Reference Check")) {
				sql = AdvantageMetric.updatedCandidateReferenceCheck(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Deleted Candidate Reference Check")) {
				sql = AdvantageMetric.deletedCandidateReferenceCheck(fromDate, toDate, param);
			} else if (metricName.equals("Advantage New AIF")) {
				sql = AdvantageMetric.newAif(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Updated AIF")) {
				sql = AdvantageMetric.updatedAif(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Deleted AIF")) {
				sql = AdvantageMetric.deletedAif(fromDate, toDate, param);
			} else if (metricName.equals("Advantage Access Log")) {
				sql = AdvantageMetric.accessLog(fromDate, toDate, param);
			} else if (metricName.equals("Advantage BIData Check")) {
				sql = AdvantageMetric.biDataCheck(fromDate, toDate, param);
			}
		} else if (metricName.startsWith("TCML")) {
			if (metricName.equals("TCML New Hires")) {
				sql = TCMLFeed.newHires(clientID, fromDate, toDate, param);
			} else if (metricName.equals("TCML Updated Hires")) {
				sql = TCMLFeed.updatedHires(clientID, fromDate, toDate, param);
			} else if (metricName.equals("TCML EEO")) {
				sql = TCMLFeed.EEO(clientID, fromDate, toDate, param);
			}
		} else if (metricName.startsWith("MISI")) {
			if (metricName.equals("MISI New/Updated Jobs")) {
				sql = MISIFeed.newJobs(clientID, fromDate, toDate, param);
			} else if (metricName.equals("MISI New/Updated Starts")) {
				sql = MISIFeed.newStarts(clientID, fromDate, toDate, param);
			}
		} else if (metricName.equals("ACSI Feed")) {
			sql = ACSIFeed.getActivities(clientID, fromDate, toDate, param);
		} else if (metricName.startsWith("Aditi")) {
			if (metricName.equals("Aditi Current Placement Activities")) {
				sql = AditiFeed.getCurrentPlacementActivities(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Aditi Current Head Count")) {
				sql = AditiFeed.getCurrentHeadCount(clientID, fromDate, toDate, params, param);
			}
		} else if (metricName.startsWith("ICS_")) {
			if (metricName.equals("ICS_OFFICE_VISITS")) {
				sql = ICSFeed.getOfficeVisits(clientID, fromDate, toDate, param);
			} else if (metricName.equals("ICS_SUBMITTALS")) {
				sql = ICSFeed.getSubmittals(clientID, fromDate, toDate, param);
			} else if (metricName.equals("ICS_INTERVIEWS")) {
				sql = ICSFeed.getInterviews(clientID, fromDate, toDate, param);
			} else if (metricName.equals("ICS_HIRES")) {
				sql = ICSFeed.getHires(clientID, fromDate, toDate, param);
			} else if (metricName.equals("ICS_REFERENCES")) {
				sql = ICSFeed.getReferences(clientID, fromDate, toDate, param);
			} else if (metricName.equals("ICS_CONTACTS")) {
				sql = ICSFeed.getContacts(clientID, fromDate, toDate, param);
			} else if (metricName.equals("ICS_CONTACT_ACTIVITIES")) {
				sql = ICSFeed.getContactActivities(clientID, fromDate, toDate, param);
			} else if (metricName.equals("ICS_COVERS")) {
				sql = ICSFeed.getCovers(clientID, fromDate, toDate, param);
			} else if (metricName.equals("ICS_JOB_REQUISITIONS")) {
				sql = ICSFeed.getJobRequisitions(clientID, fromDate, toDate, param);
			}
		} else if (metricName.startsWith("Linium")) {
			if (metricName.equals("Linium Invoices List")) {
				sql = LiniumFeed.getInvoiceList(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Linium Active Employees List")) {
				sql = LiniumFeed.getEmployeeList1(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Linium Active Employees List Not Excluded from Workterra")) {
				sql = LiniumFeed.getEmployeeList2(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Linium Employees Terminated in the Previous Day")) {
				sql = LiniumFeed.getEmployeeList3(clientID, fromDate, toDate, param);
			}
		} else if (metricName.startsWith("Rotator")) {
			if (metricName.equals("Rotator Submittal/Interview Activities Count by Recruiter")) {
				sql = RotatorFeed.getActivities(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Rotator Internal Submittals Count by Recruiter")) {
				sql = RotatorFeed.getInternalSubmittals(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Rotator External Submittals Count by Recruiter")) {
				sql = RotatorFeed.getExternalSubmittals(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Rotator Interviews Count by Recruiter")) {
				sql = RotatorFeed.getInterviews(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Rotator Hires Count by Recruiter")) {
				sql = RotatorFeed.getHires(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Rotator Active Assignments")) {
				sql = RotatorFeed.getActiveAssignments(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Rotator All Assignments")) {
				sql = RotatorFeed.getAllAssignments(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Rotator New Positions Count by Division")) {
				sql = RotatorFeed.newPositionsCountByDivision(clientID, fromDate, toDate, param);
			}
		} else if (metricName.startsWith("Genuent")) {
			if (metricName.equals("Genuent Open and On Hold Jobs by Division")) {
				sql = GenuentFeed.getOpenOnHoldJobsByDivision(clientID, params, param);
			}
		} else if (metricName.startsWith("Akraya")) {
			if (metricName.equals("Akraya In Person Meeting Note Count")) {
				sql = AkrayaFeed.getNoteCounts(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Akraya Issued Job Count by Primary Sales")) {
				sql = AkrayaFeed.getJobCountsByPrimarySales(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Akraya External Submittal Count by Primary Sales")) {
				sql = AkrayaFeed.getSubmittalCountsByPrimarySales(clientID, fromDate, toDate, param);
			} else if (metricName.equals("Akraya KPI")) {
				sql = AkrayaFeed.getKPI(clientID, fromDate, toDate, param);
			}
		} else if (metricName.startsWith("Buildspace")) {
			if (metricName.equals("Buildspace Client References")) {
				sql = BuildspaceFeed.clientReferences(clientID, param);
			} else if (metricName.equals("Buildspace Worker References")) {
				sql = BuildspaceFeed.workerReferences(clientID, param);
			} else if (metricName.equals("Buildspace Suppliers")) {
				sql = BuildspaceFeed.suppliers(clientID, param);
			} else if (metricName.equals("Buildspace CIS")) {
				sql = BuildspaceFeed.cis(clientID, param);
			}
		} else if (metricName.equals("NTT Data Moat/Flip/Submittal Count")) {
			sql = NTTDataFeed.moatFlipSubmittals(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Rang Technologies Candidate Records with Missing Data")) {
			sql = RangTechnologiesFeed.getCandidateWithMissingData(clientID, fromDate, toDate, param);
		} else if (metricName.startsWith("Diversant Active Assignments")) {
			sql = DiversantFeed.activeAssignments(clientID, params, param);
		} else if (metricName.equals("ETeam Submittal Metrics by Labor Category")) {
			sql = ETeamFeed.getSubmittalMetricsByLaborCategory(clientID, fromDate, toDate, params, param);
		} else if (metricName.equals("Red Berry Recruitment Weeks on Assignment")) {
			sql = RedBerryRecruitmentFeed.getWeeksOnAssignment(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Candidate Email Records")) {
			sql = JDData.CandidateEmailRecords(clientID, params, param);
		} else if (metricName.equals("Contact Email Records")) {
			sql = JDData.ContactEmailRecords(clientID, params, param);
		} else if (metricName.equals("Saved Emails")) {
			sql = JDData.savedEmails(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Resume/CV Count by Recruiter")) {
			sql = JDData.resumeCountByRecruiter(clientID, fromDate, toDate, params, param);
		} else if (metricName.equals("Submittal/Interview/Hire Activities Count by Recruiter")) {
			sql = JDData.submittalsInterviewsHiresByRecruiter(clientID, fromDate, toDate, param);
		} else if (metricName.equals("New/Updated Paychex Profiles")) {
			sql = JDData.paychexProfiles(clientID, fromDate, toDate, param);
		} else if (metricName.equals("New/Updated ADP Profiles")) {
			sql = JDData.adpProfiles(clientID, fromDate, toDate, param, restriction);
		} else if (metricName.equals("Companies Count by User")) {
			sql = JDData.companyCountByUser(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Job Count by User")) {
			sql = JDData.jobsCountByUser(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Contact Count by User")) {
			sql = JDData.contactCountByUser(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Candidate Note Count by User")) {
			sql = JDData.candidateNoteCountByUser(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Contact Note Count by User")) {
			sql = JDData.contactNoteCountByUser(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Candidate Note Count by Action by User")) {
			sql = JDData.candidateNoteCountByActionByUser(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Contact Note Count by Action by User")) {
			sql = JDData.contactNoteCountByActionByUser(clientID, fromDate, toDate, param);
		} else if (metricName.equals("EDI Approved Timesheets")) {
			sql = EDIFeed.getTimesheetsByStatus(clientID, fromDate, toDate, param);
		} else if (metricName.equals("New Resumes Downloaded")) {
			sql = JDData.newResumesDownloaded(clientID, fromDate, toDate, param);
		} else if (metricName.equals("New Expenses")) {
			sql = JDData.newExpenses(clientID, fromDate, toDate, param);
		} else if (metricName.equals("Candidate On-Boarding Document List")) {
			sql = JDData.candidateOnboardingDocumentList(clientID, params, param);
		} else if (metricName.equals("New Positions Count by Division")) {
			sql = //
					" select id divisionid, divisionname, sum(value) value" + //
							" from " + //
							" ((select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select /*+ index(trfq IDX_TRFQ_DATEISSUED) */ divisionid, sum(positions) value" + //
							" from trfq where teamid=? and dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') group by divisionid) a, " + //
							" (select id, name from tdivision where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid(+) = b.id) " + //
							" union " + //
							" (select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select /*+ index(trfq IDX_TRFQ_DATEISSUED) */ divisionid, sum(positions) value" + //
							" from trfq where teamid=? and dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') group by divisionid) a, " + //
							" (select id, name from tdivision where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid = b.id(+))) " + //
							" group by id, divisionname " + //
							" order by divisionname "; //
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
		} else if (metricName.equals("New Positions Count by Primary Sales")) {
			sql = //
					" select b.id userid, b.firstname||' '||b.lastname name, c.id divisionid, c.name divisionname, value" + //
							" from " + //
							" (select /*+ index(trfq IDX_TRFQ_DATEISSUED) */ y.recruiterid, sum(positions) value" + //
							" from trfq x, trecruiterrfq y where x.teamid=? and x.dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
							" and x.id = y.rfqid and y.lead_sales=1 group by y.recruiterid) a, " + //
							" trecruiter b, tdivision c " + //
							" where a.recruiterid = b.id and b.division = c.id(+) " + //
							" order by name "; //
			param.add(clientID); //
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("New Positions Count")) {
			sql = //
					" select sum(nvl(positions,1)) value from trfq where teamid=? and dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("Open Positions Count by Division")) { //
			sql = //
					" select id divisionid, divisionname, sum(value) value" + //
							" from " + //
							" ((select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select /*+ index(trfq IDX_FOR_LOADSKILL) */ divisionid, sum(nvl(positions,1)-nvl(fills,0)) value" + //
							" from trfq where teamid=? and jobstatus=0 group by divisionid) a, " + //
							" (select id, name from tdivision b where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid(+) = b.id) " + //
							" union " + //
							" (select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select /*+ index(trfq IDX_FOR_LOADSKILL) */ divisionid, sum(nvl(positions,1)-nvl(fills,0)) value" + //
							" from trfq where teamid=? and jobstatus=0 group by divisionid) a, " + //
							" (select id, name from tdivision b where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid = b.id(+))) " + //
							" group by id, divisionname " + //
							" order by divisionname "; //
			param.add(clientID);
			param.add(clientID);
			param.add(clientID);
			param.add(clientID);
		} else if (metricName.equals("Open Positions Count by Primary Sales")) {
			sql = //
					" select b.id userid, b.firstname||' '||b.lastname name, c.id divisionid, c.name divisionname, value" + //
							" from " + //
							" (select /*+ index(trfq IDX_FOR_LOADSKILL) */ y.recruiterid, sum(nvl(positions,1)-nvl(fills,0)) value" + //
							" from trfq x, trecruiterrfq y where x.teamid=? and x.jobstatus=0 " + //
							" and x.id = y.rfqid and y.lead_sales=1 group by y.recruiterid) a, " + //
							" trecruiter b, tdivision c " + //
							" where a.recruiterid = b.id and b.division = c.id(+) " + //
							" order by name "; //
			param.add(clientID); //
		} else if (metricName.equals("Open Positions Count by Job Type")) {
			sql = //
					" select decode(contract,1,'Full Time',2,'Contract',3,'Right to Hire',4,'Full Time/Contract','Unknown') jobtype, " + //
							" sum(nvl(positions,1)-nvl(fills,0)) value from trfq where teamid=? and jobstatus = 0 group by contract ";
			param.add(clientID);
		} else if (metricName.equals("Open Positions Count")) {
			sql = //
					" select sum(nvl(positions,1)-nvl(fills,0)) value from trfq where teamid=? and jobstatus = 0 ";
			param.add(clientID);
		} else if (metricName.equals("Incoming Resumes Count")) {
			sql = //
					" select count(*) value from tcandidatedocument_header where teamid=? and datereceived_original between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("Hires Count by Division")) {
			sql = //
					" select id divisionid, divisionname, sum(value) value" + //
							" from " + //
							" ((select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select /*+ index(x IDX_DATEHIREDBASIC) */ divisionid, count(*) value" + //
							" from tinterviewschedule x, trfq y where recruiter_teamid=? and datehired between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and x.rfqid = y.id " + //
							" group by divisionid) a, " + //
							" (select id, name from tdivision b where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid(+) = b.id) " + //
							" union " + //
							" (select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select /*+ index(x IDX_DATEHIREDBASIC) */ divisionid, count(*) value" + //
							" from tinterviewschedule x, trfq y where recruiter_teamid=? and datehired between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and x.rfqid = y.id " + //
							" group by divisionid) a, " + //
							" (select id, name from tdivision b where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid = b.id(+))) " + //
							" group by id, divisionname " + //
							" order by divisionname "; //
			param.add(clientID); //
			param.add(fromDate); //
			param.add(toDate);
			param.add(clientID);
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
		} else if (metricName.equals("Filled Positions Count by Division")) {
			sql = //
					" select id divisionid, divisionname, sum(value) value" + //
							" from " + //
							" ((select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select divisionid, count(*) value" + //
							" from tinterviewschedule x, trfq y where recruiter_teamid=? and placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and x.rfqid = y.id " + //
							" group by divisionid) a, " + //
							" (select id, name from tdivision b where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid(+) = b.id) " + //
							" union " + //
							" (select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select divisionid, count(*) value" + //
							" from tinterviewschedule x, trfq y where recruiter_teamid=? and placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and x.rfqid = y.id " + //
							" group by divisionid) a, " + //
							" (select id, name from tdivision b where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid = b.id(+))) " + //
							" group by id, divisionname " + //
							" order by divisionname "; //
			param.add(clientID); //
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
		} else if (metricName.equals("Filled Positions Count")) {
			sql = //
					" select count(*) value" + //
							" from tinterviewschedule where recruiter_teamid=? and placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "; //
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("Submittal/Interview Activities Count by Division")) {
			sql = //
					" select id divisionid, divisionname, sum(value) value" + //
							" from " + //
							" (select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select divisionid, sum(value) value " + //
							" from " + //
							" ((select divisionid, count(distinct (rfqid||'~'||candidateid)) value " + //
							" from tinterviewschedule x, trfq y where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and x.rfqid = y.id " + //
							" group by divisionid) " + //
							" union all " + //
							" (select divisionid, count(distinct (rfqid||'~'||candidateid)) value " + //
							" from tinterviewschedule x, trfq y where recruiter_teamid=? and (datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') or dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) "
							+ //
							" and dateinterview is not null and x.rfqid = y.id " + //
							" group by divisionid))" + //
							" group by divisionid) a " + //
							" full outer join " + //
							" (select id, name from tdivision b where teamid=? and active=1 and id > 0) b " + //
							" on a.divisionid = b.id) " + //
							" group by id, divisionname " + //
							" order by divisionname "; //
			param.add(clientID); //
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
		} else if (metricName.equals("Submittal/Interview Activities Count by Recruiter")) {
			sql = //
					" select recruiterid id, b.firstname || ' ' || b.lastname recruitername, value " + //
							" from " + //
							" (select recruiterid, sum(value) value " + //
							" from " + //
							" ((select recruiterid, count(distinct (rfqid||'~'||candidateid)) value " + //
							" from tinterviewschedule where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
							" group by recruiterid) " + //
							" union all " + //
							" (select recruiterid, count(distinct (rfqid||'~'||candidateid)) value " + //
							" from tinterviewschedule where recruiter_teamid=? and (datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') or dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) "
							+ //
							" and dateinterview is not null " + //
							" group by recruiterid))" + //
							" group by recruiterid) a, trecruiter b " + //
							" where a.recruiterid = b.id " + //
							" order by recruitername "; //
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("Hires Count by Primary Sales")) {
			sql = //
					" select primarysalesid id, (select firstname||' '||lastname from trecruiter r where r.id=primarysalesid) primarysalesname, count(*) value " + //
							" from tinterviewschedule " + //
							" where recruiter_teamid=? and placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and datehired is not null " + //
							" group by primarysalesid "; //
			param.add(clientID); //
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("Hires Count by Recruiter")) {
			sql = //
					" select recruiterid id, (select firstname||' '||lastname from trecruiter r where r.id=recruiterid) recruitername, count(*) value " + //
							" from tinterviewschedule " + //
							" where recruiter_teamid=? and placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and datehired is not null " + //
							" group by recruiterid "; //
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("Aging of Positions Count by Division")) {
			boolean over = (params.length == 2) ? true : false;
			boolean in = false;
			boolean not = false;
			if (params[0].indexOf(",") > -1)
				in = true;
			else if (params[0].startsWith("~")) {
				not = true;
				params[0] = params[0].substring(1);
			}
			sql = //
					" select id divisionid, divisionname, sum(value) value" + //
							" from " + //
							" ((select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select divisionid, sum(nvl(positions,0))-sum(nvl(fills,0)) value " + //
							" from trfq where teamid=? and jobstatus = 0 and " + (over ? "dateissued <= to_date(?,'mm/dd/yyyy hh24:mi:ss')-? " : " dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss')-? and to_date(?,'mm/dd/yyyy hh24:mi:ss')-? ") + //
							(in ? " and contract in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type) from dual)) " : //
									(not ? " and (contract is null or contract <> ?) " : " and contract = ? "))
							+ //
							" group by divisionid) a, " + //
							" (select id, name from tdivision b where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid(+) = b.id) " + //
							" union " + //
							" (select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select divisionid, sum(nvl(positions,0))-sum(nvl(fills,0)) value " + //
							" from trfq where teamid=? and jobstatus = 0 and " + (over ? "dateissued <= to_date(?,'mm/dd/yyyy hh24:mi:ss')-? " : " dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss')-? and to_date(?,'mm/dd/yyyy hh24:mi:ss')-? ") + //
							(in ? " and contract in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type) from dual)) " : //
									(not ? " and (contract is null or contract <> ?) " : " and contract = ? "))
							+ //
							" group by divisionid) a, " + //
							" (select id, name from tdivision b where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid = b.id(+))) " + //
							" group by id, divisionname " + //
							" order by divisionname "; //
			param.add(clientID); //
			if (over) {
				param.add(toDate);
				param.add(new Integer(params[1]));
			} else {
				param.add(toDate);
				param.add(new Integer(params[2]));
				param.add(toDate);
				param.add(new Integer(params[1]));
			}
			param.add(params[0]);
			param.add(clientID);
			param.add(clientID);
			if (over) {
				param.add(toDate);
				param.add(new Integer(params[1]));
			} else {
				param.add(toDate);
				param.add(new Integer(params[2]));
				param.add(toDate);
				param.add(new Integer(params[1]));
			}
			param.add(params[0]);
			param.add(clientID);
		} else if (metricName.equals("Aging of Positions Count by Primary Sales")) {
			boolean over = (params.length == 2) ? true : false;
			boolean in = false;
			boolean not = false;
			if (params[0].indexOf(",") > -1)
				in = true;
			else if (params[0].startsWith("~")) {
				not = true;
				params[0] = params[0].substring(1);
			}
			sql = //
					" select b.id userid, b.firstname||' '||b.lastname name, c.id divisionid, c.name divisionname, value" + //
							" from " + //
							" (select y.recruiterid, sum(nvl(positions,0))-sum(nvl(fills,0)) value " + //
							" from trfq x, trecruiterrfq y where x.teamid=? and x.jobstatus = 0 and "
							+ (over ? "dateissued < to_date(?,'mm/dd/yyyy hh24:mi:ss')-? " : " dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss')-? and to_date(?,'mm/dd/yyyy hh24:mi:ss')-? ") + //
							(in ? " and contract in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type) from dual)) " : //
									(not ? " and (contract is null or contract <> ?) " : " and contract = ? "))
							+ //
							" and x.id = y.rfqid and y.lead_sales = 1 " + //
							" group by y.recruiterid) a, " + //
							" trecruiter b, tdivision c " + //
							" where a.recruiterid = b.id and b.division = c.id(+) " + //
							" order by name "; //
			param.add(clientID);
			if (over) {
				param.add(toDate);
				param.add(new Integer(params[1]));
			} else {
				param.add(toDate);
				param.add(new Integer(params[2]));
				param.add(toDate);
				param.add(new Integer(params[1]));
			}
			param.add(params[0]);
		} else if (metricName.equals("Ended Assignments Count by Division")) {
			sql = //
					" select id divisionid, divisionname, sum(value) value" + //
							" from " + //
							" ((select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select divisionid, count(*) value" + //
							" from tinterviewschedule x, trfq y where recruiter_teamid=? and datehired is not null and " + //
							(clientID.longValue() == 219 ? " dateterminated " : " date_ended ") + //
							" between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and x.rfqid = y.id " + //
							" group by divisionid) a, " + //
							" (select id, name from tdivision b where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid(+) = b.id) " + //
							" union " + //
							" (select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
							" from " + //
							" (select divisionid, count(*) value" + //
							" from tinterviewschedule x, trfq y where recruiter_teamid=? and datehired is not null and " + //
							(clientID.longValue() == 219 ? " dateterminated " : " date_ended ") + //
							" between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and x.rfqid = y.id " + //
							" group by divisionid) a, " + //
							" (select id, name from tdivision b where teamid=? and active=1 and id > 0) b " + //
							" where a.divisionid = b.id(+))) " + //
							" group by id, divisionname " + //
							" order by divisionname "; //
			param.add(clientID); //
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
		} else if (metricName.equals("Issued Jobs List")) {
			StringBuffer columnsToShow = new StringBuffer();
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					columnsToShow.append(
							", (select t.userfield_value from trfq_userfields t, tuserfields n " + " where t.rfqid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + params[i] + "') \"" + params[i] + "\"");
				}
			}
			sql = //
					" select a.id jobid, rfqno_team jobdivano, rfqrefno optionalreferenceno, divisionid, b.name divisionname, " + //
							" pru.id primaryrecruiterid, pru.firstname||' '||pru.lastname primaryrecruiter, " + //
							" psu.id primarysalesid, psu.firstname||' '||psu.lastname primarysales, " + //
							" a.companyid, c.name companyname, a.customerid contactid, d.firstname contactfirstname, d.lastname contactlastname, " + //
							" a.dateissued issuedate, startdate, case when enddate < '01-Jan-1970' then null else enddate end enddate, " + //
							" decode(contract,1,'Full Time',2,'Contract',3,'Right to Hire',4,'Full Time/Contract','') positiontype, " + //
							" decode(a.jobstatus,0,'Open',1,'On Hold',2,'Filled',3,'Cancelled',4,'Closed',5,'Expired',6,'Ignored','') jobstatus, " + //
							" rfqtitle title, positions openings, fills, a.city, a.state, a.zipcode, " + //
							" billratemin, billratemax, ratemin payratemin, ratemax payratemax " + columnsToShow.toString() + //
							" from trfq a, tdivision b, tcustomercompany c, tcustomer d, trecruiterrfq pr, trecruiter pru, trecruiterrfq ps, trecruiter psu " + //
							" where a.teamid=? and a.dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
							" and a.divisionid = b.id(+) and a.companyid = c.id(+) and a.customerid = d.id(+) " + //
							" and a.id = pr.rfqid(+) and 1 = pr.lead_recruiter(+) and pr.recruiterid = pru.id(+) " + //
							" and a.id = ps.rfqid(+) and 1 = ps.lead_sales(+) and ps.recruiterid = psu.id(+) "; //
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("Updated Jobs List")) {
			StringBuffer columnsToShow = new StringBuffer();
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					columnsToShow.append(", (select t.userfield_value from trfq_userfields t, tuserfields n " + //
							" where t.rfqid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + params[i] + "') \"" + params[i] + "\"");
				}
			}
			sql = //
					" select a.id jobid, rfqno_team jobdivano, rfqrefno optionalreferenceno, divisionid, b.name divisionname, " + //
							" pru.id primaryrecruiterid, pru.firstname||' '||pru.lastname primaryrecruiter, " + //
							" psu.id primarysalesid, psu.firstname||' '||psu.lastname primarysales, " + //
							" a.companyid, c.name companyname, a.customerid contactid, d.firstname contactfirstname, d.lastname contactlastname, " + //
							" a.dateissued issuedate, startdate, case when enddate < '01-Jan-1970' then null else enddate end enddate, " + //
							" decode(contract,1,'Full Time',2,'Contract',3,'Right to Hire',4,'Full Time/Contract','') positiontype, " + //
							" decode(a.jobstatus,0,'Open',1,'On Hold',2,'Filled',3,'Cancelled',4,'Closed',5,'Expired',6,'Ignored','') jobstatus, " + //
							" rfqtitle title, positions openings, fills, a.city, a.state, a.zipcode, " + //
							" billratemin, billratemax, ratemin payratemin, ratemax payratemax " + columnsToShow.toString() + //
							" from trfq a, tdivision b, tcustomercompany c, tcustomer d, trecruiterrfq pr, trecruiter pru, trecruiterrfq ps, trecruiter psu " + //
							" where a.teamid=? and a.datelastupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
							" and a.divisionid = b.id(+) and a.companyid = c.id(+) and a.customerid = d.id(+) " + //
							" and a.id = pr.rfqid(+) and 1 = pr.lead_recruiter(+) and pr.recruiterid = pru.id(+) " + //
							" and a.id = ps.rfqid(+) and 1 = ps.lead_sales(+) and ps.recruiterid = psu.id(+) "; //
			param.add(clientID); //
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("Submittal/Interview/Hire Activities List")) {
			sql = //
					" select /*+ use_nl(a b c d e f g) ordered */ a.activityid, f.recruiterid userid, g.firstname userfirstname, g.lastname userlastname, f.primarysalesid," + //
							" a.candidateid, b.firstname candidatefirstname, b.lastname candidatelastname, b.email candidateemail, " + //
							" a.rfqid jobid, c.rfqno_team jobreferencenumber, c.rfqtitle jobtitle, " + //
							" d.id contactid, d.firstname contactfirstname, d.lastname contactlastname, " + //
							" e.id companyid, e.name companyname, " + //
							" case when a.submittalflag=1 then f.datecreated else a.activitydate end activitydate, " + //
							" a.submittalflag, a.interviewflag, a.hireflag, " + //
							" case when a.roleid > 900 then 1 else 0 end internalsubmittalflag, " + //
							" case when a.submittalflag=1 then f.datepresented else null end submittaldate, " + //
							" case when a.roleid > 900 then f.daterejected else null end internalrejectdate, " + //
							" case when a.interviewflag=1 then f.dateinterview else null end interviewdate, " + //
							" case when nvl(a.roleid,0) < 900 then f.extdaterejected else null end externalrejectdate, " + //
							" case when a.hireflag=1 then f.datehired else null end startdate, " + //
							" case when a.hireflag=1 then f.date_ended else null end enddate, " + //
							" case when a.hireflag=1 then f.dateterminated else null end terminationdate, " + //
							" f.pay_hourly agreedbillrate, " + //
							" case when f.hourly > 0 then f.hourly when f.daily > 0 then f.daily else f.yearly end agreedpayrate," + //
							" (case when f.hourly > 0 then f.hourly_corporate when f.daily > 0 then f.daily_corporate else f.yearly_corporate end) corptocorp, " + //
							" decode(f.finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', " + //
							"     (select x.name from trateunits x where x.teamid=f.recruiter_teamid and x.ratetype=1 and x.unitid=f.finalbillrateunit)) billfrequency, " + //
							" decode(substr(f.payrateunits,0,1),'h','Hourly','d','Daily','y','Yearly', " + //
							"     (select x.name from trateunits x where x.teamid=f.recruiter_teamid and x.ratetype=0 and x.unitid=substr(f.payrateunits,0,1))) payfrequency, " + //
							" nvl((select x.name from tcurrency x where x.id=f.hourly_currency), " + //
							"     nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=f.recruiter_teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency, " + //
							" case when f.contract=1 then decode(f.fee_type,0,'%',1,'Flat Amount','') else '' end fee_type, " + //
							" case when f.contract=1 and f.fee_type=0 then decode(f.fee,-1,null,f.fee)*1 when f.contract=1 and f.fee_type=1 and f.yearly > 0 then decode(f.fee,-1,null,f.fee)/f.yearly*100 else null end fee_percent, " + //
							" case when  f.contract=1 and f.fee_type=1 then decode(f.fee,-1,null,f.fee)*1 when f.contract=1 and f.fee_type=0 then f.yearly*decode(f.fee,-1,null,f.fee)/100 else null end fee " + //
							" from " + //
							" ((select /*+ index(tinterviewschedule IDX_INTER_DATECREATED) */ id activityid, candidateid, rfqid, datepresented activitydate, 1 submittalflag, 0 interviewflag, 0 hireflag, roleid " + //
							" from tinterviewschedule a where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
							" and (id = (select min(b.id) from tinterviewschedule b where b.candidateid=a.candidateid and b.rfqid=a.rfqid and nvl(roleid,0) < 900) " + //
							"    or id = (select min(b.id) from tinterviewschedule b where b.candidateid=a.candidateid and b.rfqid=a.rfqid and nvl(roleid,0) >= 900))) " + //
							" union all " + //
							" (select /*+ index(tinterviewschedule IDX_INTSCHDATE) */ id activityid, candidateid, rfqid, interviewscheduledate activitydate, 0 submittalflag, 1 interviewflag, 0 hireflag, roleid " + //
							" from tinterviewschedule where recruiter_teamid=? and interviewscheduledate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and dateinterview is not null) " + //
							" union all " + //
							" (select /*+ index(tinterviewschedule IDX_PLACEMENTDATE) */ id activityid, candidateid, rfqid, placementdate activitydate, 0 submittalflag, 0 interviewflag, 1 hireflag, roleid " + //
							" from tinterviewschedule where recruiter_teamid=? and placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and datehired is not null)) a, " + //
							" tcandidate b, trfq c, tcustomer d, tcustomercompany e, tinterviewschedule f, trecruiter g " + //
							" where a.candidateid = b.id and b.teamid=? and a.rfqid = c.id and c.customerid = d.id(+) and c.companyid = e.id(+) " + //
							" and a.activityid = f.id and f.recruiterid = g.id "; //
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
			param.add(clientID);
		} else if (metricName.equals("Candidate Actions")) {
			sql = //
					" select a.noteid, decode(a.type,1,'Last Attempt',2,'Last Reached',3,'Last Meeting',4,'Attribute',5,'Qualification', " + //
							" 6,'References Checked',7,'Incoming Call',8,'Outgoing Call',b.name) actiontype, " + //
							" a.recruiterid userid, f.firstname userfirstname, f.lastname userlastname, a.datecreated createdate, a.dateaction actiondate, " + //
							" a.candidateid, c.firstname candidatefirstname, c.lastname candidatelastname, " + //
							" decode(a.rfqid,0,'',a.rfqid) jobid, d.rfqno_team jobreferencenumber, d.rfqtitle jobtitle, " + //
							" decode(a.contactid,0,'',a.contactid) contactid, e.firstname contactfirstname, e.lastname contactlastname, " + //
							" case when note_clob is null then substr(note,1,200) else to_char(substr(note_clob,1,200)) end notefirst200chars " + //
							" from tcandidatenotes a, tactiontype_candidate b, tcandidate c, trfq d, tcustomer e, trecruiter f " + //
							" where a.recruiter_teamid = ? and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
							" and (a.auto = 0 or a.auto = 3) and a.type = b.id(+) and a.recruiter_teamid=b.teamid(+) " + //
							" and a.candidateid=c.id and a.recruiter_teamid=c.teamid and a.rfqid=d.id(+) and a.contactid=e.id(+) and a.recruiterid=f.id"; //
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("Contact Actions")) {
			sql = //
					" select a.noteid, decode(a.type,1,'Last Attempt',2,'Last Reached',3,'Last Meeting',7,'Incoming Call',8,'Outgoing Call',b.name) actiontype, " + //
							" a.recruiterid userid, f.firstname userfirstname, f.lastname userlastname, a.datemodified createdate," + //
							" case when a.type > 0 then a.datecreated else null end actiondate, " + //
							" a.customerid contactid, c.firstname contactfirstname, c.lastname contactlastname, " + //
							" decode(a.rfqid,0,'',a.rfqid) jobid, d.rfqno_team jobreferencenumber, d.rfqtitle jobtitle, " + //
							" decode(a.candidateid,0,'',a.candidateid) candidateid, e.firstname candidatefirstname, e.lastname candidatelastname, " + //
							" substr(note,1,200) notefirst200chars " + //
							" from tcustomernotes a, tactiontype b, tcustomer c, trfq d, tcandidate e, trecruiter f " + //
							" where a.recruiter_teamid = ? and a.datemodified between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
							" and a.type = b.id(+) and a.recruiter_teamid=b.teamid(+) and a.customerid=c.id and a.rfqid = d.id(+) " + //
							" and a.candidateid=e.id(+) and a.recruiter_teamid=e.teamid(+) and f.id = a.recruiterid "; //
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("Access Log")) {
			sql = " select x.userid, case when x.actioncode = -1 then 'No of Candidates Emailed' when x.actioncode=-2 then 'No of Contacts Emailed' else y.description end activitytype, " + //
					"   case when x.actioncode=-1 or x.actioncode=-2 then emailcount else x.activitycount end activitycount  " + //
					" from (select teamid, id userid, actioncode, count(*) activitycount, case when actioncode in (-1,-2) then sum(criteria*1) else 0 end emailcount " + //
					"       from taccesslog " + //
					"       where teamid = ? " + //
					"         and datecreated between str_to_date(?, '%m/%d/%Y %H:%i:%s') and str_to_date(?, '%m/%d/%Y %H:%i:%s') " + //
					"         and id > 0 and id <> 688 " + //
					"       group by id, actioncode) x" + //
					"   left join taccesslogcodes y " + //
					" on y.id = x.actioncode";
			param.add(clientID);
			param.add(fromDate);
			param.add(toDate);
		} else if (metricName.equals("Users List")) {
			sql = //
					" select a.id userid, a.firstname, a.lastname, a.email, a.dateentered datecreated, a.active activeflag, b.name division, a.title, a.city, a.state, " + //
							" a.workphone, a.workphoneext extension, cellphone, " + //
							" case when bitand(leader,1024) > 0 then 1 else 0 end recruiterflag, " + //
							" case when bitand(leader, 512) > 0 then 1 else 0 end salesflag," + //
							" (select snurl from trecruiter_socialnetwork s where s.teamid = a.groupid and s.recid = a.id and s.snid = 1) LinkedIn, " + //
							" (select snurl from trecruiter_socialnetwork s where s.teamid = a.groupid and s.recid = a.id and s.snid = 2) MySpace, " + //
							" (select snurl from trecruiter_socialnetwork s where s.teamid = a.groupid and s.recid = a.id and s.snid = 3) Facebook, " + //
							" (select snurl from trecruiter_socialnetwork s where s.teamid = a.groupid and s.recid = a.id and s.snid = 4) Twitter, " + //
							" (select snurl from trecruiter_socialnetwork s where s.teamid = a.groupid and s.recid = a.id and s.snid = 5) YouTube " + //
							" from trecruiter a, tdivision b " + //
							" where a.groupid =? and a.division = b.id(+) "; //
			param.add(clientID); //
		} else if (metricName.equals("VMS Users List")) {
			sql = //
					" select sitename, groupname, recruiterid userid, firstname, lastname, email, " + //
							" decode(assign,1,'Yes','No') assign, decode(sendemail,1,'Yes','No') receiveemail, decode(alarm,1,'Yes','No') receivealarm, " + //
							" decode(sendtimesheetemail,1,'Yes','No') receivetimesheetnotification, " + //
							" decode(sale,1,'Yes','No') sales, decode(primarysale,1,'Yes','No') primarysales, " + //
							" decode(recruiter,1,'Yes','No') recruiter, decode(primaryrecruiter,1,'Yes','No') primaryrecruiter " + //
							" from tspidersrecruiters " + //
							" where teamid =? order by sitename, groupname, firstname, lastname "; //
			param.add(clientID); //
		} else if (metricName.equals("User Group Lists")) {
			sql = //
					" select b.column_value userid, c.firstname||' '||c.lastname username, a.name grouplistname " + //
							" from tgrouplist a, table(sf_str2tbl(a.RECRUITERIDS)) b, trecruiter c " + //
							" where a.teamid =? and b.column_value = c.id ";
			param.add(clientID);
		} else if (metricName.equals("Divisions List")) {
			sql = //
					" select id divisionid, name divisionname from tdivision where teamid=? ";
			param.add(clientID);
		} else if (metricName.equals("Rejection Reasons List")) {
			sql = //
					"select id, reason, nvl(internal,0) internalflag, nvl(external,0) externalflag from trejection_reasons where teamid=? and deleted=0 ";
			param.add(clientID);
		} else if (metricName.equals("Userfields List")) {
			sql = JDData.userfieldsList(clientID, param);
		} else {
			throw new Exception("Metric not implemented yet.");
		}
		return sql;
	}
	
	public static int getDocDBID(Connection maindb_con, Long clientID, String[] params) throws Exception {
		int dbid = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = maindb_con.prepareStatement("select dbid from tcandidatedocument_header where teamid=? and global_id = ?");
			ps.setLong(1, clientID.longValue());
			ps.setString(2, params[0]);
			rs = ps.executeQuery();
			if (rs.next())
				dbid = rs.getInt(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e1) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e1) {
				}
			// System.out.println("BIData getDocDBID return Exception " +
			// e.getMessage());
		}
		return dbid;
	}
	
	public static int getUserCount(Connection conn, long clientID) {
		int usercount = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select count(*) from trecruiter where groupid=? and active=1 and bitand(leader, 1024)>0 ");
			ps.setLong(1, clientID);
			rs = ps.executeQuery();
			if (rs.next())
				usercount = rs.getInt(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e1) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e1) {
				}
			// System.out.println("BIData getUserCount return Exception " +
			// e.getMessage());
		}
		return usercount;
	}
	
	// retrieve document from attachment DB, unzip, decript, base64 encode, then
	// put in FILECONTENT column
	// on-boarding document is encrypted, attachment is not encrypted
	public static Vector<String[]> retrieveDocument(Connection db_con, String tableName, Vector<String[]> data, long docID, long teamID) throws Exception {
		// System.out.println("Candidate On-Boarding Document Detail -
		// retrieveDocument");
		String docEncoded = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			byte[] docBytes = null;
			ps = db_con.prepareStatement("select thefile from " + tableName + " where id=? and teamid=? ");
			ps.setLong(1, docID);
			ps.setLong(2, teamID);
			rs = ps.executeQuery();
			if (rs.next()) {
				Blob doc = rs.getBlob("thefile");
				docBytes = new byte[(int) doc.length()];
				// System.out.println("Candidate On-Boarding Document Detail
				// doc.length=" + doc.length());
				// System.out.println("Candidate Attachment Detail doc.length="
				// + doc.length());
				// System.out.println("Candidate Attachment Detail id=" +
				// docID);
				if (doc != null) {
					InputStream is = doc.getBinaryStream();
					if (is != null)
						is.read(docBytes, 0, docBytes.length);
					is.close();
				}
			}
			rs.close();
			ps.close();
			// System.out.println("Candidate On-Boarding Document Detail get
			// docBytes");
			if (docBytes != null) {
				byte[] unzipped = Zipper.unZipIt(docBytes);
				// System.out.println("Candidate On-Boarding Document Detail
				// unzipped");
				byte[] doc = unzipped;
				if (tableName.indexOf("onboarding") > -1)
					doc = Crypto.decrypt(unzipped);
				// System.out.println("Candidate On-Boarding Document Detail
				// decrypted");
				docEncoded = new String(Base64.encodeBase64(doc), "US-ASCII");
				// System.out.println("Candidate On-Boarding Document Detail
				// encoded doc length = " + docEncoded.length());
			}
			String[] col = (String[]) data.get(0);
			int idx = 0;
			for (; idx < col.length; idx++) {
				if (col[idx].equals("FILECONTENT"))
					break;
			}
			// System.out.println("Candidate On-Boarding Document Detail
			// filecontent at field no " + idx);
			String[] row = (String[]) data.get(1);
			row[idx] = docEncoded;
		} catch (Exception e) {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e1) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e1) {
				}
			// System.out.println("BIData retrieveDocument return Exception " +
			// e.getMessage());
			throw new Exception(e.getMessage());
		}
		return data;
	}
}

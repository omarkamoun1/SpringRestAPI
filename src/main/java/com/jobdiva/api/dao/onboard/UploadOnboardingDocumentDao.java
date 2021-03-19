package com.jobdiva.api.dao.onboard;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.axelon.mail.SMTPServer;
import com.axelon.shared.Crypto;
import com.axelon.shared.Zipper;
import com.axelon.util.JDLocale;
import com.jobdiva.api.model.OnboardingCandidateDocument;
import com.jobdiva.api.model.authenticate.JobDivaSession;

/**
 * @author Joseph Chidiac
 *
 */
@Component
public class UploadOnboardingDocumentDao extends SaveOnBoardDao {
	
	public Long uploadCandidateOnboardingDocument(JobDivaSession jobDivaSession, OnboardingCandidateDocument onboardingCandidateDocument) throws Exception {
		//
		Long onboardingId = onboardingCandidateDocument.getOnboardingId();
		byte[] filecontent = onboardingCandidateDocument.getFilecontent();
		String fileName = onboardingCandidateDocument.getFilename();
		Long uploadedby = new Long(1);
		Long uploadedbyId = jobDivaSession.getRecruiterId();
		//
		//
		if (onboardingId == null || onboardingId <= 0) {
			throw new Exception("Misssing onboardingId parameter");
		}
		//
		if (fileName == null || fileName.trim().length() == 0) {
			throw new Exception("Misssing fileName parameter");
		}
		//
		if (filecontent == null) {
			throw new Exception("Misssing filecontent parameter");
		}
		//
		//
		//
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		String sql = "select ONBOARDDOC_SEQ.nextval as NEXTVAL FROM dual";
		List<Long> list = jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return rs.getLong("NEXTVAL");
			}
		});
		//
		long docid = (list != null && list.size() > 0) ? list.get(0) : 0;
		//
		//
		//
		String sqlInsert = "insert into tcandidate_onboarding_docs(id,onboardingid,filename, uploadedon, uploadedby, uploadedbyid, deleted, teamid) " //
				+ "values" //
				+ "(?,?,?,sysdate,?,?,0,?)";
		//
		Object[] params = new Object[] { docid, onboardingId, fileName, uploadedby, uploadedbyId, jobDivaSession.getTeamId() };
		//
		jdbcTemplate.update(sqlInsert, params);
		//
		//
		// update to insert the file
		String sqlPreparStatemtInsert = "insert into tcandidate_onboarding_docs_blob (id, teamid, thefile) values (?,?,?)";
		JdbcTemplate attachmentJdbcTemplate = jobDivaConnectivity.getAttachmentJdbcTemplate();
		;
		attachmentJdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sqlPreparStatemtInsert);
				ps.setLong(1, docid);
				ps.setLong(2, jobDivaSession.getTeamId());
				//
				// if (filecontent != null) {
				try {
					byte[] entypted = Crypto.encrypt(filecontent);
					byte[] zipped = Zipper.zipIt(entypted);
					ps.setBinaryStream(3, new ByteArrayInputStream(zipped), zipped.length);
				} catch (Exception e) {
					ps.setBinaryStream(3, null, 0);
				}
				// } else {
				// ps.setBinaryStream(3, null, 0);
				// }
				return ps;
			}
		});
		//
		//
		//
		// update onboarding status if it is rejected
		String sqlUpdate = " update tcandidate_onboarding set onboardingstatus=0, statuschangedon=sysdate where id = ? and teamid=? and onboardingstatus = 2 ";
		params = new Object[] { onboardingId, jobDivaSession.getTeamId() };
		jdbcTemplate.update(sqlUpdate, params);
		//
		//
		sendOnboardingNotifications(jobDivaSession, jdbcTemplate, jobDivaSession.getTeamId(), onboardingId, fileName, uploadedby, uploadedbyId);
		//
		//
		return docid;
	}
	
	public void sendOnboardingNotifications(JobDivaSession jobDivaSession, JdbcTemplate jdbcTemplate, Long teamid, Long onboardingId, String fileName, Long uploadedby, Long uploadedbyId) throws Exception {
		//
		//
		//
		String apache_location = getApacheAddress(teamid);
		long candidateid = 0, interviewid = 0, docid2 = 0, rfqid = 0, divisionid = 0, startid = 0;
		int att_type = 0;
		String onboardname = "", refno_team = "", title = "", cand_name = "", uploadedbyname = "", label = "";
		String sql = "";
		try {
			sql = " select t1.candidateid, t1.interviewid, t1.att_type, t1.docid, t3.RFQNO_TEAM, t3.rfqtitle, t4.firstname||' '||t4.lastname, t2.jobid, t3.divisionid, t5.id as startid, t2.name "
					+ " from tcandidate_onboarding t1, tpreonboardings t2, trfq t3, tcandidate t4, tinterviewschedule t5 "
					+ " where t1.id=? and t1.teamid=? and t1.interviewid=t2.id and t1.teamid=t2.teamid and t2.jobid=t3.id(+) and t2.teamid=t3.teamid(+) and t4.id = t1.candidateid and t4.teamid=t1.teamid "
					+ " and t2.teamid=t5.recruiter_teamid(+) and t2.interviewid=t5.id(+) ";
			//
			//
			Object[] params = new Object[] { onboardingId, teamid };
			//
			List<List<Object>> list = jdbcTemplate.query(sql, params, new RowMapper<List<Object>>() {
				
				@Override
				public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					List<Object> list = new ArrayList<Object>();
					//
					list.add(rs.getLong(1));
					list.add(rs.getLong(2));
					list.add(rs.getInt(3));
					list.add(rs.getLong(4));
					list.add(rs.getString(5));
					list.add(rs.getString(6));
					list.add(rs.getString(7));
					list.add(rs.getLong("jobid"));
					list.add(rs.getLong("divisionid"));
					list.add(rs.getLong("startid"));
					list.add(rs.getString("name"));
					//
					return list;
				}
			});
			//
			if (list != null && list.size() > 0) {
				List<Object> rsList = list.get(0);
				candidateid = (long) rsList.get(0);
				interviewid = (long) rsList.get(1);
				att_type = (int) rsList.get(2);
				docid2 = (long) rsList.get(3);
				refno_team = (String) rsList.get(4);
				title = (String) rsList.get(5);
				cand_name = (String) rsList.get(6);
				rfqid = (long) rsList.get(7);
				divisionid = (long) rsList.get(8);
				startid = (long) rsList.get(9);
				label = (String) rsList.get(10);
			}
			//
			//
			switch (uploadedby.intValue()) {
				case 0: // by candidate
					sql = " select firstname||' '||lastname from tcandidate where teamid=? and id=? ";
					params = new Object[] { teamid, uploadedbyId };
					List<String> nameList = jdbcTemplate.query(sql, params, new RowMapper<String>() {
						
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							return rs.getString(1);
						}
					});
					//
					if (nameList != null && nameList.size() > 0) {
						uploadedbyname = nameList.get(0);
					}
					break;
				case 2:// by supplier
					sql = " select firstname||' '||lastname from tcustomer where teamid=? and id=? ";
					params = new Object[] { teamid, uploadedbyId };
					nameList = jdbcTemplate.query(sql, params, new RowMapper<String>() {
						
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							return rs.getString(1);
						}
					});
					//
					if (nameList != null && nameList.size() > 0) {
						uploadedbyname = nameList.get(0);
					}
					break;
			}
			//
			//
			/* check if docs are completed, and send completed notification */
			boolean iscompleted = updatePackageStatus(jdbcTemplate, teamid, candidateid, interviewid);
			//
			//
			logger.info("##### complete status:" + iscompleted + " candidateid:" + candidateid + " interviewid:" + interviewid + " rfqid:" + rfqid + " teamid:" + teamid + " divisionid:" + divisionid + " startid: " + startid);
			if (!iscompleted && uploadedby.intValue() == 1)
				return; // no notification sent
			//
			//
			//
			com.axelon.mail.SMTPServer mailServer = new com.axelon.mail.SMTPServer();
			mailServer.setHost(appProperties.getSmtpServerLocation());
			mailServer.setContentType(SMTPServer.CONTENT_TYPE_HTML);
			//
			//
			//
			Vector<String> v = getTeamRegion(jdbcTemplate, teamid);
			String teamregion = (v.size() > 0) ? v.get(1) : "en_US";
			String teamtimezoneid = (v.size() > 0) ? v.get(0) : "America/New_York";
			//
			//
			//
			if (uploadedby.intValue() == 0 || uploadedby.intValue() == 2) { // 1:
																			// send
																			// upload
																			// notification
																			// by
																			// candidate/supplier
				if (att_type == 0)
					sql = "select name from tonboardings where teamid=? and id=? ";
				else if (att_type == 1 || att_type == 2)
					sql = "select description from tcompanyattachments where teamid=? and id=? ";
				else if (att_type == 3)
					sql = "select description from tcontactattachments where teamid=? and id=? ";
				//
				params = new Object[] { docid2 };
				List<String> nameList = jdbcTemplate.query(sql, params, new RowMapper<String>() {
					
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						return rs.getString(1);
					}
				});
				//
				if (nameList != null && nameList.size() > 0) {
					onboardname = nameList.get(0);
				}
				//
				//
				java.util.Vector<String> vToaddress = new java.util.Vector<String>();
				java.util.Vector<String> vRecregioncode = new java.util.Vector<String>();
				java.util.Vector<String> vRectimezone = new java.util.Vector<String>();
				// get recruiters, who will receive this notification
				sql = "select t1.email, t1.region_code, t1.timezone from trecruiter t1, tinterviewschedule t2" + " where t1.id = t2.recruiterid and t1.groupid = t2.recruiter_teamid" + " and t1.active=1" //
						+ " and bitand(t1.permission_recruiter, 134217728)>0" + " and t2.id=? and t2.recruiter_teamid=?" + " and (bitand(t1.permission_recruiter, 4194304)=0 or nvl(t1.division,0)=0"//
						+ "      or (bitand(t1.permission_recruiter, 4194304)>0" + "          and t1.division=?))" + " union " + " select t1.email, t1.region_code, t1.timezone from trecruiter t1, trecruiterrfq t2" + " where t1.id = t2.recruiterid "//
						+ " and t1.active=1" + " and t2.rfqid=? and t1.groupid=? " + " and bitand(t1.permission_recruiter, 67108864)>0" + " and (t2.lead_recruiter=1 or t2.lead_sales=1)"//
						+ " and (bitand(t1.permission_recruiter, 4194304)=0 or nvl(t1.division,0)=0" + "      or (bitand(t1.permission_recruiter, 4194304)>0" + "          and t1.division=?))" + " union "//
						+ " select t1.email, t1.region_code, t1.timezone from trecruiter t1, trecruiterrfq t2" + " where t1.id = t2.recruiterid" + " and t1.active=1" + " and t2.rfqid=? and t1.groupid=?"//
						+ " and bitand(t1.permission_recruiter, 33554432)>0" + " and (bitand(t1.permission_recruiter, 4194304)=0 or nvl(t1.division,0)=0" + "      or (bitand(t1.permission_recruiter, 4194304)>0" + "          and t1.division=?))"//
						+ " union " + " select t1.email, t1.region_code, t1.timezone from trecruiter t1" + " where t1.groupid=?" + " and t1.active=1" + " and bitand(t1.permission_recruiter, 16777216)>0"//
						+ " and (bitand(t1.permission_recruiter, 4194304)=0 or nvl(t1.division,0)=0" + "      or (bitand(t1.permission_recruiter, 4194304)>0" + "          and t1.division=?))" + " union"//
						+ " select distinct t1.email, t1.region_code, t1.timezone from trecruiter t1, tcandidate_onboarding t2"//
						+ " where t1.active=1 and t2.teamid=? and t2.candidateid=? and t2.interviewid=? and t1.groupid=t2.teamid and t1.id = t2.recruiterid ";
				//
				params = new Object[] { startid, teamid, divisionid, rfqid, teamid, divisionid, rfqid, teamid, divisionid, teamid, divisionid, teamid, candidateid, interviewid };
				//
				jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
					
					@Override
					public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						vToaddress.add(rs.getString("email"));
						if (rs.getString("region_code") != null && rs.getString("region_code").length() > 0)
							vRecregioncode.add(rs.getString("region_code"));
						else
							vRecregioncode.add(teamregion);
						if (rs.getString("timezone") != null && rs.getString("timezone").length() > 0)
							vRectimezone.add(rs.getString("timezone"));
						else
							vRectimezone.add(teamtimezoneid);
						//
						return true;
					}
				});
				//
				//
				//
				StringBuffer missinglist = new StringBuffer("");
				sql = " select t1.id as onboardid, t2.name as onboardname, t1.onboardingstatus from tcandidate_onboarding t1, " + " (select name, id, teamid from tonboardings where nvl(require_return,0) = 1 and teamid=? and nvl(send_to,0)=? "
						+ "  union select description as name, id, teamid from tcompanyattachments where nvl(require_return, 0) = 1 and teamid=? and nvl(send_to,0)=? "
						+ "  union select description as name, id, teamid from tcontactattachments where nvl(require_return, 0) = 1 and teamid=? and nvl(send_to,0)=? ) t2 "
						+ " where t1.teamid=? and t1.candidateid=? and t1.interviewid=? and nvl(t1.deleted,0)=0 and (t1.onboardingstatus=0 or t1.onboardingstatus=2) " + " and t2.id=t1.docid and t2.teamid=t1.teamid order by upper(t2.name) ";
				//
				params = new Object[] { teamid, (uploadedby.intValue() == 0 ? 0 : 1), teamid, (uploadedby.intValue() == 0 ? 0 : 1), teamid, (uploadedby.intValue() == 0 ? 0 : 1), teamid, candidateid, interviewid };
				//
				//
				jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
					
					@Override
					public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						long onboardid = rs.getLong(1);
						String oname = rs.getString(2);
						int ostatus = rs.getInt(3);
						if (ostatus == 0) { // check if missing doc
							int cnt = checkIfMissingDocument(jdbcTemplate, onboardid, jobDivaSession.getTeamId());
							if (cnt == 0)
								missinglist.append("<li>" + oname + "</li>");
							//
						}
						return true;
					}
				});
				//
				//
				String strMissingList = (missinglist.toString().length() > 0) ? "The following on-boarding documents are still missing:<ul>" + missinglist.toString() + "</ul>" : "";
				//
				String mailbody = "";
				for (int i = 0; i < vToaddress.size(); i++) {
					String recruiterregion = (String) vRecregioncode.get(i);
					String recruitertimezone = (String) vRectimezone.get(i);
					JDLocale regionFormat = new JDLocale(recruiterregion, DateFormat.SHORT, DateFormat.SHORT, TimeZone.getTimeZone(recruitertimezone), 2, 0); // "MM/dd/yyyy
																																								// hh:mm:ss
																																								// a"
					switch (uploadedby.intValue()) {
						case 0:
							mailbody = "<a href='" + apache_location + "/employers/open_candidate.jsp?candid=" + uploadedbyId.longValue() + "&teamid=" + teamid + "&docids=-1'>" + uploadedbyname + "</a>" + " has uploaded \"" + fileName + "\" as \""
									+ onboardname + "\" in regards to " + (rfqid > 0 ? "job #" + refno_team + " - " + title : "label:" + label) + " on " + regionFormat.outputDate(Calendar.getInstance().getTime()) + ".<br>";
							break;
						case 2:
							mailbody = uploadedbyname + " has uploaded \"" + fileName + "\" as \"" + onboardname + "\" in regards to Candidate <a href='" + apache_location + "/employers/open_candidate.jsp?candid=" + candidateid + "&teamid=" + teamid
									+ "&docids=-1'>" + cand_name + "</a>" + " for " + (rfqid > 0 ? "job #" + refno_team + " - " + title : "label:" + label) + " on " + regionFormat.outputDate(Calendar.getInstance().getTime()) + ".<br>";
							break;
					}
					mailbody += strMissingList;
					try {
						mailServer.sendMail((String) vToaddress.get(i), "onboarding@jobdiva.com", "A Document was Uploaded by " + (uploadedby.intValue() == 0 ? "Candidate " : "Supplier ") + uploadedbyname, mailbody);
					} catch (Exception email_e) {
						logger.error("error in sending email:" + email_e.getMessage());
					}
				}
			}
			//
			//
			if (iscompleted) { // send onboarding completed notification
				java.util.Vector<String> vToaddress = new java.util.Vector<String>();
				java.util.Vector<String> vRecregioncode = new java.util.Vector<String>();
				java.util.Vector<String> vRectimezone = new java.util.Vector<String>();
				// get recruiters, who will receive this notification
				sql = "select t1.email, t1.region_code, t1.timezone from trecruiter t1, tinterviewschedule t2" + " where t1.id = t2.recruiterid and t1.groupid = t2.recruiter_teamid" + " and t1.active=1" //
						+ " and substr(t1.permission2_recruiter,16,1)='1'" + " and t2.id=? and t2.recruiter_teamid=?" + " and (substr(t1.permission2_recruiter, 20, 1)='0' or nvl(t1.division,0)=0" //
						+ "      or (substr(t1.permission2_recruiter, 20, 1)='1'" + "          and t1.division=?))" + " union " + " select t1.email, t1.region_code, t1.timezone from trecruiter t1, trecruiterrfq t2" + " where t1.id = t2.recruiterid " //
						+ " and t1.active=1" + " and t2.rfqid=? and t1.groupid=? " + " and substr(t1.permission2_recruiter, 17,1)='1'" + " and (t2.lead_recruiter=1 or t2.lead_sales=1)" //
						+ " and (substr(t1.permission2_recruiter, 20, 1)='0' or nvl(t1.division,0)=0" + "      or (substr(t1.permission2_recruiter, 20, 1)='1'" + "          and t1.division=?))" + " union " //
						+ " select t1.email, t1.region_code, t1.timezone from trecruiter t1, trecruiterrfq t2" + " where t1.id = t2.recruiterid" + " and t1.active=1" + " and t2.rfqid=? and t1.groupid=?" //
						+ " and substr(t1.permission2_recruiter, 18,1)='1'" + " and (substr(t1.permission_recruiter, 20, 1)='0' or nvl(t1.division,0)=0" + "      or (substr(t1.permission_recruiter, 20, 1)='1'" + "          and t1.division=?))" //
						+ " union " + " select t1.email, t1.region_code, t1.timezone from trecruiter t1" + " where t1.groupid=?" + " and t1.active=1" + " and substr(t1.permission2_recruiter, 19,1)='1'" //
						+ " and (substr(t1.permission2_recruiter, 20, 1)='0' or nvl(t1.division,0)=0" + "      or (substr(t1.permission2_recruiter, 20, 1)='1'" + "          and t1.division=?))" + " union" //
						+ " select distinct t1.email, t1.region_code, t1.timezone from trecruiter t1, tcandidate_onboarding t2" //
						+ " where t1.active=1 and t2.teamid=? and t2.candidateid=? and t2.interviewid=? and t1.groupid=t2.teamid and t1.id = t2.recruiterid ";
				//
				params = new Object[] { startid, teamid, divisionid, rfqid, teamid, divisionid, rfqid, teamid, divisionid, teamid, divisionid, teamid, candidateid, interviewid };
				//
				//
				jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
					
					@Override
					public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						vToaddress.add(rs.getString("email"));
						if (rs.getString("region_code") != null && rs.getString("region_code").length() > 0)
							vRecregioncode.add(rs.getString("region_code"));
						else
							vRecregioncode.add(teamregion);
						if (rs.getString("timezone") != null && rs.getString("timezone").length() > 0)
							vRectimezone.add(rs.getString("timezone"));
						else
							vRectimezone.add(teamtimezoneid);
						//
						return true;
					}
				});
				//
				//
				//
				for (int i = 0; i < vToaddress.size(); i++) {
					logger.info("send email to:" + (String) vToaddress.get(i));
					// String recruiterregion = (String) vRecregioncode.get(i);
					// String recruitertimezone = (String) vRectimezone.get(i);
					String mailbody = "All On-Boarding Documents requiring return have been uploaded to Candidate " + cand_name + "'s record. These documents were assigned in regards to "
							+ (rfqid > 0 ? "Job #" + refno_team + " - " + title : "label:" + label) + ".";
					try {
						mailServer.sendMail((String) vToaddress.get(i), "onboarding@jobdiva.com", "On-boarding Package for " + cand_name + " is complete", mailbody);
					} catch (Exception email_e) {
						logger.error("error in sending email:" + email_e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			logger.error("sendOnboardingNotifications Error : " + e.getMessage());
			e.printStackTrace(System.out);
		}
	}
	
	protected int checkIfMissingDocument(JdbcTemplate jdbcTemplate, long onboardid, Long teamId) {
		String sql = "select count(*) from tcandidate_onboarding_docs where onboardingid=? and teamid=? and nvl(deleted,0)=0 ";
		Object[] params = new Object[] { onboardid, teamId };
		List<Integer> list = jdbcTemplate.query(sql, params, new RowMapper<Integer>() {
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return rs.getInt(1);
			}
		});
		//
		return list != null && list.size() > 0 ? list.get(0) : 0;
	}
}
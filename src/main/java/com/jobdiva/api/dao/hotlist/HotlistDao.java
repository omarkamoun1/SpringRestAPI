package com.jobdiva.api.dao.hotlist;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.axelon.category.CandidateLicences;
import com.axelon.category.Licence;
import com.axelon.oc4j.ServletRequestData;
import com.axelon.recruiter.WorkBenchObject;
import com.axelon.shared.AuthenticationToken;
import com.axelon.shared.WorkExpUtil;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.bi.BIDataDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.servlet.ServletTransporter;

/**
 * @author Joseph Chidiac
 *
 */
@Component
public class HotlistDao extends AbstractJobDivaDao {
	
	@Autowired
	BIDataDao biDataDao;
	
	private String getAbstract(Connection docDbConnection, String global_id, int dbid) throws Exception {
		String sql = null;
		if (dbid == 2)
			sql = "select abstract from tcandidatedocument2  where global_id=?";
		else
			sql = "select abstract from tcandidatedocument  where global_id=?";
		//
		PreparedStatement pstmt = docDbConnection.prepareStatement(sql);
		pstmt.setString(1, global_id);
		//
		String abstractStr = "";
		ResultSet rs = pstmt.executeQuery();
		//
		if (rs.next()) {
			abstractStr = rs.getString("abstract");
		}
		//
		if (pstmt != null) {
			pstmt.close();
		}
		//
		if (rs != null) {
			rs.close();
		}
		//
		return abstractStr != null ? abstractStr : "";
	}
	
	private Object[] getDocDbId(JdbcTemplate jdbcTemplate, long teamid, long candidateid) throws Exception {
		Object[] obj = new Object[2];
		String sql = "select global_id, dbid from Tcandidatedocument_Header where teamid=? and candidateid=? and rownum=1 order by datecreated_original desc";
		Object[] params = new Object[] { teamid, candidateid };
		jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				obj[0] = new Integer(rs.getInt("dbid"));
				obj[1] = rs.getString("global_id");
				//
				return true;
			}
		});
		//
		return obj;
	}
	
	private String[] getFlags_HL(JdbcTemplate jdbcTemplate, long candiadteId, long teamid) throws RemoteException {
		String sql_stmt = null;
		int l_count = 0;
		//
		String[] flags = new String[4];
		flags[0] = "";
		flags[1] = "";
		flags[2] = "";
		flags[3] = "";
		//
		// get notes flag
		if (teamid != 0) {
			sql_stmt = "select count(*) from tcandidatenotes " + "where candidateid = ? and recruiter_teamid = ? and auto in (0,4) and nvl(deleted,0) = 0 ";
			Object[] params = new Object[] { candiadteId, teamid };
			List<Integer> list = jdbcTemplate.query(sql_stmt, params, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt(1);
				}
			});
			l_count = list != null && list.size() > 0 ? list.get(0) : 0;
			if (l_count > 0)
				flags[0] = "~" + String.valueOf(teamid) + "~";
		} else {
			sql_stmt = "select distinct candidateid,recruiter_teamid from " + "tcandidatenotes where candidateid = ? and auto in (0,4) and nvl(deleted,0) = 0 ";
			//
			Object[] params = new Object[] { candiadteId };
			jdbcTemplate.query(sql_stmt, params, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					long l_teamid = rs.getLong(2);
					if (flags[0] == null)
						flags[0] = "";
					flags[0] = flags[0] + "~" + String.valueOf(l_teamid);
					return 0;
				}
			});
			if (flags[0] != null)
				flags[0] = flags[0] + "~";
		}
		// get submittal flag
		if (teamid != 0) {
			sql_stmt = "select count(*) from tinterviewschedule " + "where candidateid = ? and recruiter_teamid = ? ";
			Object[] params = new Object[] { candiadteId, teamid };
			List<Integer> list = jdbcTemplate.query(sql_stmt, params, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt(1);
				}
			});
			l_count = list != null && list.size() > 0 ? list.get(0) : 0;
			//
			if (l_count > 0)
				flags[1] = "~" + String.valueOf(teamid) + "~";
		} else {
			sql_stmt = "select distinct candidateid,recruiter_teamid from " + "tinterviewschedule where candidateid = ? ";
			Object[] params = new Object[] { candiadteId };
			jdbcTemplate.query(sql_stmt, params, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					long l_teamid = rs.getLong(2);
					//
					if (flags[1] == null)
						flags[1] = "";
					flags[1] = flags[1] + "~" + String.valueOf(l_teamid);
					//
					return 0;
				}
			});
			//
			if (flags[1] != null)
				flags[1] = flags[1] + "~";
		}
		// get rating flag
		if (teamid != 0) {
			sql_stmt = "select dcatid from tcandidate_category " + "where candidateid = ? and teamid = ? and catid=2";
			//
			Object[] params = new Object[] { candiadteId, teamid };
			jdbcTemplate.query(sql_stmt, params, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					int l_dcatid = rs.getInt(1);
					flags[2] = "~" + String.valueOf(teamid) + "~" + String.valueOf(l_dcatid) + "#";
					//
					return 0;
				}
			});
			//
		} else {
			sql_stmt = "select teamid,dcatid from " + "tcandidate_category where candidateid = ? and catid=2";
			//
			Object[] params = new Object[] { candiadteId };
			jdbcTemplate.query(sql_stmt, params, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					long l_teamid = rs.getLong(1);
					int l_dcatid = rs.getInt(2);
					if (flags[2] == null)
						flags[2] = "";
					flags[2] = flags[2] + "~" + String.valueOf(l_teamid) + "~" + String.valueOf(l_dcatid) + "#";
					//
					return 0;
				}
			});
		}
		//
		// get emailmerge_optoutdate
		if (teamid != 0) {
			sql_stmt = "select dateuntil_candidate from temailmerge_flag " + "where candidateid = ? and recruiter_teamid = ? and nvl(dirty,0) <> 2 ";
			//
			Object[] params = new Object[] { candiadteId, teamid };
			jdbcTemplate.query(sql_stmt, params, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					if (rs.getTimestamp(1) != null) {
						long l_dateuntil = rs.getTimestamp(1).getTime();
						flags[3] = "~" + String.valueOf(teamid) + "~" + String.valueOf(l_dateuntil) + "#";
					}
					//
					return 0;
				}
			});
			//
		} else {
			sql_stmt = "select teamid,dateuntil_candidate from " + "temailmerge_flag where candidateid = ? and nvl(dirty,0) <> 2";
			//
			Object[] params = new Object[] { candiadteId };
			jdbcTemplate.query(sql_stmt, params, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					long l_teamid = rs.getLong(1);
					long l_dateuntil = rs.getTimestamp(2).getTime();
					if (flags[3] == null)
						flags[3] = "";
					flags[3] = flags[3] + "~" + String.valueOf(l_teamid) + "~" + String.valueOf(l_dateuntil) + "#";
					//
					return 0;
				}
			});
		}
		//
		return flags;
	}
	
	public Long createCandidateHoltilst(JobDivaSession jobDivaSession, String name, Long linkToJobId, Long linkToHiringManagerId, String description, List<Long> userIds, List<Long> groupIds, List<Long> divisionIds) throws Exception {
		//
		//
		StringBuffer message = new StringBuffer();
		//
		if (name == null || name.trim().isEmpty()) {
			message.append("name is required. \r\n");
		}
		//
		if (description == null || description.trim().isEmpty()) {
			message.append("description is required. \r\n");
		}
		//
		// if ((userIds == null || userIds.size() == 0) && (groupIds == null ||
		// groupIds.size() == 0) && (divisionIds == null || divisionIds.size()
		// == 0)) {
		// message.append(" at least userIds, groupIds or divisionIds must be
		// assigned. \r\n");
		// }
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		userIds = userIds != null ? userIds : new ArrayList<Long>();
		ArrayList<Long> allUsers = new ArrayList<Long>(userIds);
		//
		if (groupIds != null && groupIds.size() > 0) {
			//
			String sql_stmt = "SELECT ID " + //
					" FROM TRECRUITER " + //
					" WHERE GROUPID IN (:groupId) "; //
			LinkedHashMap<String, Object> paramList = new LinkedHashMap<String, Object>();
			paramList.put("groupId", groupIds);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
			//
			namedParameterJdbcTemplate.query(sql_stmt, paramList, new RowMapper<Boolean>() {
				
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					Long userId = rs.getLong("ID");
					if (!allUsers.contains(userId))
						allUsers.add(userId);
					return true;
				}
			});
		}
		//
		//
		if (divisionIds != null && divisionIds.size() > 0) {
			String sql_stmt = "SELECT ID " + //
					" FROM TRECRUITER " + //
					" WHERE DIVISION IN (:divisions) " + //
					" and bitand(leader,:leader1) = 0 " + //
					" and bitand(leader,:leader2) <> 2097152  "; //
			LinkedHashMap<String, Object> paramList = new LinkedHashMap<String, Object>();
			paramList.put("divisions", divisionIds);
			paramList.put("leader1", AuthenticationToken.LOGIN_TYPE_SPIDER);
			paramList.put("leader2", AuthenticationToken.LOGIN_TYPE_TIMESHEET_APPROVER);
			//
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
			//
			namedParameterJdbcTemplate.query(sql_stmt, paramList, new RowMapper<Boolean>() {
				
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					Long userId = rs.getLong("ID");
					if (!allUsers.contains(userId))
						allUsers.add(userId);
					return true;
				}
			});
		}
		//
		//
		WorkBenchObject workBenchObject = new WorkBenchObject(0, jobDivaSession.getRecruiterId());
		workBenchObject.setTeamID(jobDivaSession.getTeamId());
		workBenchObject.WorkBenchName = name;
		workBenchObject.Note = description;
		workBenchObject.Active = 1;
		workBenchObject.rfqid = linkToJobId != null ? linkToJobId : 0L;
		workBenchObject.contactid = linkToHiringManagerId != null ? linkToHiringManagerId : 0L;
		//
		Long[] recruiterList = (Long[]) allUsers.toArray(new Long[allUsers.size()]);
		//
		workBenchObject.recruilerList = recruiterList;
		//
		Connection connection = null;
		//
		try {
			//
			connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			//
			Long id = insertWorkBench(workBenchObject, connection);
			//
			connection.setAutoCommit(true);
			//
			return id;
		} catch (SQLException e) {
			connection.rollback();
			System.err.println(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
		return null;
	}
	
	public Long insertWorkBench(WorkBenchObject wObj, Connection con) throws SQLException, Exception {
		CallableStatement cstmt = null;
		String errMsg = "";
		Long workBenchID = new Long(0);
		try {
			String qry = "";
			if (wObj == null)
				return null;
			qry = "{CALL pkg_Recruiter.InsertWorkBench(?,?,?,?,?,?,?,?,?)}";
			cstmt = con.prepareCall(qry);
			cstmt.setLong(1, wObj.getRecruiterID());
			cstmt.setLong(2, wObj.getTeamID());
			cstmt.setString(3, wObj.WorkBenchName);
			cstmt.setString(4, wObj.Note);
			cstmt.setInt(5, wObj.Active);
			cstmt.setLong(6, wObj.rfqid);
			cstmt.setLong(7, wObj.contactid);
			cstmt.registerOutParameter(8, Types.NUMERIC);
			cstmt.registerOutParameter(9, Types.VARCHAR);
			cstmt.execute();
			workBenchID = new Long(cstmt.getLong(8));
			//
			InsertMultipleRecruiter_WorkBench(workBenchID.longValue(), wObj.recruilerList, wObj.getRecruiterID(), 1, con);
			//
			errMsg = cstmt.getString(9);
			//
			if (errMsg != null) {
				if (cstmt != null)
					cstmt.close();
				throw new SQLException("Error in pkg_Recruiter.InsertWorkBench  : " + errMsg);
			}
			//
			if (cstmt != null)
				cstmt.close();
			//
			return workBenchID;
			//
		} catch (SQLException sqle) {
			//
			if (cstmt != null)
				cstmt.close();
			//
			System.out.println("Error in Inserting WorkBench  :  " + sqle);
			//
			throw sqle;
		} catch (Exception e) {
			//
			if (cstmt != null)
				cstmt.close();
			//
			System.out.println("Error in Inserting WorkBench  :  " + e);
			//
			throw e;
		}
	}
	
	public Boolean InsertMultipleRecruiter_WorkBench(long workBenchID, Long[] arrRecruiterID, long recruiterid, int privateCall, Connection con) throws Exception {
		String sqlStr = null;
		PreparedStatement pstmt = null;
		try {
			if (arrRecruiterID.length > 0)
				deleteRecruiters_WorkBench(arrRecruiterID, workBenchID, con);
			for (int i = 0; i < arrRecruiterID.length; i++) {
				sqlStr = "INSERT INTO tworkbench_recruiter (workBenchID, recruiterid, candidatesadded)" + " VALUES (?,?,?)";
				// System.out.println("InsertMultipleRecruiter_WorkBench :Tyring
				// to insert workBenchID="+workBenchID + "
				// Recruiterid="+arrRecruiterID[i].longValue());
				pstmt = con.prepareStatement(sqlStr);
				pstmt.setLong(1, workBenchID);
				pstmt.setLong(2, arrRecruiterID[i].longValue());
				pstmt.setInt(3, 0);
				try {
					pstmt.execute();
				} catch (SQLException sqlExcp) {
					System.out.println("SQLException=" + sqlExcp);
				} finally {
					pstmt.close();
				}
			}
			try {
				pstmt.close();
			} catch (Exception ee) {
			}
			return new Boolean(true);
		} catch (SQLException ex) {
			try {
				pstmt.close();
			} catch (Exception ee) {
			}
			System.out.println("SQLException = " + ex.getMessage());
			ex.printStackTrace();
			throw ex;
		} catch (Exception e) {
			try {
				pstmt.close();
			} catch (Exception ee) {
			}
			System.out.println("Exception = " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	private void deleteRecruiters_WorkBench(Long[] listOfRecruiters, long workbenchid, Connection con) throws SQLException, Exception {
		String qry = "";
		String delQry = "";
		boolean found = false;
		//
		ResultSet rs = null;
		Long toBeDeleted[] = new Long[0];
		PreparedStatement pstmt = null;
		long existingRecruiter = 0;
		Vector<Long> vDeleted = new Vector<Long>();
		try {
			qry = "Select recruiterid from tworkbench_recruiter where workbenchid=?";
			pstmt = con.prepareCall(qry);
			pstmt.setLong(1, workbenchid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				found = false;
				existingRecruiter = rs.getInt("recruiterid");
				for (int i = 0; i < listOfRecruiters.length; i++) {
					if (existingRecruiter == listOfRecruiters[i].longValue()) {
						found = true;
						break;
					}
				}
				if (!found) {
					vDeleted.addElement(new Long(existingRecruiter));
				}
			}
			toBeDeleted = new Long[vDeleted.size()];
			vDeleted.copyInto(toBeDeleted);
			for (int i = 0; i < toBeDeleted.length; i++) {
				delQry = "delete from tworkbench_recruiter where workbenchid=? and recruiterid=?";
				pstmt = con.prepareStatement(delQry);
				pstmt.setLong(1, workbenchid);
				pstmt.setLong(2, toBeDeleted[i].longValue());
				pstmt.execute();
			}
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException ex) {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			System.out.println("deleteRecruiters_WorkBench : SQLException = " + ex.getMessage());
			ex.printStackTrace();
			throw ex;
		} catch (Exception e) {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			System.out.println("deleteRecruiters_WorkBench : Exception = " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	protected void insertHotlistIDs(JdbcTemplate jdbcTemplate, Long hotlist_id, Long rfqid) throws RemoteException {
		String sqlInsert = "INSERT INTO thotlist_rfq (hotlist_id,rfqid) " + "VALUES (?,?)";
		//
		Object[] params = new Object[] { hotlist_id, rfqid };
		//
		jdbcTemplate.update(sqlInsert, params);
		//
		String sql_update = "update tworkbench set criteria_changed = 1 " + "where id = ? ";
		//
		params = new Object[] { rfqid };
		//
		jdbcTemplate.update(sql_update, params);
		//
	}
	
	public Boolean addCandidatesToHotlist(JobDivaSession jobDivaSession, Long hotListid, List<Long> candidateIds) throws Exception {
		for (Long candidateId : candidateIds) {
			if (candidateId != null && candidateId > 0)
				addCandidateToHotlist(jobDivaSession, hotListid, candidateId);
		}
		//
		return true;
	}
	
	public Boolean addCandidateToHotlist(JobDivaSession jobDivaSession, Long hotListid, Long candidateId) throws Exception {
		//
		//
		StringBuffer message = new StringBuffer();
		//
		if (hotListid == null || hotListid <= 0) {
			message.append("Invalid  hotListid. \r\n");
		}
		//
		if (candidateId == null || candidateId <= 0) {
			message.append("Invalid  candidateId. \r\n");
		}
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		long[] resumeid = new long[4];
		resumeid[0] = jobDivaSession.getTeamId();// topcanteamid;
		resumeid[1] = 0;// topdocid;
		resumeid[2] = 0;// Ldocdate;
		resumeid[3] = 0; // Ldocdate_original;
		//
		String[] flags = new String[6];
		flags[0] = "";
		flags[1] = "";
		flags[2] = "";
		flags[3] = "";
		flags[4] = "";
		flags[5] = "";
		//
		//
		//
		long recruiter_teamid = jobDivaSession.getTeamId();
		long recruiter_id = jobDivaSession.getRecruiterId();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		//
		String[] tmp_flags = getFlags_HL(jdbcTemplate, candidateId, recruiter_teamid);
		flags[0] = tmp_flags[0];
		flags[1] = tmp_flags[1];
		flags[2] = tmp_flags[2];
		flags[5] = tmp_flags[3];
		//
		//
		String[] personal = new String[6];
		//
		Licence lr = new Licence();
		lr.candidateid = candidateId;
		lr.teamid = recruiter_teamid;
		//
		String tomcatLocation = appProperties.getLoadBalanceServletLocation();
		String lcienseServlet = tomcatLocation + "/category/servlet/LicenceServlet";
		//
		lr.reqCode = 40;
		Object reqData = new ServletRequestData(0, null, lr);
		Object obj = ServletTransporter.callServlet(lcienseServlet, reqData);
		CandidateLicences can_lic = null;
		if (obj != null) {
			try {
				can_lic = (CandidateLicences) obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		lr.reqCode = 43;
		reqData = new ServletRequestData(0, null, lr);
		obj = ServletTransporter.callServlet(lcienseServlet, reqData);
		CandidateLicences can_cert = null;
		if (obj != null) {
			try {
				can_cert = (CandidateLicences) obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//
		// get licences and certificates
		//
		if (can_lic != null && can_lic.licences != null) {
			for (int j = 0; j < can_lic.licences.length; j++) {
				Licence lic = can_lic.licences[j];
				if (lic.state == null)
					lic.state = "";
				String val = lic.expiry_date + "_" + lic.verified + "_" + lic.onfile + "_" + lic.state;
				flags[3] = WorkExpUtil.updateTeamPlusAnotherIDParsed(flags[3], can_lic.teamid, lic.id, val);
			}
			// System.out.println("flag 3=" + flags[3]);
		}
		if (can_cert != null && can_cert.licences != null) {
			for (int j = 0; j < can_cert.licences.length; j++) {
				Licence lic = can_cert.licences[j];
				if (lic.state == null)
					lic.state = "";
				String val = lic.expiry_date + "_" + lic.verified + "_" + lic.onfile + "_" + lic.state;
				flags[4] = WorkExpUtil.updateTeamPlusAnotherIDParsed(flags[4], can_cert.teamid, lic.id, val);
			}
			// System.out.println("flag 4=" + flags[4]);
		}
		//
		//
		Object[] objArray = getDocDbId(jdbcTemplate, resumeid[0], candidateId);
		int dbid = objArray[0] != null ? ((Integer) objArray[0]).intValue() : 1;
		String global_id = objArray[1] != null ? (String) objArray[1] : null;
		//
		Connection docDbConnection = biDataDao.getDocDbConnection(dbid);
		try {
			if (docDbConnection != null)
				personal[4] = getAbstract(docDbConnection, global_id, dbid);
		} finally {
			if (docDbConnection != null)
				docDbConnection.close();
		}
		//
		//
		//
		String l_notes = "" + WorkExpUtil.isTeamIDParsed(flags[0], recruiter_teamid);
		String l_presented = "" + WorkExpUtil.isTeamIDParsed(flags[1], recruiter_teamid);
		String l_rating = WorkExpUtil.getTeamFlagParsed(flags[2], recruiter_teamid);
		String l_optoutdate = WorkExpUtil.getTeamFlagParsed(flags[5], recruiter_teamid);
		long candidate_teamid = (long) resumeid[0];
		int docid = (int) resumeid[1];
		//
		//
		//
		String sql_stmt = "Delete from tcandidate_hotlist Where hotlist_id = ? and candidateid = ? ";
		Object[] params = new Object[] { hotListid, candidateId };
		jdbcTemplate.update(sql_stmt, params);
		//
		//
		//
		sql_stmt = "select firstname, lastname, state, homephone, city from tcandidate where id = ? and teamid in (0,?) ";
		params = new Object[] { candidateId, jobDivaSession.getTeamId() };
		jdbcTemplate.query(sql_stmt, params, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				personal[0] = rs.getString(1);
				personal[1] = rs.getString(2);
				personal[2] = rs.getString(3);
				personal[3] = rs.getString(4);
				personal[5] = rs.getString(5);
				return true;
			}
		});
		//
		//
		//
		//
		// get employee status
		sql_stmt = "select DCATID from tcandidate_category where candidateid = ? and teamid=? ";
		sql_stmt += "and catid=1 and dirty<>2";
		params = new Object[] { candidateId, jobDivaSession.getTeamId() };
		List<String> list = jdbcTemplate.query(sql_stmt, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		String emp_status = list != null && list.size() > 0 ? list.get(0) : "";
		//
		//
		//
		//
		//
		sql_stmt = "insert into tcandidate_hotlist (candidateid,include,recruiterid," + "firstname,lastname,state,homephone,notes_1,presented_1," //
				+ "rating_1,hotlist_id,recruiter_teamid,manual,viewed,checklist," //
				+ "teamid,docid,tag,datecreated,datecreated_original,licences_1,certificates_1, optoutdate, city, EMPLOYEE_STATUS) " //
				+ "values (?,1,?,?,?,?,?," + "?,?,?,?,?,1,0,0,?,?,?,?,?,?,?,?,?,?)";
		//
		Timestamp t_optoutdate = null;
		try {
			t_optoutdate = (l_optoutdate != null && l_optoutdate.trim().length() > 0) ? new Timestamp(Long.parseLong(l_optoutdate)) : null;
		} catch (Exception e) {
		}
		params = new Object[] { candidateId, //
				0, //
				personal[0], //
				personal[1], //
				personal[2], //
				personal[3], //
				l_notes, //
				l_presented, //
				l_rating, //
				hotListid, //
				recruiter_teamid, //
				candidate_teamid, //
				docid, //
				personal[4], //
				new java.sql.Timestamp(resumeid[2]), //
				resumeid[3] == 0 ? null : new java.sql.Timestamp(resumeid[3]), //
				flags[3], //
				flags[4], //
				t_optoutdate, //
				personal[5], //
				emp_status //
		};
		//
		jdbcTemplate.update(sql_stmt, params);
		//
		//
		String sql_stmt_update = "update tworkbench_recruiter set candidatesadded = 1  where workbenchid = ? and recruiterid <> ? ";
		params = new Object[] { hotListid, recruiter_id };
		jdbcTemplate.update(sql_stmt_update, params);
		//
		return true;
	}
	
	public Long createContactHotlist(JobDivaSession jobDivaSession, String name, Boolean active, Boolean isPrivate, String description, List<Long> sharedWithIds) throws Exception {
		//
		isPrivate = isPrivate != null ? isPrivate : false;
		active = active != null ? active : false;
		//
		StringBuffer message = new StringBuffer();
		//
		if (name == null || name.trim().isEmpty()) {
			message.append("name is required. \r\n");
		}
		//
		// if (sharedWithIds == null || sharedWithIds.size() == 0) {
		// message.append("sharedWithIds must contains at least one user.
		// \r\n");
		// }
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		String sql = "select contact_hotlistid.nextval from dual";
		List<Long> list = jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return rs.getLong(1);
			}
		});
		//
		long id = (list != null && list.size() > 0) ? list.get(0) : 0;
		//
		//
		String sqlInsert = "insert into tcontact_hotlist (id, recruiterid, teamid, name, notes, active, type, privatelist, datecreated) values( ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE) ";
		Object[] params = new Object[] { id, jobDivaSession.getRecruiterId(), jobDivaSession.getTeamId(), name, description, active, 1, isPrivate };
		jdbcTemplate.update(sqlInsert, params);
		//
		sharedWithIds = sharedWithIds != null ? sharedWithIds : new ArrayList<Long>();
		//
		//
		//
		if (jobDivaSession.getRecruiterId() > 0 && !sharedWithIds.contains(jobDivaSession.getRecruiterId())) {
			sharedWithIds.add(jobDivaSession.getRecruiterId());
		}
		//
		//
		if (sharedWithIds != null) {
			for (Long userId : sharedWithIds) {
				sqlInsert = "insert into tcontact_hotlist_recruiter (hotlist_id, recruiterid) values(?,?) ";
				params = new Object[] { id, userId };
				jdbcTemplate.update(sqlInsert, params);
			}
		}
		//
		//
		return id;
	}
	
	public Boolean addContactToHotlist(JobDivaSession jobDivaSession, Long hotListid, Long contactId) throws Exception {
		//
		StringBuffer message = new StringBuffer();
		//
		if (hotListid == null || hotListid <= 0) {
			message.append("Invalid  hotListid. \r\n");
		}
		//
		if (contactId == null || contactId <= 0) {
			message.append("Invalid  contactId. \r\n");
		}
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		String sql = "insert into tcontact_hotlist_contacts values(?,?,?,?,sysdate,?,?,?) ";
		//
		Object params[] = new Object[] { hotListid, contactId, jobDivaSession.getTeamId(), jobDivaSession.getRecruiterId(), 0, null, null };
		//
		jdbcTemplate.update(sql, params);
		//
		return true;
	}
}

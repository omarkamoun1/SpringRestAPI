package com.jobdiva.api.dao.onboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.onboard.InterviewSchedule;
import com.jobdiva.api.model.onboard.OnBoardDocument;
import com.jobdiva.api.model.onboard.SuppPackage;

/**
 * @author Joseph Chidiac
 *
 */
@Component
public class SaveOnBoardDao extends OnBoardDao {
	
	private void checkRequiredDocuments(JobDivaSession jobDivaSession, InterviewSchedule interviewSchedule) throws Exception {
		String sql = "select  t2.ID as tabid,  t2.name as tabname,  " //
				+ " nvl(t1.require_distribution,0) as require_distribution,  " //
				+ " nvl(t1.docid,0) as docid,  t3.name as docname,  " //
				+ " t3.filename,  t3.require_return,  t3.readonly,  " //
				+ " nvl(t3.send_to,0) as send_to, t3.doctype  " //
				+ " from tonboardings_mapping t1, tonboarding_tab t2, tonboardings t3  " //
				+ " where t1.tabid(+) = t2.id  " //
				+ " and t1.teamid(+) = t2.teamid  " //
				+ " and t2.teamid = ?  " //
				+ " and nvl(t2.deleted ,0) = 0  " //
				+ " and t1.docid = t3.id  " //
				+ " and t2.teamid = t3.teamid  " //
				+ " and nvl(t3.deleted,0) = 0   " //
				+ " and ( t2.id = ?  OR t2.id > ? )  " //
				+ " order by upper(t2.name), upper(t3.name) ";
		//
		Long hireTypeId = interviewSchedule.getHireTypeId();
		Object[] params = { jobDivaSession.getTeamId(), hireTypeId, Integer.valueOf(100) };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		ArrayList<String> misssingDocuments = new ArrayList<>();
		//
		jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				Boolean mandatory = rs.getBoolean("require_distribution");
				Long tabid = rs.getLong("tabid");
				Long docId = rs.getLong("docid");
				String docName = rs.getString("docname");
				//
				if (mandatory) {
					boolean hirePackage = tabid.equals(interviewSchedule.getHireTypeId());
					if (hirePackage) {
						Boolean exist = interviewSchedule.checkDocumentExistsInCandidateDocuments(tabid, docId);
						if (!exist) {
							misssingDocuments.add(docName + "\r\n");
						}
					} else {
						Boolean exist = interviewSchedule.checkDocumentExistsSupppackages(tabid, docId);
						if (!exist) {
							misssingDocuments.add(docName + "\r\n");
						}
					}
				}
				//
				//
				return false;
			}
		});
		//
		if (misssingDocuments.size() > 0)
			throw new Exception("Documents : " + misssingDocuments.toString() + "\r\n are required.");
		//
	}
	
	public String getDocList(SuppPackage suppPackage) {
		if (suppPackage.getDocumentList() == null)
			return null;
		String strDocList = "";
		for (Long doc : suppPackage.getDocumentList())
			strDocList = strDocList + "," + doc;
		if (!strDocList.isEmpty())
			strDocList = strDocList + ",";
		return strDocList;
	}
	
	public Long saveOnboarding(JobDivaSession jobDivaSession, InterviewSchedule interviewSchedule) throws Exception {
		//
		if (interviewSchedule.getCandidateId() == null || interviewSchedule.getCandidateId().longValue() <= 0L)
			throw new Exception("CandiateId is required.");
		//
		if (interviewSchedule.getHireTypeId() == null || interviewSchedule.getHireTypeId().longValue() <= 0L)
			throw new Exception("HireTypeId is required.");
		//
		if ((interviewSchedule.getJobId() == null || interviewSchedule.getJobId().longValue() <= 0L) && (interviewSchedule.getLabel() == null || interviewSchedule.getLabel().trim().isEmpty()))
			throw new Exception("JobId or Label is required.");
		//
		Long teamid = jobDivaSession.getTeamId();
		Long recruiterId = Long.valueOf(jobDivaSession.getRecruiterId());
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		checkRequiredDocuments(jobDivaSession, interviewSchedule);
		//
		String primarySalesName = null;
		Long interviewId = null;
		Map<String, Long> map = new HashMap<>();
		//
		if (interviewSchedule.getJobId() != null && interviewSchedule.getJobId().longValue() > 0L) {
			String str = "select Id, primarysalesid " //
					+ " from tinterviewschedule " //
					+ " where recruiter_teamid = ? " //
					+ " AND candidateid = ?  " //
					+ " and rfqId = ? " //
					+ " and ROWNUM = 1";
			Object[] params = new Object[] { teamid, interviewSchedule.getCandidateId(), interviewSchedule.getJobId() };
			jdbcTemplate.query(str, params, new RowMapper<Boolean>() {
				
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					map.put("ID", rs.getLong("Id"));
					map.put("PRIMARYSALESID", rs.getLong("primarysalesid"));
					return false;
				}
			});
			interviewId = map.get("ID");
			Long primarySaleId = map.get("PRIMARYSALESID");
			str = "select firstname||' '||lastname as primarysalesname  from trecruiter  WHERE GROUPID = ?   AND ID = ?  ";
			params = new Object[] { teamid, primarySaleId };
			List<String> list1 = jdbcTemplate.query(str, params, new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getString("primarysalesname");
				}
			});
			primarySalesName = (list1 != null && list1.size() > 0) ? list1.get(0) : null;
		}
		///
		String sql = "select PREONBOARD_SEQ.nextval as NEXTVAL from dual";
		///
		List<Long> list = jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return rs.getLong("NEXTVAL");
			}
		});
		//
		Long preonbId = (list != null && list.size() > 0) ? list.get(0) : null;
		String sqlInsert = " insert into tpreonboardings  (id, teamid, candidateid, name, note, datecreated,employ_type,supplierid,jobid,interviewid)  values(?,?,?,?,?,sysdate,?,?,?,?) ";
		//
		if (primarySalesName == null)
			primarySalesName = interviewSchedule.getLabel();
		//
		Object[] params = { preonbId, teamid, interviewSchedule.getCandidateId(), primarySalesName, interviewSchedule.getRemark(), interviewSchedule.getHireTypeId(), interviewSchedule.getSupplierId(), interviewSchedule.getJobId(), interviewId };
		jdbcTemplate.update(sqlInsert, params);
		//
		Map<String, Long> prevSavedDocs = new HashMap<>();
		sql = "select id, docid, att_type from tcandidate_onboarding  where teamid = ?  and candidateid = ?  and interviewid = ?  and nvl(deleted,0) <> 2 ";
		params = new Object[] { jobDivaSession.getTeamId(), interviewSchedule.getCandidateId(), preonbId };
		jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				prevSavedDocs.put(rs.getInt("att_type") + "_" + rs.getLong("docid"), rs.getLong("id"));
				//
				return false;
			}
		});
		//
		sql = "update tcandidate_onboarding  set deleted = 1  where teamid = ?  and candidateid = ?  and interviewid = ?  and nvl(deleted,0) <> 2";
		//
		params = new Object[] { jobDivaSession.getTeamId(), interviewSchedule.getCandidateId(), preonbId };
		//
		if (interviewSchedule.getCandidateDocuments() != null) {
			//
			for (OnBoardDocument candidateDocument : interviewSchedule.getCandidateDocuments()) {
				long docid = candidateDocument.getId().longValue();
				int att_type = 0;
				int doctype = candidateDocument.getDocumentType().intValue();
				String key = att_type + "_" + docid;
				//
				if (prevSavedDocs.containsKey(key)) {
					sql = "update tcandidate_onboarding  set deleted = 0  where teamid = ?  and id = ?  and deleted=1";
					params = new Object[] { jobDivaSession.getTeamId(), prevSavedDocs.get(key) };
					jdbcTemplate.update(sql, params);
					continue;
				}
				//
				sql = "insert into tcandidate_onboarding(id, teamid, candidateid, interviewid, recruiterid, datecreated, att_type,docid,onboardingstatus, deleted, doctype)  values (ONBOARD_SEQ.nextval, ?, ?, ?, ?, sysdate, ?, ?, 0, 0, ?)";
				params = new Object[] { jobDivaSession.getTeamId(), interviewSchedule.getCandidateId(), preonbId, recruiterId, Integer.valueOf(att_type), Long.valueOf(docid), Integer.valueOf(doctype) };
				jdbcTemplate.update(sql, params);
			}
		}
		//
		updatePackageStatus(jdbcTemplate, teamid, interviewSchedule.getCandidateId(), preonbId);
		sql = "delete from tinterview_suppackage  where teamid = ?  and candidateid = ?  and interviewid = ? ";
		params = new Object[] { teamid, interviewSchedule.getCandidateId(), preonbId };
		//
		jdbcTemplate.update(sql, params);
		//
		if (interviewSchedule.getSupplementalPackages() != null) {
			//
			for (SuppPackage suppPackage : interviewSchedule.getSupplementalPackages()) {
				sql = " insert into tinterview_suppackage(teamid, candidateid, interviewid, suppkgid, doclist) values( ?, ?, ?, ?, ?) ";
				params = new Object[] { teamid, interviewSchedule.getCandidateId(), preonbId, suppPackage.getPackageId(), getDocList(suppPackage) };
				jdbcTemplate.update(sql, params);
			}
		}
		//
		return preonbId;
	}
	
	private boolean updatePackageStatus(JdbcTemplate jdbcTemplate, Long teamid, Long candidateId, Long preonbId) {
		int iscompleted = 0;
		try {
			//
			String sql = " (select t1.id, t1.teamid, t1.onboardingstatus  from tcandidate_onboarding t1, tonboardings t2 where t1.candidateid = ? and t1.teamid = ? and t1.interviewid=? and t1.att_type = 0 and t1.docid = t2.id and t1.teamid = t2.teamid and nvl(t1.deleted,0)=0 and t2.require_return=1 and t1.onboardingstatus<>1 ) union (select t1.id, t1.teamid, t1.onboardingstatus  from tcandidate_onboarding t1, tcompanyattachments t2 where t1.candidateid = ? and t1.teamid = ? and t1.interviewid=? and (t1.att_type = 1 or t1.att_type=2) and t1.docid = t2.id and t1.teamid = t2.teamid and nvl(t1.deleted,0)=0 and t2.require_return=1 and t1.onboardingstatus<>1 ) union (select t1.id, t1.teamid, t1.onboardingstatus  from tcandidate_onboarding t1, tcontactattachments t2 where t1.candidateid = ? and t1.teamid = ? and t1.interviewid=? and t1.att_type = 3 and t1.docid = t2.id and t1.teamid = t2.teamid and nvl(t1.deleted,0)=0 and t2.require_return=1 and t1.onboardingstatus<>1  )";
			sql = "select nvl(min(decode(a.onboardingstatus, 2, 0, (select count(id) from tcandidate_onboarding_docs where onboardingid=a.id and teamid=a.teamid and nvl(deleted,0)=0))), 1) as id from (" + sql + ") a ";
			//
			Object[] params = { candidateId, teamid, preonbId, candidateId, teamid, preonbId, candidateId, teamid, preonbId };
			//
			List<Integer> list = jdbcTemplate.query(sql, params, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getInt("id");
				}
			});
			//
			if (list != null && list.size() > 0 && ((Integer) list.get(0)).intValue() > 0)
				iscompleted = 1;
			//
			sql = " update tpreonboardings set pkgcompleted = ? where teamid = ? and candidateid = ? and id = ? ";
			//
			params = new Object[] { Integer.valueOf(iscompleted), teamid, candidateId, preonbId };
			//
		} catch (Exception e) {
			this.logger.info("updatePackageStatus [" + teamid + "/" + candidateId + "/" + preonbId + "] Error :: " + e.getMessage());
		}
		return (iscompleted == 1);
	}
}
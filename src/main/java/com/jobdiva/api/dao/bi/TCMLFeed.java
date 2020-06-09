package com.jobdiva.api.dao.bi;

import java.util.*;

public class TCMLFeed {
	
	public static String newHires(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select id placementid from tinterviewschedule where recruiter_teamid=? " + //
				" and placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and datehired is not null ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String updatedHires(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select id placementid from tinterviewschedule where recruiter_teamid=? " + //
				" and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and datehired is not null ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String EEO(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select c.id candidateid, c.firstname, c.lastname, j.rfqno_team jobreferenceno, j.rfqtitle jobtitle, j.dateissued jobissuedate, i.datepresented datesubmitted," + //
				"   (select d.name from tdivision d where d.id = j.divisionid) division, " + //
				"   (case when i.datehired is not null then 'Hired' when nvl(i.daterejected,i.extdaterejected) is not null then 'Rejected' else '' end) disposition, " + //
				"   i.datehired, nvl(i.daterejected, i.extdaterejected) daterejected, " + //
				"   (select r.reason from trejection_reasons r where r.teamid = i.recruiter_teamid and r.id = i.extrejectreasonid) rejectionreason, " + //
				"   (select name from tresumesourcename y where nvl(i.resumesource,-1) = y.id and y.teamid = i.recruiter_teamid) source, " + //
				"   x.gender, x.race, x.veteran, x.disability, x.general_veteran, x.lgbt_status, x.ethnicity, " + //
				"   (select s.name from tresumesourcename s where s.teamid=i.recruiter_teamid and s.id=i.resumesource) referral, " + //
				"   decode(j.contract,1,'Direct Placement',2,'Contract',3,'Right to Hire',4,'Full Time/Contract','') jobtype, " + //
				"   (select dateapplied from tcandidate_applyforjob p where p.candidateid=i.candidateid and p.rfqid=i.rfqid and p.teamid=i.recruiter_teamid and rownum=1) dateapplied " + //
				" from tinterviewschedule i, tcandidate c, trfq j, " + //
				"   (select * from (select candidateid, a.eeoid, b.name from tcandidate_eeo a, teeo_detail b " + //
				"    where a.teamid = ? and b.eeoid = a.eeoid and b.id = a.deeoid) " + //
				"      pivot (min(name) for eeoid in (1 as GENDER, 2 as RACE, 3 as VETERAN, 5 as DISABILITY, 6 as GENERAL_VETERAN, 9 as LGBT_STATUS, 10 as ETHNICITY))) x " + //
				" where i.recruiter_teamid = ? and i.rfqid in (select z.rfqid from tinterviewschedule z where z.recruiter_teamid = i.recruiter_teamid " + //
				"   and z.datehired between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) " + //
				"   and i.id = (select max(id) from tinterviewschedule int where int.recruiter_teamid=i.recruiter_teamid and int.candidateid=i.candidateid and int.rfqid=i.rfqid) " + //
				"   and c.id = i.candidateid and c.teamid = i.recruiter_teamid and j.id = i.rfqid and x.candidateid(+) = i.candidateid ";
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
}

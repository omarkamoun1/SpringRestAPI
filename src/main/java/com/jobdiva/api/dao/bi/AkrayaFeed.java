package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class AkrayaFeed {
	
	public static String getNoteCounts(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				"select b.firstname, b.lastname, inperson_meeting_count " + //
						"  from " + //
						"  (select recruiterid, count(*) inperson_meeting_count " + //
						"  from tcustomernotes " + //
						"  where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and nvl(deleted,0)=0 " + //
						"  group by recruiterid) a, trecruiter b " + //
						"where b.id=a.recruiterid " + //
						"order by b.firstname, b.lastname "; //
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getJobCountsByPrimarySales(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				"select b.firstname, b.lastname, job_count " + //
						"  from " + //
						"  (select b.recruiterid, count(*) job_count " + //
						"  from trfq a, trecruiterrfq b " + //
						"  where a.teamid=? and a.dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
						"    and b.rfqid=a.id and b.teamid=a.teamid and b.lead_sales=1 " + //
						"  group by b.recruiterid) a, trecruiter b " + //
						"where b.id=a.recruiterid " + //
						"order by b.firstname, b.lastname "; //
		param.add(clientID); //
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getSubmittalCountsByPrimarySales(Long clientID, String fromDate, String toDate, Vector<Object> param) { //
		String sql = //
				"select b.firstname, b.lastname, submittal_count " + //
						"  from " + //
						"  (select primarysalesid, count(distinct candidateid||'`'||rfqid) submittal_count " + //
						"  from tinterviewschedule " + //
						"  where recruiter_teamid=? and datepresented between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and nvl(roleid,0)<900 " + //
						"  group by primarysalesid) a, trecruiter b " + //
						"where b.id=a.primarysalesid " + //
						"order by b.firstname, b.lastname "; //
		param.add(clientID); //
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getKPI(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				" select b.firstname, b.lastname, b.email, nvl(a.inperson_meeting_count,0) inperson_meeting_count, " + //
						"   nvl(a.job_count,0) job_count, nvl(a.submittal_count,0) submittal_count " + //
						" from " + //
						"   (select nvl(u.recruiterid,w.primarysalesid) recruiterid, u.inperson_meeting_count, u.job_count, w.submittal_count " + //
						"   from " + //
						"     (select nvl(x.recruiterid, y.recruiterid) recruiterid, x.inperson_meeting_count, y.job_count " + //
						"     from " + //
						"       (select recruiterid, count(*) inperson_meeting_count " + //
						"       from tcustomernotes " + //
						"       where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and nvl(deleted,0)=0 " + //
						"       group by recruiterid) x " + //
						"     full join " + //
						"       (select q.recruiterid, count(*) job_count " + //
						"       from trfq p, trecruiterrfq q " + //
						"       where p.teamid=? and p.dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
						"         and q.rfqid=p.id and q.teamid=p.teamid and q.lead_sales=1 " + //
						"       group by q.recruiterid) y " + //
						"     on x.recruiterid=y.recruiterid) u " + //
						" full join " + //
						"   (select primarysalesid, count(distinct candidateid||'`'||rfqid) submittal_count " + //
						"   from tinterviewschedule " + //
						"   where recruiter_teamid=? and datepresented between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and nvl(roleid,0)<900 " + //
						"   group by primarysalesid) w " + //
						" on u.recruiterid=w.primarysalesid) a, trecruiter b " + //
						" where b.id=a.recruiterid " + //
						" order by b.firstname, b.lastname "; //
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
}

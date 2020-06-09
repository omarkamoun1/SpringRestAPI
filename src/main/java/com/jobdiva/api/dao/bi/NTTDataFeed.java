package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class NTTDataFeed {
	
	public static String moatFlipSubmittals(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				" select (select firstname||' '||lastname from trecruiter r where r.id=n.recruiterid) \"User\", " + //
						"  (select name from tdivision d, trecruiter r where r.id=n.recruiterid and d.id=r.division) \"Division\", sum(decode(type,17,1,0)) \"MOAT Notes\", " + //
						"  sum(decode(type,16,1,0)) \"FLIP Notes\",  " + //
						"  sum(case when n.type=16 and exists (select 1 from tinterviewschedule s where s.rfqid=n.rfqid and s.candidateid=n.candidateid and nvl(s.roleid,0) < 900 " + //
						"                           and s.datepresented > n.datecreated) then 1 else 0 end) \"Submittals\", " + //
						"  sum(case when n.type=16 and exists (select 1 from tinterviewschedule s where s.rfqid=n.rfqid and s.candidateid=n.candidateid " + //
						"                           and s.datepresented > n.datecreated and s.datehired is not null) then 1 else 0 end) \"Hires\" " + //
						" from tcandidatenotes n " + //
						" where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and TYPE in (16, 17) " + //
						" group by recruiterid "; //
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
}

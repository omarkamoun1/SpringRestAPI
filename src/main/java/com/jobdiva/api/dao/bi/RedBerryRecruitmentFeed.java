package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class RedBerryRecruitmentFeed {
	
	public static String getWeeksOnAssignment(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				"select (select name from trfq j, tcustomercompany c where j.id=b.rfqid and c.id=j.companyid) \"Company Name\", " + //
						"  (select c.firstname||' '||c.lastname from tcandidate c where c.id=b.employeeid and c.teamid=?) \"Candidate Full Name\", " + //
						"  sum(weeks) \"Number of Weeks\", " + //
						"  (select rfqtitle from trfq where id=b.rfqid) \"Job/Assignment Title\", " + //
						"  to_char(min(b.start_date),'dd/mm/yyyy') \"Assignment Start Date\", " + //
						"  to_char(max(b.end_date),'dd/mm/yyyy') \"Assignment End Date\", " + //
						"  (select r.firstname||' '||r.lastname from trecruiterrfq u, trecruiter r " + //
						"   where u.rfqid=b.rfqid and u.teamid=? and u.lead_recruiter=1 and r.id=u.recruiterid) \"Primary Recruiter\" " + //
						"from " + //
						"(select employeeid, billing_recid, count(*) weeks from temployee_wed where recruiter_teamid=? and approved=1 " + //
						"group by employeeid, billing_recid) a, temployee_billingrecord b " + //
						"where b.employeeid=a.employeeid and b.recruiter_teamid=? and b.recid=a.billing_recid and b.frequency=4 " + //
						"group by b.employeeid, b.rfqid " + //
						"order by \"Company Name\", \"Candidate Full Name\"  "; //
		param.add(clientID); //
		param.add(clientID);
		param.add(clientID);
		param.add(clientID);
		return sql;
	}
}

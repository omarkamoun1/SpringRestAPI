package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class GenuentFeed {
	
	public static String getOpenOnHoldJobsByDivision(Long clientID, String[] params, Vector<Object> param) {
		String sql = //
				"select rfqno_team jobdivano, dateissued, rfqtitle jobtitle, (select c.name from tcustomercompany c where c.id=companyid) jobcompany, " + //
						"  (select c.firstname||' '||c.lastname from tcustomer c where c.id=trfq.customerid) hiringmanager, " + //
						"  city||', '||state joblocation, decode(jobstatus,0,'Open',1,'On Hold') jobstatus, " + //
						"  (select r.firstname||' '||r.lastname from trecruiterrfq u, trecruiter r where u.rfqid=trfq.id and u.teamid=trfq.teamid and u.lead_recruiter=1 and r.id=u.recruiterid) primaryrecruiter, " + //
						"  (select r.firstname||' '||r.lastname from trecruiterrfq u, trecruiter r where u.rfqid=trfq.id and u.teamid=trfq.teamid and u.lead_sales=1 and r.id=u.recruiterid) primarysales, " + //
						"  positions noofopenings, " + //
						"  (select count(distinct candidateid) from tinterviewschedule i where i.rfqid=trfq.id and nvl(roleid,0) < 900) externalsubmittals, " + //
						"  (select count(distinct candidateid) from tinterviewschedule i where i.rfqid=trfq.id and dateinterview is not null) interviews, " + //
						"  (select count(*) from tinterviewschedule i where i.rfqid=trfq.id and dateextrejected is not null) rejections, " + //
						"  (select count(*) from tinterviewschedule i where i.rfqid=trfq.id and datehired is not null) hires " + //
						"from trfq where teamid=? and (jobstatus = 0 or jobstatus = 1) and divisionid = ? "; //
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
}

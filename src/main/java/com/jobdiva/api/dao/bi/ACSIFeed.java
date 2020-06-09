package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class ACSIFeed {
	
	public static String getActivities(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				" select a.id activityid, a.recruiterid resourceid, a.datecreated, a.dateupdated, " + //
						"   datepresented datesubmitted, dateinterview dateinterviewed, datehired, " + //
						"   case when dateinterview is not null then interviewscheduledate else null end interviewscheduledate, " + //
						"   case when datehired is not null then placementdate else null end placementdate, " + //
						"   b.companyid requisitionclientid, c.name clientname, " + //
						"   a.rfqid requisitionid, b.dateissued reqcreatetime, " + //
						"   case when jobstatus=4 " + //
						"        then (select max(l.updateddate) from trfq_statuslog l where l.rfqid=a.rfqid and l.cur_status=4) " + //
						"        else null end reqclosedate, " + //
						"   (select name from tdivision d where d.id=b.divisionid) reqlocation, " + //
						"   decode(jobpriority,1,'A',2,'B',3,'C',4,'D','') reqrank, " + //
						"   b.positions reqoriginalpositions, b.positions-b.fills reqopenpositions, " + //
						"   decode(jobstatus, 0, 'Open', 1, 'On Hold', 2, 'Filled', 3, 'Cancelled', 4, 'Closed', 5, 'Expired', 6, 'Ignored', '') reqstatus, " + //
						"   (select r.recruiterid from trecruiterrfq r where r.rfqid=a.rfqid and r.lead_recruiter=1) reqprimaryrecruiter, " + //
						"   (select r.recruiterid from trecruiterrfq r where r.rfqid=a.rfqid and r.lead_sales=1) reqprimaryaccountexec " + //
						" from tinterviewschedule a, trfq b, tcustomercompany c " + //
						" where recruiter_teamid=? " + //
						"   and (a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
						"    or a.dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) " + //
						"   and b.id = a.rfqid and c.id = b.companyid "; //
		param.add(clientID); //
		param.add(fromDate); //
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
}

package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class Sage50Feed {
	
	public static String sage50RedberryNewEmployeeFeed(Long clientID, String fromDate, String toDate, String params[], Vector<Object> param) {
		String sql = " select c.firstname, c.lastname, to_char(a.start_date,'dd/mm/yyyy') startdate, " + //
				"   c.address1, c.address2, c.city, " + //
				"   case when c.countryid = 'UK' then (select state_name from tstates s where s.countryid='UK' and s.state_abbr=c.state) else c.state end state, c.zipcode, " + //
				"   c.homephone, c.cellphone, " + //
				"   c.email, to_char(hr.dateofbirth,'dd/mm/yyyy') dateofbirth, hr.ssn ninumber " + //
				" from temployee_billingrecord a, temployee_salaryrecord b, tcandidate c, tcandidate_hr hr " + //
				" where a.recruiter_teamid=? and a.approved=1 and nvl(a.closed,0)=0 " + //
				"   and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"   and b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.interviewid=a.interviewid and b.approved=1 and nvl(b.closed,0)=0 " + //
				"   and b.effective_date = (select max(s.effective_date) from temployee_salaryrecord s " + //
				"     where s.employeeid=b.employeeid and s.recruiter_teamid=b.recruiter_teamid and s.interviewid=b.interviewid and s.approved=1 and nvl(s.closed,0)=0) " + //
				"   and c.id=a.employeeid and c.teamid=a.recruiter_teamid " + //
				"   and hr.candidateid(+)=a.employeeid and hr.teamid(+)=a.recruiter_teamid "; //
		param.add(clientID); //
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String sage50RedberryNewTimesheetFeed(Long clientID, String fromDate, String toDate, String params[], Vector<Object> param) {
		String sql = "select cand.firstname, cand.lastname, cand.email, hr.ssn ninumber, mt.hourtype, mt.hoursworked, mt.rate " + //
				" from ( " + //
				" select a.employeeid, a.recruiter_teamid, b.name hourtype, a.hoursworked, " + //
				"   (select su.userfield_value from temployee_billingrecord x, tuserfields u, tstartrecord_userfields su " + //
				"   where x.employeeid=a.employeeid and x.recruiter_teamid=a.recruiter_teamid and x.recid=a.billing_recid " + //
				"     and u.teamid=a.recruiter_teamid and u.fieldname=b.name " + //
				"     and su.startid=x.interviewid and su.teamid=a.recruiter_teamid and su.userfield_id=u.id) rate " + //
				" from " + //
				" (select employeeid, recruiter_teamid, billing_recid, projectid, sum(hoursworked) hoursworked " + //
				" from temployee_timesheet t " + //
				" where recruiter_teamid=? and weekending between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"   and exists (select 1 from temployee_wed w where w.employeeid=t.employeeid and w.recruiter_teamid=t.recruiter_teamid " + //
				"             and w.billing_recid=t.billing_recid and w.weekendingdate=t.weekending and w.approved=1) " + //
				"   and projectid < 0 " + //
				" group by employeeid, recruiter_teamid, billing_recid, projectid) a, tbilling_hourtypes b " + //
				" where b.recruiter_teamid=a.recruiter_teamid and b.id=a.projectid " + //
				" union all " + //
				" select a.employeeid, a.recruiter_teamid, N'Basic' hourtype, a.hoursworked, to_char(b.salary) rate " + //
				" from " + //
				" (select employeeid, recruiter_teamid, billing_recid, projectid, sum(reghours) hoursworked " + //
				" from temployee_timesheet t " + //
				" where recruiter_teamid=? and weekending between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"   and exists (select 1 from temployee_wed w where w.employeeid=t.employeeid and w.recruiter_teamid=t.recruiter_teamid " + //
				"             and w.billing_recid=t.billing_recid and w.weekendingdate=t.weekending and w.approved=1) " + //
				"   and projectid = 0 " + //
				" group by employeeid, recruiter_teamid, billing_recid, projectid) a, temployee_salaryrecord b" + //
				" where b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.recid=a.billing_recid " + //
				" union all " + //
				" select a.employeeid, a.recruiter_teamid, N'Overtime' hourtype, a.hoursworked, to_char(b.overtime_rate1) rate " + //
				" from " + //
				" (select employeeid, recruiter_teamid, billing_recid, projectid, sum(othours) hoursworked " + //
				" from temployee_timesheet t " + //
				" where recruiter_teamid=? and weekending between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"   and exists (select 1 from temployee_wed w where w.employeeid=t.employeeid and w.recruiter_teamid=t.recruiter_teamid " + //
				"             and w.billing_recid=t.billing_recid and w.weekendingdate=t.weekending and w.approved=1) " + //
				"   and projectid = 0 " + //
				" group by employeeid, recruiter_teamid, billing_recid, projectid) a, temployee_salaryrecord b " + //
				" where b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.recid=a.billing_recid " + //
				" union all " + //
				" select a.employeeid, a.recruiter_teamid, N'Doubletime' hourtype, a.hoursworked, to_char(b.overtime_rate2) rate " + //
				" from " + //
				" (select employeeid, recruiter_teamid, billing_recid, projectid, sum(dthours) hoursworked " + //
				" from temployee_timesheet t " + //
				" where recruiter_teamid=? and weekending between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"   and exists (select 1 from temployee_wed w where w.employeeid=t.employeeid and w.recruiter_teamid=t.recruiter_teamid " + //
				"             and w.billing_recid=t.billing_recid and w.weekendingdate=t.weekending and w.approved=1) " + //
				"   and projectid = 0 " + //
				" group by employeeid, recruiter_teamid, billing_recid, projectid) a, temployee_salaryrecord b " + //
				" where b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.recid=a.billing_recid " + //
				") mt, tcandidate cand, tcandidate_hr hr " + //
				" where hoursworked > 0 " + //
				"   and cand.id=mt.employeeid and cand.teamid=mt.recruiter_teamid " + //
				"   and hr.candidateid(+)=mt.employeeid and hr.teamid(+)=mt.recruiter_teamid" + //
				" order by cand.firstname, cand.lastname, cand.email, hr.ssn "; //
		param.add(clientID); //
		param.add(fromDate);
		param.add(toDate);
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

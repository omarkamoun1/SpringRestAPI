package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class EDIFeed {
	
	public static String getTimesheetsByStatus(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				"SELECT DISTINCT (g.LASTNAME||', '||g.FIRSTNAME) \"Employee\", (select f.name from tcustomercompany f where cust.companyid = f.id) \"Company\", " + //
						"c.JOBDIVA_REFNO \"JobDiva Job #\", (select rfqtitle from trfq r where r.id=c.rfqid) \"Job Title\", k.name \"Assignment Division\", " + //
						"g.EMAIL \"Email\",  " + //
						"(case when g.WORKPHONE_EXT IS null THEN g.WORKPHONE ELSE (CASE WHEN g.WORKPHONE IS null THEN null ELSE g.WORKPHONE || g.WORKPHONE_EXT END) END) \"Work Phone\", " + //
						"c.working_state \"Working State\", d.reg_hours \"Regular Default Hours\", d.ot_hours \"Overtime Hours\", d.dt_hours \"Doubletime Hours\", " + //
						"(d.reg_hours+d.ot_hours+d.dt_hours) \"Total Default Hours\", TO_CHAR(d.weekendingdate, 'MM/dd/yyyy') \"Week Ending\",  " + //
						"(select t4.FIRSTNAME || ' ' || t4.LASTNAME from trecruiter t4 where t4.id=c.PRIMARY_SALESPERSON ) as \"Primary Sales\", " + //
						"(select t5.FIRSTNAME || ' ' || t5.LASTNAME from trecruiter t5 where t5.id=c.SECONDARY_SALESPERSON ) as \"Secondary Sales\", " + //
						"(select t6.FIRSTNAME || ' ' || t6.LASTNAME from trecruiter t6 where t6.id=c.TERTIARY_SALESPERSON ) as \"Tertiary Sales\", " + //
						"(select t7.FIRSTNAME || ' ' || t7.LASTNAME from trecruiter t7 where t7.id=c.PRIMARY_RECRUITER ) as \"Primary Recruiter\", " + //
						"(select t8.FIRSTNAME || ' ' || t8.LASTNAME from trecruiter t8 where t8.id=c.SECONDARY_RECRUITER ) as \"Secondary Recruiter\", " + //
						"(select t9.FIRSTNAME || ' ' || t9.LASTNAME from trecruiter t9 where t9.id=c.TERTIARY_RECRUITER ) as \"Tertiary Recruiter\", " + //
						"(case when d.approved=1 and d.approved_by > 0 then h.firstname||' '||h.lastname else j.firstname||' '||j.lastname end) \"Approver\", " + //
						"(case when d.approved=1 and d.approved_by > 0 then h.email else j.email end) \"Approver Email\", " + //
						"(case when d.approved=1 and d.approved_by > 0 then (h.workphone || (case when h.workphoneext is not null then to_char(' Ext. '||h.workphoneext)  else '' end)) " + //
						"    else (j.workphone || (case when j.workphoneext is not null then to_char(' Ext. '||j.workphoneext)  else '' end)) end) \"Approver Phone\", " + //
						"(case when d.approved=1 then TO_CHAR(d.approved_on, 'MM/dd/yyyy') else null end) \"Approved On\",  " + //
						"(select tpo.po# from tpo_setting tpo where tpo.teamid = d.recruiter_teamid and tpo.id = c.poid) \"PO Number\", c.bill_rate \"Regular Bill Rate\",  " + //
						"s.salary \"Regular Pay Rate\", c.overtime_rate1 \"Overtime Bill Rate\", s.overtime_rate1 \"Overtime Pay Rate\", c.overtime_rate2 \"Doubletime Bill Rate\",  " + //
						"s.overtime_rate2 \"Doubletime Pay Rate\", c.bill_rate_per \"Bill Rate Per\", salary_per \"Pay Rate Per\", et.invoiceid \"Invoice ID\", ei.invoiceId_client \"Client Invoice ID\", " + //
						"(case when c.frequency=1 then 'Bi-Weekly' when c.frequency=2 then 'Monthly' when c.frequency=3 then 'Semi Monthly' when c.frequency=4 then 'Weekly' " + //
						"    when c.frequency=5 then 'Whole Project' when c.frequency=6 then 'Monthly Ending Weekend' else 'Unknown' end) \"Frequency\", " + //
						"d.reg_hours+d.ot_hours+d.dt_hours+(select nvl(sum(st1.hoursworked),0) UDFVALUE  " + //
						"from TEMPLOYEE_TIMESHEET st1 JOIN TBILLING_HOURTYPES ts2 ON st1.RECRUITER_TEAMID = ts2.RECRUITER_TEAMID and st1.projectid=ts2.ID " + //
						"JOIN TEMPLOYEE_BILLINGRECORD ts0 ON st1.employeeid = ts0.EMPLOYEEID AND st1.RECRUITER_TEAMID=ts0.RECRUITER_TEAMID and st1.BILLING_RECID=ts0.RECID " + //
						"where st1.employeeid=c.employeeid and st1.RECRUITER_TEAMID=c.recruiter_teamid and st1.weekending=d.weekendingdate and ts0.RFQID=c.rfqid) \"Total Reported Hours\", " + //
						"(select sum(nvl(ein.amount,0)) from temployee_invoice ein where ein.employeeid=d.employeeid and ein.WEEKENDING=d.weekendingdate  " + //
						"and ein.BILLINGID=d.BILLING_RECID and ein.approved=1 and ein.INVOICETYPE=4 group by ein.employeeid,ein.WEEKENDING) \"Total Expense\", " + //
						"(select nvl(sum(st1.hoursworked),0) from TEMPLOYEE_TIMESHEET st1, TBILLING_HOURTYPES ts2, TEMPLOYEE_BILLINGRECORD ts0  " + //
						"    where st1.RECRUITER_TEAMID = ts2.RECRUITER_TEAMID and st1.projectid=ts2.ID and ts2.ID = '-4' and st1.employeeid=c.employeeid  " + //
						"    and st1.RECRUITER_TEAMID=c.recruiter_teamid and st1.weekending=d.weekendingdate and st1.employeeid = ts0.EMPLOYEEID  " + //
						"    and st1.RECRUITER_TEAMID=ts0.RECRUITER_TEAMID and st1.BILLING_RECID=ts0.RECID and ts0.RFQID=c.rfqid) \"Holiday\", " + //
						"(select nvl(sum(st1.hoursworked),0)  " + //
						"    from TEMPLOYEE_TIMESHEET st1 JOIN TBILLING_HOURTYPES ts2 ON st1.RECRUITER_TEAMID = ts2.RECRUITER_TEAMID and st1.projectid=ts2.ID " + //
						"    JOIN TEMPLOYEE_BILLINGRECORD ts0 ON st1.employeeid = ts0.EMPLOYEEID and st1.RECRUITER_TEAMID=ts0.RECRUITER_TEAMID and st1.BILLING_RECID=ts0.RECID  " + //
						"    WHERE st1.employeeid=c.employeeid and st1.RECRUITER_TEAMID=c.recruiter_teamid and st1.weekending=d.weekendingdate  " + //
						"    AND ts2.ID = '-5' and ts0.RFQID=c.rfqid) \"PTO\",  " + //
						"(SELECT USERFIELD_VALUE FROM TSTARTRECORD_USERFIELDS u WHERE u.TEAMID = ? AND u.startid=c.interviewid and u.USERFIELD_ID = 68) \"Branch\", " + //
						"(SELECT USERFIELD_VALUE FROM TSTARTRECORD_USERFIELDS u WHERE u.TEAMID = ? AND u.startid=c.interviewid and u.USERFIELD_ID = 65) \"Employee Type\", " + //
						"(SELECT USERFIELD_VALUE FROM TSTARTRECORD_USERFIELDS u WHERE u.TEAMID = ? AND u.startid=c.interviewid and u.USERFIELD_ID = 64) \"Epicor Customer ID\", " + //
						"(SELECT USERFIELD_VALUE FROM TSTARTRECORD_USERFIELDS u WHERE u.TEAMID = ? AND u.startid=c.interviewid and u.USERFIELD_ID = 66) \"Vendor Code\" " + //
						"FROM TEMPLOYEE_WED d JOIN TEMPLOYEE_BILLINGRECORD c ON  d.employeeid = c.employeeid and d.recruiter_teamid = c.recruiter_teamid and d.billing_recid = c.recid " + //
						"LEFT JOIN temployee_salaryrecord s ON d.employeeid = s.employeeid AND d.salary_recid = s.recid " + //
						"JOIN tcandidate g ON c.employeeid = g.id " + //
						"LEFT JOIN tbilling_approver p ON d.employeeid=p.employeeid and d.recruiter_teamid=p.teamid and d.billing_recid=p.billingid " + //
						"LEFT JOIN trecruiter h ON d.approved_by = h.id " + //
						"LEFT JOIN trecruiter j ON  p.approverid=j.id " + //
						"LEFT JOIN tdivision k ON c.division=k.id " + //
						"LEFT JOIN temployee_timesheet et ON d.employeeid = et.employeeid and d.recruiter_teamid = et.recruiter_teamid and d.salary_recid = et.salary_recid " + //
						"    AND d.billing_recid = et.billing_recid AND d.weekendingdate = et.weekending " + //
						"LEFT JOIN temployee_invoice ei ON et.invoiceid=ei.invoiceid and et.recruiter_teamid = ei.recruiter_teamid " + //
						"JOIN tcustomer cust ON c.billing_contact = cust.id " + //
						"WHERE d.recruiter_teamid = ? and cust.teamid = ? and g.teamid = ? and k.teamid = ? " + //
						"AND d.approved=1 AND c.approved=1 and s.recruiter_teamid=? and p.type = 1 AND nvl(et.invoiceid,0)>=0 " + //
						"and d.approved_on between TO_DATE(?,'MM/DD/YYYY hh24:mi:ss') and (TO_DATE(?, 'MM/DD/YYYY hh24:mi:ss')+1) " + //
						"and nvl(c.closed,0)=0 and ((c.bill_rate_per <>'M' and  c.bill_rate_per <>'W' and  c.bill_rate_per <>'B') or nvl(c.enable_timesheet, 0) = 1) " + //
						"order by (g.LASTNAME||', '||g.FIRSTNAME)"; //
		//
		param.add(clientID);
		param.add(clientID);
		param.add(clientID);
		param.add(clientID);
		param.add(clientID);
		param.add(clientID);
		param.add(clientID);
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
}

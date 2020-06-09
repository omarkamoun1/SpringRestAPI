package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class DiversantFeed {
	
	public static String activeAssignments(Long clientID, String[] params, Vector<Object> param) {
		String sql = //
				"select " + //
						"  (select min(start_date) from temployee_billingrecord x where x.employeeid=a.employeeid and x.recruiter_teamid=a.recruiter_teamid and x.interviewid=a.interviewid " + //
						"  and x.approved=1 and (x.closed is null or x.closed = 0)) oldest_bill_start, " + //
						"  (select max(end_date) from temployee_billingrecord x where x.employeeid=a.employeeid and x.recruiter_teamid=a.recruiter_teamid and x.interviewid=a.interviewid " + //
						"  and x.approved=1 and (x.closed is null or x.closed = 0)) newest_bill_end, " + //
						"  (select min(effective_date) from temployee_salaryrecord x where x.employeeid=b.employeeid and x.recruiter_teamid=b.recruiter_teamid and x.interviewid=b.interviewid " + //
						"  and x.approved=1 and (x.closed is null or x.closed = 0)) oldest_pay_start, " + //
						"  (select max(end_date) from temployee_billingrecord x where x.employeeid=a.employeeid and x.recruiter_teamid=a.recruiter_teamid and x.interviewid=a.interviewid " + //
						"  and x.approved=1 and (x.closed is null or x.closed = 0)) newest_pay_end, " + //
						"  a.jobdiva_refno, (select c.firstname||' '||c.lastname from tcandidate c where c.id=a.employeeid and c.teamid=a.recruiter_teamid) employee_name, " + //
						"  (select d.name from tdivision d where d.id=a.division) division, " + //
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=a.primary_salesperson) primary_sales_billing, " + //
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=a.secondary_salesperson) secondary_sales_billing, " + //
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=a.tertiary_salesperson) tertiary_sales_billing, " + //
						"  i.datehired start_date_start, " + //
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=i.recruiterid) recruited_by, " + //
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=i.primarysalesid) primary_sales_start, " + //
						"  i.date_ended end_date_start, " + //
						"  (select c.firstname||' '||c.lastname from tcustomer c where c.id=a.billing_contact and c.teamid=a.recruiter_teamid) contact, " + //
						"  (select cp.name from tcustomer c, tcustomercompany cp where c.id=a.billing_contact and c.teamid=a.recruiter_teamid and cp.id=c.companyid) company " + //
						"from temployee_billingrecord a, temployee_salaryrecord b, tinterviewschedule i " + //
						"where a.recruiter_teamid=? and a.approved=1 and (a.closed is null or a.closed = 0) and (a.end_date is null or a.end_date > sysdate) " + //
						"and b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.interviewid=a.interviewid and b.approved=1 and (b.closed is null or b.closed = 0) and (b.end_date is null or b.end_date > sysdate) " + //
						"and i.id=a.interviewid and i.recruiter_teamid = a.recruiter_teamid";
		param.add(clientID);
		return sql;
	}
}

package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class AditiFeed {
	
	public static String getCurrentPlacementActivities(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select " + "	to_number(nvl((select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=139),0))- " + //
				"	to_number(nvl((select s.salary from temployee_salaryrecord s where s.employeeid=a.employeeid and s.recruiter_teamid=a.recruiter_teamid and s.interviewid=a.interviewid and s.approved=1 " + //
				"     and nvl(s.closed,0)=0 and s.effective_date < sysdate and (s.end_date > sysdate or s.end_date is null) and rownum=1),0)) \"SPREAD\", " + //
				"	to_char(a.datecreated,'mm/dd/yyyy') \"DATE CREATED\", to_char(a.start_date, 'mm/dd/yyyy') \"BILLING START DATE\", to_char(a.end_date, 'mm/dd/yyyy') \"BILLING END DATE\", " + //
				"	(select c.firstname||' '||c.lastname from tcandidate c where c.id=a.employeeid and c.teamid=a.recruiter_teamid) \"EMPLOYEE NAME\", " + //
				"	a.rfqid \"JOBDIVA JOB NO\", " + //
				"	(select c.firstname||' '||c.lastname from tcustomer c where c.id=a.billing_contact) \"CLIENT CONTACT\", " + //
				"	a.vms_website \"VMS EMPLOYER NAME\", " + //
				"	(select r.firstname||' '||r.lastname from trecruiter r where r.id=a.primary_salesperson) \"PRIMARY SALES\", " + //
				"	(select r.firstname||' '||r.lastname from trecruiter r where r.id=a.primary_recruiter) \"PRIMARY RECRUITER\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=166) \"ACCOUNT MANAGER\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=112) \"BENEFITS\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=180) \"BILL RATE AFTER VMS FEES\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=139) \"BILL RATE BEFORE VMS FEES\", " + //
				"	(select to_char(to_date('01-jan-1970')+numtodsinterval(to_number(u.userfield_value)/1000,'second'),'mm/dd/yyyy') from tstartrecord_userfields u " + //
				"    where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=197) \"DATE OF BIRTH\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=111) \"ER NAME\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=131) \"EMPLOYEE TYPE\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=1) \"EXEMPT STATUS\",  " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=207) \"GROSS MARGIN\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=136) \"H1B EXPIRATION DATE\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=174) \"IF PLACEMENT STATUS ON LEAVE R\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=135) \"IS CANDIDATE ON A VISA\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=198) \"IS HE ADITI STAFFING H1B\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=100) \"LAPTOPSOFTWARE\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=93) \"MSP\", " + //
				"	(select to_char(to_date('01-jan-1970')+numtodsinterval(to_number(u.userfield_value)/1000,'second'),'mm/dd/yyyy') from tstartrecord_userfields u " + //
				"    where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=97) \"OFFER DATE\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=140) \"PAID HOLIDAYS\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=144) \"PAID SICK LEAVE OFFER LETTER\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=168) \"PAID TIME OFF OFFER LETTER\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=176) \"PAID TIME OFF MS FB\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=141) \"PAID VACATION\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=189) \"PER DIEM\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=130) \"PLACEMENT STATUS\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=221) \"REGION\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=102) \"REHIRE\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=195) \"SSN\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=175) \"SICK TIME\", " + //
				"	(select u.userfield_value from tcandidate_userfields u where u.teamid=a.recruiter_teamid and u.candidateid=a.employeeid and u.userfield_id=176) \"SOURCE\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=92) \"VMS\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=242) \"VISA APPROVED\", " + //
				"	(select to_char(to_date('01-jan-1970')+numtodsinterval(to_number(u.userfield_value)/1000,'second'),'mm/dd/yyyy') from tstartrecord_userfields u " + //
				"    where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=240) \"VISA EXPIRATION DATE\", " + //
				"	(select to_char(to_date('01-jan-1970')+numtodsinterval(to_number(u.userfield_value)/1000,'second'),'mm/dd/yyyy') from tstartrecord_userfields u " + //
				"    where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=239) \"VISA START DATE\", " + //
				"	(select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=177) \"WORK FROM HOME\", " + //
				"	(select s.salary from temployee_salaryrecord s where s.employeeid=a.employeeid and s.recruiter_teamid=a.recruiter_teamid and s.interviewid=a.interviewid and s.approved=1 " + //
				"    and nvl(s.closed,0)=0 and s.effective_date < sysdate and (s.end_date > sysdate or s.end_date is null) and rownum=1) \"AGREED PAY RATE\", " + //
				"	j.rfqtitle \"JOB TITLE\", j.rfqrefno \"OPTINAL REF NO\", j.city \"CITY\", j.state \"STATE\", j.zipcode \"ZIP CODE\", " + //
				"	(select cc.name from tcustomer c, tcustomercompany cc where c.id=a.billing_contact and cc.id=c.companyid) \"COMPANY NAME\" " + //
				"from temployee_billingrecord a, trfq j " + //
				"where a.recruiter_teamid=? and a.start_date < sysdate and (a.end_date > sysdate or a.end_date is null) and a.approved=1 and nvl(a.closed,0)=0 and j.id=a.rfqid"; //
		param.add(clientID);
		return sql;
	}
	
	public static String getCurrentHeadCount(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String sql = "select " + "  (select cc.name from tcustomercompany cc where cc.id=b.companyid) \"COMPANY NAME\", " + "  (select r.firstname||' '||r.lastname from trecruiter r where r.id=a.primary_salesperson) \"PRIMARY SALES\", " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=130) \"PLACEMENT STATUS\", " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=116) \"EMP ID\", " + //
				"  (select s.salary from temployee_salaryrecord s where s.employeeid=a.employeeid and s.recruiter_teamid=a.recruiter_teamid and s.interviewid=a.interviewid and s.approved=1 and nvl(s.closed,0)=0 " + //
				"	  and s.effective_date < sysdate and (s.end_date > sysdate or s.end_date is null) and rownum = 1) \"AGREED PAY RATE\", " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=180) \"BILL RATE AFTER VMS FEES\", " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=139) \"BILL RATE BEFORE VMS FEES\", " + //
				"     to_number(nvl((select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=139),0))- " + //
				"	  to_number(nvl((select s.salary from temployee_salaryrecord s where s.employeeid=a.employeeid and s.recruiter_teamid=a.recruiter_teamid and s.interviewid=a.interviewid and s.approved=1 and nvl(s.closed,0)=0 " + //
				"	  and s.effective_date < sysdate and (s.end_date > sysdate or s.end_date is null) and rownum = 1),0)) \"SPREAD\", " + //
				"  to_char(a.start_date, 'mm/dd/yyyy') \"START DATE\", to_char(a.end_date, 'mm/dd/yyyy') \"END DATE\", " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=57) \"CANDIDATE LEGAL NAME\", " + //
				"  c.firstname \"FIRST NAME\", c.lastname \"LAST NAME\", c.email \"EMAIL\", c.sysemail \"ALTERNAME EMAIL\", " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=93) \"MSP\", " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.teamid=a.recruiter_teamid and u.startid=a.interviewid and u.userfield_id=92) \"VMS\", " + //
				"  b.firstname \"HIRING MANAGER FIRST NAME\", b.lastname \"HIRING MANAGER LAST NAME\", b.title \"HIRING MANAGER TITLE\", b.email \"HIRING MANAGER EMAIL\", b.alternate_email \"HIRING MANAGER ALTERNATE EMAIL\" " + //
				"from temployee_billingrecord a, tcustomer b, tcandidate c, trfq j " + //
				"where a.recruiter_teamid=? and a.start_date < sysdate and (a.end_date > sysdate or a.end_date is null) and a.approved=1 and nvl(a.closed,0)=0 "
				+ "  and b.id=a.billing_contact and c.id=a.employeeid and c.teamid=a.recruiter_teamid and j.id=a.rfqid ";
		param.add(clientID);
		if (params != null && params.length > 0) {
			if (params[0] != null && !params[0].equals("0")) {
				sql += "  and b.companyid = ? ";
				param.add(new Long(params[0]));
			}
			if (params.length > 1) {
				sql += " and a.primary_salesperson in (";
				for (int i = 1; i < params.length; i++) {
					sql += (i > 1 ? ",?" : "?");
					param.add(new Long(params[i]));
				}
				sql += ")";
			}
		}
		return sql;
	}
}

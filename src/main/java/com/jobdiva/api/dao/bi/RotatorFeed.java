package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class RotatorFeed {
	
	public static String getActivities(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql =
				// " select recruiterid id, b.firstname || ' ' || b.lastname
				// recruitername, value " +
				// " from " +
				// " (select recruiterid, sum(value) value " +
				// " from " +
				// " ((select a.recruiterid, count(distinct
				// (a.rfqid||'~'||a.candidateid)) value " +
				// " from tinterviewschedule a, trfq b where
				// a.recruiter_teamid=? and a.datecreated between
				// to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy
				// hh24:mi:ss') " +
				// " and b.id=a.rfqid and nvl(b.contract,0) <> 5 " +
				// " group by a.recruiterid) " +
				// " union all " +
				// " (select a.recruiterid, count(distinct
				// (a.rfqid||'~'||a.candidateid)) value " +
				// " from tinterviewschedule a, trfq b where
				// a.recruiter_teamid=? and (a.datecreated between
				// to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy
				// hh24:mi:ss') " +
				// " or a.dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss')
				// and to_date(?,'mm/dd/yyyy hh24:mi:ss')) " +
				// " and a.dateinterview is not null and b.id=a.rfqid and
				// nvl(b.contract,0) <> 5 " +
				// " group by a.recruiterid))" +
				// " group by recruiterid) a, trecruiter b " +
				// " where a.recruiterid = b.id " +
				// " order by recruitername ";
				//
				" select rec.recruiterid, rec.recruitername, internal_submittal, external_submittal, interview " + //
						" from " + //
						" (select id recruiterid, firstname||' '||lastname recruitername from trecruiter where groupid=?) rec, " + //
						" (select a.recruiterid, count(*) internal_submittal " + //
						" from tinterviewschedule a, trfq b where a.recruiter_teamid=? and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
						"   and a.roleid > 900 and a.id = (select min(i.id) from tinterviewschedule i " + //
						"     where i.recruiter_teamid=a.recruiter_teamid and i.candidateid=a.candidateid and i.rfqid=a.rfqid and i.roleid > 900) and b.id=a.rfqid and nvl(b.contract,0) <> 5 " + //
						" group by a.recruiterid) intsub, " + //
						" (select a.recruiterid, count(*) external_submittal " + //
						" from tinterviewschedule a, trfq b where a.recruiter_teamid=? and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
						"   and nvl(a.roleid,0) < 900 and a.id = (select min(i.id) from tinterviewschedule i " + //
						"     where i.recruiter_teamid=a.recruiter_teamid and i.candidateid=a.candidateid and i.rfqid=a.rfqid and nvl(i.roleid,0) < 900) and b.id=a.rfqid and nvl(b.contract,0) <> 5 " + //
						" group by a.recruiterid) extsub, " + //
						" (select a.recruiterid, count(*) interview " + //
						" from tinterviewschedule a, trfq b where a.recruiter_teamid=? " + //
						"   and a.dateinterview between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and b.id=a.rfqid and nvl(b.contract,0) <> 5 " + //
						" group by a.recruiterid) int " + //
						" where rec.recruiterid = intsub.recruiterid(+) and rec.recruiterid = extsub.recruiterid(+) and rec.recruiterid = int.recruiterid(+) " + //
						"   and nvl(internal_submittal,0)+nvl(external_submittal,0)+nvl(interview,0) > 0 " + //
						" order by recruitername ";
		param.add(clientID);
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
	
	public static String getInternalSubmittals(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.recruiterid id, (select firstname||' '||lastname from trecruiter r where r.id=a.recruiterid) recruitername, count(*) value " + //
				" from tinterviewschedule a, trfq b where a.recruiter_teamid=? and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"   and a.roleid > 900 and a.id = (select min(i.id) from tinterviewschedule i " + //
				"     where i.recruiter_teamid=a.recruiter_teamid and i.candidateid=a.candidateid and i.rfqid=a.rfqid and i.roleid > 900) and b.id=a.rfqid and nvl(b.contract,0) <> 5 " + //
				" group by a.recruiterid" + //
				" order by recruitername "; //
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getExternalSubmittals(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.recruiterid id, (select firstname||' '||lastname from trecruiter r where r.id=a.recruiterid) recruitername, count(*) value " + //
				" from tinterviewschedule a, trfq b where a.recruiter_teamid=? and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"   and nvl(a.roleid,0) < 900 and a.id = (select min(i.id) from tinterviewschedule i " + //
				"     where i.recruiter_teamid=a.recruiter_teamid and i.candidateid=a.candidateid and i.rfqid=a.rfqid and nvl(i.roleid,0) < 900) and b.id=a.rfqid and nvl(b.contract,0) <> 5 " + //
				" group by a.recruiterid " + //
				" order by recruitername "; //
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getInterviews(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.recruiterid id, (select firstname||' '||lastname from trecruiter r where r.id=a.recruiterid) recruitername, count(*) value " + //
				" from tinterviewschedule a, trfq b where a.recruiter_teamid=? " + //
				"   and a.dateinterview between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and b.id=a.rfqid and nvl(b.contract,0) <> 5 " + //
				" group by a.recruiterid " + //
				" order by recruitername "; //
		param.add(clientID); //
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getHires(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.recruiterid id, (select firstname||' '||lastname from trecruiter r where r.id=a.recruiterid) recruitername, count(*) value " + //
				" from tinterviewschedule a, trfq b" + //
				" where a.recruiter_teamid=? and a.datehired between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"   and b.id=a.rfqid and nvl(b.contract,0) <> 5 " + //
				" group by a.recruiterid " + //
				" order by recruitername ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getActiveAssignments(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select c.firstname||' '||c.lastname employeename, " + //
				"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=64) employeeid, " + //
				"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=62) employeeclasskey, " + //
				"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=63) department, " + //
				"   c.email employeeemail, d.companyname, (select rfqtitle from trfq where id=a.rfqid) jobtitle, (select r.firstname||' '||r.lastname from trecruiter r where r.id = a.primary_recruiter) primaryrecuiter, " + //
				"   (select r.firstname||' '||r.lastname from trecruiter r where r.id = i.recruiterid) precruiter, (select r.firstname||' '||r.lastname from trecruiter r where r.id = a.primary_salesperson) primarysales, " + //
				"   i.datehired startdate, a.start_date billingstartdate, (select name from tdivision div where div.id=a.division) division, b.salary, b.overtime_rate1 overtimepayrate, " + //
				"   a.bill_rate - nvl((select sum(decode(d.discount_unit,'h',discount,'%',discount/100*a.bill_rate)) from temployee_billingdiscount d " + //
				"                  where d.employeeid=a.employeeid and d.teamid=a.recruiter_teamid and d.recid=a.recid and d.discount > 0),0) netbill, " + //
				"   a.overtime_rate1 - nvl((select sum(decode(d.discount_unit,'h',discount,'%',discount/100*a.overtime_rate1)) from temployee_billingdiscount d " + //
				"                  where d.employeeid=a.employeeid and d.teamid=a.recruiter_teamid and d.recid=a.recid and d.discount > 0),0) netovertime, " + //
				"   a.overtime_rate2 - nvl((select sum(decode(d.discount_unit,'h',discount,'%',discount/100*a.overtime_rate2)) from temployee_billingdiscount d " + //
				"                  where d.employeeid=a.employeeid and d.teamid=a.recruiter_teamid and d.recid=a.recid and d.discount > 0),0) netdoubletime, " + //
				"   a.bill_rate billrate, a.overtime_rate1 overtimebillrate, a.overtime_rate2 doubletimebillrate, a.working_city workingcity, a.working_state workingstate, " + //
				"   (select decode(dcatid,1,'Current Employee',2,'Past Employee',3,'Direct Placement') from tcandidate_category q where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=1) employeestatus, " + //
				"   (select u.userfield_value from tcompany_userfields u where u.companyid=d.companyid and u.teamid=a.recruiter_teamid and u.userfield_id=36) customercode, " + //
				"   (select u.userfield_value from tcompany_userfields u where u.companyid=d.companyid and u.teamid=a.recruiter_teamid and u.userfield_id=126) internaldivision, " + //
				"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=141) perdiemtype, " + //
				"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=50) screeningvendor_drug, " + //
				"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=51) screeningvendor_background, " + //
				"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=59) backgroundscreensrun, " + //
				"   (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=54) dateserviced_drug, "
				+ //
				"   (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=61) dateserviced_background, "
				+ //
				"   (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=55) datecleared_drug, "
				+ //
				"   (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=56) datecleared_background, "
				+ //
				"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=57) comments, " + //
				"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=58) rescreened, " + //
				"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=60) rescreencomments, " + //
				"   (select v.dcatname from tcandidate_category q, tcategories_detail v where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=116 " + //
				"       and v.teamid=q.teamid and v.catid=q.catid and v.dcatid=q.dcatid) exempt, " + //
				"   (select v.dcatname from tcandidate_category q, tcategories_detail v where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=117 " + //
				"       and v.teamid=q.teamid and v.catid=q.catid and v.dcatid=q.dcatid) multiplepayrates, " + //
				"   (select v.dcatname from tcandidate_category q, tcategories_detail v where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=114 " + //
				"       and v.teamid=q.teamid and v.catid=q.catid and v.dcatid=q.dcatid) perdiem, " + //
				"   (select v.dcatname from tcandidate_category q, tcategories_detail v where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=115 " + //
				"       and v.teamid=q.teamid and v.catid=q.catid and v.dcatid=q.dcatid) sca, " + //
				"   (select v.dcatname from tcandidate_category q, tcategories_detail v where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=119 " + //
				"       and v.teamid=q.teamid and v.catid=q.catid and v.dcatid=q.dcatid) oncallcontractor," + //
				"   a.customer_refno_sub customerrefno, " + //
				"   (select 1 from temployee_overhead h where h.employeeid=b.employeeid and h.recruiter_teamid=b.recruiter_teamid and h.salary_recid=b.recid and h.overheadid=11) overhead_alert, " + //
				"   (select 1 from temployee_overhead h where h.employeeid=b.employeeid and h.recruiter_teamid=b.recruiter_teamid and h.salary_recid=b.recid and h.overheadid=10) overhead_office, " + //
				"   (select 1 from temployee_overhead h where h.employeeid=b.employeeid and h.recruiter_teamid=b.recruiter_teamid and h.salary_recid=b.recid and h.overheadid=8) overhead_non_office, " + //
				"   (select 1 from temployee_overhead h where h.employeeid=b.employeeid and h.recruiter_teamid=b.recruiter_teamid and h.salary_recid=b.recid and h.overheadid=9) overhead_sick_leave " + //
				" from temployee_billingrecord a, temployee_salaryrecord b, tcandidate c, tcustomer d, tinterviewschedule i " + //
				" where a.recruiter_teamid=? and a.start_date < sysdate and (a.end_date is null or a.end_date > sysdate) and a.approved=1 and (a.closed is null or a.closed = 0) " + //
				"   and b.recruiter_teamid=a.recruiter_teamid and b.employeeid=a.employeeid and b.interviewid=a.interviewid " + //
				"   and b.effective_date < sysdate and (b.end_date is null or b.end_date > sysdate) and b.approved=1 and (b.closed is null or b.closed = 0) " + //
				"   and c.id=a.employeeid and c.teamid=a.recruiter_teamid and d.id=a.billing_contact and i.id=a.interviewid " + //
				" order by employeename "; //
		param.add(clientID); //
		return sql;
	}
	
	public static String getAllAssignments(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				" select c.firstname||' '||c.lastname employeename, " + //
						"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=64) employeeid, " + //
						"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=62) employeeclasskey, " + //
						"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=63) department, " + //
						"   c.email employeeemail, d.companyname, (select rfqtitle from trfq where id=a.rfqid) jobtitle, (select r.firstname||' '||r.lastname from trecruiter r where r.id = a.primary_recruiter) primaryrecuiter, " + //
						"   (select r.firstname||' '||r.lastname from trecruiter r where r.id = i.recruiterid) precruiter, (select r.firstname||' '||r.lastname from trecruiter r where r.id = a.primary_salesperson) primarysales, " + //
						"   i.datehired startdate, a.start_date billingstartdate, (select name from tdivision div where div.id=a.division) division, b.salary, b.overtime_rate1 overtimepayrate, " + //
						"   a.bill_rate - nvl((select sum(decode(d.discount_unit,'h',discount,'%',discount/100*a.bill_rate)) from temployee_billingdiscount d " + //
						"                  where d.employeeid=a.employeeid and d.teamid=a.recruiter_teamid and d.recid=a.recid and d.discount > 0),0) netbill, " + //
						"   a.overtime_rate1 - nvl((select sum(decode(d.discount_unit,'h',discount,'%',discount/100*a.overtime_rate1)) from temployee_billingdiscount d " + //
						"                  where d.employeeid=a.employeeid and d.teamid=a.recruiter_teamid and d.recid=a.recid and d.discount > 0),0) netovertime, " + //
						"   a.overtime_rate2 - nvl((select sum(decode(d.discount_unit,'h',discount,'%',discount/100*a.overtime_rate2)) from temployee_billingdiscount d " + //
						"                  where d.employeeid=a.employeeid and d.teamid=a.recruiter_teamid and d.recid=a.recid and d.discount > 0),0) netdoubletime, " + //
						"   a.bill_rate billrate, a.overtime_rate1 overtimebillrate, a.overtime_rate2 doubletimebillrate, a.working_city workingcity, a.working_state workingstate, " + //
						"   (select decode(dcatid,1,'Current Employee',2,'Past Employee',3,'Direct Placement') from tcandidate_category q where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=1) employeestatus, " + //
						"   (select u.userfield_value from tcompany_userfields u where u.companyid=d.companyid and u.teamid=a.recruiter_teamid and u.userfield_id=36) customercode, " + //
						"   (select u.userfield_value from tcompany_userfields u where u.companyid=d.companyid and u.teamid=a.recruiter_teamid and u.userfield_id=126) internaldivision, " + //
						"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=141) perdiemtype, " + //
						"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=50) screeningvendor_drug, " + //
						"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=51) screeningvendor_background, " + //
						"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=59) backgroundscreensrun, " + //
						"   (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=54) dateserviced_drug, "
						+ //
						"   (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=61) dateserviced_background, "
						+ //
						"   (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=55) datecleared_drug, "
						+ //
						"   (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=56) datecleared_background, "
						+ //
						"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=57) comments, " + //
						"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=58) rescreened, " + //
						"   (select u.userfield_value from tcandidate_userfields u where u.candidateid=a.employeeid and u.teamid=a.recruiter_teamid and u.userfield_id=60) rescreencomments, " + //
						"   (select v.dcatname from tcandidate_category q, tcategories_detail v where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=116 " + //
						"       and v.teamid=q.teamid and v.catid=q.catid and v.dcatid=q.dcatid) exempt, " + //
						"   (select v.dcatname from tcandidate_category q, tcategories_detail v where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=117 " + //
						"       and v.teamid=q.teamid and v.catid=q.catid and v.dcatid=q.dcatid) multiplepayrates, " + //
						"   (select v.dcatname from tcandidate_category q, tcategories_detail v where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=114 " + //
						"       and v.teamid=q.teamid and v.catid=q.catid and v.dcatid=q.dcatid) perdiem, " + //
						"   (select v.dcatname from tcandidate_category q, tcategories_detail v where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=115 " + //
						"       and v.teamid=q.teamid and v.catid=q.catid and v.dcatid=q.dcatid) sca, " + //
						"   (select v.dcatname from tcandidate_category q, tcategories_detail v where q.candidateid=a.employeeid and q.teamid=a.recruiter_teamid and q.catid=119 " + //
						"       and v.teamid=q.teamid and v.catid=q.catid and v.dcatid=q.dcatid) oncallcontractor," + //
						"   a.customer_refno_sub customerrefno, " + //
						"   (select 1 from temployee_overhead h where h.employeeid=b.employeeid and h.recruiter_teamid=b.recruiter_teamid and h.salary_recid=b.recid and h.overheadid=11) overhead_alert, " + //
						"   (select 1 from temployee_overhead h where h.employeeid=b.employeeid and h.recruiter_teamid=b.recruiter_teamid and h.salary_recid=b.recid and h.overheadid=10) overhead_office, " + //
						"   (select 1 from temployee_overhead h where h.employeeid=b.employeeid and h.recruiter_teamid=b.recruiter_teamid and h.salary_recid=b.recid and h.overheadid=8) overhead_non_office, " + //
						"   (select 1 from temployee_overhead h where h.employeeid=b.employeeid and h.recruiter_teamid=b.recruiter_teamid and h.salary_recid=b.recid and h.overheadid=9) overhead_sick_leave " + //
						" from temployee_billingrecord a, temployee_salaryrecord b, tcandidate c, tcustomer d, tinterviewschedule i " + //
						" where a.recruiter_teamid=? and a.actualstart=1 and a.approved=1 and (a.closed is null or a.closed = 0) " + //
						"   and b.recruiter_teamid=a.recruiter_teamid and b.employeeid=a.employeeid and b.interviewid=a.interviewid " + //
						"   and b.approved=1 and (b.closed is null or b.closed = 0) " + //
						"   and b.recid = (select max(x.recid) from temployee_salaryrecord x " + //
						"                  where x.recruiter_teamid=b.recruiter_teamid and x.employeeid=b.employeeid and x.interviewid=b.interviewid and x.approved=1 and (x.closed is null or x.closed = 0)) " + //
						"   and c.id=a.employeeid and c.teamid=a.recruiter_teamid and d.id=a.billing_contact and i.id=a.interviewid " + //
						" order by employeename ";
		param.add(clientID);
		return sql;
	}
	
	public static String newPositionsCountByDivision(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				" select id divisionid, divisionname, sum(value) value" + //
						" from " + //
						" ((select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
						" from " + //
						" (select /*+ index(trfq IDX_TRFQ_DATEISSUED) */ divisionid, sum(positions) value " + //
						" from trfq where teamid=? and dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and nvl(contract,0) <> 5 " + //
						" group by divisionid) a, " + //
						" (select id, name from tdivision where teamid=? and active=1 and id > 0) b " + //
						" where a.divisionid(+) = b.id) " + //
						" union " + //
						" (select b.id, nvl(b.name, 'No Division') divisionname, nvl(value, 0) value " + //
						" from " + //
						" (select /*+ index(trfq IDX_TRFQ_DATEISSUED) */ divisionid, sum(positions) value" + //
						" from trfq where teamid=? and dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and nvl(contract,0) <> 5 " + //
						" group by divisionid) a, " + //
						" (select id, name from tdivision where teamid=? and active=1 and id > 0) b " + //
						" where a.divisionid = b.id(+))) " + //
						" group by id, divisionname " + //
						" order by divisionname ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		return sql;
	}
	
	public static String hiresCountByPrimarySales(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				" select primarysalesid id, (select firstname||' '||lastname from trecruiter r where r.id=primarysalesid) primarysalesname, count(*) value " + //
						" from tinterviewschedule " + //
						" where recruiter_teamid=? and datehired between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
						"   and not exists (select 1 from trfq j where j.id=tinterviewschedule.rfqid and j.contract=5) " + //
						" group by primarysalesid "; //
		param.add(clientID); //
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
}

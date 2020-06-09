package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class ICSFeed {
	
	public static String getOfficeVisits(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select (select u.userfield_value from tcustomer c, tcustomer_userfields u " //
				+ "        where c.ifrecruiterthenid=x.recruiterid and c.teamid=? and u.customerid=c.id and u.teamid=c.teamid and u.userfield_id=287) ics_employee_id, "//
				+ "  (select r.firstname||' '||r.lastname from trecruiter r where r.id=x.recruiterid) ics_employee_name, " //
				+ "  week_ending_date, sum "//
				+ "from (select recruiterid, next_day(trunc(datecreated), 'SATURDAY')-1 week_ending_date, count(*) sum from tcandidatenotes "//
				+ "where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " //
				+ "  and type=10 " //
				+ "group by recruiterid, next_day(trunc(datecreated), 'SATURDAY')) x " + "order by ics_employee_name";
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getSubmittals(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select (select u.userfield_value from tcustomer c, tcustomer_userfields u " ////
				+ "        where c.ifrecruiterthenid=i.recruiterid and c.teamid=? and u.customerid=c.id and u.teamid=c.teamid and u.userfield_id=287) ics_employee_id, " //
				+ "  (select r.firstname||' '||r.lastname from trecruiter r where r.id=i.recruiterid) ics_employee_name, " //
				+ "  next_day(trunc(datepresented), 'SATURDAY')-1 week_ending_date, count(*) sum " + "from tinterviewschedule i "//
				+ "where i.recruiter_teamid=? and i.datepresented between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and nvl(i.roleid,0) < 900 "//
				+ "and not exists (select 1 from tinterviewschedule j where j.candidateid=i.candidateid and j.rfqid=i.rfqid and nvl(j.roleid,0) < 900 and j.id < i.id) " //
				+ "group by i.recruiterid, next_day(trunc(datepresented), 'SATURDAY')";
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getInterviews(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select (select u.userfield_value from tcustomer c, tcustomer_userfields u " //
				+ "        where c.ifrecruiterthenid=i.recruiterid and c.teamid=? and u.customerid=c.//id and u.teamid=c.teamid and u.userfield_id=287) ics_employee_id, "//
				+ "  (select r.firstname||' '||r.lastname from trecruiter r where r.id=i.recruiterid) ics_employee_name, " //
				+ "  next_day(trunc(dateinterview), 'SATURDAY')-1 week_ending_date, count(*) sum " //
				+ "from tinterviewschedule i "//
				+ "where i.recruiter_teamid=? and i.dateinterview between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " //
				+ "group by i.recruiterid, next_day(trunc(dateinterview), 'SATURDAY')";
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getContacts(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select (select u.userfield_value from tcustomer y, tcustomer_userfields u "//
				+ "        where y.ifrecruiterthenid=c.recruiterid and y.teamid=? and u.customerid=y.id and u.teamid=y.teamid and u.userfield_id=287) ics_employee_id, " //
				+ "  (select r.firstname||' '||r.lastname from trecruiter r where r.id=c.recruiterid) ics_employee_name, "// //
				+ "  next_day(trunc(timeentered), 'SATURDAY')-1 week_ending_date, count(*) sum " // //
				+ "from tcustomer c " //
				+ "where c.teamid=? and c.timeentered between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " //
				+ "group by recruiterid, next_day(trunc(timeentered), 'SATURDAY')";
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getReferences(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select (select u.userfield_value from tcustomer c, tcustomer_userfields u " //
				+ "        where c.ifrecruiterthenid=x.recruiterid and c.teamid=? and u.customerid=c.id and u.teamid=c.teamid and u.userfield_id=287) ics_employee_id, " //
				+ "  (select r.firstname||' '||r.lastname from trecruiter r where r.id=x.recruiterid) ics_employee_name, " // //
				+ "  week_ending_date, sum " //
				+ "from (select recruiterid, next_day(trunc(datecreated), 'SATURDAY')-1 week_ending_date, count(*) sum from tcandidatenotes " //
				+ "where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "//
				+ "  and type in (6, 11) " //
				+ "group by recruiterid, next_day(trunc(datecreated), 'SATURDAY')) x " + "order by ics_employee_name";
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getHires(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select c.rfqno_team job_id, i.id start_record_id, " + //
				"  (select u.userfield_value from tcompany_userfields u where u.companyid=c.companyid and u.teamid=c.teamid and u.userfield_id=113) customer_number, " + //
				"  (select cc.name from tcustomercompany cc where cc.id=c.companyid) customer_name, " + //
				"  e.firstname candidate_first_name, e.lastname candidate_last_name, e.firstname||' '||e.lastname candidate_full_name, e.id candidate_number, " + //
				"  (select substr(u.userfield_value,-6,6) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=18) primary_sales_rep_number, "
				+ "  (select substr(u.userfield_value,1,length(u.userfield_value)-7) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=18) primary_sales_rep_name, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=29) primary_sales_percent, " + //
				"  (select substr(u.userfield_value,-6,6) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=39) primary_recruiter_number, " + //
				"  (select substr(u.userfield_value,1,length(u.userfield_value)-7) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=39) primary_recruiter_name, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=31) primary_recruiter_percent, " + //
				"  (select substr(u.userfield_value,-6,6) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=19) secondary_sales_rep_number, " + //
				"  (select substr(u.userfield_value,1,length(u.userfield_value)-7) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=19) secondary_sales_rep_name, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=30) secondary_sales_percent, " + //
				"  (select substr(u.userfield_value,-6,6) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=40) secondary_recruiter_number, " + //
				"  (select substr(u.userfield_value,1,length(u.userfield_value)-7) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=40) secondary_recruiter_name, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=32) secondary_recruiter_percent, " + //
				"  (select substr(u.userfield_value,-6,6) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=82) tertiary_sales_rep_number, " + //
				"  (select substr(u.userfield_value,1,length(u.userfield_value)-7) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=82) tertiary_sales_rep_name, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=33) tertiary_sales_percent, " + //
				"  (select substr(u.userfield_value,-6,6) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=81) tertiary_recruiter_number, " + //
				"  (select substr(u.userfield_value,1,length(u.userfield_value)-7) from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=81) tertiary_recruiter_name, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=34) tertiary_recruiter_percent, " + //
				"  i.datehired date_time_hired, i.placementdate date_time_entered, " + //
				"  case when (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=65) = 'C2C' " + //
				"       then 'Yes' else 'No' end is_corp_to_corp, " + //
				"  case when (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=65) = 'PERM' " + //
				"       then 'Perm' else 'Temp' end Permanent_or_temp, " + //
				"  i.hourly pay_rate, i.pay_hourly bill_rate, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=117) ot_pay_rate, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=118) ot_bill_rate, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=72) billing_contact, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=73) billing_address, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=74) billing_city, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=75) billing_state, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=76) billing_zip_code, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=77) billing_direct_phone, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=91) client_status, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=i.id and u.teamid=i.recruiter_teamid and u.userfield_id=111) billing_time_frame " + //
				"from tinterviewschedule i, trfq c, tcandidate e " + //
				"where i.recruiter_teamid=? and i.datehired is not null and i.placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"  and c.id=i.rfqid and e.id=i.candidateid and e.teamid=i.recruiter_teamid";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getContactActivities(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select * from " + //
				"  (select (select u.userfield_value from tcustomer c, tcustomer_userfields u " + //
				"        where c.ifrecruiterthenid=n.recruiterid and c.teamid=? and u.customerid=c.id and u.teamid=c.teamid and u.userfield_id=287) ics_employee_id, " + //
				"    (select r.firstname||' '||r.lastname from trecruiter r where r.id=recruiterid) ics_employee_name, " + //
				"    next_day(trunc(datecreated), 'SATURDAY')-1 week_ending_date, type, count(*) ct from tcustomernotes n " + //
				"  where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"    and type in (18, 19, 20, 21, 22, 24) and nvl(deleted,0) = 0 " + //
				"  group by recruiterid, next_day(trunc(datecreated), 'SATURDAY'), type) " + //
				"pivot (min(ct) for type in (18 as NETWORKING_EVENT, 19 as ON_SITE_CONTRACTOR_MEETING, 20 as JOINT_CALL_W_A_RECRUITER, 21 AS JOINT_CALL_W_ANOTHER_PRAC_AREA, " + //
				"  22 as CLIENT_MEETING, 24 as NEW_MSA)) " + //
				"order by ics_employee_name";
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getCovers(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select (select u.userfield_value from tcustomer c, tcustomer_userfields u " + //
				"        where c.ifrecruiterthenid=r.recruiterid and c.teamid=? and u.customerid=c.id and u.teamid=c.teamid and u.userfield_id=287) ics_employee_id, " + //
				"  (select u.firstname||' '||u.lastname from trecruiter u where u.id=r.recruiterid) ics_employee_name, " + //
				"  next_day(trunc(dateinterview), 'SATURDAY')-1 week_ending_date, count(distinct i.rfqid||'~'||i.candidateid) sum " + //
				"from tinterviewschedule i, trecruiterrfq r " + //
				"where i.recruiter_teamid=? and i.dateinterview between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"  and i.dateinterview is not null and r.rfqid=i.rfqid and r.teamid=i.recruiter_teamid and r.lead_sales=1 " + //
				"group by r.recruiterid, next_day(trunc(dateinterview), 'SATURDAY') ";
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String getJobRequisitions(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select * from " + "  (select (select u.userfield_value from tcustomer c, tcustomer_userfields u "
				+ "        where c.ifrecruiterthenid=r.recruiterid and c.teamid=? and u.customerid=c.id and u.teamid=c.teamid and u.userfield_id=287) ics_employee_id, "
				+ "    (select u.firstname||' '||u.lastname from trecruiter u where u.id=r.recruiterid) ics_employee_name, "
				+ "    next_day(trunc(dateissued), 'SATURDAY')-1 week_ending_date, decode(j.contract,1,1,2,2,3,2,0) type, sum(nvl(positions,0)) ct " + "  from trfq j, trecruiterrfq r "
				+ "  where j.teamid=? and j.dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + "    and r.rfqid=j.id and r.teamid=j.teamid and r.lead_sales=1 "
				+ "  group by r.recruiterid, next_day(trunc(dateissued), 'SATURDAY'), decode(j.contract,1,1,2,2,3,2,0)) " + "pivot (min(ct) for type in (1 as PERM, 2 as CONTRACT_OR_C_2_H)) ";
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
}

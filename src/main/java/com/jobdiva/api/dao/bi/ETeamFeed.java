package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class ETeamFeed {
	
	public static String getSubmittalMetricsByLaborCategory(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String companyToShow = "";
		boolean companySelected = false;
		;
		if (params != null && params.length > 0) {
			if (params[0] != null && !params[0].equals("0")) {
				companySelected = true;
				companyToShow = " and companyid = ?";
			}
		}
		String sql = "select coalesce((select c.name from tcustomercompany c where c.id=t1.companyid),(select c.name from tcustomercompany c where c.id=t2.companyid)) name," + //
				" coalesce(t1.companyid,t2.companyid) companyid, " + //
				" coalesce(t1.userfield_value, t2.userfield_value) as \"LABOR CATEGORY\"," + //
				"   nvl(t1.jobcount,0) as  \"ISSUED JOBS\", nvl(t1.jobresponded,0) as \"JOBS RESPONDED\", " + //
				"   case when t1.jobcount > 0 then to_char(round(100*nvl(t1.jobresponded,0)/t1.jobcount,2)) else 'N/A' end as \"RESPONSE RATIO\"," + //
				"  nvl(t2.subcount,0) as submittals, nvl(t2.intcount,0) as interviews, nvl(t2.hirecount,0) as hires, nvl(t2.hireenteredcount,0) as \"HIRES ENTERED\"," + //
				"  case when t2.subcount > 0 then to_char(round(100*nvl(t2.intcount,0)/t2.subcount,2)) else 'N/A' end as \"INTERVIEW/SUBMITTAL RATIO\"," + //
				"  case when t2.intcount > 0 then to_char(round(100*nvl(t2.hireenteredcount,0)/t2.intcount,2)) else 'N/A' end as \"HIRE/INTERVIEW RATIO\", " + //
				"  case when t2.subcount > 0 then to_char(round(100*nvl(t2.hireenteredcount,0)/t2.subcount,2)) else 'N/A' end as \"HIRE/SUBMITTAL RATIO\"" + //
				" from" + //
				"(select companyid, b.userfield_value, count(*) jobcount, sum(decode((select count(*) from tinterviewschedule b where a.id=b.rfqid and nvl(b.roleid,0) < 900), 0,0,1)) jobresponded" + //
				" from trfq a, trfq_userfields b" + //
				" where a.teamid=?" + //
				"  and a.dateissued between to_date(?, 'MM/DD/YYYY hh24:mi:ss') and to_date(?, 'MM/DD/YYYY hh24:mi:ss')" + //
				"  and a.jobstatus <> 6" + //
				"  and b.teamid = ?" + //
				"  and b.rfqid = a.id" + //
				"  and b.userfield_id = 53" + companyToShow + //
				" group by companyid, b.userfield_value) t1" + //
				" full outer join" + //
				" (select companyid, c.userfield_value," + //
				"  count(distinct (case when nvl(roleid,0)<900 and datepresented between to_date(?, 'MM/DD/YYYY hh24:mi:ss') and to_date(?, 'MM/DD/YYYY hh24:mi:ss')" + //
				"                         then b.rfqid||'~'||candidateid else '0' end)) - " + //
				"        max(case when nvl(roleid,0)>900 or datepresented < to_date(?, 'MM/DD/YYYY hh24:mi:ss') or datepresented > to_date(?, 'MM/DD/YYYY hh24:mi:ss')" + //
				"                 then 1 else 0 end) subcount," + //
				"    count(distinct (case when dateinterview between to_date(?, 'MM/DD/YYYY hh24:mi:ss') and to_date(?, 'MM/DD/YYYY hh24:mi:ss')" + //
				"                         then b.rfqid||'~'||candidateid else '0' end)) - " + //
				"        max(case when dateinterview is null or dateinterview < to_date(?, 'MM/DD/YYYY hh24:mi:ss') or dateinterview > to_date(?, 'MM/DD/YYYY hh24:mi:ss')" + //
				"                 then 1 else 0 end) intcount," + //
				"    sum(case when datehired between to_date(?, 'MM/DD/YYYY hh24:mi:ss') and to_date(?, 'MM/DD/YYYY hh24:mi:ss') " + //
				"           then 1 else 0 end) hirecount," + //
				" sum(case when placementdate between to_date(?, 'MM/DD/YYYY hh24:mi:ss') and to_date(?, 'MM/DD/YYYY hh24:mi:ss') " + //
				"           then 1 else 0 end) hireenteredcount" + //
				" from trfq a, tinterviewschedule b, trfq_userfields c" + //
				" where a.teamid=?" + //
				"  and b.recruiter_teamid=? and nvl(b.roleid,0) < 900" + //
				"  and (b.datepresented between to_date(?, 'MM/DD/YYYY hh24:mi:ss') and to_date(?, 'MM/DD/YYYY hh24:mi:ss')" + //
				"      or b.dateinterview between to_date(?, 'MM/DD/YYYY hh24:mi:ss') and to_date(?, 'MM/DD/YYYY hh24:mi:ss')" + //
				"      or b.datehired between to_date(?, 'MM/DD/YYYY hh24:mi:ss') and to_date(?, 'MM/DD/YYYY hh24:mi:ss'))" + //
				"  and a.id = b.rfqid " + //
				"  and c.teamid = ?" + //
				"  and c.rfqid = a.id" + //
				"  and c.userfield_id = 53" + companyToShow + //
				" group by companyid, userfield_value) t2" + //
				" on t1.companyid = t2.companyid and t1.userfield_value = t2.userfield_value" + //
				" order by upper(name)"; //
		param.add(clientID); //
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		if (companySelected)
			param.add(params[0]);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		if (companySelected)
			param.add(params[0]);
		return sql;
	}
}

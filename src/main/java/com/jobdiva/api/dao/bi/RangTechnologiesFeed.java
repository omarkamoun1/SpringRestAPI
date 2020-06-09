package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class RangTechnologiesFeed {
	
	public static String getCandidateWithMissingData(Long clientID, String fromDate, String toDate, Vector<Object> param) { //
		String sql = //
				" select * from " + //
						" (select datereceived \"DATE CREATED\", (select r.firstname||' '||r.lastname from tcandidatedocument_header h, trecruiter r " + //
						"        where h.candidateid=a.id and h.teamid=a.teamid and h.recruiterid>0 and r.id=h.recruiterid and rownum=1) \"ENTERED BY\", " + //
						"   email \"MAIN EMAIL\", " + //
						"   case when upper(a.lastname)='UNKNOWN' then 'MISSING' else to_char(a.firstname||' '||a.lastname) end \"NAME\", " + //
						"   case when a.homephone is null and a.workphone is null and a.cellphone is null and a.fax is null then 'MISSING' " + //
						"        else to_char(nvl(a.homephone,nvl(a.workphone,nvl(a.cellphone,a.fax)))) end phone, " + //
						"   case when a.email like 'Auto_%@jobdiva.com' and a.sysemail is null then 'MISSING' " + //
						"        when a.email like 'Auto_%@jobdiva.com' then to_char(a.sysemail) else to_char(a.email) end email, " + //
						"   case when a.city is null then 'MISSING' else to_char(a.city) end city, " + //
						"   case when a.state is null then 'MISSING' else to_char(a.state) end state, " + //
						"   case when a.zipcode is null then 'MISSING' else to_char(a.zipcode) end \"ZIP CODE\", " + //
						"   case when not exists (select 1 from teducation_header e where e.candidateid=a.id and e.teamid=a.teamid) then 'MISSING' " + //
						"        else 'HAS EDUCATION' end education, " + //
						"   case when not exists (select 1 from tworkexperience_header e where e.candidateid=a.id and e.teamid=a.teamid) then 'MISSING' " + //
						"        else 'HAS EXPERIENCE' end experience " + //
						" from tcandidate a " + //
						" where a.teamid=? and a.datereceived between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) " + //
						" where \"ENTERED BY\" is not null and (name='MISSING' or phone='MISSING' or email='MISSING' or city='MISSING' or state='MISSING' or \"ZIP CODE\"='MISSING' or education='MISSING' or experience='MISSING') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
}

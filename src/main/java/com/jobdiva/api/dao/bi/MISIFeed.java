package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class MISIFeed {
	
	public static String newJobs(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				" select a.id jobid, " + //
						"   (select y.FIRSTNAME||' '||y.LASTNAME " + //
						"    from trecruiterrfq x, trecruiter y where x.RFQID = a.ID and x.LEAD_SALES=1 and y.ID = x.RECRUITERID) primarysales, " + //
						"   a.rfqno_team jobdivarefno, (select x.name from tcustomercompany x where x.id=a.companyid) company, " + //
						"   (select userfield_value from tcompany_userfields x where x.companyid=a.companyid and x.teamid=a.teamid and x.userfield_id=47) salesforcedotcomid, " + //
						"   (select x.userfield_value from trfq_userfields x, tuserfields y where x.rfqid=a.id and x.teamid=a.teamid " + //
						"    and y.id=x.userfield_id and y.teamid=x.teamid and y.fieldname='Deal Type') dealtype, " + //
						"   trunc(a.startdate) startdate, case when a.enddate < '01-Jan-1970' then null else trunc(a.enddate) end endddate," + //
						"   a.positions openings, nvl(a.billratemax,a.billratemin) billrate, " + //
						"   decode(lower(a.billrateper),'d','Daily','y','Yearly','h','Hourly',null,null," + //
						"			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=0 and x.unitid=billrateper)) billfrequency, " + //
						"   decode(a.jobstatus,0,'Open',1,'On-Hold',2,'Filled',3,'Cancelled',4,'Closed',5,'Expired',6,'Ignored','') status, " + //
						"   decode(a.jobpriority,1,'A',2,'B',3,'C',4,'D') priority, " + //
						"   (select x.userfield_value from trfq_userfields x, tuserfields y where x.rfqid=a.id and x.teamid=a.teamid " + //
						"    and y.id=x.userfield_id and y.teamid=x.teamid and y.fieldname='Position Type') positiontype " + //
						"   from trfq a where a.teamid=? and (a.dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
						"       or a.datelastupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newStarts(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				" select a.id startid, a.rfqid jobid, trunc(a.datehired) startdate, trunc(a.date_ended) enddate, a.pay_hourly billrate, " + //
						"   decode(a.finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', " + //
						"       (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=1 and x.unitid=a.finalbillrateunit)) billfrequency, " + //
						"   (select x.userfield_value from tstartrecord_userfields x, tuserfields y where x.startid=a.id and x.teamid=a.recruiter_teamid " + //
						"    and y.id=x.userfield_id and y.teamid=x.teamid and y.fieldname='Gross Margin') marginpect " + //
						" from tinterviewschedule a " + //
						" where a.recruiter_teamid=? " + //
						"   and (a.placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
						"     or a.dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) " + //
						"   and a.datehired is not null ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
}

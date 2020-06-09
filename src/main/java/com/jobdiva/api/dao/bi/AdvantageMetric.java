package com.jobdiva.api.dao.bi;

import java.util.*;

public class AdvantageMetric {
	public static String newCompanies(String[] params, String fromDate, String toDate, Vector<Object> param) {
		StringBuffer columnsToShow = new StringBuffer();
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " +
						" case when n.fieldtypeid = 3  " +
						" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd')||'T00:00:00.0' " +
						"      when n.fieldtypeid = 4 " +
						" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " +
						" else t.userfield_value end userfield_value " +
						" from tcompany_userfields t, tuserfields n " +
						" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"') \"" + params[i] + "\"");
			}
		}
		
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" a.id companyid, a.name companyname, dateentered datecreated, editdate dateupdated, parent_companyid, " +
			" b.address1, b.address2, b.city, b.state, b.zipcode, b.countryid, b.phone, b.fax, b.email, b.url " +
			columnsToShow.toString() +
			" from tcustomercompany a, tcustomercompanyaddresses b " +
			" where a.teamid in (22,152,185,219) and a.dateentered between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"   and b.companyid=a.id and b.teamid=a.teamid and b.default_address=1 ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String updatedCompanies(String[] params, String fromDate, String toDate, Vector<Object> param) {
		StringBuffer columnsToShow = new StringBuffer();
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " +
						" case when n.fieldtypeid = 3  " +
						" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd')||'T00:00:00.0' " +
						"      when n.fieldtypeid = 4 " +
						" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " +
						" else t.userfield_value end userfield_value " +
						" from tcompany_userfields t, tuserfields n " +
						" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"') \"" + params[i] + "\"");
			}
		}
		
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" a.id companyid, a.name companyname, dateentered datecreated, editdate dateupdated, parent_companyid, " +
			" b.address1, b.address2, b.city, b.state, b.zipcode, b.countryid, b.phone, b.fax, b.email, b.url " +
			columnsToShow.toString() +
			" from tcustomercompany a, tcustomercompanyaddresses b " +
			" where a.teamid in (22,152,185,219) and a.editdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"   and trunc(a.editdate) > trunc(a.dateentered) " +
			"   and b.companyid=a.id and b.teamid=a.teamid and b.default_address=1 ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String deletedCompanies(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" companyid, datedeleted " +
			" from tcustomercompany_deleted " +
			" where teamid in (22,152,185,219) and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String mergedCompanies(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" companyid, mergetocompanyid, recruiterid userid, datemerged " +
			" from tcompany_merge " +
			" where teamid in (22,152,185,219) and datemerged between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String companyOwnerUpdated(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, companyid, max(dateupdated) dateupdated " +
			" from tcompany_ownerupdated " +
			" where teamid in (22,152,185,219) and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" group by teamid, companyid";
		
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String companyTypeUpdated(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, companyid, max(dateupdated) dateupdated " +
			" from tcompany_typeupdated " +
			" where teamid in (22,152,185,219) and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" group by teamid, companyid";
		
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String newContacts(String[] params, String fromDate, String toDate, Vector<Object> param) {
		StringBuffer columnsToShow = new StringBuffer();
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " +
						" case when n.fieldtypeid = 3  " +
						" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd')||'T00:00:00.0' " +
						"      when n.fieldtypeid = 4 " +
						" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " +
						" else t.userfield_value end userfield_value " +
						" from tcustomer_userfields t, tuserfields n " +
						" where t.customerid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"') \"" + params[i] + "\"");
			}
		}
		
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" a.id contactid, a.firstname, a.lastname, timeentered datecreated, editdate dateupdated, " +
			" a.companyid, a.isprimarycontact, reportsto, assistantname, assistantemail, assistantphone, assistantphoneext, " +
			" workphone phone1, workphoneext phone1ext, " +
			" case when phonetypes is null or length(phonetypes) = 0 then 'Work Phone' " +
			"      else decode(substr(phonetypes,1,1),'W','Work Phone','C','Mobile Phone','H','Home Phone','F','Work Fax','X','Home Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone') end phone1type, " +
			" cellphone phone2, cellphoneext phone2ext, " +
			" case when phonetypes is null or length(phonetypes) < 2 then 'Mobile Phone' " +
			"      else decode(substr(phonetypes,2,1),'W','Work Phone','C','Mobile Phone','H','Home Phone','F','Work Fax','X','Home Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone') end phone2type, " +
			" homephone phone3, homephoneext phone3ext, " +
			" case when phonetypes is null or length(phonetypes) < 3 then 'Home Phone' " +
			"      else decode(substr(phonetypes,3,1),'W','Work Phone','C','Mobile Phone','H','Home Phone','F','Work Fax','X','Home Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone') end phone3type, " +
			" contactfax phone4, contactfaxext phone4ext, " +
			" case when phonetypes is null or length(phonetypes) < 4 then 'Work Fax' " +
			"      else decode(substr(phonetypes,4,1),'W','Work Phone','C','Mobile Phone','H','Home Phone','F','Work Fax','X','Home Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone') end phone4type, " +
			" email, alternate_email, b.address1, b.address2, b.city, b.state, b.zipcode, b.countryid " +
			columnsToShow.toString() +
			" from tcustomer a, tcustomeraddress b " +
			" where a.teamid in (22,152,185,219) and a.timeentered between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"   and b.contactid=a.id and b.teamid=a.teamid and b.default_address=1 ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String updatedContacts(String[] params, String fromDate, String toDate, Vector<Object> param) {
		StringBuffer columnsToShow = new StringBuffer();
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " +
						" case when n.fieldtypeid = 3  " +
						" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd')||'T00:00:00.0' " +
						"      when n.fieldtypeid = 4 " +
						" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " +
						" else t.userfield_value end userfield_value " +
						" from tcustomer_userfields t, tuserfields n " +
						" where t.customerid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"') \"" + params[i] + "\"");
			}
		}
		
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" a.id contactid, a.firstname, a.lastname, timeentered datecreated, editdate dateupdated, " +
			" a.companyid, a.isprimarycontact, reportsto, assistantname, assistantemail, assistantphone, assistantphoneext, " +
			" workphone phone1, workphoneext phone1ext, " +
			" case when phonetypes is null or length(phonetypes) = 0 then 'Work Phone' " +
			"      else decode(substr(phonetypes,1,1),'W','Work Phone','C','Mobile Phone','H','Home Phone','F','Work Fax','X','Home Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone') end phone1type, " +
			" cellphone phone2, cellphoneext phone2ext, " +
			" case when phonetypes is null or length(phonetypes) < 2 then 'Mobile Phone' " +
			"      else decode(substr(phonetypes,2,1),'W','Work Phone','C','Mobile Phone','H','Home Phone','F','Work Fax','X','Home Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone') end phone2type, " +
			" homephone phone3, homephoneext phone3ext, " +
			" case when phonetypes is null or length(phonetypes) < 3 then 'Home Phone' " +
			"      else decode(substr(phonetypes,3,1),'W','Work Phone','C','Mobile Phone','H','Home Phone','F','Work Fax','X','Home Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone') end phone3type, " +
			" contactfax phone4, contactfaxext phone4ext, " +
			" case when phonetypes is null or length(phonetypes) < 4 then 'Work Fax' " +
			"      else decode(substr(phonetypes,4,1),'W','Work Phone','C','Mobile Phone','H','Home Phone','F','Work Fax','X','Home Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone') end phone4type, " +
			" email, alternate_email, b.address1, b.address2, b.city, b.state, b.zipcode, b.countryid " +
			columnsToShow.toString() +
			" from tcustomer a, tcustomeraddress b " +
			" where a.teamid in (22,152,185,219) and a.editdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"   and b.contactid=a.id and b.teamid=a.teamid and b.default_address=1 ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String deletedContacts(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" customerid contactid, datedeleted " +
			" from tcustomer_deleted " +
			" where teamid in (22,152,185,219) and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String mergedContacts(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" contactid, mergetocontactid, recruiterid userid, datemerged " +
			" from tcustomer_merge " +
			" where teamid in (22,152,185,219) and datemerged between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String contactOwnerUpdated(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, contactid, max(dateupdated) dateupdated " +
			" from tcontact_ownerupdated " +
			" where teamid in (22,152,185,219) and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" group by teamid, contactid";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String issuedJobs(String[] params, String fromDate, String toDate, Vector<Object> param) {
		StringBuffer columnsToShow = new StringBuffer();
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				if(params[i].equals("Customer Number")) {
					columnsToShow.append(", nvl((select t.userfield_value from trfq_userfields t, tuserfields n " +
							" where t.rfqid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"')," +
							" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
							" where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"')) \"" + params[i] + "\"");
				}
				else {
					columnsToShow.append(", (select " +
						" case when n.fieldtypeid = 3  " +
						" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd')||'T00:00:00.0' " +
						"      when n.fieldtypeid = 4 " +
						" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " +
						" else t.userfield_value end userfield_value " +
						" from trfq_userfields t, tuserfields n " +
							" where t.rfqid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"') \"" + params[i] + "\"");
				}
			}
		}
		
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" a.id jobid, rfqno_team jobdivano, rfqrefno optionalreferenceno, divisionid, b.name divisionname, " +
			" pr.recruiterid primaryrecruiterid, ps.recruiterid primarysalesid, " +
			" a.companyid, c.name companyname, " +
			" (select recruiterid from tcustomer_company_owners x where x.companyid=a.companyid and x.isprimaryowner=1) primaryownerid, " +
			" a.customerid contactid, d.firstname contactfirstname, d.lastname contactlastname, " +
			" a.dateissued issuedate, startdate, case when enddate < '01-Jan-1970' then null else enddate end enddate, " +
			" decode(contract,1,'Full Time',2,'Contract',3,'Right to Hire',4,'Full Time/Contract','') positiontype, " +
			" decode(a.jobstatus,0,'Open',1,'On Hold',2,'Filled',3,'Cancelled',4,'Closed',5,'Expired',6,'Ignored','') jobstatus, " +
			" rfqtitle title, positions openings, fills, a.city, a.state, a.zipcode, " +
			" billratemin, billratemax, decode(lower(billrateper),'d','Daily','y','Yearly','h','Hourly',null,null," +
			"			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=0 and x.unitid=billrateper)) billfrequency, " +
			" ratemin payratemin, ratemax payratemax, decode(lower(rateper),'d','Daily','y','Yearly','h','Hourly',null,null," +
			"			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=1 and x.unitid=rateper)) payfrequency, " +
			" nvl((select x.name from tcurrency x where x.id=payrate_currency), " +
			"   	nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency " + 
			columnsToShow.toString() + ", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='DUNS#') \"DUNS#\" " +
			" from trfq a, tdivision b, tcustomercompany c, tcustomer d, trecruiterrfq pr, trecruiterrfq ps " +
			" where a.teamid in (22,152,185,219) and a.dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and a.divisionid = b.id(+) and a.companyid = c.id(+) and a.customerid = d.id(+) " +
			" and a.id = pr.rfqid(+) and 1 = pr.lead_recruiter(+) " +
			" and a.id = ps.rfqid(+) and 1 = ps.lead_sales(+) ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String updatedJobs(String[] params, String fromDate, String toDate, Vector<Object> param) {
		// updated jobs
		StringBuffer columnsToShow = new StringBuffer();
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				if(params[i].equals("Customer Number")) {
					columnsToShow.append(", nvl((select t.userfield_value from trfq_userfields t, tuserfields n " +
							" where t.rfqid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"')," +
							" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
							" where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"')) \"" + params[i] + "\"");
				}
				else {
					columnsToShow.append(", (select " +
						" case when n.fieldtypeid = 3  " +
						" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd')||'T00:00:00.0' " +
						"      when n.fieldtypeid = 4 " +
						" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " +
						" else t.userfield_value end userfield_value " +
						" from trfq_userfields t, tuserfields n " +
							" where t.rfqid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"') \"" + params[i] + "\"");
				}
			}
		}
		
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" a.id jobid, rfqno_team jobdivano, rfqrefno optionalreferenceno, divisionid, b.name divisionname, " +
			" pr.recruiterid primaryrecruiterid, ps.recruiterid primarysalesid, " +
			" a.companyid, c.name companyname, " +
			" (select recruiterid from tcustomer_company_owners x where x.companyid=a.companyid and x.isprimaryowner=1) primaryownerid, " +
			" a.customerid contactid, d.firstname contactfirstname, d.lastname contactlastname, " +
			" a.dateissued issuedate, startdate, case when enddate < '01-Jan-1970' then null else enddate end enddate, " +
			" decode(contract,1,'Full Time',2,'Contract',3,'Right to Hire',4,'Full Time/Contract','') positiontype, " +
			" decode(a.jobstatus,0,'Open',1,'On Hold',2,'Filled',3,'Cancelled',4,'Closed',5,'Expired',6,'Ignored','') jobstatus, " +
			" rfqtitle title, positions openings, fills, a.city, a.state, a.zipcode, " +
			" billratemin, billratemax, decode(lower(billrateper),'d','Daily','y','Yearly','h','Hourly',null,null," +
			"			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=0 and x.unitid=billrateper)) billfrequency, " +
			" ratemin payratemin, ratemax payratemax, decode(lower(rateper),'d','Daily','y','Yearly','h','Hourly',null,null," +
			"			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=1 and x.unitid=rateper)) payfrequency, " +
			" nvl((select x.name from tcurrency x where x.id=payrate_currency), " +
			"   	nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency " + 
			columnsToShow.toString() + ", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='DUNS#') \"DUNS#\" " +
			" from trfq a, tdivision b, tcustomercompany c, tcustomer d, trecruiterrfq pr, trecruiterrfq ps " +
			" where a.teamid in (22,152,185,219) and a.datelastupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and a.divisionid = b.id(+) and a.companyid = c.id(+) and a.customerid = d.id(+) " +
			" and a.id = pr.rfqid(+) and 1 = pr.lead_recruiter(+) " +
			" and a.id = ps.rfqid(+) and 1 = ps.lead_sales(+) ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String deletedJobs(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
				" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
				" rfqid jobid, datedeleted " +
				" from trfq_deleted " +
				" where teamid in (22,152,185,219) and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String mergedJobs(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" rfqid jobid, mergetorfqid mergetojobid, recruiterid userid, datemerged " +
			" from trfq_merge " +
			" where teamid in (22,152,185,219) and datemerged between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String jobUserUpdated(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, jobid, max(dateupdated) dateupdated " +
			" from tjob_userupdated " +
			" where teamid in (22,152,185,219) and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" group by teamid, jobid";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String newActivities(String[] params, String fromDate, String toDate, Vector<Object> param) {
		StringBuffer columnsToShow = new StringBuffer();
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " +
						" case when n.fieldtypeid = 3  " +
						" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd')||'T00:00:00.0' " +
						"      when n.fieldtypeid = 4 " +
						" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " +
						" else t.userfield_value end userfield_value " +
						" from tstartrecord_userfields t, tuserfields n " +
						" where t.startid=f.id and t.teamid=f.recruiter_teamid and n.teamid=f.recruiter_teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"') \"" + params[i] + "\"");
			}
		}
		
		String sql =
			" select decode(a.recruiter_teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" a.activityid, f.recruiterid userid, f.primarysalesid, " +
			" a.candidateid, " +
			" b.firstname candidatefirstname, b.lastname candidatelastname, " +
//			" b.email candidateemail, " +
			" decode((select count(*) from tinterviewschedule x where x.recruiter_teamid=a.recruiter_teamid and x.candidateid=a.candidateid and x.datepresented<a.activitydate and (x.roleid is null or x.roleid < 900)),0,0,1) previouslysubmittedflag, " +
			" decode((select count(*) from tinterviewschedule x where x.recruiter_teamid=a.recruiter_teamid and x.candidateid=a.candidateid and x.placementdate<a.activitydate and x.datehired is not null),0,0,1) previouslyhiredflag, " +
			" a.rfqid jobid, " +
			" decode(f.customerid,0,'',f.customerid) contactid, " + 
//			" d.firstname contactfirstname, d.lastname contactlastname, " +
//			" e.id companyid, e.name companyname, " +
			" case when a.submittalflag=1 then f.datecreated else a.activitydate end activitydate, f.dateupdated updatedate, " +
            " (select s.name from tresumesourcename s where s.teamid=f.recruiter_teamid and s.id=f.resumesource) resumesource, " +
			" a.submittalflag, a.interviewflag, a.hireflag, case when f.roleid >= 900 then 1 else 0 end internalsubmittalflag, " +
			" case when a.submittalflag=1 then f.datepresented else null end submittaldate, " +
			" case when a.submittalflag=1 and f.roleid >=900 then f.daterejected else null end internalrejectdate, " +
			" case when a.interviewflag=1 then f.dateinterview else null end interviewdate, " +
			" f.dateextrejected externalrejectdate, " +
			" case when a.hireflag=1 then f.datehired else null end startdate, " +
			" case when a.hireflag=1 then f.date_ended else null end enddate, " +
			" case when a.hireflag=1 then f.dateterminated else null end terminationdate, " +
			" case when a.hireflag=1 and f.dateterminated is not null and f.reasonterminated > 0 " +
			"      then (select description from treason_termination x where x.teamid=f.recruiter_teamid and x.id=f.reasonterminated) " +
			"      else null end terminationreason, " +
			" case when a.hireflag=1 then f.pay_hourly else null end agreedbillrate, " +
			" case when a.hireflag=1 and f.finalbillrateunit is not null " +
			"      then decode(f.finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', " +
			"           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=1 and x.unitid=f.finalbillrateunit)) " +
			"      else null end billfrequency, " +
			" case when a.hireflag=1 then f.hourly else null end agreedpayrate, " +
			" case when a.hireflag=1 and f.payrateunits is not null " +
			"      then decode(substr(f.payrateunits,0,1),'h','Hourly','d','Daily','y','Yearly', " +
			"           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=0 and x.unitid=substr(f.payrateunits,0,1))) " +
			"      else null end payfrequency, " +
			" case when a.hireflag=1 " +
			"      then nvl((select x.name from tcurrency x where x.id=f.hourly_currency), " +
			"             nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) " +
			"      else null end currency " +
			columnsToShow.toString() + 
			" from " +
			"   (select recruiter_teamid, id activityid, candidateid, rfqid, datepresented activitydate, 1 submittalflag, 0 interviewflag, 0 hireflag " +
			"   from tinterviewschedule x where recruiter_teamid in (22,152,185,219) and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"   and ((nvl(roleid, 0) < 900 and id = (select min(y.id) from tinterviewschedule y where x.candidateid=y.candidateid and x.rfqid=y.rfqid and nvl(y.roleid,0)<900))" +
			"     or (roleid >= 900 and id = (select min(y.id) from tinterviewschedule y where x.candidateid=y.candidateid and x.rfqid=y.rfqid and y.roleid>=900))) " +
			" union all " +
			"   (select recruiter_teamid, id activityid, candidateid, rfqid, interviewscheduledate activitydate, 0 submittalflag, 1 interviewflag, 0 hireflag " +
			"   from tinterviewschedule where recruiter_teamid in (22,152,185,219) and interviewscheduledate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and dateinterview is not null) " +
			" union all " +
			"   (select recruiter_teamid, id activityid, candidateid, rfqid, placementdate activitydate, 0 submittalflag, 0 interviewflag, 1 hireflag " +
			"   from tinterviewschedule where recruiter_teamid in (22,152,185,219) and placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and datehired is not null)) a, " +
//			" tcandidate b, trfq c, tcustomer d, tcustomercompany e, " +
			"   tcandidate b, tinterviewschedule f " +
			" where " +
//			"   a.candidateid = b.id and a.recruiter_teamid = b.teamid and a.rfqid = c.id and c.customerid = d.id(+) and c.companyid = e.id(+) and " +
			"   b.id = a.candidateid and b.teamid = a.recruiter_teamid and f.id = a.activityid ";
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String updatedActivities(String[] params, String fromDate, String toDate, Vector<Object> param) {
		StringBuffer columnsToShow = new StringBuffer();
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " +
						" case when n.fieldtypeid = 3  " +
						" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd')||'T00:00:00.0' " +
						"      when n.fieldtypeid = 4 " +
						" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " +
						" else t.userfield_value end userfield_value " +
						" from tstartrecord_userfields t, tuserfields n " +
						" where t.startid=f.id and t.teamid=f.recruiter_teamid and n.teamid=f.recruiter_teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"') \"" + params[i] + "\"");
			}
		}
		
		String sql =
			" select decode(a.recruiter_teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" a.activityid, f.recruiterid userid, f.primarysalesid, " +
			" a.candidateid, " +
			" b.firstname candidatefirstname, b.lastname candidatelastname, " +
//			" b.email candidateemail, " +
			" decode((select count(*) from tinterviewschedule x where x.recruiter_teamid=a.recruiter_teamid and x.candidateid=a.candidateid and x.datepresented<a.activitydate and (x.roleid is null or x.roleid < 900)),0,0,1) previouslysubmittedflag, " +			
			" decode((select count(*) from tinterviewschedule x where x.recruiter_teamid=a.recruiter_teamid and x.candidateid=a.candidateid and x.placementdate<a.activitydate and x.datehired is not null),0,0,1) previouslyhiredflag, " +
			" a.rfqid jobid, " +
			" decode(f.customerid,0,'',f.customerid) contactid, " +
//			" d.firstname contactfirstname, d.lastname contactlastname, " +
//			" e.id companyid, e.name companyname, " +
			" case when a.submittalflag=1 then f.datecreated else a.activitydate end activitydate, f.dateupdated updatedate, " +
            " (select s.name from tresumesourcename s where s.teamid=f.recruiter_teamid and s.id=f.resumesource) resumesource, " +
			" a.submittalflag, a.interviewflag, a.hireflag, case when f.roleid >= 900 then 1 else 0 end internalsubmittalflag, " +
			" case when a.submittalflag=1 then f.datepresented else null end submittaldate, " +
			" case when a.submittalflag=1 and f.roleid >=900 then f.daterejected else null end internalrejectdate, " +
			" case when a.interviewflag=1 then f.dateinterview else null end interviewdate, " +
			" f.dateextrejected externalrejectdate, " +
			" case when a.hireflag=1 then f.datehired else null end startdate, " +
			" case when a.hireflag=1 then f.date_ended else null end enddate, " +
			" case when a.hireflag=1 then f.dateterminated else null end terminationdate, " +
			" case when a.hireflag=1 and f.dateterminated is not null and f.reasonterminated > 0 " +
			"      then (select description from treason_termination x where x.teamid=f.recruiter_teamid and x.id=f.reasonterminated) " +
			"      else null end terminationreason, " +
			" case when a.hireflag=1 then f.pay_hourly else null end agreedbillrate, " +
			" case when a.hireflag=1 and f.finalbillrateunit is not null " +
			"      then decode(f.finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', " +
			"           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=1 and x.unitid=f.finalbillrateunit)) " +
			"      else null end billfrequency, " +
			" case when a.hireflag=1 then f.hourly else null end agreedpayrate," +
			" case when a.hireflag=1 and f.payrateunits is not null " +
			"      then decode(substr(f.payrateunits,0,1),'h','Hourly','d','Daily','y','Yearly', " +
			"           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=0 and x.unitid=substr(f.payrateunits,0,1))) " +
			"      else null end payfrequency, " +
			" case when a.hireflag=1 " +
			"      then nvl((select x.name from tcurrency x where x.id=f.hourly_currency), " +
			"             nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) " +
			"      else null end currency " +
			columnsToShow.toString() +
			" from " +
			"   ((select x.recruiter_teamid, x.id activityid, x.candidateid, x.rfqid, x.datepresented activitydate, 1 submittalflag, 0 interviewflag, 0 hireflag " +
			"   from " +
			"     tinterviewschedule x, " +
			"     (select rfqid, candidateid from tinterviewschedule " +
			"     where recruiter_teamid in (22,152,185,219) and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"       and trunc(dateupdated) > trunc(datecreated) " +
			"         union " +
			"     select rfqid, candidateid from tinterviewschedule_deleted " +
			"     where teamid in (22,152,185,219) and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) y " +
			"   where x.rfqid = y.rfqid and x.candidateid = y.candidateid " +
			"     and ((nvl(x.roleid,0)<900 and id = (select min(id) from tinterviewschedule z where x.candidateid=z.candidateid and x.rfqid=z.rfqid and nvl(z.roleid,0)<900)) " +
			"       or (x.roleid >=900 and id = (select min(id) from tinterviewschedule z where x.candidateid=z.candidateid and x.rfqid=z.rfqid and z.roleid>=900)))" +
			"     and nvl(dateupdated,datecreated) < to_date(?,'mm/dd/yyyy hh24:mi:ss')) " +
			" union all " +
			"   (select x.recruiter_teamid, x.id activityid, x.candidateid, x.rfqid, x.interviewscheduledate activitydate, 0 submittalflag, 1 interviewflag, 0 hireflag " +
			"   from " +
			"     tinterviewschedule x, " +
			"     (select rfqid, candidateid from tinterviewschedule " +
			"     where recruiter_teamid in (22,152,185,219) and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"       and trunc(dateupdated) > trunc(datecreated) " +
			"         union " +
			"     select rfqid, candidateid from tinterviewschedule_deleted " +
			"     where teamid in (22,152,185,219) and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) y " +
			"   where x.rfqid = y.rfqid and x.candidateid = y.candidateid " +
			"     and dateinterview is not null " +
			"     and nvl(dateupdated,datecreated) < to_date(?,'mm/dd/yyyy hh24:mi:ss')) " +
			" union all " +
			"   (select x.recruiter_teamid, x.id activityid, x.candidateid, x.rfqid, x.placementdate activitydate, 0 submittalflag, 0 interviewflag, 1 hireflag " +
			"   from " +
			"     tinterviewschedule x, " +
			"     (select rfqid, candidateid from tinterviewschedule " +
			"     where recruiter_teamid in (22,152,185,219) and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"       and trunc(dateupdated) > trunc(datecreated) " +
			"         union " +
			"     select rfqid, candidateid from tinterviewschedule_deleted " +
			"     where teamid in (22,152,185,219) and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) y " +
			"   where x.rfqid = y.rfqid and x.candidateid = y.candidateid " +
			"     and datehired is not null " +
			"     and nvl(dateupdated,datecreated) < to_date(?,'mm/dd/yyyy hh24:mi:ss'))) a, " +
//			" tcandidate b, trfq c, tcustomer d, tcustomercompany e, " +
			" tcandidate b, tinterviewschedule f " +
			" where " +
//			"   a.candidateid = b.id and a.recruiter_teamid = b.teamid and a.rfqid = c.id and c.customerid = d.id(+) and c.companyid = e.id(+) and " +
			"   b.id = a.candidateid and b.teamid = a.recruiter_teamid and f.id = a.activityid ";
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(toDate);
		
		return sql;
	}
	
	
	// all related rfq/candidate records that are updated and deleted
	public static String deletedActivities(String fromDate, String toDate, Vector<Object> param) {
		String sql =
			" select decode(y.recruiter_teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			"   y.id activityid, nvl(y.dateupdated,sysdate) datedeleted " +
			" from " +
			"   (select rfqid, candidateid from tinterviewschedule " +
			"   where recruiter_teamid in (22,152,185,219) and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"   and trunc(dateupdated) > trunc(datecreated) " +
			"       union" +
			"   select rfqid, candidateid from tinterviewschedule_deleted " +
			"   where teamid in (22,152,185,219) and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) x, " +
			"   tinterviewschedule y " +
			" where x.rfqid = y.rfqid and x.candidateid = y.candidateid " +
			"   union" +
			" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			"   activityid, datedeleted " +
			" from tinterviewschedule_deleted " +
			" where teamid in (22,152,185,219) and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String NewCandidateActions(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select /*+ ordered use_nl(a b c d e f) index(a IDX_TCANDNOTES_REPORT_2) */ " +
			" decode(a.recruiter_teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, a.noteid, " +
			" decode(a.type,-1,'Termination',1,'Last Attempt',2,'Last Reached',3,'Last Meeting',4,'Attribute',5,'Qualification'," +
			" 6,'References Checked',7,'Incoming Call',8,'Outgoing Call',b.name) actiontype, " +
			" a.recruiterid userid, a.datecreated createdate, " +
			" a.candidateid, decode(a.rfqid,0,'',a.rfqid) jobid, decode(a.contactid,0,'',a.contactid) contactid, " +
			" case when note_clob is null then substr(note,1,100) else to_char(substr(note_clob,1,100)) end notefirst100chars " + 
			" from tcandidatenotes a, tactiontype_candidate b " +
			" where a.recruiter_teamid in (22,152,185,219) and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and (a.auto = 0 or a.auto = 3) and nvl(a.deleted,0) = 0 and a.type = b.id(+) and a.recruiter_teamid=b.teamid(+) ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String deletedCandidateActions(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(a.recruiter_teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, a.noteid, " +
			" a.recruiterid userid, a.datedeleted " +
			" from tcandidatenotes a, trecruiter b " +
			" where a.recruiter_teamid in (22,152,185,219) and a.datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and a.recruiterid = b.id";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String NewContactActions(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(a.recruiter_teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, a.noteid, " +
			" decode(a.type,1,'Last Attempt',2,'Last Reached',3,'Last Meeting',7,'Incoming Call',8,'Outgoing Call',b.name) actiontype, " +
			" a.recruiterid userid, a.datecreated createdate, datemodified updatedate, " +
			" a.customerid contactid, decode(a.rfqid,0,'',a.rfqid) jobid, decode(a.candidateid,0,'',a.candidateid) candidateid,  " +
			" substr(note,1,100) notefirst100chars " + 
			" from tcustomernotes a, tactiontype b " +
			" where a.recruiter_teamid in (22,152,185,219) and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and nvl(a.deleted,0) = 0 " +
			" and b.id(+) = a.type and b.teamid(+) = a.recruiter_teamid ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String updatedContactActions(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(a.recruiter_teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, a.noteid, " +
			" decode(a.type,1,'Last Attempt',2,'Last Reached',3,'Last Meeting',7,'Incoming Call',8,'Outgoing Call',b.name) actiontype, " +
			" a.recruiterid userid, a.datecreated createdate, a.datemodified updatedate, " +
			" a.customerid contactid, decode(a.rfqid,0,'',a.rfqid) jobid, decode(a.candidateid,0,'',a.candidateid) candidateid, " +
			" substr(note,1,100) notefirst100chars " + 
			" from tcustomernotes a, tactiontype b, tcustomer c, tcandidate e " +
			" where a.recruiter_teamid in (22,152,185,219) and a.datemodified between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and nvl(a.deleted,0) = 0 " +
			" and a.type = b.id(+) and a.recruiter_teamid=b.teamid(+) ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
		
	public static String deletedContactActions(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(a.recruiter_teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, a.noteid, " +
			" a.recruiterid userid, a.datedeleted " +
			" from tcustomernotes a, trecruiter b " +
			" where a.recruiter_teamid in (22,152,185,219) and a.datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and a.recruiterid = b.id";
		param.add(fromDate);
		param.add(toDate);
	
		return sql;
	}
	
	public static String newJobActions(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(b.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, a.rfqid jobid, a.noteid, " +
			" decode(a.type,1,'Email Merge',3,'Job Approval',5,'System Note','') actiontype, " +
			" a.recruiterid userid, a.datecreated createdate, substr(note,1,100) notefirst100chars " + 
			" from trfqnotes a, trfq b  " +
			" where a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and b.id = a.rfqid and b.teamid in (22,152,185,219) " +
			" and not exists (select 1 from trecruiter r where r.id=a.recruiterid and bitand(r.leader,4096) > 0) ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String deletedJobActions(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, a.rfqid jobid, a.noteid, " +
			"   a.recruiterid userid, datedeleted " + 
			" from trfqnotes_deleted a  " +
			" where a.datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and a.teamid in (22,152,185,219) ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String newCandidates(String[] params, String fromDate, String toDate, Vector<Object> param) {
		StringBuffer columnsToShow = new StringBuffer();
		if(params != null) {
			for(int i = 0; i < params.length; i++) {				
				columnsToShow.append(", (select " +
						" case when n.fieldtypeid = 3  " +
						" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd')||'T00:00:00.0' " +
						"      when n.fieldtypeid = 4 " +
						" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " +
						" else t.userfield_value end userfield_value " +
						" from tcandidate_userfields t, tuserfields n " +
					" where t.candidateid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"') \"" + params[i] + "\"");
			}
		}
		
		String sql =
			" select decode(x.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, x.*, " +
			"   (select s.name from tresumesourcename s where s.teamid=x.teamid and s.id=y.resumesource) resumesource, " +
			"   nvl(y.resumesourcerefname, (select displayname from tresumesourceref where id=y.resumesourcerefid)) referrer " +	
			" from " +
			"   (select a.id candidateid, a.teamid, a.firstname, a.lastname, datereceived datecreated, dateupdated_manual dateupdated, a.email, a.sysemail alternateemail, a.address1, a.address2," +
			"     a.city, a.state, a.zipcode, a.workphone, a.workphone_ext, a.homephone, a.cellphone, a.fax, " +
			"     case when b.dcatid=1 then 'Current Employee' " +
			"          when b.dcatid=2 then 'Past Employee' " +
			"          when b.dcatid=3 then 'Direct Placement' " +
			"          else '' end employeestatus, " +
			"     (select docid from tcandidatedocument_header " +
			"     where candidateid=a.id and teamid=a.teamid " +
			"       and nvl(daterefreshed, datecreated) = " +
			"         (select max(nvl(daterefreshed, datecreated)) from tcandidatedocument_header " +
			"         where candidateid= a.id and teamid = a.teamid and resumesource not in (998,999)) " +
			"       and rownum = 1) docid " + 
			columnsToShow.toString() + 
			"   from tcandidate a, tcandidate_category b " +
			"   where a.teamid in (22,152,185,219) and a.datereceived between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"     and b.candidateid(+)=a.id and b.teamid(+)=a.teamid and b.catid(+)=1) x, tcandidatedocument_header y " +
			" where y.candidateid(+) = x.candidateid and y.teamid(+)=x.teamid and y.docid(+) = x.docid";		
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String updatedCandidates(String[] params, String fromDate, String toDate, Vector<Object> param) {
		StringBuffer columnsToShow = new StringBuffer();
		if(params != null) {
			for(int i = 0; i < params.length; i++) {				
				columnsToShow.append(", (select " +
						" case when n.fieldtypeid = 3  " +
						" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd')||'T00:00:00.0' " +
						"      when n.fieldtypeid = 4 " +
						" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/1000/3600/24,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " +
						" else t.userfield_value end userfield_value " +
						" from tcandidate_userfields t, tuserfields n " +
					" where t.candidateid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='"+params[i]+"') \"" + params[i] + "\"");
			}
		}
		
		String sql = 
			" select decode(x.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, x.*, " +
			"   (select s.name from tresumesourcename s where s.teamid=x.teamid and s.id=y.resumesource) resumesource, " +
			"   nvl(y.resumesourcerefname, (select displayname from tresumesourceref where id=y.resumesourcerefid)) referrer " +	
			" from " +	
			"   (select a.teamid, a.id candidateid, a.firstname, a.lastname, datereceived datecreated, dateupdated_manual dateupdated, a.email, a.sysemail alternateemail, a.address1, a.address2," +
			"     a.city, a.state, a.zipcode, a.workphone, a.workphone_ext, a.homephone, a.cellphone, a.fax, " +
			"      (case when b.dcatid=1 then 'Current Employee' " +
			"           when b.dcatid=2 then 'Past Employee' " +
			"           when b.dcatid=3 then 'Direct Placement' " +
			"           else '' end) employeestatus, " +
			"       (select docid from tcandidatedocument_header " +
			"       where candidateid=a.id and teamid=a.teamid " +
			"         and nvl(daterefreshed, datecreated) = " +
			"           (select max(nvl(daterefreshed, datecreated)) from tcandidatedocument_header " +
			"           where candidateid= a.id and teamid = a.teamid and resumesource not in (998,999)) " +
			"         and rownum = 1) docid " + 
			columnsToShow.toString() + 
			"   from " +
			"     (select id candidateid, teamid from tcandidate " +
			"     where teamid in (22,152,185,219) and dateupdated_manual between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"         union " +
			"     select candidateid, teamid from tcandidate_category " +
			"     where teamid in (22,152,185,219) and catid=1 and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"         union " +
			"     select candidateid, teamid from tcurrentemployee_deleted " +
			"     where teamid in (22,152,185,219) and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')" +
			"         union " +
			"     select candidateid, teamid from tcandidatedocument_header " +
			"     where teamid in (22,152,185,219) and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) t, " +
			"     tcandidate a, tcandidate_category b " +
			"   where a.id = t.candidateid and a.teamid=t.teamid " +
			"     and b.candidateid(+)=a.id and b.teamid(+)=a.teamid and b.catid(+)=1) x, tcandidatedocument_header y " +
			" where y.candidateid(+) = x.candidateid and y.teamid(+)=x.teamid and y.docid(+) = x.docid ";				
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String deletedCandidates(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
				" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, candidateid, datedeleted " +
				" from tcandidate_deleted a " +
				" where a.teamid in (22,152,185,219) and a.datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";				
			param.add(fromDate);
			param.add(toDate);
			
			return sql;
	}
	
	public static String mergedCandidates(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
				" select distinct decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
				" candidateid_gone candidateid, candidateid_new mergedtocandidateid, replace(to_char(datecreated, 'yyyy-mm-dd hh24:mi:ss'), ' ', 'T')||'.0' datemerged " +
				" from tworkexp_stuff a " +
				" where a.teamid in (22,152,185,219) and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
				"   and candidateid_new is not null ";				
			param.add(fromDate);
			param.add(toDate);
			
			return sql;
	}
	
	public static String newEmployee(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" b.id candidateid, decode(a.dcatid,1,1,2,0,3,2) employeestatus, b.firstname candidatefirstname, b.lastname candidatelastname, " +
			" (select userfield_value from tcandidate_userfields x where x.candidateid=a.candidateid and x.teamid=a.teamid and x.userfield_id=27) candidatessn, " +
			" nvl((select decode(y.flag,2,to_date('31-Dec-2999'),4,dateavailable) from tcandidate_unreachable y where y.teamid=a.teamid and y.candidateid=a.candidateid)," +
			" (select decode(y.flag,2,to_date('31-Dec-2999'),4,dateavailable) from tcandidate_unreachable_archive y where y.teamid=a.teamid and y.candidateid=a.candidateid)) availabilitydate " +
			" from tcandidate_category a, tcandidate b " +
			" where a.teamid in (22,152,185,219) and a.catid=1 and a.dirty <> 2 " +
			" and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and a.candidateid = b.id and a.teamid = b.teamid ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String updatedEmployee(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" b.id candidateid, decode(a.dcatid,1,1,2,0,3,2) employeestatus, b.firstname candidatefirstname, b.lastname candidatelastname, " +
			" (select userfield_value from tcandidate_userfields x where x.candidateid=a.candidateid and x.teamid=a.teamid and x.userfield_id=27) candidatessn, " +
			" nvl((select decode(y.flag,2,to_date('31-Dec-2999'),4,dateavailable) from tcandidate_unreachable y where y.teamid=a.teamid and y.candidateid=a.candidateid)," +
			" (select decode(y.flag,2,to_date('31-Dec-2999'),4,dateavailable) from tcandidate_unreachable_archive y where y.teamid=a.teamid and y.candidateid=a.candidateid)) availabilitydate " +
			" from " +
			" (select a.teamid, a.id candidateid, b.dcatid from tcandidate a, tcandidate_category b where a.teamid in (22,152,185,219) " +
			" and a.dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and a.teamid=b.teamid and a.id=b.candidateid and b.catid=1 and b.dirty <> 2 " +
			" union" +
			" select a.teamid, a.candidateid, b.dcatid from tcandidate_userfields a, tcandidate_category b where a.teamid in (22,152,185,219) " +
			" and a.userfield_id=27 and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and a.teamid=b.teamid and a.candidateid=b.candidateid and b.catid=1 and b.dirty <> 2 " +
			" union " +
			" select a.teamid, a.candidateid, b.dcatid from tcandidate_unreachable a, tcandidate_category b where a.teamid in (22,152,185,219) " +
			" and a.date_created between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and a.teamid=b.teamid and a.candidateid=b.candidateid and b.catid=1 and b.dirty <> 2 " +
			" union " +
			" select a.teamid, a.candidateid, b.dcatid from tcandidate_unreachable_archive a, tcandidate_category b where a.teamid in (22,152,185,219) " +
			" and a.date_created between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and a.teamid=b.teamid and a.candidateid=b.candidateid and b.catid=1 and b.dirty <> 2 " +
			" ) a, tcandidate b " +
			" where a.candidateid = b.id and a.teamid = b.teamid ";
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
 	
	public static String deletedEmployee(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" a.candidateid, a.datedeleted " +
			" from tcurrentemployee_deleted a where a.teamid in (22,152,185,219) " +
			" and a.datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(fromDate);
		param.add(toDate);
		
		return sql;
	}
	
	public static String biDataCheck(String fromDate, String toDate, Vector<Object> param) {
		String sql =
			" select decode(job.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			"   issuedjobs, submittals, interviews, hires, candidateactions, contactactions, currentemployees, pastemployees " +
			" from " +
			"   (select teamid, count(*) issuedjobs from trfq " +
			"    where teamid in (22,152,185,219) and dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"    group by teamid) job, " +
			"   (select recruiter_teamid teamid, count(*) submittals from tinterviewschedule x " +
			"    where recruiter_teamid in (22,152,185,219) and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" 	   and id = (select min(id) from tinterviewschedule y where y.rfqid=x.rfqid and y.candidateid=x.candidateid and nvl(y.roleid,0) < 900)" +
			"    group by recruiter_teamid) sub, " +
			"   (select recruiter_teamid teamid, count(*) interviews from tinterviewschedule " +
			"    where recruiter_teamid in (22,152,185,219) and interviewscheduledate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"      and dateinterview is not null " +
			"    group by recruiter_teamid) int, " +
			"   (select recruiter_teamid teamid, count(*) hires from tinterviewschedule " +
			"    where recruiter_teamid in (22,152,185,219) and placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"    and datehired is not null " +
			"    group by recruiter_teamid) hr, " +
			"   (select recruiter_teamid teamid, count(*) candidateactions from tcandidatenotes " +
			"    where recruiter_teamid in (22,152,185,219) and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"    group by recruiter_teamid) candnote, " +
			"   (select recruiter_teamid teamid, count(*) contactactions from tcustomernotes " +
			"    where recruiter_teamid in (22,152,185,219) and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			"    group by recruiter_teamid) contnote, " +
			"   (select teamid, count(*) currentemployees from tcandidate_category " +
			"    where teamid in (22,152,185,219) and catid=1 and dcatid=1 and dirty<>2 " +
			"    group by teamid) curemp, " +
			"   (select teamid, count(*) pastemployees from tcandidate_category " +
			"    where teamid in (22,152,185,219) and catid=1 and dcatid=2 and dirty<>2 " +
			"    group by teamid) pstemp " +
			" where sub.teamid(+) = job.teamid and int.teamid(+)=job.teamid and hr.teamid(+)=job.teamid and candnote.teamid(+)=job.teamid " +
			"   and contnote.teamid(+)=job.teamid and curemp.teamid(+)=job.teamid and pstemp.teamid(+)=job.teamid";
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
		
		return sql;
	}
	
	public static String salesPipeline(String[] params) {
		String filter = "x.typename in ('Opportunity','Prospect')";
		if(params != null && params.length > 0 && params[0].toLowerCase().indexOf("opportunity") > -1) {
			filter = "x.typename = 'Opportunity'";
		}
		
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" a.id companyid, a.name companyname, b.city, b.state, b.url, " +
			" ( select decode(a.supplier, 1, 'Supplier,', '')||max(decode(rownum,1,p.typename||','))||max(decode(rownum,2,p.typename||','))||max(decode(rownum,3,p.typename||','))|| " +
			" max(decode(rownum,4,p.typename||','))||max(decode(rownum,5,p.typename||','))||max(decode(rownum,6,p.typename||','))||max(decode(rownum,7,p.typename||','))|| " +
	        " max(decode(rownum,8,p.typename||','))||max(decode(rownum,9,p.typename||','))||max(decode(rownum,10,p.typename||',')) " +
	        " from tcustomer_company_type t, tcustomer_company_types p where t.companyid = a.id and t.typeid = p.id and p.teamid = a.teamid) companytype, " +
			" d.id primaryownerid, d.firstname||' '||d.lastname primaryowner, e.name division, f.name costcenter, " +
			" (select max(decode(rownum,1,z.firstname||' '||z.lastname,''))||max(decode(rownum,2,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,3,','||z.firstname||' '||z.lastname,''))|| " +
	        " max(decode(rownum,4,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,5,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,6,','||z.firstname||' '||z.lastname,''))|| " +
	        " max(decode(rownum,7,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,8,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,9,','||z.firstname||' '||z.lastname,''))|| " +
	        " max(decode(rownum,10,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,11,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,12,','||z.firstname||' '||z.lastname,''))|| " +
	        " max(decode(rownum,13,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,14,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,15,','||z.firstname||' '||z.lastname,''))|| " +
	        " max(decode(rownum,16,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,17,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,18,','||z.firstname||' '||z.lastname,''))|| " +
	        " max(decode(rownum,19,','||z.firstname||' '||z.lastname,''))||max(decode(rownum,20,','||z.firstname||' '||z.lastname,'')) " +
	        " from tcustomer z where a.id = z.companyid and z.isprimarycontact = 1) primarycontacts, " +
	        " (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Opportunity Type') \"OpportunityType\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Primary Product Line') \"PrimaryProductLine\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Total Annual Contract Labor Spend') \"TotalAnnualContractLaborSpend\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Estimated Annual Revenue') \"EstimatedAnnualRevenue\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Estimated GP%') \"EstimatedGP%\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Estimated Markup%') \"EstimatedMarkup%\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Length of Contract (years)') \"LengthOfContract(years)\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Opportunity Stage') \"OpportunityStage\", " +
			" (select to_date('1970-01-01','yyyy-mm-dd')+t.userfield_value/(24*60*60*1000) from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Anticipated Stage Close Date') \"AnticipatedCloseDate\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Action  Required for Stage') \"ActionRequired\", " +
			" (select to_date('1970-01-01','yyyy-mm-dd')+t.userfield_value/(24*60*60*1000) from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Date of Last Pipeline Update') \"DateOfLastUpdate\", " +
			" (select to_date('1970-01-01','yyyy-mm-dd')+t.userfield_value/(24*60*60*1000) from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Expected Close Date') \"ExpectedCloseDate\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Estimated Probability of Closure') \"EstimatedProbabilityOfClosure\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Sales Pipeline Comments') \"Comments\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='DUNS#') \"DUNS#\", " +
			" (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Customer Number') \"CustomerNumber\" " +
			" from tcustomercompany a, tcustomercompanyaddresses b, tcustomer_company_owners c, trecruiter d, tdivision e, tcostcenter f " +
			" where a.id in (select companyid from tcustomer_company_types x, tcustomer_company_type y " +
			" where x.teamid in (22,152,185,219) and " + filter + " and x.teamid = y.teamid and x.id = y.typeid) " +
			" and a.id = b.companyid and b.default_address = 1 and a.id = c.companyid(+) and 1 = c.isprimaryowner(+) " +
			" and c.recruiterid = d.id(+) and d.division = e.id(+) and d.costcenter = f.id(+) and d.groupid = f.teamid(+) ";
		
		return sql;
	}
	
	public static String usersList() {
		String sql =
			" select decode(a.groupid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" a.id userid, province employeeid, dateentered datecreated, lastlogin, a.firstname, a.lastname, a.email, a.active activeflag, b.name division, c.name costcenter, d.name branch, " +
			" a.title, a.state, decode(bitand(leader,1024),1024,1,0) recruiter, decode(bitand(leader,64),64,1,0) recruitingmanager, " +
			" decode(bitand(leader,512),512,1,0) sales, decode(bitand(leader,32),32,1,0) hiringmanager, " +
			" decode(bitand(leader,16),16,1,0) teamleader, decode(bitand(leader,256),256,1,0) superuser, " +
			" decode(bitand(leader,2048),2048,1,0) client " +
			" from trecruiter a, tdivision b, tcostcenter c, tuserbranch d " +
			" where a.groupid in (22,152,185,219) and bitand(a.leader,16384) = 0 and bitand(a.leader,4096) = 0 and a.leader < 32768 and a.id <> 688 " +
			" and a.division = b.id(+) and a.costcenter = c.id(+) and a.groupid = c.teamid(+) " +
			" and a.userbranch = d.id(+) and a.groupid = d.teamid(+) ";
		
		return sql;
	}
	
	public static String userGroupLists() {
		String sql = 
			" select decode(a.teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			" b.column_value userid, c.firstname||' '||c.lastname username, a.name grouplistname " +
			" from tgrouplist a, table(sf_str2tbl(a.RECRUITERIDS)) b, trecruiter c " +
			" where a.teamid in (22,152,185,219) and b.column_value = c.id ";
		
		return sql;
	}
	
	public static String newCandidateReferenceCheck(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(recruiter_teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			"   candidateid, customerid contactid, recruiterid userid, datecreated, datechecked, " +
			"   substr(note,1,100) notefirst100chars " +
			" from tcandidatereferences " +
			" where recruiter_teamid in (22,152,185,219) and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String updatedCandidateReferenceCheck(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(recruiter_teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			"   candidateid, customerid contactid, recruiterid userid, datecreated, datechecked, " +
			"   substr(note,1,100) notefirst100chars " +
			" from tcandidatereferences " +
			" where recruiter_teamid in (22,152,185,219) and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String deletedCandidateReferenceCheck(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			"   candidateid, customerid contactid, datecreated, datedeleted " +
			" from tcandidatereferences_deleted " +
			" where teamid in (22,152,185,219) and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newAif(String fromDate, String toDate, Vector<Object> param) {
		String sql =
			" select opco, activityid, activitytype, " +
			"   mailingaddress1, mailingaddress2, mailingaddresscity, mailingaddressstate, mailingaddresszip, " +
			"   compensationtype, newcustomer, overtimepay, overtimebill, doubletimepay, doubletimebill, holidaypay, holidaybill, perdiempay, perdiembill, " +
			"   otonholidayweek, perdiemfrequency, hourscollectiontype, hoursincrements, timeapprovername, approverphone, approveremail, " +
			"   accountpayableemail, comments, " +
			"   decode(new_fieldglasscustomer,'1','Yes','2','No','') fieldglasscustomer, " +
			"   new_holidayschedule holidayschedule, decode(isunitonly, 1, 'Yes','No') unitpaybillonly, " +
			"   coalesce(customernumber_job, customernumber_comp) customernumber, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingattn_comp else billingattn_job end billingattn, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingaddr1_comp else billingaddr1_job end billingaddr1, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingaddr2_comp else billingaddr2_job end billingaddr2, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingcity_comp else billingcity_job end billingcity, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingstate_comp else billingstate_job end billingstate, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingzip_comp else billingzip_job end billingzip, " +
			"   case when customerpo_start is null then customerpo_job else customerpo_start end customerpo " +
			" from " +	
			" (select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			"   interviewid activityid, decode(pinktype, 0, '', 1, 'New Hire', 2, 'Re-Hire', 3, 'Change', 4, '') activitytype, " +
			"   mailaddress1 mailingaddress1, mailaddress2 mailingaddress2, mailcity mailingaddresscity, mailstate mailingaddressstate, mailzipcode mailingaddresszip, " +
			"   compensationtype, newcustomer, " +
			"   payovertime overtimepay, billovertime overtimebill, " +
			"   paydoubletime doubletimepay, billdoubletime doubletimebill, " +
			"   payholiday holidaypay, billholiday holidaybill, " +
			"   payperdiem perdiempay, billperdiem perdiembill, " +
			"   otcalculation otonholidayweek, perdiem perdiemfrequency, hourscollection hourscollectiontype, hoursincrements, " +
			"   new_fasttimeapprovername timeapprovername, new_fasttimeapproverphone approverphone, new_fasttimeapproveremaiol approveremail, " +
			"   fasttimeapemail accountpayableemail, comments, new_fieldglasscustomer, new_holidayschedule, isunitonly, " +
			"   (select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Customer Number') customernumber_job, " +
			"   (select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Attention To') billingattn_job, " +
			"	(select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Address 1') billingaddr1_job, " +
			"   (select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Address 2') billingaddr2_job, " +
			"	(select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Biling City') billingcity_job, " +
			"   (select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing State') billingstate_job, " +
			"	(select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Zip') billingzip_job, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Customer Number') customernumber_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Attention To') billingattn_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Address 1') billingaddr1_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Address 2') billingaddr2_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Biling City') billingcity_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing State') billingstate_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Zip') billingzip_comp, " +
			"   (select t.userfield_value from tstartrecord_userfields t, tuserfields n " +
			" 	 where t.startid=a.interviewid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Customer PO') customerpo_start, " +
			"   (select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Customer PO') customerpo_job " +
			" from tessential_newpink a " +
			" where teamid=22 and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) ";
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String updatedAif(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select opco, activityid, activitytype, " +
			"   mailingaddress1, mailingaddress2, mailingaddresscity, mailingaddressstate, mailingaddresszip, " +
			"   compensationtype, newcustomer, overtimepay, overtimebill, doubletimepay, doubletimebill, holidaypay, holidaybill, perdiempay, perdiembill, " +
			"   otonholidayweek, perdiemfrequency, hourscollectiontype, hoursincrements, timeapprovername, approverphone, approveremail, " +
			"   accountpayableemail, comments, " +
			"   decode(new_fieldglasscustomer,'1','Yes','2','No','') fieldglasscustomer, " +
			"   new_holidayschedule holidayschedule, decode(isunitonly, 1, 'Yes','No') unitpaybillonly, " +
			"   coalesce(customernumber_job, customernumber_comp) customernumber, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingattn_comp else billingattn_job end billingattn, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingaddr1_comp else billingaddr1_job end billingaddr1, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingaddr2_comp else billingaddr2_job end billingaddr2, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingcity_comp else billingcity_job end billingcity, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingstate_comp else billingstate_job end billingstate, " +
			"   case when billingattn_job is null and billingaddr1_job is null and billingaddr2_job is null and billingcity_job is null and billingstate_job is null and billingzip_job is null then billingzip_comp else billingzip_job end billingzip, " +
			"   case when customerpo_start is null then customerpo_job else customerpo_start end customerpo " +
			" from " +	
			" (select decode(teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, " +
			"   interviewid activityid, decode(pinktype, 0, '', 1, 'New Hire', 2, 'Re-Hire', 3, 'Change', 4, '') activitytype, " +
			"   mailaddress1 mailingaddress1, mailaddress2 mailingaddress2, mailcity mailingaddresscity, mailstate mailingaddressstate, mailzipcode mailingaddresszip, " +
			"   compensationtype, newcustomer, " +
			"   payovertime overtimepay, billovertime overtimebill, " +
			"   paydoubletime doubletimepay, billdoubletime doubletimebill, " +
			"   payholiday holidaypay, billholiday holidaybill, " +
			"   payperdiem perdiempay, billperdiem perdiembill, " +
			"   otcalculation otonholidayweek, perdiem perdiemfrequency, hourscollection hourscollectiontype, hoursincrements, " +
			"   new_fasttimeapprovername timeapprovername, new_fasttimeapproverphone approverphone, new_fasttimeapproveremaiol approveremail, " +
			"   fasttimeapemail accountpayableemail, comments, new_fieldglasscustomer, new_holidayschedule, isunitonly, " +
			"   (select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Customer Number') customernumber_job, " +
			"   (select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Attention To') billingattn_job, " +
			"	(select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Address 1') billingaddr1_job, " +
			"   (select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Address 2') billingaddr2_job, " +
			"	(select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Biling City') billingcity_job, " +
			"   (select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing State') billingstate_job, " +
			"	(select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Zip') billingzip_job, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Customer Number') customernumber_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Attention To') billingattn_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Address 1') billingaddr1_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Address 2') billingaddr2_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Biling City') billingcity_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing State') billingstate_comp, " +
			"   (select t.userfield_value from tcompany_userfields t, tuserfields n " +
			" 	 where t.companyid=a.companyid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Billing Zip') billingzip_comp, " +
			"   (select t.userfield_value from tstartrecord_userfields t, tuserfields n " +
			" 	 where t.startid=a.interviewid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Customer PO') customerpo_start, " +
			"   (select t.userfield_value from trfq_userfields t, tuserfields n " +
			" 	 where t.rfqid=a.rfqid and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='Customer PO') customerpo_job " +
			" from tessential_newpink a " +
					" where teamid=22 and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) ";
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String deletedAif(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select decode(recruiter_teamid,22,'ATR US',152,'STF',185,'PRO',219,'XPO US') opco, id activityid " +
			" from tinterviewschedule where recruiter_teamid=22 and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
			" and placementdate is not null and datehired is null";
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String accessLog(String fromDate, String toDate, Vector<Object> param) {
		String sql = 
			" select case when x.teamid=22 then 'ATR US' " +
			"             when x.teamid=152 then 'STF' " +
			"             when x.teamid=185 then 'PRO' " +
			"             when x.teamid=219 then 'XPO US' " +
			"             else '' end opco,  " +
			"   x.userid, case when x.actioncode = -1 then 'No of Candidates Emailed' when x.actioncode=-2 then 'No of Contacts Emailed' else y.description end activitytype, " +
			"   case when x.actioncode=-1 or x.actioncode=-2 then emailcount else x.activitycount end activitycount " +
			" from (select teamid, id userid, actioncode, count(*) activitycount, case when actioncode in (-1,-2) then sum(criteria*1) else 0 end emailcount " +
			"       from taccesslog " +
			"       where teamid in (22,152,185,219) " +
			"         and datecreated between str_to_date(?, '%m/%d/%Y %H:%i:%s') and str_to_date(?, '%m/%d/%Y %H:%i:%s') " +
			"         and id > 0 and id <> 688 " +
			"       group by id, actioncode) x " +
			"   left join taccesslogcodes y " +
			" on y.id = x.actioncode";
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
}

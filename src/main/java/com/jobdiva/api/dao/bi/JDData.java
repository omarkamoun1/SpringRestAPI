package com.jobdiva.api.dao.bi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.jobdiva.api.utils.StringUtils;

public class JDData {
	// record detail
	
	public static String CompanyDetail(Long clientID, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 1; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3 " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from tcompany_userfields t, tuserfields n "
						+ " where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1)  ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = "select id, name companyname, dateentered, a.RECRUITERID USERID, editdate dateupdated, userfield_updatedate dateuserfieldupdated, parent_companyid parentcompanyid, parent_company_name parentcompanyname, " + //
				"  b.address1, b.address2, b.city, b.state, b.zipcode, b.phone, b.fax, b.email, b.url, " + //
				"  discount, decode(discount_type,0,'$/Hour',1,'$/Day',2,'$/Month',3,'$/Year','') discounttype, discountpct, " + //
				"  decode(invoice_contact_preference,1,'Email',2,'Do Not Send','Address') show_on_invoice, " + //
				"  decode(entryformat,1,'Minute',2,'Quarter',3,'Time In/Time Out',4,'Default - When Billing Unit is Daily','Default') timesheet_entryformat, " + //
				"  decode(financial_frequency,1,'Bi weekly',2,'Monthly',3,'Semi monthly',4,'Weekly',5,'Whole project',6,'Monthly ending weekend',' ') frequency, " + //
				"  decode(financial_weekending,1,'Sunday',2,'Saturday',3,'Friday',4,'Thursday',5,'Wednesday',6,'Tuesday',7,'Monday',' ') weekending, " + //
				"  financial_hours hours, decode(financial_billingunit,1,'Hourly',2,'Daily: based on hours',3,'Daily: half-day',4,'Daily: half-day (OT)',5,'Daily: bill whole day',6,'Daily: bill whole day (OT)',' ') billingunit, " + //
				"  decode(financial_invoice_group_index,1,'Job',2,'Company',3,'P.O. #',4,'Employee',' ') invoice_group, " + //
				"  paymentterms, financial_invoicecomment invoicecomment, financial_standard_ot standard_ot_ratio, financial_vmswebsite vmswebsite, " + //
				"  decode(financial_timesheetonportal,1,'Yes','No') timesheetonportal, " + //
				"  decode(financial_grouping_expenses,1,'Include Related Expenses with Timesheet Invoices','Generate Invoices from Timesheets Only') invoicecontent, " + //
				"  decode(financial_expensecategorytotal,1,'Display Only Category Totals','Display Expense Details') expensecategorytotal," + //
				"  submittal_instr submittal_guidelines, a.divisionid, " + //
				"  (SELECT p.name from tsalespipeline p where p.teamid = a.teamid and p.id = a.pipeline_id) sales_pipeline, " + //
				"  (SELECT LISTAGG(INDUSTRY, ',') within group ( order by industryid) as industry " + //
				"    from tcustomercompanyindustries cci join tindustry i on cci.industryid = i.id " + //
				"    where cci.teamid = a.teamid and cci.companyid = a.id) industry " + //
				//
				columnsToShow.toString() + " from tcustomercompany a, tcustomercompanyaddresses b where a.id=? and a.teamid=? " + " and b.companyid=a.id and b.default_address=1 ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String CompaniesDetail(Long clientID, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		int noRec = 1;
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 1; i < params.length; i++) {
				try {
					Long.parseLong(params[i]);
					noRec++;
				} catch (Exception e) {
					noRec = i;
					break;
				}
			}
			for (int i = noRec; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3 " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from tcompany_userfields t, tuserfields n "
						+ " where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1) ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = "select id, name companyname, dateentered, editdate dateupdated, userfield_updatedate dateuserfieldupdated, parent_companyid parentcompanyid, parent_company_name parentcompanyname, "
				+ "  b.address1, b.address2, b.city, b.state, b.zipcode, b.phone, b.fax, b.email, b.url, " + "  discount, decode(discount_type,0,'$/Hour',1,'$/Day',2,'$/Month',3,'$/Year','') discounttype, discountpct, "
				+ "  decode(invoice_contact_preference,1,'Email',2,'Do Not Send','Address') show_on_invoice, "
				+ "  decode(entryformat,1,'Minute',2,'Quarter',3,'Time In/Time Out',4,'Default - When Billing Unit is Daily','Default') timesheet_entryformat, "
				+ "  decode(financial_frequency,1,'Bi weekly',2,'Monthly',3,'Semi monthly',4,'Weekly',5,'Whole project',6,'Monthly ending weekend',' ') frequency, "
				+ "  decode(financial_weekending,1,'Sunday',2,'Saturday',3,'Friday',4,'Thursday',5,'Wednesday',6,'Tuesday',7,'Monday',' ') weekending, "
				+ "  financial_hours hours, decode(financial_billingunit,1,'Hourly',2,'Daily: based on hours',3,'Daily: half-day',4,'Daily: half-day (OT)',5,'Daily: bill whole day',6,'Daily: bill whole day (OT)',' ') billingunit, "
				+ "  decode(financial_invoice_group_index,1,'Job',2,'Company',3,'P.O. #',4,'Employee',' ') invoice_group, "
				+ "  paymentterms, financial_invoicecomment invoicecomment, financial_standard_ot standard_ot_ratio, financial_vmswebsite vmswebsite, " + "  decode(financial_timesheetonportal,1,'Yes','No') timesheetonportal, "
				+ "  decode(financial_grouping_expenses,1,'Include Related Expenses with Timesheet Invoices','Generate Invoices from Timesheets Only') invoicecontent, "
				+ "  decode(financial_expensecategorytotal,1,'Display Only Category Totals','Display Expense Details') expensecategorytotal, " + "  submittal_instr submittal_guidelines " + columnsToShow.toString()
				+ " from tcustomercompany a, tcustomercompanyaddresses b " + "where a.id in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? " + " and b.companyid=a.id and b.default_address=1 ";
		StringBuilder companies = new StringBuilder();
		for (int i = 0; i < noRec; i++) {
			if (i > 0)
				companies.append(",");
			companies.append(params[i]);
		}
		param.add(companies.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String CompanyOwnersDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.recruiterid userid, b.firstname, b.lastname, isprimaryowner " + " from tcustomer_company_owners a, trecruiter b " + " where a.companyid=? and a.teamid=? and b.id=a.recruiterid ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String CompaniesOwnersDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.companyid, a.recruiterid userid, b.firstname, b.lastname, isprimaryowner " + " from tcustomer_company_owners a, trecruiter b "
				+ " where a.companyid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? and b.id=a.recruiterid ";
		StringBuilder companies = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				companies.append(",");
			companies.append(params[i]);
		}
		param.add(companies.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String CompaniesTypesDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.companyid, b.typename " + " from tcustomer_company_type a, tcustomer_company_types b " + " where a.companyid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? "
				+ "   and b.id=a.typeid and b.teamid=a.teamid and b.deleted = 0 ";
		StringBuilder companies = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				companies.append(",");
			companies.append(params[i]);
		}
		param.add(companies.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String CompanyAddressesDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select companyid, addressid, default_address, address1, address2, city, state, zipcode, countryid, phone, fax, url, email " + " from tcustomercompanyaddresses a " + " where a.companyid=? and a.teamid=? "
				+ " order by addressid ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String ContactDetail(Long clientID, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 1; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3 " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from tcustomer_userfields t, tuserfields n "
						+ " where t.customerid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1) ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = "select a.id, firstname, lastname, companyid, companyname, departmentname, title, active, isprimarycontact primary, timeentered datecreated, a.RECRUITERID USERID, editdate dateupdated, userfield_updatedate dateuserfieldupdated,"
				+ //
				"  email, workphone, workphoneext, homephone, cellphone, contactfax fax, " + //
				"  b.address1, b.address2, b.city, b.state, b.zipcode, b.countryid country " + //
				columnsToShow.toString() + " from tcustomer a, tcustomeraddress b where a.id=? and a.teamid=? " + //
				"  and b.contactid=a.id and b.default_address=1 and nvl(b.deleted,0) <> 1 ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String V2ContactDetail(Long clientID, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 1; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from tcustomer_userfields t, tuserfields n "
						+ " where t.customerid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1) ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = "select a.id, firstname, lastname, companyid, companyname, a.RECRUITERID USERID, departmentname, title, active, " + //
				"  email, workphone phone1, workphoneext phone1_extension, case when phonetypes is null or trim(phonetypes) is null then 'Work Phone' else decode(substr(phonetypes,1,1)," + //
				"    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Work Phone') end phone1_type, " + //
				"  cellphone phone2, cellphoneext phone2_extension, case when phonetypes is null or trim(phonetypes) is null then 'Mobile Phone' else decode(substr(phonetypes,2,1), " + //
				"    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Mobile Phone') end phone2_type, " + //
				"  homephone phone3, homephoneext phone3_extension, case when phonetypes is null or trim(phonetypes) is null then 'Home Phone' else decode(substr(phonetypes,3,1), " + //
				"    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Home Phone') end phone3_type, " + //
				"  contactfax phone4, contactfaxext phone4_extension, case when phonetypes is null or trim(phonetypes) is null then 'Home Fax' else decode(substr(phonetypes,4,1), " + //
				"    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Home Fax') end phone4_type, " + //
				"  b.address1, b.address2, b.city, b.state, b.zipcode " + //
				columnsToShow.toString() + " from tcustomer a, tcustomeraddress b where a.id=? and a.teamid=? " + //
				"  and b.contactid=a.id and b.default_address=1 and nvl(b.deleted,0) <> 1 ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String ContactsDetail(Long clientID, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		int noRec = 1;
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 1; i < params.length; i++) {
				try {
					Long.parseLong(params[i]);
					noRec++;
				} catch (Exception e) {
					noRec = i;
					break;
				}
			}
			for (int i = noRec; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from tcustomer_userfields t, tuserfields n "
						+ " where t.customerid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1) ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = "select a.id, firstname, lastname, companyid, companyname, departmentname, title, active, isprimarycontact primary, timeentered datecreated, a.RECRUITERID USERID, editdate dateupdated, userfield_updatedate dateuserfieldupdated,"
				+ //
				"  email, workphone phone1, workphoneext phone1_extension, case when phonetypes is null or trim(phonetypes) is null then 'Work Phone' else decode(substr(phonetypes,1,1)," + //
				"    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Work Phone') end phone1_type, " + //
				"  cellphone phone2, cellphoneext phone2_extension, case when phonetypes is null or trim(phonetypes) is null then 'Mobile Phone' else decode(substr(phonetypes,2,1)," + //
				"    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Mobile Phone') end phone2_type, " + //
				"  homephone phone3, homephoneext phone3_extension, case when phonetypes is null or trim(phonetypes) is null then 'Home Phone' else decode(substr(phonetypes,3,1)," + //
				"    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Home Phone') end phone3_type, " + //
				"  contactfax phone4, contactfaxext phone4_extension, case when phonetypes is null or trim(phonetypes) is null then 'Home Fax' else decode(substr(phonetypes,4,1)," + //
				"    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Home Fax') end phone4_type, " + //
				"  b.address1, b.address2, b.city, b.state, b.zipcode, b.countryid " + //
				columnsToShow.toString() + " from tcustomer a, tcustomeraddress b " + //
				"where a.id in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? " + //
				"  and b.contactid=a.id and b.default_address=1 and nvl(b.deleted,0) <> 1 ";
		StringBuilder contacts = new StringBuilder();
		for (int i = 0; i < noRec; i++) {
			if (i > 0)
				contacts.append(",");
			contacts.append(params[i]);
		}
		param.add(contacts.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String V2ContactsDetail(Long clientID, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			List<Object> list = new ArrayList<Object>();
			for (int i = 1; i < params.length; i++) {
				if (!list.contains(params[i])) {
					list.add(params[i]);
					columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
							+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value "
							+ " from tcustomer_userfields t, tuserfields n " + " where t.customerid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i])
							+ "' AND (ROWNUM = 1) ) " + "\"" + (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
				}
			}
		}
		String sql = "select a.id, firstname, lastname, companyid, companyname, a.RECRUITERID USERID, departmentname, title, active, "
				+ "  email, workphone phone1, workphoneext phone1_extension, case when phonetypes is null or trim(phonetypes) is null then 'Work Phone' else decode(substr(phonetypes,1,1),"
				+ "    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Work Phone') end phone1_type, "
				+ "  cellphone phone2, cellphoneext phone2_extension, case when phonetypes is null or trim(phonetypes) is null then 'Mobile Phone' else decode(substr(phonetypes,2,1),"
				+ "    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Mobile Phone') end phone2_type, "
				+ "  homephone phone3, homephoneext phone3_extension, case when phonetypes is null or trim(phonetypes) is null then 'Home Phone' else decode(substr(phonetypes,3,1),"
				+ "    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Home Phone') end phone3_type, "
				+ "  contactfax phone4, contactfaxext phone4_extension, case when phonetypes is null or trim(phonetypes) is null then 'Home Fax' else decode(substr(phonetypes,4,1),"
				+ "    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Home Fax') end phone4_type, "
				+ "  b.address1, b.address2, b.city, b.state, b.zipcode " + columnsToShow.toString() + " from tcustomer a, tcustomeraddress b "
				+ "where a.id in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? " + "  and b.contactid=a.id and b.default_address=1 and nvl(b.deleted,0) <> 1 ";
		StringBuilder contacts = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			try {
				Long.parseLong(params[i]);
				if (i > 0)
					contacts.append(",");
				contacts.append(params[i]);
			} catch (NumberFormatException nfe) {
				break;
			}
		}
		param.add(contacts.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String CandidateDetail(Long clientID, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			List<Object> list = new ArrayList<Object>();
			for (int i = 1; i < params.length; i++) {
				if (!list.contains(params[i])) {
					list.add(params[i]);
					columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
							+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value "
							+ " from tcandidate_userfields t, tuserfields n " + " where t.candidateid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i])
							+ "' AND (ROWNUM = 1) ) " + "\"" + (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\""); //
				}
			}
		}
		String sql = "select id, decode(bitand(nvl(privacyapplied,0), 1), 0, firstname, b.text) firstname, decode(bitand(nvl(privacyapplied,0), 1), 0, lastname, b.text) lastname," + //
				" decode(bitand(nvl(privacyapplied,0), 2), 0, address1, b.text) address1, decode(bitand(nvl(privacyapplied,0), 2), 0, address2, b.text) address2," + //
				" decode(bitand(nvl(privacyapplied,0), 4), 0, city, b.text) city, decode(bitand(nvl(privacyapplied,0), 4), 0, state, b.text) state, decode(bitand(nvl(privacyapplied,0), 4), 0, zipcode, b.text) zipcode, " + //
				" decode(bitand(nvl(privacyapplied,0), 4), 0, countryid, b.text) country, " + //
				" decode(bitand(nvl(privacyapplied,0), 8), 0, workphone, b.text) workphone, decode(bitand(nvl(privacyapplied,0), 8), 0, workphone_ext, b.text) workphone_ext, " + //
				" decode(bitand(nvl(privacyapplied,0), 8), 0, homephone, b.text) homephone, " + //
				" decode(bitand(nvl(privacyapplied,0), 8), 0, cellphone, b.text) cellphone, decode(bitand(nvl(privacyapplied,0), 8), 0, fax, b.text) fax, " + //
				" decode(bitand(nvl(privacyapplied,0), 16), 0, email, b.text) email, decode(bitand(nvl(privacyapplied,0), 16), 0, sysemail, b.text) alternateemail, dateupdated, userfield_updatedate dateuserfieldupdated, dateupdated_manual dateprofileupdated, currentsalary, decode(currentsalaryper,'','','h','Hour','d','Day','y','Year', "
				+ //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=a.currentsalaryper and y.ratetype=0)) currentsalaryper, " + //
				"  preferredsalarymin, decode(preferredsalaryper,'','','h','Hour','d','Day','y','Year', " + //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=a.preferredsalaryper and y.ratetype=0)) preferredsalaryper," + //
				"  case when bitand(nvl(privacyapplied,0), 32) = 0 then (select count(*) from tcandidatedocument_header d where d.candidateid=a.id and d.teamid=a.teamid) else 0 end resumecount " + //
				columnsToShow.toString() + " from tcandidate a, (select text from (select text from gdpr_inaccessibility_text where teamid = ? or teamid = 0 order by teamid desc) where rownum = 1) b where a.id=? and a.teamid=? ";
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String CandidatesDetail(Long clientID, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		int noRec = 1;
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 1; i < params.length; i++) {
				try {
					Long.parseLong(params[i]);
					noRec++;
				} catch (Exception e) {
					noRec = i;
					break;
				}
			}
			List<Object> list = new ArrayList<Object>();
			for (int i = noRec; i < params.length; i++) {
				if (!list.contains(params[i])) {
					list.add(params[i]);
					columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
							+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value "
							+ " from tcandidate_userfields t, tuserfields n " + " where t.candidateid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i])
							+ "' AND (ROWNUM = 1) ) " + "\"" + (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
				}
			}
		}
		String sql = "select id, decode(bitand(nvl(privacyapplied,0), 1), 0, firstname, b.text) firstname, decode(bitand(nvl(privacyapplied,0), 1), 0, lastname, b.text) lastname," + //
				" decode(bitand(nvl(privacyapplied,0), 2), 0, address1, b.text) address1, decode(bitand(nvl(privacyapplied,0), 2), 0, address2, b.text) address2," + //
				" decode(bitand(nvl(privacyapplied,0), 4), 0, city, b.text) city, decode(bitand(nvl(privacyapplied,0), 4), 0, state, b.text) state, decode(bitand(nvl(privacyapplied,0), 4), 0, zipcode, b.text) zipcode, " + //
				" decode(bitand(nvl(privacyapplied,0), 4), 0, countryid, b.text) country, " + //
				" decode(bitand(nvl(privacyapplied,0), 8), 0, workphone, b.text) workphone, decode(bitand(nvl(privacyapplied,0), 8), 0, workphone_ext, b.text) workphone_ext, " + //
				" decode(bitand(nvl(privacyapplied,0), 8), 0, homephone, b.text) homephone, " + //
				" decode(bitand(nvl(privacyapplied,0), 8), 0, cellphone, b.text) cellphone, decode(bitand(nvl(privacyapplied,0), 8), 0, fax, b.text) fax, " + //
				" decode(bitand(nvl(privacyapplied,0), 16), 0, email, b.text) email, decode(bitand(nvl(privacyapplied,0), 16), 0, sysemail, b.text) alternateemail, " + //
				" dateupdated, userfield_updatedate dateuserfieldupdated, dateupdated_manual dateprofileupdated, currentsalary, decode(currentsalaryper,'','','h','Hour','d','Day','y','Year', " + //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=a.currentsalaryper and y.ratetype=0)) currentsalaryper, " + //
				"  preferredsalarymin, decode(preferredsalaryper,'','','h','Hour','d','Day','y','Year', " + //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=a.preferredsalaryper and y.ratetype=0)) preferredsalaryper," + //
				"  case when bitand(nvl(privacyapplied,0), 32) = 0 then (select count(*) from tcandidatedocument_header d where d.candidateid=a.id and d.teamid=a.teamid) else 0 end resumecount " + //
				columnsToShow.toString() + " from tcandidate a, (select text from (select text from gdpr_inaccessibility_text where teamid = ? or teamid = 0 order by teamid desc) where rownum = 1) b " + //
				" where a.id in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? ";
		StringBuilder candidates = new StringBuilder();
		param.add(clientID);
		for (int i = 0; i < noRec; i++) {
			if (i > 0)
				candidates.append(",");
			candidates.append(params[i]);
		}
		param.add(candidates.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String CandidateQualDetail(Long clientID, String[] params, Vector<Object> param) {
		//
		String sql = "select a.candidateid, b.catname qualname, c.dcatname qualvalue, a.datecreated dateentered " //
				+ " from tcandidate_category a, tcategories b, tcategories_detail c " //
				+ " where a.candidateid=? and a.teamid=? and a.dirty<>2 " //
				+ "   and b.teamid=a.teamid and b.catid=a.catid " //
				+ "   and c.teamid=a.teamid and c.catid=a.catid and c.dcatid=a.dcatid " //
				+ "   and (";
		//
		for (int i = 1; i < params.length; i++) {
			if (i > 1)
				sql += " or ";
			sql += "b.catname=?";
		}
		//
		if (params.length == 1)
			sql += "1=1";
		sql += ") order by qualname";
		//
		param.add(new Long(params[0]));
		param.add(clientID);
		//
		for (int i = 1; i < params.length; i++)
			param.add(params[i]);
		//
		return sql;
	}
	
	public static String CandidateHRDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select a.candidateid, a.legal_firstname, a.legal_middlename, a.legal_lastname, a.generationsuffix, a.ssn, to_char(a.dateofbirth, 'mm/dd/yyyy') as dateofbirth,"
				+ " decode(a.maritalstatus, 1, 'Civil Partnership', 2, 'Divorced', 3, 'Separated', 4, 'Legally Separated', 5, 'Married', 6, 'Domestic Partner', 7, 'Single', 8, 'Widowed', '') as martialstatus, "
				+ " a.visastatus, a.userfield_updatedate dateuserfieldupdated " + " from tcandidate_hr a, tcandidate b" + " where a.candidateid = ? and a.teamid = ? and a.teamid = b.teamid and a.candidateid = b.id and nvl(b.privacyapplied,0) = 0"
				+ " union all" + " select a.candidateid, c.text, c.text, c.text, c.text, c.text, c.text, c.text, c.text, a.userfield_updatedate dateuserfieldupdated "
				+ " from tcandidate_hr a, tcandidate b, (select text from (select text from gdpr_inaccessibility_text where teamid = ? or teamid = 0 order by teamid desc) where rownum = 1) c "
				+ " where a.candidateid = ? and a.teamid = ? and a.teamid = b.teamid and a.candidateid = b.id and nvl(b.privacyapplied,0) > 0";
		param.add(new Long(params[0]));
		param.addElement(clientID);
		param.addElement(clientID);
		param.add(new Long(params[0]));
		param.addElement(clientID);
		return sql;
	}
	
	public static String CandidatesHRDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select a.candidateid, a.legal_firstname, a.legal_middlename, a.legal_lastname, a.generationsuffix, a.ssn, to_char(a.dateofbirth, 'mm/dd/yyyy') as dateofbirth,"
				+ " decode(a.maritalstatus, 1, 'Civil Partnership', 2, 'Divorced', 3, 'Separated', 4, 'Legally Separated', 5, 'Married', 6, 'Domestic Partner', 7, 'Single', 8, 'Widowed', '') as martialstatus, "
				+ " a.visastatus, a.userfield_updatedate dateuserfieldupdated " + " from tcandidate_hr a, tcandidate b"
				+ " where a.candidateid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid = ? and a.teamid = b.teamid and a.candidateid = b.id and nvl(b.privacyapplied,0) = 0" + " union all"
				+ " select a.candidateid, c.text, c.text, c.text, c.text, c.text, c.text, c.text, c.text, a.userfield_updatedate dateuserfieldupdated "
				+ " from tcandidate_hr a, tcandidate b, (select text from (select text from gdpr_inaccessibility_text where teamid = ? or teamid = 0 order by teamid desc) where rownum = 1) c "
				+ " where a.candidateid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid = ? and a.teamid = b.teamid and a.candidateid = b.id and nvl(b.privacyapplied,0) > 0";
		StringBuilder candidates = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				candidates.append(",");
			candidates.append(params[i]);
		}
		param.add(candidates.toString());
		param.addElement(clientID);
		param.addElement(clientID);
		param.add(candidates.toString());
		param.addElement(clientID);
		return sql;
	}
	
	public static String CandidatesQualDetail(Long clientID, String[] params, Vector<Object> param) {
		int noRec = 1;
		if (params != null) {
			for (int i = 1; i < params.length; i++) {
				try {
					Long.parseLong(params[i]);
					noRec++;
				} catch (Exception e) {
					noRec = i;
					break;
				}
			}
		}
		String sql = "select a.candidateid, b.catname qualname, c.dcatname qualvalue, a.datecreated dateentered " + " from tcandidate_category a, tcategories b, tcategories_detail c "
				+ " where a.candidateid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? and a.dirty<>2 " + "   and b.teamid=a.teamid and b.catid=a.catid "
				+ "   and c.teamid=a.teamid and c.catid=a.catid and c.dcatid=a.dcatid " + "   and (";
		for (int i = noRec; i < params.length; i++) {
			if (i > noRec)
				sql += " or ";
			sql += "b.catname=?";
		}
		if (params.length == noRec)
			sql += "1=1";
		sql += ") order by qualname";
		// System.out.println(sql);
		StringBuilder candidates = new StringBuilder();
		for (int i = 0; i < noRec; i++) {
			if (i > 0)
				candidates.append(",");
			candidates.append(params[i]);
		}
		param.add(candidates.toString());
		param.add(clientID);
		for (int i = noRec; i < params.length; i++)
			param.add(params[i]);
		return sql;
	}
	
	public static String newUpdatedCandidateQuals(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String sql = "select candidateid, b.catname qualname, c.dcatname qualvalue, a.datecreated dateentered, 0 deleteflag " + "from tcandidate_category a, tcategories b, tcategories_detail c "
				+ " where a.teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and a.dirty<>2 " + "   and b.teamid=a.teamid and b.catid=a.catid "
				+ "   and c.teamid=a.teamid and c.catid=a.catid and c.dcatid=a.dcatid " + "  union all " + "select candidateid, b.catname qualname, c.dcatname qualvalue, a.datedeleted dateentered, 1 deleteflag "
				+ "from tcandidatequal_deleted a, tcategories b, tcategories_detail c " + " where a.teamid=? and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "   and b.teamid=a.teamid and b.catid=a.catid " + "   and c.teamid=a.teamid and c.catid=a.catid and c.dcatid=a.dcatid ";
		;
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String CandidateResumeSourceDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select h.candidateid, h.docid, h.datecreated dateuploaded, h.datereceived datedownloaded, "
				+ "decode(h.resumesourceflag, 1, 'Candidate', 2, 'Website', 3, 'Recruiter', 4, 'Harvested/New', 6, 'Harvested/Refresh', 7, 'Conversion', 8, 'Bulk Upload') source, "
				+ "(select s.name from tresumesourcename s where s.teamid=h.teamid and s.id=h.resumesource) sourcename, " + "(select r.firstname||' '||r.lastname from trecruiter r where r.id=h.recruiterid and r.groupid=h.teamid) recruitername "
				+ "from tcandidatedocument_header h , tcandidate c where h.candidateid=? and h.teamid=? and h.candidateid = c.id and bitand(nvl(c.privacyapplied,0), 32) = 0 " + "and h.teamid = c.teamid " + "union all "
				+ "select h.candidateid, null, null, null, d.text source, translate (d.text using nchar_cs), null "
				+ "from tcandidatedocument_header h , tcandidate c, (select text, teamid from (select text, teamid from gdpr_inaccessibility_text where teamid = ? or teamid = 0 order by teamid desc) where rownum = 1) d "
				+ "where h.candidateid= ? and h.teamid= ? and h.candidateid = c.id and bitand(nvl(c.privacyapplied,0), 32) > 0 " + "and h.teamid = c.teamid and h.teamid = d.teamid";
		param.add(new Long(params[0]));
		param.add(clientID);
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String CandidateResumesDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.candidateid, a.docid, a.dbid, a.global_id resumeid, a.datecreated_original datecreated, a.datecreated dateupdated, a.datereceived_original datefirstdownloaded,  a.datereceived datelastdownloaded "
				+ " from tcandidatedocument_header a , tcandidate b" + " where a.candidateid= ? and a.teamid= ? and a.teamid = b.teamid and a.candidateid = b.id and bitand(nvl(b.privacyapplied,0), 32) = 0" + " union all"
				+ " select a.candidateid, null, null, null, null, null, null, null" + " from tcandidatedocument_header a , tcandidate b "
				+ " where a.candidateid=? and a.teamid=? and a.teamid = b.teamid and a.candidateid = b.id and bitand(nvl(b.privacyapplied,0), 32) > 0";
		param.add(new Long(params[0]));
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String CandidatesResumesDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "SELECT * FROM ( " + "    select a.candidateid, a.docid, a.dbid, a.global_id resumeid, a.datecreated_original datecreated, a.datecreated dateupdated, "
				+ "    a.datereceived_original datefirstdownloaded, a.datereceived datelastdownloaded " + "    from tcandidatedocument_header a , tcandidate b "
				+ "    where a.candidateid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) " + "    and a.teamid= ? and a.teamid = b.teamid and a.candidateid = b.id and bitand(nvl(b.privacyapplied,0), 32) = 0 "
				+ "    union all " + "    select a.candidateid, null, null, null, null, null, null, null " + "    from tcandidatedocument_header a , tcandidate b, (select text from (select text from gdpr_inaccessibility_text "
				+ "    where teamid = ? or teamid = 0 order by teamid desc) where rownum = 1) c " + "    where a.candidateid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) "
				+ "    and a.teamid=? and a.teamid = b.teamid and a.candidateid = b.id and bitand(nvl(b.privacyapplied,0), 32) > 0) " + "ORDER BY CANDIDATEID";
		StringBuilder candidates = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				candidates.append(",");
			candidates.append(params[i]);
		}
		param.add(candidates.toString());
		param.add(clientID);
		param.add(clientID);
		param.add(candidates.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String CandidateResumeDetail2(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select candidateid, docid, datecreated, datecreated_original, datereceived, datereceived_original " + "from tcandidatedocument_header " + "where candiateid=? and teamid=?";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String CandidateOnboardingDocumentDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select b.id, b.filename, ' ' filecontent, a.datecreated assignedon, uploadedon from tcandidate_onboarding a, tcandidate_onboarding_docs b " + "where a.candidateid=? and a.interviewid=? and a.teamid=? and lower(a.filename) = ? "
				+ "  and b.teamid=a.teamid and b.onboardingid=a.id and b.deleted=0 ";
		param.add(new Long(params[0]));
		param.add(new Long(params[1]));
		param.add(clientID);
		param.add(params[2].toLowerCase());
		return sql;
	}
	
	public static String CandidateAttachmentDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select id, filename, ' ' filecontent, datecreated uploadedon from tcandidate_attachments " + "where candidateid=? and teamid=? and lower(filename) = ? ";
		param.add(new Long(params[0]));
		param.add(clientID);
		param.add(params[1].toLowerCase());
		return sql;
	}
	
	public static String CompanyNoteDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select companyid, noteid, case when note is null then to_char(note_clob) else note end note " + " from tcustomercompanynotes where noteid=? and recruiter_teamid=? ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String CompanyNotesDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select companyid, noteid, case when note is null then to_char(note_clob) else note end note " + " from tcustomercompanynotes "
				+ "where noteid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) " + "  and recruiter_teamid=? ";
		StringBuilder noteids = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				noteids.append(",");
			noteids.append(params[i]);
		}
		param.add(noteids.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String ContactNoteDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select note " + " from tcustomernotes where noteid=? and recruiter_teamid=? ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String ContactNotesDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select noteid, customerid contactid, note " + " from tcustomernotes " + "where noteid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) " + "  and recruiter_teamid=? ";
		StringBuilder noteids = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				noteids.append(",");
			noteids.append(params[i]);
		}
		param.add(noteids.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String CandidateNoteDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select candidateid, noteid, case when note_clob is null or length(note_clob) = 0 then note else sf_htmlclob2text(note_clob) end note " + " from tcandidatenotes where noteid=? and recruiter_teamid=? ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String CandidateNotesDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select candidateid, noteid, case when note_clob is null or length(note_clob) = 0 then note else sf_htmlclob2text(note_clob) end note " + " from tcandidatenotes "
				+ "where noteid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) " + "  and recruiter_teamid=? ";
		StringBuilder noteids = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				noteids.append(",");
			noteids.append(params[i]);
		}
		param.add(noteids.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String CandidateAvailabilityDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select candidateid, case when flag=2 then 1 else 0 end unavailableindef, dateavailable, " + " DATE_CREATED datecreated " + " from tcandidate_unreachable where candidateid=? and teamid=?" + " union "
				+ "select h.candidateid, 0 unavailableindef, max(h.datecreated) dateavailable, " + " max(h.DATECREATED) datecreated " + " from tcandidatedocument_header h where h.candidateid=? and h.teamid=? "
				+ "  and not exists (select 1 from tcandidatedocument_header d where d.candidateid=h.candidateid and d.teamid=h.teamid and d.daterefreshed > h.daterefreshed) "
				+ "  and not exists (select 1 from tcandidate_unreachable x where x.candidateid=h.candidateid and x.teamid=h.teamid) " + "  group by candidateid ";
		param.add(new Long(params[0]));
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String CandidatesAvailabilityDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select candidateid, case when flag=2 then 1 else 0 end unavailableindef, dateavailable, " + " DATE_CREATED datecreated "
				+ " from tcandidate_unreachable where candidateid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type) from dual )) and teamid=?" + "  union "
				+ "select h.candidateid, 0 unavailableindef, max(h.datecreated) dateavailable, " + " max(h.DATECREATED) datecreated "
				+ " from tcandidatedocument_header h where h.candidateid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type) from dual )) and h.teamid=? "
				+ "  and not exists (select 1 from tcandidatedocument_header d where d.candidateid=h.candidateid and d.teamid=h.teamid and d.daterefreshed > h.daterefreshed) "
				+ "  and not exists (select 1 from tcandidate_unreachable x where x.candidateid=h.candidateid and x.teamid=h.teamid) " + "  group by candidateid ";
		StringBuilder candidates = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				candidates.append(",");
			candidates.append(params[i]);
		}
		param.add(candidates.toString());
		param.add(clientID);
		param.add(candidates.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String CandidateEEODetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select te.candidateid, e.name eeocategory, nvl(ed.name,'Unspecified') selection " + "from tcandidate_eeo te, teeo e , teeo_detail ed " + "where te.candidateid=? and te.teamid=? and te.eeoid <> 3 "
				+ "and e.id=te.eeoid and ed.eeoid(+)=te.eeoid and ed.id(+)=te.deeoid " + "union all " + "select te.candidateid, e.name eeocategory, ed.name selection " + "from tcandidate_eeo te, teeo e , teeo_detail ed "
				+ "where te.candidateid=? and te.teamid=? and te.eeoid = 3 " + "and e.id=te.eeoid and ed.eeoid=te.eeoid and bitand(te.deeoid,ed.id)=ed.id ";
		param.add(new Long(params[0]));
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String ContactOwnersDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.customerid contactid, a.recruiterid userid, b.firstname, b.lastname, isprimaryowner " + " from tcustomer_owners a, trecruiter b " + " where a.customerid=? and a.teamid=? and b.id=a.recruiterid ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String ContactsOwnersDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.customerid contactid, a.recruiterid userid, b.firstname, b.lastname, isprimaryowner " + " from tcustomer_owners a, trecruiter b "
				+ " where a.customerid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? and b.id=a.recruiterid ";
		StringBuilder companies = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				companies.append(",");
			companies.append(params[i]);
		}
		param.add(companies.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String ContactsTypesDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.id contactid, b.typename " + " from tcustomer a, tcustomertype b " + " where a.id in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? "
				+ "   and b.teamid=a.teamid and bitand(b.typeid,a.type) > 0 ";
		StringBuilder companies = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				companies.append(",");
			companies.append(params[i]);
		}
		param.add(companies.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String ContactAddressesDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select contactid, id addressid, default_address, address1, address2, city, state, zipcode, countryid, freetext label " + " from tcustomeraddress a " + " where a.contactid=? and a.teamid=? and (deleted is null or deleted = 0) "
				+ " order by addressid ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String JobDetail(Long clientID, String[] params, Vector<Object> param, String[] restriction, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 1; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from trfq_userfields t, tuserfields n "
						+ " where t.rfqid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1) ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = "select  a.id, a.dateissued, a.datelastupdated dateupdated, a.userfield_updatedate dateuserfieldupdated, a.datestatusupdated, " + //
				"nvl((select x.name from trfq_statuses x where x.teamid=a.teamid and x.id=a.jobstatus), (select x.name from trfq_statuses x where x.teamid=0 and x.id=a.jobstatus)) jobstatus, " + //
				"  a.customerid, a.companyid, a.address1, a.address2, a.city, a.state, a.zipcode, a.countryid country, " + //
				"  nvl((select name from tjob_priority p where p.teamid=a.teamid and p.id=a.jobpriority and p.deleteflag=0), decode(jobpriority,1,'A',2,'B',3,'C',4,'D','')) priority, " + //
				"  (select b.name from tdivision b where b.id=a.divisionid) division, " + //
				"  rfqrefno refno, rfqno_team jobdivano, startdate, case when enddate < '01-Jan-1970' then null else enddate end enddate, " + //
				"  positions, fills, maxsubmitals maxallowedsubmittals, billratemin, billratemax, decode(a.billrateper,'','','h','Hour','d','Day','y','Year'," + //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=a.billrateper and y.ratetype=1)) billrateper, " + //
				"  ratemin payratemin, ratemax payratemax, decode(a.rateper,'','','h','Hour','d','Day','y','Year'," + //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=a.rateper and y.ratetype=0)) payrateper, " + //
				"  nvl((select x.name from trfq_position_types x where x.teamid=a.teamid and x.id=a.contract), (select x.name from trfq_position_types x where x.teamid=0 and x.id=a.contract)) positiontype, " + //
				"  sf_makecriteria(skills) skills, rfqtitle jobtitle, jobdescription, " + //
				"  instruction remarks, subinstruction submittalinstruction, case when jobdivapost > 0 then 1 else 0 end posttoportal, " + //
				"  posting_title, posting_date, posting_description postingdescription, " + //
				"  criteria_degree, jobcatalogid, c.companyid catalogcompanyid, c.title catalogtitle, d.refno catalogrefno, d.groupname catalogname, " + //
				"  decode(c.active,1,'Yes','No') catalogactive, c.effective_date catalogeffectivedate, c.expiration_date catalogexpirationdate, " + //
				"  (select y.categoryname from tjobcatgrpassigned x, tjobcataloggroup_categories y " + //
				"     where x.teamid=a.teamid and x.catalogid=a.jobcatalogid and y.teamid=x.teamid and y.categoryid=x.categoryid) catalogcategory, " + //
				"  billrate catalogbillratelow, billrate_high catalogbillratehigh, decode(c.billrate_per,'','','h','Hour','d','Day','y','Year'," + //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=c.billrate_per and y.ratetype=1)) catalogbillrateper, " + //
				"  payrate_low catalogpayratelow, payrate_high catalogpayratehigh, decode(c.payrate_per,'','','h','Hour','d','Day','y','Year'," + //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=c.payrate_per and y.ratetype=0)) catalogpayrateper, " + //
				"  c.refno positionrefno, c.preventlowerpay, c.preventhigherbill, c.notes catalognotes, " + //
				"  OVERTIME OT, REFCHECK REFERENCES, TRAVEL, DRUGTEST DRUG_TEST, BACKCHECK BACKGROUND_CHECK, " + //
				"  SECCLEARANCE SECURITY_CLEARANCE, " + //
				"  (case when a.working_remotely is not null and a.working_remotely > 0 then 1 else 0 end) onsite_flexibility, " + //
				"  a.working_remotely remote_percentage " + //
				columnsToShow.toString() + //
				" from trfq a, tjobcatalogs c, tjobcataloggroups d  " + //
				" where a.id=? and a.teamid=? " + //
				"   and c.teamid(+)=a.teamid and c.id(+)=a.jobcatalogid and d.teamid(+)=c.teamid and d.id(+)=c.cataloggroupid " + //
				(restriction != null && restriction.length > 0 && restriction[0] != null ? " and a.division=? " : "");
		;
		;
		param.add(new Long(params[0]));
		param.add(clientID);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String JobsDetail(Long clientID, String[] params, Vector<Object> param, String[] restriction, Map<String, String> colNameToAliasMap) {
		int noRec = 1;
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 1; i < params.length; i++) {
				try {
					Long.parseLong(params[i]);
					noRec++;
				} catch (Exception e) {
					noRec = i;
					break;
				}
			}
			for (int i = noRec; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from trfq_userfields t, tuserfields n "
						+ " where t.rfqid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1) ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = "select a.id, a.dateissued, a.datelastupdated dateupdated, a.userfield_updatedate dateuserfieldupdated, a.datestatusupdated, " + //
				"nvl((select x.name from trfq_statuses x where x.teamid=a.teamid and x.id=a.jobstatus), (select x.name from trfq_statuses x where x.teamid=0 and x.id=a.jobstatus)) jobstatus, " + //
				"  a.customerid, a.companyid, a.address1, a.address2, a.city, a.state, a.zipcode, a.countryid country, " + //
				"  nvl((select name from tjob_priority p where p.teamid=a.teamid and p.id=a.jobpriority and p.deleteflag=0), decode(jobpriority,1,'A',2,'B',3,'C',4,'D','')) priority, " + //
				"  (select b.name from tdivision b where b.id=a.divisionid) division, " + //
				"  rfqrefno refno, rfqno_team jobdivano, startdate, case when enddate < '01-Jan-1970' then null else enddate end enddate, " + //
				"  positions, fills, maxsubmitals maxallowedsubmittals, billratemin, billratemax, decode(a.billrateper,'','','h','Hour','d','Day','y','Year'," + //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=a.billrateper and y.ratetype=1)) billrateper, " + //
				"  ratemin payratemin, ratemax payratemax, decode(a.rateper,'','','h','Hour','d','Day','y','Year'," + //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=a.rateper and y.ratetype=0)) payrateper, " + //
				"  nvl((select x.name from trfq_position_types x where x.teamid=a.teamid and x.id=a.contract), (select x.name from trfq_position_types x where x.teamid=0 and x.id=a.contract)) positiontype, " + //
				"  sf_makecriteria(skills) skills, rfqtitle jobtitle, jobdescription, " + //
				"  instruction remarks, subinstruction submittalinstruction, case when jobdivapost > 0 then 1 else 0 end posttoportal, " + //
				"  posting_title, posting_date, posting_description postingdescription, " + //
				"  criteria_degree, jobcatalogid, c.companyid catalogcompanyid, c.title catalogtitle, d.refno catalogrefno, d.groupname catalogname, " + //
				"  decode(c.active,1,'Yes','No') catalogactive, c.effective_date catalogeffectivedate, c.expiration_date catalogexpirationdate, " + //
				"  (select y.categoryname from tjobcatgrpassigned x, tjobcataloggroup_categories y " + //
				"     where x.teamid=a.teamid and x.catalogid=a.jobcatalogid and y.teamid=x.teamid and y.categoryid=x.categoryid) catalogcategory, " + //
				"  billrate catalogbillratelow, billrate_high catalogbillratehigh, decode(c.billrate_per,'','','h','Hour','d','Day','y','Year'," + //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=c.billrate_per and y.ratetype=1)) catalogbillrateper, " + //
				"  payrate_low catalogpayratelow, payrate_high catalogpayratehigh, decode(c.payrate_per,'','','h','Hour','d','Day','y','Year'," + //
				"    (select name from trateunits y where y.teamid=a.teamid and y.unitid=c.payrate_per and y.ratetype=0)) catalogpayrateper, " + //
				"  c.refno positionrefno, c.preventlowerpay, c.preventhigherbill, c.notes catalognotes, " + //
				"  OVERTIME OT, REFCHECK REFERENCES, TRAVEL, DRUGTEST DRUG_TEST, BACKCHECK BACKGROUND_CHECK, " + //
				"  SECCLEARANCE SECURITY_CLEARANCE, " + //
				"  (case when a.working_remotely is not null and a.working_remotely > 0 then 1 else 0 end) onsite_flexibility, " + //
				"  a.working_remotely remote_percentage " + //
				columnsToShow.toString() + //
				" from trfq a, tjobcatalogs c, tjobcataloggroups d  " + //
				" where a.id in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? " + //
				"   and c.teamid(+)=a.teamid and c.id(+)=a.jobcatalogid and d.teamid(+)=c.teamid and d.id(+)=c.cataloggroupid " + //
				(restriction != null && restriction.length > 0 && restriction[0] != null ? " and a.division=? " : "");
		;
		;
		StringBuilder jobs = new StringBuilder();
		for (int i = 0; i < noRec; i++) {
			if (i > 0)
				jobs.append(",");
			jobs.append(params[i]);
		}
		param.add(jobs.toString());
		param.add(clientID);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String JobContactsDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.rfqid jobid, a.customerid, nvl(b.name,'Unspecified') role " + " from trfq_customers a, tcontactroles b "
				+ " where a.rfqid=? and a.teamid=? and (a.roleid < 900 or a.roleid is null)  and b.teamid(+)=a.teamid and b.id(+)=a.roleid ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String JobsContactsDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.rfqid jobid, a.customerid, nvl(b.name,'Unspecified') role " + " from trfq_customers a, tcontactroles b "
				+ " where a.rfqid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? and (a.roleid < 900 or a.roleid is null)  and b.teamid(+)=a.teamid and b.id(+)=a.roleid ";
		StringBuilder jobs = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				jobs.append(",");
			jobs.append(params[i]);
		}
		param.add(jobs.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String JobUsersDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.rfqid jobid, a.recruiterid userid, b.firstname, b.lastname, " + "   sales, lead_sales primarysales, decode(sales+lead_sales,0,1,recruiter) recruiter, lead_recruiter primaryrecruiter, datelastassigned "
				+ " from trecruiterrfq a, trecruiter b " + " where a.rfqid=? and a.teamid=? and b.id=a.recruiterid " + " order by a.sales desc, a.lead_sales desc ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String JobsUsersDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select /*+ index(a, PK_TRECRUITERRFQ) */ a.rfqid jobid, a.recruiterid userid, b.firstname, b.lastname, "
				+ "   sales, lead_sales primarysales, decode(sales+lead_sales,0,1,recruiter) recruiter, lead_recruiter primaryrecruiter, datelastassigned " + " from trecruiterrfq a, trecruiter b "
				+ " where a.rfqid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? and b.id=a.recruiterid " + " order by a.sales desc, a.lead_sales desc ";
		StringBuilder jobs = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				jobs.append(",");
			jobs.append(params[i]);
		}
		param.add(jobs.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String jobsByUserByStatus(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		// System.out.println("BIData get to here");
		String sql = " select a.id jobid, rfqno_team jobdivano, rfqrefno optionalreferenceno, divisionid, b.name divisionname, " + " 	pr.recruiterid primaryrecruiterid, ps.recruiterid primarysalesid, " + " 	a.companyid, c.name companyname, "
				+ " 	(select recruiterid from tcustomer_company_owners x where x.companyid=a.companyid and x.isprimaryowner=1) primaryownerid, " + " 	a.customerid contactid, d.firstname contactfirstname, d.lastname contactlastname, "
				+ " 	a.dateissued issuedate, datelastupdated dateupdated, startdate, case when enddate < '01-Jan-1970' then null else enddate end enddate, "
				+ " 	nvl((select x.name from trfq_position_types x where x.teamid=a.teamid and x.id=a.contract), (select x.name from trfq_position_types x where x.teamid=0 and x.id=a.contract)) positiontype, "
				+ " 	nvl((select x.name from trfq_statuses x where x.teamid=a.teamid and x.id=a.jobstatus), (select x.name from trfq_statuses x where x.teamid=0 and x.id=a.jobstatus)) jobstatus, "
				+ " 	rfqtitle title, positions openings, fills, a.city, a.state, a.zipcode, "
				+ "   nvl((select name from tjob_priority p where p.teamid=a.teamid and p.id=a.jobpriority and p.deleteflag=0), decode(jobpriority,1,'A',2,'B',3,'C',4,'D','')) priority, "
				+ " 	billratemin, billratemax, decode(lower(billrateper),'d','Daily','y','Yearly','h','Hourly',null,null,"
				+ "			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=0 and x.unitid=billrateper)) billfrequency, "
				+ " 	ratemin payratemin, ratemax payratemax, decode(lower(rateper),'d','Daily','y','Yearly','h','Hourly',null,null,"
				+ "			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=1 and x.unitid=rateper)) payfrequency, " + " 	nvl((select x.name from tcurrency x where x.id=payrate_currency), "
				+ "   	nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency "
				+ " from trecruiterrfq rr, trfq a, tdivision b, tcustomercompany c, tcustomer d, trecruiterrfq pr, trecruiterrfq ps " + " where rr.teamid=? and rr.recruiterid=? and rr.jobstatus=? and a.id=rr.rfqid "
				+ " and a.divisionid = b.id(+) and a.companyid = c.id(+) and a.customerid = d.id(+) " + " and a.id = pr.rfqid(+) and 1 = pr.lead_recruiter(+) " + " and a.id = ps.rfqid(+) and 1 = ps.lead_sales(+) ";
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(new Integer(params[1]));
		return sql;
	}
	
	public static String resumeDetail(String[] params, Vector<Object> param) {
		String sql = "select * from (select (case when ORIGINALFILENAME is null then 'resume.rtf' else ORIGINALFILENAME end) AS FILENAME, ZIPRTF as FILECONTENT_BASE64ENCODED from tcandidatedocument where global_id=?) x";
		param.add(params[0]);
		return sql;
	}
	
	public static String usersTasksDetail(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String sql = " select id, recruiterid, (select contactid from tevent_contacts where eventid=id and rownum=1) customerid, candidateid, creatorid, " + "   eventdate, title, decode(optional,2,'No',4,'Yes','') completed, "
				+ "	(select b.firstname||' '||b.lastname from trecruiter b where b.id=tevent.recruiterid) assignedto, " + "	(select b.firstname||' '||b.lastname from trecruiter b where b.id=tevent.creatorid) createdby, "
				+ "	case when result is null or length(trim(result))=0 " + "	then (select typename from ttask_type t where t.teamid=tevent.teamid and t.id=tevent.task_type) "
				+ "	else (select typename from ttask_type_candidate t where t.teamid=tevent.teamid and t.id = task_type) end tasktype, " + "	decode(private,1,'Yes','No') private, datecreated, lastmodified " + " from tevent "
				+ " where recruiterid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) " + "   and teamid=? and optional in (2,4) and status_new = 'ACTIVE' "
				+ "   and eventdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		StringBuilder users = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				users.append(",");
			users.append(params[i]);
		}
		param.add(users.toString());
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String EventsAttendeesDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.eventid, a.recruiterid userid, case when a.recruiterid > 0 then b.firstname||' '||b.lastname else a.sync_displayname end attendeename, "
				+ "  case when a.recruiterid > 0 then b.email else a.sync_email end attendeeemail, isorganizer, responsestatus " + " from trecruiterevent a, trecruiter b "
				+ " where a.eventid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.teamid=? and b.id(+)=a.recruiterid ";
		StringBuilder events = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				events.append(",");
			events.append(params[i]);
		}
		// System.out.println("BIData Jobs Users Detail, teamid = " + clientID +
		// ", list of job IDs = " + jobs.toString());
		param.add(events.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String jobUserUpdated(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select jobid, max(dateupdated) dateupdated " + " from tjob_userupdated " + " where teamid =?  and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + " group by jobid";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String jobsPosted(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select * from " + "(select rfqid as JOBID, RFQREFNO, b.websitename as WEBSITENAME, DATEPOSTED, DATEADDED, username as ACCOUNTNAME, " + "   case when postingerror is null then 'Success' else postingerror end as STATUS "
				+ " from trfqposted a, twebsites b " + " where a.teamid =? and a.dateposted between str_to_date(?,'%m/%d/%Y %H:%i:%s') and str_to_date(?,'%m/%d/%Y %H:%i:%s') " + "   and b.id = a.siteid) x ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String jobsCurrentlyPosted(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select * from " + "(select rfqid as JOBID, RFQREFNO, b.websitename as WEBSITENAME, DATEPOSTED, DATEADDED, username as ACCOUNTNAME, " + "   case when postingerror is null then 'Success' else postingerror end as STATUS "
				+ " from trfqposted a, twebsites b " + " where a.teamid =? and b.id = a.siteid) x ";
		param.add(clientID);
		return sql;
	}
	
	public static String portalJobsList(Long clientID, String[] params, Vector<Object> param) {
		int compid = Integer.parseInt(params[0]);
		String sql = " select id jobid, nvl(posting_date,dateissued) dateissued, datelastupdated dateupdated, userfield_updatedate dateuserfieldupdated, rfqtitle jobtitle " + " from trfq " + " where teamid = ? and jobstatus = 0 "
				+ (compid > 0 ? " and exists (select 1 from trfq_csp jc where jc.teamid=trfq.teamid and jc.rfqid=trfq.id and jc.portalid=" + compid + ") " : " and bitand(jobdivapost,1)=1 ")
				+ "   and (skills is null or upper(skills) not like 'SKILLS TO BE ASSIGN%') " + " order by nvl(posting_date,dateissued) desc ";
		param.add(clientID);
		return sql;
	}
	
	public static String JobApplicantsDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.candidateid, decode(bitand(nvl(c.privacyapplied,0), 1), 0, c.firstname, g.text) firstname, decode(bitand(nvl(c.privacyapplied,0), 1), 0, c.lastname, g.text) lastname, "
				+ " decode(bitand(nvl(c.privacyapplied,0), 16), 0, c.email, g.text) email, a.rfqid jobid, a.dateapplied, " + "   decode(a.resume_source_name,'JOBDIVA',(select company from tteam where id=a.teamid),a.resume_source_name) resumesource,"
				+ "   case when r.daterejected is not null then 'Rejected' " + "        when exists (select 1 from tinterviewschedule i where i.candidateid=a.candidateid and i.rfqid=a.rfqid) then 'Submitted'" + "   else null end action, "
				+ "   case when r.daterejected is not null then r.recruiterid " + "   else (select i.recruiterid from tinterviewschedule i where i.candidateid=a.candidateid and i.rfqid=a.rfqid "
				+ "      and i.id = (select min(j.id) from tinterviewschedule j where j.candidateid=a.candidateid and j.rfqid=a.rfqid)) " + "   end actionuserid, " + "   case when r.daterejected is not null then r.daterejected "
				+ "   else (select min(datepresented) from tinterviewschedule i where i.candidateid=a.candidateid and i.rfqid=a.rfqid) " + "   end actiondate, "
				+ "   (select nvl(f.displayname, h.resumesourcerefname) from tcandidatedocument_header h, tresumesourceref f " + "    where h.candidateid=a.candidateid and h.teamid=a.teamid and h.docid="
				+ "      (select min(h1.docid) from tcandidatedocument_header h1 where h1.candidateid=h.candidateid and h1.teamid=h.teamid and h1.resumesourceflag=1 "
				+ "       and (h1.docid=a.docid or h1.datereceived > a.dateapplied)) and f.id=h.resumesourcerefid) referrer, "
				+ "   nvl(case when exists (select 1 from tcandidatedocument_header h where h.candidateid=a.candidateid and h.teamid=a.teamid and h.global_id=a.global_id) "
				+ "     then a.global_id else null end, (select h.global_id from tcandidatedocument_header h " + "     where h.candidateid=a.candidateid and h.teamid=a.teamid and h.docid="
				+ "       (select min(h1.docid) from tcandidatedocument_header h1 where h1.candidateid=h.candidateid and h1.teamid=h.teamid and h1.resumesourceflag=1 " + "        and (h1.docid=a.docid or h1.datereceived > a.dateapplied)))) resumeid "
				+ " from tcandidate_applyforjob a, tcandidate c, trejection r, (select text from (select text from gdpr_inaccessibility_text where teamid = ? or teamid = 0 order by teamid desc) where rownum = 1) g  "
				+ " where a.teamid = ? and a.rfqid=? and a.dirty = 0 " + "   and c.id=a.candidateid and c.teamid=a.teamid " + "   and r.candidateid(+)=a.candidateid and r.recruiter_teamid(+)=a.teamid and r.rfqid(+)=a.rfqid ";
		param.add(clientID);
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
	
	public static String CandidateJobApplicationDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.candidateid, a.rfqid jobid, decode(bitand(nvl(c.privacyapplied,0), 1), 0, c.firstname, g.text) firstname, decode(bitand(nvl(c.privacyapplied,0), 1), 0, c.lastname, g.text) lastname,"
				+ "  decode(bitand(nvl(c.privacyapplied,0), 16), 0, c.email, g.text) email, a.dateapplied, a.global_id resumeid, "
				+ "   case when bitand(nvl(c.privacyapplied,0), 16) = 0 then decode(a.resume_source_name,'JOBDIVA',(select company from tteam where id=a.teamid),a.resume_source_name) else to_nchar(g.text) end resumesource,"
				+ "   case when r.daterejected is not null then 'Rejected' " + "        when exists (select 1 from tinterviewschedule i where i.candidateid=a.candidateid and i.rfqid=a.rfqid) then 'Submitted'" + "   else null end action, "
				+ "   case when r.daterejected is not null then r.recruiterid " + "   else (select i.recruiterid from tinterviewschedule i where i.candidateid=a.candidateid and i.rfqid=a.rfqid "
				+ "      and i.id = (select min(j.id) from tinterviewschedule j where j.candidateid=a.candidateid and j.rfqid=a.rfqid)) " + "   end actionuserid, " + "   case when r.daterejected is not null then r.daterejected "
				+ "   else (select min(datepresented) from tinterviewschedule i where i.candidateid=a.candidateid and i.rfqid=a.rfqid) " + "   end actiondate, "
				+ "   (select nvl(f.displayname, h.resumesourcerefname) from tcandidatedocument_header h, tresumesourceref f "
				+ "    where h.candidateid=a.candidateid and h.docid=a.docid and h.teamid=a.teamid and h.resumesourceflag=1 and f.id(+)=h.resumesourcerefid) referrer "
				+ " from tcandidate_applyforjob a, tcandidate c, trejection r, (select text from (select text from gdpr_inaccessibility_text where teamid = ? or teamid = 0 order by teamid desc) where rownum = 1) g "
				+ " where a.teamid = ? and a.candidateid = ? and a.rfqid = ? and a.dirty = 0 " + "   and c.id=a.candidateid and c.teamid=a.teamid " + "   and r.candidateid(+)=a.candidateid and r.recruiter_teamid(+)=a.teamid and r.rfqid(+)=a.rfqid ";
		param.add(clientID);
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(new Long(params[1]));
		return sql;
	}
	
	public static String JobSubmittalsDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select id, recruiterid userid, primarysalesid, candidateid, customerid contactid, roleid, datecreated, dateupdated, placementdate, interviewscheduledate, " + "  datepresented submittaldate, "
				+ " case when roleid > 900 then daterejected else extdaterejected end rejectiondate, " + "  dateinterview, interview_timezoneid, datehired startdate, date_ended enddate, a.dateterminated terminationdate, "
				+ "  managerfirstname, managerlastname, " + " case when dateterminated is not null and reasonterminated > 0 " + "      then (select description from treason_termination x where x.teamid=a.recruiter_teamid and x.id=reasonterminated) "
				+ "      else null end terminationreason, " + " case when EXTREJECTREASONID is not null and EXTREJECTREASONID > 0 "
				+ "      then (select reason from TREJECTION_REASONS rejreason where rejreason.teamid=a.recruiter_teamid and rejreason.id=a.EXTREJECTREASONID) " + "      else null end rejectreason, "
				+ " (case when a.hourly > 0 then a.hourly_corporate when a.daily > 0 then a.daily_corporate else a.yearly_corporate end) corptocorp, " + " pay_hourly agreedbillrate, " + " case when finalbillrateunit is not null "
				+ "      then decode(finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', " + "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=1 and x.unitid=finalbillrateunit)) "
				+ "      else null end billfrequency, " + " case when hourly > 0 then hourly when daily > 0 then daily else yearly end agreedpayrate, " + " case when payrateunits is not null "
				+ "      then decode(case when hourly > 0 then substr(payrateunits,1,1) when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end,'h','Hourly','d','Daily','y','Yearly', "
				+ "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=0 and x.unitid=(case when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end))) "
				+ "      else (case when hourly > 0 then 'Hourly' when daily > 0 then 'Daily' when yearly > 0 then 'Yearly' else null end) end payfrequency, " + " nvl((select x.name from tcurrency x where x.id=a.hourly_currency), "
				+ "     nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency, "
				+ " case when a.datehired is not null and a.contract=1 then decode(fee_type,0,'%',1,'Flat Amount','') else '' end fee_type, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=0 then fee/100 when contract=1 and fee_type=1 then fee/yearly else null end fee_percent, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=1 then fee when contract=1 and fee_type=0 then yearly*fee/100 else null end fee, " + " notes "
				+ " from tinterviewschedule a where a.rfqid=? and a.recruiter_teamid=? ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String JobsSubmittalsDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select a.rfqid jobid, id, recruiterid userid, primarysalesid, candidateid, customerid contactid, roleid, datecreated, dateupdated, " + "  datepresented submittaldate, placementdate, interviewscheduledate, "
				+ " case when roleid > 900 then daterejected else extdaterejected end rejectiondate, " + "  dateinterview, interview_timezoneid, datehired startdate, date_ended enddate, a.dateterminated terminationdate, "
				+ "  managerfirstname, managerlastname, " + " case when dateterminated is not null and reasonterminated > 0 " + "      then (select description from treason_termination x where x.teamid=a.recruiter_teamid and x.id=reasonterminated) "
				+ "      else null end terminationreason, " + " case when EXTREJECTREASONID is not null and EXTREJECTREASONID > 0 "
				+ "      then (select reason from TREJECTION_REASONS rejreason where rejreason.teamid=a.recruiter_teamid and rejreason.id=a.EXTREJECTREASONID) " + "      else null end rejectreason, "
				+ " (case when a.hourly > 0 then a.hourly_corporate when a.daily > 0 then a.daily_corporate else a.yearly_corporate end) corptocorp, " + " pay_hourly agreedbillrate, " + " case when finalbillrateunit is not null "
				+ "      then decode(finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', " + "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=1 and x.unitid=finalbillrateunit)) "
				+ "      else null end billfrequency, " + " case when hourly > 0 then hourly when daily > 0 then daily else yearly end agreedpayrate, " + " case when payrateunits is not null "
				+ "      then decode(case when hourly > 0 then substr(payrateunits,1,1) when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end,'h','Hourly','d','Daily','y','Yearly', "
				+ "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=0 and x.unitid=(case when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end))) "
				+ "      else (case when hourly > 0 then 'Hourly' when daily > 0 then 'Daily' when yearly > 0 then 'Yearly' else null end) end payfrequency, " + " nvl((select x.name from tcurrency x where x.id=a.hourly_currency), "
				+ "     nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency, "
				+ " case when a.datehired is not null and a.contract=1 then decode(fee_type,0,'%',1,'Flat Amount','') else '' end fee_type, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=0 then fee/100 when contract=1 and fee_type=1 then fee/yearly else null end fee_percent, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=1 then fee when contract=1 and fee_type=0 then yearly*fee/100 else null end fee, " + " notes "
				+ " from tinterviewschedule a where a.rfqid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.recruiter_teamid=? order by a.rfqid";
		StringBuilder jobs = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				jobs.append(",");
			jobs.append(params[i]);
		}
		param.add(jobs.toString());
		param.add(clientID);
		return sql;
	}
	
	public static String CandidateSubmittalsDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select id, recruiterid userid, primarysalesid, rfqid jobid, customerid contactid, roleid, datecreated, dateupdated, " + "  datepresented submittaldate, "
				+ " case when roleid > 900 then daterejected else extdaterejected end rejectiondate, " + "  dateinterview, interview_timezoneid, datehired startdate, date_ended enddate, a.dateterminated terminationdate, "
				+ "  managerfirstname, managerlastname, " + " case when dateterminated is not null and reasonterminated > 0 " + "      then (select description from treason_termination x where x.teamid=a.recruiter_teamid and x.id=reasonterminated) "
				+ "      else null end terminationreason, " + " case when EXTREJECTREASONID is not null and EXTREJECTREASONID > 0 "
				+ "      then (select reason from TREJECTION_REASONS rejreason where rejreason.teamid=a.recruiter_teamid and rejreason.id=a.EXTREJECTREASONID) " + "      else null end rejectreason, "
				+ " (case when a.hourly > 0 then a.hourly_corporate when a.daily > 0 then a.daily_corporate else a.yearly_corporate end) corptocorp, " + " pay_hourly agreedbillrate, " + " case when finalbillrateunit is not null "
				+ "      then decode(finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', " + "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=1 and x.unitid=finalbillrateunit)) "
				+ "      else null end billfrequency, " + " case when hourly > 0 then hourly when daily > 0 then daily else yearly end agreedpayrate, " + " case when payrateunits is not null "
				+ "      then decode(case when hourly > 0 then substr(payrateunits,1,1) when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end,'h','Hourly','d','Daily','y','Yearly', "
				+ "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=0 and x.unitid=(case when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end))) "
				+ "      else (case when hourly > 0 then 'Hourly' when daily > 0 then 'Daily' when yearly > 0 then 'Yearly' else null end) end payfrequency, " + " nvl((select x.name from tcurrency x where x.id=a.hourly_currency), "
				+ "     nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency, "
				+ " case when a.datehired is not null and a.contract=1 then decode(fee_type,0,'%',1,'Flat Amount','') else '' end fee_type, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=0 then fee/100 when contract=1 and fee_type=1 then fee/yearly else null end fee_percent, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=1 then fee when contract=1 and fee_type=0 then yearly*fee/100 else null end fee, " + " notes "
				+ " from tinterviewschedule a where a.candidateid=? and a.recruiter_teamid=? ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String SubmittalDetail(Long clientID, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 1; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value "
						+ " from tstartrecord_userfields t, tuserfields n " + " where t.startid=a.id and t.teamid=a.recruiter_teamid and n.teamid=a.recruiter_teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i])
						+ "' AND (ROWNUM = 1) ) " + "\"" + (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = "select id, recruiterid userid, primarysalesid, candidateid, rfqid jobid, customerid contactid, roleid, datecreated, dateupdated, userfield_updatedate dateuserfieldupdated, "
				+ "  datepresented submittaldate, placementdate, interviewscheduledate, " + " case when roleid > 900 then daterejected else extdaterejected end rejectiondate, "
				+ "  dateinterview, interview_timezoneid, datehired startdate, date_ended enddate, a.dateterminated terminationdate, " + "  managerfirstname, managerlastname, " + " case when dateterminated is not null and reasonterminated > 0 "
				+ "      then (select description from treason_termination x where x.teamid=a.recruiter_teamid and x.id=reasonterminated) " + "      else null end terminationreason, "
				+ " case when EXTREJECTREASONID is not null and EXTREJECTREASONID > 0 " + "      then (select reason from TREJECTION_REASONS rejreason where rejreason.teamid=a.recruiter_teamid and rejreason.id=a.EXTREJECTREASONID) "
				+ "      else null end rejectreason, "
				+ " nvl((select x.name from trfq_position_types x where x.teamid=a.recruiter_teamid and x.id=a.contract), (select x.name from trfq_position_types x where x.teamid=0 and x.id=a.contract)) positiontype, "
				+ " (case when a.hourly > 0 then a.hourly_corporate when a.daily > 0 then a.daily_corporate else a.yearly_corporate end) corptocorp, " + " pay_hourly agreedbillrate, " + " case when finalbillrateunit is not null "
				+ "      then decode(finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', " + "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=1 and x.unitid=finalbillrateunit)) "
				+ "      else null end billfrequency, " + " case when hourly > 0 then hourly when daily > 0 then daily else yearly end agreedpayrate, " + " case when payrateunits is not null "
				+ "      then decode(case when hourly > 0 then substr(payrateunits,1,1) when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end,'h','Hourly','d','Daily','y','Yearly', "
				+ "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=0 and x.unitid=(case when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end))) "
				+ "      else (case when hourly > 0 then 'Hourly' when daily > 0 then 'Daily' when yearly > 0 then 'Yearly' else null end) end payfrequency, " + " nvl((select x.name from tcurrency x where x.id=a.hourly_currency), "
				+ "     nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency, "
				+ " case when a.datehired is not null and a.contract=1 then decode(fee_type,0,'%',1,'Flat Amount','') else '' end fee_type, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=0 then fee/100 when a.datehired is not null and contract=1 and fee_type=1 then fee/yearly else null end fee_percent, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=1 then fee when a.datehired is not null and contract=1 and fee_type=0 then yearly*fee/100 else null end fee, " + " notes " + columnsToShow.toString()
				+ " from tinterviewschedule a where a.id=? and a.recruiter_teamid=? ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String SubmittalsDetail(Long clientID, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		int noRec = 1;
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 1; i < params.length; i++) {
				try {
					Long.parseLong(params[i]);
					noRec++;
				} catch (Exception e) {
					noRec = i;
					break;
				}
			}
			for (int i = noRec; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value "
						+ " from tstartrecord_userfields t, tuserfields n " + " where t.startid=a.id and t.teamid=a.recruiter_teamid and n.teamid=a.recruiter_teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i])
						+ "' AND (ROWNUM = 1) ) " + "\"" + (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = "select id, recruiterid userid, primarysalesid, candidateid, rfqid jobid, customerid contactid, roleid, datecreated, dateupdated, userfield_updatedate dateuserfieldupdated, "
				+ " datepresented submittaldate, placementdate, interviewscheduledate, " + " case when roleid > 900 then daterejected else extdaterejected end rejectdate, "
				+ " dateinterview, interview_timezoneid, datehired startdate, date_ended enddate, a.dateterminated terminationdate, " + "  managerfirstname, managerlastname, " + " case when dateterminated is not null and reasonterminated > 0 "
				+ "      then (select description from treason_termination x where x.teamid=a.recruiter_teamid and x.id=reasonterminated) " + "      else null end terminationreason, "
				+ " case when EXTREJECTREASONID is not null and EXTREJECTREASONID > 0 " + "      then (select reason from TREJECTION_REASONS rejreason where rejreason.teamid=a.recruiter_teamid and rejreason.id=a.EXTREJECTREASONID) "
				+ "      else null end rejectreason, "
				+ " nvl((select x.name from trfq_position_types x where x.teamid=a.recruiter_teamid and x.id=a.contract), (select x.name from trfq_position_types x where x.teamid=0 and x.id=a.contract)) positiontype, "
				+ " pay_hourly agreedbillrate, " + " case when finalbillrateunit is not null " + "      then decode(finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', "
				+ "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=1 and x.unitid=finalbillrateunit)) " + "      else null end billfrequency, "
				+ " (case when a.hourly > 0 then a.hourly_corporate when a.daily > 0 then a.daily_corporate else a.yearly_corporate end) corptocorp, " + " case when hourly > 0 then hourly when daily > 0 then daily else yearly end agreedpayrate, "
				+ " case when payrateunits is not null "
				+ "      then decode(case when hourly > 0 then substr(payrateunits,1,1) when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end,'h','Hourly','d','Daily','y','Yearly', "
				+ "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=0 and x.unitid=(case when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end))) "
				+ "      else (case when hourly > 0 then 'Hourly' when daily > 0 then 'Daily' when yearly > 0 then 'Yearly' else null end) end payfrequency, " + " nvl((select x.name from tcurrency x where x.id=a.hourly_currency), "
				+ "     nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency, "
				+ " case when a.datehired is not null and a.contract=1 then decode(fee_type,0,'%',1,'Flat Amount','') else '' end fee_type, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=0 then fee/100 when a.datehired is not null and contract=1 and fee_type=1 then fee/yearly else null end fee_percent, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=1 then fee when a.datehired is not null and contract=1 and fee_type=0 then yearly*fee/100 else null end fee, " + " notes " + columnsToShow.toString()
				+ " from tinterviewschedule a " + " where a.id in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and a.recruiter_teamid=? ";
		StringBuilder subs = new StringBuilder();
		for (int i = 0; i < noRec; i++) {
			if (i > 0)
				subs.append(",");
			subs.append(params[i]);
		}
		param.add(subs.toString());
		param.add(clientID);
		return sql;
	}
	// new / updated records
	
	public static String newUpdatedCompanies(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from tcompany_userfields t, tuserfields n "
						+ " where t.companyid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1) ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = " select a.id companyid, a.name companyname, dateentered datecreated, a.RECRUITERID USERID, editdate dateupdated, userfield_updatedate dateuserfieldupdated, "
				+ " (select firstname||' '||lastname from tcustomer_company_owners b, trecruiter c" + " where b.companyid = a.id and b.teamid=a.teamid and b.isprimaryowner=1 and c.id = b.recruiterid) primaryowner,"
				+ " companytype, c.address1, c.address2, c.city, c.state, c.zipcode, c.countryid, c.phone, c.fax, c.url, c.email " + columnsToShow.toString() + " from " + " (select id, substr(max(sys_connect_by_path(typename, ',')),2) companytype "
				+ " from " + " (select a.id, c.typename, rank() over (partition by a.id order by b.typeid) rk from tcustomercompany a, tcustomer_company_type b, tcustomer_company_types c "
				+ " where a.teamid=? and (a.dateentered between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + " or a.editdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ " or a.userfield_updatedate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss'))" + " and b.companyid(+)=a.id and b.teamid(+)=a.teamid and c.id(+)=b.typeid and c.teamid(+)=b.teamid) "
				+ " start with rk=1 connect by prior rk+1 = rk group by id) b, tcustomercompany a, tcustomercompanyaddresses c" + " where a.id = b.id and c.companyid = a.id and c.default_address = 1";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedOpportunities(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String sql = " select o.id, o.name, o.creatorid, o.createddate datecreated, o.startdate, o.enddate_est estimatedenddate, o.closedate, o.clientid, "
				+ "   (select s.name from topportunity_stage s where s.teamid=o.teamid and s.id=o.status) stage, " + "   (select p.name from topportunity_priority p where p.teamid=o.teamid and p.id=o.priority) priority, "
				+ "   reference, revenue, progress, laststatuschange, revenueduration, discontinue, discontinue_date " + " from topportunity o "
				+ " where o.teamid=? and (o.createddate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "   or o.laststatuschange between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss'))";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedContacts(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from tcustomer_userfields t, tuserfields n "
						+ " where t.customerid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1) ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = " select a.id contactid, a.firstname, a.lastname, timeentered datecreated, a.RECRUITERID USERID, editdate dateupdated, userfield_updatedate dateuserfieldupdated, a.companyid, a.companyname, a.departmentname department, a.title, a.active, "
				+ "   a.email, a.workphone, a.workphoneext, a.homephone, a.cellphone, a.contactfax fax, " + "   b.address1, b.address2, b.city, b.state, b.zipcode, b.countryid " + columnsToShow.toString() + " from tcustomer a, tcustomeraddress b "
				+ " where a.teamid=? and (a.timeentered between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + "    or a.editdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "    or a.userfield_updatedate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) " + "   and b.contactid=a.id and b.default_address=1 ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String V2newUpdatedContacts(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from tcustomer_userfields t, tuserfields n "
						+ " where t.customerid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1) ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = " select a.id contactid, a.firstname, a.lastname, timeentered datecreated, a.RECRUITERID USERID, editdate dateupdated, userfield_updatedate dateuserfieldupdated, a.companyid, a.companyname, a.departmentname department, a.title, a.active, "
				+ " a.email, a.workphone phone1, a.workphoneext phone1_extension, case when phonetypes is null or trim(phonetypes) is null then 'Work Phone' else decode(substr(phonetypes,1,1),"
				+ "    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Work Phone') end phone1_type, "
				+ "  cellphone phone2, cellphoneext phone2_extension, case when phonetypes is null or trim(phonetypes) is null then 'Mobile Phone' else decode(substr(phonetypes,2,1), "
				+ "    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Mobile Phone') end phone2_type, "
				+ "  homephone phone3, homephoneext phone3_extension, case when phonetypes is null or trim(phonetypes) is null then 'Home Phone' else decode(substr(phonetypes,3,1), "
				+ "    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Home Phone') end phone3_type, "
				+ "  contactfax phone4, contactfaxext phone4_extension, case when phonetypes is null or trim(phonetypes) is null then 'Home Fax' else decode(substr(phonetypes,4,1), "
				+ "    'W','Work Phone','H','Home Phone','C','Mobile Phone','F','Home Fax','X','Work Fax','P','Pager','M','Main Phone','D','Direct Phone','O','Other Phone',' ','Home Fax') end phone4_type, "
				+ "   b.address1, b.address2, b.city, b.state, b.zipcode, b.countryid " + columnsToShow.toString() + " from tcustomer a, tcustomeraddress b "
				+ " where a.teamid=? and (a.timeentered between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + "    or a.editdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "    or a.userfield_updatedate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) " + "   and b.contactid=a.id and b.default_address=1 ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedCompanyNotes(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.noteid, a.recruiterid userid, a.datecreated createdate, a.companyid, " + " substr(note,1,100) notefirst100chars " + " from tcustomercompanynotes a "
				+ " where a.recruiter_teamid =? and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and nvl(a.deleted,0) = 0 ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedContactNotes(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.noteid, " + " decode(a.type,1,'Last Attempt',2,'Last Reached',3,'Last Meeting',7,'Incoming Call',8,'Outgoing Call',b.name) actiontype, "
				+ " a.recruiterid userid, a.datemodified createdate, a.datemodified updatedate, case when a.type > 0 then a.datecreated else null end actiondate, "
				+ " a.customerid contactid, decode(a.rfqid,0,'',a.rfqid) jobid, decode(a.candidateid,0,'',a.candidateid) candidateid,  " + " substr(note,1,100) notefirst100chars " + " from tcustomernotes a, tactiontype b "
				+ " where a.recruiter_teamid =? and (a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "     or a.datemodified between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) " + "   and nvl(a.deleted,0) = 0 and a.type <> 6 " + "   and b.id(+) = a.type and b.teamid(+) = a.recruiter_teamid ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String deletedContactNotes(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.noteid, a.recruiterid userid, a.datedeleted " + " from tcustomernotes a, trecruiter b "
				+ " where a.recruiter_teamid =? and a.datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + " and a.recruiterid = b.id";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedJobs(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from trfq_userfields t, tuserfields n "
						+ " where t.rfqid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1) ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = " select /*+ no_merge(a) index_join(a IDX_TRFQ_1 IDX_TRFQ_UPDATE) */ " + "   a.id jobid, rfqno_team jobdivano, rfqrefno optionalreferenceno, divisionid, b.name divisionname, "
				+ " 	pr.recruiterid primaryrecruiterid, ps.recruiterid primarysalesid, " + " 	a.companyid, a.RECRUITERID USERID, c.name companyname, "
				+ " 	(select recruiterid from tcustomer_company_owners x where x.companyid=a.companyid AND x.TEAMID=a.TEAMID and x.isprimaryowner=1) primaryownerid, "
				+ " 	a.customerid contactid, d.firstname contactfirstname, d.lastname contactlastname, "
				+ " 	a.dateissued issuedate, a.datelastupdated dateupdated, a.userfield_updatedate dateuserfieldupdated, startdate, case when enddate < '01-Jan-1970' then null else enddate end enddate, "
				+ " 	nvl((select x.name from trfq_position_types x where x.teamid=a.teamid and x.id=a.contract), (select x.name from trfq_position_types x where x.teamid=0 and x.id=a.contract)) positiontype, "
				+ " 	nvl((select x.name from trfq_statuses x where x.teamid=a.teamid and x.id=a.jobstatus), (select x.name from trfq_statuses x where x.teamid=0 and x.id=a.jobstatus)) jobstatus, "
				+ " 	rfqtitle title, positions openings, fills, a.maxsubmitals maxallowedsubmittals, a.city, a.state, a.zipcode, "
				+ "   nvl((select name from tjob_priority p where p.teamid=a.teamid and p.id=a.jobpriority and p.deleteflag=0), decode(jobpriority,1,'A',2,'B',3,'C',4,'D','')) priority, "
				+ " 	billratemin, billratemax, decode(lower(billrateper),'d','Daily','y','Yearly','h','Hourly',null,null,"
				+ "			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=0 and x.unitid=billrateper)) billfrequency, "
				+ " 	ratemin payratemin, ratemax payratemax, decode(lower(rateper),'d','Daily','y','Yearly','h','Hourly',null,null,"
				+ "			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=1 and x.unitid=rateper)) payfrequency, " + " 	nvl((select x.name from tcurrency x where x.id=payrate_currency), "
				+ "   	nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency " + columnsToShow.toString()
				+ " from trfq a, tdivision b, tcustomercompany c, tcustomer d, trecruiterrfq pr, trecruiterrfq ps " + " where a.teamid =? and (a.dateissued between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "     or a.datelastupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + "     or a.userfield_updatedate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) "
				+ " and a.divisionid = b.id(+) and a.companyid = c.id(+) and a.customerid = d.id(+) " + " and a.id = pr.rfqid(+) and 1 = pr.lead_recruiter(+) " + " and a.id = ps.rfqid(+) and 1 = ps.lead_sales(+) ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedJobUsers(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String sql = " select a.jobid, a.dateupdated, b.recruiterid userid, c.firstname, c.lastname, " + "   sales, lead_sales primarysales, decode(sales+lead_sales,0,1,recruiter) recruiter, lead_recruiter primaryrecruiter, datelastassigned "
				+ " from " + " (select jobid, teamid, max(dateupdated) dateupdated " + " from tjob_userupdated " + " where teamid =?  and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ " group by jobid, teamid) a, trecruiterrfq b, trecruiter c " + " where b.rfqid=a.jobid and b.teamid=a.teamid and c.id=b.recruiterid ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String openJobsListByCompany(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String sql = " select a.id jobid, rfqno_team jobdivano, rfqrefno optionalreferenceno, divisionid, b.name divisionname, " + " 	pr.recruiterid primaryrecruiterid, ps.recruiterid primarysalesid, "
				+ " 	a.companyid, (select c.name from tcustomercompany c where c.id=a.companyid) companyname, " + " 	(select recruiterid from tcustomer_company_owners x where x.companyid=a.companyid and x.isprimaryowner=1) primaryownerid, "
				+ " 	a.customerid contactid, (select d.firstname||' '||d.lastname from tcustomer d where d.id=a.customerid) contactname, "
				+ " 	a.dateissued issuedate, datelastupdated dateupdated, startdate, case when enddate < '01-Jan-1970' then null else enddate end enddate, "
				+ " 	nvl((select x.name from trfq_position_types x where x.teamid=a.teamid and x.id=a.contract), (select x.name from trfq_position_types x where x.teamid=0 and x.id=a.contract)) positiontype, "
				+ " 	nvl((select x.name from trfq_statuses x where x.teamid=a.teamid and x.id=a.jobstatus), (select x.name from trfq_statuses x where x.teamid=0 and x.id=a.jobstatus)) jobstatus, "
				+ " 	rfqtitle title, positions openings, fills, a.maxsubmitals maxallowedsubmittals, a.city, a.state, a.zipcode, "
				+ "   nvl((select name from tjob_priority p where p.teamid=a.teamid and p.id=a.jobpriority and p.deleteflag=0), decode(jobpriority,1,'A',2,'B',3,'C',4,'D','')) priority, "
				+ " 	billratemin, billratemax, decode(lower(billrateper),'d','Daily','y','Yearly','h','Hourly',null,null,"
				+ "			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=0 and x.unitid=billrateper)) billfrequency, "
				+ " 	ratemin payratemin, ratemax payratemax, decode(lower(rateper),'d','Daily','y','Yearly','h','Hourly',null,null,"
				+ "			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=1 and x.unitid=rateper)) payfrequency, " + " 	nvl((select x.name from tcurrency x where x.id=payrate_currency), "
				+ "   	nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency " + " from trfq a, tdivision b, trecruiterrfq pr, trecruiterrfq ps "
				+ " where a.teamid =? and a.companyid=? and a.jobstatus = 0 and a.divisionid = b.id(+) " + " and a.id = pr.rfqid(+) and 1 = pr.lead_recruiter(+) " + " and a.id = ps.rfqid(+) and 1 = ps.lead_sales(+) ";
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
	
	public static String openJobsListByContact(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String sql = " select a.id jobid, rfqno_team jobdivano, rfqrefno optionalreferenceno, divisionid, b.name divisionname, " + " 	pr.recruiterid primaryrecruiterid, ps.recruiterid primarysalesid, "
				+ " 	a.companyid, (select c.name from tcustomercompany c where c.id=a.companyid) companyname, " + " 	(select recruiterid from tcustomer_company_owners x where x.companyid=a.companyid and x.isprimaryowner=1) primaryownerid, "
				+ " 	a.customerid contactid, (select d.firstname||' '||d.lastname from tcustomer d where d.id=a.customerid) contactname, "
				+ " 	a.dateissued issuedate, datelastupdated dateupdated, startdate, case when enddate < '01-Jan-1970' then null else enddate end enddate, "
				+ " 	nvl((select x.name from trfq_position_types x where x.teamid=a.teamid and x.id=a.contract), (select x.name from trfq_position_types x where x.teamid=0 and x.id=a.contract)) positiontype, "
				+ " 	nvl((select x.name from trfq_statuses x where x.teamid=a.teamid and x.id=a.jobstatus), (select x.name from trfq_statuses x where x.teamid=0 and x.id=a.jobstatus)) jobstatus, "
				+ " 	rfqtitle title, positions openings, fills, a.maxsubmitals maxallowedsubmittals, a.city, a.state, a.zipcode, "
				+ "   nvl((select name from tjob_priority p where p.teamid=a.teamid and p.id=a.jobpriority and p.deleteflag=0), decode(jobpriority,1,'A',2,'B',3,'C',4,'D','')) priority, "
				+ " 	billratemin, billratemax, decode(lower(billrateper),'d','Daily','y','Yearly','h','Hourly',null,null,"
				+ "			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=0 and x.unitid=billrateper)) billfrequency, "
				+ " 	ratemin payratemin, ratemax payratemax, decode(lower(rateper),'d','Daily','y','Yearly','h','Hourly',null,null,"
				+ "			(select x.name from trateunits x where x.teamid=a.teamid and x.ratetype=1 and x.unitid=rateper)) payfrequency, " + " 	nvl((select x.name from tcurrency x where x.id=payrate_currency), "
				+ "   	nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency " + " from trfq a, tdivision b, trecruiterrfq pr, trecruiterrfq ps "
				+ " where a.teamid =? and a.customerid=? and a.jobstatus = 0 and a.divisionid = b.id(+) " + " and a.id = pr.rfqid(+) and 1 = pr.lead_recruiter(+) " + " and a.id = ps.rfqid(+) and 1 = ps.lead_sales(+) ";
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
	
	public static String newUpdatedTasks(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select id, recruiterid, (select contactid from tevent_contacts where eventid=id and rownum=1) customerid, candidateid, creatorid, " + "   eventdate, title, decode(optional,2,'No',4,'Yes','') completed, "
				+ "	(select b.firstname||' '||b.lastname from trecruiter b where b.id=tevent.recruiterid) assignedto, " + "	(select b.firstname||' '||b.lastname from trecruiter b where b.id=tevent.creatorid) createdby, "
				+ "	case when result is null or length(trim(result))=0 " + "	then (select typename from ttask_type t where t.teamid=tevent.teamid and t.id=tevent.task_type) "
				+ "	else (select typename from ttask_type_candidate t where t.teamid=tevent.teamid and t.id = task_type) end tasktype, " + "	decode(private,1,'Yes','No') private, datecreated, lastmodified " + " from tevent "
				+ " where teamid=? and optional in (2,4) and status_new='ACTIVE' " + "   and (datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "   or lastmodified between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedEvents(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select id, recruiterid, (select contactid from tevent_contacts where eventid=id and rownum=1) customerid, "
				+ "   candidateid, activityid, (select t.typename from teventtype t where t.teamid=tevent.teamid and t.id=tevent.eventtype) eventtype, location, title, notes, "
				+ "	decode(private,1,'Yes','No') private, datecreated, lastmodified, eventdate, timezone, duration, dtend enddate, " + "   rrule, rdate, exrule, exdate " + " from tevent "
				+ " where teamid=? and optional in (0,1,3) and status_new='ACTIVE' " + "   and (datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "   or lastmodified between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String deletedTasks(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select id, lastmodified datedeleted " + " from tevent " + " where teamid=? and optional in (2,4) and status_new='CANCELLED' "
				+ "   and lastmodified between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedCandidates(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value " + " from tcandidate_userfields t, tuserfields n "
						+ " where t.candidateid=a.id and t.teamid=a.teamid and n.teamid=a.teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i]) + "' AND (ROWNUM = 1) ) " + "\""
						+ (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = " select /*+ no_merge(a) index_join(a IDX_TCANDIDATE_DATERECEIVED IDX_TCANDIDATE_MANUAL IDX_TCAND_UFD_UPDATE) */ "
				+ "   a.id candidateid, decode(bitand(nvl(privacyapplied,0), 1), 0, firstname, b.text) firstname, decode(bitand(nvl(privacyapplied,0), 1), 0, lastname, b.text) lastname,"
				+ " datereceived datecreated, dateupdated dateupdated, dateupdated_profile dateprofileupdated, userfield_updatedate dateuserfieldupdated, "
				+ " decode(bitand(nvl(privacyapplied,0), 16), 0, email, b.text) email, decode(bitand(nvl(privacyapplied,0), 16), 0, sysemail, b.text) alternateemail, "
				+ " decode(bitand(nvl(privacyapplied,0), 2), 0, address1, b.text) address1, decode(bitand(nvl(privacyapplied,0), 2), 0, address2, b.text) address2,"
				+ " decode(bitand(nvl(privacyapplied,0), 4), 0, city, b.text) city, decode(bitand(nvl(privacyapplied,0), 4), 0, state, b.text) state, decode(bitand(nvl(privacyapplied,0), 4), 0, zipcode, b.text) zipcode,"
				+ " decode(bitand(nvl(privacyapplied,0), 8), 0, workphone, b.text) workphone, decode(bitand(nvl(privacyapplied,0), 8), 0, workphone_ext, b.text) workphone_ext, "
				+ " decode(bitand(nvl(privacyapplied,0), 8), 0, homephone, b.text) homephone,  decode(bitand(nvl(privacyapplied,0), 8), 0, cellphone, b.text) cellphone," + " decode(bitand(nvl(privacyapplied,0), 8), 0, fax, b.text) fax "
				+ columnsToShow.toString() + " from tcandidate a, (select text from (select text from gdpr_inaccessibility_text where teamid = ? or teamid = 0 order by teamid desc) where rownum = 1) b"
				+ " where (a.teamid =? and a.datereceived between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) "
				+ "     or (a.teamid =? and a.dateupdated_manual between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) "
				+ "     or (a.teamid =? and a.userfield_updatedate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) "
				+ "     or (a.teamid =? and a.dateupdated_profile between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) ";
		// System.out.println("BIData sql =" + sql);
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
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedCandidateHR(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select a.candidateid, a.legal_firstname, a.legal_middlename, a.legal_lastname, a.generationsuffix, a.ssn, to_char(a.dateofbirth, 'mm/dd/yyyy') as dateofbirth,"
				+ " decode(a.maritalstatus, 1, 'Civil Partnership', 2, 'Divorced', 3, 'Separated', 4, 'Legally Separated', 5, 'Married', 6, 'Domestic Partner', 7, 'Single', 8, 'Widowed', '') as martialstatus, "
				+ " a.visastatus, (select min(date_created) from tcandidate_hr_management b where b.teamid = a.teamid and candidateid = a.candidateid) datecreated,"
				+ " (select max(date_created) from tcandidate_hr_management b where b.teamid = a.teamid and candidateid = a.candidateid) dateupdated," + " a.userfield_updatedate dateuserfieldupdated" + " from tcandidate_hr a, tcandidate c "
				+ " where a.teamid = ? and a.candidateid in (select candidateid from tcandidate_hr_management where teamid = ? and date_created between " + " to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss'))"
				+ " and a.candidateid = c.id and a.teamid = c.teamid and nvl(c.PRIVACYAPPLIED,0) = 0" + " union all"
				+ " select a.candidateid, d.text, d.text, d.text, d.text, d.text, d.text, d.text, d.text, (select min(date_created) from tcandidate_hr_management b where b.teamid = a.teamid and candidateid = a.candidateid) datecreated, "
				+ " (select max(date_created) from tcandidate_hr_management b where b.teamid = a.teamid and candidateid = a.candidateid) dateupdated, a.userfield_updatedate dateuserfieldupdated"
				+ " from tcandidate_hr a , tcandidate c , (select text from (select text from gdpr_inaccessibility_text where teamid = ? or teamid = 0 order by teamid desc) where rownum = 1) d"
				+ " where a.teamid = ? and a.candidateid in (select candidateid from tcandidate_hr_management where teamid = ? " + " and date_created between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss'))"
				+ " and a.candidateid = c.id and a.teamid = c.teamid and nvl(c.PRIVACYAPPLIED,0) > 0";
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedEmployees(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.candidateid candidateid, decode(dcatid, 1, 'Current', 2, 'Past', 3, 'Direct Placement') status, " + " datecreated DATE_MODIFIED" + " from tcandidate_category a "
				+ " where a.teamid = ? and catid=1 and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')" + "   and dirty <> 2 ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedActivities(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param, Map<String, String> colNameToAliasMap) {
		StringBuffer columnsToShow = new StringBuffer();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				columnsToShow.append(", (select " + " case when n.fieldtypeid = 3  " + " then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + "      when n.fieldtypeid = 4 "
						+ " then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + " else t.userfield_value end userfield_value "
						+ " from tstartrecord_userfields t, tuserfields n " + " where t.startid=a.id and t.teamid=a.recruiter_teamid and n.teamid=a.recruiter_teamid and n.id=t.userfield_id and n.fieldname='" + StringUtils.escapeSql(params[i])
						+ "' AND (ROWNUM = 1) ) " + "\"" + (colNameToAliasMap.containsKey(params[i]) ? colNameToAliasMap.get(params[i]) : params[i]) + "\"");
			}
		}
		String sql = " select a.id activityid, a.datecreated, a.dateupdated, a.userfield_updatedate dateuserfieldupdated, " + " a.recruiterid userid, a.RECRUITERID_CREATOR UPDATEDBY_USERID, a.primarysalesid, a.candidateid, "
				+ " 	a.rfqid jobid, decode(a.customerid,0,'',a.customerid) contactid, roleid, b.companyid, " + " case when a.id = (select min(x.id) from tinterviewschedule x "
				+ "                   where x.candidateid=a.candidateid and x.rfqid=a.rfqid and nvl(x.roleid,0) < 900)" + "      then 1 else 0 end submittalflag,  "
				+ " case when daterejected is not null or extdaterejected is not null then 1 else 0 end rejectflag, " + " case when a.dateinterview is not null then 1 else 0 end interviewflag, "
				+ " case when a.datehired is not null then 1 else 0 end hireflag, " + " case when a.id = (select min(x.id) from tinterviewschedule x "
				+ "                   where x.candidateid=a.candidateid and x.rfqid=a.rfqid and nvl(x.roleid,0) < 900) " + "      then a.datepresented else null end submittaldate, "
				+ " case when roleid > 900 then daterejected else extdaterejected end rejectdate, " + " case when EXTREJECTREASONID is not null and EXTREJECTREASONID > 0 "
				+ "      then (select reason from TREJECTION_REASONS rejreason where rejreason.teamid=a.recruiter_teamid and rejreason.id=a.EXTREJECTREASONID) " + "      else null end rejectreason, "
				+ " a.dateinterview interviewdate, interview_timezoneid, a.datehired startdate, a.date_ended enddate, a.dateterminated terminationdate, " + " case when a.reasonterminated > 0 "
				+ "      then (select description from treason_termination x where x.teamid=a.recruiter_teamid and x.id=a.reasonterminated) " + "      else null end terminationreason, "
				+ " nvl((select x.name from trfq_position_types x where x.teamid=a.recruiter_teamid and x.id=a.contract), (select x.name from trfq_position_types x where x.teamid=0 and x.id=a.contract)) positiontype, "
				+ " case when a.datehired is not null then a.pay_hourly else null end agreedbillrate, " + " case when a.datehired is not null and a.finalbillrateunit is not null "
				+ "      then decode(a.finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', " + "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=1 and x.unitid=a.finalbillrateunit)) "
				+ "      else null end billfrequency, " + " case when a.datehired is not null then decode(contract,1,a.yearly,a.hourly) else null end agreedpayrate, "
				+ " case when a.datehired is not null then decode(contract,1,a.yearly_corporate,a.hourly_corporate) else null end corptocorp, " + " case when a.datehired is not null and a.payrateunits is not null "
				+ "      then decode(case when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end,'h','Hourly','d','Daily','y','Yearly', "
				+ "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=0 and x.unitid=(case when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end))) "
				+ "      else null end payfrequency, " + " case when a.datehired is not null " + "      then nvl((select x.name from tcurrency x where x.id=a.hourly_currency), "
				+ "             nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) " + "      else null end currency, "
				+ " case when a.datehired is not null and a.contract=1 then decode(fee_type,0,'%',1,'Flat Amount','') else '' end fee_type, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=0 then fee/100 when a.datehired is not null and contract=1 and fee_type=1 then fee/yearly else null end fee_percent, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=1 then (case when fee < 0 then 0 else fee end) "
				+ "      when a.datehired is not null and contract=1 and fee_type=0 then yearly*(case when fee < 0 then 0 else fee end)/100 else null end fee " + columnsToShow.toString() + " from tinterviewschedule a, tcustomer b "
				+ " where a.recruiter_teamid = ? and (a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "   or a.dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + "   or a.userfield_updatedate between  to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) "
				+ "   and b.id(+) = a.customerid ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedSIHActivitieslist(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String sql = " select a.id activityid, a.datecreated, a.dateupdated, a.userfield_updatedate dateuserfieldupdated, a.placementdate, a.interviewscheduledate, " + "   a.recruiterid userid, f.firstname userfirstname, f.lastname userlastname, "
				+ "   a.primarysalesid, g.firstname primarysalesfirstname, g.lastname primarysaleslastname, " + "   a.candidateid, b.firstname candidatefirstname, b.lastname candidatelastname, b.email candidateemail, "
				+ " 	a.rfqid jobid, c.rfqno_team jobreferencenumber, c.rfqtitle jobtitle, " + "   decode(a.customerid,0,'',a.customerid) contactid, d.firstname contactfirstname, d.lastname contactlastname,"
				+ " 	e.id companyid, e.name companyname, " + " case when a.roleid > 900 then 1 else 0 end internalsubmittalflag, " + " case when a.id = (select min(x.id) from tinterviewschedule x "
				+ "                   where x.candidateid=a.candidateid and x.rfqid=a.rfqid and nvl(x.roleid,0) < 900)" + "      then 1 else 0 end externalsubmittalflag,  "
				+ " case when daterejected is not null or extdaterejected is not null then 1 else 0 end rejectflag, " + " case when a.dateinterview is not null then 1 else 0 end interviewflag, "
				+ " case when a.datehired is not null then 1 else 0 end hireflag, " + " case when a.roleid > 900 or a.id = (select min(x.id) from tinterviewschedule x "
				+ "                   where x.candidateid=a.candidateid and x.rfqid=a.rfqid and nvl(x.roleid,0) < 900) " + "      then a.datepresented else null end submittaldate, "
				+ " case when a.roleid > 900 then a.daterejected else null end internalrejectdate, " + " case when nvl(a.roleid,0) < 900 then a.extdaterejected else null end externalrejectdate, "
				+ " case when EXTREJECTREASONID is not null and EXTREJECTREASONID > 0 " + "      then (select reason from TREJECTION_REASONS rejreason where rejreason.teamid=a.recruiter_teamid and rejreason.id=a.EXTREJECTREASONID) "
				+ "      else null end rejectreason, " + " a.dateinterview interviewdate, interview_timezoneid, a.datehired startdate, a.date_ended enddate, a.dateterminated terminationdate, " + " case when a.reasonterminated > 0 "
				+ "      then (select description from treason_termination x where x.teamid=a.recruiter_teamid and x.id=a.reasonterminated) " + "      else null end terminationreason, "
				+ " (CASE WHEN a.PAY_HOURLY>0 THEN a.PAY_HOURLY WHEN a.PAY_DAILY>0 THEN PAY_DAILY ELSE 0 END) agreedbillrate, "
				+ " (case when a.hourly > 0 then a.hourly_corporate when a.daily > 0 then a.daily_corporate else a.yearly_corporate end) corptocorp, " + " case when a.finalbillrateunit is not null "
				+ "    then decode(a.finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', " + "    (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=1 and x.unitid=a.finalbillrateunit)) "
				+ "    else null end billfrequency, " + " (CASE WHEN a.HOURLY>0 THEN a.HOURLY WHEN a.DAILY>0 THEN a.DAILY WHEN a.YEARLY>0 THEN a.YEARLY ELSE 0 END) agreedpayrate, " + " case when a.payrateunits is not null "
				+ "    then decode(case when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end,'h','Hourly','d','Daily','y','Yearly', "
				+ "    (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=0 and x.unitid=(case when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end))) "
				+ "    else null end payfrequency, " + " nvl((select x.name from tcurrency x where x.id=a.hourly_currency), "
				+ "    nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) " + "	 currency, "
				+ " case when a.datehired is not null and a.contract=1 then decode(a.fee_type,0,'%',1,'Flat Amount','') else '' end fee_type, "
				+ " case when a.datehired is not null and a.contract=1 and a.fee_type=0 then a.fee/100 when a.datehired is not null and a.contract=1 and a.fee_type=1 then a.fee/yearly else null end fee_percent, "
				+ " case when a.datehired is not null and a.contract=1 and a.fee_type=1 then (case when a.fee < 0 then 0 else a.fee end) "
				+ "      when a.datehired is not null and a.contract=1 and a.fee_type=0 then yearly*(case when a.fee < 0 then 0 else a.fee end)/100 else null end fee "
				+ " from tinterviewschedule a, tcandidate b, trfq c, tcustomer d, tcustomercompany e, trecruiter f, trecruiter g "
				+ " where a.recruiter_teamid = ? and (a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "   or a.dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + "   or a.userfield_updatedate between  to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) "
				+ "   and a.candidateid = b.id and b.teamid=a.recruiter_teamid and a.rfqid = c.id and a.customerid = d.id(+) and c.companyid = e.id(+) " + "   and a.recruiterid = f.id and a.primarysalesid = g.id(+) ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newHires(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select id placementid from tinterviewschedule where recruiter_teamid=? and placementdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and datehired is not null ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String updatedHires(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select id placementid from tinterviewschedule where recruiter_teamid=? and dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and datehired is not null ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedCandidateNotes(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.noteid, " + " 	decode(a.type,1,'Last Attempt',2,'Last Reached',3,'Last Meeting',4,'Attribute',5,'Qualification'," + " 			6,'References Checked',7,'Incoming Call',8,'Outgoing Call',b.name) actiontype, "
				+ " 	a.recruiterid userid, a.datecreated createdate, a.dateaction actiondate, " + " 	a.candidateid, decode(a.rfqid,0,'',a.rfqid) jobid, decode(a.contactid,0,'',a.contactid) contactid, "
				+ " 	case when note_clob is null then substr(note,1,100) else to_char(substr(note_clob,1,100)) end notefirst100chars " + " from tcandidatenotes a, tactiontype_candidate b "
				+ " where a.recruiter_teamid = ? and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ " and (a.auto = 0 or a.auto = 3) and nvl(a.deleted,0) = 0 and a.type = b.id(+) and a.recruiter_teamid=b.teamid(+) ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String deletedCandidates(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select candidateid, datedeleted " + " from tcandidate_deleted a " + " where a.teamid=? and a.datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String deletedCandidateNotes(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.noteid, a.datedeleted " + " from tcandidatenotes a " + " where a.recruiter_teamid =? and a.datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String deletedCompanies(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select companyid, datedeleted " + " from tcustomercompany_deleted " + " where teamid=? and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String deletedContacts(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select customerid contactid, datedeleted " + " from tcustomer_deleted " + " where teamid=? and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String deletedEmployees(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.candidateid, a.datedeleted " + " from tcandidatequal_deleted a where a.teamid = ? " + " and a.datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and a.catid=1 ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String deletedActivities(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select activityid, datedeleted " + " from tinterviewschedule_deleted " + " where teamid = ? and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String deletedJobs(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select rfqid jobid, datedeleted " + " from trfq_deleted " + " where teamid=? and datedeleted between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String mergedCompanies(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select companyid, mergetocompanyid, recruiterid userid, datemerged " + " from tcompany_merge " + " where teamid=? and datemerged between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String mergedContacts(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select contactid, mergetocontactid, recruiterid userid, datemerged " + " from tcustomer_merge " + " where teamid=? and datemerged between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String mergedJobs(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select rfqid jobid, mergetorfqid mergetojobid, recruiterid userid, datemerged " + " from trfq_merge " + " where teamid = ? and datemerged between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String mergedCandidates(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select distinct candidateid_gone candidateid, candidateid_new mergedtocandidateid, replace(to_char(datecreated, 'yyyy-mm-dd hh24:mi:ss'), ' ', 'T')||'.0' datemerged " + " from tworkexp_stuff a "
				+ " where a.teamid=? and a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + " and candidateid_new is not null ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String candidateApplicationRecords(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select a.candidateid, decode(bitand(nvl(c.privacyapplied,0), 1), 0, c.firstname, g.text) firstname, decode(bitand(nvl(c.privacyapplied,0), 1), 0, c.lastname, g.text) lastname,"
				+ "  decode(bitand(nvl(c.privacyapplied,0), 16), 0, c.email, g.text) email, a.rfqid jobid, a.dateapplied, a.global_id resumeid, "
				+ "   case when bitand(nvl(c.privacyapplied,0), 16) = 0 then decode(a.resume_source_name,'JOBDIVA',(select company from tteam where id=a.teamid),a.resume_source_name) else to_nchar(g.text) end resumesource,"
				+ "   case when r.daterejected is not null then 'Rejected' " + "        when exists (select 1 from tinterviewschedule i where i.candidateid=a.candidateid and i.rfqid=a.rfqid) then 'Submitted'" + "   else null end action, "
				+ "   case when r.daterejected is not null then r.recruiterid " + "   else (select i.recruiterid from tinterviewschedule i where i.candidateid=a.candidateid and i.rfqid=a.rfqid "
				+ "      and i.id = (select min(j.id) from tinterviewschedule j where j.candidateid=a.candidateid and j.rfqid=a.rfqid)) " + "   end actionuserid, " + "   case when r.daterejected is not null then r.daterejected "
				+ "   else (select min(datepresented) from tinterviewschedule i where i.candidateid=a.candidateid and i.rfqid=a.rfqid) " + "   end actiondate, "
				+ "   (select nvl(f.displayname, h.resumesourcerefname) from tcandidatedocument_header h, tresumesourceref f "
				+ "    where h.candidateid=a.candidateid and h.docid=a.docid and h.teamid=a.teamid and h.resumesourceflag=1 and f.id(+)=h.resumesourcerefid) referrer "
				+ " from tcandidate_applyforjob a, tcandidate c, trejection r, (select text from (select text from gdpr_inaccessibility_text where teamid = ? or teamid = 0 order by teamid desc) where rownum = 1) g "
				+ " where a.teamid = ? and a.dateapplied between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and a.dirty = 0 " + "   and c.id=a.candidateid and c.teamid=a.teamid "
				+ "   and r.candidateid(+)=a.candidateid and r.recruiter_teamid(+)=a.teamid and r.rfqid(+)=a.rfqid ";
		param.add(clientID);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newApprovedBillingRecords(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = " select employeeid, recid, datecreated_real datecreated " + " from temployee_billingrecord " + " where recruiter_teamid=? and datecreated_real between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ " and approved=1 and (closed is null or closed = 0) " + (restriction != null && restriction.length > 0 && restriction[0] != null ? " and division=? " : "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String newApprovedSalaryRecords(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = " select employeeid, recid, datecreated_real datecreated " + " from temployee_salaryrecord a " + " where recruiter_teamid=? and datecreated_real between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ " and approved=1 and (closed is null or closed = 0) "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null
						? " and exists (select 1 from temployee_billingrecord b where b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.interviewid=a.interviewid and b.division=?) "
						: "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String updatedApprovedBillingRecords(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = " select employeeid, recid, datecreated_real datecreated, datecreated dateupdated " + " from temployee_billingrecord "
				+ " where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + " and approved=1 and (closed is null or closed = 0) "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null ? " and division=? " : "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String updatedApprovedSalaryRecords(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = " select employeeid, recid, datecreated_real datecreated, datecreated dateupdated  " + " from temployee_salaryrecord a "
				+ " where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + " and approved=1 and (closed is null or closed = 0) "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null
						? " and exists (select 1 from temployee_billingrecord b where b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.interviewid=a.interviewid and b.division=?) "
						: "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String newUpdatedBillingRecords(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = " select a.EMPLOYEEID,a.RECID,a.RFQID JOBID,a.INTERVIEWID ACTIVITYID,nvl(a.APPROVED, 0) ARRPOVED,nvl(a.CLOSED, 0) DELETED,a.START_DATE,a.END_DATE,a.ACTUALSTART,a.ACTUALEND, "
				+ "   a.DATECREATED_REAL DATECREATED,a.DATECREATED DATEUPDATED,a.CREATED_BY,a.APPROVERID, " + "   a.PRIMARY_SALESPERSON,a.SECONDARY_SALESPERSON,a.TERTIARY_SALESPERSON,a.PRIMARY_RECRUITER,a.SECONDARY_RECRUITER,a.TERTIARY_RECRUITER, "
				+ "   a.BILLING_CONTACT,a.HIRING_MANAGER,a.CUSTOMER_REFNO,a.JOBDIVA_REFNO,a.FREQUENCY,a.WEEK_ENDING, " + "   a.BILL_RATE,a.BILL_RATE_PER,a.OVERTIME_RATE1,a.OVERTIME_RATE1_PER,a.OVERTIME_RATE2,a.OVERTIME_RATE2_PER,a.HOURS_PER_DAY, "
				+ "   a.WORKING_CITY,a.WORKING_STATE,a.WORKING_COUNTRY,a.WORK_ADDRESS1,a.WORK_ADDRESS2,a.WORK_CITY,a.WORK_STATE,a.WORK_ZIP,a.WORK_COUNTRY, "
				+ "   a.PASS_DISCOUNT,a.BILLING_UNIT,a.HOURS_PER_HALF_DAY,a.DISCOUNT_PERCENT,a.VMS_WEBSITE,a.PASS_THROUGH, "
				+ "   a.PRISALE_COMM_PERCENT,a.SECSALE_COMM_PERCENT,a.TERSALE_COMM_PERCENT,a.PRIREC_COMM_PERCENT,a.SECREC_COMM_PERCENT,a.TERREC_COMM_PERCENT, "
				+ "   a.PAYMENTTERMS,a.EXPENSEENABLED,a.POID,b.AMOUNT POAMOUNT,b.START_DATE PO_START_DATE,b.END_DATE PO_END_DATE,a.OVERTIMEEXEMPT,a.DIVISION,a.TIMESHEET_ENTRY_FORMAT,a.CUSTOMER_REFNO_SUB,a.TIMESHEET_INSTRUCTION "
				+ "   from temployee_billingrecord a, tpo_setting b " + "   where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "   and b.teamid(+)=a.recruiter_teamid and b.id(+)=a.poid " + (restriction != null && restriction.length > 0 && restriction[0] != null ? " and a.division=? " : "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String newUpdatedSalaryRecords(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = " select EMPLOYEEID,RECID,RFQID JOBID,INTERVIEWID ACTIVITYID,EFFECTIVE_DATE,END_DATE,nvl(APPROVED,0) APPROVED,nvl(CLOSED, 0) DELETED, " + "   DATECREATED_REAL DATECREATED,DATECREATED DATEUPDATED,CREATED_BY, "
				+ "   STATUS,PER_DIEM,PER_DIEM_PER,OUTSIDE_COMMISSION,OUTSIDE_COMMISSION_PER,OTHER_EXPENSES,OTHER_EXPENSES_PER, " + "   INSURED,SALARY,SALARY_PER,OVERTIME_RATE1,OVERTIME_RATE1_PER,OVERTIME_RATE2,OVERTIME_RATE2_PER,OVERTIMEEXEMPT, "
				+ "   SUBCONTRACT_COMPANYID,SUBCONTRACT_PAYONREMIT," + "   OH_W2,OH_INSURANCE,OH_VACATION,OH_CORP_INSURANCE,OH_401K,OH_C2C,OH_PERDIEM, "
				+ "   TAXID,PAYMENTTERMS,BIRTHDAY,VISA_STATUS,SSN,VACATION,COMMENTS,ADP_FILE_NO,  PAYMENT_FREQUENCY " + // EMPLOYMENT_CATEGORY,
				" from temployee_salaryrecord a " + " where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null
						? " and exists (select 1 from temployee_billingrecord b where b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.interviewid=a.interviewid and b.division=?) "
						: "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String billingRecordDetail(Long clientID, String[] params, Vector<Object> param, String[] restriction) {
		StringBuffer discountColumns = new StringBuffer();
		StringBuffer activityUdfColumns = new StringBuffer();
		if (params != null) {
			String[] ary = null;
			for (int i = 2; i < params.length; i++) {
				ary = params[i].split("_");
				if (ary[0].equals("discount")) {
					discountColumns.append(", (select discount || (case when discount_unit = '%' then '' else '/' end) || discount_unit from temployee_billingdiscount d where a.recruiter_teamid = d.teamid " + //
							"	and a.employeeid = d.employeeid and a.recid = d.recid and discountid = " + //
							ary[1] + ") \"" + StringUtils.escapeSql(ary[2]) + "\" ");
				} else if (ary[0].equals("activityUdf")) {
					activityUdfColumns.append( //
							", (select " + //
									" case when n.fieldtypeid = 3 " + //
									" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + //
									"      when n.fieldtypeid = 4 " + //
									" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + //
									" else t.userfield_value end userfield_value " + //
									" from tstartrecord_userfields t join tuserfields n on t.teamid = n.teamid and t.userfield_id = n.id " + //
									" join tinterviewschedule i on t.teamid = i.recruiter_teamid and t.startid = i.id " + //
									" where t.teamid = a.recruiter_teamid and i.candidateid = a.employeeid and i.id = a.interviewid " + //
									"and n.id=" + ary[1] + ") \"" + StringUtils.escapeSql(ary[2]) + "\"");
				}
			}
		}
		String sql = " select a.EMPLOYEEID,a.RECID,a.RFQID JOBID,a.INTERVIEWID ACTIVITYID,a.APPROVED,a.CLOSED," + //
				"   TO_CHAR(a.START_DATE,'mm/dd/yyyy') START_DATE,TO_CHAR(a.END_DATE,'mm/dd/yyyy') END_DATE,a.ACTUALSTART,a.ACTUALEND, " + //
				"   a.DATECREATED_REAL DATECREATED,a.DATECREATED DATEUPDATED,a.CREATED_BY,a.APPROVERID, " + //
				"   a.PRIMARY_SALESPERSON,a.SECONDARY_SALESPERSON,a.TERTIARY_SALESPERSON,a.PRIMARY_RECRUITER,a.SECONDARY_RECRUITER,a.TERTIARY_RECRUITER, " + //
				"   a.BILLING_CONTACT, " + //
				"   (SELECT cc.NAME FROM TCUSTOMER c JOIN TCUSTOMERCOMPANY cc ON c.TEAMID = cc.TEAMID AND c.COMPANYID = cc.ID " + //
				"        WHERE a.BILLING_CONTACT = c.ID AND a.RECRUITER_TEAMID = c.TEAMID) COMPANY_NAME, " + //
				"    (SELECT c.FIRSTNAME||' '||c.LASTNAME " + //
				"        FROM TCUSTOMER c WHERE a.RECRUITER_TEAMID = c.TEAMID AND a.HIRING_MANAGER = c.ID) CLIENT_CONTACT, " + //
				"   a.HIRING_MANAGER,a.JOBDIVA_REFNO, a.FREQUENCY, " + //
				"   DECODE(a.FREQUENCY,'1','Bi-Weekly','2','Monthly','4','Weekly','') FREQUENCY_LABEL, " + //
				"   a.WEEK_ENDING, " + //
				"   (SELECT b.NAME FROM TTEAM_CURRENCY t JOIN TCURRENCY b ON t.CURRENCYID = b.ID WHERE t.TEAMID = a.RECRUITER_TEAMID AND t.DEFAULTCURRENCY = 1) currency, " + //
				"   a.BILL_RATE,a.BILL_RATE_PER,a.OVERTIME_RATE1,a.OVERTIME_RATE1_PER,a.OVERTIME_RATE2,a.OVERTIME_RATE2_PER,a.HOURS_PER_DAY, " + //
				"   a.WORKING_CITY,a.WORKING_STATE,a.WORKING_COUNTRY,a.WORK_ADDRESS1,a.WORK_ADDRESS2,a.WORK_CITY,a.WORK_STATE,a.WORK_ZIP,a.WORK_COUNTRY, " + //
				"   a.PASS_DISCOUNT,a.BILLING_UNIT,a.HOURS_PER_HALF_DAY," + //
				"   (select discount from temployee_billingdiscount d where a.recruiter_teamid = d.teamid " + //
				"     and a.employeeid = d.employeeid and a.recid = d.recid and d.discountid = 0) DISCOUNT_PERCENT," + //
				"   (select discount_unit from temployee_billingdiscount d where a.recruiter_teamid = d.teamid " + //
				"     and a.employeeid = d.employeeid and a.recid = d.recid and d.discountid = 0) DISCOUNT_UNIT " + //
				discountColumns.toString() + //
				"   , a.VMS_WEBSITE,a.PASS_THROUGH, " + //
				"   a.PRISALE_COMM_PERCENT,a.SECSALE_COMM_PERCENT,a.TERSALE_COMM_PERCENT,a.PRIREC_COMM_PERCENT,a.SECREC_COMM_PERCENT,a.TERREC_COMM_PERCENT, " + //
				"   a.PAYMENTTERMS,a.EXPENSEENABLED,a.POID,b.AMOUNT POAMOUNT,b.START_DATE PO_START_DATE,b.END_DATE PO_END_DATE,a.OVERTIMEEXEMPT,a.DIVISION,a.TIMESHEET_ENTRY_FORMAT,a.CUSTOMER_REFNO_SUB,a.TIMESHEET_INSTRUCTION, " + //
				"   decode(a.ot_by_working_state,0,'Manually Distribute',1,'Auto-Calculate by Working State',2,'Auto-Calculate by Federal 40-Hour Rule', " + //
				"	3,'Auto-Calculate by 4/10 Work Week','') OVERTIME, " + //
				"nvl((select x.symbol from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid " + //
				"    and y.defaultcurrency=1 and y.currencyid=x.id), '$') || " + //
				"nvl(round((case when a.bill_rate_per = 'D' then a.bill_rate/a.hours_per_day when a.bill_rate_per = 'Y' " + //
				"	then a.bill_rate/e.days_per_year/a.hours_per_day else a.bill_rate end) - " + //
				"(select sum(case when d.discount_unit='%' then (case when a.bill_rate_per='D' and a.hours_per_day <> 0 " + //
				"    then a.bill_rate/a.hours_per_day when a.bill_rate_per='Y' and a.hours_per_day <> 0 then a.bill_rate/e.days_per_year/a.hours_per_day " + //
				"    else a.bill_rate end)*d.discount/100 when a.bill_rate_per in ('W','B','M') then 0 when d.discount_unit='d' and a.hours_per_day <> 0 " + //
				"    then d.discount/a.hours_per_day when d.discount_unit='y' and a.hours_per_day <> 0 then d.discount/e.days_per_year/a.hours_per_day " + //
				"    else d.discount end) from temployee_billingdiscount d where d.teamid=a.recruiter_teamid and d.employeeid=a.employeeid " + //
				"    and d.recid=a.recid),2),0) || decode(a.bill_rate_per,'W','/W - Flat','B','/BW - Flat','M','/M - Flat','/H') net_bill, " + //
				"nvl((select x.symbol from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid " + //
				"    and y.defaultcurrency=1 and y.currencyid=x.id), '$') || " + //
				"nvl(round((select sum(case when d.discount_unit='%' then (case when a.bill_rate_per='D' and a.hours_per_day <> 0 " + //
				"    then a.bill_rate/a.hours_per_day when a.bill_rate_per='Y' and a.hours_per_day <> 0 then a.bill_rate/e.days_per_year/a.hours_per_day " + //
				"    else a.bill_rate end)*d.discount/100 when a.bill_rate_per in ('W','B','M') then 0 when d.discount_unit='d' and a.hours_per_day <> 0 " + //
				"    then d.discount/a.hours_per_day when d.discount_unit='y' and a.hours_per_day <> 0 then d.discount/e.days_per_year/a.hours_per_day " + //
				"    else d.discount end) from temployee_billingdiscount d where d.teamid=a.recruiter_teamid and d.employeeid=a.employeeid " + //
				"    and d.recid=a.recid),2),0) || decode(a.bill_rate_per,'W','/W - Flat','B','/BW - Flat','M','/M - Flat','/H') discount " + //
				activityUdfColumns.toString() + //
				" from temployee_billingrecord a, tpo_setting b, tteam_billing_variables e " + //
				" where employeeid=? and recruiter_teamid=? and recid=? and b.teamid(+)=a.recruiter_teamid and a.recruiter_teamid=e.teamid(+) and b.id(+)=a.poid " + //
				(restriction != null && restriction.length > 0 && restriction[0] != null ? " and a.division=? " : "");
		param.add(new Long(params[0]));
		param.add(clientID);
		param.add(new Integer(params[1]));
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String billingRecordsDetail(Long clientID, String[] params, Vector<Object> param, String[] restriction) {
		String sql = " select a.EMPLOYEEID,a.RECID,a.RFQID JOBID,a.INTERVIEWID ACTIVITYID,a.APPROVED,a.CLOSED," + //
				"	to_char(a.START_DATE, 'mm/dd/yyyy') START_DATE,to_char(a.END_DATE, 'mm/dd/yyyy') END_DATE,a.ACTUALSTART,a.ACTUALEND, " + //
				"   a.DATECREATED_REAL DATECREATED,a.DATECREATED DATEUPDATED,a.CREATED_BY,a.APPROVERID, " + //
				"   a.PRIMARY_SALESPERSON,a.SECONDARY_SALESPERSON,a.TERTIARY_SALESPERSON,a.PRIMARY_RECRUITER,a.SECONDARY_RECRUITER,a.TERTIARY_RECRUITER, " + //
				"   a.BILLING_CONTACT,a.HIRING_MANAGER,a.CUSTOMER_REFNO_SUB CUSTOMER_REFNO,a.JOBDIVA_REFNO,a.FREQUENCY,a.WEEK_ENDING, " + //
				"   a.BILL_RATE,a.BILL_RATE_PER,a.OVERTIME_RATE1,a.OVERTIME_RATE1_PER,a.OVERTIME_RATE2,a.OVERTIME_RATE2_PER,a.HOURS_PER_DAY, " + //
				"   a.WORKING_CITY,a.WORKING_STATE,a.WORKING_COUNTRY,a.WORK_ADDRESS1,a.WORK_ADDRESS2,a.WORK_CITY,a.WORK_STATE,a.WORK_ZIP,a.WORK_COUNTRY, " + //
				"   a.PASS_DISCOUNT,a.BILLING_UNIT,a.HOURS_PER_HALF_DAY,a.VMS_WEBSITE,a.PASS_THROUGH, " + //
				"   a.PRISALE_COMM_PERCENT,a.SECSALE_COMM_PERCENT,a.TERSALE_COMM_PERCENT,a.PRIREC_COMM_PERCENT,a.SECREC_COMM_PERCENT,a.TERREC_COMM_PERCENT, " + //
				"   a.PAYMENTTERMS,a.EXPENSEENABLED,a.POID,b.AMOUNT POAMOUNT,b.START_DATE PO_START_DATE,b.END_DATE PO_END_DATE,a.OVERTIMEEXEMPT,a.DIVISION,a.TIMESHEET_ENTRY_FORMAT,a.CUSTOMER_REFNO_SUB,a.TIMESHEET_INSTRUCTION, " + //
				"   (SELECT b.NAME FROM TTEAM_CURRENCY t JOIN TCURRENCY b ON t.CURRENCYID = b.ID WHERE t.TEAMID = a.RECRUITER_TEAMID AND t.DEFAULTCURRENCY = 1) currency, " + //
				"   (select discount from temployee_billingdiscount d where a.recruiter_teamid = d.teamid " + //
				"     and a.employeeid = d.employeeid and a.recid = d.recid and d.discountid = 0) DISCOUNT_PERCENT," + //
				"   (select discount_unit from temployee_billingdiscount d where a.recruiter_teamid = d.teamid " + //
				"     and a.employeeid = d.employeeid and a.recid = d.recid and d.discountid = 0) DISCOUNT_UNIT " + //
				" from temployee_billingrecord a, tpo_setting b " + //
				" where employeeid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and recruiter_teamid=? " + //
				"   and b.teamid(+)=a.recruiter_teamid and b.id(+)=a.poid " + //
				(restriction != null && restriction.length > 0 && restriction[0] != null ? " and a.division=? " : "");
		//
		StringBuilder candidates = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				candidates.append(",");
			candidates.append(params[i]);
		}
		param.add(candidates.toString());
		param.add(clientID);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String employeeBillingRecordsDetail(Long clientID, String[] params, Vector<Object> param, String[] restriction) {
		String sql = " select a.EMPLOYEEID,a.RECID,a.RFQID JOBID,a.INTERVIEWID ACTIVITYID,a.APPROVED,a.CLOSED,a.START_DATE,a.END_DATE,a.ACTUALSTART,a.ACTUALEND, "
				+ "   a.DATECREATED_REAL DATECREATED,a.DATECREATED DATEUPDATED,a.CREATED_BY,a.APPROVERID, " + "   a.PRIMARY_SALESPERSON,a.SECONDARY_SALESPERSON,a.TERTIARY_SALESPERSON,a.PRIMARY_RECRUITER,a.SECONDARY_RECRUITER,a.TERTIARY_RECRUITER, "
				+ "   a.BILLING_CONTACT,a.HIRING_MANAGER,a.CUSTOMER_REFNO,a.JOBDIVA_REFNO,a.FREQUENCY,a.WEEK_ENDING, " + "   a.BILL_RATE,a.BILL_RATE_PER,a.OVERTIME_RATE1,a.OVERTIME_RATE1_PER,a.OVERTIME_RATE2,a.OVERTIME_RATE2_PER,a.HOURS_PER_DAY, "
				+ "   a.WORKING_CITY,a.WORKING_STATE,a.WORKING_COUNTRY,a.WORK_ADDRESS1,a.WORK_ADDRESS2,a.WORK_CITY,a.WORK_STATE,a.WORK_ZIP,a.WORK_COUNTRY, "
				+ "   a.PASS_DISCOUNT,a.BILLING_UNIT,a.HOURS_PER_HALF_DAY,a.DISCOUNT_PERCENT,a.VMS_WEBSITE,a.PASS_THROUGH, "
				+ "   a.PRISALE_COMM_PERCENT,a.SECSALE_COMM_PERCENT,a.TERSALE_COMM_PERCENT,a.PRIREC_COMM_PERCENT,a.SECREC_COMM_PERCENT,a.TERREC_COMM_PERCENT, "
				+ "   a.PAYMENTTERMS,a.EXPENSEENABLED,a.POID,b.AMOUNT POAMOUNT,b.START_DATE PO_START_DATE,b.END_DATE PO_END_DATE,a.OVERTIMEEXEMPT,a.DIVISION,a.TIMESHEET_ENTRY_FORMAT,a.CUSTOMER_REFNO_SUB,a.TIMESHEET_INSTRUCTION "
				+ " from temployee_billingrecord a, tpo_setting b " + " where employeeid=? and recruiter_teamid=? and b.teamid(+)=a.recruiter_teamid and b.id(+)=a.poid "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null ? " and a.division=? " : "") + " order by recid ";
		param.add(new Long(params[0]));
		param.add(clientID);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String salaryRecordDetail(Long clientID, String[] params, Vector<Object> param, String[] restriction) {
		StringBuffer overheadColumns = new StringBuffer();
		StringBuffer activityUdfColumns = new StringBuffer();
		if (params != null) {
			String[] ary = null;
			for (int i = 2; i < params.length; i++) {
				ary = params[i].split("_");
				if (ary[0].equals("overhead")) {
					overheadColumns.append(", (SELECT 1 FROM TEMPLOYEE_OVERHEAD e JOIN TTEAM_OVERHEAD_TYPE o " + //
							"ON e.RECRUITER_TEAMID = o.TEAMID AND e.OVERHEADID=o.ID " + //
							"WHERE a.RECRUITER_TEAMID=e.RECRUITER_TEAMID AND a.EMPLOYEEID=e.EMPLOYEEID AND a.RECID=e.SALARY_RECID " + //
							"AND o.NAME = '" + ary[1] + "') \"" + StringUtils.escapeSql(ary[1]) + "\"");
				} else if (ary[0].equals("activityUdf")) {
					activityUdfColumns.append( //
							", (select " + //
									" case when n.fieldtypeid = 3 " + //
									" then to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd')||'T00:00:00.0' " + //
									"      when n.fieldtypeid = 4 " + //
									" then replace(to_char(to_date('01-jan-1970')+to_number(t.userfield_value)/86400000,'yyyy-mm-dd hh24:mi:ss')||'.0',' ','T') " + //
									" else t.userfield_value end userfield_value " + //
									" from tstartrecord_userfields t join tuserfields n on t.teamid = n.teamid and t.userfield_id = n.id " + //
									" join tinterviewschedule i on t.teamid = i.recruiter_teamid and t.startid = i.id " + //
									" where t.teamid = a.recruiter_teamid and i.candidateid = a.employeeid and i.id = a.interviewid " + //
									"and n.id=" + ary[1] + ") \"" + StringUtils.escapeSql(ary[2]) + "\"");
				}
			}
		}
		String sql = " select EMPLOYEEID,RECID,RFQID JOBID,INTERVIEWID ACTIVITYID,EFFECTIVE_DATE,END_DATE,APPROVED,CLOSED, " + //
				"   DATECREATED_REAL DATECREATED,DATECREATED DATEUPDATED,CREATED_BY, " + //
				"   STATUS,PER_DIEM,PER_DIEM_PER,OUTSIDE_COMMISSION,OUTSIDE_COMMISSION_PER,OTHER_EXPENSES,OTHER_EXPENSES_PER, " + //
				"   INSURED,SALARY,SALARY_PER,OVERTIME_RATE1,OVERTIME_RATE1_PER,OVERTIME_RATE2,OVERTIME_RATE2_PER,OVERTIMEEXEMPT, " + //
				"   SUBCONTRACT_COMPANYID,SUBCONTRACT_PAYONREMIT," + //
				"   OH_W2,OH_INSURANCE,OH_VACATION,OH_CORP_INSURANCE,OH_401K,OH_C2C,OH_PERDIEM, " + //
				"   TAXID,PAYMENTTERMS,BIRTHDAY,VISA_STATUS,SSN,VACATION,COMMENTS,ADP_FILE_NO, " + //
				"   DECODE(STATUS,'1','Hourly','2','Subcontractor','3','Employee','') EMPLOYMENT_CATEGORY, " + //
				"   DECODE(PAYMENT_FREQUENCY,'1','Bi-Weekly','2','Monthly','3','Semi Monthly','4','Weekly','') PAYMENT_FREQUENCY " + //
				overheadColumns.toString() + activityUdfColumns.toString() + " from temployee_salaryrecord a " + " where employeeid=? and recruiter_teamid=? and recid=? "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null
						? " and exists (select 1 from temployee_billingrecord b where b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.interviewid=a.interviewid and b.division=?) "
						: "");
		;
		param.add(new Long(params[0]));
		param.add(clientID);
		param.add(new Integer(params[1]));
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String salaryRecordsDetail(Long clientID, String[] params, Vector<Object> param, String[] restriction) {
		String sql = " select EMPLOYEEID,RECID,RFQID JOBID,INTERVIEWID ACTIVITYID,EFFECTIVE_DATE,END_DATE,APPROVED,CLOSED, " + "   DATECREATED_REAL DATECREATED,DATECREATED DATEUPDATED,CREATED_BY, "
				+ "   STATUS,PER_DIEM,PER_DIEM_PER,OUTSIDE_COMMISSION,OUTSIDE_COMMISSION_PER,OTHER_EXPENSES,OTHER_EXPENSES_PER, " + "   INSURED,SALARY,SALARY_PER,OVERTIME_RATE1,OVERTIME_RATE1_PER,OVERTIME_RATE2,OVERTIME_RATE2_PER,OVERTIMEEXEMPT, "
				+ "   SUBCONTRACT_COMPANYID,SUBCONTRACT_PAYONREMIT," + "   OH_W2,OH_INSURANCE,OH_VACATION,OH_CORP_INSURANCE,OH_401K,OH_C2C,OH_PERDIEM, "
				+ "   TAXID,PAYMENTTERMS,BIRTHDAY,VISA_STATUS,SSN,VACATION,COMMENTS,ADP_FILE_NO,ADPCOCODE ADP_CO_CODE " + " from temployee_salaryrecord a "
				+ " where employeeid in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual )) and recruiter_teamid=? "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null
						? " and exists (select 1 from temployee_billingrecord b where b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.interviewid=a.interviewid and b.division=?) "
						: "");
		StringBuilder candidates = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				candidates.append(",");
			candidates.append(params[i]);
		}
		param.add(candidates.toString());
		param.add(clientID);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String employeeSalaryRecordsDetail(Long clientID, String[] params, Vector<Object> param, String[] restriction) {
		String sql = " select EMPLOYEEID,RECID,RFQID JOBID,INTERVIEWID ACTIVITYID,EFFECTIVE_DATE,END_DATE,APPROVED,CLOSED, " + "   DATECREATED_REAL DATECREATED,DATECREATED DATEUPDATED,CREATED_BY, "
				+ "   STATUS,PER_DIEM,PER_DIEM_PER,OUTSIDE_COMMISSION,OUTSIDE_COMMISSION_PER,OTHER_EXPENSES,OTHER_EXPENSES_PER, " + "   INSURED,SALARY,SALARY_PER,OVERTIME_RATE1,OVERTIME_RATE1_PER,OVERTIME_RATE2,OVERTIME_RATE2_PER,OVERTIMEEXEMPT, "
				+ "   SUBCONTRACT_COMPANYID,SUBCONTRACT_PAYONREMIT," + "   OH_W2,OH_INSURANCE,OH_VACATION,OH_CORP_INSURANCE,OH_401K,OH_C2C,OH_PERDIEM, " + "   TAXID,PAYMENTTERMS,BIRTHDAY,VISA_STATUS,SSN,VACATION,COMMENTS,ADP_FILE_NO "
				+ " from temployee_salaryrecord a " + " where employeeid=? and recruiter_teamid=? "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null
						? " and exists (select 1 from temployee_billingrecord b where b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.interviewid=a.interviewid and b.division=?) "
						: "")
				+ " order by recid ";
		param.add(new Long(params[0]));
		param.add(clientID);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String newInvoices(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select invoiceid, customer_refno po_no, customer_refno_sub customer_referenceno, billingid billing_recid, rfqid jobid, "
				+ "   decode(invoicetype,1,'regular',3,'whole project',4,'expense',5,'perm placement','other') invoicetype, " + "   fromdate, todate, employeeid, billing_contact, invoicedate, hoursworked, amount  " + " from temployee_invoice "
				+ " where recruiter_teamid=? and datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + "   and approved=1 and (void is null or void = 0) and (closed is null or closed = 0) ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String voidedInvoices(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = " select invoiceid, customer_refno po_no, customer_refno_sub customer_referenceno, billingid billing_recid, rfqid jobid, "
				+ "   decode(invoicetype,1,'regular',3,'whole project',4,'expense',5,'perm placement','other') invoicetype, " + "   fromdate, todate, employeeid, billing_contact, invoicedate, hoursworked, amount " + " from temployee_invoice "
				+ " where recruiter_teamid=? and void = 1 " + "   and datevoided between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String invoiceDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select invoiceid, customer_refno po_no, customer_refno_sub customer_referenceno, billingid billing_recid, rfqid jobid, "
				+ "   decode(invoicetype,1,'regular',3,'whole project',4,'expense',5,'perm placement','other') invoicetype, " + "   fromdate, todate, employeeid, billing_contact, invoicedate, hoursworked, "
				+ "   amount-nvl((select sum(b.expense) from tinvoice_expense b where b.invoiceid=a.invoiceid and b.recruiter_teamid=a.recruiter_teamid),0) amount, " + "   to_char(itemdescription) itemdescription, to_char(comments) comments "
				+ " from temployee_invoice a " + " where invoiceid=? and recruiter_teamid=? " + "   union " + " select invoiceid, null po_no, null customer_referenceno, null billing_recid, null jobid, "
				+ "   'overtime' invoicetype, null fromdate, null todate, null employeeid, null billing_contact, " + "   null invoicedate, quantity hoursworked, rate_per_unit*quantity amount, to_char(item) itemdescription, null comments "
				+ " from temployee_invoice_ot1 " + " where invoiceid=? and recruiter_teamid=? " + "   union " + " select invoiceid, null po_no, null customer_referenceno, null billing_recid, null jobid, "
				+ "   'expense' invoicetype, null fromdate, null todate, null employeeid, null billing_contact, " + "   null invoicedate, null hoursworked, expense amount, to_char(item) itemdescription, to_char(comments) comments "
				+ " from tinvoice_expense " + " where invoiceid=? and recruiter_teamid=? and billable=1 ";
		param.add(new Long(params[0]));
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String placementDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select invoiceid, salary, fee, startdate, " + "   salespersonid, commision sales_comm, salespersonid2, sales_comm2, salespersonid3, sales_comm3, " + "   recruiterid, rec_comm, recruiterid2, rec_comm2, recruiterid3, rec_comm3 "
				+ " from tplacements a " + " where invoiceid=? and recruiter_teamid=? ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String approvedTimesheets(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = "SELECT w.timecardid timesheetid, w.employeeid, w.billing_recid, w.salary_recid, w.weekendingdate, w.datecreated,  "
				+ "w.approved_on dateapproved, w.approved_by userapproved, w.hoursworked, w.reg_hours, w.ot_hours, w.dt_hours, w.comments, "
				+ "s.SALARY REG_PAY, s.OVERTIME_RATE1 OT_PAY, s.OVERTIME_RATE2 DT_PAY, s.SALARY_PER REG_PAY_RATE_UNIT, s.OVERTIME_RATE1_PER OT_PAY_RATE_UNIT, s.OVERTIME_RATE2_PER DT_PAY_RATE_UNIT,  "
				+ "b.BILL_RATE REG_BILL, b.OVERTIME_RATE1 OT_BILL, b.OVERTIME_RATE2 DT_BILL, b.BILL_RATE_PER REG_BILL_RATE_UNIT, b.OVERTIME_RATE1_PER OT_BILL_RATE_UNIT, b.OVERTIME_RATE1_PER DT_BILL_RATE_UNIT "
				+ "FROM temployee_wed w JOIN TEMPLOYEE_SALARYRECORD s ON w.RECRUITER_TEAMID = s.RECRUITER_TEAMID AND w.EMPLOYEEID = s.EMPLOYEEID AND w.BILLING_RECID = s.RECID "
				+ "JOIN TEMPLOYEE_BILLINGRECORD b ON w.RECRUITER_TEAMID = b.RECRUITER_TEAMID AND w.EMPLOYEEID = b.EMPLOYEEID AND w.BILLING_RECID = b.RECID "
				+ "where w.recruiter_teamid=? and w.approved_on between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')  " + "and w.approved=1 "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null ? " and b.division=? " : "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String newUpdatedApprovedTimesheets(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = "SELECT w.timecardid timesheetid, w.employeeid, w.billing_recid, w.salary_recid, w.weekendingdate, w.datecreated, "
				+ "w.approved_on dateapproved, w.approved_by userapproved, NVL(b.BILLRATE_NAME,'Regular Hours') HOURS_DESCRIPTION, " + "w.hoursworked, w.reg_hours, w.ot_hours, w.dt_hours, w.comments, "
				+ "s.SALARY REG_PAY, s.OVERTIME_RATE1 OT_PAY, s.OVERTIME_RATE2 DT_PAY, s.SALARY_PER REG_PAY_RATE_UNIT, s.OVERTIME_RATE1_PER OT_PAY_RATE_UNIT, s.OVERTIME_RATE2_PER DT_PAY_RATE_UNIT,  "
				+ "b.BILL_RATE REG_BILL, b.OVERTIME_RATE1 OT_BILL, b.OVERTIME_RATE2 DT_BILL, b.BILL_RATE_PER REG_BILL_RATE_UNIT, b.OVERTIME_RATE1_PER OT_BILL_RATE_UNIT, b.OVERTIME_RATE1_PER DT_BILL_RATE_UNIT "
				+ "FROM temployee_wed w JOIN TEMPLOYEE_SALARYRECORD s ON w.RECRUITER_TEAMID = s.RECRUITER_TEAMID AND w.EMPLOYEEID = s.EMPLOYEEID AND w.BILLING_RECID = s.RECID "
				+ "JOIN TEMPLOYEE_BILLINGRECORD b ON w.RECRUITER_TEAMID = b.RECRUITER_TEAMID AND w.EMPLOYEEID = b.EMPLOYEEID AND w.BILLING_RECID = b.RECID "
				+ "WHERE w.recruiter_teamid=? and (w.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')   "
				+ "or w.approved_on between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) " + "and w.approved=1 " + (restriction != null && restriction.length > 0 && restriction[0] != null ? " and b.division=? " : "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String newUpdatedTimesheets(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = "SELECT w.timecardid timesheetid, w.employeeid, w.billing_recid, w.salary_recid, w.weekendingdate, w.datecreated, "
				+ "w.approved_on DATEUPDATED, w.approved_by USERUPDATED, NVL(b.BILLRATE_NAME,'Regular Hours') HOURS_DESCRIPTION, w.hoursworked, "
				+ "w.reg_hours, w.ot_hours, w.dt_hours, w.comments, s.SALARY REG_PAY, s.OVERTIME_RATE1 OT_PAY, s.OVERTIME_RATE2 DT_PAY, "
				+ "s.SALARY_PER REG_PAY_RATE_UNIT, s.OVERTIME_RATE1_PER OT_PAY_RATE_UNIT, s.OVERTIME_RATE2_PER DT_PAY_RATE_UNIT, " + "b.BILL_RATE REG_BILL, b.OVERTIME_RATE1 OT_BILL, b.OVERTIME_RATE2 DT_BILL, b.BILL_RATE_PER REG_BILL_RATE_UNIT, "
				+ "b.OVERTIME_RATE1_PER OT_BILL_RATE_UNIT, b.OVERTIME_RATE1_PER DT_BILL_RATE_UNIT, " + "DECODE(w.APPROVED,'0','PENDING','1','APPROVED','2','REJECTED','') STATUS "
				+ "FROM temployee_wed w JOIN TEMPLOYEE_SALARYRECORD s ON w.RECRUITER_TEAMID = s.RECRUITER_TEAMID AND w.EMPLOYEEID = s.EMPLOYEEID AND w.BILLING_RECID = s.RECID "
				+ "JOIN TEMPLOYEE_BILLINGRECORD b ON w.RECRUITER_TEAMID = b.RECRUITER_TEAMID AND w.EMPLOYEEID = b.EMPLOYEEID AND w.BILLING_RECID = b.RECID "
				+ "WHERE w.recruiter_teamid=? and (w.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "or w.approved_on between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) " + "and w.approved >=0 " + (restriction != null && restriction.length > 0 && restriction[0] != null ? " and b.division=? " : "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String weekendingOnTimesheets(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = "SELECT s.ADP_FILE_NO, " + "(SELECT c.ID FROM TRFQ r JOIN TCUSTOMER c ON r.CUSTOMERID = c.ID AND r.TEAMID = c.TEAMID WHERE s.RFQID = r.ID AND s.RECRUITER_TEAMID = r.TEAMID) COMPANY_ID, "
				+ "(SELECT c.COMPANYNAME FROM TRFQ r JOIN TCUSTOMER c ON r.CUSTOMERID = c.ID AND r.TEAMID = c.TEAMID WHERE s.RFQID = r.ID AND s.RECRUITER_TEAMID = r.TEAMID) COMPANY_NAME, "
				+ "w.timecardid timesheetid, w.employeeid, w.billing_recid, w.salary_recid, w.weekendingdate, w.datecreated, "
				+ "w.approved_on dateapproved, w.approved_by userapproved, w.hoursworked, w.reg_hours, w.ot_hours, w.dt_hours, w.comments,  "
				+ "s.SALARY REG_PAY, s.OVERTIME_RATE1 OT_PAY, s.OVERTIME_RATE2 DT_PAY, s.SALARY_PER REG_PAY_RATE_UNIT, s.OVERTIME_RATE1_PER OT_PAY_RATE_UNIT, s.OVERTIME_RATE2_PER DT_PAY_RATE_UNIT,  "
				+ "b.BILL_RATE REG_BILL, b.OVERTIME_RATE1 OT_BILL, b.OVERTIME_RATE2 DT_BILL, b.BILL_RATE_PER REG_BILL_RATE_UNIT, b.OVERTIME_RATE1_PER OT_BILL_RATE_UNIT, b.OVERTIME_RATE1_PER DT_BILL_RATE_UNIT "
				+ "FROM temployee_wed w JOIN TEMPLOYEE_SALARYRECORD s ON w.RECRUITER_TEAMID = s.RECRUITER_TEAMID AND w.EMPLOYEEID = s.EMPLOYEEID AND w.BILLING_RECID = s.RECID "
				+ "JOIN TEMPLOYEE_BILLINGRECORD b ON w.RECRUITER_TEAMID = b.RECRUITER_TEAMID AND w.EMPLOYEEID = b.EMPLOYEEID AND w.BILLING_RECID = b.RECID "
				+ "JOIN TEMPLOYEE_ADP a ON s.EMPLOYEEID = a.EMPLOYEEID AND s.RECRUITER_TEAMID = a.RECRUITER_TEAMID "
				+ "where w.recruiter_teamid=? and w.weekendingdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')  " + "and w.approved=1 "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null ? " and b.division=? " : "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String timesheetDetail(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param, String[] restriction) {
		boolean externalid_flag = false;
		if (params.length >= 2) {
			try {
				externalid_flag = Boolean.parseBoolean(params[1]);
			} catch (Exception e) {
			}
		} else {
			try {
				Long.parseLong(params[0]);
			} catch (Exception e) {
				externalid_flag = true;
			}
		}
		String sql = "select a.timecardid timesheetid, a.externalid, a.employeeid, a.billing_recid, s.RECID SALARY_RECID, a.weekendingdate, "
				+ "case when b.projectid >= 0 then 'Billable' else to_char(c.name) end hourtype, NVL(br.BILLRATE_NAME,'Regular Hours') HOURS_DESCRIPTION, " + "b.TDATE, b.reghours reg_hours, b.othours ot_hours, b.dthours dt_hours "
				+ "FROM TEMPLOYEE_WED a JOIN TEMPLOYEE_TIMESHEET b ON b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.billing_recid=a.billing_recid and b.weekending = a.weekendingdate "
				+ "LEFT JOIN TBILLING_HOURTYPES c ON b.RECRUITER_TEAMID = c.RECRUITER_TEAMID AND b.PROJECTID = c.ID "
				+ "LEFT JOIN TEMPLOYEE_BILLINGRECORD br ON a.RECRUITER_TEAMID = br.RECRUITER_TEAMID AND a.EMPLOYEEID = br.EMPLOYEEID AND a.BILLING_RECID = br.RECID "
				+ "LEFT JOIN TEMPLOYEE_SALARYRECORD s ON a.RECRUITER_TEAMID = s.RECRUITER_TEAMID AND a.EMPLOYEEID = s.EMPLOYEEID AND a.SALARY_RECID = s.RECID " + "where a.recruiter_teamid=? "
				+ (externalid_flag ? "and a.externalid=? " : "and (a.timecardid=? or a.externalid=?) ")
				+ (restriction != null && restriction.length > 0 && restriction[0] != null
						? " and exists (select 1 from temployee_billingrecord b where b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.recid=a.billing_recid and b.division=?) "
						: "");
		param.add(clientID);
		if (externalid_flag)
			param.add(params[0]);
		else {
			param.add(new Long(params[0]));
			param.add(params[0]);
		}
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String employeeTimesheetImageDetail(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param, String[] restriction) {
		String sql = " select a.weekendingdate, b.image_filename filename, b.image_bytes filecontent " + " from temployee_wed_images a, temployee_timesheet_images b "
				+ " where a.recruiter_teamid=? and a.weekendingdate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + "   and a.employeeid=? and a.billing_recid=? "
				+ "   and b.recruiter_teamid=a.recruiter_teamid and b.imageid=a.imageid "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null
						? " and exists (select 1 from temployee_billingrecord b where b.employeeid=a.employeeid and b.recruiter_teamid=a.recruiter_teamid and b.recid=a.billing_recid and b.division=?) "
						: "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(new Long(params[0]));
		param.add(new Long(params[1]));
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String employeeTimesheetImageDetailByTimecardId(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param, String[] restriction) {
		String sql = " select a.weekendingdate, b.image_filename filename, b.image_bytes filecontent " + " from temployee_wed t, temployee_wed_images a, temployee_timesheet_images b "
				+ " where t.recruiter_teamid=? and t.employeeid=? and t.timecardid=? " + "   and a.recruiter_teamid=t.recruiter_teamid and a.employeeid=t.employeeid and a.billing_recid=t.billing_recid and a.weekendingdate = t.weekendingdate"
				+ "   and b.recruiter_teamid=a.recruiter_teamid and b.imageid=a.imageid "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null
						? " and exists (select 1 from temployee_billingrecord br where br.employeeid=t.employeeid and br.recruiter_teamid=t.recruiter_teamid and br.recid=t.billing_recid and br.division=?) "
						: "");
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(new Long(params[1]));
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String jobsDashboardByUser(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String sql = " select b.id jobid, b.dateissued, b.rfqno_team jobdivano, b.rfqrefno refno, b.rfqtitle jobtitle, " + "   b.contract, b.city, b.state, (select d.name from tdivision d where d.teamid=a.teamid and d.id=b.divisionid) division, "
				+ "   initcap(trim(b.firstname||' '||b.lastname)) hiringmanager, "
				+ "   (select r.firstname||' '||r.lastname from trecruiterrfq u, trecruiter r where u.teamid=a.teamid and u.rfqid=a.rfqid and u.lead_recruiter=1 and r.id=u.recruiterid) primaryrecruiter, "
				+ "   (select r.firstname||' '||r.lastname from trecruiterrfq u, trecruiter r where u.teamid=a.teamid and u.rfqid=a.rfqid and u.lead_sales=1 and r.id=u.recruiterid) primarysales, "
				+ "   nvl((select p.name from tjob_priority p where p.teamid=a.teamid and p.id=b.jobpriority),decode(jobpriority,1,'A',2,'B',3,'C',4,'D',' ')) jobpriority, b.positions openings, "
				+ "   decode(sign(nvl(b.positions,1)-nvl(b.maxsubmitals, 0)), 1, nvl(b.positions,1), nvl(b.maxsubmitals, 0)) maxsubmitals, "
				+ "   (select count(distinct candidateid) from tinterviewschedule t1 where t1.rfqid=a.rfqid and nvl(t1.roleid,0)>900) internalsubmittals, "
				+ "   (select nvl(sum(case when daterejected is not null then 1 else 0 end),0) from tinterviewschedule t1 where t1.rfqid=a.rfqid) internalrejects, "
				+ "   (select count(distinct candidateid) from tinterviewschedule t1 where t1.rfqid=a.rfqid and nvl(t1.roleid,0)<900) externalsubmittals, "
				+ "   (select count(distinct (case when dateinterview is not null then candidateid else -1 end)) - nvl(max(case when dateinterview is null then 1 else 0 end),0) from tinterviewschedule t1 where t1.rfqid=a.rfqid) interviews, "
				+ "   (select count(distinct t1.candidateid) from tinterviewschedule t1 where t1.rfqid=a.rfqid and t1.extdaterejected is not null) rejects, "
				+ "   (select nvl(sum(case when datehired is not null then 1 else 0 end),0) from tinterviewschedule t1 where t1.rfqid=a.rfqid) hires " + " from trecruiterrfq a, trfq b "
				+ " where a.teamid=? and a.recruiterid=? and a.jobstatus=0 and b.id=a.rfqid and nvl(b.approvedstatus,0)=0 ";
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
	
	public static String jobsDashboard(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String sql = " select userid, b.id jobid, b.dateissued, b.rfqno_team jobdivano, b.rfqrefno refno, b.rfqtitle jobtitle, "
				+ "   b.contract, b.city, b.state, (select d.name from tdivision d where d.teamid=a.teamid and d.id=b.divisionid) division, " + "   initcap(trim(b.firstname||' '||b.lastname)) hiringmanager, "
				+ "   (select r.firstname||' '||r.lastname from trecruiterrfq u, trecruiter r where u.teamid=a.teamid and u.rfqid=a.rfqid and u.lead_recruiter=1 and r.id=u.recruiterid) primaryrecruiter, "
				+ "   (select r.firstname||' '||r.lastname from trecruiterrfq u, trecruiter r where u.teamid=a.teamid and u.rfqid=a.rfqid and u.lead_sales=1 and r.id=u.recruiterid) primarysales, "
				+ "   nvl((select p.name from tjob_priority p where p.teamid=a.teamid and p.id=b.jobpriority),decode(jobpriority,1,'A',2,'B',3,'C',4,'D',' ')) jobpriority, b.positions openings, "
				+ "   decode(sign(nvl(b.positions,1)-nvl(b.maxsubmitals, 0)), 1, nvl(b.positions,1), nvl(b.maxsubmitals, 0)) maxsubmitals, "
				+ "   (select count(distinct candidateid) from tinterviewschedule t1 where t1.rfqid=a.rfqid and nvl(t1.roleid,0)>900) internalsubmittals, "
				+ "   (select nvl(sum(case when daterejected is not null then 1 else 0 end),0) from tinterviewschedule t1 where t1.rfqid=a.rfqid) internalrejects, "
				+ "   (select count(distinct candidateid) from tinterviewschedule t1 where t1.rfqid=a.rfqid and nvl(t1.roleid,0)<900) externalsubmittals, "
				+ "   (select count(distinct (case when dateinterview is not null then candidateid else -1 end)) - nvl(max(case when dateinterview is null then 1 else 0 end),0) from tinterviewschedule t1 where t1.rfqid=a.rfqid) interviews, "
				+ "   (select count(distinct t1.candidateid) from tinterviewschedule t1 where t1.rfqid=a.rfqid and t1.extdaterejected is not null) rejects, "
				+ "   (select nvl(sum(case when datehired is not null then 1 else 0 end),0) from tinterviewschedule t1 where t1.rfqid=a.rfqid) hires " + " from trecruiterrfq a, trfq b "
				+ " where a.teamid=? and a.recruiterid=? and a.jobstatus=0 and b.id=a.rfqid and nvl(b.approvedstatus,0)=0 ";
		param.add(clientID);
		return sql;
	}
	
	public static String contactHotlistDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select h.contactid, c.firstname, c.lastname, c.title, c.companyname, c.workphone, c.email " + "from tcontact_hotlist_contacts h, tcustomer c " + "where h.teamid=? and h.hotlist_id=? and nvl(deleted,0)=0 and c.id=h.contactid ";
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
	
	public static String candidateHotlistDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select candidateid, firstname, lastname, tag abstract, homephone, city, state, datereceived_original date_received, datecreated date_available " + "from tcandidate_hotlist "
				+ "where teamid=? and hotlist_id=? and nvl(removed,0)=0 and candidateid in (select id from tcandidate where nvl(privacyapplied,0) = 0)";
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
	
	public static String userContactHotlistDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select h.hotlist_id, h.contactid, c.firstname, c.lastname, c.title, c.companyname, c.workphone, c.email " + "from tcontact_hotlist_contacts h, tcustomer c "
				+ "where h.teamid=? and h.recruiterid=? and nvl(deleted,0)=0 and c.id=h.contactid order by h.hotlist_id";
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
	
	public static String userCandidateHotlistDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select hotlist_id, candidateid, firstname, lastname, tag abstract, homephone, city, state, datereceived_original date_received, datecreated date_available " + "from tcandidate_hotlist "
				+ "where teamid=? and recruiterid=? and nvl(removed,0)=0 and candidateid in (select id from tcandidate where nvl(privacyapplied,0) = 0) order by hotlist_id";
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
	
	public static String contactHotlistsDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select h.hotlist_id, h.contactid, c.firstname, c.lastname, c.title, c.companyname, c.workphone, c.email " + "from tcontact_hotlist_contacts h, tcustomer c "
				+ "where h.teamid=? and h.hotlist_id in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual ))" + " and nvl(deleted,0)=0 and c.id=h.contactid order by h.hotlist_id";
		StringBuilder hotlists = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				hotlists.append(",");
			hotlists.append(params[i]);
		}
		param.add(clientID);
		param.add(hotlists.toString());
		return sql;
	}
	
	public static String candidateHotlistsDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select hotlist_id, candidateid, firstname, lastname, tag abstract, homephone, city, state, datereceived_original date_received, datecreated date_available " + "from tcandidate_hotlist "
				+ "where teamid=? and hotlist_id in (Select * from THE (Select cast(sf_inlist(?) as sf_inlist_table_type ) from dual ))"
				+ " and nvl(removed,0)=0 and candidateid in (select id from tcandidate where nvl(privacyapplied,0) = 0) order by hotlist_id";
		StringBuilder hotlists = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				hotlists.append(",");
			hotlists.append(params[i]);
		}
		param.add(clientID);
		param.add(hotlists.toString());
		return sql;
	}
	
	public static String submittalbyCandbyJob(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select id, recruiterid userid, primarysalesid, candidateid, rfqid jobid, customerid contactid, roleid, datecreated, dateupdated, " + "  datepresented submittaldate, "
				+ " case when roleid > 900 then daterejected else extdaterejected end rejectiondate, " + "  dateinterview, interview_timezoneid, datehired startdate, date_ended enddate, a.dateterminated terminationdate, "
				+ "  managerfirstname, managerlastname, " + " case when dateterminated is not null and reasonterminated > 0 " + "      then (select description from treason_termination x where x.teamid=a.recruiter_teamid and x.id=reasonterminated) "
				+ "      else null end terminationreason, " + " case when EXTREJECTREASONID is not null and EXTREJECTREASONID > 0 "
				+ "      then (select reason from TREJECTION_REASONS rejreason where rejreason.teamid=a.recruiter_teamid and rejreason.id=a.EXTREJECTREASONID) " + "      else null end rejectreason, "
				+ " (case when a.hourly > 0 then a.hourly_corporate when a.daily > 0 then a.daily_corporate else a.yearly_corporate end) corptocorp, " + " pay_hourly agreedbillrate, " + " case when finalbillrateunit is not null "
				+ "      then decode(finalbillrateunit,'h','Hourly','d','Daily','y','Yearly', " + "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=1 and x.unitid=finalbillrateunit)) "
				+ "      else null end billfrequency, " + " case when hourly > 0 then hourly when daily > 0 then daily else yearly end agreedpayrate, " + " case when payrateunits is not null "
				+ "      then decode(case when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end,'h','Hourly','d','Daily','y','Yearly', "
				+ "           (select x.name from trateunits x where x.teamid=a.recruiter_teamid and x.ratetype=0 and x.unitid=(case when daily > 0 then substr(payrateunits,2,1) when yearly > 0 then substr(payrateunits,3,1) else substr(payrateunits,1,1) end))) "
				+ "      else (case when hourly > 0 then 'Hourly' when daily > 0 then 'Daily' when yearly > 0 then 'Yearly' else null end) end payfrequency, " + " nvl((select x.name from tcurrency x where x.id=a.hourly_currency), "
				+ "     nvl((select x.name from tcurrency x, tteam_currency y where y.teamid=a.recruiter_teamid and y.defaultcurrency=1 and y.currencyid=x.id), 'USD')) currency, "
				+ " case when a.datehired is not null and a.contract=1 then decode(fee_type,0,'%',1,'Flat Amount','') else '' end fee_type, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=0 then fee/100 when contract=1 and fee_type=1 then fee/yearly else null end fee_percent, "
				+ " case when a.datehired is not null and a.contract=1 and fee_type=1 then fee when contract=1 and fee_type=0 then yearly*fee/100 else null end fee, " + " notes "
				+ " from tinterviewschedule a where a.recruiter_teamid=? and a.candidateid = ? and a.rfqid=?";
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(new Long(params[1]));
		return sql;
	}
	
	public static String CandidateNotesListDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = " select a.noteid, " + " 	decode(a.type,1,'Last Attempt',2,'Last Reached',3,'Last Meeting',4,'Attribute',5,'Qualification'," + " 			6,'References Checked',7,'Incoming Call',8,'Outgoing Call',b.name) actiontype, "
				+ " 	a.recruiterid userid, a.datecreated createdate, " + " 	a.candidateid, decode(a.rfqid,0,'',a.rfqid) jobid, decode(a.contactid,0,'',a.contactid) contactid, "
				+ " 	case when note_clob is null then substr(note,1,100) else to_char(substr(note_clob,1,100)) end notefirst100chars " + " from tcandidatenotes a, tactiontype_candidate b " + " where a.recruiter_teamid = ? and a.candidateid = ? "
				+ " and (a.auto = 0 or a.auto = 3) and nvl(a.deleted,0) = 0 and a.type = b.id(+) and a.recruiter_teamid=b.teamid(+) ";
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
	
	public static String CandidateEmailRecords(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select b.messageid, a.from_address, a.to_address, a.subject, a.dateofemail  " + " from temails_saved a, temails_saved_links b " + " where b.link_personid = ? and b.teamid = ? and b.type = 2 and a.id = b.messageid ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String ContactEmailRecords(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select b.messageid, a.from_address, a.to_address, a.subject, a.dateofemail  " + " from temails_saved a, temails_saved_links b " + " where b.link_personid = ? and b.teamid = ? and b.type = 1 and a.id = b.messageid ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String EmailDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select id, emailobject" + " from temails_saved " + " where id = ? and teamid = ? ";
		param.add(new Long(params[0]));
		param.add(clientID);
		return sql;
	}
	
	public static String candidateResumeSubmittedtoJob(Long clientID, String[] params, Vector<Object> param) {
		String sql = "select candidateid, rfqid jobid, recruiterid userid, datecreated, document, filename, ziprtf RESUME_BASE64ENCODED" + " from tpresenteddocument where teamid =? and candidateid = ? and rfqid=?";
		param.add(clientID);
		param.add(new Long(params[0]));
		param.add(new Long(params[1]));
		return sql;
	}
	
	public static String savedEmails(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select a.messageid, a.datecreated, b.from_address, b.to_address, b.subject, b.dateofemail, d.recruiterid userid, case when c.type=1 then 'Contact' else 'Candidate' end PERSONTYPE, c.link_personid"
				+ " from temails_saved_date a, temails_saved b, temails_saved_links c, temails_saved_recruiters d" + " where a.teamid=? and a.datecreated between str_to_date(?,'%m/%d/%Y %H:%i:%s') and str_to_date(?,'%m/%d/%Y %H:%i:%s')"
				+ " and b.id=a.messageid and c.messageid=a.messageid and d.messageid=a.messageid";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String submittalsInterviewsHiresByRecruiter(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT ID, FIRSTNAME||' '||LASTNAME RECRUITERNAME,  " + "NVL(s.VALUE,0) AS SUBMITTALS, NVL(i.VALUE,0) AS INTERVIEWS, NVL(h.VALUE,0) AS HIRES " + "FROM TRECRUITER r  "
				+ "LEFT JOIN ( SELECT RECRUITERID, count(distinct (rfqid||'~'||candidateid)) VALUE  " + "            FROM tinterviewschedule WHERE RECRUITER_TEAMID = ? AND datecreated between  "
				+ "            to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + "            group by RECRUITERID) s ON r.ID = s.RECRUITERID  "
				+ "LEFT JOIN ( SELECT RECRUITERID, count(distinct (rfqid||'~'||candidateid)) VALUE  " + "            FROM tinterviewschedule where RECRUITER_TEAMID = ? AND (INTERVIEWSCHEDULEDATE between "
				+ "            to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) and dateinterview is not null  " + "            group by RECRUITERID) i ON r.ID = i.RECRUITERID "
				+ "LEFT JOIN ( SELECT RECRUITERID, COUNT(*) VALUE  " + "            FROM tinterviewschedule where RECRUITER_TEAMID = ? and placementdate between  "
				+ "            to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') and datehired is not null  " + "            group by RECRUITERID) h ON r.ID = h.RECRUITERID "
				+ "WHERE r.GROUPID = ? AND (s.VALUE > 0 OR i.VALUE > 0 OR h.VALUE > 0) " + "ORDER BY ID";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(clientID);
		return sql;
	}
	
	public static String resumeCountByRecruiter(Long clientID, String fromDate, String toDate, String[] params, Vector<Object> param) {
		String sql = "SELECT RECRUITERID ID, (SELECT FIRSTNAME||' '||LASTNAME FROM TRECRUITER r WHERE r.ID = t.RECRUITERID) RECRUITERNAME, COUNT(*) VALUE  " + "FROM TCANDIDATEDOCUMENT_HEADER t WHERE TEAMID = ? AND DATERECEIVED BETWEEN  "
				+ "to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')  " + "GROUP BY RECRUITERID " + "ORDER BY RECRUITERID";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String paychexProfiles(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT p.EMPLOYEEID_PAYCHECK, " + "(SELECT c.ID FROM TRFQ r JOIN TCUSTOMER c ON r.CUSTOMERID = c.ID AND r.TEAMID=c.TEAMID WHERE r.ID = s.RFQID AND r.TEAMID=s.RECRUITER_TEAMID) COMPANY_ID, "
				+ "(SELECT c.COMPANYNAME FROM TRFQ r JOIN TCUSTOMER c ON r.CUSTOMERID = c.ID AND r.TEAMID=c.TEAMID WHERE r.ID = s.RFQID AND r.TEAMID=s.RECRUITER_TEAMID) COMPANY_NAME, "
				+ "p.EMPLOYEEID, h.LEGAL_FIRSTNAME, h.LEGAL_MIDDLENAME, h.LEGAL_LASTNAME, h.GENERATIONSUFFIX, "
				+ "DECODE((SELECT e.DEEOID FROM TCANDIDATE_EEO e WHERE e.TEAMID = p.RECRUITER_TEAMID AND e.CANDIDATEID = p.EMPLOYEEID AND e.EEOID = 1),'0','','1','F','2','M') SEX, "
				+ "h.SSN, TO_CHAR(h.DATEOFBIRTH, 'MM/dd/yyyy') DATEOFBIRTH, p.TELEPHONE, p.COUNTRY, p.ADDRESS1, p.ADDRESS2, p.CITY, p.STATE, p.ZIPCODE, "
				+ "DECODE(p.EMPLOYEESTATUS,'A','A - Active','T','T - Terminated','L','L - On Leave (Inactive in CA reporting)','M','M - Transferred (PBS only)') EMPLOYEESTATUS,    "
				+ "DECODE(p.REASON,'1','1 - Active','2','2 - Hired','3','3 - Rehire','4','4 - Return from Leave','5','5 - Begin Contract (Ind Contractor)','6',   "
				+ "'6 - Resume Contract (Ind Contractor)') REASON, p.STATUSDATE, p.POSITION,  p.EMPLOYEETYPE,  " + "(SELECT c.NAME FROM TCUSTOMERCOMPANY c WHERE c.id = s.subcontract_companyID and c.teamid = s.recruiter_teamid) IC_COMPANY_NAME,  "
				+ "p.HOMEEMAIL, p.WORKEMAIL, p.HIREDATE, p.JOBNUMBER, p.WORKSTATE, " + "DECODE(p.STATETAXABILITY, 'Y','Y - Withhold','N','N - Taxable') STATETAXABILITY, p.STATEFILLINGSTATUS, p.STATEUNEMPLOYMENT, p.STATEINCOMETAX,    "
				+ "DECODE(p.STATEDISABILITY, 'Y','Y - Withhold','N','N - Taxable') STATEDISABILITY, p.FEDERALIDNUMBER,    "
				+ "DECODE(p.FEDERALFILINGSTATUS,'S','S - Single','M','M - Married','MWS','Married but withhold at a single rate') FEDERALFILINGSTATUS,    " + "p.FEDERALALLOWANCES, p.FEDERALADDITIONALTAXAMOUNT,    "
				+ "DECODE(s.SALARY_PER,'H','HR - Hourly Rate','D','DR - Daily Rate','Y','AS - Annual Salary') PAY_RATE_1, s.SALARY PAY_RATE_AMOUNT_1,    " + "DECODE(p.OVERTIMEEXEMPT, 'Y','Y - Withhold','N','N - Taxable') OVERTIMEEXEMPT,    "
				+ "DECODE(s.OVERTIME_RATE1_PER,'Y','Y - Withhold','N','N - Taxable') PAY_RATE_2, s.OVERTIME_RATE1 PAY_RATE_AMOUNT_2,    "
				+ "DECODE(s.OVERTIME_RATE2_PER,'Y','Y - Withhold','N','N - Taxable') PAY_RATE_3, s.OVERTIME_RATE2 PAY_RATE_AMOUNT_3   "
				+ "FROM TEMPLOYEE_PAYCHECK p JOIN TCANDIDATE_HR h ON p.RECRUITER_TEAMID = h.TEAMID AND p.EMPLOYEEID = h.CANDIDATEID   "
				+ "JOIN TEMPLOYEE_SALARYRECORD s ON p.RECRUITER_TEAMID = s.RECRUITER_TEAMID AND p.EMPLOYEEID = s.EMPLOYEEID and p.SALARY_RECID = s.RECID   " + "WHERE p.RECRUITER_TEAMID = ? AND s.approved=1 and nvl(s.closed,0) = 0   "
				+ "AND (p.DATECREATED BETWEEN to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')    " + "OR p.DATEUPDATED BETWEEN to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss'))   "
				+ "and not exists (select 1 from temployee_salaryrecord s1 where s1.employeeid=s.employeeid and s1.recruiter_teamid=s.recruiter_teamid    "
				+ "and s1.interviewid=s.interviewid and s1.approved=1 and nvl(s1.closed,0)=0 and s1.recid > s.recid)";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String adpProfiles(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = " select a.employeeid, h.legal_firstname, h.legal_middlename, h.legal_lastname, h.generationsuffix generation_suffix, a.hirereason reason_for_hire, " + "   a.companycode company_code, h.ssn, "
				+ "   nvl(decode(LOWER(a.gender),'n','N - Non-Specified','m','M - Male','f','F - Female',null), (decode((select e.deeoid from tcandidate_eeo e  "
				+ "   where e.teamid = a.recruiter_teamid and e.candidateid = a.employeeid and e.eeoid = 1), '0','','1','F','2','M'))) sex,   "
				+ "   to_char(h.dateofbirth, 'mm/dd/yyyy') date_of_birth, a.country, a.address1, a.address2, a.address3, a.city, a.county, a.state, a.zipcode,   " + "   a.hphoneprefix || a.hphonelast telephone, a.email,  "
				+ "   decode(h.maritalstatus, 0, '', 1, 'C', 2, 'D', 3, 'E', 4, 'L', 5, 'M', 6, 'P', 7, 'S', 8, 'W', '') marital_status,    "
				+ "   s.adp_file_no file_number, a.jobtitle job_title, a.workercategory worker_category, a.mngposition manage_position,  "
				+ "   nvl(a.allowedtakencode, '') allowedtakencode, a.resetyear, a.businessunit, b.working_state location, a.homedept home_department,   "
				+ "   a.codedhomedept coded_home_department, a.benefitseligibilityclass, nvl(a.naicsworkercomp, '') naics_workers_comp,   "
				+ "   nvl(a.healthcoveragecode, '') health_coverage_code, a.retirementplan retirement_plan, decode(LOWER(a.payfrequency),'w','W - Weekly','b',  "
				+ "   'B - Bi-Weekly','s','S - Semi-Monthly','m','M - Monthly','d','D - Daily','5','5 - 5.2 weeks','2','2 - 2.6 weeks','4','4 - 4 weeks','') pay_frequency,  "
				+ "   decode(LOWER(s.salary_per),'h','H - Hourly','s','S - Salary','d','D - Daily','n','N - None','') regular_pay_rate, s.salary regular_pay_amount,  " + "   nvl(a.standardhours, 0) standard_hours, nvl(a.paygroup, '1') pay_group,   "
				+ "   decode(LOWER(a.federalmarritalstatus),'d','Single or Married filing separately','j','Married filing jointly (or Qualifying widow(er))','h','Head of Household'," + "   a.federalmarritalstatus) federal_marital_status, "
				+ "   a.federalexemptions federal_exemptions, " + "   a.W4_MULTIPLEJOBS \"W-4_MULTIPLE_JOBS\", a.W4_DEPENDENTS \"W-4_DEPENDANTS\", a.W4_OTHERINCOME \"W-4_OTHER_INCOME\", " + "   a.W4_DEDUCTIONS \"W-4_DEDUCTIONS\", "
				+ "   a.workedinstate worked_state_tax_code,   " + "   decode(LOWER(a.statemaritalstatus),'m','M - Married','n','N - Married - Head of Household','r','R - Single - HOH/ Qualifying Dependent','s','S - Single',  "
				+ "   't','T - Single - Head of Household','x','X - Married - Two Incomes','y','Y - Married - HOH/ Two Incomes',a.statemaritalstatus) state_marital_status,   "
				+ "   a.stateexemptions state_exemptions, a.livedinstate lived_state_tax_code, a.suisditaxcode, a.extrataxtype federal_extra_tax_type,   "
				+ "   a.extratax federal_extra_tax, a.donotcaltax_federalincome, a.donotcaltax_state, a.donotcaltax_socialsecurity,  "
				+ "   a.donotcaltax_medicare, a.localtaxcode0 local_tax_code, a.localtaxcode1 worked_local_tax_code, a.localtaxcode2 lived_local_tax_code,  "
				+ "   a.localtaxcode3 local_school_district_tax_code, a.localtaxcode4 local_tax_code_4, a.localtaxcode5 local_tax_code_5,   "
				+ "   decode(LOWER(a.deductioncode1),'v','V - 3rd checking','w','W - 2nd checking','x','X - savings','y','Y - checking','z','Z - 2nd savings','') bank_deposit_deduction_code,   "
				+ "   decode(a.isfulldeposit1, 1, 'Y', 'N') bank_full_deposit_flag, nvl(a.deductionamount1, '') bank_deposit_deduction_amount,   "
				+ "   a.transitabanumber1 bank_deposit_transit_or_aba, a.bankdepositaccountnumber1 bank_deposit_account_number,   "
				+ "   a.prenotificationmethod1 prenotification_method, a.bankdepositprenotedate1 bank_deposit_prenote_date, s.RECID SALARY_RECID, b.RECID BILLING_RECID  " + " from temployee_adp a "
				+ " join temployee_salaryrecord s on s.recruiter_teamid = a.recruiter_teamid and s.employeeid = a.employeeid and s.recid = a.salary_recid "
				+ " join temployee_billingrecord b on b.recruiter_teamid = a.recruiter_teamid and b.employeeid = a.employeeid and b.interviewid = s.interviewid "
				+ " left join tcandidate_hr h on h.teamid = a.recruiter_teamid and h.candidateid = a.employeeid  " + " where a.recruiter_teamid = ? "
				+ "   and (a.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')   " + "   or a.dateupdated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss'))   "
				+ "   and s.approved=1 and nvl(s.closed,0) = 0 " + "   and not exists ( " + "     select 1 from temployee_salaryrecord s1 where s1.employeeid=s.employeeid and s1.recruiter_teamid=s.recruiter_teamid  "
				+ "     and s1.interviewid=s.interviewid and s1.approved=1 and nvl(s1.closed,0)=0 and s1.recid > s.recid) " + "   and b.approved=1 and nvl(b.closed,0) = 0 " + "   and not exists ( "
				+ "     select 1 from temployee_billingrecord b1 where b1.employeeid=b.employeeid and b1.recruiter_teamid=b.recruiter_teamid  "
				+ "     and b1.interviewid=b.interviewid and b1.approved=1 and nvl(b1.closed,0)=0 and b1.recid > b.recid) " + (restriction != null && restriction.length > 0 && restriction[0] != null ? " and b.division=? " : "");
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String adpProfile(Long clientID, String[] params, Vector<Object> param, String[] restriction) {
		String sql = " select a.employeeid, h.legal_firstname, h.legal_middlename, h.legal_lastname, h.generationsuffix generation_suffix, a.hirereason reason_for_hire, " + "a.companycode company_code, h.ssn, "
				+ "nvl(decode(LOWER(a.gender),'n','N - Non-Specified','m','M - Male','f','F - Female',null), (decode((select e.deeoid from tcandidate_eeo e "
				+ "where e.teamid = a.recruiter_teamid and e.candidateid = a.employeeid and e.eeoid = 1), '0','','1','F','2','M'))) sex, "
				+ "to_char(h.dateofbirth, 'mm/dd/yyyy') date_of_birth, a.country, a.address1, a.address2, a.address3, a.city, a.county, a.state, a.zipcode, " + "a.hphoneprefix || a.hphonelast telephone, a.email, "
				+ "decode(h.maritalstatus, 0, '', 1, 'C', 2, 'D', 3, 'E', 4, 'L', 5, 'M', 6, 'P', 7, 'S', 8, 'W', '') marital_status, "
				+ "s.adp_file_no file_number, a.jobtitle job_title, a.workercategory worker_category, a.mngposition manage_position, "
				+ "nvl(a.allowedtakencode, '') allowedtakencode, a.resetyear, a.businessunit, b.working_state location, a.homedept home_department, "
				+ "a.codedhomedept coded_home_department, a.benefitseligibilityclass, nvl(a.naicsworkercomp, '') naics_workers_comp, "
				+ "nvl(a.healthcoveragecode, '') health_coverage_code, a.retirementplan retirement_plan, decode(LOWER(a.payfrequency),'w','W - Weekly','b', "
				+ "'B - Bi-Weekly','s','S - Semi-Monthly','m','M - Monthly','d','D - Daily','5','5 - 5.2 weeks','2','2 - 2.6 weeks','4','4 - 4 weeks','') pay_frequency, "
				+ "decode(LOWER(s.salary_per),'h','H - Hourly','s','S - Salary','d','D - Daily','n','N - None','') regular_pay_rate, s.salary regular_pay_amount, " + "nvl(a.standardhours, 0) standard_hours, nvl(a.paygroup, '1') pay_group, "
				+ "decode(LOWER(a.federalmarritalstatus),'d','Single or Married filing separately','j','Married filing jointly (or Qualifying widow(er))','h','Head of Household',"
				+ "a.federalmarritalstatus) federal_marital_status, a.federalexemptions federal_exemptions, " + "a.W4_MULTIPLEJOBS \"W-4_MULTIPLE_JOBS\", a.W4_DEPENDENTS \"W-4_DEPENDANTS\", a.W4_OTHERINCOME \"W-4_OTHER_INCOME\", "
				+ "a.W4_DEDUCTIONS \"W-4_DEDUCTIONS\", " + "a.workedinstate worked_state_tax_code, "
				+ "decode(LOWER(a.statemaritalstatus),'m','M - Married','n','N - Married - Head of Household','r','R - Single - HOH/ Qualifying Dependent','s','S - Single', "
				+ "'t','T - Single - Head of Household','x','X - Married - Two Incomes','y','Y - Married - HOH/ Two Incomes',a.statemaritalstatus) state_marital_status, "
				+ "a.stateexemptions state_exemptions, a.livedinstate lived_state_tax_code, a.suisditaxcode, a.extrataxtype federal_extra_tax_type, "
				+ "a.extratax federal_extra_tax, a.donotcaltax_federalincome, a.donotcaltax_state, a.donotcaltax_socialsecurity, "
				+ "a.donotcaltax_medicare, a.localtaxcode0 local_tax_code, a.localtaxcode1 worked_local_tax_code, a.localtaxcode2 lived_local_tax_code, "
				+ "a.localtaxcode3 local_school_district_tax_code, a.localtaxcode4 local_tax_code_4, a.localtaxcode5 local_tax_code_5, "
				+ "decode(LOWER(a.deductioncode1),'v','V - 3rd checking','w','W - 2nd checking','x','X - savings','y','Y - checking','z','Z - 2nd savings','') bank_deposit_deduction_code, "
				+ "decode(a.isfulldeposit1, 1, 'Y', 'N') bank_full_deposit_flag, nvl(a.deductionamount1, '') bank_deposit_deduction_amount, "
				+ "a.transitabanumber1 bank_deposit_transit_or_aba, a.bankdepositaccountnumber1 bank_deposit_account_number, "
				+ "a.prenotificationmethod1 prenotification_method, a.bankdepositprenotedate1 bank_deposit_prenote_date, s.RECID SALARY_RECID, b.RECID BILLING_RECID " + "    from temployee_adp a "
				+ "    join temployee_salaryrecord s on s.recruiter_teamid = a.recruiter_teamid and s.employeeid = a.employeeid and s.recid = a.salary_recid "
				+ "    join temployee_billingrecord b on b.recruiter_teamid = a.recruiter_teamid and b.employeeid = a.employeeid and b.interviewid = s.interviewid "
				+ "    left join tcandidate_hr h on h.teamid = a.recruiter_teamid and h.candidateid = a.employeeid " + "where a.recruiter_teamid = ? AND a.EMPLOYEEID = ? " + "and s.approved=1 and nvl(s.closed,0) = 0 " + "and not exists ( "
				+ "  select 1 from temployee_salaryrecord s1 where s1.employeeid=s.employeeid and s1.recruiter_teamid=s.recruiter_teamid " + "  and s1.interviewid=s.interviewid and s1.approved=1 and nvl(s1.closed,0)=0 and s1.recid > s.recid) "
				+ "and b.approved=1 and nvl(b.closed,0) = 0 " + "and not exists ( " + "  select 1 from temployee_billingrecord b1 where b1.employeeid=b.employeeid and b1.recruiter_teamid=b.recruiter_teamid "
				+ "  and b1.interviewid=b.interviewid and b1.approved=1 and nvl(b1.closed,0)=0 and b1.recid > b.recid) " + (restriction != null && restriction.length > 0 && restriction[0] != null ? " and b.division=? " : "");
		param.add(clientID);
		param.add(params[0]);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String companyCountByUser(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT RECRUITERID USERID, (SELECT FIRSTNAME||' '||LASTNAME FROM TRECRUITER r WHERE r.ID = t.RECRUITERID) USERNAME, COUNT(*) COUNT " + "from TCUSTOMERCOMPANY t " + "WHERE TEAMID = ? "
				+ "AND DATEENTERED BETWEEN TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') AND TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') " + "GROUP BY RECRUITERID";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String jobsCountByUser(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT RECRUITERID USERID, (SELECT FIRSTNAME||' '||LASTNAME FROM TRECRUITER r WHERE r.ID = a.RECRUITERID) USERNAME, COUNT(*) COUNT " + "FROM TRFQ a " + "WHERE TEAMID = ? AND "
				+ "(a.DATEISSUED BETWEEN TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') AND TO_DATE(?,'mm/dd/yyyy hh24:mi:ss')) " + "GROUP BY RECRUITERID";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String contactCountByUser(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT RECRUITERID USERID, (SELECT FIRSTNAME||' '||LASTNAME FROM TRECRUITER r WHERE r.ID = t.RECRUITERID) USERNAME, COUNT(*) COUNT " + "FROM TCUSTOMER t " + "WHERE TEAMID= ? AND "
				+ "(TIMEENTERED BETWEEN TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') AND TO_DATE(?,'mm/dd/yyyy hh24:mi:ss')) " + "GROUP BY RECRUITERID ";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String candidateNoteCountByUser(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT RECRUITERID USERID, (SELECT FIRSTNAME||' '||LASTNAME FROM TRECRUITER r WHERE r.ID = c.RECRUITERID) USERNAME, COUNT(*) COUNT " + "FROM TCANDIDATENOTES c "
				+ "where RECRUITER_TEAMID = ? and recruiterid>0 and nvl(deleted,0) = 0 and (auto = 0 or auto = 3) " + "AND DATECREATED BETWEEN TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') AND TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "GROUP BY RECRUITERID order by count(*) desc";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String contactNoteCountByUser(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT RECRUITERID USERID, (SELECT FIRSTNAME||' '||LASTNAME FROM TRECRUITER r WHERE r.ID = c.RECRUITERID) USERNAME, COUNT(*) COUNT " + "FROM TCUSTOMERNOTES c "
				+ "WHERE RECRUITER_TEAMID = ? AND (DATEMODIFIED BETWEEN TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') AND TO_DATE(?,'mm/dd/yyyy hh24:mi:ss')) " + "AND NVL(DELETED,0) = 0 and recruiterid>0 " + "GROUP BY RECRUITERID order by count(*) desc";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String candidateNoteCountByActionByUser(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT RECRUITERID USERID, (SELECT FIRSTNAME||' '||LASTNAME FROM TRECRUITER r WHERE r.ID = c.RECRUITERID) USERNAME, "
				+ "    DECODE(c.TYPE,1,'Last Attempt',2,'Last Reached',3,'Last Meeting',4,'Attribute',5,'Qualification',6,'References Checked', " + "    7,'Incoming Call',8,'Outgoing Call',a.NAME) ACTIONTYPE, COUNT(*) COUNT "
				+ "FROM TCANDIDATENOTES c LEFT JOIN TACTIONTYPE_CANDIDATE a ON c.TYPE = a.ID AND c.RECRUITER_TEAMID = a.TEAMID " + "WHERE c.RECRUITER_TEAMID = ? AND c.RECRUITERID>0 AND NVL(c.DELETED,0) = 0 AND (c.AUTO = 0 OR c.AUTO = 3) "
				+ "AND DATECREATED BETWEEN TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') AND TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') " + "GROUP BY RECRUITERID, DECODE(c.TYPE,1,'Last Attempt',2,'Last Reached',3,'Last Meeting',4,'Attribute', "
				+ "    5,'Qualification',6,'References Checked',7,'Incoming Call',8,'Outgoing Call',a.NAME) " + "ORDER BY USERNAME";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String contactNoteCountByActionByUser(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT RECRUITERID USERID, (SELECT FIRSTNAME||' '||LASTNAME FROM TRECRUITER r WHERE r.ID = c.RECRUITERID) USERNAME, "
				+ "DECODE(c.TYPE,1,'Last Attempt',2,'Last Reached',3,'Last Meeting',4,'Attribute',5,'Qualification',6,'References Checked', " + "7,'Incoming Call',8,'Outgoing Call',a.NAME) ACTIONTYPE, COUNT(NOTEID) COUNT "
				+ "FROM TCUSTOMERNOTES c LEFT JOIN TACTIONTYPE_CANDIDATE a ON c.TYPE = a.ID AND c.RECRUITER_TEAMID = a.TEAMID " + "WHERE RECRUITER_TEAMID = ? AND NVL(c.DELETED,0) = 0 and recruiterid>0 "
				+ "AND (DATECREATED BETWEEN TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') AND TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') ) "
				+ "GROUP BY RECRUITERID, DECODE(c.TYPE,1,'Last Attempt',2,'Last Reached',3,'Last Meeting',4,'Attribute',5,'Qualification',6,'References Checked', " + "7,'Incoming Call',8,'Outgoing Call',a.NAME) " + "ORDER BY USERNAME";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newResumesDownloaded(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT RECRUITERID USERID, candidateid, docid, dbid, global_id resumeid, datecreated_original datecreated, datecreated dateupdated, " + "    datereceived_original datefirstdownloaded, datereceived datelastdownloaded "
				+ "from tcandidatedocument_header " + "where teamid= ? " + "and datereceived_original between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String newUpdatedCandidateAvailabilityRecords(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT candidateid, case when flag=2 then 1 else 0 end unavailableindef, dateavailable, " + " DATE_CREATED datecreated " + "FROM tcandidate_unreachable where teamid = ? "
				+ "and DATE_CREATED between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + "    union " + "select candidateid, 0 unavailableindef, max(a.date_created) dateavailable, " + "max(DATE_CREATED) datecreated "
				+ "FROM tcandidate_unreachable_archive a WHERE a.teamid = ? " + "and DATE_CREATED between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + "GROUP BY CANDIDATEID " + "    union "
				+ "select candidateid, 0 unavailableindef, max(h.datecreated) dateavailable, " + "max(DATECREATED) datecreated " + "FROM tcandidatedocument_header h where h.teamid = ? "
				+ "and DATECREATED between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "and not exists (select 1 from tcandidatedocument_header d where d.candidateid=h.candidateid and d.teamid=h.teamid and d.daterefreshed > h.daterefreshed) "
				+ "and not exists (select 1 from tcandidate_unreachable x where x.candidateid=h.candidateid and x.teamid=h.teamid) "
				+ "and not exists (select 1 from tcandidate_unreachable_archive a where h.candidateid=a.candidateid and h.teamid=a.teamid) " + "GROUP BY CANDIDATEID";
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
	
	public static String candidateAttributeDetail(Long clientID, String[] params, Vector<Object> param) {
		String sql = "SELECT CANDIDATEID, RECRUITERID USERID, DATECREATED DATECREATED, " + "CASE WHEN NOTE_CLOB IS NULL OR LENGTH(NOTE_CLOB) = 0 THEN TO_CLOB(NOTE) ELSE NOTE_CLOB END \"ATTRIBUTE\" "
				+ "FROM TCANDIDATENOTES WHERE RECRUITER_TEAMID = ? AND CANDIDATEID = ? AND AUTO = 4";
		param.add(clientID);
		param.add(params[0]);
		return sql;
	}
	
	public static String newExpenses(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "SELECT e.ENTRYID EXPENSEID, d.EXPENSEID SUB_EXPENSEID, e.EMPLOYEEID, e.BILLINGID BILLING_RECID, " + "    (SELECT s.RECID FROM TEMPLOYEE_BILLINGRECORD b JOIN TEMPLOYEE_SALARYRECORD s ON b.RECRUITER_TEAMID=s.RECRUITER_TEAMID "
				+ "    AND b.EMPLOYEEID=s.EMPLOYEEID AND b.INTERVIEWID=s.INTERVIEWID " + "    WHERE b.EMPLOYEEID = e.EMPLOYEEID AND b.RECRUITER_TEAMID = e.RECRUITER_TEAMID AND b.RECID = e.BILLINGID "
				+ "    AND e.TODATE BETWEEN s.EFFECTIVE_DATE AND NVL(s.END_DATE,'31-DEC-2999') AND s.APPROVED = 1 AND NVL(s.CLOSED,0)=0) SALARY_RECID, " + "e.WEEKENDING, d.ITEM, d.EXPENSE, d.QUANTITY "
				+ "FROM TEXPENSE_ENTRY e JOIN TEXPENSE_DETAIL d ON e.ENTRYID = d.ENTRYID AND e.RECRUITER_TEAMID = d.RECRUITER_TEAMID " + "WHERE e.RECRUITER_TEAMID = ? AND "
				+ "DATECREATED BETWEEN TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') AND TO_DATE(?,'mm/dd/yyyy hh24:mi:ss')";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	public static String expensesById(Long clientID, String[] params, Vector<Object> param) {
		String sql = "SELECT e.ENTRYID EXPENSEID, d.EXPENSEID SUB_EXPENSEID, e.EMPLOYEEID, e.BILLINGID BILLING_RECID, "//
				+ " (SELECT s.RECID FROM TEMPLOYEE_BILLINGRECORD b JOIN TEMPLOYEE_SALARYRECORD s ON b.RECRUITER_TEAMID=s.RECRUITER_TEAMID " //
				+ "    AND b.EMPLOYEEID=s.EMPLOYEEID AND b.INTERVIEWID=s.INTERVIEWID " //
				+ "    WHERE b.EMPLOYEEID = e.EMPLOYEEID AND b.RECRUITER_TEAMID = e.RECRUITER_TEAMID AND b.RECID = e.BILLINGID "
				+ "    AND e.TODATE BETWEEN s.EFFECTIVE_DATE AND NVL(s.END_DATE,'31-DEC-2999') AND s.APPROVED = 1 AND NVL(s.CLOSED,0)=0) SALARY_RECID, " //
				+ "e.WEEKENDING, d.ITEM, d.EXPENSE, d.QUANTITY " //
				+ " FROM TEXPENSE_ENTRY e " //
				+ " JOIN TEXPENSE_DETAIL d ON e.ENTRYID = d.ENTRYID AND e.RECRUITER_TEAMID = d.RECRUITER_TEAMID " //
				+ " WHERE e.RECRUITER_TEAMID = ? " //
				+ " AND e.ENTRYID = ? ";
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
	
	public static String newUpdatedApprovedTimesheetsAndExpenses(Long clientID, String fromDate, String toDate, Vector<Object> param, String[] restriction) {
		String sql = "WITH EXPENSE_CTE AS ( " + "    SELECT e.ENTRYID EXPENSEID, d.EXPENSEID SUB_EXPENSEID, e.EMPLOYEEID, e.BILLINGID BILLING_RECID, b.division, " + "		(SELECT s.RECID FROM TEMPLOYEE_SALARYRECORD s "
				+ "    	WHERE b.EMPLOYEEID = e.EMPLOYEEID AND b.RECRUITER_TEAMID = e.RECRUITER_TEAMID AND b.RECID = e.BILLINGID " + "    	AND b.RECRUITER_TEAMID=s.RECRUITER_TEAMID AND b.EMPLOYEEID=s.EMPLOYEEID AND b.INTERVIEWID=s.INTERVIEWID "
				+ "    	AND e.TODATE BETWEEN s.EFFECTIVE_DATE AND NVL(s.END_DATE,'31-DEC-2999') AND s.APPROVED = 1 AND NVL(s.CLOSED,0)=0) SALARY_RECID, " + "	e.WEEKENDING, d.ITEM, d.EXPENSE, d.QUANTITY, e.TIMECARDID, "
				+ "   DECODE((SELECT DEDUCTIONCODE1 FROM TEMPLOYEE_ADP a WHERE b.RECRUITER_TEAMID = a.RECRUITER_TEAMID AND b.EMPLOYEEID = a.EMPLOYEEID "
				+ "   AND ROWNUM = 1),'V','V - 3rd Checking','W','W - 2nd Checking','X','X - Savings','Y','Y - Checking','Z','Z - 2nd Savings', " + "    'C','C - Cash Pay Card','') DEDUCTION_CODE, e.RECRUITER_TEAMID TEAMID "
				+ "	FROM TEXPENSE_ENTRY e JOIN TEXPENSE_DETAIL d ON e.ENTRYID = d.ENTRYID AND e.RECRUITER_TEAMID = d.RECRUITER_TEAMID "
				+ "	JOIN TEMPLOYEE_BILLINGRECORD b ON b.EMPLOYEEID = e.EMPLOYEEID AND b.RECRUITER_TEAMID = e.RECRUITER_TEAMID AND b.RECID = e.BILLINGID " + "	WHERE e.RECRUITER_TEAMID = ? AND "
				+ "	e.DATECREATED BETWEEN TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') AND TO_DATE(?,'mm/dd/yyyy hh24:mi:ss') " + (restriction != null && restriction.length > 0 && restriction[0] != null ? " and b.division=? " : "") + "), "
				+ "TIMESHEET_CTE AS ( " + "    SELECT w.timecardid timesheetid, w.employeeid, w.billing_recid, w.salary_recid, w.weekendingdate, w.datecreated, "
				+ "    w.approved_on dateapproved, w.approved_by userapproved, NVL(b.BILLRATE_NAME,'Regular Hours') HOURS_DESCRIPTION, " + "    w.hoursworked, w.reg_hours, w.ot_hours, w.dt_hours, w.comments, s.COMPCODE, "
				+ "    s.SALARY REG_PAY, s.OVERTIME_RATE1 OT_PAY, s.OVERTIME_RATE2 DT_PAY, s.SALARY_PER REG_PAY_RATE_UNIT, " + "    s.OVERTIME_RATE1_PER OT_PAY_RATE_UNIT, s.OVERTIME_RATE2_PER DT_PAY_RATE_UNIT, b.BILL_RATE REG_BILL, "
				+ "    b.OVERTIME_RATE1 OT_BILL, b.OVERTIME_RATE2 DT_BILL, b.BILL_RATE_PER REG_BILL_RATE_UNIT, " + "    b.OVERTIME_RATE1_PER OT_BILL_RATE_UNIT, b.OVERTIME_RATE1_PER DT_BILL_RATE_UNIT, "
				+ "    DECODE((SELECT DEDUCTIONCODE1 FROM TEMPLOYEE_ADP a WHERE s.RECRUITER_TEAMID = a.RECRUITER_TEAMID AND s.EMPLOYEEID = a.EMPLOYEEID "
				+ "    AND ROWNUM = 1),'V','V - 3rd Checking','W','W - 2nd Checking','X','X - Savings','Y','Y - Checking','Z','Z - 2nd Savings', " + "    'C','C - Cash Pay Card','') DEDUCTION_CODE, w.RECRUITER_TEAMID TEAMID "
				+ "    FROM temployee_wed w JOIN TEMPLOYEE_SALARYRECORD s ON w.RECRUITER_TEAMID = s.RECRUITER_TEAMID AND w.EMPLOYEEID = s.EMPLOYEEID AND w.SALARY_RECID = s.RECID "
				+ "    JOIN TEMPLOYEE_BILLINGRECORD b ON w.RECRUITER_TEAMID = b.RECRUITER_TEAMID AND w.EMPLOYEEID = b.EMPLOYEEID AND w.BILLING_RECID = b.RECID "
				+ "    WHERE w.recruiter_teamid= ? and (w.datecreated between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "
				+ "    or w.approved_on between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')) " + "    and w.approved=1 "
				+ (restriction != null && restriction.length > 0 && restriction[0] != null ? " and b.division=? " : "") + ") " + "SELECT DISTINCT * FROM ( " + "    ( "
				+ "        SELECT t.EMPLOYEEID, t.BILLING_RECID, t.SALARY_RECID, t.WEEKENDINGDATE,t.TIMESHEETID, t.DEDUCTION_CODE, t.DATECREATED, "
				+ "        t.USERAPPROVED, t.HOURS_DESCRIPTION, t.HOURSWORKED, t.REG_HOURS, t.OT_HOURS, t.DT_HOURS, t.COMMENTS, " + "        t.COMPCODE, t.REG_PAY, t.OT_PAY, t.DT_PAY, t.REG_PAY_RATE_UNIT, t.OT_PAY_RATE_UNIT, "
				+ "        t.DT_PAY_RATE_UNIT, t.REG_BILL, t.OT_BILL, t.DT_BILL, t.REG_BILL_RATE_UNIT, t.OT_BILL_RATE_UNIT, " + "        t.DT_BILL_RATE_UNIT, NULL EXPENSEID, NULL ITEM, NULL EXPENSE, NULL QUANTITY " + "        FROM TIMESHEET_CTE t "
				+ "    ) UNION ALL ( " + "        SELECT e.EMPLOYEEID, e.BILLING_RECID, e.SALARY_RECID, e.WEEKENDING, e.TIMECARDID, e.DEDUCTION_CODE, NULL DATECREATED, "
				+ "        NULL USERAPPROVED, NULL HOURS_DESCRIPTION, NULL HOURSWORKED, NULL REG_HOURS, NULL OT_HOURS, NULL DT_HOURS, NULL COMMENTS, "
				+ "        NULL COMPCODE, NULL REG_PAY, NULL OT_PAY, NULL DT_PAY, NULL REG_PAY_RATE_UNIT, NULL OT_PAY_RATE_UNIT, "
				+ "        NULL DT_PAY_RATE_UNIT, NULL REG_BILL, NULL OT_BILL, NULL DT_BILL, NULL REG_BILL_RATE_UNIT, NULL OT_BILL_RATE_UNIT, " + "        NULL DT_BILL_RATE_UNIT, e.EXPENSEID, e.ITEM, e.EXPENSE, e.QUANTITY "
				+ "        FROM EXPENSE_CTE e " + "    ) " + ") " + "ORDER BY EMPLOYEEID, TIMESHEETID, EXPENSEID NULLS FIRST";
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		param.add(fromDate);
		param.add(toDate);
		if (restriction != null && restriction.length > 0 && restriction[0] != null)
			param.add(new Long(restriction[0]));
		return sql;
	}
	
	public static String candidateOnboardingDocumentList(Long clientID, String[] params, Vector<Object> param) {
		String sql = "SELECT CANDIDATEID, ID, FILENAME, " + "(CASE WHEN ONBOARDINGSTATUS>0 THEN (CASE WHEN ONBOARDINGSTATUS=1 THEN " + "    'Verified' ELSE 'Rejected' END) ELSE (CASE WHEN NUM >0 THEN (CASE WHEN DTYPE=1 THEN 'Completed Outside' ELSE "
				+ "    'Pending' END) ELSE (CASE WHEN REQUIRE_RETURN=1 THEN (CASE WHEN ASON IS NOT NULL THEN 'Missing' ELSE 'Saved' END) " + "    ELSE 'Returned Not Required' END) END) END) STATUS, ASSIGNEDON, UPLOADEDON " + "FROM " + "(select "
				+ "(SELECT p.assignedon FROM tpreonboardings p WHERE a.TEAMID = p.TEAMID AND a.CANDIDATEID = p.CANDIDATEID AND a.INTERVIEWID = p.ID) ason, " + "a.onboardingstatus, nvl(b.require_return,0) as require_return, "
				+ "(select count(*) from tcandidate_onboarding_docs where onboardingid=a.id and teamid=a.teamid and nvl(a.DELETED,0)=0) as num, "
				+ "0 as fromtest, b.doctype dtype, a.CANDIDATEID, d.ID, d.filename, a.datecreated assignedon, d.uploadedon " + "    from tcandidate_onboarding a JOIN tonboardings b ON a.TEAMID = b.TEAMID AND a.DOCID = b.ID "
				+ "    LEFT JOIN tcandidate_onboarding_docs d ON a.TEAMID = d.TEAMID AND a.ID = d.ONBOARDINGID " + "    WHERE a.TEAMID = ? AND a.CANDIDATEID = ? " + "    AND d.deleted=0)";
		param.add(clientID);
		param.add(new Long(params[0]));
		return sql;
	}
	
	public static String userfieldsList(Long clientID, Vector<Object> param) {
		String sql = "SELECT id, fieldname, " + "(CASE WHEN fieldtypeid = 1 THEN 'String' " + "		WHEN fieldtypeid = 2 THEN 'Number' " + "		WHEN fieldtypeid = 3 THEN 'Date' " + "		WHEN fieldtypeid = 4 THEN 'Date/Time' "
				+ "		WHEN fieldtypeid = 5 THEN 'Drop Down (Single Selection)' " + "		WHEN fieldtypeid = 6 THEN 'List (Multiple Selection)' " + "		WHEN fieldtypeid = 7 THEN 'URL' " + "		WHEN fieldtypeid = 8 THEN 'Currency' "
				+ "		WHEN fieldtypeid = 9 THEN 'Percentage' " + "		WHEN fieldtypeid = 10 THEN 'Text Area' " + "		WHEN fieldtypeid = 11 THEN 'Sequence' ELSE 'Unknown' END) fieldtype, " + "fieldsize, "
				+ "(CASE WHEN BITAND(tuf.fieldfor, 1) = 1 THEN 'Contacts' " + "		WHEN BITAND(tuf.fieldfor, 2) = 2 THEN 'Candidates' " + "		WHEN BITAND(tuf.fieldfor, 4) = 4 THEN 'Jobs' "
				+ "		WHEN BITAND(tuf.fieldfor, 8) = 8 THEN 'Companies' " + "		WHEN BITAND(tuf.fieldfor, 112) > 0 THEN 'Activities' " + "		WHEN BITAND(tuf.fieldfor, 128) = 128 THEN 'HR' "
				+ "		WHEN BITAND(tuf.fieldfor, 256) = 256 THEN 'Opportunity' ELSE 'Unknown' END) fieldfor, " + "(CASE WHEN mandatory = 1 THEN 'Yes' " + "		ELSE 'No' END) mandatory, " + "ttabs.tabname "
				+ "FROM tuserfields tuf LEFT JOIN tuserfield_tabs ttabs " + "ON tuf.tabid = ttabs.tabid AND tuf.teamid = ttabs.teamid " + "WHERE tuf.teamid = ? ";
		param.add(clientID);
		return sql;
	}
}

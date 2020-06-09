package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class LiniumFeed {
	
	public static String getInvoiceList(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select division, company, billingcontact billing_contact, primary_sales, secondary_sales, tertiary_sales, primary_recruiter, secondary_recruiter, tertiary_recruiter, " + //
				"invoicetype invoice_type, invoiceid invoice_id, group_invoiceid group_invoice_id, invoiceid_client client_invoice_id, invoicedate invoice_date, datecreated invoice_created_date, " + //
				"to_char(fromdate,'mm/dd/yyyy')||' - '||to_char(todate,'mm/dd/yyyy') service_period, employee, rfqno_team jobdiva_job#, customer_refno customer_ref#, " + //
				"poname po#, sowname sow, milestonename milestone, frequency, total_hours reg_quantity, bill_rate_per bill_unit, bill_rate reg_rate, " + //
				"ot_hours ot_quantity, ot_item, ot_rate, ot_unit " + //
				"from " + //
				"(select c.name company, b.firstname|| ' ' ||b.lastname billingcontact, 'Regular' invoicetype, a.invoiceid, a.invoiceid_client, a.invoicedate, a.datecreated, a.fromdate, a.todate, " + //
				"d.lastname||', '||d.firstname employee, f.rfqno_team, e.customer_refno," + //
				"decode(e.frequency,1,'BiWeekly',2,'Monthly',3,'Semi Monthly',4,'Weekly',5,'Whole Project',6,'Monthly Ending Weekend','UnKnown') frequency, e.bill_rate, " + //
				"decode(e.bill_rate_per,'H', 'Hourly','D','Daily','W', 'Weekly','Y','Yearly','M', 'Monthly','Unknown') bill_rate_per, " + //
				"a.hoursworked total_hours,(select g.quantity from temployee_invoice_ot1 g where a.invoiceid=g.invoiceid and a.recruiter_teamid=g.recruiter_teamid and SUBSTR(g.item,1,2)<>'DT') ot_hours, " + //
				"(select g.item from temployee_invoice_ot1 g where a.invoiceid=g.invoiceid and a.recruiter_teamid=g.recruiter_teamid and SUBSTR(g.item,1,2)<>'DT') ot_item, " + //
				"(select g.rate_per_unit from temployee_invoice_ot1 g where a.invoiceid=g.invoiceid and a.recruiter_teamid=g.recruiter_teamid and SUBSTR(g.item,1,2)<>'DT') ot_rate, " + //
				"(select g.unit from temployee_invoice_ot1 g where a.invoiceid=g.invoiceid and a.recruiter_teamid=g.recruiter_teamid and SUBSTR(g.item,1,2)<>'DT') ot_unit, " + //
				"(select g.quantity from temployee_invoice_ot1 g where a.invoiceid=g.invoiceid and a.recruiter_teamid=g.recruiter_teamid and SUBSTR(g.item,1,2)='DT') dt_hours, " + //
				"(select g.item from temployee_invoice_ot1 g where a.invoiceid=g.invoiceid and a.recruiter_teamid=g.recruiter_teamid and SUBSTR(g.item,1,2)='DT') dt_item, " + //
				"(select g.rate_per_unit from temployee_invoice_ot1 g where a.invoiceid=g.invoiceid and a.recruiter_teamid=g.recruiter_teamid and SUBSTR(g.item,1,2)='DT') dt_rate, " + //
				"(select g.unit from temployee_invoice_ot1 g where a.invoiceid=g.invoiceid and a.recruiter_teamid=g.recruiter_teamid and SUBSTR(g.item,1,2)='DT') dt_unit, " + //
				"a.subtotal, a.discount, h.name division, a.amount DUE, " + //
				"nvl(c.INVOICE_CONTACT_PREFERENCE, 0) as SHOW_ON_INVOICE, e.PAYMENTTERMS as PAYMENT_TERM, a.group_invoiceid as group_invoiceid, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.primary_salesperson) PRIMARY_SALES, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.secondary_salesperson) SECONDARY_SALES, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.tertiary_salesperson) TERTIARY_SALES, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.primary_recruiter) PRIMARY_RECRUITER," + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.secondary_recruiter) SECONDARY_RECRUITER," + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.tertiary_recruiter) TERTIARY_RECRUITER, " + //
				"sow.name as sowname, tms.name as milestonename, (select po# from tpo_setting where id = a.poid and teamid = a.recruiter_teamid ) as poname, a.customer_refno_sub as customer_refno_name " + //
				"from temployee_invoice a, tcustomer b, tcustomercompany c, tcandidate d, temployee_billingrecord e, trfq f, tdivision h, tsow sow, tmilestone tms " + //
				"where a.recruiter_teamid=? and a.invoicetype=1 and nvl(a.void,0)=0 and nvl(a.approved,0)=1 " + //
				"and a.invoicedate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"and a.billing_contact=b.id(+) and a.recruiter_teamid=b.teamid(+) " + //
				"and b.companyid=c.id(+) and b.teamid=c.teamid(+) " + //
				"and a.employeeid=d.id and a.recruiter_teamid=d.teamid " + //
				"and a.employeeid=e.employeeid and a.recruiter_teamid=e.recruiter_teamid and a.billingid=e.recid " + //
				"and e.rfqid=f.id(+) and e.recruiter_teamid=f.teamid(+) " + //
				"and e.division=h.id(+) and e.recruiter_teamid=h.teamid(+) " + //
				"and a.sowid = sow.id(+) and a.recruiter_teamid = sow.teamid(+) " + //
				"and a.sow_milestoneid = tms.id(+) and a.sowid = tms.sowid(+) and a.recruiter_teamid = tms.teamid(+) " + //
				"union all " + //
				"select c.name company, b.firstname|| ' ' ||b.lastname billingcontact, 'Whole Project' invoicetype, a.invoiceid, a.invoiceid_client, a.invoicedate, a.datecreated, null, null, " + //
				"d.lastname||', '||d.firstname employee, " + //
				"null, null, null, null, null, null,null, null, null, null, null, null, null, null, nvl(a.subtotal,a.amount), a.discount, f.name division, a.amount DUE, " + //
				"nvl(c.INVOICE_CONTACT_PREFERENCE, 0) as SHOW_ON_INVOICE, null, a.group_invoiceid as group_invoiceid, " + //
				"null PRIMARY_SALES , null SECONDARY_SALES, null TERTIARY_SALES, null PRIMARY_RECRUITER, null SECONDARY_RECRUITER, null TERTIARY_RECRUITER, " + //
				"null sowname , null milestonename, (select po# from tpo_setting where id = a.poid and teamid = a.recruiter_teamid ) as poname, a.customer_refno_sub as customer_refno_name " + //
				"from temployee_invoice a, tcustomer b, tcustomercompany c, tcandidate d, tdivision f " + //
				"where a.recruiter_teamid=? and a.invoicetype=3 and nvl(a.void,0)=0 and nvl(a.approved,0)=1 " + //
				"and a.invoicedate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"and a.billing_contact=b.id(+) and a.recruiter_teamid=b.teamid(+) " + //
				"and b.companyid=c.id(+) and b.teamid=c.teamid(+) " + //
				"and a.employeeid=d.id and a.recruiter_teamid=d.teamid " + //
				"and a.division=f.id(+) and a.recruiter_teamid=f.teamid(+) " + //
				"union all " + //
				"select c.name company, b.firstname|| ' ' ||b.lastname billingcontact, 'Expense' invoicetype, a.invoiceid, a.invoiceid_client, a.invoicedate, a.datecreated, null, null, " + //
				"d.lastname||', '||d.firstname employee, " + //
				"null, null, null, null, null, null,null, null, null, null, null, null, null, null, nvl(a.subtotal,a.amount), nvl(a.discount,0), f.name division, a.amount DUE, " + //
				"nvl(c.INVOICE_CONTACT_PREFERENCE, 0) as SHOW_ON_INVOICE, e.PAYMENTTERMS as PAYMENT_TERM, a.group_invoiceid as group_invoiceid," + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.primary_salesperson) PRIMARY_SALES, " + //
				" (select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.secondary_salesperson) SECONDARY_SALES, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.tertiary_salesperson) TERTIARY_SALES, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.primary_recruiter) PRIMARY_RECRUITER," + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.secondary_recruiter) SECONDARY_RECRUITER," + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = e.recruiter_teamid and ID = e.tertiary_recruiter) TERTIARY_RECRUITER, " + //
				"sow.name as sowname, tms.name as milestonename, (select po# from tpo_setting where id = a.poid and teamid = a.recruiter_teamid ) as poname, " + //
				"a.customer_refno_sub as customer_refno_name " + //
				"from temployee_invoice a, tcustomer b, tcustomercompany c, tcandidate d, temployee_billingrecord e, tdivision f, tsow sow, tmilestone tms " + //
				"where a.recruiter_teamid=? and a.invoicetype=4 and nvl(a.void,0)=0 and nvl(a.approved,0)=1 " + //
				"and a.invoicedate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"and a.billing_contact=b.id(+) and a.recruiter_teamid=b.teamid(+) " + //
				"and b.companyid=c.id(+) and b.teamid=c.teamid(+) " + //
				"and a.employeeid=d.id and a.recruiter_teamid=d.teamid " + //
				"and a.employeeid=e.employeeid and a.recruiter_teamid=e.recruiter_teamid and a.billingid=e.recid " + //
				"and e.division=f.id(+) and e.recruiter_teamid=f.teamid(+) " + //
				"and a.sowid = sow.id(+) and a.recruiter_teamid = sow.teamid(+) " + //
				"and a.sow_milestoneid = tms.id(+) and a.sowid = tms.sowid(+) and a.recruiter_teamid = tms.teamid(+) " + //
				"union all " + //
				"select c.name company, b.firstname|| ' ' ||b.lastname billingcontact, 'Direct Placement' invoicetype, a.invoiceid, a.invoiceid_client, a.invoicedate, a.datecreated, null, null, " + //
				"d.lastname||', '||d.firstname employee, " + //
				"null, null, null, null, null, null,null, null, null, null, null, null, null, null, nvl(a.subtotal,a.amount), a.discount, f.name division, a.amount DUE, " + //
				"nvl(c.INVOICE_CONTACT_PREFERENCE, 0) as SHOW_ON_INVOICE, null, a.group_invoiceid as group_invoiceid, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = g.recruiter_teamid and ID = g.salespersonid) PRIMARY_SALES, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = g.recruiter_teamid and ID = g.salespersonid2) SECONDARY_SALES, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = g.recruiter_teamid and ID = g.salespersonid3) TERTIARY_SALES, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = g.recruiter_teamid and ID = g.recruiterid) PRIMARY_RECRUITER, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = g.recruiter_teamid and ID = g.recruiterid2) SECONDARY_RECRUITER, " + //
				"(select firstname || ' ' || lastname from trecruiter where groupid = g.recruiter_teamid and ID = g.recruiterid3) TERTIARY_RECRUITER, " + //
				"null sowname , null milestonename,(select po# from tpo_setting where id = a.poid and teamid = a.recruiter_teamid ) as poname, a.customer_refno_sub as customer_refno_name " + //
				"from temployee_invoice a, tcustomer b, tcustomercompany c, tcandidate d, tdivision f, tplacements g " + //
				"where a.recruiter_teamid=? and a.invoicetype=5 and nvl(a.void,0)=0 and nvl(a.approved,0)=1 " + //
				"and a.invoicedate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"and a.billing_contact=b.id(+) and a.recruiter_teamid=b.teamid(+) " + //
				"and b.companyid=c.id(+) and b.teamid=c.teamid(+) " + //
				"and a.employeeid=d.id and a.recruiter_teamid=d.teamid " + //
				"and a.division=f.id(+) and a.recruiter_teamid=f.teamid(+) " + //
				"and a.invoiceid = g.invoiceid and a.recruiter_teamid = g.recruiter_teamid " + //
				"union all " + //
				"select c.name company, b.firstname|| ' ' ||b.lastname billingcontact, 'SOW' invoicetype, a.invoiceid, a.invoiceid_client, a.invoicedate, a.datecreated, a.fromdate, a.todate, " + //
				"null,null, null, " + //
				"null, null,null,null,null,null,null,null,null,null,null,null, nvl(a.subtotal,a.amount), a.discount, h.name division, a.amount DUE, " + //
				"nvl(c.INVOICE_CONTACT_PREFERENCE, 0) as SHOW_ON_INVOICE, null, a.group_invoiceid as group_invoiceid, null, null, null,null,null,null, sow.name as sowname, tms.name as milestonename, (select po# from tpo_setting where id = a.poid and teamid = a.recruiter_teamid ) as poname, a.customer_refno_sub as customer_refno_name "
				+ //
				"from temployee_invoice a, tcustomer b, tcustomercompany c, tdivision h, tsow sow, tmilestone tms " + //
				"where a.recruiter_teamid=? and a.invoicetype=6 and nvl(a.void,0)=0 and nvl(a.approved,0)=1 " + //
				"and a.invoicedate between to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " + //
				"and a.billing_contact=b.id(+) and a.recruiter_teamid=b.teamid(+) " + //
				"and sow.companyid=c.id(+) and sow.teamid=c.teamid(+) " + //
				"and a.division=h.id(+) and a.recruiter_teamid=h.teamid(+) " + //
				"and a.sowid = sow.id and a.recruiter_teamid = sow.teamid " + //
				"and a.sow_milestoneid = tms.id and a.sowid = tms.sowid and a.recruiter_teamid = tms.teamid) ";
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
		param.add(clientID);
		param.add(fromDate);
		param.add(toDate);
		return sql;
	}
	
	////
	////
	public static String getEmployeeList1(Long clientID, String fromDate, String toDate, Vector<Object> param) { ////
		String sql = ////
				"select e.firstname, e.lastname, e.email, e.sysemail alternate_email, e.address1, e.address2, e.city, e.state, to_char(e.zipcode) zipcode, " + ////
						"  e.workphone, e.workphone_ext, e.homephone, e.homephone_ext, e.cellphone, e.cellphone_ext, " + ////
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.primary_salesperson) primary_sales, " + ////
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.secondary_salesperson) secondary_sales, " + ////
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.tertiary_salesperson) tertiary_sales, " + ////
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.primary_recruiter) primary_recruiter, " + ////
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.secondary_recruiter) secondary_recruiter, " + ////
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.tertiary_recruiter) tertiary_recruiter, " + ////
						"  bc.companyname company_name, (select c.name from tcustomercompany c where c.id=t2.subcontract_companyid) subcontractor, " + ////
						"  trfq.rfqno_team job#, trfq.rfqtitle job_title, " + ////
						"  (select nvl(x.name,y.name) from trfq_position_types x, trfq_position_types y where x.teamid(+)=t1.recruiter_teamid and x.id(+)=tint.contract " + ////
						"      and y.teamid(+)=0 and y.id(+)=x.id) position_type, " + ////
						"  cc.firstname||' '||cc.lastname client_contact, cc.companyname company_of_client_contact, " + ////
						"  bc.firstname||' '||bc.lastname billing_contact, bc.companyname company_of_billing_contact, " + ////
						"  (select d.name from tdivision d where d.id=t1.division) assignment_division, " + ////
						"  rec.firstname||' '||rec.lastname timesheet_approver_name, rec.email timesheet_approver_email, " + ////
						"  to_char(t1.start_date,'mm/dd/yyyy') bill_start, to_char(t1.datecreated_real,'mm/dd/yyyy') bill_entered, " + ////
						"  to_char(t1.end_date,'mm/dd/yyyy') current_bill_ends, to_char(t2.end_date,'mm/dd/yyyy') last_scheduled_pay_ends, " + ////
						"  tint.dateterminated termination_date, tint.date_terminate_record as termination_entered, " + ////
						"  (select x.description from treason_termination x where x.teamid=tint.recruiter_teamid and x.id=tint.reasonterminated) termination_reason, " + ////
						"  decode(t1.pass_through, 1, 'yes', ' ') passthrough, " + ////
						"  commision_rate(t1.employeeid, t1.recruiter_teamid, t1.recid, t2.recid, 2) hourly_spread,  " + ////
						"  t1.bill_rate, t1.bill_rate_per, decode(t1.overtimeexempt, 1, 'yes', 'no') bill_ot_exempt, " + ////
						"  t1.overtime_rate1 bill_ot_rate, t1.overtime_rate1_per bill_ot_per, t1.overtime_rate2 bill_dt_rate, t1.overtime_rate2_per bill_dt_per, " + ////
						"  bd.discount default_discount, bd.discount_unit default_discount_per, " + ////
						"  t1.bill_rate/decode(t1.bill_rate_per,'d',nvl(t1.hours_per_day,tteam.hourperday),'y',nvl(t1.hours_per_day,tteam.hourperday)*tteam.daypermonth,1) " + ////
						"  - (select sum(decode(tbd.discount_unit,'d',tbd.discount/nvl(t1.hours_per_day,tteam.hourperday),'y',tbd.discount/nvl(t1.hours_per_day,tteam.hourperday)/tteam.daypermonth," + ////
						"      '%',t1.bill_rate/decode(t1.bill_rate_per,'d',nvl(t1.hours_per_day,tteam.hourperday),'y',nvl(t1.hours_per_day,tteam.hourperday)*tteam.daypermonth,1)*tbd.discount/100,tbd.discount)) " + ////
						"      from temployee_billingdiscount tbd where tbd.employeeid=t1.employeeid and tbd.teamid=t1.recruiter_teamid and tbd.recid=t1.recid) net_bill_per_hour, " + ////
						"  t2.salary pay, t2.salary_per pay_per, decode(t2.overtimeexempt, 1, 'yes', 'no') pay_ot_exempt, " + ////
						"  t2.overtime_rate1 pay_ot_rate, t2.overtime_rate1_per pay_ot_per, t2.overtime_rate2 pay_dt_rate, t2.overtime_rate2_per pay_dt_per, t2.per_diem, t2.per_diem_per, " + ////
						"  t2.other_expenses other_expenses, t2.other_expenses_per as expenses_per, t2.outside_commission,  t2.outside_commission_per commission_per," + ////
						"  decode(t2.status, 1, 'hourly', 2, 'subcontract', 3, 'employee', '') employee_status, tpo.po# purchasing_order, tpo.amount po_amount, " + ////
						"  tpo.hours po_hours, tpo.start_date po_start_date, tpo.end_date po_end_date, t1.customer_refno_sub customer_ref_no, " + ////
						"  decode(t1.invoice_group_index,1,'Job',2,'Company',3,'Customer',4,'P.O.#',5,'Employee',6,'Customer Ref No') group_invoice_by, " + ////
						"  t1.vmsemployeename vms_emp_name, t1.vms_website vms_timesheet_website, " + ////
						"  decode(t1.grouping_expenses, '0','generate invoices from timesheets only','include related expenses with timesheet invoices') invoice_content, " + ////
						"  decode(t1.expense_display_categorytotal,0,'Display Expense Details',1,'Display Category Totals','') generating_expense_invoice, " + ////
						"  decode(t1.allow_enter_time_on_portal,1,'Yes','No') allow_to_enter_timesheets, " + ////
						"  decode(t1.expenseenabled,1,'Yes','No') allow_to_enter_expense, " + ////
						"  decode(t1.overtimeexempt, 1, '', 0, decode(t1.ot_by_working_state,0,'manually distribute',1,'auto-calculate by working state'," + ////
						"      2,'auto-calculate by federal 40-hour rule',3,'auto-calculate by 4/10 work week'),'') overtime, " + ////
						"  decode(bd.apply_to_invoice,1,'yes','no') apply_discount_to_invoice, decode(t1.timesheet_entry_format,3,'time in/time out','default') timesheet_entry_format, " + ////
						"  decode(t1.frequency,1,'bi weekly',2,'monthly',3,'semi monthly',4,'weekly',5,'whole project',6,'monthly ending weekend','') frequency, " + ////
						"  decode(t1.billing_unit,1,'hourly',2,'daily: based on hours',3,'daily: half-day',4,'daily: half-day (ot)',5,'daily: bill whole day',6,'daily: bill whold day (ot)','') billing_unit, " + ////
						"  decode(t1.week_ending,1,'sunday',2,'saturday',3,'friday',4,'thursday',5,'wednesday',6,'tuesday',7,'monday','') bill_week_ending, t1.hours_per_day, " + ////
						"  nvl(t1.working_city,t1.work_city) working_city, t1.working_state, t1.working_country, t1.paymentterms payment_terms_bill, " + ////
						"  (select name from tcustomercompany tcomp where tcomp.id = t2.subcontract_companyid) corporation, t2.taxid tax_id, " + ////
						"  decode(t2.subcontract_payonremit,1,'yes','no') pay_on_remittance, t2.paymentterms payment_terms_pay, t2.adp_file_no file_number, t2.adpcocode as co_code, " + ////
						"  t2.adppayfrequency as tax_frequency, get_recurrentexpenses_list(t1.employeeid, t1.recruiter_teamid, t1.recid) recurrent_expenses, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=50) annual_salary, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=45) class_1_location_ins_type, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=46) class_2_employee_type, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=47) class_3_hours_worked, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=60) class_4_subsidiary, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=51) current_status, " + ////
						"  (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'mm/dd/yyyy') from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=28) date_of_birth, "
						+ ////
						"  (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'mm/dd/yyyy') from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=29) date_of_original_employment, "
						+ ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=26) do_not_use, " + ////
						"  (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'mm/dd/yyyy') from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=33) effective_date, "
						+ ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=44) exclude_from_workterra, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=49) gender, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=35) jde_business_unit, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=25) jde_emp_number, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=52) location, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=62) ns_company_name, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=61) ns_project_name, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=63) payment_terms, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=48) payroll_schedule, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=57) practice, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=36) ssn, " + ////
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=53) subsidiary_company," + ////
						"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=6) pto, " + ////
						"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + ////
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=6 " + ////
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + ////
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) pto_overhead_value," + ////
						"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + ////
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=6 " + ////
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + ////
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) pto_overhead_per, " + ////
						"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=1) w2, " + ////
						"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + ////
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=1 " + ////
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + ////
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) w2_overhead_value," + ////
						"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + ////
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=1 " + ////
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + ////
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) w2_overhead_per, " + ////
						"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=2) insurance, " + ////
						"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + ////
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=2 " + ////
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + ////
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) insurance_overhead_value," + ////
						"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + ////
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=2 " + ////
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + ////
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) insurance_overhead_per, " + ////
						"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=7) vocation, " + ////
						"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + ////
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=7 " + ////
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + ////
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) vocation_overhead_value," + ////
						"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + ////
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=7 " + ////
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + ////
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) vacation_overhead_per, " + ////
						"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=3) corp_to_corp, " + ////
						"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + ////
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=3 " + ////
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + ////
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) corp_to_corp_overhead_value," + ////
						"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + ////
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=3 " + ////
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + ////
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) corp_to_corp_overhead_per " + ////
						"from temployee_billingrecord t1, temployee_salaryrecord t2, tcustomer bc, tcustomer cc, tcandidate e, trfq, tinterviewschedule tint, temployee_billingdiscount bd, " + ////
						"    trecruiter rec, tpo_setting tpo, tteam " + ////
						"where t1.recruiter_teamid = ? and nvl(t1.closed,0)=0 and t1.approved=1 and (t1.end_date is null or t1.end_date >= sysdate) and t1.start_date <= sysdate " + ////
						"  and t2.recruiter_teamid = t1.recruiter_teamid and t2.employeeid = t1.employeeid and t2.interviewid=t1.interviewid " + ////
						"  and nvl(t2.closed,0)=0 and t2.approved=1 and (t2.end_date is null or t2.end_date >= sysdate) and t2.effective_date <= sysdate " + ////
						"  and t2.effective_date = (select max(effective_date) from temployee_salaryrecord s where s.employeeid=t1.employeeid and s.recruiter_teamid=t1.recruiter_teamid and s.interviewid=t1.interviewid " + ////
						"  		and nvl(s.closed,0)=0 and s.approved=1 and s.effective_date <= sysdate and (s.end_date is null or s.end_date >= sysdate)) " + ////
						"  and bc.id=t1.billing_contact and cc.id=t1.hiring_manager " + ////
						"  and e.id=t1.employeeid and e.teamid=t1.recruiter_teamid " + ////
						"  and trfq.id = t1.rfqid and tint.id=t1.interviewid " + ////
						"  and bd.employeeid(+)=t1.employeeid and bd.teamid(+)=t1.recruiter_teamid and bd.recid(+)=t1.recid and bd.discountid(+)=0 " + ////
						"  and t1.recruiter_teamid = rec.groupid(+) and t1.approverid = rec.id(+) " + ////
						"  and tteam.id=t1.recruiter_teamid " + ////
						"  and t1.poid = tpo.id(+) and t1.recruiter_teamid = tpo.teamid(+) " + ////
						"order by e.firstname, e.lastname, t1.end_date desc";
		param.add(clientID);
		return sql;
	}
	
	public static String getEmployeeList2(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = //
				"select e.firstname, e.lastname, e.email, e.sysemail alternate_email, e.address1, e.address2, e.city, e.state, to_char(e.zipcode) zipcode, " + //
						"  e.workphone, e.workphone_ext, e.homephone, e.homephone_ext, e.cellphone, e.cellphone_ext, " + //
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.primary_salesperson) primary_sales, " + //
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.secondary_salesperson) secondary_sales, " + //
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.tertiary_salesperson) tertiary_sales, " + //
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.primary_recruiter) primary_recruiter, " + //
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.secondary_recruiter) secondary_recruiter, " + //
						"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.tertiary_recruiter) tertiary_recruiter, " + //
						"  bc.companyname company_name, (select c.name from tcustomercompany c where c.id=t2.subcontract_companyid) subcontractor, " + //
						"  trfq.rfqno_team job#, trfq.rfqtitle job_title, " + //
						"  (select nvl(x.name,y.name) from trfq_position_types x, trfq_position_types y where x.teamid(+)=t1.recruiter_teamid and x.id(+)=tint.contract " + //
						"      and y.teamid(+)=0 and y.id(+)=x.id) position_type, " + //
						"  cc.firstname||' '||cc.lastname client_contact, cc.companyname company_of_client_contact, " + //
						"  bc.firstname||' '||bc.lastname billing_contact, bc.companyname company_of_billing_contact, " + //
						"  (select d.name from tdivision d where d.id=t1.division) assignment_division, " + //
						"  rec.firstname||' '||rec.lastname timesheet_approver_name, rec.email timesheet_approver_email, " + //
						"  to_char(t1.start_date,'mm/dd/yyyy') bill_start, to_char(t1.datecreated_real,'mm/dd/yyyy') bill_entered, " + //
						"  to_char(t1.end_date,'mm/dd/yyyy') current_bill_ends, to_char(t2.end_date,'mm/dd/yyyy') last_scheduled_pay_ends, " + //
						"  tint.dateterminated termination_date, tint.date_terminate_record as termination_entered, " + //
						"  (select x.description from treason_termination x where x.teamid=tint.recruiter_teamid and x.id=tint.reasonterminated) termination_reason, " + //
						"  decode(t1.pass_through, 1, 'yes', ' ') passthrough, " + //
						"  commision_rate(t1.employeeid, t1.recruiter_teamid, t1.recid, t2.recid, 2) hourly_spread,  " + //
						"  t1.bill_rate, t1.bill_rate_per, decode(t1.overtimeexempt, 1, 'yes', 'no') bill_ot_exempt, " + //
						"  t1.overtime_rate1 bill_ot_rate, t1.overtime_rate1_per bill_ot_per, t1.overtime_rate2 bill_dt_rate, t1.overtime_rate2_per bill_dt_per, " + //
						"  bd.discount default_discount, bd.discount_unit default_discount_per, " + //
						"  t1.bill_rate/decode(t1.bill_rate_per,'d',nvl(t1.hours_per_day,tteam.hourperday),'y',nvl(t1.hours_per_day,tteam.hourperday)*tteam.daypermonth,1) " + //
						"  - (select sum(decode(tbd.discount_unit,'d',tbd.discount/nvl(t1.hours_per_day,tteam.hourperday),'y',tbd.discount/nvl(t1.hours_per_day,tteam.hourperday)/tteam.daypermonth," + //
						"      '%',t1.bill_rate/decode(t1.bill_rate_per,'d',nvl(t1.hours_per_day,tteam.hourperday),'y',nvl(t1.hours_per_day,tteam.hourperday)*tteam.daypermonth,1)*tbd.discount/100,tbd.discount)) " + //
						"      from temployee_billingdiscount tbd where tbd.employeeid=t1.employeeid and tbd.teamid=t1.recruiter_teamid and tbd.recid=t1.recid) net_bill_per_hour, " + //
						"  t2.salary pay, t2.salary_per pay_per, decode(t2.overtimeexempt, 1, 'yes', 'no') pay_ot_exempt, " + //
						"  t2.overtime_rate1 pay_ot_rate, t2.overtime_rate1_per pay_ot_per, t2.overtime_rate2 pay_dt_rate, t2.overtime_rate2_per pay_dt_per, t2.per_diem, t2.per_diem_per, " + //
						"  t2.other_expenses other_expenses, t2.other_expenses_per as expenses_per, t2.outside_commission,  t2.outside_commission_per commission_per," + //
						"  decode(t2.status, 1, 'hourly', 2, 'subcontract', 3, 'employee', '') employee_status, tpo.po# purchasing_order, tpo.amount po_amount, " + //
						"  tpo.hours po_hours, tpo.start_date po_start_date, tpo.end_date po_end_date, t1.customer_refno_sub customer_ref_no, " + //
						"  decode(t1.invoice_group_index,1,'Job',2,'Company',3,'Customer',4,'P.O.#',5,'Employee','') group_invoice_by, " + //
						"  t1.vmsemployeename vms_emp_name, t1.vms_website vms_timesheet_website, " + //
						"  decode(t1.grouping_expenses, '0','generate invoices from timesheets only','include related expenses with timesheet invoices') invoice_content, " + //
						"  decode(t1.expense_display_categorytotal,0,'Display Expense Details',1,'Display Category Totals','') generating_expense_invoice, " + //
						"  decode(t1.allow_enter_time_on_portal,1,'Yes','No') allow_to_enter_timesheets, " + //
						"  decode(t1.expenseenabled,1,'Yes','No') allow_to_enter_expense, " + //
						"  decode(t1.overtimeexempt, 1, '', 0, decode(t1.ot_by_working_state,0,'manually distribute',1,'auto-calculate by working state'," + //
						"      2,'auto-calculate by federal 40-hour rule',3,'auto-calculate by 4/10 work week'),'') overtime, " + //
						"  decode(bd.apply_to_invoice,1,'yes','no') apply_discount_to_invoice, decode(t1.timesheet_entry_format,3,'time in/time out','default') timesheet_entry_format, " + //
						"  decode(t1.frequency,1,'bi weekly',2,'monthly',3,'semi monthly',4,'weekly',5,'whole project',6,'monthly ending weekend','') frequency, " + //
						"  decode(t1.billing_unit,1,'hourly',2,'daily: based on hours',3,'daily: half-day',4,'daily: half-day (ot)',5,'daily: bill whole day',6,'daily: bill whold day (ot)','') billing_unit, " + //
						"  decode(t1.week_ending,1,'sunday',2,'saturday',3,'friday',4,'thursday',5,'wednesday',6,'tuesday',7,'monday','') bill_week_ending, t1.hours_per_day, " + //
						"  nvl(t1.working_city,t1.work_city) working_city, t1.working_state, t1.working_country, t1.paymentterms payment_terms_bill, " + //
						"  (select name from tcustomercompany tcomp where tcomp.id = t2.subcontract_companyid) corporation, t2.taxid tax_id, " + //
						"  decode(t2.subcontract_payonremit,1,'yes','no') pay_on_remittance, t2.paymentterms payment_terms_pay, t2.adp_file_no file_number, t2.adpcocode as co_code, " + //
						"  t2.adppayfrequency as tax_frequency, get_recurrentexpenses_list(t1.employeeid, t1.recruiter_teamid, t1.recid) recurrent_expenses, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=50) annual_salary, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=45) class_1_location_ins_type, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=46) class_2_employee_type, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=47) class_3_hours_worked, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=60) class_4_subsidiary, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=51) current_status, " + //
						"  (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'mm/dd/yyyy') from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=28) date_of_birth, "
						+ //
						"  (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'mm/dd/yyyy') from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=29) date_of_original_employment, "
						+ //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=26) do_not_use, " + //
						"  (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'mm/dd/yyyy') from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=33) effective_date, "
						+ //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=44) exclude_from_workterra, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=49) gender, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=35) jde_business_unit, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=25) jde_emp_number, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=52) location, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=62) ns_company_name, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=61) ns_project_name, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=63) payment_terms, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=48) payroll_schedule, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=57) practice, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=36) ssn, " + //
						"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=53) subsidiary_company," + //
						"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=6) pto, " + //
						"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=6 " + //
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) pto_overhead_value," + //
						"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=6 " + //
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) pto_overhead_per, " + //
						"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=1) w2, " + //
						"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=1 " + //
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) w2_overhead_value," + //
						"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=1 " + //
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) w2_overhead_per, " + //
						"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=2) insurance, " + //
						"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=2 " + //
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) insurance_overhead_value," + //
						"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=2 " + //
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) insurance_overhead_per, " + //
						"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=7) vocation, " + //
						"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=7 " + //
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) vocation_overhead_value," + //
						"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=7 " + //
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) vacation_overhead_per, " + //
						"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=3) corp_to_corp, " + //
						"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=3 " + //
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) corp_to_corp_overhead_value," + //
						"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
						"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=3 " + //
						"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
						"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) corp_to_corp_overhead_per " + //
						"from temployee_billingrecord t1, temployee_salaryrecord t2, tstartrecord_userfields u, tcustomer bc, tcustomer cc, tcandidate e, trfq, tinterviewschedule tint, temployee_billingdiscount bd, " + //
						"    trecruiter rec, tpo_setting tpo, tteam " + //
						"where t1.recruiter_teamid = ? and nvl(t1.closed,0)=0 and t1.approved=1 and (t1.end_date is null or t1.end_date >= sysdate) and t1.start_date <= sysdate " + //
						"  and t2.recruiter_teamid = t1.recruiter_teamid and t2.employeeid = t1.employeeid and t2.interviewid=t1.interviewid " + //
						"  and nvl(t2.closed,0)=0 and t2.approved=1 and (t2.end_date is null or t2.end_date >= sysdate) and t2.effective_date <= sysdate " + //
						"  and t2.effective_date = (select max(effective_date) from temployee_salaryrecord s where s.employeeid=t1.employeeid and s.recruiter_teamid=t1.recruiter_teamid and s.interviewid=t1.interviewid " + //
						"  		and nvl(s.closed,0)=0 and s.approved=1 and s.effective_date <= sysdate and (s.end_date is null or s.end_date >= sysdate)) " + //
						"  and u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=44 and u.userfield_value='No' " + //
						"  and bc.id=t1.billing_contact and cc.id=t1.hiring_manager " + //
						"  and e.id=t1.employeeid and e.teamid=t1.recruiter_teamid " + //
						"  and trfq.id = t1.rfqid and tint.id=t1.interviewid " + //
						"  and bd.employeeid(+)=t1.employeeid and bd.teamid(+)=t1.recruiter_teamid and bd.recid(+)=t1.recid and bd.discountid(+)=0 " + //
						"  and t1.recruiter_teamid = rec.groupid(+) and t1.approverid = rec.id(+) " + //
						"  and tteam.id=t1.recruiter_teamid " + //
						"  and t1.poid = tpo.id(+) and t1.recruiter_teamid = tpo.teamid(+) " + //
						"order by e.firstname, e.lastname, t1.end_date desc";
		param.add(clientID);
		return sql;
	}
	
	public static String getEmployeeList3(Long clientID, String fromDate, String toDate, Vector<Object> param) {
		String sql = "select e.firstname, e.lastname, e.email, e.sysemail alternate_email, e.address1, e.address2, e.city, e.state, to_char(e.zipcode) zipcode, " + //
				"  e.workphone, e.workphone_ext, e.homephone, e.homephone_ext, e.cellphone, e.cellphone_ext, " + //
				"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.primary_salesperson) primary_sales, " + //
				"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.secondary_salesperson) secondary_sales, " + //
				"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.tertiary_salesperson) tertiary_sales, " + //
				"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.primary_recruiter) primary_recruiter, " + //
				"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.secondary_recruiter) secondary_recruiter, " + //
				"  (select r.firstname||' '||r.lastname from trecruiter r where r.id=t1.tertiary_recruiter) tertiary_recruiter, " + //
				"  bc.companyname company_name, (select c.name from tcustomercompany c where c.id=t2.subcontract_companyid) subcontractor, " + //
				"  trfq.rfqno_team job#, trfq.rfqtitle job_title, " + //
				"  (select nvl(x.name,y.name) from trfq_position_types x, trfq_position_types y where x.teamid(+)=t1.recruiter_teamid and x.id(+)=tint.contract " + //
				"      and y.teamid(+)=0 and y.id(+)=x.id) position_type, " + //
				"  cc.firstname||' '||cc.lastname client_contact, cc.companyname company_of_client_contact, " + //
				"  bc.firstname||' '||bc.lastname billing_contact, bc.companyname company_of_billing_contact, " + //
				"  (select d.name from tdivision d where d.id=t1.division) assignment_division, " + //
				"  rec.firstname||' '||rec.lastname timesheet_approver_name, rec.email timesheet_approver_email, " + //
				"  to_char(t1.start_date,'mm/dd/yyyy') bill_start, to_char(t1.datecreated_real,'mm/dd/yyyy') bill_entered, " + //
				"  to_char(t1.end_date,'mm/dd/yyyy') current_bill_ends, to_char(t2.end_date,'mm/dd/yyyy') last_scheduled_pay_ends, " + //
				"  tint.dateterminated termination_date, tint.date_terminate_record as termination_entered, " + //
				"  (select x.description from treason_termination x where x.teamid=tint.recruiter_teamid and x.id=tint.reasonterminated) termination_reason, " + //
				"  decode(t1.pass_through, 1, 'yes', ' ') passthrough, " + //
				"  commision_rate(t1.employeeid, t1.recruiter_teamid, t1.recid, t2.recid, 2) hourly_spread,  " + //
				"  t1.bill_rate, t1.bill_rate_per, decode(t1.overtimeexempt, 1, 'yes', 'no') bill_ot_exempt, " + //
				"  t1.overtime_rate1 bill_ot_rate, t1.overtime_rate1_per bill_ot_per, t1.overtime_rate2 bill_dt_rate, t1.overtime_rate2_per bill_dt_per, " + //
				"  bd.discount default_discount, bd.discount_unit default_discount_per, " + //
				"  t1.bill_rate/decode(t1.bill_rate_per,'d',nvl(t1.hours_per_day,tteam.hourperday),'y',nvl(t1.hours_per_day,tteam.hourperday)*tteam.daypermonth,1) " + //
				"  - (select sum(decode(tbd.discount_unit,'d',tbd.discount/nvl(t1.hours_per_day,tteam.hourperday),'y',tbd.discount/nvl(t1.hours_per_day,tteam.hourperday)/tteam.daypermonth," + //
				"      '%',t1.bill_rate/decode(t1.bill_rate_per,'d',nvl(t1.hours_per_day,tteam.hourperday),'y',nvl(t1.hours_per_day,tteam.hourperday)*tteam.daypermonth,1)*tbd.discount/100,tbd.discount)) " + //
				"      from temployee_billingdiscount tbd where tbd.employeeid=t1.employeeid and tbd.teamid=t1.recruiter_teamid and tbd.recid=t1.recid) net_bill_per_hour, " + //
				"  t2.salary pay, t2.salary_per pay_per, decode(t2.overtimeexempt, 1, 'yes', 'no') pay_ot_exempt, " + //
				"  t2.overtime_rate1 pay_ot_rate, t2.overtime_rate1_per pay_ot_per, t2.overtime_rate2 pay_dt_rate, t2.overtime_rate2_per pay_dt_per, t2.per_diem, t2.per_diem_per, " + //
				"  t2.other_expenses other_expenses, t2.other_expenses_per as expenses_per, t2.outside_commission,  t2.outside_commission_per commission_per," + //
				"  decode(t2.status, 1, 'hourly', 2, 'subcontract', 3, 'employee', '') employee_status, tpo.po# purchasing_order, tpo.amount po_amount, " + //
				"  tpo.hours po_hours, tpo.start_date po_start_date, tpo.end_date po_end_date, t1.customer_refno_sub customer_ref_no, " + //
				"  decode(t1.invoice_group_index,1,'Job',2,'Company',3,'Customer',4,'P.O.#',5,'Employee','') group_invoice_by, " + //
				"  t1.vmsemployeename vms_emp_name, t1.vms_website vms_timesheet_website, " + //
				"  decode(t1.grouping_expenses, '0','generate invoices from timesheets only','include related expenses with timesheet invoices') invoice_content, " + //
				"  decode(t1.expense_display_categorytotal,0,'Display Expense Details',1,'Display Category Totals','') generating_expense_invoice, " + //
				"  decode(t1.allow_enter_time_on_portal,1,'Yes','No') allow_to_enter_timesheets, " + //
				"  decode(t1.expenseenabled,1,'Yes','No') allow_to_enter_expense, " + //
				"  decode(t1.overtimeexempt, 1, '', 0, decode(t1.ot_by_working_state,0,'manually distribute',1,'auto-calculate by working state'," + //
				"      2,'auto-calculate by federal 40-hour rule',3,'auto-calculate by 4/10 work week'),'') overtime, " + //
				"  decode(bd.apply_to_invoice,1,'yes','no') apply_discount_to_invoice, decode(t1.timesheet_entry_format,3,'time in/time out','default') timesheet_entry_format, " + //
				"  decode(t1.frequency,1,'bi weekly',2,'monthly',3,'semi monthly',4,'weekly',5,'whole project',6,'monthly ending weekend','') frequency, " + //
				"  decode(t1.billing_unit,1,'hourly',2,'daily: based on hours',3,'daily: half-day',4,'daily: half-day (ot)',5,'daily: bill whole day',6,'daily: bill whold day (ot)','') billing_unit, " + //
				"  decode(t1.week_ending,1,'sunday',2,'saturday',3,'friday',4,'thursday',5,'wednesday',6,'tuesday',7,'monday','') bill_week_ending, t1.hours_per_day, " + //
				"  nvl(t1.working_city,t1.work_city) working_city, t1.working_state, t1.working_country, t1.paymentterms payment_terms_bill, " + //
				"  (select name from tcustomercompany tcomp where tcomp.id = t2.subcontract_companyid) corporation, t2.taxid tax_id, " + //
				"  decode(t2.subcontract_payonremit,1,'yes','no') pay_on_remittance, t2.paymentterms payment_terms_pay, t2.adp_file_no file_number, t2.adpcocode as co_code, " + //
				"  t2.adppayfrequency as tax_frequency, get_recurrentexpenses_list(t1.employeeid, t1.recruiter_teamid, t1.recid) recurrent_expenses, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=50) annual_salary, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=45) class_1_location_ins_type, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=46) class_2_employee_type, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=47) class_3_hours_worked, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=60) class_4_subsidiary, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=51) current_status, " + //
				"  (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'mm/dd/yyyy') from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=28) date_of_birth, " + //
				"  (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'mm/dd/yyyy') from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=29) date_of_original_employment, "
				+ //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=26) do_not_use, " + //
				"  (select to_char(to_date('01-jan-1970')+to_number(u.userfield_value)/86400000,'mm/dd/yyyy') from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=33) effective_date, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=44) exclude_from_workterra, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=49) gender, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=35) jde_business_unit, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=25) jde_emp_number, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=52) location, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=62) ns_company_name, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=61) ns_project_name, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=63) payment_terms, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=48) payroll_schedule, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=57) practice, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=36) ssn, " + //
				"  (select u.userfield_value from tstartrecord_userfields u where u.startid=t1.interviewid and u.teamid=t1.recruiter_teamid and u.userfield_id=53) subsidiary_company," + //
				"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=6) pto, " + //
				"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
				"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=6 " + //
				"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
				"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) pto_overhead_value," + //
				"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
				"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=6 " + //
				"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
				"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) pto_overhead_per, " + //
				"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=1) w2, " + //
				"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
				"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=1 " + //
				"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
				"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) w2_overhead_value," + //
				"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
				"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=1 " + //
				"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
				"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) w2_overhead_per, " + //
				"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=2) insurance, " + //
				"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
				"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=2 " + //
				"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
				"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) insurance_overhead_value," + //
				"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
				"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=2 " + //
				"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
				"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) insurance_overhead_per, " + //
				"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=7) vocation, " + //
				"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
				"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=7 " + //
				"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
				"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) vocation_overhead_value," + //
				"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
				"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=7 " + //
				"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
				"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) vacation_overhead_per, " + //
				"  (select 'Yes' from temployee_overhead o where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=3) corp_to_corp, " + //
				"  (select nvl(d1.rate,d2.rate) from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
				"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=3 " + //
				"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
				"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) corp_to_corp_overhead_value," + //
				"  (select decode(nvl(d1.ratetype,d2.ratetype),0,'%',1,'$','') from temployee_overhead o, tteam_overhead_detail d1, tteam_overhead_detail d2 " + //
				"   where o.employeeid=t2.employeeid and o.recruiter_teamid=t2.recruiter_teamid and o.salary_recid=t2.recid and o.overheadid=3 " + //
				"     and d1.teamid(+)=o.recruiter_teamid and d1.overheadid(+)=o.overheadid and d1.divisionid(+)=t1.division " + //
				"     and d2.teamid(+)=o.recruiter_teamid and d2.overheadid(+)=o.overheadid and d2.divisionid(+)=0) corp_to_corp_overhead_per " + //
				"from temployee_billingrecord t1, temployee_salaryrecord t2, tinterviewschedule tint, tcustomer bc, tcustomer cc, tcandidate e, trfq, temployee_billingdiscount bd, " + //
				"    trecruiter rec, tpo_setting tpo, tteam " + //
				"where t1.recruiter_teamid = ? and nvl(t1.closed,0)=0 and t1.approved=1 and t1.start_date <= sysdate " + //
				"  and t2.recruiter_teamid = t1.recruiter_teamid and t2.employeeid = t1.employeeid and t2.interviewid=t1.interviewid " + //
				"  and nvl(t2.closed,0)=0 and t2.approved=1 and t2.effective_date <= sysdate " + //
				"  and t2.effective_date = (select max(effective_date) from temployee_salaryrecord s where s.employeeid=t1.employeeid and s.recruiter_teamid=t1.recruiter_teamid and s.interviewid=t1.interviewid " + //
				"  		and nvl(s.closed,0)=0 and s.approved=1 and s.effective_date <= sysdate and (s.end_date is null or s.end_date >= sysdate)) " + //
				"  and tint.id=t1.interviewid and tint.date_terminate_record between trunc(sysdate) and trunc(sysdate+1)-1/24/3600 " + //
				"  and bc.id=t1.billing_contact and cc.id=t1.hiring_manager " + //
				"  and e.id=t1.employeeid and e.teamid=t1.recruiter_teamid " + //
				"  and trfq.id = t1.rfqid " + //
				"  and bd.employeeid(+)=t1.employeeid and bd.teamid(+)=t1.recruiter_teamid and bd.recid(+)=t1.recid and bd.discountid(+)=0 " + //
				"  and t1.recruiter_teamid = rec.groupid(+) and t1.approverid = rec.id(+) " + //
				"  and tteam.id=t1.recruiter_teamid " + //
				"  and t1.poid = tpo.id(+) and t1.recruiter_teamid = tpo.teamid(+) " + "order by e.firstname, e.lastname, t1.end_date desc";
		param.add(clientID);
		return sql;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}

package com.jobdiva.api.dao.bi;

import java.util.Vector;

public class BuildspaceFeed {
	
	public static String clientReferences(Long clientID, Vector<Object> param) {
		String sql = //
				"SELECT (SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 26) \"Client Ref\", " + //
						"cc.NAME \"Client Name\", ca.ADDRESS1 \"Client Address 1\", ca.ADDRESS2 \"Client Address 2\", ca.CITY \"Client Address 3\", ca.STATE \"Client Address 4\", " + //
						"'' \"Client Address 5\", ca.ZIPCODE \"Client Postcode\", ca.COUNTRYID \"Client Country\", ca.PHONE||' ' \"Client Telephone\", ca.FAX||' ' \"Client Fax No\", " + //
						"ca.PHONE||' ' \"Client Mobile\", '' \"Client Email\", '' \"Personnel Contact\", '' \"Personnel Telephone\", '' \"Personnel Mobile\", '' \"Personnel Email\", " + //
						"cc.NAME \"Invoice Client Name\", " + //
						"(CASE WHEN cnc.BILLING_CONTACT_ID IS NULL THEN NULL ELSE c.FIRSTNAME ||' '||c.LASTNAME END) \"Invoicing Contact\", " + //
						"(CASE WHEN cnc.BILLING_CONTACT_ID IS NULL THEN NULL ELSE c.WORKPHONE END) \"Invoicing Telephone\", " + //
						"'' \"Invoicing Mobile\", (CASE WHEN cnc.BILLING_CONTACT_ID IS NULL THEN NULL ELSE c.EMAIL END) \"Invoicing Email\", " + //
						"'' \"Produce Invoice\", '' \"Invoice Type (Legacy invoicing only)\", " + //
						"DECODE(cc.FINANCIAL_FREQUENCY,0,'',cc.FINANCIAL_FREQUENCY) \"Invoice Frequency\", '' \"Invoice Order\", '' \"Invoice Calculation Group\", 'Y' \"Vatable?\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 20) \"VAT Number\", " + //
						"'' \"Euro VAT Number\", 'GBP' \"Currency code\", '' \"Credit Check\", '' \"Credit Limit\", '' \"Credit Stop\", " + //
						"DECODE(cc.PAYMENTTERMS,0,'',cc.PAYMENTTERMS) \"Settlement Days\", " + //
						"'' \"PO Required\", '16625' \"Employer Ref\", '16625' \"Division\", '140' \"Department\", " + //
						"'' \"Job Category\", '' \"Report to Client\", '' \"Invoice to Client\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 44) \"Consultant Code\", " + //
						"'' \"Cost Centre Required\", '' \"Fixed NI Percentage\", '' \"Client WTR Percentage\", '' \"Client WTR Margin Accrual Percentage\", " + //
						"'' \"Notes\", '' \"Default VAT Rate\", '' \"Allow Online Expenses\", '' \"Client to Authorise Online Expenses\", '' \"Disable Email Generation\", " + //
						"'' \"Enable Client Self Billing\", '' \"Enable PreBilling\", '' \"Print VAT on PreBill\", '' \"Show Negative Timesheets On PreBill\", " + //
						"'' \"Invoice Delivery Method\", '' \"Master Document\", '' \"Accounts AC Ref\", '' \"Legal Hirer\", '' \"Default Assignment Type\", " + //
						"'' \"Fixed Pension Percentage\", '' \"Customer_Defined_Field_1\", '' \"Customer_Defined_Field_2\", '' \"Customer_Defined_Field_3\", " + //
						"'' \"Customer_Defined_Field_4\", '' \"Workflow Description\", '' \"Online_Authorisation\", '' \"TS CRF Group Code\", '' \"PLC CRF Group Code\", " + //
						"'' \"Online Timesheet Type\", '' \"Online Escalation Type\", '' \"Included in GP report\", '' \"Print Docs With Invoice\", '' \"VAT Area\", " + //
						"'' \"VAT Exempt\", '' \"EU VAT Exemption Number\", '' \"Geofence Distance\", '' \"EIR Direct Client\", '' \"Print Invoice CRFs\", " + //
						"'' \"Settlement Term Code\", 'N' \"Public Sector\", 'N' \"Always OPWIPS\", '' \"DataHub ID\" " + //
						"from " + //
						"    (select COMPANYID, TEAMID, (select id from tcustomer c, tcustomer_userfields u where c.teamid=comp.teamid and c.companyid=comp.companyid " + //
						"    and u.teamid=c.teamid and u.customerid=c.id and u.userfield_id=50 and u.userfield_value='Yes') BILLING_CONTACT_ID " + //
						"        from (select distinct COMPANYID, recruiter_teamid TEAMID " + //
						"            from temployee_invoice e, tcustomer cu " + //
						"            where recruiter_teamid = ? and approved=1 and nvl(closed,0)=0 and cu.id=e.billing_contact) comp " + //
						"    ) cnc JOIN tcustomercompany cc ON cnc.TEAMID = cc.TEAMID AND cnc.COMPANYID = cc.ID " + //
						"    JOIN tcustomercompanyaddresses ca ON cc.TEAMID = ca.TEAMID AND cnc.COMPANYID = ca.COMPANYID " + //
						"    LEFT JOIN tcustomer c ON cnc.TEAMID = c.TEAMID AND cnc.BILLING_CONTACT_ID = c.ID " + //
						"where ca.DEFAULT_ADDRESS = 1";
		param.add(clientID);
		return sql;
	}
	
	public static String workerReferences(Long clientID, Vector<Object> param) {
		String sql = //
				"SELECT 16625 \"Employer Ref\", s.ADP_FILE_NO \"Personnel Ref\", '' \"Employment Sequence No\", c.LASTNAME \"Surname\", " + //
						"c.FIRSTNAME \"First forename\", '' \"Second forename\", '' \"Third forename\", '' \"Title\", '' \"Known as\", c.ADDRESS1 \"Address line 1\", " + //
						"c.ADDRESS2 \"Address line 2\", c.CITY \"Address line 3\", c.COUNTRYID \"Address line 4\", '' \"Address line 5\", c.ZIPCODE \"Post code\", " + //
						"c.COUNTRYID \"Country\", '' \"Telephone number\", " + //
						"(SELECT USERFIELD_VALUE FROM THR_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.CANDIDATEID = cc.CANDIDATEID AND u.USERFIELD_ID = 52) \"Gender\", " + //
						"(SELECT USERFIELD_VALUE FROM THR_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.CANDIDATEID = cc.CANDIDATEID AND u.USERFIELD_ID = 6) \"Date of birth\", " + //
						"'' \"Marital status\", '' \"Previous surname\", '' \"Nationality\", '' \"Citizenship\", '' \"Passport held\", '' \"Next of kin\", '' \"Next of kin relation\", " + //
						"'' \"Next of kin addr 1\", '' \"Next of kin addr 2\", '' \"Next of kin addr 3\", '' \"Next of kin addr 4\", '' \"Next of kin addr 5\", " + //
						"'' \"Next of kin postcode\", '' \"Next of kin country\", '' \"Next of kin telephne\", '' \"Emergency contact\", '' \"Emergency cont tel\", " + //
						"'' \"Last address line 1\", '' \"Last address line 2\", '' \"Last address line 3\", '' \"Last address line 4\", '' \"Last address line 5\", " + //
						"'' \"Last post code\", '' \"Last country\", '' \"Date of address change\", '' \"Start date\", '' \"Leaving Date\", '' \"Leaving Code\", '' \"User index\", " + //
						"'' \"Personnel notes\", '' \"Work Telephone\", '' \"Work Fax\", '' \"Mobile Telephone\", c.EMAIL \"Email Address\", " + //
						"(SELECT USERFIELD_VALUE FROM TCANDIDATE_USERFIELDS u WHERE u.TEAMID = c.TEAMID AND u.CANDIDATEID = c.ID AND u.USERFIELD_ID = 7) \"NI number\", " + //
						"'' \"NI table letter\", '' \"Contracted out\", '' \"PAYE code\", '' \"PAYE method\", " + //
						"(SELECT USERFIELD_VALUE FROM TCUSTOMER_USERFIELDS u WHERE u.TEAMID = c.TEAMID AND u.CUSTOMERID = c.ID AND u.USERFIELD_ID = 11) \"Pay method\", " + //
						"(SELECT USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = c.TEAMID AND u.COMPANYID = s.SUBCONTRACT_COMPANYID AND u.USERFIELD_ID = 31) \"Bank1 Sort Code\", " + //
						"(SELECT USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = c.TEAMID AND u.COMPANYID = s.SUBCONTRACT_COMPANYID AND u.USERFIELD_ID = 32) \"Bank1 Account Number\", " + //
						"(CASE WHEN s.STATUS = 2 THEN (SELECT NAME FROM TCUSTOMERCOMPANY tcc WHERE tcc.TEAMID = s.RECRUITER_TEAMID " + //
						"AND tcc.ID = s.SUBCONTRACT_COMPANYID) ELSE CAST('' AS NVARCHAR2(100)) END) \"Bank1 account name\", '' \"Bank1 account type\", " + //
						"'' \"Bank1 Ref\", '' \"Build society1 name\", '' \"Build society1 num\", '' \"Secondary Pay method\", '' \"Bank2 sort code\", " + //
						"'' \"Bank2 account number\", '' \"Bank2 account name\", '' \"Bank2 account type\", '' \"Bank2 Ref\", '' \"Build society2 name\", " + //
						"'' \"Build society2 num\", '' \"Payroll notes\", '' \"Student\", '' \"Analysis Code\", '' \"Tax Regime\", '16625' \"Division\", " + //
						"'' \"Department\", '' \"Job Category\", '' \"WTD Waiver Signed\", 'C' \"Employment Type\", '' \"Business Name\", '' \"Partner Name 1\", " + //
						"'' \"Partner DOB 1\", '' \"Partner NI 1\" " + //
						"FROM TCANDIDATE_CATEGORY cc JOIN TCANDIDATE c ON c.TEAMID = cc.TEAMID AND c.ID = cc.CANDIDATEID " + //
						"LEFT JOIN TCANDIDATE_HR h ON c.TEAMID = h.TEAMID AND c.ID = h.CANDIDATEID " + //
						"LEFT JOIN TEMPLOYEE_SALARYRECORD s ON c.TEAMID = s.RECRUITER_TEAMID AND c.ID = s.EMPLOYEEID " + //
						"WHERE cc.TEAMID = ? AND cc.CATID = 1 AND cc.DCATID = 1 " + //
						"AND s.APPROVED = 1 AND NVL(s.CLOSED,0)=0 " + //
						"AND NOT EXISTS (SELECT 1 FROM TEMPLOYEE_SALARYRECORD s1 WHERE s1.employeeid=s.employeeid and s1.recruiter_teamid=s.recruiter_teamid " + //
						"AND s1.APPROVED = 1 AND NVL(s1.CLOSED,0)=0 and s1.RECID > s.RECID)"; //
		param.add(clientID); //
		return sql;
	}
	
	public static String suppliers(Long clientID, Vector<Object> param) {
		String sql = //
				"WITH COMPANY_CTE AS ( " + //
						"    SELECT DISTINCT c.COMPANYID, e.RECRUITER_TEAMID TEAMID " + //
						"    FROM TEMPLOYEE_INVOICE e JOIN TCUSTOMER c ON e.RECRUITER_TEAMID = c.TEAMID AND e.BILLING_CONTACT = c.ID " + //
						"    WHERE e.RECRUITER_TEAMID = ? " + //
						") " + //
						"SELECT 16625 \"Employer Ref\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 26) \"Supplier Ref\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 53) \"Legal Status\", " + //
						"'C' \"Supplier Type\", cc.NAME \"Business Name\", ca.ADDRESS1 \"Address Line 1\", ca.ADDRESS2 \"Address Line 2\", " + //
						"(CASE WHEN ca.CITY LIKE '%,%' THEN (SUBSTR(ca.CITY, 0, INSTR(',', ca.CITY))) ELSE ca.CITY END) \"Address Line 3\", " + //
						"(CASE WHEN ca.CITY LIKE '%,%' THEN (SUBSTR(ca.CITY, INSTR(',', ca.CITY)+1, LENGTH(ca.CITY))) ELSE NULL END) \"Address Line 4\", " + //
						"'' \"Address Line 5\", ca.ZIPCODE \"Post Code\", '' \"Country\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 23) \"Incorporation Date\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 22) \"Company Reg No\", " + //
						"'' \"Schedule D Number-NOT USED\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 20) \"VAT Number\", " + //
						"'' \"Default VAT Rate\", '' \"Division\", '' \"Department\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 54) \"Remittance Address\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 53) \"Purchase Ledger Account Number\", " + //
						"'' \"Payment Option\", '' \"Payment Terms Value\", 'B' \"Payment Method\", '' \"Currency Code\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 31) \"Bank Sort Code\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 32) \"Bank Account Number\", " + //
						"cc.NAME \"Bank Account Name\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 49) \"CIS Unique Tax Reference\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 55) \"CIS Legal Status\", " + //
						"'' \"CIS Partnership Name\", '' \"CIS Partnership UTR-NOT USED\", '' \"Self Billing Enabled\", '' \"Self Billing Logo Filename\", " + //
						"'' \"Self Billing Invoice Break Level\", '' \"Partner Name 1\", '' \"Partner DOB 1\", '' \"Partner NI 1\", '' \"Partner Name 2\", " + //
						"'' \"Partner DOB 2\", '' \"Partner NI 2\", '' \"Partner Name 3\", '' \"Partner DOB 3\", '' \"Partner NI 3\", '' \"Supplier Notes\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 35) \"IBAN\", " + //
						"(SELECT u.USERFIELD_VALUE FROM TCOMPANY_USERFIELDS u WHERE u.TEAMID = cc.TEAMID AND u.COMPANYID = cc.ID AND u.USERFIELD_ID = 36) \"BIC Code\" " + //
						"FROM COMPANY_CTE t JOIN TCUSTOMERCOMPANY cc ON t.COMPANYID = cc.ID AND t.TEAMID = cc.TEAMID " + //
						"JOIN TCUSTOMERCOMPANYADDRESSES ca ON cc.TEAMID = ca.TEAMID AND t.COMPANYID = ca.COMPANYID AND ca.ADDRESSID = 1";
		param.add(clientID);
		return sql;
	}
	
	public static String cis(Long clientID, Vector<Object> param) {
		String sql = //
				"SELECT (CASE WHEN s.ADP_FILE_NO IS NULL " + //
						"AND (SELECT USERFIELD_VALUE FROM TCANDIDATE_USERFIELDS u WHERE u.TEAMID = c.TEAMID AND u.CANDIDATEID = c.ID AND u.USERFIELD_ID = 15) IS NULL " + //
						"AND (SELECT USERFIELD_VALUE FROM TCANDIDATE_USERFIELDS u WHERE u.TEAMID = c.TEAMID AND u.CANDIDATEID = c.ID AND u.USERFIELD_ID = 16) IS NULL " + //
						"AND (SELECT USERFIELD_VALUE FROM TCANDIDATE_USERFIELDS u WHERE u.TEAMID = c.TEAMID AND u.CANDIDATEID = c.ID AND u.USERFIELD_ID = 14) IS NULL " + //
						"THEN CAST(c.FIRSTNAME || ' ' || c.LASTNAME as varchar(100)) || ' does not have CIS details entered.' ELSE CAST(s.ADP_FILE_NO AS VARCHAR(20)) END) \"Tempest Ref\", " + //
						"(SELECT USERFIELD_VALUE FROM TCANDIDATE_USERFIELDS u WHERE u.TEAMID = c.TEAMID AND u.CANDIDATEID = c.ID AND u.USERFIELD_ID = 15) \"CIS Verification Number\", " + //
						"(SELECT USERFIELD_VALUE FROM TCANDIDATE_USERFIELDS u WHERE u.TEAMID = c.TEAMID AND u.CANDIDATEID = c.ID AND u.USERFIELD_ID = 16) \"CIS verification date\", " + //
						"DECODE((SELECT USERFIELD_VALUE FROM TCANDIDATE_USERFIELDS u WHERE u.TEAMID = c.TEAMID AND u.CANDIDATEID = c.ID AND u.USERFIELD_ID = 14), " + //
						"'Gross Payments 0%','T','Nett Deductions 20%','F','Nett Deductions 30%','F','') \"Nett Deduction (F) and Gross Payments (T)\", " + //
						"DECODE((SELECT USERFIELD_VALUE FROM TCANDIDATE_USERFIELDS u WHERE u.TEAMID = c.TEAMID AND u.CANDIDATEID = c.ID AND u.USERFIELD_ID = 14), " + //
						"'Gross Payments 0%','0','Nett Deductions 20%','20','Nett Deductions 30%','30','') \"CIS type\" " + //
						"FROM TCANDIDATE_CATEGORY cc JOIN TCANDIDATE c ON c.TEAMID = cc.TEAMID AND c.ID = cc.CANDIDATEID " + //
						"LEFT JOIN TCANDIDATE_HR h ON c.TEAMID = h.TEAMID AND c.ID = h.CANDIDATEID " + //
						"LEFT JOIN TEMPLOYEE_SALARYRECORD s ON c.TEAMID = s.RECRUITER_TEAMID AND c.ID = s.EMPLOYEEID " + //
						"WHERE cc.TEAMID = ? AND cc.CATID = 1 AND cc.DCATID = 1 " + //
						"AND s.APPROVED = 1 AND NVL(s.CLOSED,0)=0 " + //
						"AND NOT EXISTS (SELECT 1 FROM TEMPLOYEE_SALARYRECORD s1 WHERE s1.employeeid=s.employeeid and s1.recruiter_teamid=s.recruiter_teamid " + //
						"AND s1.APPROVED = 1 AND NVL(s1.CLOSED,0)=0 and s1.RECID > s.RECID)"; //
		param.add(clientID); //
		return sql;
	}
}

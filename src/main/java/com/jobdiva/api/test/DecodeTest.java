package com.jobdiva.api.test;

import java.io.File;

import org.springframework.web.util.UriUtils;

/**
 * @author Joseph Chidiac
 *
 */
public class DecodeTest {
	
	public static void main(String[] args) {
		String url = "https://api.jobdiva.com/api/jobdiva/createJob?title=API%20Test%20Set%20Contact%20011821%201040am&description=test&companyid=8553103&jobtype=Full%20Time%2FContract&billrateunit=hour&payrateunit=hour&contacts=%7B%0A%20%20%22action%22%3A%201%2C%0A%20%20%22contactId%22%3A%2033661373%2C%0A%20%20%22roleId%22%3A%201%2C%0A%20%20%22showOnJob%22%3A%20true%0A%7D&users=%7B%0A%20%20%22action%22%3A%201%2C%0A%20%20%22role%22%3A%20%22Primary%20Sales%22%2C%0A%20%20%22userId%22%3A%202423739%0A%7D&Userfields=%7B%0A%20%20%22userfieldId%22%3A%201%2C%0A%20%20%22userfieldValue%22%3A%20%22Proactive%22%0A%7D";
		url = "https://appcenter.intuit.com/connect/oauth2?client_id=ABfyUY7fvG1D3vEc6d0GJZESRHzZ5N2PxLAeAmLwd2vy88gt68&response_type=code&scope=openid+profile+email+phone+address+com.intuit.quickbooks.accounting+com.intuit.quickbooks.payment&redirect_uri=https%3A%2F%2Fws.jobdiva.com%2Fconnect%2Foauth2redirect.jsp&state=7727e01a-5080-49d1-8235-8bb81d78f0c4";
		url = "http%3A%2F%2Flocalhost%3A3000%2Fimplicit%2Fcallback";
		url = "http://localhost:88/api/jobdiva/createCandidate?firstName=Inanna&lastName=Chidiac&state=Gauteng&countryid=South%20Africa&titleskillcertifications=%7B%0A%20%20%22endDate%22%3A%20%222020-05-05T07%3A41%3A50.261Z%22%2C%0A%20%20%22startDate%22%3A%20%222021-05-05T07%3A41%3A50.261Z%22%2C%0A%20%20%22titleskillcertification%22%3A%20%22TEST%20SKILL%20CERTIFICATION%22%2C%0A%20%20%22years%22%3A%202021%0A%7D";
		url = "http://10.50.129.4:88/api/jobdiva/updateBillingRecord?assignmentID=504832&candidateID=916372633608&Userfields=%7B%0A%20%20%22userfieldId%22%3A%202035%2C%0A%20%20%22userfieldValue%22%3A%20%22this%20was%20done%20for%20testing%20purposes%20on%2005%2F05%2F2021%22%0A%7D";
		//
 		url = "http://10.50.129.4:88/api/jobdiva/updateBillingRecord?assignmentID=504832&candidateID=916372633608&Userfields=%7B%0A%20%20%22userfieldId%22%3A%202093%2C%0A%20%20%22userfieldValue%22%3A%20%22api%20test%20bob%22%0A%7D";

 		url = "https://api.jobdiva.com/api/jobdiva/updateCandidateUserfields?candidateid=8881381290927&overwrite=true&Userfields=%7B%0A%20%20%22userfieldId%22%3A%201163%2C%0A%20%20%22userfieldValue%22%3A%20%22testAPI%22%0A%7D" ;
 		
 		
 		url = "http://10.50.129.4:88/api/jobdiva/updateJob?jobid=330553&users=%7B%0A%20%20%22action%22%3A%201%2C%0A%20%20%22role%22%3A%20%22Sales%22%2C%0A%20%20%22userId%22%3A%20125009%0A%7D"; 
 		
 		url = "{ \"activityId\": 504371, \"approved\": true, \"approverId\": 125009, \"employeeId\": 916352170460, \"externalId\": \"\", \"jobId\": 332482, \"timesheet\": [ { \"date\": \"2021-05-03T11:28:41.404Z\", \"hours\": 8 }, { \"date\": \"2021-05-04T11:28:41.404Z\", \"hours\": 8 }, { \"date\": \"2021-05-05T11:28:41.404Z\", \"hours\": 8 }, { \"date\": \"2021-05-06T11:28:41.404Z\", \"hours\": 8 }, { \"date\": \"2021-05-07T11:28:41.404Z\", \"hours\": 8 }, { \"date\": \"2021-05-08T11:28:41.404Z\", \"hours\": 8 }, { \"date\": \"2021-05-09T11:28:41.404Z\", \"hours\": 8 } ], \"vmsEmployeeId\": \"$@ 1 yr22tyy uy3ye4ry78yuyr H iui\", \"weekendingdate\": \"2021-05-09T11:28:41.404Z\"}" ;
 		
 		url = UriUtils.decode(url, "UTF-8");
		System.out.println(url);
	}
	
	public static void ___main(String[] args) {
		File file = new File("C:\\JobDiva\\DB - oracle-maria-postgresql\\Nathalie");
		for (File file2 : file.listFiles()) {
			System.out.println(file2.getName());
		}
	}
}

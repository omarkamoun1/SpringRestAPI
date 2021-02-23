package com.jobdiva.api.test;

import java.io.File;

import org.springframework.web.util.UriUtils;

/**
 * @author Joseph Chidiac
 *
 */
public class DecodeTest {
	
	public static void _main(String[] args) {
		String url = "https://api.jobdiva.com/api/jobdiva/createJob?title=API%20Test%20Set%20Contact%20011821%201040am&description=test&companyid=8553103&jobtype=Full%20Time%2FContract&billrateunit=hour&payrateunit=hour&contacts=%7B%0A%20%20%22action%22%3A%201%2C%0A%20%20%22contactId%22%3A%2033661373%2C%0A%20%20%22roleId%22%3A%201%2C%0A%20%20%22showOnJob%22%3A%20true%0A%7D&users=%7B%0A%20%20%22action%22%3A%201%2C%0A%20%20%22role%22%3A%20%22Primary%20Sales%22%2C%0A%20%20%22userId%22%3A%202423739%0A%7D&Userfields=%7B%0A%20%20%22userfieldId%22%3A%201%2C%0A%20%20%22userfieldValue%22%3A%20%22Proactive%22%0A%7D";
		url = UriUtils.decode(url, "UTF-8");
		System.out.println(url);
	}
	
	public static void main(String[] args) {
		File file = new File("C:\\JobDiva\\DB - oracle-maria-postgresql\\Nathalie") ; 
		for(File file2 : file.listFiles()) { 
			System.out.println(file2.getName());
		}
	}
}

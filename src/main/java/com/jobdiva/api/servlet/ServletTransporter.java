package com.jobdiva.api.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import com.axelon.oc4j.ServletRequestData;

public class ServletTransporter {
	//
	
	public static Object callServlet(String url, Object obj) throws Exception {
		//
		try {
			URL u = new URL(url);
			URLConnection urlConn = u.openConnection();
			urlConn.setUseCaches(false);
			urlConn.setDoOutput(true);
			urlConn.setDoInput(true);
			urlConn.setRequestProperty("Content-type", "application/x-java-serialized-object");
			//
			ObjectOutputStream objout = new ObjectOutputStream(urlConn.getOutputStream());
			try {
				objout.writeObject(obj);
				objout.flush();
			} finally {
				if (objout != null)
					objout.close();
			}
			//
			//
			ObjectInputStream input = new ObjectInputStream(urlConn.getInputStream());
			try {
				Object retobj = input.readObject();
				return retobj;
			} finally {
				if (input != null)
					input.close();
			}
		} catch (Exception e) {
			System.out.println("Getting Data From Servlet ==> Error : " + e.toString());
			throw new Exception("Servlet : " + url + " Error : " + e.toString());
		}
	}
	//
	
	public static void _main(String[] args) throws Exception {
		//
		String url = "http://10.10.139.22:8000/BIData/servlet/GetBIDataServlet";
		Vector<Object> reqData = new Vector<Object>();
		reqData.add(1);
		reqData.add("api.user@jobdiva.com");
		reqData.add("password1");
		reqData.add("New/Updated Candidate Note Records");
		reqData.add("2017-01-03");
		reqData.add("2017-01-04");
		reqData.add("");
		//
		ServletRequestData servletRequestData = new ServletRequestData(1, reqData);
		//
		System.out.println(callServlet(url, servletRequestData));
		//
	}
	
	public static void main(String[] args) throws Exception {
		//
		String url = "http://10.10.139.22:8000/BIData/servlet/GetBIDataServlet";
		Vector<Object> reqData = new Vector<Object>();
		reqData.add("1");
		reqData.add("api.user@jobdiva.com");
		reqData.add("password1");
		reqData.add("New/Updated Candidate Note Records");
		reqData.add("01/04/2020 00:00:00");
		reqData.add("01/04/2020 23:59:59");
		reqData.add(null);
		com.axelon.oc4j.ServletRequestData srd = new com.axelon.oc4j.ServletRequestData(0L, reqData);
		System.out.println(callServlet(url, srd));
	}
}

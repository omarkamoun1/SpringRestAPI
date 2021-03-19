package com.jobdiva.api.dao.event;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joseph Chidiac
 *
 */
public class NotificationThread implements Runnable {
	
	private Thread				thread;
	private String				threadName;
	private int					whichApp;
	private int					notificationOptionInt;
	private String				notificationOptionStr;
	private boolean				isDebug;
	private long				teamid;
	private ArrayList<Long>		recruiteridArray;
	private String				message;
	private int					badgenumber;
	private Map<String, String>	additionalValues;
	private ArrayList<String>	emailArray;
	private String				ntype;
	private String				ncontent;
	private long				relatedid;
	private String				relatedString;
	private String				loadBalanceServletLocation;
	private boolean				isUSProduction;
	
	NotificationThread(boolean isUSProduction, String loadBalanceServletLocation, String threadName, int whichApp, int notificationOptionInt, String notificationOptionStr, boolean isDebug, long teamid, ArrayList<Long> recruiteridArray,
			String message, int badgenumber, Map<String, String> additionalValues, ArrayList<String> emailArray, String ntype, String ncontent, long relatedid, String relatedString) {
		this.isUSProduction = isUSProduction;
		this.loadBalanceServletLocation = loadBalanceServletLocation;
		this.threadName = threadName;
		this.whichApp = whichApp;
		this.isDebug = isDebug;
		this.notificationOptionInt = notificationOptionInt;
		this.notificationOptionStr = notificationOptionStr;
		this.teamid = teamid;
		this.recruiteridArray = recruiteridArray;
		this.message = message;
		this.badgenumber = badgenumber;
		this.additionalValues = additionalValues;
		this.emailArray = emailArray;
		this.ntype = ntype;
		this.ncontent = ncontent;
		this.relatedid = relatedid;
		this.relatedString = relatedString;
	}
	
	@SuppressWarnings("unused")
	@Override
	public void run() {
		try {
			System.out.println("EventServlet---------------SendPushServlet.java");
			//
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("whichApp", whichApp);
			hm.put("isDebug", isDebug);
			hm.put("notificationOptionInt", notificationOptionInt);
			hm.put("notificationOptionStr", notificationOptionStr);
			hm.put("teamid", teamid);
			hm.put("recruiteridArray", recruiteridArray);
			hm.put("message", message);
			hm.put("badgenumber", badgenumber);
			hm.put("additionalValues", additionalValues);
			hm.put("emailArray", emailArray);
			hm.put("ntype", ntype);
			hm.put("ncontent", ncontent);
			hm.put("relatedid", relatedid);
			hm.put("relatedString", relatedString);
			//
			if (isUSProduction) {
				Object retObj = sendData(hm, "appws/servlet/SendPushServlet", "http://10.10.146.190:8000/");
			} else {
				Object retObj = sendData(hm, "appws/servlet/SendPushServlet", loadBalanceServletLocation);
			}
			// Object retObj=sendData(hm, "appws/servlet/SendPushServlet",
			// Application.isIntegration()?LOADBALANCERSERVLETLOCATION:"http://10.10.146.190:8000/");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	public void start() {
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
	
	public static Object sendData(Object reqData, String servletname, String location) {
		Object rspData = null;
		try {
			String url = location + servletname;
			System.out.println("CandidateDBUtil: calling servlet on " + url);
			rspData = (Object) doTransport(url, reqData);
			System.out.println("CandidateDBUtil: servlet " + servletname + " returned");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rspData;
	}
	
	public static Object doTransport(String url, Object obj) throws Exception {
		URL u = new URL(url);
		URLConnection urlConn = u.openConnection();
		urlConn.setUseCaches(false);
		urlConn.setDoOutput(true);
		urlConn.setDoInput(true);
		urlConn.setRequestProperty("Content-type", "application/x-java-serialized-object");
		ObjectOutputStream objout = new ObjectOutputStream(urlConn.getOutputStream());
		objout.writeObject(obj);
		objout.flush();
		objout.close();
		ObjectInputStream input = new ObjectInputStream(urlConn.getInputStream());
		Object retobj = input.readObject();
		input.close();
		return retobj;
	}
}
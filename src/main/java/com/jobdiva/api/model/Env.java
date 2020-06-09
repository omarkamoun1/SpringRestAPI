package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class Env implements java.io.Serializable {
	
	private Short	id;
	//
	private String	connectString;
	//
	private String	connectThin;
	//
	private String	connectUser;
	//
	private String	connectPassword;
	//
	private String	mainServlet;
	//
	private String	waferServlet;
	//
	private String	waferBackupServlet;
	//
	private String	attributeServlet;
	//
	private String	environmentType;
	//
	private String	apacheLocation;
	
	public Short getId() {
		return id;
	}
	
	public void setId(Short id) {
		this.id = id;
	}
	
	public String getConnectString() {
		return connectString;
	}
	
	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}
	
	public String getConnectThin() {
		return connectThin;
	}
	
	public void setConnectThin(String connectThin) {
		this.connectThin = connectThin;
	}
	
	public String getConnectUser() {
		return connectUser;
	}
	
	public void setConnectUser(String connectUser) {
		this.connectUser = connectUser;
	}
	
	public String getConnectPassword() {
		return connectPassword;
	}
	
	public void setConnectPassword(String connectPassword) {
		this.connectPassword = connectPassword;
	}
	
	public String getMainServlet() {
		return mainServlet;
	}
	
	public void setMainServlet(String mainServlet) {
		this.mainServlet = mainServlet;
	}
	
	public String getWaferServlet() {
		return waferServlet;
	}
	
	public void setWaferServlet(String waferServlet) {
		this.waferServlet = waferServlet;
	}
	
	public String getWaferBackupServlet() {
		return waferBackupServlet;
	}
	
	public void setWaferBackupServlet(String waferBackupServlet) {
		this.waferBackupServlet = waferBackupServlet;
	}
	
	public String getAttributeServlet() {
		return attributeServlet;
	}
	
	public void setAttributeServlet(String attributeServlet) {
		this.attributeServlet = attributeServlet;
	}
	
	public String getEnvironmentType() {
		return environmentType;
	}
	
	public void setEnvironmentType(String environmentType) {
		this.environmentType = environmentType;
	}
	
	public String getApacheLocation() {
		return apacheLocation;
	}
	
	public void setApacheLocation(String apacheLocation) {
		this.apacheLocation = apacheLocation;
	}
}
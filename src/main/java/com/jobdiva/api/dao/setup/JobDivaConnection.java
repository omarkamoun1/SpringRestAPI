package com.jobdiva.api.dao.setup;

import java.util.List;

public class JobDivaConnection {
	
	private Integer		id;
	private String		connectionString;
	private String		connectionThin;
	private String		userName;
	private String		pasword;
	private String		mainServlet;
	private String		waferServlet;
	private String		waferBackupServlet;
	private String		attributeServlet;
	private String		environmentType;
	private String		apacheLocation;
	private String		universalServlet;
	private String		flexStaffingServlet;
	private List<Long>	teamIds;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getConnectionString() {
		return connectionString;
	}
	
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	
	public String getConnectionThin() {
		return connectionThin;
	}
	
	public void setConnectionThin(String connectionThin) {
		this.connectionThin = connectionThin;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPasword() {
		return pasword;
	}
	
	public void setPasword(String pasword) {
		this.pasword = pasword;
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
	
	public String getUniversalServlet() {
		return universalServlet;
	}
	
	public void setUniversalServlet(String universalServlet) {
		this.universalServlet = universalServlet;
	}
	
	public String getFlexStaffingServlet() {
		return flexStaffingServlet;
	}
	
	public void setFlexStaffingServlet(String flexStaffingServlet) {
		this.flexStaffingServlet = flexStaffingServlet;
	}
	
	public List<Long> getTeamIds() {
		return teamIds;
	}
	
	public void setTeamIds(List<Long> teamIds) {
		this.teamIds = teamIds;
	}
}

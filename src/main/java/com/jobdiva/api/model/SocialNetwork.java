package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class SocialNetwork implements java.io.Serializable {
	
	private Integer	snid;
	private String	snurl;
	private String	snname;
	
	public Integer getSnid() {
		return snid;
	}
	
	public void setSnid(Integer snid) {
		this.snid = snid;
	}
	
	public String getSnurl() {
		return snurl;
	}
	
	public void setSnurl(String snurl) {
		this.snurl = snurl;
	}
	
	public String getSnname() {
		return snname;
	}
	
	public void setSnname(String snname) {
		this.snname = snname;
	}
}

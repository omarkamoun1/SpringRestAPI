package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class Team implements java.io.Serializable {
	
	private long	id;
	private String	company;
	private Byte	notePrivacy;
	private Boolean	candinfoChange;
	private Short	dailyDeleteTotal;
	private Boolean	userCandidatePortal;
	private Boolean	useDivaFinancials;
	private Boolean	useExchange;
	private Boolean	useOwnReport;
	private Boolean	useSSL;
	private String	regionCode;
	private String	strTimeZone;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCompany() {
		return this.company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public Byte getNotePrivacy() {
		return this.notePrivacy;
	}
	
	public void setNotePrivacy(Byte notePrivacy) {
		this.notePrivacy = notePrivacy;
	}
	
	public Boolean getCandinfoChange() {
		return this.candinfoChange;
	}
	
	public void setCandinfoChange(Boolean candinfoChange) {
		this.candinfoChange = candinfoChange;
	}
	
	public Short getDailyDeleteTotal() {
		return this.dailyDeleteTotal;
	}
	
	public void setDailyDeleteTotal(Short dailyDeleteTotal) {
		this.dailyDeleteTotal = dailyDeleteTotal;
	}
	
	public Boolean getUserCandidatePortal() {
		return this.userCandidatePortal;
	}
	
	public void setUserCandidatePortal(Boolean userCandidatePortal) {
		this.userCandidatePortal = userCandidatePortal;
	}
	
	public Boolean getUseDivaFinancials() {
		return this.useDivaFinancials;
	}
	
	public void setUseDivaFinancials(Boolean useDivaFinancials) {
		this.useDivaFinancials = useDivaFinancials;
	}
	
	public Boolean getUseExchange() {
		return this.useExchange;
	}
	
	public void setUseExchange(Boolean useExchange) {
		this.useExchange = useExchange;
	}
	
	public Boolean getUseOwnReport() {
		return this.useOwnReport;
	}
	
	public void setUseOwnReport(Boolean useOwnReport) {
		this.useOwnReport = useOwnReport;
	}
	
	public Boolean getUseSSL() {
		return this.useSSL;
	}
	
	public void setUseSSL(Boolean useSSL) {
		this.useSSL = useSSL;
	}
	
	public String getRegionCode() {
		return this.regionCode;
	}
	
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	public String getStrTimeZone() {
		return this.strTimeZone;
	}
	
	public void setStrTimeZone(String strTimeZone) {
		this.strTimeZone = strTimeZone;
	}
}

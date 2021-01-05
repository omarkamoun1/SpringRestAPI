package com.jobdiva.api.model.chatbot;

@SuppressWarnings("serial")
public class ChatbotUserData implements java.io.Serializable {
	
	private boolean	isTeamLeader;
	private String	companyName;
	private String	apacheLocation;
	private String	envType;
	private Integer	numberOfNonDownloadingMachines	= 0;
	private Integer	machineDownloadingResumes		= 0;
	private boolean	allowManagingJobBoardsCriteriaAndProfiles;
	private boolean	allowManagingJobBoardsCriteriaOnly;
	private boolean	displayTheFourDailyEmailProfileOption;
	private String	firstname;
	private String	lastname;
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public boolean isTeamLeader() {
		return isTeamLeader;
	}
	
	public void setTeamLeader(boolean isTeamLeader) {
		this.isTeamLeader = isTeamLeader;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getApacheLocation() {
		return apacheLocation;
	}
	
	public void setApacheLocation(String apacheLocation) {
		this.apacheLocation = apacheLocation;
	}
	
	public String getEnvType() {
		return envType;
	}
	
	public void setEnvType(String envType) {
		this.envType = envType;
	}
	
	public Integer getNumberOfNonDownloadingMachines() {
		return numberOfNonDownloadingMachines;
	}
	
	public void setNumberOfNonDownloadingMachines(Integer numberOfNonDownloadingMachines) {
		this.numberOfNonDownloadingMachines = numberOfNonDownloadingMachines;
	}
	
	public Integer getMachineDownloadingResumes() {
		return machineDownloadingResumes;
	}
	
	public void setMachineDownloadingResumes(Integer machineDownloadingResumes) {
		this.machineDownloadingResumes = machineDownloadingResumes;
	}
	
	public boolean isAllowManagingJobBoardsCriteriaAndProfiles() {
		return allowManagingJobBoardsCriteriaAndProfiles;
	}
	
	public void setAllowManagingJobBoardsCriteriaAndProfiles(boolean allowManagingJobBoardsCriteriaAndProfiles) {
		this.allowManagingJobBoardsCriteriaAndProfiles = allowManagingJobBoardsCriteriaAndProfiles;
	}
	
	public boolean isAllowManagingJobBoardsCriteriaOnly() {
		return allowManagingJobBoardsCriteriaOnly;
	}
	
	public void setAllowManagingJobBoardsCriteriaOnly(boolean allowManagingJobBoardsCriteriaOnly) {
		this.allowManagingJobBoardsCriteriaOnly = allowManagingJobBoardsCriteriaOnly;
	}
	
	public boolean isDisplayTheFourDailyEmailProfileOption() {
		return displayTheFourDailyEmailProfileOption;
	}
	
	public void setDisplayTheFourDailyEmailProfileOption(boolean displayTheFourDailyEmailProfileOption) {
		this.displayTheFourDailyEmailProfileOption = displayTheFourDailyEmailProfileOption;
	}
}

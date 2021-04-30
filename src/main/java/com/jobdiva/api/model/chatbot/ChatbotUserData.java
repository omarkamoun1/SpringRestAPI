package com.jobdiva.api.model.chatbot;

@SuppressWarnings("serial")
public class ChatbotUserData implements java.io.Serializable {
	
	public String	firstname;
	public String	lastname;
	public boolean	isTeamLeader;
	public String	companyName;
	public String	apacheLocation;
	public String	envType;
	public Integer	numberOfNonDownloadingMachines	= 0;
	public Integer	machineDownloadingResumes		= 0;
	public boolean	allowManagingJobBoardsCriteriaAndProfiles;
	public boolean	allowManagingJobBoardsCriteriaOnly;
	public boolean	displayTheFourDailyEmailProfileOption;
	public boolean allowAssignOnboardingToNotLinkedJob;//42
	public boolean allowManagingOnboarding;//13
	public boolean allowAccessCompletedOnboarding;//6
	public boolean allowAccessCompletedOnboardingForHires;//7
	public boolean allowAccessCompletedOnboardingForPrimaryJobs;//8
	public boolean allowAccessCompletedOnboardingForMyJobs;//9
	public boolean allowAccessCompletedOnboardingForAllJobs;//10
	public boolean allowAccessCompletedOnboardingForDivision;//11
	public boolean allowUnassignIndividualDocuments;//120
	public boolean allowCreateSuppliersLogins; //64
	public boolean allowViewAccessLogReport; 
	public boolean allowOnboardCandidatesFor;//44
	public boolean allowAssignSuppliersToJob;//112
	
	public boolean hasUnusedHirePackage;
	public String unusedHirePackagesName;
	public boolean isSuperUser;
	public boolean reportPermissionUserJournalOfCandiateNotes;
	public boolean reportPermissionUserJournalOfContactNotes;
	public boolean reportPermissionUserJournalOfCompanyNotes;
	public boolean reportPermissionCompanyList;
	public boolean reportPermissionMyCompanyList;
	public boolean reportPermissionSubmittalMetrics;
	public boolean reportPermissionPhasedSubmittalMetrics;
	public boolean reportPermissionIncomingResumesReceivedDuringPeriod;
	public boolean reportPermissionHiresReport;
	public boolean reportPermissionEmployeeReport;
	public boolean reportPermissionJobActivitySummaryAndDetails;


	
}

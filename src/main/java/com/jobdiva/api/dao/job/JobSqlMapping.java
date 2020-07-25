package com.jobdiva.api.dao.job;

import java.sql.Types;
import java.util.LinkedHashMap;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

import com.jobdiva.api.model.Job;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.sql.JobDivaSqlLobValue;

public class JobSqlMapping {
	
	public static void mapJob(JobDivaSession jobDivaSession, Job job, LinkedHashMap<String, String> fields, MapSqlParameterSource parameterSource, Boolean insertMode) {
		//
		// <column name="ID" precision="10" scale="0" />
		// fields.put("", "");
		// parameterSource.addValue("", job.get );
		if (job.getRecruiterId() != null) {
			fields.put("RECRUITERID", "RECRUITERID");
			parameterSource.addValue("RECRUITERID", job.getRecruiterId());
		}
		//
		if (job.getDepartment() != null) {
			fields.put("DEPARTMENT", "DEPARTMENT");
			parameterSource.addValue("DEPARTMENT", job.getDepartment());
		}
		//
		if (job.getAddress1() != null) {
			fields.put("ADDRESS1", "ADDRESS1");
			parameterSource.addValue("ADDRESS1", job.getAddress1());
		}
		//
		if (job.getAddress2() != null) {
			fields.put("ADDRESS2", "ADDRESS2");
			parameterSource.addValue("ADDRESS2", job.getAddress2());
		}
		//
		if (job.getCity() != null) {
			fields.put("CITY", "CITY");
			parameterSource.addValue("CITY", job.getCity());
		}
		//
		if (job.getState() != null) {
			fields.put("STATE", "STATE");
			parameterSource.addValue("STATE", job.getState());
		}
		//
		if (job.getZipcode() != null) {
			fields.put("ZIPCODE", "ZIPCODE");
			parameterSource.addValue("ZIPCODE", job.getZipcode());
		}
		//
		if (job.getProvince() != null) {
			fields.put("PROVINCE", "PROVINCE");
			parameterSource.addValue("PROVINCE", job.getProvince());
		}
		//
		if (job.getDateIssued() != null) {
			fields.put("DATEISSUED", "DATEISSUED");
			parameterSource.addValue("DATEISSUED", job.getDateIssued());
		}
		//
		if (job.getDeadLine() != null) {
			fields.put("DEADLINE", "DEADLINE");
			parameterSource.addValue("DEADLINE", job.getDeadLine());
		}
		//
		if (job.getStartDate() != null) {
			fields.put("STARTDATE", "STARTDATE");
			parameterSource.addValue("STARTDATE", job.getStartDate());
		}
		//
		if (job.getEndDate() != null) {
			fields.put("ENDDATE", "ENDDATE");
			parameterSource.addValue("ENDDATE", job.getEndDate());
		}
		//
		if (job.getPositions() != null) {
			fields.put("POSITIONS", "POSITIONS");
			parameterSource.addValue("POSITIONS", job.getPositions());
		}
		//
		if (job.getTravel() != null) {
			fields.put("TRAVEL", "TRAVEL");
			parameterSource.addValue("TRAVEL", job.getTravel());
		}
		//
		if (job.getOvertime() != null) {
			fields.put("OVERTIME", "OVERTIME");
			parameterSource.addValue("OVERTIME", job.getOvertime());
		}
		//
		if (job.getRateMin() != null) {
			fields.put("RATEMIN", "RATEMIN");
			parameterSource.addValue("RATEMIN", job.getRateMin());
		}
		//
		if (job.getRateMax() != null) {
			fields.put("RATEMAX", "RATEMAX");
			parameterSource.addValue("RATEMAX", job.getRateMax());
		}
		//
		if (job.getRateper() != null) {
			fields.put("RATEPER", "RATEPER");
			parameterSource.addValue("RATEPER", job.getRateper().toString());
		}
		//
		if (job.getBillRatePer() != null) {
			fields.put("BILLRATEPER", "BILLRATEPER");
			parameterSource.addValue("BILLRATEPER", job.getBillRatePer().toString());
		}
		//
		if (job.getRfqTitle() != null) {
			fields.put("RFQTITLE", "RFQTITLE");
			parameterSource.addValue("RFQTITLE", job.getRfqTitle());
		}
		//
		if (job.getExperience() != null) {
			fields.put("EXPERIENCE", "EXPERIENCE");
			parameterSource.addValue("EXPERIENCE", job.getExperience());
		}
		//
		if (job.getJobDescription() != null) {
			fields.put("JOBDESCRIPTION", "JOBDESCRIPTION");
			parameterSource.addValue("JOBDESCRIPTION", new JobDivaSqlLobValue(job.getJobDescription(), new DefaultLobHandler()), Types.CLOB);
		}
		//
		if (job.getJobStatus() != null) {
			fields.put("JOBSTATUS", "JOBSTATUS");
			parameterSource.addValue("JOBSTATUS", job.getJobStatus());
		}
		//
		if (job.getUrl() != null) {
			fields.put("URL", "URL");
			parameterSource.addValue("URL", job.getUrl());
		}
		//
		if (job.getRfqRefNo() != null) {
			fields.put("RFQREFNO", "RFQREFNO");
			parameterSource.addValue("RFQREFNO", job.getRfqRefNo());
		}
		//
		if (job.getPrivateAddress() != null) {
			fields.put("PRIVATEADDRESS", "PRIVATEADDRESS");
			parameterSource.addValue("PRIVATEADDRESS", job.getPrivateAddress());
		}
		//
		if (job.getPrivateCompanyName() != null) {
			fields.put("PRIVATECOMPANYNAME", "PRIVATECOMPANYNAME");
			parameterSource.addValue("PRIVATECOMPANYNAME", job.getPrivateCompanyName());
		}
		//
		if (job.getPrivateExpiryDate() != null) {
			fields.put("PRIVATEEXPIRYDATE", "PRIVATEEXPIRYDATE");
			parameterSource.addValue("PRIVATEEXPIRYDATE", job.getPrivateExpiryDate());
		}
		//
		if (job.getPrivateJobStartDate() != null) {
			fields.put("PRIVATEJOBSTARTDATE", "PRIVATEJOBSTARTDATE");
			parameterSource.addValue("PRIVATEJOBSTARTDATE", job.getPrivateJobStartDate());
		}
		//
		if (job.getPrivateJobEndDate() != null) {
			fields.put("PRIVATEJOBENDDATE", "PRIVATEJOBENDDATE");
			parameterSource.addValue("PRIVATEJOBENDDATE", job.getPrivateJobEndDate());
		}
		//
		if (job.getPrivateSalary() != null) {
			fields.put("PRIVATESALARY", "PRIVATESALARY");
			parameterSource.addValue("PRIVATESALARY", job.getPrivateSalary());
		}
		//
		if (job.getCustomerId() != null) {
			fields.put("CUSTOMERID", "CUSTOMERID");
			parameterSource.addValue("CUSTOMERID", job.getCustomerId());
		}
		//
		if (job.getFirstName() != null) {
			fields.put("FIRSTNAME", "FIRSTNAME");
			parameterSource.addValue("FIRSTNAME", job.getFirstName());
		}
		//
		if (job.getLastName() != null) {
			fields.put("LASTNAME", "LASTNAME");
			parameterSource.addValue("LASTNAME", job.getLastName());
		}
		//
		if (job.getSkills() != null) {
			fields.put("SKILLS", "SKILLS");
			parameterSource.addValue("SKILLS", job.getSkills());
		}
		//
		if (insertMode && job.getTeamid() != null) {
			fields.put("TEAMID", "TEAMID");
			parameterSource.addValue("TEAMID", job.getTeamid());
		}
		//
		if (job.getRfqNoTeam() != null) {
			fields.put("RFQNO_TEAM", "RFQNO_TEAM");
			parameterSource.addValue("RFQNO_TEAM", job.getRfqNoTeam());
		}
		//
		if (job.getCriteriaChanged() != null) {
			fields.put("CRITERIA_CHANGED", "CRITERIA_CHANGED");
			parameterSource.addValue("CRITERIA_CHANGED", job.getCriteriaChanged());
		}
		//
		if (job.getCriteriaState() != null) {
			fields.put("CRITERIA_STATE", "CRITERIA_STATE");
			parameterSource.addValue("CRITERIA_STATE", job.getCriteriaState());
		}
		//
		if (job.getCriteriaAreacode() != null) {
			fields.put("CRITERIA_AREACODE", "CRITERIA_AREACODE");
			parameterSource.addValue("CRITERIA_AREACODE", job.getCriteriaAreacode());
		}
		//
		if (job.getCriteriaMajor() != null) {
			fields.put("CRITERIA_MAJOR", "CRITERIA_MAJOR");
			parameterSource.addValue("CRITERIA_MAJOR", job.getCriteriaMajor());
		}
		//
		if (job.getCriteriaSalaryFrom() != null) {
			fields.put("CRITERIA_SALARY_FROM", "CRITERIA_SALARY_FROM");
			parameterSource.addValue("CRITERIA_SALARY_FROM", job.getCriteriaSalaryFrom());
		}
		//
		if (job.getCriteriaSalaryTo() != null) {
			fields.put("CRITERIA_SALARY_TO", "CRITERIA_SALARY_TO");
			parameterSource.addValue("CRITERIA_SALARY_TO", job.getCriteriaSalaryTo());
		}
		//
		if (job.getCriteriaSalaryPer() != null) {
			fields.put("CRITERIA_SALARY_PER", "CRITERIA_SALARY_PER");
			parameterSource.addValue("CRITERIA_SALARY_PER", job.getCriteriaSalaryPer());
		}
		//
		if (job.getCriteriaDegree() != null) {
			fields.put("CRITERIA_DEGREE", "CRITERIA_DEGREE");
			parameterSource.addValue("CRITERIA_DEGREE", job.getCriteriaDegree());
		}
		//
		if (job.getDateLastUpdated() != null) {
			fields.put("DATELASTUPDATED", "DATELASTUPDATED");
			parameterSource.addValue("DATELASTUPDATED", job.getDateLastUpdated());
		}
		//
		if (job.getDateStatusUpdated() != null) {
			fields.put("DATESTATUSUPDATED", "DATESTATUSUPDATED");
			parameterSource.addValue("DATESTATUSUPDATED", job.getDateStatusUpdated());
		}
		//
		if (job.getSyncRequired() != null) {
			fields.put("SYNC_REQUIRED", "SYNC_REQUIRED");
			parameterSource.addValue("SYNC_REQUIRED", job.getSyncRequired());
		}
		//
		if (job.getNotSkills() != null) {
			fields.put("NOTSKILLS", "NOTSKILLS");
			parameterSource.addValue("NOTSKILLS", job.getNotSkills());
		}
		//
		if (job.getNotCriteriaState() != null) {
			fields.put("NOTCRITERIA_STATE", "NOTCRITERIA_STATE");
			parameterSource.addValue("", job.getNotCriteriaState());
		}
		//
		if (job.getNotCriteriaAreacode() != null) {
			fields.put("NOTCRITERIA_AREACODE", "NOTCRITERIA_AREACODE");
			parameterSource.addValue("", job.getNotCriteriaAreacode());
		}
		//
		if (job.getNotCriteriaDegree() != null) {
			fields.put("NOTCRITERIA_DEGREE", "NOTCRITERIA_DEGREE");
			parameterSource.addValue("NOTCRITERIA_DEGREE", job.getNotCriteriaDegree());
		}
		//
		if (job.getNotCriteriaMajor() != null) {
			fields.put("NOTCRITERIA_MAJOR", "NOTCRITERIA_MAJOR");
			parameterSource.addValue("NOTCRITERIA_MAJOR", job.getNotCriteriaMajor());
		}
		//
		if (job.getNotCriteriaSalaryFrom() != null) {
			fields.put("NOTCRITERIA_SALARY_FROM", "NOTCRITERIA_SALARY_FROM");
			parameterSource.addValue("NOTCRITERIA_SALARY_FROM", job.getNotCriteriaSalaryFrom());
		}
		//
		if (job.getNotCriteriaSalaryTo() != null) {
			fields.put("NOTCRITERIA_SALARY_TO", "NOTCRITERIA_SALARY_TO");
			parameterSource.addValue("NOTCRITERIA_SALARY_TO", job.getNotCriteriaSalaryTo());
		}
		//
		if (job.getNotCriteriaSalaryPer() != null) {
			fields.put("NOTCRITERIA_SALARY_PER", "NOTCRITERIA_SALARY_PER");
			parameterSource.addValue("NOTCRITERIA_SALARY_PER", job.getNotCriteriaSalaryPer());
		}
		//
		if (job.getCriteriaCategories() != null) {
			fields.put("CRITERIA_CATEGORIES", "CRITERIA_CATEGORIES");
			parameterSource.addValue("CRITERIA_CATEGORIES", job.getCriteriaCategories());
		}
		//
		if (job.getNotCriteriaCategories() != null) {
			fields.put("NOTCRITERIA_CATEGORIES", "NOTCRITERIA_CATEGORIES");
			parameterSource.addValue("NOTCRITERIA_CATEGORIES", job.getNotCriteriaCategories());
		}
		//
		if (job.getPrivateCompanyName() != null) {
			fields.put("PRIVATEMYCOMPANYNAME", "PRIVATEMYCOMPANYNAME");
			parameterSource.addValue("PRIVATEMYCOMPANYNAME", job.getPrivateCompanyName());
		}
		//
		if (job.getExternalJobId() != null) {
			fields.put("EXTERNALJOBID", "EXTERNALJOBID");
			parameterSource.addValue("EXTERNALJOBID", job.getExternalJobId());
		}
		//
		if (job.getDatePriorityUpdated() != null) {
			fields.put("DATEPRIORITYUPDATED", "DATEPRIORITYUPDATED");
			parameterSource.addValue("DATEPRIORITYUPDATED", job.getDatePriorityUpdated());
		}
		//
		if (job.getResumesNo() != null) {
			fields.put("RESUMES_NO", "RESUMES_NO");
			parameterSource.addValue("RESUMES_NO", job.getResumesNo());
		}
		//
		if (job.getMaxResumesNo() != null) {
			fields.put("MAX_RESUMES_NO", "MAX_RESUMES_NO");
			parameterSource.addValue("MAX_RESUMES_NO", job.getMaxResumesNo());
		}
		//
		if (job.getContract() != null) {
			fields.put("CONTRACT", "CONTRACT");
			parameterSource.addValue("CONTRACT", job.getContract());
		}
		//
		if (job.getPriority() != null) {
			fields.put("PRIORITY", "PRIORITY");
			parameterSource.addValue("PRIORITY", job.getPriority());
		}
		//
		if (job.getJobdivaPost() != null) {
			fields.put("JOBDIVAPOST", "JOBDIVAPOST");
			parameterSource.addValue("JOBDIVAPOST", job.getJobdivaPost());
		}
		//
		if (job.getHarvestEnable() != null) {
			fields.put("HARVEST_ENABLE", "HARVEST_ENABLE");
			parameterSource.addValue("HARVEST_ENABLE", job.getHarvestEnable());
		}
		//
		if (job.getJobPriority() != null) {
			fields.put("JOBPRIORITY", "JOBPRIORITY");
			parameterSource.addValue("JOBPRIORITY", job.getJobPriority());
		}
		//
		if (job.getBillRateMin() != null) {
			fields.put("BILLRATEMIN", "BILLRATEMIN");
			parameterSource.addValue("BILLRATEMIN", job.getBillRateMin());
		}
		//
		if (job.getBillRateMax() != null) {
			fields.put("BILLRATEMAX", "BILLRATEMAX");
			parameterSource.addValue("BILLRATEMAX", job.getBillRateMax());
		}
		//
		if (job.getDivisionId() != null) {
			fields.put("DIVISIONID", "DIVISIONID");
			parameterSource.addValue("DIVISIONID", job.getDivisionId());
		}
		//
		if (job.getInstruction() != null) {
			fields.put("INSTRUCTION_OLD", "INSTRUCTION_OLD");
			parameterSource.addValue("INSTRUCTION_OLD", job.getInstruction());
		}
		//
		if (job.getCriteriaAttributes() != null) {
			fields.put("CRITERIA_ATTRIBUTES", "CRITERIA_ATTRIBUTES");
			parameterSource.addValue("CRITERIA_ATTRIBUTES", job.getCriteriaAttributes());
		}
		//
		if (job.getSearchId() != null) {
			fields.put("SEARCHID", "SEARCHID");
			parameterSource.addValue("SEARCHID", job.getSearchId());
		}
		//
		if (job.getCriteriaZipCode() != null) {
			fields.put("CRITERIA_ZIPCODE", "CRITERIA_ZIPCODE");
			parameterSource.addValue("CRITERIA_ZIPCODE", job.getCriteriaZipCode());
		}
		//
		if (job.getCriteriaZipCodeMiles() != null) {
			fields.put("CRITERIA_ZIPCODE_MILES", "CRITERIA_ZIPCODE_MILES");
			parameterSource.addValue("CRITERIA_ZIPCODE_MILES", job.getCriteriaZipCodeMiles());
		}
		//
		if (job.getPostingTitle() != null) {
			fields.put("POSTING_TITLE", "POSTING_TITLE");
			parameterSource.addValue("POSTING_TITLE", job.getPostingTitle());
		}
		//
		if (job.getPostingDescription() != null) {
			fields.put("POSTING_DESCRIPTION", "POSTING_DESCRIPTION");
			parameterSource.addValue("POSTING_DESCRIPTION", new JobDivaSqlLobValue(job.getPostingDescription(), new DefaultLobHandler()), Types.CLOB);
		}
		//
		if (job.getFills() != null) {
			fields.put("FILLS", "FILLS");
			parameterSource.addValue("FILLS", job.getFills());
		}
		//
		if (job.getJobCatalogid() != null) {
			fields.put("JOBCATALOGID", "JOBCATALOGID");
			parameterSource.addValue("JOBCATALOGID", job.getJobCatalogid());
		}
		//
		if (job.getCriteriaSubmitted() != null) {
			fields.put("CRITERIA_SUBMITTED", "CRITERIA_SUBMITTED");
			parameterSource.addValue("CRITERIA_SUBMITTED", job.getCriteriaSubmitted());
		}
		//
		if (job.getRefCheck() != null) {
			fields.put("REFCHECK", "REFCHECK");
			parameterSource.addValue("REFCHECK", job.getRefCheck());
		}
		//
		if (job.getDrugTest() != null) {
			fields.put("DRUGTEST", "DRUGTEST");
			parameterSource.addValue("DRUGTEST", job.getDrugTest());
		}
		//
		if (job.getBackCheck() != null) {
			fields.put("BACKCHECK", "BACKCHECK");
			parameterSource.addValue("BACKCHECK", job.getBackCheck());
		}
		//
		if (job.getMaxSubmitals() != null) {
			fields.put("MAXSUBMITALS", "MAXSUBMITALS");
			parameterSource.addValue("MAXSUBMITALS", job.getMaxSubmitals());
		}
		//
		if (job.getCurSubmittals() != null) {
			fields.put("CURSUBMITTALS", "CURSUBMITTALS");
			parameterSource.addValue("CURSUBMITTALS", job.getCurSubmittals());
		}
		//
		if (job.getCriteriaCity() != null) {
			fields.put("CRITERIA_CITY", "CRITERIA_CITY");
			parameterSource.addValue("CRITERIA_CITY", job.getCriteriaCity());
		}
		//
		if (job.getNotCriteriaCity() != null) {
			fields.put("NOTCRITERIA_CITY", "NOTCRITERIA_CITY");
			parameterSource.addValue("NOTCRITERIA_CITY", job.getNotCriteriaCity());
		}
		//
		if (job.getSecClearance() != null) {
			fields.put("SECCLEARANCE", "SECCLEARANCE");
			parameterSource.addValue("SECCLEARANCE", job.getSecClearance());
		}
		//
		if (job.getScheduleFacilityid() != null) {
			fields.put("SCHEDULE_FACILITYID", "SCHEDULE_FACILITYID");
			parameterSource.addValue("SCHEDULE_FACILITYID", job.getScheduleFacilityid());
		}
		//
		if (job.getScheduleColor() != null) {
			fields.put("SCHEDULE_COLOR", "SCHEDULE_COLOR");
			parameterSource.addValue("SCHEDULE_COLOR", job.getScheduleColor());
		}
		//
		if (job.getSuppPayRateMin() != null) {
			fields.put("SUPP_PAYRATEMIN", "SUPP_PAYRATEMIN");
			parameterSource.addValue("SUPP_PAYRATEMIN", job.getSuppPayRateMin());
		}
		//
		if (job.getSuppPayRateMax() != null) {
			fields.put("SUPP_PAYRATEMAX", "SUPP_PAYRATEMAX");
			parameterSource.addValue("SUPP_PAYRATEMAX", job.getSuppPayRateMax());
		}
		//
		if (job.getSuppPayRatePer() != null) {
			fields.put("SUPP_PAYRATEPER", "SUPP_PAYRATEPER");
			parameterSource.addValue("SUPP_PAYRATEPER", job.getSuppPayRatePer());
		}
		//
		if (job.getSuppComments() != null) {
			fields.put("SUPP_COMMENTS", "SUPP_COMMENTS");
			parameterSource.addValue("SUPP_COMMENTS", new JobDivaSqlLobValue(job.getSuppComments(), new DefaultLobHandler()), Types.CLOB);
		}
		//
		if (job.getApprovedStatus() != null) {
			fields.put("APPROVEDSTATUS", "APPROVEDSTATUS");
			parameterSource.addValue("APPROVEDSTATUS", job.getApprovedStatus());
		}
		//
		if (job.getSubInstruction() != null) {
			fields.put("SUBINSTRUCTION", "SUBINSTRUCTION");
			parameterSource.addValue("SUBINSTRUCTION", new JobDivaSqlLobValue(job.getSubInstruction(), new DefaultLobHandler()), Types.CLOB);
		}
		//
		if (job.getDateApproved() != null) {
			fields.put("DATEAPPROVED", "DATEAPPROVED");
			parameterSource.addValue("DATEAPPROVED", job.getDateApproved());
		}
		//
		if (job.getCountry() != null) {
			fields.put("COUNTRYID", "COUNTRYID");
			parameterSource.addValue("COUNTRYID", job.getCountry());
		}
		//
		if (job.getJobdivaPostDate() != null) {
			fields.put("JOBDIVAPOST_DATE", "JOBDIVAPOST_DATE");
			parameterSource.addValue("JOBDIVAPOST_DATE", job.getJobdivaPostDate());
		}
		//
		if (job.getBillrateCurrency() != null) {
			fields.put("BILLRATE_CURRENCY", "BILLRATE_CURRENCY");
			parameterSource.addValue("BILLRATE_CURRENCY", job.getBillrateCurrency());
		}
		//
		if (job.getPayrateCurrency() != null) {
			fields.put("PAYRATE_CURRENCY", "PAYRATE_CURRENCY");
			parameterSource.addValue("PAYRATE_CURRENCY", job.getPayrateCurrency());
		}
		//
		if (job.getSuppSubGuideline() != null) {
			fields.put("SUPP_SUB_GUIDELINE", "SUPP_SUB_GUIDELINE");
			parameterSource.addValue("SUPP_SUB_GUIDELINE", job.getSuppSubGuideline());
		}
		//
		if (job.getCompanyId() != null) {
			fields.put("COMPANYID", "COMPANYID");
			parameterSource.addValue("COMPANYID", job.getCompanyId());
		}
		//
		if (job.getCriteriaTitles() != null) {
			fields.put("CRITERIA_TITLES", "CRITERIA_TITLES");
			parameterSource.addValue("CRITERIA_TITLES", job.getCriteriaTitles());
		}
		//
		if (job.getMyFlag() != null) {
			fields.put("MY_FLAG", "MY_FLAG");
			parameterSource.addValue("MY_FLAG", job.getMyFlag());
		}
		//
		if (job.getDimDateIssued() != null) {
			fields.put("DIM_DATEISSUED", "DIM_DATEISSUED");
			parameterSource.addValue("DIM_DATEISSUED", job.getDimDateIssued());
		}
		//
		if (job.getPostingDate() != null) {
			fields.put("POSTING_DATE", "POSTING_DATE");
			parameterSource.addValue("POSTING_DATE", job.getPostingDate());
		}
		//
		if (job.getPortaldate() != null) {
			fields.put("PORTALDATE", "PORTALDATE");
			parameterSource.addValue("PORTALDATE", job.getPortaldate());
		}
		//
		if (job.getShowPayRate() != null) {
			fields.put("SHOW_PAYRATE", "SHOW_PAYRATE");
			parameterSource.addValue("SHOW_PAYRATE", job.getShowPayRate());
		}
		//
		if (job.getPortalJobCatId() != null) {
			fields.put("PORTALJOBCATID", "PORTALJOBCATID");
			parameterSource.addValue("PORTALJOBCATID", job.getPortalJobCatId());
		}
		//
		if (job.getInstruction() != null) {
			fields.put("INSTRUCTION", "INSTRUCTION");
			parameterSource.addValue("INSTRUCTION", new JobDivaSqlLobValue(job.getInstruction(), new DefaultLobHandler()), Types.CLOB);
		}
		//
	}
}

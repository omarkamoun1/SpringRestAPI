package com.jobdiva.api.dao.job;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.Job;

public class JobRowMapper implements RowMapper<Job> {
	
	private Short getShort(ResultSet rs, String fieldName) throws SQLException {
		Integer value = rs.getInt(fieldName);
		return value != null ? value.shortValue() : null;
	}
	
	@Override
	public Job mapRow(ResultSet rs, int rowNum) throws SQLException {
		Job job = new Job();
		//
		job.setId(rs.getLong("id"));
		job.setRecruiterId(rs.getLong("RECRUITERID"));
		job.setDepartment(rs.getString("DEPARTMENT"));
		job.setAddress1(rs.getString("ADDRESS1"));
		job.setAddress2(rs.getString("ADDRESS2"));
		job.setCity(rs.getString("CITY"));
		job.setState(rs.getString("STATE"));
		job.setZipcode(rs.getString("ZIPCODE"));
		job.setProvince(rs.getString("PROVINCE"));
		job.setDateIssued(rs.getDate("DATEISSUED"));
		job.setDeadLine(rs.getDate("DEADLINE"));
		job.setStartDate(rs.getDate("STARTDATE"));
		job.setEndDate(rs.getDate("ENDDATE"));
		job.setPositions(getShort(rs, "POSITIONS"));
		job.setTravel(rs.getBoolean("TRAVEL"));
		job.setOvertime(rs.getBoolean("OVERTIME"));
		job.setRateMin(rs.getBigDecimal("RATEMIN"));
		job.setRateMax(rs.getBigDecimal("RATEMAX"));
		job.setRateper(rs.getString("RATEPER") != null ? rs.getString("RATEPER").charAt(0) : null);
		job.setRfqTitle(rs.getString("RFQTITLE"));
		job.setExperience(rs.getInt("EXPERIENCE"));
		job.setJobDescription(rs.getString("JOBDESCRIPTION"));
		job.setJobStatus(rs.getInt("JOBSTATUS"));
		job.setUrl(rs.getString("URL"));
		job.setRfqRefNo(rs.getString("RFQREFNO"));
		job.setPrivateAddress(rs.getBoolean("PRIVATEADDRESS"));
		job.setPrivateCompanyName(rs.getBoolean("PRIVATECOMPANYNAME"));
		job.setPrivateExpiryDate(rs.getBoolean("PRIVATEEXPIRYDATE"));
		job.setPrivateJobStartDate(rs.getBoolean("PRIVATEJOBSTARTDATE"));
		job.setPrivateJobEndDate(rs.getBoolean("PRIVATEJOBENDDATE"));
		job.setPrivateSalary(rs.getBoolean("PRIVATESALARY"));
		job.setCustomerId(rs.getLong("CUSTOMERID"));
		job.setFirstName(rs.getString("FIRSTNAME"));
		job.setLastName(rs.getString("LASTNAME"));
		job.setSkills(rs.getString("SKILLS"));
		job.setTeamid(rs.getLong("TEAMID"));
		job.setRfqNoTeam(rs.getString("RFQNO_TEAM"));
		job.setCriteriaChanged(rs.getBoolean("CRITERIA_CHANGED"));
		job.setCriteriaState(rs.getString("CRITERIA_STATE"));
		job.setCriteriaAreacode(rs.getString("CRITERIA_AREACODE"));
		job.setCriteriaMajor(rs.getString("CRITERIA_MAJOR"));
		job.setCriteriaSalaryFrom(rs.getBigDecimal("CRITERIA_SALARY_FROM"));
		job.setCriteriaSalaryTo(rs.getBigDecimal("CRITERIA_SALARY_TO"));
		job.setCriteriaSalaryPer(rs.getString("CRITERIA_SALARY_PER"));
		job.setCriteriaDegree(rs.getString("CRITERIA_DEGREE"));
		job.setDateLastUpdated(rs.getDate("DATELASTUPDATED"));
		job.setDateStatusUpdated(rs.getDate("DATESTATUSUPDATED"));
		job.setSyncRequired(rs.getBoolean("SYNC_REQUIRED"));
		job.setNotSkills(rs.getString("NOTSKILLS"));
		job.setNotCriteriaState(rs.getString("NOTCRITERIA_STATE"));
		job.setNotCriteriaAreacode(rs.getString("NOTCRITERIA_AREACODE"));
		job.setNotCriteriaDegree(rs.getString("NOTCRITERIA_DEGREE"));
		job.setNotCriteriaMajor(rs.getString("NOTCRITERIA_MAJOR"));
		job.setNotCriteriaSalaryFrom(rs.getBigDecimal("NOTCRITERIA_SALARY_FROM"));
		job.setNotCriteriaSalaryTo(rs.getBigDecimal("NOTCRITERIA_SALARY_TO"));
		job.setNotCriteriaSalaryPer(rs.getString("NOTCRITERIA_SALARY_PER"));
		job.setCriteriaCategories(rs.getString("CRITERIA_CATEGORIES"));
		job.setNotCriteriaCategories(rs.getString("NOTCRITERIA_CATEGORIES"));
		job.setPrivateCompanyName(rs.getBoolean("PRIVATEMYCOMPANYNAME"));
		job.setExternalJobId(rs.getString("EXTERNALJOBID"));
		job.setDatePriorityUpdated(rs.getDate("DATEPRIORITYUPDATED"));
		job.setResumesNo(getShort(rs, "RESUMES_NO"));
		job.setMaxResumesNo(getShort(rs, "MAX_RESUMES_NO"));
		job.setContract(rs.getInt("CONTRACT"));
		job.setPriority(rs.getString("PRIORITY"));
		job.setJobdivaPost(rs.getBoolean("JOBDIVAPOST"));
		job.setHarvestEnable(rs.getBoolean("HARVEST_ENABLE"));
		job.setPriority(rs.getString("JOBPRIORITY"));
		job.setBillRateMin(rs.getBigDecimal("BILLRATEMIN"));
		job.setBillRateMax(rs.getBigDecimal("BILLRATEMAX"));
		job.setDivisionId(rs.getLong("DIVISIONID"));
		job.setSubInstruction(rs.getString("INSTRUCTION_OLD"));
		job.setCriteriaAttributes(rs.getString("CRITERIA_ATTRIBUTES"));
		job.setSearchId(rs.getLong("SEARCHID"));
		job.setCriteriaZipCode(rs.getString("CRITERIA_ZIPCODE"));
		job.setCriteriaZipCodeMiles(getShort(rs, "CRITERIA_ZIPCODE_MILES"));
		job.setPostingTitle(rs.getString("POSTING_TITLE"));
		// job.setPositions(getShort(rs, "POSTING_DESCRIPTION"));
		job.setBillRatePer(rs.getString("BILLRATEPER") != null ? rs.getString("BILLRATEPER").charAt(0) : null);
		job.setFills(getShort(rs, "FILLS"));
		job.setJobCatalogid(rs.getLong("JOBCATALOGID"));
		job.setCriteriaSubmitted(rs.getBoolean("CRITERIA_SUBMITTED"));
		job.setRefCheck(rs.getBoolean("REFCHECK"));
		job.setDrugTest(rs.getBoolean("DRUGTEST"));
		job.setBackCheck(rs.getBoolean("BACKCHECK"));
		job.setMaxSubmitals(getShort(rs, "MAXSUBMITALS"));
		job.setCurSubmittals(getShort(rs, "CURSUBMITTALS"));
		job.setCriteriaCity(rs.getString("CRITERIA_CITY"));
		job.setNotCriteriaCity(rs.getString("NOTCRITERIA_CITY"));
		job.setSecClearance(rs.getBoolean("SECCLEARANCE"));
		job.setScheduleFacilityid(rs.getLong("SCHEDULE_FACILITYID"));
		job.setScheduleColor(rs.getString("SCHEDULE_COLOR"));
		job.setSuppPayRateMin(rs.getBigDecimal("SUPP_PAYRATEMIN"));
		job.setSuppPayRateMax(rs.getBigDecimal("SUPP_PAYRATEMAX"));
		job.setSuppPayRatePer(rs.getString("SUPP_PAYRATEPER"));
		job.setSuppComments(rs.getString("SUPP_COMMENTS"));
		job.setApprovedStatus(rs.getBoolean("APPROVEDSTATUS"));
		job.setSubInstruction(rs.getString("SUBINSTRUCTION"));
		job.setDateApproved(rs.getDate("DATEAPPROVED"));
		job.setCountry(rs.getString("COUNTRYID"));
		job.setJobdivaPostDate(rs.getDate("JOBDIVAPOST_DATE"));
		job.setBillrateCurrency(rs.getInt("BILLRATE_CURRENCY"));
		job.setPayrateCurrency(rs.getInt("PAYRATE_CURRENCY"));
		job.setSuppSubGuideline(rs.getBoolean("SUPP_SUB_GUIDELINE"));
		job.setCompanyId(rs.getLong("COMPANYID"));
		job.setCriteriaTitles(rs.getString("CRITERIA_TITLES"));
		job.setMyFlag(rs.getBoolean("MY_FLAG"));
		job.setDimDateIssued(rs.getDate("DIM_DATEISSUED"));
		job.setPostingDate(rs.getDate("POSTING_DATE"));
		// job.setPortaldate(rs.getDate("PORTALDATE"));
		job.setShowPayRate(rs.getBoolean("SHOW_PAYRATE"));
		job.setShowPayRate(rs.getBoolean("PORTALJOBCATID"));
		job.setInstruction(rs.getString("INSTRUCTION"));
		//
		return job;
	}
}

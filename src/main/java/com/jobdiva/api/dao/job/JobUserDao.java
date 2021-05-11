package com.jobdiva.api.dao.job;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.axelon.mail.SMTPServer;
import com.axelon.recruiter.JDCurrency;
import com.axelon.shared.Application;
import com.axelon.util.JDLocale;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.JobUserSimple;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.utils.StringUtils;

@Component
public class JobUserDao extends AbstractJobDivaDao {
	
	
	public List<JobUserSimple> getAllJobUsers(JobDivaSession jobDivaSession, Long jobId) {
		
			String sql = "Select ID,NVL(T1.Recruiterid,0) as rfqRecruiter,a.Firstname,a.Lastname,a.email," +
			    	"a.rec_email, a.workphone, a.leader, NVL(T1.lead_recruiter,0), NVL(T1.sales,0), " +
			    	"NVL(T1.lead_sales,0),NVL(T1.recruiter,0),  nvl(a.user_options, 0), a.active  " +
			    	" From tRecruiter a," +
			    	" (select * From trecruiterrfq where rfqid= ? ) T1" +
			    	" Where Groupid= ? and active=1 and id<>688 and bitand(leader, 4096+16384+2097152+32768)=0 " +
			    	"   And T1.RecruiterId(+)=ID " +
			    	" Order by upper(Lastname),upper(Firstname)";
			Object[] params = new Object[] { jobId, jobDivaSession.getTeamId() };
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			//
			List<JobUserSimple> users = jdbcTemplate.query(sql, params, new RowMapper<JobUserSimple>() {
				
				@Override
				public JobUserSimple mapRow(ResultSet rs, int rowNum) throws SQLException {
					JobUserSimple jobUser = new JobUserSimple();
				    jobUser.setRecruiterId(rs.getLong("ID"));	 
				    jobUser.setFirstName(rs.getString("Firstname"));
		    		jobUser.setLastName(rs.getString("Lastname"));
					return jobUser;
				}
			});
			//
			return users;
	}
	
	
	
	public void insertJobUser(long jobid, Long userId, long teamid, boolean rec_email, Boolean isLeadRecruiter, Boolean isSale, Boolean isLeadSales, Boolean isRecruiter, Integer status) {
		String sqlInsert = "INSERT INTO TRECRUITERRFQ " //
				+ " (RFQID, RECRUITERID, TEAMID, REC_EMAIL, LEAD_RECRUITER, SALES, LEAD_SALES, RECRUITER, JOBSTATUS ) " //
				+ " VALUES " //
				+ "(?, ?, ? ,? ,? , ? ,? ,? , ? ) ";//
		Object[] params = new Object[] { jobid, userId, teamid, rec_email, isLeadRecruiter, isSale, isLeadSales, isRecruiter, status };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
	
	public void updateJobUser(long jobid, Long userId, long teamid, boolean rec_email, Boolean isLeadRecruiter, Boolean isSale, Boolean isLeadSales, Boolean isRecruiter, Integer status) {
		String sqlInsert = "UPDATE  TRECRUITERRFQ " //
				+ " SET REC_EMAIL = ? , LEAD_RECRUITER = ? , SALES = ? , LEAD_SALES = ?, RECRUITER = ? , JOBSTATUS = ?   " //
				+ " WHERE RFQID = ? AND  RECRUITERID = ? AND TEAMID = ? ";//
		Object[] params = new Object[] { rec_email, isLeadRecruiter, isSale, isLeadSales, isRecruiter, status, jobid, userId, teamid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
	
	public void deletJobuser(Long jobid, Long userId, Long teamid) {
		String sqlInsert = "DELETE FROM  TRECRUITERRFQ " //
				+ " WHERE RFQID = ? AND  RECRUITERID = ? AND TEAMID = ? ";//
		Object[] params = new Object[] { jobid, userId, teamid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
	
	public Boolean assignUserToJob(JobDivaSession jobDivaSession, Long rfqid, Long recruiterid, List<Long> roleIds, List<String> flexibleRoleNames , Integer jobstatus) throws Exception{
		
		String assignedTitle="";
		int receive_email=0;
        String recruiterEmail="";
    	String recruiterName="";
    	String sqlStr = "select rec_email_delete, email, firstname, lastname from trecruiter where groupid=? and id=?";
    	//
    	JdbcTemplate jdbcTemplate = getJdbcTemplate();
    	//
    	Object[] params = new Object[] { jobDivaSession.getTeamId(), recruiterid};
 
        List<List<String>> results = jdbcTemplate.query(sqlStr,params, new RowMapper<List<String>>() {
			
			@Override
			public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
			List<String> values= new ArrayList<>();
			values.add(rs.getString (1));
			values.add(rs.getString (2));
			values.add(rs.getString (3));
			values.add(rs.getString (4));
			return values;
			}
		});
        if (results!=null && results.size()>0) {
        	List<String> values=results.get(0);
          if (values!=null && values.size()>0) {
        	receive_email = Integer.parseInt(values.get(0));
        	recruiterEmail = values.get(1);
        	recruiterName = values.get(2)+" "+values.get(3);
          }
         }
		//
		String sqlInsert = "INSERT INTO tRecruiterRFQ (rfqid, recruiterid,teamid,recruiter,lead_recruiter,sales,lead_sales,rec_email,jobstatus) VALUES (?,?,?,?,?,?,?,?,?)";
		//
		if(receive_email == 2){
        	if(roleIds.contains(998l) || roleIds.contains(996l))
        		receive_email = 1;
        	else  receive_email =0;
        }
		
	    params = new Object[] { rfqid, recruiterid, jobDivaSession.getTeamId(),roleIds.contains(997l)?1:0,roleIds.contains(998l)?1:0,roleIds.contains(999l)?1:0,roleIds.contains(996l)?1:0,receive_email,jobstatus};
		//
		jdbcTemplate.update(sqlInsert, params);
		//
		long customerID = 0;
		//
        sqlStr = "select id from tcustomer where teamid=? and ifrecruiterthenid=?";
        //
        params = new Object[] { jobDivaSession.getTeamId(), recruiterid};
        //
        List<Long> customerIDs = jdbcTemplate.query(sqlStr, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("id");
			}
		});
        if (customerIDs!=null && customerIDs.size()>0) {
          customerID = customerIDs.get(0);
        }
        if(roleIds.contains(998l)){
        	assignedTitle = "Primary Recruiter";
		  }else if(roleIds.contains(997l)){
			 assignedTitle= "Recruiter";
		  }
        if(assignedTitle!="") {
        	assignedTitle += " and ";
			if(roleIds.contains(996l)){
			    assignedTitle += "Primary Sales";
			}else if(roleIds.contains(999l)){
				assignedTitle += "Sales";
			}
        }
        else {
			if(roleIds.contains(996l)){
				assignedTitle += "Primary Sales";
			}else if(roleIds.contains(999l)){
				assignedTitle += "Sales";
			}
        } 
        if(flexibleRoleNames!=null)
        if(!flexibleRoleNames.isEmpty()) {
        	for(int i=0;i<flexibleRoleNames.size();i++) {
        		if(assignedTitle!="") assignedTitle+=" and ";
                assignedTitle+=flexibleRoleNames;
        	}
        }
        if(customerID>0){
        	int RoleID = 950;
	        if (roleIds.contains(996l)) RoleID = 996;   //lead sales
	        else if (roleIds.contains(999l)) RoleID = 999; //sales
	        else if (roleIds.contains(998l)) RoleID = 998; //lead recruiter
	        else if (roleIds.contains(997l)) RoleID = 997; //recruiter
	        
	        sqlInsert = "INSERT INTO trfq_customers (teamid,rfqid,customerid,roleID,showonjob) VALUES (?,?,?,?,0)";
    	    //
	        params = new Object[] {jobDivaSession.getTeamId(),rfqid,customerID,RoleID};
            //
	        jdbcTemplate.update(sqlInsert, params);
	        //
        }
        Boolean leadRecruiter=false;
        if(roleIds.contains(998l)) leadRecruiter=true;
        roleIds.remove(996l); //lead sales
        roleIds.remove(999l); //sales
        roleIds.remove(998l); //lead recruiter
        roleIds.remove(997l); //recruiter
        if(roleIds.size()>0) { // flexible user roles
            for(int i=0; i < roleIds.size();i++) {	
            sqlInsert = "insert into TRECRUITERRFQ_ROLES values(?,?,?,?,sysdate)";				
			//
			params = new Object[] {rfqid,jobDivaSession.getTeamId(),recruiterid,roleIds.get(i)};
			//
			jdbcTemplate.update(sqlInsert, params);
			//
          }
            
        }
        //
        // Sync
        sqlInsert = "UPDATE trfq SET sync_required=2 Where id=?";
        params = new Object[] {rfqid};
        jdbcTemplate.update(sqlInsert, params);
        //
        
        //code added to choose harvest websites
        if(leadRecruiter) {//only when assigned person is lead recruiter
	        sqlStr = "delete from twebsites_jobs where teamid=? and rfqid=?";
	        params = new Object[] {jobDivaSession.getTeamId(),rfqid};
	        jdbcTemplate.update(sqlStr, params);
            //
	        sqlStr ="insert into twebsites_jobs (rfqid,teamid,webid,username,isharvest,machine_no) ";
	        sqlStr += " select ?, teamid, webid, username, isharvest, machine_no from twebsites_recruiters ";
	        sqlStr += " where teamid=? and recruiterid=?";
	        params = new Object[] {rfqid,jobDivaSession.getTeamId(),recruiterid};
	        jdbcTemplate.update(sqlStr, params);
        }
        //send Email
        SendEmailJobAssignment(jdbcTemplate,rfqid, jobDivaSession.getTeamId(), recruiterid, assignedTitle, jobDivaSession.getEnvironment().toString(), recruiterEmail, recruiterName, EMAIL_OPTION_ASSIGN_USER, receive_email==1, jobDivaSession.getRecruiterId());
        //
        return true;
	}



	private void SendEmailJobAssignment(JdbcTemplate jdbcTemplate,Long rfqid, Long teamId, Long recruiterid, String assignedTitle, String env,
	String recruiterEmail, String recruiterName, int email_option, boolean sendEmail, long recruiterId2) throws Exception{
		
		//contract=0---not specified  1--direct placement  2--contract   3--right to hire, 4--full time
	    String sqlStr = "select rfq.rfqno_team,rfq.rfqrefno,rfq.rfqtitle,rfq.department,rfq.city,decode(countries.defaultdisplay,1,states.state_name, states.state_abbr) as jobstate, countries.country as countryname,rfq.jobdescription," +
	        	      "cus.firstname,cus.lastname,rfq.billratemin," +
	        	      "rfq.billratemax," +
	        	      "decode(rfq.billrateper,'h','hour','d','day','y','year',(select x.name from trateunits x where rfq.billrateper=x.unitid and x.teamid=rfq.teamid and x.ratetype=1 and x.deleted=0 )) as billUnit, " +//"ASCII(rfq.billrateper) AS UnitCode, " +
	        	      "currency.id as currencyid, currency.name as currencyname, currency.symbol as currencysign,rfq.ratemin,rfq.ratemax," +
	        	      "decode(rfq.rateper,'h','hour','d','day','y','year',(select x.name from trateunits x where rfq.rateper=x.unitid and x.teamid=rfq.teamid and x.ratetype=0 and x.deleted=0 )) as payUnit, "+ //"ASCII(rfq.rateper) AS pay_UnitCode, " +
	        	      "currency.id as pay_currencyid, currency.name as pay_currencyname, currency.symbol as pay_currencysign,rfq.startdate,rfq.enddate," +
	        	      "rfq.positions, rfq.contract as contractCode," +
	        	      "(select x.name from trfq_position_types x where rfq.contract=x.id and rfq.teamid=x.teamid) as Contract,  " +//rfq.contract
	        	      "(select x.region_code from tteam x where x.id=rfq.teamid) as region_code,"+
	        	      "rfq.instruction from trfq rfq,tcustomer cus, tstates states, tcountries countries, tcurrency currency " +
	        	      "where rfq.customerid=cus.id(+) and rfq.id=? and rfq.teamid=?  and countries.id(+)=rfq.countryid and states.state_code(+)=rfq.state and states.countryid(+)=rfq.countryid  and currency.id(+)=rfq.billrate_currency ";
	        //
	        Object[] params = new Object[] {rfqid,teamId};
	        
	        jdbcTemplate.query(sqlStr, params , new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String JobDivaNo="", ContactName="", CompanyName="", primaryRecName="", primarySaleName="";
			        String strPayRate="", strBillRate="", strPositions="", strContract="", Location="";
			        String JobDescription="", Remark="", rfqtitle="", rfqno="";
			        String billUnit="", payUnit="",regionCode="";
			        double[] ArrPayRate = {0.0, 0.0};
			        double[] ArrBillRate = {0.0, 0.0};
			        Date startdate=null, enddate=null;
			        JDCurrency currency=null, pay_currency=null;
			        boolean hasJobInfo=false;
					hasJobInfo=true;
		        	JobDivaNo = StringUtils.deNull(rs.getString("rfqno_team"));
		        	rfqno = StringUtils.deNull(rs.getString("rfqrefno"));
		        	rfqtitle = StringUtils.deNull(rs.getString("rfqtitle"));
		        	CompanyName = StringUtils.deNull(rs.getString("department"));
		        	Location = StringUtils.deNull(rs.getString("city")).trim();
		        	if (Location.equals(""))
		        		Location = StringUtils.deNull(rs.getString("jobstate")).trim();
		        	else if(StringUtils.deNull(rs.getString("jobstate")).trim().length()>0)
		        	    Location += ", " + StringUtils.deNull(rs.getString("jobstate")).trim();
		        	if(Location.equals(""))
		        	    Location = StringUtils.deNull(rs.getString("countryname")).trim();
		        	else if(StringUtils.deNull(rs.getString("countryname")).trim().length()>0)
		        	    Location += ", " + StringUtils.deNull(rs.getString("countryname")).trim();		  
		        	  
		        	JobDescription = StringUtils.deNull(rs.getString("jobdescription"));
		        	ContactName = StringUtils.deNull(rs.getString("firstname")).trim() + " " + StringUtils.deNull(rs.getString("lastname")).trim();
		        	ArrBillRate[0] = rs.getDouble("billratemin");
		        	ArrBillRate[1] = rs.getDouble("billratemax");
		        	if((rs.getInt("currencyid")==3 || (StringUtils.deNull(rs.getString("currencyname"))).toLowerCase().equals("euro")) && (int) rs.getString("currencysign").charAt(0) != 128) {
		        		int euro = 128;
		        	    currency = new JDCurrency(rs.getInt("currencyid"), (StringUtils.deNull(rs.getString("currencyname"))), Character.valueOf((char) euro).toString());
		        	}else
		        	    currency = new JDCurrency(rs.getInt("currencyid"),(StringUtils.deNull(rs.getString("currencyname"))), rs.getString("currencysign"));
		        	if( currency.symbol == null || currency.symbol.length() == 0 || currency.name == null || currency.name.equals("")) {
		        	    currency = new JDCurrency(1,"USD","$");
		        	}
		        	ArrPayRate[0] = rs.getDouble("ratemin");
		        	ArrPayRate[1] = rs.getDouble("ratemax");
		        	if((rs.getInt("pay_currencyid")==3 || (StringUtils.deNull(rs.getString("pay_currencyname"))).toLowerCase().equals("euro")) && (int) rs.getString("pay_currencysign").charAt(0) != 128) {
		        		int euro = 128;
		        	    pay_currency = new JDCurrency(rs.getInt("pay_currencyid"), (StringUtils.deNull(rs.getString("pay_currencyname"))), Character.valueOf((char) euro).toString());
		        	}else
		        	    pay_currency = new JDCurrency(rs.getInt("pay_currencyid"),(StringUtils.deNull(rs.getString("pay_currencyname"))), rs.getString("pay_currencysign"));
		
		        	if( pay_currency.symbol == null || pay_currency.symbol.length() == 0 || pay_currency.name == null || pay_currency.name.equals("")) {
		        		pay_currency = new JDCurrency(1,"USD","$");
		        	}
		        	startdate = rs.getDate("startdate");
		        	billUnit=rs.getString("billUnit");
		        	payUnit=rs.getString("payUnit");
		        	regionCode=rs.getString("region_code");
		        	enddate = (rs.getTimestamp("enddate")!=null && rs.getTimestamp("enddate").getTime()>0?rs.getDate("enddate"):null);
		        	strPositions = StringUtils.deNull(rs.getString("positions"));
		        	strContract = rs.getString("Contract");
		        	if(strContract==null || strContract.trim().length()==0) {
		        		switch(rs.getInt("contractCode")){
		        		case 0:
		        			strContract="Not Specified";
		        			break;
		        		case 1:
		        			strContract="Direct Placement";
		        			break;
		        		case 2:
		        			strContract="Contract";
		        			break;
		        		case 3:
		        			strContract="Right to Hire";
		        			break;
		        		case 4:
		        			strContract="Full Time";
		        			break;
		        		}
		        	}
		        	Remark = StringUtils.deNull(rs.getString("instruction"));
				    //
		        	String emailSubject="";
			        if(hasJobInfo && sendEmail){
			        	try {
				        String[] tmpBillRate = new String[2];
				        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
				        tmpBillRate[0] = currency.symbol + df.format(ArrBillRate[0]);
				        tmpBillRate[1] = currency.symbol + df.format(ArrBillRate[1]);
				        strBillRate = getSeperatedTexts(tmpBillRate,"-",currency.symbol+df.format(0.0));
				        if (strBillRate.length()>0)
				          strBillRate += "/" + billUnit;
				        String[] tmpPayRate = new String[2];
				        tmpPayRate[0] = pay_currency.symbol + df.format(ArrPayRate[0]);
				        tmpPayRate[1] = pay_currency.symbol + df.format(ArrPayRate[1]);
				        strPayRate = getSeperatedTexts(tmpPayRate,"-",pay_currency.symbol+df.format(0.0));
				        if (strPayRate.equals("")==false)
				        	strPayRate += "/" + payUnit;
			
				        String TEAM_REGION_CODE = regionCode;
					    JDLocale regionFormat = new JDLocale(TEAM_REGION_CODE);
				        String emailLocation="http://www"+env+"jobdiva.com";
				        String strJobInfo;
						strJobInfo = "<b>Additional Job Information:</b>" +
									"<br><b>Title</b>: <a href='" + emailLocation + "/employers/open_rfq.jsp?rfqid=" + rfqid + "'>" + rfqtitle + "</a>" +
							        "<br><b>JobDiva #</b>: " + JobDivaNo +
							        "<br><b>Contact</b>: " + ContactName +
							        "<br><b>Company</b>: " + CompanyName +
							        "<br><b>Primary Recruiter</b>: " + primaryRecName + 
							        "<br><b>Primary Sales</b>: " + primarySaleName +		
							        "<br><b>Pay Rate:</b>: " + strPayRate +
							        "<br><b>Bill Rate:</b>: " + strBillRate +
							        "<br><b>Start Date</b>: " + (startdate==null?"":regionFormat.outputDate(startdate)) +
							        "<br><b>End Date</b>: " + (enddate==null?"":regionFormat.outputDate(enddate)) +
							        "<br><b># of Openings</b>: " + strPositions +
							        "<br><b>Position Type</b>: " + strContract +
							        "<br><b>Location</b>: " + Location +
							        "<br><b>Description</b>: <div style='border:gray 1px solid;padding:5px;'>" + JobDescription + "</div>" +
							        "<br><b>Remarks</b>: <div style='border:gray 1px solid; padding: 5px;'>" + Remark +"</div>";
				        String emailBody="";
				        if(email_option==EMAIL_OPTION_ASSIGN_USER){
				        	emailSubject = "You have just been assigned "+ (assignedTitle.length()>0?"as "+assignedTitle:"") 
					                +" to Job Reference # " + rfqno+(CompanyName.length()>0?CompanyName:"");
					        emailBody = "This is to inform you that you have just been assigned "+ (assignedTitle.length()>0?"as "+assignedTitle:"") +" to Job Reference # <a href='" + emailLocation + "/employers/open_rfq.jsp?rfqid=" + rfqid + "'>" + rfqno + "</a> (" + rfqtitle + ")" +
					                "<br><br>" + strJobInfo;
				        }else if (email_option==EMAIL_OPTION_UNASSIGN_USER){
					        emailSubject = "You have just been unassigned from Job Reference # " + rfqno+(CompanyName.length()>0?CompanyName:"");
					        emailBody = "This is to inform you that you have just been unassigned from Job Reference # <a href='" + emailLocation + "/employers/open_rfq.jsp?rfqid=" + rfqid + "'>" + rfqno + "</a> (" + rfqtitle + ")" +
					                "<br><br>" + strJobInfo;
				        }
				        emailBody = "<div style='font-family:arial;font-size:13px;'>" + emailBody + "</div>";
				        SMTPServer smtp_server = new SMTPServer();
			            smtp_server.setHost(Application.getSMTPServerLocation());
			            smtp_server.setIgnoreSPF(true);
			            smtp_server.setContentType(SMTPServer.CONTENT_TYPE_HTML);
						smtp_server.sendMail(recruiterEmail, "JobAssignment@jobdiva.com", emailSubject,emailBody);
						} catch (Exception e) {
							e.printStackTrace();
						}
			        }
				return "";
				}
			});
	}



	public Boolean unassignUserFromJob(JobDivaSession jobDivaSession, Long jobId, Long recruiterid) throws Exception{
		
		String recruiterEmail="";
    	String recruiterName="";
    	int receive_email=0;
    	String sqlStr = "select rec_email_delete, email, firstname, lastname from trecruiter where groupid=? and id=?"; 
    	//
    	Object[] params = new Object[] {jobDivaSession.getTeamId(),recruiterid};
    	//
    	JdbcTemplate jdbcTemplate = getJdbcTemplate();
    	//
    	 List<List<String>> results = jdbcTemplate.query(sqlStr,params, new RowMapper<List<String>>() {
 			
 			@Override
 			public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
 			List<String> values= new ArrayList<>();
 			values.add(rs.getString (1));
 			values.add(rs.getString (2));
 			values.add(rs.getString (3));
 			values.add(rs.getString (4));
 			return values;
 			}
 		});
        if (results!=null && results.size()>0) {
         	List<String> values=results.get(0);
           if (values!=null && values.size()>0) {
         	receive_email = Integer.parseInt(values.get(0));
         	recruiterEmail = values.get(1);
         	recruiterName = values.get(2)+" "+values.get(3);
           }
          }
		//
		String sqlInsert = "delete from tRecruiterRFQ where rfqid=? and recruiterid=? and teamid=? ";
		//
		params = new Object[] {jobId,recruiterid,jobDivaSession.getTeamId()};
    	//
		jdbcTemplate.update(sqlInsert, params);
		//
		//customer
    	sqlInsert="delete from trfq_customers a where a.teamid=? and a.rfqid=? and a.customerid=(select id from tcustomer x where x.teamid=a.teamid and x.ifrecruiterthenid=?)";
	    //
    	params = new Object[] {jobDivaSession.getTeamId(),jobId,recruiterid};
	    //
    	jdbcTemplate.update(sqlInsert, params);
    	//
    	//flexible roles
    	sqlInsert="delete from TRECRUITERRFQ_ROLES where rfqid=? and recruiterid=? and teamid=? ";
	    //
        params = new Object[] {jobId,recruiterid,jobDivaSession.getTeamId()};
	    //
    	jdbcTemplate.update(sqlInsert, params);
    	//
    	// Sync
        sqlInsert = "UPDATE trfq SET sync_required=2 Where id=?";
        params = new Object[] {jobId};
        jdbcTemplate.update(sqlInsert, params);
        //
        //Send Email
        SendEmailJobAssignment(jdbcTemplate, jobId, jobDivaSession.getTeamId(), recruiterid, "", jobDivaSession.getEnvironment().toString(), recruiterEmail, recruiterName, EMAIL_OPTION_UNASSIGN_USER, receive_email==1, jobDivaSession.getRecruiterId());
 
		return true;
	}



	public Boolean updateUserRoleForJob(JobDivaSession jobDivaSession, Long jobId, Long recruiterid, List<Long> roleIds) throws Exception{
	
		int receive_email=0;
    	String recruiterEmail="";
    	String recruiterName="";
    	String assignedTitle="";
    	String sqlStr = "select rec_email_delete, email, firstname, lastname from trecruiter where groupid=? and id=?";
    	Object[] params = new Object[] {jobDivaSession.getTeamId(),recruiterid};
    	//
    	JdbcTemplate jdbcTemplate = getJdbcTemplate();
    	//
    	List<List<String>> results = jdbcTemplate.query(sqlStr,params, new RowMapper<List<String>>() {
 			
 			@Override
 			public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
 			List<String> values= new ArrayList<>();
 			values.add(rs.getString(1));
 			values.add(rs.getString(2));
 			values.add(rs.getString(3));
 			values.add(rs.getString(4));
 			return values;
 			}
 		});
        if (results!=null && results.size()>0) {
         	List<String> values=results.get(0);
           if (values!=null && values.size()>0) {
         	receive_email = Integer.parseInt(values.get(0));
         	recruiterEmail = values.get(1);
         	recruiterName = values.get(2)+" "+values.get(3);
           }
          }
        //
        sqlStr = "Select recruiter,lead_recruiter,sales,lead_sales from tRecruiterRFQ " +
    			"Where rfqid=? and recruiterid=? and teamid=? ";
		params = new Object[] {jobId, recruiterid, jobDivaSession.getTeamId()};
		List<List<String>>roles=jdbcTemplate.query(sqlStr, params, new RowMapper<List<String>>() {
 			
 			@Override
 			public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
 			List<String> values= new ArrayList<>();
 			values.add(rs.getString(1));
 			values.add(rs.getString(2));
 			values.add(rs.getString(3));
 			values.add(rs.getString(4));
 			return values;
 			}
 		});
		List <String> role = new ArrayList<String>();
		if(roles!=null && roles.size()>0)
			role=roles.get(0);
		
		if(role.size()>=4) {
		if(role.get(1)=="0" && roleIds.contains(998l)) { // Primary Recruiter
				assignedTitle+="Primary Recruiter";
	     }
		if(role.get(0)=="0" && roleIds.contains(997l)) { // Recruiter
			if(assignedTitle!="") assignedTitle+=" and ";
            assignedTitle+="Recruiter";
		}
		if(role.get(2)=="0" && roleIds.contains(999l)) { // Sales
			if(assignedTitle!="") assignedTitle+=" and ";
            assignedTitle+="Sales";
		}
		if(role.get(3)=="0" && roleIds.contains(996l)) { // Primary Sales
			if(assignedTitle!="") assignedTitle+=" and ";
            assignedTitle+="Primary Sales";
		}
		}
        //
		String sqlUpdate = "Update tRecruiterRFQ Set recruiter=?,lead_recruiter=?,sales=?,lead_sales=? " +
    			"Where rfqid=? and recruiterid=? and teamid=? ";
		params = new Object[] {roleIds.contains(997l)?1:0,roleIds.contains(998l)?1:0,roleIds.contains(999l)?1:0,roleIds.contains(996l)?1:0, jobId, recruiterid, jobDivaSession.getTeamId()};
		jdbcTemplate.update(sqlUpdate, params);
		//
	    //customer
		int RoleID = 950;
        if (roleIds.contains(996l)) RoleID = 996; //Lead Sales
        else if (roleIds.contains(999l)) RoleID = 999; //Sales
        else if (roleIds.contains(998l)) RoleID = 998; //Lead Recruiter
        else if (roleIds.contains(997l)) RoleID = 997; //Recruiter
		//
        sqlUpdate="Update trfq_customers a Set roleID=? where teamid=? and rfqid=? and customerid=(select id from tcustomer x where x.teamid=a.teamid and x.ifrecruiterthenid=?) ";
    	//
		params = new Object[] {RoleID,jobDivaSession.getTeamId(),jobId,recruiterid};
		//
		jdbcTemplate.update(sqlUpdate, params);
		//
    	Boolean leadRecruiter=false;
        if(roleIds.contains(998l)) leadRecruiter=true;
        //
		roleIds.remove(996l); //lead sales
        roleIds.remove(999l); //sales
        roleIds.remove(998l); //lead recruiter
        roleIds.remove(997l); //recruiter
        //flexible Roles
        //
    	String sqlInsert="delete from TRECRUITERRFQ_ROLES where rfqid=? and recruiterid=? and teamid=? ";
        params = new Object[] {jobId,recruiterid,jobDivaSession.getTeamId()};
    	jdbcTemplate.update(sqlInsert, params);
		//
        if(roleIds.size()>0) {
            for(int i=0; i < roleIds.size();i++) {	
            sqlInsert = "insert into TRECRUITERRFQ_ROLES values(?,?,?,?,sysdate)";				
			params = new Object[] {jobId,jobDivaSession.getTeamId(),recruiterid,roleIds.get(i)};
			jdbcTemplate.update(sqlInsert, params);
          }
        }
        //
        if(roleIds.size()>0) {
        sqlStr="select id from TRECRUITERRFQ_ROLES where rfqid=? and recruiterid=? and teamid=? ";
        params = new Object[] {jobId,recruiterid,jobDivaSession.getTeamId()};
    	List<Long> existingRoleIds = jdbcTemplate.query(sqlStr, params, new RowMapper<Long>() {
 			
 			@Override
 			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
 			return rs.getLong("id");
 			}
 		});
    	if(existingRoleIds!=null && existingRoleIds.size()>0)
    	roleIds.removeAll(existingRoleIds);
        }
        List<String> flexibleRoleNames=new ArrayList<String>();
        if(roleIds.size()>0) {
        	for(int j=0;j<roleIds.size();j++) {
        	sqlStr="select name from TRECRUITER_ROLES where id=? ";
            params = new Object[] {roleIds.get(j)};
         	List<String> name = jdbcTemplate.query(sqlStr, params, new RowMapper<String>() {
      			
      			@Override
      			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
      			return rs.getString("name");
      			}
      		});
         	if(name!=null && name.size()>0)
         	flexibleRoleNames.add(name.get(0));
        	}
        }
        if(flexibleRoleNames!=null)
            if(!flexibleRoleNames.isEmpty()) {
            	for(int i=0;i<flexibleRoleNames.size();i++) {
            		if(assignedTitle!="") assignedTitle+=" and ";
                    assignedTitle+=flexibleRoleNames;
            	}
            }
        // Sync
        sqlInsert = "UPDATE trfq SET sync_required=2 Where id=?";
        params = new Object[] {jobId};
        jdbcTemplate.update(sqlInsert, params);
        //
        //code added to choose harvest websites
        if(leadRecruiter) {//only when assigned person is lead recruiter
	        sqlStr = "delete from twebsites_jobs where teamid=? and rfqid=?";
	        params = new Object[] {jobDivaSession.getTeamId(),jobId};
	        jdbcTemplate.update(sqlStr,params);
            //
	        sqlStr ="insert into twebsites_jobs (rfqid,teamid,webid,username,isharvest,machine_no) ";
	        sqlStr += " select ?, teamid, webid, username, isharvest, machine_no from twebsites_recruiters ";
	        sqlStr += " where teamid=? and recruiterid=?";
	        params = new Object[] {jobId,jobDivaSession.getTeamId(),recruiterid};
	        jdbcTemplate.update(sqlStr,params);
        }
        // Send Email
        SendEmailJobAssignment(jdbcTemplate,jobId, jobDivaSession.getTeamId(), recruiterid, assignedTitle, jobDivaSession.getEnvironment().toString(), recruiterEmail, recruiterName, EMAIL_OPTION_ASSIGN_USER, receive_email==1, jobDivaSession.getRecruiterId());
        //
		return true;
	}
}

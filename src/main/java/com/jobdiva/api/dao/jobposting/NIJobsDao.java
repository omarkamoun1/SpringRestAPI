package com.jobdiva.api.dao.jobposting;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.ByteArrayOutputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.setup.JobDivaConnectivity;
import com.jobdiva.api.model.JobBoardResponse;


@Component
public class NIJobsDao {
	
	//
	@Autowired
	JobDivaConnectivity		jobDivaConnectivity;
	//
	public JobBoardResponse request(String req, Long teamid, String rfqid, String username, String pass) {
		//
		//JobDivaSession jobDivaSession = getJobDivaSession();
		
		int webid = 118; // ****** Change value before Production
		
		String[] data = new String[30];
		String response = "";
		String status = "Success";

		
		String sql = "select param, value " //
				+ " from tjobpostingdata "//
				+ " where teamid = ? "//
				+ " and webid = ? "
				+ " and rfqid = ? ";
		Object[] params = new Object[] { teamid, webid, rfqid };
		//
		JdbcTemplate jdbcTemplate = jobDivaConnectivity.getJdbcTemplate(teamid);
		//
		List<List<Object>> list = jdbcTemplate.query(sql, params, new RowMapper<List<Object>>() {
			
			@Override
			public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<Object> list = new ArrayList<Object>();
				list.add(rs.getString("param"));
				list.add(rs.getString("value"));
				return list;
			}
		});
			
			
		if (req.equals("PostRq")) {	
			
			sql = "select posting_description from trfq where id = ? and teamid = ?";
			params = new Object[] { rfqid, teamid };
			
			List<String> desc = jdbcTemplate.query(sql, params, new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("posting_description");
				}
			});
			
			
			if (desc != null && desc.size() > 0) {
				if (desc.get(0)==null) {
					sql = "select jobdescription from trfq where id = ? and teamid = ?";
					params = new Object[] { rfqid, teamid };
				
					desc = jdbcTemplate.query(sql, params, new RowMapper<String>() {
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString("jobdescription");
						}
					});
				}
			}
			//
			//
		
			String[] elements = {"username","password","JobRefNo","jobTitle","jobExpiry","contactEmail","description","htmlDescription","categories","location","salaryRange","jobType","eduLevel",
							 		"contactName","addBenefits_1","q1","q2","q3", "location_1","location_2","categories_1",
							 		"addBenefits_2","addBenefits_3","addBenefits_4","addBenefits_5","addBenefits_6","addBenefits_7","addBenefits_8","addBenefits_9","addBenefits_10"}; //29
		
			for (int i = 3; i < 13; i++) {
				if (i==6 || i==7) data[i] = (desc != null && desc.size() > 0)?desc.get(0):"";
				else if (isInList(list,elements[i]) != -1) data[i] = (String) list.get(isInList(list,elements[i])).get(1); 
				else {
					List<String> failList = new ArrayList<String>();
					failList.add("Required Field(s) Missing in Database. (SourceError: " + elements[i] + ")");
					return new JobBoardResponse("Fail", failList, "NO REQUEST ESTABLISHED", "Server Not Contacted.");
				}
			}
			for (int i = 13; i < 30; i++) {
				if (isInList(list,elements[i]) != -1) data[i] = (String) list.get(isInList(list,elements[i])).get(1);
				else data[i] = "";
			}
			
			data[6] = data[6].replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
			data[6] = data[6].replaceAll("%C2%92","%27").replaceAll("%C2%93","%22").replaceAll("%C2%94","%22"); 
			try {
				data[6] = URLDecoder.decode(data[6], "UTF-8");
			} catch (Exception e) {
				data[6] = "";
			}
			data[6] = data[6].replace("\"", "\\\"");
			data[6] = data[6].replace("\r\n", "  ");
			data[6] = data[6].replace("\n", "  ");
			
			data[6] = data[6].replace( (char) 39, '\'').replace( (char) 34, '\'');
			data[6] = data[6].replaceAll("(?im)<br>",((char)13) + "");
			data[6] = data[6].replaceAll("<a href=`https"," ~_@_~ ");
			data[6] = data[6].replaceAll("` target=`_blank`>"," ~_@@_~ ");
			data[6] = data[6].replaceAll("<a "," ~_~_~ ");
			data[6] = data[6].replaceAll("<A "," ~_~_~ ");
			data[6] = data[6].replaceAll("</a>"," ~=~=~ ");
			data[6] = data[6].replaceAll("</A>"," ~=~=~ ");
			data[6] = data[6].replaceAll("(?m)<.*?>","");
			data[6] = data[6].replaceAll("<","&lt;");
			data[6] = data[6].replaceAll(" ~_~_~ ","<a ");
			data[6] = data[6].replaceAll(" ~=~=~ ","</a>");
			data[6] = data[6].replaceAll(" ~_@_~ ","<a href=\'https");
			
			data[7] = data[7].replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
			data[7] = data[7].replaceAll("%C2%92","%27").replaceAll("%C2%93","%22").replaceAll("%C2%94","%22"); 
			try {
				data[7] = URLDecoder.decode(data[7], "UTF-8");
			} catch (Exception e) {
				data[7] = "";
			}
			data[7] = data[7].replace("\"", "\\\"");
			data[7] = data[7].replace("\r\n", "");
			data[7] = data[7].replace("\n", "");
			
			//if (data[6].length() > 300) data[6] = data[6].substring(0, data[6].substring(0, 297).lastIndexOf(" ")) + "..."; // 300 chars max
			
		} else if (req.equals("DeleteRq")) {
			if (isInList(list,"jobTitle") != -1) data[3] = (String) list.get(isInList(list,"jobTitle")).get(1);
			else {
				List<String> failList = new ArrayList<String>();
				failList.add("Required Field Missing in Database: Job Title.");
				return new JobBoardResponse("Fail", failList, "NO REQUEST ESTABLISHED", "Server Not Contacted.");
			}
		}
		
		
		data[0] = username;
		data[1] = pass;
		data[2] = rfqid;
		
		String msRequest = "";
		String soapResponse = "";
		
		try {
			msRequest = createJsMessage(data, req);
			soapResponse = sendJsPost(msRequest, req);
		} catch (IOException e) {
			List<String> errorList = new ArrayList<String>();
			errorList.add(e.getMessage());
			return new JobBoardResponse("Exception", errorList, msRequest, "Error while contacting Server.");
		}
		
		switch (req) {
				
			case "PostRq":
				if (!isTag(soapResponse, "CreditsRemaining")) {
					status = "Post Failure";
					if (isTag(soapResponse, "LoginDetails")) response = "Account credentials are invalid.";
					else if (isTag(soapResponse, "JobDetails")) response = getTagValue(soapResponse, "JobDetails");
					else response = "NEW RESPONSE";
				} else {
					if (soapResponse.indexOf("<JobPostSuccess>Job has been unsuccessfully posted.</JobPostSuccess>")!=-1) {
						status = "Post Failure";
						response = "Parameter(s) sent don't match the criteria provided by NIJobs.";
						//if (soapResponse.indexOf("Error='Yes' ErrorDetails=")!=-1) {
						//} else response = "Parameter(s) sent don't match the criteria provided by NIJobs.";
					} else response = "Successfully posted onto NIJobs!";
				}
				break;
				
			case "DeleteRq":
				if (!isTag(soapResponse, "CreditsRemaining")) {
					status = "Delete Failure";
					if (isTag(soapResponse, "LoginDetails")) response = "Account credentials are invalid.";
					else if (isTag(soapResponse, "JobDetails")) response = getTagValue(soapResponse, "JobDetails");
					else response = "NEW RESPONSE";
				} else response = "Successfully deleted from NIJobs!";
				break;
				
			case "GetBalance":
			default:
				if (!isTag(soapResponse, "CreditsRemaining")) {
					status = "Query Failure";
					if (isTag(soapResponse, "LoginDetails")) response = "Account credentials are invalid.";
					else if (isTag(soapResponse, "JobDetails")) response = getTagValue(soapResponse, "JobDetails");
					else response = "NEW RESPONSE";
				} else response = getTagValue(soapResponse, "CreditsRemaining");
				break;
		}
		
		
		List<String> messageList = new ArrayList<String>();
		messageList.add(response);
		return new JobBoardResponse(status, messageList, msRequest, soapResponse);
	
		//
	}
	
	
	private int isInList(List<List<Object>> list, String element) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get(0).equals(element)) return i;
		}
		return -1;
	}
	
	private String createJsMessage(String[] data, String action) {
        
        String xmlMessage = "<XML ID=\"Transaction\"><Transaction>";
        xmlMessage += "<LoginDetails><Email>" + data[0] + "</Email><Password>" + data[1] + "</Password></LoginDetails>";
        
        switch (action) {
            case "PostRq":
            	xmlMessage += "<JobDetails>";
                
                xmlMessage += "<JobRefNo>" + data[2] + "</JobRefNo>";
                xmlMessage += "<JobTitle>" + data[3] + "</JobTitle>";
                xmlMessage += "<JobExpiry>" + data[4] + "</JobExpiry>";
                if (data[13] != "") xmlMessage += "<JobContactName>" + data[13] + "</JobContactName>";
                xmlMessage += "<JobContactEmail>" + data[5] + "</JobContactEmail>";
                xmlMessage += "<JobShortDesc>" + "" + "</JobShortDesc>";
                xmlMessage += "<JobDetDesc><![CDATA[ " + data[7] + " ]]></JobDetDesc>";
                xmlMessage += "<JobCategories>" + data[8]; //Multiple <>0,1,2<>
                if (data[20] != "") xmlMessage += "," + data[20];
                xmlMessage += "</JobCategories>";
                xmlMessage += "<JobLocations>" + data[9]; //Multiple <>0,1,2<>
                if (data[18] != "") xmlMessage += "," + data[18];
                if (data[19] != "") xmlMessage += "," + data[19];
                xmlMessage += "</JobLocations>";
                xmlMessage += "<JobSalaryRange>" + data[10] + "</JobSalaryRange>"; 
                if (data[14] != "") { //Multiple <>0,1,2<>
                	xmlMessage += "<JobAddnlBens>" + data[14]; 
                	if (data[21] != "") xmlMessage += "," + data[21];
                	if (data[22] != "") xmlMessage += "," + data[22];
                	if (data[23] != "") xmlMessage += "," + data[23];
                	if (data[24] != "") xmlMessage += "," + data[24];
                	if (data[25] != "") xmlMessage += "," + data[25];
                	if (data[26] != "") xmlMessage += "," + data[26];
                	if (data[27] != "") xmlMessage += "," + data[27];
                	if (data[28] != "") xmlMessage += "," + data[28];
                	if (data[29] != "") xmlMessage += "," + data[29];
                	xmlMessage += "</JobAddnlBens>";
                }
                xmlMessage += "<JobType>" + data[11] + "</JobType>";
                
                xmlMessage += "<JobMinQual>" + data[12] + "</JobMinQual>";
                if (data[15] != "") xmlMessage += "<JobQues1>" + data[15] + "</JobQues1>";
                if (data[16] != "") xmlMessage += "<JobQues2>" + data[16] + "</JobQues2>";
                if (data[17] != "") xmlMessage += "<JobQues3>" + data[17] + "</JobQues3>";
                
                xmlMessage += "</JobDetails>";
                //xmlMessage += "<SlotType>1</SlotType>";
                xmlMessage += "</Transaction></XML>";  
                
                break;
                
            case "DeleteRq":
            	xmlMessage += "<JobDetails>";
                
                xmlMessage += "<JobRefNo>" + data[2] + "</JobRefNo>";
                xmlMessage += "<JobTitle>" + data[3] + "</JobTitle>";
                
                xmlMessage += "</JobDetails></Transaction></XML>";
                
                break;
                
            default: //CreditsQuery
                xmlMessage += "</Transaction></XML>";
        }
         
        return xmlMessage;
    }
    
    private String sendJsPost(String xmlInput, String action) throws IOException {

        //Code to make a webservice HTTP request
        String responseString = "";
        String outputString = "";
        String wsURL = "";
        
        if (action.equals("PostRq")) wsURL = "https://www.NIJobs.com/VacManager/AddV2_XML.aspx";
        else if (action.equals("DeleteRq")) wsURL = "https://www.NIJobs.com/VacManager/DelV_XML.aspx";
        else wsURL = "https://www.NIJobs.com/VacManager/GetBalance_XML.aspx";
        
        URL url = new URL(wsURL);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) connection;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        
        byte[] buffer = new byte[xmlInput.length()];
        buffer = xmlInput.getBytes();
        bout.write(buffer);
        byte[] b = bout.toByteArray();
        
        // Set the appropriate HTTP Header parameters
        httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
        httpConn.setRequestProperty("Content-Type", "application/xml");
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        
        //Write the content of the request to the outputstream of the HTTP Connection
        OutputStream out = httpConn.getOutputStream();
        out.write(b);
        out.close();

        //Read the response
        InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
        BufferedReader in = new BufferedReader(isr);

        //Write the SOAP message response to a String
        while ((responseString = in.readLine()) != null) {
            outputString = outputString + responseString;
        }
        
        return outputString;
        
    }   
    
    private boolean isTag(String message, String tag) {
    	return (((message.split(Pattern.quote("<"+tag+" "), -1).length) - 1)==0)?false:true;
    }
    
    
    private String getTagValue(String message, String tag) {
        
    	//int rep = (message.split(Pattern.quote("<"+tag+" "), -1).length) - 1;
        
        String results = "";//new ArrayList<String>();
        //int repIndex = 0;
        //int searchIndex = 0;

        //while (true) {
            int indexB = message.indexOf(tag);//, searchIndex);
            int indexBFixed = indexB;
            int indexE = 0, indexEE = 0;
            String endStr = "";

            if (indexB == -1) {
                return results;
            } else {
                if (message.charAt(indexB - 1) == '<') {
                    while (message.charAt(indexB) != '>' || message.charAt(indexB) != ' ') {
                        if (indexB - indexBFixed >= tag.length()) {
                            break;
                        }
                        indexB++;
                    }

                    if (message.charAt(indexB) == '>' || message.charAt(indexB) == ' ') {
                    	String tmp = message.substring(indexB, message.indexOf("/>", indexB));
                    	
                    	if (tmp.indexOf("Description='") != -1) { // case 4
                    		indexE = tmp.indexOf("Error='") + 7;
                    		indexEE = getTagEndIndex(tmp, indexE);
                    		endStr += tmp.substring(indexE, indexEE) + ": ";
                    		
                    		indexE = tmp.indexOf("Description='") + 13;
                    		indexEE = getTagEndIndex(tmp, indexE);
                    		endStr += tmp.substring(indexE, indexEE);
                    		
                    	} else if (tmp.indexOf("ErrorDetails='") != -1) { // case 2
                    		endStr += "Error";
                    		
                    		indexE = tmp.indexOf("Value='") + 7;
                    		indexEE = getTagEndIndex(tmp, indexE);
                    		endStr += "(" + tmp.substring(indexE, indexEE) + "): ";
                    		
                    		indexE = tmp.indexOf("ErrorDetails='") + 14;
                    		indexEE = getTagEndIndex(tmp, indexE);
                    		endStr += tmp.substring(indexE, indexEE);
                 
                    	} else if (tmp.indexOf("Error='") != -1) { // case 3
                    		indexE = tmp.indexOf("Error='") + 7;
                    		indexEE = getTagEndIndex(tmp, indexE);
                    		endStr = tmp.substring(indexE, indexEE);
                    	} else if (tmp.indexOf("Value='") != -1) { // case 1
                    		indexE = tmp.indexOf("Value='") + 7;
                    		indexEE = getTagEndIndex(tmp, indexE);
                            endStr = tmp.substring(indexE, indexEE);
                    	} else {
                    		endStr = "API Response: New Message Type";
                    	}
                    	
                    	//results.add(endStr);
                    	//repIndex++;
                        //if (repIndex == rep) return results;
                    }

                }
                //searchIndex = indexB + 1;
            }
        //}
            
            return endStr;

        // Null: No tag found
        // "" : Empty Value
    }
    
    private int getTagEndIndex(String str, int startIndex) {
		int indexEE = startIndex;
		while (str.charAt(indexEE) != '\'') {
            indexEE++;
        }
		return indexEE;
    }
}
package com.jobdiva.api.dao.jobposting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.io.ByteArrayOutputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.config.AppProperties;
import com.jobdiva.api.dao.setup.JobDivaConnectivity;


@Component
public class IrishJobsDao {
	
	//
	@Autowired
	JobDivaConnectivity		jobDivaConnectivity;
	//
	@Autowired
	AppProperties		appProperties;
	//
	
	private final String PROVIDER_ID = "8ee8610f-726d-404b-8615-f813ec4874eb";
	
	// TO JSPs
	@SuppressWarnings("unchecked")
	public String getSlots(String username, String password, String recruiterid, String teamid) {
		
		String authToken = authenticate(username, password, recruiterid, teamid, 0);
		if (authToken.contains("EXCEPTION") || authToken.contains("ERROR")) return "{\"response\":{\"status\":\"Error\",\"reason\":\"" + authToken + "\"}}";
		
		String response = "";
		
		try {
			String responseString = "";
	        String wsURL = appProperties.getIrishJobsUrl() + "/api/1.0/units";

	        URL url = new URL(wsURL);
	        URLConnection connection = url.openConnection();
	        HttpURLConnection httpConn = (HttpURLConnection) connection;
	        
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
	        dateFormat.setTimeZone(gmtTime);
	        Date date = new Date();
	        
	        // Set the appropriate HTTP Header parameters
	        httpConn.setRequestProperty("X-Auth-Token", authToken);
	        httpConn.setRequestProperty("X-Request-Date", dateFormat.format(date).replace(" ", "T"));
	        httpConn.setRequestProperty("Content-Type", "application/json");
	        httpConn.setRequestMethod("GET");
	        httpConn.setDoOutput(true);
	        httpConn.setDoInput(true);

	        //Read the response
	        InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
	        BufferedReader in = new BufferedReader(isr);

	        //Write the message response to a String
	        while ((responseString = in.readLine()) != null) {
	        	response = response + responseString;
	        }
	        
			JsonParser parser = JsonParserFactory.getJsonParser();
			Map<String, Object> map = parser.parseMap(response);
		    
			if (map.get("errorCode") != null) {
				if ((int) map.get("errorCode") != 0) {
					response = "{\"response\":{\"status\":\"Error\",\"code\":\"" + (int) map.get("errorCode") + "\",\"reason\":\"" + (String) map.get("message") + "\"}}";
				} else if (map.get("data") != null) {
					map = (Map<String, Object>) map.get("data");
					response = map.toString().replace("{", "{\"").replace("=","\":\"").replace(", ","\",\"").replace("\"[","[").replace("}\",\"","},").replace("}","\"}").replace("]\"}","]}");
				}
			}
			
		} catch (Exception e) {
			response = "{\"response\":{\"status\":\"Exception\",\"reason\":\"" + e.getMessage() + "\"}}";
		}
		
		return response;
	}
	
	// TO JSPs
	@SuppressWarnings("unchecked")
	public String request(String username, String password, String action, String rfqid, String recruiterid, String teamid) {

		int webid = 102;
		
		String[] data = new String[17];
		
		if (action.equals("post") || action.equals("update")) {
			String sql = "select param, value " //
					+ " from tjobpostingdata "//
					+ " where teamid = ? "//
					+ " and webid = ? "
					+ " and rfqid = ? ";
			Object[] params = new Object[] { teamid, webid, rfqid };
			//
			JdbcTemplate jdbcTemplate = jobDivaConnectivity.getJdbcTemplate(Long.parseLong(teamid));
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
					
					if (desc != null && desc.size() > 0) {
						if (desc.get(0) != null) {
							data[10] = desc.get(0);
							data[10] = data[10].replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
						} else {
							data[10] = "";
						}
					} else {
						return "{\"response\":{\"status\":\"Fail\",\"reason\":\"Error while retrieving description from Database!\"}}";
					}
				} else {
					data[10] = desc.get(0);
					data[10] = data[10].replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
					data[10] = data[10].replaceAll("%C2%92","%27").replaceAll("%C2%93","%22").replaceAll("%C2%94","%22"); 
					try {
						data[10] = URLDecoder.decode(data[10], "UTF-8");
					} catch (Exception e) {
						data[10] = "";
					}
				}
				
				data[10] = data[10].replace("\"", "\\\"");
				data[10] = data[10].replace("\r\n", "");
				data[10] = data[10].replace("\n", "");
				data[10] = data[10].replaceAll("[\\p{C}]","");
			} else {
				return "{\"response\":{\"status\":\"Fail\",\"reason\":\"Error while retrieving description from Database!\"}}";
			}
			//
			//
		
			String[] NElements = {"UUID", "Job Title", "Job Category", "Employment Type", "Street Location Type", "Street Location Description", "Remuneration Type", 
					"Remuneration From Amount", "Remuneration To Amount", "Desired Skills", "Job Description", "Advert Unit Type"};
			
			String[] elements = {"uuid", "jobtitle", "jobcategory", "employmenttype", "streetlocationtype", "streetlocationdesc", "remunerationtype", 
									"fromamount", "toamount", "desiredSkills", "jobdescription", "advertunittype", "addjobCategory", "employername", 
									"ben", "applyurl", "hideSal"}; //17

			
			if (action.equals("update") && isInList(list,elements[0]) == -1) return "{\"response\":{\"status\":\"Fail\",\"reason\":\"UUID Field Missing in Database\"}}";
			else if (isInList(list,elements[0]) != -1) data[0] = (String) list.get(isInList(list,elements[0])).get(1);
			else data[0] = "";
			
			String missingFields = "";
		
			for (int i = 1; i < 12; i++) {
				if (i != 10) {
					if (isInList(list,elements[i]) != -1) data[i] = (String) list.get(isInList(list,elements[i])).get(1);
					else if (i==5 && data[4].equals("2")) data[i] = "";
					else if ((i==7 || i==8) &&  data[6].equals("2001")) data[i] = "";
					else missingFields += NElements[i] + ", ";
				}
			}
			if (missingFields.length() > 0) {
				missingFields = missingFields.substring(0, missingFields.length()-2);
				return "{\"response\":{\"status\":\"Fail\",\"reason\":\"Required Field(s) Missing in Database: " + missingFields + ".\"}}";
			}
			
			for (int i = 12; i < 17; i++) {
				if (isInList(list,elements[i]) != -1) data[i] = (String) list.get(isInList(list,elements[i])).get(1);
				else data[i] = "";
			}
			
		}
		
		String authToken = authenticate(username, password, recruiterid, teamid, 0);
		if (authToken.contains("EXCEPTION") || authToken.contains("ERROR")) return "{\"response\":{\"status\":\"Error\",\"reason\":\"" + authToken + "\"}}";
		
		
		String rqResponse = "";
		
		try {
			rqResponse = sendRequest(action, createMessage(data, action), authToken, data[0]);
			
			JsonParser parser = JsonParserFactory.getJsonParser();
			Map<String, Object> map = parser.parseMap(rqResponse);
		    
			if (map.get("errorCode") != null) {
				if ((int) map.get("errorCode") != 0) {
					rqResponse = "{\"response\":{\"status\":\"Error\",\"code\":\"" + (int) map.get("errorCode") + "\",\"reason\":\"" + (String) map.get("message") + "\"}}";
				} else if (map.get("data") != null) {
					map = (Map<String, Object>) map.get("data");
					if (map.get("id") != null) {
						rqResponse = "{\"response\":{\"status\":\"Success\",\"id\":\"" + (String) map.get("id") + "\"";
						if (map.get("viewUrl") != null) rqResponse += ",\"viewUrl\":\"" + (String) map.get("viewUrl") + "\"";
						rqResponse += "}}";
					}
				}
			}
			
			
		} catch (Exception e) {
			rqResponse = "{\"response\":{\"status\":\"Exception\",\"reason\":\"" + e.getMessage() + "\"}}";
		}
		
		
		return rqResponse;
	}
	
	private int isInList(List<List<Object>> list, String element) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get(0).equals(element)) return i;
		}
		return -1;
	}
	
	private String createMessage(String[] data, String action) {
        
        String xmlMessage = "{";
        
        switch (action) {
            case "post":
            case "update":
            	xmlMessage += "\"ownerId\":" + null + ",";
            	xmlMessage += "\"job\":{";
            	
                xmlMessage += "\"jobTitle\":\"" + data[1] + "\",";
                xmlMessage += "\"jobCategory\":" + data[2] + ",";
                xmlMessage += "\"employmentType\":" + data[3] + ",";
                
                xmlMessage += "\"streetLocation\":{" + "\"type\":" + data[4]; // type: (1) - Office Location / (2) - Work From Home
                if (data[5] != "") xmlMessage += ",\"description\":\"" + data[5] + "\"";
                xmlMessage += "},";
                
                xmlMessage += "\"remuneration\":{" + "\"type\":" + data[6];
                if (data[7] != "" || data[8] != "") xmlMessage += ",\"fromAmount\":" + data[7] + ",\"toAmount\":" + data[8];
                if (data[16] != "") xmlMessage += ",\"hideAmount\":" + data[16];
                xmlMessage += "},";
                		
                xmlMessage += "\"desiredSkills\":[";
                xmlMessage += data[9];
                xmlMessage += "],";
                
                xmlMessage += "\"jobDescription\":\"" + data[10] + "\"";
                
                if (data[12] != "") xmlMessage += ",\"additionalJobCategories\":[" + data[12] + "]"; 
                if (data[13] != "") xmlMessage += ",\"employerName\":\"" + data[13] + "\"";
                
                if (data[14] != "") xmlMessage += ",\"benefits\":[" + data[14] + "]";
                
                xmlMessage += "},";
                
                xmlMessage += "\"advert\":{" + "\"unitType\":" + data[11] + "}";
                
                if (data[15] != "") xmlMessage += ",\"apply\":{" + "\"applyUrl\":\"" + data[15] + "\"}";
                
                xmlMessage += "}";
                
                break;

            default:
                xmlMessage = "";
        }
         
        return xmlMessage;
    }
	
	// TO JSPs
	public String getAllLists() {
		return "{\"unitTypes\":" + getList("units")
				+ ", \"actions\":" + getList("actions")
				+ ", \"categories\":" + getList("categories")
				+ ", \"employmenttypes\":" + getList("employmenttypes")
				+ ", \"remunerationtypes\":" + getList("remunerationtypes")
				+ "}";
	}
	
	@SuppressWarnings("unchecked")
	private String getList(String list) {
		String response = "";
		
		try {
			//Code to make a web service HTTP request
	        String responseString = "";
	        String wsURL = appProperties.getIrishJobsUrl();
	        
	        if (list.equals("units")) wsURL += "/api/1.0/lists/unittypes";
	        else if (list.equals("categories")) wsURL += "/api/1.0/lists/jobcategories";
	        else if (list.equals("employmenttypes")) wsURL += "/api/1.0/lists/employmenttypes";
	        else if (list.equals("remunerationtypes")) wsURL += "/api/1.0/lists/remunerationtypes";
	        else wsURL += "/api/1.0/lists/jobactions";
	        
	        URL url = new URL(wsURL);
	        URLConnection connection = url.openConnection();
	        HttpURLConnection httpConn = (HttpURLConnection) connection;
	        
	        // Set the appropriate HTTP Header parameters
	        httpConn.setRequestProperty("Content-Type", "application/json");
	        httpConn.setRequestMethod("GET");
	        httpConn.setDoOutput(true);
	        httpConn.setDoInput(true);

	        //Read the response
	        InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
	        BufferedReader in = new BufferedReader(isr);

	        //Write the message response to a String
	        while ((responseString = in.readLine()) != null) {
	        	response = response + responseString;
	        }
			
			JsonParser parser = JsonParserFactory.getJsonParser();
			Map<String, Object> map = parser.parseMap(response);
		    
			if (map.get("errorCode") != null) {
				if ((int) map.get("errorCode") != 0) {
					response = "{\"response\":{\"status\":\"Error\",\"code\":\"" + (int) map.get("errorCode") + "\",\"reason\":\"" + (String) map.get("message") + "\"}}";
				} else if (map.get("data") != null) {
					map = (Map<String, Object>) map.get("data");
					response = map.toString().replace("{", "{\"").replace("=","\":\"").replace(", value","\",\"value").replace(", items","\",\"items").replace("\"[","[").replace("}\",\"","},").replace("}","\"}").replace("]\"}","]}");
				}
			}
			
		} catch (Exception e) {
			response = "{\"response\":{\"status\":\"Exception\",\"reason\":\"" + e.getMessage() + "\"}}";
		}
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	private String authenticate(String username, String password, String recruiterid, String teamid, int responsetype) {
		
		int typeid = 10;
		
		Date date = new Date();
		
		String sql = "select accesstoken, expires"
				+ " from toauthaccesstoken"
				+ " where typeid=?"
				+ " and recruiterid=?"
				+ " and teamid=?";
		Object[] params = new Object[] { typeid, recruiterid, teamid };
		JdbcTemplate jdbcTemplate = jobDivaConnectivity.getJdbcTemplate(Long.parseLong(teamid));
		List<List<Object>> list = jdbcTemplate.query(sql, params, new RowMapper<List<Object>>() {
			
			@Override
			public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<Object> list = new ArrayList<Object>();
				list.add(rs.getString("accesstoken"));
				list.add(rs.getTimestamp("expires").getTime());
				return list;
			}
		});
		
		if (list != null && list.size() > 0) {
			if (list.get(0) != null && list.get(0).size() > 0) {
				if (date.getTime() < (long) list.get(0).get(1)) {
					if (responsetype == 0) return (String) list.get(0).get(0);
					else return "{\"response\":{\"status\":\"Success\",\"token\":\"" + (String) list.get(0).get(0) + "\"}}";
				}
			}
		}
		
		String response = "";
		String reqBody = "{" + 
				"\"username\":\""+ username +"\"," + 
				"\"password\":\""+ password +"\"," + 
				"\"providerId\":\""+ PROVIDER_ID +"\"" + 
				"}";
		
		try {
			response = sendRequest("authenticate", reqBody, "", "");
			
			JsonParser parser = JsonParserFactory.getJsonParser();
			Map<String, Object> map = parser.parseMap(response);
		    
			if (map.get("errorCode") != null) {
				if ((int) map.get("errorCode") != 0) {
					if (responsetype == 0) response =  "ERROR: (" + (int) map.get("errorCode") + "): " + (String) map.get("message");
					else response = "{\"response\":{\"status\":\"Error\",\"code\":\"" + (int) map.get("errorCode") + "\",\"reason\":\"" + (String) map.get("message") + "\"}}";
				} else if (map.get("data") != null) {
					map = (Map<String, Object>) map.get("data");
					if (map.get("token") != null && map.get("expires") != null) {
						if (responsetype == 0) response =  (String) map.get("token");
						else response = "{\"response\":{\"status\":\"Success\",\"token\":\"" + (String) map.get("token") + "\"}}";
						
			            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			            TimeZone zTime = TimeZone.getTimeZone("GMT");
				        dateFormat.setTimeZone(zTime);
			            date = dateFormat.parse(((String) map.get("expires")).replace("T", " "));
			            long expires = date.getTime();
						
						sql = "delete from toauthaccesstoken"
							+ " where recruiterid=?"
							+ " and teamid=?"
							+ " and typeid = ?";
						params = new Object[] { recruiterid, teamid, typeid };
						jdbcTemplate.update(sql, params);
						
						sql = "insert into toauthaccesstoken"
							+ "(typeid, recruiterid, teamid, accesstoken, expires, refreshtoken, lastaccess)"
							+ "values (?,?,?,?,?,?,sysdate)";
						params = new Object[] {typeid, recruiterid, teamid, (String) map.get("token"), new Timestamp(expires), ""};
						jdbcTemplate.update(sql, params);
						
					}
				}
			}
		    
		} catch (Exception e) {
			if (responsetype == 0) response =  "EXCEPTION: " + e.getMessage();
			else response = "{\"response\":{\"status\":\"Exception\",\"reason\":\"" + e.getMessage() + "\"}}";
		}
		
		return response;
	}

	private String sendRequest(String action, String body, String auth_key, String jobUUID) throws IOException {

        //Code to make a web service HTTP request
        String responseString = "";
        String outputString = "";
        String wsURL = appProperties.getIrishJobsUrl();
        
        if (action.equals("authenticate")) wsURL += "/api/1.0/authenticate";
        else if (action.equals("post")) wsURL += "/api/1.0/jobs";
        else if (action.equals("update")) wsURL += "/api/1.0/jobs/" + jobUUID;
        else if (action.equals("expire")) wsURL += "/api/1.0/jobs/" + jobUUID + "/actions/5";
        else {return "ERROR(sendPost()): Request not valid.";}
        
        URL url = new URL(wsURL);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) connection;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        
        byte[] buffer = new byte[body.length()];
        buffer = body.getBytes();
        bout.write(buffer);
        byte[] b = bout.toByteArray();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        dateFormat.setTimeZone(gmtTime);
        Date date = new Date();
        
        // Set the appropriate HTTP Header parameters
        if (!action.equals("authenticate")) httpConn.setRequestProperty("X-Auth-Token", auth_key);
        httpConn.setRequestProperty("X-Request-Date", dateFormat.format(date).replace(" ", "T"));
        httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        
        //Write the content of the request to the output stream of the HTTP Connection
        OutputStream out = httpConn.getOutputStream();
        out.write(b);
        out.close();

        int statusCode = httpConn.getResponseCode();
        InputStream is;
        
        if (statusCode >= 200 && statusCode < 400) is = httpConn.getInputStream();
        else is = httpConn.getErrorStream();
        
        //Read the response
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader in = new BufferedReader(isr);

        //Write the message response to a String
        while ((responseString = in.readLine()) != null) {
            outputString = outputString + responseString;
        }
        
        return outputString;
        
    } 
	

}
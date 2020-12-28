package com.jobdiva.api.dao.jobposting;

import java.util.ArrayList;
import java.util.List;
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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.setup.JobDivaConnectivity;


@Component
public class VolcanicDao {
	
	//
	@Autowired
	JobDivaConnectivity		jobDivaConnectivity;
	//
	
	public String request(String req, Long teamid, String rfqid, String refNo, String website, String apiKey) {
		//
		//JobDivaSession jobDivaSession = getJobDivaSession();
		
		int webid = 117;
		
		String[] data = new String[30];
		
		if (req.equals("PostRq")) {
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
		
			String[] elements = {"website","apiKey","JobRefNo","job_title&"+website,"job_type&"+website,"disciplines_idsq&"+website,
					
									"client_id&"+website,"job_reference&"+website,"job_duration&"+website,"job_startdate&"+website,"job_description&"+website,
									"job_location&"+website,"postcode&"+website,"salary_free&"+website,"salary_currency&"+website,"contact_name&"+website,"contact_email&"+website,
									"contact_telephone&"+website,"application_email&"+website,"days_to_advertise&"+website,"job_function"+website,"branch"+website,"languages_refsq&"+website,
									"job_enddate&"+website,"salary_low&"+website,"salary_high&"+website,"salary_per&"+website,"video_url&"+website,"hot&"+website,"extra_categorisation&"+website}; //30
		
			for (int i = 3; i < 6; i++) {
				if (isInList(list,elements[i]) != -1) data[i] = (String) list.get(isInList(list,elements[i])).get(1); 
				else return "{\"response\":{\"status\":\"Fail\",\"reason\":\"Required Field(s) Missing in Database\"}}";
			}
			for (int i = 6; i < 29; i++) {
				if (i==10) data[i] = (desc != null && desc.size() > 0)?desc.get(0):"";
				//if (i==10) data[i] = "";
				else if (isInList(list,elements[i]) != -1) data[i] = (String) list.get(isInList(list,elements[i])).get(1);
				else data[i] = "";
			}
			
			// data[5] example: convert (_23_56_44_) to (23,56,44)
			if (data[5].length() >=3) {
				data[5] = data[5].substring(1, data[5].length()-1);
				data[5] = data[5].replace('_', ',');
			}
			
			if (data[22].length() >=3) {
				data[22] = data[22].substring(1, data[22].length()-1);
				data[22] = data[22].replace('_', ',');
			}
			
			data[10] = data[10].replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
			data[10] = data[10].replaceAll("%C2%92","%27").replaceAll("%C2%93","%22").replaceAll("%C2%94","%22"); 
			try {
				data[10] = URLDecoder.decode(data[10], "UTF-8");
			} catch (Exception e) {
				data[10] = "";
			}
			data[10] = data[10].replace("\"", "\\\"");
			data[10] = data[10].replace("\r\n", "");
			data[10] = data[10].replace("\n", "");
			
		}
		
		data[0] = website;
		data[1] = apiKey;
		data[2] = rfqid;
		data[7] = refNo;
		
		
		String rqResponse = "";
		
		try {
			rqResponse = sendPost(website, apiKey, createMessage(data, req), req);
		} catch (IOException e) {
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
            case "PostRq":
            	xmlMessage += "\"api_key\":\"" + data[1] + "\",";
            	xmlMessage += "\"job\":{";
            	
                xmlMessage += "\"job_title\":\"" + data[3] + "\",";
                xmlMessage += "\"job_type\":\"" + data[4] + "\",";
                xmlMessage += "\"discipline\":\"" + data[5] + "\"";
                
                if (data[7] != "") xmlMessage += ",\"client_id\":\"" + data[6] + "\""; 
                else xmlMessage += ",\"client_id\":\"\""; 
                
                if (data[7] != "") xmlMessage += ",\"job_reference\":\"" + data[7] + "\"";
                if (data[2] != "") xmlMessage += ",\"remote_id\":\"" + data[2] + "\"";
                if (data[8] != "") xmlMessage += ",\"job_duration\":\"" + data[8] + "\"";
                if (data[9] != "") xmlMessage += ",\"job_startdate\":\"" + data[9] + "\"";
                if (data[10] != "") xmlMessage += ",\"job_description\":\"" + data[10] + "\"";
                if (data[11] != "") xmlMessage += ",\"job_location\":\"" + data[11] + "\"";
                if (data[12] != "") xmlMessage += ",\"postcode\":\"" + data[12] + "\"";
                if (data[13] != "") xmlMessage += ",\"salary_free\":\"" + data[13] + "\"";
                if (data[14] != "") xmlMessage += ",\"salary_currency\":\"" + data[14] + "\"";
                if (data[15] != "") xmlMessage += ",\"contact_name\":\"" + data[15] + "\"";
                if (data[16] != "") xmlMessage += ",\"contact_email\":\"" + data[16] + "\"";
                if (data[17] != "") xmlMessage += ",\"contact_telephone\":\"" + data[17] + "\"";
                if (data[18] != "") xmlMessage += ",\"application_email\":\"" + data[18] + "\"";
                if (data[19] != "") xmlMessage += ",\"days_to_advertise\":\"" + data[19] + "\"";
                if (data[20] != "") xmlMessage += ",\"job_function\":\"" + data[20] + "\"";
                if (data[21] != "") xmlMessage += ",\"branch\":\"" + data[21] + "\"";
                if (data[22] != "") xmlMessage += ",\"language\":\"" + data[22] + "\"";
                if (data[23] != "") xmlMessage += ",\"job_enddate\":\"" + data[23] + "\"";
                if (data[24] != "") xmlMessage += ",\"salary_low\":\"" + data[24] + "\"";
                if (data[25] != "") xmlMessage += ",\"salary_high\":\"" + data[25] + "\"";
                if (data[26] != "") xmlMessage += ",\"salary_per\":\"" + data[26] + "\"";
                if (data[27] != "") xmlMessage += ",\"video_url\":\"" + data[27] + "\"";
                if (data[28] != "") xmlMessage += ",\"hot\":\"" + data[28] + "\"";
                if (data[29] != "") xmlMessage += ",\"extra_categorisation\":\"" + data[29] + "\"";
                
                xmlMessage += "}}";
                
                break;
                
            case "ExpireRq":    
            case "DeleteRq":
            	xmlMessage += "\"job\":{";
            	
            	xmlMessage += "\"api_key\":\"" + data[1] + "\",";
            	xmlMessage += "\"job_reference\":\"" + data[7] + "\",";
            	xmlMessage += "\"remote_id\":\"" + data[2] + "\"";
            	
            	xmlMessage += "}}";
                
                break;
                
            default:
                xmlMessage = "";
        }
         
        return xmlMessage;
    }
	
	private String sendPost(String website, String api_key, String xmlInput, String action) throws IOException {

        //Code to make a web service HTTP request
        String responseString = "";
        String outputString = "";
        String wsURL = website;
        
        if (action.equals("PostRq")) wsURL += "/api/v1/jobs.json?api_key=" + api_key;
        else if (action.equals("ExpireRq")) wsURL += "/api/v1/jobs/expire.json?api_key=" + api_key;
        else if (action.equals("DeleteRq")) wsURL += "/api/v1/jobs/delete.json?api_key=" + api_key;
        else {return "ERROR(sendPost()): Request not valid.";}
        
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
	
	
	//////////////////////////
	
	public String getLists(String website, String apiKey) {
		
		String reqResponse = "";
		
		try {
			reqResponse = getAttr(website, apiKey);
		} catch (Exception e) {
			reqResponse = "{\"response\":{\"status\":\"Exception\",\"reason\":\"" + e.getMessage() + "\"}}";
		}
		
		return reqResponse;
	}
	
	private String getAttr(String website, String apiKey) throws IOException {

        //Code to make a web service HTTP request
        String responseString = "";
        String outputString = "";
        String wsURL = "";
        
        wsURL = website + "/api/v1/available_job_attributes.json?api_key=" + apiKey;
        
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
            outputString = outputString + responseString;
        }
        
        return outputString;
        
    }   

}
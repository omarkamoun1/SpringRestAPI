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
import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.setup.JobDivaConnectivity;
import com.jobdiva.api.config.AppProperties;
import com.jobdiva.api.model.JsukResponse;


@Component
public class JobServeUKDao {
	
	//
	@Autowired
	JobDivaConnectivity		jobDivaConnectivity;
	//
	@Autowired
	AppProperties		appProperties;
	//
	public JsukResponse request(String req, Long teamid, String rfqid, String username, String pass) {
		//
		//JobDivaSession jobDivaSession = getJobDivaSession();
		
		int webid = 76;
		
		String[] data = new String[25];
		String response = "";
		String status = "Success";
		List<String> responseAr = new ArrayList<String>();
		
		if (req.equals("PostAdvert") || req.equals("AmendAdvert")) {
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
		
			String[] elements = {"username","password","rfqid","marketID","contact","position","htmlskills","location","countryCode","reference","type","consultantEmail","applyOnlineEmail",
							 		"telephone","postZipCode","startDate","duration","rate","categories","securityClearance","nbrOfReAds","consultantFirstName","consultantLastName","marketID2","marketID3"};
		
			for (int i = 3; i < 13; i++) {
				if (i==6) data[i] = (desc != null && desc.size() > 0)?desc.get(0):"";
				else if (isInList(list,elements[i]) != -1) data[i] = (String) list.get(isInList(list,elements[i])).get(1); 
				else {
					List<String> failList = new ArrayList<String>();
					failList.add("Required Field(s) Missing in Database");
					return new JsukResponse("Fail", failList);
				}
			}
			for (int i = 13; i < 25; i++) {
				if (isInList(list,elements[i]) != -1) data[i] = (String) list.get(isInList(list,elements[i])).get(1);
				else data[i] = "";
			}
		}
		
		
		data[0] = username;
		data[1] = pass;
		data[2] = rfqid;
		
		String soapResponse = "";
		
		try {
			soapResponse = sendJsPost(createJsMessage(data, req), req);
		} catch (IOException e) {
			List<String> errorList = new ArrayList<String>();
			errorList.add(e.getMessage());
			return new JsukResponse("Exception", errorList);
		}
		
		switch (req) {
			case "CreditsQuery":
				if (!isTag(soapResponse, "CreditsRemaining")) {
					status = "Query Failure";
					response = "Account credentials are invalid";
				} else response = getTagValue(soapResponse, "CreditsRemaining").get(0);
				break;
				
			case "PostAdvert":
				if (!isTag(soapResponse, "JobID")) {
					status = "Post Failure";
					responseAr = getTagValue(soapResponse, "Message");
				} else response = "Successfully posted onto JobserveUK!";
				break;
				
			case "AmendAdvert":
				if (!isTag(soapResponse, "JobID")) {
					status = "Amend Failure";
					responseAr = getTagValue(soapResponse, "Message");
				} else response = "Successfully Amended onto JobserveUK!";
				break;
				
			case "DeleteAdvert":
				if (!isTag(soapResponse, "JobID")) {
					status = "Delete Failure";
					responseAr = getTagValue(soapResponse, "Message");
				} else response = "Successfully deleted from JobserveUK!";
				break;
				
			case "ReAdvertise":
				if (!isTag(soapResponse, "JobID")) {
					status = "ReAdvertise Failure";
					responseAr = getTagValue(soapResponse, "Message");
				} else response = "Successfully re-advertised on JobserveUK!";
				break;
		}
		
		if(status.equals("Success") || status.equals("Query Failure")) {
			List<String> messageList = new ArrayList<String>();
			messageList.add(response);
			return new JsukResponse(status, messageList);
		} else return new JsukResponse(status, responseAr);
		
		//
	}
	
	
	private int isInList(List<List<Object>> list, String element) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get(0).equals(element)) return i;
		}
		return -1;
	}
	
	private String createJsMessage(String[] data, String action) {
        
        String xmlMessage = "<SOAP:Envelope xmlns:SOAP=\"urn:schemas-xmlsoap-org:soap.v1\">";
        xmlMessage += "<SOAP:Header><AccountNumber>" + data[0] + "</AccountNumber><AuthorisationCode>" + data[1] + "</AuthorisationCode>";
        
        switch (action) {
            case "PostAdvert":
                xmlMessage += "<Source>xs</Source></SOAP:Header>";
                
                xmlMessage += "<SOAP:Body><PostAdvert><Adverts><Advert>";
                xmlMessage += "<MarketID>" + data[3] + "</MarketID>";
                xmlMessage += "<AccountNumber>" + data[0] + "</AccountNumber>";
                xmlMessage += "<Contact>" + data[4] + "</Contact>";
                if (data[13] != "") xmlMessage += "<Telephone>" + data[13] + "</Telephone>"; //Telephone
                xmlMessage += "<Position>" + data[5] + "</Position>";
                xmlMessage += "<HtmlSkills><![CDATA[ " + data[6] + " ]]></HtmlSkills>";
                xmlMessage += "<Location>" + data[7] + "</Location>";
                xmlMessage += "<CountryCode>" + data[8] + "</CountryCode>";
                if (data[14] != "") xmlMessage += "<PostZipCode>" + data[14] + "</PostZipCode>"; //PostZipCode
                xmlMessage += "<Reference>" + data[9] + "</Reference>";
                if (data[15] != "") xmlMessage += "<StartDate>" + data[15] + "</StartDate>"; //StartDate
                if (data[16] != "") xmlMessage += "<Duration>" + data[16] + "</Duration>"; //Duration 
                if (data[17] != "") xmlMessage += "<Rate>" + data[17] + "</Rate>"; //Rate
                xmlMessage += "<Type>" + data[10] + "</Type>";
                if (data[18] != "") xmlMessage += "<Categories>" + data[18] + "</Categories>"; //Categories
                xmlMessage += "<FastTrack><ApplyOnlineEmail>" + data[12] + "</ApplyOnlineEmail></FastTrack>"; //FastTrack -> ApplyOnlineEmail
                
                xmlMessage += "<Configuration>"; //Clearance + NumberOfReAds
                if (data[19] != "") xmlMessage += "<SecurityClearance>" + data[19] + "</SecurityClearance>";
                xmlMessage += "<CustomJobID>" + data[2] + "</CustomJobID>";
                if (data[20] != "") xmlMessage += "<NumberOfReAds>" + data[20] + "</NumberOfReAds>";
                if (data[23] != "") xmlMessage += "<SecondaryMarketID>" + data[23] + "</SecondaryMarketID>";
                if (data[24] != "") xmlMessage += "<TertiaryMarketID>" + data[24] + "</TertiaryMarketID>";
                xmlMessage += "</Configuration>";
                
                xmlMessage += "<Consultant>"; //ConsultantFirstName ConsultantLastName
                xmlMessage += "<ConsultantEmail>" + data[11] + "</ConsultantEmail>";
                if (data[21] != "") xmlMessage += "<ConsultantFirstName>" + data[21] + "</ConsultantFirstName>";
                if (data[22] != "") xmlMessage += "<ConsultantLastName>" + data[22] + "</ConsultantLastName>";
                xmlMessage += "</Consultant>";
                
                xmlMessage += "</Advert></Adverts></PostAdvert></SOAP:Body></SOAP:Envelope>";
                
                break;
                
            case "ReAdvertise":
                xmlMessage += "</SOAP:Header>";
                xmlMessage += "<SOAP:Body><ReAdvertise><Adverts><Advert>";
                xmlMessage += "<CustomJobID>" + data[2] + "</CustomJobID>";
                xmlMessage += "</Advert></Adverts></ReAdvertise></SOAP:Body></SOAP:Envelope>";
                
                break;
                
            case "DeleteAdvert":
                xmlMessage += "</SOAP:Header>";
                xmlMessage += "<SOAP:Body><DeleteAdvert><Adverts><Advert>";
                xmlMessage += "<CustomJobID>" + data[2] + "</CustomJobID>";
                xmlMessage += "</Advert></Adverts></DeleteAdvert></SOAP:Body></SOAP:Envelope>";
                
                break;
                
            case "AmendAdvert":
                xmlMessage += "<Market>" + data[3] + "</Market></SOAP:Header>";
                
                xmlMessage += "<SOAP:Body><AmendAdvert><Amendments><Amendment>";
                xmlMessage += "<CustomJobID>" + data[2] + "</CustomJobID>";
                xmlMessage += "<AccountNumber>" + data[0] + "</AccountNumber>";
                
                xmlMessage += "<Contact>" + data[4] + "</Contact>";
                if (data[13] != "") xmlMessage += "<Telephone>" + data[13] + "</Telephone>"; //Telephone
                xmlMessage += "<Position>" + data[5] + "</Position>";
                xmlMessage += "<HtmlSkills><![CDATA[ " + data[6] + " ]]></HtmlSkills>";
                xmlMessage += "<Location>" + data[7] + "</Location>";
                if (data[15] != "") xmlMessage += "<StartDate>" + data[15] + "</StartDate>"; //StartDate
                if (data[16] != "") xmlMessage += "<Duration>" + data[16] + "</Duration>"; //Duration 
                if (data[17] != "") xmlMessage += "<Rate>" + data[17] + "</Rate>"; //Rate
                xmlMessage += "<Type>" + data[10] + "</Type>";
                if (data[19] != "") xmlMessage += "<SecurityClearance>" + data[19] + "</SecurityClearance>";
                if (data[20] != "") xmlMessage += "<NumberOfReAds>" + data[20] + "</NumberOfReAds>";
                
                xmlMessage += "</Amendment></Amendments></AmendAdvert></SOAP:Body></SOAP:Envelope>";
                
                break;
                
            default: //CreditsQuery
                xmlMessage += "</SOAP:Header></SOAP:Envelope>";
        }
         
        return xmlMessage;
    }
    
    private String sendJsPost(String xmlInput, String action) throws IOException {

        //Code to make a webservice HTTP request
        String responseString = "";
        String outputString = "";
        String wsURL = appProperties.getJobserveukUrl();
        
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
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestProperty("SOAPMethodName", action);
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
    	return (((message.split(Pattern.quote("<"+tag+">"), -1).length) - 1)==0)?false:true;
    }
    
    
    private List<String> getTagValue(String message, String tag) {
        
    	int rep = (message.split(Pattern.quote("<"+tag+">"), -1).length) - 1;
        
        List<String> results = new ArrayList<String>();
        int repIndex = 0;
        int searchIndex = 0;

        while (true) {
            int indexB = message.indexOf(tag, searchIndex);
            int indexBFixed = indexB;
            int indexE = 0;

            if (indexB == -1) {
                return results;
            } else {
                if (message.charAt(indexB - 1) == '<' || message.charAt(indexB - 1) == ':') {
                    while (message.charAt(indexB) != '>') {
                        if (indexB - indexBFixed >= tag.length()) {
                            break;
                        }
                        indexB++;
                    }

                    if (message.charAt(indexB) == '>') {
                        indexE = ++indexB;
                        while (message.charAt(indexE) != '<') {
                            indexE++;
                        }
                        results.add(message.substring(indexB, indexE));
                        repIndex++;
                        if (repIndex == rep) return results;
                    }

                }
                searchIndex = indexB + 1;
            }
        }

        // Null: No tag found
        // "" : Empty Value
    } 
}
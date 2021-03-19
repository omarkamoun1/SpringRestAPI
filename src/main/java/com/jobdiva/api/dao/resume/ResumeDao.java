package com.jobdiva.api.dao.resume;

import static java.net.URLDecoder.decode;

import java.rmi.Naming;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.axelon.candidate.CandidateData;
import com.axelon.candidate.DocumentObjectResume;
import com.axelon.filedistributor.FileDistributor_Stub;
import com.axelon.oc4j.ServletRequestData;
import com.axelon.robot.Robot;
import com.axelon.shared.Application;
import com.axelon.shared.Zipper;
import com.axelon.wordserver.FileBundle;
import com.axelon.wordserver.RMIWordServer_Stub;
import com.jobdiva.api.config.AppProperties;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.servlet.ServletTransporter;

@Component
public class ResumeDao extends AbstractJobDivaDao {
	
	protected String getCandidateAttachmentServlet() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/candidate/servlet/CandidateAttachmentServlet";
	}
	
	public Long uploadResume(JobDivaSession jobDivaSession, String filename, byte[] filecontent, String textfile, Long candidateid, Integer resumesource, Long recruiterId, Date resumeDate) throws Exception {
		//
		// data checking
		StringBuffer message = new StringBuffer();
		if (filename.trim().length() == 0)
			message.append("Error: filename is empty. ");
		//
		if (textfile != null && textfile.trim().length() == 0)
			textfile = null;
		//
		// if (candidateid == null)
		// message.append("Error: Invalid candidate ID" + candidateid + "). ");
		//
		if (resumesource == null)
			message.append("Error: Invalid resume source (" + resumesource + "). ");
		//
		if (textfile == null && filecontent == null)
			message.append("Error: No resume found. ");
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		if (textfile != null) {
			textfile = decode(textfile, "UTF-8");
		}
		try {
			DocumentObjectResume doc = new DocumentObjectResume(1, "", "Resume Uploaded by JobDivaAPI", null);
			doc.setDateCreated(System.currentTimeMillis());
			if (resumesource != null && resumesource > 0)
				doc.ResumeSource = String.valueOf(resumesource);
			else
				doc.ResumeSource = "998";
			doc.ResumeSourceFlag = 3;
			boolean converterNeeded = true;
			FileBundle fb = new FileBundle();
			// file sent via filecontent variable, base64 binary
			if (filecontent != null) {
				if (filename.toLowerCase().endsWith(".rtf") || filename.toLowerCase().endsWith(".pdf") || filename.toLowerCase().endsWith(".doc") || filename.toLowerCase().endsWith(".docx")) {
					// printLog("resume upload rtf/pdf/doc/docx file");
					Object[] sendObj = new Object[3];
					sendObj[0] = new Integer(0); // extract text content from
													// resume
					sendObj[1] = filecontent;
					sendObj[2] = filename.substring(filename.lastIndexOf(".") + 1);
					Object[] retObj = new Object[2];
					try {
						// printLog("resume uploaded to converter");
						ServletRequestData srd = new ServletRequestData(0, null, sendObj);
						retObj = (Object[]) ServletTransporter.callServlet(appProperties.getLoadBalanceServletLocation() + "/candidate/servlet/CandidateResumeConvertServlet", srd);
						// printLog("resume converter done");
						converterNeeded = ((Boolean) retObj[0]).booleanValue();
						fb.txtFile = (String) retObj[1];
					} catch (Exception e) {
						message.append("Resume upload to converter failed. ");
						throw e;
					}
				}
				if (converterNeeded) {
					String tempname = new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date()) + filename;
					try {
						FileDistributor_Stub fd = (FileDistributor_Stub) Naming.lookup("//" + Application.getFileDistributorLocation() + "/FileDistributor");
						String WSURL = fd.getAvailableWordServerAddress();
						RMIWordServer_Stub converter = (RMIWordServer_Stub) Naming.lookup(WSURL);
						fb = converter.convert(filecontent, tempname);
					} catch (Exception e) {
						message.append("Convert resume failed. ");
						throw e;
					}
				}
				doc.OriginalFilename = filename;
				doc.ZIP_OriginalDocument = Zipper.zipIt(filecontent);
				doc.PlainText = fb.txtFile;
			} else if (textfile != null && textfile.length() > 0) {
				if (filename.toLowerCase().endsWith(".txt"))
					doc.OriginalFilename = filename;
				else
					doc.OriginalFilename = "Resume.txt";
				doc.ZIP_OriginalDocument = Zipper.zipIt(textfile);
				doc.PlainText = textfile;
			}
			String[] args = new String[4];
			args[0] = doc.PlainText;
			//
			Long longResumeDate = resumeDate != null ? resumeDate.getTime() : System.currentTimeMillis();
			//
			// Robot.PARSE_ALL // Robot.PARSE_ALL_EU
			args[1] = String.valueOf(longResumeDate); //
			args[2] = (appProperties.getApiServerEnvironment().equalsIgnoreCase(AppProperties.UK_ENVIRONEMNT) ? Robot.PARSE_ALL_EU : Robot.PARSE_ALL) + ""; // "16511";
																																							// //
																																							// Robot.PARSE_ALL
			args[3] = String.valueOf(jobDivaSession.getTeamId());
			//
			ServletRequestData srd = new ServletRequestData(0, null, args);
			//
			Object retObj = ServletTransporter.callServlet(appProperties.getLoadBalanceServletLocation() + "/candidate/servlet/CandidateRobot", srd);
			// printLog("CandidateRobot returned datatype: " +
			// retObj.getClass().getName());
			if (retObj instanceof Exception) {
				message.append("Parse resume failed. ");
				throw (Exception) retObj;
			}
			CandidateData canData = (CandidateData) retObj;
			canData.teamid = jobDivaSession.getTeamId();
			canData.password = "password";
			doc.rfqtitles = canData.resume.rfqtitles;
			canData.resume = doc;
			if (candidateid != null && candidateid > 0) {
				canData.setID(candidateid);
				canData.action_code = 50;
			} else {
				canData.action_code = -1;
			}
			//
			//
			if (resumeDate != null) {
				canData.resume.setDateCreated(resumeDate.getTime());
			}
			//
			if (recruiterId != null)
				canData.resume.recruiterid = recruiterId;
			// ServletTransporter servlet1 = new
			// ServletTransporter("http://10.8.126.12:8000/candidate/servlet/CandidateUploadFull");
			srd = new ServletRequestData(0, null, canData);
			Object retObj1 = ServletTransporter.callServlet(appProperties.getLoadBalanceServletLocation() + "/candidate/servlet/CandidateUploadFull", srd);
			if (retObj1 instanceof Exception) {
				message.append("Upload resume failed. ");
				throw (Exception) retObj1;
			}
			CandidateData canData1 = (CandidateData) retObj1;
			return canData1.getID();
			// res.setGlobalid( canData1.resume.global_id);
		} catch (Exception e) {
			throw new Exception("Error: " + message.toString() + e.getMessage());
		}
		//
		//
		//
		//
		//
		//
		//
		//
		//
		//
		//
		//
		/*
		 * RFQData rfq = new RFQData(candidateid,
		 * jobDivaSession.getRecruiterId(), 0, 0); rfq.teamid =
		 * jobDivaSession.getTeamId(); rfq.attachments = new rfqAttachment[1];
		 * rfqAttachment att = new rfqAttachment(); att.id = candidateid;
		 * att.filename = filename; att.bytes = filecontent;
		 * java.text.SimpleDateFormat dfm = new
		 * java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); att.dtcreated =
		 * dfm.format(new Date().getTime()); att.description = "";// Not
		 * specified From API att.setRemark(""); // Not specified From API
		 * att.attachment_type = 0; // Not specified From API rfq.LC_type = 0;
		 * // from other rfq.LC_id = 0; // 0 for non-LIC attachments
		 * rfq.actioncode = 1; // insert rfq.attachments[0] = att; //
		 * ServletRequestData servletRequestData = new ServletRequestData(0L,
		 * jobDivaSession.getEnvironment() + "", rfq); Object retObj =
		 * ServletTransporter.callServlet(getCandidateAttachmentServlet(),
		 * servletRequestData); if (retObj instanceof Exception) {
		 * message.append("Error occurs when saving uploaded attachment.");
		 * throw (Exception) retObj; } Long attachmentId = (Long) retObj; //
		 * return attachmentId;
		 */
	}
}

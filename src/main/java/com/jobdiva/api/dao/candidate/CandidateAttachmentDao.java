package com.jobdiva.api.dao.candidate;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.axelon.oc4j.ServletRequestData;
import com.axelon.rfq.RFQData;
import com.axelon.rfq.rfqAttachment;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.servlet.ServletTransporter;

@Component
public class CandidateAttachmentDao extends AbstractJobDivaDao {
	
	private String getCandidateAttachmentServlet() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/candidate/servlet/CandidateAttachmentServlet";
	}
	
	public Long uploadCandidateAttachment(JobDivaSession jobDivaSession, Long candidateid, String name, String filename, byte[] filecontent, Integer attachmenttype, String description) throws Exception {
		//
		// data checking
		StringBuffer message = new StringBuffer();
		if (filename.trim().length() == 0)
			message.append("Error: filename is empty. ");
		//
		if (candidateid == null)
			message.append("Error: Invalid candidate ID" + candidateid + "). ");
		//
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		RFQData rfq = new RFQData(candidateid, jobDivaSession.getRecruiterId(), 0, 0);
		rfq.teamid = jobDivaSession.getTeamId();
		rfq.attachments = new rfqAttachment[1];
		rfqAttachment att = new rfqAttachment();
		att.id = candidateid;
		att.filename = filename;
		att.bytes = filecontent;
		java.text.SimpleDateFormat dfm = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		att.dtcreated = dfm.format(new Date().getTime());
		att.description = name;
		att.setRemark(description != null ? description.trim() : "");
		att.attachment_type = attachmenttype;
		rfq.LC_type = 0; // from other
		rfq.LC_id = 0; // 0 for non-LIC attachments
		rfq.actioncode = 1; // insert
		rfq.attachments[0] = att;
		//
		ServletRequestData srd = new ServletRequestData(0, null, rfq);
		Object retObj = ServletTransporter.callServlet(getCandidateAttachmentServlet(), srd);
		if (retObj instanceof Exception) {
			message.append("Error occurs when saving uploaded attachment.");
			throw (Exception) retObj;
		}
		Long attachmentId = (Long) retObj;
		//
		return attachmentId;
	}
}

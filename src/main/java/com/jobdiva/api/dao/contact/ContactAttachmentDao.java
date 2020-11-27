package com.jobdiva.api.dao.contact;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.axelon.oc4j.ServletRequestData;
import com.axelon.rfq.rfqAttachment;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.servlet.ServletTransporter;

@Component
public class ContactAttachmentDao extends AbstractJobDivaDao {
	
	private String getContactAttachmentServlet() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/recruiter/servlet/ContactAttachmentInsertServlet";
	}
	
	public Long uploadContactAttachment(JobDivaSession jobDivaSession, Long contactId, String name, String filename, byte[] filecontent, Integer attachmenttype, String description) throws Exception {
		//
		// data checking
		StringBuffer message = new StringBuffer();
		if (filename.trim().length() == 0)
			message.append("Error: filename is empty. ");
		//
		if (contactId == null)
			message.append("Error: Invalid Contact ID" + contactId + "). ");
		//
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		rfqAttachment att = new rfqAttachment();
		att.id = contactId;
		att.linked_id = 0;
		att.filename = filename;
		att.bytes = filecontent;
		java.text.SimpleDateFormat dfm = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		att.dtcreated = dfm.format(new Date().getTime());
		att.description = name;
		att.setRemark(description != null ? description.trim() : "");
		att.attachment_type = attachmenttype;
		//
		//
		ServletRequestData srd = new ServletRequestData(0, null, att);
		//
		Object retObj = ServletTransporter.callServlet(getContactAttachmentServlet(), srd);
		if (retObj instanceof Exception) {
			message.append("Error occurs when saving uploaded attachment.");
			throw (Exception) retObj;
		}
		Long attachmentId = (Long) retObj;
		//
		return attachmentId;
	}
}

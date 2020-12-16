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
	
	public Long uploadContactAttachment(JobDivaSession jobDivaSession, Long contactId, String documentName, String fileName, byte[] fileContent, String urlLink, String designedForm, Integer attachmentType, String internalDescription,
			Date expirationDate, Boolean isOnboarding, Boolean isMandatory, Boolean requireReturn, Boolean isReadOnly, Integer sendTo, Boolean isMedicalDoc, String portalInstruction) throws Exception {
		//
		//
		StringBuffer message = new StringBuffer();
		if (contactId < 1)
			message.append("Error: invalid contact ID(" + contactId + "). ");
		//
		final int BINARY_FILE = 0, URL_LINK = 1, DESIGNED_FORM = 2; // Document
																	// Type
		//
		int documentType = BINARY_FILE;
		//
		if (!urlLink.isEmpty()) {
			documentType = URL_LINK;
			if (documentName.isEmpty())
				message.append("Error: documentname is empty. ");
			if (urlLink.isEmpty() || !urlLink.contains("."))
				message.append("Error: Invalid urlLink. ");
		} else if (!designedForm.isEmpty()) {
			documentType = DESIGNED_FORM;
			documentName = "";
			if (designedForm.isEmpty())
				message.append("Error: designedform is empty. ");
		} else {
			if (documentName.isEmpty())
				message.append("Error: documentname is empty. ");
			if (fileName.isEmpty() || !fileName.contains("."))
				message.append("Error: Invalid filename. ");
		}
		//
		//
		//
		if (attachmentType != null && attachmentType < 0)
			message.append("Error: invalid attachment type(" + attachmentType + "). ");
		//
		if (isOnboarding != null && !isOnboarding) {
			isMandatory = false;
			requireReturn = false;
			isReadOnly = false;
			sendTo = 0;
			isMedicalDoc = false;
		}
		//
		if (sendTo != null && sendTo != 0 && sendTo != 1 && sendTo != 2)
			message.append("Error: invalid sendto(" + sendTo + "). Valid value: [0:Send to Candidate, 1:Send to Supplier, 2:For Internal Use Only]");
		//
		if (message.length() > 0) {
			String exception = "Parameter Check Failed \r\n";
			exception += message.toString();
			throw new Exception(exception);
		}
		//
		//
		try {
			rfqAttachment att = new rfqAttachment();
			att.LC_id = jobDivaSession.getTeamId(); // placeholder for teamid
			att.recruiterid = jobDivaSession.getRecruiterId();
			att.id = contactId;
			att.doctype = documentType;
			att.description = documentName;
			//
			if (documentType == BINARY_FILE) {
				att.filename = fileName;
				att.bytes = fileContent;
			} else if (documentType == URL_LINK) {
				att.filename = urlLink;
			} else if (documentType == DESIGNED_FORM) {
				att.filename = designedForm;
			}
			//
			att.attachment_type = attachmentType != null ? attachmentType : 0;
			att.setRemark(internalDescription);
			att.dateexpire = expirationDate != null ? expirationDate.getTime() : 0;
			att.linked_for = isOnboarding != null && isOnboarding ? 1 : 0;
			att.mandatory = isMandatory != null && isMandatory ? 1 : 0;
			att.require_return = requireReturn != null && requireReturn ? 1 : 0;
			att.readonly = isReadOnly != null && isReadOnly ? 1 : 0;
			att.send_to = sendTo != null ? sendTo : 0;
			att.ismedical = isMedicalDoc != null && isMedicalDoc ? 1 : 0;
			att.instruction = portalInstruction;
			att.linked_id = 0; // Insert Document
			att.show_des = 0;
			att.setSource(0);
			//
			//
			ServletRequestData srd = new ServletRequestData(0, null, att);
			Object retObj = ServletTransporter.callServlet(getContactAttachmentServlet(), srd);
			//
			//
			if (retObj instanceof Exception) {
				message.append("Error occurs when uploading attachment.");
				throw (Exception) retObj;
			}
			//
			//
			Long attachmentId = (Long) retObj;
			switch (attachmentId.intValue()) {
				case -2:
					message.append("This file has already been uploaded as an on-boarding document.");
					break;
				case -4:
					message.append("'Read Only' documents should not contain web form tags.");
					break;
				case -100:
					message.append("This PDF's compatibility requirements are set at 'Acrobat 9.0 or higher' under its Security Settings. " //
							+ "You can proceed using this document as is, but if you require its fields to auto-populate with special tags, " //
							+ "adjust this setting to 'Acrobat 7.0 or later' and upload again.");
					break;
				case -110:
					message.append("This document is password secured. " //
							+ "You can proceed using this document as is, but if you require its fields to auto-populate with special tags, " //
							+ "please remove this security and upload again.");
					break;
				case -120:
					message.append("This document is password secured. " //
							+ "You can proceed using this document as is, but if you require its fields to auto-populate with special tags, " //
							+ "please remove this security and upload again.");
					break;
				case -3:
					message.append("This document contains an unusual formatting (e.g. a font script in a foreign language). " //
							+ "You can proceed using this document as is, but if you require its fields to auto-populate with special tags, " //
							+ "please remove these unusual formats and upload again.");
					break;
				default:
					break;
			}
			//
			//
			if (message.length() > 0)
				throw new Exception("Error uploading attachment. ");
			//
			//
			return attachmentId;
			//
		} catch (Exception e) {
			String exceptrion = "Upload Failed \r\n" + "Error: " + message.toString();
			throw new Exception(exceptrion);
		}
	}
}

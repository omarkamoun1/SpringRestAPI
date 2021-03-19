package com.jobdiva.api.dao.contact;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class ContactAttachmentDao extends AbstractJobDivaDao {
	
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
		if (urlLink != null && !urlLink.isEmpty()) {
			documentType = URL_LINK;
			if (documentName.isEmpty())
				message.append("Error: documentname is empty. ");
			if (urlLink.isEmpty() || !urlLink.contains("."))
				message.append("Error: Invalid urlLink. ");
		} else if (designedForm != null && !designedForm.isEmpty()) {
			documentType = DESIGNED_FORM;
			documentName = "";
			if (designedForm.isEmpty())
				message.append("Error: designedform is empty. ");
		} else {
			if (documentName == null || documentName.isEmpty())
				message.append("Error: documentname is empty. ");
			//
			if (fileName == null || fileName.isEmpty() || !fileName.contains("."))
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
		//
		//
		JdbcTemplate jdbctemplate = getJdbcTemplate();
		//
		//
		byte[] bytes = null;
		int linked_for = isOnboarding != null && isOnboarding ? 1 : 0;
		String filename = "";
		if (documentType == BINARY_FILE) {
			filename = fileName;
			bytes = fileContent;
		} else if (documentType == URL_LINK) {
			filename = urlLink;
		} else if (documentType == DESIGNED_FORM) {
			filename = designedForm;
		}
		String localDocumentName = documentName;
		String localFileName = filename;
		Boolean localrequireReturn = requireReturn;
		Boolean localisMandatory = isMandatory;
		Integer localsendTo = sendTo;
		Boolean localisReadOnly = isReadOnly;
		Boolean localisMedicalDoc = isMedicalDoc;
		Integer localdocumentType = documentType;
		try {
			// con=getDbConnection();
			if (linked_for == 1) { // onboarding doc name check
				String sql = " select id from tcontactattachments "//
						+ "where teamid = ? "//
						+ "and contactid = ? "//
						+ "and upper(filename) = ? "//
						+ "and onboarding = 1 " //
						+ "and nvl(deleted,0) = 0 ";
				//
				Object[] params = new Object[] { jobDivaSession.getTeamId(), contactId, filename.toUpperCase() };
				//
				List<Long> list = jdbctemplate.query(sql, params, new RowMapper<Long>() {
					
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong("id");
					}
				});
				//
				if (list != null && list.size() > 0) {
					message.append("This file has already been uploaded as an on-boarding document.");
					throw new Exception("This file has already been uploaded as an on-boarding document.");
				}
				//
			}
			//
			//
			//
			//
			//
			// get the id
			String sql = "select PROFILE_RETAGID.nextval as id from dual";
			List<Long> list = jdbctemplate.query(sql, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong("id");
				}
			});
			//
			long id = list != null && list.size() > 0 ? list.get(0) : -1;
			//
			// insert the row
			String sqlInsert = "insert into tcontactattachments(id,teamid,contactid,description,filename,thefile, onboarding, require_return, mandatory, remark,ESIGNATURE_TYPE, attachment_type, send_to, instruction, readonly, ismedical, dateexpire, recruiterid, doctype) " //
					+ "values(?,?,?,?,?,empty_blob(),?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//
			jdbctemplate.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
					//
					pstmt.setLong(1, id);
					pstmt.setLong(2, jobDivaSession.getTeamId());
					pstmt.setLong(3, contactId);
					pstmt.setString(4, localDocumentName);
					pstmt.setString(5, localFileName);
					pstmt.setInt(6, linked_for);
					pstmt.setInt(7, localrequireReturn != null && localrequireReturn ? 1 : 0);
					pstmt.setInt(8, localisMandatory != null && localisMandatory ? 1 : 0);
					pstmt.setString(9, internalDescription);
					pstmt.setInt(10, 0);
					pstmt.setInt(11, attachmentType != null ? attachmentType : 0);
					pstmt.setInt(12, localsendTo != null ? localsendTo : 0);
					pstmt.setString(13, portalInstruction);
					pstmt.setInt(14, localisReadOnly != null && localisReadOnly ? 1 : 0);
					pstmt.setInt(15, localisMedicalDoc != null && localisMedicalDoc ? 1 : 0);
					pstmt.setTimestamp(16, new Timestamp(expirationDate != null ? expirationDate.getTime() : 0));
					pstmt.setLong(17, jobDivaSession.getRecruiterId());
					pstmt.setInt(18, localdocumentType);
					//
					return pstmt;
				}
			});
			//
			//
			//
			///
			// update to insert the file
			if (documentType == 0 && bytes != null) {
				sql = "select thefile from tcontactattachments where id = ? and teamid=? for update";
				//
				Object[] params = new Object[] { id, jobDivaSession.getTeamId() };
				//
				byte[] localBytes = bytes;
				//
				jdbctemplate.query(sql, params, new RowMapper<Boolean>() {
					
					@SuppressWarnings("deprecation")
					@Override
					public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						java.io.OutputStream outstream = null;
						oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob(1);
						if (blob != null) {
							outstream = blob.getBinaryOutputStream();
							try {
								outstream.write(localBytes);
								outstream.flush();
								outstream.close();
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
							}
						}
						//
						return true;
					}
				});
			}
			return id;
			//
		} catch (Exception e) {
			String exceptrion = "Upload Failed \r\n" + "Error: " + message.toString();
			throw new Exception(exceptrion);
		}
	}
}

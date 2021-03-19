package com.jobdiva.api.dao.contact;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.Contact;

public class ContactRowMapper implements RowMapper<Contact> {
	
	@Override
	public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
		Contact contact = createContact();
		//
		try {
			contact.setId(rs.getLong("id"));
			contact.setTeamId(rs.getLong("TEAMID"));
			contact.setFirstName(rs.getString("firstname"));
			contact.setLastName(rs.getString("lastname"));
			contact.setCompanyName(rs.getString("companyname"));
			contact.setDepartmentName(rs.getString("departmentname"));
			contact.setWorkPhone(rs.getString("workphone"));
			contact.setHomePhone(rs.getString("homephone"));
			contact.setEmail(rs.getString("email"));
			contact.setCompanyId(rs.getLong("companyid"));
			contact.setTitle(rs.getString("title"));
			contact.setCellPhone(rs.getString("cellphone"));
			contact.setContactFax(rs.getString("contactfax"));
			contact.setPhoneTypes(rs.getString("phonetypes"));
			contact.setAddress1(rs.getString("address1"));
			contact.setAddress2(rs.getString("address2"));
			contact.setCity(rs.getString("city"));
			contact.setState(rs.getString("state"));
			contact.setZipcode(rs.getString("zipcode"));
			contact.setCountry(rs.getString("countryid"));
			contact.setAlternateEmail(rs.getString("ALTERNATE_EMAIL"));
			contact.setAssistantName(rs.getString("assistantname"));
			contact.setAssistantEmail(rs.getString("assistantemail"));
			contact.setAssistantPhone(rs.getString("assistantphone"));
			contact.setAssistantPhoneExt(rs.getString("assistantphoneext"));
			contact.setWorkphoneExt(rs.getString("workphoneext"));
			contact.setCellPhoneExt(rs.getString("cellphoneext"));
			contact.setHomePhoneExt(rs.getString("homephoneext"));
			contact.setContactFaxExt(rs.getString("contactfaxext"));
			contact.setReportsto(rs.getLong("reportsto"));
		} catch (Exception e) {
		}
		//
		return contact;
	}
	
	protected Contact createContact() {
		Contact contact = new Contact();
		return contact;
	}
}

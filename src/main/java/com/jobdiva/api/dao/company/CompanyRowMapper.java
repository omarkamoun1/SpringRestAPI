package com.jobdiva.api.dao.company;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.Company;

public class CompanyRowMapper implements RowMapper<Company> {
	
	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company company = new Company();
		//
		company.setId(rs.getLong("id"));
		company.setTeamid(rs.getLong("teamid"));
		company.setName(rs.getString("name"));
		company.setAddress1(rs.getString("address1"));
		company.setAddress2(rs.getString("address2"));
		company.setCity(rs.getString("city"));
		company.setState(rs.getString("state"));
		company.setZipcode(rs.getString("zipcode"));
		company.setCountryid(rs.getString("countryid"));
		company.setPhone(rs.getString("phone"));
		company.setFax(rs.getString("fax"));
		company.setEmail(rs.getString("email"));
		company.setUrl(rs.getString("url"));
		company.setParentCompanyid(rs.getLong("PARENT_COMPANYID"));
		company.setParentCompanyName(rs.getString("PARENT_COMPANY_NAME"));
		//
		//
		return company;
	}
}

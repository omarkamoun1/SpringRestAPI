package com.jobdiva.api.dao.contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.axelon.recruiter.CustomerCompanyData;
import com.axelon.recruiter.CustomerData;
import com.axelon.shared.CacheServer_Stub;
import com.axelon.shared.NamedServer;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.company.SearchCompanyDao;
import com.jobdiva.api.model.Company;
import com.jobdiva.api.model.Contact;
import com.jobdiva.api.model.ContactAddress;
import com.jobdiva.api.model.ContactNote;
import com.jobdiva.api.model.ContactOwner;
import com.jobdiva.api.model.ContactType;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.PhoneType;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.webhook.WebhookContact;

@Component
public class ContactDao extends AbstractJobDivaDao {
	
	@Autowired
	SearchCompanyDao	searchCompanyDao;
	//
	@Autowired
	ContactDao			contactDao;
	//
	@Autowired
	ContactTypeDao		contactTypeDao;
	//
	@Autowired
	ContactAddressDao	contactAddressDao;
	//
	@Autowired
	ContactNoteDao		contactNoteDao;
	//
	@Autowired
	ContactOwnerDao		contactOwnerDao;
	//
	
	private Map<String, Long> getTypeMap(Long teamId) {
		//
		Map<String, Long> typeMap = new Hashtable<String, Long>();
		// pre-defined contact types
		typeMap.put("Hiring Manager", 1L);
		typeMap.put("Reference", 2L);
		typeMap.put("Prospect", 3L);
		typeMap.put("Supplier", 4L);
		typeMap.put("VMO", 5L);
		//
		String sql = "select typeid, typename " //
				+ " from tcustomertype " //
				+ " where teamid = ? " //
				+ " and ( isdeleted is null or isdeleted = 0 ) ";
		Object[] params = new Object[] { teamId };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.query(sql, params, new RowMapper<Void>() {
			
			@Override
			public Void mapRow(ResultSet rs, int rowNum) throws SQLException {
				String typeName = rs.getString("typename");
				Long typeid = rs.getLong("typeid");
				//
				typeMap.put(typeName.toUpperCase(), typeid);
				//
				return null;
			}
		});
		//
		return typeMap;
	}
	
	//
	private void deleteContactUserField(Long contactId, Integer userfieldId, Long teamId) {
		String sqlDelete = "DELETE FROM TCUSTOMER_USERFIELDS Where CUSTOMERID = ? AND USERFIELD_ID = ? AND TEAMID = ? ";
		Object[] params = new Object[] { contactId, userfieldId, teamId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlDelete, params);
	}
	
	public Contact getContact(JobDivaSession jobDivaSession, Long contactId) {
		String sql = "Select distinct " //
				+ " a.id, " //
				+ " a.TEAMID, " //
				+ " a.firstname, " //
				+ " a.lastname, " //
				+ " a.companyname, " //
				+ " a.departmentname, " //
				+ " a.workphone, " //
				+ " a.homephone, " //
				+ " a.email," // //
				+ " a.companyid, " //
				+ " a.title, " //
				+ " a.cellphone, " //
				+ " a.contactfax, " //
				+ " a.phonetypes, " //
				+ " ca.address1, " //
				+ " ca.address2, " //
				+ " ca.city, " //
				+ " ca.state," // //
				+ " ca.zipcode, " //
				+ " ca.countryid, " //
				+ " a.ALTERNATE_EMAIL, " //
				+ " a.assistantname, " //
				+ " a.assistantemail, " //
				+ " a.assistantphone, " //
				+ " a.assistantphoneext," //
				+ " a.workphoneext, " //
				+ " a.cellphoneext, " //
				+ " a.homephoneext, " //
				+ " a.contactfaxext, " //
				+ " a.reportsto " //
				+ " from ";
		//
		sql += " TCUSTOMER a  left join  TCUSTOMERADDRESS ca on a.teamid = ca.teamid and a.id = ca.contactid and ca.DEFAULT_ADDRESS = 1  and ca.deleted = 0 ";
		//
		sql += " where a.teamid = ? AND a.ID = ?  " //
		;
		//
		Object[] params = new Object[] { jobDivaSession.getTeamId(), contactId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Contact> jobContacts = jdbcTemplate.query(sql, params, new ContactRowMapper());
		//
		return jobContacts != null && jobContacts.size() > 0 ? jobContacts.get(0) : null;
	}
	
	//
	@SuppressWarnings("unlikely-arg-type")
	public List<Contact> searchContacts(JobDivaSession jobDivaSession, Long teamId, Long contactId, String firstname, String lastname, String email, String title, //
			String phone, String company, String address, String city, String state, String zipCode, String country, Boolean onlyMyContacts, String[] types, Long ownerIds, //
			String division, Boolean showPrimary, Boolean showInactive, Boolean fromAPI) throws Exception {
		//
		if (isNotEmpty(country)) {
			country = getCountryID(country.trim());
			if (country.equals("not found"))
				throw new Exception("Error: 'country' parameter mapping unfound. \r\n");// jd_comp_add.localCountryidTracker
																						// =
																						// false;
		}
		if (isNotEmpty(phone)) {
			phone = Pattern.compile(NON_DIGITS).matcher(phone.trim()).replaceAll("");
			if (phone.length() > 20)
				throw new Exception("Error: 'phone' should be no more than 20 characters. \r\n");
			else if (phone.length() > 0) {
			} else
				phone = null;
		}
		//
		if (isNotEmpty(division))
			division = division.trim().toUpperCase();
		//
		if (types != null) {
			for (int i = 0; i < types.length; i++)
				if (types[i] != null)
					types[i] = types[i].trim().toUpperCase();
		}
		//
		Map<String, Long> typeMap = getTypeMap(teamId);
		//
		// Build the query
		String select = null;
		StringBuffer tables = new StringBuffer();
		StringBuffer constrains = new StringBuffer();
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		//
		//
		select = "Select distinct " //
				+ " a.id, " //
				+ " a.TEAMID, " //
				+ " a.firstname, " //
				+ " a.lastname, " //
				+ " a.companyname, " //
				+ " a.departmentname, " //
				+ " a.workphone, " //
				+ " a.homephone, " //
				+ " a.email," // //
				+ " a.companyid, " //
				+ " a.title, " //
				+ " a.cellphone, " //
				+ " a.contactfax, " //
				+ " a.phonetypes, " //
				+ " ca.address1, " //
				+ " ca.address2, " //
				+ " ca.city, " //
				+ " ca.state," // //
				+ " ca.zipcode, " //
				+ " ca.countryid, " //
				+ " a.ALTERNATE_EMAIL, " //
				+ " a.assistantname, " //
				+ " a.assistantemail, " //
				+ " a.assistantphone, " //
				+ " a.assistantphoneext," //
				+ " a.workphoneext, " //
				+ " a.cellphoneext, " //
				+ " a.homephoneext, " //
				+ " a.contactfaxext, " //
				+ " a.reportsto " //
				+ " from ";
		//
		tables.append(" TCUSTOMER a, TCUSTOMERADDRESS ca");
		//
		constrains.append(" where a.teamid = :teamid " //
				+ " and a.teamid = ca.teamid "//
				+ " and ca.DEFAULT_ADDRESS = 1 "//
				+ " and ca.deleted = 0 "//
				+ " and a.id = ca.contactid");
		//
		params.put("teamid", teamId);
		//
		//
		if (contactId != null) {
			constrains.append(" and a.id = :contactid");
			params.put("contactid", contactId);
		}
		//
		if (isNotEmpty(firstname)) {
			constrains.append(" and upper(a.firstname) like upper(:firstname)||'%'");
			params.put("firstname", firstname);
		}
		//
		if (isNotEmpty(lastname)) {
			constrains.append(" and upper(a.lastname) like upper(:lastname)||'%'");
			params.put("lastname", lastname);
		}
		//
		if (isNotEmpty(company)) {
			tables.append(", TCUSTOMERCOMPANY c");
			constrains.append(" and a.companyid = c.id and a.teamid = c.teamid");
			//
			List<Long> companyIds = getCompanyIdsByName(teamId, company);
			if (companyIds != null && companyIds.size() > 0) {
				constrains.append(" and instr(c.parentpath, (select parentpath from TCUSTOMERCOMPANY where id in (:compid))) > 0");
				params.put("compid", companyIds);
			} else {
				constrains.append(" and upper(c.name) like upper(:company)||'%'");
				params.put("company", company);
			}
		}
		//
		if (isNotEmpty(email)) {
			constrains.append(" and (upper(a.email) like upper(:email)||'%' or nls_upper(a.ALTERNATE_EMAIL) like :email||'%')");
			params.put("email", email);
		}
		//
		if (isNotEmpty(title)) {
			constrains.append(" and upper(a.title) like upper(:title)||'%'");
			params.put("title", title);
		}
		//
		if (isNotEmpty(address) //
				|| isNotEmpty(city) //
				|| isNotEmpty(state) //
				|| isNotEmpty(zipCode) //
				|| isNotEmpty(country) //
		) {
			//
			constrains.append(" and a.id in (select distinct CONTACTID from TCUSTOMERADDRESS where teamid = :teamid and nvl(deleted,0)=0");
			//
			if (isNotEmpty(address)) {
				constrains.append(" and (NLS_UPPER(address1) like '%'||:address||'%' or NLS_UPPER(address2) like '%'||:address||'%')");// ("
																																		// and
																																		// NLS_UPPER(address1)
																																		// like
																																		// '%'||:address||'%'");
				params.put("address", address.toUpperCase());
			}
			//
			if (isNotEmpty(city)) {
				constrains.append(" and NLS_UPPER(city) like NLS_UPPER(:city)||'%'");
				params.put("city", city);
			}
			//
			if (isNotEmpty(state)) {
				String stateid = lookupState(state, country);
				if (stateid != null) {
					constrains.append(" and NLS_UPPER(state) = NLS_UPPER(:state)");
					params.put("state", stateid);
				} else
					throw new Exception("Error: State (" + state + ") can not be identified.(with countryid(" + country + ")) \r\n");
			}
			//
			if (isNotEmpty(zipCode)) {
				constrains.append(" and NLS_UPPER(zipcode) like NLS_UPPER(:zip)||'%'");
				params.put("zip", zipCode);
			}
			//
			if (isNotEmpty(country)) {
				constrains.append(" and NLS_UPPER(countryid) like NLS_UPPER(:country)||'%'");
				params.put("country", country);
			}
			//
			constrains.append(")");
			//
		}
		if (isNotEmpty(phone)) {
			constrains.append(" and (NLS_UPPER(a.workphone) like :phone||'%' or NLS_UPPER(a.homephone) like :phone||'%' or " //
					+ " NLS_UPPER(a.cellphone) like :phone||'%' or NLS_UPPER(a.contactfax) like :phone||'%')");
			params.put("phone", phone);
		}
		//
		if (isNotEmpty(division)) {
			// SYNC_REQUIRED <> 3 (could be null, 0, 2, 3)
			constrains.append(" and a.id in(select x.CUSTOMERID from TCUSTOMER_OWNERS x, TRECRUITER y, TDIVISION z where "//
					+ "x.teamid = :teamid and x.RECRUITERID = y.id and y.groupid = x.teamid and y.division = z.ID " //
					+ "and z.NAME_INDEX = :division and z.teamid = x.teamid)");
			params.put("division", division.toUpperCase());
		}
		//
		if (types != null) {
			String typesConstrain = "";
			List<Long> typeIds = new ArrayList<Long>();
			//
			for (Map.Entry<String, Long> entry : typeMap.entrySet()) {
				if (typeIds.contains(entry.getKey().toUpperCase())) {
					typeIds.add(entry.getValue());
				}
			}
			//
			if (typeIds.size() > 0) {
				typesConstrain = "exists (select 1 from TCUSTOMER_TYPE ty where a.id= ty.CUSTOMERID and ty.TYPEID in :typeIds and ty.teamid=a.teamid)";
				constrains.append(" and (" + typesConstrain + " )");
				params.put("typeIds", typeIds);
			}
		}
		//
		//
		if (showPrimary != null && showPrimary)
			constrains.append(" and a.isprimarycontact = 1");
		//
		if (ownerIds != null) {
			tables.append(", TCUSTOMER_OWNERS b");
			constrains.append(" and a.id = b.CUSTOMERID and a.teamid = b.teamid and b.RECRUITERID = :recruiterid");
			params.put("recruiterid", ownerIds);
		}
		//
		if (!(showInactive != null && showInactive))
			constrains.append(" and a.active = 1");
		//
		String queryString = select + tables + constrains + " AND ROWNUM <= 300 order by upper(a.lastname), upper(a.firstname)";
		//
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
		//
		List<Contact> list = namedParameterJdbcTemplate.query(queryString, params, new ContactRowMapper());
		//
		if (fromAPI && list != null) {
			for (Contact contact : list) {
				//
				assignContact(jobDivaSession, teamId, typeMap, contact);
			}
		}
		return list;
		//
	}
	
	private void assignContact(JobDivaSession jobDivaSession, Long teamId, Map<String, Long> typeMap, Contact contact) {
		String typenames = "";
		List<ContactType> contactTypes = contactTypeDao.getContactTypes(contact.getId(), jobDivaSession.getTeamId());
		for (ContactType contactType : contactTypes) {
			long typeid = contactType.getId();
			for (Map.Entry<String, Long> entry : typeMap.entrySet()) {
				if (typeid == entry.getValue().longValue())
					typenames += entry.getKey() + "|";
			}
		}
		contact.setContactType(typenames);
		//
		//
		String sql = "select firstname, lastname from TCUSTOMER where teamid = ? and id = ? ";
		Object[] parameters = new Object[] { teamId, contact.getId() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<String> reportToList = jdbcTemplate.query(sql, parameters, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				//
				String firstName = rs.getString("firstname");
				String lastName = rs.getString("lastname");
				//
				String name = firstName;
				if (lastName != null)
					name += " " + lastName;
				return name;
			}
		});
		if (reportToList != null && reportToList.size() > 0) {
			contact.setReportsToName(reportToList.get(0));
		}
		//
		String phonetype = contact.getPhoneTypes();// .substring(0, 4);
		for (int j = 0; j < 4; j++) {
			if (phonetype == null)
				phonetype = "WCHF";
			else if (phonetype.length() < 4)
				break;
			String strPhone = "";
			char t = phonetype.charAt(j);
			switch (t) { // take care of space!
				case 'W':
					strPhone = "Work Phone: ";
					break;
				case 'C':
					strPhone = "Mobile Phone: ";
					break;
				case 'H':
					strPhone = "Home Phone: ";
					break;
				case 'F':
					strPhone = "Work Fax: ";
					break;
				case 'P':
					strPhone = "Pager: ";
					break;
				case 'M':
					strPhone = "Main Phone: ";
					break;
				case 'X':
					strPhone = "Home Fax: ";
					break;
				case 'D':
					strPhone = "Direct Phone: ";
					break;
				case 'O':
					strPhone = "Other Phone: ";
					break;
			}
			switch (j) {
				case 0:
					strPhone += (contact.getWorkPhone() == null ? "" : (contact.getWorkPhone()) + (contact.getWorkphoneExt() == null ? "" : " ext(" + contact.getWorkphoneExt() + ")"));
					contact.setPhone1(strPhone);
					break;
				case 1:
					strPhone += (contact.getCellPhone() == null ? "" : (contact.getCellPhone()) + (contact.getCellPhoneExt() == null ? "" : " ext(" + contact.getCellPhoneExt() + ")"));
					contact.setPhone2(strPhone);
					break;
				case 2:
					strPhone += (contact.getHomePhone() == null ? "" : (contact.getHomePhone()) + (contact.getHomePhoneExt() == null ? "" : " ext(" + contact.getHomePhoneExt() + ")"));
					contact.setPhone3(strPhone);
					break;
				case 3:
					strPhone += (contact.getContactFax() == null ? "" : (contact.getHomePhone()) + (contact.getContactFaxExt() == null ? "" : " ext(" + contact.getContactFaxExt() + ")"));
					contact.setPhone4(strPhone);
					break;
			}
		}
	}
	
	public Long createContact(JobDivaSession jobDivaSession, String company, String firstname, String lastname, String title, String department, PhoneType[] phones, ContactAddress[] addresses, String email, String alternateemail, String[] types,
			Owner[] owners, Long reportsto, Boolean primary, String assistantname, String assistantemail, String assistantphone, String assistantphoneextension, String subguidelines, Integer maxsubmittals, Boolean references, Boolean drugtest,
			Boolean backgroundcheck, Boolean securityclearance) throws Exception {
		//
		if (isNotEmpty(company)) {
			company = company.toUpperCase().trim();
		} else if (reportsto != null)
			throw new Exception("Error: Please first select a Company for this contact(" + firstname + ", " + lastname + ")");
		//
		checkPhoneType(phones, "contact");
		//
		checkAdddresses(addresses);
		//
		if (isNotEmpty(assistantphone)) {
			String phone = Pattern.compile(NON_DIGITS).matcher(assistantphone).replaceAll("");
			if (phone.length() > 20)
				throw new Exception("Error: 'assistantphone' should be no more than 20 characters. \r\n");
			else if (phone.length() > 0)
				assistantphone = phone;
			else
				assistantphone = null;
		}
		if (isNotEmpty(assistantphoneextension)) {
			String ext = Pattern.compile(NON_DIGITS).matcher(assistantphoneextension).replaceAll("");
			if (ext.length() > 10)
				throw new Exception("Error: 'assistantphoneextension' should be no more than 10 characters. \r\n");
			else if (ext.length() > 0)
				assistantphoneextension = ext;
			else
				assistantphoneextension = null;
		}
		//
		checkOwners(owners);
		//
		//
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		//
		Timestamp datecreated = new Timestamp(System.currentTimeMillis());
		//
		if (isNotEmpty(company)) {
			List<Long> companies = searchCompanyDao.searchForCompany(jobDivaSession, company);
			if (companies.size() > 0) {
				//
				Long companyId = companies.get(0);
				//
				fields.put("COMPANYID", "companyId");
				parameterSource.addValue("companyId", companyId);
				//
				fields.put("COMPANYNAME", "company");
				parameterSource.addValue("company", company);
				//
				fields.put("COMPANYNAME_INDEX", "companyIndex");
				parameterSource.addValue("companyIndex", company.toUpperCase());
				//
				//
				if (reportsto != null) {
					List<Contact> contactList = searchContacts(jobDivaSession, jobDivaSession.getTeamId(), reportsto, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false);
					//
					if (contactList == null || contactList.isEmpty()) {
						throw new Exception("Error: Contact(\'reportsto\'-" + reportsto + ") does not exist. \r\n");
					} else if (!contactList.get(0).getCompanyId().equals(companyId)) {
						throw new Exception("Error: New contact and \'reportsto\'(" + reportsto + ") do not belong to the same company(" + contactList.get(0).getCompanyName() + ").");
					}
					//
					fields.put("REPORTSTO", "reportsto");
					parameterSource.addValue("reportsto", reportsto);
					//
				}
				//
				//
			} else {
				throw new Exception("Error: Company(" + company + ") does not exist. \r\n");
			}
		} else {
			fields.put("COMPANYID", "companyId");
			parameterSource.addValue("companyId", 0L);
		}
		//
		fields.put("FIRSTNAME", "firstname");
		parameterSource.addValue("firstname", firstname);
		//
		fields.put("LASTNAME", "lastname");
		parameterSource.addValue("lastname", lastname);
		//
		if (isNotEmpty(title)) {
			fields.put("TITLE", "title");
			parameterSource.addValue("title", title);
		}
		//
		if (isNotEmpty(department)) {
			fields.put("DEPARTMENTNAME", "department");
			parameterSource.addValue("department", department);
		}
		//
		if (phones != null) {
			String phoneTypes = "";
			int i = 0;
			for (PhoneType phone : phones) {
				if (phone == null)
					continue;
				phoneTypes += phone.getType();
				//
				switch (i) {
					case 0:
						fields.put("WORKPHONE", "workPhone");
						parameterSource.addValue("workPhone", phone.getPhone());
						//
						fields.put("WORKPHONEEXT", "workPhoneExt");
						parameterSource.addValue("workPhoneExt", phone.getExt());
						//
						break;
					case 1:
						fields.put("CELLPHONE", "cellPhone");
						parameterSource.addValue("cellPhone", phone.getPhone());
						//
						fields.put("CELLPHONEEXT", "cellPhoneExt");
						parameterSource.addValue("cellPhoneExt", phone.getExt());
						//
						break;
					case 2:
						fields.put("HOMEPHONE", "homePhone");
						parameterSource.addValue("homePhone", phone.getPhone());
						//
						fields.put("HOMEPHONEEXT", "homePhoneExt");
						parameterSource.addValue("homePhoneExt", phone.getExt());
						//
						break;
					case 3:
						fields.put("CONTACTFAX", "contactFax");
						parameterSource.addValue("contactFax", phone.getPhone());
						//
						fields.put("CONTACTFAXEXT", "contactFaxExt");
						parameterSource.addValue("contactFaxExt", phone.getExt());
						//
						break;
				}
				//
				i++;
			}
			if (phoneTypes.length() < 4)
				phoneTypes += "    ";
			fields.put("PHONETYPES", "phoneTypes");
			parameterSource.addValue("phoneTypes", phoneTypes);
		}
		//
		if (isNotEmpty(email)) {
			fields.put("EMAIL", "email");
			parameterSource.addValue("email", email);
		}
		//
		if (isNotEmpty(alternateemail)) {
			fields.put("ALTERNATE_EMAIL", "alternateemail");
			parameterSource.addValue("alternateemail", alternateemail);
		}
		//
		if (primary != null) {
			fields.put("ISPRIMARYCONTACT", "primary");
			parameterSource.addValue("primary", primary);
		}
		//
		if (isNotEmpty(assistantname)) {
			fields.put("ASSISTANTNAME", "assistantname");
			parameterSource.addValue("assistantname", assistantname);
		}
		//
		if (isNotEmpty(assistantemail)) {
			fields.put("ASSISTANTEMAIL", "assistantemail");
			parameterSource.addValue("assistantemail", assistantemail);
		}
		//
		if (isNotEmpty(assistantphone)) {
			fields.put("ASSISTANTPHONE", "assistantphone");
			parameterSource.addValue("assistantphone", assistantphone);
		}
		//
		if (isNotEmpty(assistantphoneextension)) {
			fields.put("ASSISTANTPHONEEXT", "assistantphoneextension");
			parameterSource.addValue("assistantphoneextension", assistantphoneextension);
		}
		//
		maxsubmittals = maxsubmittals != null ? maxsubmittals : 0;
		fields.put("MAXSUBMITALS", "maxsubmittals");
		parameterSource.addValue("maxsubmittals", maxsubmittals.shortValue(), Types.SMALLINT);
		//
		references = references != null ? references : false;
		fields.put("REFCHECK", "references");
		parameterSource.addValue("references", references, Types.BOOLEAN);
		//
		drugtest = drugtest != null ? drugtest : false;
		fields.put("DRUGTEST", "drugtest");
		parameterSource.addValue("drugtest", drugtest, Types.BOOLEAN);
		//
		backgroundcheck = backgroundcheck != null ? backgroundcheck : false;
		fields.put("BACKCHECK", "backgroundcheck");
		parameterSource.addValue("backgroundcheck", backgroundcheck, Types.BOOLEAN);
		//
		securityclearance = securityclearance != null ? securityclearance : false;
		fields.put("SECCLEARANCE", "securityclearance");
		parameterSource.addValue("securityclearance", securityclearance, Types.BOOLEAN);
		//
		fields.put("TIMEENTERED", "datecreated");
		parameterSource.addValue("datecreated", new Date());// ,
															// Types.TIMESTAMP);
		//
		fields.put("TYPE", "TYPE");
		parameterSource.addValue("TYPE", 0);
		//
		fields.put("TEAMID", "TEAMID");
		parameterSource.addValue("TEAMID", jobDivaSession.getTeamId());
		//
		fields.put("RECRUITERID", "RECRUITERID");
		parameterSource.addValue("RECRUITERID", jobDivaSession.getRecruiterId());
		//
		fields.put("ACTIVE", "active");
		parameterSource.addValue("active", true, Types.BOOLEAN);
		//
		fields.put("PRIVATEINFO", "privateInfo");
		parameterSource.addValue("privateInfo", false, Types.BOOLEAN);
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		// this is the key holder
		Long contactId = null;
		String sql = "SELECT CUSTOMERID.nextval AS contactId FROM dual";
		List<Long> listLong = jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("contactId");
			}
		});
		if (listLong != null && listLong.size() > 0) {
			contactId = listLong.get(0);
		}
		//
		//
		String sqlInsert = "INSERT INTO TCUSTOMER (ID," + sqlInsertFields(new ArrayList<String>(fields.keySet())) + ") VALUES (" + contactId + "," + sqlInsertParams(new ArrayList<String>(fields.keySet())) + ") ";
		//
		jdbcTemplate.update(sqlInsert, parameterSource.getValues().values().toArray());
		//
		// get the value of the generated id
		//
		//
		//
		if (types != null) {
			Map<String, Long> typeMap = getTypeMap(jobDivaSession.getTeamId());
			List<Long> typeIds = new ArrayList<Long>();
			//
			ArrayList<String> typeList = new ArrayList<String>();
			for (int i = 0; i < types.length; i++) {
				if (isNotEmpty(types[i]))
					typeList.add(types[i].toUpperCase());
			}
			for (Map.Entry<String, Long> entry : typeMap.entrySet()) {
				if (typeList.contains(entry.getKey().toUpperCase())) {
					typeIds.add(entry.getValue());
				}
			}
			//
			for (Long typeId : typeIds) {
				contactTypeDao.addcontactType(jobDivaSession, contactId, typeId);
			}
		}
		//
		//
		long addressId = 1;
		if (addresses == null || addresses.length == 0) {
			ContactAddress contactAddress = new ContactAddress();
			contactAddress.setId(addressId);
			contactAddress.setCountryId("US");
			contactAddress.setDefaultAddress(true);
			contactAddress.setDeleted(false);
			addresses = new ContactAddress[] { contactAddress };
			//
			contactAddressDao.insertUpdateContactAddress(jobDivaSession, contactId, contactAddress, true);
		} else {
			//
			//
			//
			for (int i = 0; i < addresses.length; i++) {
				ContactAddress contactAddress = addresses[i];
				// to be sure it's an insert
				contactAddress.setId(addressId);
				contactAddress.setDeleted(false);
				//
				//
				if (isNotEmpty(contactAddress.getState())) {
					String countryid = contactAddress.getCountryId();
					if (countryid == null || countryid.length() == 0) {
						countryid = "US";
						contactAddress.setCountryId(countryid);
					}
					String state = lookupState(contactAddress.getState(), countryid);
					if (state != null)
						contactAddress.setState(state);
					else
						throw new Exception("Error: Contact(" + firstname + ", " + lastname + ") can not be created, due to State(" + contactAddress.getState() + ") mapping unfound.(with countryid(" + countryid + ")) \r\n");
				}
				//
				contactAddressDao.insertUpdateContactAddress(jobDivaSession, contactId, contactAddress, true);
				//
				addressId++;
			}
		}
		//
		//
		if (owners != null) {
			// List<ContactOwner> contactOwners =
			// contactOwnerDao.getContactOwners(contactId) ;
			//
			for (Owner owner : owners) {
				ContactOwner contactOwner = new ContactOwner();
				contactOwner.setCustomerId(contactId);
				contactOwner.setTeamId(jobDivaSession.getTeamId());
				contactOwner.setIsPrimaryOwner(owner.getPrimary());
				contactOwner.setRecruiterId(0L);
				//
				if (owner.getOwnerId() != null) {
					//
					sql = "select id from TRECRUITER where GROUPID = ? AND Id = ? ";
					Object[] params = new Object[] { jobDivaSession.getTeamId(), owner.getOwnerId() };
					List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
						
						@Override
						public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							return rs.getLong("id");
						}
					});
					//
					if (list != null && list.size() > 0) {
						contactOwner.setRecruiterId(list.get(0));
					} else {
						throw new Exception("Error: Contact owner(" + owner.getOwnerId() + ") does not exist. \r\n");
					}
					//
				} else {
					sql = "select id from TRECRUITER where GROUPID = ? and nls_upper(FIRSTNAME) = nls_upper(?) and nls_upper(LASTNAME) = nls_upper(?) ";
					Object[] params = new Object[] { jobDivaSession.getTeamId(), firstname, lastname };
					//
					//
					List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
						
						@Override
						public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							return rs.getLong("id");
						}
					});
					if (list != null && list.size() > 0) {
						contactOwner.setRecruiterId(list.get(0));
					} else {
						throw new Exception("Error: Contact owner(" + firstname + ", " + lastname + ") does not exist. \r\n");
					}
				}
				//
				contactOwnerDao.insertcontactOwner(jobDivaSession, contactOwner);
			}
		}
		//
		//
		ContactNote contactNote = new ContactNote();
		contactNote.setCustomerId(contactId);
		contactNote.setNote("New Contact is created.");
		contactNote.setDateModified(datecreated);
		contactNote.setRfqId(0L);
		contactNote.setRecruiterTeamId(jobDivaSession.getTeamId());
		contactNote.setRecruiterId(jobDivaSession.getRecruiterId());
		contactNote.setCandidateId(0L);
		contactNote.setLatestNotes(true);
		contactNote.setDateCreated(datecreated);
		contactNoteDao.insertUpdateContactNote(jobDivaSession, contactNote);
		//
		updateCacheServerForCreateContact(jobDivaSession, contactId, types);
		//
		return contactId;
	}
	
	private void updateCacheServerForCreateContact(JobDivaSession jobDivaSession, Long contactId, String[] types) {
		Long teamid = jobDivaSession.getTeamId();
		String envType = getEnvironmentType();
		CustomerData customerData = prepareCustomerData(contactId, types, teamid.longValue());
		try {
			CacheServer_Stub cache_server = (CacheServer_Stub) NamedServer.findService("CacheServer", envType);
			if (cache_server.exists("SUPPLIER_LIST:TEAM" + teamid) && customerData.types.contains(Long.valueOf(4L)))
				NamedServer.addToAll("SUPPLIER_LIST:TEAM" + teamid, customerData, envType);
			if (cache_server.exists("CONTACTS_LIST:TEAM" + teamid))
				NamedServer.addToAll("CONTACTS_LIST:TEAM" + teamid, customerData, envType);
			CustomerCompanyData custcompData = new CustomerCompanyData(customerData.companyid);
			custcompData.company_name = customerData.company_name;
			if (customerData.companyid > 0L && cache_server.exists("COMPANY_LIST:TEAM" + teamid))
				NamedServer.addToAll("COMPANY_LIST:TEAM" + teamid, custcompData, envType);
		} catch (Exception e) {
			this.logger.error("Update Cache Server For Create Contact[" + contactId + " ] " + e.getMessage());
		}
	}
	
	private void checkAdddresses(ContactAddress[] addresses) throws Exception {
		if (addresses != null) {
			int default_cnt = 0;
			for (ContactAddress cont_add : addresses) {
				//
				if (cont_add == null)
					continue;
				//
				if (cont_add.getDefaultAddress())
					default_cnt++;
				//
				if (isNotEmpty(cont_add.getAddress1())) {
					if (cont_add.getAddress1().length() > 100)
						throw new Exception("Error: Address1 should be no more than 100 characters. \r\n");
				}
				//
				if (isNotEmpty(cont_add.getAddress2())) {
					if (cont_add.getAddress2().length() > 100)
						throw new Exception("Error: Address2 should be no more than 100 characters. \r\n");
				}
				//
				if (isNotEmpty(cont_add.getCity())) {
					if (cont_add.getCity().length() > 100)
						throw new Exception("Error: City shoule be no more than 100 characters. \r\n");
				}
				//
				if (isNotEmpty(cont_add.getCountryId())) {
					cont_add.setCountryId(getCountryID(cont_add.getCountryId().trim()));
					if (cont_add.getCountryId().equals("not found"))
						throw new Exception("Error: Address cannot be created. 'country'(" + cont_add.getCountryId().trim() + ") is invalid . \r\n");// jd_cont_add.localCountryidTracker
																																						// =
																																						// false;
				}
				if (isNotEmpty(cont_add.getFreeText())) {
					if (cont_add.getFreeText().length() > 50)
						throw new Exception("Error: Label shoule be no more than 50 characters. \r\n");
				}
				if (isNotEmpty(cont_add.getZipCode())) {
					if (cont_add.getZipCode().length() > 20)
						throw new Exception("Error: Zip shoule be no more than 20 characters. \r\n");
				}
			}
			//
			if (default_cnt != 1)
				throw new Exception("Error: Please set one and only one address as default address. \r\n");
		}
	}
	
	//
	private void updateContactUserFields(JobDivaSession jobDivaSession, Long contactid, Userfield[] userfields) throws Exception {
		if (userfields != null) {
			//
			validateUserFields(jobDivaSession, jobDivaSession.getTeamId(), userfields, UDF_FIELDFOR_CONTACT);
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			for (Userfield userfield : userfields) {
				//
				if (isEmpty(userfield.getUserfieldValue())) {
					deleteContactUserField(contactid, userfield.getUserfieldId(), jobDivaSession.getTeamId());
				} else {
					String sql = "SELECT userfieldValue from TCUSTOMER_USERFIELDS Where CUSTOMERID = ? AND USERFIELD_ID = ? AND TEAMID = ? ";
					//
					Object[] params = new Object[] { contactid, userfield.getUserfieldId(), jobDivaSession.getTeamId() };
					//
					//
					List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
						
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString("userfieldValue");
						}
					});
					//
					Timestamp currentDt = new Timestamp(System.currentTimeMillis());
					//
					if (list == null || list.isEmpty()) {
						String sqlInsert = "INSERT INTO TCUSTOMER_USERFIELDS(CUSTOMERID, USERFIELD_ID, TEAMID, USERFIELD_VALUE, DATECREATED) " //
								+ "VALUES " //
								+ "(? , ?, ?, ?, ?) ";
						params = new Object[] { contactid, userfield.getUserfieldId(), jobDivaSession.getTeamId(), userfield.getUserfieldValue(), currentDt };
						jdbcTemplate.update(sqlInsert, params);
					} else {
						String sqlUpdate = "Update TCUSTOMER_USERFIELDS SET USERFIELD_VALUE = ?, DATECREATED = ? " //
								+ "WHERE CUSTOMERID = ? AND USERFIELD_ID = ? AND TEAMID = ?   "; //
						params = new Object[] { userfield.getUserfieldValue(), currentDt, contactid, userfield.getUserfieldId(), jobDivaSession.getTeamId() };
						jdbcTemplate.update(sqlUpdate, params);
					}
				}
			}
		}
	}
	
	private void assignContactPhones(JobDivaSession jobDivaSession, Long contactid, PhoneType[] phones, LinkedHashMap<String, String> fields, MapSqlParameterSource parameterSource) throws Exception {
		if (phones != null) {
			//
			List<Contact> contacts = searchContacts(jobDivaSession, jobDivaSession.getTeamId(), contactid, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, true);
			//
			if (contacts != null && contacts.size() > 0) {
				Contact contact = contacts.get(0);
				//
				// Check phone types setting
				String[] numbers = { contact.getWorkPhone(), contact.getCellPhone(), contact.getHomePhone(), contact.getContactFax() };
				String[] exts = { contact.getWorkphoneExt(), contact.getCellPhoneExt(), contact.getHomePhoneExt(), contact.getContactFaxExt() };
				String typeStr = contact.getPhoneTypes();
				//
				if (typeStr == null || typeStr.length() < 4)
					typeStr = "WCHF";
				//
				String[] types = { typeStr.substring(0, 1).equals(" ") ? "W" : typeStr.substring(0, 1), //
						typeStr.substring(1, 2).equals(" ") ? "C" : typeStr.substring(1, 2), //
						typeStr.substring(2, 3).equals(" ") ? "H" : typeStr.substring(2, 3), //
						typeStr.substring(3, 4).equals(" ") ? "F" : typeStr.substring(3, 4) };
				//
				ArrayList<String> localPhones = new ArrayList<String>(4);
				for (int i = 0; i < 4; i++) {
					if (numbers[i] == null && exts[i] == null)
						continue;
					else if (types[i].equals("W") || types[i].equals("P"))
						localPhones.add(numbers[i] + "~" + exts[i] + "~" + types[i]);
					else if (numbers[i] == null)
						continue; // avoid page bug
					else
						localPhones.add(numbers[i] + "~null~" + types[i]);
				}
				//
				//
				for (PhoneType phoneObj : phones) {
					String phone = phoneObj.getPhone() + "~" + phoneObj.getExt() + "~" + phoneObj.getType();
					//
					if (phoneObj.getAction() == 1) {
						if (localPhones.contains(phone)) {
							// message.append("Phone(" + jdContPhone.getPhone()
							// + "), ext(" + jdContPhone.getExt() + ") & type("
							// + jdContPhone.getType() + ") already exists and
							// wasn't inserted. \r\n");
						} else {
							localPhones.add(phone);
						}
					} else if (phoneObj.getAction() == 2) {
						if (localPhones.contains(phone))
							localPhones.remove(localPhones.indexOf(phone));
						else
							throw new Exception("Error: Phone(" + phoneObj.getPhone() + "), ext(" + phoneObj.getExt() + ") & type(" + phoneObj.getType() + ") " //
									+ " doesn't exist and can't be deleted. \r\n");
					}
				}
				//
				if (localPhones.size() > 4)
					throw new Exception("Error: # of Phones out of bound. 4 can be displayed at most.");
				//
				for (int i = 0; i < 4; i++) {
					numbers[i] = "";
					exts[i] = "";
				}
				String phoneType = "";
				for (int i = 0; i < localPhones.size(); i++) {
					String[] str_arr = localPhones.get(i).split("~");
					if (!str_arr[0].equals("null"))
						numbers[i] = str_arr[0];
					if (!str_arr[1].equals("null"))
						exts[i] = str_arr[1];
					if (!str_arr[2].equals("null"))
						phoneType += str_arr[2];
				}
				//
				fields.put("WORKPHONE", "workPhone");
				parameterSource.addValue("workPhone", numbers[0]);
				//
				fields.put("WORKPHONEEXT", "workPhoneExt");
				parameterSource.addValue("workPhoneExt", exts[0]);
				//
				fields.put("CELLPHONE", "cellPhone");
				parameterSource.addValue("cellPhone", numbers[1]);
				//
				fields.put("CELLPHONEEXT", "cellPhoneExt");
				parameterSource.addValue("cellPhoneExt", exts[1]);
				//
				fields.put("HOMEPHONE", "homePhone");
				parameterSource.addValue("homePhone", numbers[2]);
				//
				fields.put("HOMEPHONEEXT", "homePhoneExt");
				parameterSource.addValue("homePhoneExt", exts[2]);
				//
				fields.put("CONTACTFAX", "contactFax");
				parameterSource.addValue("contactFax", numbers[3]);
				//
				fields.put("CONTACTFAXEXT", "contactFaxExt");
				parameterSource.addValue("contactFaxExt", exts[3]);
				//
				if (phoneType.length() < 4)
					phoneType += "    ";
				fields.put("PHONETYPES", "phoneType");
				parameterSource.addValue("phoneType", phoneType);
			}
		}
	}
	
	private void updateContactAddresses(JobDivaSession jobDivaSession, Long contactid, ContactAddress[] addresses) throws Exception {
		if (addresses != null) {
			List<ContactAddress> oldAddresses = contactAddressDao.getContactAddresses(contactid, jobDivaSession.getTeamId(), false, null);
			//
			//
			// if there's only one address with no data, delete it so that new
			// ones can be inserted
			if (oldAddresses.size() == 1) {
				ContactAddress contAdd = oldAddresses.get(0);
				if (contAdd.getAddress1() == null && contAdd.getAddress2() == null && contAdd.getCity() == null && contAdd.getState() == null && contAdd.getZipCode() == null && contAdd.getFreeText() == null && contAdd.getDeleted() == false
						&& (contAdd.getCountryId() == null || contAdd.getCountryId().equals("US")) && contAdd.getDefaultAddress() == true) {
					oldAddresses.remove(0);
					contactAddressDao.deleteContactAddress(jobDivaSession.getTeamId(), contAdd.getId(), contactid);
				}
			}
			//
			//
			long maxAddressId = 0;
			Hashtable<Long, Boolean> pairs = new Hashtable<Long, Boolean>();
			for (ContactAddress contAdd : oldAddresses) {
				if (maxAddressId < contAdd.getId())
					maxAddressId = contAdd.getId();
				if (contAdd.getDefaultAddress() == null)
					pairs.put(contAdd.getId(), false);
				else
					pairs.put(contAdd.getId(), contAdd.getDefaultAddress());
			}
			//
			//
			long maxAddId_tmp = maxAddressId;
			for (int i = 0; i < addresses.length; i++) {
				ContactAddress jd_contAddress = addresses[i];
				if (jd_contAddress.getAction() == 1) {
					if (jd_contAddress.getId() == null)// must be insert
						pairs.put(++maxAddId_tmp, jd_contAddress.getDefaultAddress());
					else // may be modification
						pairs.put(jd_contAddress.getId(), jd_contAddress.getDefaultAddress());
				} else if (jd_contAddress.getAction() == 2) // delete
					pairs.remove(jd_contAddress.getId());
			}
			//
			//
			//
			Enumeration<Boolean> v = pairs.elements();
			int da_cnt = 0;
			while (v.hasMoreElements() && da_cnt < 2)
				if (v.nextElement())
					da_cnt++;
			if (da_cnt != 1)
				throw new Exception("Error: There should be one and only one default address for contact(" + contactid + "), please make sure the \'default\' flags are set correctly. \r\n");
			//
			//
			for (int i = 0; i < addresses.length; i++) {
				//
				Boolean insertMode = false;
				ContactAddress jd_contAddress = addresses[i];
				//
				//
				List<ContactAddress> contAddressess = jd_contAddress.getId() == null ? null : contactAddressDao.getContactAddresses(contactid, jobDivaSession.getTeamId(), jd_contAddress.getId());
				//
				ContactAddress contAddress = contAddressess != null && contAddressess.size() > 0 ? contAddressess.get(0) : null;
				//
				//
				if (jd_contAddress.getAction() == 1) {
					if (contAddress == null) {// insert
						insertMode = true;
						contAddress = new ContactAddress();
						if (jd_contAddress.getId() != null)
							contAddress.setId(jd_contAddress.getId());
						else
							contAddress.setId(++maxAddressId);
						contAddress.setDeleted(false);
						if (!isNotEmpty(jd_contAddress.getCountryId()))
							;
						contAddress.setCountryId("US");
						//
					} else if (contAddress.getDeleted()) { // replace an old
															// record
						contAddress.setDeleted(false);
						contAddress.setAddress1(null);
						contAddress.setAddress2(null);
						contAddress.setCity(null);
						contAddress.setState(null);
						contAddress.setZipCode(null);
						contAddress.setCountryId(null);
						contAddress.setFreeText(null);
					}
					//
					contAddress.setDefaultAddress(jd_contAddress.getDefaultAddress());
					//
					if (isNotEmpty(jd_contAddress.getAddress1()))
						contAddress.setAddress1(jd_contAddress.getAddress1());
					//
					if (isNotEmpty(jd_contAddress.getAddress2()))
						contAddress.setAddress2(jd_contAddress.getAddress2());
					//
					if (isNotEmpty(jd_contAddress.getCity()))
						contAddress.setCity(jd_contAddress.getCity());
					//
					if (isNotEmpty(jd_contAddress.getCountryId()))
						contAddress.setCountryId(jd_contAddress.getCountryId());
					//
					if (isNotEmpty(jd_contAddress.getState())) {
						String countryid = contAddress.getCountryId();
						//
						String state = lookupState(jd_contAddress.getState(), countryid);
						if (state != null)
							contAddress.setState(state);
						else
							throw new Exception("Error: State (" + jd_contAddress.getState() + ") can not be updated due to the mapping unfound.(with countryid(" + countryid + ")) \r\n");
					}
					//
					if (isNotEmpty(jd_contAddress.getZipCode()))
						contAddress.setZipCode(jd_contAddress.getZipCode());
					//
					if (isNotEmpty(jd_contAddress.getFreeText()))
						contAddress.setFreeText(jd_contAddress.getFreeText());
					// else modify
				} else if (jd_contAddress.getAction() == 2) {// delete
					if (contAddress == null)
						throw new Exception("Error: Address(" + jd_contAddress.getId() + ") of contact(" + contactid + ") is not found. Can not delete this address from the contact. \r\n");
					contAddress.setDeleted(true);
				}
				//
				//
				if (contAddress != null)
					contactAddressDao.insertUpdateContactAddress(jobDivaSession, contactid, contAddress, insertMode);
			}
		}
	}
	
	public Boolean updateContact(JobDivaSession jobDivaSession, Long contactid, //
			String firstname, String lastname, String title, Long companyid, String department, //
			ContactAddress[] addresses, PhoneType[] phones, //
			Long reportsto, Boolean active, Boolean primary, //
			String email, String alternateemail, String assistantname, String assistantemail, String assistantphone, String assistantphoneextension, //
			String subguidelines, Integer maxsubmittals, Boolean references, Boolean drugtest, Boolean backgroundcheck, Boolean securityclearance, //
			Userfield[] userfields, Owner[] owners, String[] types) throws Exception {
		//
		checkAdddresses(addresses);
		//
		if (isNotEmpty(assistantphone)) {
			String phone = Pattern.compile(NON_DIGITS).matcher(assistantphone).replaceAll("");
			if (phone.length() > 20)
				throw new Exception("Error: 'assistantphone' should be no more than 20 characters. \r\n");
			else if (phone.length() > 0)
				assistantphone = phone;
			else
				assistantphone = null;
		}
		if (isNotEmpty(assistantphoneextension)) {
			String ext = Pattern.compile(NON_DIGITS).matcher(assistantphoneextension).replaceAll("");
			if (ext.length() > 10)
				throw new Exception("Error: 'assistantphoneextension' should be no more than 10 characters. \r\n");
			else if (ext.length() > 0)
				assistantphoneextension = ext;
			else
				assistantphoneextension = null;
		}
		//
		checkOwners(owners);
		//
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		//
		//
		if (isNotEmpty(firstname)) {
			fields.put("FIRSTNAME", "firstname");
			parameterSource.addValue("firstname", firstname);
		}
		//
		if (isNotEmpty(lastname)) {
			fields.put("LASTNAME", "lastname");
			parameterSource.addValue("lastname", lastname);
		}
		//
		if (isNotEmpty(title)) {
			fields.put("TITLE", "title");
			parameterSource.addValue("title", title);
		}
		//
		if (companyid != null) {
			List<Company> companies = searchCompanyDao.searchForCompany(jobDivaSession, companyid);
			//
			if (companies.size() == 0) {
				throw new Exception("Error: Company " + companyid + " is not found");
			}
			//
			Company companyObj = companies.get(0);
			//
			fields.put("COMPANYID", "companyId");
			parameterSource.addValue("companyId", companyObj.getId());
			//
			fields.put("COMPANYNAME", "company");
			parameterSource.addValue("company", companyObj.getName());
			//
			fields.put("COMPANYNAME_INDEX", "companyIndex");
			parameterSource.addValue("companyIndex", companyObj.getName().toUpperCase());
		}
		//
		updateContactAddresses(jobDivaSession, contactid, addresses);
		//
		assignContactPhones(jobDivaSession, contactid, phones, fields, parameterSource);
		//
		if (reportsto != null) {
			List<Contact> contacts = searchContacts(jobDivaSession, jobDivaSession.getTeamId(), reportsto, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false);
			if (contacts == null || contacts.size() == 0) {
				throw new Exception("Error: Contact(\'reportsto\'-" + reportsto + ") does not exist. \r\n");
			}
			Contact contact = contacts.get(0);
			//
			if (companyid != null && companyid > 0 && !contact.getCompanyId().equals(companyid))
				throw new Exception("Error: Contact(" + contact.getId() + ") and \'reportsto\'(" + reportsto + ") do not belong to the same company.");
			//
			fields.put("REPORTSTO", "reportsto");
			parameterSource.addValue("reportsto", reportsto);
		}
		//
		//
		if (isNotEmpty(department)) {
			fields.put("DEPARTMENTNAME", "department");
			parameterSource.addValue("department", department);
		}
		//
		if (primary != null) {
			fields.put("ISPRIMARYCONTACT", "primary");
			parameterSource.addValue("primary", true);
		}
		if (active != null) {
			fields.put("ACTIVE", "active");
			parameterSource.addValue("active", true);
		}
		//
		if (isNotEmpty(email)) {
			fields.put("EMAIL", "email");
			parameterSource.addValue("email", email);
		}
		//
		if (isNotEmpty(alternateemail)) {
			fields.put("ALTERNATE_EMAIL", "alternateemail");
			parameterSource.addValue("alternateemail", alternateemail);
		}
		//
		if (primary != null) {
			fields.put("ISPRIMARYCONTACT", "primary");
			parameterSource.addValue("primary", primary);
		}
		//
		if (isNotEmpty(assistantname)) {
			fields.put("ASSISTANTNAME", "assistantname");
			parameterSource.addValue("assistantname", assistantname);
		}
		//
		if (isNotEmpty(assistantemail)) {
			fields.put("ASSISTANTEMAIL", "assistantemail");
			parameterSource.addValue("assistantemail", assistantemail);
		}
		//
		if (isNotEmpty(assistantphone)) {
			fields.put("ASSISTANTPHONE", "assistantphone");
			parameterSource.addValue("assistantphone", assistantphone);
		}
		//
		if (isNotEmpty(assistantphoneextension)) {
			fields.put("ASSISTANTPHONEEXT", "assistantphoneextension");
			parameterSource.addValue("assistantphoneextension", assistantphoneextension);
		}
		//
		if (maxsubmittals != null) {
			fields.put("MAXSUBMITALS", "maxsubmittals");
			parameterSource.addValue("maxsubmittals", maxsubmittals);
		}
		//
		if (references != null) {
			fields.put("REFCHECK", "references");
			parameterSource.addValue("references", references);
		}
		//
		if (drugtest != null) {
			fields.put("DRUGTEST", "drugtest");
			parameterSource.addValue("drugtest", drugtest);
		}
		//
		if (backgroundcheck != null) {
			fields.put("BACKCHECK", "backgroundcheck");
			parameterSource.addValue("backgroundcheck", backgroundcheck);
		}
		//
		if (securityclearance != null) {
			fields.put("SECCLEARANCE", "securityclearance");
			parameterSource.addValue("securityclearance", securityclearance);
		}
		//
		updateContactUserFields(jobDivaSession, contactid, userfields);
		//
		updateContactOwners(jobDivaSession, contactid, owners);
		//
		//
		//
		if (types != null) {
			Map<String, Long> typeMap = getTypeMap(jobDivaSession.getTeamId());
			List<Long> typeIds = new ArrayList<Long>();
			//
			contactTypeDao.deleteContactTypes(jobDivaSession, contactid);
			//
			ArrayList<String> typeList = new ArrayList<String>();
			for (int i = 0; i < types.length; i++) {
				if (isNotEmpty(types[i]))
					typeList.add(types[i].trim().toUpperCase());
			}
			for (Map.Entry<String, Long> entry : typeMap.entrySet()) {
				if (typeList.contains(entry.getKey().toUpperCase())) {
					typeIds.add(entry.getValue());
				}
			}
			//
			for (Long typeId : typeIds) {
				contactTypeDao.addcontactType(jobDivaSession, contactid, typeId);
			}
		}
		//
		//
		//
		if (fields.size() > 0) {
			fields.put("EDITDATE", "editDate");
			parameterSource.addValue("editDate", new Timestamp(System.currentTimeMillis()));
			//
			String sqlUpdate = " UPDATE TCUSTOMER SET " + sqlUpdateFields(fields) + " WHERE ID = :contactid and teamid = :teamid ";
			parameterSource.addValue("contactid", contactid);
			parameterSource.addValue("teamid", jobDivaSession.getTeamId());
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
			jdbcTemplateObject.update(sqlUpdate, parameterSource);
		}
		//
		updateCacheServerForUpdateContact(jobDivaSession, contactid, types);
		//
		return true;
	}
	
	private CustomerData prepareCustomerData(Long contactId, String[] contactTypes, long teamid) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		String sql = "SELECT * FROM TCUSTOMER WHERE teamid = ? AND ID = ? ";
		Object[] params = { teamid, contactId };
		List<CustomerData> list = jdbcTemplate.query(sql, params, new RowMapper<CustomerData>() {
			
			@Override
			public CustomerData mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				//
				CustomerData customerData = new CustomerData(contactId);
				customerData.max_subs = rs.getInt("MAXSUBMITALS");
				customerData.isPrimaryContact = rs.getInt("ISPRIMARYCONTACT");
				customerData.active = rs.getInt("ACTIVE");
				customerData.refcheck = rs.getInt("REFCHECK");
				customerData.drugtest = rs.getInt("DRUGTEST");
				customerData.backcheck = rs.getInt("BACKCHECK");
				customerData.secclearance = rs.getInt("SECCLEARANCE");
				customerData.first_name = rs.getString("FIRSTNAME");
				customerData.last_name = rs.getString("LASTNAME");
				customerData.address1 = rs.getString("ADDRESS1");
				customerData.address2 = rs.getString("ADDRESS2");
				customerData.city = rs.getString("CITY");
				customerData.state = rs.getString("STATE");
				customerData.zipcode = rs.getString("ZIPCODE");
				customerData.country_id = rs.getString("COUNTRYID");
				customerData.email = rs.getString("EMAIL");
				customerData.alternate_email = rs.getString("ALTERNATE_EMAIL");
				customerData.company_name = rs.getString("COMPANYNAME");
				customerData.companyid = rs.getLong("COMPANYID");
				customerData.department_name = rs.getString("DEPARTMENTNAME");
				customerData.work_phone = rs.getString("WORKPHONE");
				customerData.work_phone_ext = rs.getString("WORKPHONEEXT");
				customerData.cell_phone = rs.getString("CELLPHONE");
				customerData.cell_phone_ext = rs.getString("CELLPHONEEXT");
				customerData.home_phone = rs.getString("HOMEPHONE");
				customerData.home_phone_ext = rs.getString("HOMEPHONEEXT");
				customerData.contact_fax = rs.getString("CONTACTFAX");
				customerData.contact_fax_ext = rs.getString("CONTACTFAXEXT");
				customerData.phone_types = rs.getString("PHONETYPES");
				customerData.contact_notes = rs.getString("CONTACTNOTES");
				customerData.title = rs.getString("TITLE");
				ArrayList<Long> types = new ArrayList<Long>();
				if (contactTypes != null) {
					for (String typeName : contactTypes) {
						if (typeName.equalsIgnoreCase("HIRING MANAGER"))
							types.add(1L);
						else if (typeName.equalsIgnoreCase("REFERENCE"))
							types.add(2L);
						else if (typeName.equalsIgnoreCase("PROSPECT"))
							types.add(3L);
						else if (typeName.equalsIgnoreCase("SUPPLIER"))
							types.add(4L);
					}
				}
				customerData.types = types;
				customerData.discount = rs.getDouble("DISCOUNT");
				customerData.discountType = rs.getInt("DISCOUNT_TYPE");
				customerData.discountPct = rs.getDouble("DISCOUNTPCT");
				customerData.customer_options = rs.getLong("CUSTOMER_OPTION");
				// customerData.Attributes
				return customerData;
			}
		});
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}
	
	private void updateCacheServerForUpdateContact(JobDivaSession jobDivaSession, Long contactid, String[] types) {
		Long teamid = jobDivaSession.getTeamId();
		String envType = getEnvironmentType();
		CustomerData customerData = prepareCustomerData(contactid, types, teamid.longValue());
		try {
			CacheServer_Stub cache_server = (CacheServer_Stub) NamedServer.findService("CacheServer", envType);
			if (cache_server.exists("SUPPLIER_LIST:TEAM" + teamid) && customerData.types.contains(Long.valueOf(4L))) {
				NamedServer.removeFromAll("SUPPLIER_LIST:TEAM" + teamid, customerData.getID(), envType);
				NamedServer.addToAll("SUPPLIER_LIST:TEAM" + teamid, customerData, envType);
			}
			if (cache_server.exists("CONTACTS_LIST:TEAM" + teamid)) {
				NamedServer.removeFromAll("CONTACTS_LIST:TEAM" + teamid, customerData.getID(), envType);
				NamedServer.addToAll("CONTACTS_LIST:TEAM" + teamid, customerData, envType);
			}
		} catch (Exception e) {
			this.logger.error("Update Cache Server For Update Contact[" + contactid + " ] " + e.getMessage());
		}
	}
	
	private void updateContactOwners(JobDivaSession jobDivaSession, Long contactid, Owner[] owners) throws Exception {
		//
		if (owners != null) {
			List<ContactOwner> allOwners = contactOwnerDao.getContactOwners(contactid, jobDivaSession.getTeamId());
			//
			Hashtable<Long, ContactOwner> all = new Hashtable<Long, ContactOwner>();
			int numOfprimary = 0;
			Set<Long> update = new HashSet<Long>();
			//
			for (int i = 0; i < allOwners.size(); i++) { // deal with old owners
															// and get the
															// primary recruiter
															// info
				if (allOwners.get(i).getIsPrimaryOwner())
					numOfprimary++;
				all.put(allOwners.get(i).getRecruiterId(), allOwners.get(i));
			}
			//
			//
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			for (Owner owner : owners) {
				ContactOwner contactOwner = new ContactOwner();
				contactOwner.setInsertMode(true);
				contactOwner.setCustomerId(contactid);
				contactOwner.setTeamId(jobDivaSession.getTeamId());
				contactOwner.setIsPrimaryOwner(owner.getPrimary());
				contactOwner.setRecruiterId(0L);
				//
				if (owner.getOwnerId() != null) {
					String sql = "select id from TRECRUITER where GROUPID = ? AND Id = ? ";
					Object[] params = new Object[] { jobDivaSession.getTeamId(), owner.getOwnerId() };
					List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
						
						@Override
						public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							return rs.getLong("id");
						}
					});
					//
					if (list != null && list.size() > 0) {
						contactOwner.setRecruiterId(list.get(0));
					} else {
						throw new Exception("Error: Contact owner(" + owner.getOwnerId() + ") does not exist. \r\n");
					}
				} else {
					Object[] params = new Object[] { jobDivaSession.getTeamId(), owner.getFirstName(), owner.getLastName() };
					//
					String sql = "select id from TRECRUITER where GROUPID = ? and nls_upper(FIRSTNAME) = nls_upper(?) and nls_upper(LASTNAME) = nls_upper(?) ";
					//
					List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
						
						@Override
						public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							return rs.getLong("id");
						}
					});
					if (list != null && list.size() > 0) {
						contactOwner.setRecruiterId(list.get(0));
					} else {
						throw new Exception("Error: Contact owner(" + owner.getFirstName() + ", " + owner.getLastName() + ") does not exist. \r\n");
					}
				}
				contactOwner.setTeamId(jobDivaSession.getTeamId());
				contactOwner.setIsPrimaryOwner(owner.getPrimary());
				long recid = contactOwner.getRecruiterId();
				//
				if ((Integer) owner.getAction() == 2) {
					if (all.containsKey(recid)) {
						ContactOwner localOwner = all.get(recid);
						String sqlDelete = "DELETE FROM TCUSTOMER_OWNERS WHERE CUSTOMERID = ? AND RECRUITERID = ? and TEAMID = ? ";
						//
						Object[] params = new Object[] { localOwner.getCustomerId(), localOwner.getRecruiterId(), jobDivaSession.getTeamId() };
						//
						jdbcTemplate.update(sqlDelete, params);
						//
						all.remove(recid);
					}
					//
					if (update.contains(recid))
						update.remove(recid);
					//
					if (owner.getPrimary())
						numOfprimary--;
					//
				} else {
					if (owner.getPrimary()) {
						numOfprimary++;
					}
					update.add(recid);
					if (all.containsKey(recid)) {
						all.get(recid).setIsPrimaryOwner(owner.getPrimary());
					} else {
						all.put(recid, contactOwner);
					}
				}
			}
			//
			if (numOfprimary > 1)
				throw new Exception("There are more than one primary owners.");
			//
			for (Long u : update) {
				ContactOwner contactOwner = all.get(u);
				//
				int updated = contactOwnerDao.updatecontactOwner(jobDivaSession, contactOwner);
				if (updated == 0) {
					contactOwnerDao.insertcontactOwner(jobDivaSession, contactOwner);
				}
			}
		}
	}
	
	//
	public WebhookContact getWebhookContact(JobDivaSession jobDivaSession, Long contactId) {
		String sql = "Select distinct " //
				+ " a.id, " //
				+ " a.TEAMID, " //
				+ " a.firstname, " //
				+ " a.lastname, " //
				+ " a.companyname, " //
				+ " a.departmentname, " //
				+ " a.workphone, " //
				+ " a.homephone, " //
				+ " a.email," // //
				+ " a.companyid, " //
				+ " a.title, " //
				+ " a.cellphone, " //
				+ " a.contactfax, " //
				+ " a.phonetypes, " //
				+ " ca.address1, " //
				+ " ca.address2, " //
				+ " ca.city, " //
				+ " ca.state," // //
				+ " ca.zipcode, " //
				+ " ca.countryid, " //
				+ " a.ALTERNATE_EMAIL, " //
				+ " a.assistantname, " //
				+ " a.assistantemail, " //
				+ " a.assistantphone, " //
				+ " a.assistantphoneext," //
				+ " a.workphoneext, " //
				+ " a.cellphoneext, " //
				+ " a.homephoneext, " //
				+ " a.contactfaxext, " //
				+ " a.reportsto " //
				+ " from ";
		//
		sql += " TCUSTOMER a  left join  TCUSTOMERADDRESS ca on a.teamid = ca.teamid and a.id = ca.contactid and ca.DEFAULT_ADDRESS = 1  and ca.deleted = 0 ";
		//
		sql += " where a.teamid = ? AND a.ID = ?  " //
		;
		//
		Object[] params = new Object[] { jobDivaSession.getTeamId(), contactId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Contact> webhookContacts = jdbcTemplate.query(sql, params, new WebhookContactRowMapper());
		//
		if (webhookContacts != null && webhookContacts.size() > 0) {
			WebhookContact webhookContact = (WebhookContact) webhookContacts.get(0);
			//
			Map<String, Long> typeMap = getTypeMap(jobDivaSession.getTeamId());
			//
			assignContact(jobDivaSession, contactId, typeMap, webhookContact);
			//
			return webhookContact;
		}
		return null;
	}
}

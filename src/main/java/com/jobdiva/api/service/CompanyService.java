package com.jobdiva.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.jobdiva.api.dao.company.CompanyAddressDao;
import com.jobdiva.api.dao.company.CompanyNotesDao;
import com.jobdiva.api.dao.company.CreateCompanyDao;
import com.jobdiva.api.dao.company.SearchCompanyDao;
import com.jobdiva.api.dao.company.SearchCompanyUDFDao;
import com.jobdiva.api.dao.company.UpdateCompanyDao;
import com.jobdiva.api.model.Company;
import com.jobdiva.api.model.CompanyAddress;
import com.jobdiva.api.model.FinancialsType;
import com.jobdiva.api.model.Note;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.SimpleContact;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class CompanyService extends AbstractService {
	
	@Autowired
	SearchCompanyDao	searchCompanyDao;
	//
	@Autowired
	CompanyAddressDao	companyAddressDao;
	//
	@Autowired
	SearchCompanyUDFDao	companyUDFDao;
	//
	@Autowired
	CreateCompanyDao	createCompanyDao;
	//
	@Autowired
	UpdateCompanyDao	updateCompanyDao;
	//
	@Autowired
	CompanyNotesDao		companyNotesDao;
	
	public List<Company> searchForCompany(JobDivaSession jobDivaSession, Long companyId, String company, String address, String city, String state, String zip, String country, String phone, String fax, String url, String parentCompany,
			Boolean showAll, String[] types, Long ownerIds, String division, String salespipeline) throws Exception {
		//
		//
		try {
			List<Company> companies = searchCompanyDao.searchForCompany(jobDivaSession, companyId, company, address, city, state, zip, country, phone, fax, url, parentCompany, showAll, types, ownerIds, division, salespipeline);
			//
			searchCompanyDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchCompany", "Search Successful ");
			//
			return companies;
			//
		} catch (Exception e) {
			//
			searchCompanyDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchCompany", "Search Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Long createCompany(JobDivaSession jobDivaSession, String companyname, String address1, String address2, String city, String state, String zipcode, String country, String phone, String fax, String email, String url, String parentcompany,
			String[] companytypes, Owner[] owners, String salespipeline) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Long>() {
				
				@Override
				public Long doInTransaction(TransactionStatus status) {
					try {
						Long newCompanyId = createCompanyDao.createCompany(jobDivaSession, companyname, address1, address2, city, state, zipcode, country, phone, fax, email, url, parentcompany, companytypes, owners, salespipeline);
						//
						createCompanyDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCompany", "Create Successful");
						//
						return newCompanyId;
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			createCompanyDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCompany", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean updateCompany(JobDivaSession jobDivaSession, Long companyid, String name, Long parentcompanyid, String[] companytypes, CompanyAddress[] addresses, String subguidelines, Integer maxsubmittals, Boolean references, Boolean drugtest,
			Boolean backgroundcheck, Boolean securityclearance, Userfield[] userfields, Double discount, String discountper, Double percentagediscount, FinancialsType financials, Owner[] owners, String salespipeline) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						Boolean sucess = updateCompanyDao.updateCompany(jobDivaSession, companyid, name, parentcompanyid, companytypes, addresses, subguidelines, maxsubmittals, references, drugtest, backgroundcheck, securityclearance, userfields,
								discount, discountper, percentagediscount, financials, owners, salespipeline);
						//
						updateCompanyDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCompany", "Update Successful");
						//
						return sucess;
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
			//
		} catch (Exception e) {
			//
			updateCompanyDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCompany", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public List<Note> getCompanyNotes(JobDivaSession jobDivaSession, Long companyid) throws Exception {
		//
		try {
			List<Note> notes = companyNotesDao.getNotes(jobDivaSession, companyid);
			//
			companyNotesDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getCompanyNotes", "Get Company Notes Successful");
			//
			return notes;
			//
		} catch (Exception e) {
			//
			companyNotesDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getCompanyNotes", "Get Company Notes Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Long addCompanyNote(JobDivaSession jobDivaSession, Long userid, Long companyid, String note) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Long>() {
				
				@Override
				public Long doInTransaction(TransactionStatus status) {
					try {
						Long noteid = companyNotesDao.addNote(jobDivaSession, userid, companyid, note);
						//
						companyNotesDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addCompanyNotes", "Add Company Notes Successful");
						//
						return noteid;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			companyNotesDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addCompanyNotes", "Add Company Notes Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public List<SimpleContact> getCompanyContacts(JobDivaSession jobDivaSession, Long companyid) throws Exception {
		//
		try {
			List<SimpleContact> contacts = searchCompanyDao.getCompanyContacts(jobDivaSession, companyid);
			//
			companyNotesDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getCompanyContacts", "Get Company Contacts Successful");
			//
			return contacts;
			//
		} catch (Exception e) {
			//
			companyNotesDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getCompanyContacts", "Get Company Contacts Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
}

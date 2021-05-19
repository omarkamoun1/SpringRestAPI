package com.jobdiva.api.controller.v2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.Company;
import com.jobdiva.api.model.CompanyAddress;
import com.jobdiva.api.model.FinancialsType;
import com.jobdiva.api.model.Note;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.SimpleContact;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.v2.company.AddCompanyNoteDef;
import com.jobdiva.api.model.v2.company.CreateCompanyDef;
import com.jobdiva.api.model.v2.company.SearchCompanyDef;
import com.jobdiva.api.model.v2.company.UpdateCompanyDef;
import com.jobdiva.api.service.CompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

//
@CrossOrigin
@RestController
@RequestMapping("/apiv2/jobdiva/")
@Api(value = "Company API", description = "REST API Used For Company")
@ApiIgnore
public class CompanyV2Controller extends AbstractJobDivaController {
	
	//
	@Autowired
	CompanyService companyService;
	
	//
	@ApiOperation(value = "Search Company")
	@RequestMapping(value = "/searchCompany", method = RequestMethod.POST, produces = "application/json")
	public List<Company> searchCompany( //
			//
			@ApiParam(value = "companyid : Company ID \r\n " + "company : Company Name \r\n" //
					+ "address : Company Address \r\n" //
					+ "city : City \r\n" //
					+ "state : State \r\n" //
					+ "zip : Zip Code \r\n" //
					+ "country : Please set “country” if “state” is set. \r\n" //
					+ "phone : Phone \r\n" //
					+ "fax : Fax \r\n" //
					+ "url : Company WebSite \r\n" //
					+ "parentcompany : Parent Company Name \r\n" //
					+ "showall : Show all children companies \r\n" //
					+ "types : Company types, valid values can be found through “Leader Tools” → “My Team” → “Manage Company Types”. \r\n" //
					+ "ownerids : Company owner IDs(Customer ID) \r\n" //
					+ "division : Valid values can be found through “Leader Tools” → “My Team” → “Manage Divisions”. \r\n" //
					+ "salespipeline : Sales pipeline" //
			) //
			@RequestBody SearchCompanyDef searchCompanyDef
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("searchCompany");
		//
		Long companyid = searchCompanyDef.getCompanyid();
		String company = searchCompanyDef.getCompany();
		String address = searchCompanyDef.getAddress();
		String city = searchCompanyDef.getCity();
		String state = searchCompanyDef.getState();
		String zip = searchCompanyDef.getZip();
		String country = searchCompanyDef.getCountry();
		String phone = searchCompanyDef.getPhone();
		String fax = searchCompanyDef.getFax();
		String url = searchCompanyDef.getUrl();
		String parentcompany = searchCompanyDef.getParentcompany();
		Boolean showall = searchCompanyDef.getShowall();
		String[] types = searchCompanyDef.getTypes();
		Long ownerids = searchCompanyDef.getOwnerids();
		String division = searchCompanyDef.getDivision();
		String salespipeline = searchCompanyDef.getSalespipeline();
		//
		return companyService.searchForCompany(jobDivaSession, companyid, company, address, city, state, zip, country, phone, fax, url, parentcompany, showall, types, ownerids, division, salespipeline);
		//
	}
	//
	
	@RequestMapping(value = "/createCompany", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Company", notes = "Known limitation : Enter all fields on the create page, but not others on the company display page.")
	public Long createCompany( //
			//
			//
			@ApiParam(value = "companyname : Company Name \r\n" //
					+ "address1 : Address Line 1 \r\n" //
					+ "address2 : Address Line 2 \r\n" //
					+ "city : City \r\n" //
					+ "state : State \r\n" //
					+ "zipcode : Zip Code \r\n" //
					+ "country : Please set “country” if “state” is set. \r\n" //
					+ "phone : Phone \r\n" //
					+ "fax : Fax \r\n" //
					+ "email : Email; \r\n" //
					+ "url : Company WebSite \r\n" //
					+ "parentcompany : Parent Company name \r\n" //
					+ "companytypes : Company types, valid values can be found through “Leader Tools” → “My Team” → “Manage Company Types” \r\n" //
					+ "owners : Owner \r\n" //
					+ "salespipeline : Sales pipeline") //
			//
			@RequestBody CreateCompanyDef createCompanyDef
	//
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createCompany");
		//
		//
		String companyname = createCompanyDef.getCompanyname();
		String address1 = createCompanyDef.getAddress1();
		String address2 = createCompanyDef.getAddress2();
		String city = createCompanyDef.getCity();
		String state = createCompanyDef.getState();
		String zipcode = createCompanyDef.getZipcode();
		String country = createCompanyDef.getCountry();
		String phone = createCompanyDef.getPhone();
		String fax = createCompanyDef.getFax();
		String email = createCompanyDef.getEmail();
		String url = createCompanyDef.getUrl();
		String parentcompany = createCompanyDef.getParentcompany();
		String[] companytypes = createCompanyDef.getCompanytypes();
		Owner[] owners = createCompanyDef.getOwners();
		String salespipeline = createCompanyDef.getSalespipeline();
		//
		return companyService.createCompany(jobDivaSession, companyname, address1, address2, city, state, zipcode, country, phone, fax, email, url, parentcompany, companytypes, owners, salespipeline);
		//
	} //
	
	//
	@RequestMapping(value = "/updateCompany", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Company", notes = "Does not update company types, owners, primary contacts or other tabs except for ‘Submittal Guidelines’, ‘Addresses’.")
	public Boolean updateCompany( //
			//
			@ApiParam(value = "companyid : Company ID \r\n" //
					+ "name : Company Name \r\n" //
					+ "parentcompanyid : Parent company ID \r\n" //
					+ "companytypes : Company types to update to. If specified, previous type assignment will be removed \r\n" //
					+ "addresses : CompanyAddress \r\n" //
					+ "subguidelines : Submittal guidelines \r\n" //
					+ "maxsubmittals : Number of max allowed submittals \r\n" //
					+ "references : References \r\n" //
					+ "drugtest : Drug test \r\n" //
					+ "backgroundcheck : Background check \r\n" //
					+ "securityclearance : Security clearance \r\n" //
					+ "Userfields : Userfield \r\n" //
					+ "discount : Discount \r\n" //
					+ "discountper : Discount Per \r\n" //
					+ "percentagediscount : Percentage Discount \r\n" //
					+ "financials : Financials Type \r\n" //
					+ "owners : Owner \r\n" //
					+ "salespipeline : Sales pipeline ") //
			//
			@RequestBody UpdateCompanyDef updateCompanyDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCompany");
		//
		//
		Long companyid = updateCompanyDef.getCompanyid();
		String name = updateCompanyDef.getName();
		Long parentcompanyid = updateCompanyDef.getParentcompanyid();
		String[] companytypes = updateCompanyDef.getCompanytypes();
		CompanyAddress[] addresses = updateCompanyDef.getAddresses();
		String subguidelines = updateCompanyDef.getSubguidelines();
		Integer maxsubmittals = updateCompanyDef.getMaxsubmittals();
		Boolean references = updateCompanyDef.getReferences();
		Boolean drugtest = updateCompanyDef.getDrugtest();
		Boolean backgroundcheck = updateCompanyDef.getBackgroundcheck();
		Boolean securityclearance = updateCompanyDef.getSecurityclearance();
		Userfield[] Userfields = updateCompanyDef.getUserfields();
		Double discount = updateCompanyDef.getDiscount();
		String discountper = updateCompanyDef.getDiscountper();
		Double percentagediscount = updateCompanyDef.getPercentagediscount();
		FinancialsType financials = updateCompanyDef.getFinancials();
		Owner[] owners = updateCompanyDef.getOwners();
		String salespipeline = updateCompanyDef.getSalespipeline();
		//
		//
		return companyService.updateCompany(jobDivaSession, companyid, name, parentcompanyid, companytypes, addresses, subguidelines, maxsubmittals, references, drugtest, backgroundcheck, securityclearance, Userfields, discount, discountper,
				percentagediscount, financials, owners, salespipeline);
		//
	}
	
	@ApiOperation(value = "Get Company Notes")
	@RequestMapping(value = "/getCompanyNotes", method = RequestMethod.GET, produces = "application/json")
	public List<Note> getCompanyNotes( //
			//
			@ApiParam(value = "Company ID", required = true) //
			@RequestParam(required = true) Long companyid //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("getCompanyNotes");
		//
		return companyService.getCompanyNotes(jobDivaSession, companyid);
		//
	} //
	
	@ApiOperation(value = "Add Company Note")
	@RequestMapping(value = "/addCompanyNote", method = RequestMethod.POST, produces = "application/json")
	public Long addCompanyNote( //
			//
			@ApiParam(value = "userid : User ID \r\n" //
					+ "companyid : Company ID \r\n" //
					+ "note : Note") //
			//
			@RequestBody AddCompanyNoteDef addCompanyNoteDef
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("getCompanyNote");
		//
		//
		Long userid = addCompanyNoteDef.getUserid();
		Long companyid = addCompanyNoteDef.getCompanyid();
		String note = addCompanyNoteDef.getNote();
		//
		//
		return companyService.addCompanyNote(jobDivaSession, userid, companyid, note);
		//
	} //
	
	@ApiOperation(value = "Get Company Contacts")
	@RequestMapping(value = "/getCompanyContacts", method = RequestMethod.GET, produces = "application/json")
	public List<SimpleContact> getCompanyContacts( //
			//
			@ApiParam(value = "Company ID", required = true) //
			@RequestParam(required = true) Long companyid //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("getCompanyContacts");
		//
		return companyService.getCompanyContacts(jobDivaSession, companyid);
		//
	} //
}

package com.jobdiva.api.controller.jobdivaapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.jobdiva.api.service.CompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//
@CrossOrigin
@RestController
@RequestMapping("/api/jobdiva/")
@Api(value = "Company API", description = "REST API Used For Company")
public class CompanyController extends AbstractJobDivaController {
	
	//
	@Autowired
	CompanyService companyService;
	
	//
	@ApiOperation(value = "Search Company")
	@RequestMapping(value = "/searchCompany", method = RequestMethod.GET, produces = "application/json")
	public List<Company> searchCompany( //
			//
			@ApiParam(value = "Company ID", required = false) //
			@RequestParam(required = false) Long companyid, //
			//
			@ApiParam(value = "Company Name", required = false) //
			@RequestParam(required = false) String company, //
			//
			@ApiParam(value = "Company Address", required = false) //
			@RequestParam(required = false) String address, //
			//
			@ApiParam(value = "City", required = false) //
			@RequestParam(required = false) String city, //
			//
			@ApiParam(value = "State", required = false) //
			@RequestParam(required = false) String state, //
			//
			@ApiParam(value = "Zip Code", required = false) //
			@RequestParam(required = false) String zip, //
			//
			@ApiParam(value = "Please set “country” if “state” is set.", required = false) //
			@RequestParam(required = false) String country, //
			//
			@ApiParam(value = "Phone", required = false) //
			@RequestParam(required = false) String phone, //
			//
			@ApiParam(value = "Fax", required = false) //
			@RequestParam(required = false) String fax, //
			//
			@ApiParam(value = "Company WebSite", required = false) //
			@RequestParam(required = false) String url, //
			//
			@ApiParam(value = "Parent Company Name", required = false) //
			@RequestParam(required = false) String parentcompany, //
			//
			@ApiParam(value = "Show all children companies", required = false) //
			@RequestParam(required = false) Boolean showall, //
			//
			@ApiParam(value = "Company types, valid values can be found through “Leader Tools” → “My Team” → “Manage Company Types”.", required = false) //
			@RequestParam(required = false) String[] types, //
			//
			@ApiParam(value = "Company owner IDs(Customer ID)", required = false) //
			@RequestParam(required = false) Long ownerids, //
			//
			@ApiParam(value = "Valid values can be found through “Leader Tools” → “My Team” → “Manage Divisions”.", required = false) //
			@RequestParam(required = false) String division, //
			//
			@ApiParam(value = "Sales pipeline", required = false) //
			@RequestParam(required = false) String salespipeline //
	//
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("searchCompany");
		//
		return companyService.searchForCompany(jobDivaSession, companyid, company, address, city, state, zip, country, phone, fax, url, parentcompany, showall, types, ownerids, division, salespipeline);
		//
	}
	
	//
	@ApiImplicitParams({ @ApiImplicitParam(name = "owners", required = false, allowMultiple = true, dataType = "Owner") })
	@RequestMapping(value = "/createCompany", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Create Company", notes = "Known limitation : Enter all fields on the create page, but not others on the company display page.")
	public Long createCompany( //
			//
			//
			@ApiParam(value = "Company Name", required = true) //
			@RequestParam(required = true) String companyname, //
			//
			@ApiParam(value = "Address Line 1", required = false) //
			@RequestParam(required = false) String address1, //
			//
			@ApiParam(value = "Address Line 2", required = false) //
			@RequestParam(required = false) String address2, //
			//
			@ApiParam(value = "City", required = false) //
			@RequestParam(required = false) String city, //
			//
			@ApiParam(value = "State", required = false) //
			@RequestParam(required = false) String state, //
			//
			@ApiParam(value = "Zip Code", required = false) //
			@RequestParam(required = false) String zipcode, //
			//
			@ApiParam(value = "Please set “country” if “state” is set.", required = false) //
			@RequestParam(required = false) String country, //
			//
			@ApiParam(value = "Phone", required = false) //
			@RequestParam(required = false) String phone, //
			//
			@ApiParam(value = "Fax", required = false) //
			@RequestParam(required = false) String fax, //
			//
			@ApiParam(value = "Email", required = false) //
			@RequestParam(required = false) String email, //
			//
			@ApiParam(value = "Company WebSite", required = false) //
			@RequestParam(required = false) String url, //
			//
			@ApiParam(value = "Parent Company name", required = false) //
			@RequestParam(required = false) String parentcompany, //
			//
			@ApiParam(value = "Company types, valid values can be found through “Leader Tools” → “My Team” → “Manage Company Types”.", allowableValues = "{Leader Tools, My Team, Manage Company Types}", required = false) //
			@RequestParam(required = false) String[] companytypes, //
			//
			@ApiParam(required = false, type = "Owner", allowMultiple = true) //
			@RequestParam(required = false) Owner[] owners, //
			//
			@ApiParam(value = "Sales pipeline", required = false) //
			@RequestParam(required = false) String salespipeline //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("createCompany");
		//
		return companyService.createCompany(jobDivaSession, companyname, address1, address2, city, state, zipcode, country, phone, fax, email, url, parentcompany, companytypes, owners, salespipeline);
		//
	} //
	
	@ApiImplicitParams({ @ApiImplicitParam(name = "addresses", required = false, allowMultiple = true, dataType = "CompanyAddress"), //
			@ApiImplicitParam(name = "Userfields", required = false, allowMultiple = true, dataType = "Userfield"), //
			@ApiImplicitParam(name = "financials", required = false, allowMultiple = false, dataType = "FinancialsType"), //
			@ApiImplicitParam(name = "owners", required = false, allowMultiple = true, dataType = "Owner") })
	//
	@RequestMapping(value = "/updateCompany", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ApiOperation(value = "Update Company", notes = "Does not update company types, owners, primary contacts or other tabs except for ‘Submittal Guidelines’, ‘Addresses’.")
	public Boolean updateCompany( //
			//
			//
			@ApiParam(value = "Company ID", required = true) //
			@RequestParam(required = true) Long companyid, //
			//
			@ApiParam(value = "Company Name", required = false) //
			@RequestParam(required = false) String name, //
			//
			@ApiParam(value = "Parent company ID", required = false) //
			@RequestParam(required = false) Long parentcompanyid, //
			//
			@ApiParam(value = "Company types to update to. \r\n" //
					+ "If specified, previous type assignment will be removed", required = false) //
			@RequestParam(required = false) String[] companytypes, //
			//
			@ApiParam(required = false, type = "CompanyAddress", allowMultiple = true) //
			@RequestParam(required = false) CompanyAddress[] addresses, //
			//
			@ApiParam(value = "Submittal guidelines", required = false) //
			@RequestParam(required = false) String subguidelines, //
			//
			@ApiParam(value = "Number of max allowed submittals", required = false) //
			@RequestParam(required = false) Integer maxsubmittals, //
			//
			@ApiParam(value = "References", required = false) //
			@RequestParam(required = false) Boolean references, //
			//
			@ApiParam(value = "Drug test", required = false) //
			@RequestParam(required = false) Boolean drugtest, //
			//
			@ApiParam(value = "Background check", required = false) //
			@RequestParam(required = false) Boolean backgroundcheck, //
			//
			@ApiParam(value = "Security clearance", required = false) //
			@RequestParam(required = false) Boolean securityclearance, //
			//
			@ApiParam(required = false, type = "Userfield", allowMultiple = true) //
			@RequestParam(required = false) Userfield[] Userfields, //
			//
			@ApiParam(value = "Discount", required = false) //
			@RequestParam(required = false) Double discount, //
			//
			@ApiParam(value = "Discount Per", required = false) //
			@RequestParam(required = false) String discountper, //
			//
			@ApiParam(value = "Percentage Discount", required = false) //
			@RequestParam(required = false) Double percentagediscount, //
			//
			@ApiParam(required = false, type = "Financials Type", allowMultiple = false) //
			@RequestParam(required = false) FinancialsType financials, //
			//
			@ApiParam(required = false, type = "Owner", allowMultiple = true) //
			@RequestParam(required = false) Owner[] owners, //
			//
			@ApiParam(value = "Sales pipeline", required = false) //
			@RequestParam(required = false) String salespipeline //
	//
	) throws Exception {
		//
		JobDivaSession jobDivaSession = getJobDivaSession();
		//
		jobDivaSession.checkForAPIPermission("updateCompany");
		//
		return companyService.updateCompany(jobDivaSession, companyid, name, parentcompanyid, companytypes,addresses, subguidelines, maxsubmittals, references, drugtest, backgroundcheck, securityclearance, Userfields, discount, discountper, percentagediscount,
				financials, owners, salespipeline);
		//
	}
	
	@ApiOperation(value = "Get Company Notes")
	@RequestMapping(value = "/getCompanyNotes", method = RequestMethod.GET, produces = "application/json")
	public List<Note> getCompanyNotes( //
			//
			@ApiParam(value = "Company ID", required = true) //
			@RequestParam(required = true) Long companyid  //
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
 			@ApiParam(value = "User ID", required = true) //
 			@RequestParam(required = true) Long userid,  //
 			//
 			//
 			@ApiParam(value = "Company ID", required = true) //
 			@RequestParam(required = true) Long companyid, //
 			//
 			//
 			@ApiParam(value = "Note", required = true) //
 			@RequestParam(required = true) String note //
 			//
 	) throws Exception {
 		//
 		JobDivaSession jobDivaSession = getJobDivaSession();
 		//
 		jobDivaSession.checkForAPIPermission("getCompanyNote");
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

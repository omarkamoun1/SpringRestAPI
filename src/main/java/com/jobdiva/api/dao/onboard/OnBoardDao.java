package com.jobdiva.api.dao.onboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.onboard.HireType;
import com.jobdiva.api.model.onboard.OnBoardDocument;
import com.jobdiva.api.model.onboard.OnBoardLocationPackage;
import com.jobdiva.api.model.onboard.OnBoardPackage;

/**
 * @author Joseph Chidiac
 *
 */
@Component
public class OnBoardDao extends AbstractJobDivaDao {
	
	public List<HireType> getHireTypes(JobDivaSession jobDivaSession) {
		String sql = "SELECT ID, NAME " //
				+ " FROM tonboarding_tab " //
				+ " where teamid = ? " //
				+ " and nvl(deleted,0) = 0 " //
				+ " and ID > 0 " //
				+ " and ID <= 100  " //
				+ " order by upper(NAME) ";
		Object[] params = { jobDivaSession.getTeamId() };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		List<HireType> list = jdbcTemplate.query(sql, params, new RowMapper<HireType>() {
			
			@Override
			public HireType mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				HireType hireType = new HireType();
				hireType.setId(rs.getLong("ID"));
				hireType.setName(rs.getString("NAME"));
				return hireType;
			}
		});
		return list;
	}
	
	public List<OnBoardPackage> getPackages(JobDivaSession jobDivaSession, Long hireTypeId) {
		//
		String sql = "select  " //
				+ " t2.ID as tabid,  " //
				+ " t2.name as tabname,  " //
				+ " nvl(t1.require_distribution,0) as require_distribution,  " //
				+ " nvl(t1.docid,0) as docid,  " //
				+ " t3.name as docname,  " //
				+ " t3.filename,  " //
				+ " t3.require_return,  " //
				+ " t3.readonly,  " //
				+ " nvl(t3.send_to,0) as send_to, " //
				+ " t3.doctype  " //
				+ " from  tonboardings_mapping t1, tonboarding_tab t2, tonboardings t3  " //
				+ " where t1.tabid(+) = t2.id  " //
				+ " and t1.teamid(+) = t2.teamid  " //
				+ " and t2.teamid = ?  " //
				+ " and nvl(t2.deleted ,0) = 0  " //
				+ " and t1.docid=t3.id  " //
				+ " and t2.teamid = t3.teamid  " //
				+ " and nvl(t3.deleted,0) = 0   " //
				+ " and ( t2.id = ?  OR t2.id > ? )  " //
				+ " order by upper(t2.name), upper(t3.name) ";
		//
		Object[] params = { jobDivaSession.getTeamId(), hireTypeId, 100 };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<OnBoardPackage> packages = new ArrayList<>();
		//
		HashMap<String, OnBoardPackage> map = new HashMap<>();
		//
		jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				String packageName = rs.getString("tabname");
				//
				OnBoardPackage onBoardPackage = map.get(packageName);
				if (onBoardPackage == null) {
					Long localHireTypeId = rs.getLong("tabid");
					onBoardPackage = new OnBoardPackage();
					onBoardPackage.setId(localHireTypeId);
					onBoardPackage.setName(rs.getString("tabname"));
					map.put(packageName, onBoardPackage);
					packages.add(onBoardPackage);
					if (localHireTypeId != null && localHireTypeId.equals(hireTypeId)) {
						onBoardPackage.setHirePackage(true);
					} else {
						onBoardPackage.setSupplementalPackage(true);
					}
				}
				//
				OnBoardDocument onBoardDocument = new OnBoardDocument();
				onBoardDocument.setId(rs.getLong("docid"));
				onBoardDocument.setName(rs.getString("docname"));
				onBoardDocument.setMandatory(rs.getBoolean("require_distribution"));
				onBoardDocument.setDocumentType(rs.getInt("doctype"));
				//
				onBoardPackage.addDocument(onBoardDocument);
				//
				return null;
			}
		});
		//
		//
		Collections.sort(packages, new Comparator<OnBoardPackage>() {
			
			@Override
			public int compare(OnBoardPackage o1, OnBoardPackage o2) {
				//
				return o1.getId().compareTo(hireTypeId);
			}
		});
		//
		//
		//
		return packages;
	}
	
	public List<OnBoardDocument> getDocumentsByCompany(JobDivaSession jobDivaSession, Long companyId) {
		//
		if (companyId == null || companyId.longValue() <= 0L)
			return new ArrayList<>();
		//
		long parentcompanyid = 0L;
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		String companyname = "";
		//
		String sql = " select t1.name, t1.parent_companyid, t2.NAME as parentcompanyname  " //
				+ " from tcustomercompany t1, tcustomercompany t2  " //
				+ " where t1.teamid = ? " //
				+ " and t1.id = ?  " //
				+ " and t2.id(+) = t1.parent_companyid  " //
				+ " and t2.teamid(+) = t1.teamid ";
		//
		Object[] params = { jobDivaSession.getTeamId(), companyId };
		//
		List<List<Object>> parentInfos = jdbcTemplate.query(sql, params, new RowMapper<List<Object>>() {
			
			@Override
			public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				List<Object> list = new ArrayList<Object>();
				list.add(rs.getLong("parent_companyid"));
				list.add(rs.getString("name"));
				//
				return list;
			}
		});
		//
		if (parentInfos != null && parentInfos.size() > 0) {
			parentcompanyid = (long) parentInfos.get(0).get(0);
			companyname = (String) parentInfos.get(0).get(1);
			companyname = (companyname != null) ? companyname : "";
		}
		//
		//
		//
		sql = " select id, description, filename, require_return, readonly, mandatory, nvl(send_to,0) as send_to, doctype  " //
				+ " from tcompanyattachments  " //
				+ " where teamid = ?  " //
				+ " and companyid = ?  " //
				+ " and onboarding = 1  " //
				+ " and nvl(deleted,0) = 0  " //
				+ " order by upper(description) ";
		//
		params = new Object[] { jobDivaSession.getTeamId(), companyId };
		//
		List<OnBoardDocument> companyList = jdbcTemplate.query(sql, params, new RowMapper<OnBoardDocument>() {
			
			@Override
			public OnBoardDocument mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				OnBoardDocument onBoardDocument = new OnBoardDocument();
				onBoardDocument.setId(rs.getLong("id"));
				onBoardDocument.setName(rs.getString("description"));
				onBoardDocument.setMandatory(rs.getBoolean("mandatory"));
				onBoardDocument.setDocumentType(rs.getInt("doctype"));
				//
				return onBoardDocument;
			}
		});
		//
		//
		//
		List<OnBoardDocument> parentCompanyList = null;
		if (parentcompanyid > 0L) {
			sql = " select id, description, filename, require_return, readonly, mandatory, nvl(send_to,0) as send_to, doctype  " //
					+ " from tcompanyattachments  " //
					+ " where teamid = ?  " //
					+ " and companyid = ?  " //
					+ " and onboarding = 1  " //
					+ " and nvl(deleted,0) = 0  " //
					+ " order by upper(description) ";
			params = new Object[] { jobDivaSession.getTeamId(), Long.valueOf(parentcompanyid) };
			parentCompanyList = jdbcTemplate.query(sql, params, new RowMapper<OnBoardDocument>() {
				
				@Override
				public OnBoardDocument mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					OnBoardDocument onBoardDocument = new OnBoardDocument();
					onBoardDocument.setId(rs.getLong("id"));
					onBoardDocument.setName(rs.getString("description"));
					onBoardDocument.setMandatory(rs.getBoolean("mandatory"));
					onBoardDocument.setDocumentType(rs.getInt("doctype"));
					//
					return onBoardDocument;
				}
			});
		}
		//
		List<OnBoardDocument> globalList = new ArrayList<>();
		//
		globalList.addAll(companyList);
		//
		if (parentCompanyList != null)
			globalList.addAll(parentCompanyList);
		//
		return globalList;
	}
	
	public List<OnBoardDocument> getDocumentsByContact(JobDivaSession jobDivaSession, Long contactId) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		String sql = "select id, description, filename, thefile, mandatory, nvl(send_to,0) as send_to, doctype  " //
				+ " from tcontactattachments  " //
				+ " where teamid = ?  " //
				+ " and contactid = ?  " //
				+ " and onboarding = 1  " //
				+ " and nvl(deleted,0) = 0  " //
				+ " order by upper(DESCRIPTION) ";
		Object[] params = { jobDivaSession.getTeamId(), contactId };
		List<OnBoardDocument> list = jdbcTemplate.query(sql, params, new RowMapper<OnBoardDocument>() {
			
			@Override
			public OnBoardDocument mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				OnBoardDocument onBoardDocument = new OnBoardDocument();
				//
				onBoardDocument.setDocumentType(rs.getInt("doctype"));
				onBoardDocument.setId(rs.getLong("id"));
				onBoardDocument.setName(rs.getString("description"));
				onBoardDocument.setMandatory(rs.getBoolean("mandatory"));
				//
				return onBoardDocument;
				//
			}
		});
		return list;
	}
	
	public List<OnBoardLocationPackage> getJobLocationDocuments(JobDivaSession jobDivaSession, Long jobId) {
		//
		List<OnBoardLocationPackage> list = new ArrayList<>();
		//
		String city = "";
		String state = "";
		String country = "";
		//
		String sql = " select city, state, countryid " //
				+ " from trfq " //
				+ " where teamid = ? " //
				+ " and id = ? ";
		//
		Object[] params = { jobDivaSession.getTeamId(), jobId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		List<List<String>> localtionList = jdbcTemplate.query(sql, params, new RowMapper<List<String>>() {
			
			@Override
			public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				List<String> list = new ArrayList<String>();
				list.add(rs.getString("city"));
				list.add(rs.getString("state"));
				list.add(rs.getString("countryid"));
				return list;
			}
		});
		//
		//
		if (localtionList != null && localtionList.size() > 0) {
			List<String> location = localtionList.get(0);
			city = location.get(0);
			state = location.get(1);
			country = location.get(2);
		}
		//
		//
		if (city != null && city.length() > 0) {
			OnBoardLocationPackage onBoardPackage = new OnBoardLocationPackage();
			onBoardPackage.setCityPackage(true);
			//
			sql = " select id, name, filename, require_return, readonly, 0 mandatory, nvl(send_to,0) as send_to, doctype " //
					+ " from tonboardings  " //
					+ " where teamid= ?  " //
					+ " and nvl(deleted,0) = 0  " //
					+ " and archived = 0  " //
					+ " and city = ? " //
					+ " order by upper(name) ";
			//
			params = new Object[] { jobDivaSession.getTeamId(), city };
			//
			jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
				
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					OnBoardDocument onBoardDocument = new OnBoardDocument();
					onBoardDocument.setDocumentType(0);
					onBoardDocument.setId(rs.getLong("id"));
					onBoardDocument.setName(rs.getString("name"));
					onBoardDocument.setMandatory(rs.getBoolean("mandatory"));
					onBoardPackage.addDocument(onBoardDocument);
					//
					return true;
				}
			});
			//
			if (onBoardPackage.getDocuments() != null && onBoardPackage.getDocuments().size() > 0)
				list.add(onBoardPackage);
			//
		}
		if (state != null && state.length() > 0) {
			OnBoardLocationPackage onBoardPackage = new OnBoardLocationPackage();
			onBoardPackage.setStatePackage(true);
			sql = " select id, name, filename, require_return, readonly, 0 mandatory, nvl(send_to,0) as send_to, doctype "//
					+ " from tonboardings  " //
					+ " where teamid = ?  " //
					+ " and nvl(deleted,0) = 0  " //
					+ " and archived = 0  " //
					+ " and state = ?  " //
					+ " and city is null  " //
					+ " order by upper(name) ";
			//
			params = new Object[] { jobDivaSession.getTeamId(), state };
			//
			jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
				
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					//
					OnBoardDocument onBoardDocument = new OnBoardDocument();
					onBoardDocument.setDocumentType(0);
					onBoardDocument.setId(rs.getLong("id"));
					onBoardDocument.setName(rs.getString("name"));
					onBoardDocument.setMandatory(rs.getBoolean("mandatory"));
					onBoardPackage.addDocument(onBoardDocument);
					//
					return null;
				}
			});
			//
			if (onBoardPackage.getDocuments() != null && onBoardPackage.getDocuments().size() > 0)
				list.add(onBoardPackage);
			//
		}
		if (country != null && country.length() > 0) {
			OnBoardLocationPackage onBoardPackage = new OnBoardLocationPackage();
			onBoardPackage.setCountryPackage(true);
			sql = " select id, name, filename, require_return, readonly, 0 mandatory, nvl(send_to,0) as send_to, doctype " //
					+ " from tonboardings  " //
					+ " where teamid = ?  " //
					+ " and nvl(deleted,0) = 0  " //
					+ " and archived = 0  " //
					+ " and country = ?  " //
					+ " and state is null  " //
					+ " and city is null  " //
					+ " order by upper(name) ";
			params = new Object[] { jobDivaSession.getTeamId(), country };
			jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
				
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					OnBoardDocument onBoardDocument = new OnBoardDocument();
					onBoardDocument.setDocumentType(0);
					onBoardDocument.setId(rs.getLong("id"));
					onBoardDocument.setName(rs.getString("name"));
					onBoardDocument.setMandatory(rs.getBoolean("mandatory"));
					onBoardPackage.addDocument(onBoardDocument);
					//
					return true;
				}
			});
			//
			if (onBoardPackage.getDocuments() != null && onBoardPackage.getDocuments().size() > 0)
				list.add(onBoardPackage);
			//
		}
		return list;
	}
}

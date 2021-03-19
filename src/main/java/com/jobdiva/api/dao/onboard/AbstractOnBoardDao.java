package com.jobdiva.api.dao.onboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

/**
 * @author Joseph Chidiac
 *
 */
public class AbstractOnBoardDao extends AbstractJobDivaDao {
	
	public final String	WEB_FORM		= "Web Form";
	public final String	URL				= "URL";
	public final String	DESIGN_FORM		= "Design Form";
	//
	public final String	CANDIDATE		= "Candidate";
	public final String	SUPPLIER		= "Supplier";
	public final String	INTERNAL_USE	= "Internal Use";
	
	protected HashMap<Long, Integer> getDocumentTypesByIds(JobDivaSession jobDivaSession, List<Long> candidateDocIds) {
		//
		HashMap<Long, Integer> map = new HashMap<Long, Integer>();
		//
		try {
			if (candidateDocIds != null && candidateDocIds.size() > 0) {
				String sql = "SELECT id , doctype from tonboardings where teamid = :teamid and id in (:id) and nvl(deleted,0) = 0    "//
						+ " UNION "//
						+ " SELECT id , doctype from tcompanyattachments where teamid = :teamid and id in (:id)  and onboarding = 1  and nvl(deleted,0) = 0 " //
						+ " UNION  "//
						+ " SELECT id , doctype FROM tcontactattachments where teamid = :teamid and id in (:id)  and onboarding = 1 and nvl(deleted,0) = 0  ";
				//
				LinkedHashMap<String, Object> paramList = new LinkedHashMap<String, Object>();
				paramList.put("teamid", jobDivaSession.getTeamId());
				paramList.put("id", candidateDocIds);
				NamedParameterJdbcTemplate namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
				//
				namedParameterJdbcTemplate.query(sql, paramList, new RowMapper<Boolean>() {
					
					@Override
					public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						Long id = rs.getLong("id");
						Integer docType = rs.getInt("doctype");
						map.put(id, docType);
						//
						return true;
					}
				});
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		//
		return map;
	}
	
	protected String getStringDocumentType(Integer documetType) {
		if (documetType != null) {
			switch (documetType) {
				case 0:
					return WEB_FORM;
				case 1:
					return URL;
				case 2:
					return DESIGN_FORM;
			}
		}
		return null;
	}
	
	protected int getIntDocumentType(String documetType) {
		if (documetType != null) {
			if (documetType.equalsIgnoreCase(WEB_FORM))
				return 0;
			else if (documetType.equalsIgnoreCase(URL))
				return 1;
			else if (documetType.equalsIgnoreCase(DESIGN_FORM))
				return 2;
		}
		return 0;
	}
	
	// -- 0 Candidate
	// -- 1 Supplier
	// -- 2 internal use
	protected String getStringSendTo(Integer sendTo) {
		if (sendTo != null) {
			switch (sendTo) {
				case 0:
					return CANDIDATE;
				case 1:
					return SUPPLIER;
				case 2:
					return INTERNAL_USE;
			}
		}
		return CANDIDATE;
	}
}

package com.jobdiva.api.dao.qualification;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.Qualification;
import com.jobdiva.api.model.QualificationOption;

@Component
public class QualificationDao extends AbstractJobDivaDao {
	
	public Qualification getQualificationByCatId(Long teamid, Integer catId) {
		// hash dcatName and dcatId
		String sql = "select * " //
				+ " from TCATEGORIES " //
				+ " where CATID = ? AND TEAMID = ? ";
		//
		Object[] params = new Object[] { catId, teamid };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Qualification> list = jdbcTemplate.query(sql, params, new QualificationRowMapper());
		//
		return list != null && list.size() > 0 ? list.get(0) : null;
		//
	}
	
	public List<QualificationOption> getQualificationOptions(Long teamid, int catId, boolean closed) {
		// hash dcatName and dcatId
		String sql = "select * " //
				+ " from TCATEGORIES_DETAIL " //
				+ " where TEAMID  = ? " //
				+ " and CATID = ? " //
				+ " and CLOSED = ? ";
		Object[] params = new Object[] { teamid, catId, closed };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<QualificationOption> list = jdbcTemplate.query(sql, params, new QualificationOptionRowMapper());
		//
		return list;
	}
	
	public List<QualificationOption> getQualificationOptions(Long teamid, int catId) {
		return getQualificationOptions(teamid, catId, false);
	}
}

package com.jobdiva.api.dao.company;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.Note;
import com.jobdiva.api.model.authenticate.JobDivaSession;


@Component
public class GetCompanyNotesDao extends AbstractJobDivaDao {
	
	
	public List <Note> getNotes(JobDivaSession jobDivaSession, Long companyId) {
		String sql = "SELECT a.note, a.datecreated, b.firstname, b.lastname, b.id "
				+ "FROM tcustomercompanynotes a, trecruiter b "
				+ "WHERE a.companyid=? and a.recruiter_teamid=? and a.recruiterid=b.id order by a.datecreated desc";
		Object[] params = new Object[] {companyId, jobDivaSession.getTeamId() };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Note> notes = jdbcTemplate.query(sql, params, new RowMapper<Note>() {
			
			@Override
			public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
				Note noteObj = new Note();
				//
				noteObj.setId(companyId);
				noteObj.setDate(rs.getDate("DATECREATED"));
				noteObj.setContent(rs.getString("NOTE"));
				noteObj.setRecruiterId(rs.getLong("ID"));
				noteObj.setRecruiterName(rs.getString("FIRSTNAME")+" "+rs.getString("LASTNAME"));
				//
				return noteObj;
			}
		});
		return  notes;
		
	}
	
}
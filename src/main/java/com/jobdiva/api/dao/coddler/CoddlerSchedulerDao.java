package com.jobdiva.api.dao.coddler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.setup.JobDivaConnectivity;
import com.jobdiva.api.model.controller.Coddler;
import com.jobdiva.api.model.controller.CoddlerScheduler;
import com.jobdiva.api.model.controller.Configuration;
import com.jobdiva.api.model.controller.FixedTime;
import com.jobdiva.api.model.controller.Parameter;

@Component
public class CoddlerSchedulerDao extends AbstractJobDivaDao {
	
	//
	@Autowired
	JobDivaConnectivity	jobDivaConnectivity;
	//
	//
	SimpleDateFormat	dateFormat	= new SimpleDateFormat("HH:mm");
	
	private Timestamp strToTimeStamp(String strTime) {
		try {
			if (strTime != null && !strTime.isEmpty()) {
				return new Timestamp(dateFormat.parse(strTime).getTime());
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	private String timeStampToStr(Timestamp value) {
		try {
			if (value != null) {
				return dateFormat.format(value.getTime());
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	private JdbcTemplate getJdbcTemplate(Long teamId) {
		return jobDivaConnectivity.getJdbcTemplate(teamId);
	}
	
	public List<Coddler> getCoddlers(Integer machineId) {
		List<Coddler> list = new ArrayList<Coddler>();
		for (JdbcTemplate jdbctemplate : jobDivaConnectivity.getJdbcsTemplates()) {
			List<Coddler> teamIdList = getCoddlers(machineId, jdbctemplate, null);
			if (teamIdList != null && teamIdList.size() > 0) {
				list.addAll(teamIdList);
			}
		}
		//
		return list;
	}
	
	public List<Coddler> getCoddlers(Integer machineId, Long teamId) {
		JdbcTemplate jdbctemplate = getJdbcTemplate(teamId);
		//
		return getCoddlers(machineId, jdbctemplate, teamId);
	}
	
	private List<Coddler> getCoddlers(Integer machineId, JdbcTemplate jdbctemplate, Long teamId) {
		//
		//
		String sql = "SELECT ID, MACHINEID, TEAMID, SITE, CODLERTYPEID, NAME, ACTIVE, TIMEZONE, EXECUTABLEFILE, ENABLECLEANLOGS, ENABLECLEANIMAGES, LOGEXPDAYS, IMAGEEXPDAYS " //
				+ " FROM TCONTROLLER_CODDLER  " //
				+ " WHERE MACHINEID = ? ";
		//
		if (teamId != null) {
			sql += "AND TEAMID = ? ";
		}
		//
		Object[] params = teamId != null ? new Object[] { machineId, teamId } : new Object[] { machineId };
		//
		List<Coddler> list = jdbctemplate.query(sql, params, new RowMapper<Coddler>() {
			
			@Override
			public Coddler mapRow(ResultSet rs, int rowNum) throws SQLException {
				Coddler coddler = new Coddler();
				coddler.setId(rs.getInt("ID"));
				coddler.setSiteName(rs.getString("SITE"));
				coddler.setCoddlerTypeId(rs.getInt("CODLERTYPEID"));
				coddler.setMachineId(rs.getInt("MACHINEID"));
				coddler.setTeamId(rs.getLong("TEAMID"));
				coddler.setActive(rs.getBoolean("ACTIVE"));
				coddler.setName(rs.getString("NAME"));
				coddler.setTimezone(rs.getString("TIMEZONE"));
				coddler.setExecutableFile(rs.getString("EXECUTABLEFILE"));
				//
				coddler.setEnableCleanLogs(rs.getBoolean("ENABLECLEANLOGS"));
				coddler.setEnableCleanImages(rs.getBoolean("ENABLECLEANIMAGES"));
				coddler.setLogExpDays(rs.getInt("LOGEXPDAYS"));
				coddler.setImageExpDays(rs.getInt("IMAGEEXPDAYS"));
				return coddler;
			}
		});
		//
		if (list != null) {
			for (Coddler coddler : list) {
				//
				calculateCoddlerParams(coddler);
				//
				//
				sql = "SELECT ID, MACHINEID, TEAMID, STARTTIME, ENDTIME, FREQUENCYINMINUTES , MODETYPEID  " //
						+ " FROM TCONTROLLER_SCHEDULER " //
						+ " WHERE CODDLERID = ? ";
				//
				params = new Object[] { coddler.getId() };
				//
				List<CoddlerScheduler> schedulerList = jdbctemplate.query(sql, params, new RowMapper<CoddlerScheduler>() {
					
					@Override
					public CoddlerScheduler mapRow(ResultSet rs, int rowNum) throws SQLException {
						CoddlerScheduler coddlerScheduler = new CoddlerScheduler();
						//
						coddlerScheduler.setId(rs.getInt("ID"));
						coddlerScheduler.setCompanyId(rs.getString("MACHINEID"));
						coddlerScheduler.setTeamId(rs.getLong("TEAMID"));
						//
						coddlerScheduler.setStartTime(rs.getTimestamp("STARTTIME"), dateFormat);
						coddlerScheduler.setEndTime(rs.getTimestamp("ENDTIME"), dateFormat);
						coddlerScheduler.setFrequency(rs.getInt("FREQUENCYINMINUTES"));
						coddlerScheduler.setModeTypeId(rs.getInt("MODETYPEID"));
						//
						//
						coddlerScheduler.setStrStartTime(timeStampToStr(coddlerScheduler.getStartTime()));
						coddlerScheduler.setStrEndTime(timeStampToStr(coddlerScheduler.getEndTime()));
						//
						return coddlerScheduler;
					}
				});
				//
				if (schedulerList != null) {
					for (CoddlerScheduler coddlerScheduler : schedulerList) {
						calculateSchedularParams(coddlerScheduler);
						//
						//
						calculateFixedTime(coddlerScheduler);
					}
				}
				//
				coddler.setSchedulers(schedulerList);
			}
		}
		return list;
	}
	
	public List<String> getSites(Long teamId) {
		JdbcTemplate jdbctemplate = getJdbcTemplate(teamId);
		//
		String sqlParam = "SELECT SITE " //
				+ " FROM TSPIDERSWEBSITES " //
				+ " WHERE TEAMID = ? " //
				+ " ORDER BY SITE ";
		Object[] param = new Object[] { teamId };
		//
		List<String> list = jdbctemplate.query(sqlParam, param, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return rs.getString("SITE");
				//
			}
		});
		//
		return list;
	}
	
	private void calculateCoddlerParams(Coddler coddler) {
		String sqlParam = "SELECT * FROM TCONTROLLER_PARAMETER WHERE CODDLERID = ? ";
		Object[] param = new Object[] { coddler.getId() };
		//
		JdbcTemplate jdbctemplate = getJdbcTemplate(coddler.getTeamId());
		//
		List<Parameter> apramList = jdbctemplate.query(sqlParam, param, new RowMapper<Parameter>() {
			
			@Override
			public Parameter mapRow(ResultSet rs, int rowNum) throws SQLException {
				Parameter parameter = new Parameter();
				//
				parameter.setParamName(rs.getString("NAME"));
				//
				return parameter;
			}
		});
		//
		coddler.setGlobalParameters(apramList);
	}
	
	private void calculateSchedularParams(CoddlerScheduler coddlerScheduler) {
		String sqlParams = "SELECT * FROM TCONTROLLER_PARAMETER WHERE SCHEDULERID = ? ";
		Object[] paramList = new Object[] { coddlerScheduler.getId() };
		//
		JdbcTemplate jdbctemplate = getJdbcTemplate(coddlerScheduler.getTeamId());
		//
		List<Parameter> apramList = jdbctemplate.query(sqlParams, paramList, new RowMapper<Parameter>() {
			
			@Override
			public Parameter mapRow(ResultSet rs, int rowNum) throws SQLException {
				Parameter parameter = new Parameter();
				//
				parameter.setParamName(rs.getString("NAME"));
				//
				return parameter;
			}
		});
		//
		coddlerScheduler.setParameters(apramList);
	}
	
	private void calculateFixedTime(CoddlerScheduler coddlerScheduler) {
		String sqlFixedTime = "SELECT * FROM TCONTROLLER_SCHFIXTIME WHERE SCHEDULERID = ? ";
		Object[] paramsFixedTime = new Object[] { coddlerScheduler.getId() };
		//
		JdbcTemplate jdbctemplate = getJdbcTemplate(coddlerScheduler.getTeamId());
		//
		List<FixedTime> fixedTimeList = jdbctemplate.query(sqlFixedTime, paramsFixedTime, new RowMapper<FixedTime>() {
			
			@Override
			public FixedTime mapRow(ResultSet rs, int rowNum) throws SQLException {
				FixedTime fixedTime = new FixedTime();
				//
				fixedTime.setValue(rs.getTimestamp("FIXEDTIME"));
				fixedTime.setStrValue(timeStampToStr(fixedTime.getValue()));
				//
				return fixedTime;
			}
		});
		//
		coddlerScheduler.setFixedTimes(fixedTimeList);
	}
	
	public Boolean saveCoddlers(List<Coddler> coddlers) {
		//
		//
		if (coddlers.size() > 0) {
			Coddler coddler = coddlers.get(0);
			//
			JdbcTemplate jdbctemplate = getJdbcTemplate(coddler.getTeamId());
			//
			String sqlDelete = "DELETE FROM TCONTROLLER_CODDLER WHERE MACHINEID = ? AND TEAMID = ? ";
			Object[] params = new Object[] { coddler.getMachineId(), coddler.getTeamId() };
			jdbctemplate.update(sqlDelete, params);
			//
			sqlDelete = "DELETE FROM TCONTROLLER_SCHEDULER WHERE MACHINEID = ? AND TEAMID = ? ";
			jdbctemplate.update(sqlDelete, params);
			//
		}
		//
		//
		for (Coddler coddler : coddlers) {
			//
			JdbcTemplate jdbctemplate = getJdbcTemplate(coddler.getTeamId());
			//
			if (coddler.getId() != null) {
				String sqlDelete = "DELETE FROM TCONTROLLER_PARAMETER WHERE CODDLERID = ? ";
				Object[] params = new Object[] { coddler.getId() };
				jdbctemplate.update(sqlDelete, params);
				//
			}
			//
			String sql = "SELECT TCONTROLLER_CODDLER_SEQ.nextval  AS id  FROM dual";
			List<Integer> listLong = jdbctemplate.query(sql, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt("id");
				}
			});
			Integer coddlerId = listLong != null && listLong.size() > 0 ? listLong.get(0) : 0;
			//
			//
			String sqlInsert = "INSERT INTO TCONTROLLER_CODDLER(Id, MACHINEID, TEAMID ,  CODLERTYPEID, NAME, EXECUTABLEFILE, ACTIVE, TIMEZONE, " //
					+ " ENABLECLEANLOGS, ENABLECLEANIMAGES, LOGEXPDAYS, IMAGEEXPDAYS,SITE) " //
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )  ";
			//
			jdbctemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sqlInsert);
				ps.setInt(1, coddlerId);
				ps.setInt(2, coddler.getMachineId());
				ps.setLong(3, coddler.getTeamId());
				ps.setInt(4, coddler.getCoddlerTypeId());
				ps.setString(5, coddler.getName());
				ps.setString(6, coddler.getExecutableFile());
				ps.setBoolean(7, coddler.getActive());
				ps.setString(8, coddler.getTimezone());
				ps.setBoolean(9, coddler.getEnableCleanLogs());
				ps.setBoolean(10, coddler.getEnableCleanImages());
				ps.setInt(11, coddler.getLogExpDays());
				ps.setInt(12, coddler.getImageExpDays());
				ps.setString(13, coddler.getSiteName());
				return ps;
			});
			//
			if (coddler.getGlobalParameters() != null) {
				for (Parameter parameter : coddler.getGlobalParameters()) {
					String sqlInsertParam = "INSERT INTO TCONTROLLER_PARAMETER(CODDLERID, NAME) VALUES(?, ? )";
					Object[] params = new Object[] { coddlerId, parameter.getParamName() };
					jdbctemplate.update(sqlInsertParam, params);
				}
			}
			//
			if (coddler.getSchedulers() != null) {
				//
				//
				//
				for (CoddlerScheduler coddlerScheduler : coddler.getSchedulers()) {
					//
					//
					if (coddlerScheduler.getId() != null) {
						String sqlDelete = "DELETE FROM TCONTROLLER_PARAMETER WHERE SCHEDULERID = ? ";
						Object[] params = new Object[] { coddlerScheduler.getId() };
						jdbctemplate.update(sqlDelete, params);
						//
						sqlDelete = "DELETE FROM TCONTROLLER_SCHFIXTIME WHERE SCHEDULERID = ? ";
						params = new Object[] { coddlerScheduler.getId() };
						jdbctemplate.update(sqlDelete, params);
					}
					//
					sql = "SELECT TCONTROLLER_SCHEDULER_SEQ.nextval  AS id  FROM dual";
					listLong = jdbctemplate.query(sql, new RowMapper<Integer>() {
						
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getInt("id");
						}
					});
					Integer coddlerSchedulerId = listLong != null && listLong.size() > 0 ? listLong.get(0) : 0;
					//
					String sqlInsertScheduler = "INSERT INTO TCONTROLLER_SCHEDULER( Id, MACHINEID, TEAMID ,CODDLERID, MODETYPEID, STARTTIME, ENDTIME, FREQUENCYINMINUTES ) " //
							+ " VALUES ( ? ,?, ?, ?, ?, ? , ?, ?)  ";
					//
					String strStartTime = coddlerScheduler.getStrStartTime();
					String strEndTime = coddlerScheduler.getStrEndTime();
					//
					Timestamp startTimestamp = strToTimeStamp(strStartTime);
					Timestamp endTimestamp = strToTimeStamp(strEndTime);
					//
					//
					jdbctemplate.update(connection -> {
						PreparedStatement ps = connection.prepareStatement(sqlInsertScheduler);
						ps.setInt(1, coddlerSchedulerId);
						ps.setInt(2, coddler.getMachineId());
						ps.setLong(3, coddler.getTeamId());
						ps.setInt(4, coddlerId);
						ps.setInt(5, coddlerScheduler.getModeTypeId());
						ps.setTimestamp(6, startTimestamp);
						ps.setTimestamp(7, endTimestamp);
						ps.setInt(8, coddlerScheduler.getFrequency());
						return ps;
					});
					//
					//
					if (coddlerScheduler.getParameters() != null) {
						for (Parameter parameter : coddlerScheduler.getParameters()) {
							String sqlInsertParam = "INSERT INTO TCONTROLLER_PARAMETER(SCHEDULERID, NAME) VALUES(?, ? )";
							Object[] params = new Object[] { coddlerSchedulerId, parameter.getParamName() };
							jdbctemplate.update(sqlInsertParam, params);
						}
					}
					//
					if (coddlerScheduler.getFixedTimes() != null) {
						for (FixedTime fixedTime : coddlerScheduler.getFixedTimes()) {
							String sqlInsertParam = "INSERT INTO TCONTROLLER_SCHFIXTIME(SCHEDULERID, FIXEDTIME) VALUES(?,?)";
							//
							Timestamp fixedTimeValue = strToTimeStamp(fixedTime.getStrValue());
							//
							Object[] params = new Object[] { coddlerSchedulerId, fixedTimeValue };
							jdbctemplate.update(sqlInsertParam, params);
						}
					}
				}
			}
		}
		//
		return true;
		//
	}
	
	public Configuration getConfiguration(Integer machineId) {
		for (JdbcTemplate jdbctemplate : jobDivaConnectivity.getJdbcsTemplates()) {
			Configuration configuration = getConfiguration(machineId, jdbctemplate, null);
			if (configuration != null) {
				return configuration;
			}
		}
		//
		return null;
	}
	
	public Configuration getConfiguration(Integer machineId, Long teamId) {
		JdbcTemplate jdbctemplate = getJdbcTemplate(teamId);
		//
		return getConfiguration(machineId, jdbctemplate, teamId);
	}
	
	public Configuration getConfiguration(Integer machineId, JdbcTemplate jdbctemplate, Long teamId) {
		//
		//
		String sql = "SELECT MACHINEID, TEAMID, RESTARTDAY, RESTARTTIME  " //
				+ " FROM TCONTROLLER_CONFIGURATION  " //
				+ " WHERE MACHINEID = ?  ";
		//
		//
		if (teamId != null) {
			sql += "AND TEAMID = ? ";
		}
		//
		Object[] params = teamId != null ? new Object[] { machineId, teamId } : new Object[] { machineId };
		//
		List<Configuration> list = jdbctemplate.query(sql, params, new RowMapper<Configuration>() {
			
			@Override
			public Configuration mapRow(ResultSet rs, int rowNum) throws SQLException {
				Configuration configuration = new Configuration();
				configuration.setMachineId(rs.getInt("MACHINEID"));
				configuration.setTeamId(rs.getLong("TEAMID"));
				configuration.setDay(rs.getInt("RESTARTDAY"));
				configuration.setRestartMachine(rs.getTimestamp("RESTARTTIME"));
				//
				configuration.setStrRestartMachine(timeStampToStr(configuration.getRestartMachine()));
				//
				return configuration;
			}
		});
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	
	public void saveConfiguration(Configuration configuration) {
		//
		JdbcTemplate jdbctemplate = getJdbcTemplate(configuration.getTeamId());
		//
		String sqlDelete = "DELETE FROM TCONTROLLER_CONFIGURATION WHERE MACHINEID = ? AND TEAMID = ? ";
		Object[] params = new Object[] { configuration.getMachineId(), configuration.getTeamId() };
		jdbctemplate.update(sqlDelete, params);
		//
		String sqlInsert = "INSERT INTO TCONTROLLER_CONFIGURATION(Id, MACHINEID, TEAMID , RESTARTDAY , RESTARTTIME ) VALUES ( TCONTROLLER_CONFIGURATION_SEQ.nextval ,?, ?, ?, ? )  ";
		//
		jdbctemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert);
			ps.setInt(1, configuration.getMachineId());
			ps.setLong(2, configuration.getTeamId());
			ps.setInt(3, configuration.getDay());
			//
			Timestamp restartTimestamp = strToTimeStamp(configuration.getStrRestartMachine());
			//
			ps.setTimestamp(4, restartTimestamp);
			return ps;
		});
		//
	}
	
	public Integer saveMachineId(String machineId) throws Exception {
		//
		Integer returnId = null;
		//
		Integer failure = 0;
		StringBuffer buffer = new StringBuffer("");
		for (JdbcTemplate jdbctemplate : jobDivaConnectivity.getJdbcsTemplates()) {
			try {
				String sql = "SELECT TCONTROLLER_MACHINE_SEQ.nextval  AS id  FROM dual";
				logger.info("saveMachineId :: " + sql);
				List<Integer> list = jdbctemplate.query(sql, new RowMapper<Integer>() {
					
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt("id");
					}
				});
				Integer id = list != null && list.size() > 0 ? list.get(0) : 0;
				String sqlInsert = "INSERT INTO TCONTROLLER_MACHINE(Id, MACHINE ) VALUES ( ? ,? )  ";
				logger.info("saveMachineId ::" + sqlInsert);
				//
				jdbctemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sqlInsert);
					ps.setInt(1, id);
					ps.setString(2, machineId);
					return ps;
				});
				//
				if (returnId == null) {
					returnId = id;
				}
			} catch (Exception e) {
				buffer.append(e.getMessage());
				failure++;
				logger.info("saveMachineId  Exception :: " + e.getMessage());
			}
		}
		//
		if (failure.equals(jobDivaConnectivity.getJdbcsTemplates().size())) {
			throw new Exception(buffer.toString());
		}
		//
		return returnId;
		//
	}
}

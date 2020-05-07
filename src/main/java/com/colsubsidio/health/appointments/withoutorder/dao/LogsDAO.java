package com.colsubsidio.health.appointments.withoutorder.dao;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;

/**
 *
 * @author LordMVP
 */
@Component
public class LogsDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private LogsManager logsManager;

	private static String exception = "exception";

	@Value("${colsubsidio.procedures.logs.insert}")
	private String PR_COLSSALUD_LOGS_I_RESERVATION;

	/**
	 *
	 * @param logAction
	 * @param logMessage
	 * @return
	 */
	@Async
	public void createLog(String logAction, String logMessage) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("accion", logAction);
			params.addValue("message", logMessage);

			int update = jdbcTemplate.update(PR_COLSSALUD_LOGS_I_RESERVATION, params);
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception, "LogsDAO; createLog; " + ex.getMessage());
		} finally {
			try {
				if (jdbcTemplate != null && jdbcTemplate.getJdbcTemplate() != null) {
					jdbcTemplate.getJdbcTemplate().getDataSource().getConnection().close();
				}
			} catch (SQLException ex) {
				logsManager.logsBuildAppInsights(exception, "LogsDAO; getDataSource; " + ex.getMessage());
			}
		}
	}
}

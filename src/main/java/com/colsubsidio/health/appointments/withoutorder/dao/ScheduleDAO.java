package com.colsubsidio.health.appointments.withoutorder.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.model.Schedule;
import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;

/**
 *
 * @author LordMVP
 */
@Component
public class ScheduleDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private LogsManager logsManager;

	private static String exception = "exception";

	@Value("${colsubsidio.procedures.schedule.insert}")
	private String PR_COLSSALUD_SCHEDULE_I_RESERVATION;
	@Value("${colsubsidio.procedures.schedule.update}")
	private String PR_COLSSALUD_SCHEDULE_U_RESERVATION;
	@Value("${colsubsidio.procedures.schedule.select}")
	private String PR_COLSSALUD_SCHEDULE_S_RESERVATION;

	@Value("${apigee.path.logger}")
	private String apigeePathLogger;
	
	public void insertSchedule(Schedule schedule) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("p_date", schedule.getDate());
			params.addValue("p_order", schedule.getOrder());
			params.addValue("p_reservation", schedule.getReservation());
			params.addValue("p_specialty", schedule.getSpecialty());
			params.addValue("p_state", schedule.getState());
			params.addValue("p_type_document", schedule.getDocumentType());
			params.addValue("p_document_number", schedule.getDocumentNumber());
			params.addValue("p_data", schedule.getData());
			params.addValue("p_cancellation", schedule.getCancellation());
			params.addValue("p_modified", schedule.getModified());
			params.addValue("p_modified_by", schedule.getModifiedBy());
			params.addValue("p_created", schedule.getCreated());
			params.addValue("p_created_by", schedule.getCreatedBy());

			jdbcTemplate.update(PR_COLSSALUD_SCHEDULE_I_RESERVATION, params);
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception,
					"ScheduleDAO; insertSchedule; data: " + schedule.toString() + " ; " + ex.getMessage());
		} finally {
			try {
				jdbcTemplate.getJdbcTemplate().getDataSource().getConnection().close();
			} catch (SQLException ex) {
				logsManager.logsBuildAppInsights(exception,
						"ScheduleDAO; insertSchedule; getDataSource: " + schedule.toString() + " ; " + ex.getMessage());
			}
		}
	}

	public void updateSchedule(Schedule schedule) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("p_order", schedule.getOrder());
			params.addValue("p_reservation", schedule.getReservation());
			params.addValue("p_specialty", schedule.getSpecialty());
			params.addValue("p_state", schedule.getState());
			params.addValue("p_type_document", schedule.getDocumentType());
			params.addValue("p_document_number", schedule.getDocumentNumber());
			params.addValue("p_cancellation", schedule.getCancellation());
			params.addValue("p_modified", schedule.getModified());
			params.addValue("p_modified_by", schedule.getModifiedBy());

			jdbcTemplate.update(PR_COLSSALUD_SCHEDULE_U_RESERVATION, params);
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception,
					"ScheduleDAO; updateSchedule; data: " + schedule.toString() + " ; " + ex.getMessage());
		} finally {
			try {
				jdbcTemplate.getJdbcTemplate().getDataSource().getConnection().close();
			} catch (SQLException ex) {
				logsManager.logsBuildAppInsights("exception",
						"ScheduleDAO; updateSchedule; getDataSource: " + schedule.toString() + " ; " + ex.getMessage());
			}
		}
	}

	public List<Schedule> selectSchedule() {

		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		RowMapper<Schedule> rowMapper = new BeanPropertyRowMapper<>(Schedule.class);
		List<Schedule> list = null;
		
		try {
			sql.append(PR_COLSSALUD_SCHEDULE_S_RESERVATION);

			list = this.jdbcTemplate.query(sql.toString(), params, rowMapper);

			if (list.isEmpty()) {
				list = null;
			}
			return list;
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception, "ScheduleDAO; selectSchedule; " + ex.getMessage());
		} finally {
			try {
				DataSourceUtils.getConnection(this.jdbcTemplate.getJdbcTemplate().getDataSource()).close();
			} catch (SQLException ex) {
				logsManager.logsBuildAppInsights(exception, "ScheduleDAO; getDataSource; " + ex.getMessage());
			}
		}
		return list;
	}

}

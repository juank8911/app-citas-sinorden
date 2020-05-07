package com.colsubsidio.health.appointments.withoutorder.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.model.Services;
import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;

/**
 *
 * @author LordMVP
 */
@Component
public class ServicesDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private LogsManager logsManager;

	/**
	 *
	 * @param name
	 * @return
	 */
	public Services getServicioByName(String name) {

		try {
			StringBuilder sql = new StringBuilder();
			MapSqlParameterSource params = new MapSqlParameterSource();

			sql.append("CALL PR_COLSSALUD_S_SERVICIO(:nameService)");
			params.addValue("nameService", name);

			List<Services> listServices = this.jdbcTemplate.query(sql.toString(), params,
					new BeanPropertyRowMapper<Services>(Services.class));

			if (listServices.isEmpty()) {
				return null;
			} else {
				return listServices.get(0);
			}

		} catch (Exception ex) {
			logsManager.logsBuildAppInsights("exception", "ServicesDAO; getServicioByName; " + ex.getMessage());
		} finally {
			try {
				DataSourceUtils.getConnection(jdbcTemplate.getJdbcTemplate().getDataSource()).close();
			} catch (SQLException ex) {
				Logger.getLogger(ServicesDAO.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return null;
	}
}

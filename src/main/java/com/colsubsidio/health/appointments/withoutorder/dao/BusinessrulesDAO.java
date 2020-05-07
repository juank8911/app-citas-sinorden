package com.colsubsidio.health.appointments.withoutorder.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.model.BusinessRules;
import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;

/**
 *
 * @author LordMVP
 */
@Component
public class BusinessrulesDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private LogsManager logsManager;

	/**
	 *
	 * @param tipo
	 * @return
	 */
	public List<BusinessRules> getRules(String tipo) {

		List<BusinessRules> listRules = null;

		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();

		try {

			sql.append("CALL PR_COLSSALUD_S_BRULES(:tipo)");
			params.addValue("tipo", tipo);
			listRules = this.jdbcTemplate.query(sql.toString(), params,
					new BeanPropertyRowMapper<BusinessRules>(BusinessRules.class));

			if (listRules.isEmpty()) {
				return null;
			} else {
				return listRules;
			}

		} catch (Exception ex) {
			logsManager.logsBuildAppInsights("exception", "BusinessrulesDAO; getRules; " + ex.getMessage());
		} finally {
			try {
				jdbcTemplate.getJdbcTemplate().getDataSource().getConnection().close();
			} catch (SQLException ex) {
				Logger.getLogger(BusinessrulesDAO.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return listRules;
	}

}

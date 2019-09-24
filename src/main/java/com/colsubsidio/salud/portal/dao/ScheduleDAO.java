package com.colsubsidio.salud.portal.dao;

import com.colsubsidio.salud.portal.common.CommonConstantsDB;
import com.colsubsidio.salud.portal.common.PropertiesLoader;
import com.colsubsidio.salud.portal.dao.interfaces.IScheduleDAO;
import com.colsubsidio.salud.portal.models.Schedule;
import com.colsubsidio.salud.transversal.utils.LogsManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author Usuario
 */
@Component
public class ScheduleDAO implements IScheduleDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private LogsManager logsManager;

    @Override
    public void insertSchedule(Schedule schedule) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("p_date", schedule.getDate());
            params.addValue("p_order", schedule.getOrder());
            params.addValue("p_reservation", schedule.getReservation());
            params.addValue("p_specialty", schedule.getSpecialty());
            params.addValue("p_state", schedule.getState());
            params.addValue("p_type_document", schedule.getType_document());
            params.addValue("p_document_number", schedule.getDocument_number());
            params.addValue("p_data", schedule.getData());
            params.addValue("p_cancellation", schedule.getCancellation());
            params.addValue("p_modified", schedule.getModified());
            params.addValue("p_modified_by", schedule.getModified_by());
            params.addValue("p_created", schedule.getCreated());
            params.addValue("p_created_by", schedule.getCreated_by());

            jdbcTemplate.update(
                    PropertiesLoader.getInstance().getProperty(CommonConstantsDB.PR_COLSSALUD_SCHEDULE_I_RESERVATION),
                    params);
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                logsManager.LogsBuildAppInsights("exception",
                        "ScheduleDAO; insertSchedule; data: " + schedule.toString() + " ; " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                jdbcTemplate.getJdbcTemplate().getDataSource().getConnection().close();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void updateSchedule(Schedule schedule) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("p_order", schedule.getOrder());
            params.addValue("p_reservation", schedule.getReservation());
            params.addValue("p_specialty", schedule.getSpecialty());
            params.addValue("p_state", schedule.getState());
            params.addValue("p_type_document", schedule.getType_document());
            params.addValue("p_document_number", schedule.getDocument_number());
            params.addValue("p_cancellation", schedule.getCancellation());
            params.addValue("p_modified", schedule.getModified());
            params.addValue("p_modified_by", schedule.getModified_by());

            jdbcTemplate.update(
                    PropertiesLoader.getInstance().getProperty(CommonConstantsDB.PR_COLSSALUD_SCHEDULE_U_RESERVATION),
                    params);
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                logsManager.LogsBuildAppInsights("exception",
                        "ScheduleDAO; updateSchedule; data: " + schedule.toString() + " ; " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                jdbcTemplate.getJdbcTemplate().getDataSource().getConnection().close();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Schedule> selectSchedule() {
        try {
            StringBuilder sql = new StringBuilder();
            MapSqlParameterSource params = new MapSqlParameterSource();
            RowMapper<Schedule> rowMapper = new BeanPropertyRowMapper(Schedule.class);

            sql.append(PropertiesLoader.getInstance().getProperty(CommonConstantsDB.PR_COLSSALUD_SCHEDULE_S_RESERVATION));

            List<Schedule> list = this.jdbcTemplate.query(sql.toString(), params, rowMapper);

            if (list.isEmpty()) {
                return null;
            }
            return list;
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "ScheduleDAO; selectSchedule; " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                DataSourceUtils.getConnection(this.jdbcTemplate.getJdbcTemplate().getDataSource()).close();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}

package com.colsubsidio.salud.transversal.dao;

import com.colsubsidio.salud.transversal.dao.interfaces.ILogsDAO;
import com.colsubsidio.salud.transversal.utils.LogsManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Usuario
 */
@Component
public class LogsDAO implements ILogsDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private LogsManager logsManager;

    /**
     *
     * @param logAction
     * @param logMessage
     * @return
     */
    @Override
    public int createLog(String logAction, String logMessage) {
        int update = 0;
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("accion", logAction);
            params.addValue("message", logMessage);

            update = jdbcTemplate.update("CALL PR_COLSSALUD_I_LOGS(:accion, :message)", params);
            return update;
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "ILogsDAO; createLog; " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(LogsDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                jdbcTemplate.getJdbcTemplate().getDataSource().getConnection().close();
            } catch (SQLException ex) {
                Logger.getLogger(LogsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return update;
    }
}

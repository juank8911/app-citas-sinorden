package com.colsubsidio.salud.portal.dao;

import com.colsubsidio.salud.portal.dao.interfaces.ILogsDAO;
import com.colsubsidio.salud.portal.models.Services;
import java.sql.ResultSet;
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
import com.colsubsidio.salud.portal.dao.interfaces.IServicesDAO;
import com.colsubsidio.salud.portal.utils.LogsManager;
import java.io.IOException;

/**
 *
 * @author Usuario
 */
@Component
public class ServicesDAO implements IServicesDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ILogsDAO LogsDAO;

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

            List<Services> listServices;
            listServices = jdbcTemplate.query(sql.toString(), params,
                    new RowMapper<Services>() {
                public Services mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Services service = new Services();
                    service.setServ_id(rs.getInt("serv_id"));
                    service.setServ_name(rs.getString("serv_name"));
                    service.setServ_url(rs.getString("serv_url"));
                    service.setServ_apikey(rs.getString("serv_apikey"));
                    service.setServ_authorization(rs.getString("serv_authorization"));
                    service.setServ_parameters(rs.getString("serv_parameters"));
                    service.setServ_method(rs.getString("serv_method"));
                    service.setClient_secret(rs.getString("client_secret"));
                    service.setClient_id(rs.getString("client_id"));
                    return service;
                }
            });

            if (listServices.isEmpty()) {
                return null;
            } else {
                return listServices.get(0);
            }

        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "ServicesDAO; getServicioByName; " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(ServicesDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
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

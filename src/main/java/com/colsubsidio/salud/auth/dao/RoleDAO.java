package com.colsubsidio.salud.auth.dao;

import com.colsubsidio.salud.auth.dao.interfaces.IRoleDAO;
import com.colsubsidio.salud.auth.models.Role;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoleDAO implements IRoleDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Get Roles
     *
     * @return List Role
     */
    @Override
    public List<Role> getRoles() {
        String sql = "";

        sql = "SELECT "
                + "id, "
                + "name "
                + "FROM roles";
        RowMapper<Role> rowMapper = new BeanPropertyRowMapper(Role.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * Get Roles
     *
     * @return List Role
     */
    @Override
    public List<Role> getRolesByUser(String id) {
        String sql = "";

        MapSqlParameterSource params = new MapSqlParameterSource();

        try {
            sql = "CALL PR_COLSSALUD_EMPLOY_S_ROLES(:id)";
            params.addValue("id", id);

            RowMapper<Role> rowMapper = new BeanPropertyRowMapper(Role.class);
            List<Role> list = this.jdbcTemplate.query(sql.toString(), params, rowMapper);

            if (!list.isEmpty()) {
                return list;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                jdbcTemplate.getJdbcTemplate().getDataSource().getConnection().close();
            } catch (SQLException ex) {
                Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

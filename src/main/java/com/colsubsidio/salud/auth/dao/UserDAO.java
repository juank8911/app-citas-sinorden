package com.colsubsidio.salud.auth.dao;

import com.colsubsidio.salud.auth.dao.interfaces.IUserDAO;
import com.colsubsidio.salud.auth.models.User;
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
public class UserDAO implements IUserDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Get User by username excluding one id
     *
     * @param username String
     * @param excludeId string
     * @return User
     */
    @Override
    public User getUserByUserName(String username) {
        String sql = "";
        MapSqlParameterSource params = new MapSqlParameterSource();
        sql = "CALL PR_COLSSALUD_EMPLOY_S_USER_V2(:username)";
        params.addValue("username", username);

        try {
            RowMapper<User> rowMapper = new BeanPropertyRowMapper(User.class);
            List<User> list = this.jdbcTemplate.query(sql.toString(), params, rowMapper);

            if (!list.isEmpty()) {
                return list.get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                jdbcTemplate.getJdbcTemplate().getDataSource().getConnection().close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

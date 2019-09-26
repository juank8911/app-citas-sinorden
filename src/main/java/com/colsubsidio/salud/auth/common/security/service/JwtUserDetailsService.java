package com.colsubsidio.salud.auth.common.security.service;

import com.colsubsidio.salud.auth.common.security.JwtUserFactory;
import com.colsubsidio.salud.auth.dao.interfaces.IRoleDAO;
import com.colsubsidio.salud.auth.dao.interfaces.IUserDAO;
import com.colsubsidio.salud.auth.models.Role;
import com.colsubsidio.salud.auth.models.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IRoleDAO roleDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            List<Role> listRol = new ArrayList();
            User user = userDAO.getUserByUserName(username);
            if (user == null) {
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            } else {
                user.setAuthorities(roleDAO.getRolesByUser(String.valueOf(user.getId())));
                return JwtUserFactory.create(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.auth.controllers.util;

import com.colsubsidio.salud.auth.common.security.JwtAuthenticationRequest;
import com.colsubsidio.salud.auth.common.security.JwtTokenUtil;
import com.colsubsidio.salud.auth.common.security.exception.AuthenticationException;
import com.colsubsidio.salud.auth.common.security.JwtUser;
import com.colsubsidio.salud.auth.common.security.JwtUserFactory;
import com.colsubsidio.salud.auth.common.security.service.JwtAuthenticationResponse;
import com.colsubsidio.salud.auth.dao.interfaces.IRoleDAO;
import com.colsubsidio.salud.auth.dao.interfaces.IUserDAO;
import com.colsubsidio.salud.auth.models.Role;
import com.colsubsidio.salud.auth.models.User;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil implements Serializable {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.application}")
    private String application;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    IUserDAO userDAO;

    @Autowired
    IRoleDAO roleDAO;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    public ResponseEntity<?> getObjectToken(JwtAuthenticationRequest authenticationRequest) throws UsernameNotFoundException {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        List<Role> listRol = new ArrayList();
        HashMap<String, Object> map = new HashMap<>();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        if (userDetails != null) {
            final String token = jwtTokenUtil.generateToken(userDetails);
            final String refreshToken = jwtTokenUtil.refreshToken(token);
            final Date expiration = jwtTokenUtil.getExpirationDateFromToken(token);
            final String username = jwtTokenUtil.getUsernameFromToken(token);
            final User user = userDAO.getUserByUserName(username);
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            user.setAuthorities(roleDAO.getRolesByUser(String.valueOf(user.getId())));

            map.put("cliente", application);
            if (user != null && (user.getAuthorities() != null && user.getAuthorities().size() > 0)) {
                map.put("token", "Bearer ".concat(token));
                map.put("refreshToken", "Bearer ".concat(refreshToken));
                map.put("fechaExpiracion", sdf.format(expiration));
                map.put("user", user);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> getRefreshToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastpasswordresetdate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Authenticates the user. If something is wrong, an
     * {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }

}

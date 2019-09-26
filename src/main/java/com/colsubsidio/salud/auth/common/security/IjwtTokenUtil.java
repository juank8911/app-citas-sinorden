/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.auth.common.security;

import com.colsubsidio.salud.auth.common.security.JwtAuthenticationRequest;
import java.util.HashMap;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Bits
 */
public interface IjwtTokenUtil {
    HashMap<String, Object> getObjectToken(JwtAuthenticationRequest authenticationRequest);
}

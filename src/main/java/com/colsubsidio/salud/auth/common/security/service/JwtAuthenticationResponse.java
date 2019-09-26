package com.colsubsidio.salud.auth.common.security.service;

import java.io.Serializable;
import java.util.HashMap;


public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    //private final String token;
    private HashMap<String, String> token = new HashMap<>();

    public JwtAuthenticationResponse(String token) {
        //this.token = token;
        
        this.token.put("token", token);
        this.token.put("refreshToken", token);
        this.token.put("foo", "bar");
        this.token.put("aa", "bb");
        //return map;
    }

    public HashMap<String, String> getToken() {
        return this.token;
    }
}

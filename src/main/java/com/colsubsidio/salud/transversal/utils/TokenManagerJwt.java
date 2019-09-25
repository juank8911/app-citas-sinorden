/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.utils;

import com.colsubsidio.salud.transversal.models.LoginUser;
import com.colsubsidio.salud.transversal.models.Properties;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author LordMVP
 */
@Component
public class TokenManagerJwt {

    @Autowired
    Properties properties;

    public String extracTokenJwt(String tokenId) {
        Claims claims = extractClaimsJWT(tokenId);
        Object obj = claims.get("customer");
        Gson gson = new Gson();
        String jsonString = gson.toJson(obj);
        LoginUser customer = gson.fromJson(jsonString, LoginUser.class);
        return customer.getTokenId();
    }

    public LoginUser extracUserJwt(String tokenId) {
        Claims claims = extractClaimsJWT(tokenId);
        Object obj = claims.get("customer");
        Gson gson = new Gson();
        String jsonString = gson.toJson(obj);
        LoginUser customer = gson.fromJson(jsonString, LoginUser.class);
        return customer;
    }

    public Claims extractClaimsJWT(String jwt) {
        Claims claimsResponse = null;
        try {
            claimsResponse = Jwts.parser().setSigningKey(properties.getJwtSecret()).parseClaimsJws(jwt).getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return claimsResponse;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.portal.services;

import com.colsubsidio.salud.portal.dao.interfaces.ILogsDAO;
import com.colsubsidio.salud.portal.models.BearerToken;
import com.colsubsidio.salud.portal.models.Services;
import com.colsubsidio.salud.portal.services.interfaces.IAuthService;
import com.colsubsidio.salud.portal.utils.LogsManager;
import com.colsubsidio.salud.portal.utils.HandleDate;
import com.colsubsidio.salud.portal.utils.ProcessChain;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.colsubsidio.salud.portal.dao.interfaces.IServicesDAO;
import com.colsubsidio.salud.portal.services.util.ServiceUtil;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author Usuario
 */
@Component
public class AuthService implements IAuthService {

    @Autowired
    private ProcessChain o;

    @Autowired
    private IServicesDAO serviciosDAO;

    @Autowired
    private LogsManager logsManager;

    @Autowired
    private ServiceUtil serviceUtil;

    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity<?> BearerToken() {
        try {

            Services serviciosVO = new Services();
            serviciosVO = (Services) serviciosDAO.getServicioByName("BearerToken");
            StringBuffer parameters = new StringBuffer();
            parameters.append("grant_type=client_credentials");

            String data = "client_secret=" + URLEncoder.encode(o.notEmpty(serviciosVO.getClient_secret(), ""))
                    + "&client_id=" + URLEncoder.encode(o.notEmpty(serviciosVO.getClient_id(), ""));

            return serviceUtil.consumeRestTemplateBearer(serviciosVO, data, parameters.toString());
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "AuthController; BearerToken; " + ex.getMessage().toString());
            } catch (IOException ex1) {
                Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> BearerTokenTest() {
        try {

            Services serviciosVO = new Services();
            serviciosVO = (Services) serviciosDAO.getServicioByName("BearerTokenTest");
            StringBuffer parameters = new StringBuffer();
            parameters.append("grant_type=client_credentials");

            String data = "client_secret=" + URLEncoder.encode(o.notEmpty(serviciosVO.getClient_secret(), ""))
                    + "&client_id=" + URLEncoder.encode(o.notEmpty(serviciosVO.getClient_id(), ""));

            return serviceUtil.consumeRestTemplateBearer(serviciosVO, data, parameters.toString());
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "AuthController; BearerTokenTest; " + ex.getMessage().toString());
            } catch (IOException ex1) {
                Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }
}

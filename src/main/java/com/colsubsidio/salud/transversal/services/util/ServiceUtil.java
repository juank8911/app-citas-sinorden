/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.services.util;

import com.colsubsidio.salud.transversal.common.PropertiesLoader;
import com.colsubsidio.salud.transversal.models.BearerToken;
import com.colsubsidio.salud.transversal.models.Response;
import com.colsubsidio.salud.transversal.models.Result;
import com.colsubsidio.salud.transversal.models.Services;
import com.colsubsidio.salud.transversal.utils.HandleDate;
import com.colsubsidio.salud.transversal.utils.LogsManager;
import com.colsubsidio.salud.transversal.utils.ProcessChain;

import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ServiceUtil {

    private RestTemplate restTemplate;

    @Autowired
    private LogsManager logsManager;

	public ResponseEntity<?> consumeRestTemplate(Services services, String body, String parametes, BearerToken token) {
        Response response = new Response();
        Result Result = new Result();
        ResponseEntity<?> responseEntity = null;
        try {
            restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=utf-8");
            headers.add("Accept", "application/json");
            headers.add(PropertiesLoader.getInstance().getProperty("service.web.authorization"), services.getServ_authorization().replace(PropertiesLoader.getInstance().getProperty("service.web.token.bearer"), token.getAccess_token() != null ? token.getAccess_token() : ""));
            body.getBytes(StandardCharsets.UTF_8);
            HttpEntity<?> entity = new HttpEntity<>(body, headers);
            responseEntity = restTemplate.exchange(services.getServ_url().concat("?").concat(parametes), HttpMethod.resolve(services.getServ_method()), entity, String.class);
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "ServiceUtil; consumeRestTemplate; services: " + services.toString() + "; body: " + body + "; parametes: " + parametes + "; exception: " + ex.getMessage());
                Result.setCode("0");
                Result.setDescription(ex.getMessage());
                response.setResult(Result);
                response.setData(null);
            } catch (IOException ex1) {
                Logger.getLogger(ServiceUtil.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    public ResponseEntity<?> consumeRestTemplate(Services services, String body, String parametes) {

        ResponseEntity<?> response = null;

        try {
            restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Accept", "*/*");
            body.getBytes(StandardCharsets.UTF_8);
            HttpEntity<?> entity = new HttpEntity<>(body, headers);

            response = restTemplate.exchange(services.getServ_url().concat("?").concat(parametes), HttpMethod.resolve(services.getServ_method()), entity, String.class);
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "ServiceUtil; consumeRestTemplate; services: " + services.toString() + "; body: " + body + "; parametes: " + parametes + "; exception: " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(ServiceUtil.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return response;
    }

    public ResponseEntity<?> consumeRestTemplateBearer(Services services, String body, String parametes) {

        ResponseEntity<?> response = null;

        try {
            restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            headers.add("Accept", "*/*");
            body.getBytes(StandardCharsets.UTF_8);
            HttpEntity<?> entity = new HttpEntity<>(body, headers);

            response = restTemplate.exchange(services.getServ_url().concat("?").concat(parametes), HttpMethod.resolve(services.getServ_method()), entity, String.class);
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "ServiceUtil; consumeRestTemplateBearer; services: " + services.toString() + "; body: " + body + "; parametes: " + parametes + "; exception: " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(ServiceUtil.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return response;
    }   

}

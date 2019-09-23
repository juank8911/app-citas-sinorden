/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.portal.services.util;

import com.colsubsidio.salud.portal.common.PropertiesLoader;
import com.colsubsidio.salud.portal.utils.HandleDate;
import com.colsubsidio.salud.portal.utils.ProcessChain;
import com.colsubsidio.salud.portal.common.CommonConstants;
import com.colsubsidio.salud.portal.models.BearerToken;
import com.colsubsidio.salud.portal.models.Response;
import com.colsubsidio.salud.portal.models.Result;
import com.colsubsidio.salud.portal.models.Services;
import com.colsubsidio.salud.portal.utils.LogsManager;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
    private ProcessChain o;

    @Autowired
    private HandleDate f;

    @Autowired
    private LogsManager logsManager;

    public ResponseEntity<?> consumeRestTemplate(Services services, String body, String parametes, BearerToken token) {
        Response response = new Response();
        List<Result> listResult = new ArrayList<Result>();
        Result Result = new Result();
        ResponseEntity<?> responseEntity = null;
        try {
            restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=utf-8");
            headers.add("Accept", "application/json");
            headers.add(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_AUTHORIZATION), services.getServ_authorization().replace(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_TOKEN_BEARER), token.getAccess_token() != null ? token.getAccess_token() : ""));
            body.getBytes(StandardCharsets.UTF_8);
            HttpEntity<?> entity = new HttpEntity<>(body, headers);
            responseEntity = restTemplate.exchange(services.getServ_url().concat("?").concat(parametes), HttpMethod.resolve(services.getServ_method()), entity, String.class);
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "ServiceUtil; consumeRestTemplate; services: " + services.toString() + "; body: " + body + "; parametes: " + parametes + "; exception: " + ex.getMessage());
                Result.setCode("0");
                Result.setDescription(ex.getMessage());
                listResult.add(Result);
                response.setResult(listResult);
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

    public String consume(String nameService, String urlService, String method, String authorization, String token) {
        try {
            URL url;
            url = new URL(urlService);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_CONTENT_TYPE), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_CONTENT_TYPE_JSON_VALUE));
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_AUTHORIZATION), (authorization.replace(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_TOKEN_BEARER), token != null ? token : "")));
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_ACCEPT), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_ACCEPT_VALUE));
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //Response
            InputStreamReader in = new InputStreamReader(conn.getInputStream(), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_UTF8));
            StringBuilder result = ProcessChain.readChain(in);
            conn.disconnect();
            return result.toString();
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "ServiceUtil; consume; services: " + nameService + "; exception: " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(ServiceUtil.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    public String consume(String nameService, String urlService, String body, String method, String authorization, String token) {
        try {

            URL url;
            url = new URL(urlService);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_CONTENT_TYPE), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_CONTENT_TYPE_JSON_VALUE));
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_AUTHORIZATION), (authorization.replace(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_TOKEN_BEARER), token != null ? token : "")));
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_ACCEPT), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_ACCEPT_VALUE));
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //Request
            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();
            //Response
            InputStreamReader in = new InputStreamReader(conn.getInputStream(), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_UTF8));
            StringBuilder result = ProcessChain.readChain(in);
            conn.disconnect();

            return result.toString();
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "ServiceUtil; consume; services: " + nameService + "; exception: " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(ServiceUtil.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    public String consumeBasic(String nameService, String urlService, String body, String method, String authorization, String token) {
        try {

            URL url;
            url = new URL(urlService);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_CONTENT_TYPE), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_CONTENT_TYPE_JSON_VALUE));
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_AUTHORIZATION), authorization);
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_ACCEPT), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_ACCEPT_VALUE));
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("charset", "UTF-8");

            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            byte[] out = body.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            conn.setFixedLengthStreamingMode(length);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(out);
            }

            InputStreamReader in = new InputStreamReader(conn.getInputStream(), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_UTF8));
            StringBuilder result = ProcessChain.readChain(in);
            conn.disconnect();

            return result.toString();
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "ServiceUtil; consumeBasic; services: " + nameService + "; body: " + body + "; exception: " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(ServiceUtil.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    public String consumeSpring(String nameService, String urlService, String body, String method, String token) {
        try {

            URL url;
            url = new URL(urlService);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_CONTENT_TYPE), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_CONTENT_TYPE_JSON_VALUE));

            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            byte[] out = body.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            conn.setFixedLengthStreamingMode(length);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(out);
            }

            InputStreamReader in = new InputStreamReader(conn.getInputStream(), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_UTF8));
            StringBuilder result = ProcessChain.readChain(in);
            conn.disconnect();

            return result.toString();
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "ServiceUtil; consumeSpring; services: " + nameService + "; body: " + body + "; exception: " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(ServiceUtil.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    /**
     *
     * @param urlService
     * @param body
     * @param method
     * @param authorization
     * @return
     * @throws IntegrationException
     */
    public static String consume(String nameService, String urlService, String body, String method, String authorization, String token, String form) {
        try {

            URL url;
            url = new URL(urlService);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_CONTENT_TYPE), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_CONTENT_TYPE_JSON_VALUE));
            conn.setRequestProperty(PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_ACCEPT), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_ACCEPT_VALUE));
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //Request
            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();
            //Response
            InputStreamReader in = new InputStreamReader(conn.getInputStream(), PropertiesLoader.getInstance().getProperty(CommonConstants.SERVICE_WEB_UTF8));
            StringBuilder result = ProcessChain.readChain(in);
            conn.disconnect();
            return result.toString();
        } catch (IOException ex) {

        }
        return null;
    }

}

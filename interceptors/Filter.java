/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.appsalud.interceptors;

import com.colsubsidio.appsalud.models.LogRequests;
import com.colsubsidio.appsalud.models.Properties;
import com.colsubsidio.appsalud.services.interfaces.ILogsService;
import com.colsubsidio.appsalud.utils.LogsManager;
import com.colsubsidio.appsalud.utils.ManejadorFechas;
import com.colsubsidio.appsalud.utils.ProcesaCadenas;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author mac
 */
@Component
public class Filter extends GenericFilterBean {

    private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.Utilidades");

    @Autowired
    LogRequests logRequestsService;

    @Autowired
    private ProcesaCadenas o;

    @Autowired
    private ManejadorFechas f;

    @Autowired
    private LogsManager logsManager;

    @Autowired
    private Properties properties;

    @Autowired
    private ILogsService logsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Gson Gson = new Gson();
        TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
        Calendar currentDate = Calendar.getInstance(timeZone);

        try {

            if (response.getCharacterEncoding() == null) {
                response.setCharacterEncoding("UTF-8"); // Or whatever default. UTF-8 is good for World Domination.
            }

            LogRequests logRequest = new LogRequests();

            ResponseWrapper responseCopier = new ResponseWrapper((HttpServletResponse) response);
            HttpServletRequest currentRequest = (HttpServletRequest) request;

            logRequest.setBody("");
            if (!o.Compara("/ips/prueba", currentRequest.getRequestURI())) {
                RequestWrapper wrappedRequest = new RequestWrapper(currentRequest);
                request = wrappedRequest;
                logRequest.setBody(wrappedRequest.getBody());
            }
            logRequest.setFunction(currentRequest.getRequestURI());
            logRequest.setMethod(currentRequest.getMethod());
            logRequest.setServerName(currentRequest.getRequestURI());
            logRequest.setParameter(currentRequest.getQueryString() != null ? currentRequest.getQueryString() : "[]");
            logRequest.setDate(Gson.toJson(String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH))
                    + "/" + String.valueOf(currentDate.get(Calendar.MONTH) + 1)
                    + "/" + String.valueOf(currentDate.get(Calendar.YEAR))));
            logRequest.setHour(Gson.toJson(String.valueOf(currentDate.get(Calendar.HOUR_OF_DAY))
                    + ":" + String.valueOf(currentDate.get(Calendar.MINUTE))
                    + ":" + String.valueOf(currentDate.get(Calendar.SECOND))));

            Enumeration<String> headerNames = currentRequest.getHeaderNames();

            if (headerNames != null) {
                LinkedHashMap<String, String> headerMap = new LinkedHashMap<>();

                while (headerNames.hasMoreElements()) {
                    String key = headerNames.nextElement();
                    if (key != null) {
                        String value = String.valueOf(currentRequest.getHeader(key));
                        if (value != null) {
                            headerMap.put(key, value);
                        }
                    }
                }
                logRequest.setHeader(new com.google.gson.Gson().toJson(headerMap));
            }

            logRequest.setAction("request");
            logsManager.RegisterLog("spring", logRequest.toJson());

            chain.doFilter(request, responseCopier);
            responseCopier.flushBuffer();
            byte[] copy = responseCopier.getCopy();
            String content = new String(copy, response.getCharacterEncoding());
            logRequest.setBody(content);
            logRequest.setHttpStatus(String.valueOf(responseCopier.getStatus()));

            logRequest.setAction("response");
//            logsManager.RegisterLog("spring", logRequest.toJson());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

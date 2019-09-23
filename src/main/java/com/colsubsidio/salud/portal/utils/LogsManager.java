/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.portal.utils;

import com.colsubsidio.salud.portal.models.LogRequests;
import com.colsubsidio.salud.portal.models.LogsLogger;
import com.colsubsidio.salud.portal.models.Properties;
import com.colsubsidio.salud.portal.models.Services;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.LinkedHashMap;
import java.util.TimeZone;
import java.util.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.TraceTelemetry;

/**
 *
 * @author Usuario
 */
@Component
public class LogsManager {

    private final static Logger LOGGER = Logger.getLogger("APIGEE");
    private final static Logger LOGGERERROR = Logger.getLogger("ERROR");

    private LogRequests logRequests;

    private String function;

    @Autowired
    private ProcessChain o;

    @Autowired
    private HandleDate f;

    @Autowired
    private TelemetryClient telemetryClient;

    public LogsManager() {
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Async("asyncExecutor")
    public void LogsBuildServices(String log, String type, ResponseEntity<?> response, Services service, String body, String parameters) throws IOException {
        try {
            //Gson Gson = new Gson();
            Gson Gson = new GsonBuilder().disableHtmlEscaping().create();
            TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
            Calendar currentDate = Calendar.getInstance(timeZone);

            logRequests = new LogRequests();
            logRequests.setFunction(function);
            logRequests.setAction(Gson.toJson(type != null || type != "" ? type : "[]"));
            logRequests.setBody(body != null || body != "" ? body.toString().replace(" ", "").replace("\t", "").replace("\n", "") : "[]");
            logRequests.setBodyResponse(response != null || response.getBody().toString() != "" ? response.getBody().toString().replace(" ", "").replace("\t", "").replace("\n", "") : "[]");
            logRequests.setDate(Gson.toJson(String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH))
                    + "/" + String.valueOf(currentDate.get(Calendar.MONTH) + 1)
                    + "/" + String.valueOf(currentDate.get(Calendar.YEAR))));
            logRequests.setHour(Gson.toJson(String.valueOf(currentDate.get(Calendar.HOUR_OF_DAY))
                    + ":" + String.valueOf(currentDate.get(Calendar.MINUTE))
                    + ":" + String.valueOf(currentDate.get(Calendar.SECOND))));

            logRequests.setHttpStatus(Gson.toJson(response.getStatusCode()));
            logRequests.setMethod(Gson.toJson(service.getServ_method()));
            logRequests.setParameter(Gson.toJson(parameters));
            logRequests.setServerName(Gson.toJson(service.getServ_url()));

            LinkedHashMap<String, String> headerMap = new LinkedHashMap<>();
            response.getHeaders().forEach((k, v) -> headerMap.put(k, v.get(0).toString()));
            logRequests.setHeader(Gson.toJson(headerMap != null ? headerMap : "[]"));

            if (o.Compara(o.notEmpty(log, ""), "apigee")) {
                telemetryClient.trackTrace(Gson.toJson(logRequests.toJson()).replace("\\\t", "").replace("\t", "").replace("\\\"", "\"").replace("\"\"", "\"").replace("\\\\t", "").replace("\\\\", ""));
            }
            LogsLogger logL = new LogsLogger();

            logL.setIndex("portalsalud_personas");
            logL.setType("personas");
            logL.setStartDate(f.getFechaHoraTimeStamp(0).toString());
            logL.setEndDate(f.getFechaHoraTimeStamp(0).toString());
            logL.setStatusCode(logRequests.getHttpStatus());

            logL.setReqRes(logRequests.toString().replace("\"", ""));
        } catch (Exception ex) {
            telemetryClient.trackException(new Exception("LogsManager; LogsBuildServices; " + ex.getMessage()));
        }
    }

    @Async("asyncExecutor")
    public void LogsBuildAppInsights(String type, String exception) throws IOException {

        try {
            switch (type) {
                case "info":
                    telemetryClient.trackEvent(exception);
                    break;
                case "view":
                    telemetryClient.trackPageView(exception);
                    break;
                case "trace":
                    telemetryClient.trackTrace(exception);
                    break;
                case "error":
                    telemetryClient.trackException(new Exception(exception));
                    break;
                case "exception":
                    telemetryClient.trackException(new Exception(exception));
                    break;
                default:
                    telemetryClient.trackException(new Exception(exception));
                    break;
            }
        } catch (Exception ex) {
            telemetryClient.trackException(new Exception("LogsManager; LogsBuildExceptionError; " + ex.getMessage()));
        }
    }
}

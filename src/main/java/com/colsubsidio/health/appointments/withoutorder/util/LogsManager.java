package com.colsubsidio.health.appointments.withoutorder.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.constans.TelemetryConstants;
import com.colsubsidio.health.appointments.withoutorder.model.LogElasticSearch;
import com.colsubsidio.health.appointments.withoutorder.services.TokenService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.applicationinsights.TelemetryClient;

@Component
public class LogsManager {

	@Value("${spring.application.name}")
	private String nameApplication;
	@Value("${apigee.path.logger}")
	private String apigeePathLogger;
	private final TelemetryClient telemetryClient;
	private DateUtils dateUtils;

	@Autowired
	public LogsManager(TelemetryClient telemetryClient, DateUtils dateUtils, TokenService tokenService) {
		this.telemetryClient = telemetryClient;
		this.dateUtils = dateUtils;
	}

	@Async("asyncExecutor")
	public void logsBuildServicesReqRes(String url, Object body, Object bodyResponse, HttpMethod method,
			HttpStatus status, long timeConn, String function) {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
			String currentDate = formatter.format(date);
			formatter.applyPattern("hh:mm:ss");
			String currentHour = formatter.format(date);
			JsonObject obj = new JsonObject();
			obj.addProperty("function", function);
			obj.addProperty("date", currentDate);
			obj.addProperty("hour", currentHour);
			obj.addProperty("url", url);
			obj.addProperty("method", method.name());
			obj.addProperty("httpStatus", status.value());
			obj.addProperty("time", timeConn + "ms");
			JsonObject objReqRes = new JsonObject();
			objReqRes.add("requestBody", new Gson().toJsonTree(body));

			objReqRes.add("response", (bodyResponse instanceof String) ? JsonParser.parseString(bodyResponse.toString())
					: new Gson().toJsonTree(bodyResponse));

			obj.add("body", objReqRes);
			telemetryClient.trackTrace(gson.toJson(obj));
		} catch (Exception ex) {
			telemetryClient.trackException(new Exception("LogsManager; LogsBuildServices; " + ex.getMessage()));
		}
	}

	@Async("asyncExecutor")
	public void telemetry(String tag, Map<String, String> map) {
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		String message = gson.toJson(map);
		LogElasticSearch logModel = new LogElasticSearch();
		logModel.setEventDate(dateUtils.getDateString("yyyy-MM-dd'T'HH:mm:ss.SS"));
		logModel.setIndex(nameApplication);
		logModel.setTypeStatusCode(200);
		logModel.setMessage(message);
		logModel.setType(tag);

		if (telemetryClient != null) {
			Map<String, Double> m2 = new HashMap<>();
			telemetryClient.trackEvent(tag, map, m2);
		}
		logsBuildAppInsights("info", gson.toJson(logModel));
		try {
			// tokenService.sendToElasticSearch(logModel);
		} catch (Exception e) {
			logsBuildAppInsights("error", e.getMessage());
		}

	}

	@Async("asyncExecutor")
	public void logsBuildAppInsights(String type, String exception) {
		try {
			int code;
			String tag = "";
			switch (type) {
			case "info":
				code = 200;
				tag = TelemetryConstants.INFO;
				telemetryClient.trackEvent(exception);
				break;
			case "view":
				code = 200;
				tag = TelemetryConstants.INFO;
				telemetryClient.trackPageView(exception);
				break;
			case "trace":
				code = 200;
				tag = TelemetryConstants.INFO;
				telemetryClient.trackTrace(exception);
				break;
			case "error":
				code = 500;
				tag = TelemetryConstants.ERROR;
				telemetryClient.trackException(new Exception(exception));
				break;
			case "exception":
				code = 500;
				tag = TelemetryConstants.ERROR;
				telemetryClient.trackException(new Exception(exception));
				break;
			default:
				code = 500;
				tag = TelemetryConstants.ERROR;
				telemetryClient.trackException(new Exception(exception));
				break;
			}
			LogElasticSearch logModel = new LogElasticSearch();
			logModel.setEventDate(dateUtils.getDateString("yyyy-MM-dd'T'HH:mm:ss.SS"));
			logModel.setIndex(nameApplication);
			logModel.setTypeStatusCode(code);
			logModel.setMessage(exception);
			logModel.setType(tag);
			System.out.println(exception);
			// tokenService.sendToElasticSearch(logModel);
		} catch (Exception ex) {
			telemetryClient.trackException(new Exception("LogsManager; LogsBuildExceptionError; " + ex.getMessage()));
		}
	}
}

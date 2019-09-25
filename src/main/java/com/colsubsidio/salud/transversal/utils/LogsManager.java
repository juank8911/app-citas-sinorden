/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.colsubsidio.salud.transversal.models.Errores;
import com.colsubsidio.salud.transversal.models.LogRequests;
import com.colsubsidio.salud.transversal.models.LogsLogger;
import com.colsubsidio.salud.transversal.models.Properties;
import com.colsubsidio.salud.transversal.models.Services;
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

	private String ruta;
	private String function;

	@Autowired
	private Properties properties;

	@Autowired
	private HandleDate dateManager;

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

	public boolean RegisterLog(String type, String mensaje) throws IOException {

		try {
			boolean exito = false;
			FileWriter archivo;
			int dia = Integer.parseInt(dateManager.getCadenaArchivo(0).substring(0, 10));
			if (dia % 2 != 0) {
				dia--;
			}
			ruta = o.notEmpty(properties.getLocationLogs(), "logs") + "/" + type + dia + ".log";

			if (ruta != null) {
				if (new File(ruta).exists() == false) {
					archivo = new FileWriter(new File(ruta), false);
				}
				archivo = new FileWriter(new File(ruta), true);

				mensaje = mensaje.replace("\\\t", "").replace("\t", "").replace("\\\"", "\"").replace("\"\"", "\"")
						.replace("\\\\t", "").replace("\\\\", "");
				archivo.write(mensaje + ", \r\n");
				archivo.close();
			}
			return exito;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Async("asyncExecutor")
	public void LogsBuildServices(String log, String type, ResponseEntity<?> response, Services service, String body,
			String parameters) throws IOException {
		try {
			// Gson Gson = new Gson();
			Gson Gson = new GsonBuilder().disableHtmlEscaping().create();
			TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
			Calendar currentDate = Calendar.getInstance(timeZone);

			logRequests = new LogRequests();
			logRequests.setFunction(function);
			logRequests.setAction(Gson.toJson(type != null || type != "" ? type : "[]"));
			logRequests.setBody(
					body != null || body != "" ? body.toString().replace(" ", "").replace("\t", "").replace("\n", "")
							: "[]");
			logRequests.setBodyResponse(response != null || response.getBody().toString() != ""
					? response.getBody().toString().replace(" ", "").replace("\t", "").replace("\n", "")
					: "[]");
			logRequests.setDate(Gson.toJson(String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)) + "/"
					+ String.valueOf(currentDate.get(Calendar.MONTH) + 1) + "/"
					+ String.valueOf(currentDate.get(Calendar.YEAR))));
			logRequests.setHour(Gson.toJson(String.valueOf(currentDate.get(Calendar.HOUR_OF_DAY)) + ":"
					+ String.valueOf(currentDate.get(Calendar.MINUTE)) + ":"
					+ String.valueOf(currentDate.get(Calendar.SECOND))));

			logRequests.setHttpStatus(Gson.toJson(response.getStatusCode()));
			logRequests.setMethod(Gson.toJson(service.getServ_method()));
			logRequests.setParameter(Gson.toJson(parameters));
			logRequests.setServerName(Gson.toJson(service.getServ_url()));

			LinkedHashMap<String, String> headerMap = new LinkedHashMap<>();
			response.getHeaders().forEach((k, v) -> headerMap.put(k, v.get(0).toString()));
			logRequests.setHeader(Gson.toJson(headerMap != null ? headerMap : "[]"));

			if (o.Compara(o.notEmpty(log, ""), "apigee")) {
				telemetryClient.trackTrace(Gson.toJson(logRequests.toJson()).replace("\\\t", "").replace("\t", "")
						.replace("\\\"", "\"").replace("\"\"", "\"").replace("\\\\t", "").replace("\\\\", ""));
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
	public void LogsBuildServices(String log, String type, HttpURLConnection service, String response)
			throws IOException {
		try {
			// Gson Gson = new Gson();
			Gson Gson = new GsonBuilder().disableHtmlEscaping().create();
			TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
			Calendar currentDate = Calendar.getInstance(timeZone);

			logRequests = new LogRequests();
			logRequests.setFunction(function);
			logRequests.setAction(Gson.toJson(type != null || type != "" ? type : "[]"));
			logRequests
					.setBody(response != null || response != "" ? response.replace(" ", "").replace("\t", "") : "[]");
			logRequests.setDate(Gson.toJson(String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)) + "/"
					+ String.valueOf(currentDate.get(Calendar.MONTH) + 1) + "/"
					+ String.valueOf(currentDate.get(Calendar.YEAR))));
			logRequests.setHour(Gson.toJson(String.valueOf(currentDate.get(Calendar.HOUR_OF_DAY)) + ":"
					+ String.valueOf(currentDate.get(Calendar.MINUTE)) + ":"
					+ String.valueOf(currentDate.get(Calendar.SECOND))));

			logRequests.setHttpStatus(Gson.toJson(service.getResponseCode()));
			logRequests.setMethod(Gson.toJson(service.getRequestMethod()));
			logRequests.setParameter(
					Gson.toJson(service.getURL().getQuery() != null && service.getURL().getQuery().length() > 0
							? service.getURL().getQuery().replace("=", ":").replace("&", ", ")
							: "[]"));
			logRequests.setServerName(Gson.toJson(service.getURL().getProtocol() + "://" + service.getURL().getHost()
					+ "" + service.getURL().getPath()));

			LinkedHashMap<String, String> headerMap = new LinkedHashMap<>();
			service.getHeaderFields().forEach((k, v) -> headerMap.put(k, v.get(0).toString()));
			logRequests.setHeader(Gson.toJson(headerMap != null ? headerMap : "[]"));

			if (o.Compara(o.notEmpty(log, ""), "apigee")) {
				telemetryClient
						.trackTrace(Gson.toJson(Gson.toJson(logRequests.toJson()).replace("\\\t", "").replace("\t", "")
								.replace("\\\"", "\"").replace("\"\"", "\"").replace("\\\\t", "").replace("\\\\", "")));
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
	public void LogsBuildServices(String log, String type, Services service, String body, String parameters)
			throws IOException {
		try {

			Gson Gson = new GsonBuilder().disableHtmlEscaping().create();
			TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
			Calendar currentDate = Calendar.getInstance(timeZone);

			logRequests = new LogRequests();
			logRequests.setFunction(function);
			logRequests.setAction(Gson.toJson(type != null || type.equals("") ? type : "[]"));
			logRequests.setBody(Gson.toJson(body != null || !body.equals("") ? body : "[]").replace("\"[", "[")
					.replace("]\"", "]"));
			logRequests.setDate(Gson.toJson(String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)) + "/"
					+ String.valueOf(currentDate.get(Calendar.MONTH) + 1) + "/"
					+ String.valueOf(currentDate.get(Calendar.YEAR))));
			logRequests.setHour(Gson.toJson(String.valueOf(currentDate.get(Calendar.HOUR_OF_DAY)) + ":"
					+ String.valueOf(currentDate.get(Calendar.MINUTE)) + ":"
					+ String.valueOf(currentDate.get(Calendar.SECOND))));
			logRequests.setHttpStatus("[]");
			logRequests.setMethod(Gson.toJson(
					service.getServ_method() != null || service.getServ_method() != "" ? service.getServ_method()
							: "[]"));
			logRequests.setParameter(Gson.toJson(parameters != null && parameters.length() > 0 ? parameters : "[]"));
			logRequests.setServerName(Gson.toJson(service.getServ_url()));
			logRequests.setHeader(Gson.toJson(service.getServ_headers() != null ? service.getServ_headers() : "[]"));

			if (o.Compara(o.notEmpty(log, ""), "apigee")) {
				telemetryClient.trackTrace(Gson.toJson(logRequests.toJson()).replace("\\\t", "").replace("\t", "")
						.replace("\\\"", "\"").replace("\"\"", "\"").replace("\\\\t", "").replace("\\\\", ""));
			}
			LogsLogger logL = new LogsLogger();

			logL.setIndex("portalsalud_personas");
			logL.setType("personas");
			logL.setStartDate(f.getFechaHoraTimeStamp(0).toString());
			logL.setEndDate(f.getFechaHoraTimeStamp(0).toString());
			logL.setStatusCode(logRequests.getHttpStatus());
			logL.setReqRes(logRequests.toString().replace("\"", ""));
//            logsService.insertLogsService(logL);
		} catch (Exception ex) {
			telemetryClient.trackException(new Exception("LogsManager; LogsBuildServices; " + ex.getMessage()));
		}
	}

	@Async("asyncExecutor")
	public void LogsBuildServices(String log, Services serviceDB, HttpURLConnection serviceC, String response,
			String body, String parameters) throws IOException {
		try {

			Gson Gson = new GsonBuilder().disableHtmlEscaping().create();
			TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
			Calendar currentDate = Calendar.getInstance(timeZone);

			LogsLogger logL = new LogsLogger();

			logL.setIndex("portalsalud_personas");
			logL.setType("personas");
			logL.setStartDate(f.getFechaHoraTimeStamp(0).toString());
			logL.setEndDate(f.getFechaHoraTimeStamp(0).toString());
			logL.setStatusCode(logRequests.getHttpStatus());
			logL.setReqRes(logRequests.toString().replace("\"", ""));
//            logsService.insertLogsService(logL);
		} catch (Exception ex) {
			telemetryClient.trackException(new Exception("LogsManager; LogsBuildServices; " + ex.getMessage()));
		}
	}

	public void LogsBuildError(String log, String type, Errores errores) throws IOException {
		try {

			Gson Gson = new Gson();
			TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
			Calendar currentDate = Calendar.getInstance(timeZone);

			errores.setFunction(function);
			errores.setAction(Gson.toJson(type != null || type != "" ? type : "[]"));
			errores.setDate(Gson.toJson(String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)) + "/"
					+ String.valueOf(currentDate.get(Calendar.MONTH) + 1) + "/"
					+ String.valueOf(currentDate.get(Calendar.YEAR))));
			errores.setHour(Gson.toJson(String.valueOf(currentDate.get(Calendar.HOUR_OF_DAY)) + ":"
					+ String.valueOf(currentDate.get(Calendar.MINUTE)) + ":"
					+ String.valueOf(currentDate.get(Calendar.SECOND))));

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

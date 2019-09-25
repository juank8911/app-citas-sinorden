/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.services;

import com.colsubsidio.salud.transversal.dao.interfaces.ILogsDAO;
import com.colsubsidio.salud.transversal.dao.interfaces.IServicesDAO;
import com.colsubsidio.salud.transversal.models.BearerToken;
import com.colsubsidio.salud.transversal.models.LogsLogger;
import com.colsubsidio.salud.transversal.models.Services;
import com.colsubsidio.salud.transversal.services.interfaces.IAuthService;
import com.colsubsidio.salud.transversal.services.interfaces.ILogsService;
import com.colsubsidio.salud.transversal.utils.HandleDate;
import com.colsubsidio.salud.transversal.utils.LogsManager;
import com.colsubsidio.salud.transversal.utils.ProcessChain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LogsService implements ILogsService {

	@Autowired
	private ProcessChain o;

	@Autowired
	private HandleDate f;

	@Autowired
	private IServicesDAO serviciosDAO;

	@Autowired
	private IAuthService authService;

	@Autowired
	private ILogsDAO LogsDAO;

	@Autowired
	private LogsManager logsManager;

	@Override
	@Async("asyncExecutor")
	public void insertLogsService(LogsLogger logs) {

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
		BearerToken token;
		String result = "";

		try {

			ResponseEntity<?> tokenAuthorization = authService.BearerToken();
			if (tokenAuthorization != null && tokenAuthorization.getBody() != null && logs != null) {
				token = gson.fromJson(tokenAuthorization.getBody().toString(), BearerToken.class);
				Services servicios = new Services();
				servicios = (Services) serviciosDAO.getServicioByName("logsService");
				URL url = new URL(servicios.getServ_url());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod(servicios.getServ_method());
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setReadTimeout(10000);
				conn.setConnectTimeout(15000);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestProperty("Authorization",
						(servicios.getServ_authorization().replace("!!token!!", token.getAccess_token())));

				byte[] out = gson.toJson(logs).getBytes(StandardCharsets.UTF_8);
				int length = out.length;

				conn.setFixedLengthStreamingMode(length);

				try (OutputStream os = conn.getOutputStream()) {
					os.write(out);
				}

				InputStreamReader in = new InputStreamReader(conn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(in);

				while (br.ready()) {
					result += br.readLine();
				}
			}
		} catch (Exception ex) {
			try {
				logsManager.LogsBuildAppInsights("exception", "LogsService; insertLogsService; " + ex.getMessage());
			} catch (IOException ex1) {
				Logger.getLogger(LogsService.class.getName()).log(Level.SEVERE, null, ex1);
			}
		}
	}

	@Override
	public ResponseEntity<String> insertLogs(String logAction, String logMessage) {
		try {
			logsManager.LogsBuildAppInsights(logAction, logMessage);
			return new ResponseEntity(LogsDAO.createLog(logAction, logMessage), HttpStatus.OK);
		} catch (Exception ex) {
			try {
				logsManager.LogsBuildAppInsights("exception", "LogsService; insertLogs; " + ex.getMessage());
			} catch (IOException ex1) {
				Logger.getLogger(LogsService.class.getName()).log(Level.SEVERE, null, ex1);
			}
		}
		return new ResponseEntity(null, HttpStatus.OK);
	}
}

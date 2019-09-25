/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

import com.colsubsidio.salud.transversal.dao.interfaces.ILogsDAO;
import com.colsubsidio.salud.transversal.dao.interfaces.IServicesDAO;
import com.colsubsidio.salud.transversal.models.BearerToken;
import com.colsubsidio.salud.transversal.models.Services;
import com.colsubsidio.salud.transversal.services.interfaces.IAuthService;
import com.colsubsidio.salud.transversal.services.util.ServiceUtil;
import com.colsubsidio.salud.transversal.utils.HandleDate;
import com.colsubsidio.salud.transversal.utils.LogsManager;
import com.colsubsidio.salud.transversal.utils.ProcessChain;

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
	private HandleDate f;

	@Autowired
	private IServicesDAO serviciosDAO;

	@Autowired
	private ILogsDAO LogsDAO;

	@Autowired
	private LogsManager logsManager;

	@Autowired
	private ServiceUtil serviceUtil;

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> BearerToken() {
		try {

			Services serviciosVO = new Services();
			serviciosVO = serviciosDAO.getServicioByName("BearerToken");
			StringBuffer parameters = new StringBuffer();
			parameters.append("grant_type=client_credentials");

			String data = "client_secret=" + URLEncoder.encode(o.notEmpty(serviciosVO.getClient_secret(), ""))
					+ "&client_id=" + URLEncoder.encode(o.notEmpty(serviciosVO.getClient_id(), ""));

			return serviceUtil.consumeRestTemplateBearer(serviciosVO, data, parameters.toString());
		} catch (Exception ex) {
			try {
				logsManager.LogsBuildAppInsights("exception",
						"AuthController; BearerToken; " + ex.getMessage().toString());
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
			serviciosVO = serviciosDAO.getServicioByName("BearerTokenTest");
			StringBuffer parameters = new StringBuffer();
			parameters.append("grant_type=client_credentials");

			String data = "client_secret=" + URLEncoder.encode(o.notEmpty(serviciosVO.getClient_secret(), ""))
					+ "&client_id=" + URLEncoder.encode(o.notEmpty(serviciosVO.getClient_id(), ""));

			return serviceUtil.consumeRestTemplateBearer(serviciosVO, data, parameters.toString());
		} catch (Exception ex) {
			try {
				logsManager.LogsBuildAppInsights("exception",
						"AuthController; BearerTokenTest; " + ex.getMessage().toString());
			} catch (IOException ex1) {
				Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex1);
			}
		}
		return new ResponseEntity(null, HttpStatus.OK);
	}
}

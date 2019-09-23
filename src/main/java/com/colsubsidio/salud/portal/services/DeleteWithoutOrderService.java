/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.portal.services;

import com.colsubsidio.salud.portal.models.BearerToken;
import com.colsubsidio.salud.portal.models.Services;
import com.colsubsidio.salud.portal.services.interfaces.IAuthService;
import com.colsubsidio.salud.portal.utils.LogsManager;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.colsubsidio.salud.portal.dao.interfaces.IServicesDAO;
import com.colsubsidio.salud.portal.models.Response;
import com.colsubsidio.salud.portal.models.deletewithoutorder.Delete;
import com.colsubsidio.salud.portal.services.interfaces.IDeleteWithoutOrderService;
import com.colsubsidio.salud.portal.services.util.ServiceUtil;
import com.google.gson.Gson;

/**
 *
 * @author Usuario
 */
@Component
public class DeleteWithoutOrderService implements IDeleteWithoutOrderService {

	@Autowired
	private IServicesDAO serviciosDAO;

	@Autowired
	private LogsManager logsManager;

	@Autowired
	private IAuthService authService;

	@Autowired
	private ServiceUtil serviceUtil;

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> deleteWithoutOrder(Delete delete) {
		StringBuffer parameters = new StringBuffer();
		Response response = new Response();
		ResponseEntity<?> responseEntity = null;
		Gson gson = new Gson();
		try {
			if ((delete != null && delete.getBorrarSinOrden() != null) && (delete.getBorrarSinOrden().getCita() != null
					&& !delete.getBorrarSinOrden().getCita().getIdReserva().isEmpty())) {

				Services serviciosVO = serviciosDAO.getServicioByName("CitaborradoSinOrdenV2");
				BearerToken token = gson.fromJson(authService.BearerToken().getBody().toString(), BearerToken.class);

				responseEntity = serviceUtil.consumeRestTemplate(serviciosVO, gson.toJson(delete),
						parameters.toString(), token);

				if (responseEntity.getStatusCode().equals(HttpStatus.OK) && !responseEntity.getBody().equals("")) {

					LinkedHashMap<String, String> headerMap = new LinkedHashMap<>();
					headerMap.put("Accept", "application/json");
					headerMap.put("Content-type", "application/json");
					headerMap.put("Authorization", serviciosVO.getServ_authorization());
					headerMap.put("x-api-key", serviciosVO.getServ_apikey());
					serviciosVO.setServ_headers(headerMap);

					logsManager.setFunction(serviciosVO.getServ_name());
					logsManager.LogsBuildServices("apigee", "request-response", responseEntity, serviciosVO,
							gson.toJson(delete), parameters.toString());

					return new ResponseEntity<Object>(responseEntity.getBody(), HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			try {
				logsManager.LogsBuildAppInsights("exception",
						"DeleteWithoutOrderService; deleteWithoutOrder; borrarCitaSinOrdenJSON=" + gson.toJson(delete)
								+ "; " + ex.getMessage());
			} catch (IOException ex1) {
				Logger.getLogger(DeleteWithoutOrderService.class.getName()).log(Level.SEVERE, null, ex1);
			}
		}
		return new ResponseEntity<Object>(responseEntity != null ? responseEntity.getBody() : response,
				HttpStatus.BAD_REQUEST);
	}
}

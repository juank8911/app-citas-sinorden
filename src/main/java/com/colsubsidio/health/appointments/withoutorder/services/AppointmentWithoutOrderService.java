/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.health.appointments.withoutorder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.colsubsidio.health.appointments.withoutorder.dao.ServicesDAO;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.ReservationAppointmentRequest;
import com.colsubsidio.health.appointments.withoutorder.model.ReservationAppointmentResponse;
import com.colsubsidio.health.appointments.withoutorder.model.Services;
import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;
import com.colsubsidio.health.appointments.withoutorder.util.RestTemplateUtil;

/**
 *
 * @author LordMVP
 */
@Component
public class AppointmentWithoutOrderService {

	@Autowired
	private RestTemplateUtil restTemplateUtil;

	@Autowired
	private ServicesDAO servicesDAO;

	@Autowired
	LogsManager logsManager;

	private static String exception = "exception";

	public ResponseEntity<ReservationAppointmentResponse> getReservationAppointment(
			ReservationAppointmentRequest reservationAppointment) {

		ResponseEntity<ReservationAppointmentResponse> response = null;

		try {

			Services services = servicesDAO.getServicioByName("ReservaCitaSinOrdenV2");

			UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(services.getServ_url());
			response = restTemplateUtil.sendRequest(uri, HttpMethod.valueOf(services.getServ_method()),
					reservationAppointment, ReservationAppointmentResponse.class, true, services.getServ_name());
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderService; getReservationAppointment; " + ex.getMessage());
		}

		return response;
	}

	public ResponseEntity<CreateWithoutOrderResponse> getCreateWithoutOrder(
			CreateWithoutOrderRequest createWithoutOrder) {

		ResponseEntity<CreateWithoutOrderResponse> response = null;

		try {

			Services services = servicesDAO.getServicioByName("CitaCreacionSinOrdenV2");

			UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(services.getServ_url());
			response = restTemplateUtil.sendRequest(uri, HttpMethod.valueOf(services.getServ_method()),
					createWithoutOrder, CreateWithoutOrderResponse.class, true, services.getServ_name());
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderService; getCreateWithoutOrder; " + ex.getMessage());
		}

		return response;
	}

	public ResponseEntity<DeleteWithoutOrderResponse> getDeleteWithoutOrder(
			DeleteWithoutOrderRequest deleteWithoutOrderRequest) {

		ResponseEntity<DeleteWithoutOrderResponse> response = null;

		try {

			Services services = servicesDAO.getServicioByName("CitaborradoSinOrdenV2");

			UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(services.getServ_url());
			response = restTemplateUtil.sendRequest(uri, HttpMethod.valueOf(services.getServ_method()),
					deleteWithoutOrderRequest, DeleteWithoutOrderResponse.class, true, services.getServ_name());
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderService; getDeleteWithoutOrder; " + ex.getMessage());
		}

		return response;
	}

}

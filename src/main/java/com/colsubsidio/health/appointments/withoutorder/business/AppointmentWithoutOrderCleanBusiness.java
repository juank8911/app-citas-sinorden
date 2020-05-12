package com.colsubsidio.health.appointments.withoutorder.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.dao.LogsDAO;
import com.colsubsidio.health.appointments.withoutorder.dao.ScheduleDAO;
import com.colsubsidio.health.appointments.withoutorder.dto.AppointmentInformationDTO;
import com.colsubsidio.health.appointments.withoutorder.dto.LogAppointmentDTO;
import com.colsubsidio.health.appointments.withoutorder.model.AppointmentReserveResponse;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrder;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.ReservationAppointmentRequest;
import com.colsubsidio.health.appointments.withoutorder.model.ReservationAppointmentResponse;
import com.colsubsidio.health.appointments.withoutorder.model.ReserveWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.Result;
import com.colsubsidio.health.appointments.withoutorder.model.Schedule;
import com.colsubsidio.health.appointments.withoutorder.services.AppointmentWithoutOrderService;
import com.colsubsidio.health.appointments.withoutorder.util.DateUtils;
import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;
import com.colsubsidio.health.appointments.withoutorder.enums.ResultAppointmentEnum;
import com.google.gson.Gson;

@Component
public class AppointmentWithoutOrderCleanBusiness {

	@Autowired
	DateUtils dateUtils;
	@Autowired
	LogsManager logsManager;
	@Autowired
	AppointmentWithoutOrderService appointmentWithoutOrderService;
	@Autowired
	LogsDAO logsDAO;
	@Autowired
	ScheduleDAO scheduleDAO;
	@Autowired
	Gson gson;

	private static String exception = "exception";

	public ResponseEntity<ReservationAppointmentResponse> getReservationWithoutOrder(
			ReservationAppointmentRequest reservationAppointment) {

		ResponseEntity<ReservationAppointmentResponse> response = null;

		try {
			response = appointmentWithoutOrderService.getReservationAppointment(reservationAppointment);
		} catch (Exception e) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getReservationAppointment; " + e.getMessage());
		}
		return response;
	}

	public ResponseEntity<CreateWithoutOrderResponse> getCreateWithoutOrder(
			CreateWithoutOrderRequest createWithoutOrder) {

		ResponseEntity<CreateWithoutOrderResponse> response = null;

		try {
			response = appointmentWithoutOrderService.getCreateWithoutOrder(createWithoutOrder);
		} catch (Exception e) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getCreateWithoutOrder; " + e.getMessage());
		}
		return response;
	}

	public ResponseEntity<DeleteWithoutOrderResponse> getDeleteWithoutOrder(
			DeleteWithoutOrderRequest deleteWithoutOrderRequest) {

		ResponseEntity<DeleteWithoutOrderResponse> response = null;

		try {
			response = appointmentWithoutOrderService.getDeleteWithoutOrder(deleteWithoutOrderRequest);
		} catch (Exception e) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getDeleteWithoutOrder; " + e.getMessage());
		}
		return response;
	}

}

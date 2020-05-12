package com.colsubsidio.health.appointments.withoutorder.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.colsubsidio.health.appointments.withoutorder.business.AppointmentWithoutOrderCleanBusiness;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.ReservationAppointmentRequest;
import com.colsubsidio.health.appointments.withoutorder.model.ReservationAppointmentResponse;

/**
 *
 * @author LordMVP
 */
@RestController
@RequestMapping("/agendamiento/directo/citas/sinorden")
public class AppointmentWithoutOrderCleanController {

	@Autowired
	private AppointmentWithoutOrderCleanBusiness appointmentWithoutOrderCleanBusiness;

	/**
	 *
	 * @param reservationAppointment
	 * @return
	 */
	@ResponseBody
	@PostMapping("/reserva")
	public ResponseEntity<ReservationAppointmentResponse> getReservationWithoutOrder(
			@RequestBody ReservationAppointmentRequest reservationAppointment) {
		return appointmentWithoutOrderCleanBusiness.getReservationWithoutOrder(reservationAppointment);
	}

	/**
	 *
	 * @param createWithoutOrder
	 * @return
	 */
	@ResponseBody
	@PutMapping("/confirmacion")
	public ResponseEntity<CreateWithoutOrderResponse> getCreateWithoutOrder(
			@RequestBody CreateWithoutOrderRequest createWithoutOrder) {
		return appointmentWithoutOrderCleanBusiness.getCreateWithoutOrder(createWithoutOrder);
	}

	/**
	 *
	 * @param deleteWithoutOrderRequest
	 * @return
	 */
	@ResponseBody
	@PutMapping("/eliminacion")
	public ResponseEntity<DeleteWithoutOrderResponse> getDeleteWithoutOrder(
			@RequestBody DeleteWithoutOrderRequest deleteWithoutOrderRequest) {
		return appointmentWithoutOrderCleanBusiness.getDeleteWithoutOrder(deleteWithoutOrderRequest);
	}

}

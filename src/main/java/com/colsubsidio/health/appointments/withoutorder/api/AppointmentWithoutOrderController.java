package com.colsubsidio.health.appointments.withoutorder.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.colsubsidio.health.appointments.withoutorder.business.AppointmentWithoutOrderBusiness;
import com.colsubsidio.health.appointments.withoutorder.business.DeleteWithoutOrderBusiness;
import com.colsubsidio.health.appointments.withoutorder.dto.AppointmentInformationDTO;
import com.colsubsidio.health.appointments.withoutorder.dto.DeleteInformationDTO;
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
@RequestMapping("/agendamiento/citas/sinorden")
public class AppointmentWithoutOrderController {

	@Autowired
	private AppointmentWithoutOrderBusiness appointmentWithoutOrderBusiness;
	@Autowired
	private DeleteWithoutOrderBusiness deleteWithoutOrderBusiness;

	/**
	 *
	 * @param appointmentInformation
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/reserva-confirmacion", produces = "application/json")
	public ResponseEntity<CreateWithoutOrderResponse> getReservationWithoutOrder(
			@RequestBody AppointmentInformationDTO appointmentInformation) {
		return appointmentWithoutOrderBusiness.getReservationWithoutOrder(appointmentInformation);
	}

	/**
	 *
	 * @param appointmentInformation
	 * @return
	 */
	@ResponseBody
	@PutMapping(value = "/eliminacion-cita", produces = "application/json")
	public ResponseEntity<DeleteWithoutOrderResponse> getDeleteWithoutOrder(
			@RequestBody DeleteInformationDTO deleteInformationDTO) {
		return deleteWithoutOrderBusiness.getDeleteWithoutOrder(deleteInformationDTO);
	}

	/**
	 *
	 * @param reservationAppointment
	 * @return
	 */
	@ResponseBody
	@PostMapping("/reserva")
	public ResponseEntity<ReservationAppointmentResponse> getReservationWithoutOrder(
			@RequestBody ReservationAppointmentRequest reservationAppointment) {
		return appointmentWithoutOrderBusiness.getReservationWithoutOrder(reservationAppointment);
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
		return appointmentWithoutOrderBusiness.getCreateWithoutOrder(createWithoutOrder);
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
		return appointmentWithoutOrderBusiness.getDeleteWithoutOrder(deleteWithoutOrderRequest);
	}

}

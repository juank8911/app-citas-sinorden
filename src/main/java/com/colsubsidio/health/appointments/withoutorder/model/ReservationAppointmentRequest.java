package com.colsubsidio.health.appointments.withoutorder.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservationAppointmentRequest {

	@JsonProperty("reservaSinOrden")
	public ReserveWithoutOrderRequest reserveWithoutOrder;

}

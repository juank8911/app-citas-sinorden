package com.colsubsidio.health.appointments.withoutorder.model;

import java.util.List;

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
public class ReservationAppointmentResponse {

	@JsonProperty("resultado")
	public List<Result> result;
	@JsonProperty("reservaSinOrden")
	public ReserveWithoutOrderResponse reserveWithoutOrder;

}

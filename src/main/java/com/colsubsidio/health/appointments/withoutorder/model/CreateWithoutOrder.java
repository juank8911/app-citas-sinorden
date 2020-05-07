package com.colsubsidio.health.appointments.withoutorder.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateWithoutOrder {

	@JsonProperty("cita")
	public AppointmentCreateRequest appointment = new AppointmentCreateRequest();

}

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

@lombok.Data
class ReserveWithoutOrderRequest {

	@JsonProperty("cita")
	public AppointmentReserveRequest appointment;
	@JsonProperty("prestador")
	public LenderReserveRequest lender;
	@JsonProperty("paciente")
	public PatientReserveRequest patient;
	@JsonProperty("tipoPlanificacion")
	public String typePlanning;
	@JsonProperty("unidadTratamiento")
	public String treatmentUnit;
	@JsonProperty("eps")
	public String eps;
	@JsonProperty("consultorio")
	public String consultingRoom;

}

@lombok.Data
class AppointmentReserveRequest {

	@JsonProperty("fechaHora")
	public String datetime;

}

@lombok.Data
class LenderReserveRequest {

	@JsonProperty("codigo")
	public String code;

}

@lombok.Data
class PatientReserveRequest {

	@JsonProperty("codigo")
	public String code;

}


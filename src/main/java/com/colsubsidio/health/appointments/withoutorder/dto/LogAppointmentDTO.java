package com.colsubsidio.health.appointments.withoutorder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LogAppointmentDTO {

	@JsonProperty("tipoDocumento")
	private String typeDocument;
	@JsonProperty("numeroDocumento")
	private String numberDocument;
	@JsonProperty("nombre")
	private String name;
	@JsonProperty("reserva")
	private String idReservation;
	@JsonProperty("orden")
	private String idOrder;
	@JsonProperty("idEspecialidad")
	private String idSpecialty;
	@JsonProperty("descripcionEspecialidad")
	private String descriptionSpecialty;
	@JsonProperty("fecha")
	private String date;
	@JsonProperty("ipciente")
	private String ipClient;
	@JsonProperty("valor")
	private String value;

	@Override
	public String toString() {
		return typeDocument + "; " + numberDocument + "; " + name + "; " + idReservation + "; " + idOrder + "; "
				+ idSpecialty + "; " + descriptionSpecialty + "; " + date + "; " + ipClient;
	}

}

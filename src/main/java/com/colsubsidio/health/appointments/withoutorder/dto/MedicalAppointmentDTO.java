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
public class MedicalAppointmentDTO {

	@JsonProperty("fechaHora")
	private String dateTime;
	@JsonProperty("hora")
	private String hour;
	@JsonProperty("codigoCentroMedico")
	private String medicalCenterCode;
	@JsonProperty("nombreCentroMedico")
	private String medicalCenterName;
	@JsonProperty("codigoConvenio")
	private String conventionCode;
	@JsonProperty("nombreConvenio")
	private String conventionName;
	@JsonProperty("codigoMedico")
	private String codeDoctor;
	@JsonProperty("nombreMedico")
	private String nameDoctor;
	@JsonProperty("codigoPrestacion")
	private String codeBenefit;
	@JsonProperty("nombrePrestacion")
	private String nameBenefit;
	@JsonProperty("codigoEspecialidad")
	private String codeSpecialty;
	@JsonProperty("descripcionEspecialidad")
	private String descriptionSpecialty;
	@JsonProperty("codigoEstado")
	private String stateCode;
	@JsonProperty("descripcionEstado")
	private String stateDescription;
	@JsonProperty("consultorio")
	private String office;
	@JsonProperty("idReserva")
	private String idReserve;
	@JsonProperty("valor")
	private String value;
	@JsonProperty("numeroOrden")
	private String orderNumber;
	@JsonProperty("documentoContable")
	private String documentCountable;

}

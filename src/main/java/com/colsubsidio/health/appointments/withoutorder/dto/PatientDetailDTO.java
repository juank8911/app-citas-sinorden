package com.colsubsidio.health.appointments.withoutorder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
public class PatientDetailDTO {

	@JsonProperty("id")
	private String id;
	@JsonProperty("tipoDocumento")
	private String typeDocument;
	@JsonProperty("numeroDocumento")
	private String numberDocument;
	@JsonProperty("tratamiento")
	private String treatment;
	@JsonProperty("telefono")
	private String phone;
	@JsonProperty("correoElectronico")
	private String email;
	@JsonProperty("primerNombre")
	private String firstName;
	@JsonProperty("segundoNombre")
	private String secondName;
	@JsonProperty("primerApellido")
	private String firstLastname;
	@JsonProperty("segundoApellido")
	private String secondLastname;
	@JsonProperty("generoCodigo")
	private String genreCode;
	@JsonProperty("generoDescripcion")
	private String genreDescription;
	@JsonProperty("fechaNacimiento")
	private String birthdate;

	@JsonProperty("paisId")
	private String countryId;
	@JsonProperty("paisNombre")
	private String countryName;
	@JsonProperty("departamentoId")
	private String departmentId;
	@JsonProperty("departamentoNombre")
	private String departmentName;
	@JsonProperty("ciudadId")
	private String cityId;
	@JsonProperty("ciudadNombre")
	private String cityName;
	@JsonProperty("localidadId")
	private String localityId;
	@JsonProperty("localidadNombre")
	private String localityName;
	@JsonProperty("codigoPostal")
	private String postcode;
	@JsonProperty("direccionPrincipal")
	private String mainaddress;
	@JsonProperty("edad")
	private String age;
	@JsonProperty("eps")
	private String eps;
	@JsonProperty("tipoPaciente")
	private String patientType;
	@JsonProperty("nombreCompleto")
	private String fullname;

	public void buildFullname() {
		this.fullname = this.firstName;
		if (this.secondName != null) {
			this.fullname = this.fullname + " " + this.secondName;
		}
		this.fullname = this.fullname + " " + this.firstLastname;
		if (this.secondLastname != null) {
			this.fullname = this.fullname + " " + this.secondLastname;
		}
	}
}

package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PacientesDetalle {

	private String id;
	private String tipoDocumento;
	private String numeroDocumento;
	private String tratamiento;
	private String telefono;
	private String correoElectronico;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String generoCodigo;
	private String generoDescripcion;
	private String fechaNacimiento;

	private String paisId;
	private String paisNombre;
	private String departamentoId;
	private String departamentoNombre;
	private String ciudadId;
	private String ciudadNombre;
	private String localidadId;
	private String localidadNombre;
	private String codigoPostal;
	private String direccionPrincipal;
	private String edad;
	private String eps;
	private String tipoPaciente;
	private String nombreCompleto;

	private Derechos ComprobacionDerechos;

	public PacientesDetalle(String id, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, String generoCodigo, String generoDescripcion, String fechaNacimiento) {
		this.id = id;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.generoCodigo = generoCodigo;
		this.generoDescripcion = generoDescripcion;
		this.fechaNacimiento = fechaNacimiento;
	}

	public void nombre() {
		this.nombreCompleto = this.primerNombre;
		if (this.segundoNombre != null) {
			this.nombreCompleto = this.nombreCompleto + " " + this.segundoNombre;
		}
		this.nombreCompleto = this.nombreCompleto + " " + this.primerApellido;
		if (this.segundoApellido != null) {
			this.nombreCompleto = this.nombreCompleto + " " + this.segundoApellido;
		}
	}
}

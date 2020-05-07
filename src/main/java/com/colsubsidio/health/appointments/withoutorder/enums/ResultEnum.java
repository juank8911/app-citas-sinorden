package com.colsubsidio.health.appointments.withoutorder.enums;

public enum ResultEnum {
	SUCCESS("100", "Consulta Exitosa"), ERROR("200", "Error en la consulta");

	private String code;
	private String description;

	private ResultEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}

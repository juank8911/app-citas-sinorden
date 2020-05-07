package com.colsubsidio.health.appointments.withoutorder.enums;

public enum ResultAppointmentEnum {
	SUCCESS("I", "Agendamiento exitoso"), WARNING("W", "Se ha presentado un error en el proceso de agendamiento"), ERROR("E", "Se ha presentado un error en el proceso de agendamiento, intenta de nuevo");

	private String code;
	private String description;

	private ResultAppointmentEnum(String code, String description) {
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

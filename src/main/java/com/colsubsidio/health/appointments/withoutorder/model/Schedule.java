package com.colsubsidio.health.appointments.withoutorder.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@lombok.Data
@Builder
@AllArgsConstructor
@ToString
public class Schedule {

	private String id;
	private Timestamp date;
	private String order;
	private String reservation;
	private String specialty;
	private String state;
	private String type_document;
	private String document_number;
	private String data;
	private String cancellation;
	private Timestamp modified;
	private String modified_by;
	private Timestamp created;
	private String created_by;

	public Schedule(Timestamp date, String order, String reservation, String specialty, String state,
			String type_document, String document_number, String data, String cancellation, Timestamp modified,
			String modified_by, Timestamp created, String created_by) {
		this.date = date;
		this.order = order;
		this.reservation = reservation;
		this.specialty = specialty;
		this.state = state;
		this.type_document = type_document;
		this.document_number = document_number;
		this.data = data;
		this.cancellation = cancellation;
		this.modified = modified;
		this.modified_by = modified_by;
		this.created = created;
		this.created_by = created_by;
	}

	public Schedule(String order, String reservation, String specialty, String state, String type_document,
			String document_number, String cancellation, Timestamp modified, String modified_by) {
		this.order = order;
		this.reservation = reservation;
		this.specialty = specialty;
		this.state = state;
		this.type_document = type_document;
		this.document_number = document_number;
		this.cancellation = cancellation;
		this.modified = modified;
		this.modified_by = modified_by;
	}

}

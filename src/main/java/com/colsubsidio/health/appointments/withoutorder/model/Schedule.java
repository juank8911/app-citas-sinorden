package com.colsubsidio.health.appointments.withoutorder.model;

import java.io.Serializable;

import com.microsoft.azure.storage.table.TableServiceEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@ToString
public class Schedule extends TableServiceEntity implements Serializable {

	private String date;
	private String order;
	private String reservation;
	private String specialty;
	private String state;
	private String documentType;
	private String documentNumber;
	private String appointmentType;
	private String data;
	private String cancellation;
	private String modified;
	private String modifiedBy;
	private String created;
	private String createdBy;
	private int retry;

	public Schedule() {

	}

	public Schedule(String date, String order, String reservation, String specialty, String state, String documentType,
			String documentNumber, String data, String cancellation, String modified, String modifiedBy, String created,
			String createdBy) {
		this.date = date;
		this.order = order;
		this.reservation = reservation;
		this.specialty = specialty;
		this.state = state;
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.data = data;
		this.cancellation = cancellation;
		this.modified = modified;
		this.modifiedBy = modifiedBy;
		this.created = created;
		this.createdBy = createdBy;
	}

	public Schedule(String order, String reservation, String specialty, String state, String documentType,
			String documentNumber, String cancellation, String modified, String modifiedBy) {
		this.order = order;
		this.reservation = reservation;
		this.specialty = specialty;
		this.state = state;
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.cancellation = cancellation;
		this.modified = modified;
		this.modifiedBy = modifiedBy;
	}

}

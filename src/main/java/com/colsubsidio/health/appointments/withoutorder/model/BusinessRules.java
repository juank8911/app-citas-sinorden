package com.colsubsidio.health.appointments.withoutorder.model;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BusinessRules {

	private String id;
	private String name;
	private String value;
	private Timestamp created;
	private String created_by;
	private Timestamp modified;
	private String modified_by;
	private String state;
	private String category;
	private String description;

}
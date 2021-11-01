package com.colsubsidio.health.appointments.withoutorder.model;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduleLogElasticSearch {
	String app;
	String version;
	String index;
	String type;
	String eventDate;
	Schedule schedule;
}

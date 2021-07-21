package com.colsubsidio.health.appointments.withoutorder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChangeBenefitPatientType{
	
	private String code;
	private String description;

}

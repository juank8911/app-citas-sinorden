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
public class ClientDetailDTO {

	@JsonProperty("ipAddress")
	private String ipAddress;
}

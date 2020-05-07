package com.colsubsidio.health.appointments.withoutorder.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateWithoutOrderResponse {

	@JsonProperty("resultado")
	public List<Result> result;
	@JsonProperty("creacionSinOrden")
	public CreateWithoutOrder createWithoutOrder = new CreateWithoutOrder();

}
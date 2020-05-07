package com.colsubsidio.health.appointments.withoutorder.dto.apigee;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class ApigeeTokenReqDTO {

    private String clienteId;
	private String clienteSecreto;

}


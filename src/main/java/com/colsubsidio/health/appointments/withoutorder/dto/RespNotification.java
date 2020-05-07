package com.colsubsidio.health.appointments.withoutorder.dto;

import java.util.List;

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
public class RespNotification {
	
	private List<Resultado> resultado;
	
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	@Data
	@Builder
	public static class Resultado{
		
		private String descripcion;
	    private String codigo;
	}

}

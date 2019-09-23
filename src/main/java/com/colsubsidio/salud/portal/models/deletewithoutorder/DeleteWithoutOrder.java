package com.colsubsidio.salud.portal.models.deletewithoutorder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DeleteWithoutOrder {

	private MedicalAppointment cita;

	public DeleteWithoutOrder() {
		this.cita = new MedicalAppointment();
	}

}

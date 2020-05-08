package com.colsubsidio.health.appointments.withoutorder.dto;

import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderRequest;

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
public class DeleteInformationDTO {

	private DeleteWithoutOrderRequest deleteWithoutOrder;
	private PatientDetailDTO patientDetail;
	private ClientDetailDTO clientDetail;
	private MedicalAppointmentDTO medicalAppointment;
	private SpecialtyDetailDTO specialtyDetail;
}

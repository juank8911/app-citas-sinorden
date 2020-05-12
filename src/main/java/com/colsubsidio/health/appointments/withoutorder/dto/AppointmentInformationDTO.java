package com.colsubsidio.health.appointments.withoutorder.dto;

import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.ReservationAppointmentRequest;

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
public class AppointmentInformationDTO {

	private ReservationAppointmentRequest reserveWithoutOrderRequest;
	private CreateWithoutOrderRequest createWithoutOrderRequest;
	private PatientDetailDTO patientDetail;
	private ClientDetailDTO clientDetail;
	private SpecialtyDetailDTO specialtyDetail;
}

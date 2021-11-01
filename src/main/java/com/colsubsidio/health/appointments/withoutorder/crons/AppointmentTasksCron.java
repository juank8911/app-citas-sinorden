package com.colsubsidio.health.appointments.withoutorder.crons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.business.AppointmentTasksBusiness;

/**
 *
 * @author LordMVP
 */
@Component
@ConditionalOnProperty(value = "scheduled.agendamiento.tarea.enabled", havingValue = "true", matchIfMissing = false)
public class AppointmentTasksCron {

	@Autowired
	private AppointmentTasksBusiness appointmentTasksBusiness;

	@Scheduled(cron = "${scheduled.agendamiento.tarea.borrarcitas}")
	public void searchQuotesError() {
		appointmentTasksBusiness.searchAppoinmentError();
	}

}

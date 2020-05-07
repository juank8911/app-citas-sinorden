package com.colsubsidio.health.appointments.withoutorder.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colsubsidio.health.appointments.withoutorder.business.AppointmentTasksBusiness;

/**
 *
 * @author LordMVP
 */
@RestController
@RequestMapping("/agendamiento/tareas")
public class AppointmentTasksController {

	@Autowired
	private AppointmentTasksBusiness appointmentTasksBusiness;

	@GetMapping(value = "/borrarcitas")
	public void searchQuotesError() {
		appointmentTasksBusiness.searchAppoinmentError();
	}

}

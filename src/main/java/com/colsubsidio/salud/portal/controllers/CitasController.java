package com.colsubsidio.salud.portal.controllers;

import com.colsubsidio.salud.portal.services.interfaces.ICitasService;
import com.colsubsidio.salud.transversal.utils.HandleDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/citas")
public class CitasController {

	@Autowired
	HandleDate handleDate;

	@Autowired
	private ICitasService citasService;

	@Scheduled(fixedDelay = 300000)
	@RequestMapping(value = "/tarea/borrarCitaSinOrden", produces = "application/json", method = RequestMethod.GET)
	public void searchQuotesError() {
		citasService.searchQuotesError();
	}

}

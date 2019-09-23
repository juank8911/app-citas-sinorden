package com.colsubsidio.salud.portal.controllers;

import com.colsubsidio.salud.portal.services.interfaces.ICitasService;
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
    private ICitasService citasService;

    @Scheduled(cron = "0 0 0 ? * *")
    @RequestMapping(value = "/tarea/borrarCitaSinOrden", produces = "application/json", method = RequestMethod.GET)
    public void searchQuotesError() {
        citasService.searchQuotesError();
    }

}

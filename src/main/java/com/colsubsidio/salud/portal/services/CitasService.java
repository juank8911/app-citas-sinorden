/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.portal.services;

import com.colsubsidio.salud.portal.dao.interfaces.IScheduleDAO;
import com.colsubsidio.salud.portal.models.Schedule;
import com.colsubsidio.salud.portal.models.deletewithoutorder.Delete;
import com.colsubsidio.salud.portal.services.interfaces.ICitasService;
import com.colsubsidio.salud.portal.services.interfaces.IDeleteWithoutOrderService;
import com.colsubsidio.salud.portal.utils.LogsManager;
import com.colsubsidio.salud.portal.utils.HandleDate;
import com.colsubsidio.salud.portal.utils.ProcessChain;
import com.google.gson.Gson;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author Usuario
 */
@Component
public class CitasService implements ICitasService {

	@Autowired
	private ProcessChain o;

	@Autowired
	private HandleDate f;

	@Autowired
	private IScheduleDAO scheduleDAO;

	@Autowired
	private IDeleteWithoutOrderService deleteWithoutOrderService;

	@Autowired
	private LogsManager logsManager;

	public void searchQuotesError() {

		Delete delete;
		Gson gson = new Gson();
		ResponseEntity<?> responseEntity = null;
		try {

			List<Schedule> listSchedule = scheduleDAO.selectSchedule();
			logsManager.LogsBuildAppInsights("info", "excecuteTaskError; search for appointments with error");
			if (listSchedule != null && !listSchedule.isEmpty()) {
				logsManager.LogsBuildAppInsights("info",
						"excecuteTaskError; list appointments with error, quantity = " + listSchedule.size());
				for (Schedule schedule : listSchedule) {
					logsManager.LogsBuildAppInsights("info",
							"excecuteTaskError; going through appointments with error");
					if (schedule != null && !schedule.getReservation().isEmpty()) {
						logsManager.LogsBuildAppInsights("info",
								"excecuteTaskError; appointment number reserve = " + schedule.getReservation());
						delete = new Delete();
						delete.getBorrarSinOrden().getCita().setIdReserva(schedule.getReservation());
						responseEntity = deleteWithoutOrderService.deleteWithoutOrder(delete);

						if (responseEntity.getStatusCode().equals(HttpStatus.OK)
								&& !responseEntity.getBody().equals("")) {
							logsManager.LogsBuildAppInsights("info",
									"excecuteTaskError; correct appointment elimination" + gson.toJson(responseEntity));
							scheduleDAO.updateSchedule(new Schedule("", schedule.getReservation(),
									schedule.getSpecialty(), "cancelTask", schedule.getType_document(),
									schedule.getDocument_number(), gson.toJson(responseEntity),
									f.getFechaHoraTimeStamp(0), "deleteWithoutOrder"));
						} else {
							logsManager.LogsBuildAppInsights("info",
									"excecuteTaskError; incorrect appointment elimination"
											+ gson.toJson(responseEntity));
						}
					}
				}
			} else {
				logsManager.LogsBuildAppInsights("info", "excecuteTaskError; no appointments found with error");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

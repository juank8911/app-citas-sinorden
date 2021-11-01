package com.colsubsidio.health.appointments.withoutorder.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.Schedule;
import com.colsubsidio.health.appointments.withoutorder.services.AppointmentWithoutOrderService;
import com.colsubsidio.health.appointments.withoutorder.tablestorage.ScheduleStorage;
import com.colsubsidio.health.appointments.withoutorder.util.DateUtils;
import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;
import com.google.gson.Gson;

@Component
public class AppointmentTasksBusiness {

	@Autowired
	DateUtils dateUtils;
	@Autowired
	LogsManager logsManager;
	@Autowired
	AppointmentWithoutOrderService appointmentWithoutOrderService;
//	@Autowired
//	ScheduleDAO scheduleDAO;
	@Autowired
	ScheduleStorage scheduleStorage;

	private static String exception = "exception";
	private static String information = "info";

	@Async("asyncExecutor")
	public void searchAppoinmentError() {

		logsManager.logsBuildAppInsights(information, "Execute task appointments with > " + new Date());

		CreateWithoutOrderRequest deleteAppoint;
		Gson gson = new Gson();
		ResponseEntity<CreateWithoutOrderResponse> responseEntity = null;
		CreateWithoutOrderResponse response;
		String status = "";
		List<Schedule> listSchedule = new ArrayList<>();

		try {

			List<Schedule> listScheduleAux = scheduleStorage.selectSchedule();

			if (listScheduleAux != null && !listScheduleAux.isEmpty()) {
				logsManager.logsBuildAppInsights(information,
						"AppointmentTasksBusiness; new search for appointments withoutorder with error");
				for (Schedule schedule : listScheduleAux) {
					if (!listSchedule.contains(schedule)) {
						listSchedule.add(schedule);
					}
				}
				validateSchedule(listSchedule);
			}

			logsManager.logsBuildAppInsights(information,
					"AppointmentTasksBusiness; appointments withoutorder > listSchedule: " + listSchedule.size()
							+ " -- listScheduleAux: " + listSchedule.size());

			if (listSchedule != null && !listSchedule.isEmpty()) {
				logsManager.logsBuildAppInsights(information,
						"AppointmentTasksBusiness; list appointments withoutorder with error, quantity = "
								+ listSchedule.size());
				Schedule schedule = null;

				do {

					schedule = listSchedule.get(0);
					logsManager.logsBuildAppInsights(information, "processSchedule " + schedule);
					if (schedule != null && !schedule.getReservation().isEmpty()) {
						deleteAppoint = new CreateWithoutOrderRequest();
						deleteAppoint.setIdAppointment(schedule.getReservation());
						deleteAppoint.setDesistAppointment("X");

						responseEntity = appointmentWithoutOrderService.getCreateWithoutOrder(deleteAppoint);

						if (responseEntity.getStatusCode().equals(HttpStatus.OK) && responseEntity.getBody() != null) {
							response = responseEntity.getBody();
							status = "ERRORCANCEL";
							if (response != null && !response.getResult().isEmpty()) {
								status = response.getResult().get(0).getCode().equals("I") ? "CANCELTASK" : status;
							}

							schedule.setState(status);
							schedule.setModified(dateUtils.getDateString("yyyy-MM-dd HH:mm:ss"));
							schedule.setModifiedBy(schedule.getDocumentNumber());
							schedule.setCancellation(gson.toJson(response));
							schedule.setRetry(schedule.getRetry() + 1);
							scheduleStorage.updateSchedule(schedule);
						} else {
							logsManager.logsBuildAppInsights(information,
									"AppointmentTasksBusiness; incorrect appointment elimination"
											+ gson.toJson(responseEntity));
						}
					}
					listSchedule.remove(schedule);
				} while (listSchedule != null && !listSchedule.isEmpty());
			} else {
				logsManager.logsBuildAppInsights(information,
						"AppointmentTasksBusiness; no appointments withoutorder found with error");
			}

		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentTasksBusiness; searchAppoinmentError " + ex.getMessage());
		}
	}

	public void validateSchedule(List<Schedule> listSchedule) {
		if (listSchedule != null && !listSchedule.isEmpty()) {
			listSchedule.removeIf(opc -> !dateUtils.validateMinutes(opc.getDate()));
		}
	}

}

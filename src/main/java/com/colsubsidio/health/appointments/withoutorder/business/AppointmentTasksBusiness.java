package com.colsubsidio.health.appointments.withoutorder.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.dao.ScheduleDAO;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.Schedule;
import com.colsubsidio.health.appointments.withoutorder.services.AppointmentWithoutOrderService;
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
	@Autowired
	ScheduleDAO scheduleDAO;

	private static String exception = "exception";
	private static String information = "info";

	@Async("asyncExecutor")
	public void searchAppoinmentError() {

		CreateWithoutOrderRequest deleteAppoint;
		Gson gson = new Gson();
		ResponseEntity<CreateWithoutOrderResponse> responseEntity = null;

		try {

			List<Schedule> listSchedule = scheduleDAO.selectSchedule();
			logsManager.logsBuildAppInsights(information,
					"AppointmentTasksBusiness; search for appointments with error");
			if (listSchedule != null && !listSchedule.isEmpty()) {
				logsManager.logsBuildAppInsights(information,
						"AppointmentTasksBusiness; list appointments with error, quantity = " + listSchedule.size());
				for (Schedule schedule : listSchedule) {
					if (schedule != null && !schedule.getReservation().isEmpty()) {
						deleteAppoint = new CreateWithoutOrderRequest();
						deleteAppoint.setIdAppointment(schedule.getReservation());
						deleteAppoint.setDesistAppointment("X");

						responseEntity = appointmentWithoutOrderService.getCreateWithoutOrder(deleteAppoint);
						if (responseEntity.getStatusCode().equals(HttpStatus.OK)
								&& !responseEntity.getBody().equals("")) {
							scheduleDAO.updateSchedule(new Schedule("", schedule.getReservation(),
									schedule.getSpecialty(), "cancelTask", schedule.getType_document(),
									schedule.getDocument_number(), gson.toJson(responseEntity),
									dateUtils.getDateTimeTimeStamp(), "deleteWithoutOrder"));
						} else {
							logsManager.logsBuildAppInsights(information,
									"AppointmentTasksBusiness; incorrect appointment elimination"
											+ gson.toJson(responseEntity));
						}
					}
				}
			} else {
				logsManager.logsBuildAppInsights(information,
						"AppointmentTasksBusiness; no appointments found with error");
			}

		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentTasksBusiness; searchAppoinmentError " + ex.getMessage());
		}
	}

}

package com.colsubsidio.health.appointments.withoutorder.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.dao.LogsDAO;
import com.colsubsidio.health.appointments.withoutorder.dao.ScheduleDAO;
import com.colsubsidio.health.appointments.withoutorder.dto.DeleteInformationDTO;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.services.AppointmentWithoutOrderService;
import com.colsubsidio.health.appointments.withoutorder.util.DateUtils;
import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;
import com.google.gson.Gson;

@Component
public class DeleteWithoutOrderBusiness {

	@Autowired
	DateUtils dateUtils;
	@Autowired
	LogsManager logsManager;
	@Autowired
	AppointmentWithoutOrderService appointmentWithoutOrderService;
	@Autowired
	LogsDAO logsDAO;
	@Autowired
	ScheduleDAO scheduleDAO;
	@Autowired
	Gson gson;

	private static String exception = "exception";

	public ResponseEntity<?> getDeleteWithoutOrder(DeleteInformationDTO deleteInformation) {

		ResponseEntity<DeleteWithoutOrderResponse> response = null;

		DeleteWithoutOrderRequest deleteWithoutOrderRequest = deleteInformation.getDeleteWithoutOrder();

		try {

			response = appointmentWithoutOrderService.getDeleteWithoutOrder(deleteWithoutOrderRequest);
			if (response != null) {

			}

		} catch (Exception e) {
			logsManager.logsBuildAppInsights(exception,
					"DeleteWithoutOrderBusiness; getDeleteWithoutOrder; " + e.getMessage());
		}
		return response;
	}

}

package com.colsubsidio.health.appointments.withoutorder.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.dao.LogsDAO;
import com.colsubsidio.health.appointments.withoutorder.dao.ScheduleDAO;
import com.colsubsidio.health.appointments.withoutorder.dto.DeleteInformationDTO;
import com.colsubsidio.health.appointments.withoutorder.dto.LogAppointmentDTO;
import com.colsubsidio.health.appointments.withoutorder.enums.ResultAppointmentEnum;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.Result;
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

	public ResponseEntity<DeleteWithoutOrderResponse> getDeleteWithoutOrder(DeleteInformationDTO deleteInformation) {

		DeleteWithoutOrderResponse response = new DeleteWithoutOrderResponse();
		ResponseEntity<DeleteWithoutOrderResponse> responseDelete = null;
		DeleteWithoutOrderRequest deleteWithoutOrderRequest = deleteInformation.getDeleteWithoutOrder();
		LogAppointmentDTO logAppoint = new LogAppointmentDTO();
		List<Result> resultList = new ArrayList<>();
		try {
			this.buildPatientData(logAppoint, deleteInformation);
			responseDelete = appointmentWithoutOrderService.getDeleteWithoutOrder(deleteWithoutOrderRequest);
			if (responseDelete != null && responseDelete.getStatusCode().equals(HttpStatus.OK)
					&& responseDelete.getBody() != null) {
				logsDAO.createLog("cancel", logAppoint.toString());
				response = responseDelete.getBody();
			} else {
				resultList.add(new Result(ResultAppointmentEnum.ERROR.getCode(),
						ResultAppointmentEnum.ERROR.getDescription()));
				response.setResult(resultList);
			}
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception,
					"DeleteWithoutOrderBusiness; getDeleteWithoutOrder; " + ex.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void buildPatientData(LogAppointmentDTO logAppoint, DeleteInformationDTO deleteInformation)
			throws NullPointerException {

		deleteInformation.getPatientDetail().buildFullname();
		logAppoint.setTypeDocument(deleteInformation.getPatientDetail().getTypeDocument());
		logAppoint.setNumberDocument(deleteInformation.getPatientDetail().getNumberDocument());
		logAppoint.setName(deleteInformation.getPatientDetail().getFullname());
		logAppoint.setModality(deleteInformation.getMedicalAppointment().getModality().isEmpty() ? "P"
				: deleteInformation.getMedicalAppointment().getModality());
		logAppoint.setIdReservation(deleteInformation.getMedicalAppointment().getIdReserve());
		logAppoint.setIdOrder(null);
		logAppoint.setIdSpecialty(deleteInformation.getSpecialtyDetail().getCode());
		logAppoint.setDescriptionSpecialty(deleteInformation.getSpecialtyDetail().getDescription());
		logAppoint.setDate(deleteInformation.getMedicalAppointment().getDate());
		logAppoint.setIpClient(deleteInformation.getClientDetail().getIpAddress());
	}

}

package com.colsubsidio.health.appointments.withoutorder.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.dao.LogsDAO;
import com.colsubsidio.health.appointments.withoutorder.dao.ScheduleDAO;
import com.colsubsidio.health.appointments.withoutorder.dto.AppointmentInformationDTO;
import com.colsubsidio.health.appointments.withoutorder.dto.LogAppointmentDTO;
import com.colsubsidio.health.appointments.withoutorder.model.AppointmentReserveResponse;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrder;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.DeleteWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.ReservationAppointmentRequest;
import com.colsubsidio.health.appointments.withoutorder.model.ReservationAppointmentResponse;
import com.colsubsidio.health.appointments.withoutorder.model.ReserveWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.Result;
import com.colsubsidio.health.appointments.withoutorder.model.Schedule;
import com.colsubsidio.health.appointments.withoutorder.services.AppointmentWithoutOrderService;
import com.colsubsidio.health.appointments.withoutorder.util.DateUtils;
import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;
import com.colsubsidio.health.appointments.withoutorder.enums.ResultAppointmentEnum;
import com.google.gson.Gson;

@Component
public class AppointmentWithoutOrderBusiness {

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

	public ResponseEntity<CreateWithoutOrderResponse> getReservationWithoutOrder(AppointmentInformationDTO appointmentInformation) {

		CreateWithoutOrderResponse createWithoutOrderResponse = new CreateWithoutOrderResponse();
		LogAppointmentDTO logAppoint = new LogAppointmentDTO();
		List<Result> resultList = new ArrayList<>();
		ReservationAppointmentRequest reservationAppointment;

		ResponseEntity<ReservationAppointmentResponse> responseReserve = null;

		try {

			this.buildPatientData(logAppoint, appointmentInformation);
			logsDAO.createLog("reservationInformation", logAppoint.toString());

			reservationAppointment = appointmentInformation.getReserveWithoutOrderRequest();
			responseReserve = appointmentWithoutOrderService.getReservationAppointment(reservationAppointment);

			if (responseReserve != null && responseReserve.getStatusCode().equals(HttpStatus.OK)) {
				this.validateReservation(logAppoint, reservationAppointment,
						responseReserve.getBody().getReserveWithoutOrder());
				if (logAppoint.getIdReservation() != null) {
					createWithoutOrderResponse = this.getCreateWithoutOrder(logAppoint).getBody();
				}
			}
			resultList.add(new Result(ResultAppointmentEnum.WARNING.getCode(),
					ResultAppointmentEnum.WARNING.getDescription()));
			createWithoutOrderResponse
					.setResult(responseReserve != null && responseReserve.getBody().getResultado() != null
							&& !responseReserve.getBody().getResultado().isEmpty()
									? responseReserve.getBody().getResultado()
									: resultList);
		} catch (Exception e) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getReservationAppointment; " + e.getMessage());
		}
		return new ResponseEntity<>(createWithoutOrderResponse, HttpStatus.OK);
	}

	public ResponseEntity<CreateWithoutOrderResponse> getCreateWithoutOrder(LogAppointmentDTO logAppoint) {

		ResponseEntity<CreateWithoutOrderResponse> response = null;
		CreateWithoutOrderRequest createWithoutOrder = new CreateWithoutOrderRequest();

		Schedule schedule = new Schedule("", logAppoint.getIdReservation(), logAppoint.getIdSpecialty(), "pending",
				logAppoint.getTypeDocument(), logAppoint.getNumberDocument(), "", dateUtils.getDateTimeTimeStamp(),
				logAppoint.getNumberDocument());

		try {
			logsDAO.createLog("createInformation", logAppoint.toString());
			createWithoutOrder.setIdAppointment(logAppoint.getIdReservation());
			createWithoutOrder.setDesistAppointment("");
			response = appointmentWithoutOrderService.getCreateWithoutOrder(createWithoutOrder);

			if (response != null && response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
				response = validateCreate(logAppoint, response.getBody(), schedule);
			} else {
				response = errorCreate(logAppoint, createWithoutOrder, schedule, gson.toJson(response));
			}
		} catch (Exception e) {
			response = getDeleteWithoutOrder(logAppoint);
			schedule.setState("pending");
			scheduleDAO.updateSchedule(schedule);
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getCreateWithoutOrder; " + e.getMessage());
		}
		return response;
	}

	public ResponseEntity<CreateWithoutOrderResponse> getDeleteWithoutOrder(LogAppointmentDTO logAppoint) {

		CreateWithoutOrderResponse response = new CreateWithoutOrderResponse();
		ResponseEntity<DeleteWithoutOrderResponse> responseDelete = null;
		DeleteWithoutOrderRequest deleteWithoutOrder = new DeleteWithoutOrderRequest();
		List<Result> resultList = new ArrayList<>();
		try {

			if (logAppoint.getIdReservation() != null && !logAppoint.getIdReservation().isEmpty()) {
				deleteWithoutOrder.getDeleteWithoutOrder().getAppointment().setIdReserve(logAppoint.getIdReservation());
				responseDelete = appointmentWithoutOrderService.getDeleteWithoutOrder(deleteWithoutOrder);

				if (responseDelete.getStatusCode().equals(HttpStatus.OK) && responseDelete.getBody() != null) {
					logsDAO.createLog("cancelAplication", logAppoint.toString());
					resultList.add(new Result(ResultAppointmentEnum.ERROR.getCode(),
							ResultAppointmentEnum.ERROR.getDescription()));
				} else {
					resultList.add(new Result(ResultAppointmentEnum.ERROR.getCode(),
							ResultAppointmentEnum.ERROR.getDescription()));
				}
			}
			response.setResult(resultList);
		} catch (Exception e) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getDeleteWithoutOrder; " + e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	private void buildPatientData(LogAppointmentDTO logAppoint, AppointmentInformationDTO appointmentInformation)
			throws Exception {

		appointmentInformation.getPatientDetail().buildFullname();
		logAppoint.setTypeDocument(appointmentInformation.getPatientDetail().getTypeDocument());
		logAppoint.setNumberDocument(appointmentInformation.getPatientDetail().getNumberDocument());
		logAppoint.setName(appointmentInformation.getPatientDetail().getFullname());
		logAppoint.setIdReservation(null);
		logAppoint.setIdOrder(null);
		logAppoint.setIdSpecialty(appointmentInformation.getSpecialtyDetail().getCode());
		logAppoint.setDescriptionSpecialty(appointmentInformation.getSpecialtyDetail().getDescription());
		logAppoint.setDate(appointmentInformation.getReserveWithoutOrderRequest().getReserveWithoutOrder()
				.getAppointment().getDatetime());
		logAppoint.setIpClient(appointmentInformation.getClientDetail().getIpAddress());
	}

	private void validateReservation(LogAppointmentDTO logAppoint, ReservationAppointmentRequest reservationAppointment,
			ReserveWithoutOrderResponse reserveWithoutOrder) {

		try {

			if (reserveWithoutOrder != null && reserveWithoutOrder.getAppointment() != null) {
				AppointmentReserveResponse appointmentReserve = reserveWithoutOrder.getAppointment();
				if (appointmentReserve.getIdReserve() != null && !appointmentReserve.getIdReserve().equals("null")) {
					logAppoint.setIdReservation(reserveWithoutOrder.getAppointment().getIdReserve());
					logAppoint.setValue(reserveWithoutOrder.getAppointment().getValue());
					logsDAO.createLog("reservation", logAppoint.toString());

					scheduleDAO.insertSchedule(
							new Schedule(dateUtils.getDateTimeTimeStamp(), "", logAppoint.getIdReservation(),
									logAppoint.getIdSpecialty(), "reservation", logAppoint.getTypeDocument(),
									logAppoint.getNumberDocument(), gson.toJson(reservationAppointment), "", null, "",
									dateUtils.getDateTimeTimeStamp(), logAppoint.getNumberDocument()));
				}
			}
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; validateReservation; " + ex.getMessage());
		}
	}

	private ResponseEntity<CreateWithoutOrderResponse> validateCreate(LogAppointmentDTO logAppoint,
			CreateWithoutOrderResponse createWithoutOrderResponse, Schedule schedule) {

		CreateWithoutOrder createWithoutOrder;
		List<Result> resultList = new ArrayList<>();

		try {
			if (createWithoutOrderResponse.getResult() != null && !createWithoutOrderResponse.getResult().isEmpty()) {
				for (Result result : createWithoutOrderResponse.getResult()) {
					if (result.getCode().equals("E")) {
						getDeleteWithoutOrder(logAppoint);
						schedule.setState("pending");
						scheduleDAO.updateSchedule(schedule);
					} else if (result.getCode().equals("I")) {
						createWithoutOrder = new CreateWithoutOrder();
						logsDAO.createLog("create", logAppoint.toString());
						schedule.setState("create");
						scheduleDAO.updateSchedule(schedule);
						createWithoutOrder.getAppointment().setIdReserve(logAppoint.getIdReservation());
						createWithoutOrder.getAppointment().setValue(logAppoint.getValue());
						createWithoutOrderResponse.setCreateWithoutOrder(createWithoutOrder);
					}
				}
			} else {
				getDeleteWithoutOrder(logAppoint);
				schedule.setState("pending");
				scheduleDAO.updateSchedule(schedule);

				resultList.add(new Result(ResultAppointmentEnum.WARNING.getCode(),
						ResultAppointmentEnum.WARNING.getDescription()));

				createWithoutOrderResponse.setResult(resultList);
				createWithoutOrderResponse.setCreateWithoutOrder(null);
			}
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; validateCreate; " + ex.getMessage());
		}
		return new ResponseEntity<>(createWithoutOrderResponse, HttpStatus.OK);
	}

	private ResponseEntity<CreateWithoutOrderResponse> errorCreate(LogAppointmentDTO logAppoint,
			CreateWithoutOrderRequest createWithoutOrder, Schedule schedule, String response) {

		CreateWithoutOrderResponse createWithoutOrderResponse = new CreateWithoutOrderResponse();
		List<Result> resultList = new ArrayList<>();

		schedule.setState("pending");
		scheduleDAO.updateSchedule(schedule);

		resultList.add(
				new Result(ResultAppointmentEnum.WARNING.getCode(), ResultAppointmentEnum.WARNING.getDescription()));

		createWithoutOrderResponse.setResult(resultList);
		createWithoutOrderResponse.setCreateWithoutOrder(null);

		logsManager.logsBuildAppInsights("error", "AppointmentWithoutOrderBusiness; errorCreate; createJSON="
				+ gson.toJson(createWithoutOrder) + "; datos=" + gson.toJson(logAppoint) + "; response: " + response);

		return new ResponseEntity<>(createWithoutOrderResponse, HttpStatus.OK);
	}

	public ResponseEntity<ReservationAppointmentResponse> getReservationWithoutOrder(
			ReservationAppointmentRequest reservationAppointment) {

		ResponseEntity<ReservationAppointmentResponse> response = null;

		try {
			response = appointmentWithoutOrderService.getReservationAppointment(reservationAppointment);
		} catch (Exception e) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getReservationAppointment; " + e.getMessage());
		}
		return response;
	}

	public ResponseEntity<CreateWithoutOrderResponse> getCreateWithoutOrder(
			CreateWithoutOrderRequest createWithoutOrder) {

		ResponseEntity<CreateWithoutOrderResponse> response = null;

		try {
			response = appointmentWithoutOrderService.getCreateWithoutOrder(createWithoutOrder);
		} catch (Exception e) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getCreateWithoutOrder; " + e.getMessage());
		}
		return response;
	}

	public ResponseEntity<DeleteWithoutOrderResponse> getDeleteWithoutOrder(
			DeleteWithoutOrderRequest deleteWithoutOrderRequest) {

		ResponseEntity<DeleteWithoutOrderResponse> response = null;

		try {
			response = appointmentWithoutOrderService.getDeleteWithoutOrder(deleteWithoutOrderRequest);
		} catch (Exception e) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getDeleteWithoutOrder; " + e.getMessage());
		}
		return response;
	}

}

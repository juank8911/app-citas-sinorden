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
import com.colsubsidio.health.appointments.withoutorder.model.ChangeBenefitPatientType;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrder;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderRequest;
import com.colsubsidio.health.appointments.withoutorder.model.CreateWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.ReservationAppointmentRequest;
import com.colsubsidio.health.appointments.withoutorder.model.ReservationAppointmentResponse;
import com.colsubsidio.health.appointments.withoutorder.model.ReserveWithoutOrderResponse;
import com.colsubsidio.health.appointments.withoutorder.model.Result;
import com.colsubsidio.health.appointments.withoutorder.model.Schedule;
import com.colsubsidio.health.appointments.withoutorder.services.AppointmentWithoutOrderService;
import com.colsubsidio.health.appointments.withoutorder.services.ChangeBenefitPatientTypeService;
import com.colsubsidio.health.appointments.withoutorder.util.DateUtils;
import com.colsubsidio.health.appointments.withoutorder.util.DocumentUtils;
import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;
import com.colsubsidio.health.appointments.withoutorder.enums.ResultAppointmentEnum;
import com.google.gson.Gson;

@Component
public class AppointmentWithoutOrderBusiness {

	@Autowired
	DocumentUtils documentUtils;
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
	
	@Autowired
	ChangeBenefitPatientTypeService changeBenefitPatientTypeService;

	private static String exception = "exception";
	private static String cancelAplication = "cancelAplication";

	public ResponseEntity<CreateWithoutOrderResponse> getReservationWithoutOrderMerge(
			AppointmentInformationDTO appointmentInformation) {

		//apply us rule not support by SAP, for let flow Valoration Odontology such as PARTICULAR from patients POS
		applyRuleNotSupportBySAP(appointmentInformation);

		CreateWithoutOrderResponse createWithoutOrderResponse = null;
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
				if (responseReserve.getBody() != null && !responseReserve.getBody().getResult().isEmpty()) {
					if (logAppoint.getIdReservation() != null
							&& responseReserve.getBody().getResult().get(0).getCode().equals("I")) {
						createWithoutOrderResponse = this.getCreateWithoutOrderMerge(logAppoint).getBody();
					}
				}
			}
			resultList.add(new Result(ResultAppointmentEnum.WARNING.getCode(),
					ResultAppointmentEnum.WARNING.getDescription()));
			if (createWithoutOrderResponse == null || (createWithoutOrderResponse.getCreateWithoutOrder() == null
					&& createWithoutOrderResponse.getResult().isEmpty())) {
				createWithoutOrderResponse = new CreateWithoutOrderResponse();
				createWithoutOrderResponse
						.setResult(responseReserve != null && responseReserve.getBody().getResult() != null
								&& !responseReserve.getBody().getResult().isEmpty()
										? responseReserve.getBody().getResult()
										: resultList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getReservationWithoutOrderMerge; " + e.getMessage());
		}
		return new ResponseEntity<>(createWithoutOrderResponse, HttpStatus.OK);
	}

	/*
	 * Este método aplica una regla de forma temporal para cubrir una necesidad no cubierta por SAP
	 * 
	 * */
	private void applyRuleNotSupportBySAP(AppointmentInformationDTO appointmentInformation) {
		List<ChangeBenefitPatientType> changeBenefitChangeList = changeBenefitPatientTypeService.getChangeBenefits();

		if (changeBenefitChangeList!=null) {
			String patientType = appointmentInformation.getPatientDetail().getPatientType();
			String typePlanning = appointmentInformation.getReserveWithoutOrderRequest().getReserveWithoutOrder().getTypePlanning();

			if (patientType!="PARTICULAR" && changeBenefitChangeList.stream()
				.filter(obj -> typePlanning.equals(obj.getCode())).count()>0) {	
				appointmentInformation.getReserveWithoutOrderRequest().getReserveWithoutOrder().setEps("x");
				
			}
		}
	}

	public ResponseEntity<CreateWithoutOrderResponse> getCreateWithoutOrderMerge(LogAppointmentDTO logAppoint) {

		ResponseEntity<CreateWithoutOrderResponse> response = null;
		CreateWithoutOrderRequest createWithoutOrder = new CreateWithoutOrderRequest();

		Schedule schedule = new Schedule(null, logAppoint.getIdReservation(), logAppoint.getIdSpecialty(), "pending",
				logAppoint.getTypeDocument(), logAppoint.getNumberDocument(), null, dateUtils.getDateTimeTimeStamp(),
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
				getCancelWithoutOrder(logAppoint);
			}
		} catch (Exception e) {
			response = getCancelWithoutOrder(logAppoint);
			schedule.setState("pending");
			scheduleDAO.updateSchedule(schedule);
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getCreateWithoutOrder; " + e.getMessage());
		}
		return response;
	}

	private void buildPatientData(LogAppointmentDTO logAppoint, AppointmentInformationDTO appointmentInformation)
			throws NullPointerException {

		String desist = appointmentInformation.getCreateWithoutOrderRequest() != null
				&& appointmentInformation.getCreateWithoutOrderRequest().getDesistAppointment() != null
						? appointmentInformation.getCreateWithoutOrderRequest().getDesistAppointment()
						: "";

		String modality = appointmentInformation.getReserveWithoutOrderRequest() != null
				&& appointmentInformation.getReserveWithoutOrderRequest().getReserveWithoutOrder() != null
				&& appointmentInformation.getReserveWithoutOrderRequest().getReserveWithoutOrder()
						.getAppointment() != null
				&& appointmentInformation.getReserveWithoutOrderRequest().getReserveWithoutOrder().getAppointment()
						.getModality() != null
								? appointmentInformation.getReserveWithoutOrderRequest().getReserveWithoutOrder()
										.getAppointment().getModality()
								: "P";

		appointmentInformation.getPatientDetail().buildFullname();
		logAppoint.setTypeDocument(appointmentInformation.getPatientDetail().getTypeDocument());
		logAppoint.setNumberDocument(appointmentInformation.getPatientDetail().getNumberDocument());
		logAppoint.setName(documentUtils.replaceSpecialCharacter(appointmentInformation.getPatientDetail().getFullname()));
		logAppoint.setIdReservation(appointmentInformation.getCreateWithoutOrderRequest() == null ? null
				: appointmentInformation.getCreateWithoutOrderRequest().getIdAppointment());
		logAppoint.setIdOrder(null);
		logAppoint.setIdSpecialty(appointmentInformation.getSpecialtyDetail().getCode());
		logAppoint.setDescriptionSpecialty(documentUtils.replaceSpecialCharacter(appointmentInformation.getSpecialtyDetail().getDescription()));
		logAppoint.setDate(appointmentInformation.getReserveWithoutOrderRequest().getReserveWithoutOrder()
				.getAppointment().getDatetime());
		logAppoint.setIpClient(appointmentInformation.getClientDetail().getIpAddress());
		logAppoint.setDesistAppointment(desist);
		logAppoint.setModality(modality);
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
							new Schedule(dateUtils.getDateTimeTimeStamp(), null, logAppoint.getIdReservation(),
									logAppoint.getIdSpecialty(), "reservation", logAppoint.getTypeDocument(),
									logAppoint.getNumberDocument(), gson.toJson(reservationAppointment), null, null,
									null, dateUtils.getDateTimeTimeStamp(), logAppoint.getNumberDocument()));
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
						schedule.setState("pending");
						scheduleDAO.updateSchedule(schedule);
						getCancelWithoutOrder(logAppoint);
						createWithoutOrderResponse.setCreateWithoutOrder(null);
					} else if (result.getCode().equals("I")) {
						createWithoutOrder = new CreateWithoutOrder();
						String action = logAppoint.getDesistAppointment() != null
								&& logAppoint.getDesistAppointment().equals("X") ? cancelAplication : "create";
						logsDAO.createLog(action, logAppoint.toString());
						schedule.setState(action);
						scheduleDAO.updateSchedule(schedule);
						createWithoutOrder.getAppointment().setIdReserve(logAppoint.getIdReservation());
						createWithoutOrder.getAppointment().setValue(logAppoint.getValue());
						createWithoutOrderResponse.setCreateWithoutOrder(createWithoutOrder);
					}
				}
			} else {
				schedule.setState("pending");
				scheduleDAO.updateSchedule(schedule);
				getCancelWithoutOrder(logAppoint);

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
			AppointmentInformationDTO appointmentInformation) {

		ReservationAppointmentResponse reservationAppointmentResponse = new ReservationAppointmentResponse();
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
				reservationAppointmentResponse = responseReserve.getBody();
			}

			resultList.add(new Result(ResultAppointmentEnum.WARNING.getCode(),
					ResultAppointmentEnum.WARNING.getDescription()));
			reservationAppointmentResponse
					.setResult(responseReserve != null && responseReserve.getBody().getResult() != null
							&& !responseReserve.getBody().getResult().isEmpty() ? responseReserve.getBody().getResult()
									: resultList);
		} catch (Exception e) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getReservationWithoutOrder; " + e.getMessage());
		}
		return new ResponseEntity<>(reservationAppointmentResponse, HttpStatus.OK);
	}

	public ResponseEntity<CreateWithoutOrderResponse> getCreateWithoutOrder(
			AppointmentInformationDTO appointmentInformation) {

		ResponseEntity<CreateWithoutOrderResponse> response = null;
		CreateWithoutOrderRequest createWithoutOrder;
		LogAppointmentDTO logAppoint = new LogAppointmentDTO();
		Schedule schedule;

		try {
			this.buildPatientData(logAppoint, appointmentInformation);

			schedule = new Schedule(null, logAppoint.getIdReservation(), logAppoint.getIdSpecialty(), "pending",
					logAppoint.getTypeDocument(), logAppoint.getNumberDocument(), null,
					dateUtils.getDateTimeTimeStamp(), logAppoint.getNumberDocument());

			logsDAO.createLog("createInformation", logAppoint.toString());
			createWithoutOrder = appointmentInformation.getCreateWithoutOrderRequest();
			response = appointmentWithoutOrderService.getCreateWithoutOrder(createWithoutOrder);
			if (response != null && response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
				response = validateCreate(logAppoint, response.getBody(), schedule);
			} else {
				response = errorCreate(logAppoint, createWithoutOrder, schedule, gson.toJson(response));
				getCancelWithoutOrder(logAppoint);
			}
		} catch (Exception e) {
			response = getCancelWithoutOrder(logAppoint);
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getCreateWithoutOrder; " + e.getMessage());
		}
		return response;
	}

	public ResponseEntity<CreateWithoutOrderResponse> getCancelWithoutOrder(LogAppointmentDTO logAppoint) {

		ResponseEntity<CreateWithoutOrderResponse> response;
		CreateWithoutOrderRequest createWithoutOrder = new CreateWithoutOrderRequest();
		CreateWithoutOrderResponse createWithoutOrderResponse = new CreateWithoutOrderResponse();
		List<Result> resultList = new ArrayList<>();

		Schedule schedule = new Schedule(null, logAppoint.getIdReservation(), logAppoint.getIdSpecialty(), "pending",
				logAppoint.getTypeDocument(), logAppoint.getNumberDocument(), null, dateUtils.getDateTimeTimeStamp(),
				logAppoint.getNumberDocument());

		try {

			if (logAppoint.getIdReservation() != null && !logAppoint.getIdReservation().isEmpty()) {
				createWithoutOrder.setIdAppointment(logAppoint.getIdReservation());
				createWithoutOrder.setDesistAppointment("X");
				response = appointmentWithoutOrderService.getCreateWithoutOrder(createWithoutOrder);
				createWithoutOrderResponse = response.getBody();
				if (response.getStatusCode().equals(HttpStatus.OK) && createWithoutOrderResponse.getResult() != null
						&& !createWithoutOrderResponse.getResult().isEmpty()) {
					for (Result result : createWithoutOrderResponse.getResult()) {
						if (result.getCode().equals("I")) {
							schedule.setState(cancelAplication);
							logsDAO.createLog(cancelAplication, logAppoint.toString());
						} else {
							schedule.setState("errorApplication");
							logsDAO.createLog("errorApplication", logAppoint.toString());
						}
					}
				} else {
					schedule.setState("errorApplication");
					logsDAO.createLog("errorApplication", logAppoint.toString());
				}

				resultList.add(new Result(ResultAppointmentEnum.ERROR.getCode(),
						ResultAppointmentEnum.ERROR.getDescription()));
			}
			createWithoutOrderResponse.setCreateWithoutOrder(null);
			createWithoutOrderResponse.setResult(resultList);
		} catch (Exception e) {
			logsManager.logsBuildAppInsights(exception,
					"AppointmentWithoutOrderBusiness; getCancelWithoutOrder; " + e.getMessage());
		} finally {
			scheduleDAO.updateSchedule(schedule);
		}
		return new ResponseEntity<>(createWithoutOrderResponse, HttpStatus.OK);

	}

}

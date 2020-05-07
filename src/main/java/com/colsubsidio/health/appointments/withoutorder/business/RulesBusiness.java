package com.colsubsidio.health.appointments.withoutorder.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.dao.BusinessrulesDAO;
import com.colsubsidio.health.appointments.withoutorder.enums.ResultEnum;
import com.colsubsidio.health.appointments.withoutorder.model.BusinessRules;
import com.colsubsidio.health.appointments.withoutorder.model.BusinessRulesResponse;
import com.colsubsidio.health.appointments.withoutorder.model.Result;
import com.colsubsidio.health.appointments.withoutorder.services.AppointmentWithoutOrderService;
import com.colsubsidio.health.appointments.withoutorder.util.DateUtils;
import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;

@Component
public class RulesBusiness {

	@Autowired
	DateUtils dateUtils;
	@Autowired
	LogsManager logsManager;
	@Autowired
	AppointmentWithoutOrderService availabilityService;
	@Autowired
	BusinessrulesDAO businessrulesDAO;

	private static String exception = "exception";

	public ResponseEntity<BusinessRulesResponse> getRulesCategory(String category) {

		BusinessRulesResponse businessRulesResponse = new BusinessRulesResponse();
		List<BusinessRules> listRules;
		List<Result> listResult = new ArrayList<>();
		businessRulesResponse.setResults(listResult);

		try {
			listRules = businessrulesDAO.getRules(category);

			businessRulesResponse.getResults()
					.add(listRules != null
							? new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDescription())
							: new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getDescription()));
			businessRulesResponse.setBusinessRules(listRules);
		} catch (Exception e) {
			businessRulesResponse.getResults()
					.add(new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getDescription()));
			logsManager.logsBuildAppInsights(exception, "RulesBusiness; getRulesCategory; " + e.getMessage());
		}
		return new ResponseEntity<BusinessRulesResponse>(businessRulesResponse, HttpStatus.OK);
	}
}

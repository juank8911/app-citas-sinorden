package com.colsubsidio.health.appointments.withoutorder.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.colsubsidio.health.appointments.withoutorder.business.RulesBusiness;
import com.colsubsidio.health.appointments.withoutorder.model.BusinessRulesResponse;

/**
 *
 * @author LordMVP
 */
@RestController
@RequestMapping("/salud/reglas/")
public class BussinesRulesController {

	@Autowired
	private RulesBusiness rulesBusiness;

	/**
	 *
	 * @param espId
	 * @return
	 */
	@GetMapping("categoria")
	public ResponseEntity<BusinessRulesResponse> getRulesCategory(@RequestParam("categoria") String category) {
		return rulesBusiness.getRulesCategory(category);
	}

}

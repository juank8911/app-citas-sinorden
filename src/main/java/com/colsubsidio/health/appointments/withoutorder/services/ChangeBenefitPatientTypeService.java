package com.colsubsidio.health.appointments.withoutorder.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colsubsidio.health.appointments.withoutorder.conf.ChangeBenefitPatientTypeConfig;
import com.colsubsidio.health.appointments.withoutorder.model.ChangeBenefitPatientType;

@Service
public class ChangeBenefitPatientTypeService {

	private final List<ChangeBenefitPatientType> benefitChange;

	@Autowired
	public ChangeBenefitPatientTypeService (ChangeBenefitPatientTypeConfig changeBenefit) {
		this.benefitChange = changeBenefit.getChangeBenefits();
	}

	public List<ChangeBenefitPatientType> getChangeBenefits() {
		return this.benefitChange;
	}

	public ChangeBenefitPatientType getChangeBenefit(String name) {
		return benefitChange.stream().filter(benefit -> name.equals(benefit.getCode())).findFirst().orElse(null);
	}
	
}

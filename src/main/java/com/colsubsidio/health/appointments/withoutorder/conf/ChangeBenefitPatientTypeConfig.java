package com.colsubsidio.health.appointments.withoutorder.conf;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.colsubsidio.health.appointments.withoutorder.model.ChangeBenefitPatientType;

@Configuration
@ConfigurationProperties(prefix = "changeBenefitPatientType.benefit")
public class ChangeBenefitPatientTypeConfig {

	private List<ChangeBenefitPatientType> changeBenefit;

	public List<ChangeBenefitPatientType> getBenefits() {
		return changeBenefit;
	}

	public void setBenefits(List<ChangeBenefitPatientType> benefits) {
		this.changeBenefit = benefits;
	}

	
}
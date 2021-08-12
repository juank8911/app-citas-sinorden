package com.colsubsidio.health.appointments.withoutorder.conf;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.colsubsidio.health.appointments.withoutorder.model.ChangeBenefitPatientType;

@Configuration
@ConfigurationProperties(prefix = "changebenefitpatienttype.benefits")
public class ChangeBenefitPatientTypeConfig {

	private List<ChangeBenefitPatientType> changebenefits;

	public List<ChangeBenefitPatientType> getChangeBenefits() {
		return changebenefits;
	}

	public void setChangeBenefits(List<ChangeBenefitPatientType> changeBenefits) {
		this.changebenefits = changeBenefits;
	}

	
}
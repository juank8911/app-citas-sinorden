package com.colsubsidio.health.appointments.withoutorder.util;

import org.springframework.stereotype.Component;

@Component
public class DocumentUtils {

	public String formatDocumentTypePayment(String typeDocument) {
		String typeDocumentPago = "";
		switch (typeDocument) {
		case "CC":
			typeDocumentPago = "02";
			break;
		case "CE":
			typeDocumentPago = "03";
			break;
		case "TI":
			typeDocumentPago = "04";
			break;
		case "RC":
			typeDocumentPago = "05";
			break;
		case "PEP":
			typeDocumentPago = "08";
			break;
		default:
			typeDocumentPago = "02";
			break;
		}
		return typeDocumentPago;
	}
}

package com.colsubsidio.health.appointments.withoutorder.util;

import java.util.Arrays;

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
	
	public String replaceSpecialCharacter(String palabra) {
        String[] caracteresMalos = {"ñ","|","à","á","À","Á","è","é","È","É","ì","í","Ì","Í","ò","ó","Ò","Ó","ù","ú","Ù","Ú","\b","/",":","<","*","?",">"};
        String[] caracteresBuenos = {"n","_","a","a","A","A","e","e","E","E","i","i","I","I","o","o","O","O","u","u","U","U","_","_","_","_","","_","_"};

		for (String letraMala : caracteresMalos) {
			if (palabra.contains(letraMala)) {
				palabra = palabra.replace(letraMala,
						caracteresBuenos[Arrays.asList(caracteresMalos).indexOf(letraMala)]);
			}
		}

		return palabra;
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.services.interfaces;

import org.springframework.http.ResponseEntity;

import com.colsubsidio.salud.transversal.models.BearerToken;

/**
 *
 * @author Usuario
 */
public interface IAuthService {

	ResponseEntity<?> BearerToken();
	ResponseEntity<?> BearerTokenTest();
}

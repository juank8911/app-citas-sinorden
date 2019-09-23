package com.colsubsidio.salud.portal.services.interfaces;

import org.springframework.http.ResponseEntity;

/**
 *
 * @author Usuario
 */
public interface ILogsService {

    ResponseEntity<String> insertLogs(String logAction, String logMessage);
}

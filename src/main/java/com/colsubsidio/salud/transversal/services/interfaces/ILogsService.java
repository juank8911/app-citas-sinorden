package com.colsubsidio.salud.transversal.services.interfaces;

import org.springframework.http.ResponseEntity;

import com.colsubsidio.salud.transversal.models.AuthApplicationLogs;
import com.colsubsidio.salud.transversal.models.LogsLogger;

/**
 *
 * @author Usuario
 */
public interface ILogsService {

    void insertLogsService(LogsLogger logs);
    ResponseEntity<String> insertLogs(String logAction, String logMessage);
}

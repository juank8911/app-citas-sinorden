/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.portal.services;

import com.colsubsidio.salud.portal.dao.interfaces.ILogsDAO;
import com.colsubsidio.salud.portal.services.interfaces.ILogsService;
import com.colsubsidio.salud.portal.utils.HandleDate;
import com.colsubsidio.salud.portal.utils.ProcessChain;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.colsubsidio.salud.portal.dao.interfaces.IServicesDAO;
import com.colsubsidio.salud.portal.utils.LogsManager;
import java.io.IOException;

@Component
public class LogsService implements ILogsService {

    @Autowired
    private ProcessChain o;

    @Autowired
    private HandleDate f;

    @Autowired
    private IServicesDAO serviciosDAO;

    @Autowired
    private ILogsDAO LogsDAO;

    @Autowired
    private LogsManager logsManager;

    @Override
    @Async("asyncExecutor")
    public ResponseEntity<String> insertLogs(String logAction, String logMessage) {
        try {
            logsManager.LogsBuildAppInsights(logAction, logMessage);
            return new ResponseEntity(LogsDAO.createLog(logAction, logMessage), HttpStatus.OK);
        } catch (Exception ex) {
            try {
                logsManager.LogsBuildAppInsights("exception", "LogsService; insertLogs; " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(LogsService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }
}

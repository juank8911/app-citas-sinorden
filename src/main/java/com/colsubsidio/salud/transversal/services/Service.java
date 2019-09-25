/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.services;

import com.colsubsidio.salud.transversal.dao.interfaces.ILogsDAO;
import com.colsubsidio.salud.transversal.dao.interfaces.IServicesDAO;
import com.colsubsidio.salud.transversal.services.interfaces.IService;
import com.colsubsidio.salud.transversal.utils.HandleDate;
import com.colsubsidio.salud.transversal.utils.ProcessChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Usuario
 */
@Component
public class Service implements IService {

    @Autowired
    private ProcessChain o;

    @Autowired
    private HandleDate f;

    @Autowired
    private IServicesDAO serviciosDAO;

    @Autowired
    private ILogsDAO LogsDAO;

}

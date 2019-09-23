/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.portal.services.interfaces;

import com.colsubsidio.salud.portal.models.deletewithoutorder.Delete;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Usuario
 */
public interface IDeleteWithoutOrderService {

    ResponseEntity<?> deleteWithoutOrder(Delete borrarCitaSinOrdenJSON);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.auth.controllers.util;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Bits
 */
public class ExceptionControl implements Serializable {
    
    public HashMap<String, Object> ExceptionHandling(Number codigo, String descripcion) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("codigo", codigo);
        map.put("descripcion", descripcion);   
        return map;
    }
    
}

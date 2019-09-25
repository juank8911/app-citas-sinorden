/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.models;

import java.io.Serializable;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Component
public class Errores implements Serializable {

    private String function;
    private String action;
    private String line;
    private String message;
    private String parameter;
    private String body;
    private String date;
    private String hour;
    private String exception;

    public String toJson() {
        return "{" + "\"function\":\"" + function + "\", \"date\":\"" + date + "\", \"hour\":\"" + hour + "\", \"line\":\"" + line + "\", \"action\":\"" + action + "\", \"message\":\"" + message + "\", \"exception\":\"" + exception + "\", \"parameter\":" + parameter + ", \"body\":" + body + "}";
    }
}

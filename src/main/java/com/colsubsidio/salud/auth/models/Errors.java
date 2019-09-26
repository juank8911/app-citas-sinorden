/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.auth.models;

import java.io.Serializable;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class Errors implements Serializable {

    private String function;
    private String action;
    private String line;
    private String message;
    private String parameter;
    private String body;
    private String date;
    private String hour;
    private String exception;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String toJson() {
        return "{" + "\"function\":\"" + function + "\", \"date\":\"" + date + "\", \"hour\":\"" + hour + "\", \"line\":\"" + line + "\", \"action\":\"" + action + "\", \"message\":\"" + message + "\", \"exception\":\"" + exception + "\", \"parameter\":" + parameter + ", \"body\":" + body + "}";
    }
}

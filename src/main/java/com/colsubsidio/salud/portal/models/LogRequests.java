/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.portal.models;

import java.io.Serializable;
import java.util.Date;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Component
public class LogRequests implements Serializable {

    private Integer id;
    private Date dateException;
    private String startDate;
    private String serverName;
    private String action;
    private String httpStatus;
    private String body;
    private String bodyResponse;
    private String header;
    private String method;
    private String parameter;
    private String date;
    private String hour;
    private String function;

//    @Override
//    public String toString() {
//        return "LogRequests{" + "id=" + id + ", dateException=" + dateException + ", serverName=" + serverName + ", action=" + action + ", httpStatus=" + httpStatus + ", body=" + body + ", header=" + header + ", method=" + method + ", parameter=" + parameter + ", date=" + date + ", hour=" + hour + '}';
//    }
    public String toJson() {
        String json = "{" + "\"function\":\"" + function + "\", \"date\":\"" + date + "\", \"hour\":\"" + hour + "\", \"serverName\":\"" + serverName + "\", \"action\":\"" + action + "\", \"method\":\"" + method + "\", \"httpStatus\":\"" + httpStatus + "\", \"header\":" + header + ", \"parameter\":" + parameter + ", \"body\":" + body + ", \"bodyResponse\":" + bodyResponse + "}";
        return json.replace("\"\"", "\"");
    }
}

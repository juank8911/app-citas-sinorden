package com.colsubsidio.health.appointments.withoutorder.model;

import lombok.Data;

import java.util.Date;

@Data
public class LogElasticSearch {
    String app;
    String version;
    String index;
    String type;
    String eventDate;
    int typeStatusCode;
    String message;
    String spanIdString;
    String localRootIdString;
    String parentIdString;
    String traceIdString;


}



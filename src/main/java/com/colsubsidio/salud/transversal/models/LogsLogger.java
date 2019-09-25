package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LogsLogger {

    private String index;
    private String type;
    private String startDate;
    private String endDate;
    private String statusCode;
    private String ReqRes;
}

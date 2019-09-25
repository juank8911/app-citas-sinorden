package com.colsubsidio.salud.transversal.models;

import java.util.LinkedHashMap;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Services {

    private int serv_id;
    private String serv_name;
    private LinkedHashMap<String, String> serv_headers;
    private String serv_url;
    private String serv_apikey;
    private String serv_authorization;
    private String serv_parameters;
    private String serv_method;
    private String client_secret;
    private String client_id;

}

package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Ips {

    private String ips_id;
    private String ips_name;
    private String ips_nameAux;
    private String ips_code;
    private String ips_direction;
    private String ips_authorization;
    private String ips_latitud;
    private String ips_longitud;
    private String citi_id;
    private String citi_name;

    public Ips(String ips_id, String ips_name, String ips_code, String ips_direction, String ips_authorization, String citi_id, String citi_name) {
        this.ips_id = ips_id;
        this.ips_name = ips_name;
        this.ips_code = ips_code;
        this.ips_direction = ips_direction;
        this.ips_authorization = ips_authorization;
        this.citi_id = citi_id;
        this.citi_name = citi_name;
    }

}

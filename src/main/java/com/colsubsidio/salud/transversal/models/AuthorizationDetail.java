/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.models;

import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthorizationDetail {

    private String id;
    private String id_authorization;
    private Timestamp order_date;
    private String diagnostic_id;
    private String doctor_document;
    private String doctor_name;
    private String doctor_specialty;
    private String cups_id;
    private String laterality;
    private String quantity;
    private String order_observations;
    private String order_place;
    private String classification;
    private String ips_addressing;
    private String return_observations;
    private String internal_observation;
    private Timestamp modified;
    private String modified_by;
    private Timestamp create;
    private String created_by;

}

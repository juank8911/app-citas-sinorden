package com.colsubsidio.salud.transversal.models;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthHabeasdata {

    private String id;
    private String tipo_doc;
    private String num_doc;
    private Timestamp auth_date;
    private String auth_ckeck;
    private String auth_ip;
    private String create_by;
    private String modified_by;
    private Timestamp created;
    private Timestamp modified;
    private String tipo_doc_approval;
    private String num_doc_approval;
    private String module;

}

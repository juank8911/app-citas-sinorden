package com.colsubsidio.salud.transversal.models;

import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LaboratoryDepartment {

    private String id;
    private String id_department;
    private String name;
    private String text;
    private String type;
    private String state;
    private String created_by;
    private String modified_by;
    private Timestamp created;
    private Timestamp modified;
    private boolean redirection;
    private String url;
    private String order;

}

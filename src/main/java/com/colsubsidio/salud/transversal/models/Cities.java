package com.colsubsidio.salud.transversal.models;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Cities {

    private String id;
    private String id_departament;
    private String code;
    private String name;
    private Timestamp created;
    private String created_by;
    private Timestamp modified;
    private String modified_by;

}

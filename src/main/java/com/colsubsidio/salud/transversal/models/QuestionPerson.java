package com.colsubsidio.salud.transversal.models;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class QuestionPerson {

    private String id;
    private String tipo_doc;
    private String num_doc;
    private String nombre;
    private Timestamp dateresponse;

}

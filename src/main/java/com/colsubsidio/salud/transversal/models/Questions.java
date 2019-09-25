package com.colsubsidio.salud.transversal.models;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Questions {

    private String id;
    private String question;
    private int count;
    private String observation;
    private String state;
    private Timestamp created;
    private Timestamp modified;
    private String created_by;
    private String modified_by;
    private List<Answers> respuestas;
}

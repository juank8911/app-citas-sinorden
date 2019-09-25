package com.colsubsidio.salud.transversal.models;

import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Answers {

    private String id;
    private String answer;
    private String state;
    private boolean correct;
    private boolean selected;
    private Timestamp created;
    private String created_by;
    private Timestamp modified;
    private String modified_by;
    private String idquestion;
}

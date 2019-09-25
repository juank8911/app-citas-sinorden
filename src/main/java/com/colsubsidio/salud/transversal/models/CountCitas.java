package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CountCitas {

    private String tipoDoc;
    private String numDoc;
    private String espId;
    private int totalCount;

}

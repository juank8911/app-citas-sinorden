package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CountMultas {

    private String tipoDoc;
    private String numDoc;
    private String espId;
    private int evaluacionesCount;
    private int especialidadCount;
    private int multaCount;
    private int inasistenteCount;
    private int exentoCount;
    private int facturadoCount;
    private int agendadoCount;
    private int totalCount;

}

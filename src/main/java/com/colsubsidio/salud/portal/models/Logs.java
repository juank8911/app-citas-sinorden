package com.colsubsidio.salud.portal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Logs {

    private int logs_id;
    private String logs_accion;
    private String logs_fechacambio;

}

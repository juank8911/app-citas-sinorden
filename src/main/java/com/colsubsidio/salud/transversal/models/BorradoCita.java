package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BorradoCita {

    private String codigo;
    private String descripcion;

    private String resultCodigo;
    private String resultDescripcion;

    public BorradoCita(String codigo, String descripcion, String resultCodigo, String resultDescripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.resultCodigo = resultCodigo;
        this.resultDescripcion = resultDescripcion;
    }

}

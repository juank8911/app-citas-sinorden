package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReservarCita {

    private String idReserva;
    private String valor;

    private String resultCodigo;
    private String resultDescripcion;

    public ReservarCita(String idReserva, String valor, String resultCodigo, String resultDescripcion) {
        this.idReserva = idReserva;
        this.valor = valor;
        this.resultCodigo = resultCodigo;
        this.resultDescripcion = resultDescripcion;
    }

}

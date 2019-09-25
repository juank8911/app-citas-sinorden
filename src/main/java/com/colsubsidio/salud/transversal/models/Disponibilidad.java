package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Disponibilidad {

    private String fecha;

    /**
     *
     * @param fecha
     */
    public Disponibilidad(String fecha) {
        this.fecha = fecha;
    }
}

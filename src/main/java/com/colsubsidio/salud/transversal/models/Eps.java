package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Eps {

    private String id;
    private String nombre;

    public Eps(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}

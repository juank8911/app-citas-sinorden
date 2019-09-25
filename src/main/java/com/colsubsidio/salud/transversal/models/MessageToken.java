package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MessageToken {

    private String destino;
    private String contenido;
    private String tipo;
    private String fechaEnvio;

    public MessageToken(String destino, String contenido, String tipo, String fechaEnvio) {
        this.destino = destino;
        this.contenido = contenido;
        this.tipo = tipo;
        this.fechaEnvio = fechaEnvio;
    }
}

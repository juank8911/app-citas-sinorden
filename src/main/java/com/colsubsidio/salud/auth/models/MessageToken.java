package com.colsubsidio.salud.auth.models;

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

    public MessageToken() {
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    @Override
    public String toString() {
        return "messageToken{" + "destino=" + destino + ", contenido=" + contenido + ", tipo=" + tipo + ", fechaEnvio=" + fechaEnvio + '}';
    }
    
}

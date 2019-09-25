package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CitaHorario {

    private String prestadorCodigo;
    private String prestadorNombre;
    private String especialidadCodigo;
    private String especialidadDescripcion;
    private String tipoPlanificacion;
    private String unidadTratamiento;
    private String fechaHora;
    private String fecha;
    private String hora;
    private String ipsCodigo;
    private String ipsNombre;
    private String consultorioCodigo;
    private String consultorioNombre;

    public CitaHorario(String prestadorCodigo, String prestadorNombre, String especialidadCodigo, String especialidadDescripcion, String tipoPlanificacion, String unidadTratamiento, String fechaHora, String ipsCodigo, String ipsNombre, String consultorioCodigo, String consultorioNombre) {
        this.prestadorCodigo = prestadorCodigo;
        this.prestadorNombre = prestadorNombre;
        this.especialidadCodigo = especialidadCodigo;
        this.especialidadDescripcion = especialidadDescripcion;
        this.tipoPlanificacion = tipoPlanificacion;
        this.unidadTratamiento = unidadTratamiento;
        this.fechaHora = fechaHora;
        this.ipsCodigo = ipsCodigo;
        this.ipsNombre = ipsNombre;
        this.consultorioCodigo = consultorioCodigo;
        this.consultorioNombre = consultorioNombre;
    }
}

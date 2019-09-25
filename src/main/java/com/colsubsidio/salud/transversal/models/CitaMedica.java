package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CitaMedica {

    private String fechaHora;
    private String hora;
    private String codigoCentroMedico;
    private String nombreCentroMedico;
    private String codigoConvenio;
    private String nombreConvenio;
    private String codigoMedico;
    private String nombreMedico;
    private String codigoPrestacion;
    private String nombrePrestacion;
    private String codigoEspecialidad;
    private String descripcionEspecialidad;
    private String codigoEstado;
    private String descripcionEstado;
    private String consultorio;
    private String idReserva;
    private String valor;
    private String numeroOrden;
    private String documentoContable;

}

package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Multas {

    private String citaEstado;
    private String citaFecha;
    private String citaValor;
    private String citaNumeroOrden;
    private String citaDocumentoContable;
    private String citaReservaId;
    private String centroMedicoCodigo;
    private String centroMedicoNombre;
    private String centroMedicoConsultorio;
    private String prestacionCodigo;
    private String prestacionDescripcion;
    private String especialidadCodigo;
    private String especialidadDescripcion;
    private String prestadorCodigo;
    private String prestadorNombre;
    private String citaDescripcion;
    private String convenioCodigo;
    private String convenioNombre;
}

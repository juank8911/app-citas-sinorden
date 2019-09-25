package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrdenesClinicas {

    private String unidadOrgId;
    private String fechaGenOrd;
    private String numeroOrden;
    private String prestacionId;
    private String descripcionPrestacion;
    private String cantidadPrestacion;
    private String estado;
    private String codigoEspecialidad;
    private String descripcionEspecialidad;
    private String medicoId;
    private String pacienteId;
    private String autorizacionId;
}

package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReservarCitaConOrden {

    private String fechaHora;
    private String especialidadId;
    private String prestadorId;
    private String pacienteId;
    private String epsParticular;
    private String tipoPlanificacionId;
    private String ordenId;
    private String unidadPlanificada;
    private String numeroPrestacion;

}

package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GrupoFamiliar {

    private String tipo;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombre;
    private String parentesco;
    private String fechaNacimiento;
    private String sexo;
    private String estado;
    private String tipoIdentificacionCotizante;
    private String numeroIdentificacionCotizante;

}

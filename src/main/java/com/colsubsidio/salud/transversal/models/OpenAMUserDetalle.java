package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OpenAMUserDetalle {

    private String id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String generoCodigo;
    private String generoDescripcion;
    private String fechaNacimiento;

    public OpenAMUserDetalle(String id, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String generoCodigo, String generoDescripcion, String fechaNacimiento) {
        this.id = id;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.generoCodigo = generoCodigo;
        this.generoDescripcion = generoDescripcion;
        this.fechaNacimiento = fechaNacimiento;
    }
}

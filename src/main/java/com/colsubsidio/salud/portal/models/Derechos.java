package com.colsubsidio.salud.portal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Derechos {

    private String fechaComprobacion;
    private String documentoTipo;
    private String documentoNumero;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String categoriaCodigo;
    private String tipoCodigo;
    private String estadoCodigo;
    private String semanasCotizadas;
    private String convenioCodigo;
    private String epsTipoDoc;
    private String epsNumDoc;
    private String epsNombre;
    private String epsConvEstado;
    private String epsConvNombre;
    private String epsConvClase;

    public Derechos(String fechaComprobacion, String documentoTipo, String documentoNumero, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String categoriaCodigo, String tipoCodigo, String estadoCodigo, String semanasCotizadas, String convenioCodigo, String epsTipoDoc, String epsNumDoc, String epsNombre, String epsConvEstado, String epsConvNombre, String epsConvClase) {
        this.fechaComprobacion = fechaComprobacion;
        this.documentoTipo = documentoTipo;
        this.documentoNumero = documentoNumero;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.categoriaCodigo = categoriaCodigo;
        this.tipoCodigo = tipoCodigo;
        this.estadoCodigo = estadoCodigo;
        this.semanasCotizadas = semanasCotizadas;
        this.convenioCodigo = convenioCodigo;
        this.epsTipoDoc = epsTipoDoc;
        this.epsNumDoc = epsNumDoc;
        this.epsNombre = epsNombre;
        this.epsConvEstado = epsConvEstado;
        this.epsConvNombre = epsConvNombre;
        this.epsConvClase = epsConvClase;
    }

}

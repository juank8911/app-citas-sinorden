package com.colsubsidio.salud.transversal.models;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OpenAMUser {

    private int id;
    private String numdoc;
    private String tipodoc;
    private String nombre;
    private String apellidos;
    private String correo;
    private String fechanacimiento;
    private String idSession;
    private String tokenSalud;
    private String token;
    private String refreshToken;
    private String hash;
    private Timestamp date;

    private String estado;
    private String minutos;
    private String mensaje;

    private String username;
    private String password;

}

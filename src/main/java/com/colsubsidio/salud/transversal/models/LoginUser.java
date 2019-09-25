/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginUser {

    private String documentNumber;

    private String documentType;

    private String tokenId;

    private String telephoneNumber;

    private String mail;

    private String timeSession;

    private String username;

    private String firstName;

    private String lastName;
}

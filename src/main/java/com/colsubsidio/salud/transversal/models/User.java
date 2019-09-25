package com.colsubsidio.salud.transversal.models;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String name;
    private Long phone;
    private Integer role;
    private Integer alliance;
    private Integer changePassword;
    private String resetToken;
    private Integer enabled;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer usrIdCreate;
    private Integer usrIdUpdate;

    public User(Integer id, String username, String password, String email, String name, Long phone, Integer role, Integer alliance, Integer changePassword, String resetToken, Integer enabled, Timestamp createdAt, Timestamp updatedAt, Integer usrIdCreate, Integer usrIdUpdate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.alliance = alliance;
        this.changePassword = changePassword;
        this.resetToken = resetToken;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.usrIdCreate = usrIdCreate;
        this.usrIdUpdate = usrIdUpdate;
    }
}

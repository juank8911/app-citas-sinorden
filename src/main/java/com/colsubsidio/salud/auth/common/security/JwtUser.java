package com.colsubsidio.salud.auth.common.security;

import com.colsubsidio.salud.auth.models.Role;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import java.util.List;

public class JwtUser implements UserDetails {

    private static final long serialVersionUID = 1215456216545L;

    private Integer id;
    private String username;
    private String auth_key;
    private String password_hash;
    private String password_reset_token;
    private String email;
    private String status;
    private String name;
    private String phone;
    private Integer change_password;
    private String reset_token;
    private Integer enabled;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Timestamp lastpasswordresetdate;
    private Integer usr_id_create;
    private Integer usr_id_update;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(
            Integer id,
            String username,
            String auth_key,
            String password_hash,
            String password_reset_token,
            String email,
            String status,
            String phone,
            String name,
            Integer change_password,
            String reset_token,
            Integer enabled,
            Timestamp created_at,
            Timestamp updated_at,
            Timestamp lastpasswordresetdate,
            Integer usr_id_create,
            Integer usr_id_update,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.username = username;
        this.auth_key = auth_key;
        this.password_hash = password_hash;
        this.password_reset_token = password_reset_token;
        this.email = email;
        this.status = status;
        this.name = name;
        this.phone = phone;
        this.change_password = change_password;
        this.reset_token = reset_token;
        this.enabled = enabled;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.lastpasswordresetdate = lastpasswordresetdate;
        this.usr_id_create = usr_id_create;
        this.usr_id_update = usr_id_update;
        this.authorities = authorities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getPassword_reset_token() {
        return password_reset_token;
    }

    public void setPassword_reset_token(String password_reset_token) {
        this.password_reset_token = password_reset_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getChange_password() {
        return change_password;
    }

    public void setChange_password(Integer change_password) {
        this.change_password = change_password;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getUsr_id_create() {
        return usr_id_create;
    }

    public void setUsr_id_create(Integer usr_id_create) {
        this.usr_id_create = usr_id_create;
    }

    public Integer getUsr_id_update() {
        return usr_id_update;
    }

    public void setUsr_id_update(Integer usr_id_update) {
        this.usr_id_update = usr_id_update;
    }

    public Timestamp getLastpasswordresetdate() {
        return lastpasswordresetdate;
    }

    public void setLastpasswordresetdate(Timestamp lastpasswordresetdate) {
        this.lastpasswordresetdate = lastpasswordresetdate;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEnabled() {
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password_hash;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

}

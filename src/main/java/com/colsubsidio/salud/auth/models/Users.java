package com.colsubsidio.salud.auth.models;

import java.sql.Timestamp;

public class Users {
    private Integer id;
    private String username;
    private String password_hash;
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

    public Users(Integer id, String username, String password_hash, String email, String name, Long phone, Integer role, Integer alliance, Integer changePassword, String resetToken, Integer enabled, Timestamp createdAt, Timestamp updatedAt, Integer usrIdCreate, Integer usrIdUpdate) {
        this.id = id;
        this.username = username;
        this.password_hash = password_hash;
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

    public Users() {
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

    public String getPassword() {
        return password_hash;
    }

    public void setPassword(String password) {
        this.password_hash = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getAlliance() {
        return alliance;
    }

    public void setAlliance(Integer alliance) {
        this.alliance = alliance;
    }

    public Integer getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Integer changePassword) {
        this.changePassword = changePassword;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUsrIdCreate() {
        return usrIdCreate;
    }

    public void setUsrIdCreate(Integer usrIdCreate) {
        this.usrIdCreate = usrIdCreate;
    }

    public Integer getUsrIdUpdate() {
        return usrIdUpdate;
    }

    public void setUsrIdUpdate(Integer usrIdUpdate) {
        this.usrIdUpdate = usrIdUpdate;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password_hash + ", email=" + email + ", name=" + name + ", phone=" + phone + ", role=" + role + ", alliance=" + alliance + ", changePassword=" + changePassword + ", resetToken=" + resetToken + ", enabled=" + enabled + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", usrIdCreate=" + usrIdCreate + ", usrIdUpdate=" + usrIdUpdate + '}';
    }
    
}

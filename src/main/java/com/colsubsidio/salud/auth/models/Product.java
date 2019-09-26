package com.colsubsidio.salud.auth.models;

import java.sql.Timestamp;

public class Product {
    private Integer id;
    private String name;
    private Integer typId;
    private Integer state; 
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer usrIdCreate;
    private Integer usrIdUpdate;

    public Product(Integer id, String name, Integer typId, Integer state, Timestamp createdAt, Timestamp updatedAt, Integer usrIdCreate, Integer usrIdUpdate) {
        this.id = id;
        this.name = name;
        this.typId = typId;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.usrIdCreate = usrIdCreate;
        this.usrIdUpdate = usrIdUpdate;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypId() {
        return typId;
    }

    public void setTypId(Integer typId) {
        this.typId = typId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
        return "Product{" + "id=" + id + ", name=" + name + ", typId=" + typId + ", state=" + state + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", usrIdCreate=" + usrIdCreate + ", usrIdUpdate=" + usrIdUpdate + '}';
    }

}

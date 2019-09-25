package com.colsubsidio.salud.transversal.models;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
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
}

package com.colsubsidio.salud.transversal.models;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Authorization {

    private String id;
    private String document_type;
    private String document_number;
    private String phone_number;
    private String email;
    private String code;
    private String authorization_number;
    private String type;
    private String motive;
    private String state;
    private String data;
    private String description;
    private Timestamp application_date;
    private Timestamp approval_date;
    private String attend;
    private Timestamp modified;
    private String modified_by;
    private Timestamp created;
    private String created_by;
    private String id_attend;
    private String management;
    private String id_renew;
    private int count;
    private AuthorizationDetail authorizationDetail;
    private List<FileAuthorization> files;
    private String authorization;
    private String typeName;
    private String solicitude;

    public String toString() {
        return "Authorization{id=" + this.id + ", document_type=" + this.document_type + ", document_number=" + this.document_number + ", code=" + this.code + ", authorization=" + this.authorization + ", type=" + this.type + ", motive=" + this.motive + ", solicitude=" + this.solicitude + ", data=" + this.data + ", description=" + this.description + ", application_date=" + this.application_date + ", approval_date=" + this.approval_date + ", attend=" + this.attend + ", modified=" + this.modified + ", modified_by=" + this.modified_by + ", created=" + this.created + ", created_by=" + this.created_by + '}';
    }
}

package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FileAuthorization {

    private String code;
    private String name;
    private String url;
    private Boolean load;

    public FileAuthorization(String code) {
        this.code = code;
    }

}

package com.colsubsidio.salud.transversal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Result {

    private String code;
    private String description;

    public Result(String code, String description) {
        this.code = code;
        this.description = description;
    }

}

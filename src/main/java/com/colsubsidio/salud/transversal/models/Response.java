package com.colsubsidio.salud.transversal.models;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Response {

    private Result result;
    private Object data;

}

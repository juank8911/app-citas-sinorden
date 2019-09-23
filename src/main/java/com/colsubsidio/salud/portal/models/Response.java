package com.colsubsidio.salud.portal.models;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Response {

    private List<Result> result;
    private Object data;

}

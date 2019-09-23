package com.colsubsidio.salud.portal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BearerToken {

    private String organization_name;
    private String developer;
    private String token_type;
    private String issued_at;
    private String client_id;
    private String access_token;
    private String application_name;
    private String scope;
    private String expires_in;
    private String refresh_count;
    private String status;
}

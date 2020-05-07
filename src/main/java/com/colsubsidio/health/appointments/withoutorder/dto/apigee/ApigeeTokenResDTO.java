package com.colsubsidio.health.appointments.withoutorder.dto.apigee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class ApigeeTokenResDTO {
    
    private String refresh_token_expires_in;
    private String api_product_list;
    private List<String> api_product_list_json;
    private String organization_name;
    @JsonProperty("developer.email")
    private String developer_email;
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


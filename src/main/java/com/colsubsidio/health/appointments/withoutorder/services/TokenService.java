package com.colsubsidio.health.appointments.withoutorder.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.colsubsidio.health.appointments.withoutorder.dto.apigee.ApigeeTokenReqDTO;
import com.colsubsidio.health.appointments.withoutorder.dto.apigee.ApigeeTokenResDTO;
import com.colsubsidio.health.appointments.withoutorder.model.*;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Component
public class TokenService {

    @Value("${apigee.url}")
    private String urlApi;
    
    @Value("${apigee.token.url}")
	private String auth;
    @Value("${apigee.token.clienteId}")
    private String clienteId;
	@Value("${apigee.token.clienteSecreto}")
	private String clienteSecreto;
	@Value("${apigee.token.maxTimeInMillis}")
	private Long maxTimeInMillis;
	
	

	@Value("${apigee.path.logger}")
	private String logger;

	
	private String token;
	private LocalDateTime tokenDate;
	
	public String getToken() {
		if (token == null) {
			generateToken();
		} else {
            long tokenMillis = tokenDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long currMillis = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			Long timeLive = currMillis - tokenMillis;
			if (timeLive > maxTimeInMillis) {
				generateToken();
			}
		}
		return token;
	}
	
	private void generateToken() {
		ApigeeTokenReqDTO request = new ApigeeTokenReqDTO();
		request.setClienteId(clienteId);
		request.setClienteSecreto(clienteSecreto);
		token = postAccessToken(request).getAccess_token();
		tokenDate = LocalDateTime.now();
	}
	
	public ApigeeTokenResDTO postAccessToken(ApigeeTokenReqDTO request) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(urlApi+auth);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<ApigeeTokenReqDTO> entity = new HttpEntity<ApigeeTokenReqDTO>(request, headers);
        
        ResponseEntity<ApigeeTokenResDTO> result = 
                restTemplate.exchange(uri.toUriString(), HttpMethod.POST, entity, ApigeeTokenResDTO.class);
        
        return result.getBody();
    }
	
	public void sendToElasticSearch(LogElasticSearch log){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.getToken());
		HttpEntity<LogElasticSearch> request = new HttpEntity<LogElasticSearch>(log,headers);
		ResponseEntity<Object> response = restTemplate.exchange(urlApi+logger, HttpMethod.POST, request, Object.class);
	}

}


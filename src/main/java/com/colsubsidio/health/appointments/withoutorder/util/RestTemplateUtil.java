package com.colsubsidio.health.appointments.withoutorder.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.colsubsidio.health.appointments.withoutorder.services.TokenService;

@Component
public class RestTemplateUtil {

	private static final String BEARER = "Bearer ";

	@Autowired
	TokenService tokenService;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	LogsManager logsManager;

	public <T> ResponseEntity<T> sendRequest(UriComponentsBuilder uri, HttpMethod method, Object body,
			Class<T> classOfT, boolean apigeeToken, String function) throws RestClientException {
		long startTimeTry = System.currentTimeMillis();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("app", "portal");
		if (apigeeToken) {
			headers.add(HttpHeaders.AUTHORIZATION, BEARER + tokenService.getToken());
		}
		HttpEntity<Object> entity = null;
		entity = (body != null) ? new HttpEntity<>(body, headers) : new HttpEntity<>(headers);
		ResponseEntity<T> resp = restTemplate.exchange(uri.toUriString(), method, entity, classOfT);
		long endTimeConn = System.currentTimeMillis() - startTimeTry;
		logsManager.logsBuildServicesReqRes(uri.toUriString(), body, resp.getBody(), method, resp.getStatusCode(),
				endTimeConn, function);
		return resp;
	}
}

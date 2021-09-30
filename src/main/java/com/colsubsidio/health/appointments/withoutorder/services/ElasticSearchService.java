package com.colsubsidio.health.appointments.withoutorder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;
import com.colsubsidio.health.appointments.withoutorder.util.RestTemplateUtil;

@Component
public class ElasticSearchService {

	@Value("${apigee.url}")
	private String urlApi;
	@Value("${apigee.path.logger}")
	private String logger;
	@Value("${apigee.path.method}")
	private String method;

	@Autowired
	private RestTemplateUtil restTemplateUtil;

	@Autowired
	LogsManager logsManager;

	@Async("asyncExecutor")
	public void sendToElasticSearch(Object object) {

		try {
			UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(urlApi + logger);
			restTemplateUtil.sendRequest(uri, HttpMethod.valueOf(method), object, Object.class, true,
					"sendToElasticSearch");
		} catch (Exception ex) {
			ex.printStackTrace();
			logsManager.logsBuildAppInsights("exception",
					"ElasticSearchService; sendToElasticSearch; " + ex.getMessage());
		}

	}

}

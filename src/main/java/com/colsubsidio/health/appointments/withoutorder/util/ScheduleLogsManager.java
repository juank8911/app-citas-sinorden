package com.colsubsidio.health.appointments.withoutorder.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.colsubsidio.health.appointments.withoutorder.model.Schedule;
import com.colsubsidio.health.appointments.withoutorder.model.ScheduleLogElasticSearch;
import com.colsubsidio.health.appointments.withoutorder.services.ElasticSearchService;

@Component
public class ScheduleLogsManager {

	@Value("${spring.application.name}")
	private String nameApplication;
	@Value("${spring.application.version}")
	private String versionApplication;
	@Value("${apigee.path.logger}")
	private String apigeePathLogger;
	@Value("${colsubsidio.elasticSearch.path}")
	private String elasticIndexPath;
	@Value("${colsubsidio.elasticSearch.schedule}")
	private String elasticIndexSchedule;
	private DateUtils dateUtils;
	private ElasticSearchService elasticSearchService;
	private LogsManager logsManager;

	@Autowired
	public ScheduleLogsManager(DateUtils dateUtils, ElasticSearchService elasticSearchService,
			LogsManager logsManager) {
		this.dateUtils = dateUtils;
		this.elasticSearchService = elasticSearchService;
		this.logsManager = logsManager;
	}

	@Async
	public void sendToElasticSearch(Schedule schedule) {

		try {
			ScheduleLogElasticSearch logModel = new ScheduleLogElasticSearch();
			logModel.setApp(nameApplication);
			logModel.setType(elasticIndexPath + elasticIndexSchedule);
			logModel.setEventDate(dateUtils.getDateString("yyyy-MM-dd'T'HH:mm:ss.SS"));
			logModel.setIndex(elasticIndexPath + elasticIndexSchedule);
			logModel.setVersion(versionApplication);
			logModel.setSchedule(schedule);
			elasticSearchService.sendToElasticSearch(logModel);
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights("exception",
					"ScheduleLogsManager; sendToElasticSearch; " + ex.getMessage());

		}

	}
}

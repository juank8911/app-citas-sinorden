package com.colsubsidio.health.appointments.withoutorder.tablestorage;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.colsubsidio.health.appointments.withoutorder.model.Schedule;
import com.colsubsidio.health.appointments.withoutorder.util.DateUtils;
import com.colsubsidio.health.appointments.withoutorder.util.LogsManager;
import com.colsubsidio.health.appointments.withoutorder.util.ScheduleLogsManager;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;

@Service
public class ScheduleStorage {

	@Value("${azure.storage.url-connection}")
	public String storageConnection;

	@Value("${azure.storage.schedule.table}")
	public String storageTable;

	@Value("${azure.storage.schedule.consult}")
	public String storageConsult;

	@Autowired
	public DateUtils dateUtils;

	@Autowired
	LogsManager logsManager;

	@Autowired
	ScheduleLogsManager scheduleLogsManager;

	private static String exception = "exception";
	private static String partitionKey = "MEDICALAPPOINTMENT";
	private static String appointmentType = "WITHOUTORDER";

	@Async("asyncExecutor")
	public Schedule insertSchedule(Schedule schedule) {

		try {

			schedule.setPartitionKey(partitionKey);
			schedule.setRowKey(schedule.getReservation());
			schedule.setAppointmentType(appointmentType);

			CloudTable cloudTable = init();
			cloudTable.createIfNotExists();
			TableOperation insert = TableOperation.insert(schedule);
			cloudTable.execute(insert);

			scheduleLogsManager.sendToElasticSearch(schedule);

		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception, "ScheduleStorage; addQuestionsAppoint; " + ex.getMessage());
		}
		return schedule;
	}

	@Async("asyncExecutor")
	public void updateSchedule(Schedule schedule) {

		final String PARTITION_KEY = "PartitionKey";
		final String ROW_KEY = "RowKey";

		try {

			CloudTable cloudTable = init();
			cloudTable.createIfNotExists();

			String partitionFilter = TableQuery.generateFilterCondition(PARTITION_KEY,
					TableQuery.QueryComparisons.EQUAL, partitionKey);

			String rowFilter = TableQuery.generateFilterCondition(ROW_KEY, TableQuery.QueryComparisons.EQUAL,
					schedule.getReservation());
			String combinedFilter = TableQuery.combineFilters(partitionFilter, TableQuery.Operators.AND, rowFilter);
			TableQuery<Schedule> partitionQuery = TableQuery.from(Schedule.class).where(combinedFilter);

			for (Schedule entity : cloudTable.execute(partitionQuery)) {
				entity.setState(schedule.getState());
				entity.setCancellation(schedule.getCancellation());
				entity.setModified(schedule.getModified());
				entity.setModifiedBy(schedule.getModifiedBy());
				TableOperation replaceEntity = TableOperation.replace(entity);
				cloudTable.execute(replaceEntity);
			}

			scheduleLogsManager.sendToElasticSearch(schedule);

		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception, "ScheduleStorage; updateSchedule; " + ex.getMessage());
		}
	}

	public List<Schedule> selectSchedule() {
		List<Schedule> table = new ArrayList<>();
		try {

			CloudTable cloudTable = init();

			String fillOrder = TableQuery.generateFilterCondition("AppointmentType", TableQuery.QueryComparisons.EQUAL,
					appointmentType);
			String combinedRowFilter = TableQuery.combineFilters(fillOrder, TableQuery.Operators.AND, storageConsult);
			TableQuery<Schedule> dataQuery = TableQuery.from(Schedule.class).where(combinedRowFilter);

			for (Schedule entity : cloudTable.execute(dataQuery)) {
				if (entity != null) {
					table.add(entity);
				}
			}
		} catch (Exception ex) {
			logsManager.logsBuildAppInsights(exception, "ScheduleStorage; selectSchedule; " + ex.getMessage());
		}
		return table;
	}

	@SuppressWarnings("unused")
	private CloudTable init() throws URISyntaxException, InvalidKeyException, StorageException {
		CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnection);
		CloudTableClient tableClient = storageAccount.createCloudTableClient();
		return tableClient.getTableReference(storageTable);
	}

	@SuppressWarnings("unused")
	private CloudTable init(String storageTableName) throws URISyntaxException, InvalidKeyException, StorageException {
		CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnection);
		CloudTableClient tableClient = storageAccount.createCloudTableClient();
		return tableClient.getTableReference(storageTableName);
	}

}

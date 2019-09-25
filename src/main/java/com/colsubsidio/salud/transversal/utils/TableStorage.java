/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.utils;

import com.colsubsidio.salud.transversal.models.LogsStorage;
import com.colsubsidio.salud.transversal.models.Properties;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableResult;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TableStorage {

    @Autowired
    private HandleDate dateHandler;

    @Autowired
    private Properties properties;

    private CloudTableClient tableClient = null;
    private CloudStorageAccount storageAccount;
    private CloudBlobContainer container = null;
    private CloudTable cloudTable = null;
    private TableResult cloudTableResult = null;

    public TableStorage() {

    }

    public boolean initConnection(String table) {
        try {
            storageAccount = CloudStorageAccount.parse(properties.getStorageConnection());
            tableClient = storageAccount.createCloudTableClient();
            cloudTable = tableClient.getTableReference(properties.getStorageTable() + table);
            cloudTable.createIfNotExists();
            return cloudTable.getServiceClient().getTableReference(properties.getStorageTable() + table).exists();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void listTables() {
        try {
            storageAccount = CloudStorageAccount.parse(properties.getStorageConnection());
            tableClient = storageAccount.createCloudTableClient();

            for (String table : tableClient.listTables()) {
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertTable(String app, String log, String mensaje, boolean connect) {
        try {
            if (connect) {
                LogsStorage logs = new LogsStorage(app, log);
                mensaje = mensaje.replace("\\\t", "").replace("\t", "").replace("\\\"", "\"").replace("\"\"", "\"").replace("\\\\t", "").replace("\\\\", "");
                logs.setLogs(mensaje);
                logs.setRowKey("cols" + dateHandler.geCadena(1));
                TableOperation insertLog = TableOperation.insert(logs);
                cloudTableResult = cloudTable.execute(insertLog);

                if (cloudTableResult != null) {
                    return true;
                } else {
                    logs.setRowKey("cols" + dateHandler.geCadena(1) + "logs" + dateHandler.geCadena(1));
                    insertLog = TableOperation.insert(logs);
                    cloudTableResult = cloudTable.execute(insertLog);
                    return cloudTableResult != null ? true : false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        
        }
        return false;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.colsubsidio.salud.transversal.utils;

import com.colsubsidio.salud.transversal.models.Properties;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlobStorage {

    @Autowired
    private Properties properties;

    private File sourceFile = null, downloadedFile = null;
    private CloudStorageAccount storageAccount;
    private CloudBlobClient blobClient = null;
    private CloudBlobContainer container = null;

    public BlobStorage() {

    }

    public void initConnection() {
        try {
            storageAccount = CloudStorageAccount.parse(properties.getStorageConnection());
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(properties.getStorageContainer());
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public CloudBlobContainer getContainer() {
        try {
            container = blobClient.getContainerReference(properties.getStorageContainer());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return container;
    }

    public void updateFileContainer(File fileUpload) {
        try {
            CloudBlockBlob blob = container.getBlockBlobReference(fileUpload.getName());
            blob.uploadFromFile(fileUpload.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateFileContainer(File fileUpload, String locationName) {
        try {
            CloudBlockBlob blob = container.getBlockBlobReference(locationName);
            blob.uploadFromFile(fileUpload.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Iterable<ListBlobItem> listFileContainer(String prefix) {
        try {
            return container.listBlobs(prefix) != null ? container.listBlobs(prefix) : null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void downloadFileContainer(String file) {
        try {
            CloudBlockBlob blob = container.getBlockBlobReference(file);
            downloadedFile = new File(sourceFile.getParentFile(), properties.getStorageDownload());
            blob.downloadToFile(downloadedFile.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteContainer() {
        try {
            if (container != null) {
                container.deleteIfExists();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteFileDownloader() {
        try {

            if (downloadedFile != null) {
                downloadedFile.deleteOnExit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteFileContainer(File upload) {
        try {

            CloudBlockBlob blob = container.getBlockBlobReference(upload.getName());

            if (blob != null) {
                blob.deleteIfExists();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public CloudBlockBlob fileContainer(String file) {
        try {
            CloudBlockBlob blob = container.getBlockBlobReference(file);
            return blob != null ? blob : null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String urlFileContainer(String file) {
        try {
            CloudBlockBlob blob = container.getBlockBlobReference(file);
            return blob != null ? blob.getUri().toURL().toString() : "";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}

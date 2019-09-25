package com.colsubsidio.salud.transversal.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Component
public class Properties {

    @Value("${spring.mail.host}")
    private String host;

//    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${portalsalud.url.login}")
    private String urlLogin;

    @Value("${portalsalud.url.logout}")
    private String urlLogout;

    @Value("${portalsalud.url.backend}")
    private String urlBackend;

    @Value("${portalsalud.access.key.secret.jwt}")
    private String JwtSecret;

    @Value("${spring.cloud.azure.storage.blob.account}")
    private String storageConnection;

    @Value("${spring.cloud.azure.storage.blob.container}")
    private String storageContainer;

    @Value("${spring.cloud.azure.storage.blob.download}")
    private String storageDownload;

    @Value("${spring.cloud.azure.storage.blob.logs}")
    private String storageLogs;

    @Value("${spring.cloud.azure.storage.blob.table}")
    private String storageTable;

    @Value("${spring.cloud.azure.storage.blob.application}")
    private String storageApplication;

    @Value("${portalsalud.location.logs}")
    private String locationLogs;
}

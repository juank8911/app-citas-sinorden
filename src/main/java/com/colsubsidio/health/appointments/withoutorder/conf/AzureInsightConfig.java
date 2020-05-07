package com.colsubsidio.health.appointments.withoutorder.conf;

import org.springframework.context.annotation.Configuration;

import com.microsoft.applicationinsights.extensibility.TelemetryProcessor;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.microsoft.applicationinsights.telemetry.Telemetry;

@Configuration
public class AzureInsightConfig implements TelemetryProcessor {

    private static final String ALWAYSON_METHOD_NAME = "GET /version";
    private static final String RES_CODE_404 = "404";

    public boolean process(Telemetry telemetry) {
        RequestTelemetry requestTelemetry = (RequestTelemetry) telemetry;
        String name = requestTelemetry.getName();
        String responseCode = requestTelemetry.getResponseCode();
        if (name.equals(ALWAYSON_METHOD_NAME) || responseCode.equals(RES_CODE_404)) {
            return false;
        } else {
            return true;
        }
    }
}


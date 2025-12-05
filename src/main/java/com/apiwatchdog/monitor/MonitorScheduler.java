package com.apiwatchdog.monitor;

import com.apiwatchdog.model.ApiResponse;
import com.apiwatchdog.service.ApiCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MonitorScheduler {

    private static final Logger log = LoggerFactory.getLogger(MonitorScheduler.class);

    private final MonitorProperties properties;
    private final ApiCheckService apiCheckService;

    public MonitorScheduler(MonitorProperties properties,
                            ApiCheckService apiCheckService) {
        this.properties = properties;
        this.apiCheckService = apiCheckService;
    }

    /**
     * Körs med fast intervall (fixedDelay), hämtat från config.
     */
    @Scheduled(fixedDelayString = "${apiwatchdog.monitor.fixed-delay-ms:60000}")
    public void runChecks() {
        if (!properties.isEnabled()) {
            return; // avstängt → gör inget
        }

        if (properties.getUrls().isEmpty()) {
            log.debug("Monitor is enabled but no URLs are configured.");
            return;
        }

        for (String url : properties.getUrls()) {
            try {
                ApiResponse result = apiCheckService.checkUrl(url);
                log.debug("Monitor check for {} -> status={}, latency={}ms, error={}",
                        url, result.getStatus(), result.getResponseTimeMs(), result.getError());
                // Mongo + alerts sköts redan inne i ApiCheckServiceImpl
            } catch (Exception e) {
                log.error("Monitor check failed for {}: {}", url, e.getMessage());
            }
        }
    }
}

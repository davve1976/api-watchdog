package com.apiwatchdog.monitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class MonitorProperties {

    private final boolean enabled;
    private final long fixedDelayMs;
    private final List<String> urls;

    public MonitorProperties(
            @Value("${apiwatchdog.monitor.enabled:false}") boolean enabled,
            @Value("${apiwatchdog.monitor.fixed-delay-ms:60000}") long fixedDelayMs,
            @Value("${apiwatchdog.monitor.urls:}") String urlsRaw
    ) {
        this.enabled = enabled;
        this.fixedDelayMs = fixedDelayMs;
        if (urlsRaw == null || urlsRaw.isBlank()) {
            this.urls = Collections.emptyList();
        } else {
            this.urls = Arrays.stream(urlsRaw.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public long getFixedDelayMs() {
        return fixedDelayMs;
    }

    public List<String> getUrls() {
        return urls;
    }
}

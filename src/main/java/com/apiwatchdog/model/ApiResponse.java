package com.apiwatchdog.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "api_checks")
public class ApiResponse {

    @Id
    private String id;

    private String url;
    private int status;
    private long responseTimeMs;
    private LocalDateTime timestamp;
    private String error;

    public ApiResponse() {
    }

    public ApiResponse(String url, int status, long responseTimeMs, LocalDateTime timestamp, String error) {
        this.url = url;
        this.status = status;
        this.responseTimeMs = responseTimeMs;
        this.timestamp = timestamp;
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public int getStatus() {
        return status;
    }

    public long getResponseTimeMs() {
        return responseTimeMs;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setResponseTimeMs(long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setError(String error) {
        this.error = error;
    }
}

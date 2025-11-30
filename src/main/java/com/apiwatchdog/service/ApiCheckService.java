package com.apiwatchdog.service;

import com.apiwatchdog.model.ApiResponse;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Service
public class ApiCheckService {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public ApiResponse checkUrl(String url) {
        long start = System.currentTimeMillis();
        int statusCode = 0;
        String error = null;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            statusCode = response.statusCode();
        } catch (Exception e) {
            error = e.getClass().getSimpleName() + ": " + e.getMessage();
        }

        long end = System.currentTimeMillis();

        return new ApiResponse(
            url,
            statusCode,
            end - start,
            LocalDateTime.now(),
            error
        );
    }
}

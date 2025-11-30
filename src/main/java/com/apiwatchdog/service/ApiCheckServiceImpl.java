package com.apiwatchdog.service;

import com.apiwatchdog.model.ApiResponse;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Service
public class ApiCheckServiceImpl implements ApiCheckService {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    // In-memory history of last N checks
    private static final int MAX_HISTORY_SIZE = 10;
    private final Deque<ApiResponse> history = new LinkedList<>();

    @Override
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

        ApiResponse result = new ApiResponse(
                url,
                statusCode,
                end - start,
                LocalDateTime.now(),
                error
        );

        addToHistory(result);
        return result;
    }

    private synchronized void addToHistory(ApiResponse response) {
        history.addFirst(response);
        while (history.size() > MAX_HISTORY_SIZE) {
            history.removeLast();
        }
    }

    @Override
    public synchronized List<ApiResponse> getHistory() {
        return List.copyOf(history);
    }
}

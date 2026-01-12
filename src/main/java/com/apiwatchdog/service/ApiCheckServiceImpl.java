package com.apiwatchdog.service;

import com.apiwatchdog.alert.AlertService;
import com.apiwatchdog.model.ApiResponse;
import com.apiwatchdog.repository.ApiResponseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
	private final AlertService alertService;
	private ApiResponseRepository repository;
	private boolean mongoEnabled;

	// In-memory history of last N checks
	private static final int MAX_HISTORY_SIZE = 10;
	private final Deque<ApiResponse> history = new LinkedList<>();

    public ApiCheckServiceImpl(AlertService alertService, @Value("${apiwatchdog.history.mongodb.enabled:false}") boolean mongoEnabled) {
        this.alertService = alertService;
        this.mongoEnabled = mongoEnabled;
    }

    @Autowired(required = false)
    public void setRepository(ApiResponseRepository repo) {
        this.repository = repo;
    }

	@Override
	public ApiResponse checkUrl(String url) {
		long start = System.currentTimeMillis();
		int statusCode = 0;
		String error = null;
		
		if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
			url = "https://" + url;
		}

		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.GET()
					.build();

			HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
			statusCode = response.statusCode();
		} catch (InterruptedException e) {
			error = e.getClass().getSimpleName() + ": " + e.getMessage();
		    Thread.currentThread().interrupt();
		} catch (IOException e) {
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

		if (mongoEnabled && repository != null) {
			try {
				repository.save(result);
			} catch (Exception ignored) {
				// logga ev.
			}
		}

		addToHistory(result);
		alertService.notifyIfFailure(result);
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

	@Override
	public List<ApiResponse> getDbHistory() {
		if (!mongoEnabled || repository == null) {
			return List.of(); // eller kasta exception, men empty är snällare
		}
		return repository.findAll(); // ev. page/limit senare
	}
}

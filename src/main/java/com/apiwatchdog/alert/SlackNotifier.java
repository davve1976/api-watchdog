package com.apiwatchdog.alert;

import com.apiwatchdog.model.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class SlackNotifier {

	private final boolean enabled;
	private final String webhookUrl;
	private final HttpClient client = HttpClient.newHttpClient();

	public SlackNotifier(
			@Value("${apiwatchdog.alerts.slack.enabled:false}") boolean enabled,
			@Value("${apiwatchdog.alerts.slack.webhook-url:}") String webhookUrl
			) {
		this.enabled = enabled;
		this.webhookUrl = webhookUrl;
	}

	public void notifyFailure(ApiResponse r) {
		if (!enabled || webhookUrl == null || webhookUrl.isBlank()) {
			return; // ingen konfig → gör inget
		}

		String text = String.format(
				":rotating_light: *API check failed*%n" +
						"URL: `%s`%n" +
						"Status: `%d`%n" +
						"Latency: `%d ms`%n" +
						"Error: `%s`",
						r.getUrl(),
						r.getStatus(),
						r.getResponseTimeMs(),
						r.getError() == null ? "-" : r.getError()
				);

		// enkel JSON {"text":"..."}
		String json = "{\"text\":\"" + escapeJson(text) + "\"}";

		HttpRequest req = HttpRequest.newBuilder()
				.uri(URI.create(webhookUrl))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json))
				.build();

		try {
			client.send(req, HttpResponse.BodyHandlers.discarding());
		} catch (Exception e) {
			// vi kraschar aldrig pga Slack-fel
			System.err.println("Failed to send Slack alert: " + e.getMessage());
		}
	}

	private String escapeJson(String s) {
		// väldigt enkel escapning som räcker här
		return s
				.replace("\\", "\\\\")
				.replace("\"", "\\\"")
				.replace("\n", "\\n")
				.replace("\r", "\\r");
	}
}

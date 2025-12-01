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
            @Value("${apiwatchdog.alerts.slack.webhook-url:}") String webhookUrl) {
        this.enabled = enabled;
        this.webhookUrl = webhookUrl;
    }

    public void notifyFailure(ApiResponse r) {
        if (!enabled || webhookUrl == null || webhookUrl.isBlank()) {
            return;
        }

        String text = String.format(
                ":rotating_light: *API check failed*\\nURL: `%s`\\nStatus: `%d`\\nLatency: `%d ms`\\nError: `%s`",
                r.getUrl(), r.getStatus(), r.getResponseTimeMs(),
                r.getError() == null ? "" : r.getError());

        String payload = "{\"text\":\"" + text.replace("\"", "\\\"") + "\"}";

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(webhookUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        try {
            client.send(req, HttpResponse.BodyHandlers.discarding());
        } catch (Exception ignored) {
        }
    }
}

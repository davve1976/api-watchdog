package com.apiwatchdog.alert;

import com.apiwatchdog.model.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private final MailSender mailSender;
    private final boolean mailEnabled;
    private final String mailTo;
    private final SlackNotifier slackNotifier;

    public AlertService(
            @Nullable MailSender mailSender,
            @Value("${apiwatchdog.alerts.mail.enabled:false}") boolean mailEnabled,
            @Value("${apiwatchdog.alerts.mail.to:}") String mailTo,
            SlackNotifier slackNotifier) {

        this.mailSender = mailSender;
        this.mailEnabled = mailEnabled;
        this.mailTo = mailTo;
        this.slackNotifier = slackNotifier;
    }

    public void notifyIfFailure(ApiResponse response) {
        // Inget alert om mail/slack ej konfigurerat
        boolean failure = response.getError() != null || response.getStatus() >= 400;
        if (!failure) {
            return;
        }

        // Mail (om aktiverat)
        sendMailIfEnabled(response);

        // Slack (om aktiverat)
        slackNotifier.notifyFailure(response);
    }

    private void sendMailIfEnabled(ApiResponse response) {
        if (!mailEnabled || mailSender == null || mailTo == null || mailTo.isBlank()) {
            return;
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailTo);
        msg.setSubject("[API Watchdog] Check failed: " + response.getUrl());
        msg.setText(buildBody(response));

        try {
            mailSender.send(msg);
        } catch (Exception ignored) {
            // här kan du logga senare om du vill
        }
    }

    private String buildBody(ApiResponse r) {
        return "URL: " + r.getUrl() + "\n" +
               "Status: " + r.getStatus() + "\n" +
               "Latency: " + r.getResponseTimeMs() + " ms\n" +
               "Timestamp: " + r.getTimestamp() + "\n" +
               "Error: " + r.getError();
    }
}

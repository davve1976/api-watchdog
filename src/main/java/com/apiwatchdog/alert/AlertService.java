package com.apiwatchdog.alert;

import com.apiwatchdog.model.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

	private final JavaMailSender mailSender;
	private final boolean mailEnabled;
	private final String mailTo;
	private final String mailFrom;
	private final SlackNotifier slackNotifier;

	public AlertService(
			@Nullable JavaMailSender mailSender,
			@Value("${apiwatchdog.alerts.mail.enabled:false}") boolean mailEnabled,
			@Value("${apiwatchdog.alerts.mail.to:}") String mailTo,
			@Value("${apiwatchdog.alerts.mail.from:no-reply@apiwatchdog.local}") String mailFrom,
			SlackNotifier slackNotifier
			) {
		this.mailSender = mailSender;
		this.mailEnabled = mailEnabled;
		this.mailTo = mailTo;
		this.mailFrom = mailFrom;
		this.slackNotifier = slackNotifier;
	}

	public void notifyIfFailure(ApiResponse response) {
		boolean failure = response.getError() != null || response.getStatus() >= 400;
		if (!failure) {
			return;
		}

		// Mail
		sendMailIfEnabled(response);

		// Slack
		slackNotifier.notifyFailure(response);
	}

	private void sendMailIfEnabled(ApiResponse response) {
		if (!mailEnabled || mailSender == null || mailTo == null || mailTo.isBlank()) {
			return;
		}

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mailTo);
		msg.setFrom(mailFrom);
		msg.setSubject("[API Watchdog] Check failed: " + response.getUrl());
		msg.setText(buildBody(response));

		try {
			mailSender.send(msg);
		} catch (MailException e) {
			System.err.println("Failed to send alert mail: " + e.getMessage());
		}
	}

	private String buildBody(ApiResponse r) {
		return "URL: " + r.getUrl() + "\n" +
				"Status: " + r.getStatus() + "\n" +
				"Latency: " + r.getResponseTimeMs() + " ms\n" +
				"Timestamp: " + r.getTimestamp() + "\n" +
				"Error: " + (r.getError() == null ? "-" : r.getError());
	}
}

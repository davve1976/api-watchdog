package com.apiwatchdog.controller;

import com.apiwatchdog.model.ApiResponse;
import com.apiwatchdog.service.ApiCheckService;

import jakarta.annotation.PostConstruct;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

	private final ApiCheckService apiCheckService;
	
	@Value("${apiwatchdog.api-key.enabled:false}")
	private boolean apiKeyEnabled;

	// support both property names
	@Value("${apiwatchdog.api-key.value:}")
	private String apiKeyValuePrimary;

	@Value("${apiwatchdog.security.api-key:}")
	private String apiKeyValueLegacy;

	public HealthCheckController(ApiCheckService apiCheckService) {
		this.apiCheckService = apiCheckService;
	}

	private String getEffectiveApiKey() {
		if (apiKeyValuePrimary != null && !apiKeyValuePrimary.isBlank()) {
			return apiKeyValuePrimary.trim();
		}
		if (apiKeyValueLegacy != null && !apiKeyValueLegacy.isBlank()) {
			return apiKeyValueLegacy.trim();
		}
		return null;
	}

	@GetMapping("/check")
	public ResponseEntity<ApiResponse> check(@RequestParam("url") String url,
						@RequestHeader(value = "X-API-KEY", required = false) String apiKey) {

		if (apiKeyEnabled) {
			String effective = getEffectiveApiKey();
			if (effective == null || !effective.equals(apiKey)) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}
		return ResponseEntity.ok(apiCheckService.checkUrl(url));
	}

	@PostConstruct
	public void logKey() {
	    System.out.println("API key enabled=" + apiKeyEnabled + " effectiveKeyPresent=" + (getEffectiveApiKey() != null));
	}

	@GetMapping("/public/check")
	public ResponseEntity<ApiResponse> publicCheck(@RequestParam("url") String url) {
	    ApiResponse result = apiCheckService.checkUrl(url);
	    return ResponseEntity.ok(result);
	}

	@GetMapping("/history")
	public ResponseEntity<List<ApiResponse>> history() {
		return ResponseEntity.ok(apiCheckService.getHistory());
	}

	@GetMapping("/history/db")
	public ResponseEntity<List<ApiResponse>> dbHistory() {
	    return ResponseEntity.ok(apiCheckService.getDbHistory());
	}

}
package com.apiwatchdog.controller;

import com.apiwatchdog.model.ApiResponse;
import com.apiwatchdog.service.ApiCheckService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

	private final ApiCheckService apiCheckService;

	public HealthCheckController(ApiCheckService apiCheckService) {
		this.apiCheckService = apiCheckService;
	}

	//    @GetMapping("/check")
	//    public ResponseEntity<ApiResponse> check(@RequestParam("url") String url,
	//    		@RequestHeader(value = "x-api-key", required = false) String apiKey) {
	//
	//    	if (!"my-secret-key".equals(apiKey)) {
	//    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	//    	}
	//    	return ResponseEntity.ok(apiCheckService.checkUrl(url));
	//    }

	@GetMapping("/check")
	public ResponseEntity<ApiResponse> check(@RequestParam("url") String url) {
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
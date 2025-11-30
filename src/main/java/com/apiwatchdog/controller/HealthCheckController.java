package com.apiwatchdog.controller;

import com.apiwatchdog.model.ApiResponse;
import com.apiwatchdog.service.ApiCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthcheck")
public class HealthCheckController {

    private final ApiCheckService apiCheckService;

    public HealthCheckController(ApiCheckService apiCheckService) {
        this.apiCheckService = apiCheckService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> check(@RequestParam("url") String url) {
        ApiResponse result = apiCheckService.checkUrl(url);
        return ResponseEntity.ok(result);
    }
}

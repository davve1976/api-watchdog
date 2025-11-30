package com.apiwatchdog.controller;

import com.apiwatchdog.model.ApiResponse;
import com.apiwatchdog.service.ApiCheckService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    private final ApiCheckService apiCheckService;

    public HealthCheckController(ApiCheckService apiCheckService) {
        this.apiCheckService = apiCheckService;
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse> check(@RequestParam("url") String url) {
        return ResponseEntity.ok(apiCheckService.checkUrl(url));
    }

    @GetMapping("/history")
    public ResponseEntity<List<ApiResponse>> history() {
        return ResponseEntity.ok(apiCheckService.getHistory());
    }
}

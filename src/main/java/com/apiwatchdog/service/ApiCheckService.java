package com.apiwatchdog.service;

import com.apiwatchdog.model.ApiResponse;

import java.util.List;

public interface ApiCheckService {

	ApiResponse checkUrl(String url);

	List<ApiResponse> getHistory();

	List<ApiResponse> getDbHistory();
}

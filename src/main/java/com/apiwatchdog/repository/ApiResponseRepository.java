package com.apiwatchdog.repository;

import com.apiwatchdog.model.ApiResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiResponseRepository extends MongoRepository<ApiResponse, String> {
}

package com.apiwatchdog.repository;

import com.apiwatchdog.model.ApiResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("production")
public interface ApiResponseRepository extends MongoRepository<ApiResponse, String> {
}

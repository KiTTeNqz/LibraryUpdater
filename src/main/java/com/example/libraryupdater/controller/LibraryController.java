package com.example.libraryupdater.controller;

import com.example.libraryupdater.exceptions.ExceptionResponse;
import com.example.libraryupdater.model.UpdateRecommendationAdapterRequest;
import com.example.libraryupdater.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {
    private final RecommendationService recommendationService;

    public LibraryController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PatchMapping("/updateRecommendation")
    public ResponseEntity<String> updateRecommendation(
            @RequestHeader("x-trace-id") String traceId,
            @RequestBody UpdateRecommendationAdapterRequest request) throws ExceptionResponse {
        recommendationService.updateRecommendation(request, traceId);
        return ResponseEntity.ok().build();
    }
}

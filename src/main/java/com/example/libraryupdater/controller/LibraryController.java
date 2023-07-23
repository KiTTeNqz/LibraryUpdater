package com.example.libraryupdater.controller;

import com.example.libraryupdater.exceptions.ErrorResponse;
import com.example.libraryupdater.exceptions.ExceptionResponse;
import com.example.libraryupdater.model.UpdateRecommendationAdapterRequest;
import com.example.libraryupdater.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class LibraryController {
    private final RecommendationService recommendationService;

    public LibraryController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PatchMapping("/updateRecommendation")
    public Mono<ResponseEntity<Object>> updateRecommendation(
            @RequestHeader("x-trace-id") String traceId,
            @RequestBody UpdateRecommendationAdapterRequest request) throws ExceptionResponse {
        return recommendationService.updateRecommendation(request, traceId)
                .thenReturn(ResponseEntity.ok().build())
                .onErrorResume(ExceptionResponse.class, ex -> {
                    ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getStatus(), ex.getMessages());
                    return Mono.just(ResponseEntity.badRequest().body(errorResponse));
                });
    }
}

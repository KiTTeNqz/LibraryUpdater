package com.example.libraryupdater.handlers;

import com.example.libraryupdater.exceptions.ErrorResponse;
import com.example.libraryupdater.exceptions.ExceptionResponse;
import com.example.libraryupdater.model.UpdateRecommendationAdapterRequest;
import com.example.libraryupdater.service.RecommendationService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class LibraryHandler {
    private final RecommendationService recommendationService;

    public LibraryHandler(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    public Mono<ServerResponse> updateRecommendation(ServerRequest request) {
        String traceId = request.headers().header("x-trace-id").get(0);
        Mono<UpdateRecommendationAdapterRequest> body = request.bodyToMono(UpdateRecommendationAdapterRequest.class);

        return body.flatMap(req -> recommendationService.updateRecommendation(req, traceId))
                .then(ServerResponse.ok().build())
                .onErrorResume(ExceptionResponse.class, ex -> {
                    ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getStatus(), ex.getMessages());
                    return ServerResponse.badRequest().bodyValue(errorResponse);
                });
    }
}

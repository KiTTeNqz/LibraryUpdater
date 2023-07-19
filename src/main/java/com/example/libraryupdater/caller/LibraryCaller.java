package com.example.libraryupdater.caller;

import com.example.libraryupdater.exceptions.ExceptionResponse;
import com.example.libraryupdater.model.UpdateRecommendationExternalRequest;
import com.example.libraryupdater.model.UpdateRecommendationGetExternalResponse;
import com.example.libraryupdater.model.UpdateRecommendationPatchRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class LibraryCaller {
    private final WebClient webClient;

    public LibraryCaller(WebClient webClient) {
        this.webClient = webClient;
    }
    public Mono<UpdateRecommendationGetExternalResponse> getBooks(UpdateRecommendationExternalRequest getExternalRequest, String traceId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-trace-id", traceId);

        Mono<UpdateRecommendationGetExternalResponse> responseMono = webClient.post()
                .uri("http://localhost:8081/getBooksList")
                .header("x-trace-id", traceId)
                .bodyValue(getExternalRequest)
                .retrieve()
                //onStatus(HttpStatus::isError, response -> handleErrorResponse(response))
                .bodyToMono(UpdateRecommendationGetExternalResponse.class);
                //.onErrorMap(ExceptionResponse.class, ex -> handleCustomException(ex));
        return responseMono;
    }

    public Mono<Void> updateRecommendation(Mono<UpdateRecommendationPatchRequest> patchRequest, String traceId){
        return webClient.patch()
                .uri("http://localhost:8081/updateRecommendation")
                .header("x-trace-id", traceId)
                .body(patchRequest, UpdateRecommendationPatchRequest.class)
                .exchangeToMono(response -> response.bodyToMono(Void.class));
    }

}

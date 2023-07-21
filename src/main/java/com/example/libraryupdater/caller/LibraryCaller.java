package com.example.libraryupdater.caller;

import com.example.libraryupdater.exceptions.ExceptionResponse;
import com.example.libraryupdater.model.UpdateRecommendationExternalRequest;
import com.example.libraryupdater.model.UpdateRecommendationGetExternalResponse;
import com.example.libraryupdater.model.UpdateRecommendationPatchRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(getExternalRequest)
                .retrieve()
                .bodyToMono(UpdateRecommendationGetExternalResponse.class)
                .onErrorResume(WebClientResponseException.class, ex->{
                        ExceptionResponse body = ex.getResponseBodyAs(ExceptionResponse.class);
                        System.out.println(body.getMessages());
                        return Mono.error(body);
                });
        return responseMono;
    }

    public Mono<Void> updateRecommendation(Mono<UpdateRecommendationPatchRequest> patchRequest, String traceId){
        return webClient.patch()
                .uri("http://localhost:8081/updateRecommendation")
                .header("x-trace-id", traceId)
                .body(patchRequest, UpdateRecommendationPatchRequest.class)
                .exchangeToMono(response -> response.bodyToMono(Void.class))
                .onErrorResume(WebClientResponseException.class, ex->{
                    System.out.println(ex.getResponseBodyAsString());
                    ExceptionResponse body = ex.getResponseBodyAs(ExceptionResponse.class);
                    return Mono.error(body);
                });
    }

}

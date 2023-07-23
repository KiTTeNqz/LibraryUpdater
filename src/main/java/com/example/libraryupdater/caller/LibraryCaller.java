package com.example.libraryupdater.caller;

import com.example.libraryupdater.exceptions.ExceptionResponse;
import com.example.libraryupdater.model.UpdateRecommendationExternalRequest;
import com.example.libraryupdater.model.UpdateRecommendationGetExternalResponse;
import com.example.libraryupdater.model.UpdateRecommendationPatchRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Objects;

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
                .onErrorResume(throwable -> {
                    if (throwable instanceof WebClientResponseException responseException) {
                        return Mono.error(Objects.requireNonNull(responseException.getResponseBodyAs(ExceptionResponse.class)));

                    } else if (throwable instanceof WebClientRequestException requestException){
                        ExceptionResponse exceptionResponse = (ExceptionResponse) requestException.getCause();
                        return Mono.error(exceptionResponse);
                    }
                    else {
                        return Mono.error(new ExceptionResponse("ERR-004", "error", "Бизнес-ошибка, " +
                                "произошедшая внутри системы-получателя при выполнении операции"));
                    }
                });
        return responseMono;
    }

    public Mono<Void> updateRecommendation(Mono<UpdateRecommendationPatchRequest> patchRequest, String traceId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-trace-id", traceId);
        return webClient.patch()
                .uri("http://localhost:8081/updateRecommendation")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(patchRequest, UpdateRecommendationPatchRequest.class)
                .exchangeToMono(response -> response.bodyToMono(Void.class))
                .onErrorResume(WebClientResponseException.class, ex->{
                    System.out.println(ex.getResponseBodyAsString());
                    ExceptionResponse body = ex.getResponseBodyAs(ExceptionResponse.class);
                    return Mono.error(Objects.requireNonNull(body));
                });
    }

}

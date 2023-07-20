package com.example.libraryupdater.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

@Component
public class ExchangeFilterFunctionWebClient implements ExchangeFilterFunction {

    private final Logger logger = LoggerFactory.getLogger(ExchangeFilterFunctionWebClient.class);

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {

        logger.info("Request: {} {} {}", request.method(), request.url(), request.headers());

        if (request.body() != null) {
            logger.info("Request Body: {}", request.body());
        }

        return next.exchange(request)
                .doOnNext(response -> {
                    logger.info("Response Status: {}", response.statusCode());
                    logger.info("Response Headers: {}", response.headers().asHttpHeaders());

                    response.body((clientHttpResponse, context) -> {
                        logger.info("Response Body: {}", clientHttpResponse);
                        return clientHttpResponse;
                    });
                })
                .doOnError(error -> logger.error("Error: {}", error.getMessage()));
    }
}

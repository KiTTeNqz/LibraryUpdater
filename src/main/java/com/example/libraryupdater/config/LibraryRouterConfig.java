package com.example.libraryupdater.config;

import com.example.libraryupdater.handlers.LibraryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class LibraryRouterConfig {
    @Bean
    public RouterFunction<ServerResponse> libraryRoutes(LibraryHandler handler) {
        return RouterFunctions.route()
                .PATCH("/updateRecommendation", handler::updateRecommendation)
                .build();
    }
}

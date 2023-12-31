package com.example.libraryupdater.service;

import com.example.libraryupdater.caller.LibraryCaller;
import com.example.libraryupdater.exceptions.ExceptionResponse;
import com.example.libraryupdater.mapper.RecommendationMapper;
import com.example.libraryupdater.model.UpdateRecommendationAdapterRequest;
import com.example.libraryupdater.model.UpdateRecommendationExternalRequest;
import com.example.libraryupdater.model.UpdateRecommendationGetExternalResponse;
import com.example.libraryupdater.model.UpdateRecommendationPatchRequest;
import com.example.libraryupdater.model.inner.response.ExternalBook;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
@Service
public class RecommendationService {
    private final RecommendationMapper mapper;

    private final LibraryCaller libCaller;

    public RecommendationService(RecommendationMapper mapper, LibraryCaller libCaller) {
        this.mapper = mapper;
        this.libCaller = libCaller;
    }

    public Mono<Void> updateRecommendation(UpdateRecommendationAdapterRequest updateRequest, String traceId){

        UpdateRecommendationExternalRequest getBooksRequest = mapper.mapId(updateRequest);
        List<UpdateRecommendationExternalRequest> getRecommendationRequests = updateRequest.getRecommendationList()
                .stream().map(mapper::mapRecommendation).toList();

        Mono<UpdateRecommendationGetExternalResponse> booksResponse = libCaller.getBooks(getBooksRequest, traceId).
                onErrorResume(ExceptionResponse.class, Mono::error);//MARK

        List<Mono<UpdateRecommendationGetExternalResponse>> recommendationBooksResponse = getRecommendationRequests.stream()
                .map(recommendation -> libCaller.getBooks(recommendation, traceId))
                .toList();

        Mono<Long> bookId = extractIdFromResponseMono(booksResponse);
        Mono<List<Long>> recommendationIdList = Flux.merge(recommendationBooksResponse.stream().map(this::extractIdFromResponseMono).toList()).collectList();

        Mono<UpdateRecommendationPatchRequest> patchRequestMono = Mono.zip(bookId, recommendationIdList, UpdateRecommendationPatchRequest::new);

        return libCaller.updateRecommendation(patchRequestMono, traceId);
    }

    public Mono<Long> extractIdFromResponseMono(Mono<UpdateRecommendationGetExternalResponse> responseMono) {
        return responseMono.flatMap(response -> {
            List<ExternalBook> bookData = response.getBookData();
            if (bookData.isEmpty()) {
                return Mono.error(new ExceptionResponse("ERR-004", "error", "Бизнес-ошибка, " +
                        "произошедшая внутри системы-получателя при выполнении операции"));
            }
            ExternalBook book = bookData.get(0);
            return Mono.just(book.getId());
        });
    }
}

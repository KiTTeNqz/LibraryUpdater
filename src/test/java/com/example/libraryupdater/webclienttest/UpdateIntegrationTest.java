package com.example.libraryupdater.webclienttest;


import com.example.libraryupdater.caller.LibraryCaller;
import com.example.libraryupdater.exceptions.ExceptionResponse;
import com.example.libraryupdater.model.UpdateRecommendationExternalRequest;
import com.example.libraryupdater.model.UpdateRecommendationGetExternalResponse;
import com.example.libraryupdater.model.UpdateRecommendationPatchRequest;
import com.example.libraryupdater.model.inner.request.SearchAttribute;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UpdateIntegrationTest {

    @Autowired
    private WebClient webClient;

    @MockBean
    private LibraryCaller libraryCaller;

    private static MockWebServer mockBackEnd;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());;
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
    }

    @Test
    public void testGetBooksCall() throws IOException, InterruptedException {
        File file = new File("src/test/java/resources/GETExternalResponse.json");

        List<SearchAttribute> searchAttributes = new ArrayList<>();
        searchAttributes.add(new SearchAttribute("title", "book title", SearchAttribute.Type.EQUAL));
        searchAttributes.add(new SearchAttribute("publisher", "book publisher", SearchAttribute.Type.EQUAL));
        UpdateRecommendationExternalRequest expectedExternalRequest = new UpdateRecommendationExternalRequest(searchAttributes);

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(expectedExternalRequest))
                .addHeader("x-trace-id", "123UFC")
                .addHeader("Content-Type", "application/json")
        );


        UpdateRecommendationGetExternalResponse expectedExternalResponse = objectMapper.readValue(file, UpdateRecommendationGetExternalResponse.class);
        Mono<UpdateRecommendationGetExternalResponse> actualExternalResponseMono = libraryCaller.getBooks(expectedExternalRequest, "123UFC");


        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertThat(recordedRequest.getPath()).isEqualTo("/getBooksList");
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        assertThat(recordedRequest.getHeader("x-trace-id")).isEqualTo("123UFC");
        StepVerifier.create(actualExternalResponseMono)
                .expectNextMatches(response-> response.getBookData().get(0).getId()
                        .equals(122334567L))
                .verifyComplete();
    }

    @Test
    public void testUpdateRecommendationSuccess() throws Exception {

        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"status\":\"success\"}")
        );

        UpdateRecommendationPatchRequest request = new UpdateRecommendationPatchRequest(123L, List.of(1L, 2L, 3L));
        Mono<Void> resultMono = libraryCaller.updateRecommendation(Mono.just(request), "123UFC");
        StepVerifier.create(resultMono)
                .expectComplete()
                .verify();
        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertThat(recordedRequest.getPath()).isEqualTo("/updateRecommendation");
        assertThat(recordedRequest.getMethod()).isEqualTo("PATCH");
        assertThat(recordedRequest.getHeader("x-trace-id")).isEqualTo("123UFC");
        assertThat(recordedRequest.getBody().readUtf8()).isEqualTo("{\"id\":123,\"recommendationIdList\":[1,2,3]}");
    }

    @Test
    public void testUpdateRecommendationFailure() throws Exception {
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("{\"errorCode\":\"ERR-001\",\"message\":\"Internal server error\"}")
        );

        UpdateRecommendationPatchRequest request = new UpdateRecommendationPatchRequest(123L, List.of(1L, 2L, 3L));

        Mono<Void> resultMono = libraryCaller.updateRecommendation(Mono.just(request), "123UFC");

        StepVerifier.create(resultMono)
                .expectErrorMatches(throwable -> throwable instanceof ExceptionResponse &&
                        ((ExceptionResponse) throwable).getErrorCode().equals("ERR-001"))
                .verify();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertThat(recordedRequest.getPath()).isEqualTo("/updateRecommendation");
        assertThat(recordedRequest.getMethod()).isEqualTo("PATCH");
        assertThat(recordedRequest.getHeader("x-trace-id")).isEqualTo("123UFC");
        assertThat(recordedRequest.getBody().readUtf8()).isEqualTo("{\"id\":123,\"recommendationIdList\":[1,2,3]}");
    }
}

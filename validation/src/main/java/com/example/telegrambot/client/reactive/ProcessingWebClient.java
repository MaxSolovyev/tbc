package com.example.telegrambot.client.reactive;

import com.example.telegrambot.dto.ProcessingRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import com.example.telegrambot.utils.ReactionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProcessingWebClient {
    private final WebClient webClient;
    private final ReactiveCircuitBreaker processingCircuitBreaker;

    public ProcessingWebClient(@Value("${processing.url}") String processingUrl,
                               ReactiveCircuitBreakerFactory circuitBreakerFactory) {

        this.webClient = WebClient.builder()
                .baseUrl("http://" + processingUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .build();
        this.processingCircuitBreaker = circuitBreakerFactory.create("ProcessingProcess");
    }

    public Mono<KeyBoardResponse> process(ProcessingRequest request) {
        return processingCircuitBreaker.run(
                webClient
                        .post()
                        .uri("/process")
                        .body(Mono.just(request), ProcessingRequest.class)
                        .retrieve()
                        .bodyToMono(KeyBoardResponse.class), throwable -> {
                    System.out.println("Error making request to processing service");
                    throwable.printStackTrace();
                    return Mono.just(new KeyBoardResponse(ReactionType.TEXT,  "service unavailable", null));
                });
    }
}

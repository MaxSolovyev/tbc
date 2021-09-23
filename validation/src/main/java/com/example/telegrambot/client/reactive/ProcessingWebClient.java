package com.example.telegrambot.client.reactive;

import com.example.telegrambot.dto.BotMessage;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.ws.rs.core.MediaType;

@Component
public class ProcessingWebClient {
    private final WebClient webClient;
    private final ReactiveCircuitBreaker processingCircuitBreaker;

    public ProcessingWebClient(@Value("${processing.url}") String processingUrl,
                               ReactiveCircuitBreakerFactory circuitBreakerFactory) {

        this.webClient = WebClient.builder()
                .baseUrl("http://" + processingUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .build();
        this.processingCircuitBreaker = circuitBreakerFactory.create("process");
    }

    public Mono<BotMessage> process(BotMessage message) {
        return processingCircuitBreaker.run(
                webClient
                        .post()
                        .uri("/process")
                        .body(Mono.just(message), BotMessage.class)
                        .retrieve()
                        .bodyToMono(BotMessage.class), throwable -> {
                    System.out.println("Error making request to processing service");
                    return Mono.just(new BotMessage(message.getBotType(), "service unavailable"));
                });
    }
}

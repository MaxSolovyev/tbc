package com.example.telegrambot.client;

import com.example.telegrambot.dto.AccountRequest;
import com.example.telegrambot.dto.AccountResponse;
import com.example.telegrambot.dto.AccountStateRequest;
import com.example.telegrambot.dto.AccountStateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AccountWebClient {
    private final WebClient webClient;
    private final ReactiveCircuitBreaker processingCircuitBreaker;

    public AccountWebClient(@Value("${account.url}") String processingUrl,
                            ReactiveCircuitBreakerFactory circuitBreakerFactory) {

        this.webClient = WebClient.builder()
                .baseUrl("http://" + processingUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .build();
        this.processingCircuitBreaker = circuitBreakerFactory.create("accountProcess");
    }

    public Mono<AccountResponse> process(AccountRequest accountRequest) {
//        AccountResponse response = webClient
//                .post()
//                .uri("/process")
//                .body(Mono.just(accountRequest), AccountRequest.class)
//                .retrieve()
//                .bodyToMono(AccountResponse.class).block();

//        return Mono.just(response);
//        return webClient
//                .post()
//                .uri("/process")
//                .body(Mono.just(accountRequest), AccountRequest.class)
//                .retrieve()
//                .bodyToMono(AccountResponse.class);


        return processingCircuitBreaker.run(
                webClient
                        .post()
                        .uri("/process")
                        .body(Mono.just(accountRequest), AccountRequest.class)
                        .retrieve()
                        .bodyToMono(AccountResponse.class), throwable -> {
                    System.out.println("Error making request to processing service");
                    throwable.printStackTrace();
                    return Mono.just(new AccountResponse("service unavailable"));
                });
    }

    public Mono<AccountStateResponse> state(AccountStateRequest accountRequest) {
        return processingCircuitBreaker.run(
                webClient
                        .post()
                        .uri("/state")
                        .body(Mono.just(accountRequest), AccountStateRequest.class)
                        .retrieve()
                        .bodyToMono(AccountStateResponse.class), throwable -> {
                    System.out.println("Error making request to processing service");
                    throwable.printStackTrace();
                    return Mono.empty();
                });
    }

}

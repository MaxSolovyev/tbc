package com.example.telegrambot.controller;

import com.example.telegrambot.dto.AccountRequest;
import com.example.telegrambot.dto.AccountResponse;
import com.example.telegrambot.dto.AccountStateRequest;
import com.example.telegrambot.dto.AccountStateResponse;
import com.example.telegrambot.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class AccountController {
    private final AccountService processingService;

    public AccountController(AccountService processingService) {
        this.processingService = processingService;
    }

    @PostMapping("/process")
    public Mono<AccountResponse> process(@RequestBody AccountRequest accountRequest) {
        return Mono.fromCallable(() -> processingService.process(accountRequest))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/state")
    public Mono<AccountStateResponse> process(@RequestBody AccountStateRequest accountRequest) {
        return Mono.fromCallable(() -> processingService.state(accountRequest))
                .subscribeOn(Schedulers.boundedElastic());
    }

}

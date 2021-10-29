package com.example.telegrambot.client;

import com.example.telegrambot.dto.AccountRequest;
import com.example.telegrambot.dto.AccountResponse;
import com.example.telegrambot.dto.AccountStateRequest;
import com.example.telegrambot.dto.AccountStateResponse;
import reactor.core.publisher.Mono;

public interface AccountReactiveService {
    Mono<AccountResponse> process(AccountRequest accountRequest);

    Mono<AccountStateResponse> state(AccountStateRequest accountRequest);
}

package com.example.telegrambot.client;

import com.example.telegrambot.dto.AccountRequest;
import com.example.telegrambot.dto.AccountResponse;
import com.example.telegrambot.dto.AccountStateRequest;
import com.example.telegrambot.dto.AccountStateResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountReactiveServiceImpl implements AccountReactiveService {
    private final AccountWebClient accountWebClient;

    public AccountReactiveServiceImpl(AccountWebClient accountWebClient) {
        this.accountWebClient = accountWebClient;

    }

    @Override
    public Mono<AccountResponse> process(AccountRequest accountRequest) {
        return accountWebClient.process(accountRequest);
    }

    @Override
    public Mono<AccountStateResponse> state(AccountStateRequest accountRequest) {
        return accountWebClient.state(accountRequest);
    }
}

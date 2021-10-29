package com.example.telegrambot.service;

import com.example.telegrambot.dto.AccountRequest;
import com.example.telegrambot.dto.AccountResponse;
import com.example.telegrambot.dto.AccountStateRequest;
import com.example.telegrambot.dto.AccountStateResponse;
import com.example.telegrambot.exception.NotFoundException;

public interface AccountService {
    AccountResponse process(AccountRequest accountRequest) throws NotFoundException;

    AccountStateResponse state(AccountStateRequest accountRequest) throws NotFoundException;
}

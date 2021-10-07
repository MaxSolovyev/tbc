package com.example.telegrambot.client.reactive;

import com.example.telegrambot.dto.KeyBoardRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import reactor.core.publisher.Mono;

public interface ProcessingReactiveService {
    Mono<KeyBoardResponse> process(KeyBoardRequest request);
}

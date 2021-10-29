package com.example.telegrambot.client.reactive;

import com.example.telegrambot.dto.ProcessingRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import reactor.core.publisher.Mono;

public interface ProcessingReactiveService {
    Mono<KeyBoardResponse> process(ProcessingRequest request);
}

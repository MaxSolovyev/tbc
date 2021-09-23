package com.example.telegrambot.client.reactive;

import com.example.telegrambot.dto.BotMessage;
import reactor.core.publisher.Mono;

public interface ProcessingReactiveService {
    Mono<BotMessage> process(BotMessage botMessage);
}

package com.example.telegrambot.controller;

import com.example.telegrambot.dto.BotMessage;
import com.example.telegrambot.service.ProcessingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class ProcessingController {
    private final ProcessingService processingService;

    public ProcessingController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping("/process")
    public Mono<BotMessage> process(@RequestBody BotMessage message) {
        return Mono.fromCallable(() -> processingService.process(message))
                .subscribeOn(Schedulers.boundedElastic());
    }
}

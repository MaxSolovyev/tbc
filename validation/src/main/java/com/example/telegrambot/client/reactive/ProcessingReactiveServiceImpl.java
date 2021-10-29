package com.example.telegrambot.client.reactive;

import com.example.telegrambot.dto.ProcessingRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProcessingReactiveServiceImpl implements ProcessingReactiveService {
    private final ProcessingWebClient processingWebClient;

    public ProcessingReactiveServiceImpl(ProcessingWebClient processingWebClient) {
        this.processingWebClient = processingWebClient;

    }

    @Override
    public Mono<KeyBoardResponse> process(ProcessingRequest request) {
        return processingWebClient.process(request);
    }
}

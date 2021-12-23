package com.example.telegrambot.controller;

import com.example.telegrambot.dto.BotMessage;
import com.example.telegrambot.dto.ButtonDto;
import com.example.telegrambot.dto.ProcessingRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.model.keyboard.ButtonNativeDto;
import com.example.telegrambot.service.ProcessingService;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class ProcessingController {
    private final ProcessingService processingService;

    public ProcessingController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @GetMapping("/checkGet")
//    public String check(String request) {
    public String check(
        ProcessingRequest request,
        BotMessage botMessage) {
        return "all is ok";
    }


    @PostMapping("/process")
    public Mono<KeyBoardResponse> process(@RequestBody ProcessingRequest request) {
        return Mono.fromCallable(() -> processingService.process(request))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/getButton/{id}")
    public Mono<ButtonDto> getButton(@PathVariable int id) {
        return Mono.fromCallable(() -> processingService.getButton(id))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/getButtonNative/{id}")
    public Mono<ButtonDto> getButtonNative(@PathVariable int id) {
        return Mono.fromCallable(() -> processingService.getNativeById(id))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/getButtonCriteria/{id}")
    public Mono<List<Object[]>> getButtonViaCriteria(@PathVariable int id) {
        return Mono.fromCallable(() -> processingService.getViaCriteriaById(id))
            .subscribeOn(Schedulers.boundedElastic());
    }

}

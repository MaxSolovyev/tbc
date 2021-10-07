package com.example.telegrambot.service;

import com.example.telegrambot.dto.KeyBoardDto;
import com.example.telegrambot.dto.KeyBoardRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import reactor.core.publisher.Mono;

public interface KeyBoardSupplier {

    ReplyKeyboard get(KeyBoardDto keyBoardDto);

    Mono<KeyBoardResponse> getResponse(KeyBoardRequest keyBoardRequest);
}

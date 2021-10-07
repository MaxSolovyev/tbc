package com.example.telegrambot.controller;

import com.example.telegrambot.dto.KeyBoardDto;
import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.model.mapper.KeyBoardMapper;
import com.example.telegrambot.service.KeyBoardActionsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeyBoardController {

    private final KeyBoardActionsService keyBoardActionsService;

    public KeyBoardController(KeyBoardActionsService keyBoardActionsService) {
        this.keyBoardActionsService = keyBoardActionsService;
    }

    @PostMapping("/keyboard")
    @ResponseStatus(HttpStatus.CREATED)
    KeyBoard create(@RequestBody KeyBoardDto keyBoardDto) {
        KeyBoard keyBoard = KeyBoardMapper.instance.toDomainObject(keyBoardDto);
        return keyBoardActionsService.create(keyBoard);
    }
}

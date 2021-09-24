package com.example.telegrambot.controller;

import com.example.telegrambot.exceptions.NotFoundException;
import com.example.telegrambot.model.dto.KeyBoardDto;
import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.model.mapper.KeyBoardMapper;
import com.example.telegrambot.request.KeyBoardAttachRequest;
import com.example.telegrambot.service.KeyBoardActionsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/attachKeyboard")
    void keyBoardAttach(@RequestBody KeyBoardAttachRequest request) throws NotFoundException {
        keyBoardActionsService.keyBoardAttachToBot(request.getBotName(), request.getKeyboardName());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle(NotFoundException exception) {
        return exception.getMessage();
    }
}

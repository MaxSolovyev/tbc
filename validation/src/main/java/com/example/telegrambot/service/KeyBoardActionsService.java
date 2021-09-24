package com.example.telegrambot.service;

import com.example.telegrambot.exceptions.NotFoundException;
import com.example.telegrambot.model.keyboard.KeyBoard;

public interface KeyBoardActionsService {
    KeyBoard create(KeyBoard keyBoard);
    void keyBoardAttachToBot(String botName, String keyBoardName) throws NotFoundException;
}

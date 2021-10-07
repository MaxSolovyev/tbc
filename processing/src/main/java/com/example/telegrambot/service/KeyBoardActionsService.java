package com.example.telegrambot.service;

import com.example.telegrambot.model.keyboard.KeyBoard;

public interface KeyBoardActionsService {

    KeyBoard create(KeyBoard keyBoard);

    KeyBoard findById(long keyBoardId);
}

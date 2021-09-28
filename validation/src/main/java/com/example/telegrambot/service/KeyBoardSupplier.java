package com.example.telegrambot.service;

import com.example.telegrambot.exceptions.NotFoundException;
import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.request.KeyBoardAnswer;
import com.example.telegrambot.request.KeyBoardRequest;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface KeyBoardSupplier {

    ReplyKeyboard get(String name) throws NotFoundException;

    ReplyKeyboard get(KeyBoard keyBoard);

    KeyBoardAnswer getAnswer(KeyBoard keyBoard, KeyBoardRequest keyBoardRequest);
}

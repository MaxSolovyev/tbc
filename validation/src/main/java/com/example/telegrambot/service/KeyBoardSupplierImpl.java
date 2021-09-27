package com.example.telegrambot.service;

import com.example.telegrambot.exceptions.NotFoundException;
import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.repository.KeyBoardRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyBoardSupplierImpl implements KeyBoardSupplier {

    public final KeyBoardRepository keyBoardRepository;

    public KeyBoardSupplierImpl(KeyBoardRepository keyBoardRepository) {
        this.keyBoardRepository = keyBoardRepository;
    }

    @Override
    public ReplyKeyboard get(String keyBoardName) throws NotFoundException {
        KeyBoard keyBoard = keyBoardRepository.findByName(keyBoardName).stream().findAny().orElseThrow(NotFoundException::new);

        return buildKeyBoard(keyBoard);
    }

    @Override
    public ReplyKeyboard get(KeyBoard keyBoard) {

        return buildKeyBoard(keyBoard);
    }

    private ReplyKeyboard buildKeyBoard(KeyBoard keyBoard) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> buttonRows = new ArrayList<>();
        keyBoard.getButtons().stream().collect(Collectors.groupingBy(Button::getRow)).forEach((row, buttons) -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            buttons.forEach(button -> {
                keyboardRow.add(button.getName());
            });
            buttonRows.add(keyboardRow);
        });
        keyboard.setKeyboard(buttonRows);
        return keyboard;
    }
}

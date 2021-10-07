package com.example.telegrambot.service;

import com.example.telegrambot.client.reactive.ProcessingReactiveService;
import com.example.telegrambot.dto.ButtonDto;
import com.example.telegrambot.dto.KeyBoardDto;
import com.example.telegrambot.dto.KeyBoardRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyBoardSupplierImpl implements KeyBoardSupplier {
    private final ProcessingReactiveService processingReactiveService;

    public KeyBoardSupplierImpl(ProcessingReactiveService processingReactiveService) {
        this.processingReactiveService = processingReactiveService;
    }

    @Override
    public ReplyKeyboard get(KeyBoardDto keyBoard) {

        return buildKeyBoard(keyBoard);
    }

    @Override
    public Mono<KeyBoardResponse> getResponse(KeyBoardRequest keyBoardRequest) {
        return processingReactiveService.process(keyBoardRequest);
    }

    private ReplyKeyboard buildKeyBoard(KeyBoardDto keyBoard) {
        switch (keyBoard.getType()) {
            case REPLY:
                return buildKeyBoardReply(keyBoard);
            case INLINE:
                return buildKeyBoardInline(keyBoard);
            default:
                return null;
        }
    }

    private ReplyKeyboard buildKeyBoardReply(KeyBoardDto keyBoard) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> buttonRows = new ArrayList<>();
        keyBoard.getButtons().stream().collect(Collectors.groupingBy(ButtonDto::getRow)).forEach((row, buttons) -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            buttons.forEach(button -> keyboardRow.add(button.getName()));
            buttonRows.add(keyboardRow);
        });
        keyboard.setKeyboard(buttonRows);
        return keyboard;
    }

    private ReplyKeyboard buildKeyBoardInline(KeyBoardDto keyBoard) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyBoardPanel = new ArrayList<>();
        keyBoard.getButtons().stream().collect(Collectors.groupingBy(ButtonDto::getRow)).forEach((row, buttons) -> {
            List<InlineKeyboardButton> keyBoardButtons = new ArrayList<>();
            buttons.forEach(button -> {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(button.getName());
                inlineKeyboardButton.setCallbackData(button.getName());

                keyBoardButtons.add(inlineKeyboardButton);
            });
            keyBoardPanel.add(keyBoardButtons);
        });
        keyboard.setKeyboard(keyBoardPanel);

        return keyboard;
    }
}

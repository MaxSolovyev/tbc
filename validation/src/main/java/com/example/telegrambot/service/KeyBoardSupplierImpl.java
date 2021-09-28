package com.example.telegrambot.service;

import com.example.telegrambot.exceptions.NotFoundException;
import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.repository.KeyBoardRepository;
import com.example.telegrambot.request.KeyBoardAnswer;
import com.example.telegrambot.request.KeyBoardRequest;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyBoardSupplierImpl implements KeyBoardSupplier {
    private static final String GET_DAY = "get current day";
    private static final String GET_RANDOM = "get random";

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

    @Override
    public KeyBoardAnswer getAnswer(KeyBoard keyBoard, KeyBoardRequest keyBoardRequest) {
        return keyBoard.getButtons().stream()
                .filter(button -> button.getName().equals(keyBoardRequest.getRequest()))
                .map(button -> new KeyBoardAnswer(button.getName()))
                .findFirst()
                .orElse(new KeyBoardAnswer("not found answer for request " + keyBoardRequest.getRequest()));
    }

    private ReplyKeyboard buildKeyBoard(KeyBoard keyBoard) {
        switch (keyBoard.getType()) {
            case REPLY:
                return buildKeyBoardReply(keyBoard);
            case INLINE:
                return buildKeyBoardInline(keyBoard);
            default:
                return null;
        }
    }

    private ReplyKeyboard buildKeyBoardReply(KeyBoard keyBoard) {
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

    private ReplyKeyboard buildKeyBoardInline(KeyBoard keyBoard) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyBoardPanel = new ArrayList<>();
        keyBoard.getButtons().stream().collect(Collectors.groupingBy(Button::getRow)).forEach((row, buttons) -> {
            List<InlineKeyboardButton> keyBoardButtons = new ArrayList<>();
            buttons.forEach(button -> {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(button.getName());

                keyBoardButtons.add(inlineKeyboardButton);
            });
            keyBoardPanel.add(keyBoardButtons);
        });
        keyboard.setKeyboard(keyBoardPanel);

        return keyboard;
    }

    private ReplyKeyboard buildKeyBoardCustomInline(KeyBoard keyBoard) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(GET_DAY);
        inlineKeyboardButton1.setCallbackData(GET_DAY);
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText(GET_RANDOM);
        inlineKeyboardButton2.setCallbackData(GET_RANDOM);
        inlineKeyboardButton2.setCallbackData(GET_RANDOM);

        List<InlineKeyboardButton> keyBoardButtons = new ArrayList<>();
        keyBoardButtons.add(inlineKeyboardButton1);
        keyBoardButtons.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> keyBoardPanel = new ArrayList<>();
        keyBoardPanel.add(keyBoardButtons);
        keyboard.setKeyboard(keyBoardPanel);

        return keyboard;
    }


}

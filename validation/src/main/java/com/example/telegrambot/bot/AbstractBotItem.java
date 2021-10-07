package com.example.telegrambot.bot;

import com.example.telegrambot.exceptions.NotFoundException;
import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.service.KeyBoardSupplier;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public abstract class AbstractBotItem extends TelegramLongPollingBot {
    protected final BotInfo botInfo;

    public AbstractBotItem(BotInfo botInfo) {
        this.botInfo = botInfo;
    }

    public static AbstractBotItem getBotItemByType(BotInfo botInfo,
                                                   KeyBoardSupplier keyBoardSupplier) throws NotFoundException {
        if (botInfo.getType() == null) {
            throw new NotFoundException();
        }
        switch (botInfo.getType()) {
            case COMMAND:
                return new BotItemCommands(botInfo);
            case KEYBOARD:
                return new BotItemKeyboard(botInfo, keyBoardSupplier);
            case DIALOGFLOW:
                return new BotItemDialogFlow(botInfo);
            default:
                throw new NotFoundException();
        }
    }

    public String getBotUsername() {
        return botInfo.getName();
    }

    public String getBotToken() {
        return botInfo.getToken();
    }
}

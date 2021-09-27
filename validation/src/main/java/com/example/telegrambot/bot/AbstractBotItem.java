package com.example.telegrambot.bot;

import com.example.telegrambot.client.reactive.ProcessingReactiveService;
import com.example.telegrambot.exceptions.NotFoundException;
import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.service.KeyBoardSupplier;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public abstract class AbstractBotItem extends TelegramLongPollingBot {
    protected final BotInfo botInfo;
    private final ProcessingReactiveService processingReactiveService;

    public AbstractBotItem(BotInfo botInfo, ProcessingReactiveService processingReactiveService) {
        this.botInfo = botInfo;
        this.processingReactiveService = processingReactiveService;
    }

    public static AbstractBotItem getBotItemByType(BotInfo botInfo,
                                                   ProcessingReactiveService processingReactiveService,
                                                   KeyBoardSupplier keyBoardSupplier) throws NotFoundException {
        if (botInfo.getType() == null) {
            throw new NotFoundException();
        }
        switch (botInfo.getType()) {
            case COMMAND:
                return new BotItemCommands(botInfo, processingReactiveService);
            case KEYBOARD:
                return new BotItemKeyboard(botInfo, processingReactiveService, keyBoardSupplier);
            case DIALOGFLOW:
                return new BotItemDialogFlow(botInfo, processingReactiveService);
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

    protected ProcessingReactiveService getProcessingReactiveService() {
        return processingReactiveService;
    }
}

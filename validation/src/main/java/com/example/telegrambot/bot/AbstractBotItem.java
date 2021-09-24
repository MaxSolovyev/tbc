package com.example.telegrambot.bot;

import com.example.telegrambot.client.reactive.ProcessingReactiveService;
import com.example.telegrambot.exceptions.NotFoundException;
import com.example.telegrambot.model.BotInfo;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public abstract class AbstractBotItem extends TelegramLongPollingBot {
    private final String botName;
    private final String token;
    private final ProcessingReactiveService processingReactiveService;

    public AbstractBotItem(String botName, String token, ProcessingReactiveService processingReactiveService) {
        this.botName = botName;
        this.token = token;
        this.processingReactiveService = processingReactiveService;
    }

    public static AbstractBotItem getBotItemByType(BotInfo botInfo, ProcessingReactiveService processingReactiveService) throws NotFoundException {
        if (botInfo.getType() == null) {
            throw new NotFoundException();
        }
        switch (botInfo.getType()) {
            case COMMAND:
                return new BotItemCommands(botInfo.getName(), botInfo.getToken(), processingReactiveService);
            case KEYBOARD:
                return new BotItemKeyboard(botInfo.getName(), botInfo.getToken(), processingReactiveService);
            case DIALOGFLOW:
                return new BotItemDialogFlow(botInfo.getName(), botInfo.getToken(), processingReactiveService);
            default:
                throw new NotFoundException();
        }
    }

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return token;
    }

    protected ProcessingReactiveService getProcessingReactiveService() {
        return processingReactiveService;
    }
}

package com.example.telegrambot.bot;

import com.example.telegrambot.client.reactive.ProcessingReactiveService;
import com.example.telegrambot.model.BotInfo;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotItemDialogFlow extends AbstractBotItem {

    public BotItemDialogFlow(BotInfo botInfo, ProcessingReactiveService processingReactiveService) {
        super(botInfo, processingReactiveService);
    }

    public void onUpdateReceived(Update update) {
        SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder();

        String messageText;
        String chatId;
        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId().toString();
            messageBuilder.chatId(chatId);
            messageText = update.getMessage().getText();
        } else {
            messageText = update.getChannelPost().getText();
            chatId = update.getChannelPost().getChatId().toString();
            messageBuilder.chatId(chatId);
        }
        messageBuilder.text(messageText);
        try {
            execute(messageBuilder.build());
        } catch (TelegramApiException e) {
            System.out.println(e.toString());
        }
    }
}

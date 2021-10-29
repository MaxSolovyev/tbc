package com.example.telegrambot.bot;

import com.example.telegrambot.dto.ProcessingRequest;
import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.service.KeyBoardSupplier;
import com.example.telegrambot.utils.ReactionType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotItemKeyboard extends AbstractBotItem {
    private static final String DEFAULT_MESSAGE = "Please, choose operation";

    private final KeyBoardSupplier keyBoardSupplier;

    public BotItemKeyboard(BotInfo botInfo, KeyBoardSupplier keyBoardSupplier) {
        super(botInfo);
        this.keyBoardSupplier = keyBoardSupplier;
    }

    public void onUpdateReceived(Update update) {
        SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder();

        String userRequest = "";
        String chatId;

        ProcessingRequest processingRequest = new ProcessingRequest();
        if (update.getMessage() != null) {
            //реакция на обычное сообщение
            chatId = update.getMessage().getChatId().toString();
            messageBuilder.chatId(chatId);
            messageBuilder.text(DEFAULT_MESSAGE);
            processingRequest.setRequestType(ReactionType.TEXT);
            userRequest = update.getMessage().getText();
        } else {
            //реакция на клавиатуру
            userRequest = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            messageBuilder.chatId(chatId);
            processingRequest.setRequestType(ReactionType.KEYBOARD);
        }
        processingRequest.setRequest(userRequest);
        processingRequest.setChatId(chatId);

        keyBoardSupplier.getResponse(processingRequest)
                .subscribe(keyBoardResponse -> {
                    String message;
                    if (keyBoardResponse.getType() == ReactionType.TEXT) {
                        message = keyBoardResponse.getText();
                    } else {
                        message = keyBoardResponse.getKeyboard().getDescription();
                        messageBuilder.replyMarkup(keyBoardSupplier.get(keyBoardResponse.getKeyboard()));
                    }
                    messageBuilder.text(message);
                    try {
                        execute(messageBuilder.build());
                    } catch (TelegramApiException e) {
                        System.out.println(e);
                    }
                });
    }
}

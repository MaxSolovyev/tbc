package com.example.telegrambot.bot;

import com.example.telegrambot.dto.KeyBoardRequest;
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

        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId().toString();
            messageBuilder.chatId(chatId);
            messageBuilder.text(DEFAULT_MESSAGE);
        } else {
            userRequest = update.getCallbackQuery().getData();
            messageBuilder.chatId(update.getCallbackQuery().getMessage().getChatId().toString());
        }

        keyBoardSupplier.getResponse(new KeyBoardRequest(userRequest))
                .subscribe(keyBoardResponse -> {
                    String message = DEFAULT_MESSAGE;
                    if (keyBoardResponse.getType() == ReactionType.TEXT) {
                        message = keyBoardResponse.getText();
                    } else {
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

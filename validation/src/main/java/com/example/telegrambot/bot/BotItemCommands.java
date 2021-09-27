package com.example.telegrambot.bot;

import com.example.telegrambot.client.reactive.ProcessingReactiveService;
import com.example.telegrambot.dto.BotMessage;
import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.utils.BotType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BotItemCommands extends AbstractBotItem {

    public BotItemCommands(BotInfo botInfo, ProcessingReactiveService processingReactiveService) {
        super(botInfo, processingReactiveService);
    }

    public void onUpdateReceived(Update update) {
        SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder();

        String messageText;
        String chatId;
        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId().toString();
            messageBuilder.chatId(chatId);
            messageText = getMessageText(update.getMessage().getText());
        } else {
            chatId = update.getChannelPost().getChatId().toString();
            messageBuilder.chatId(chatId);
            messageText = update.getChannelPost().getText();
        }

        getProcessingReactiveService().process(new BotMessage(BotType.COMMAND, messageText))
        .subscribe(botMessage -> {
            messageBuilder.text(botMessage.getMessage());
            try {
                execute(messageBuilder.build());
            } catch (TelegramApiException e) {
                System.out.println(e.toString());
            }
        });

    }

    private String getMessageText(String source) {
        String messageText = "unknown command";
        String bodyCommand = source.trim();
        String[] words = bodyCommand.split(" ", 2);

        Command command;
        try {
            command = Command.valueOf(words[0].substring(1));
        } catch (IllegalArgumentException ex) {
            return messageText;
        }
        switch (command) {
            case echo:
                messageText = words[1];
                break;
            case start:
                messageText = "Let's start";
                break;
            case help:
                messageText = "available commands: " + Arrays.stream(Command.values())
                        .map(Enum::toString)
                        .collect(Collectors.joining(", "));
                break;
        }
        return messageText;
    }

    enum Command {
        start, help, echo
    }
}

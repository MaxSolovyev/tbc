package com.example.telegrambot.bot;

import com.example.telegrambot.client.reactive.ProcessingReactiveService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BotItemKeyboard extends AbstractBotItem {
    private static final String DEFAULT_MESSAGE = "Please, choose operation";
    private static final String GET_DAY = "get current day";
    private static final String GET_RANDOM = "get random";

    public BotItemKeyboard(String botName, String token, ProcessingReactiveService processingReactiveService) {
        super(botName, token, processingReactiveService);
    }

    public void onUpdateReceived(Update update) {
        SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder();

        String messageText;
        String chatId;
        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId().toString();
            messageBuilder.chatId(chatId);
            messageText = DEFAULT_MESSAGE;
        } else {
            messageText = update.getCallbackQuery().getData();
            messageBuilder.chatId(update.getCallbackQuery().getMessage().getChatId().toString());
            switch (messageText) {
                case GET_DAY :
                    messageText = getDay();
                    break;
                case GET_RANDOM :
                    messageText = random();
                    break;

            }
        }
        messageBuilder.text(messageText);
        messageBuilder.replyMarkup(getKeyboardInline());
        try {
            execute(messageBuilder.build());
        } catch (TelegramApiException e) {
            System.out.println(e.toString());
        }
    }

    private InlineKeyboardMarkup getKeyboardInline() {
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

    private ReplyKeyboardMarkup getKeyboardReply() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("one");
        keyboardRow.add("two");
        keyboardRow.add("three");

        keyboard.setKeyboard(List.of(keyboardRow));

        return keyboard;
    }

    private String getDay() {
        return "today is " + LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    private String random() {
        return "my random number is " + new Random().nextInt(100);
    }
}

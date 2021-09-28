package com.example.telegrambot.bot;

import com.example.telegrambot.client.reactive.ProcessingReactiveService;
import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.request.KeyBoardRequest;
import com.example.telegrambot.service.KeyBoardSupplier;
import com.example.telegrambot.utils.KeyBoardType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Random;

public class BotItemKeyboard extends AbstractBotItem {
    private static final String DEFAULT_MESSAGE = "Please, choose operation";
    private static final String GET_DAY = "get current day";
    private static final String GET_RANDOM = "get random";

    private final KeyBoardSupplier keyBoardSupplier;

    public BotItemKeyboard(BotInfo botInfo, ProcessingReactiveService processingReactiveService,
                           KeyBoardSupplier keyBoardSupplier) {
        super(botInfo, processingReactiveService);
        this.keyBoardSupplier = keyBoardSupplier;
    }

    public void onUpdateReceived(Update update) {
        SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder();

        String messageText;
        String chatId;

        KeyBoard currentKeyBoard = this.botInfo.getKeyBoard();

        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId().toString();
            messageBuilder.chatId(chatId);
            if (currentKeyBoard.getType() == KeyBoardType.INLINE) {
                messageText = DEFAULT_MESSAGE;
            } else {
                messageText = keyBoardSupplier.getAnswer(currentKeyBoard, new KeyBoardRequest(update.getMessage().getText())).getAnswer();
            }
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
        messageBuilder.replyMarkup(getKeyboardReply());
        try {
            execute(messageBuilder.build());
        } catch (TelegramApiException e) {
            System.out.println(e.toString());
        }
    }

    private ReplyKeyboard getKeyboardReply() {
        return keyBoardSupplier.get(this.botInfo.getKeyBoard());
    }

    private String getDay() {
        return "today is " + LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    private String random() {
        return "my random number is " + new Random().nextInt(100);
    }
}

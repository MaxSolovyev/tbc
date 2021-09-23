package com.example.telegrambot.service;

import com.example.telegrambot.bot.AbstractBotItem;
import com.example.telegrambot.client.reactive.ProcessingReactiveService;
import com.example.telegrambot.configuration.BotConfiguration;
import com.example.telegrambot.exceptions.BotTypeIsNotFound;
import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.repository.BotInfoRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BotsListenerService implements BotManagerActions {

    private final BotInfoRepository repository;
    private final BotConfiguration configuration;
    private final ProcessingReactiveService processingReactiveService;
    private final Map<String, BotSession> registeredBots;

    public BotsListenerService(BotInfoRepository repository, BotConfiguration configuration, ProcessingReactiveService processingReactiveService) {
        this.repository = repository;
        this.configuration = configuration;
        this.processingReactiveService = processingReactiveService;
        registeredBots = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        prepare();
    }

    private void prepare() {
        List<BotInfo> boInfoList =  repository.findAll();
        boInfoList.forEach(this::addBotForListener);
    }

    public void addBotForListener(BotInfo botInfo) {
        if (!registeredBots.containsKey(botInfo.getName())) {
            try {
                TelegramLongPollingBot botItem = AbstractBotItem.getBotItemByType(botInfo, processingReactiveService);
                BotSession botSession = configuration.getTelegramBotsApi().registerBot(botItem);
                registeredBots.putIfAbsent(botItem.getBotUsername(), botSession);
            } catch (BotTypeIsNotFound | TelegramApiException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeFromListener(String botName) {
        Optional.ofNullable(registeredBots.remove(botName)).ifPresent(BotSession::stop);
    }
}

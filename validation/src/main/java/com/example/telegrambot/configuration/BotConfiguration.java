package com.example.telegrambot.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@Configuration
@EnableConfigurationProperties(BotProperties.class)
public class BotConfiguration {

    private TelegramBotsApi telegramBotsApi;

    @PostConstruct
    public void Init() throws TelegramApiException {
        telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    }

    public TelegramBotsApi getTelegramBotsApi() {
        return telegramBotsApi;
    }
}

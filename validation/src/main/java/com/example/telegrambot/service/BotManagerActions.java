package com.example.telegrambot.service;

import com.example.telegrambot.model.BotInfo;

public interface BotManagerActions {
    void addBotForListener(BotInfo botInfo);
    void removeFromListener(String botName);
}

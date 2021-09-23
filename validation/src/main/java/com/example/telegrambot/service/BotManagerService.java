package com.example.telegrambot.service;

import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.model.dto.BotDto;

import java.util.List;

public interface BotManagerService {
    void register(BotInfo botInfo);
    void unregister(String botName);
    List<BotDto> getAll();
}

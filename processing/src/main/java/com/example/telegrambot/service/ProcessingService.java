package com.example.telegrambot.service;

import com.example.telegrambot.dto.BotMessage;

public interface ProcessingService {
    BotMessage process(BotMessage message);
}

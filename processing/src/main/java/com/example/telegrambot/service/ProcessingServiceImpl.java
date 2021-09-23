package com.example.telegrambot.service;

import com.example.telegrambot.dto.BotMessage;
import org.springframework.stereotype.Service;

@Service
public class ProcessingServiceImpl implements ProcessingService {

    @Override
    public BotMessage process(BotMessage message) {
        System.out.println("process message = " + message.getMessage());
        return message;
    }
}

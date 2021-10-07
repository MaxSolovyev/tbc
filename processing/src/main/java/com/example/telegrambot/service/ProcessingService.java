package com.example.telegrambot.service;

import com.example.telegrambot.dto.KeyBoardRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import com.example.telegrambot.exception.NotFoundException;

public interface ProcessingService {
    KeyBoardResponse process(KeyBoardRequest request) throws NotFoundException;
}

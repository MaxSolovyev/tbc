package com.example.telegrambot.service;

import com.example.telegrambot.dto.ProcessingRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import com.example.telegrambot.exception.NotFoundException;

public interface ProcessingService {
    KeyBoardResponse process(ProcessingRequest request) throws NotFoundException;
}

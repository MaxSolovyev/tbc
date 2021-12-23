package com.example.telegrambot.service;

import com.example.telegrambot.dto.ButtonDto;
import com.example.telegrambot.dto.ProcessingRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import com.example.telegrambot.exception.NotFoundException;
import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.model.keyboard.ButtonNativeDto;
import java.util.List;

public interface ProcessingService {
    KeyBoardResponse process(ProcessingRequest request) throws NotFoundException;
    ButtonDto getButton(int buttonId);
    ButtonDto getNativeById(int buttonId);
    List<Object[]> getViaCriteriaById(int buttonId);
}

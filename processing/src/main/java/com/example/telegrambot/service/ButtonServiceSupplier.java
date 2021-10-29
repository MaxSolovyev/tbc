package com.example.telegrambot.service;

import com.example.telegrambot.exception.NotFoundException;
import com.example.telegrambot.model.keyboard.Button;

public interface ButtonServiceSupplier {
    Button getButtonByName(String name) throws NotFoundException;
    Button getButtonById(long id) throws NotFoundException;
}

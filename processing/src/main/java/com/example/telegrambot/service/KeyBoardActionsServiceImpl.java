package com.example.telegrambot.service;

import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.repository.KeyBoardRepository;
import org.springframework.stereotype.Service;

@Service
public class KeyBoardActionsServiceImpl implements KeyBoardActionsService {

    private final KeyBoardRepository keyBoardRepository;
    public KeyBoardActionsServiceImpl(KeyBoardRepository keyBoardRepository) {
        this.keyBoardRepository = keyBoardRepository;
    }

    @Override
    public KeyBoard create(KeyBoard keyBoard) {
        return keyBoardRepository.save(keyBoard);
    }

    @Override
    public KeyBoard findById(long keyBoardId) {
        return keyBoardRepository.findById(keyBoardId).orElse(null);
    }
}

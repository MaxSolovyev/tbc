package com.example.telegrambot.service;

import com.example.telegrambot.exceptions.NotFoundException;
import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.repository.BotInfoRepository;
import com.example.telegrambot.repository.KeyBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyBoardActionsServiceImpl implements KeyBoardActionsService {

    private final BotInfoRepository botInfoRepository;
    private final KeyBoardRepository keyBoardRepository;

    public KeyBoardActionsServiceImpl(BotInfoRepository botInfoRepository, KeyBoardRepository keyBoardRepository) {
        this.botInfoRepository = botInfoRepository;
        this.keyBoardRepository = keyBoardRepository;
    }

    @Override
    public KeyBoard create(KeyBoard keyBoard) {
        return keyBoardRepository.save(keyBoard);
    }

    @Override
    public void keyBoardAttachToBot(String botName, String keyBoardName) throws NotFoundException {
        List<KeyBoard> keyBoards = keyBoardRepository.findByName(keyBoardName);
        List<BotInfo> botInfos = botInfoRepository.findByName(botName);
        if (keyBoards.isEmpty()) {
            throw new NotFoundException("keyboard not found by name");
        }
        if (botInfos.isEmpty()) {
            throw new NotFoundException("bot not found by name");
        }
        BotInfo botInfo = botInfos.get(0);
        botInfo.setKeyBoard(keyBoards.get(0));
        botInfoRepository.save(botInfo);
    }
}

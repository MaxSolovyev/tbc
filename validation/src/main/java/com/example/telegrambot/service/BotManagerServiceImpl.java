package com.example.telegrambot.service;

import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.model.dto.BotDto;
import com.example.telegrambot.repository.BotInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BotManagerServiceImpl implements BotManagerService {
    private BotInfoRepository botInfoRepository;
    private BotManagerActions botManagerActions;

    @Autowired
    public BotManagerServiceImpl(BotInfoRepository botInfoRepository, BotManagerActions botManagerActions) {
        this.botInfoRepository = botInfoRepository;
        this.botManagerActions = botManagerActions;
    }

    @Transactional
    @Override
    public void register(BotInfo botInfo) {
        List<BotInfo> botInfoList = botInfoRepository.findByName(botInfo.getName());
        if (botInfoList.isEmpty()) {
            botInfoRepository.save(botInfo);
            botManagerActions.addBotForListener(botInfo);
        }
    }

    @Transactional
    @Override
    public void unregister(String botName) {
        botInfoRepository.findByName(botName).forEach(info -> {
            botInfoRepository.delete(info);
            botManagerActions.removeFromListener(info.getName());
        });
    }

    @Override
    public List<BotDto> getAll() {
        return botInfoRepository
                .findAll()
                .stream()
                .map(info -> new BotDto(info.getName(), info.getToken(), info.getType()))
                .collect(Collectors.toList());
    }
}

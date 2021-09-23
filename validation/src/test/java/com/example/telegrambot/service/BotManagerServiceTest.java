package com.example.telegrambot.service;

import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.repository.BotInfoRepository;
import com.example.telegrambot.utils.BotType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Bot manager service operation execution correctly")
@ExtendWith(MockitoExtension.class)
public class BotManagerServiceTest {

    @Mock
    BotInfoRepository botInfoRepository;

    @Mock
    BotManagerActions botManagerActions;

    @InjectMocks
    BotManagerServiceImpl botManagerService;

    @DisplayName("Should invoke save method of repository when not found at datasource")
    @Test
    void registerIfNotExist() {
        BotInfo botInfo = new BotInfo("bot", "pass", BotType.COMMAND);
        Mockito.when(botInfoRepository.findByName(anyString())).thenReturn(Collections.emptyList());
        Mockito.when(botInfoRepository.save(any(BotInfo.class))).thenReturn(botInfo);
        Mockito.doNothing().when(botManagerActions).addBotForListener(any(BotInfo.class));
        botManagerService.register(botInfo);
        verify(botInfoRepository).save(botInfo);
    }

    @DisplayName("Should not invoke save method of repository when is found at datasource")
    @Test
    void registerIfExist() {
        BotInfo botInfo = new BotInfo("bot", "pass", BotType.COMMAND);
        Mockito.when(botInfoRepository.findByName(anyString())).thenReturn(List.of(botInfo));
        botManagerService.register(botInfo);
        verify(botInfoRepository, times(0)).save(any(BotInfo.class));
    }

}

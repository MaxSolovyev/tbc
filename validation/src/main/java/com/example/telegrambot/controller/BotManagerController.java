package com.example.telegrambot.controller;

import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.model.dto.BotDto;
import com.example.telegrambot.model.mapper.BotInfoMapper;
import com.example.telegrambot.service.BotManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BotManagerController {
    @Autowired
    BotManagerService botManagerService;

    @PostMapping("/bot/register")
    public void register(@RequestBody BotDto botDto) {
        BotInfo botInfo = BotInfoMapper.instance.toDomainObject(botDto);
        botManagerService.register(botInfo);
    }

    @GetMapping("/bot/unregister/{botName}")
    public void unregister(@PathVariable String botName) {
        botManagerService.unregister(botName);
    }

    @GetMapping("/bot/readAll")
    public List<BotDto> getAll() {
        return botManagerService.getAll();
    }
}

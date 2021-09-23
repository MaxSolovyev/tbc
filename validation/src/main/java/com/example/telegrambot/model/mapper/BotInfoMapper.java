package com.example.telegrambot.model.mapper;

import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.model.dto.BotDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BotInfoMapper {

    BotInfoMapper instance = Mappers.getMapper(BotInfoMapper.class);

    @Mapping(target = "id", ignore = true)
    BotInfo toDomainObject(BotDto botDto);
}

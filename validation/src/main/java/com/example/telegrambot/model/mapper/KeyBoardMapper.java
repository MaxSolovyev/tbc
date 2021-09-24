package com.example.telegrambot.model.mapper;

import com.example.telegrambot.model.dto.KeyBoardDto;
import com.example.telegrambot.model.keyboard.KeyBoard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface KeyBoardMapper {

    KeyBoardMapper instance = Mappers.getMapper(KeyBoardMapper.class);

    @Mapping(target = "id", ignore = true)
    KeyBoard toDomainObject(KeyBoardDto keyBoardDto);
}

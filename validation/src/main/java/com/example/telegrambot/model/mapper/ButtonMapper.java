package com.example.telegrambot.model.mapper;

import com.example.telegrambot.model.dto.ButtonDto;
import com.example.telegrambot.model.keyboard.Button;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ButtonMapper {

    ButtonMapper instance = Mappers.getMapper(ButtonMapper.class);

    @Mapping(target = "id", ignore = true)
    Button toDomainObject(ButtonDto buttonDto);
}

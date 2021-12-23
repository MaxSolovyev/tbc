package com.example.telegrambot.model.mapper;

import com.example.telegrambot.dto.ButtonDto;
import com.example.telegrambot.model.keyboard.Button;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ReactionMapper.class})
public interface ButtonMapper {

    ButtonMapper instance = Mappers.getMapper(ButtonMapper.class);

    @Mapping(target = "id", ignore = true)
    Button toDomainObject(ButtonDto buttonDto);

    @Mapping(target = "reaction", ignore = true)
    ButtonDto toDto(Button button);

}

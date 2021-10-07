package com.example.telegrambot.model.mapper;

import com.example.telegrambot.dto.ReactionDto;
import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.model.keyboard.Reaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReactionMapper {

    ReactionMapper instance = Mappers.getMapper(ReactionMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "keyBoardId", target = "keyboard")
    default Reaction toDomainObject(ReactionDto reactionDto) {
        Reaction reaction = new Reaction();
        if (reactionDto.getKeyBoardId() > 0) {
            KeyBoard keyBoard = new KeyBoard();
            keyBoard.setId(reactionDto.getKeyBoardId());
            reaction.setKeyboard(keyBoard);
        }
        reaction.setType(reactionDto.getType());
        reaction.setText(reactionDto.getText());
        return reaction;
    }
}

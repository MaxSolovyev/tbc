package com.example.telegrambot.service;

import com.example.telegrambot.dto.KeyBoardDto;
import com.example.telegrambot.dto.KeyBoardRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import com.example.telegrambot.exception.NotFoundException;
import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.model.keyboard.Reaction;
import com.example.telegrambot.model.mapper.KeyBoardMapper;
import com.example.telegrambot.repository.KeyBoardRepository;
import com.example.telegrambot.utils.ReactionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessingServiceImpl implements ProcessingService {
    private static final String FIRST_KEYBOARD = "main_menu";

    private final ButtonServiceSupplier buttonServiceSupplier;
    private final KeyBoardRepository keyBoardRepository;

    public ProcessingServiceImpl(ButtonServiceSupplier buttonServiceSupplier,
                                 KeyBoardRepository keyBoardRepository) {
        this.buttonServiceSupplier = buttonServiceSupplier;
        this.keyBoardRepository = keyBoardRepository;
    }

    @Override
    public KeyBoardResponse process(KeyBoardRequest request) throws NotFoundException {
        KeyBoardResponse keyBoardResponse = new KeyBoardResponse();

        if (request.getRequest().isEmpty()) {
            List<KeyBoard> keyBoards = keyBoardRepository.findByName(FIRST_KEYBOARD);
            if (!keyBoards.isEmpty()) {
                keyBoardResponse.setType(ReactionType.KEYBOARD);
                KeyBoardDto keyBoardDto = KeyBoardMapper.instance.toDto(keyBoards.get(0));
                keyBoardResponse.setKeyboard(keyBoardDto);
            } else {
                throw new NotFoundException();
            }

        } else {
            Button buttonByName = buttonServiceSupplier.getButtonByName(request.getRequest());
            Reaction reaction = buttonByName.getReaction();
            keyBoardResponse.setType(reaction.getType());
            switch (keyBoardResponse.getType()) {
                case TEXT:
                    keyBoardResponse.setText(reaction.getText());
                    break;
                case KEYBOARD:
                    KeyBoardDto keyBoardDto = KeyBoardMapper.instance.toDto(reaction.getKeyboard());
                    keyBoardResponse.setKeyboard(keyBoardDto);
                    break;
            }
        }
        return keyBoardResponse;
    }
}

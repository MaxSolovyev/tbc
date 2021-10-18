package com.example.telegrambot.service;

import com.example.telegrambot.dto.KeyBoardDto;
import com.example.telegrambot.dto.KeyBoardRequest;
import com.example.telegrambot.dto.KeyBoardResponse;
import com.example.telegrambot.exception.NotFoundException;
import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.model.keyboard.Reaction;
import com.example.telegrambot.model.mapper.KeyBoardMapper;
import com.example.telegrambot.repository.KeyBoardRepository;
import com.example.telegrambot.utils.ReactionType;
import org.springframework.stereotype.Service;

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
            keyBoardRepository.findByNameWithButtons(FIRST_KEYBOARD).ifPresentOrElse(keyBoard -> {
                keyBoardResponse.setType(ReactionType.KEYBOARD);
                KeyBoardDto keyBoardDto = KeyBoardMapper.instance.toDto(keyBoard);
                keyBoardResponse.setKeyboard(keyBoardDto);
            }, NotFoundException::new);
        } else {
            Button buttonByName = buttonServiceSupplier.getButtonByName(request.getRequest());
            Reaction reaction = buttonByName.getReaction();
            keyBoardResponse.setType(reaction.getType());
            switch (keyBoardResponse.getType()) {
                case TEXT:
                    keyBoardResponse.setText(reaction.getText());
                    break;
                case KEYBOARD:
                    keyBoardRepository.findByIdWithButtons(reaction.getKeyboard().getId()).ifPresentOrElse(
                            button -> keyBoardResponse.setKeyboard(KeyBoardMapper.instance.toDto(button)),
                            NotFoundException::new);
                    break;
            }
        }
        return keyBoardResponse;
    }
}

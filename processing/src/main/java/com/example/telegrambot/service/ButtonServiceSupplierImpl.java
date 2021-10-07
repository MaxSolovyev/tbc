package com.example.telegrambot.service;

import com.example.telegrambot.exception.NotFoundException;
import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.repository.ButtonRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ButtonServiceSupplierImpl implements ButtonServiceSupplier {

    private final ButtonRepository buttonRepository;

    public ButtonServiceSupplierImpl(ButtonRepository buttonRepository) {
        this.buttonRepository = buttonRepository;
    }

    @Override
    @Cacheable(value="buttons")
    public Button getButtonByName(String name) throws NotFoundException {
        List<Button> buttonsByName = buttonRepository.findByName(name);
        System.out.println("read button from database by name - " + name);
        if (buttonsByName.isEmpty()) {
            throw new NotFoundException();
        } else {
            return buttonsByName.get(0);
        }
    }
}

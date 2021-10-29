package com.example.telegrambot.service;

import com.example.telegrambot.exception.NotFoundException;
import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.repository.ButtonRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ButtonServiceSupplierImpl implements ButtonServiceSupplier {

    private final ButtonRepository buttonRepository;

    public ButtonServiceSupplierImpl(ButtonRepository buttonRepository) {
        this.buttonRepository = buttonRepository;
    }

    @Override
    @Cacheable(value="buttonsByName")
    public Button getButtonByName(String name) throws NotFoundException {
        return buttonRepository.findByName(name).orElseThrow(NotFoundException::new);
    }

    @Override
    @Cacheable(value="buttonsById")
    public Button getButtonById(long id) throws NotFoundException {
        return buttonRepository.findById(id).orElseThrow(NotFoundException::new);
    }

}

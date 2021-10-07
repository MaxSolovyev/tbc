package com.example.telegrambot.dto;

import com.example.telegrambot.utils.KeyBoardType;

import java.util.Set;

public class KeyBoardDto {

    private String name;
    private KeyBoardType type;
    private Set<ButtonDto> buttons;

    public KeyBoardDto() {
    }

    public KeyBoardDto(String name, KeyBoardType type, Set<ButtonDto> buttons) {
        this.name = name;
        this.type = type;
        this.buttons = buttons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KeyBoardType getType() {
        return type;
    }

    public void setType(KeyBoardType type) {
        this.type = type;
    }

    public Set<ButtonDto> getButtons() {
        return buttons;
    }

    public void setButtons(Set<ButtonDto> buttons) {
        this.buttons = buttons;
    }

    @Override
    public String toString() {
        return "KeyBoardDto{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", buttons=" + buttons +
                '}';
    }
}

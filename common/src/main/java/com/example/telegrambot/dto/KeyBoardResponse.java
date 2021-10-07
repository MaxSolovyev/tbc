package com.example.telegrambot.dto;

import com.example.telegrambot.utils.ReactionType;

import java.io.Serializable;

public class KeyBoardResponse implements Serializable {
    ReactionType type;
    String text;
    KeyBoardDto keyboard;

    public KeyBoardResponse() {
    }

    public KeyBoardResponse(ReactionType type, String text, KeyBoardDto keyboard) {
        this.type = type;
        this.text = text;
        this.keyboard = keyboard;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public KeyBoardDto getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(KeyBoardDto keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public String toString() {
        return "KeyBoardResponse{" +
                "type=" + type +
                ", text='" + text + '\'' +
                ", keyboard=" + keyboard +
                '}';
    }
}

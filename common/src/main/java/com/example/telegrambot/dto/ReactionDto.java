package com.example.telegrambot.dto;

import com.example.telegrambot.utils.ReactionType;

public class ReactionDto {
    private ReactionType type;
    private String text;
    private long keyBoardId;

    public ReactionDto() {
    }

    public ReactionDto(ReactionType type, String text, long keyBoardId) {
        this.type = type;
        this.text = text;
        this.keyBoardId = keyBoardId;
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

    public long getKeyBoardId() {
        return keyBoardId;
    }

    public void setKeyBoardId(long keyBoardId) {
        this.keyBoardId = keyBoardId;
    }

    @Override
    public String toString() {
        return "ReactionDto{" +
                "type=" + type +
                ", Text='" + text + '\'' +
                ", keyBoardId=" + keyBoardId +
                '}';
    }
}

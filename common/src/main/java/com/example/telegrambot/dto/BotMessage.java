package com.example.telegrambot.dto;


import com.example.telegrambot.utils.BotType;

public class BotMessage {
    private BotType botType;
    private String Message;

    public BotMessage() {
    }

    public BotMessage(BotType botType, String message) {
        this.botType = botType;
        Message = message;
    }

    public BotType getBotType() {
        return botType;
    }

    public void setBotType(BotType botType) {
        this.botType = botType;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "BotMessage{" +
                "botType=" + botType +
                ", Message='" + Message + '\'' +
                '}';
    }
}

package com.example.telegrambot.request;

public class KeyBoardAttachRequest {
    private String botName;
    private String keyboardName;

    public KeyBoardAttachRequest(String botName, String keyboardName) {
        this.botName = botName;
        this.keyboardName = keyboardName;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getKeyboardName() {
        return keyboardName;
    }

    public void setKeyboardName(String keyboardName) {
        this.keyboardName = keyboardName;
    }

    @Override
    public String toString() {
        return "KeyBoardAttachRequest{" +
                "botName='" + botName + '\'' +
                ", keyboardName='" + keyboardName + '\'' +
                '}';
    }
}

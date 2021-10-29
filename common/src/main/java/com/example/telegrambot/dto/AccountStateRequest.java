package com.example.telegrambot.dto;

public class AccountStateRequest {
    private String chatId;
    private String answer;

    public AccountStateRequest() {
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "AccountStateRequest{" +
                "chatId='" + chatId + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}

package com.example.telegrambot.dto;

public class AccountStateResponse {
    private int currentQuestion;

    public AccountStateResponse() {
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    @Override
    public String toString() {
        return "AccountStateResponse{" +
                "currentQuestion=" + currentQuestion +
                '}';
    }
}

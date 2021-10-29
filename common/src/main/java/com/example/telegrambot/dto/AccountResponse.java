package com.example.telegrambot.dto;

public class AccountResponse {
    private String answer;

    public AccountResponse() {
    }

    public AccountResponse(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "answer='" + answer + '\'' +
                '}';
    }
}

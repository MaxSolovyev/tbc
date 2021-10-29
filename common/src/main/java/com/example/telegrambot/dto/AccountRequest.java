package com.example.telegrambot.dto;

public class AccountRequest {
    private String chatId;
    private int nextQuestionId;
    private String nextQuestion;

    public AccountRequest() {
    }

    public AccountRequest(String chatId, int nextQuestionId) {
        this.chatId = chatId;
        this.nextQuestionId = nextQuestionId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public int getNextQuestionId() {
        return nextQuestionId;
    }

    public void setNextQuestionId(int nextQuestionId) {
        this.nextQuestionId = nextQuestionId;
    }

    public String getNextQuestion() {
        return nextQuestion;
    }

    public void setNextQuestion(String nextQuestion) {
        this.nextQuestion = nextQuestion;
    }

    @Override
    public String toString() {
        return "AccountRequest{" +
                "chatId='" + chatId + '\'' +
                ", nextQuestionId=" + nextQuestionId +
                '}';
    }
}

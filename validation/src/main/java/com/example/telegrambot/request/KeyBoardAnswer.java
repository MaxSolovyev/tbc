package com.example.telegrambot.request;

public class KeyBoardAnswer {
    private String answer;

    public KeyBoardAnswer(String answer) {
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
        return "KeyBoardAnswer{" +
                "answer='" + answer + '\'' +
                '}';
    }
}

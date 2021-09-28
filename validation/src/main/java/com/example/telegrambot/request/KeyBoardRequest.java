package com.example.telegrambot.request;

public class KeyBoardRequest {
    private String request;

    public KeyBoardRequest(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    @Override
    public String toString() {
        return "KeyBoardRequest{" +
                "request='" + request + '\'' +
                '}';
    }
}

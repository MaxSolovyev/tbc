package com.example.telegrambot.dto;

import java.io.Serializable;

public class KeyBoardRequest implements Serializable {
    private String request;

    public KeyBoardRequest() {
    }

    public KeyBoardRequest(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "KeyBoardRequest{" +
                "request='" + request + '\'' +
                '}';
    }
}

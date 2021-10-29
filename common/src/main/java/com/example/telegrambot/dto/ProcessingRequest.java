package com.example.telegrambot.dto;

import com.example.telegrambot.utils.ReactionType;

import java.io.Serializable;

public class ProcessingRequest implements Serializable {
    private ReactionType requestType;
    private String chatId;
    private String request;

    public ProcessingRequest() {
    }

    public ProcessingRequest(ReactionType requestType, String chatId, String request) {
        this.requestType = requestType;
        this.chatId = chatId;
        this.request = request;
    }

    public ReactionType getRequestType() {
        return requestType;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setRequestType(ReactionType requestType) {
        this.requestType = requestType;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "ProcessingRequest{" +
                "requestType=" + requestType +
                ", request='" + request + '\'' +
                '}';
    }
}

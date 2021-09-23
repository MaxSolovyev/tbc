package com.example.telegrambot.model.dto;

import com.example.telegrambot.utils.BotType;

import java.io.Serializable;
import java.util.Objects;

public class BotDto implements Serializable {
    private String name;
    private String token;
    private BotType type;

    public BotDto() {
    }

    public BotDto(String name, String token, BotType type) {
        this.name = name;
        this.token = token;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public BotType getType() {
        return type;
    }

    public void setType(BotType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotDto botDto = (BotDto) o;
        return name.equals(botDto.name) &&
                token.equals(botDto.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, token);
    }

    @Override
    public String toString() {
        return "BotDto{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", type=" + type +
                '}';
    }
}

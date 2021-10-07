package com.example.telegrambot.model;

import com.example.telegrambot.utils.BotType;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class BotInfo {

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    private String name;
    private String token;

    @Enumerated(EnumType.STRING)
    private BotType type;

    public BotInfo() {
    }

    public BotInfo(long id, String name, String token, BotType type) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.type = type;
    }

    public BotInfo(String name, String token, BotType type) {
        this.name = name;
        this.token = token;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    public String toString() {
        return "BotInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", type=" + type +
                '}';
    }
}

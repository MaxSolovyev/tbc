package com.example.telegrambot.model;

import com.example.telegrambot.utils.EnumAccountState;

import javax.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String chatId;
    private String name;
    private String phone;
    private EnumAccountState state;

    private int questionId;

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EnumAccountState getState() {
        return state;
    }

    public void setState(EnumAccountState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", chatId='" + chatId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

package com.example.telegrambot.model.keyboard;

public class ButtonNativeDto {
    private long id;
    private String name;
    private int row;

    public ButtonNativeDto() {
    }

    public ButtonNativeDto(long id, String name, int row) {
        this.id = id;
        this.name = name;
        this.row = row;
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}

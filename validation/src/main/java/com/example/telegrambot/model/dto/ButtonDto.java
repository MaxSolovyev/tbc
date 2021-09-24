package com.example.telegrambot.model.dto;

public class ButtonDto {
    private int row;
    private String name;
    private String answer;

    public ButtonDto() {
    }

    public ButtonDto(int row, String name, String answer) {
        this.row = row;
        this.name = name;
        this.answer = answer;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "ButtonDto{" +
                "row=" + row +
                ", name='" + name + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}

package com.example.telegrambot.dto;

public class ButtonDto {
    private int row;
    private String name;
    private ReactionDto reaction;

    public ButtonDto() {
    }

    public ButtonDto(int row, String name, ReactionDto reaction) {
        this.row = row;
        this.name = name;
        this.reaction = reaction;
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

    public ReactionDto getReaction() {
        return reaction;
    }

    public void setReaction(ReactionDto reaction) {
        this.reaction = reaction;
    }

    @Override
    public String toString() {
        return "ButtonDto{" +
                "row=" + row +
                ", name='" + name + '\'' +
                '}';
    }
}

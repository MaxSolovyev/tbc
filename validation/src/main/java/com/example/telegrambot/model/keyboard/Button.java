package com.example.telegrambot.model.keyboard;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Button {
    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;
    private int row;
    private String name;
    private String answer;
    @ManyToOne
    private KeyBoard keyBoard;

    public Button() {
    }

    public Button(long id, int row, String name, String answer) {
        this.id = id;
        this.row = row;
        this.name = name;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return "Button{" +
                "id=" + id +
                ", row=" + row +
                ", name='" + name + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}

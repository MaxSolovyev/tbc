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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "keyboard_id")
    private KeyBoard keyBoard;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Reaction reaction;

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

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
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

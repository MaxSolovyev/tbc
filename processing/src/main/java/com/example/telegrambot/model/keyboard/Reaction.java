package com.example.telegrambot.model.keyboard;

import com.example.telegrambot.utils.ReactionType;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Reaction {
    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private ReactionType type;
    private String text;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "keyboard_id", referencedColumnName = "id")
    private KeyBoard keyboard;

    public Reaction() {
    }

    public Reaction(long id, String text) {
        this.id = id;
        this.text = text;
        this.type = ReactionType.TEXT;
    }

    public Reaction(long id, KeyBoard keyboard) {
        this.id = id;
        this.keyboard = keyboard;
        this.type = ReactionType.KEYBOARD;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public KeyBoard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(KeyBoard keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "id=" + id +
                ", type=" + type +
                ", text='" + text + '\'' +
                ", keyboard=" + keyboard +
                '}';
    }
}

package com.example.telegrambot.model.keyboard;

import com.example.telegrambot.utils.KeyBoardType;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class KeyBoard {

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private KeyBoardType type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "keyboard_id")
    private Set<Button> buttons;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "keyboard_id")
    private Set<Reaction> reactions;

    public KeyBoard() {
    }

    public KeyBoard(long id, String name, KeyBoardType type, Set<Button> buttons) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.buttons = buttons;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public KeyBoardType getType() {
        return type;
    }

    public void setType(KeyBoardType type) {
        this.type = type;
    }

    public Set<Button> getButtons() {
        return buttons;
    }

    public void setButtons(Set<Button> buttons) {
        this.buttons = buttons;
    }

    public Set<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }

    @Override
    public String toString() {
        return "KeyBoard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", buttons=" + buttons +
                '}';
    }
}

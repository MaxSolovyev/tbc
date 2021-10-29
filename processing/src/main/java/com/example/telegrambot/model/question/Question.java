package com.example.telegrambot.model.question;

import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.model.keyboard.Reaction;
import com.example.telegrambot.utils.ReactionType;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    private int id;
    private int order;
    private String question;

    @Enumerated(EnumType.STRING)
    private ReactionType answerType;

    @ManyToOne
    private KeyBoard answerKeyBoard;

    @ManyToOne
    private CheckList checkList;

    public Question() {
    }

    public int getId() {
        return id;
    }

    public KeyBoard getAnswerKeyBoard() {
        return answerKeyBoard;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ReactionType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(ReactionType answerType) {
        this.answerType = answerType;
    }

    public CheckList getCheckList() {
        return checkList;
    }

    public void setCheckList(CheckList checkList) {
        this.checkList = checkList;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", checkList=" + checkList +
                '}';
    }
}

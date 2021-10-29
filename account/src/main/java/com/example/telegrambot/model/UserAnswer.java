package com.example.telegrambot.model;

import javax.persistence.*;

@Entity
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String request;

    @ManyToOne
    private Account account;
    private int questionId;
    private String question;

    private String answer;
    private boolean done;

    public UserAnswer() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", done=" + done +
                '}';
    }
}
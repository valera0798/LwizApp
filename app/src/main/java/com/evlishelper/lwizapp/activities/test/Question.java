package com.evlishelper.lwizapp.activities.test;

/**
 * Created by Asus on 13.07.2017.
 */

public class Question {
    private String question;
    private boolean isCorrecet = false;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isCorrecet() {
        return isCorrecet;
    }

    public void setCorrecet(boolean correcet) {
        isCorrecet = correcet;
    }

    public Question(String question, boolean isCorrecet) {
        this.question = question;
        this.isCorrecet = isCorrecet;

    }

    public Question(String question) {
        this.question = question;

    }
}

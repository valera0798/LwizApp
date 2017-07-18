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

    public boolean isCorrecet() {
        return isCorrecet;
    }

    public void setCorrecet(boolean correcet) {
        isCorrecet = correcet;
    }


    public Question(String question) {
        this.question = question;

    }
}

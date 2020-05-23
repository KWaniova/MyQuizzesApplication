package com.example.myquizzesapplication;

public class Result {
    private int quantityOfQuestions;
    private int getQuantityOfRightAnswers;
    private int getQuantityOfWrongAnswers;

    public Result() {}

    public int getQuantityOfQuestions() {
        return quantityOfQuestions;
    }

    public void setQuantityOfQuestions(int quantityOfQuestions) {
        this.quantityOfQuestions = quantityOfQuestions;
    }

    public int getGetQuantityOfRightAnswers() {
        return getQuantityOfRightAnswers;
    }

    public void setGetQuantityOfRightAnswers(int getQuantityOfRightAnswers) {
        this.getQuantityOfRightAnswers = getQuantityOfRightAnswers;
    }

    public int getGetQuantityOfWrongAnswers() {
        return getQuantityOfWrongAnswers;
    }

    public void setGetQuantityOfWrongAnswers(int getQuantityOfWrongAnswers) {
        this.getQuantityOfWrongAnswers = getQuantityOfWrongAnswers;
    }
}

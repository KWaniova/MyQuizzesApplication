package com.example.myquizzesapplication.Statistic;

public class Result {
    private int quantityOfQuestions;
    private int QuantityOfRightAnswers = 0;
    private int QuantityOfWrongAnswers = 0;

    public Result() {}

    public int getQuantityOfQuestions() {
        return quantityOfQuestions;
    }

    public void setQuantityOfQuestions(int quantityOfQuestions) {
        this.quantityOfQuestions = quantityOfQuestions;
    }

    public int getGetQuantityOfRightAnswers() {
        return QuantityOfRightAnswers;
    }

    public void incrementQuantityOfRightAnswers() {
        this.QuantityOfRightAnswers++;
    }

    public int getGetQuantityOfWrongAnswers() {
        return QuantityOfWrongAnswers;
    }

    public void incrementQuantityOfWrongAnswers() {
        this.QuantityOfWrongAnswers++;
    }
}

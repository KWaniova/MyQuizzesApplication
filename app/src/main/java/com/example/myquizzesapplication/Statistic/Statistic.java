package com.example.myquizzesapplication.Statistic;

import android.content.Context;

public abstract class Statistic {

    private int quantityOfAnsweredQuestions;
    private int quantityOfAllGames;
    private double percentageOfSuccess;
    private int quantityOfRightAnswers;
    private int quantityOfWrongAnswers;

    public Statistic() {
        this.quantityOfAnsweredQuestions = 0;
        this.quantityOfAllGames = 0;
        this.percentageOfSuccess = 0;
        this.quantityOfRightAnswers = 0;
        this.quantityOfWrongAnswers = 0;
    }

    public int getQuantityOfAnsweredQuestions() {
        return quantityOfAnsweredQuestions;
    }

    public void setQuantityOfAnsweredQuestions(int quantityOfAnsweredQuestions) {
        this.quantityOfAnsweredQuestions = quantityOfAnsweredQuestions;
    }

    public int getQuantityOfAllGames() {
        return quantityOfAllGames;
    }

    public void setQuantityOfAllGames(int quantityOfAllGames) {
        this.quantityOfAllGames = quantityOfAllGames;
    }

    public double getPercentageOfSuccess() {
        if(quantityOfAnsweredQuestions==0)
            return 0;
        else
            return(quantityOfRightAnswers*100)/quantityOfAnsweredQuestions;
    }





    public int getQuantityOfRightAnswers() {
        return quantityOfRightAnswers;
    }

    public void setQuantityOfRightAnswers(int quantityOfRightAnswers) {
        this.quantityOfRightAnswers = quantityOfRightAnswers;
    }

    public int getQuantityOfWrongAnswers() {
        return quantityOfWrongAnswers;
    }

    public void setQuantityOfWrongAnswers(int quantityOfWrongAnswers) {
        this.quantityOfWrongAnswers = quantityOfWrongAnswers;
    }
}

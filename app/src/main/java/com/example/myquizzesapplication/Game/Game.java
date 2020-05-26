package com.example.myquizzesapplication.Game;

import com.example.myquizzesapplication.Helpers.ListRandomizer;
import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.Statistic.Result;

import java.util.ArrayList;
import java.util.List;

// game
public class Game implements GameInterface{

    private List<Question> questions;
    private Result result;

    //number of already answered questions
    private int answeredQuestions = 0;

    public Game() {
        this.questions = new ArrayList<>();
        result = new Result();
    }


    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Result getResult() {
        return result;
    }

    public int getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void incrementAnsweredQuestions() {
        this.answeredQuestions++;
    }


    public void incrementRightAnswers() {
        result.incrementQuantityOfRightAnswers();
    }

    public void incrementWrongAnswers() {
        result.incrementQuantityOfWrongAnswers();
    }

    public void drawQuestions(List<Question> allQuestions,int quantityOfQuestions){
        //drawing random questions from list of all questions
        questions = ListRandomizer.drawRandomList(allQuestions,quantityOfQuestions);
        result.setQuantityOfQuestions(quantityOfQuestions);
    }
}

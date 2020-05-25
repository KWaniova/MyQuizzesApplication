package com.example.myquizzesapplication.Game;

import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.Statistic.Result;

import java.util.List;

// game
public class Game {

    private List<Question> questions;
    private Result result;

    //number of already answered questions
    private int answeredQuestions = 0;

    public Game(List<Question> questions) {
        this.questions = questions;
        result = new Result();
        result.setQuantityOfQuestions(questions.size());
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
}

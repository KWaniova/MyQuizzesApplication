package com.example.myquizzesapplication.Game;

import com.example.myquizzesapplication.Question.Question;

import java.util.List;

public interface GameActivityInterface {
    void startGame();
    List<Question> drawQuestions();
    //during game
    void showNextQuestion();
    void showResultOfQuestion();

    //ate the end of the game
    void showEndResult();
}

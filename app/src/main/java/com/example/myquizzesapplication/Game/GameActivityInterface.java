package com.example.myquizzesapplication.Game;

import com.example.myquizzesapplication.Question.Question;

import java.util.List;

public interface GameActivityInterface {
    List<Question> drawQuestions();
    void showEndResult();
    void startGame();
    void showResultOfQuestion();
}

package com.example.myquizzesapplication.Game;

import com.example.myquizzesapplication.Helpers.ListRandomizer;
import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.Statistic.Result;

import java.util.List;

public interface GameInterface {

    List<Question> getQuestions();

     void setQuestions(List<Question> questions);

     Result getResult();

     int getAnsweredQuestions();

     void incrementAnsweredQuestions();

     void incrementRightAnswers();

     void incrementWrongAnswers();

     void drawQuestions(List<Question> allQuestions,int quantityOfQuestions);
}

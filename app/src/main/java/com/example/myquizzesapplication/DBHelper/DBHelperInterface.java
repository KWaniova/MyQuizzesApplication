package com.example.myquizzesapplication.DBHelper;

import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.QuizFolder.Quiz;

import java.util.List;

public interface DBHelperInterface {
    List<Quiz> getAllQuizzes();

    Quiz getQuiz(int quizPosition);

    void addQuiz();

    void deleteQuiz(int quizPosition);

    void editQuizName(int quizPosition, String newQuizName);

    List<Question> getAllQuizQuestions(int quizID);

    void addQuestion(int quizPosition, Question question);

    void deleteQuestion(int quizPosition, int questionPosition);

    void editQuestionContent(int quizPosition, int questionPosition, String newContent);

    void editQuestionAnswer(int quizPosition, int questionPosition, Boolean newAnswer);

}

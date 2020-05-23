package com.example.myquizzesapplication.DBHelper;

import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.QuizFolder.Quiz;

import java.util.List;

public interface DBHelperInterface {
    List<Quiz> getAllQuizzes();

    boolean addQuiz(String name);

    boolean deleteQuiz(int quizPosition);

    boolean editQuizName(int quizPosition, String newQuizName);

    List<Question> getAllQuizQuestions(int quizID);

    boolean addQuestion(int quizPosition, Question question);

    boolean deleteQuestion(int quizPosition, int questionPosition);

    boolean editQuestionContent(int quizPosition, int questionPosition, String newContent);

    boolean editQuestionAnswer(int quizPosition, int questionPosition, Boolean newAnswer);

}

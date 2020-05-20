package com.example.quizesapplication.QuizFolder;

import com.example.quizesapplication.Question.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int idQuiz;
    private String name;
    private List<Question> questions;

    public Quiz(int idQuiz, String name) {
        this.idQuiz = idQuiz;
        this.name = name;
        this.questions = new ArrayList<>();
    }

    public int getIdQuiz() {
        return idQuiz;
    }
    public int getNumberOfQuestions(){
        return questions.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

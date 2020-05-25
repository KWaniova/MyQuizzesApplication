package com.example.myquizzesapplication.Question;


public class Question extends Object{
    private int quizID;
    private String content;
    private boolean rightAnswer;

    public Question(int quizID, String content, boolean rightAnswer) {
        this.quizID = quizID;
        this.content = content;
        this.rightAnswer = rightAnswer;
    }

    public Question(String content, boolean rightAnswer) {
        this.content = content;
        this.rightAnswer = rightAnswer;
    }

    public int getQuizID() {
        return quizID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
    public void setQuizID(int QuizID){this.quizID = QuizID;};
}

package com.example.myquizzesapplication.Question;


public class Question{
    private int quizID;
    private String content;
    private boolean rightAnswer;

    public Question(int quizID, String content, boolean rightAnswer) {
        this.content = content;
        this.rightAnswer = rightAnswer;
        this.quizID = quizID;
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
}

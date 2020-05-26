package com.example.myquizzesapplication.Question;

public class QuestionFromFileFormat {
    //format pytania pobranego z pliku txt
    private String trueAnswerFormat;
    private String falseAnswerFormat;
    private String separatorFormat;

    public String getTrueAnswerFormat() {
        return trueAnswerFormat;
    }

    public void setTrueAnswerFormat(String trueAnswerFormat) {
        this.trueAnswerFormat = trueAnswerFormat;
    }

    public String getFalseAnswerFormat() {
        return falseAnswerFormat;
    }

    public void setFalseAnswerFormat(String falseAnswerFormat) {
        this.falseAnswerFormat = falseAnswerFormat;
    }

    public String getSeparatorFormat() {
        return separatorFormat;
    }

    public void setSeparatorFormat(String separatorFormat) {
        this.separatorFormat = separatorFormat;
    }
}

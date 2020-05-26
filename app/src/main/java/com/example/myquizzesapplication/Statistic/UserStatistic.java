package com.example.myquizzesapplication.Statistic;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myquizzesapplication.DBHelper.DBHelper;

public class UserStatistic extends Statistic {
    private int quantityOfQuestionsInDB;

    public UserStatistic() {}

    public void setQuantityOfQuestionsInDB(Context context) {
        this.quantityOfQuestionsInDB = DBHelper.getInstance(context).getNumberOfQuestions();

    }

    public int getQuantityOfQuestionsInDB() {
        return quantityOfQuestionsInDB;
    }
}

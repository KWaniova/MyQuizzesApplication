package com.example.myquizzesapplication.User;

import android.content.Context;

import com.example.myquizzesapplication.Statistic.UserStatistic;

import java.io.Serializable;

public class User implements Serializable {
    private String userStatisticPreferencesName;

    public User(String preferencesName) {
        this.userStatisticPreferencesName = preferencesName;
    }

    public String getNick(Context context) {
        return UserStatisticAdapter.GetUserNamePreferences(context);
    }

    public UserStatistic getUserStatistic(Context context){
        return UserStatisticAdapter.getUserStatisticPreferences(context,userStatisticPreferencesName);
    }
}

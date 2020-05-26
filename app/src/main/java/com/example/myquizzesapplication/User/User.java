package com.example.myquizzesapplication.User;

import android.content.Context;

import com.example.myquizzesapplication.Statistic.Result;
import com.example.myquizzesapplication.Statistic.UserStatistic;

import java.io.Serializable;

public class User implements Serializable {
    private String userStatisticPreferencesName;
    private String userPreferencesName;
    private String userNameKey;

    public User(String userPreferencesName) {
        this.userPreferencesName = userPreferencesName;
        this.userStatisticPreferencesName = "userStatistic";
        this.userNameKey = "UserName";
    }

    public String getUserStatisticPreferencesName() {
        return userStatisticPreferencesName;
    }

    public String getUserPreferencesName() {
        return userPreferencesName;
    }

    public String getUserNameKey() {
        return userNameKey;
    }

    public String getNick(Context context) {
        return UserStatisticAdapter.GetUserNamePreferences(userPreferencesName,userNameKey,context);
    }

    public UserStatistic getUserStatistic(Context context){
        return UserStatisticAdapter.getUserStatisticPreferences(context,userStatisticPreferencesName);
    }

    public void updateUserStatistic(Result result, Context context){
        UserStatisticAdapter.SetUserStatisticPreferencesAfterGameOver(result,context,userStatisticPreferencesName);
    }
}

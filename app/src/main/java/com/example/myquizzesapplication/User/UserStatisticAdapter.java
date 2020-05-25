package com.example.myquizzesapplication.User;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myquizzesapplication.Statistic.Result;
import com.example.myquizzesapplication.Statistic.UserStatistic;

public class UserStatisticAdapter {

    final static String getQuantityOfAllGamesKey = "getQuantityOfAllGamesKey";
    final static String getQuantityOfRightAnswersKey = "getQuantityOfRightAnswersKey";
    final static String getQuantityOfWrongAnswersKey = "getQuantityOfWrongAnswersKey";
    final static String getQuantityOfAnsweredQuestionsKey = "getQuantityOfAnsweredQuestionsKey";


    public static String getGetQuantityOfAllGamesKey() {
        return getQuantityOfAllGamesKey;
    }

    public static String getGetQuantityOfRightAnswersKey() {
        return getQuantityOfRightAnswersKey;
    }

    public static String getGetQuantityOfWrongAnswersKey() {
        return getQuantityOfWrongAnswersKey;
    }

    public static String getGetQuantityOfAnsweredQuestionsKey() {
        return getQuantityOfAnsweredQuestionsKey;
    }

    public static void SetUserNamePreferences(String enteredUserName, Context context){
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserName",enteredUserName);
        editor.apply();
    }

    public static String GetUserNamePreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return preferences.getString("UserName","new");
    }

    public static void SetUserStatisticPreferencesAfterGameOver(Result result,Context context,String preferencesName){
        SharedPreferences preferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
        int newQuantityOfAnsweredQuestions = preferences.getInt(getGetQuantityOfAnsweredQuestionsKey(),0) + result.getQuantityOfQuestions();
        int newQuantityOfRightAnswers = preferences.getInt(getGetQuantityOfRightAnswersKey(),0) + result.getGetQuantityOfRightAnswers();
        int newQuantityOfWrongAnswers = preferences.getInt(getGetQuantityOfWrongAnswersKey(),0) + result.getGetQuantityOfWrongAnswers();
        int newQuantityOfGames = preferences.getInt(getGetQuantityOfAllGamesKey(),0) + 1;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(UserStatisticAdapter.getGetQuantityOfAnsweredQuestionsKey(),newQuantityOfAnsweredQuestions);
        editor.putInt(UserStatisticAdapter.getGetQuantityOfRightAnswersKey(),newQuantityOfRightAnswers);
        editor.putInt(UserStatisticAdapter.getGetQuantityOfWrongAnswersKey(),newQuantityOfWrongAnswers);
        editor.putInt(UserStatisticAdapter.getGetQuantityOfAllGamesKey(),newQuantityOfGames);
        editor.apply();
    }

    public static UserStatistic getUserStatisticPreferences(Context context,String preferencesName){

        UserStatistic userStatistic = new UserStatistic(preferencesName);
        SharedPreferences preferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);

        userStatistic.setQuantityOfQuestionsInDB(context);
        userStatistic.setQuantityOfAllGames(preferences.getInt(UserStatisticAdapter.getGetQuantityOfAllGamesKey(),0));
        userStatistic.setQuantityOfRightAnswers(preferences.getInt(UserStatisticAdapter.getGetQuantityOfRightAnswersKey(),0));
        userStatistic.setQuantityOfWrongAnswers(preferences.getInt(UserStatisticAdapter.getGetQuantityOfWrongAnswersKey(),0));
        userStatistic.setQuantityOfAnsweredQuestions(preferences.getInt(UserStatisticAdapter.getGetQuantityOfAnsweredQuestionsKey(),0));
        return  userStatistic;
    }
}

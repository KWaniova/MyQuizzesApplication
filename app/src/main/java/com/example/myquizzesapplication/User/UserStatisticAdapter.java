package com.example.myquizzesapplication.User;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myquizzesapplication.Statistic.Result;
import com.example.myquizzesapplication.Statistic.UserStatistic;

//getting data from shared preferences for that user
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

    public static int getGetQuantityOfAllGames(Context context,String preferencesName) {
        SharedPreferences preferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
        return preferences.getInt(getGetQuantityOfAllGamesKey(),0);
    }

    public static int getGetQuantityOfRightAnswers(Context context,String preferencesName) {
        SharedPreferences preferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
        return preferences.getInt(getGetQuantityOfRightAnswersKey(),0);
    }

    public static int getGetQuantityOfWrongAnswers(Context context,String preferencesName) {
        SharedPreferences preferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
        return preferences.getInt(getGetQuantityOfWrongAnswersKey(),0);
    }

    public static int getGetQuantityOfAnsweredQuestions(Context context,String preferencesName) {
        SharedPreferences preferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
        return preferences.getInt(getGetQuantityOfAnsweredQuestionsKey(),0);
    }

    public static void SetUserNamePreferences(String enteredUserName,String preferencesName,String userNameKey, Context context){
        SharedPreferences preferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(userNameKey,enteredUserName);
        editor.apply();
    }

    public static String GetUserNamePreferences(String preferencesName,String userNameKey,Context context){
        SharedPreferences preferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
        return preferences.getString(userNameKey,"new");
    }

    public static void SetUserStatisticPreferencesAfterGameOver(Result result,Context context,String preferencesName){
        SharedPreferences preferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
        int newQuantityOfAnsweredQuestions = preferences.getInt(getGetQuantityOfAnsweredQuestionsKey(),0) + result.getQuantityOfQuestions();
        int newQuantityOfRightAnswers = preferences.getInt(getGetQuantityOfRightAnswersKey(),0) + result.getGetQuantityOfRightAnswers();
        int newQuantityOfWrongAnswers = preferences.getInt(getGetQuantityOfWrongAnswersKey(),0) + result.getGetQuantityOfWrongAnswers();
        int newQuantityOfGames = preferences.getInt(getGetQuantityOfAllGamesKey(),0) + 1;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getGetQuantityOfAnsweredQuestionsKey(),newQuantityOfAnsweredQuestions);
        editor.putInt(getGetQuantityOfRightAnswersKey(),newQuantityOfRightAnswers);
        editor.putInt(getGetQuantityOfWrongAnswersKey(),newQuantityOfWrongAnswers);
        editor.putInt(getGetQuantityOfAllGamesKey(),newQuantityOfGames);
        editor.apply();
    }

    public static UserStatistic getUserStatisticPreferences(Context context,String preferencesName){

        UserStatistic userStatistic = new UserStatistic();
        SharedPreferences preferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);

        userStatistic.setQuantityOfQuestionsInDB(context);
        userStatistic.setQuantityOfAllGames(preferences.getInt(UserStatisticAdapter.getGetQuantityOfAllGamesKey(),0));
        userStatistic.setQuantityOfRightAnswers(preferences.getInt(UserStatisticAdapter.getGetQuantityOfRightAnswersKey(),0));
        userStatistic.setQuantityOfWrongAnswers(preferences.getInt(UserStatisticAdapter.getGetQuantityOfWrongAnswersKey(),0));
        userStatistic.setQuantityOfAnsweredQuestions(preferences.getInt(UserStatisticAdapter.getGetQuantityOfAnsweredQuestionsKey(),0));
        return  userStatistic;
    }
}

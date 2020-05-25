package com.example.myquizzesapplication.Game;

import android.widget.TextView;

public class ShowResultQuestion {

    public static void showCorrectResult(TextView resultTextView){
        resultTextView.setText("Correct!");
    }

    public static void showWrongResult(TextView resultTextView){
        resultTextView.setText("Wrong!");
    }
}

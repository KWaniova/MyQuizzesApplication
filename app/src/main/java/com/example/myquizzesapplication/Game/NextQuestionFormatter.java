package com.example.myquizzesapplication.Game;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NextQuestionFormatter {
    //dla przebiegu gry - ustawia wygląd dla nowego pytania

    public static void nextTrueFalseQuestion(View trueView, View falseView, TextView questionContentView, TextView resultView,String questionContent){
        questionContentView.setText(questionContent);
        trueView.setBackgroundColor(Color.WHITE);
        falseView.setBackgroundColor(Color.WHITE);
        resultView.setText("");
    }

    public static void newQuestionOKButton(Button button){
        button.setText("OK");
        button.setEnabled(false); //because no answer is selected at start
    }
    //we can add in the future more forms of questions
}

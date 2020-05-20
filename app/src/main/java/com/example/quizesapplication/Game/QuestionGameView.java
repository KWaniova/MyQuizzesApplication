package com.example.quizesapplication.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizesapplication.R;

public class QuestionGameView extends AppCompatActivity {

    Button trueButton, falseButton;
    TextView backTextView;
    TextView questionContent, questionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_game_view);

        trueButton = (Button)findViewById(R.id.true_button);
        falseButton = (Button)findViewById(R.id.false_button);
        backTextView = (TextView)findViewById(R.id.back_button);
        questionContent = (TextView)findViewById(R.id.question_content);
        questionNumber = (TextView)findViewById(R.id.questionX_text_view);



    }
}

package com.example.myquizzesapplication.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.R;

public class ResultViewActivity extends AppCompatActivity {

    Intent intent;
    Button OKResultButton;
    TextView resultNumberOfQuestions, resultNumberOgRight, resultNumberOgWrong;
    int quantityOfQuestions, rightAnswers, wrongAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);
        OKResultButton = (Button)findViewById(R.id.result_ok_button);
        resultNumberOfQuestions = (TextView)findViewById(R.id.number_of_questionsX);
        resultNumberOgRight = (TextView)findViewById(R.id.number_of_right_questionsX);
        resultNumberOgWrong = (TextView)findViewById(R.id.number_of_wrong_questionsX);
        getDataFromIntent();

        resultNumberOfQuestions.setText("Number of questions: " + quantityOfQuestions);
        resultNumberOgRight.setText("Right answers " + rightAnswers);
        resultNumberOgWrong.setText("Wrong answers " + wrongAnswers);

        OKResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getDataFromIntent(){
        intent = getIntent();
        rightAnswers = intent.getIntExtra("RightAnswers",-1);
        wrongAnswers = intent.getIntExtra("WrongAnswers",-1);
        quantityOfQuestions = intent.getIntExtra("numberOfQuestions",-1);
    }

}

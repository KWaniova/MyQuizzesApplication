package com.example.myquizzesapplication.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.R;

import java.util.ArrayList;

public class GameStartActivity extends AppCompatActivity {

    Button STARTButton, CANCELButton;
    EditText enterNumberOfQuestions;
    ArrayList<Integer> quizzesSelected;
    Intent intent;
    int numberOfQuestions;
    int actualNumberOfQuestions;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        STARTButton = (Button)findViewById(R.id.ok_button_game_start);
        CANCELButton = (Button)findViewById(R.id.cancel_button_game_start);
        enterNumberOfQuestions = (EditText)findViewById(R.id.enter_number_of_questions);
        setEnterNumberOfQuestionsOnClick();

        CANCELButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        STARTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        numberOfQuestions = 0;
        setQuizList();
    }

    public void setQuizList(){
        dbHelper = DBHelper.getInstance(this);
        intent = getIntent();
        quizzesSelected = (ArrayList<Integer>) intent.getSerializableExtra("SelectedItemsList");

        for(int i = 0; i < quizzesSelected.size(); i++){
            numberOfQuestions += dbHelper.getQuizzes().get(quizzesSelected.get(i)).getQuestions().size();
        }

        enterNumberOfQuestions.setHint("Enter number of questions. Max " + numberOfQuestions);
    }

    public void setEnterNumberOfQuestionsOnClick(){
        enterNumberOfQuestions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                STARTButton.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!enterNumberOfQuestions.getText().toString().equals("")){
                    actualNumberOfQuestions = Integer.parseInt(enterNumberOfQuestions.getText().toString());
                    if(actualNumberOfQuestions>numberOfQuestions){
                        STARTButton.setEnabled(false);
                    }else
                        STARTButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

package com.example.myquizzesapplication.AllMainActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myquizzesapplication.Interfaces.ActivityInterfaceWithButtons;
import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.Helpers.KeyboardSettingsClass;
import com.example.myquizzesapplication.R;

import java.util.ArrayList;

public class GameStartActivity extends AppCompatActivity implements ActivityInterfaceWithButtons {
    //odpowiada za wybranie ilości pytań do nowej gry

    Button STARTButton, CANCELButton;
    EditText enterNumberOfQuestions;
    ArrayList<Integer> quizzesSelected;
    Intent intent;
    int numberOfQuestions;//quantity of all questions from selected quizzes
    int actualNumberOfQuestions;//number of questions that user enters
    DBHelper dbHelper = DBHelper.getInstance(this);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        setView();
        buttonsSettings();

        setEnterNumberOfQuestionsOnClick();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        numberOfQuestions = 0;
        setView();
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
                    if(actualNumberOfQuestions == 0 || actualNumberOfQuestions>numberOfQuestions){
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

    @Override
    public void buttonsSettings() {
            CANCELButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            STARTButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GameStartActivity.this, GameActivity.class);
                    intent.putExtra("SelectedItemsList",quizzesSelected);
                    intent.putExtra("numberOfQuestions",actualNumberOfQuestions);
                    startActivity(intent);
                    finish();
                }
            });
    }

    @Override
    public void getDataFromIntent() {
        intent = getIntent();

        quizzesSelected = (ArrayList<Integer>) intent.getSerializableExtra("SelectedItemsList");

        for(int i = 0; i < quizzesSelected.size(); i++){
            numberOfQuestions += dbHelper.getQuizzes().get(quizzesSelected.get(i)).getQuestions().size();
        }
    }

    @Override
    public void setView() {
        getDataFromIntent();
        STARTButton = (Button)findViewById(R.id.ok_button_game_start);
        CANCELButton = (Button)findViewById(R.id.cancel_button_game_start);
        enterNumberOfQuestions = (EditText)findViewById(R.id.enter_number_of_questions);
        enterNumberOfQuestions.setHint("Enter number of questions. Max " + numberOfQuestions);
    }

    public void onLinearLayoutClick(View view) {
        KeyboardSettingsClass.closeKeyboard(this);
    }
}

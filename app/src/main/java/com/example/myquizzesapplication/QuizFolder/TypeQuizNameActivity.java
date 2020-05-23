package com.example.myquizzesapplication.QuizFolder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.Interfaces.ActivityInterfaceWithButtons;
import com.example.myquizzesapplication.KeyboardSettingsClass;
import com.example.myquizzesapplication.R;

import org.w3c.dom.Text;

public class TypeQuizNameActivity extends AppCompatActivity implements ActivityInterfaceWithButtons {

    EditText enterQuizName;
    Button addButton;
    Button cancelButton;
    String quizName;

    @Override
    public void getDataFromIntent(){
        int quizPosition = getIntent().getIntExtra("QuizPosition",-1);

        if(quizPosition>-1){
            TextView quizName = findViewById(R.id.type_quiz_name_text_view);
            quizName.setText(DBHelper.getInstance(this).getQuizzes().get(quizPosition).getName());
        }
    }

    @Override
    public void setView() {
        enterQuizName = (EditText)findViewById(R.id.enter_quiz_name);
        addButton = (Button)findViewById(R.id.add_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_quiz_name);
        getDataFromIntent();
        setView();
        buttonsSettings();

        enterQuizName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                addButton.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                quizName = enterQuizName.getText().toString();
                addButton.setEnabled(!quizName.isEmpty());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onLinearLayoutClick(View view) {
        KeyboardSettingsClass.closeKeyboard(this);
    }

    @Override
    public void buttonsSettings() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("QuizName",quizName);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

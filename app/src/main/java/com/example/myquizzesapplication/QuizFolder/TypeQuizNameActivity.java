package com.example.myquizzesapplication.QuizFolder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myquizzesapplication.R;

public class TypeQuizNameActivity extends AppCompatActivity {

    EditText enterQuizName;
    Button addButton;
    Button cancelButton;
    String quizName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_quiz_name);

        enterQuizName = (EditText)findViewById(R.id.enter_quiz_name);
        addButton = (Button)findViewById(R.id.add_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);

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

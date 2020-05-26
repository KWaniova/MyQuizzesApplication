package com.example.myquizzesapplication.Question;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.Interfaces.ActivityInterfaceWithButtons;
import com.example.myquizzesapplication.Helpers.KeyboardSettingsClass;
import com.example.myquizzesapplication.R;

public class AddQuestionActivity extends AppCompatActivity implements ActivityInterfaceWithButtons {

    RadioButton true_RB, false_RB;
    Button ADDButton, CancelButton;
    EditText questionContent;
    Intent intent;
    private int quizPosition;
    String newQuestionContent = "";
    boolean questionAnswer = true;
    DBHelper dbHelper = DBHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        setView();
        getDataFromIntent();
        buttonsSettings();

        questionContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newQuestionContent = questionContent.getText().toString();
                ADDButton.setEnabled(!newQuestionContent.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_button_true:
                if(checked){
                    str = "True Selected";
                    true_RB.setChecked(true);
                    false_RB.setChecked(false);
                    questionAnswer = true;
                }
                break;
            case R.id.radio_button_false:
                if(checked){
                    str = "False Selected";
                    true_RB.setChecked(false);
                    false_RB.setChecked(true);
                    questionAnswer = false;
                }
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void buttonsSettings() {
        true_RB.setChecked(true);
        false_RB.setChecked(false);

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ADDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question q = new Question(newQuestionContent,questionAnswer);
                dbHelper.addQuestion(quizPosition,q);
                finish();
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        intent = getIntent();
        quizPosition = intent.getIntExtra("quizPosition",-1);
    }

    @Override
    public void setView() {
        true_RB = (RadioButton)findViewById(R.id.radio_button_true);
        false_RB = (RadioButton)findViewById(R.id.radio_button_false);
        ADDButton =(Button)findViewById(R.id.add_new_question_button);
        questionContent = (EditText)findViewById(R.id.question_content_edit_text_view);
        CancelButton = (Button)findViewById(R.id.cancel_new_question_button);
        ADDButton.setEnabled(false);
    }

    public void onLinearLayoutClick(View view) {
        KeyboardSettingsClass.closeKeyboard(this);
    }
}

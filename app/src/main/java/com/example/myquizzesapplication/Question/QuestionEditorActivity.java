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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.R;

public class QuestionEditorActivity extends AppCompatActivity {

    RadioButton true_RB, false_RB;
    Button OKButton;
    EditText questionContent;
    RadioGroup radioGroup;
    Intent intent;
    private int quizPosition;
    private int questionPosition;
    String newQuestionContent;
    boolean newRightAnswer;
    DBHelper dbHelper = DBHelper.getInstance(this);

    public void setView(){
        true_RB = (RadioButton)findViewById(R.id.radio_button_true);
        false_RB = (RadioButton)findViewById(R.id.radio_button_false);
        OKButton =(Button)findViewById(R.id.ok_button_edit_question);
        questionContent = (EditText)findViewById(R.id.question_content_edit_text_view);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);

        intent = getIntent();
        quizPosition = intent.getIntExtra("quizPosition",-1);
        questionPosition = intent.getIntExtra("questionPosition",-1);
        newQuestionContent = dbHelper.getQuizzes().get(quizPosition).getQuestions().get(questionPosition).getContent();
        questionContent.setText(newQuestionContent);
        setRadioChecked();
    }
    public void setRadioChecked(){
        if(dbHelper.getQuizzes().get(quizPosition).getQuestions().get(questionPosition).isRightAnswer()==true){
            true_RB.setChecked(true);
            false_RB.setChecked(false);
            newRightAnswer = true;
        }else{
            false_RB.setChecked(true);
            true_RB.setChecked(false);
            newRightAnswer = false;
        }
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
                    newRightAnswer =true;
                }
                break;
            case R.id.radio_button_false:
                if(checked){
                    str = "False Selected";
                    false_RB.setChecked(true);
                    true_RB.setChecked(false);
                    newRightAnswer = false;
                }
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_editor);
        setView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("new right answer " + newRightAnswer);
                dbHelper.editQuestion(quizPosition,questionPosition,newQuestionContent,newRightAnswer);
                finish();
            }
        });

        questionContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newQuestionContent = questionContent.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

package com.example.quizesapplication.Question;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.quizesapplication.DBHelper.DBHelper;
import com.example.quizesapplication.R;

public class QuestionEditorActivity extends AppCompatActivity {

    RadioButton true_RB, false_RB;
    Button OKButton;
    EditText questionContent;
    RadioGroup radioGroup;
    Intent intent;
    private int quizPosition;
    private int questionPosition;
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
        questionContent.setText(dbHelper.getQuizzes().get(quizPosition).getQuestions().get(questionPosition).getContent());
        setRadioChecked();
    }
    public void setRadioChecked(){
        if(dbHelper.getQuizzes().get(quizPosition).getQuestions().get(questionPosition).isRightAnswer()==true){
            true_RB.setChecked(true);
            false_RB.setChecked(false);
        }else{
            false_RB.setChecked(true);
            true_RB.setChecked(false);
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_button_true:
                if(checked)
                    str = "True Selected";
                break;
            case R.id.radio_button_false:
                if(checked)
                    str = "False Selected";
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_editor);
        setView();

    }
}

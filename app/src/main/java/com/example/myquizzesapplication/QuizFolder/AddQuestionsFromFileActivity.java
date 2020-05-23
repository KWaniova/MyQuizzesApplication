package com.example.myquizzesapplication.QuizFolder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.FileHelper.FileHelper;
import com.example.myquizzesapplication.Interfaces.ActivityInterfaceWithButtons;
import com.example.myquizzesapplication.KeyboardSettingsClass;
import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.Question.QuestionFromFileFormat;
import com.example.myquizzesapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionsFromFileActivity extends AppCompatActivity implements ActivityInterfaceWithButtons {

    EditText TRUEAnswerFormat, FALSEAnswerFormat;
    RadioButton TabRadioButton, NewLineRadioButton;
    Button ADDButton, CANCELButton;
    Uri uri;
    QuestionFromFileFormat questionFromFileFormat;
    DBHelper dbHelper = DBHelper.getInstance(this);
    int quizPosition;
    ArrayList<Question> questions = new ArrayList<>();


    @Override
    public void setView(){
        TRUEAnswerFormat = findViewById(R.id.enter_true_answer_format);
        FALSEAnswerFormat = findViewById(R.id.enter_false_answer_format);
        TabRadioButton = findViewById(R.id.tabulator_radio_button);
        NewLineRadioButton = findViewById(R.id.newLine_radio_button);
        ADDButton = findViewById(R.id.add_button_from_text_file);
        CANCELButton = findViewById(R.id.cancel_button_from_text_file);
        questionFromFileFormat = new QuestionFromFileFormat();
        TabRadioButton.setChecked(true);
        ADDButton.setEnabled(false);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions_from_file);
        setView();
        buttonsSettings();
        getDataFromIntent();

        TRUEAnswerFormat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                questionFromFileFormat.setTrueAnswerFormat(TRUEAnswerFormat.getText().toString().toLowerCase());
                if(questionFromFileFormat.getTrueAnswerFormat().equals("") && questionFromFileFormat.getFalseAnswerFormat().equals("")){
                    ADDButton.setEnabled(false);
                }else
                    ADDButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        FALSEAnswerFormat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                questionFromFileFormat.setFalseAnswerFormat(FALSEAnswerFormat.getText().toString().toLowerCase());
                if(questionFromFileFormat.getTrueAnswerFormat().equals("") && questionFromFileFormat.getFalseAnswerFormat().equals("")){
                    ADDButton.setEnabled(false);
                }else
                    ADDButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void getDataFromIntent(){
        uri = Uri.parse(getIntent().getExtras().getString("UriData"));
        quizPosition = getIntent().getIntExtra("QuizPosition",-1);
    }

    public void onLinearLayoutClick(View view) {
        KeyboardSettingsClass.closeKeyboard(this);
    }

    public void radioButtonOnClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tabulator_radio_button:
                if(checked){
                    str = "Tabulator selected";
                    TabRadioButton.setChecked(true);
                    NewLineRadioButton.setChecked(false);
                    questionFromFileFormat.setSeparatorFormat("TAB");
                }
                break;
            case R.id.newLine_radio_button:
                if(checked){
                    str = "New line selected";
                    TabRadioButton.setChecked(false);
                    NewLineRadioButton.setChecked(true);
                    questionFromFileFormat.setSeparatorFormat("NEWLINE");
                }
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void addDataToDatabase(){
        if(getDataFromFile() == true){
            for(Question q: questions){
                dbHelper.addQuestion(quizPosition,q);
            }
        }else
            Toast.makeText(this.getApplicationContext(),"You entered wrong format of answer. Try again.",Toast.LENGTH_SHORT).show();

        finish();
    }

    public boolean getDataFromFile(){
        String textFromFile = FileHelper.readTextFile(uri,this);
        String[] separatedTextFromFile = textFromFile.split("\t");
        boolean rightAnswer;
        for(int i = 0 ;i<separatedTextFromFile.length;i+=2){

            if(!(separatedTextFromFile[i+1].equals(questionFromFileFormat.getTrueAnswerFormat())) && !(separatedTextFromFile[i+1].equals(questionFromFileFormat.getFalseAnswerFormat()))){
                return false;
            }

            rightAnswer = (separatedTextFromFile[i+1].toLowerCase().equals(questionFromFileFormat.getTrueAnswerFormat()) ? true:false);

            Question q = new Question(separatedTextFromFile[i],rightAnswer);
            questions.add(q);
        }
        return true;
    }

    @Override
    public void buttonsSettings() {
        CANCELButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ADDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set questions from file in database
                addDataToDatabase();
            }
        });
    }
}

package com.example.myquizzesapplication.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myquizzesapplication.Interfaces.ActivityInterfaceWithButtons;
import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.QuizFolder.Quiz;
import com.example.myquizzesapplication.R;
import com.example.myquizzesapplication.Result;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity implements ActivityInterfaceWithButtons, GameActivityInterface {

    private int quantityOfQuestions; //how many questions will be in the game

    List<Quiz> quizzes = new ArrayList<>(); // quizzes list to game
    ArrayList<Question> questions;
    Result result = new Result();

    Intent intent;
    DBHelper dbHelper = DBHelper.getInstance(this);
    TextView questionContent, TRUEAnswer, FALSEAnswer,resultTextView;
    Button OK_NEXTButton;

    //Caunting variables to game
    int answeredQuestions = 0;
    int rightAnswers=0, wrongAnswers=0;

    boolean TRUEselected = false, FALSEselected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getDataFromIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setView();
        buttonsSettings();

        //drawQuestions for the game
        drawQuestions();
        //start game
        startGame();
    }

    @Override
    public void getDataFromIntent(){
        intent = getIntent();
        //list of selected quizzes
        ArrayList<Integer> quizzesSelected = (ArrayList<Integer>) intent.getSerializableExtra("SelectedItemsList");
        for(int i=0;i<quizzesSelected.size();i++){
            quizzes.add(dbHelper.getQuizzes().get(quizzesSelected.get(i)));
        }

        quantityOfQuestions = intent.getIntExtra("numberOfQuestions",-1);
        if(quantityOfQuestions == -1) finish();

    }
    @Override
    public void setView(){
        questionContent = (TextView)findViewById(R.id.game_question_content);
        TRUEAnswer = (TextView)findViewById(R.id.game_true_answer);
        FALSEAnswer = (TextView)findViewById(R.id.game_false_answer);
        OK_NEXTButton = (Button) findViewById(R.id.game_ok_next_button);
        OK_NEXTButton.setText("OK");
        resultTextView = (TextView)findViewById(R.id.result_text_view);
    }

    @Override
    public void buttonsSettings(){
        OK_NEXTButton.setEnabled(false);

        TRUEAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TRUEselected = !TRUEselected;
                TRUEAnswer.setBackgroundColor(TRUEselected ? Color.CYAN : Color.WHITE);
                if(FALSEselected==true && TRUEselected==true){
                    FALSEAnswer.setBackgroundColor(Color.WHITE);
                    FALSEselected = !FALSEselected;
                }
                if(TRUEselected == false && FALSEselected==false && OK_NEXTButton.getText().equals("OK")){
                    OK_NEXTButton.setEnabled(false);
                }else{
                    OK_NEXTButton.setEnabled(true);
                }
            }
        });

        FALSEAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FALSEselected = !FALSEselected;
                FALSEAnswer.setBackgroundColor(FALSEselected ? Color.CYAN : Color.WHITE);
                if(TRUEselected==true && FALSEselected==true){
                    TRUEAnswer.setBackgroundColor(Color.WHITE);
                    TRUEselected = !TRUEselected;
                }
                if(TRUEselected == false && FALSEselected==false && OK_NEXTButton.getText().equals("OK")){
                    OK_NEXTButton.setEnabled(false);
                }else{
                    OK_NEXTButton.setEnabled(true);
                }
            }
        });

        OK_NEXTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OK_NEXTButton.getText().equals("OK")){
                    showResultOfQuestion();
                    OK_NEXTButton.setText("NEXT");
                    if(answeredQuestions == quantityOfQuestions){
                        OK_NEXTButton.setText("FINISH");
                    }
                }
                else if(OK_NEXTButton.getText().equals("NEXT")){
                    nextQuestion();
                    OK_NEXTButton.setText("OK");
                    OK_NEXTButton.setEnabled(false);

                }else {
                    showEndResult();
                }
            }
        });

    }

    @Override
    public void showResultOfQuestion(){
        boolean answerSelected = (TRUEselected ? true:false);
        if(questions.get(answeredQuestions).isRightAnswer()==answerSelected){
            resultTextView.setText("Correct!");
            rightAnswers++;
        }else{
            resultTextView.setText("Wrong!");
            wrongAnswers++;
        }
        answeredQuestions++;
    }
    public void nextQuestion(){
        questionContent.setText(questions.get(answeredQuestions).getContent());
        TRUEAnswer.setBackgroundColor(Color.WHITE);
        TRUEselected = false;
        FALSEAnswer.setBackgroundColor(Color.WHITE);
        FALSEselected = false;
        resultTextView.setText("");
    }

    public void showEndResult(){
        result.setGetQuantityOfRightAnswers(rightAnswers);
        result.setGetQuantityOfWrongAnswers(wrongAnswers);
        intent = new Intent(GameActivity.this,ResultViewActivity.class);
        intent.putExtra("RightAnswers",rightAnswers);
        intent.putExtra("WrongAnswers",wrongAnswers);
        intent.putExtra("numberOfQuestions",quantityOfQuestions);
        startActivity(intent);
        finish();
    }

    @Override
    public void startGame() {
        result.setQuantityOfQuestions(quantityOfQuestions);
        questions = (ArrayList<Question>) drawQuestions();// questions list to game
        nextQuestion();
    }

    @Override
    public List<Question> drawQuestions(){
        ArrayList<Question> questions = new ArrayList<>();
        Set<Question> set = new HashSet<>();
        for(int i =0;i<quizzes.size();i++){
            set.addAll(quizzes.get(i).getQuestions());
        }
        ArrayList<Question> allQuestions = new ArrayList<>(set);
        for(int i = 0;i<allQuestions.size();i++){
            System.out.println(allQuestions.get(i).getContent());
        }

        Random random = new Random();
        int randomValue;

        for(int i =quantityOfQuestions-1;i>=0;i--){
            randomValue = random.nextInt(allQuestions.size());
            System.out.println(randomValue);
            questions.add(allQuestions.get(randomValue));
            allQuestions.remove(randomValue);
        }

        for(int i=0;i<questions.size();i++)
            System.out.println(questions.get(i).getContent());

        return questions;
    }
}

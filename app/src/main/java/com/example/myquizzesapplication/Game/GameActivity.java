package com.example.myquizzesapplication.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myquizzesapplication.Helpers.ListRandomizer;
import com.example.myquizzesapplication.Interfaces.ActivityInterfaceWithButtons;
import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.R;
import com.example.myquizzesapplication.User.User;
import com.example.myquizzesapplication.User.UserStatisticAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameActivity extends AppCompatActivity implements ActivityInterfaceWithButtons,GameActivityInterface {
    //przebieg gry

    //data from intent
    private int quantityOfQuestions; //how many questions will be in the game
    List<Question> allQuestions; // quizzes list to game
    User user;

    Intent intent;
    DBHelper dbHelper = DBHelper.getInstance(this);
    TextView questionContent, TRUEAnswer, FALSEAnswer,resultTextView;
    Button OK_NEXTButton;
    Game game;



    boolean TRUEselected = false, FALSEselected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //settings
        getDataFromIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setView();
        buttonsSettings();
        //start game
        startGame();
    }

    @Override
    public void getDataFromIntent(){
        intent = getIntent();
        //list of selected quizzes
        ArrayList<Integer> quizzesSelected = (ArrayList<Integer>) intent.getSerializableExtra("SelectedItemsList");

        Set<Question> set = new HashSet<>();//to get all questions from selected quizzes
        for(int i=0;i<quizzesSelected.size();i++){
            set.addAll(dbHelper.getQuizzes().get(quizzesSelected.get(i)).getQuestions());
        }

        allQuestions = new ArrayList<>(set);
        user = new User("user");
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
                //change background color
                TRUEAnswer.setBackgroundColor(TRUEselected ? Color.CYAN : Color.WHITE);

                //if true is selected false can't be selected too
                //user can chose just one answer
                if(TRUEselected == true){
                    FALSEAnswer.setBackgroundColor(Color.WHITE);
                    FALSEselected = false;
                }
                OK_NEXTButtonEnabled();
            }
        });

        FALSEAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FALSEselected = !FALSEselected;
                FALSEAnswer.setBackgroundColor(FALSEselected ? Color.CYAN : Color.WHITE);

                //if false is selected true can't be selected too
                //user can chose just one answer
                if(FALSEselected == true){
                    TRUEAnswer.setBackgroundColor(Color.WHITE);
                    TRUEselected = false;
                }
                OK_NEXTButtonEnabled();
            }
        });

        OK_NEXTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OK_NEXTButton.getText().equals("OK")){
                    //after OK is pressed - show result
                    showResultOfQuestion();
                    OK_NEXTButton.setText("NEXT");
                    if(game.getAnsweredQuestions() == game.getQuestions().size()){
                        OK_NEXTButton.setText("FINISH");
                    }
                }
                else if(OK_NEXTButton.getText().equals("NEXT")){
                    showNextQuestion();
                }else{//text FINISH
                    user.updateUserStatistic(game.getResult(),getApplicationContext());
                    showEndResult();
                }
            }
        });

    }

    //when no answer is selected user can't press ok button
    private void OK_NEXTButtonEnabled(){
        if(TRUEselected == false && FALSEselected==false && OK_NEXTButton.getText().equals("OK")){
            OK_NEXTButton.setEnabled(false);
        }else{
            OK_NEXTButton.setEnabled(true);
        }
    }

    @Override
    public void startGame(){
        game = new Game(drawQuestions());
        showNextQuestion();
    }

    //clears activity view
    //after that user chooses true or false answer and presses ok button
    @Override
    public void showNextQuestion(){
        //if I want to change format of new question view I go to NextQuestionFormatter
        NextQuestionFormatter.nextTrueFalseQuestion(TRUEAnswer,FALSEAnswer,
                questionContent,resultTextView,
                game.getQuestions().get(game.getAnsweredQuestions()).getContent());
        NextQuestionFormatter.newQuestionOKButton(OK_NEXTButton);
        TRUEselected = false;
        FALSEselected = false;
    }

    @Override
    public void showResultOfQuestion(){
        boolean answerSelected = (TRUEselected ? true:false);

        if(game.getQuestions().get(game.getAnsweredQuestions()).isRightAnswer()==answerSelected){
            ShowResultQuestion.showCorrectResult(resultTextView);
            game.incrementRightAnswers(); //increment right answers
        }else{
            ShowResultQuestion.showWrongResult(resultTextView);
            game.incrementWrongAnswers(); //increment wrong answers
        }
        game.incrementAnsweredQuestions(); // increment answered questions
    }

    @Override
    public List<Question> drawQuestions(){
        List<Question> questions;
        //drawing random questions from list of all questions
        questions = ListRandomizer.drawRandomList(allQuestions,quantityOfQuestions);
        return questions;
    }

    @Override
    public void showEndResult(){
        //Go to another activity at the end of the game
        intent = new Intent(GameActivity.this,ResultViewActivity.class);
        intent.putExtra("RightAnswers",game.getResult().getGetQuantityOfRightAnswers());
        intent.putExtra("WrongAnswers",game.getResult().getGetQuantityOfWrongAnswers());
        intent.putExtra("numberOfQuestions",quantityOfQuestions);
        startActivity(intent);
        finish();
    }
}

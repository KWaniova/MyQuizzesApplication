package com.example.myquizzesapplication.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.QuizFolder.Quiz;
import com.example.myquizzesapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

    private int quantityOfQuestions;
    List<Quiz> quizzes = new ArrayList<>();

    Intent intent;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setFromIntent();
        drawQuestions();
    }

    public void setFromIntent(){
        dbHelper = DBHelper.getInstance(this);
        intent = getIntent();
        ArrayList<Integer> quizzesSelected = (ArrayList<Integer>) intent.getSerializableExtra("SelectedItemsList");
        for(int i=0;i<quizzesSelected.size();i++){
            quizzes.add(dbHelper.getQuizzes().get(quizzesSelected.get(i)));
        }
        quantityOfQuestions = intent.getIntExtra("numberOfQuestions",-1);
        if(quantityOfQuestions == -1) finish();

    }

    public boolean startGame(){

        return true;
    }

    public void stopGame(){

    }
    public void showResult(){

    }
    private List<Question> drawQuestions(){
        Set<Question> set = new HashSet<>();
        for(int i =0;i<quizzes.size();i++){
            set.addAll(quizzes.get(i).getQuestions());
        }
        ArrayList<Question> allQuestions = new ArrayList<>(set);
        for(int i = 0;i<allQuestions.size();i++){
            System.out.println(allQuestions.get(i).getContent());
        }

        ArrayList<Question> questions = new ArrayList<>();
        Random random = new Random();
        int randomValue;

        for(int i =quantityOfQuestions-1;i>=0;i--){
            randomValue = random.nextInt(i+1);
            System.out.println(randomValue);
            questions.add(allQuestions.get(randomValue));
            allQuestions.remove(randomValue);
        }

        for(int i=0;i<questions.size();i++)
            System.out.println(questions.get(i).getContent());
        return questions;
    }

    public void showEndResult(){

    }

}

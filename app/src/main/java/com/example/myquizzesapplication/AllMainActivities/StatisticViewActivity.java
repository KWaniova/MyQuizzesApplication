package com.example.myquizzesapplication.AllMainActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myquizzesapplication.Interfaces.ActivityIntefaceFunctions;
import com.example.myquizzesapplication.R;
import com.example.myquizzesapplication.Statistic.UserStatistic;
import com.example.myquizzesapplication.User.User;

public class StatisticViewActivity extends AppCompatActivity implements ActivityIntefaceFunctions {

    User user;
    TextView quantityOfAllQuestionsInDB,
            quantityOfAllAnswers,quantityOfAllRightAnswers,
            quantityOfAllWrongAnswers,quantityOfAllGames,
            percentageOfSuccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDataFromIntent();
        setView();
    }

    @Override
    public void getDataFromIntent() {
        Intent intent = getIntent();
        user =(User) intent.getSerializableExtra("user");

    }
    @Override
    public void setView(){
        quantityOfAllAnswers = findViewById(R.id.quantity_of_all_answered_questions);
        quantityOfAllGames = findViewById(R.id.quantity_of_all_games);
        quantityOfAllQuestionsInDB = findViewById(R.id.quantity_of_all_questions);
        quantityOfAllRightAnswers = findViewById(R.id.quantity_of_all_right_answers);
        quantityOfAllWrongAnswers = findViewById(R.id.quantity_of_all_wrong_answers);
        percentageOfSuccess = findViewById(R.id.percentage);

        UserStatistic userStatistic = user.getUserStatistic(getApplicationContext());

        quantityOfAllGames.setText("Quantity of all games: " + Integer.toString(userStatistic.getQuantityOfAllGames()));
        quantityOfAllAnswers.setText("Quantity of all answers: " +Integer.toString(userStatistic.getQuantityOfAnsweredQuestions()));
        quantityOfAllWrongAnswers.setText("Quantity of all wrong answers: " +Integer.toString(userStatistic.getQuantityOfWrongAnswers()));
        quantityOfAllRightAnswers.setText("Quantity of all right answers: "+Integer.toString(userStatistic.getQuantityOfRightAnswers()));
        quantityOfAllQuestionsInDB.setText("Quantity of all questions: " +Integer.toString(userStatistic.getQuantityOfQuestionsInDB()));
        percentageOfSuccess.setText("Success percentage: "+Double.toString(userStatistic.getPercentageOfSuccess()));
    }

}

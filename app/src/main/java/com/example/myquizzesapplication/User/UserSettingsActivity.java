package com.example.myquizzesapplication.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myquizzesapplication.Interfaces.ActivityInterfaceWithButtons;
import com.example.myquizzesapplication.R;
import com.example.myquizzesapplication.Statistic.StatisticViewActivity;
import com.example.myquizzesapplication.Statistic.UserStatistic;

public class UserSettingsActivity extends AppCompatActivity implements ActivityInterfaceWithButtons{

    User user;
    TextView userName;
    CardView showUserStatistic, editUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        getDataFromIntent();
        setView();
        buttonsSettings();
    }

    public void buttonsSettings() {

        showUserStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSettingsActivity.this, StatisticViewActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        editUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSettingsActivity.this,EnterUserNameActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        Intent intent = getIntent();
        user =(User) intent.getSerializableExtra("User");
    }

    public void setView() {
        userName = findViewById(R.id.user_name_text_view);
        showUserStatistic = findViewById(R.id.show_user_statistic_card_view);
        editUserName = findViewById(R.id.edit_user_name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userName.setText(user.getNick(getApplicationContext()));
    }
}

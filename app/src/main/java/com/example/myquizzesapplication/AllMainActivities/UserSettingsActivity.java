package com.example.myquizzesapplication.AllMainActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myquizzesapplication.Interfaces.ActivityInterfaceWithButtons;
import com.example.myquizzesapplication.R;
import com.example.myquizzesapplication.User.EnterUserNameActivity;
import com.example.myquizzesapplication.User.User;

public class UserSettingsActivity extends AppCompatActivity implements ActivityInterfaceWithButtons{

    User user;
    TextView userName;
    CardView showUserStatistic, editUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                Intent intent = new Intent(UserSettingsActivity.this, EnterUserNameActivity.class);
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
        user = new User("user");
        userName.setText(user.getNick(getApplicationContext()));
    }
}

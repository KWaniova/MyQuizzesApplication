package com.example.myquizzesapplication.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.myquizzesapplication.KeyboardSettingsClass;
import com.example.myquizzesapplication.R;

public class EnterUserNameActivity extends AppCompatActivity {

    EditText userName;
    String enteredUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_user_name);

        userName = findViewById(R.id.user_name_on_start);

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
                enteredUserName = preferences.getString("UserName","new");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enteredUserName = userName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onOKClick(View view) {
        //I save in shared preferences user name which is not empty string.
        UserStatisticAdapter.SetUserNamePreferences(enteredUserName,getApplicationContext());
        finish();
    }

    public void onLinearLayoutClick(View view) {
        KeyboardSettingsClass.closeKeyboard(this);
    }
}


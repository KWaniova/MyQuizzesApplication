package com.example.myquizzesapplication.MainPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.User.EnterUserNameActivity;
import com.example.myquizzesapplication.Game.SelectionQuizzesToGameActivity;
import com.example.myquizzesapplication.QuizFolder.TypeQuizNameActivity;
import com.example.myquizzesapplication.R;
import com.example.myquizzesapplication.User.User;
import com.example.myquizzesapplication.User.UserSettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private User user;
    private RecyclerView recycler_quiz;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private MainPageRecyclerViewAdapter myAdapter;
    Button start_game_button;

    public SQLiteDatabase getDB(){
        return db;
    }
    public DBHelper getDbHelper(){
        return dbHelper;
    }

    private void setUser(){
        user = new User("userStatistic");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Quizzes");
        setSupportActionBar(toolbar);

        //setting on start
        setView();
        DatabaseOnStart();
        onFirstStart();
        setUser();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add new quiz", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), TypeQuizNameActivity.class);
                startActivityForResult(intent,1);//when creating new quiz data set changes - function onActivityResult notify
            }
        });


        //setting adapter of quizzes list
        recycler_quiz.setHasFixedSize(true);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        dbHelper = DBHelper.getInstance(this);
        myAdapter = new MainPageRecyclerViewAdapter(this,  dbHelper.getAllQuizzes());
        recycler_quiz.setLayoutManager(new GridLayoutManager(this,1));
        recycler_quiz.setAdapter(myAdapter);


        //setting button listener
        start_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectionQuizzesToGameActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onFirstStart(){
        SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
        String firstStartUserName = preferences.getString("UserName","");

        if(firstStartUserName.equals(""))
            showStartDialog();
    }

    private void showStartDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Welcome to the MyQuizzesApp!")
                .setMessage("On the start please enter your nick.")
                .setPositiveButton("ok",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Intent intent = new Intent(MainActivity.this, EnterUserNameActivity.class);
                        dialog.dismiss();
                        startActivity(intent);
                    }
                })
                .create().show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String name = data.getStringExtra("QuizName");
                dbHelper.addQuiz(name);
                myAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUser();
        myAdapter.notifyDataSetChanged();
    }

    public void DatabaseOnStart(){
        dbHelper = DBHelper.getInstance(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, UserSettingsActivity.class);
            intent.putExtra("User", user);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setView() {
        start_game_button = (Button)findViewById(R.id.start_game_button);
        recycler_quiz = (RecyclerView)findViewById(R.id.recyclerView_id);
    }
}

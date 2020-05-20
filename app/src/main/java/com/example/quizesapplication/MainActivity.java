package com.example.quizesapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.quizesapplication.DBHelper.DBHelper;
import com.example.quizesapplication.QuizFolder.TypeQuizNameActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recycler_quiz;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private MainPageRecyclerViewAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add new quiz", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), TypeQuizNameActivity.class);
                startActivityForResult(intent,1);
            }
        });

        DatabaseOnStart();

        recycler_quiz = (RecyclerView)findViewById(R.id.recyclerView_id);
        recycler_quiz.setHasFixedSize(true);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        dbHelper = DBHelper.getInstance(this);
        myAdapter = new MainPageRecyclerViewAdapter(this,  dbHelper.getAllQuizzes());
        recycler_quiz.setLayoutManager(new GridLayoutManager(this,1));
        recycler_quiz.setAdapter(myAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("activity Result");
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
        myAdapter.notifyDataSetChanged();
    }

    public void DatabaseOnStart(){
        db = this.openOrCreateDatabase("QuizzesDB",MODE_PRIVATE,null);
        System.out.println("getInstance");
        dbHelper = DBHelper.getInstance(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Quizzes");
        setSupportActionBar(toolbar);
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
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

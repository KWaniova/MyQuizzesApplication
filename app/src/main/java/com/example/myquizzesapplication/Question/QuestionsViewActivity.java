package com.example.myquizzesapplication.Question;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class QuestionsViewActivity extends AppCompatActivity {

    Intent intent;
    List<Question> questions;
    QuestionsViewAdapter adapter;
    DBHelper dbHelper = DBHelper.getInstance(this);
    int quizPosition;
    public static int PICK_FILE = 1;
    private static final String TAG = "MyActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.question_view_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.add_question_questionView){
            //add question
            Intent intent = new Intent(getApplicationContext(), AddQuestionActivity.class);
            intent.putExtra("quizPosition",quizPosition);
            System.out.println("QuestionViewActivity quizPosition: " + quizPosition);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_view);


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.questions_recycle_view);
        setQuestions();

        adapter = new QuestionsViewAdapter(this,quizPosition);

        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(adapter);
    }

    public void setQuestions(){
        if(getIntent().getExtras() != null) {
            intent = getIntent();
            quizPosition = intent.getIntExtra("QuizPositionInQuizzes",-1);
            try{
                questions = dbHelper.getQuizzes().get(quizPosition).getQuestions();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("ON RESUME QUESTIONVIEWACTIVITY");
        adapter.notifyDataSetChanged();
    }
}

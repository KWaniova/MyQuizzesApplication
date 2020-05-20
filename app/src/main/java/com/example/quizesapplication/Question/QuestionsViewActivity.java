package com.example.quizesapplication.Question;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.quizesapplication.DBHelper.DBHelper;
import com.example.quizesapplication.MainActivity;
import com.example.quizesapplication.MainPageRecyclerViewAdapter;
import com.example.quizesapplication.QuizFolder.QuizMenuActivity;
import com.example.quizesapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class QuestionsViewActivity extends AppCompatActivity {

    Intent intent;
    List<Question> questions;
    QuestionViewAdapter adapter;
    DBHelper dbHelper = DBHelper.getInstance(this);
    int quizPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_view);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.questions_recycle_view);
        setQuestions();

        adapter = new QuestionViewAdapter(this,quizPosition);

        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(adapter);
    }

    public void setQuestions(){
        if(getIntent().getExtras() != null) {
            intent = getIntent();
            quizPosition = intent.getIntExtra("QuizPositionInQuizzes",-1);
            System.out.println("Quiz position>>>>>>>." + quizPosition);

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

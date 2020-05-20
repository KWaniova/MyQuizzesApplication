package com.example.myquizzesapplication.Question;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.QuizFolder.QuizMenuActivity;
import com.example.myquizzesapplication.QuizFolder.TypeQuizNameActivity;
import com.example.myquizzesapplication.R;

import java.util.List;

public class QuestionsViewActivity extends AppCompatActivity {

    Intent intent;
    List<Question> questions;
    QuestionViewAdapter adapter;
    DBHelper dbHelper = DBHelper.getInstance(this);
    int quizPosition;

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

package com.example.quizesapplication.QuizFolder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.quizesapplication.DBHelper.DBHelper;
import com.example.quizesapplication.Game.GameStartActivity;
import com.example.quizesapplication.MainActivity;
import com.example.quizesapplication.Question.QuestionsViewActivity;
import com.example.quizesapplication.R;
import com.example.quizesapplication.Statistic.StatisticViewActivity;

public class QuizMenuActivity extends AppCompatActivity {

    Intent intent;
    private Quiz quiz;
    private int quizPosition;
    private TextView quizName, numberOfQuestions;
    private CardView showQuestions, showStatistic, startGame;
    private DBHelper dbHelper = DBHelper.getInstance(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_quiz_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("activity Result after change quiz name");
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String name = data.getStringExtra("QuizName");
                dbHelper.editQuizName(quizPosition,name);
                onResume();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.edit_quiz_name_menu_item){
            //edytowanie nazwy quizu
            Intent intent = new Intent(getApplicationContext(), TypeQuizNameActivity.class);
            startActivityForResult(intent,1);
            return true;
        }
        else if(item.getItemId() == R.id.delete_quiz_menu_item){

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this question?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String QuizName = quiz.getName();
                            Toast.makeText(QuizMenuActivity.this,"Clicked at delete " +QuizName ,Toast.LENGTH_SHORT).show();
                            dbHelper.removeQuiz(quizPosition);
                            finish();
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();
            return true;
        }
        return false;
    }


    public void setQuiz(){

        if(getIntent().getExtras() != null) {
            intent = getIntent();
            quizPosition = intent.getIntExtra("QuizPositionInQuizzes", -1);
            try{
                quiz = dbHelper.getQuizzes().get(quizPosition);
            }catch (Exception e){
                System.out.println("Quiz menu activity - QuizPositionInQuizzes error");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_menu);

        System.out.println("Quiz menu activity");
        quizName = (TextView)findViewById(R.id.quiz_menu_text_view);
        numberOfQuestions = (TextView)findViewById(R.id.quiz_menu_quantity_of_questions);
        showQuestions = (CardView)findViewById(R.id.show_questions_card_view);
        showStatistic = (CardView)findViewById(R.id.show_quiz_statistic_card_view);
        startGame = (CardView)findViewById(R.id.start_quiz_card_view);

        setQuiz();//intent and setting quiz obj, setting name and quantity of questions in onResume()


        showQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizMenuActivity.this,"Clicked at show questions" ,Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), QuestionsViewActivity.class);
                intent.putExtra("QuizPositionInQuizzes",quizPosition);
                //start the activity
                try{
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        showStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizMenuActivity.this,"Clicked at show statistic" ,Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), StatisticViewActivity.class);
                startActivity(intent);
            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizMenuActivity.this,"Clicked at start game" ,Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), GameStartActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        quizName.setText(quiz.getName());
        numberOfQuestions.setText(Integer.toString(quiz.getNumberOfQuestions()));
    }
}

package com.example.myquizzesapplication.QuizFolder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.Game.GameStartActivity;
import com.example.myquizzesapplication.Interfaces.ActivityInterfaceWithButtons;
import com.example.myquizzesapplication.Question.QuestionsViewActivity;
import com.example.myquizzesapplication.R;
import com.example.myquizzesapplication.Statistic.StatisticViewActivity;

import java.util.ArrayList;

public class QuizMenuActivity extends AppCompatActivity implements ActivityInterfaceWithButtons {

    Intent intent;
    private Quiz quiz;
    private int quizPosition;
    private TextView quizName, numberOfQuestions;
    private CardView showQuestions, showStatistic, startGame;
    private DBHelper dbHelper = DBHelper.getInstance(this);
    public static int PICK_FILE = 2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_quiz_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        // Handle action bar item clicks here.

        if(item.getItemId() == R.id.edit_quiz_name_menu_item){
            //edit quiz name
            Intent intent = new Intent(getApplicationContext(), TypeQuizNameActivity.class);
            intent.putExtra("QuizPosition",quizPosition);
            startActivityForResult(intent,1);
            return true;
        }
        else if(item.getItemId() == R.id.delete_quiz_menu_item){
            //deleting quiz
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this quiz?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String QuizName = quiz.getName();
                            Toast.makeText(QuizMenuActivity.this,QuizName + " deleted." ,Toast.LENGTH_SHORT).show();
                            dbHelper.deleteQuiz(quizPosition);
                            finish();
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();
            return true;
        }
        else if(item.getItemId() == R.id.add_questions_from_file){
            //adding questions from file
            performFileSearch();
            return true;
        }
        return false;
    }

    @Override
    public void setView() {
        quizName = (TextView)findViewById(R.id.quiz_menu_text_view);
        numberOfQuestions = (TextView)findViewById(R.id.quiz_menu_quantity_of_questions);
        showQuestions = (CardView)findViewById(R.id.show_questions_card_view);
        showStatistic = (CardView)findViewById(R.id.show_quiz_statistic_card_view);
        startGame = (CardView)findViewById(R.id.start_quiz_card_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_menu);

        setView();
        getDataFromIntent();//intent and setting quiz obj, setting name and quantity of questions in onResume()

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonsSettings();

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
        if (requestCode == PICK_FILE) {
            if (resultCode == RESULT_OK) {
                //To another activity
                Uri uri = data.getData();
                if((getContentResolver().getType(uri)).equals("text/plain")){
                    intent = new Intent(QuizMenuActivity.this,AddQuestionsFromFileActivity.class);
                    intent.putExtra("UriData",uri.toString());
                    intent.putExtra("QuizPosition",quizPosition);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Wrong data type selected.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Wrong data type", Toast.LENGTH_LONG).show();
                Log.i("Wrong data type", data.toString());
            }
        }
    }

    public void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");
        startActivityForResult(intent, PICK_FILE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        quizName.setText(quiz.getName());
        numberOfQuestions.setText(Integer.toString(quiz.getNumberOfQuestions()));
    }

    @Override
    public void buttonsSettings() {
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
                ArrayList<Integer> quizList = new ArrayList<>();
                quizList.add(quizPosition);
                intent.putExtra("SelectedItemsList",quizList);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        if(getIntent().getExtras() != null) {
            intent = getIntent();
            quizPosition = intent.getIntExtra("QuizPositionInQuizzes", -1);
            try{
                quiz = dbHelper.getQuizzes().get(quizPosition);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

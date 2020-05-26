package com.example.myquizzesapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.Question.Question;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainPageTests {

    private Context context = ApplicationProvider.getApplicationContext();
    DBHelper dbHelper;

    @Before
    public void setUp() throws Exception {
        dbHelper = DBHelper.getInstance(context);
    }

    @After
    public void tearDown() throws Exception {
        //use the instance created in setUp() function to close the database
        dbHelper.close();
    }

    //zmiana w tescie
    //metoda getQuizy() na getQuizzes() z singletonu DBHelper
    @Test
    public void add_new_quiz(){
        int quizCounter = dbHelper.getQuizzes().size();
        //zmiana tworze nowy quiz w DBHelper - podaje tylko nazwe nie objekt
        dbHelper.addQuiz("Nowy");
        System.out.println(dbHelper.getAllQuizzes().get(0).getName());
        assertThat(quizCounter+1, is(dbHelper.getQuizzes().size()));

    }

    //zmiana w tescie
    //metoda getQuizy() na getQuizzes() z singletonu DBHelper
    @Test
    public void delete_quiz() {
        int quizCounter = dbHelper.getQuizzes().size();
        int idQuiz = dbHelper.getQuizzes().size();
        dbHelper.addQuiz("quizObject");
        dbHelper.deleteQuiz(idQuiz);
        assertThat(quizCounter, is(dbHelper.getQuizzes().size()));
    }


    //zmiana przechodzenie do quizMenuActivity bez sensu bo od razu wprowadzam zmiany w bazie danych poprzez singleton
    @Test
    public void add_more_questions(){
        //zmiana dodaje quiz do bazy poprzez DBHelper
        dbHelper.addQuiz("Quiz");

        List<Question> questions = new ArrayList<>();
        //zmiana by dodac pytania do quizu potrzebuję jego pozycję w liscie quizzes
        int quizPosition = dbHelper.getQuizzes().size()-1;

        String a = "a";
        for(int i=0;i<10;i++){
            Question question = new Question(a,true);
            a+="a";
            questions.add(question);
        }
        //zmiana : dodaje nowe pytanie do quizu i bazy przez DBHelper

        for(int i=0;i<10;i++){
            dbHelper.addQuestion(quizPosition,questions.get(i));
        }

        System.out.println(dbHelper.getQuizzes().get(quizPosition).getQuestions().size());
        //sprawdzam czy ilosc dodanych pytań sie zgadza
        assertThat(dbHelper.getQuizzes().get(quizPosition).getQuestions().size(),is(10));

    }

    //zmiana od razu edytuje nazwe quizu w DBHelper - edyduje równocześnie w bazie danych i w liście quizów
    @Test
    public void edit_quiz_name(){
        dbHelper.addQuiz("Music");
        int quizPosition = dbHelper.getQuizzes().size()-1;
        System.out.println(quizPosition);
        dbHelper.editQuizName(quizPosition,"Theory");
        assertThat("Theory",is(dbHelper.getQuizzes().get(quizPosition).getName()));
    }

}

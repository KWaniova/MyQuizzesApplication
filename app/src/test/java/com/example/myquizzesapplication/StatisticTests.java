package com.example.myquizzesapplication;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.Statistic.Result;
import com.example.myquizzesapplication.Statistic.UserStatistic;
import com.example.myquizzesapplication.User.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class StatisticTests {
    //zmiany wszystkie dodawania pytan i nowych quizów przez DBHelper
    //tworzyłam obiekt mainActivity gdzie miałam liste quizzów, leprzym rozwiązaniem było stworzyć samodzielną
    //klasę DBHelpek która się troszczy o połączenie z bazą danych

    private Context context = ApplicationProvider.getApplicationContext();
    DBHelper dbHelper;
    User user = new User("user");

    @Before
    public void setUp() throws Exception {
        dbHelper = DBHelper.getInstance(context);
    }

    @Test
    public void user_statistic_questions_number_after_adding_quiz(){

        dbHelper.addQuiz("New");
        System.out.println(dbHelper.getQuizzes().size());
        int quizPosition = dbHelper.getQuizzes().size()-1;
        Question q0 = new Question("Content1",true);
        Question q1 = new Question("Content2",true);
        Question q2 = new Question("Content3",true);
        Question q3 = new Question("Content4",true);
        dbHelper.addQuestion(quizPosition,q0);
        dbHelper.addQuestion(quizPosition,q1);
        dbHelper.addQuestion(quizPosition,q2);
        dbHelper.addQuestion(quizPosition,q3);

        assertThat(4,is(user.getUserStatistic(context).getQuantityOfQuestionsInDB()));
    }
    @Test
    public void user_statistic_after_adding_one_quiz(){
        dbHelper.addQuiz("Name");
        int quizPosition = dbHelper.getQuizzes().size()-1;
        assertThat(0,is(user.getUserStatistic(context).getQuantityOfRightAnswers()));
        assertThat(0,is(user.getUserStatistic(context).getQuantityOfWrongAnswers()));
        assertThat(0.,is(user.getUserStatistic(context).getPercentageOfSuccess()));
    }
    @Test
    public void user_statistic_after_one_game_answers() {
        Result result = new Result();
        result.setQuantityOfQuestions(24);
        //game simulation
        for(int i=0;i<12;i++){
            result.incrementQuantityOfRightAnswers();
            result.incrementQuantityOfWrongAnswers();
        }
        System.out.println(result.getGetQuantityOfWrongAnswers());
        User user = new User("user");
        user.updateUserStatistic(result,context);
        assertThat(12, is(user.getUserStatistic(context).getQuantityOfWrongAnswers()));
        assertThat(12, is(user.getUserStatistic(context).getQuantityOfRightAnswers()));
        assertThat(24, is(user.getUserStatistic(context).getQuantityOfAnsweredQuestions()));
    }

    @Test
    public void user_statistic_after_one_game_percent_games(){
        Result result = new Result();
        result.setQuantityOfQuestions(24);
        //game simulation
        for(int i=0;i<12;i++){
            result.incrementQuantityOfRightAnswers();
            result.incrementQuantityOfWrongAnswers();
        }
        System.out.println(result.getGetQuantityOfWrongAnswers());
        User user = new User("user");
        user.updateUserStatistic(result,context);
        assertThat(50.0, is(user.getUserStatistic(context).getPercentageOfSuccess()));
    }
}

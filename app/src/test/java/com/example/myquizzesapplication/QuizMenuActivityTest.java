package com.example.myquizzesapplication;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.Game.GameActivity;
import com.example.myquizzesapplication.MainPage.MainActivity;
import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.QuizFolder.Quiz;
import com.example.myquizzesapplication.QuizFolder.QuizMenuActivity;
import com.example.myquizzesapplication.Statistic.Result;
import com.example.myquizzesapplication.Statistic.UserStatistic;
import com.example.myquizzesapplication.User.User;
import com.example.myquizzesapplication.User.UserStatisticAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class QuizMenuActivityTest {

    private Context context = ApplicationProvider.getApplicationContext();
    DBHelper dbHelper;
    //zmiany wszystkie dodawania pytan i nowych quizów przez DBHelper
    //tworzyłam obiekt mainActivity gdzie miałam liste quizzów, leprzym rozwiązaniem było stworzyć samodzielną
    //klasę DBHelpek która się troszczy o połączenie z bazą danych


    @Test
    public void edit_question_content(){
        dbHelper = DBHelper.getInstance(context);
        Question question = new Question("Content",true);

        dbHelper.addQuiz("Name");
        int quizPosition = dbHelper.getQuizzes().size()-1;
        dbHelper.addQuestion(quizPosition,question);
        dbHelper.editQuestionContent(quizPosition,0,"New content");
        assertThat("New content",is(dbHelper.getQuizzes().get(quizPosition).getQuestions().get(0).getContent()));
    }
    @Test
    public void edit_selected_question_answer(){
        dbHelper = DBHelper.getInstance(context);
        dbHelper.addQuiz("Quiz");
        int quizPosition = dbHelper.getQuizzes().size()-1;
        Question question = new Question("Content",true);
        dbHelper.addQuestion(quizPosition,question);
        int questionPosition = dbHelper.getQuizzes().get(quizPosition).getQuestions().size()-1;
        dbHelper.editQuestionAnswer(quizPosition,questionPosition,false);
        assertThat(false,is(dbHelper.getQuizzes().get(quizPosition).getQuestions().get(questionPosition).isRightAnswer()));
    }
    @Test
    public void edit_selected_question_content_and_answer(){
        dbHelper = DBHelper.getInstance(context);
        dbHelper.addQuiz("Quiz");
        int quizPosition = dbHelper.getQuizzes().size()-1;
        Question question = new Question("Content",true);
        dbHelper.addQuestion(quizPosition,question);
        int questionPosition = dbHelper.getQuizzes().get(quizPosition).getQuestions().size()-1;
        dbHelper.editQuestionAnswer(quizPosition,questionPosition,false);
        dbHelper.editQuestionContent(quizPosition,0,"New content");
        assertThat(false,is(dbHelper.getQuizzes().get(quizPosition).getQuestions().get(quizPosition).isRightAnswer()));
        assertThat("New content",is(dbHelper.getQuizzes().get(quizPosition).getQuestions().get(0).getContent()));
    }
    @Test
    public void add_question_to_empty_quiz(){
        dbHelper = DBHelper.getInstance(context);
        dbHelper.addQuiz("Quiz");
        int quizPosition = dbHelper.getQuizzes().size()-1;
        Question question = new Question("Content",true);
        dbHelper.addQuestion(quizPosition,question);
        assertThat(1,is(dbHelper.getQuizzes()
                .get(dbHelper.getQuizzes().size()-1).getQuestions().size()));
    }
    @Test
    public void delete_question_from_quiz(){
        dbHelper = DBHelper.getInstance(context);
        dbHelper.addQuiz("Quiz");
        int quizPosition = dbHelper.getQuizzes().size()-1;
        Question q0 = new Question("Content1",true);
        Question q1 = new Question("Content2",true);
        Question q2 = new Question("Content3",true);
        Question q3 = new Question("Content4",true);
        dbHelper.addQuestion(quizPosition,q0);
        dbHelper.addQuestion(quizPosition,q1);
        dbHelper.addQuestion(quizPosition,q2);
        dbHelper.addQuestion(quizPosition,q3);
        dbHelper.deleteQuestion(quizPosition,1);
        assertThat(3,is(dbHelper.getQuizzes().get(quizPosition).getQuestions().size()));
    }
    @Test
    public void edit_question_from_quiz(){
        dbHelper = DBHelper.getInstance(context);
        dbHelper.addQuiz("Quiz");
        int quizPosition = dbHelper.getQuizzes().size()-1;
        Question q0 = new Question("Content1",true);
        Question q1 = new Question("Content2",true);
        Question q2 = new Question("Content3",true);
        Question q3 = new Question("Content4",true);
        dbHelper.addQuestion(quizPosition,q0);
        dbHelper.addQuestion(quizPosition,q1);
        dbHelper.addQuestion(quizPosition,q2);
        dbHelper.addQuestion(quizPosition,q3);
        dbHelper.editQuestionAnswer(quizPosition,1,false);
        assertThat(false,is(dbHelper.getQuizzes().get(quizPosition).getQuestions().get(1).isRightAnswer()));
    }

    @Test
    public void get_quantity_of_questions_in_the_quiz(){
        dbHelper = DBHelper.getInstance(context);
        dbHelper.addQuiz("Quiz");
        int quizPosition = dbHelper.getQuizzes().size()-1;
        Question q0 = new Question("Content1",true);
        Question q1 = new Question("Content2",true);
        Question q2 = new Question("Content3",true);
        Question q3 = new Question("Content4",true);
        dbHelper.addQuestion(quizPosition,q0);
        dbHelper.addQuestion(quizPosition,q1);
        dbHelper.addQuestion(quizPosition,q2);
        dbHelper.addQuestion(quizPosition,q3);
        assertThat(4,is(dbHelper.getQuizzes().get(quizPosition).getQuestions().size()));
    }
    @Test
    public void get_quantity_of_questions_in_empty_quiz(){
        dbHelper = DBHelper.getInstance(context);
        dbHelper.addQuiz("Quiz");
        int quizPosition = dbHelper.getQuizzes().size()-1;
        assertThat(0,is(dbHelper.getQuizzes().get(quizPosition).getNumberOfQuestions()));
    }
    @Test
    public void get_statistic_num_of_questions_after_adding_question(){
        dbHelper = DBHelper.getInstance(context);
        dbHelper.addQuiz("Quiz");
        int quizPosition = dbHelper.getQuizzes().size()-1;
        Question q0 = new Question("Content1",true);
        dbHelper.addQuestion(quizPosition,q0);
        User user = new User("userStatistic");
        UserStatistic userStatistic = user.getUserStatistic(context);
        assertThat(1,is(userStatistic.getQuantityOfQuestionsInDB()));
    }

    //update statisctic
    @Test
    public void update_statistic_wrong_answers_after_game_is_over(){
        Result result = new Result();
        result.setQuantityOfQuestions(12);
        //game simulation
        for(int i=0;i<12;i++){
            result.incrementQuantityOfRightAnswers();
        }
        User user = new User("userStatistic");
        UserStatistic userStatistic = user.getUserStatistic(context);
        int numOfwrong = userStatistic.getQuantityOfWrongAnswers();
        user.updateUserStatistic(result,context);
        int numOfwrongAfterUpdate = userStatistic.getQuantityOfWrongAnswers();
        assertThat(numOfwrong,is(numOfwrongAfterUpdate));
    }
    @Test
    public void update_statistic_right_answers_after_game_is_over(){
        Result result = new Result();
        result.setQuantityOfQuestions(12);
        //game simulation
        for(int i=0;i<12;i++){
            result.incrementQuantityOfWrongAnswers();
        }
        User user = new User("userStatistic");
        UserStatistic userStatistic = user.getUserStatistic(context);
        int numOfrigth = userStatistic.getQuantityOfRightAnswers();
        user.updateUserStatistic(result,context);
        int numOfrightAfterUpdate = userStatistic.getQuantityOfRightAnswers();
        assertThat(numOfrigth,is(numOfrightAfterUpdate));
    }
    //ten test nie miał sensu ponieważ quiz menu nie troszczy się o grę, ta funkcjo nalność jest w innej klasie
}

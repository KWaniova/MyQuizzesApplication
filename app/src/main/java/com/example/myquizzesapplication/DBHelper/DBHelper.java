package com.example.myquizzesapplication.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.QuizFolder.Quiz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class DBHelper extends SQLiteOpenHelper implements DBHelperInterface {

    private static final String DB_NAME = "QuizzesDB.db";
    private static final String TABLE_NAME_QUIZZES = "Quizzes";
    private static final String TABLE_NAME_QUESTIONS = "Questions";
    private static final int DB_VER = 1;

    private SQLiteDatabase db;
    private List<Quiz> quizzes;
    private static DBHelper instance = null;

    final String INSERT_INTO_QUIZZES = "INSERT INTO Quizzes (QuizName) VALUES ";
    final String INSERT_INTO_QUESTIONS = "INSERT INTO Questions (QuizID,QuestionContent,RightAnswer) VALUES ";

    Context context;


    public static synchronized DBHelper getInstance(Context context){
        //creating singleton
        if(instance == null){
            instance = new DBHelper(context);
        }
        instance.context = context;
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME,null, DB_VER);
        db = context.openOrCreateDatabase("QuizzesDB",MODE_PRIVATE,null);
        db = getWritableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//Creating and filling tables
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS Quizzes (QuizID INTEGER PRIMARY KEY AUTOINCREMENT,QuizName VARCHAR)");
            db.execSQL("CREATE TABLE IF NOT EXISTS Questions (QuizID int, QuestionContent VARCHAR, RightAnswer TEXT NOT NULL CHECK( RightAnswer IN ('TRUE','FALSE')));");
            getAllQuizzes();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }
    private void dropTable(String tableName){
        db.execSQL("DROP TABLE " + tableName);
    }


    //po stworzeniu objektu DBHelper pobiera quizy i pytania z bazy danych - tworzy listę quizzes
    public List<Quiz> getAllQuizzes(){
        quizzes = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_QUIZZES,null);

            if(cursor.moveToFirst()){

                while(!cursor.isAfterLast()){
                    int QuizID = cursor.getInt(cursor.getColumnIndex("QuizID"));
                    Quiz quiz = new Quiz(QuizID,
                            cursor.getString(cursor.getColumnIndex("QuizName")));
                    //creating questions list for quiz
                    ArrayList<Question> questions = (ArrayList<Question>) getAllQuizQuestions(QuizID);
                    quiz.setQuestions(questions);
                    quizzes.add(quiz);
                    cursor.moveToNext();
                }
            }
            cursor.close();

        }catch (Exception e){
            System.out.println("Exception: ");
            e.printStackTrace();
        }

        return quizzes;
    }

    //dla danego quizu tworzy listę pytań z bazy danych
    @Override
    public List<Question> getAllQuizQuestions(int quizID) {
        Cursor questionsCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_QUESTIONS + " WHERE QuizID = " + Integer.toString(quizID),null);

        List<Question> questions = new ArrayList<>();

        boolean rightAnswer;

        String selectedAnswer;

        if(questionsCursor.moveToFirst()){
            while(!questionsCursor.isAfterLast()){

                selectedAnswer = questionsCursor.getString(questionsCursor.getColumnIndex("RightAnswer"));

                rightAnswer = selectedAnswer.equals("TRUE") ? true : false;

                String content = questionsCursor.getString(questionsCursor.getColumnIndex("QuestionContent"));
                Question question = new Question(quizID, content, rightAnswer);
                questions.add(question);

                questionsCursor.moveToNext();
            }
        }
        questionsCursor.close();
        return questions;
    }


    @Override
    public boolean addQuiz(String name) {
        try{
            ContentValues values = new ContentValues();
            values.put("QuizName",name);

            long insertID = db.insert("Quizzes","",values);//getting unique quizID

            Quiz quiz = new Quiz((int)insertID,name);
            quizzes.add(quiz);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteQuiz(int quizPosition) {
        int QuizID = quizzes.get(quizPosition).getIdQuiz();//getting id of quiz in database
        String quizName = quizzes.get(quizPosition).getName();
        try{
            db.execSQL("DELETE FROM Quizzes WHERE QuizID = " + QuizID);
            db.execSQL("DELETE FROM Questions WHERE QuizID = " + QuizID);//deleting all questions
            quizzes.remove(quizPosition);
            //showQuizzesInDatabase();
            Toast.makeText(context,quizName + " successfully removed!",Toast.LENGTH_SHORT).show();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        Toast.makeText(context,quizName + " can't be removed!",Toast.LENGTH_SHORT).show();
        return false;
    }


    @Override
    public boolean editQuizName(int QuizPosition,String name){
        int idQuiz = quizzes.get(QuizPosition).getIdQuiz();
        ContentValues values = new ContentValues();
        values.put("QuizName",name);
        String where = "QuizID=?";
        String[] whereArgs = new String[] {String.valueOf(idQuiz)};

        db.update("Quizzes",values,where,whereArgs);
        quizzes.get(QuizPosition).setName(name);

        //showQuizzesInDatabase();
        return true;
    }

    @Override
    public boolean addQuestion(int quizPosition, Question question) {
        try {
            int QuizID = quizzes.get(quizPosition).getIdQuiz();
            question.setQuizID(QuizID);
            String rightAnswer = (question.isRightAnswer() ? "TRUE" : "FALSE");
            db.execSQL(INSERT_INTO_QUESTIONS +  "(" + question.getQuizID() + ",'" + question.getContent() + "', '" + rightAnswer + "')");
            quizzes.get(quizPosition).getQuestions().add(question);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteQuestion(int quizPosition, int questionPosition) {
        String QuestionContent = quizzes.get(quizPosition).getQuestions().get(questionPosition).getContent();
        int QuizID = quizzes.get(quizPosition).getIdQuiz();
        try {
            db.execSQL("DELETE FROM Questions WHERE QuizID = " +QuizID +" AND QuestionContent LIKE '" + QuestionContent + "'");
            quizzes.get(quizPosition).getQuestions().remove(questionPosition);
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean editQuestionContent(int quizPosition, int questionPosition, String newContent) {
        String oldContent = quizzes.get(quizPosition).getQuestions().get(questionPosition).getContent();
        ContentValues values = new ContentValues();
        try {
            if(!newContent.equals(oldContent)){
                values.put("QuestionContent",newContent);
                String where = "QuestionContent=?";
                String[] whereArgs = new String[] {oldContent};
                db.update("Questions",values,where,whereArgs);
                quizzes.get(quizPosition).getQuestions().get(questionPosition).setContent(newContent);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        //showQuestionsInDatabase();
        return false;
    }

    @Override
    public boolean editQuestionAnswer(int quizPosition, int questionPosition, Boolean newAnswer) {
        boolean oldAnswer = quizzes.get(quizPosition).getQuestions().get(questionPosition).isRightAnswer();
        ContentValues values = new ContentValues();
        try{
            if(newAnswer != oldAnswer){
                int quizID = quizzes.get(quizPosition).getIdQuiz();
                String answer = (newAnswer == true ? "TRUE" : "FALSE");
                String questionContent = quizzes.get(quizPosition).getQuestions().get(questionPosition).getContent();
                db.execSQL("UPDATE Questions SET RightAnswer='" +answer + "'  WHERE QuizID = " + quizID + " AND QuestionContent LIKE '" +  questionContent + "'");
                quizzes.get(quizPosition).getQuestions().get(questionPosition).setRightAnswer(newAnswer);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
    public int getNumberOfQuestions(){
        int numberOfQuestions = 0;
        for(int i=0;i<quizzes.size();i++){
            numberOfQuestions +=quizzes.get(i).getQuestions().size();
        }
        return numberOfQuestions;
    }
}


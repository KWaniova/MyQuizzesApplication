package com.example.myquizzesapplication.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myquizzesapplication.Question.Question;
import com.example.myquizzesapplication.QuizFolder.Quiz;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "QuizzesDB.db";
    private static final String TABLE_NAME = "Quizzes";

    private static final int DB_VER = 1;
    private Context context;
    private SQLiteDatabase db;
    private List<Quiz> quizzes;
    private static DBHelper instance = null;

     final String INSERT_INTO_QUIZZES = "INSERT INTO Quizzes (QuizID,QuizName) VALUES ";
     final String INSERT_INTO_QUESTIONS = "INSERT INTO Questions (QuizID,QuestionContent,RightAnswer) VALUES ";

    public int getNumberOfQuizzes() {
        return quizzes.size();
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public static synchronized DBHelper getInstance(Context context){
        if(instance == null){
            instance = new DBHelper(context);
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME,null, DB_VER);
        this.context = context;
        db = this.getWritableDatabase();
        System.out.println("Creating");
        onCreate(db);
    }

    public List<Quiz> getAllQuizzes(){
        quizzes = new ArrayList<>();
        try{
            System.out.println("getAllquizes");

            Cursor cursor = db.rawQuery("SELECT * FROM Quizzes",null);

            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    int QuizID = cursor.getInt(cursor.getColumnIndex("QuizID"));
                    Quiz quiz = new Quiz(QuizID,
                            cursor.getString(cursor.getColumnIndex("QuizName")));
                    //creating QuestionsList
                    addQuestions(quiz,QuizID);
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

    public void addQuestions(Quiz quiz, int QuizID){
        Cursor questionsCursor = db.rawQuery("SELECT * FROM Questions",null);
        List<Question> questions = new ArrayList<>();
        boolean rightAnswer;
        String answer;
        int quizID;
        if(questionsCursor.moveToFirst()){
            while(!questionsCursor.isAfterLast()){
                quizID = questionsCursor.getInt(questionsCursor.getColumnIndex("QuizID"));
                if(quizID==QuizID){
                    answer = questionsCursor.getString(questionsCursor.getColumnIndex("RightAnswer"));
                    if(answer.equals("TRUE")) rightAnswer = true;
                    else rightAnswer=false;
                    String content = questionsCursor.getString(questionsCursor.getColumnIndex("QuestionContent"));
                    Question question = new Question(quizID, content, rightAnswer);
                    questions.add(question);
                }
                questionsCursor.moveToNext();
            }
        }
        quiz.setQuestions(questions);
        questionsCursor.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            //db.execSQL("DROP TABLE Quizzes");
            //db.execSQL("DROP TABLE Questions");

            db.execSQL("CREATE TABLE IF NOT EXISTS Quizzes (QuizID INTEGER PRIMARY KEY AUTOINCREMENT,QuizName VARCHAR)");
            db.execSQL("CREATE TABLE IF NOT EXISTS Questions (QuizID int, QuestionContent VARCHAR UNIQUE, RightAnswer TEXT NOT NULL CHECK( RightAnswer IN ('TRUE','FALSE')));");
           // System.out.println("tables created");
            /*addQuiz("MUSIC");
            addQuiz("LOCATION");
            addQuiz("Medit");
            addQuiz("MEDICINE");
            Question question = new Question(0,"Leonardo da vinci is singer",false);
            addQuestion(question);
            question = new Question(0,"Aguilerra''s song is Chandalier",false);
            addQuestion(question);
            question = new Question(1,"Argentina is in America",true);
            addQuestion(question);
            question = new Question(1,"Boliwia is in Africa",false);
            addQuestion(question);
            question = new Question(3,"Aspirine is a tablet",true);
            addQuestion(question);*/

            /*db.execSQL("INSERT INTO Quizzes (QuizID,QuizName) VALUES (1,'MUSIC')");
            db.execSQL("INSERT INTO Quizzes (QuizID,QuizName) VALUES (2,'LOCATION')");
            db.execSQL("INSERT INTO Quizzes (QuizID,QuizName) VALUES (3,'Medit')");
            db.execSQL("INSERT INTO Quizzes (QuizID,QuizName) VALUES (4,'MEDICINE')");
            db.execSQL("INSERT INTO Quizzes (QuizID,QuizName) VALUES (5,'new fifth')");
            db.execSQL("INSERT INTO Questions (QuizID,QuestionContent,RightAnswer) VALUES (3,'The sun is yellow','TRUE')");
            db.execSQL("INSERT INTO Questions (QuizID,QuestionContent,RightAnswer) VALUES (1,'Leonardo da vinci is singer','FALSE')");
            db.execSQL("INSERT INTO Questions (QuizID,QuestionContent,RightAnswer) VALUES (1,'Aguilerra''s song is Chandalier','FALSE')");
            db.execSQL("INSERT INTO Questions (QuizID,QuestionContent,RightAnswer) VALUES (2,'Argentina is in America','TRUE')");
            db.execSQL("INSERT INTO Questions (QuizID,QuestionContent,RightAnswer) VALUES (2,'Boliwia is in Africa','FALSE')");
            db.execSQL("INSERT INTO Questions (QuizID,QuestionContent,RightAnswer) VALUES (4,'Aspirine is a tablet','TRUE')");*/

            //db.close();
        }catch(Exception e){
            System.out.println("EXEPTION: ");
            e.printStackTrace();
        }
    }

    public void showQuizzesInDatabase(){
        Cursor c = db.rawQuery("SELECT * FROM Quizzes", null);
        int contentIndex = c.getColumnIndex("QuizName");
        int idindex = c.getColumnIndex("QuizID");
        c.moveToFirst();
        if(c!=null){
            while(true){
                System.out.println("Quiz " + c.getInt(idindex));
                //Log.i("Name: ", c.getString(contentIndex));
                if(c.moveToNext() == false) break;
            }
        }

        c.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public  void showTablesInDatabase(){
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                System.out.println( "Table Name=> "+c.getString(0));
                c.moveToNext();
            }
        }
    }

    public boolean addQuestion(Question question,int QuizPositionInQuizzes){
        try {
            String rightAnswer;
            if(question.isRightAnswer() == true){
                rightAnswer = "TRUE";
            }else{
                rightAnswer= "FALSE";
            }
            db.execSQL(INSERT_INTO_QUESTIONS +  "(" + question.getQuizID() + ",'" + question.getContent() + "', '" + rightAnswer + "')");
            quizzes.get(QuizPositionInQuizzes).getQuestions().add(question);
        }catch(Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public boolean editQuizName(int QuizPosition,String name){
        int idQuiz = quizzes.get(QuizPosition).getIdQuiz();
        ContentValues values = new ContentValues();
        values.put("QuizName",name);
        String where = "QuizID=?";
        String[] whereArgs = new String[] {String.valueOf(idQuiz)};
        long insertID = db.update("Quizzes",values,where,whereArgs);
        quizzes.get(QuizPosition).setName(name);
        showQuizzesInDatabase();
        return true;
    }

    public boolean removeQuestion(int QuizPosition, int QuestionPosition){
        String UniquieQuestionContent = quizzes.get(QuizPosition).getQuestions().get(QuestionPosition).getContent();
        int QuizID = quizzes.get(QuizPosition).getIdQuiz();
        try {
            db.execSQL("DELETE FROM Questions WHERE QuizID = " +QuizID +" AND QuestionContent LIKE '" + UniquieQuestionContent + "'");
            quizzes.get(QuizPosition).getQuestions().remove(QuestionPosition);
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean addQuiz(String name){
        try{
            ContentValues values = new ContentValues();
            values.put("QuizName",name);
            long insertID = db.insert("Quizzes","",values);
            Quiz quiz = new Quiz((int)insertID,name);
            quizzes.add(quiz);
            showQuizzesInDatabase();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeQuiz(int QuizPosition){
        int QuizID = quizzes.get(QuizPosition).getIdQuiz();
        System.out.println("Quiz: " + quizzes.get(QuizPosition).getName() + " will be removed");
        try{
            db.execSQL("DELETE FROM Quizzes WHERE QuizID = " +QuizID);
            db.execSQL("DELETE FROM Questions WHERE QuizID = " +QuizID);
            quizzes.remove(QuizPosition);
            showQuizzesInDatabase();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}


package com.example.myquizzesapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.QuizFolder.Quiz;
import com.example.myquizzesapplication.QuizFolder.QuizMenuActivity;

import java.util.List;

public class MainPageRecyclerViewAdapter extends RecyclerView.Adapter<MainPageRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    DBHelper dbHelper;


    public MainPageRecyclerViewAdapter(Context aContext, List<Quiz> aData) {
        this.context = aContext;
        dbHelper = DBHelper.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.card_view_item_quiz,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {

        holder.quiz_name_textView.setText(dbHelper.getQuizzes().get(position).getName());
        holder.quantity_of_questions_textView.setText(Integer.toString(dbHelper.getQuizzes().get(position).getNumberOfQuestions()));

        //Set on item click listener
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuizMenuActivity.class);
                //passing data to the quiz menu activity
                intent.putExtra("QuizPositionInQuizzes",position);
                //start the activity
                try{
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        holder.card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int itemToDelete = position;
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this quiz?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("quizzes size: " + dbHelper.getQuizzes().size());
                                dbHelper.removeQuiz(itemToDelete);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return dbHelper.getQuizzes().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CardView card_view;
        TextView quiz_name_textView;
        TextView quantity_of_questions_textView;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            quiz_name_textView = itemView.findViewById(R.id.quiz_name_id);
            quantity_of_questions_textView = itemView.findViewById(R.id.quantity_of_questions_quiz);
            card_view = itemView.findViewById(R.id.quiz_mainPage_card_view_id);

        }
    }
}

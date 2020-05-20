package com.example.myquizzesapplication.Question;

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
import com.example.myquizzesapplication.R;

import java.util.List;

public class QuestionViewAdapter extends RecyclerView.Adapter<QuestionViewAdapter.MyViewHolder> {

    private Context context;
    List<Question> questions;
    private DBHelper dbHelper;
    private int quizPosition;


    public QuestionViewAdapter(Context aContext,int quizPosition) {
        this.context = aContext;
        this.quizPosition = quizPosition;
        dbHelper = DBHelper.getInstance(context);
        this.questions = dbHelper.getQuizzes().get(quizPosition).getQuestions();
    }

    @NonNull
    @Override
    public QuestionViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.card_view_item_question_view,parent,false);
        return new QuestionViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionViewAdapter.MyViewHolder holder, final int position) {

        holder.question_content.setText(questions.get(position).getContent());
        holder.question_right_answer.setText(Boolean.toString(questions.get(position).isRightAnswer()));

        //Set on item click listener
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionEditorActivity.class);
                //passing data to the quiz menu activity
                intent.putExtra("questionPosition",position);
                intent.putExtra("quizPosition",quizPosition);
                //start the activity
                try{
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }

                //Log.i("Come back:", Integer.toString(position));
            }
        });
        holder.card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int itemToDelete = position;
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this question?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.removeQuestion(quizPosition,position);
                                System.out.println("questions size: " + questions.size());
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
        return questions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CardView card_view;
        TextView question_content;
        TextView question_right_answer;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            question_right_answer = itemView.findViewById(R.id.question_answer_card_view);
            question_content = itemView.findViewById(R.id.question_content_card_view);
            card_view = itemView.findViewById(R.id.quiz_questionView_card_view_id);
        }
    }
}


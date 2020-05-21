package com.example.myquizzesapplication.Game;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizzesapplication.DBHelper.DBHelper;
import com.example.myquizzesapplication.MainPageRecyclerViewAdapter;
import com.example.myquizzesapplication.QuizFolder.Quiz;
import com.example.myquizzesapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizzesSelectionGameAdapter extends RecyclerView.Adapter<com.example.myquizzesapplication.Game.QuizzesSelectionGameAdapter.MyViewHolder> {

    private Context context;
    List<Quiz> quizzes;
    Map<Integer,Boolean> selectedItemsList = new HashMap<Integer,Boolean>();
    private DBHelper dbHelper;
    private int quantityOfSelectedItems = 0;
    TextView selectedTextView;
    Button OKButton;


    public QuizzesSelectionGameAdapter(Context aContext, View selectedTextView, View OKButton) {
        this.context = aContext;
        dbHelper = DBHelper.getInstance(context);
        this.quizzes = dbHelper.getQuizzes();
        initializeSelectedItemsList();
        this.OKButton = (Button) OKButton;
        this.selectedTextView =(TextView) selectedTextView;
    }

    public void initializeSelectedItemsList(){
        for(int i = 0 ; i < quizzes.size(); i++){
            selectedItemsList.put(i,false);
        }
    }

    public ArrayList<Integer> getSelectedItemsList(){
        ArrayList<Integer> selected = new ArrayList<>();
        for(int i = 0 ; i < quizzes.size(); i++){
            if(selectedItemsList.get(i) == true)
                selected.add(i);
        }
        return selected;
    }
    @NonNull
    @Override
    public QuizzesSelectionGameAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.selected_text_view_item,parent,false);
        return new QuizzesSelectionGameAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuizzesSelectionGameAdapter.MyViewHolder holder, final int position) {

        holder.quizName.setText(quizzes.get(position).getName());
        selectedTextView.setText(Integer.toString(quantityOfSelectedItems));
        holder.quizName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItemsList.get(position) == false){
                    selectedItemsList.put(position,!selectedItemsList.get(position));
                    quantityOfSelectedItems++;
                }else{
                    selectedItemsList.put(position,!selectedItemsList.get(position));
                    quantityOfSelectedItems--;
                }
                if(quantityOfSelectedItems>0) OKButton.setEnabled(true);
                else OKButton.setEnabled(false);
                selectedTextView.setText(Integer.toString(quantityOfSelectedItems));
                holder.quizName.setBackgroundColor(selectedItemsList.get(position) ? Color.CYAN : Color.WHITE);
            }
        });
    }


    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView quizName;
        //TextView quantityOfQuestionsSelected;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            cardView = itemView.findViewById(R.id.selected_item_text_view);
            quizName = itemView.findViewById(R.id.selected_item_quiz_name);
            //quantityOfQuestionsSelected = itemView.findViewById(R.id.quantity_od_selected_quizzes);
        }
    }

}
package com.evlishelper.lwizapp.activities.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.evlishelper.lwizapp.R;

import java.util.List;

/**
 * Created by Asus on 13.07.2017.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    List<Question> questions;

    public QuestionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @Override

    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.question, parent, false));
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, final int position) {
        holder.question.setText(questions.get(position).getQuestion());
        holder.cb.setChecked(questions.get(position).isCorrecet());
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(position).setCorrecet(!questions.get(position).isCorrecet());
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public int getNumberOfCorrect() {
        int k = 0;
        for (Question q : questions) {
            if (q.isCorrecet())
                k++;
        }
        return k;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        CheckBox cb;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.question_text);
            cb = (CheckBox) itemView.findViewById(R.id.cb);
        }
    }
}

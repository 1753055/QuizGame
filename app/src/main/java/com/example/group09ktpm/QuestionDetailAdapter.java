package com.example.group09ktpm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class
QuestionDetailAdapter extends RecyclerView.Adapter<QuestionDetailAdapter.ViewHolder>  {
    private Context mContext;
    private List<String> mCategory = new ArrayList<>();
    private List<Integer> mHard = new ArrayList<>();

    public QuestionDetailAdapter(Context context, List<String> category, List<Integer> hard){
        this.mContext = context;
        this.mCategory.addAll(category);
        this.mHard.addAll(hard);
    }
    @NonNull
    @Override
    public QuestionDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.contribute_question, parent, false);
        return new QuestionDetailAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionDetailAdapter.ViewHolder holder, int position) {
        holder.category.setText(mCategory.get(position));
        holder.hard.setText(mHard.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView category;
        private TextView hard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            hard = itemView.findViewById(R.id.hard);
        }
    }
}

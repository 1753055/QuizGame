package com.example.group09ktpm;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.auth.User;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreDetailsAdapter extends RecyclerView.Adapter<ScoreDetailsAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mUsernames = new ArrayList<>();
    private List<Integer> mScore = new ArrayList<>();

    public ScoreDetailsAdapter(Context context, List<String> users, List<Integer> scores){
        this.mContext = context;
        this.mUsernames.addAll(users);
        this.mScore.addAll(scores);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.highscore_detail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.username.setText(mUsernames.get(position));
        holder.score.setText(mScore.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mUsernames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView score;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            score = itemView.findViewById(R.id.score);
        }
    }
}

package com.example.group09ktpm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Highsocre extends AppCompatActivity {

    private RecyclerView highscore;
    private List<String> usernames = new ArrayList<>();
    private List<Integer> scores = new ArrayList<>();
    private ScoreDetailsAdapter adapter;

    Button bt_setting;
    boolean playSound = true;
    boolean isAdmin = false;
    boolean isLogin = false;
    String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_board);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            playSound = bundle.getBoolean("sound");
            isAdmin = bundle.getBoolean("isAdmin");
            isLogin = bundle.getBoolean("isLogin");
            username = bundle.getString("username");
        }
        bt_setting = findViewById(R.id.sounddc);

        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingClass setting = new SettingClass();
                setting.showPopupWindow(view, username, getSupportFragmentManager());
            }
        });

        highscore = findViewById(R.id.listView);
        usernames.add("Hieu");
        usernames.add("Hoang");
        usernames.add("An");
        scores.add(10);
        scores.add(9);
        scores.add(8);
        adapter = new ScoreDetailsAdapter(this, usernames, scores);
        highscore.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        highscore.setLayoutManager(linearLayoutManager);
    }
}
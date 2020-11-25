package com.example.group09ktpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewGame extends AppCompatActivity {

    Button bt_ok;
    Button bt_setting;
    Spinner category;
    Spinner hardLevel;
    boolean playSound = true;
    boolean isAdmin = false;
    boolean isLogin = false;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            playSound = bundle.getBoolean("sound");
            isAdmin = bundle.getBoolean("isAdmin");
            isLogin = bundle.getBoolean("isLogin");
            username = bundle.getString("username");
        }
        bt_setting = findViewById(R.id.soundng);

        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingClass setting = new SettingClass();
                setting.showPopupWindow(view, username, getSupportFragmentManager());
            }
        });
        category = (Spinner) findViewById(R.id.spinner);
        hardLevel = (Spinner) findViewById(R.id.spinner2);

        List<String> categories = new ArrayList<>();
        categories.add("Tất cả");
        List<Integer> hardLevels  = new ArrayList<>();


        loadCategories(new MyCallback() {
            @Override
            public void MyCallback(List<Question> questions) {
            }

            @Override
            public void MyCallback3(boolean Admin, String username) {

            }

            @Override
            public void MyCallback2(List<String> categories, List<Integer> hardLevels) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, categories);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(getBaseContext() , android.R.layout.simple_spinner_item, hardLevels);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Thiết lập adapter cho Spinner
                category.setAdapter(adapter);

                //Thiết lập adapter cho Spinner
                hardLevel.setAdapter(adapter2);
            }
        });

        bt_ok = (Button) findViewById(R.id.oknewgame);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewGame.this, PlayGame.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("sound", playSound);
                bundle.putString("category", category.getSelectedItem().toString());
                bundle.putString("hardlevel", hardLevel.getSelectedItem().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    public void loadCategories(final MyCallback myCallback){
        Query query;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        query = db.collection("Question");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int id =0;
                    List<String> tmp = new ArrayList<String>();
                    List<Integer> tmp2 = new ArrayList<Integer>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        tmp.add(document.get("category", String.class));
                        tmp2.add(document.get("hard", Integer.class));
                    }
                    Set<String> set = new HashSet<>(tmp);
                    tmp.clear();
                    tmp.addAll(set);

                    Set<Integer> set2 = new HashSet<Integer>(tmp2);
                    tmp2.clear();
                    tmp2.addAll(set2);

                    myCallback.MyCallback2(tmp, tmp2);
                } else {
                }
            }
        });
    }
}

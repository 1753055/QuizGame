package com.example.group09ktpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contribute extends AppCompatActivity {
    Button bt_submit;
    EditText question;
    EditText choice1;
    EditText choice2;
    EditText choice3;
    EditText choice4;
    RadioGroup answer;
    RadioButton rb1, rb2, rb3, rb4;
    EditText category;
    EditText hardlevel;
    Button bt_setting;
    boolean playSound = true;
    boolean isAdmin = false;
    boolean isLogin = false;
    String username = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            playSound = bundle.getBoolean("sound");
            isAdmin = bundle.getBoolean("isAdmin");
            isLogin = bundle.getBoolean("isLogin");
            username = bundle.getString("username");
        }

//        tg_sound = (ToggleButton) findViewById(R.id.soundc);
        bt_setting = findViewById(R.id.sounddc);

        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingClass setting = new SettingClass();
                setting.showPopupWindow(view, username, getSupportFragmentManager());
            }
        });
        bt_submit = (Button) findViewById(R.id.submit);
        question = (EditText) findViewById(R.id.question);
        answer = (RadioGroup) findViewById(R.id.choices);
        answer.check(0);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);
        choice1 = (EditText) findViewById(R.id.choice1);
        choice2 = (EditText) findViewById(R.id.choice2);
        choice3 = (EditText) findViewById(R.id.choice3);
        choice4 = (EditText) findViewById(R.id.choice4);
        category = (EditText) findViewById(R.id.category);
        hardlevel = (EditText) findViewById(R.id.hardlevel);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidQuestion()){
                    List<String> tmp = new ArrayList<String>();
                    tmp.add(choice1.getText().toString());
                    tmp.add(choice2.getText().toString());
                    tmp.add(choice3.getText().toString());
                    tmp.add(choice4.getText().toString());
                    String ansTmp= tmp.get(answer.getCheckedRadioButtonId());
                    Question ques = new Question(question.getText().toString(), tmp, ansTmp, category.getText().toString(), Integer.parseInt(hardlevel.getText().toString()),false);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map tmp3 = ques.toMap();
                    tmp3.put("isComfirmed", isAdmin);
                    db.collection("Question").document(ques.getQuestion()).set(tmp3, SetOptions.merge());
                    finish();
                }
            }
        });

    }
    public boolean checkValidQuestion(){
        if(question.getText().toString().equals(question.getHint().toString())) {
            Toast t = Toast.makeText(getApplicationContext(), "Have no question!", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if(choice2.getText().toString().equals(choice2.getHint().toString())) {
            Toast t = Toast.makeText(getApplicationContext(), "Have no second choice!", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }
        if(choice1.getText().toString().equals(choice1.getHint().toString())) {
            Toast t = Toast.makeText(getApplicationContext(), "Have no first choice!", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }
        if(choice3.getText().toString().equals(choice3.getHint().toString())) {
            Toast t = Toast.makeText(getApplicationContext(), "Have no third choice!", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }
        if(choice4.getText().toString().equals(choice4.getHint().toString())) {
            Toast t = Toast.makeText(getApplicationContext(), "Have no fourth choice!", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }
        if(category.getText().toString().equals(category.getHint().toString())) {
            Toast t = Toast.makeText(getApplicationContext(), "Have no category!", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }
        if(hardlevel.getText().toString().equals(hardlevel.getHint().toString()))  {
            Toast t = Toast.makeText(getApplicationContext(), "Have no hard level!", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        Toast t = Toast.makeText(getApplicationContext(), "Da them vao database", Toast.LENGTH_SHORT);
        t.show();
        return true;
    }
}

package com.example.group09ktpm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.util.Map;

public class CheckQuizContributeDetail extends AppCompatActivity {
    String jsonMyObject;

    Button bt_accept,bt_delete;
    EditText tvquestion;
    EditText choice1;
    EditText choice2;
    EditText choice3;
    EditText choice4;
    RadioGroup answer;
    RadioButton rb1, rb2, rb3, rb4;
    EditText category;
    EditText hardlevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.question_detail);

        if (extras != null) {
            jsonMyObject = extras.getString("myQuestion");
        }
        map();

        final Question question = new Gson().fromJson(jsonMyObject, Question.class);

        tvquestion.setText(question.getQuestion());
        choice1.setText(question.getChoices().get(0));
        choice2.setText(question.getChoices().get(1));
        choice3.setText(question.getChoices().get(2));
        choice4.setText(question.getChoices().get(3));
        if(choice1.getText().toString().equals(question.getRightAnswer()))
        {
            rb1.setChecked(true);
        }
        else if(choice2.getText().toString().equals(question.getRightAnswer()))
        {
            rb2.setChecked(true);
        }
        else if(choice3.getText().toString().equals(question.getRightAnswer()))
        {
            rb3.setChecked(true);
        }
        else if(choice4.getText().toString().equals(question.getRightAnswer()))
        {
            rb4.setChecked(true);
        }

        category.setText(question.getCategory());
        hardlevel.setText(String.valueOf(question.getHardLevel()));

        bt_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setConfirmed(true);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map tmp3 = question.toMap();
                db.collection("Question").document(question.getQuestion()).set(tmp3, SetOptions.merge());

                finish();
            }
        });
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map tmp3 = question.toMap();
                db.collection("Question").document(question.getQuestion()).delete();
                finish();
            }
        });
    }

    private void map(){
        bt_delete = (Button) findViewById(R.id.delete);
        bt_accept=findViewById(R.id.accept);
        tvquestion = (EditText) findViewById(R.id.checkquestion);
        answer = (RadioGroup) findViewById(R.id.checkchoices);
        rb1 = (RadioButton) findViewById(R.id.checkrb1);
        rb2 = (RadioButton) findViewById(R.id.checkrb2);
        rb3 = (RadioButton) findViewById(R.id.checkrb3);
        rb4 = (RadioButton) findViewById(R.id.checkrb4);
        choice1 = (EditText) findViewById(R.id.checkchoice1);
        choice2 = (EditText) findViewById(R.id.checkchoice2);
        choice3 = (EditText) findViewById(R.id.checkchoice3);
        choice4 = (EditText) findViewById(R.id.checkchoice4);
        category = (EditText) findViewById(R.id.checkcategory);
        hardlevel = (EditText) findViewById(R.id.checkhardlevel);
    }
}

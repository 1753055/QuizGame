package com.example.group09ktpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlayGame extends AppCompatActivity {
    Button bt_choice1;
    Button bt_choice2;
    Button bt_choice3;
    Button bt_choice4;
    TextView tv_question;
    TextView tv_number;
    List<Question> questions = new ArrayList<Question>();
    boolean playSound = true;
    String category = "";
    Integer hardlevel;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            playSound = bundle.getBoolean("sound");
            category = bundle.getString("category");
            hardlevel = bundle.getInt("hardlevel", 5);
        }



        tv_question = (TextView) findViewById(R.id.question);
        tv_number = (TextView) findViewById(R.id.number);
        bt_choice1 = (Button) findViewById(R.id.choice1);
        bt_choice2 = (Button) findViewById(R.id.choice2);
        bt_choice3 = (Button) findViewById(R.id.choice3);
        bt_choice4 = (Button) findViewById(R.id.choice4);


        loadQuestions(new MyCallback() {
            @Override
            public void MyCallback(List<Question> questionss) {
                questions = questionss;
                updateQuestion(0);
            }

            @Override
            public void MyCallback3(boolean Admin, String user) {

            }

            @Override
            public void MyCallback2(List<String> categories, List<Integer> hardLevel) {

            }
        });

        bt_choice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ABCDE", "onClick: "+ questions.get(0).getQuestion());
                if(bt_choice4.getText().toString().equals(questions.get(score).getRightAnswer())){
                    score++;
                    Toast toast = Toast.makeText(getApplicationContext(), "Đúng rồi, mời sang câu tiếp theo.", Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestion(score);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Sai rồi, mới bạn chơi lại.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(PlayGame.this, GameOver.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("score", score);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        bt_choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bt_choice3.getText().toString().equals(questions.get(score).getRightAnswer())){
                    score++;
                    Toast toast = Toast.makeText(getApplicationContext(), "Dap an dung roi, moi sang cau tiep theo", Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestion(score);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Dap an sai, moi chon lai", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(PlayGame.this, GameOver.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("sound", playSound);
                    bundle.putInt("score", score);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        bt_choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bt_choice2.getText().toString().equals(questions.get(score).getRightAnswer())){
                    score++;
                    Toast toast = Toast.makeText(getApplicationContext(), "Dap an dung roi, moi sang cau tiep theo", Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestion(score);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Dap an sai, moi chon lai", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(PlayGame.this, GameOver.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("sound", playSound);
                    bundle.putInt("score", score);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        bt_choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bt_choice1.getText().toString().equals(questions.get(score).getRightAnswer())){
                    score++;
                    Toast toast = Toast.makeText(getApplicationContext(), "Dap an dung roi, moi sang cau tiep theo", Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestion(score);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Dap an sai, moi chon lai", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(PlayGame.this, GameOver.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("sound", playSound);
                    bundle.putInt("score", score);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
    public void loadQuestions(final MyCallback myCallback){
        Query query;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(category.equals("All")) {
            query = db.collection("Question");//.whereLessThanOrEqualTo("hard", hardlevel);
        }
        else query = db.collection("Question");//.whereArrayContains("category", category).whereLessThanOrEqualTo("hard", hardlevel);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Integer id = 0;
                    List<Question> tmp = new ArrayList<Question>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        tmp.add(Question.mapToQuestion(document.getData(), document.getId()));
                    }
                    myCallback.MyCallback(tmp);
                } else {
                }
            }
        });
    }
    public void updateQuestion(int id){
        tv_question.setText(questions.get(id).getQuestion());
        tv_number.setText(Integer.toString(id));
        List<String> tmp = questions.get(id).getChoices();
        Collections.shuffle(tmp);
        bt_choice1.setText(tmp.get(0));
        bt_choice2.setText(tmp.get(1));
        bt_choice3.setText(tmp.get(2));
        bt_choice4.setText(tmp.get(3));
    }
}

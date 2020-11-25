package com.example.group09ktpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckQuizContribute extends AppCompatActivity {
    RecyclerView lv;
    List<Question> questions = new ArrayList<Question>();
    private QuestionDetailAdapter adapter;
    View ChildView ;
    int RecyclerViewItemPosition;
    List<Integer> listPos=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check);

        lv = findViewById(R.id.listView);

        loadQuestions(new MyCallback() {
            @Override
            public void MyCallback(List<Question> questionss) {
                questions = questionss;
                updateList(questionss);
            }

            @Override
            public void MyCallback2(List<String> categories, List<Integer> hardLevel) {

            }

            @Override
            public void MyCallback3(boolean Admin, String user) {

            }
        });

        lv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getBaseContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);

                    Intent intent = new Intent(CheckQuizContribute.this, CheckQuizContributeDetail.class);
                    intent.putExtra("myQuestion",new Gson().toJson(questions.get(listPos.get(RecyclerViewItemPosition))));
                    startActivity(intent);

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        lv.removeAllViewsInLayout();
        updateList(questions);
    }

    public void loadQuestions(final MyCallback myCallback){
        Query query;
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        query = db.collection("Question").whereEqualTo("isConfirmed", false);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int id =0;
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

    public void updateList(List<Question> questionss){
        List<String> category= new ArrayList<>();
        List<Integer> hard= new ArrayList<>();
        for (Question question : questionss){
            if(question.getIsConfirmed()==false){
            category.add(question.getCategory());
            hard.add(question.getHardLevel());
            listPos.add(questionss.indexOf(question));
            }
        }

        adapter = new QuestionDetailAdapter(this, category, hard);
        lv.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayoutManager);
    }
}
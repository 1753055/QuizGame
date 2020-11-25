package com.example.group09ktpm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bt_newgame;
    Button bt_highscore;
    Button bt_quit;
    Button bt_contribute;
    Button btn_settings;
    boolean playSound = true;
    boolean isAdmin = false;
    boolean isLogin = false;
    String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            playSound = bundle.getBoolean("sound");
            isAdmin = bundle.getBoolean("isAdmin");
            isLogin = bundle.getBoolean("isLogin");
            username = bundle.getString("username");
        }

        //tg_sound = (ToggleButton) findViewById(R.id.toggleButton);

        bt_highscore =(Button) findViewById(R.id.highscore);
        bt_newgame = (Button)findViewById(R.id.newgame);
        bt_quit =(Button) findViewById(R.id.quit);
        bt_contribute =(Button) findViewById(R.id.contribute);
        btn_settings = (Button) findViewById(R.id.toggleButton);


//        isAdmin(new MyCallback() {
//            @Override
//            public void MyCallback(List<Question> questions) {
//
//            }
//
//            @Override
//            public void MyCallback2(List<String> categories, List<Integer> hardLevel) {
//
//            }
//
//            @Override
//            public void MyCallback3(boolean Admin) {
//                isAdmin = Admin;
//
//            }
//        });
        if(isAdmin){bt_highscore.setText("Check Quiz");}
        bt_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewGame.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("sound", playSound);
                bundle.putBoolean("isAdmin", isAdmin);
                bundle.putBoolean("isLogin", isLogin);
                bundle.putString("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        bt_contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Contribute.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("sound", playSound);
                bundle.putBoolean("isAdmin", isAdmin);
                bundle.putBoolean("isLogin", isLogin);
                bundle.putString("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingClass setting = new SettingClass();
                setting.showPopupWindow(view, username, getSupportFragmentManager());
            }
        });

        bt_highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("sound", playSound);
                bundle.putBoolean("isAdmin", isAdmin);
                bundle.putBoolean("isLogin", isLogin);
                bundle.putString("username", username);
                if(isAdmin){
                    Intent intent = new Intent(MainActivity.this, CheckQuizContribute.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, Highsocre.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //stop service and stop music
        stopService(new Intent(MainActivity.this, SoundService.class));
        super.onDestroy();
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }


}

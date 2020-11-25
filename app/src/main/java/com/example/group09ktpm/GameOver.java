package com.example.group09ktpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

public class GameOver extends AppCompatActivity {
    private TextView tv_score;
    private Button btnShareImage, btnExit, btnReplay;
    int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            score = bundle.getInt("score");
        }
        setContentView(R.layout.activity_game_over);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnShareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = "Hieu Pham đã được "+ score + " điểm trong the Quizzz. Bạn được bao nhiêu?";
                shareLinkFB(title);
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOver.this, NewGame.class);
                startActivity(intent);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControl() {
        tv_score = (TextView) findViewById(R.id.tv_score);
        btnShareImage = (Button) findViewById(R.id.btn_share_image);
        btnExit = findViewById(R.id.exitButton);
        btnReplay = findViewById(R.id.playAgainButton);
        tv_score.setText(score + "");
    }

    // Check permistion cho android 6.0
    public void runTimePermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
    }
    public void shareLinkFB(String title) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://github.com/1753052/ktpm-group09"))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag(title)
                        .build())
                .build();
        ShareDialog.show(this, content);
    }

    public void sharePhoto(Bitmap b, String caption) {
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(b)
                .setCaption(caption)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo).build();
        ShareDialog.show(this, content);
    }
}

package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intentsplash=new Intent(getApplicationContext(),MainActivity.class);
        Animation rotate= AnimationUtils.loadAnimation(this,R.anim.rotateanim);
        img=findViewById(R.id.img);
        img.startAnimation(rotate);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intentsplash);
                finish();
                Log.d("nish", "run: hdrgeg");
            }
        },1000);
    }
}
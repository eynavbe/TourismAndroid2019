package com.example.myprojectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    ProgressBar pbSplashScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        pbSplashScreen = findViewById(R.id.pbSplashScreen);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashScreen.this,MainActivity.class));
//            }
//        },3000);
        pbSplashScreen.setScaleY(3f);


        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i = 0;i<=pbSplashScreen.getMax();i++){
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pbSplashScreen.setProgress(i);
                }
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
            }

        };
        thread.start();

    }
}

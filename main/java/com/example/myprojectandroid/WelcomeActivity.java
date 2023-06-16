package com.example.myprojectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity  extends AppCompatActivity {

    private Button btn;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btn = (Button) findViewById(R.id.btn);
        preferenceHelper = new PreferenceHelper(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.putIntro("");
//                onBackPressed();
                Intent intent = new Intent(WelcomeActivity.this, HomeIntroActivithy.class);
                startActivity(intent);
            }
        });

    }
}

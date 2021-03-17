package com.example.pruebas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(com.example.pruebas.SplashActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(1,R.anim.slash_out);
                finish();
            }
        }, 2000);


    }
}
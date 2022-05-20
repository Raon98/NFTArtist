package com.example.nftartist.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import com.example.nftartist.R;
import com.wang.avi.AVLoadingIndicatorView;

public class Intro extends AppCompatActivity {

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);


        String indicator=getIntent().getStringExtra("indicator");

        avi= (AVLoadingIndicatorView) findViewById(R.id.loading);
        avi.setVisibility(avi.VISIBLE);
        avi.setIndicator(indicator);
        avi.show();

        Handler handler = new Handler(); //객체생성
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intro.this, Login.class);
                startActivity(intent);
                finish();
            }

        },2400);
    }

}
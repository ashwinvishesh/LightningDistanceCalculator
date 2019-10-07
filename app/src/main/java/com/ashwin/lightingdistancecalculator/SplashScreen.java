package com.ashwin.lightingdistancecalculator;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

       final Handler handler = new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent = new Intent(SplashScreen.this,MainActivity.class);
               startActivity(intent);
               finish();
           }
       },2000);
    }
}

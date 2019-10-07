package com.ashwin.lightingdistancecalculator;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private float deciseconds=0,sec;
    private int distance = 0,min;
    private boolean startRun = false;
    private String time = "00:00";
    TextView timetv,disttv;
    Button thebutton;
    RecyclerView recyclerView;
    CountDownTimer myTimer;
    ImageView resetBt,infoBt;

    private static final String TAG = "MainActivity";
    private ArrayList<String> distances = new ArrayList<>();
    private ArrayList<String> timestamps = new ArrayList<>();
    RecyclerViewAdapter adapter;

    DateFormat df = new SimpleDateFormat("h:mm.ss a");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState !=null)
        {
            deciseconds = savedInstanceState.getInt("seconds");
            startRun = savedInstanceState.getBoolean("startRun");
        }

        timetv = findViewById(R.id.timetv);
        disttv = findViewById(R.id.disttv);
        thebutton = findViewById(R.id.thebutton);
        recyclerView = findViewById(R.id.logrecycler);
        resetBt = findViewById(R.id.resetBt);
        infoBt = findViewById(R.id.infoBt);

        initRecyclerView();

        thebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(startRun)
                {
                    startRun=false;
                    myTimer.cancel();
                    distance = (int)((deciseconds/10) * 343);
                    disttv.setText(distance+" ");
                    distances.add(distance+" m ");
                    timestamps.add(df.format(Calendar.getInstance().getTime()));
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    thebutton.setText("Lightning");
                    resetBt.setVisibility(View.INVISIBLE);
                }
                else
                {
                    deciseconds=0;
                    myTimer.start();
                    thebutton.setText("Thunder");
                    resetBt.setVisibility(View.VISIBLE);
                    startRun=true;
                }
            }
        });

        resetBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startRun=false;
                myTimer.cancel();
                disttv.setText("0");
                timetv.setText("00:00.0");
                resetBt.setVisibility(View.INVISIBLE);

            }
        });

        infoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                startActivity(intent);
            }
        });

        Timer();
    }


   private void Timer()
   {
       myTimer = new CountDownTimer(1000000,100)
       {
           @Override
           public void onTick(long l)
           {
               ++deciseconds;
               min = (int) (deciseconds/600);
               sec = ((deciseconds/10)%60);
               time = String.format("%02d:%.1f ",min,sec);

               timetv.setText(time);
           }

           @Override
           public void onFinish()
           {

           }
       };
   }

   private void initRecyclerView()
   {
        Log.d(TAG, "initRecyclerView: init");
        adapter = new RecyclerViewAdapter(distances,timestamps,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}

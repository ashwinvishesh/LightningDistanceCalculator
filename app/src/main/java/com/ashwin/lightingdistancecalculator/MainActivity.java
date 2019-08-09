package com.ashwin.lightingdistancecalculator;

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
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private float seconds=0;
    private int timecounter=0;
    private int distance = 0;
    private boolean startRun = false;
    private String time = "00:00";
    TextView timetv,disttv;
    Button thebutton;
    RecyclerView recyclerView;
    ConstraintLayout constraintLayout;

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
            seconds = savedInstanceState.getInt("seconds");
            startRun = savedInstanceState.getBoolean("startRun");
        }

        timetv = findViewById(R.id.timetv);
        disttv = findViewById(R.id.disttv);
        thebutton = findViewById(R.id.thebutton);
        recyclerView =findViewById(R.id.logrecycler);
        constraintLayout = findViewById(R.id.constraintLayout);

        initRecyclerView();

        thebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(startRun)
                {
                    startRun = false;
                    distance = (int)((seconds/10) * 343);
                    disttv.setText(distance+" ");
                    distances.add(distance+" m ");
                    timestamps.add(df.format(Calendar.getInstance().getTime()));
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    thebutton.setText("Lightning");
                }
                else
                {
                    seconds=0;
                    startRun = true;
                    thebutton.setText("Thunder");
                }
            }
        });

        Timer();
    }


    private void Timer()
    {
        final Handler handler = new Handler();
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {

                int min = (int) (seconds/600);
                float sec = ((seconds/10)%60);
                time = String.format("%02d:%.1f ",min,sec);

                timecounter = (int) (Math.random()*10000);
                Log.e(TAG, "run: "+timecounter );
                if((timecounter%6)==0)
                {
                    constraintLayout.setBackgroundColor(getResources().getColor(R.color.lightGrey));
                }
                else
                {
                    constraintLayout.setBackgroundColor(getResources().getColor(R.color.lightGrey2));
                }

                timetv.setText(time);
                if(startRun)
                {
                    seconds++;
                }
                handler.postDelayed(this,100);
            }
        });
    }

    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView: init");
        adapter = new RecyclerViewAdapter(distances,timestamps,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}

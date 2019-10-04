package com.ashwin.lightingdistancecalculator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> distances = new ArrayList<>();
    private ArrayList<String> timestamps = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> distances,ArrayList<String> timestamps, Context mContext)
    {
        this.distances = distances;
        this.timestamps = timestamps;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        Log.d(TAG,"onBindViewHolder: called.");
        viewHolder.logdist.setText(distances.get(i));
        viewHolder.logtime.setText(timestamps.get(i));

        if(i%2==0)
        {
            viewHolder.logdist.setBackgroundColor(mContext.getResources().getColor(R.color.darkGrey));
            viewHolder.logtime.setBackgroundColor(mContext.getResources().getColor(R.color.darkGrey));
        }
        else
        {
            viewHolder.logdist.setBackgroundColor(mContext.getResources().getColor(R.color.almostBlack));
            viewHolder.logtime.setBackgroundColor(mContext.getResources().getColor(R.color.almostBlack));

        }
    }

    @Override
    public int getItemCount()
    {
        return distances.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView logdist,logtime;
        LinearLayout parentlayout;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            logdist = itemView.findViewById(R.id.log_disttv);
            logtime = itemView.findViewById(R.id.log_timetv);
            parentlayout = itemView.findViewById(R.id.parentlayout);
        }
    }
}
